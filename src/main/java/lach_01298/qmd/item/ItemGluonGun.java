package lach_01298.qmd.item;

import java.util.List;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import lach_01298.qmd.DamageSources;
import lach_01298.qmd.config.QMDConfig;
import lach_01298.qmd.entity.EntityGluonBeam;
import lach_01298.qmd.entity.EntityLeptonBeam;
import lach_01298.qmd.enums.MaterialTypes.CellType;
import lach_01298.qmd.sound.MovingSoundGluonGun;
import lach_01298.qmd.sound.MovingSoundGluonGunStart;
import lach_01298.qmd.sound.QMDSounds;
import nc.capability.radiation.entity.IEntityRads;
import nc.item.IInfoItem;
import nc.radiation.RadiationHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.MovingSound;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ItemGluonGun extends ItemGun implements IInfoItem
{
	private ISound sound;
	private EntityGluonBeam beam;
	
	public ItemGluonGun()
	{
		super(); 
	}

	@Override
	public void setInfo()
	{
		// TODO Auto-generated method stub
		
	}
	
	
	
	
	public int getMaxItemUseDuration(ItemStack stack)
    {
        return 72000;
    }
	
	
    public EnumAction getItemUseAction(ItemStack stack)
    {
        return EnumAction.NONE;
    }

    
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
    {
		ItemStack itemstack = player.getHeldItem(hand);
		if(findCell(player) >=0 )
		{
			player.setActiveHand(hand);
			
			RayTraceResult lookingAt = rayTrace(player,QMDConfig.gluon_range,1.0f,true,true);
			
			
			if(!world.isRemote)
			{
				if(lookingAt != null)
				{
					double length = lookingAt.hitVec.distanceTo(player.getPositionVector().add(0, player.eyeHeight, 0));
					if(length > QMDConfig.gluon_range)
					{
						length = QMDConfig.gluon_range;
					}
					world.spawnEntity(new EntityGluonBeam(world, player, length, hand));
				}
				else
				{
					world.spawnEntity(new EntityGluonBeam(world, player, QMDConfig.gluon_range, hand));
				}
			}
			else
			{
				if(lookingAt != null)
				{
					double length = lookingAt.hitVec.distanceTo(player.getPositionVector().add(0, player.eyeHeight, 0));
					if(length > QMDConfig.gluon_range)
					{
						length = QMDConfig.gluon_range;
					}
					beam = new EntityGluonBeam(world, player, length, hand);
				}
				else
				{
					beam = new EntityGluonBeam(world, player, QMDConfig.gluon_range, hand);
				}
				world.spawnEntity(beam);
			}
			return new ActionResult<ItemStack>(EnumActionResult.PASS, itemstack);
		}

        return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemstack);
    }
    
    
    public void onUsingTick(ItemStack stack, EntityLivingBase user, int count)
    {
    	if(user instanceof EntityPlayer)
    	{
    		EntityPlayer player = (EntityPlayer) user;
    		if(findCell(player) >=0 )
    		{
    			ItemStack cell = player.inventory.getStackInSlot(findCell(player));
    			ItemCell itemCell = (ItemCell) cell.getItem();
    			player.inventory.setInventorySlotContents(findCell(player),itemCell.empty(cell, 1));
    			
    			World world = user.getEntityWorld();
    	    	RayTraceResult lookingAt = rayTrace(user,QMDConfig.gluon_range,1.0f,true,true);

    	    	if(!world.isRemote)
    			{
    				
    		        if (lookingAt != null && lookingAt.typeOfHit == RayTraceResult.Type.BLOCK) 
    		        {
    		            BlockPos pos = lookingAt.getBlockPos();
    		            if(world.getBlockState(pos).getBlockHardness(world, pos) >= 0.0f)
    		            {
    		            	 world.destroyBlock(pos, true);
    		            	
    		            }
    		            
    				}
    				else if (lookingAt != null && lookingAt.typeOfHit == RayTraceResult.Type.ENTITY)
    				{
    					Entity entity = lookingAt.entityHit;

    					if (entity instanceof EntityLivingBase)
    					{
    						IEntityRads entityRads = RadiationHelper.getEntityRadiation((EntityLivingBase) entity);
    						entityRads.setRadiationLevel(RadiationHelper.addRadsToEntity(entityRads, (EntityLivingBase) entity,
    								QMDConfig.gluon_radiation, false, false, 1));
    					}
    					entity.attackEntityFrom(DamageSources.GLUON_GUN, (float) QMDConfig.gluon_damage);	
    				}
    			}
    	    	else
    	    	{
    	    		if (count == getMaxItemUseDuration(stack))
    	    		{
    	    			sound = new MovingSoundGluonGunStart(user);
    	    			Minecraft.getMinecraft().getSoundHandler().playSound(sound);
    	    		}
    	    		else 
    	    		{
    	    			if(sound == null || !Minecraft.getMinecraft().getSoundHandler().isSoundPlaying(sound))
    	        		{
    	        			sound = new MovingSoundGluonGun(user);
    	        			Minecraft.getMinecraft().getSoundHandler().playSound(sound);
    	        		}
    	    		}
    	    		
    	    		if(lookingAt != null)
    	    		{
    	    			beam.length = lookingAt.hitVec.distanceTo(user.getPositionVector().add(0, user.getEyeHeight(), 0));
    	    		}
    	    	}
    		}
    		else
    		{
    			user.stopActiveHand();
    		}
    	}
    	
    	
    	
    	
    }
	
	public void onPlayerStoppedUsing(ItemStack stack, World world, EntityLivingBase entityLiving, int timeLeft)
	{
		if(world.isRemote)
		{
			
			if(sound != null && Minecraft.getMinecraft().getSoundHandler().isSoundPlaying(sound))
    		{
    			Minecraft.getMinecraft().getSoundHandler().stopSound(sound);
    		}
			world.playSound(entityLiving.posX, entityLiving.posY, entityLiving.posZ, QMDSounds.gluon_gun_stop, SoundCategory.PLAYERS, 0.1f, 1.0f, false);
		}

	}
	
	
	private int findCell(EntityPlayer player)
	{
		for (int i = 0; i < player.inventory.getSizeInventory(); ++i)
        {
            ItemStack itemstack = player.inventory.getStackInSlot(i);

            if (this.isCell(itemstack))
            {
                return i;
            }
        }

        return -1; 
	}
	
	private boolean isCell(ItemStack stack)
    {
        if(stack.getItem() == QMDItems.cell)
        {
        	return stack.getMetadata() == CellType.GLUEBALLS.getID();
        }
        return false;
    }
	
	
	
}
