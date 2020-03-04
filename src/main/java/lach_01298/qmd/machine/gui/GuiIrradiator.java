package lach_01298.qmd.machine.gui;

import java.io.IOException;

import lach_01298.qmd.QMD;
import lach_01298.qmd.machine.container.ContainerIrradiator;
import lach_01298.qmd.machine.container.ContainerOreLeacher;
import lach_01298.qmd.machine.network.QMDOpenSideConfigGuiPacket;
import lach_01298.qmd.machine.network.QMDOpenTileGuiPacket;
import lach_01298.qmd.machine.tile.TileItemDamageFuelProcessor;
import lach_01298.qmd.machine.tile.TileQMDProcessor.TileOreLeacher;
import lach_01298.qmd.network.QMDPacketHandler;
import nc.Global;
import nc.container.ContainerTile;
import nc.container.processor.ContainerInfuser;
import nc.container.processor.ContainerMachineConfig;
import nc.gui.NCGui;
import nc.gui.element.GuiFluidRenderer;
import nc.gui.element.GuiItemRenderer;
import nc.gui.element.NCButton;
import nc.gui.element.NCToggleButton;
import nc.gui.processor.GuiFluidSorptions;
import nc.gui.processor.GuiInfuser;
import nc.gui.processor.GuiItemFluidProcessor;
import nc.gui.processor.GuiItemSorptions;
import nc.network.PacketHandler;
import nc.network.gui.EmptyTankPacket;
import nc.network.gui.OpenSideConfigGuiPacket;
import nc.network.gui.OpenTileGuiPacket;
import nc.network.gui.ToggleRedstoneControlPacket;
import nc.tile.processor.TileItemFluidProcessor;
import nc.util.Lang;
import nc.util.NCUtil;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class GuiIrradiator  extends GuiItemDamageFuelMachine
{
	
	public GuiIrradiator(EntityPlayer player, TileItemDamageFuelProcessor tile)
	{
		this(player, tile, new ContainerIrradiator(player, tile));
	}

	private GuiIrradiator(EntityPlayer player, TileItemDamageFuelProcessor tile, ContainerTile container)
	{
		super("irradiator", player, tile, container);
		xSize = 176;
		ySize = 166;
	}

	@Override
	public void renderTooltips(int mouseX, int mouseY)
	{
		renderButtonTooltips(mouseX, mouseY);
	}

	public void renderButtonTooltips(int mouseX, int mouseY)
	{
		drawTooltip(Lang.localise("gui.nc.container.machine_side_config"), mouseX, mouseY, 6, 40, 18, 18);
		drawTooltip(Lang.localise("gui.nc.container.redstone_control"), mouseX, mouseY, 6, 60, 18, 18);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);

		drawTexturedModalRect(guiLeft + 62, guiTop + 57, 176, 0, getCookProgressScaled(52), 10);
		
		int rays = (int)Math.round(tile.tick/20.0*19);
		drawTexturedModalRect(guiLeft + 67, guiTop + 38, 176,10, 40, rays);
		//System.out.println("hi"+tile.tick);
		
		drawBackgroundExtras();
	}

	protected void drawBackgroundExtras()
	{
		
	}

	@Override
	public void initGui()
	{
		super.initGui();
		initButtons();
	}

	public void initButtons()
	{
		buttonList.add(new NCButton.MachineConfig(0, guiLeft +6, guiTop + 40));
		buttonList.add(new NCToggleButton.RedstoneControl(1, guiLeft + 6, guiTop + 60, tile));
	}

	@Override
	protected void actionPerformed(GuiButton guiButton)
	{
		if (tile.getWorld().isRemote)
		{
			if (guiButton.id == 0)
			{
				QMDPacketHandler.instance.sendToServer(new QMDOpenSideConfigGuiPacket(tile));
			}
			else if (guiButton.id == 1)
			{
				tile.setRedstoneControl(!tile.getRedstoneControl());
				PacketHandler.instance.sendToServer(new ToggleRedstoneControlPacket(tile));
			}
		}
	}

	public static class SideConfig extends GuiIrradiator
	{

		public SideConfig(EntityPlayer player, TileItemDamageFuelProcessor tile)
		{
			super(player, tile, new ContainerMachineConfig(player, tile));
		}

		@Override
		protected void keyTyped(char typedChar, int keyCode) throws IOException
		{
			if (isEscapeKeyDown(keyCode))
			{
				QMDPacketHandler.instance.sendToServer(new QMDOpenTileGuiPacket(tile));
			}
			else
				super.keyTyped(typedChar, keyCode);
		}

		@Override
		public void renderButtonTooltips(int mouseX, int mouseY)
		{
			drawTooltip(TextFormatting.BLUE + Lang.localise("gui.nc.container.input_item_config"), mouseX, mouseY, 44, 54, 18, 18);
			drawTooltip(TextFormatting.BLUE + Lang.localise("gui.nc.container.input_item_config"), mouseX, mouseY, 80, 21, 18, 18);
			drawTooltip(TextFormatting.GOLD + Lang.localise("gui.nc.container.output_item_config"), mouseX, mouseY, 116, 54, 18, 18);

			
		}

		@Override
		protected void drawBackgroundExtras()
		{
		};

		@Override
		public void initButtons()
		{
			buttonList.add(new NCButton.SorptionConfig.ItemInput(0, guiLeft + 43, guiTop + 53));
			buttonList.add(new NCButton.SorptionConfig.ItemInput(1, guiLeft + 79, guiTop + 20));
			buttonList.add(new NCButton.SorptionConfig.ItemOutputSmall(2, guiLeft + 115, guiTop + 53));

		}

		@Override
		protected void actionPerformed(GuiButton guiButton)
		{
			if (tile.getWorld().isRemote)
			{
				switch (guiButton.id)
				{
				case 0:
					FMLCommonHandler.instance().showGuiScreen(new GuiItemSorptions.Input(this, tile, 0));
					return;
				case 1:
					FMLCommonHandler.instance().showGuiScreen(new GuiItemSorptions.Input(this, tile, 1));
					return;
				case 2:
					FMLCommonHandler.instance().showGuiScreen(new GuiItemSorptions.Output(this, tile, 2));
					return;
				
				}

			}
		}
	}

}