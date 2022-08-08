package lach_01298.qmd.particleChamber;


import static lach_01298.qmd.recipes.QMDRecipes.collision_chamber;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lach_01298.qmd.QMD;
import lach_01298.qmd.config.QMDConfig;
import lach_01298.qmd.enums.EnumTypes.IOType;
import lach_01298.qmd.multiblock.network.CollisionChamberUpdatePacket;
import lach_01298.qmd.multiblock.network.ParticleChamberUpdatePacket;
import lach_01298.qmd.particle.ParticleStack;
import lach_01298.qmd.particleChamber.tile.TileParticleChamber;
import lach_01298.qmd.particleChamber.tile.TileParticleChamberBeam;
import lach_01298.qmd.particleChamber.tile.TileParticleChamberBeamPort;
import lach_01298.qmd.particleChamber.tile.TileParticleChamberDetector;
import lach_01298.qmd.particleChamber.tile.TileParticleChamberEnergyPort;
import lach_01298.qmd.recipe.QMDRecipe;
import lach_01298.qmd.recipe.QMDRecipeInfo;
import lach_01298.qmd.util.Equations;
import nc.multiblock.tile.TileBeefAbstract.SyncReason;
import nc.tile.internal.fluid.Tank;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.math.BlockPos;

public class CollisionChamberLogic extends ParticleChamberLogic
{
	

	public QMDRecipeInfo<QMDRecipe> recipeInfo;
	
	
	public byte portASetting = 0+2;//IO setting 0,1 are already taken by the inputs
	public byte portBSetting = 1+2;
	public byte portCSetting = 2+2;
	public byte portDSetting = 3+2;

	public final static int chamberLength = 17;
	
	public CollisionChamberLogic(ParticleChamberLogic oldLogic)
	{
		super(oldLogic);	
		/*
		beam 0 = input particle 1
		beam 1 = input particle 2
		beam 2 = output particle 1
		beam 3 = output particle 2
		beam 4 = output particle 3
		beam 5 = output particle 4
		
		*/
	}
	
	@Override
	public String getID() 
	{
		return "collision_chamber";
	}
	
