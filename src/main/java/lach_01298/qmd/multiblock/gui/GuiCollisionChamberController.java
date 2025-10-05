package lach_01298.qmd.multiblock.gui;

import lach_01298.qmd.QMD;
import lach_01298.qmd.gui.GuiParticle;
import lach_01298.qmd.multiblock.network.ParticleChamberUpdatePacket;
import lach_01298.qmd.particleChamber.CollisionChamberLogic;
import lach_01298.qmd.particleChamber.ParticleChamber;
import lach_01298.qmd.particleChamber.ParticleChamberLogic;
import lach_01298.qmd.particleChamber.tile.IParticleChamberPart;
import lach_01298.qmd.particleChamber.tile.TileCollisionChamberController;
import lach_01298.qmd.util.Units;
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

import java.util.ArrayList;
import java.util.List;

public class GuiCollisionChamberController
		extends GuiLogicMultiblockController<ParticleChamber, ParticleChamberLogic, IParticleChamberPart, ParticleChamberUpdatePacket, TileCollisionChamberController, TileContainerInfo<TileCollisionChamberController>, CollisionChamberLogic>
{

	protected final ResourceLocation gui_texture;

	private final GuiParticle guiParticle;

	public GuiCollisionChamberController(Container inventory, EntityPlayer player, TileCollisionChamberController controller, String textureLocation)
	{
		super(inventory, player, controller, textureLocation);
		gui_texture = new ResourceLocation(QMD.MOD_ID + ":textures/gui/collision_chamber_controller.png");
		xSize = 176;
		ySize = 130;
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
		String title = Lang.localize("gui.qmd.container.collision_chamber_controller.name");
		fontRenderer.drawString(title, xSize / 2 - fontRenderer.getStringWidth(title) / 2, 4, fontColor);

		String efficiency = Lang.localize("gui.qmd.container.particle_chamber.efficiency",
				String.format("%.2f", multiblock.efficiency * 100));
		fontRenderer.drawString(efficiency, offset, 95, fontColor);
		
		String length = Lang.localize("gui.qmd.container.particle_chamber.length", logic.getBeamLength());
		fontRenderer.drawString(length, offset, 115, fontColor);

		if (multiblock.beams.get(0).getParticleStack() != null && multiblock.beams.get(1).getParticleStack() != null)
		{
			String collsionEnergy = Lang.localize("gui.qmd.container.collison_chamber.energy", Units.getSIFormat(2 * Math.sqrt(multiblock.beams.get(0).getParticleStack().getMeanEnergy()* multiblock.beams.get(1).getParticleStack().getMeanEnergy()),3, "eV"));
			fontRenderer.drawString(collsionEnergy, offset, 105, fontColor);
		}

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

		// input 1
		if (multiblock.beams.get(0).getParticleStack() != null)
		{
			drawTexturedModalRect(guiLeft + 63, guiTop + 50, 182, 19, 16, 6);
		}

		// input 2
		if (multiblock.beams.get(1).getParticleStack() != null)
		{
			drawTexturedModalRect(guiLeft + 97, guiTop + 50, 216, 19, 16, 6);
		}

		// collision
		if (multiblock.beams.get(2).getParticleStack() != null || multiblock.beams.get(3).getParticleStack() != null
				|| multiblock.beams.get(4).getParticleStack() != null
				|| multiblock.beams.get(5).getParticleStack() != null)
		{
			drawTexturedModalRect(guiLeft + 79, guiTop + 52, 198, 21, 18, 2);
			drawTexturedModalRect(guiLeft + 87, guiTop + 50, 206, 19, 2, 6);
			drawTexturedModalRect(guiLeft + 86, guiTop + 51, 205, 20, 4, 4);
		}

		// output 1
		if (multiblock.beams.get(2).getParticleStack() != null)
		{
			drawTexturedModalRect(guiLeft + 66, guiTop + 31, 185, 0, 21, 21);
		}

		// output 2
		if (multiblock.beams.get(3).getParticleStack() != null)
		{
			drawTexturedModalRect(guiLeft + 89, guiTop + 31, 208, 0, 21, 21);
		}

		// output 3
		if (multiblock.beams.get(4).getParticleStack() != null)
		{
			drawTexturedModalRect(guiLeft + 66, guiTop + 54, 185, 23, 21, 21);
		}
		// output 4
		if (multiblock.beams.get(5).getParticleStack() != null)
		{
			drawTexturedModalRect(guiLeft + 89, guiTop + 54, 208, 23, 21, 21);
		}

		guiParticle.drawParticleStack(multiblock.beams.get(0).getParticleStack(), guiLeft + 46, guiTop + 45);
		guiParticle.drawParticleStack(multiblock.beams.get(1).getParticleStack(), guiLeft + 114, guiTop + 45);

		guiParticle.drawParticleStack(multiblock.beams.get(2).getParticleStack(), guiLeft + 49, guiTop + 14);
		guiParticle.drawParticleStack(multiblock.beams.get(3).getParticleStack(), guiLeft + 111, guiTop + 14);
		guiParticle.drawParticleStack(multiblock.beams.get(4).getParticleStack(), guiLeft + 49, guiTop + 76);
		guiParticle.drawParticleStack(multiblock.beams.get(5).getParticleStack(), guiLeft + 111, guiTop + 76);

	}

	@Override
	public void renderTooltips(int mouseX, int mouseY)
	{
		// if (NCUtil.isModifierKeyDown()) drawTooltip(clearAllInfo(), mouseX, mouseY,
		// 153, 81, 18, 18);

		drawTooltip(energyInfo(), mouseX, mouseY, 160, 4, 8, 76);
		guiParticle.drawToolTipBoxwithFocus(multiblock.beams.get(0).getParticleStack(), guiLeft + 46, guiTop + 45,
				mouseX, mouseY);
		guiParticle.drawToolTipBoxwithFocus(multiblock.beams.get(1).getParticleStack(), guiLeft + 114, guiTop + 45,
				mouseX, mouseY);

		guiParticle.drawToolTipBoxwithFocus(multiblock.beams.get(2).getParticleStack(), guiLeft + 49, guiTop + 14,
				mouseX, mouseY);
		guiParticle.drawToolTipBoxwithFocus(multiblock.beams.get(3).getParticleStack(), guiLeft + 111, guiTop + 14,
				mouseX, mouseY);
		guiParticle.drawToolTipBoxwithFocus(multiblock.beams.get(4).getParticleStack(), guiLeft + 49, guiTop + 76,
				mouseX, mouseY);
		guiParticle.drawToolTipBoxwithFocus(multiblock.beams.get(5).getParticleStack(), guiLeft + 111, guiTop + 76,
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
		GuiParticle guiParticle = new GuiParticle(this);

		renderTooltips(mouseX, mouseY);
	}

	@Override
	public void initGui()
	{
		super.initGui();

	}

	@Override
	protected void actionPerformed(GuiButton guiButton)
	{
		if (multiblock.WORLD.isRemote)
		{
			if (guiButton.id == 0 && NCUtil.isModifierKeyDown())
			{
				// new ClearAllMaterialPacket(tile.getTilePos()).sendToServer();
			}
		}
	}

}
