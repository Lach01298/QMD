package lach_01298.qmd.vacuumChamber.tile;

import static nc.config.NCConfig.enable_gtce_eu;

import javax.annotation.Nullable;

//import gregtech.api.capability.GregtechCapabilities;
import ic2.api.energy.tile.IEnergyEmitter;
import lach_01298.qmd.vacuumChamber.VacuumChamber;
import nc.ModCheck;
import nc.multiblock.cuboidal.CuboidalPartPositionType;
import nc.tile.energy.ITileEnergy;
import nc.tile.internal.energy.EnergyConnection;
import nc.tile.internal.energy.EnergyStorage;
import nc.tile.internal.energy.EnergyTileWrapper;
import nc.tile.internal.energy.EnergyTileWrapperGT;
import nc.util.EnergyHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fml.common.Optional;

public class TileVacuumChamberEnergyPort extends TileVacuumChamberPart implements ITileEnergy
{

	protected final EnergyStorage backupStorage = new EnergyStorage(1);

	protected final EnergyConnection[] energyConnections = ITileEnergy.energyConnectionAll(EnergyConnection.IN);
	protected final EnergyTileWrapper[] energySides = ITileEnergy.getDefaultEnergySides(this);
	protected final EnergyTileWrapperGT[] energySidesGT = ITileEnergy.getDefaultEnergySidesGT(this);

	protected boolean ic2reg = false;

	public TileVacuumChamberEnergyPort()
	{
		super(CuboidalPartPositionType.EXTERIOR);
	}

	@Override
	public void onMachineAssembled(VacuumChamber controller)
	{
		doStandardNullControllerResponse(controller);
		super.onMachineAssembled(controller);

	}

	@Override
	public void onMachineBroken()
	{
		super.onMachineBroken();

	}

	public void onLoad()
	{
		super.onLoad();
		if (ModCheck.ic2Loaded())
		{
			addTileToENet();
		}
	}

	@Override
	public void invalidate()
	{
		super.invalidate();
		if (ModCheck.ic2Loaded())
		{
			removeTileFromENet();
		}
	}

	@Override
	public void onChunkUnload()
	{
		super.onChunkUnload();
		if (ModCheck.ic2Loaded())
		{
			removeTileFromENet();
		}
	}

	@Override
	public EnergyStorage getEnergyStorage()
	{
		if (!isMultiblockAssembled())
		{
			return backupStorage;
		}
		return getMultiblock().energyStorage;
	}

	@Override
	public EnergyConnection[] getEnergyConnections()
	{
		return energyConnections;
	}

	@Override
	public EnergyTileWrapper[] getEnergySides()
	{
		return energySides;
	}

	@Override
	public EnergyTileWrapperGT[] getEnergySidesGT()
	{
		return energySidesGT;
	}

	// IC2 Energy

	@Override
	public boolean getIC2Reg()
	{
		return ic2reg;
	}

	@Override
	public void setIC2Reg(boolean ic2reg)
	{
		this.ic2reg = ic2reg;
	}

	@Override
	public int getSinkTier()
	{
		return 1;
	}

	@Override
	public int getSourceTier()
	{
		if (!isMultiblockAssembled())
		{
			return 1;
		}
		return EnergyHelper.getEUTier(getMultiblock().requiredEnergy);
	}

	@Override
	@Optional.Method(modid = "ic2")
	public boolean acceptsEnergyFrom(IEnergyEmitter emitter, EnumFacing side)
	{
		return ITileEnergy.super.acceptsEnergyFrom(emitter, side);
	}

	@Override
	@Optional.Method(modid = "ic2")
	public double getDemandedEnergy()
	{
		return ITileEnergy.super.getDemandedEnergy();
	}

	@Override
	@Optional.Method(modid = "ic2")
	public double injectEnergy(EnumFacing directionFrom, double amount, double voltage)
	{
		return ITileEnergy.super.injectEnergy(directionFrom, amount, voltage);
	}

	// NBT

	@Override
	public NBTTagCompound writeAll(NBTTagCompound nbt)
	{
		super.writeAll(nbt);
		writeEnergy(nbt);
		writeEnergyConnections(nbt);
		return nbt;
	}

	@Override
	public void readAll(NBTTagCompound nbt)
	{
		super.readAll(nbt);
		readEnergy(nbt);
		readEnergyConnections(nbt);
	}

	// Capability

	@Override
	public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing side)
	{
		if (capability == CapabilityEnergy.ENERGY /*|| ModCheck.gregtechLoaded() && enable_gtce_eu
				&& capability == GregtechCapabilities.CAPABILITY_ENERGY_CONTAINER*/)
		{
			return hasEnergySideCapability(side);
		}
		return super.hasCapability(capability, side);
	}

	@Override
	public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing side)
	{
		if (capability == CapabilityEnergy.ENERGY)
		{
			if (hasEnergySideCapability(side))
			{
				return (T) getEnergySide(nonNullSide(side));
			}
			return null;
		}
		/*else if (ModCheck.gregtechLoaded() && capability == GregtechCapabilities.CAPABILITY_ENERGY_CONTAINER)
		{
			if (enable_gtce_eu && hasEnergySideCapability(side))
			{
				return (T) getEnergySideGT(nonNullSide(side));
			}
			return null;
		}*/
		return super.getCapability(capability, side);
	}

}