	@Override
	public boolean isMachineWhole()
	{
		
		Axis axis;
		
		if (getMultiblock().getExteriorLengthX() > getMultiblock().getExteriorLengthZ())
		{
			axis = Axis.X;
			if(getMultiblock().getExteriorLengthX() != chamberLength)
			{
				multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.collision_chamber.wrong_length", null);
				return false;
			}
			if (getMultiblock().getExteriorLengthZ() % 2 != 1 || getMultiblock().getExteriorLengthZ() !=getMultiblock().getExteriorLengthY())
			{
				multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.collision_chamber.must_be_odd_square", null);
				return false;
			}

			
		}
		else
		{
			axis = Axis.Z;
			if(getMultiblock().getExteriorLengthZ() != chamberLength)
			{
				multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.collision_chamber.wrong_length", null);
				return false;
			}
			if (getMultiblock().getExteriorLengthX() % 2 != 1 || getMultiblock().getExteriorLengthX() !=getMultiblock().getExteriorLengthY())
			{
				multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.collision_chamber.must_be_odd_square", null);
				return false;
			}
		}

		// particle chambers

		for (BlockPos pos : getinteriorAxisPositions(axis))
		{
			if (!(getMultiblock().WORLD.getTileEntity(pos) instanceof TileParticleChamber))
			{
				multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.collision_chamber.must_be_target", pos);
				return false;
			}
		}

		// inputs
		if (axis == Axis.X)
		{
			BlockPos end1 = getMultiblock().getExtremeInteriorCoord(false, false, false).add(-1, getMultiblock().getInteriorLengthY() / 2, getMultiblock().getInteriorLengthZ() / 2);
			BlockPos end2 = getMultiblock().getExtremeInteriorCoord(true, false, false).add(1, getMultiblock().getInteriorLengthY() / 2, getMultiblock().getInteriorLengthZ() / 2);
			if((getMultiblock().WORLD.getTileEntity(end1) instanceof TileParticleChamberBeamPort))
			{
				TileParticleChamberBeamPort port = (TileParticleChamberBeamPort) getMultiblock().WORLD.getTileEntity(end1);
				if(port.getIOType() != IOType.INPUT)
				{
					multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.collision_chamber.must_be_input_beam_port", end1);
					return false;
				}
			}
			else
			{
				multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.collision_chamber.must_be_input_beam_port", end1);
				return false;
			}
			
			
			if((getMultiblock().WORLD.getTileEntity(end2) instanceof TileParticleChamberBeamPort))
			{
				TileParticleChamberBeamPort port = (TileParticleChamberBeamPort) getMultiblock().WORLD.getTileEntity(end2);
				if(port.getIOType() != IOType.INPUT)
				{
					multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.collision_chamber.must_be_input_beam_port", end2);
					return false;
				}
			}
			else
			{
				multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.collision_chamber.must_be_input_beam_port", end2);
				return false;
			}
					
	
		}

		if (axis == Axis.Z)
		{
			
			BlockPos end1 = getMultiblock().getExtremeInteriorCoord(false, false, false).add(getMultiblock().getInteriorLengthX() / 2, getMultiblock().getInteriorLengthY() / 2, -1);
			BlockPos end2 =	getMultiblock().getExtremeInteriorCoord(false, false, true).add(getMultiblock().getInteriorLengthX() / 2, getMultiblock().getInteriorLengthY() / 2, 1);
			

			if((getMultiblock().WORLD.getTileEntity(end1) instanceof TileParticleChamberBeamPort))
			{
				TileParticleChamberBeamPort port = (TileParticleChamberBeamPort) getMultiblock().WORLD.getTileEntity(end1);
				if(port.getIOType() != IOType.INPUT)
				{
					multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.collision_chamber.must_be_input_beam_port", end1);
					return false;
				}
			}
			else
			{
				multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.collision_chamber.must_be_input_beam_port", end1);
				return false;
			}
			
			
			
			if((getMultiblock().WORLD.getTileEntity(end2) instanceof TileParticleChamberBeamPort))
			{
				TileParticleChamberBeamPort port = (TileParticleChamberBeamPort) getMultiblock().WORLD.getTileEntity(end2);
				if(port.getIOType() != IOType.INPUT)
				{
					multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.collision_chamber.must_be_input_beam_port", end2);
					return false;
				}
			}
			else
			{
				multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.collision_chamber.must_be_input_beam_port", end2);
				return false;
			}
		}
		
		// outputs
		
		if (axis == Axis.X)
		{
			
			BlockPos port1 = getMultiblock().getExtremeCoord(false, false, true).add(2, getMultiblock().getInteriorLengthY() / 2+1,0);
			BlockPos port2 = getMultiblock().getExtremeCoord(true, false, true).add(-2, getMultiblock().getInteriorLengthY() / 2+1,0);
			BlockPos port3 = getMultiblock().getExtremeCoord(true, false, false).add(-2, getMultiblock().getInteriorLengthY() / 2+1,0);
			BlockPos port4 = getMultiblock().getExtremeCoord(false, false, false).add(2, getMultiblock().getInteriorLengthY() / 2+1,0);
			List<BlockPos> portPostions = new ArrayList() {};
			portPostions.add(port1);
			portPostions.add(port2);
			portPostions.add(port3);
			portPostions.add(port4);
			
			
			for(BlockPos pos : portPostions)
			{
				if((getMultiblock().WORLD.getTileEntity(pos) instanceof TileParticleChamberBeamPort))
				{
					TileParticleChamberBeamPort port = (TileParticleChamberBeamPort) getMultiblock().WORLD.getTileEntity(pos);
					if(port.getIOType() != IOType.OUTPUT)
					{
						multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.collision_chamber.must_be_output_beam_port", pos);
						return false;
					}
				}
				else
				{
					multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.collision_chamber.must_be_output_beam_port", pos);
					return false;
				}
				
				for (int i = 1; i <= getMultiblock().getExteriorLengthZ() - 2; i++)
				{
					if(i == (getMultiblock().getExteriorLengthZ() - 2)/2 +1)
					{
						continue;
					}
					
					if (!(getMultiblock().WORLD.getTileEntity(port1.offset(EnumFacing.NORTH, i)) instanceof TileParticleChamberBeam))
					{
						multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.chamber.must_be_beam", port1.offset(EnumFacing.NORTH, i));
						return false;
					}
				}
				
				for (int i = 1; i <= getMultiblock().getExteriorLengthZ() - 2; i++)
				{
					if(i == (getMultiblock().getExteriorLengthZ() - 2)/2 +1)
					{
						continue;
					}
					
					if (!(getMultiblock().WORLD.getTileEntity(port2.offset(EnumFacing.NORTH, i)) instanceof TileParticleChamberBeam))
					{
						multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.chamber.must_be_beam", port2.offset(EnumFacing.NORTH, i));
						return false;
					}
				}
				
				
				
			}
			

		}
		
		if (axis == Axis.Z)
		{
			BlockPos port1 = getMultiblock().getExtremeCoord(false, false, false).add(0, getMultiblock().getInteriorLengthY() / 2 +1,2);
			BlockPos port2 = getMultiblock().getExtremeCoord(false, false, true).add(0, getMultiblock().getInteriorLengthY() / 2+1,-2);
			BlockPos port3 = getMultiblock().getExtremeCoord(true, false, true).add(0, getMultiblock().getInteriorLengthY() / 2+1,-2);
			BlockPos port4 = getMultiblock().getExtremeCoord(true, false, false).add(0, getMultiblock().getInteriorLengthY() / 2+1,2);
			
			List<BlockPos> portPostions = new ArrayList() {};
			portPostions.add(port1);
			portPostions.add(port2);
			portPostions.add(port3);
			portPostions.add(port4);
			
			
			for(BlockPos pos : portPostions)
			{
				if((getMultiblock().WORLD.getTileEntity(pos) instanceof TileParticleChamberBeamPort))
				{
					TileParticleChamberBeamPort port = (TileParticleChamberBeamPort) getMultiblock().WORLD.getTileEntity(pos);
					if(port.getIOType() != IOType.OUTPUT)
					{
						multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.collision_chamber.must_be_output_beam_port", pos);
						return false;
					}
				}
				else
				{
					multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.collision_chamber.must_be_output_beam_port", pos);
					return false;
				}
			}
			
			for (int i = 1; i <= getMultiblock().getExteriorLengthX() - 2; i++)
			{
				if(i == (getMultiblock().getExteriorLengthX() - 2)/2 +1)
				{
					continue;
				}
				
				if (!(getMultiblock().WORLD.getTileEntity(port1.offset(EnumFacing.EAST, i)) instanceof TileParticleChamberBeam))
				{
					multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.chamber.must_be_beam", port1.offset(EnumFacing.EAST, i));
					return false;
				}
			}
			
			for (int i = 1; i <= getMultiblock().getExteriorLengthX() - 2; i++)
			{
				if(i == (getMultiblock().getExteriorLengthX() - 2)/2 +1)
				{
					continue;
				}
				
				if (!(getMultiblock().WORLD.getTileEntity(port2.offset(EnumFacing.EAST, i)) instanceof TileParticleChamberBeam))
				{
					multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.chamber.must_be_beam", port2.offset(EnumFacing.EAST, i));
					return false;
				}
			}
			
			
		}
		// Energy Ports
		if (getPartMap(TileParticleChamberEnergyPort.class).size() < 1)
		{
			multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.need_energy_ports", null);
			return false;
		}
		
		
		
		return true;
	}
	
