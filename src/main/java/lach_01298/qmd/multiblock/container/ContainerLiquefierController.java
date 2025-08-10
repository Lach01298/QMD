package lach_01298.qmd.multiblock.container;

import lach_01298.qmd.liquefier.tile.TileLiquefierController;
import nc.container.multiblock.controller.ContainerMultiblockController;
import nc.multiblock.hx.HeatExchanger;
import nc.network.multiblock.HeatExchangerUpdatePacket;
import nc.tile.TileContainerInfo;
import nc.tile.hx.IHeatExchangerPart;
import net.minecraft.entity.player.EntityPlayer;

public class ContainerLiquefierController extends ContainerMultiblockController<HeatExchanger, IHeatExchangerPart, HeatExchangerUpdatePacket, TileLiquefierController, TileContainerInfo<TileLiquefierController>>
{
	public ContainerLiquefierController(EntityPlayer player, TileLiquefierController controller)
	{
		super(player, controller);
	}
}
