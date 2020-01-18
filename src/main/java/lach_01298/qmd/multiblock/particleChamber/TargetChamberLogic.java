package lach_01298.qmd.multiblock.particleChamber;


import static lach_01298.qmd.recipe.QMDRecipes.target_chamber;

import java.util.ArrayList;

import lach_01298.qmd.EnumTypes.IOType;
import lach_01298.qmd.capabilities.CapabilityParticleStackHandler;
import lach_01298.qmd.QMD;
import lach_01298.qmd.Util;
import lach_01298.qmd.config.QMDConfig;
import lach_01298.qmd.multiblock.accelerator.Accelerator;
import lach_01298.qmd.multiblock.accelerator.AcceleratorLogic;
import lach_01298.qmd.multiblock.accelerator.QuadrupoleMagnet;
import lach_01298.qmd.multiblock.accelerator.RFCavity;
import lach_01298.qmd.multiblock.accelerator.tile.IAcceleratorComponent;
import lach_01298.qmd.multiblock.accelerator.tile.IAcceleratorController;
import lach_01298.qmd.multiblock.accelerator.tile.IAcceleratorPart;
import lach_01298.qmd.multiblock.accelerator.tile.TileAcceleratorBeam;
import lach_01298.qmd.multiblock.accelerator.tile.TileAcceleratorCooler;
import lach_01298.qmd.multiblock.accelerator.tile.TileAcceleratorEnergyPort;
import lach_01298.qmd.multiblock.accelerator.tile.TileAcceleratorInlet;
import lach_01298.qmd.multiblock.accelerator.tile.TileAcceleratorPart;
import lach_01298.qmd.multiblock.accelerator.tile.TileAcceleratorRFCavity;
import lach_01298.qmd.multiblock.accelerator.tile.TileAcceleratorSource;
import lach_01298.qmd.multiblock.container.ContainerLinearAcceleratorController;
import lach_01298.qmd.multiblock.container.ContainerTargetChamberController;
import lach_01298.qmd.multiblock.network.AcceleratorUpdatePacket;
import lach_01298.qmd.multiblock.network.LinearAcceleratorUpdatePacket;
import lach_01298.qmd.multiblock.network.ParticleChamberUpdatePacket;
import lach_01298.qmd.multiblock.network.TargetChamberUpdatePacket;
import lach_01298.qmd.multiblock.particleChamber.tile.IParticleChamberController;
import lach_01298.qmd.multiblock.particleChamber.tile.IParticleChamberPart;
import lach_01298.qmd.multiblock.particleChamber.tile.TileParticleChamberBeam;
import lach_01298.qmd.multiblock.particleChamber.tile.TileParticleChamberBeamPort;
import lach_01298.qmd.multiblock.particleChamber.tile.TileParticleChamberDetector;
import lach_01298.qmd.multiblock.particleChamber.tile.TileParticleChamberEnergyPort;
import lach_01298.qmd.multiblock.particleChamber.tile.TileParticleChamberPort;
import lach_01298.qmd.multiblock.particleChamber.tile.TileParticleChamberTarget;
import lach_01298.qmd.multiblock.particleChamber.tile.TileTargetChamberController;
import lach_01298.qmd.particle.AcceleratorStorage;
import lach_01298.qmd.particle.IParticleStackHandler;
import lach_01298.qmd.particle.ParticleStack;
import lach_01298.qmd.recipe.QMDRecipe;
import lach_01298.qmd.recipe.QMDRecipeInfo;
import nc.multiblock.Multiblock;
import nc.multiblock.MultiblockLogic;
import nc.multiblock.TileBeefBase.SyncReason;
import nc.multiblock.container.ContainerMultiblockController;
import nc.recipe.ingredient.IFluidIngredient;
import nc.tile.internal.fluid.Tank;
import nc.util.MaterialHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TargetChamberLogic extends ParticleChamberLogic
{
	public QMDRecipeInfo<QMDRecipe> recipeTargetInfo;
	
	protected TileParticleChamberTarget target;
	
	
	
	
	public int particleCount = 0;
	public int recipeParticleCount = 100;
	
	
	public TargetChamberLogic(ParticleChamberLogic oldLogic)
	{
		super(oldLogic);
		
		//add out out beams
		getChamber().beams.add(new AcceleratorStorage());
		getChamber().beams.add(new AcceleratorStorage());
		getChamber().beams.add(new AcceleratorStorage());
	}
	
	@Override
	public String getID() 
	{
		return "target_chamber";
	}
	
	@Override
	public boolean isMachineWhole(Multiblock multiblock)
	{
		
		//sizing
		if (getChamber().getExteriorLengthX() != getChamber().getExteriorLengthZ())
		{
			multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.chamber.target_chamber.must_be_square", null);
			return false;
		}
		if (getChamber().getExteriorLengthX() % 2 != 1)
		{
			multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.chamber.target_chamber.must_be_odd", null);
			return false;
		}
		
		
		
		BlockPos middle =getChamber().getExtremeCoord(false, false, false).add(getChamber().getExteriorLengthX()/2,getChamber().getExteriorLengthY()/2,getChamber().getExteriorLengthZ()/2);
		
		//target
		if (!(getChamber().WORLD.getTileEntity(middle) instanceof TileParticleChamberTarget))
		{
			multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.chamber.must_have_target", middle);
			return false;
		}
		
		TileParticleChamberTarget target = (TileParticleChamberTarget) getChamber().WORLD.getTileEntity(middle);
		
		// target beams
		int ports = 0;
		for (EnumFacing face : EnumFacing.HORIZONTALS)
		{
			if (getChamber().WORLD.getTileEntity(middle.offset(face, getChamber().getExteriorLengthX() / 2)) instanceof TileParticleChamberBeamPort)
			{
				ports++;
				for (int i = 1; i <= getChamber().getExteriorLengthX() / 2 - 1; i++)
				{
					if (!(getChamber().WORLD.getTileEntity(middle.offset(face, i)) instanceof TileParticleChamberBeam))
					{
						multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.chamber.must_be_beam", middle.offset(face, i));
						return false;
					}
				}
			}
		}
		
		if(ports != getPartMap(TileParticleChamberBeamPort.class).size())
		{
			multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.chamber.beam_port_wrong_spot", null);
			return false;
		}
		
		// has input
		int inputs =0;
		for(TileParticleChamberBeamPort tile :getPartMap(TileParticleChamberBeamPort.class).values())
		{
			if(tile.getIOType() == IOType.INPUT)
			{
				inputs++;
			}
		}
		if(inputs != 1)
		{
			multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.chamber.must_have_input_beam",null);
			return false;
		}
	
		
		// Energy Ports
		if (getPartMap(TileParticleChamberEnergyPort.class).size() < 1)
		{
			multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.chamber.need_energy_ports", null);
			return false;
		}
		
		if (getPartMap(TileParticleChamberPort.class).size() < 2)
		{
			multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.chamber.need_ports", null);
			return false;
		}
		
		return true;
	}
	
	
	@Override
	public void onChamberFormed()
	{
		onResetStats();
		if (!getWorld().isRemote)
		{
			for (TileParticleChamberTarget target : getPartMap(TileParticleChamberTarget.class).values())
			{
				this.target = target;
			}

			for (TileParticleChamberDetector detector : getPartMap(TileParticleChamberDetector.class).values())
			{
				getChamber().requiredEnergy += detector.basePower;
				if (detector.isInvalidPostion(target.getPos()))
				{
					getChamber().efficiency += detector.efficiency - 1;
				}
			}

			getChamber().efficiency /= getChamber().getInteriorVolume();
			getChamber().efficiency += 1;

			BlockPos input = null;

			for (TileParticleChamberBeamPort tile : getPartMap(TileParticleChamberBeamPort.class).values())
			{
				if (tile.getIOType() == IOType.INPUT)
				{
					tile.setIONumber(0);
					input = tile.getPos();
				}
			}

			int distance = getChamber().getInteriorLengthX() / 2;
			EnumFacing facing = null;
			if (target.getPos().getX() == input.getX())
			{
				if (input.getZ() > target.getPos().getZ())
				{
					facing = EnumFacing.SOUTH;
				}
				else
				{
					facing = EnumFacing.NORTH;
				}

			}
			else if (target.getPos().getZ() == input.getZ())
			{
				if (input.getX() > target.getPos().getX())
				{
					facing = EnumFacing.EAST;
				}
				else
				{
					facing = EnumFacing.WEST;
				}
			}

			if (getWorld().getTileEntity(
					target.getPos().offset(facing.rotateY(), distance)) instanceof TileParticleChamberBeamPort)
			{
				TileParticleChamberBeamPort port = (TileParticleChamberBeamPort) getWorld()
						.getTileEntity(target.getPos().offset(facing.rotateY(), distance));
				port.setIONumber(1);
			}
			if (getWorld().getTileEntity(target.getPos().offset(facing.rotateY().rotateY(),
					distance)) instanceof TileParticleChamberBeamPort)
			{
				TileParticleChamberBeamPort port = (TileParticleChamberBeamPort) getWorld()
						.getTileEntity(target.getPos().offset(facing.rotateY().rotateY(), distance));
				port.setIONumber(2);
			}
			if (getWorld().getTileEntity(target.getPos().offset(facing.rotateY().rotateY().rotateY(),
					distance)) instanceof TileParticleChamberBeamPort)
			{
				TileParticleChamberBeamPort port = (TileParticleChamberBeamPort) getWorld()
						.getTileEntity(target.getPos().offset(facing.rotateY().rotateY().rotateY(), distance));
				port.setIONumber(3);
			}

		}

		super.onChamberFormed();
	}
	
	
	public void onMachineDisassembled()
	{	
		target = null;
		for(TileParticleChamberBeamPort tile :getPartMap(TileParticleChamberBeamPort.class).values())
		{
			tile.setIONumber(0);
		}
		super.onMachineDisassembled();
	}
	
	

	@Override
	public boolean onUpdateServer() 
	{
		getChamber().beams.get(0).setParticleStack(null);
		pull();
		
		
		
		isChamberOn();
		
		if (getChamber().isChamberOn)
		{
			if (getChamber().energyStorage.extractEnergy(getChamber().requiredEnergy,true) == getChamber().requiredEnergy)
			{
				getChamber().energyStorage.changeEnergyStored(-getChamber().requiredEnergy);
			
				refreshRecipe();
				
				if(recipeTargetInfo != null)
				{
					if(canProduceProduct())
					{
						produceProduct();
						produceBeams();
					}	
				}
				else
				{
					particleCount= 0;
					resetBeams();
				}

			}
			else
			{
				resetBeams();
			}	
		}
		else
		{
			resetBeams();
		}
		
		return super.onUpdateServer();

	}
	

	private void pull()
	{
		for(TileParticleChamberBeamPort port : getPartMap(TileParticleChamberBeamPort.class).values())
		{
		
			if(port.getIOType() == IOType.INPUT)
			{
				for(EnumFacing face : EnumFacing.HORIZONTALS)
				{
					TileEntity tile = port.getWorld().getTileEntity(port.getPos().offset(face));
					if(tile != null)
					{
						if (tile.hasCapability(CapabilityParticleStackHandler.PARTICLE_HANDLER_CAPABILITY, face.getOpposite()))
						{
							IParticleStackHandler otherStorage = tile.getCapability(CapabilityParticleStackHandler.PARTICLE_HANDLER_CAPABILITY,face.getOpposite());
							getChamber().beams.get(0).setParticleStack(otherStorage.extractParticle(face.getOpposite()));
						}
					}
				}
			}
		}
		
	}

	private boolean canProduceProduct()
	{
		TileTargetChamberController cont = (TileTargetChamberController) getChamber().controller;
		ItemStack product = recipeTargetInfo.getRecipe().itemProducts().get(0).getStack();
		ParticleStack inputParticles = recipeTargetInfo.getRecipe().particleIngredients().get(0).getStack();
	
		
		if (cont.getInventory().getStackInSlot(1) == ItemStack.EMPTY)
		{	
			return true;
		}
		else if (cont.getInventory().getStackInSlot(1).isItemEqual(product))
		{
			int count = cont.getInventory().getStackInSlot(1).getCount();
			if (count + product.getCount() <= product.getMaxStackSize())
			{
				return true;	
			}
		}
		return false;
	}

	private void produceProduct()
	{
		recipeParticleCount = recipeTargetInfo.getRecipe().particleIngredients().get(0).getStack().getAmount();
		if (particleCount >= recipeParticleCount/getChamber().efficiency)
		{
			TileTargetChamberController cont = (TileTargetChamberController) getChamber().controller;
			ItemStack product = recipeTargetInfo.getRecipe().itemProducts().get(0).getStack();

			if (cont.getInventory().getStackInSlot(1) == ItemStack.EMPTY)
			{
				cont.getInventory().setInventorySlotContents(1, product);
				if(cont.getInventory().getStackInSlot(0).getCount() -recipeTargetInfo.getRecipe().itemIngredients().get(0).getStack().getCount() <= 0)
				{
					cont.getInventory().setInventorySlotContents(0, ItemStack.EMPTY);
					
				}
				else
				{
					int inputCount = cont.getInventory().getStackInSlot(0).getCount();
					cont.getInventory().getStackInSlot(0).setCount(inputCount -recipeTargetInfo.getRecipe().itemIngredients().get(0).getStack().getCount());
					
				}
				cont.markDirtyAndNotify();
				
			}
			else if (cont.getInventory().getStackInSlot(1).isItemEqual(product))
			{
				int count = cont.getInventory().getStackInSlot(1).getCount();
				if (count + product.getCount() <= product.getMaxStackSize())
				{
					cont.getInventory().getStackInSlot(1).setCount(count + product.getCount());
					if(cont.getInventory().getStackInSlot(0).getCount() -recipeTargetInfo.getRecipe().itemIngredients().get(0).getStack().getCount() <= 0)
					{
						cont.getInventory().setInventorySlotContents(0, ItemStack.EMPTY);
						
						
					}
					else
					{
						int inputCount = cont.getInventory().getStackInSlot(0).getCount();
						cont.getInventory().getStackInSlot(0).setCount(inputCount -recipeTargetInfo.getRecipe().itemIngredients().get(0).getStack().getCount());
						
					}
					cont.markDirtyAndNotify();
					
				}

			}

			particleCount =0;
		}
		else
		{
			particleCount += getChamber().beams.get(0).getParticleStack().getAmount();
		}
	}
	

	
	

	public void onResetStats()
	{
		getChamber().efficiency =0;
		getChamber().requiredEnergy =5000;
		
	}
	
	
	
	private void produceBeams()
	{
		int particles = 3;
		
		ParticleStack outputPlus = recipeTargetInfo.getRecipe().particleProducts().get(0).getStack();
		ParticleStack outputNeutral = recipeTargetInfo.getRecipe().particleProducts().get(1).getStack();
		ParticleStack outputMinus = recipeTargetInfo.getRecipe().particleProducts().get(2).getStack();
		
		
		if(outputPlus == null) particles--;
		if(outputNeutral == null) particles--;
		if(outputMinus == null) particles--;
		
		
		getChamber().beams.get(1).setParticleStack(outputPlus);
		if(outputPlus != null)
		{
			getChamber().beams.get(1).getParticleStack().setMeanEnergy(outputPlus.getMeanEnergy()/particles + getChamber().beams.get(0).getParticleStack().getMeanEnergy());
			getChamber().beams.get(1).getParticleStack().setLuminosity(getChamber().beams.get(0).getParticleStack().getLuminosity());
		}
		
		
		getChamber().beams.get(2).setParticleStack(outputNeutral);
		if(outputNeutral != null)
		{
			getChamber().beams.get(2).getParticleStack().setMeanEnergy(outputNeutral.getMeanEnergy()/particles + getChamber().beams.get(0).getParticleStack().getMeanEnergy());
			getChamber().beams.get(2).getParticleStack().setLuminosity(getChamber().beams.get(0).getParticleStack().getLuminosity());
		}
		
		getChamber().beams.get(3).setParticleStack(outputMinus);
		if(outputMinus != null)
		{
			getChamber().beams.get(3).getParticleStack().setMeanEnergy(outputMinus.getMeanEnergy()/particles + getChamber().beams.get(0).getParticleStack().getMeanEnergy());
			getChamber().beams.get(3).getParticleStack().setLuminosity(getChamber().beams.get(0).getParticleStack().getLuminosity());
		}
	}


	
	private void resetBeams()
	{
		getChamber().beams.get(1).setParticleStack(null);
		getChamber().beams.get(2).setParticleStack(null);
		getChamber().beams.get(3).setParticleStack(null);
	}
	
	protected void refreshRecipe() 
	{
		TileTargetChamberController cont = (TileTargetChamberController) getChamber().controller;
		ArrayList<ItemStack> items = new ArrayList<ItemStack>();
		ItemStack item =cont.getInventory().getStackInSlot(0).copy();
		items.add(item);
		ArrayList<ParticleStack> particles = new ArrayList<ParticleStack>();
		particles.add(getChamber().beams.get(0).getParticleStack());
		
		
		recipeTargetInfo = target_chamber.getRecipeInfoFromInputs(items, new ArrayList<Tank>(), particles);
	}
	
	@Override
	public ParticleChamberUpdatePacket getUpdatePacket()
	{
		return new TargetChamberUpdatePacket(getChamber().controller.getTilePos(), getChamber().isChamberOn,
				getChamber().requiredEnergy, getChamber().efficiency, getChamber().energyStorage,
				getChamber().beams,particleCount,recipeParticleCount);
	}
	
	@Override
	public void onPacket(ParticleChamberUpdatePacket message)
	{
		super.onPacket(message);
		if (message instanceof TargetChamberUpdatePacket)
		{
			TargetChamberUpdatePacket packet = (TargetChamberUpdatePacket) message;
			getChamber().beams = packet.beams;
			this.particleCount = packet.particleCount;
			this.recipeParticleCount = packet.recipeParticleCount;
		}
	}
	
	
	
	
	@Override
	public void writeToLogicTag(NBTTagCompound logicTag, SyncReason syncReason)
	{
		super.writeToLogicTag(logicTag, syncReason);
		
		logicTag.setInteger("particleCount", particleCount);
		logicTag.setInteger("recipeParticleCount", recipeParticleCount);
	}

	@Override
	public void readFromLogicTag(NBTTagCompound logicTag, SyncReason syncReason)
	{
		super.readFromLogicTag(logicTag, syncReason);
		
		particleCount=logicTag.getInteger("particleCount");
		recipeParticleCount=logicTag.getInteger("recipeParticleCount");
	}
	
	
	
	
	public ContainerMultiblockController<ParticleChamber, IParticleChamberController> getContainer(EntityPlayer player)
	{
		
		return new ContainerTargetChamberController(player, (TileTargetChamberController) getChamber().controller);
	}
	
	
}
