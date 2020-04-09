package lach_01298.qmd.multiblock.gui;

import java.util.ArrayList;
import java.util.List;

import lach_01298.qmd.QMD;
import lach_01298.qmd.Units;
import lach_01298.qmd.Util;
import lach_01298.qmd.gui.GuiParticle;
import lach_01298.qmd.multiblock.accelerator.Accelerator;
import lach_01298.qmd.multiblock.accelerator.LinearAcceleratorLogic;
import lach_01298.qmd.multiblock.accelerator.RingAcceleratorLogic;
import nc.multiblock.gui.GuiLogicMultiblockController;
import nc.multiblock.gui.GuiMultiblockController;
import nc.multiblock.gui.element.MultiblockButton;
import nc.multiblock.network.ClearAllMaterialPacket;
import nc.network.PacketHandler;
import nc.util.Lang;
import nc.util.NCMath;
import nc.util.NCUtil;
import nc.util.StringHelper;
import nc.util.UnitHelper;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;

public class GuiLinearAcceleratorController extends GuiLogicMultiblockController<Accelerator, LinearAcceleratorLogic>
{

	protected final ResourceLocation gui_texture;

	

	public GuiLinearAcceleratorController(Accelerator multiblock, BlockPos controllerPos, Container container)
	{
		super(multiblock, controllerPos, container);
		gui_texture = new ResourceLocation(QMD.MOD_ID + ":textures/gui/accelerator_controller.png");
		xSize = 196;
		ySize = 109;
	}

	@Override
	protected ResourceLocation getGuiTexture()
	{
		return gui_texture;
	}

	

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) 
	{
		super.drawScreen(mouseX, mouseY, partialTicks);
		GuiParticle guiParticle = new GuiParticle(this);
		guiParticle.drawParticleStack(multiblock.beams.get(1).getParticleStack(), guiLeft+40, guiTop+21);
		
		guiParticle.drawToolTipBoxwithLuminosity(multiblock.beams.get(1).getParticleStack(), guiLeft+40, guiTop+21, mouseX, mouseY);

		renderTooltips(mouseX, mouseY);
	}
	
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) 
	{
		int offset = 40;
		
		
		int fontColor = multiblock.isAcceleratorOn ? -1 : 15641088;
		String title = Lang.localise("gui.qmd.container.linear_accelerator_controller.name");
		fontRenderer.drawString(title,offset, 5, fontColor);
		
		String length = Lang.localise("gui.qmd.container.accelerator.length", logic.getLength());
		fontRenderer.drawString(length,offset+25, 25, fontColor);
		
		String cavitys = Lang.localise("gui.qmd.container.accelerator.cavitys",multiblock.RFCavityNumber, Units.getSIFormat(multiblock.acceleratingVoltage, 3, "V")) ;
		fontRenderer.drawString(cavitys,offset, 50, fontColor);
		
		String quadrupoles = Lang.localise("gui.qmd.container.accelerator.quadrupoles", multiblock.quadrupoleNumber,  Units.getSIFormat(multiblock.quadrupoleStrength,""));
		fontRenderer.drawString(quadrupoles,offset, 60, fontColor);
		
		
		String temperature=Lang.localise("gui.qmd.container.temperature",Units.getSIFormat(multiblock.getTemperature(),"K"));
		fontRenderer.drawString(temperature,offset, 70, fontColor);
		
		if(multiblock.errorCode != Accelerator.errorCode_Nothing)
		{
			String error=Lang.localise("gui.qmd.container.accelerator.error."+multiblock.errorCode);
			fontRenderer.drawString(error,offset, 80, 15641088);
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

		int power = (int)Math.round((double)multiblock.energyStorage.getEnergyStored()/(double)multiblock.energyStorage.getMaxEnergyStored()*95);

		drawTexturedModalRect(guiLeft + 8, guiTop + 101-power, 196, 0, 6, power);
		
		int heat = (int)Math.round((double)multiblock.heatBuffer.getHeatStored()/(double)multiblock.heatBuffer.getHeatCapacity()*95);
		drawTexturedModalRect(guiLeft + 18, guiTop + 101- heat, 202, 0, 6, heat);
		
		int coolant = (int)Math.round((double)multiblock.tanks.get(0).getFluidAmount()/(double)multiblock.tanks.get(0).getCapacity()*95);
		drawTexturedModalRect(guiLeft + 28, guiTop + 101-coolant, 208, 0, 6, coolant);
		

	}
	
	@Override
	public void renderTooltips(int mouseX, int mouseY) {
		if (NCUtil.isModifierKeyDown()) drawTooltip(clearAllFluidsInfo(), mouseX, mouseY, 153, 81, 18, 18);
		
		
		drawTooltip(energyInfo(), mouseX, mouseY, 8, 5, 8, 96);
		drawTooltip(heatInfo(), mouseX, mouseY, 18, 5, 8, 96);
		drawTooltip(coolantInfo(), mouseX, mouseY, 28, 5, 8, 96);
		
	}
	

	public List<String> heatInfo() 
	{
		List<String> info = new ArrayList<String>();
		info.add(TextFormatting.YELLOW + Lang.localise("gui.qmd.container.heat_stored",Units.getSIFormat(multiblock.heatBuffer.getHeatStored(), "H"),Units.getSIFormat(multiblock.heatBuffer.getHeatCapacity(), "H")));
		info.add(TextFormatting.BLUE + Lang.localise("gui.qmd.container.accelerator.cooling",Units.getSIFormat(-multiblock.cooling, "H/t")));
		info.add(TextFormatting.RED + Lang.localise("gui.qmd.container.accelerator.heating",Units.getSIFormat(multiblock.rawHeating+multiblock.getMaxExternalHeating(),"H/t")));
		info.add(TextFormatting.RED + Lang.localise("gui.qmd.container.accelerator.external_heating",Units.getSIFormat( multiblock.getMaxExternalHeating(), "H/t")));
		return info;
	}
	
	public List<String> energyInfo() 
	{
		List<String> info = new ArrayList<String>();
		info.add(TextFormatting.YELLOW + Lang.localise("gui.qmd.container.energy_stored", Units.getSIFormat(multiblock.energyStorage.getEnergyStored(), "RF"),Units.getSIFormat(multiblock.energyStorage.getMaxEnergyStored(), "RF")));
		info.add(TextFormatting.RED + Lang.localise("gui.qmd.container.required_energy",Units.getSIFormat(multiblock.requiredEnergy, "RF/t")) +
				Lang.localise("gui.qmd.container.accelerator.efficiency",String.format("%.2f", (1/multiblock.efficiency)*100)));
		return info;
	}
	
	
	public List<String> coolantInfo() 
	{
		List<String> info = new ArrayList<String>();
		info.add(TextFormatting.YELLOW + Lang.localise("gui.qmd.container.accelerator.coolant_stored",Units.getSIFormat(multiblock.tanks.get(0).getFluidAmount(), -3,"B"),Units.getSIFormat(multiblock.tanks.get(0).getCapacity(), -3,"B")));
		info.add(TextFormatting.BLUE + Lang.localise("gui.qmd.container.accelerator.coolant_required",Units.getSIFormat(multiblock.maxCoolantIn , -3,"B/t")));
		info.add(TextFormatting.RED + Lang.localise("gui.qmd.container.accelerator.coolant_out",Units.getSIFormat(multiblock.maxCoolantOut, -3,"B/t")));
	

		return info;
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
				PacketHandler.instance.sendToServer(new ClearAllMaterialPacket(controllerPos));
			}
		}
	}

}
