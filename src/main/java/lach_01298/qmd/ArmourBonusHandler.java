package lach_01298.qmd;

import lach_01298.qmd.config.QMDConfig;
import lach_01298.qmd.item.QMDArmour;
import nc.util.Lazy;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.*;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.energy.*;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.PotionEvent.PotionApplicableEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

import java.util.*;

@Mod.EventBusSubscriber(modid = QMD.MOD_ID)
public class ArmourBonusHandler
{
	private static final UUID HEV_SPEED_BOOST = UUID.fromString("662A5B8D-DE3E-4C1C-8103-96EA50972780"); //some random hex numbers
	private static final UUID[] ARMOR_MODIFIERS = new UUID[] {UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B6B"), UUID.fromString("D8499B04-0E66-4726-AB29-64469D734E0D"), UUID.fromString("9F3D476D-C118-4544-8365-64846904B48E"), UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150")};
	
	private final Map<EntityPlayer, Float> previousStepHeights = Collections.synchronizedMap(new WeakHashMap<>());
	private static final Lazy<AttributeModifier> HEV_SPEED_MODIFIER = new Lazy<>(() -> new AttributeModifier(ARMOR_MODIFIERS[1], "Movement speed", 0.25D, 1));
	
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=false)
	public static void onLivingJumpEvent(LivingJumpEvent event)
	{
		// Jump boost
		if (event.getEntity() instanceof EntityLivingBase)
		{
			EntityLivingBase entity = (EntityLivingBase) event.getEntity();

			if (entity.getItemStackFromSlot(EntityEquipmentSlot.LEGS).getItem() == QMDArmour.legs_hev)
			{
				if (entity.getItemStackFromSlot(EntityEquipmentSlot.LEGS).hasCapability(CapabilityEnergy.ENERGY, null))
				{
					IEnergyStorage energy = entity.getItemStackFromSlot(EntityEquipmentSlot.LEGS).getCapability(CapabilityEnergy.ENERGY, null);

					if (energy.extractEnergy(QMDConfig.hev_power[1], true) == QMDConfig.hev_power[1])
					{
						energy.extractEnergy(QMDConfig.hev_power[1], false);
						entity.motionY += 0.2;
					}
				}
			}
			
			// long jump
			if(entity.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem() == QMDArmour.chest_hev)
			{
				if(entity.isSprinting())
				{
					if (entity.getItemStackFromSlot(EntityEquipmentSlot.CHEST).hasCapability(CapabilityEnergy.ENERGY, null))
					{
						IEnergyStorage energy = entity.getItemStackFromSlot(EntityEquipmentSlot.CHEST)
								.getCapability(CapabilityEnergy.ENERGY, null);

						if (energy.extractEnergy(QMDConfig.hev_power[2], true) == QMDConfig.hev_power[2])
						{
							energy.extractEnergy(QMDConfig.hev_power[2], false);
							float boost = 1;
							float xBoost = -boost * MathHelper.sin(entity.rotationYaw * 0.017453292F);
							float zBoost = boost * MathHelper.cos(entity.rotationYaw * 0.017453292F);
							entity.motionX += xBoost;
							entity.motionZ += zBoost;
						}
					}
				}
			}
		}
	}
	
	
	// fall reduction
	@SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = false)
	public static void onLivingFallEvent(LivingFallEvent event)
	{
	
		if (event.getEntity() instanceof EntityLivingBase)
		{
			EntityLivingBase entity = (EntityLivingBase) event.getEntity();
			if (entity.getItemStackFromSlot(EntityEquipmentSlot.FEET).getItem() == QMDArmour.boots_hev)
			{

				if (entity.getItemStackFromSlot(EntityEquipmentSlot.FEET).hasCapability(CapabilityEnergy.ENERGY, null))
				{
					IEnergyStorage energy = entity.getItemStackFromSlot(EntityEquipmentSlot.FEET)
							.getCapability(CapabilityEnergy.ENERGY, null);

					if (energy.extractEnergy(QMDConfig.hev_power[3], true) == QMDConfig.hev_power[3])
					{
						if(event.getDistance() > 3)
						{
							energy.extractEnergy(QMDConfig.hev_power[3], false);
						}
						event.setDistance(event.getDistance() - 4.0f);
						event.setDamageMultiplier(0.5f);
					}
				}
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
	
	
	
	// Poison removal
	@SubscribeEvent
	public void onPotionAplied(PotionApplicableEvent event)
	{
		
		if(event.getEntity() instanceof EntityLivingBase)
		{
			EntityLivingBase entity = (EntityLivingBase) event.getEntity();
			
			if (entity.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem() == QMDArmour.helm_hev)
			{
				if(event.getPotionEffect().getPotion() == Potion.getPotionFromResourceLocation("poison"))
				{
						if (entity.getItemStackFromSlot(EntityEquipmentSlot.HEAD).hasCapability(CapabilityEnergy.ENERGY, null))
						{
							IEnergyStorage energy = entity.getItemStackFromSlot(EntityEquipmentSlot.HEAD)
									.getCapability(CapabilityEnergy.ENERGY, null);

							if (energy.extractEnergy(QMDConfig.hev_power[4], true) == QMDConfig.hev_power[4])
							{
								energy.extractEnergy(QMDConfig.hev_power[4], false);
								event.setResult(Result.DENY);
							}
						}
				}
			}
		}
	}
	
	
	
	@SubscribeEvent
	public void onTick(PlayerTickEvent event)
	{
		// step assist
		boolean stepAssist = false;
		
		if (event.player.getItemStackFromSlot(EntityEquipmentSlot.FEET).getItem() == QMDArmour.boots_hev)
		{
			if (event.player.getItemStackFromSlot(EntityEquipmentSlot.FEET).hasCapability(CapabilityEnergy.ENERGY, null))
			{
				IEnergyStorage energy = event.player.getItemStackFromSlot(EntityEquipmentSlot.FEET).getCapability(CapabilityEnergy.ENERGY, null);
				stepAssist = energy != null && energy.getEnergyStored() > 0;
			}
		}
		
		if (stepAssist)
		{
			if (event.player.stepHeight < 1.002F)
			{
				previousStepHeights.putIfAbsent(event.player, event.player.stepHeight);
				event.player.stepHeight = 1.002F;
			}
		}
		else
		{
			Float previousStepHeight = previousStepHeights.remove(event.player);
			if (previousStepHeight != null && event.player.stepHeight == 1.002F)
			{
				event.player.stepHeight = previousStepHeight;
			}
		}
		
		// wither reduction
		if (event.player.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem() == QMDArmour.helm_hev)
		{
			if (event.player.getItemStackFromSlot(EntityEquipmentSlot.HEAD).hasCapability(CapabilityEnergy.ENERGY,null))
			{
				IEnergyStorage energy = event.player.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getCapability(CapabilityEnergy.ENERGY, null);
				if (energy.extractEnergy(QMDConfig.hev_power[4], true) == QMDConfig.hev_power[4])
				{

					if (event.player.getActivePotionEffect(Potion.getPotionById(20)) != null) // Wither
					{
						PotionEffect effect = event.player.getActivePotionEffect(Potion.getPotionById(20));
						if (effect.getAmplifier() > 0 || effect.getDuration() > 80)
						{
							energy.extractEnergy(QMDConfig.hev_power[4], false);
							event.player.removeActivePotionEffect(Potion.getPotionById(20));
							event.player.addPotionEffect(new PotionEffect(Potion.getPotionById(20), 80, 0));
						}
					}
				}
			}
		}
		
		//speed boost
		if (!event.player.world.isRemote && event.phase == TickEvent.Phase.END)
		{
			ItemStack leggings = event.player.getItemStackFromSlot(EntityEquipmentSlot.LEGS);
			boolean speedBoost = false;
			
			if (leggings.getItem() == QMDArmour.legs_hev && leggings.hasCapability(CapabilityEnergy.ENERGY, null))
			{
				IEnergyStorage energy = leggings.getCapability(CapabilityEnergy.ENERGY, null);
				speedBoost = energy != null && energy.getEnergyStored() > 0;
			}
			
			IAttributeInstance movementSpeed = event.player.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);
			boolean hasModifier = movementSpeed.getModifier(HEV_SPEED_MODIFIER.get().getID()) != null;
			
			if (speedBoost && !hasModifier)
			{
				movementSpeed.applyModifier(HEV_SPEED_MODIFIER.get());
			}
			else if (!speedBoost && hasModifier)
			{
				movementSpeed.removeModifier(HEV_SPEED_MODIFIER.get().getID());
			}
		}
		
	}
	
}
