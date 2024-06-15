package lach_01298.qmd.vacuumChamber.tile;

import com.google.common.collect.Lists;
import lach_01298.qmd.QMD;
import lach_01298.qmd.recipes.QMDRecipes;
import lach_01298.qmd.vacuumChamber.VacuumChamber;
import nc.handler.TileInfoHandler;
import nc.multiblock.cuboidal.CuboidalPartPositionType;
import nc.recipe.BasicRecipeHandler;
import nc.tile.TileContainerInfo;
import nc.tile.internal.inventory.*;
import nc.tile.inventory.ITileInventory;
import nc.util.*;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.relauncher.*;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.*;

import static nc.block.property.BlockProperties.FACING_ALL;

public class TileExoticContainmentController extends TileVacuumChamberPart implements IVacuumChamberController<TileExoticContainmentController>, ITileInventory
{
	protected final TileContainerInfo<TileExoticContainmentController> info = TileInfoHandler.getTileContainerInfo("neutral_containment_controller");

	private final @Nonnull String inventoryName = QMD.MOD_ID + ".container.neutral_containment_controller";
	private final @Nonnull NonNullList<ItemStack> inventoryStacks = NonNullList.withSize(2, ItemStack.EMPTY);
	private @Nonnull InventoryConnection[] inventoryConnections = ITileInventory.inventoryConnectionAll(Lists.newArrayList(ItemSorption.IN, ItemSorption.OUT));
	
	public BasicRecipeHandler recipe_handler = QMDRecipes.cell_filling;
	
	public boolean isRenderer = false;
	
	public TileExoticContainmentController()
	{
		super(CuboidalPartPositionType.WALL);
	}

	@Override
	public String getLogicID()
	{
		return	"neutral_containment";
	}
	
	@Override
	public TileContainerInfo<TileExoticContainmentController> getContainerInfo()
	{
		return info;
	}

	@Override
	public void onMachineAssembled(VacuumChamber controller)
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



	public BasicRecipeHandler getRecipeHandler() {
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
	public int getInventoryStackLimit()
	{
		return 1;
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
	
	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getRenderBoundingBox()
	{
		if (!isRenderer || !isMultiblockAssembled())
		{
			return Block.FULL_BLOCK_AABB.offset(pos);
		}
		return new AxisAlignedBB(getMultiblock().getMinimumCoord(), getMultiblock().getMaximumCoord());
	}

	@Override
	public double getDistanceSq(double x, double y, double z)
	{
		double dX, dY, dZ;
		if (!isRenderer || !isMultiblockAssembled())
		{
			dX = pos.getX() + 0.5D - x;
			dY = pos.getY() + 0.5D - y;
			dZ = pos.getZ() + 0.5D - z;
		}
		else
		{
			dX = getMultiblock().getMiddleX() + 0.5D - x;
			dY = getMultiblock().getMiddleY() + 0.5D - y;
			dZ = getMultiblock().getMiddleZ() + 0.5D - z;
		}
		return dX * dX + dY * dY + dZ * dZ;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared()
	{
		double defaultDistSq = super.getMaxRenderDistanceSquared();
		if (!isRenderer || !isMultiblockAssembled())
		{
			return defaultDistSq;
		}
		return defaultDistSq
				+ (NCMath.sq(getMultiblock().getExteriorLengthX()) + NCMath.sq(getMultiblock().getExteriorLengthY())
						+ NCMath.sq(getMultiblock().getExteriorLengthZ())) / 4D;
	}

	public boolean isRenderer()
	{
		return isRenderer;
	}
	
	public void setIsRenderer(boolean isRenderer)
	{
		this.isRenderer = isRenderer;
	}
}
