package lach_01298.qmd.machine.gui;

import lach_01298.qmd.machine.container.ContainerOreLeacher;
import lach_01298.qmd.machine.network.QMDOpenSideConfigGuiPacket;
import lach_01298.qmd.machine.network.QMDOpenTileGuiPacket;
import lach_01298.qmd.machine.tile.TileQMDProcessor;
import nc.gui.element.GuiFluidRenderer;
import nc.gui.element.NCButton;
import nc.gui.element.NCToggleButton;
import nc.network.gui.ClearTankPacket;
import nc.network.gui.ToggleRedstoneControlPacket;
import nc.util.Lang;
import nc.util.NCUtil;
import nclegacy.container.ContainerMachineConfigLegacy;
import nclegacy.container.ContainerTileLegacy;
import nclegacy.gui.GuiFluidSorptionsLegacy;
import nclegacy.gui.GuiItemSorptionsLegacy;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.FMLCommonHandler;

import java.io.IOException;

public class GuiOreLeacher  extends GuiItemFluidMachine
{
	
	public GuiOreLeacher(EntityPlayer player, TileQMDProcessor tile)
	{
		this(player, tile, new ContainerOreLeacher(player, tile));
	}

	private GuiOreLeacher(EntityPlayer player, TileQMDProcessor tile, ContainerTileLegacy container)
	{
		super("ore_leacher", player, tile, container);
		xSize = 176;
		ySize = 166;
	}

	@Override
	public void renderTooltips(int mouseX, int mouseY)
	{
		drawEnergyTooltip(tile, mouseX, mouseY, 8, 6, 16, 74);
		renderButtonTooltips(mouseX, mouseY);
	}

	public void renderButtonTooltips(int mouseX, int mouseY)
	{
		drawFluidTooltip(tile.getTanks().get(0), mouseX, mouseY, 36, 42, 16, 16);
		drawFluidTooltip(tile.getTanks().get(1), mouseX, mouseY, 56, 42, 16, 16);
		drawFluidTooltip(tile.getTanks().get(2), mouseX, mouseY, 76, 42, 16, 16);

		drawTooltip(Lang.localize("gui.nc.container.machine_side_config"), mouseX, mouseY, 27, 63, 18, 18);
		drawTooltip(Lang.localize("gui.nc.container.redstone_control"), mouseX, mouseY, 47, 63, 18, 18);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);

		if (tile.defaultProcessPower != 0)
		{
			int e = (int) Math.round(74D * tile.getEnergyStorage().getEnergyStored() / tile.getEnergyStorage().getMaxEnergyStored());
			drawTexturedModalRect(guiLeft + 8, guiTop + 6 + 74 - e, 176, 90 + 74 - e, 16, e);
		}
		else
			drawGradientRect(guiLeft + 8, guiTop + 6, guiLeft + 8 + 16, guiTop + 6 + 74, 0xFFC6C6C6, 0xFF8B8B8B);

		drawTexturedModalRect(guiLeft + 40, guiTop + 29, 176, 0, getCookProgressScaled(48), 11);
		drawTexturedModalRect(guiLeft + 94, guiTop + 46, 176, 12, getCookProgressScaled(48)-32 < 0 ?0 : getCookProgressScaled(48)-32, 8);

		drawUpgradeRenderers();

