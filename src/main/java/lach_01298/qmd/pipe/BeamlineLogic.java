package lach_01298.qmd.pipe;

import static nc.block.property.BlockProperties.AXIS_ALL;

import lach_01298.qmd.particle.ParticleStorageBeamline;
import nc.tile.multiblock.TilePartAbstract.SyncReason;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing.Axis;

public class BeamlineLogic extends PipeLogic
{

	private int maxBeamStorageTime = 2;
	private int beamStorageTime = 0;
	
	public final ParticleStorageBeamline storage = new ParticleStorageBeamline(1);
	private Axis axis;
	
	public BeamlineLogic(PipeLogic oldLogic)
	{
		super(oldLogic);
		
		if(getWorld().getBlockState(multiblock.controller.getTilePos()).getValue(AXIS_ALL) != null)
		{
			this.axis = getWorld().getBlockState(multiblock.controller.getTilePos()).getValue(AXIS_ALL);
		}
		else
		{
			this.axis =Axis.X;
		}
		
	}
	
	@Override
	public boolean isMachineWhole()
	{
		return true;
	}
	
	
	@Override
	public void onPipeFormed()
	{
		storage.setLength(getPipe().length());
	}
	
	
	@Override
	public String getID()
	{
		return "beamline";
	}
	
	public boolean onUpdateServer()
	{
		
		//storage.setParticleStack(null);
		return false;
	}
	
	
	
	@Override
	public void writeToLogicTag(NBTTagCompound logicTag, SyncReason syncReason)
	{
		storage.writeToNBT(logicTag);
	}

	@Override
	public void readFromLogicTag(NBTTagCompound logicTag, SyncReason syncReason)
	{
		storage.readFromNBT(logicTag);
	}
	

	public Axis getAxis()
	{
		return axis;
	}
	
	public boolean setAxis(Axis newAxis)
	{
		if(newAxis != null)
		{
			axis = newAxis;
			return true;
		}
		return false;
	}
	
	
}
