package lach_01298.qmd.render.tile;

import lach_01298.qmd.vacuumChamber.VacuumChamber;
import lach_01298.qmd.vacuumChamber.tile.TileVacuumChamberLaser;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.*;

import static nc.block.property.BlockProperties.FACING_ALL;

@SideOnly(Side.CLIENT)
public class RenderVacuumChamberLaser extends TileEntitySpecialRenderer<TileVacuumChamberLaser>
{
	private static final Minecraft MC = Minecraft.getMinecraft();

	@Override
	public boolean isGlobalRenderer(TileVacuumChamberLaser tile)
	{
		return tile.isRenderer && tile.isMultiblockAssembled();
	}

	@Override
	public void render(TileVacuumChamberLaser laser, double posX, double posY, double posZ, float partialTicks,
			int destroyStage, float alpha)
	{
		
		
		if (!laser.isRenderer || !laser.isMultiblockAssembled())
			return;
		
		VacuumChamber containment = laser.getMultiblock();
		if (containment == null)
			return;
		
		if (!containment.isChamberOn)
			return;
		
		
		GlStateManager.pushMatrix();
		GlStateManager.translate(posX + 0.5f, posY + 0.5f, posZ + 0.5f);

		GlStateManager.pushMatrix();
		BlockPos pos = laser.getPos();
		if (laser.getBlockState(pos).getValue(FACING_ALL) == EnumFacing.NORTH)
		{
			GlStateManager.rotate(-90f, 1f, 0f, 0f);
		}
		if (laser.getBlockState(pos).getValue(FACING_ALL) == EnumFacing.SOUTH)
		{
			GlStateManager.rotate(90f, 1f, 0f, 0f);
		}
		if (laser.getBlockState(pos).getValue(FACING_ALL) == EnumFacing.EAST)
		{
			GlStateManager.rotate(-90f, 0f, 0f, 1f);
		}
		if (laser.getBlockState(pos).getValue(FACING_ALL) == EnumFacing.WEST)
		{
			GlStateManager.rotate(90f, 0f, 0f, 1f);
		}

		GlStateManager.pushMatrix();
		int bright = 0xC0;
		int brightX = bright % 65536;
		int brightY = bright / 65536;
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, brightX, brightY);
		GlStateManager.disableLighting();
		GlStateManager.depthMask(false);
		GlStateManager.disableTexture2D();
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
		
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();

		
		
		
		
		
		
		bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
		double radius = 0.10;
		double length = (double) laser.getMultiblock().getInteriorLengthX() / 2d + 0.5d;
		bufferbuilder.pos(-radius, 0D, radius).color(1.0f, 0.0f, 0.0f, 0.6f).endVertex();
		bufferbuilder.pos(radius, 0D, radius).color(1.0f, 0.0f, 0.0f, 0.6f).endVertex();
		bufferbuilder.pos(radius, length, radius).color(1.0f, 0.0f, 0.0f, 0.6f).endVertex();
		bufferbuilder.pos(-radius, length, radius).color(1.0f, 0.0f, 0.0f, 0.6f).endVertex();

		bufferbuilder.pos(radius, 0D, radius).color(1.0f, 0.0f, 0.0f, 0.6f).endVertex();
		bufferbuilder.pos(radius, 0D, -radius).color(1.0f, 0.0f, 0.0f, 0.6f).endVertex();
		bufferbuilder.pos(radius, length, -radius).color(1.0f, 0.0f, 0.0f, 0.6f).endVertex();
		bufferbuilder.pos(radius, length, radius).color(1.0f, 0.0f, 0.0f, 0.6f).endVertex();

		bufferbuilder.pos(-radius, 0D, -radius).color(1.0f, 0.0f, 0.0f, 0.6f).endVertex();
		bufferbuilder.pos(-radius, length, -radius).color(1.0f, 0.0f, 0.0f, 0.6f).endVertex();
		bufferbuilder.pos(radius, length, -radius).color(1.0f, 0.0f, 0.0f, 0.6f).endVertex();
		bufferbuilder.pos(radius, 0D, -radius).color(1.0f, 0.0f, 0.0f, 0.6f).endVertex();

		bufferbuilder.pos(-radius, 0D, radius).color(1.0f, 0.0f, 0.0f, 0.6f).endVertex();
		bufferbuilder.pos(-radius, length, radius).color(1.0f, 0.0f, 0.0f, 0.6f).endVertex();
		bufferbuilder.pos(-radius, length, -radius).color(1.0f, 0.0f, 0.0f, 0.6f).endVertex();
		bufferbuilder.pos(-radius, 0D, -radius).color(1.0f, 0.0f, 0.0f, 0.6f).endVertex();

		tessellator.draw();
		
		
		GlStateManager.disableBlend();
		GlStateManager.enableTexture2D();
		GlStateManager.depthMask(true);
		GlStateManager.enableLighting();
		GlStateManager.popMatrix();
		GlStateManager.popMatrix();
		GlStateManager.popMatrix();
		

	}

}
