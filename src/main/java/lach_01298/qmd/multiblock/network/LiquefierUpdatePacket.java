package lach_01298.qmd.multiblock.network;

import io.netty.buffer.ByteBuf;
import lach_01298.qmd.liquefier.tile.TileLiquefierController;
import lach_01298.qmd.util.ByteUtil;
import nc.multiblock.hx.HeatExchanger;
import nc.network.multiblock.HeatExchangerUpdatePacket;
import nc.network.multiblock.MultiblockUpdatePacket;
import nc.tile.TileContainerInfo;
import nc.tile.hx.IHeatExchangerPart;
import nc.tile.internal.energy.EnergyStorage;
import nc.tile.internal.fluid.Tank;
import nc.tile.internal.fluid.Tank.TankInfo;
import net.minecraft.util.math.BlockPos;

import java.util.List;

public class LiquefierUpdatePacket extends HeatExchangerUpdatePacket
{
	public EnergyStorage energyStorage;
	public List<TankInfo> tanksInfo;
	public double energyEfficiency, heatEfficiency, pressure;

	public LiquefierUpdatePacket()
	{
		super();
	}

	public LiquefierUpdatePacket(BlockPos pos, boolean isExchangerOn, int totalNetworkCount, int activeNetworkCount, int activeTubeCount, int activeContactCount, double tubeInputRateFP, double shellInputRateFP, double heatTransferRateFP, double totalTempDiff, EnergyStorage energyStorage, List<Tank> tanks, double energyEfficiency, double heatEfficiency, double pressure)
	{
		super(pos, isExchangerOn, totalNetworkCount, activeNetworkCount, activeTubeCount, activeContactCount, tubeInputRateFP, shellInputRateFP, heatTransferRateFP, totalTempDiff);
		this.energyStorage = energyStorage;
		tanksInfo = TankInfo.getInfoList(tanks);
		this.energyEfficiency = energyEfficiency;
		this.heatEfficiency = heatEfficiency;
		this.pressure = pressure;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		super.fromBytes(buf);
		energyStorage = ByteUtil.readBufEnergy(buf);
		tanksInfo = readTankInfos(buf);
		energyEfficiency = buf.readDouble();
		heatEfficiency = buf.readDouble();
		pressure = buf.readDouble();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		super.toBytes(buf);
		ByteUtil.writeBufEnergy(energyStorage, buf);
		writeTankInfos(buf, tanksInfo);
		buf.writeDouble(energyEfficiency);
		buf.writeDouble(heatEfficiency);
		buf.writeDouble(pressure);
	}

	public static class Handler extends MultiblockUpdatePacket.Handler<HeatExchanger, IHeatExchangerPart, HeatExchangerUpdatePacket, TileLiquefierController, TileContainerInfo<TileLiquefierController>, LiquefierUpdatePacket>
	{

		public Handler()
		{
			super(TileLiquefierController.class);
		}

		@Override
		protected void onPacket(LiquefierUpdatePacket message, HeatExchanger multiblock)
		{
			multiblock.onMultiblockUpdatePacket(message);
		}
	}
}
