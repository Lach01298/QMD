package lach_01298.qmd.multiblock.liquifier;

import nc.multiblock.Multiblock;
import nc.multiblock.MultiblockLogic;
import nc.multiblock.TileBeefBase.SyncReason;
import nc.multiblock.heatExchanger.HeatExchanger;
import nc.multiblock.heatExchanger.tile.IHeatExchangerPart;
import nc.multiblock.network.HeatExchangerUpdatePacket;
import net.minecraft.nbt.NBTTagCompound;

public class LiquifierLogic extends MultiblockLogic<HeatExchanger, IHeatExchangerPart, HeatExchangerUpdatePacket>
{

	public LiquifierLogic(HeatExchanger multiblock)
	{
		super(multiblock);
		
	}

	@Override
	public String getID()
	{
		return "liquifier";
	}

	@Override
	public int getMinimumInteriorLength()
	{
		
		return 3;
	}

	@Override
	public int getMaximumInteriorLength()
	{
		return 24;
	}

	@Override
	public void onMachineAssembled()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMachineRestored()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMachinePaused()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMachineDisassembled()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isMachineWhole(Multiblock multiblock)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void writeToLogicTag(NBTTagCompound logicTag, SyncReason syncReason)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void readFromLogicTag(NBTTagCompound logicTag, SyncReason syncReason)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public HeatExchangerUpdatePacket getUpdatePacket()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onPacket(HeatExchangerUpdatePacket message)
	{
		// TODO Auto-generated method stub
		
	}

}
