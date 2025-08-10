package lach_01298.qmd.multiblock.network;

import io.netty.buffer.ByteBuf;
import lach_01298.qmd.liquefier.tile.TileLiquefierController;
import nc.multiblock.hx.HeatExchanger;
import nc.network.multiblock.HeatExchangerRenderPacket;
import nc.network.multiblock.HeatExchangerUpdatePacket;
import nc.network.multiblock.MultiblockUpdatePacket;
import nc.tile.TileContainerInfo;
import nc.tile.hx.IHeatExchangerPart;
import nc.tile.internal.fluid.Tank;
import net.minecraft.util.math.BlockPos;

import java.util.List;

public class LiquefierRenderPacket extends HeatExchangerRenderPacket
{

	public LiquefierRenderPacket()
	{
		super();
	}

	public LiquefierRenderPacket(BlockPos pos, List<Tank> shellTanks)
	{
		super(pos, shellTanks);
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		super.fromBytes(buf);
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		super.toBytes(buf);
	}

	public static class Handler extends MultiblockUpdatePacket.Handler<HeatExchanger, IHeatExchangerPart, HeatExchangerUpdatePacket, TileLiquefierController, TileContainerInfo<TileLiquefierController>, LiquefierRenderPacket>
	{

		public Handler()
		{
			super(TileLiquefierController.class);
		}

		@Override
		protected void onPacket(LiquefierRenderPacket message, HeatExchanger multiblock)
		{
			multiblock.onRenderPacket(message);
		}
	}
}
