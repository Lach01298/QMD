package lach_01298.qmd.render.entity;

import java.awt.Color;

import lach_01298.qmd.entity.EntityAntimatterProjectile;
import lach_01298.qmd.entity.EntityGluonBeam;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public  class RenderAntimatterProjectile extends Render<EntityAntimatterProjectile>
{
	private static final ResourceLocation Texture = new ResourceLocation("qmd:textures/entities/antimatter_projectile.png");
	
	public RenderAntimatterProjectile(RenderManager renderManagerIn)
    {
        super(renderManagerIn);
    }

    
	@Override
	public ResourceLocation getEntityTexture(EntityAntimatterProjectile entity)
	{
		return Texture;
	}
    
  
    public void doRender(EntityAntimatterProjectile entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        this.bindEntityTexture(entity);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.pushMatrix();
        GlStateManager.disableLighting();
        GlStateManager.translate((float)x, (float)y, (float)z);
        GlStateManager.rotate(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks - 90.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks, 0.0F, 0.0F, 1.0F);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        int i = 0;
        float f = 0.0F;
        float f1 = 0.5F;
        float f2 = 0.0F;
        float f3 = 0.15625F;
        float f4 = 0.0F;
        float f5 = 0.15625F;
        float f6 = 0.15625F;
        float f7 = 0.3125F;
        float f8 = 0.05625F;
        GlStateManager.enableRescaleNormal();
      

        GlStateManager.rotate(45.0F+ entity.ticksExisted*20f, 1.0F, 0.0F, 0.0F);
        GlStateManager.scale(0.5F, 0.5F, 0.5F);
        GlStateManager.translate(-1.0F, 0.0F, 0.0F);

       

        float red = 100/ 255f;
        float green = 100/ 255f;
        float blue = 100/ 255f;
        if(entity.getColor() != null)
        {
       	 red = entity.getColor().getRed()/ 255f;
       	 green = entity.getColor().getGreen()/ 255f;
       	 blue = entity.getColor().getBlue()/ 255f;
        }
        
        
        float width = 1F;
        float length = 1F;
        float brightness = 1.0F;

     
        
        
        for (int j = 0; j < 4; ++j)
        {
            GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.glNormal3f(0.0F, 0.0F, 0.05625F);
            bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
            bufferbuilder.pos(length, -width, 0.0D).tex(0, 1).color(red, green, blue, brightness).endVertex();
            bufferbuilder.pos(0, -width, 0.0D).tex(0, 0).color(red, green, blue, brightness).endVertex();
            bufferbuilder.pos(0, width, 0.0D).tex(1, 0).color(red, green, blue, brightness).endVertex();
            bufferbuilder.pos(length, width, 0.0D).tex(1, 1).color(red, green, blue, brightness).endVertex();
            tessellator.draw();
        }
        
        GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.translate(0.0F, 0.0F, 0.5F);
        for (int j = 0; j < 2; ++j)
        {
	        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
	        bufferbuilder.pos(-width/4, -width/4, 0.0D).tex(5/16D, 10/16D).color(red, green, blue, brightness).endVertex();
	        bufferbuilder.pos(width/4, -width/4, 0.0D).tex(5/16D, 5/16D).color(red, green, blue, brightness).endVertex();
	        bufferbuilder.pos(width/4, width/4, 0.0D).tex(10/16D, 5/16D).color(red, green, blue, brightness).endVertex();
	        bufferbuilder.pos(-width/4, width/4, 0.0D).tex(10/16D, 10/16D).color(red, green, blue, brightness).endVertex();
	        tessellator.draw();
	        GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
        }

        GlStateManager.disableRescaleNormal();
        GlStateManager.enableLighting();
        GlStateManager.popMatrix();
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }
}