package lach_01298.qmd.multiblock.gui;

import lach_01298.qmd.QMD;
import lach_01298.qmd.liquefier.LiquefierLogic;
import lach_01298.qmd.liquefier.tile.TileLiquefierController;
import lach_01298.qmd.util.Units;
import nc.gui.element.MultiblockButton;
import nc.gui.multiblock.controller.GuiMultiblockController;
import nc.multiblock.hx.HeatExchanger;
import nc.network.multiblock.ClearAllMaterialPacket;
import nc.network.multiblock.HeatExchangerUpdatePacket;
import nc.tile.TileContainerInfo;
import nc.tile.hx.IHeatExchangerPart;
import nc.util.Lang;
import nc.util.NCUtil;
import nc.util.StringHelper;
import nc.util.UnitHelper;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class GUILiquefierController extends GuiMultiblockController<HeatExchanger, IHeatExchangerPart, HeatExchangerUpdatePacket, TileLiquefierController, TileContainerInfo<TileLiquefierController>>
{
	protected final ResourceLocation gui_texture;

	public GUILiquefierController(Container inventory, EntityPlayer player, TileLiquefierController controller, String textureLocation)
	{
		super(inventory, player, controller, textureLocation);
		gui_texture = new ResourceLocation(QMD.MOD_ID + ":textures/gui/liquefier_controller.png");
		xSize = 176;
		ySize = 100;
	}

	@Override
	protected ResourceLocation getGuiTexture()
	{
		return gui_texture;
	}

	@Override
	public void renderTooltips(int mouseX, int mouseY)
	{
		if (NCUtil.isModifierKeyDown())
		{
			drawTooltip(clearAllInfo(), mouseX, mouseY, 153, 5, 18, 18);
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		if (multiblock.getLogic() instanceof LiquefierLogic)
		{
			LiquefierLogic logic  = (LiquefierLogic) multiblock.getLogic();
			int fontColor = multiblock.isExchangerOn ? 4210752 : 15619328;
			String title = multiblock.getInteriorLengthX() + "*" + multiblock.getInteriorLengthY() + "*" + multiblock.getInteriorLengthZ() + " " + Lang.localize("gui.qmd.container.liquefier_controller.name");
			fontRenderer.drawString(title, xSize / 2 - fontRenderer.getStringWidth(title) / 2, 6, fontColor);

			String underline = StringHelper.charLine('-', MathHelper.ceil((double) fontRenderer.getStringWidth(title) / fontRenderer.getStringWidth("-")));
			fontRenderer.drawString(underline, xSize / 2 - fontRenderer.getStringWidth(underline) / 2, 12, fontColor);
			int lineSpacing = 12;
			int yOffset = 25;

			if (NCUtil.isModifierKeyDown())
			{


				String gasIn = Lang.localize("gui.qmd.container.liquefier.gas_in", UnitHelper.prefix(logic.getGasInputRate(),5,"B/t",-1));
				fontRenderer.drawString(gasIn, xSize / 2 - fontRenderer.getStringWidth(gasIn) / 2, yOffset, fontColor);

				String coolantOut = Lang.localize("gui.qmd.container.liquefier.coolant_out",UnitHelper.prefix(logic.getCoolantOutputRate(),5,"B/t",-1));
				fontRenderer.drawString(coolantOut, xSize / 2 - fontRenderer.getStringWidth(coolantOut) / 2, yOffset+lineSpacing*1, fontColor);

				String compressorEnergyEfficiency = Lang.localize("gui.qmd.container.liquefier.compressor_energy_efficiency", String.format("%.2f", logic.getEnergyEfficiency()*100));
				fontRenderer.drawString(compressorEnergyEfficiency, xSize / 2 - fontRenderer.getStringWidth(compressorEnergyEfficiency) / 2, yOffset+lineSpacing*2, fontColor);

				String tempDiff = Lang.localize("gui.qmd.container.liquefier.temperature_difference", UnitHelper.prefix(multiblock.totalTempDiff,5,"K"));
				fontRenderer.drawString(tempDiff, xSize / 2 - fontRenderer.getStringWidth(tempDiff) / 2, yOffset+lineSpacing*3, fontColor);

				String compressorHeatEfficiency = Lang.localize("gui.qmd.container.liquefier.compressor_heat_efficiency", String.format("%.2f", logic.getHeatEfficiency()*100));
				fontRenderer.drawString(compressorHeatEfficiency, xSize / 2 - fontRenderer.getStringWidth(compressorHeatEfficiency) / 2, yOffset+lineSpacing*4, fontColor);

				String pressureEfficiency = Lang.localize("gui.qmd.container.liquefier.pressure_efficiency", String.format("%.2f", logic.getPressureEfficiency()*100));
				fontRenderer.drawString(pressureEfficiency, xSize / 2 - fontRenderer.getStringWidth(pressureEfficiency) / 2, yOffset+lineSpacing*5, fontColor);
			}
			else
			{
				String liquidOut = Lang.localize("gui.qmd.container.liquefier.liquid_out", UnitHelper.prefix(logic.getLiquidOutputRate(),5,"B/t",-1));
				fontRenderer.drawString(liquidOut, xSize / 2 - fontRenderer.getStringWidth(liquidOut) / 2, yOffset, fontColor);

				String coolantIn = Lang.localize("gui.qmd.container.liquefier.coolant_in", UnitHelper.prefix(logic.getCoolantInputRate(),5,"B/t",-1)) + Lang.localize("gui.qmd.container.liquefier.efficiency", String.format("%.2f", 100*logic.getHeatInefficiency()));
				fontRenderer.drawString(coolantIn, xSize / 2 - fontRenderer.getStringWidth(coolantIn) / 2, yOffset+lineSpacing, fontColor);

				String power = Lang.localize("gui.qmd.container.liquefier.power", UnitHelper.prefix(logic.getPowerUsage(),5,"RF/t")) + Lang.localize("gui.qmd.container.liquefier.efficiency", String.format("%.2f", 100*logic.getEnergyInefficiency()));
				fontRenderer.drawString(power, xSize / 2 - fontRenderer.getStringWidth(power) / 2, yOffset+lineSpacing*2, fontColor);

				String heatTransfer = Lang.localize("gui.qmd.container.liquefier.heat_transfer", UnitHelper.prefix(logic.getHeatTransferRate(),5,"H/t"));
				fontRenderer.drawString(heatTransfer, xSize / 2 - fontRenderer.getStringWidth(heatTransfer) / 2, yOffset+lineSpacing*3, fontColor);

				String compressorNozzleAmount = Lang.localize("gui.qmd.container.liquefier.compressor_nozzle_amount", logic.getCompressorAmount(), logic.getNozzlesAmount());
				fontRenderer.drawString(compressorNozzleAmount, xSize / 2 - fontRenderer.getStringWidth(compressorNozzleAmount) / 2, yOffset+lineSpacing*4, fontColor);

				String pressure = Lang.localize("gui.qmd.container.liquefier.pressure", UnitHelper.prefix(logic.getPressure(),5,"bar"));
				fontRenderer.drawString(pressure, xSize / 2 - fontRenderer.getStringWidth(pressure) / 2, yOffset+lineSpacing*5, fontColor);

			}
		}
	}

	@Override
	public void initGui()
	{
		super.initGui();
		buttonList.add(new MultiblockButton.ClearAllMaterial(0, guiLeft + 153, guiTop + 5));
	}

	@Override
	protected void actionPerformed(GuiButton guiButton)
	{
		if (multiblock.WORLD.isRemote)
		{
			if (guiButton.id == 0 && NCUtil.isModifierKeyDown())
			{
				new ClearAllMaterialPacket(tile.getTilePos()).sendToServer();
			}
		}
	}


}

