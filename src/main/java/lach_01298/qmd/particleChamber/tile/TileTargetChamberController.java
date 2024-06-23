package lach_01298.qmd.particleChamber.tile;

import static nc.block.property.BlockProperties.FACING_ALL;

import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.collect.Lists;

import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import lach_01298.qmd.QMD;
import lach_01298.qmd.particleChamber.ParticleChamber;
import lach_01298.qmd.recipe.QMDRecipeHandler;
import lach_01298.qmd.recipes.QMDRecipes;
import nc.handler.TileInfoHandler;
import nc.multiblock.cuboidal.CuboidalPartPositionType;
import nc.render.BlockHighlightTracker;
import nc.tile.TileContainerInfo;
import nc.tile.internal.inventory.InventoryConnection;
import nc.tile.internal.inventory.ItemOutputSetting;
import nc.tile.internal.inventory.ItemSorption;
import nc.tile.inventory.ITileInventory;
import nc.util.Lang;
import nc.util.NBTHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;

public class TileTargetChamberController extends TileParticleChamberPart implements IParticleChamberController<TileTargetChamberController>, ITileInventory
{
	protected final TileContainerInfo<TileTargetChamberController> info = TileInfoHandler.getTileContainerInfo("target_chamber_controller");

	private final @Nonnull String inventoryName = QMD.MOD_ID + ".container.target_chamber_controller";
	private final @Nonnull NonNullList<ItemStack> inventoryStacks = NonNullList.withSize(2, ItemStack.EMPTY);
	private @Nonnull InventoryConnection[] inventoryConnections = ITileInventory.inventoryConnectionAll(Lists.newArrayList(ItemSorption.IN, ItemSorption.OUT));

	
	
	public QMDRecipeHandler recipe_handler = QMDRecipes.target_chamber;
	
	protected Set<EntityPlayer> playersToUpdate;
	
	public TileTargetChamberController()
	{
		super(CuboidalPartPositionType.WALL);
		
		playersToUpdate = new ObjectOpenHashSet<>();
	}

	@Override
	public String getLogicID()
	{
		return	"target_chamber";
	}
	
	@Override
	public TileContainerInfo<TileTargetChamberController> getContainerInfo()
	{
		return info;
	}

	@Override
	public void onMachineAssembled(ParticleChamber controller)
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
		if (stack == ItemStack.EMPTY || slot >= getRecipeHandler().itemInputSize)
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
	public boolean onUseMultitool(ItemStack multitoolStack, EntityPlayerMP player, World world, EnumFacing facing,float hitX, float hitY, float hitZ)
	{
		if (player.isSneaking() && this.isMultiblockAssembled())
		{
			int invalidAmount = 0;
			for (TileParticleChamberDetector detector : this.getMultiblock().getPartMap(TileParticleChamberDetector.class).values())
			{
				BlockPos chamberPos = new BlockPos(getMultiblock().getMiddleX(), getMultiblock().getMiddleY(),getMultiblock().getMiddleZ());
				if (!detector.isValidPostion(chamberPos))
				{
					BlockHighlightTracker.sendPacket(player, detector.getPos(), 10000);
					invalidAmount++;
				}
			}
			player.sendMessage(new TextComponentString(Lang.localize("qmd.multiblock_validation.chamber.invalid_detectors", invalidAmount)));
			return true;
		}

		return false;
	}

}
