package lach_01298.qmd.pipe;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import nc.multiblock.IPacketMultiblockLogic;
import nc.multiblock.Multiblock;
import nc.multiblock.MultiblockLogic;
import nc.multiblock.tile.TileBeefAbstract.SyncReason;
import net.minecraft.nbt.NBTTagCompound;

public class PipeLogic extends MultiblockLogic<Pipe, PipeLogic, IPipePart>
		implements IPacketMultiblockLogic<Pipe, PipeLogic, IPipePart, PipeUpdatePacket>
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
	public boolean isMachineWhole()
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
	public PipeUpdatePacket getMultiblockUpdatePacket()
	{
		return null;
	}

	@Override
	public void onMultiblockUpdatePacket(PipeUpdatePacket message)
	{
		
	}

	public boolean onUpdateServer()
	{
		return false;
	}

	public void onAssimilate(Pipe assimilated)
	{
		
	}

	public void onAssimilated(Pipe assimilator)
	{
		
	}

	public void onUpdateClient()
	{
		
	}

	@Override
	public List<Pair<Class<? extends IPipePart>, String>> getPartBlacklist()
	{
		return new ArrayList<>();
	}

	@Override
	public void clearAllMaterial()
	{
		
	}

}
