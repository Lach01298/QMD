package lach_01298.qmd.machine.tile;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import ic2.api.energy.EnergyNet;
import ic2.api.energy.tile.IEnergyAcceptor;
import ic2.api.energy.tile.IEnergyEmitter;
import ic2.api.energy.tile.IEnergySink;
import ic2.api.energy.tile.IEnergySource;
import lach_01298.qmd.enums.QMDRTGType;
import nc.ModCheck;
import nc.config.NCConfig;
import nc.multiblock.rtg.RTGMultiblock;
import nc.multiblock.rtg.RTGType;
import nc.multiblock.rtg.tile.TileRTG;
import nc.multiblock.tile.TileMultiblockPart;
import nc.tile.energy.IEnergySpread;
import nc.tile.energy.ITileEnergy;
import nc.tile.internal.energy.EnergyConnection;
import nc.tile.internal.energy.EnergyStorage;
import nc.tile.internal.energy.EnergyTileWrapper;
import nc.tile.internal.energy.EnergyTileWrapperGT;
import nc.util.EnergyHelper;
import nc.util.NCMath;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fml.common.Optional;

public class QMDTileRTG extends TileRTG
{

	public QMDTileRTG(int power, double radiation)
	{
		super(power, radiation);
	}

	public static class Strontium extends QMDTileRTG
	{

		public Strontium()
		{
			super(QMDRTGType.STRONTIUM.getPower(), QMDRTGType.STRONTIUM.getRadiation());
		}
	}
}