	public Set<BlockPos> getinteriorAxisPositions(Axis axis)
	{
		Set<BlockPos> postions = new HashSet<BlockPos>();
		
		
		if (axis == Axis.X)
		{
			for (BlockPos pos : BlockPos.getAllInBoxMutable(
					getMultiblock().getExtremeInteriorCoord(false, false, false).add(0, getMultiblock().getInteriorLengthY() / 2, getMultiblock().getInteriorLengthZ() / 2),
					getMultiblock().getExtremeInteriorCoord(true, false, false).add(0, getMultiblock().getInteriorLengthY() / 2, getMultiblock().getInteriorLengthZ() / 2)))
			{
				postions.add(pos.toImmutable());
			}
		}

		if (axis == Axis.Z)
		{
			for (BlockPos pos : BlockPos.getAllInBoxMutable(
					getMultiblock().getExtremeInteriorCoord(false, false, false).add(getMultiblock().getInteriorLengthX() / 2, getMultiblock().getInteriorLengthY() / 2, 0),
					getMultiblock().getExtremeInteriorCoord(false, false, true).add(getMultiblock().getInteriorLengthX() / 2, getMultiblock().getInteriorLengthY() / 2, 0)))
			{
				postions.add(pos.toImmutable());
			}
		}

		return postions;
	}


