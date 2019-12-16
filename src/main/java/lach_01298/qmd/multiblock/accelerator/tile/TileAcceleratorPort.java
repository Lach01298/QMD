package lach_01298.qmd.multiblock.accelerator.tile;

import static nc.block.property.BlockProperties.AXIS_ALL;
import static lach_01298.qmd.block.BlockProperties.IO_SIMPLE;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.collect.Lists;

import lach_01298.qmd.EnumTypes;
import lach_01298.qmd.EnumTypes.IOType;
import lach_01298.qmd.io.IIOType;
import lach_01298.qmd.multiblock.accelerator.Accelerator;
import nc.ModCheck;
import nc.block.property.BlockProperties;
import nc.config.NCConfig;
import nc.multiblock.cuboidal.CuboidalPartPositionType;
import nc.multiblock.turbine.Turbine;
import nc.multiblock.turbine.tile.TileTurbineOutlet;
import nc.multiblock.turbine.tile.TileTurbinePartBase;
import nc.tile.fluid.ITileFluid;
import nc.tile.internal.fluid.FluidConnection;
import nc.tile.internal.fluid.FluidTileWrapper;
import nc.tile.internal.fluid.GasTileWrapper;
import nc.tile.internal.fluid.Tank;
import nc.tile.internal.fluid.TankOutputSetting;
import nc.tile.internal.fluid.TankSorption;
import nc.tile.passive.ITilePassive;
import nc.util.GasHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class TileAcceleratorPort extends TileAcceleratorPartBase implements ITileFluid, IIOType
{

	private final @Nonnull List<Tank> backupTanks = Lists.newArrayList(new Tank(1, new ArrayList<String>()),new Tank(1, new ArrayList<String>()));

	private @Nonnull FluidConnection[] fluidConnections = ITileFluid.fluidConnectionAll(TankSorption.OUT);

	private @Nonnull FluidTileWrapper[] fluidSides;

	private @Nonnull GasTileWrapper gasWrapper;
	
	private EnumTypes.IOType type;

	protected int outletCount;

	public TileAcceleratorPort()
	{
		super(CuboidalPartPositionType.WALL);
		this.type = EnumTypes.IOType.INPUT;
		
		
		fluidSides = ITileFluid.getDefaultFluidSides(this);
		gasWrapper = new GasTileWrapper(this);
		
	}

	@Override
	public void onMachineAssembled(Accelerator controller)
	{
		doStandardNullControllerResponse(controller);
		super.onMachineAssembled(controller);

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
	public void update()
	{
		if (!world.isRemote)
		{
			if (outletCount == 0 && type == EnumTypes.IOType.OUTPUT)
				pushFluid();
			tickOutlet();
		}
	}

	public void tickOutlet()
	{
		outletCount++;
		outletCount %= NCConfig.machine_update_rate / 4;
	}

	// Fluids

	@Override
	@Nonnull
	public List<Tank> getTanks()
	{
		//if (!isMultiblockAssembled())
			return backupTanks;
		//return getMultiblock().tanks;
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
	public void pushFluidToSide(@Nonnull EnumFacing side)
	{
		if (!getTankSorption(side, 0).canDrain() || getTanks().get(0).getFluid() == null)
			return;

		TileEntity tile = getTileWorld().getTileEntity(getTilePos().offset(side));
		if (tile == null || tile instanceof TileAcceleratorPort)
			return;

		if (tile instanceof ITilePassive)
			if (!((ITilePassive) tile).canPushFluidsTo())
				return;

		IFluidHandler adjStorage = tile.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, side.getOpposite());
		if (adjStorage == null || getTanks().get(0).getFluid() == null)
			return;

		getTanks().get(1).drain(adjStorage.fill(getTanks().get(0).drain(getTanks().get(0).getCapacity(), false), true),true);
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

	// NBT

	@Override
	public NBTTagCompound writeAll(NBTTagCompound nbt)
	{
		super.writeAll(nbt);
		//nbt.setInteger("setting", type.getSimpleID());
		writeFluidConnections(nbt);
		
		return nbt;
	}

	@Override
	public void readAll(NBTTagCompound nbt)
	{
		super.readAll(nbt);
		//type =EnumTypes.IOType.getSimpleTypeFromID(nbt.getInteger("setting"));
		readFluidConnections(nbt);
	}

	// Capability

	@Override
	public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing side)
	{
		if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
		{
			return !getTanks().isEmpty() && hasFluidSideCapability(side);
		}
		return super.hasCapability(capability, side);
	}

	@Override
	public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing side)
	{
		if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
		{
			if (!getTanks().isEmpty() && hasFluidSideCapability(side))
			{
				return (T) getFluidSide(nonNullSide(side));
			}
			return null;
		}

		return super.getCapability(capability, side);
	}

	@Override
	public GasTileWrapper getGasWrapper()
	{
		return gasWrapper;
	}

	@Override
	public IOType getIOType()
	{
		
		return type;
	}

	@Override
	public void setIOType(IOType type)
	{
		this.type = type;
	}
	
	public void toggleSetting()
	{
		setIOType(type.getNextSimpleIO());
		getWorld().setBlockState(getPos(),getWorld().getBlockState(getPos()).withProperty(IO_SIMPLE, type));
		markDirtyAndNotify();
	}
	
	
	
	
}
