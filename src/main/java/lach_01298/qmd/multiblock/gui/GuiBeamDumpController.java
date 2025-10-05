package lach_01298.qmd.multiblock.gui;

import lach_01298.qmd.QMD;
import lach_01298.qmd.gui.GuiParticle;
import lach_01298.qmd.multiblock.network.ParticleChamberUpdatePacket;
import lach_01298.qmd.multiblock.network.QMDClearTankPacket;
import lach_01298.qmd.particleChamber.BeamDumpLogic;
import lach_01298.qmd.particleChamber.ParticleChamber;
import lach_01298.qmd.particleChamber.ParticleChamberLogic;
import lach_01298.qmd.particleChamber.tile.IParticleChamberPart;
import lach_01298.qmd.particleChamber.tile.TileBeamDumpController;
import lach_01298.qmd.util.Units;
import nc.gui.element.GuiFluidRenderer;
import nc.gui.element.NCButton;
import nc.gui.multiblock.controller.GuiLogicMultiblockController;
import nc.tile.TileContainerInfo;
import nc.util.Lang;
import nc.util.NCUtil;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class GuiBeamDumpController
		extends GuiLogicMultiblockController<ParticleChamber, ParticleChamberLogic, IParticleChamberPart, ParticleChamberUpdatePacket, TileBeamDumpController, TileContainerInfo<TileBeamDumpController>, BeamDumpLogic>
{

	protected final ResourceLocation gui_texture;

	private final GuiParticle guiParticle;

	public GuiBeamDumpController(Container inventory, EntityPlayer player, TileBeamDumpController controller, String textureLocation)
	{
		super(inventory, player, controller, textureLocation);
		gui_texture = new ResourceLocation(QMD.MOD_ID + ":textures/gui/beam_dump_controller.png");
		xSize = 137;
		ySize = 89;
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

		int offset = 8;
		int fontColor = multiblock.isChamberOn ? -1 : 15641088;
		String title = Lang.localize("gui.qmd.container.beam_dump_controller.name");
		fontRenderer.drawString(title, offset, 5, fontColor);

		// String efficiency =
		// Lang.localize("gui.qmd.container.target_chamber.efficiency",String.format("%.2f",
		// multiblock.efficiency*100));
		// fontRenderer.drawString(efficiency,offset, 60, fontColor);

	
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{

		GlStateManager.color(1F, 1F, 1F, 1F);
		mc.getTextureManager().bindTexture(getGuiTexture());
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		int power = (int) Math.round((double) multiblock.energyStorage.getEnergyStored()
				/ (double) multiblock.energyStorage.getMaxEnergyStored() * 74);

		drawTexturedModalRect(guiLeft + 122, guiTop + 79 - power, 137, 74 - power, 6, power);

		// input
		if (multiblock.beams.get(0).getParticleStack() != null)
		{
			drawTexturedModalRect(guiLeft + 20, guiTop + 42, 143, 0, 16, 6);
		}

		// draw progress bar
		int progress = Math
				.min((int) Math.round((double) getLogic().particleWorkDone / (double) getLogic().recipeParticleWork * 26), 26);
		drawTexturedModalRect(guiLeft + 54, guiTop + 38, 143, 6, progress, 14);

		GuiFluidRenderer.renderGuiTank(multiblock.tanks.get(1), guiLeft + 81, guiTop + 37, zLevel, 16, 16);
		GL11.glColor4ub((byte) 255, (byte) 255, (byte) 255, (byte) 255);
		guiParticle.drawParticleStack(multiblock.beams.get(0).getParticleStack(), guiLeft + 37, guiTop + 37);

	}

	@Override
	public void renderTooltips(int mouseX, int mouseY)
	{

		drawFluidTooltip(multiblock.tanks.get(1), mouseX, mouseY, 81, 37, 16, 16);
		drawTooltip(energyInfo(), mouseX, mouseY, 122, 4, 8, 76);
		guiParticle.drawToolTipBoxwithFocus(multiblock.beams.get(0).getParticleStack(), guiLeft + 37, guiTop + 37,
				mouseX, mouseY);

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
		buttonList.add(new NCButton.ClearTank(0, guiLeft + 81, guiTop + 37, 16, 16));
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
					new QMDClearTankPacket(tile.getTilePos(),1).sendToServer();
					break;
				}
			}
		}
	}

}