	@Override
	public int getMaximumInteriorLength()
	{
		return chamberLength-2;
	}
	
	
	
	
	@Override
	public void onChamberFormed()
	{
		onResetStats();
		if (!getWorld().isRemote)
		{

			if (getMultiblock().getExteriorLengthX() > getMultiblock().getExteriorLengthZ())
			{
				//axis = Axis.X;
				
				
				
				
				BlockPos end1 = getMultiblock().getExtremeInteriorCoord(false, false, false).add(-1, getMultiblock().getInteriorLengthY() /2,getMultiblock().getInteriorLengthZ() / 2);
				BlockPos end2 =	getMultiblock().getExtremeInteriorCoord(true, false, false).add(1, getMultiblock().getInteriorLengthY() / 2, getMultiblock().getInteriorLengthZ() / 2);
				
				
				TileParticleChamberBeamPort port0 = (TileParticleChamberBeamPort) getMultiblock().WORLD.getTileEntity(end1);
				port0.setIONumber(0);
				
				TileParticleChamberBeamPort port1 = (TileParticleChamberBeamPort) getMultiblock().WORLD.getTileEntity(end2);
				port1.setIONumber(1);
				
				
				TileParticleChamberBeamPort portA = (TileParticleChamberBeamPort) getMultiblock().WORLD.getTileEntity(getMultiblock().getExtremeCoord(false, false, false).add(2, getMultiblock().getInteriorLengthY() / 2+1,0));
				TileParticleChamberBeamPort portB = (TileParticleChamberBeamPort) getMultiblock().WORLD.getTileEntity(getMultiblock().getExtremeCoord(true, false, false).add(-2, getMultiblock().getInteriorLengthY() / 2+1,0));
				TileParticleChamberBeamPort portC = (TileParticleChamberBeamPort) getMultiblock().WORLD.getTileEntity(getMultiblock().getExtremeCoord(true, false, true).add(-2, getMultiblock().getInteriorLengthY() / 2+1,0));
				TileParticleChamberBeamPort portD = (TileParticleChamberBeamPort) getMultiblock().WORLD.getTileEntity(getMultiblock().getExtremeCoord(false, false, true).add(2, getMultiblock().getInteriorLengthY() / 2+1,0));
				
				portA.setIONumber(portASetting);
				portB.setIONumber(portBSetting);
				portC.setIONumber(portCSetting);
				portD.setIONumber(portDSetting);
				
				
				for (TileParticleChamberDetector detector : getPartMap(TileParticleChamberDetector.class).values())
				{
					TileEntity chamber = getMultiblock().WORLD.getTileEntity(new BlockPos(detector.getPos().getX(),getMultiblock().getMiddleY(),getMultiblock().getMiddleZ()));
					getMultiblock().requiredEnergy += detector.basePower;
					
					if (detector.isInvalidPostion(chamber.getPos()))
					{
						getMultiblock().efficiency += detector.efficiency;
					}

				}
				
				
				
			}
			else
			{
				//axis = Axis.Z;
				
				
				BlockPos end1 = getMultiblock().getExtremeInteriorCoord(false, false, false).add(getMultiblock().getInteriorLengthX() / 2, getMultiblock().getInteriorLengthY() / 2, -1);
				BlockPos end2 =	getMultiblock().getExtremeInteriorCoord(false, false, true).add(getMultiblock().getInteriorLengthX() / 2, getMultiblock().getInteriorLengthY() / 2, 1);
				
				
				TileParticleChamberBeamPort port0 = (TileParticleChamberBeamPort) getMultiblock().WORLD.getTileEntity(end1);
				port0.setIONumber(0);
				
				TileParticleChamberBeamPort port1 = (TileParticleChamberBeamPort) getMultiblock().WORLD.getTileEntity(end2);
				port1.setIONumber(1);
				
				TileParticleChamberBeamPort portA = (TileParticleChamberBeamPort) getMultiblock().WORLD.getTileEntity(getMultiblock().getExtremeCoord(true, false, false).add(0, getMultiblock().getInteriorLengthY() / 2+1,2));
				TileParticleChamberBeamPort portB = (TileParticleChamberBeamPort) getMultiblock().WORLD.getTileEntity(getMultiblock().getExtremeCoord(true, false, true).add(0, getMultiblock().getInteriorLengthY() / 2+1,-2));
				TileParticleChamberBeamPort portC = (TileParticleChamberBeamPort) getMultiblock().WORLD.getTileEntity(getMultiblock().getExtremeCoord(false, false, true).add(0, getMultiblock().getInteriorLengthY() / 2+1,-2));
				TileParticleChamberBeamPort portD = (TileParticleChamberBeamPort) getMultiblock().WORLD.getTileEntity(getMultiblock().getExtremeCoord(false, false, false).add(0, getMultiblock().getInteriorLengthY() / 2 +1,2));
				
				portA.setIONumber(portASetting);
				portB.setIONumber(portBSetting);
				portC.setIONumber(portCSetting);
				portD.setIONumber(portDSetting);
				
				for (TileParticleChamberDetector detector : getPartMap(TileParticleChamberDetector.class).values())
				{
					TileEntity chamber = getMultiblock().WORLD.getTileEntity(new BlockPos(getMultiblock().getMiddleX(),getMultiblock().getMiddleY(),detector.getPos().getZ()));
					getMultiblock().requiredEnergy += detector.basePower;
					
					if (detector.isInvalidPostion(chamber.getPos()))
					{
						getMultiblock().efficiency += detector.efficiency;
					}

				}
			}

			
			
			
		}

		super.onChamberFormed();
	}
	
	
	public void onMachineDisassembled()
	{	
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
		getMultiblock().beams.get(1).setParticleStack(null);
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
		getMultiblock().requiredEnergy = QMDConfig.collision_chamber_power;	
	}
	
