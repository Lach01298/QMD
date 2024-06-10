package lach_01298.qmd.machine.gui;

import com.google.common.collect.Lists;
import lach_01298.qmd.QMD;
import nc.gui.element.GuiItemRenderer;
import nc.init.NCItems;
import nc.tile.energy.ITileEnergy;
import nc.util.*;
import nclegacy.gui.NCGuiLegacy;
import nclegacy.tile.TileItemFluidProcessorLegacy;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

import java.util.List;

public abstract class GuiItemFluidMachine extends NCGuiLegacy
{

	protected final EntityPlayer player;
	protected final TileItemFluidProcessorLegacy tile;
	protected final ResourceLocation gui_textures;
	protected GuiItemRenderer speedUpgradeRender = null, energyUpgradeRender = null;

	public GuiItemFluidMachine(String name, EntityPlayer player, TileItemFluidProcessorLegacy tile, Container inventory)
	{
		super(inventory);
		this.player = player;
		this.tile = tile;
		gui_textures = new ResourceLocation(QMD.MOD_ID + ":textures/gui/" + name + ".png");
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		String s = tile.getDisplayName().getUnformattedText();
		fontRenderer.drawString(s, xSize / 2 - fontRenderer.getStringWidth(s) / 2, 6, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		GlStateManager.color(1F, 1F, 1F, 1F);
		mc.getTextureManager().bindTexture(gui_textures);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}

	protected int getCookProgressScaled(double pixels)
	{
		double i = tile.time;
		double j = tile.baseProcessTime;
		return j != 0D ? (int) Math.round(i * pixels / j) : 0;
	}

	@Override
	protected void actionPerformed(GuiButton guiButton)
	{
		/*
		 * if (tile.getWorld().isRemote) { if (guiButton != null) if (guiButton
		 * instanceof NCButton) {
		 *
		 * } }
		 */
	}

	@Override
	public void drawEnergyTooltip(ITileEnergy tile, int mouseX, int mouseY, int x, int y, int width, int height)
	{
		if (this.tile.defaultProcessPower != 0)
			super.drawEnergyTooltip(tile, mouseX, mouseY, x, y, width, height);
		else
			drawNoEnergyTooltip(mouseX, mouseY, x, y, width, height);
	}

	@Override
	public List<String> energyInfo(ITileEnergy tile)
	{
		String energy = UnitHelper.prefix(tile.getEnergyStorage().getEnergyStored(),
				tile.getEnergyStorage().getMaxEnergyStored(), 5, "RF");
		String power = UnitHelper.prefix(this.tile.getProcessPower(), 5, "RF/t");

		String speedMultiplier = "x" + NCMath.decimalPlaces(this.tile.getSpeedMultiplier(), 2);
		String powerMultiplier = "x" + NCMath.decimalPlaces(this.tile.getPowerMultiplier(), 2);

		return Lists.newArrayList(
				TextFormatting.LIGHT_PURPLE + Lang.localize("gui.nc.container.energy_stored") + TextFormatting.WHITE
						+ " " + energy,
				TextFormatting.LIGHT_PURPLE + Lang.localize("gui.nc.container.process_power") + TextFormatting.WHITE
						+ " " + power,
				TextFormatting.AQUA + Lang.localize("gui.nc.container.speed_multiplier") + TextFormatting.WHITE + " "
						+ speedMultiplier,
				TextFormatting.AQUA + Lang.localize("gui.nc.container.power_multiplier") + TextFormatting.WHITE + " "
						+ powerMultiplier);
	}

	protected void drawUpgradeRenderers()
	{
		if (speedUpgradeRender == null)
		{
			speedUpgradeRender = new GuiItemRenderer(NCItems.upgrade, 0, guiLeft + 132, guiTop + ySize - 102, 0.5F);
		}
		if (energyUpgradeRender == null)
		{
			energyUpgradeRender = new GuiItemRenderer(NCItems.upgrade, 1, guiLeft + 152, guiTop + ySize - 102, 0.5F);
		}
		speedUpgradeRender.draw();
		energyUpgradeRender.draw();
	}
}
