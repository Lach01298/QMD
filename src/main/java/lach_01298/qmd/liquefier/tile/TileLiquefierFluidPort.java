package lach_01298.qmd.liquefier.tile;

import lach_01298.qmd.liquefier.LiquefierLogic;
import nc.ModCheck;
import nc.multiblock.cuboidal.CuboidalPartPositionType;
import nc.multiblock.hx.HeatExchanger;
import nc.tile.fluid.ITileFluid;
import nc.tile.hx.TileHeatExchangerPart;
import nc.tile.internal.fluid.*;
import nc.tile.passive.ITilePassive;
import nc.util.CapabilityHelper;
import nc.util.Lang;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

import static nc.block.property.BlockProperties.FACING_ALL;
import static nc.config.NCConfig.enable_mek_gas;

public class TileLiquefierFluidPort extends TileHeatExchangerPart implements ITickable, ITileFluid
{
	private final @Nonnull List<Tank> backupTanks = Collections.emptyList();

	private @Nonnull FluidConnection[] fluidConnections = ITileFluid.fluidConnectionAll(TankSorption.IN);

	private final @Nonnull FluidTileWrapper[] fluidSides;
	private final @Nonnull GasTileWrapper gasWrapper;


	public TileLiquefierFluidPort()
	{
		super(CuboidalPartPositionType.WALL);
		fluidSides = ITileFluid.getDefaultFluidSides(this);
		gasWrapper = new GasTileWrapper(this);
	}


	@Override
	public void onMachineAssembled(HeatExchanger multiblock)
	{
		doStandardNullControllerResponse(multiblock);
		super.onMachineAssembled(multiblock);
		if (!world.isRemote)
		{
			EnumFacing facing = getPartPosition().getFacing();
			if (facing != null)
			{
				world.setBlockState(pos, world.getBlockState(pos).withProperty(FACING_ALL, facing), 2);
			}
		}
	}

	@Override
	public void update()
	{
		if (!world.isRemote)
		{
			List<Tank> tanks = getTanks();
			if (!tanks.isEmpty() && !tanks.get(0).isEmpty())
			{
				EnumFacing facing = getPartPosition().getFacing();
				if (facing != null && getTankSorption(facing, 0).canDrain())
				{
					pushFluidToSide(facing);
				}
			}
		}
	}

	// Fluids

	@Override
	public @Nonnull List<Tank> getTanks()
	{
		if (getLogic() instanceof LiquefierLogic)
		{
			LiquefierLogic logic = (LiquefierLogic) getLogic();
			return logic.getTanks(backupTanks);
		}
		return backupTanks;
	}

	@Override
	@Nonnull
	public FluidConnection[] getFluidConnections()
	{
		return fluidConnections;
	}

	@Override
	public void setFluidConnections(@Nonnull FluidConnection[] connections)
	{
		fluidConnections = connections;
	}

	@Override
	@Nonnull
	public FluidTileWrapper[] getFluidSides()
	{
		return fluidSides;
	}

	@Override
	public @Nonnull GasTileWrapper getGasWrapper()
	{
		return gasWrapper;
	}

	@Override
	public void pushFluidToSide(@Nonnull EnumFacing side)
	{
		TileEntity tile = getTileWorld().getTileEntity(getTilePos().offset(side));
		if (tile == null)
		{
			return;
		}

		if (tile instanceof ITilePassive tilePassive && !tilePassive.canPushFluidsTo())
		{
			return;
		}

		IFluidHandler adjStorage = tile.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, side.getOpposite());
		if (adjStorage == null)
		{
			return;
		}

		List<Tank> tanks = getTanks();
		if (!tanks.isEmpty())
		{
			Tank tank = tanks.get(0);
			onWrapperDrain(tank.drain(adjStorage.fill(tank.drain(tank.getCapacity(), false), true), true), true);
		}
	}

	@Override
	public boolean getInputTanksSeparated()
	{
		return false;
	}

	@Override
	public void setInputTanksSeparated(boolean separated)
	{
	}

	@Override
	public boolean getVoidUnusableFluidInput(int tankNumber)
	{
		return false;
	}

	@Override
	public void setVoidUnusableFluidInput(int tankNumber, boolean voidUnusableFluidInput)
	{
	}

	@Override
	public TankOutputSetting getTankOutputSetting(int tankNumber)
	{
		return TankOutputSetting.DEFAULT;
	}

	@Override
	public void setTankOutputSetting(int tankNumber, TankOutputSetting setting)
	{
	}

	@Override
	public boolean hasConfigurableFluidConnections()
	{
		return true;
	}

	// IMultitoolLogic

	@Override
	public boolean onUseMultitool(ItemStack multitool, EntityPlayerMP player, World worldIn, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if (player.isSneaking())
		{

		}
		else
		{
			if (getMultiblock() != null)
			{
				if (getTankSorption(facing, 0) != TankSorption.IN)
				{
					for (EnumFacing side : EnumFacing.VALUES)
					{
						setTankSorption(side, 0, TankSorption.IN);
					}
					setActivity(false);
					player.sendMessage(new TextComponentString(Lang.localize("nc.block.port_toggle") + " " + TextFormatting.DARK_AQUA + Lang.localize("nc.block.port_mode.input") + " " + TextFormatting.WHITE + Lang.localize("nc.block.port_toggle.mode")));
				}
				else
				{
					for (EnumFacing side : EnumFacing.VALUES)
					{
						setTankSorption(side, 0, TankSorption.OUT);
					}
					setActivity(true);
					player.sendMessage(new TextComponentString(Lang.localize("nc.block.port_toggle") + " " + TextFormatting.RED + Lang.localize("nc.block.port_mode.output") + " " + TextFormatting.WHITE + Lang.localize("nc.block.port_toggle.mode")));
				}
				markDirtyAndNotify(true);
				return true;
			}
		}
		return super.onUseMultitool(multitool, player, worldIn, facing, hitX, hitY, hitZ);
	}

	// NBT

	@Override
	public NBTTagCompound writeAll(NBTTagCompound nbt)
	{
		super.writeAll(nbt);
		writeFluidConnections(nbt);
		return nbt;
	}

	@Override
	public void readAll(NBTTagCompound nbt)
	{
		super.readAll(nbt);
		readFluidConnections(nbt);
	}

	// Capability

	@Override
	public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing side)
	{
		if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY || (ModCheck.mekanismLoaded() && enable_mek_gas && capability == CapabilityHelper.GAS_HANDLER_CAPABILITY))
		{
			return hasFluidSideCapability(side);
		}
		return super.hasCapability(capability, side);
	}

	@Override
	public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing side)
	{
		if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
		{
			if (hasFluidSideCapability(side))
			{
				return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(getFluidSide(nonNullSide(side)));
			}
			return null;
		}
		else if (ModCheck.mekanismLoaded() && capability == CapabilityHelper.GAS_HANDLER_CAPABILITY)
		{
			if (enable_mek_gas && hasFluidSideCapability(side))
			{
				return CapabilityHelper.GAS_HANDLER_CAPABILITY.cast(getGasWrapper());
			}
			return null;
		}
		return super.getCapability(capability, side);
	}

}
