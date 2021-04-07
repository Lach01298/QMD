package lach_01298.qmd.multiblock.gui;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import lach_01298.qmd.QMD;
import lach_01298.qmd.containment.Containment;
import lach_01298.qmd.containment.NeutralContainmentLogic;
import lach_01298.qmd.containment.tile.IContainmentController;
import lach_01298.qmd.gui.GuiParticle;
import lach_01298.qmd.util.Units;
import nc.gui.element.GuiFluidRenderer;
import nc.multiblock.gui.GuiLogicMultiblock;
import nc.multiblock.gui.element.MultiblockButton;
import nc.network.PacketHandler;
import nc.network.multiblock.ClearAllMaterialPacket;
import nc.util.Lang;
import nc.util.NCUtil;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

public class GuiNeutralContainmentController
		extends GuiLogicMultiblock<Containment, NeutralContainmentLogic, IContainmentController>
{

	protected final ResourceLocation gui_texture;

	private final GuiParticle guiParticle;

	public GuiNeutralContainmentController(EntityPlayer player, IContainmentController controller)
	{
		super(player, controller);
		gui_texture = new ResourceLocation(QMD.MOD_ID + ":textures/gui/neutral_containment_controller.png");
		xSize = 176;
		ySize = 177;
		guiParticle = new GuiParticle(this);

	}

	@Override
	protected ResourceLocation getGuiTexture()
	{
		return gui_texture;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{

		int offset = 22;
		int fontColor = multiblock.isContainmentOn ? -1 : 15641088;
		String title = Lang.localise("gui.qmd.container.neutral_containment_controller.name");
		fontRenderer.drawString(title, offset, 5, fontColor);

		String maxTemperature = Lang.localise("gui.qmd.container.accelerator.max_temperature",
				Units.getSIFormat(multiblock.maxOperatingTemp, "K"));
		fontRenderer.drawString(maxTemperature, offset, 84, fontColor);

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

		// power bar
		int power = (int) Math.round((double) multiblock.energyStorage.getEnergyStored()
				/ (double) multiblock.energyStorage.getMaxEnergyStored() * 74);
		drawTexturedModalRect(guiLeft + 160, guiTop + 82 - power, 176, 74 - power, 6, power);

		// heat bar
		int heat = (int) Math.round(
				(double) multiblock.heatBuffer.getHeatStored() / (double) multiblock.heatBuffer.getHeatCapacity() * 74);
		drawTexturedModalRect(guiLeft + 8, guiTop + 82 - heat, 182, 74 - heat, 4, heat);

		// coolant bar
		int coolant = (int) Math.round((double) multiblock.tanks.get(0).getFluidAmount()
				/ (double) multiblock.tanks.get(0).getCapacity() * 74);
		drawTexturedModalRect(guiLeft + 15, guiTop + 82 - coolant, 186, 74 - coolant, 4, coolant);

		// input left
		if (multiblock.beams.get(0).getParticleStack() != null)
		{
			drawTexturedModalRect(guiLeft + 44, guiTop + 33, 190, 0, 16, 6);
		}

		int left = (int) Math
				.min(Math.round((double) logic.particle1WorkDone / (double) logic.recipeParticle1Work * 10), 10);
		drawTexturedModalRect(guiLeft + 60, guiTop + 33, 206, 0, left, 6);

		// input right
		if (multiblock.beams.get(1).getParticleStack() != null)
		{
			drawTexturedModalRect(guiLeft + 114, guiTop + 33, 190, 6, 16, 6);
		}
		int right = (int) Math
				.min(Math.round((double) logic.particle2WorkDone / (double) logic.recipeParticle2Work * 10), 10);

		drawTexturedModalRect(guiLeft + 114 - right, guiTop + 33, 216 - right, 6, right, 6);

		GuiFluidRenderer.renderGuiTank(multiblock.tanks.get(2), guiLeft + 71, guiTop + 20, zLevel, 32, 32);
		GL11.glColor4ub((byte) 255, (byte) 255, (byte) 255, (byte) 255);

		guiParticle.drawParticleStack(multiblock.beams.get(0).getParticleStack(), guiLeft + 27, guiTop + 28);
		guiParticle.drawParticleStack(multiblock.beams.get(1).getParticleStack(), guiLeft + 131, guiTop + 28);

	}

	@Override
	public void renderTooltips(int mouseX, int mouseY)
	{
		if (NCUtil.isModifierKeyDown())
			drawTooltip(clearAllInfo(), mouseX, mouseY, 130, 59, 18, 18);

		drawFluidTooltip(multiblock.tanks.get(2), mouseX, mouseY, 71, 20, 32, 32);

		drawTooltip(energyInfo(), mouseX, mouseY, 159, 7, 8, 76);
		drawTooltip(heatInfo(), mouseX, mouseY, 7, 7, 6, 76);
		drawTooltip(coolantInfo(), mouseX, mouseY, 14, 7, 6, 76);
		guiParticle.drawToolTipBoxwithFocus(multiblock.beams.get(0).getParticleStack(), guiLeft + 27, guiTop + 28,
				mouseX, mouseY);
		guiParticle.drawToolTipBoxwithFocus(multiblock.beams.get(1).getParticleStack(), guiLeft + 131, guiTop + 28,
				mouseX, mouseY);

	}

	public List<String> heatInfo()
	{
		List<String> info = new ArrayList<String>();

		info.add(TextFormatting.YELLOW + Lang.localise("gui.qmd.container.heat_stored",
				Units.getSIFormat(multiblock.heatBuffer.getHeatStored(), "H"),
				Units.getSIFormat(multiblock.heatBuffer.getHeatCapacity(), "H")));
		info.add(Lang.localise("gui.qmd.container.temperature", Units.getSIFormat(multiblock.getTemperature(), "K")));
		info.add(TextFormatting.RED + Lang.localise("gui.qmd.container.accelerator.heating",
				Units.getSIFormat(multiblock.heating + multiblock.getMaxExternalHeating(), "H/t")));
		info.add(TextFormatting.RED + Lang.localise("gui.qmd.container.accelerator.external_heating",
				Units.getSIFormat(multiblock.getMaxExternalHeating(), "H/t")));

		return info;
	}

	public List<String> energyInfo()
	{
		List<String> info = new ArrayList<String>();
		info.add(TextFormatting.YELLOW + Lang.localise("gui.qmd.container.energy_stored",
				Units.getSIFormat(multiblock.energyStorage.getEnergyStored(), "RF"),
				Units.getSIFormat(multiblock.energyStorage.getMaxEnergyStored(), "RF")));
		info.add(TextFormatting.RED + Lang.localise("gui.qmd.container.required_energy",
				Units.getSIFormat(multiblock.requiredEnergy, "RF/t")));
		return info;
	}

	public List<String> coolantInfo()
	{
		List<String> info = new ArrayList<String>();
		info.add(TextFormatting.YELLOW + Lang.localise("gui.qmd.container.accelerator.coolant_stored",
				Units.getSIFormat(multiblock.tanks.get(0).getFluidAmount(), -3, "B"),
				Units.getSIFormat(multiblock.tanks.get(0).getCapacity(), -3, "B")));
		info.add(TextFormatting.BLUE + Lang.localise("gui.qmd.container.accelerator.coolant_required",
				Units.getSIFormat(multiblock.maxCoolantIn, -6, "B/t")));
		info.add(TextFormatting.RED + Lang.localise("gui.qmd.container.accelerator.coolant_out",
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
		buttonList.add(new MultiblockButton.ClearAllMaterial(0, guiLeft + 130, guiTop + 59));
		// buttonList.add(new NCButton.EmptyTank(1, guiLeft + 81, guiTop + 37, 16, 16));
	}

	@Override
	protected void actionPerformed(GuiButton guiButton)
	{
		if (multiblock.WORLD.isRemote)
		{
			if (guiButton.id == 0 && NCUtil.isModifierKeyDown())
			{
				PacketHandler.instance.sendToServer(new ClearAllMaterialPacket(tile.getTilePos()));
			}
		}
	}

}