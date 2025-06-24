package lach_01298.qmd.accelerator.tile;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import lach_01298.qmd.QMD;
import lach_01298.qmd.accelerator.Accelerator;
import lach_01298.qmd.recipe.QMDRecipeHandler;
import lach_01298.qmd.recipes.QMDRecipes;
import nc.handler.TileInfoHandler;
import nc.multiblock.cuboidal.CuboidalPartPositionType;
import nc.recipe.BasicRecipeHandler;
import nc.tile.TileContainerInfo;
import nc.tile.internal.inventory.*;
import nc.tile.inventory.ITileInventory;
import nc.util.NBTHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.*;
import java.util.Set;

import static nc.block.property.BlockProperties.FACING_ALL;

public class TileMassSpectrometerController extends TileAcceleratorPart implements IAcceleratorController<TileMassSpectrometerController>, ITileInventory
{
	protected final TileContainerInfo<TileMassSpectrometerController> info = TileInfoHandler.getTileContainerInfo("mass_spectrometer_controller");

	private final @Nonnull String inventoryName = QMD.MOD_ID + ".container.mass_spectrometer_controller";
	private final @Nonnull NonNullList<ItemStack> inventoryStacks = NonNullList.withSize(6, ItemStack.EMPTY);
	private @Nonnull InventoryConnection[] inventoryConnections = ITileInventory.inventoryConnectionAll(Lists.newArrayList(ItemSorption.IN,ItemSorption.NON, ItemSorption.OUT,ItemSorption.OUT,ItemSorption.OUT,ItemSorption.OUT));

	
	
	public QMDRecipeHandler recipe_handler = QMDRecipes.mass_spectrometer;
	
	protected Set<EntityPlayer> playersToUpdate;
	
	public TileMassSpectrometerController()
	{
		super(CuboidalPartPositionType.WALL);
		
		playersToUpdate = new ObjectOpenHashSet<>();
	}

	@Override
	public String getLogicID()
	{
		return	"mass_spectrometer";
	}
	
	@Override
	public TileContainerInfo<TileMassSpectrometerController> getContainerInfo()
	{
		return info;
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


	public QMDRecipeHandler getRecipeHandler() {
		return recipe_handler;
	}
	
	public NBTTagCompound writeAll(NBTTagCompound nbt)
	{
		super.writeAll(nbt);
		writeInventory(nbt);
		writeInventoryConnections(nbt);

		
		return nbt;
	}
	
	public void readAll(NBTTagCompound nbt)
	{
		super.readAll(nbt);
		readInventory(nbt);
		readInventoryConnections(nbt);
	}
	
	@Override
	public NonNullList<ItemStack> getInventoryStacks()
	{
		return inventoryStacks;
	}

	@Override
	public String getName()
	{
		return inventoryName;
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
	public boolean isItemValidForSlot(int slot, ItemStack stack)
	{
		if (getRecipeHandler() == null)
		{
			return true;
		}
		if (stack == ItemStack.EMPTY || slot >= getRecipeHandler().getItemInputSize())
			return false;
		return getRecipeHandler().isValidItemInput(stack);
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, EnumFacing side)
	{
		return  (getRecipeHandler() == null || isItemValidForSlot(slot, stack));
	}
	
	
	
	@Override
	public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing side)
	{
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
		{
			return !getInventoryStacks().isEmpty() && hasInventorySideCapability(side);
		}
		return super.hasCapability(capability, side);
	}

	@Override
	public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing side)
	{
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
		{
			if (!getInventoryStacks().isEmpty() && hasInventorySideCapability(side))
			{
				return (T) getItemHandler(side);
			}
			return null;

		}
		return super.getCapability(capability, side);
	}
}
