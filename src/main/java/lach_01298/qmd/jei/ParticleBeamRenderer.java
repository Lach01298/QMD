package lach_01298.qmd.jei;

import javax.annotation.Nullable;

import lach_01298.qmd.particle.Particle;
import lach_01298.qmd.particle.ParticleBeam;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.ingredients.IIngredientRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class ParticleBeamRenderer  implements IIngredientRenderer<ParticleBeam> 
{

	private static final int TEX_WIDTH = 16;
	private static final int TEX_HEIGHT = 16;
	
	private final int luminosity;
	private final int energy;
	private final int width;
	private final int height;
	
	@Nullable
	private final IDrawable overlay;
	
	public ParticleBeamRenderer() 
	{
		this(0,0, TEX_WIDTH, TEX_HEIGHT, null);
	}
	
	public ParticleBeamRenderer(int luminosity, int energy, int width, int height, @Nullable IDrawable overlay) 
	{
		this.luminosity = luminosity;
		this.energy = energy;
		//this.tooltipMode = tooltipMode;
		this.width = width;
		this.height = height;
		this.overlay = overlay;
	}

	
	
	
	@Override
	public void render(Minecraft minecraft, final int xPosition, final int yPosition, @Nullable ParticleBeam particleBeam) 
	{
		GlStateManager.enableBlend();
		GlStateManager.enableAlpha();

		drawParticle(minecraft, xPosition, yPosition, particleBeam);

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
	
	
	
	private void drawParticle(Minecraft minecraft, final int xPosition, final int yPosition,
			@Nullable ParticleBeam particleBeam)
	{
		if (particleBeam == null)
		{
			return;
		}
		Particle particle = particleBeam.getParticle();
		if (particle == null)
		{
			return;
		}

		TextureAtlasSprite particleSprite = getSprite(minecraft, particle);

		int particleColor = particle.getColor();


		drawTiledSprite(minecraft, xPosition, yPosition, width, height, particleColor, particleSprite);
	}
	
	
	private void drawTiledSprite(Minecraft minecraft, final int xPosition, final int yPosition, final int tiledWidth,
			final int tiledHeight, int color, TextureAtlasSprite sprite)
	{
		minecraft.renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		setGLColorFromInt(color);

		

		drawTextureWithMasking(xPosition, yPosition, sprite, 100);

	}
	
	
	private static void drawTextureWithMasking(double xCoord, double yCoord, TextureAtlasSprite textureSprite, double zLevel) {
		double uMin = textureSprite.getMinU();
		double uMax = textureSprite.getMaxU();
		double vMin = textureSprite.getMinV();
		double vMax = textureSprite.getMaxV();

		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferBuilder = tessellator.getBuffer();
		bufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
		bufferBuilder.pos(xCoord, yCoord + 16, zLevel).tex(uMin, vMax).endVertex();
		bufferBuilder.pos(xCoord + 16, yCoord + 16, zLevel).tex(uMax, vMax).endVertex();
		bufferBuilder.pos(xCoord + 16, yCoord, zLevel).tex(uMax, vMin).endVertex();
		bufferBuilder.pos(xCoord, yCoord, zLevel).tex(uMin, vMin).endVertex();
		tessellator.draw();
	}
	
	private static void setGLColorFromInt(int color)
	{
		float red = (color >> 16 & 0xFF) / 255.0F;
		float green = (color >> 8 & 0xFF) / 255.0F;
		float blue = (color & 0xFF) / 255.0F;

		GlStateManager.color(red, green, blue, 1.0F);
	}
	
	
	private static TextureAtlasSprite getSprite(Minecraft minecraft, Particle particle) {
		TextureMap textureMapBlocks = minecraft.getTextureMapBlocks();
		ResourceLocation texture = particle.getTexture();
		TextureAtlasSprite textureSprite = null;
		if (texture != null) {
			textureSprite = textureMapBlocks.getTextureExtry(texture.toString());
		}
		if (textureSprite == null) {
			textureSprite = textureMapBlocks.getMissingSprite();
		}
		return textureSprite;
	}

}