	@Override
	public boolean toggleSetting(BlockPos pos, int ioNumber)
	{
		if(ioNumber == 0 || ioNumber == 1)
		{
			return false;
		}
		
		TileParticleChamberBeamPort portA;
		TileParticleChamberBeamPort portB;
		TileParticleChamberBeamPort portC;
		TileParticleChamberBeamPort portD;
		if (getMultiblock().getExteriorLengthX() > getMultiblock().getExteriorLengthZ())
		{
			//axis = Axis.X;
			
			portA = (TileParticleChamberBeamPort) getMultiblock().WORLD.getTileEntity(getMultiblock().getExtremeCoord(false, false, false).add(2, getMultiblock().getInteriorLengthY() / 2+1,0));
			portB = (TileParticleChamberBeamPort) getMultiblock().WORLD.getTileEntity(getMultiblock().getExtremeCoord(true, false, false).add(-2, getMultiblock().getInteriorLengthY() / 2+1,0));
			portC = (TileParticleChamberBeamPort) getMultiblock().WORLD.getTileEntity(getMultiblock().getExtremeCoord(true, false, true).add(-2, getMultiblock().getInteriorLengthY() / 2+1,0));
			portD = (TileParticleChamberBeamPort) getMultiblock().WORLD.getTileEntity(getMultiblock().getExtremeCoord(false, false, true).add(2, getMultiblock().getInteriorLengthY() / 2+1,0));
			
		}
		else
		{
			//axis = Axis.Z;
			portA = (TileParticleChamberBeamPort) getMultiblock().WORLD.getTileEntity(getMultiblock().getExtremeCoord(true, false, false).add(0, getMultiblock().getInteriorLengthY() / 2+1,2));
			portB = (TileParticleChamberBeamPort) getMultiblock().WORLD.getTileEntity(getMultiblock().getExtremeCoord(true, false, true).add(0, getMultiblock().getInteriorLengthY() / 2+1,-2));
			portC = (TileParticleChamberBeamPort) getMultiblock().WORLD.getTileEntity(getMultiblock().getExtremeCoord(false, false, true).add(0, getMultiblock().getInteriorLengthY() / 2+1,-2));
			portD = (TileParticleChamberBeamPort) getMultiblock().WORLD.getTileEntity(getMultiblock().getExtremeCoord(false, false, false).add(0, getMultiblock().getInteriorLengthY() / 2 +1,2));
		}
		
		if(pos.equals(portA.getPos()))
		{
			int otherIO = portB.getIONumber();
			portB.setIONumber(ioNumber);
			portBSetting=(byte) ioNumber;
			portA.setIONumber(otherIO);
			portASetting=(byte) otherIO;
			return true;
		}
		else if (pos.equals(portB.getPos()))
		{
			int otherIO = portC.getIONumber();
			portC.setIONumber(ioNumber);
			portCSetting=(byte) ioNumber;
			portB.setIONumber(otherIO);
			portBSetting=(byte) otherIO;
			return true;
		}
		else if (pos.equals(portC.getPos()))
		{
			int otherIO = portD.getIONumber();
			portD.setIONumber(ioNumber);
			portDSetting=(byte) ioNumber;
			portC.setIONumber(otherIO);
			portCSetting=(byte) otherIO;
			return true;
		}
		else if (pos.equals(portD.getPos()))
		{
			int otherIO = portA.getIONumber();
			portA.setIONumber(ioNumber);
			portASetting=(byte) ioNumber;
			portD.setIONumber(otherIO);
			portDSetting=(byte) otherIO;
			return true;
		}

		return false;
	}
	
