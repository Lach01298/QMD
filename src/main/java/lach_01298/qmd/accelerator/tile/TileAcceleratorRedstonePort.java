package lach_01298.qmd.accelerator.tile;

import static nc.block.property.BlockProperties.ACTIVE;
import static nc.block.property.BlockProperties.FACING_ALL;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.collect.Lists;

import lach_01298.qmd.accelerator.Accelerator;
import lach_01298.qmd.accelerator.block.BlockAcceleratorRedstonePort;
import lach_01298.qmd.accelerator.block.BlockAcceleratorVent;
import nc.multiblock.cuboidal.CuboidalPartPositionType;
import nc.tile.fluid.ITileFluid;
import nc.tile.internal.fluid.FluidConnection;
import nc.tile.internal.fluid.FluidTileWrapper;
import nc.tile.internal.fluid.GasTileWrapper;
import nc.tile.internal.fluid.Tank;
import nc.tile.internal.fluid.TankOutputSetting;
import nc.tile.internal.fluid.TankSorption;
import nc.tile.passive.ITilePassive;
import nc.util.Lang;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class TileAcceleratorRedstonePort extends TileAcceleratorPart
{

	private int redstoneLevel =0;

	public TileAcceleratorRedstonePort()
	{
		super(CuboidalPartPositionType.WALL);


	}

	@Override
	public void onMachineAssembled(Accelerator controller)
	{
		

	}



	public void updateBlockState(boolean isActive)
	{
		if (getBlockType() instanceof BlockAcceleratorRedstonePort)
		{
			((BlockAcceleratorRedstonePort) getBlockType()).setState(isActive, this);
		}
	}

	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState)
	{
		return oldState.getBlock() != newState.getBlock();
	}

	


	// IMultitoolLogic

	@Override
	public boolean onUseMultitool(ItemStack multitoolStack, EntityPlayer player, World world, EnumFacing facing,
			float hitX, float hitY, float hitZ)
	{
		
		
		if (player.isSneaking())
		{

		}
		else
		{
			if (getMultiblock() != null && world.getBlockState(pos).getValue(ACTIVE) != null)
			{
				if (world.getBlockState(pos).getValue(ACTIVE).booleanValue())
				{
					setRedstoneLevel(0);
					updateBlockState(false);
					getMultiblock().checkIfMachineIsWhole();
					player.sendMessage(new TextComponentString(Lang.localise("qmd.block.redstone_port_toggle") + " "
							+ TextFormatting.DARK_AQUA + Lang.localise("qmd.block.redstone_port_toggle.input") + " "
							+ TextFormatting.WHITE + Lang.localise("qmd.block.redstone_port_toggle.mode")));
				}
				else
				{
					setRedstoneLevel(0);
					updateBlockState(true);
					getMultiblock().checkIfMachineIsWhole();
					player.sendMessage(new TextComponentString(Lang.localise("qmd.block.redstone_port_toggle") + " "
							+ TextFormatting.RED + Lang.localise("qmd.block.redstone_port_toggle.output") + " "
							+ TextFormatting.WHITE + Lang.localise("qmd.block.redstone_port_toggle.mode")));
				}
				markDirtyAndNotify();
				return true;
			}
		}
		return super.onUseMultitool(multitoolStack, player, world, facing, hitX, hitY, hitZ);
	}

	// NBT

	@Override
	public NBTTagCompound writeAll(NBTTagCompound nbt)
	{
		super.writeAll(nbt);
		nbt.setInteger("redstoneLevel", redstoneLevel);

		return nbt;
	}

	@Override
	public void readAll(NBTTagCompound nbt)
	{
		super.readAll(nbt);
		redstoneLevel = nbt.getInteger("redstoneLevel");
	}

	
	public int getredstoneLevel()
    {
        return redstoneLevel;
    }

    public void setRedstoneLevel(int redstoneLevel)
    {
        if(this.redstoneLevel !=redstoneLevel)
        {
	    	this.redstoneLevel = MathHelper.clamp(redstoneLevel,0,15);
	        this.world.updateComparatorOutputLevel(pos, this.blockType);
	        this.world.notifyNeighborsOfStateChange(pos, this.blockType, true);
        }
    }
	
	
	// Capability

	@Override
	public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing side)
	{	
		return super.hasCapability(capability, side);
	}

	@Override
	public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing side)
	{
		return super.getCapability(capability, side);
	}



}
	
		
		
		

