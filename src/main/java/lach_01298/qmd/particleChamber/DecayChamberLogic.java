package lach_01298.qmd.particleChamber;

import com.google.common.collect.Lists;
import lach_01298.qmd.QMD;
import lach_01298.qmd.config.QMDConfig;
import lach_01298.qmd.enums.EnumTypes.IOType;
import lach_01298.qmd.multiblock.network.*;
import lach_01298.qmd.particle.ParticleStack;
import lach_01298.qmd.particleChamber.tile.*;
import lach_01298.qmd.recipe.*;
import lach_01298.qmd.util.Equations;
import nc.tile.internal.fluid.Tank;
import nc.tile.multiblock.TilePartAbstract.SyncReason;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

import static lach_01298.qmd.recipes.QMDRecipes.decay_chamber;

public class DecayChamberLogic extends ParticleChamberLogic
{

	
	
	public QMDRecipeInfo<QMDRecipe> recipeInfo;
	
	protected TileParticleChamber mainChamber;
	
	
	public boolean outputSwitched = false;
	
	public DecayChamberLogic(ParticleChamberLogic oldLogic)
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
		return "decay_chamber";
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
	
	public static final List<Pair<Class<? extends IParticleChamberPart>, String>> PART_BLACKLIST = Lists.newArrayList(
			Pair.of(TileParticleChamberFluidPort.class, QMD.MOD_ID + ".multiblock_validation.chamber.no_fluid_ports"),
			Pair.of(TileParticleChamberPort.class, QMD.MOD_ID + ".multiblock_validation.chamber.no_item_ports"));
	
	@Override
	public List<Pair<Class<? extends IParticleChamberPart>, String>> getPartBlacklist()
	{
		return PART_BLACKLIST;
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
				getMultiblock().requiredEnergy += detector.basePower;
				if (detector.isValidPostion(mainChamber.getPos()))
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

			if (getWorld().getTileEntity(mainChamber.getPos().offset(facing.rotateY(), distance)) instanceof TileParticleChamberBeamPort)
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
			if (getWorld().getTileEntity(mainChamber.getPos().offset(facing.rotateY().rotateY(), distance)) instanceof TileParticleChamberBeamPort)
			{
				TileParticleChamberBeamPort port = (TileParticleChamberBeamPort) getWorld().getTileEntity(mainChamber.getPos().offset(facing.rotateY().rotateY(), distance));
				port.setIONumber(2);

			}
			if (getWorld().getTileEntity(mainChamber.getPos().offset(facing.rotateY().rotateY().rotateY(), distance)) instanceof TileParticleChamberBeamPort)
			{
				TileParticleChamberBeamPort port = (TileParticleChamberBeamPort) getWorld().getTileEntity(mainChamber.getPos().offset(facing.rotateY().rotateY().rotateY(), distance));
				if (outputSwitched)
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
					getMultiblock().energyStorage.changeEnergyStored(-getMultiblock().requiredEnergy);
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
		
		push();
		return super.onUpdateServer();

	}
	

	public void onResetStats()
	{
		getMultiblock().efficiency =1;
		getMultiblock().requiredEnergy = QMDConfig.decay_chamber_power;
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
	
	private void produceBeams()
	{
		ParticleStack input = getMultiblock().beams.get(0).getParticleStack();
		ParticleStack outputPlus = recipeInfo.recipe.getParticleProducts().get(0).getStack();
		ParticleStack outputNeutral = recipeInfo.recipe.getParticleProducts().get(1).getStack();
		ParticleStack outputMinus = recipeInfo.recipe.getParticleProducts().get(2).getStack();
		
		long energyReleased = recipeInfo.recipe.getEnergyReleased();
		double crossSection = recipeInfo.recipe.getCrossSection();
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
			getMultiblock().beams.get(1).getParticleStack().setAmount((int) (outputPlus.getAmount() * outputFactor * input.getAmount()));
			getMultiblock().beams.get(1).getParticleStack().setFocus(input.getFocus()-Equations.focusLoss(getBeamLength()/2d, input)-Equations.focusLoss(getBeamLength()/2d, getMultiblock().beams.get(1).getParticleStack()));
		}
		
		
		getMultiblock().beams.get(2).setParticleStack(outputNeutral);
		if(outputNeutral != null)
		{
			getMultiblock().beams.get(2).getParticleStack().setMeanEnergy(Math.round((input.getMeanEnergy() + energyReleased) / (double) particlesOut));
			getMultiblock().beams.get(2).getParticleStack().setAmount((int) (outputNeutral.getAmount() * outputFactor * input.getAmount()));
			getMultiblock().beams.get(2).getParticleStack().setFocus(input.getFocus()-Equations.focusLoss(getBeamLength()/2d, input)-Equations.focusLoss(getBeamLength()/2d, getMultiblock().beams.get(2).getParticleStack()));
		}
		
		getMultiblock().beams.get(3).setParticleStack(outputMinus);
		if(outputMinus != null)
		{
			getMultiblock().beams.get(3).getParticleStack().setMeanEnergy(Math.round((input.getMeanEnergy() + energyReleased) / (double) particlesOut));
			getMultiblock().beams.get(3).getParticleStack().setAmount((int) (outputMinus.getAmount() * outputFactor * input.getAmount()));
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
		if(getMultiblock().beams.get(0).getParticleStack() != null)
		{
			ArrayList<ParticleStack> particles = new ArrayList<ParticleStack>();
			ParticleStack input =getMultiblock().beams.get(0).getParticleStack().copy();
			particles.add(input);
			
			recipeInfo = decay_chamber.getRecipeInfoFromInputs(new ArrayList<ItemStack>(), new ArrayList<Tank>(), particles);
		}
		else
		{
			recipeInfo = null;
		}
	}
	
	@Override
	public ParticleChamberUpdatePacket getMultiblockUpdatePacket()
	{
		return new DecayChamberUpdatePacket(getMultiblock().controller.getTilePos(), getMultiblock().isChamberOn,
				getMultiblock().requiredEnergy, getMultiblock().efficiency, getMultiblock().energyStorage,
				getMultiblock().tanks,getMultiblock().beams);
	}
	
	@Override
	public void onMultiblockUpdatePacket(ParticleChamberUpdatePacket message)
	{
		super.onMultiblockUpdatePacket(message);
		if (message instanceof DecayChamberUpdatePacket)
		{
			DecayChamberUpdatePacket packet = (DecayChamberUpdatePacket) message;
			getMultiblock().beams = packet.beams;
			
		}
	}
	
	
	
	
	@Override
	public void writeToLogicTag(NBTTagCompound logicTag, SyncReason syncReason)
	{
		super.writeToLogicTag(logicTag, syncReason);
		
		logicTag.setBoolean("outputSwitched", outputSwitched);
	}

	@Override
	public void readFromLogicTag(NBTTagCompound logicTag, SyncReason syncReason)
	{
		super.readFromLogicTag(logicTag, syncReason);
		
		outputSwitched =logicTag.getBoolean("outputSwitched");
	}
	
	
	
	
	/*public ContainerMultiblockController<ParticleChamber, IParticleChamberController> getContainer(EntityPlayer player)
	{
		
		return new ContainerDecayChamberController(player, (TileDecayChamberController) getMultiblock().controller);
	}*/
	
	
}