	private void produceBeams()
	{
		ParticleStack input1 = getMultiblock().beams.get(0).getParticleStack();
		ParticleStack input2 = getMultiblock().beams.get(1).getParticleStack();
		ParticleStack output1 = recipeInfo.getRecipe().getParticleProducts().get(0).getStack();
		ParticleStack output2 = recipeInfo.getRecipe().getParticleProducts().get(1).getStack();
		ParticleStack output3 = recipeInfo.getRecipe().getParticleProducts().get(2).getStack();
		ParticleStack output4 = recipeInfo.getRecipe().getParticleProducts().get(3).getStack();
		
		long energyReleased = recipeInfo.getRecipe().getEnergyReleased();
		double crossSection = recipeInfo.getRecipe().getCrossSection();
		
		long collisionEnergy = Math.round(2*Math.sqrt(input1.getMeanEnergy()*input2.getMeanEnergy()));
		double inputFocus = Math.min(input1.getFocus(),input2.getFocus());
		int inputAmount = Math.min(input1.getAmount(),input2.getAmount());
		
		double outputFactor = crossSection * getMultiblock().efficiency *(1-Math.abs(input1.getMeanEnergy()-input2.getMeanEnergy())/(double)(input1.getMeanEnergy()+input2.getMeanEnergy()));
		
		
		
		if(outputFactor >= 1)
		{
			outputFactor = 1;
		}
		

		
		int particlesOut = 0;
		if (output1 != null)
		{
			particlesOut += output1.getAmount();
		}
		if (output2 != null)
		{
			particlesOut += output2.getAmount();
		}
		if (output3 != null)
		{
			particlesOut += output3.getAmount();
		}
		if (output4 != null)
		{
			particlesOut += output4.getAmount();
		}
		
		
		getMultiblock().beams.get(2).setParticleStack(output1);
		if(output1 != null)
		{
			getMultiblock().beams.get(2).getParticleStack().setMeanEnergy(Math.round((collisionEnergy + energyReleased) /(double) particlesOut));
			getMultiblock().beams.get(2).getParticleStack().setAmount((int) Math.round(output1.getAmount() * outputFactor * inputAmount));
			getMultiblock().beams.get(2).getParticleStack().setFocus(inputFocus-Equations.focusLoss(getBeamLength(), getMultiblock().beams.get(2).getParticleStack()));
		}
		
		getMultiblock().beams.get(3).setParticleStack(output2);
		if(output2 != null)
		{
			getMultiblock().beams.get(3).getParticleStack().setMeanEnergy(Math.round((collisionEnergy + energyReleased) /(double) particlesOut));
			getMultiblock().beams.get(3).getParticleStack().setAmount((int) Math.round(output2.getAmount() * outputFactor * inputAmount));
			getMultiblock().beams.get(3).getParticleStack().setFocus(inputFocus-Equations.focusLoss(getBeamLength(), getMultiblock().beams.get(3).getParticleStack()));
		}
		
		getMultiblock().beams.get(4).setParticleStack(output3);
		if(output3 != null)
		{
			getMultiblock().beams.get(4).getParticleStack().setMeanEnergy(Math.round((collisionEnergy + energyReleased) /(double) particlesOut));
			getMultiblock().beams.get(4).getParticleStack().setAmount((int) Math.round(output3.getAmount() * outputFactor * inputAmount));
			getMultiblock().beams.get(4).getParticleStack().setFocus(inputFocus-Equations.focusLoss(getBeamLength(), getMultiblock().beams.get(4).getParticleStack()));
		}
		
		getMultiblock().beams.get(5).setParticleStack(output4);
		if(output4 != null)
		{
			getMultiblock().beams.get(5).getParticleStack().setMeanEnergy(Math.round((collisionEnergy + energyReleased) /(double) particlesOut));
			getMultiblock().beams.get(5).getParticleStack().setAmount((int) Math.round(output4.getAmount() * outputFactor * inputAmount));
			getMultiblock().beams.get(5).getParticleStack().setFocus(inputFocus-Equations.focusLoss(getBeamLength(), getMultiblock().beams.get(5).getParticleStack()));
		}
	}

