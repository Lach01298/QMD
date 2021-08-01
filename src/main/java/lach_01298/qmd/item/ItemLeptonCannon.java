package lach_01298.qmd.item;

import lach_01298.qmd.QMDDamageSources;
import lach_01298.qmd.config.QMDConfig;
import lach_01298.qmd.entity.EntityLeptonBeam;
import lach_01298.qmd.enums.MaterialTypes.CellType;
import lach_01298.qmd.sound.QMDSounds;
import nc.capability.radiation.entity.IEntityRads;
import nc.item.IInfoItem;
import nc.radiation.RadiationHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class ItemLeptonCannon extends ItemGun
{
	
	
	public ItemLeptonCannon(String... tooltip)
	{
		super(tooltip);
	}


	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
	{
		ItemStack itemstack = player.getHeldItem(hand);
		
		if(findCell(player) >=0 )
		{
			ItemStack cell = player.inventory.getStackInSlot(findCell(player));
			
			float damage = 0;
			double radiation = 0;
			double range = 0;
			Integer color = 0;
			if(cell.getMetadata() == CellType.POSITRONIUM.getID())
			{
				damage = (float) QMDConfig.lepton_damage[0];
				radiation = QMDConfig.lepton_radiation[0];
				range = QMDConfig.lepton_range[0];
				color = 0xababab;
			}
			else if(cell.getMetadata() == CellType.MUONIUM.getID())
			{
				damage = (float) QMDConfig.lepton_damage[1];
				radiation  = QMDConfig.lepton_radiation[1];
				range = QMDConfig.lepton_range[1];
				color = 0x6357ff;
			}
			else if(cell.getMetadata() == CellType.TAUONIUM.getID())
			{
				damage = (float) QMDConfig.lepton_damage[2];
				radiation = QMDConfig.lepton_radiation[2];
				range = QMDConfig.lepton_range[2];
				color = 0xff7e00;
			}
			
			
			ItemCell itemCell = (ItemCell) cell.getItem();
			player.inventory.setInventorySlotContents(findCell(player),itemCell.empty(cell, 1));
			
			
			player.getCooldownTracker().setCooldown(this, QMDConfig.lepton_cool_down);
			
			RayTraceResult lookingAt = rayTrace(player, range, 1.0f, false,true);
			
			if (!world.isRemote)
			{
				EntityLeptonBeam entityBeam;
				if(lookingAt != null)
				{
					double length = lookingAt.hitVec.distanceTo(player.getPositionVector().add(0, player.eyeHeight, 0));
					if(length > range)
					{
						length = range;
					}
					entityBeam = new EntityLeptonBeam(world, player, length, hand, color);
				}
				else
				{
					entityBeam = new EntityLeptonBeam(world, player, range, hand, color);
				}
				world.spawnEntity(new EntityLeptonBeam(world, player, range, hand, color));
				
				
				
				if (lookingAt != null && lookingAt.typeOfHit == RayTraceResult.Type.ENTITY)
				{
					Entity entity = lookingAt.entityHit;

					if (entity instanceof EntityLivingBase)
					{
						IEntityRads entityRads = RadiationHelper.getEntityRadiation((EntityLivingBase) entity);
						entityRads.setRadiationLevel(RadiationHelper.addRadsToEntity(entityRads, (EntityLivingBase) entity,
								radiation, false, false, 1));
					}

					entity.attackEntityFrom(QMDDamageSources.causeLeptonCannonDamage(entityBeam, player), damage);
				}
				world.playSound(null,player.posX, player.posY, player.posZ, QMDSounds.lepton_cannon, SoundCategory.NEUTRAL, 0.2f, 1.0f);
			}

			return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
		}
		
		return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemstack);
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
        	return stack.getMetadata() == CellType.POSITRONIUM.getID() || stack.getMetadata() == CellType.MUONIUM.getID() || stack.getMetadata() == CellType.TAUONIUM.getID();
        }
        return false;
    }
	


}
