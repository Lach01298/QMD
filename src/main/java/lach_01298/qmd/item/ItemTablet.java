package lach_01298.qmd.item;

import lach_01298.qmd.QMDDamageSources;
import lach_01298.qmd.config.QMDConfig;
import nc.capability.radiation.entity.IEntityRads;
import nc.item.NCItemFood;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.*;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class ItemTablet extends NCItemFood
{

	public ItemTablet(PotionEffect[] potionEffects, String... tooltip)
	{
		super(0, 0f, false, potionEffects, tooltip);
	}

	
	
	
	@Override
	protected void onFoodEaten(ItemStack stack, World world, EntityPlayer player)
	{
		super.onFoodEaten(stack, world, player);
		double lifetime = (double)QMDConfig.ki_time;
		double currentTime = player.getCapability(IEntityRads.CAPABILITY_ENTITY_RADS, null).getRadiationImmunityTime();
		
		if(currentTime > lifetime)
		{
			double newTime = currentTime+lifetime*lifetime/currentTime;
			player.getCapability(IEntityRads.CAPABILITY_ENTITY_RADS, null).setRadiationImmunityTime(newTime);
			
			int newIntTime = (int) newTime;
			
			player.addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("weakness"),newIntTime));
			
			if(currentTime>lifetime*1.5) //Maybe you should not take that many
			{
				player.addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("hunger"),newIntTime));
			}
			if(currentTime>lifetime*2) //You should not take this many
			{
				player.addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("hunger"),newIntTime*2));
				player.addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("weakness"),newIntTime*2));
				player.addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("slowness"),newIntTime*2));
			}
			if(currentTime>lifetime*3) //You really should not take this many
			{
				player.addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("hunger"),newIntTime*2,1));
				player.addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("weakness"),newIntTime*2,1));
				player.addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("slowness"),newIntTime*2,1));
				player.addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("poison"),newIntTime/2));
			}
			if(currentTime>lifetime*4) //Are you trying to kill your self?
			{
				player.addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("nausea"),newIntTime));
				player.attackEntityFrom(QMDDamageSources.SELF_POISONING, 10f);
			}
			if(currentTime>lifetime*4.5) // Apparently so
			{
				player.attackEntityFrom(QMDDamageSources.SELF_POISONING,player.getHealth());
			}
			
		}
		else
		{
			player.getCapability(IEntityRads.CAPABILITY_ENTITY_RADS, null).setRadiationImmunityTime(currentTime+lifetime);
			player.addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("weakness"),(int)(currentTime+lifetime)));
		}
		
		
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
	{
		ItemStack itemstack = player.getHeldItem(hand);

		player.setActiveHand(hand);
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);

	}
	
}
