package lach_01298.qmd.pipe;

import lach_01298.qmd.multiblock.particleChamber.ParticleChamber;
import nc.multiblock.Multiblock;
import nc.multiblock.MultiblockLogic;
import nc.multiblock.TileBeefBase.SyncReason;
import net.minecraft.nbt.NBTTagCompound;

public class PipeLogic extends MultiblockLogic<Pipe, PipeLogic, IPipePart, PipeUpdatePacket>
{

	public PipeLogic(PipeLogic oldLogic)
	{
		super(oldLogic); 
		
	}
	
	public PipeLogic(Pipe multiblock)
	{
		super(multiblock);
	
	}
	
	
	protected Pipe getPipe() 
	{
		return multiblock;
	}

	@Override
	public String getID()
	{
		return "";
	}

	@Override
	public int getMinimumInteriorLength()
	{
		return 0;
	}

	@Override
	public int getMaximumInteriorLength()
	{
		return 0;
	}

	@Override
	public void onMachineAssembled()
	{
		onPipeFormed();
	}

	public void onPipeFormed()
	{
		
	}

	@Override
	public void onMachineRestored()
	{
		onPipeFormed();
	}

	@Override
	public void onMachinePaused()
	{
		
	}

	@Override
	public void onMachineDisassembled()
	{
		
	}

	@Override
	public boolean isMachineWhole(Multiblock multiblock)
	{
		return false;
	}

	@Override
	public void writeToLogicTag(NBTTagCompound logicTag, SyncReason syncReason)
	{
		
	}

	@Override
	public void readFromLogicTag(NBTTagCompound logicTag, SyncReason syncReason)
	{
		
	}

	@Override
	public PipeUpdatePacket getUpdatePacket()
	{
		return null;
	}

	@Override
	public void onPacket(PipeUpdatePacket message)
	{
		
	}

	public boolean onUpdateServer()
	{
		return false;
	}

	public void onAssimilate(Multiblock assimilated)
	{
		
	}

	public void onAssimilated(Multiblock assimilator)
	{
		
	}

	public void onUpdateClient()
	{
		
	}

}
