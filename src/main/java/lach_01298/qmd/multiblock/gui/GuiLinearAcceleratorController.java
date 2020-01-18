package lach_01298.qmd.multiblock.gui;

import java.util.ArrayList;
import java.util.List;

import lach_01298.qmd.QMD;
import lach_01298.qmd.Util;
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
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) 
	{
		
		int offset = 50;
		int fontColor = multiblock.isAcceleratorOn ? -1 : 15641088;
		String title = Lang.localise("gui.qmd.container.linear_accelerator_controller.name");
		fontRenderer.drawString(title,offset, 7, fontColor);
		
		String cavitys = Lang.localise("gui.qmd.container.accelerator.cavitys")+ multiblock.RFCavityNumber;
		fontRenderer.drawString(cavitys,offset, 25, fontColor);
		
		String voltage =Lang.localise("gui.qmd.container.accelerator.voltage")+ UnitHelper.prefix(multiblock.acceleratingVoltage*1000, 5, "V");
		fontRenderer.drawString(voltage,offset, 35, fontColor);
		
		String quadrupoles = Lang.localise("gui.qmd.container.accelerator.quadrupoles") +
				 multiblock.quadrupoleNumber +" "+Lang.localise("gui.qmd.container.accelerator.magnet_strength") + multiblock.quadrupoleStrength;
		fontRenderer.drawString(quadrupoles,offset, 50, fontColor);
		
		String particle;
		String particle2;
		if(multiblock.beams.get(0).getParticleStack() != null)
		{
			if(multiblock.beams.get(0).getParticleStack().getParticle() != null)
			{
				particle =Lang.localise("gui.qmd.container.accelerator.beam") +Lang.localise("qmd.particle."+ multiblock.beams.get(0).getParticleStack().getParticle().getName()+".name"); 	
				particle2=Lang.localise("gui.qmd.container.beam.stats",UnitHelper.prefix(multiblock.beams.get(0).getParticleStack().getMeanEnergy()*1000, 5, "eV"),multiblock.beams.get(0).getParticleStack().getLuminosity());
			}
			else
			{
				particle =Lang.localise("gui.qmd.container.accelerator.beam") +Lang.localise("qmd.particle.none.name"); 
				particle2=Lang.localise("gui.qmd.container.beam.stats",UnitHelper.prefix(0, 5, "eV"),0);
			}
		}
		else
		{
			particle =Lang.localise("gui.qmd.container.accelerator.beam") +Lang.localise("qmd.particle.none.name"); 
			particle2=Lang.localise("gui.qmd.container.beam.stats",UnitHelper.prefix(0, 5, "eV"),0);
		}
		
		fontRenderer.drawString(particle,offset, 70, fontColor);
		
		fontRenderer.drawString(particle2,offset, 80, fontColor);
		
		String temperature=Lang.localise("gui.qmd.container.temperature",multiblock.getTemperature());
		fontRenderer.drawString(temperature,offset, 90, fontColor);
		
		
		
		
		if (!NCUtil.isModifierKeyDown()) 
		{
			//System.out.println("pop "+ multiblock.beam.getMeanEnergy());
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
		info.add(TextFormatting.YELLOW + Lang.localise("gui.qmd.container.accelerator.heat_stored") + " " + TextFormatting.WHITE + UnitHelper.prefix(multiblock.heatBuffer.getHeatStored(), multiblock.heatBuffer.getHeatCapacity(), 6, "H"));
		info.add(TextFormatting.BLUE + Lang.localise("gui.qmd.container.accelerator.cooling") + " " + TextFormatting.WHITE + UnitHelper.prefix(-multiblock.cooling, 6, "H/t"));
		info.add(TextFormatting.RED + Lang.localise("gui.qmd.container.accelerator.heating") + " " + TextFormatting.WHITE + UnitHelper.prefix(multiblock.rawHeating+multiblock.getMaxExternalHeating(), 6, "H/t"));
		info.add(TextFormatting.RED + Lang.localise("gui.qmd.container.accelerator.external_heating") + " " + TextFormatting.WHITE + UnitHelper.prefix(multiblock.getMaxExternalHeating(), 6, "H/t"));
		return info;
	}
	
	public List<String> energyInfo() 
	{
		List<String> info = new ArrayList<String>();
		info.add(TextFormatting.YELLOW + Lang.localise("gui.qmd.container.accelerator.energy_stored") + " " + TextFormatting.WHITE + UnitHelper.prefix(multiblock.energyStorage.getEnergyStored(), multiblock.energyStorage.getMaxEnergyStored(), 4, "RF"));
		info.add(TextFormatting.RED + Lang.localise("gui.qmd.container.accelerator.required_energy",UnitHelper.prefix(multiblock.requiredEnergy, 4, "RF/t")) +
				Lang.localise("gui.qmd.container.accelerator.efficiency",String.format("%.2f", (2-multiblock.efficiency)*100)));
		return info;
	}
	
	
	public List<String> coolantInfo() 
	{
		List<String> info = new ArrayList<String>();
		info.add(TextFormatting.YELLOW + Lang.localise("gui.qmd.container.accelerator.coolant_stored") + " " + TextFormatting.WHITE + UnitHelper.prefix((multiblock.tanks.get(0).getFluidAmount()), multiblock.tanks.get(0).getCapacity(), 8, "mB"));
		info.add(TextFormatting.BLUE + Lang.localise("gui.qmd.container.accelerator.coolant_required") + " " + TextFormatting.WHITE + multiblock.maxCoolantIn + "mB/t");
		info.add(TextFormatting.RED + Lang.localise("gui.qmd.container.accelerator.coolant_out") + " " + TextFormatting.WHITE + multiblock.maxCoolantOut + " mB/t");
	
		
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
