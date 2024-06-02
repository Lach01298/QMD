package lach_01298.qmd.particleChamber;


import static lach_01298.qmd.recipes.QMDRecipes.target_chamber;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import lach_01298.qmd.QMD;
import lach_01298.qmd.config.QMDConfig;
import lach_01298.qmd.enums.EnumTypes.IOType;
import lach_01298.qmd.multiblock.InventoryHelper;
import lach_01298.qmd.multiblock.network.ParticleChamberUpdatePacket;
import lach_01298.qmd.multiblock.network.TargetChamberUpdatePacket;
import lach_01298.qmd.particle.ParticleStack;
import lach_01298.qmd.particleChamber.tile.IParticleChamberPart;
import lach_01298.qmd.particleChamber.tile.TileParticleChamber;
import lach_01298.qmd.particleChamber.tile.TileParticleChamberBeam;
import lach_01298.qmd.particleChamber.tile.TileParticleChamberBeamPort;
import lach_01298.qmd.particleChamber.tile.TileParticleChamberDetector;
import lach_01298.qmd.particleChamber.tile.TileParticleChamberEnergyPort;
import lach_01298.qmd.particleChamber.tile.TileTargetChamberController;
import lach_01298.qmd.recipe.QMDRecipe;
import lach_01298.qmd.recipe.QMDRecipeInfo;
import lach_01298.qmd.util.Equations;
import nc.multiblock.tile.TileBeefAbstract.SyncReason;
import nc.tile.internal.fluid.Tank;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;

public class TargetChamberLogic extends ParticleChamberLogic
{

	
	public QMDRecipeInfo<QMDRecipe> recipeInfo;
	
	public QMDRecipeInfo<QMDRecipe> rememberedRecipeInfo;

	protected TileParticleChamber mainChamber;
	
	
	public long particleWorkDone = 0;
	public long recipeParticleWork = 100;
	public boolean outputSwitched = false;
	
	public TargetChamberLogic(ParticleChamberLogic oldLogic)
	{
		super(oldLogic);
		
		/*
		beam 0 = input particle 1
		beam 1 = output particle 1
		beam 2 = output particle 2
		beam 3 = output particle 3

		*/

	}
	
	@Override
	public String getID() 
	{
		return "target_chamber";
	}
	
