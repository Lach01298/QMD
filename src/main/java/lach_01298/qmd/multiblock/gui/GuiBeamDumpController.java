package lach_01298.qmd.multiblock.gui;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import lach_01298.qmd.QMD;
import lach_01298.qmd.gui.GuiParticle;
import lach_01298.qmd.multiblock.network.ClearTankPacket;
import lach_01298.qmd.network.QMDPacketHandler;
import lach_01298.qmd.particleChamber.BeamDumpLogic;
import lach_01298.qmd.particleChamber.ParticleChamber;
import lach_01298.qmd.particleChamber.tile.IParticleChamberController;
import lach_01298.qmd.util.Units;
import nc.gui.element.GuiFluidRenderer;
import nc.gui.element.NCButton;
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

public class GuiBeamDumpController
		extends GuiLogicMultiblock<ParticleChamber, BeamDumpLogic, IParticleChamberController>
{

	protected final ResourceLocation gui_texture;

	private final GuiParticle guiParticle;

	public GuiBeamDumpController(EntityPlayer player, IParticleChamberController controller)
	{
		super(player, controller);
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
		String title = Lang.localise("gui.qmd.container.beam_dump_controller.name");
		fontRenderer.drawString(title, offset, 5, fontColor);

		// String efficiency =
		// Lang.localise("gui.qmd.container.target_chamber.efficiency",String.format("%.2f",
		// multiblock.efficiency*100));
		// fontRenderer.drawString(efficiency,offset, 60, fontColor);

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
				.min((int) Math.round((double) logic.particleWorkDone / (double) logic.recipeParticleWork * 26), 26);
		drawTexturedModalRect(guiLeft + 54, guiTop + 38, 143, 6, progress, 14);

		GuiFluidRenderer.renderGuiTank(multiblock.tanks.get(1), guiLeft + 81, guiTop + 37, zLevel, 16, 16);
		GL11.glColor4ub((byte) 255, (byte) 255, (byte) 255, (byte) 255);
		guiParticle.drawParticleStack(multiblock.beams.get(0).getParticleStack(), guiLeft + 37, guiTop + 37);

	}

	@Override
	public void renderTooltips(int mouseX, int mouseY)
	{
		if (NCUtil.isModifierKeyDown())
			drawTooltip(clearAllInfo(), mouseX, mouseY, 60, 80, 18, 18);

		drawFluidTooltip(multiblock.tanks.get(1), mouseX, mouseY, 81, 37, 16, 16);
		drawTooltip(energyInfo(), mouseX, mouseY, 122, 4, 8, 76);
		guiParticle.drawToolTipBoxwithFocus(multiblock.beams.get(0).getParticleStack(), guiLeft + 37, guiTop + 37,
				mouseX, mouseY);

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
		buttonList.add(new MultiblockButton.ClearAllMaterial(0, guiLeft + 80, guiTop + 60));
		buttonList.add(new NCButton.EmptyTank(1, guiLeft + 81, guiTop + 37, 16, 16));
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
					PacketHandler.instance.sendToServer(new ClearAllMaterialPacket(tile.getTilePos()));
					break;
				case 1:
					QMDPacketHandler.instance.sendToServer(new ClearTankPacket(tile.getTilePos(),1));
					break;
				}
			}
		}
	}

}