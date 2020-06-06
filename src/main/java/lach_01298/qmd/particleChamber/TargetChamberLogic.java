package lach_01298.qmd.particleChamber;


import static lach_01298.qmd.recipes.QMDRecipes.target_chamber;

import java.util.ArrayList;

import lach_01298.qmd.QMD;
import lach_01298.qmd.config.QMDConfig;
import lach_01298.qmd.enums.EnumTypes.IOType;
import lach_01298.qmd.multiblock.container.ContainerTargetChamberController;
import lach_01298.qmd.multiblock.network.ParticleChamberUpdatePacket;
import lach_01298.qmd.multiblock.network.TargetChamberUpdatePacket;
import lach_01298.qmd.particle.ParticleStack;
import lach_01298.qmd.particle.ParticleStorageAccelerator;
import lach_01298.qmd.particleChamber.tile.IParticleChamberController;
import lach_01298.qmd.particleChamber.tile.TileParticleChamber;
import lach_01298.qmd.particleChamber.tile.TileParticleChamberBeam;
import lach_01298.qmd.particleChamber.tile.TileParticleChamberBeamPort;
import lach_01298.qmd.particleChamber.tile.TileParticleChamberDetector;
import lach_01298.qmd.particleChamber.tile.TileParticleChamberEnergyPort;
import lach_01298.qmd.particleChamber.tile.TileTargetChamberController;
import lach_01298.qmd.recipe.QMDRecipe;
import lach_01298.qmd.recipe.QMDRecipeInfo;
import nc.multiblock.Multiblock;
import nc.multiblock.container.ContainerMultiblockController;
import nc.multiblock.tile.TileBeefAbstract.SyncReason;
import nc.tile.internal.fluid.Tank;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class TargetChamberLogic extends ParticleChamberLogic
{
	public QMDRecipeInfo<QMDRecipe> recipeInfo;

	protected TileParticleChamber mainChamber;
	
	
	public long particleWorkDone = 0;
	public long recipeParticleWork = 100;
	public boolean outputSwitched = false;
	
	public TargetChamberLogic(ParticleChamberLogic oldLogic)
	{
		super(oldLogic);
		
		getChamber().beams.add(new ParticleStorageAccelerator());
		getChamber().beams.add(new ParticleStorageAccelerator());
		getChamber().beams.add(new ParticleStorageAccelerator());
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
		if (!(getChamber().WORLD.getTileEntity(middle) instanceof TileParticleChamber))
		{
			multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.chamber.must_have_target", middle);
			return false;
		}
		
		TileParticleChamber target = (TileParticleChamber) getChamber().WORLD.getTileEntity(middle);
		
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
		
		
		return true;
	}
	
	@Override
	public int getMinimumInteriorLength()
	{
		return 1;
	}
	
	
	@Override
	public void onChamberFormed()
	{
		onResetStats();
		if (!getWorld().isRemote)
		{
			for (TileParticleChamber target : getPartMap(TileParticleChamber.class).values())
			{
				this.mainChamber = target;
			}

			for (TileParticleChamberDetector detector : getPartMap(TileParticleChamberDetector.class).values())
			{
				getChamber().requiredEnergy += detector.basePower;
				if (detector.isInvalidPostion(mainChamber.getPos()))
				{
					getChamber().efficiency += detector.efficiency;
				}
			}


			BlockPos input = null;

			for (TileParticleChamberBeamPort tile : getPartMap(TileParticleChamberBeamPort.class).values())
			{
				if (tile.getIOType() == IOType.INPUT)
				{
					tile.setIONumber(0);
					input = tile.getPos();
				}
			}

			int distance = getChamber().getExteriorLengthX() / 2;
			EnumFacing facing = null;
			if (mainChamber.getPos().getX() == input.getX())
			{
				if (input.getZ() > mainChamber.getPos().getZ())
				{
					facing = EnumFacing.SOUTH;
				}
				else
				{
					facing = EnumFacing.NORTH;
				}

			}
			else if (mainChamber.getPos().getZ() == input.getZ())
			{
				if (input.getX() > mainChamber.getPos().getX())
				{
					facing = EnumFacing.EAST;
				}
				else
				{
					facing = EnumFacing.WEST;
				}
			}


			if (getWorld().getTileEntity(
					mainChamber.getPos().offset(facing.rotateY(), distance)) instanceof TileParticleChamberBeamPort)
			{
				TileParticleChamberBeamPort port = (TileParticleChamberBeamPort) getWorld()
						.getTileEntity(mainChamber.getPos().offset(facing.rotateY(), distance));
				if(outputSwitched)
				{
					port.setIONumber(3);
				}
				else
				{
					port.setIONumber(1);
				}

			}
			if (getWorld().getTileEntity(mainChamber.getPos().offset(facing.rotateY().rotateY(),
					distance)) instanceof TileParticleChamberBeamPort)
			{
				TileParticleChamberBeamPort port = (TileParticleChamberBeamPort) getWorld()
						.getTileEntity(mainChamber.getPos().offset(facing.rotateY().rotateY(), distance));
				port.setIONumber(2);

			}
			if (getWorld().getTileEntity(mainChamber.getPos().offset(facing.rotateY().rotateY().rotateY(),
					distance)) instanceof TileParticleChamberBeamPort)
			{
				TileParticleChamberBeamPort port = (TileParticleChamberBeamPort) getWorld()
						.getTileEntity(mainChamber.getPos().offset(facing.rotateY().rotateY().rotateY(), distance));
				if(outputSwitched)
				{
					port.setIONumber(1);
				}
				else
				{
					port.setIONumber(3);
				}

			}

		}

		super.onChamberFormed();
	}
	
	
	public void onMachineDisassembled()
	{	
		mainChamber = null;
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
				
				if(recipeInfo != null)
				{
					
					
					if(canProduceProduct())
					{
						
						produceProduct();
						produceBeams();
					}	
				}
				else
				{
				
					particleWorkDone= 0;
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
		push();
		
		return super.onUpdateServer();

	}
	

	public boolean switchOutputs(BlockPos pos)
	{
		
		if (getWorld().getTileEntity(pos) instanceof TileParticleChamberBeamPort)
		{
			if(outputSwitched)
			{
				outputSwitched = false;
			}
			else
			{
				outputSwitched = true;
			}
			
			TileParticleChamberBeamPort	port = (TileParticleChamberBeamPort) getWorld().getTileEntity(pos);
			if(port.getIONumber() ==1)
			{
				port.setIONumber(3);
			}
			else if(port.getIONumber() ==3)
			{
				port.setIONumber(1);
			}
		
			EnumFacing facing = null;
			if (mainChamber.getPos().getX() == port.getPos().getX())
			{
				if (port.getPos().getZ() > mainChamber.getPos().getZ())
				{
					facing = EnumFacing.NORTH;
				}
				else
				{
					facing = EnumFacing.SOUTH;
				}

			}
			else if (mainChamber.getPos().getZ() == port.getPos().getZ())
			{
				if (port.getPos().getX() > mainChamber.getPos().getX())
				{
					facing = EnumFacing.WEST;
				}
				else
				{
					facing = EnumFacing.EAST;
				}
			}
			
		
			if (getWorld().getTileEntity(port.getPos().offset(facing, getChamber().getExteriorLengthX()-1)) instanceof TileParticleChamberBeamPort)
			{
				
				TileParticleChamberBeamPort port2 = (TileParticleChamberBeamPort) getWorld().getTileEntity(port.getPos().offset(facing, getChamber().getExteriorLengthX()-1));
				if(port2.getIONumber() ==1)
				{
					port2.setIONumber(3);	
				}
				else if(port2.getIONumber() ==3)
				{
					port2.setIONumber(1);
				}
			}
		
		return true;
		}
		return false;	
	}

	private boolean canProduceProduct()
	{
		TileTargetChamberController cont = (TileTargetChamberController) getChamber().controller;
		ItemStack product = recipeInfo.getRecipe().getItemProducts().get(0).getStack();
		if(cont.getInventoryStacks().get(1).getCount() <= 0)
		{
			cont.getInventoryStacks().set(1, ItemStack.EMPTY);
		}
		if (cont.getInventoryStacks().get(1) == ItemStack.EMPTY)
		{	
			return true;
		}
		else if (cont.getInventoryStacks().get(1).isItemEqual(product))
		{
			int count = cont.getInventoryStacks().get(1).getCount();
			if (count + product.getCount() <= product.getMaxStackSize())
			{
				return true;	
			}
		}
		return false;
	}

	private void produceProduct()
	{
		recipeParticleWork = recipeInfo.getRecipe().getParticleIngredients().get(0).getStack().getAmount();
		if (particleWorkDone >= recipeParticleWork)
		{
			TileTargetChamberController cont = (TileTargetChamberController) getChamber().controller;
			ItemStack product = recipeInfo.getRecipe().getItemProducts().get(0).getStack();

			if (cont.getInventoryStacks().get(1) == ItemStack.EMPTY)
			{
				cont.getInventoryStacks().set(1, product);
				if(cont.getInventoryStacks().get(0).getCount() -recipeInfo.getRecipe().getItemIngredients().get(0).getStack().getCount() <= 0)
				{
					cont.getInventoryStacks().set(0, ItemStack.EMPTY);
					
				}
				else
				{
					int inputCount = cont.getInventoryStacks().get(0).getCount();
					cont.getInventoryStacks().get(0).setCount(inputCount -recipeInfo.getRecipe().getItemIngredients().get(0).getStack().getCount());
					
				}
				cont.markDirtyAndNotify();
				
			}
			else if (cont.getInventoryStacks().get(1).isItemEqual(product))
			{
				int count = cont.getInventoryStacks().get(1).getCount();
				if (count + product.getCount() <= product.getMaxStackSize())
				{
					cont.getInventoryStacks().get(1).setCount(count + product.getCount());
					if(cont.getInventoryStacks().get(0).getCount() -recipeInfo.getRecipe().getItemIngredients().get(0).getStack().getCount() <= 0)
					{
						cont.getInventoryStacks().set(0, ItemStack.EMPTY);
						
						
					}
					else
					{
						int inputCount = cont.getInventoryStacks().get(0).getCount();
						cont.getInventoryStacks().get(0).setCount(inputCount -recipeInfo.getRecipe().getItemIngredients().get(0).getStack().getCount());
						
					}
					cont.markDirtyAndNotify();
					
				}

			}

			particleWorkDone =0;
		}
		else
		{
			particleWorkDone += getChamber().beams.get(0).getParticleStack().getAmount()*getChamber().efficiency;
		}
	}
	

	
	

	public void onResetStats()
	{
		getChamber().efficiency =1;
		getChamber().requiredEnergy =QMDConfig.target_chamber_power;
		
	}
	
	
	
	private void produceBeams()
	{
		
		ParticleStack input = getChamber().beams.get(0).getParticleStack();
		ParticleStack outputPlus = recipeInfo.getRecipe().getParticleProducts().get(0).getStack();
		ParticleStack outputNeutral = recipeInfo.getRecipe().getParticleProducts().get(1).getStack();
		ParticleStack outputMinus = recipeInfo.getRecipe().getParticleProducts().get(2).getStack();
		
		long energyReleased = recipeInfo.getRecipe().getEnergyRelased();
		double crossSection = recipeInfo.getRecipe().getCrossSection();
		double outputFactor = crossSection * getChamber().efficiency;
		if(outputFactor >= 1)
		{
			outputFactor = 1;
		}
		
		
		int particlesOut = 0;
		if (outputPlus != null)
		{
			particlesOut += outputPlus.getAmount();
		}
		if (outputNeutral != null)
		{
			particlesOut += outputNeutral.getAmount();
		}
		if (outputMinus != null)
		{
			particlesOut += outputMinus.getAmount();
		}
		
		
		getChamber().beams.get(1).setParticleStack(outputPlus);
		if(outputPlus != null)
		{
			getChamber().beams.get(1).getParticleStack().setMeanEnergy((input.getMeanEnergy() + energyReleased) / particlesOut);
			getChamber().beams.get(1).getParticleStack().setAmount((int) (outputPlus.getAmount() * outputFactor * input.getAmount()));
			getChamber().beams.get(1).getParticleStack().setFocus(input.getFocus());
		}
		
		
		getChamber().beams.get(2).setParticleStack(outputNeutral);
		if(outputNeutral != null)
		{
			getChamber().beams.get(2).getParticleStack().setMeanEnergy((input.getMeanEnergy() + energyReleased) / particlesOut);
			getChamber().beams.get(2).getParticleStack().setAmount((int) (outputNeutral.getAmount() * outputFactor * input.getAmount()));
			getChamber().beams.get(2).getParticleStack().setFocus(input.getFocus());
		}
		
		getChamber().beams.get(3).setParticleStack(outputMinus);
		if(outputMinus != null)
		{
			getChamber().beams.get(3).getParticleStack().setMeanEnergy((input.getMeanEnergy() + energyReleased) / particlesOut);
			getChamber().beams.get(3).getParticleStack().setAmount((int) (outputMinus.getAmount() * outputFactor * input.getAmount()));
			getChamber().beams.get(3).getParticleStack().setFocus(input.getFocus());
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
		ItemStack item =cont.getInventoryStacks().get(0).copy();
		items.add(item);
		ArrayList<ParticleStack> particles = new ArrayList<ParticleStack>();
		particles.add(getChamber().beams.get(0).getParticleStack());
		
		recipeInfo = target_chamber.getRecipeInfoFromInputs(items, new ArrayList<Tank>(), particles);
		
	}
	
	@Override
	public ParticleChamberUpdatePacket getUpdatePacket()
	{
		return new TargetChamberUpdatePacket(getChamber().controller.getTilePos(), getChamber().isChamberOn,
				getChamber().requiredEnergy, getChamber().efficiency, getChamber().energyStorage,
				getChamber().beams,particleWorkDone,recipeParticleWork);
	}
	
	@Override
	public void onPacket(ParticleChamberUpdatePacket message)
	{
		super.onPacket(message);
		if (message instanceof TargetChamberUpdatePacket)
		{
			TargetChamberUpdatePacket packet = (TargetChamberUpdatePacket) message;
			getChamber().beams = packet.beams;
			this.particleWorkDone = packet.particleCount;
			this.recipeParticleWork = packet.recipeParticleCount;
		}
	}
	
	
	
	
	@Override
	public void writeToLogicTag(NBTTagCompound logicTag, SyncReason syncReason)
	{
		super.writeToLogicTag(logicTag, syncReason);
		
		logicTag.setLong("particleCount", particleWorkDone);
		logicTag.setLong("recipeParticleCount", recipeParticleWork);
		logicTag.setBoolean("outputSwitched", outputSwitched);
	}

	@Override
	public void readFromLogicTag(NBTTagCompound logicTag, SyncReason syncReason)
	{
		super.readFromLogicTag(logicTag, syncReason);
		
		particleWorkDone=logicTag.getLong("particleCount");
		recipeParticleWork=logicTag.getLong("recipeParticleCount");
		outputSwitched =logicTag.getBoolean("outputSwitched");
	}
	
	
	
	
	public ContainerMultiblockController<ParticleChamber, IParticleChamberController> getContainer(EntityPlayer player)
	{
		
		return new ContainerTargetChamberController(player, (TileTargetChamberController) getChamber().controller);
	}
	
	
}