		drawBackgroundExtras();
	}

	protected void drawBackgroundExtras()
	{
		GuiFluidRenderer.renderGuiTank(tile.getTanks().get(0), guiLeft + 36, guiTop + 42, zLevel, 16, 16);
		GuiFluidRenderer.renderGuiTank(tile.getTanks().get(1), guiLeft + 56, guiTop + 42, zLevel, 16, 16);
		GuiFluidRenderer.renderGuiTank(tile.getTanks().get(2), guiLeft + 76, guiTop + 42, zLevel, 16, 16);
	}

	@Override
	public void initGui()
	{
		super.initGui();
		initButtons();
	}

	public void initButtons()
	{
		buttonList.add(new NCButton.ClearTank(0, guiLeft + 36, guiTop + 42, 16, 16));
		buttonList.add(new NCButton.ClearTank(1, guiLeft + 56, guiTop + 42, 16, 16));
		buttonList.add(new NCButton.ClearTank(2, guiLeft + 76, guiTop + 42, 16, 16));

		buttonList.add(new NCButton.MachineConfig(3, guiLeft + 27, guiTop + 63));
		buttonList.add(new NCToggleButton.RedstoneControl(4, guiLeft + 47, guiTop + 63, tile));
	}

	@Override
	protected void actionPerformed(GuiButton guiButton)
	{
		if (tile.getWorld().isRemote)
		{
			for (int i = 0; i < 3; i++)
				if (guiButton.id == i && NCUtil.isModifierKeyDown())
				{
					new ClearTankPacket(tile, i).sendToServer();
					return;
				}
			if (guiButton.id == 3)
			{
				new QMDOpenSideConfigGuiPacket(tile).sendToServer();
			}
			else if (guiButton.id == 4)
			{
				tile.setRedstoneControl(!tile.getRedstoneControl());
				new ToggleRedstoneControlPacket(tile).sendToServer();
			}
		}
	}

	public static class SideConfig extends GuiOreLeacher
	{

		public SideConfig(EntityPlayer player, TileQMDProcessor tile)
		{
			super(player, tile, new ContainerMachineConfigLegacy(player, tile));
		}

		@Override
		protected void keyTyped(char typedChar, int keyCode) throws IOException
		{
			if (isEscapeKeyDown(keyCode))
			{
				new QMDOpenTileGuiPacket(tile).sendToServer();
			}
			else
				super.keyTyped(typedChar, keyCode);
		}

		@Override
		public void renderButtonTooltips(int mouseX, int mouseY)
		{
			drawTooltip(TextFormatting.BLUE + Lang.localize("gui.nc.container.input_item_config"), mouseX, mouseY, 36, 11, 18, 18);
			drawTooltip(TextFormatting.DARK_AQUA + Lang.localize("gui.nc.container.input_tank_config"), mouseX, mouseY, 36, 42, 18, 18);
			drawTooltip(TextFormatting.DARK_AQUA + Lang.localize("gui.nc.container.input_tank_config"), mouseX, mouseY, 56, 42, 18, 18);
			drawTooltip(TextFormatting.DARK_AQUA + Lang.localize("gui.nc.container.input_tank_config"), mouseX, mouseY, 76, 42, 18, 18);
			drawTooltip(TextFormatting.GOLD + Lang.localize("gui.nc.container.output_item_config"), mouseX, mouseY, 112, 42, 18, 18);
			drawTooltip(TextFormatting.GOLD + Lang.localize("gui.nc.container.output_item_config"), mouseX, mouseY, 132, 42, 18, 18);
			drawTooltip(TextFormatting.GOLD + Lang.localize("gui.nc.container.output_item_config"), mouseX, mouseY, 152, 42, 18, 18);
			drawTooltip(TextFormatting.DARK_BLUE + Lang.localize("gui.nc.container.upgrade_config"), mouseX, mouseY, 131, 63, 18, 18);
			drawTooltip(TextFormatting.YELLOW + Lang.localize("gui.nc.container.upgrade_config"), mouseX, mouseY, 151, 63, 18, 18);
		}

		@Override
		protected void drawUpgradeRenderers()
		{
		}

		@Override
		protected void drawBackgroundExtras()
		{
		};

		@Override
		public void initButtons()
		{
			buttonList.add(new NCButton.SorptionConfig.ItemInput(0, guiLeft + 35, guiTop + 10, 18, 18));
			buttonList.add(new NCButton.SorptionConfig.FluidInput(1, guiLeft + 35, guiTop + 41, 18, 18));
			buttonList.add(new NCButton.SorptionConfig.FluidInput(2, guiLeft + 55, guiTop + 41, 18, 18));
			buttonList.add(new NCButton.SorptionConfig.FluidInput(3, guiLeft + 75, guiTop + 41, 18, 18));
			buttonList.add(new NCButton.SorptionConfig.ItemOutput(4, guiLeft + 111, guiTop + 41, 18, 18));
			buttonList.add(new NCButton.SorptionConfig.ItemOutput(5, guiLeft + 131, guiTop + 41, 18, 18));
			buttonList.add(new NCButton.SorptionConfig.ItemOutput(6, guiLeft + 151, guiTop + 41, 18, 18));
			buttonList.add(new NCButton.SorptionConfig.SpeedUpgrade(7, guiLeft + 131, guiTop + 63, 18, 18));
			buttonList.add(new NCButton.SorptionConfig.EnergyUpgrade(8, guiLeft + 151, guiTop + 63, 18, 18));
		}

		@Override
		protected void actionPerformed(GuiButton guiButton)
		{
			if (tile.getWorld().isRemote)
			{
				switch (guiButton.id)
				{
				case 0:
					FMLCommonHandler.instance().showGuiScreen(new GuiItemSorptionsLegacy.Input(this, tile, 0));
					return;
				case 1:
					FMLCommonHandler.instance().showGuiScreen(new GuiFluidSorptionsLegacy.Input(this, tile, 0));
					return;
				case 2:
					FMLCommonHandler.instance().showGuiScreen(new GuiFluidSorptionsLegacy.Input(this, tile, 1));
					return;
				case 3:
					FMLCommonHandler.instance().showGuiScreen(new GuiFluidSorptionsLegacy.Input(this, tile, 2));
					return;
				case 4:
					FMLCommonHandler.instance().showGuiScreen(new GuiItemSorptionsLegacy.Output(this, tile, 1));
					return;
				case 5:
					FMLCommonHandler.instance().showGuiScreen(new GuiItemSorptionsLegacy.Output(this, tile, 2));
					return;
				case 6:
					FMLCommonHandler.instance().showGuiScreen(new GuiItemSorptionsLegacy.Output(this, tile, 3));
					return;
				case 7:
					FMLCommonHandler.instance().showGuiScreen(new GuiItemSorptionsLegacy.SpeedUpgrade(this, tile, 4));
					return;
				case 8:
					FMLCommonHandler.instance().showGuiScreen(new GuiItemSorptionsLegacy.EnergyUpgrade(this, tile, 5));
					return;
				}

			}
		}
	}

}
