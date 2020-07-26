package lach_01298.qmd.render.tile;

import org.lwjgl.opengl.GL11;

import lach_01298.qmd.containment.Containment;
import lach_01298.qmd.containment.NeutralContainmentLogic;
import lach_01298.qmd.containment.tile.TileNeutralContainmentController;
import lach_01298.qmd.model.ModelMaterial;
import lach_01298.qmd.multiblock.network.ContainmentResendFormPacket;
import lach_01298.qmd.network.QMDPacketHandler;
import nc.config.NCConfig;
import nc.multiblock.network.TurbineResendFormPacket;
import nc.multiblock.turbine.Turbine;
import nc.multiblock.turbine.tile.TileTurbineController;
import nc.network.PacketHandler;
import nc.util.NCMath;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelChest;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.model.pipeline.ForgeBlockModelRenderer;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderContainmentMaterial extends TileEntitySpecialRenderer<TileNeutralContainmentController>
{
private static final Minecraft MC = Minecraft.getMinecraft();
	
	private final float[] brightness = new float[] {1F, 1F, 1F, 1F, 1F, 1F, 1F, 1F};
	private byte count = 0;
	private final ModelMaterial model = new ModelMaterial();
	@Override
	public boolean isGlobalRenderer(TileNeutralContainmentController tile) {
		return tile.isRenderer && tile.isMultiblockAssembled();
	}
	
	@Override
	public void render(TileNeutralContainmentController controller, double posX, double posY, double posZ, float partialTicks, int destroyStage, float alpha) 
	{
		if (!controller.isRenderer || !controller.isMultiblockAssembled()) return;
		Containment containment = controller.getMultiblock();
		if (containment == null) return;
		
		if (containment.tanks == null) 
		{
			
			QMDPacketHandler.instance.sendToServer(new ContainmentResendFormPacket(controller.getPos()));
			return;
			
		}
		
		
		
		
		
		FluidStack fluidStack = containment.tanks.get(2).getFluid();
		
		if (fluidStack != null)
		{
		
			BlockRendererDispatcher renderer = MC.getBlockRendererDispatcher();
			
			brightness[count] = controller.getWorld().getLightBrightness(containment.getExtremeInteriorCoord(NCMath.getBit(count, 0) == 1, NCMath.getBit(count, 1) == 1, NCMath.getBit(count, 2) == 1));
			count++;
			count %= 8;
			float bright = (brightness[0] + brightness[1] + brightness[2] + brightness[3] + brightness[4] + brightness[5] + brightness[6] + brightness[7]) / 8F;
			
			
			MC.renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
			
			
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 0, 15 * 16);
			
			BlockPos pos = controller.getPos();
			
			double	scale = ((double)containment.tanks.get(2).getFluidAmount()/(double)containment.tanks.get(2).getCapacity())*1.5;
			//scale=1;
			double x = -containment.getMaxX() + pos.getX();
			double y = -containment.getMaxY() + pos.getY();
			double z = -containment.getMaxZ() + pos.getZ();
			GlStateManager.pushMatrix();
			GlStateManager.translate(posX+0.5f, posY+0.5f, posZ+0.5f);
			GlStateManager.translate(containment.getMiddleX()-pos.getX(), containment.getMiddleY()-pos.getY(), containment.getMiddleZ()-pos.getZ());
			
			GlStateManager.pushMatrix();
			
			{
				long systemTime = Minecraft.getSystemTime();
				if (!MC.isGamePaused()) containment.materialAngle = (containment.materialAngle + (systemTime - containment.prevRenderTime) * 0.05f) % 360F;
				containment.prevRenderTime = systemTime;
				GlStateManager.rotate(containment.materialAngle, 1F, 0F , 0F);
				GlStateManager.rotate(containment.materialAngle, 0F, 1F , 0F);
				GlStateManager.rotate(containment.materialAngle, 0F, 0F , 1F);
			}
			
			GlStateManager.pushMatrix();
			GlStateManager.scale(scale, scale*1.125f, scale);
			GlStateManager.translate(-0.50f, -0.5f+0.055f, 0.5f);
			
			IBlockState blockstate = fluidStack.getFluid().getBlock().getDefaultState();
			// System.out.println("ho " + scale );
			
			renderer.renderBlockBrightness(blockstate, bright);
		
			
			GlStateManager.popMatrix();
			
			
			GlStateManager.popMatrix();
			GlStateManager.popMatrix();
			
		}

	}
	
	
}
