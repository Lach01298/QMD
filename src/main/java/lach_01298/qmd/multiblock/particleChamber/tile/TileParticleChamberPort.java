package lach_01298.qmd.multiblock.particleChamber.tile;

import lach_01298.qmd.io.IIOType;
import lach_01298.qmd.particle.ITileParticleStorage;
import nc.multiblock.cuboidal.CuboidalPartPositionType;
import nc.tile.internal.inventory.InventoryConnection;
import nc.tile.internal.inventory.InventoryTileWrapper;
import nc.tile.internal.inventory.ItemOutputSetting;
import nc.tile.inventory.ITileInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class TileParticleChamberPort extends TileParticleChamberPart implements ITileInventory
{

	public TileParticleChamberPort()
	{
		super(CuboidalPartPositionType.WALL);
		
	}

	@Override
	public void update()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public NonNullList<ItemStack> getInventoryStacks()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InventoryConnection[] getInventoryConnections()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setInventoryConnections(InventoryConnection[] connections)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public InventoryTileWrapper getInventory()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemOutputSetting getItemOutputSetting(int slot)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setItemOutputSetting(int slot, ItemOutputSetting setting)
	{
		// TODO Auto-generated method stub
		
	}

}
