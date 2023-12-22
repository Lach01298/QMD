package lach_01298.qmd.item;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import lach_01298.qmd.QMDDamageSources;
import lach_01298.qmd.config.QMDConfig;
import lach_01298.qmd.entity.EntityGluonBeam;
import lach_01298.qmd.enums.MaterialTypes.CellType;
import lach_01298.qmd.util.Util;
import nc.capability.radiation.entity.IEntityRads;
import nc.radiation.RadiationHelper;
import nc.util.InfoHelper;
import nc.util.Lang;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ItemGluonGun extends ItemGun implements IItemMode
{
	
	
	private EntityGluonBeam beam;

	
	public ItemGluonGun(String... tooltip)
	{
		super(tooltip); 
		
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
		
		if(player.isSneaking())
		{
			if(!world.isRemote)
			{
				if(getMode(itemstack).equals("breaking"))
				{
					setMode(itemstack,"silk_touch");
					player.sendMessage(new TextComponentString(Lang.localise("info.qmd.item.mode.switch") + TextFormatting.DARK_GREEN + Lang.localise("info.qmd.item.mode.silk_touch")));
				}
				else
				{
					setMode(itemstack,"breaking");
					player.sendMessage(new TextComponentString(Lang.localise("info.qmd.item.mode.switch") + TextFormatting.DARK_GREEN + Lang.localise("info.qmd.item.mode.breaking")));
				}
			}
			
			return new ActionResult<ItemStack>(EnumActionResult.PASS, itemstack);
		}

		if(findCell(player) >=0 )
		{
			ItemStack cell = player.inventory.getStackInSlot(findCell(player));
			ItemCell itemCell = (ItemCell) cell.getItem();
			if(itemCell.getAmountStored(cell) < QMDConfig.gluon_particle_usage)
			{
				return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemstack);
			}
			
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
    			if(itemCell.getAmountStored(cell) < QMDConfig.gluon_particle_usage)
    			{
    				user.stopActiveHand();
    				return;
    			}
    			
    			
    			if(!player.isCreative())
    			{
    				player.inventory.setInventorySlotContents(findCell(player),itemCell.use(cell, QMDConfig.gluon_particle_usage));	
    			}
    			
    			World world = user.getEntityWorld();
    	    	RayTraceResult lookingAt = rayTrace(user,QMDConfig.gluon_range,1.0f,true,true);

    	    	if(!world.isRemote)
    			{
    				
    		        if (lookingAt != null && lookingAt.typeOfHit == RayTraceResult.Type.BLOCK) 
    		        {
    		            BlockPos pos = lookingAt.getBlockPos();
						
    		            String mode = getMode(stack);
    		            if(mode.equals("breaking"))
    		            {
    		            	Util.mineBlock(world, pos, player, 4, false, true);
    		            }
    		            else if(mode.equals("silk_touch"))
    		            {
    		            	Util.mineBlock(world, pos, player, 0, true, true);
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
    					entity.attackEntityFrom(QMDDamageSources.causeGluonGunDamage(beam,player), (float) QMDConfig.gluon_damage);
    				}

					if (lookingAt != null)
					{
						beam.setLength(lookingAt.hitVec.distanceTo(user.getPositionVector().add(0, user.getEyeHeight(), 0)));
					}
				}
			}
			else
			{
				user.stopActiveHand();
			}
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
	
	
	@Override
	public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag)
	{
		InfoHelper.infoLine(tooltip, TextFormatting.DARK_GREEN,Lang.localise("info.qmd.item.mode", Lang.localise("info.qmd.item.mode."+ getMode(stack))));
	
		super.addInformation(stack, world, tooltip, flag);
	}




	@Override
	public String getDefaultMode()
	{
		
		return "breaking";
	}




	@Override
	public List<String> getModes()
	{
		List<String> modes = new ArrayList<String>();
		modes.add("breaking");
		modes.add("silk_touch");
		return modes;
	}






	
	
}
