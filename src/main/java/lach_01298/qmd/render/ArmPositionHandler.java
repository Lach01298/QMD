package lach_01298.qmd.render;

import lach_01298.qmd.QMD;
import lach_01298.qmd.item.ItemGun;
import net.minecraft.client.model.*;
import net.minecraft.client.model.ModelBiped.ArmPose;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHandSide;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.fml.relauncher.*;

@Mod.EventBusSubscriber(modid = QMD.MOD_ID)
public class ArmPositionHandler
{

	@SideOnly(Side.CLIENT)
	@SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = false)
	public static void onRenderLivingEventPre(RenderLivingEvent.Pre event)
	{
		if (event.getEntity() instanceof EntityPlayer)
		{
			EntityPlayer ply = (EntityPlayer) event.getEntity();

			ItemStack stack = ply.getHeldItemMainhand();
			if (!stack.isEmpty() && stack.getItem() instanceof ItemGun)
			{
				ModelBase mdl = event.getRenderer().getMainModel();
				if (mdl instanceof ModelPlayer)
				{
					ModelPlayer model = (ModelPlayer) mdl;
					if (ply.getPrimaryHand() == EnumHandSide.RIGHT)
					{
						model.rightArmPose = ArmPose.BOW_AND_ARROW;
					}
					else
					{
						model.leftArmPose = ArmPose.BOW_AND_ARROW;
					}
				}
			}
			else
			{

				ItemStack stack2 = ply.getHeldItemOffhand();
				if (!stack2.isEmpty() && stack2.getItem() instanceof ItemGun)
				{
					ModelBase mdl = event.getRenderer().getMainModel();
					if (mdl instanceof ModelPlayer)
					{
						ModelPlayer model = (ModelPlayer) mdl;

						if (ply.getPrimaryHand() == EnumHandSide.RIGHT)
						{
							model.leftArmPose = ArmPose.BOW_AND_ARROW;
						}
						else
						{
							model.rightArmPose = ArmPose.BOW_AND_ARROW;
						}

					}
				}
			}
		}

	}

}
