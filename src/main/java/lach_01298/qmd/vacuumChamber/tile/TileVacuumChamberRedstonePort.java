package lach_01298.qmd.vacuumChamber.tile;

import static nc.block.property.BlockProperties.ACTIVE;

import javax.annotation.Nullable;

import lach_01298.qmd.accelerator.block.BlockAcceleratorRedstonePort;
import lach_01298.qmd.vacuumChamber.VacuumChamber;
import lach_01298.qmd.vacuumChamber.block.BlockVacuumChamberRedstonePort;
import nc.multiblock.cuboidal.CuboidalPartPositionType;
import nc.util.Lang;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;

public class TileVacuumChamberRedstonePort extends TileVacuumChamberPart
{

	private int redstoneLevel =0;

	public TileVacuumChamberRedstonePort()
	{
		super(CuboidalPartPositionType.WALL);


	}

	@Override
	public void onMachineAssembled(VacuumChamber controller)
	{
		

	}



	public void updateBlockState(boolean isActive)
	{
		if (getBlockType() instanceof BlockVacuumChamberRedstonePort)
		{
			((BlockVacuumChamberRedstonePort) getBlockType()).setState(isActive, this);
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
							+ TextFormatting.DARK_BLUE + Lang.localise("qmd.block.redstone_port_toggle.1") + " "
							+ TextFormatting.WHITE + Lang.localise("qmd.block.redstone_port_toggle.mode")));
				}
				else
				{
					setRedstoneLevel(0);
					updateBlockState(true);
					getMultiblock().checkIfMachineIsWhole();
					player.sendMessage(new TextComponentString(Lang.localise("qmd.block.redstone_port_toggle") + " "
							+ TextFormatting.DARK_GREEN + Lang.localise("qmd.block.redstone_port_toggle.2") + " "
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
	
		
		
		

