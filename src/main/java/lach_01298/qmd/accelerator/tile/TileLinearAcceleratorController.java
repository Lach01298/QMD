package lach_01298.qmd.accelerator.tile;

import static nc.block.property.BlockProperties.FACING_ALL;

import java.util.Arrays;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import lach_01298.qmd.QMD;
import lach_01298.qmd.accelerator.Accelerator;
import lach_01298.qmd.item.IItemParticleAmount;
import lach_01298.qmd.multiblock.container.ContainerLinearAcceleratorController;
import lach_01298.qmd.network.QMDTileUpdatePacket;
import lach_01298.qmd.recipes.QMDRecipes;
import nc.multiblock.container.ContainerMultiblockController;
import nc.multiblock.cuboidal.CuboidalPartPositionType;
import nc.tile.internal.inventory.InventoryConnection;
import nc.tile.internal.inventory.ItemOutputSetting;
import nc.tile.internal.inventory.ItemSorption;
import nc.tile.inventory.ITileInventory;
import nc.util.Lang;
import nc.util.NBTHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;

public class TileLinearAcceleratorController extends TileAcceleratorPart implements IAcceleratorController<TileLinearAcceleratorController>, ITileInventory
{

	private final @Nonnull String inventoryName = QMD.MOD_ID + ".container.linear_accelerator_controller";
	private @Nonnull InventoryConnection[] inventoryConnections = ITileInventory.inventoryConnectionAll(Arrays.asList(ItemSorption.BOTH,ItemSorption.BOTH));
	private final @Nonnull NonNullList<ItemStack> inventoryStacks = NonNullList.<ItemStack>withSize(2, ItemStack.EMPTY);
	
	public TileLinearAcceleratorController()
	{
		super(CuboidalPartPositionType.WALL);
	}

	@Override
	public String getLogicID()
	{
		return	"linear_accelerator";
	}


	@Override
	public void onMachineAssembled(Accelerator controller)
	{
		super.onMachineAssembled(controller);
		if (!getWorld().isRemote && getPartPosition().getFacing() != null)
		{
			getWorld().setBlockState(getPos(),getWorld().getBlockState(getPos()).withProperty(FACING_ALL, getPartPosition().getFacing()), 2);
		}	
	}

	@Override
	public void onMachineBroken()
	{
		super.onMachineBroken();
	}

	//items
	
	@Override
	public NonNullList<ItemStack> getInventoryStacks()
	{
		return inventoryStacks;
	}

	@Override
	public String getName()
	{
		return Lang.localise("gui."+inventoryName);
	}

	@Override
	public InventoryConnection[] getInventoryConnections()
	{
		return inventoryConnections;
	}

	@Override
	public void setInventoryConnections(InventoryConnection[] connections)
	{
		inventoryConnections = connections;
	}


	@Override
	public ItemOutputSetting getItemOutputSetting(int slot)
	{
		return ItemOutputSetting.DEFAULT;
	}

	@Override
	public void setItemOutputSetting(int slot, ItemOutputSetting setting)
	{
	}
	
	@Override
	public int getInventoryStackLimit() 
	{
		return 1;
	}
	
	
	
	
	
	
	
	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState)
	{
		return oldState.getBlock() != newState.getBlock();
	}

	@Override
	public void onBlockNeighborChanged(IBlockState state, World world, BlockPos pos, BlockPos fromPos)
	{
		super.onBlockNeighborChanged(state, world, pos, fromPos);
		if (getMultiblock() != null) getMultiblock().updateActivity();
	}
	
	@Override
	public ContainerMultiblockController getContainer(EntityPlayer player) {
		return new ContainerLinearAcceleratorController(player, this);
	}
	
	
	
	
	@Override
	public NBTTagCompound writeAll(NBTTagCompound nbt) {
		super.writeAll(nbt);
		writeInventory(nbt);
		writeInventoryConnections(nbt);

		return nbt;
	}
	
	@Override
	public void readAll(NBTTagCompound nbt) 
	{
		super.readAll(nbt);
		readInventory(nbt);
		readInventoryConnections(nbt);
		
		
	}
	
	@Override
	public NBTTagCompound writeInventory(NBTTagCompound nbt)
	{
		NBTHelper.writeAllItems(nbt, inventoryStacks);
		return nbt;
	}

	@Override
	public void readInventory(NBTTagCompound nbt)
	{
		NBTHelper.readAllItems(nbt, inventoryStacks);
	}
	
	
	
	@Override
	public  boolean isItemValidForSlot(int slot, ItemStack stack) 
	{
		return QMDRecipes.accelerator_source.isValidItemInput(IItemParticleAmount.cleanNBT(stack));
	}
	
}
