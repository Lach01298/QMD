package lach_01298.qmd.multiblock.gui;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import lach_01298.qmd.QMD;
import lach_01298.qmd.gui.GuiParticle;
import lach_01298.qmd.multiblock.network.ClearTankPacket;
import lach_01298.qmd.multiblock.network.VacuumChamberUpdatePacket;
import lach_01298.qmd.network.QMDPacketHandler;
import lach_01298.qmd.util.Units;
import lach_01298.qmd.vacuumChamber.NucleosynthesisChamberLogic;
import lach_01298.qmd.vacuumChamber.VacuumChamber;
import lach_01298.qmd.vacuumChamber.VacuumChamberLogic;
import lach_01298.qmd.vacuumChamber.tile.IVacuumChamberPart;
import lach_01298.qmd.vacuumChamber.tile.TileNucleosynthesisChamberController;
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

public class GuiNucleosynthesisChamberController
		extends GuiLogicMultiblock<VacuumChamber, VacuumChamberLogic, IVacuumChamberPart, VacuumChamberUpdatePacket, TileNucleosynthesisChamberController, NucleosynthesisChamberLogic>
{

	protected final ResourceLocation gui_texture;

	private final GuiParticle guiParticle;

	public GuiNucleosynthesisChamberController(EntityPlayer player, TileNucleosynthesisChamberController controller)
	{
		super(player, controller);
		gui_texture = new ResourceLocation(QMD.MOD_ID + ":textures/gui/nucleosynthesis_chamber_controller.png");
		xSize = 176;
		ySize = 113;
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

		int offset = 28;
		int fontColor = multiblock.isChamberOn ? -1 : 15641088;
		String title = Lang.localise("gui.qmd.container.nucleosynthesis_chamber_controller.name");
		fontRenderer.drawString(title, offset, 5, fontColor);
		
		String efficiency = Lang.localise("gui.qmd.container.nucleosynthesis_chamber.efficiency",
				String.format("%.2f", getLogic().getCoolingEfficiency() * 100));
		fontRenderer.drawString(efficiency, offset, 90, fontColor);

		

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
		int power = (int) Math.round((double) multiblock.energyStorage.getEnergyStored()/ (double) multiblock.energyStorage.getMaxEnergyStored() * 74);
		drawTexturedModalRect(guiLeft + 8, guiTop + 82 - power, 176, 74 - power, 4, power);

		// heat bar
		int heat = (int) Math.round((double) multiblock.heatBuffer.getHeatStored() / (double) multiblock.heatBuffer.getHeatCapacity() * 74);
		drawTexturedModalRect(guiLeft + 15, guiTop + 82 - heat, 180, 74 - heat, 4, heat);

		// coolant bar
		int coolant = (int) Math.round((double) multiblock.tanks.get(0).getFluidAmount()
				/ (double) multiblock.tanks.get(0).getCapacity() * 74);
		drawTexturedModalRect(guiLeft + 22, guiTop + 82 - coolant, 184, 74 - coolant, 4, coolant);

		// casing heat bar
		int casingHeat = (int) Math.round((double) getLogic().casingHeatBuffer.getHeatStored() / (double) getLogic().casingHeatBuffer.getHeatCapacity() * 74);
		drawTexturedModalRect(guiLeft + 156, guiTop + 82 - casingHeat, 180, 74 - casingHeat, 4, casingHeat);

		// casing coolant bar
		int casingCoolant = (int) Math.round((double) multiblock.tanks.get(2).getFluidAmount()
				/ (double) multiblock.tanks.get(2).getCapacity() * 74);
		drawTexturedModalRect(guiLeft + 163, guiTop + 82 - casingCoolant, 184, 74 - casingCoolant, 4, casingCoolant);
		

		// particle input 
		if (multiblock.beams.get(0).getParticleStack() != null)
		{
			drawTexturedModalRect(guiLeft + 49, guiTop + 37, 188, 0, 59, 2);
		}

		int bar = (int) Math.min(Math.round((double) getLogic().particleWorkDone / (double) getLogic().recipeParticleWork * 62), 62);
		drawTexturedModalRect(guiLeft + 71, guiTop + 24, 188, 2, bar, 28);


		GuiFluidRenderer.renderGuiTank(multiblock.tanks.get(4), guiLeft + 54, guiTop + 18, zLevel, 16, 16);
		GuiFluidRenderer.renderGuiTank(multiblock.tanks.get(5), guiLeft + 54, guiTop + 42, zLevel, 16, 16);
		GuiFluidRenderer.renderGuiTank(multiblock.tanks.get(6), guiLeft + 134, guiTop + 18, zLevel, 16, 16);
		GuiFluidRenderer.renderGuiTank(multiblock.tanks.get(7), guiLeft + 134, guiTop + 42, zLevel, 16, 16);
		
		
		GL11.glColor4ub((byte) 255, (byte) 255, (byte) 255, (byte) 255);
		guiParticle.drawParticleStack(multiblock.beams.get(0).getParticleStack(), guiLeft + 32, guiTop + 30);
		

	}

	@Override
	public void renderTooltips(int mouseX, int mouseY)
	{
		if (NCUtil.isModifierKeyDown())
			drawTooltip(clearAllInfo(), mouseX, mouseY, 150, 90, 18, 18);

		drawFluidTooltip(multiblock.tanks.get(4), mouseX, mouseY, 54, 18, 16, 16);
		drawFluidTooltip(multiblock.tanks.get(5), mouseX, mouseY, 54, 42, 16, 16);
		drawFluidTooltip(multiblock.tanks.get(6), mouseX, mouseY, 134, 18, 16, 16);
		drawFluidTooltip(multiblock.tanks.get(7), mouseX, mouseY, 134, 42, 16, 16);
		
		

		drawTooltip(energyInfo(), mouseX, mouseY, 8, 7, 8, 76);
		drawTooltip(heatInfo(), mouseX, mouseY, 15, 7, 6, 76);
		drawTooltip(coolantInfo(), mouseX, mouseY, 22, 7, 6, 76);
		drawTooltip(casingHeatInfo(), mouseX, mouseY, 156, 7, 6, 76);
		drawTooltip(casingCoolantInfo(), mouseX, mouseY, 163, 7, 6, 76);
		
		guiParticle.drawToolTipBoxwithFocus(multiblock.beams.get(0).getParticleStack(), guiLeft + 32, guiTop + 30,
				mouseX, mouseY);

	}

	public List<String> heatInfo()
	{
	
		List<String> info = new ArrayList<String>();

		info.add(TextFormatting.YELLOW + Lang.localise("gui.qmd.container.cryo.heat_stored",
				Units.getSIFormat(multiblock.heatBuffer.getHeatStored(), "H"),
				Units.getSIFormat(multiblock.heatBuffer.getHeatCapacity(), "H")));
		info.add(Lang.localise("gui.qmd.container.temperature", Units.getSIFormat(multiblock.getTemperature(), "K")));
		info.add(Lang.localise("gui.qmd.container.max_temperature",
				Units.getSIFormat(multiblock.maxOperatingTemp, "K")));
		info.add(TextFormatting.RED + Lang.localise("gui.qmd.container.heating",
				Units.getSIFormat(multiblock.currentHeating, "H/t")));
		info.add(TextFormatting.RED + Lang.localise("gui.qmd.container.max_heating",
				Units.getSIFormat(multiblock.heating + multiblock.getMaxExternalHeating(), "H/t")));
		info.add(TextFormatting.RED + Lang.localise("gui.qmd.container.external_heating",
				Units.getSIFormat(multiblock.getMaxExternalHeating(), "H/t")));

		return info;
	}
	
	public List<String> casingHeatInfo()
	{
		List<String> info = new ArrayList<String>();
		
		info.add(TextFormatting.YELLOW + Lang.localise("gui.qmd.container.heat_stored",
				Units.getSIFormat(getLogic().casingHeatBuffer.getHeatStored(), "H"),
				Units.getSIFormat(getLogic().casingHeatBuffer.getHeatCapacity(), "H")));
		info.add(Lang.localise("gui.qmd.container.temperature", Units.getSIFormat(getLogic().getCasingTemperature(), "K")));
		info.add(TextFormatting.BLUE + Lang.localise("gui.qmd.container.cooling",
				Units.getSIFormat(-getLogic().casingCooling, "H/t")));
		info.add(TextFormatting.RED + Lang.localise("gui.qmd.container.heating",
				Units.getSIFormat(getLogic().casingHeating, "H/t")));

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
		info.add(TextFormatting.YELLOW + Lang.localise("gui.qmd.container.cryo.coolant_stored",
				Units.getSIFormat(multiblock.tanks.get(0).getFluidAmount(), -3, "B"),
				Units.getSIFormat(multiblock.tanks.get(0).getCapacity(), -3, "B")));
		info.add(TextFormatting.BLUE + Lang.localise("gui.qmd.container.max_coolant_in",
				Units.getSIFormat(multiblock.maxCoolantIn, -6, "B/t")));
		info.add(TextFormatting.RED + Lang.localise("gui.qmd.container.max_coolant_out",
				Units.getSIFormat(multiblock.maxCoolantOut, -6, "B/t")));
		return info;
	}
	
	public List<String> casingCoolantInfo()
	{
		List<String> info = new ArrayList<String>();
		info.add(TextFormatting.YELLOW + Lang.localise("gui.qmd.container.coolant_stored",
				Units.getSIFormat(multiblock.tanks.get(2).getFluidAmount(), -3, "B"),
				Units.getSIFormat(multiblock.tanks.get(2).getCapacity(), -3, "B")));
		info.add(TextFormatting.BLUE + Lang.localise("gui.qmd.container.max_coolant_in",
				Units.getSIFormat(getLogic().maxCasingCoolantIn, -6, "B/t")));
		info.add(TextFormatting.RED + Lang.localise("gui.qmd.container.max_coolant_out",
				Units.getSIFormat(getLogic().maxCasingCoolantOut, -6, "B/t")));

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
		buttonList.add(new MultiblockButton.ClearAllMaterial(0, guiLeft + 150, guiTop + 90));
		buttonList.add(new NCButton.EmptyTank(1, guiLeft + 54, guiTop + 18, 16, 16));
		buttonList.add(new NCButton.EmptyTank(2, guiLeft + 54, guiTop + 42, 16, 16));
		buttonList.add(new NCButton.EmptyTank(3, guiLeft + 134, guiTop + 18, 16, 16));
		buttonList.add(new NCButton.EmptyTank(4, guiLeft + 134, guiTop + 42, 16, 16));
		
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
					QMDPacketHandler.instance.sendToServer(new ClearTankPacket(tile.getTilePos(),4));
					break;
				case 2:
					QMDPacketHandler.instance.sendToServer(new ClearTankPacket(tile.getTilePos(),5));
					break;
				case 3:
					QMDPacketHandler.instance.sendToServer(new ClearTankPacket(tile.getTilePos(),6));
					break;
				case 4:
					QMDPacketHandler.instance.sendToServer(new ClearTankPacket(tile.getTilePos(),7));
					break;
				}
				
			}

		}
	}

}