package lach_01298.qmd.multiblock.gui;

import java.util.ArrayList;
import java.util.List;

import lach_01298.qmd.QMD;
import lach_01298.qmd.Units;
import lach_01298.qmd.gui.GuiParticle;
import lach_01298.qmd.multiblock.accelerator.Accelerator;
import lach_01298.qmd.multiblock.accelerator.LinearAcceleratorLogic;
import lach_01298.qmd.multiblock.accelerator.RingAcceleratorLogic;
import lach_01298.qmd.multiblock.particleChamber.ParticleChamber;
import lach_01298.qmd.multiblock.particleChamber.TargetChamberLogic;
import nc.multiblock.gui.GuiLogicMultiblockController;
import nc.multiblock.network.ClearAllMaterialPacket;
import nc.network.PacketHandler;
import nc.util.Lang;
import nc.util.NCUtil;
import nc.util.UnitHelper;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;

public class GuiDecayChamberController extends GuiLogicMultiblockController<ParticleChamber, TargetChamberLogic>
{

	protected final ResourceLocation gui_texture;

	

	public GuiDecayChamberController(ParticleChamber multiblock, BlockPos controllerPos, Container container)
	{
		super(multiblock, controllerPos, container);
		gui_texture = new ResourceLocation(QMD.MOD_ID + ":textures/gui/decay_chamber_controller.png");
		xSize = 176;
		ySize = 166;
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
		
		String efficiency = Lang.localise("gui.qmd.container.target_chamber.efficiency",String.format("%.2f", multiblock.efficiency*100));
		fontRenderer.drawString(efficiency,offset, 73, fontColor);
		
		
		
		
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

		int power = (int)Math.round((double)multiblock.energyStorage.getEnergyStored()/(double)multiblock.energyStorage.getMaxEnergyStored()*74);

		drawTexturedModalRect(guiLeft + 161, guiTop + 79-power, 176, 0, 6, power);
		
		//input
		if(multiblock.beams.get(0).getParticleStack() != null)
		{
			drawTexturedModalRect(guiLeft + 51, guiTop + 39, 182, 0, 16, 6);
		}
		
		
		
		//top output
		if(multiblock.beams.get(1).getParticleStack() != null)
		{
			drawTexturedModalRect(guiLeft + 84, guiTop + 18, 182, 6, 16, 16);
		}
		
		//middle output
		if(multiblock.beams.get(2).getParticleStack() != null)
		{
			drawTexturedModalRect(guiLeft + 85, guiTop + 40, 182, 38, 16, 4);
		}
		
		//bottom output
		if(multiblock.beams.get(3).getParticleStack() != null)
		{
			drawTexturedModalRect(guiLeft + 84, guiTop + 50, 182, 22, 16, 16);
		}
		
		

	}
	
	@Override
	public void renderTooltips(int mouseX, int mouseY) {
		if (NCUtil.isModifierKeyDown()) drawTooltip(clearAllFluidsInfo(), mouseX, mouseY, 153, 81, 18, 18);
		
		
		drawTooltip(energyInfo(), mouseX, mouseY, 160, 4, 8, 76);
		
		
	}
	

	
	
	public List<String> energyInfo() 
	{
		List<String> info = new ArrayList<String>();
		info.add(TextFormatting.YELLOW + Lang.localise("gui.qmd.container.energy_stored",Units.getSIFormat(multiblock.energyStorage.getEnergyStored(), "RF"),Units.getSIFormat(multiblock.energyStorage.getMaxEnergyStored(),"RF")));
		info.add(TextFormatting.RED + Lang.localise("gui.qmd.container.required_energy",Units.getSIFormat(multiblock.requiredEnergy, "RF/t")));
		return info;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) 
	{
		super.drawScreen(mouseX, mouseY, partialTicks);
		GuiParticle guiParticle = new GuiParticle(this);
		guiParticle.drawParticleStack(multiblock.beams.get(0).getParticleStack(), guiLeft+68, guiTop+34);
		guiParticle.drawParticleStack(multiblock.beams.get(1).getParticleStack(), guiLeft+101, guiTop+11);
		guiParticle.drawParticleStack(multiblock.beams.get(2).getParticleStack(), guiLeft+101, guiTop+34);
		guiParticle.drawParticleStack(multiblock.beams.get(3).getParticleStack(), guiLeft+101, guiTop+57);
		
		guiParticle.drawToolTipBoxwithLuminosity(multiblock.beams.get(0).getParticleStack(), guiLeft+68, guiTop+34, mouseX, mouseY);
		guiParticle.drawToolTipBoxwithLuminosity(multiblock.beams.get(1).getParticleStack(), guiLeft+101, guiTop+11, mouseX, mouseY);
		guiParticle.drawToolTipBoxwithLuminosity(multiblock.beams.get(2).getParticleStack(), guiLeft+101, guiTop+34, mouseX, mouseY);
		guiParticle.drawToolTipBoxwithLuminosity(multiblock.beams.get(3).getParticleStack(), guiLeft+101, guiTop+57, mouseX, mouseY);
		
		
		
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
				PacketHandler.instance.sendToServer(new ClearAllMaterialPacket(controllerPos));
			}
		}
	}

}