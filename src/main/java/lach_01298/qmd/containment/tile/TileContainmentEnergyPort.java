package lach_01298.qmd.containment.tile;

import javax.annotation.Nullable;

import lach_01298.qmd.containment.Containment;
import nc.multiblock.cuboidal.CuboidalPartPositionType;
import nc.tile.energy.ITileEnergy;
import nc.tile.internal.energy.EnergyConnection;
import nc.tile.internal.energy.EnergyStorage;
import nc.tile.internal.energy.EnergyTileWrapper;
import nc.tile.internal.energy.EnergyTileWrapperGT;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;

public class TileContainmentEnergyPort extends TileContainmentPart implements ITileEnergy
{

	protected final EnergyStorage backupStorage = new EnergyStorage(1);
	
	protected final EnergyConnection[] energyConnections = ITileEnergy.energyConnectionAll(EnergyConnection.IN);
	protected final EnergyTileWrapper[] energySides = ITileEnergy.getDefaultEnergySides(this);
	
	public TileContainmentEnergyPort()
	{
		super(CuboidalPartPositionType.EXTERIOR);
	}


	@Override
	public void onMachineAssembled(Containment controller)
	{
		super.onMachineAssembled(controller);
		
	}

	@Override
	public void onMachineBroken()
	{
		super.onMachineBroken();
		
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
	public int getEUSourceTier()
	{
		return 0;
	}

	@Override
	public int getEUSinkTier()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void addTileToENet()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeTileFromENet()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public EnergyTileWrapper[] getEnergySides()
	{
		return energySides;
	}

	@Override
	public EnergyTileWrapperGT[] getEnergySidesGT()
	{
		return null;
	}

	
	// NBT
	
	@Override
	public NBTTagCompound writeAll(NBTTagCompound nbt) {
		super.writeAll(nbt);
		writeEnergy(nbt);
		writeEnergyConnections(nbt);
		return nbt;
	}
	
	@Override
	public void readAll(NBTTagCompound nbt) {
		super.readAll(nbt);
		readEnergy(nbt);
		readEnergyConnections(nbt);
	}
	
	
	// Capability
	
	@Override
	public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing side)
	{
		if (capability == CapabilityEnergy.ENERGY)
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
		return super.getCapability(capability, side);
	}
	
	
}
