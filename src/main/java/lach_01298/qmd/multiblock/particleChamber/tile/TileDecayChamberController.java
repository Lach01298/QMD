package lach_01298.qmd.multiblock.particleChamber.tile;


import static nc.block.property.BlockProperties.FACING_ALL;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.collect.Lists;

import lach_01298.qmd.QMD;
import lach_01298.qmd.multiblock.accelerator.Accelerator;
import lach_01298.qmd.multiblock.accelerator.AcceleratorLogic;
import lach_01298.qmd.multiblock.accelerator.LinearAcceleratorLogic;
import lach_01298.qmd.multiblock.accelerator.block.BlockLinearAcceleratorController;
import lach_01298.qmd.multiblock.accelerator.tile.IAcceleratorController;
import lach_01298.qmd.multiblock.accelerator.tile.TileAcceleratorPart;
import lach_01298.qmd.multiblock.particleChamber.ParticleChamber;
import lach_01298.qmd.multiblock.particleChamber.block.BlockDecayChamberController;
import lach_01298.qmd.recipe.QMDRecipeHandler;
import lach_01298.qmd.recipes.QMDRecipes;
import nc.multiblock.cuboidal.CuboidalPartPositionType;
import nc.tile.internal.inventory.InventoryConnection;
import nc.tile.internal.inventory.InventoryTileWrapper;
import nc.tile.internal.inventory.ItemOutputSetting;
import nc.tile.internal.inventory.ItemSorption;
import nc.tile.inventory.ITileInventory;
import nc.util.BlockPosHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;

public class TileDecayChamberController extends TileParticleChamberPart implements IParticleChamberController
{

	public TileDecayChamberController()
	{
		super(CuboidalPartPositionType.WALL);
	}

	@Override
	public String getLogicID()
	{
		return	"decay_chamber";
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

	@Override
	public void updateBlockState(boolean isActive)
	{
		if (getBlockType() instanceof BlockDecayChamberController)
		{
			((BlockDecayChamberController) getBlockType()).setState(isActive, this);
			world.notifyNeighborsOfStateChange(pos, getBlockType(), true);
		}
	}


	
	public NBTTagCompound writeAll(NBTTagCompound nbt) 
	{
		super.writeAll(nbt);

		return nbt;
	}
	
	public void readAll(NBTTagCompound nbt) 
	{
		super.readAll(nbt);
		
	}
	
	

}