	@Override
	public int getBeamLength()
	{
		return chamberLength-2 + (getMultiblock().getExteriorLengthY()-1)/2;
	}
	
	
	private void resetBeams()
	{
		getMultiblock().beams.get(2).setParticleStack(null);
		getMultiblock().beams.get(3).setParticleStack(null);
		getMultiblock().beams.get(4).setParticleStack(null);
		getMultiblock().beams.get(5).setParticleStack(null);
	}
	
	protected void refreshRecipe() 
	{
		if(getMultiblock().beams.get(0).getParticleStack() != null && getMultiblock().beams.get(1).getParticleStack() != null)
		{
			ArrayList<ParticleStack> particles = new ArrayList<ParticleStack>();
			long collisionEnergy = (long) (2*Math.sqrt(getMultiblock().beams.get(0).getParticleStack().getMeanEnergy()*getMultiblock().beams.get(1).getParticleStack().getMeanEnergy()));
			ParticleStack copy1 = getMultiblock().beams.get(0).getParticleStack().copy();
			ParticleStack copy2 = getMultiblock().beams.get(1).getParticleStack().copy();
			copy1.setMeanEnergy(collisionEnergy/2);
			copy2.setMeanEnergy(collisionEnergy/2);
			
			particles.add(copy1);
			particles.add(copy2);
			
			recipeInfo = collision_chamber.getRecipeInfoFromInputs(new ArrayList<ItemStack>(), new ArrayList<Tank>(), particles);
			
			
			if(recipeInfo != null)
			{
				if(collisionEnergy > recipeInfo.getRecipe().getMaxEnergy())
				{
					recipeInfo = null;
				}
			}
			
		}
		else
		{
			recipeInfo = null;
		}
	}
	

	
	@Override
	public ParticleChamberUpdatePacket getMultiblockUpdatePacket()
	{
		return new CollisionChamberUpdatePacket(getMultiblock().controller.getTilePos(), getMultiblock().isChamberOn,
				getMultiblock().requiredEnergy, getMultiblock().efficiency, getMultiblock().energyStorage,
				getMultiblock().tanks, getMultiblock().beams);
	}

	@Override
	public void onMultiblockUpdatePacket(ParticleChamberUpdatePacket message)
	{
		super.onMultiblockUpdatePacket(message);
		if (message instanceof CollisionChamberUpdatePacket)
		{
			CollisionChamberUpdatePacket packet = (CollisionChamberUpdatePacket) message;
			getMultiblock().beams = packet.beams;

		}
	}
	
	
	
	@Override
	public void writeToLogicTag(NBTTagCompound logicTag, SyncReason syncReason)
	{
		super.writeToLogicTag(logicTag, syncReason);
		logicTag.setByte("portASetting", portASetting);
		logicTag.setByte("portBSetting", portBSetting);
		logicTag.setByte("portCSetting", portCSetting);
		logicTag.setByte("portDSetting", portDSetting);
	}

	@Override
	public void readFromLogicTag(NBTTagCompound logicTag, SyncReason syncReason)
	{
		super.readFromLogicTag(logicTag, syncReason);
		portASetting = logicTag.getByte("portASetting");
		portBSetting = logicTag.getByte("portBSetting");
		portCSetting = logicTag.getByte("portCSetting");
		portDSetting = logicTag.getByte("portDSetting");
	}
	
	
	
	
	/*public ContainerMultiblockController<ParticleChamber, IParticleChamberController> getContainer(EntityPlayer player)
	{
		
		return new ContainerCollisionChamberController(player, (TileCollisionChamberController) getMultiblock().controller);
	}*/
	
	
}
