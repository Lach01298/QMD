package lach_01298.qmd.multiblock.gui;

import lach_01298.qmd.QMD;
import lach_01298.qmd.accelerator.Accelerator;
import lach_01298.qmd.accelerator.AcceleratorLogic;
import lach_01298.qmd.accelerator.MassSpectrometerLogic;
import lach_01298.qmd.accelerator.tile.IAcceleratorPart;
import lach_01298.qmd.accelerator.tile.TileMassSpectrometerController;
import lach_01298.qmd.multiblock.network.AcceleratorUpdatePacket;
import lach_01298.qmd.multiblock.network.QMDClearTankPacket;
import lach_01298.qmd.util.Units;
import nc.gui.element.GuiFluidRenderer;
import nc.gui.element.MultiblockButton;
import nc.gui.element.NCButton;
import nc.gui.multiblock.controller.GuiLogicMultiblockController;
import nc.network.multiblock.ClearAllMaterialPacket;
import nc.tile.TileContainerInfo;
import nc.util.Lang;
import nc.util.NCUtil;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.List;

public class GuiMassSpectrometerController
		extends GuiLogicMultiblockController<Accelerator, AcceleratorLogic, IAcceleratorPart, AcceleratorUpdatePacket, TileMassSpectrometerController, TileContainerInfo<TileMassSpectrometerController>, MassSpectrometerLogic>
{

	protected final ResourceLocation gui_texture;

	public GuiMassSpectrometerController(Container inventory, EntityPlayer player, TileMassSpectrometerController controller, String textureLocation)
	{
		super(inventory, player, controller, textureLocation);
		gui_texture = new ResourceLocation(QMD.MOD_ID + ":textures/gui/mass_spectrometer_controller.png");
		xSize = 176;
		ySize = 201;
	}

	@Override
	protected ResourceLocation getGuiTexture()
	{
		return gui_texture;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{

		int offset = 8;
		int fontColor = multiblock.isControllorOn ? -1 : 15641088;
		String title = Lang.localize("gui.qmd.container.mass_spectrometer_controller.name");
		fontRenderer.drawString(title, offset+40, 4, fontColor);

		String speedMultiplier = Lang.localize("gui.qmd.container.speed",String.format("%.2f", getLogic().speed));
		fontRenderer.drawString(speedMultiplier, offset, 108, fontColor);

		if (!NCUtil.isModifierKeyDown())
		{

		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{

		GlStateManager.color(1F, 1F, 1F, 1F);
		mc.getTextureManager().bindTexture(getGuiTexture());
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		int power = (int) Math.round((double) multiblock.energyStorage.getEnergyStored() / (double) multiblock.energyStorage.getMaxEnergyStored() * 92);

		drawTexturedModalRect(guiLeft + 8, guiTop + 105 - power, 176, 92 - power, 4, power);

		int heat = (int) Math.round((double) multiblock.heatBuffer.getHeatStored() / (double) multiblock.heatBuffer.getHeatCapacity() * 92);
		drawTexturedModalRect(guiLeft + 15, guiTop + 105 - heat, 180, 92 - heat, 4, heat);

		int coolant = (int) Math.round((double) multiblock.tanks.get(0).getFluidAmount() / (double) multiblock.tanks.get(0).getCapacity() * 92);
		drawTexturedModalRect(guiLeft + 22, guiTop + 105 - coolant, 184, 92 - coolant, 4, coolant);
		
		
		// draw progress bar
		int progress = Math.min((int) Math.round((double) getLogic().workDone / (double) getLogic().recipeWork * 101), 101);
		drawTexturedModalRect(guiLeft + 52, guiTop + 51, 0, 201, progress, 55);

		
		GuiFluidRenderer.renderGuiTank(multiblock.tanks.get(2), guiLeft + 46, guiTop + 33, zLevel, 16, 16);
		GuiFluidRenderer.renderGuiTank(multiblock.tanks.get(3), guiLeft + 82, guiTop + 33, zLevel, 16, 16);
		GuiFluidRenderer.renderGuiTank(multiblock.tanks.get(4), guiLeft + 101, guiTop + 33, zLevel, 16, 16);
		GuiFluidRenderer.renderGuiTank(multiblock.tanks.get(5), guiLeft + 120, guiTop + 33, zLevel, 16, 16);
		GuiFluidRenderer.renderGuiTank(multiblock.tanks.get(6), guiLeft + 139, guiTop + 33, zLevel, 16, 16);
		
		
		
	}

	@Override
	public void renderTooltips(int mouseX, int mouseY)
	{
		if (NCUtil.isModifierKeyDown())
			drawTooltip(clearAllInfo(), mouseX, mouseY, 30, 80, 18, 18);

		drawFluidTooltip(multiblock.tanks.get(2), mouseX, mouseY, 46, 33, 16, 16);
		drawFluidTooltip(multiblock.tanks.get(3), mouseX, mouseY, 82, 33, 16, 16);
		drawFluidTooltip(multiblock.tanks.get(4), mouseX, mouseY, 101, 33, 16, 16);
		drawFluidTooltip(multiblock.tanks.get(5), mouseX, mouseY, 120, 33, 16, 16);
		drawFluidTooltip(multiblock.tanks.get(6), mouseX, mouseY, 139, 33, 16, 16);
		
		drawTooltip(energyInfo(), mouseX, mouseY, 7, 12, 6, 94);
		drawTooltip(heatInfo(), mouseX, mouseY, 14, 12, 6, 94);
		drawTooltip(coolantInfo(), mouseX, mouseY, 21, 12, 6, 94);
	}

	public List<String> energyInfo()
	{
		List<String> info = new ArrayList<String>();
		info.add(TextFormatting.YELLOW + Lang.localize("gui.qmd.container.energy_stored",
				Units.getSIFormat(multiblock.energyStorage.getEnergyStored(), "RF"),
				Units.getSIFormat(multiblock.energyStorage.getMaxEnergyStored(), "RF")));
		info.add(TextFormatting.RED + Lang.localize("gui.qmd.container.required_energy",
				Units.getSIFormat(multiblock.requiredEnergy, "RF/t")));
		return info;
	}
	
	public List<String> heatInfo()
	{
		List<String> info = new ArrayList<String>();
		info.add(TextFormatting.YELLOW + Lang.localize("gui.qmd.container.heat_stored",
				Units.getSIFormat(multiblock.heatBuffer.getHeatStored(), "H"),
				Units.getSIFormat(multiblock.heatBuffer.getHeatCapacity(), "H")));
		info.add(Lang.localize("gui.qmd.container.temperature", Units.getSIFormat(multiblock.getTemperature(), "K")));
		info.add(Lang.localize("gui.qmd.container.max_temperature", Units.getSIFormat(multiblock.maxOperatingTemp, "K")));
		info.add(TextFormatting.BLUE + Lang.localize("gui.qmd.container.cooling",
				Units.getSIFormat(-multiblock.cooling, "H/t")));
		info.add(TextFormatting.RED + Lang.localize("gui.qmd.container.heating",
				Units.getSIFormat(multiblock.currentHeating, "H/t")));
		info.add(TextFormatting.RED + Lang.localize("gui.qmd.container.max_heating",
				Units.getSIFormat(multiblock.rawHeating + multiblock.getMaxExternalHeating(), "H/t")));
		info.add(TextFormatting.RED + Lang.localize("gui.qmd.container.external_heating",
				Units.getSIFormat(multiblock.getMaxExternalHeating(), "H/t")));
		return info;
	}
	
	public List<String> coolantInfo()
	{
		List<String> info = new ArrayList<String>();
		info.add(TextFormatting.YELLOW + Lang.localize("gui.qmd.container.coolant_stored",
				Units.getSIFormat(multiblock.tanks.get(0).getFluidAmount(), -3, "B"),
				Units.getSIFormat(multiblock.tanks.get(0).getCapacity(), -3, "B")));
		info.add(TextFormatting.BLUE + Lang.localize("gui.qmd.container.max_coolant_in",
				Units.getSIFormat(multiblock.maxCoolantIn, -6, "B/t")));
		info.add(TextFormatting.RED + Lang.localize("gui.qmd.container.max_coolant_out",
				Units.getSIFormat(multiblock.maxCoolantOut, -6, "B/t")));

		return info;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		super.drawScreen(mouseX, mouseY, partialTicks);

		renderTooltips(mouseX, mouseY);
	}

	@Override
	public void initGui()
	{
		super.initGui();
		buttonList.add(new MultiblockButton.ClearAllMaterial(0, guiLeft + 30, guiTop + 80));
		buttonList.add(new NCButton.ClearTank(1, guiLeft + 46, guiTop + 33, 16, 16));
		buttonList.add(new NCButton.ClearTank(2, guiLeft + 82, guiTop + 33, 16, 16));
		buttonList.add(new NCButton.ClearTank(3, guiLeft + 101, guiTop + 33, 16, 16));
		buttonList.add(new NCButton.ClearTank(4, guiLeft + 120, guiTop + 33, 16, 16));
		buttonList.add(new NCButton.ClearTank(5, guiLeft + 139, guiTop + 33, 16, 16));
	}

	@Override
	protected void actionPerformed(GuiButton guiButton)
	{
		if (multiblock.WORLD.isRemote)
		{
			if(NCUtil.isModifierKeyDown())
			{
				switch(guiButton.id)
				{
				case 0:
					new ClearAllMaterialPacket(tile.getTilePos()).sendToServer();
					break;
				case 1:
					new QMDClearTankPacket(tile.getTilePos(),2).sendToServer();
					break;
				case 2:
					new QMDClearTankPacket(tile.getTilePos(),3).sendToServer();
					break;
				case 3:
					new QMDClearTankPacket(tile.getTilePos(),4).sendToServer();
					break;
				case 4:
					new QMDClearTankPacket(tile.getTilePos(),5).sendToServer();
					break;
				case 5:
					new QMDClearTankPacket(tile.getTilePos(),6).sendToServer();
					break;
				}
				
			}

		}
	}

}
