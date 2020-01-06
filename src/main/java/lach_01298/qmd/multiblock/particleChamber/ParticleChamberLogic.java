package lach_01298.qmd.multiblock.particleChamber;

import lach_01298.qmd.multiblock.network.ParticleChamberUpdatePacket;
import lach_01298.qmd.multiblock.particleChamber.tile.IParticleChamberPart;
import nc.multiblock.Multiblock;
import nc.multiblock.MultiblockLogic;
import nc.multiblock.TileBeefBase.SyncReason;
import net.minecraft.nbt.NBTTagCompound;

public class ParticleChamberLogic extends MultiblockLogic<ParticleChamber,IParticleChamberPart,ParticleChamberUpdatePacket>
{

	public ParticleChamberLogic(ParticleChamber multiblock)
	{
		super(multiblock);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void load()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unload()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getMinimumInteriorLength()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMaximumInteriorLength()
	{
		// TODO Auto-generated method stub
		return 0;
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
	public void writeToNBT(NBTTagCompound data, SyncReason syncReason)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void readFromNBT(NBTTagCompound data, SyncReason syncReason)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public ParticleChamberUpdatePacket getUpdatePacket()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onPacket(ParticleChamberUpdatePacket message)
	{
		// TODO Auto-generated method stub
		
	}

}