	@Override
	public boolean isMachineWhole()
	{
		
		//sizing
		if (getMultiblock().getExteriorLengthX() != getMultiblock().getExteriorLengthZ())
		{
			multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.chamber.must_be_square", null);
			return false;
		}
		if (getMultiblock().getExteriorLengthX() % 2 != 1)
		{
			multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.chamber.must_be_odd", null);
			return false;
		}
		
		
		
		BlockPos middle =getMultiblock().getExtremeCoord(false, false, false).add(getMultiblock().getExteriorLengthX()/2,getMultiblock().getExteriorLengthY()/2,getMultiblock().getExteriorLengthZ()/2);
		
		//target
		if (!(getMultiblock().WORLD.getTileEntity(middle) instanceof TileParticleChamber))
		{
			multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.chamber.must_have_target", middle);
			return false;
		}
		
		TileParticleChamber target = (TileParticleChamber) getMultiblock().WORLD.getTileEntity(middle);
		
		// target beams
		int ports = 0;
		for (EnumFacing face : EnumFacing.HORIZONTALS)
		{
			if (getMultiblock().WORLD.getTileEntity(middle.offset(face, getMultiblock().getExteriorLengthX() / 2)) instanceof TileParticleChamberBeamPort)
			{
				ports++;
				for (int i = 1; i <= getMultiblock().getExteriorLengthX() / 2 - 1; i++)
				{
					if (!(getMultiblock().WORLD.getTileEntity(middle.offset(face, i)) instanceof TileParticleChamberBeam))
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
			multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.need_energy_ports", null);
			return false;
		}
		
		if(containsBlacklistedPart())
		{
			return false;
		}
		
		return true;
	}
	
	@Override
	public int getMinimumInteriorLength()
	{
		return 1;
	}
	
	public static final List<Pair<Class<? extends IParticleChamberPart>, String>> PART_BLACKLIST = new ArrayList<>();

	@Override
	public List<Pair<Class<? extends IParticleChamberPart>, String>> getPartBlacklist()
	{
		return PART_BLACKLIST;
	}
	
	
	
	@Override
	public void onChamberFormed()
	{
		onResetStats();
		
		getMultiblock().tanks.get(0).setCapacity(QMDConfig.particle_chamber_input_tank_capacity);
		getMultiblock().tanks.get(1).setCapacity(QMDConfig.particle_chamber_output_tank_capacity * getCapacityMultiplier());
		
		if (!getWorld().isRemote)
		{
			for (TileParticleChamber target : getPartMap(TileParticleChamber.class).values())
			{
				this.mainChamber = target;
			}

			for (TileParticleChamberDetector detector : getPartMap(TileParticleChamberDetector.class).values())
			{
				getMultiblock().requiredEnergy += detector.basePower;
				if (detector.isInvalidPostion(mainChamber.getPos()))
				{
					getMultiblock().efficiency += detector.efficiency;
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

			int distance = getMultiblock().getExteriorLengthX() / 2;
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
		
		getMultiblock().beams.get(0).setParticleStack(null);
		pull();
		
		if (isChamberOn())
		{

			if (getMultiblock().energyStorage.extractEnergy(getMultiblock().requiredEnergy,true) == getMultiblock().requiredEnergy)
			{
				
				
				refreshRecipe();
				
				if(recipeInfo != null)
				{
					if(rememberedRecipeInfo != null)
					{
						if(rememberedRecipeInfo.getRecipe() !=recipeInfo.getRecipe())
						{
							particleWorkDone= 0;
						}	
					}
					rememberedRecipeInfo = recipeInfo;
					
					if(canProduceProduct())
					{
						getMultiblock().energyStorage.changeEnergyStored(-getMultiblock().requiredEnergy);
						particleWorkDone += getMultiblock().beams.get(0).getParticleStack().getAmount();
						
						produceProduct();
						produceBeams();
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
	

	@Override
	public boolean toggleSetting(BlockPos pos, int ioNumber)
	{
		if(ioNumber == 0 || ioNumber == 2)
		{
			return false;
		}
		
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
			
		
			if (getWorld().getTileEntity(port.getPos().offset(facing, getMultiblock().getExteriorLengthX()-1)) instanceof TileParticleChamberBeamPort)
			{
				
				TileParticleChamberBeamPort port2 = (TileParticleChamberBeamPort) getWorld().getTileEntity(port.getPos().offset(facing, getMultiblock().getExteriorLengthX()-1));
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
		TileTargetChamberController inv = (TileTargetChamberController) getMultiblock().controller;
		ItemStack productItem = recipeInfo.getRecipe().getItemProducts().get(0).getStack();
		FluidStack productFluid = recipeInfo.getRecipe().getFluidProducts().get(0).getStack();
		
		
		// some strange safety measure
		if(inv.getInventoryStacks().get(1).getCount() <= 0)
		{
			inv.getInventoryStacks().set(1, ItemStack.EMPTY);
		}
			
		if(productItem != null)
		{
			if (!inv.getInventoryStacks().get(1).isItemEqual(productItem) && inv.getInventoryStacks().get(1) != ItemStack.EMPTY)
			{
				return false;
			}
			
			if (inv.getInventoryStacks().get(1).getCount() + productItem.getCount() > productItem.getMaxStackSize())
			{
				return false;	
			}
		}
		
		
		if(productFluid != null)
		{
			if(getMultiblock().tanks.get(1).fill(productFluid, false) != productFluid.amount)
			{
				return false;
			}	
		}
		
		return true;
	}

	private void produceProduct()
	{
		recipeParticleWork = (long) Math.max(recipeInfo.getRecipe().getCrossSection()*recipeInfo.getRecipe().getParticleIngredients().get(0).getStack().getAmount(),recipeInfo.getRecipe().getParticleIngredients().get(0).getStack().getAmount()/getMultiblock().efficiency);
		particleWorkDone=Math.min(particleWorkDone, recipeParticleWork*64);
		
		while(particleWorkDone >= recipeParticleWork && canProduceProduct())
		{
			
			TileTargetChamberController inv = (TileTargetChamberController) getMultiblock().controller;
			ItemStack productItem = recipeInfo.getRecipe().getItemProducts().get(0).getStack();
			if(productItem == null)
			{
				productItem = ItemStack.EMPTY;
			}
			else
			{
				productItem.setCount(recipeInfo.getRecipe().getItemProducts().get(0).getNextStackSize(0));
			}
					
			InventoryHelper.addItem(1,productItem,inv.getInventoryStacks(),inv);
			InventoryHelper.removeItem(0,recipeInfo.getRecipe().getItemIngredients().get(0).getMaxStackSize(0),inv.getInventoryStacks(),inv);
			
			
			FluidStack productFluid = recipeInfo.getRecipe().getFluidProducts().get(0).getStack();
			FluidStack ingredientFluid = recipeInfo.getRecipe().getFluidIngredients().get(0).getStack();
			
			if(productFluid != null)
			{
				productFluid.amount = recipeInfo.getRecipe().getFluidProducts().get(0).getNextStackSize(0);
				getMultiblock().tanks.get(1).fill(productFluid, true);
			}
			
			if(ingredientFluid != null)
			{
				getMultiblock().tanks.get(0).drain(ingredientFluid, true);
			}
			
		
			particleWorkDone = Math.max(0, particleWorkDone - recipeParticleWork);
		}

	}
	

	
	

	public void onResetStats()
	{
		getMultiblock().efficiency =1;
		getMultiblock().requiredEnergy =QMDConfig.target_chamber_power;
		
	}
	
	
	
	private void produceBeams()
	{
		
		ParticleStack input = getMultiblock().beams.get(0).getParticleStack();
		ParticleStack outputPlus = recipeInfo.getRecipe().getParticleProducts().get(0).getStack();
		ParticleStack outputNeutral = recipeInfo.getRecipe().getParticleProducts().get(1).getStack();
		ParticleStack outputMinus = recipeInfo.getRecipe().getParticleProducts().get(2).getStack();
		
		long energyReleased = recipeInfo.getRecipe().getEnergyReleased();
		double crossSection = recipeInfo.getRecipe().getCrossSection();
		double outputFactor = crossSection * getMultiblock().efficiency;
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
		
		
		getMultiblock().beams.get(1).setParticleStack(outputPlus);
		if(outputPlus != null)
		{
			getMultiblock().beams.get(1).getParticleStack().setMeanEnergy(Math.round((input.getMeanEnergy() + energyReleased) / (double) particlesOut));
			getMultiblock().beams.get(1).getParticleStack().setAmount((int) Math.round(outputPlus.getAmount() * outputFactor * input.getAmount()));
			getMultiblock().beams.get(1).getParticleStack().setFocus(input.getFocus()-Equations.focusLoss(getBeamLength()/2d, input)-Equations.focusLoss(getBeamLength()/2d, getMultiblock().beams.get(1).getParticleStack()));
		}
		
		
		getMultiblock().beams.get(2).setParticleStack(outputNeutral);
		if(outputNeutral != null)
		{
			
			
			getMultiblock().beams.get(2).getParticleStack().setMeanEnergy(Math.round((input.getMeanEnergy() + energyReleased) / (double) particlesOut));
			getMultiblock().beams.get(2).getParticleStack().setAmount((int) Math.round(outputNeutral.getAmount() * outputFactor * input.getAmount()));
			getMultiblock().beams.get(2).getParticleStack().setFocus(input.getFocus()-Equations.focusLoss(getBeamLength()/2d, input)-Equations.focusLoss(getBeamLength()/2d, getMultiblock().beams.get(2).getParticleStack()));
		}
		
		getMultiblock().beams.get(3).setParticleStack(outputMinus);
		if(outputMinus != null)
		{
			getMultiblock().beams.get(3).getParticleStack().setMeanEnergy(Math.round((input.getMeanEnergy() + energyReleased) / (double) particlesOut));
			getMultiblock().beams.get(3).getParticleStack().setAmount((int) Math.round(outputMinus.getAmount() * outputFactor * input.getAmount()));
			getMultiblock().beams.get(3).getParticleStack().setFocus(input.getFocus()-Equations.focusLoss(getBeamLength()/2d, input)-Equations.focusLoss(getBeamLength()/2d, getMultiblock().beams.get(3).getParticleStack()));
		}
	}


	
	private void resetBeams()
	{
		getMultiblock().beams.get(1).setParticleStack(null);
		getMultiblock().beams.get(2).setParticleStack(null);
		getMultiblock().beams.get(3).setParticleStack(null);
	}
	
	protected void refreshRecipe() 
	{
		TileTargetChamberController cont = (TileTargetChamberController) getMultiblock().controller;
		ArrayList<ItemStack> items = new ArrayList<ItemStack>();
		ItemStack item = cont.getInventoryStacks().get(0).copy();
		items.add(item);
		ArrayList<Tank> tanks = new ArrayList<Tank>();
		tanks.add(getMultiblock().tanks.get(0));
		ArrayList<ParticleStack> particles = new ArrayList<ParticleStack>();
		particles.add(getMultiblock().beams.get(0).getParticleStack());
		
		recipeInfo = target_chamber.getRecipeInfoFromInputs(items, tanks, particles);
		
	}
	
	@Override
	public ParticleChamberUpdatePacket getMultiblockUpdatePacket()
	{
		return new TargetChamberUpdatePacket(getMultiblock().controller.getTilePos(), getMultiblock().isChamberOn,
				getMultiblock().requiredEnergy, getMultiblock().efficiency, getMultiblock().energyStorage,
				particleWorkDone,recipeParticleWork, getMultiblock().tanks, getMultiblock().beams);
	}
	
	@Override
	public void onMultiblockUpdatePacket(ParticleChamberUpdatePacket message)
	{
		super.onMultiblockUpdatePacket(message);
		if (message instanceof TargetChamberUpdatePacket)
		{
			TargetChamberUpdatePacket packet = (TargetChamberUpdatePacket) message;
			getMultiblock().beams = packet.beams;
			for (int i = 0; i < getMultiblock().tanks.size(); i++) getMultiblock().tanks.get(i).readInfo(message.tanksInfo.get(i));
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
	
	
	
}
