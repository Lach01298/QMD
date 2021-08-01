package lach_01298.qmd.item;

import java.util.List;

import javax.annotation.Nullable;

import lach_01298.qmd.QMDDamageSources;
import lach_01298.qmd.config.QMDConfig;
import lach_01298.qmd.entity.EntityGluonBeam;
import lach_01298.qmd.enums.MaterialTypes.CellType;
import nc.capability.radiation.entity.IEntityRads;
import nc.item.IInfoItem;
import nc.radiation.RadiationHelper;
import nc.util.InfoHelper;
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
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemGluonGun extends ItemGun
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
	
	
	
}
