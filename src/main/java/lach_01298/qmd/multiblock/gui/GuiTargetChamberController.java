package lach_01298.qmd.multiblock.gui;

import lach_01298.qmd.QMD;
import lach_01298.qmd.gui.GuiParticle;
import lach_01298.qmd.multiblock.network.*;
import lach_01298.qmd.particleChamber.*;
import lach_01298.qmd.particleChamber.tile.*;
import lach_01298.qmd.util.Units;
import nc.gui.element.*;
import nc.gui.multiblock.controller.GuiLogicMultiblockController;
import nc.network.multiblock.ClearAllMaterialPacket;
import nc.tile.TileContainerInfo;
import nc.util.*;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import org.lwjgl.opengl.GL11;

import java.util.*;

public class GuiTargetChamberController
		extends GuiLogicMultiblockController<ParticleChamber, ParticleChamberLogic, IParticleChamberPart, ParticleChamberUpdatePacket, TileTargetChamberController, TileContainerInfo<TileTargetChamberController>, TargetChamberLogic>
{

	protected final ResourceLocation gui_texture;

	private final GuiParticle guiParticle;

	public GuiTargetChamberController(Container inventory, EntityPlayer player, TileTargetChamberController controller, String textureLocation)
	{
		super(inventory, player, controller, textureLocation);
		gui_texture = new ResourceLocation(QMD.MOD_ID + ":textures/gui/target_chamber_controller.png");
		xSize = 176;
		ySize = 200;
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
		String title = Lang.localize("gui.qmd.container.target_chamber_controller.name");
		fontRenderer.drawString(title, offset, 5, fontColor);

		String efficiency = Lang.localize("gui.qmd.container.particle_chamber.efficiency",
				String.format("%.2f", multiblock.efficiency * 100));
		fontRenderer.drawString(efficiency, offset, 98, fontColor);
		
		String length = Lang.localize("gui.qmd.container.particle_chamber.length", logic.getBeamLength());
		fontRenderer.drawString(length, offset, 108, fontColor);

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

		drawTexturedModalRect(guiLeft + 161, guiTop + 87 - power, 176, 74 - power, 6, power);

		// input
		if (multiblock.beams.get(0).getParticleStack() != null)
		{
			drawTexturedModalRect(guiLeft + 35, guiTop + 51, 182, 12, 16, 6);
		}

		// top output
		if (multiblock.beams.get(1).getParticleStack() != null)
		{
			drawTexturedModalRect(guiLeft + 69, guiTop + 22, 182, 18, 16, 16);
		}

		// middle output
		if (multiblock.beams.get(2).getParticleStack() != null)
		{
			drawTexturedModalRect(guiLeft + 112, guiTop + 52, 182, 50, 16, 4);
		}

		// bottom output
		if (multiblock.beams.get(3).getParticleStack() != null)
		{
			drawTexturedModalRect(guiLeft + 69, guiTop + 71, 182, 34, 16, 16);
		}

		// draw progress bar
		int progress = Math
				.min((int) Math.round((double) getLogic().particleWorkDone / (double) getLogic().recipeParticleWork * 21), 21);
		drawTexturedModalRect(guiLeft + 71, guiTop + 48, 182, 0, progress, 12);

		
		GuiFluidRenderer.renderGuiTank(multiblock.tanks.get(0), guiLeft + 53, guiTop + 55, zLevel, 16, 16);
		GuiFluidRenderer.renderGuiTank(multiblock.tanks.get(1), guiLeft + 94, guiTop + 55, zLevel, 16, 16);
		GL11.glColor4ub((byte) 255, (byte) 255, (byte) 255, (byte) 255);
		
		
		
		guiParticle.drawParticleStack(multiblock.beams.get(0).getParticleStack(), guiLeft + 18, guiTop + 46);
		guiParticle.drawParticleStack(multiblock.beams.get(1).getParticleStack(), guiLeft + 86, guiTop + 15);
		guiParticle.drawParticleStack(multiblock.beams.get(2).getParticleStack(), guiLeft + 129, guiTop + 46);
		guiParticle.drawParticleStack(multiblock.beams.get(3).getParticleStack(), guiLeft + 86, guiTop + 78);
	}

	@Override
	public void renderTooltips(int mouseX, int mouseY)
	{
		if (NCUtil.isModifierKeyDown())
			drawTooltip(clearAllInfo(), mouseX, mouseY, 128, 70, 18, 18);

		drawFluidTooltip(multiblock.tanks.get(0), mouseX, mouseY, 53, 55, 16, 16);
		drawFluidTooltip(multiblock.tanks.get(1), mouseX, mouseY, 94, 55, 16, 16);
		
		drawTooltip(energyInfo(), mouseX, mouseY, 160, 12, 8, 76);

		guiParticle.drawToolTipBoxwithFocus(multiblock.beams.get(0).getParticleStack(), guiLeft + 18, guiTop + 46,
				mouseX, mouseY);
		guiParticle.drawToolTipBoxwithFocus(multiblock.beams.get(1).getParticleStack(), guiLeft + 86, guiTop + 15,
				mouseX, mouseY);
		guiParticle.drawToolTipBoxwithFocus(multiblock.beams.get(2).getParticleStack(), guiLeft + 129, guiTop + 46,
				mouseX, mouseY);
		guiParticle.drawToolTipBoxwithFocus(multiblock.beams.get(3).getParticleStack(), guiLeft + 86, guiTop + 78,
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
		buttonList.add(new MultiblockButton.ClearAllMaterial(0, guiLeft + 128, guiTop + 70));
		buttonList.add(new NCButton.ClearTank(1, guiLeft + 53, guiTop + 55, 16, 16));
		buttonList.add(new NCButton.ClearTank(2, guiLeft + 94, guiTop + 55, 16, 16));
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
					new QMDClearTankPacket(tile.getTilePos(),0).sendToServer();
					break;
				case 2:
					new QMDClearTankPacket(tile.getTilePos(),1).sendToServer();
					break;
				}
				
			}

		}
	}

}
