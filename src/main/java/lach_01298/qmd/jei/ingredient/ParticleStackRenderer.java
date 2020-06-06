package lach_01298.qmd.jei.ingredient;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import lach_01298.qmd.Units;
import lach_01298.qmd.particle.Particle;
import lach_01298.qmd.particle.ParticleStack;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.ingredients.IIngredientRenderer;
import nc.util.Lang;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.util.text.TextFormatting;

public class ParticleStackRenderer  implements IIngredientRenderer<ParticleStack> 
{

	private static final int TEX_WIDTH = 16;
	private static final int TEX_HEIGHT = 16;
	
	private final int amount;
	private final long energy;
	private final double focus;
	private final int width;
	private final int height;
	
	@Nullable
	private final IDrawable overlay;
	
	public ParticleStackRenderer() 
	{
		this(0,0,0, TEX_WIDTH, TEX_HEIGHT, null);
	}
	
	public ParticleStackRenderer(int amount, long energy, double focus, int width, int height, @Nullable IDrawable overlay) 
	{
		this.amount = amount;
		this.energy = energy;
		this.focus = focus;
		//this.tooltipMode = tooltipMode;
		this.width = width;
		this.height = height;
		this.overlay = overlay;
	}

	
	
	
	@Override
	public void render(Minecraft minecraft, final int xPosition, final int yPosition, @Nullable ParticleStack particleStack) 
	{
		GlStateManager.enableBlend();
		GlStateManager.enableAlpha();

		drawParticle(minecraft, xPosition, yPosition, particleStack);

		GlStateManager.color(1, 1, 1, 1);

		if (overlay != null) {
			GlStateManager.pushMatrix();
			GlStateManager.translate(0, 0, 200);
			overlay.draw(minecraft, xPosition, yPosition);
			GlStateManager.popMatrix();
		}

		GlStateManager.disableAlpha();
		GlStateManager.disableBlend();
	}
	
	@Override
	public List<String> getTooltip(Minecraft minecraft, ParticleStack ingredient, ITooltipFlag tooltipFlag) 
	{
		List<String> list = new ArrayList<>();
		list.add(Lang.localise(ingredient.getParticle().getUnlocalizedName()));
		list.add(TextFormatting.GRAY + Lang.localise("gui.qmd.particlestack.amount",Units.getSIFormat(ingredient.getAmount(),"pu")));
		list.add(TextFormatting.GRAY + Lang.localise("gui.qmd.particlestack.mean_energy",Units.getSIFormat(ingredient.getMeanEnergy(),3,"eV")));
		list.add(TextFormatting.GRAY + Lang.localise("gui.qmd.particlestack.focus",Units.getSIFormat(ingredient.getFocus(),"")));
		
		
		return list;
	}
	
	private void drawParticle(Minecraft minecraft, final int xPosition, final int yPosition,
			@Nullable ParticleStack particleStack)
	{
		if (particleStack == null)
		{
			return;
		}
		Particle particle = particleStack.getParticle();
		if (particle == null)
		{
			return;
		}

	
		minecraft.renderEngine.bindTexture(particleStack.getParticle().getTexture());
		double zLevel = 100;
		double width = 16;
		double uMin = 0;
		double uMax = 1;
		double vMin = 0;
		double vMax = 1;
		
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferBuilder = tessellator.getBuffer();
		bufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
		bufferBuilder.pos(xPosition, yPosition + width, zLevel).tex(uMin, vMax).endVertex();
		bufferBuilder.pos(xPosition + width, yPosition + 16, zLevel).tex(uMax, vMax).endVertex();
		bufferBuilder.pos(xPosition + width, yPosition, zLevel).tex(uMax, vMin).endVertex();
		bufferBuilder.pos(xPosition, yPosition, zLevel).tex(uMin, vMin).endVertex();
		tessellator.draw();

	}

}
