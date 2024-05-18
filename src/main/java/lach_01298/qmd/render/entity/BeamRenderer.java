package lach_01298.qmd.render.entity;

import com.google.common.base.Predicates;
import lach_01298.qmd.QMD;
import lach_01298.qmd.entity.EntityBeamProjectile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.fml.relauncher.*;

import java.util.List;

@Mod.EventBusSubscriber(modid = QMD.MOD_ID)
public class BeamRenderer
{
	
	//this is to make the EntityBeamProjectile to render even in unrendered chunks
	@SideOnly(Side.CLIENT)
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void renderBeamEffects(RenderWorldLastEvent event)
	{

		List<EntityBeamProjectile> list= Minecraft.getMinecraft().world.getEntities(EntityBeamProjectile.class, Predicates.alwaysTrue());

		for(Entity entity : list)
		{
			float partialTicks = Minecraft.getMinecraft().getRenderPartialTicks();
			double d0 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double) partialTicks;
            double d1 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double) partialTicks;
            double d2 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double) partialTicks;
            float f = entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks;
            Entity entity2 = Minecraft.getMinecraft().getRenderViewEntity();
            double d3 = entity2.lastTickPosX + (entity2.posX - entity2.lastTickPosX) * (double) partialTicks;
            double d4 = entity2.lastTickPosY + (entity2.posY - entity2.lastTickPosY) * (double) partialTicks;
            double d5 = entity2.lastTickPosZ + (entity2.posZ - entity2.lastTickPosZ) * (double) partialTicks;
			
			Render<Entity> render = Minecraft.getMinecraft().getRenderManager().getEntityRenderObject(entity);
			render.doRender(entity, d0 - d3, d1 - d4, d2 - d5, f, partialTicks);
			
		}
	}
}
