package lach_01298.qmd;


import java.util.UUID;

import lach_01298.qmd.item.QMDArmour;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.PotionEvent.PotionApplicableEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


@Mod.EventBusSubscriber(modid = QMD.MOD_ID)
public class ArmourBonusHandler
{
	private static final UUID HEV_SPEED_BOOST = UUID.fromString("662A5B8D-DE3E-4C1C-8103-96EA50972780"); //some random hex numbers
	
	
	
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=false)
	public static void onLivingJumpEvent(LivingJumpEvent event)
	{
		
		if(event.getEntity() instanceof EntityLivingBase)
		{
			EntityLivingBase entity = (EntityLivingBase) event.getEntity();
			
			if(entity.getItemStackFromSlot(EntityEquipmentSlot.LEGS).getItem() == QMDArmour.legs_hev)
			{
				entity.motionY+=0.2;
				
				
			}
			
			if(entity.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem() == QMDArmour.chest_hev)
			{
				if(entity.isSprinting())
				{
					float boost = 1;
					float xBoost = -boost * MathHelper.sin(entity.rotationYaw * 0.017453292F);
					float zBoost = boost * MathHelper.cos(entity.rotationYaw * 0.017453292F);
					entity.motionX += xBoost;
					entity.motionZ += zBoost;
				
				}
			}
			
			
		}
		
	}
	
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=false)
	public static void onLivingFallEvent(LivingFallEvent event)
	{
		
		if(event.getEntity() instanceof EntityLivingBase)
		{
			EntityLivingBase entity = (EntityLivingBase) event.getEntity();
			if(entity.getItemStackFromSlot(EntityEquipmentSlot.FEET).getItem() == QMDArmour.boots_hev)
			{
				event.setDistance(event.getDistance() - 4.0f);
				event.setDamageMultiplier(0.5f);
			}
			
		}
	}
	
	
	
//	@SideOnly(Side.CLIENT)
//	@SubscribeEvent(priority = EventPriority.LOW) 
//	public static void handleFovEvent(FOVUpdateEvent event)
//	{
//		if(event.getEntity() instanceof EntityLivingBase)
//		{
//			EntityLivingBase entity = (EntityLivingBase) event.getEntity();
//			if(entity.getItemStackFromSlot(EntityEquipmentSlot.LEGS).getItem() == QMDArmour.legs_hev)
//			{
//				IAttributeInstance iattributeinstance = event.getEntity().getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);
//				float f = 1/((float) ((((iattributeinstance.getAttributeValue())/ (double) event.getEntity().capabilities.getWalkSpeed() + 1.0D) / 2.0D)));
//				
//				
//				
//				event.setNewfov(event.getNewfov()*f);
//			}
//			
//			
//		}
//	}
	
	
	
	
	@SubscribeEvent
	public void onPotionAplied(PotionApplicableEvent event)
	{
		if(event.getEntity() instanceof EntityLivingBase)
		{
			EntityLivingBase entity = (EntityLivingBase) event.getEntity();
			
			if (entity.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem() == QMDArmour.helm_hev)
			{
				if(event.getPotionEffect().getPotion() == Potion.getPotionById(19)) // Poison
				{
					event.setResult(Result.DENY);
				}
			}
		}
	}
	
	
	
	@SubscribeEvent
	public void onTick(PlayerTickEvent event)
	{
		if (event.player.getItemStackFromSlot(EntityEquipmentSlot.FEET).getItem() == QMDArmour.boots_hev)
		{
			event.player.stepHeight = 1.002F;
		}
		else if (event.player.stepHeight == 1.002F)
		{
			event.player.stepHeight = 0.6F;
		}
		if (event.player.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem() == QMDArmour.helm_hev)
		{
			if (event.player.getActivePotionEffect(Potion.getPotionById(20)) != null) // Wither
			{
				PotionEffect effect = event.player.getActivePotionEffect(Potion.getPotionById(20));
				if (effect.getAmplifier() > 0 || effect.getDuration() > 80)
				{
					
					event.player.removeActivePotionEffect(Potion.getPotionById(20));
					event.player.addPotionEffect(new PotionEffect(Potion.getPotionById(20), 80, 0));
				}

			}

		}
	}

}
