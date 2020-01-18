package lach_01298.qmd.gui;

import java.util.ArrayList;
import java.util.List;

import lach_01298.qmd.QMD;
import lach_01298.qmd.particle.ParticleStack;
import nc.gui.NCGui;
import nc.util.Lang;
import nc.util.UnitHelper;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

public class GuiParticle 
{
	private int width = 16;
	private int height = 16;
	private final GuiContainer screen;
	
	
	public GuiParticle(GuiContainer screen)
	{
		this.screen = screen;
	}
	
	
	public void drawParticleStack(ParticleStack particleStack, int x, int y,int mouseX, int mouseY)
	{
		if(particleStack == null)
		{
			return;
		}
		if(particleStack.getParticle() == null)
		{
			return;
		}
		
		
		GlStateManager.disableLighting();
		GlStateManager.color(1F, 1F, 1F, 1F);
		screen.mc.getTextureManager().bindTexture(particleStack.getParticle().getTexture());
		screen.drawModalRectWithCustomSizedTexture(x, y, 0, 0, width, height,width,height);

		if (mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height)
		{
			
			drawToolTip(particleStack, mouseX, mouseY, false);
		}

		
	}


	private void drawToolTip(ParticleStack stack,int mouseX, int mouseY, boolean showLuminosity)
	{
		List<String> text = new ArrayList<String>();
		text.add(TextFormatting.WHITE + Lang.localise(stack.getParticle().getUnlocalizedName()));
		text.add(TextFormatting.GRAY + Lang.localise("gui.qmd.particlestack.mean_energy",UnitHelper.prefix(stack.getMeanEnergy()*1000,4,"eV")));
		text.add(TextFormatting.GRAY + Lang.localise("gui.qmd.particlestack.amount",UnitHelper.prefix(stack.getAmount(),4,"pu")));
		if(showLuminosity)
		{
			text.add(TextFormatting.GRAY + Lang.localise("gui.qmd.particlestack.luminosity",UnitHelper.prefix(stack.getLuminosity(),4,"lu")));
		}
		screen.drawHoveringText(text, mouseX, mouseY);
		
	}
	
	public void drawParticleStackWithLuminosity(ParticleStack particleStack, int x, int y,int mouseX, int mouseY)
	{
		if(particleStack == null)
		{
			return;
		}
		if(particleStack.getParticle() == null)
		{
			return;
		}
		
		
		GlStateManager.disableLighting();
		GlStateManager.color(1F, 1F, 1F, 1F);
		screen.mc.getTextureManager().bindTexture(particleStack.getParticle().getTexture());
		screen.drawModalRectWithCustomSizedTexture(x, y, 0, 0, width, height,width,height);

		if (mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height)
		{
			
			drawToolTip(particleStack, mouseX, mouseY,true);
		}

		
	}
	

	
	
	
	
	
	
}
