package lach_01298.qmd.item;

import lach_01298.qmd.config.QMDConfig;
import lach_01298.qmd.entity.EntityAntimatterProjectile;
import lach_01298.qmd.enums.MaterialTypes.CellType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemAntimatterLauncher extends ItemGun
{
	
	
	public ItemAntimatterLauncher(String... tooltip)
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
			Integer color = 0;
			if(cell.getMetadata() == CellType.ANTIHYDROGEN.getID())
			{
				damage = 1.0f;
				color = 0xB37AC4;
			}
			else if(cell.getMetadata() == CellType.ANTIDEUTERIUM.getID())
			{
				damage = 2.0f;
				color = 0x9E6FEF;
			}
			else if(cell.getMetadata() == CellType.ANTITRITIUM.getID())
			{
				damage = 3.0f;
				color = 0x5DBBD6;
			}
			else if(cell.getMetadata() == CellType.ANTIHELIUM3.getID())
			{
				damage = 3.0f;
				color = 0xCBBB67;
			}
			else if(cell.getMetadata() == CellType.ANTIHELIUM.getID())
			{
				damage = 4.0f;
				color = 0xC57B81;
			}
			
			
			ItemCell itemCell = (ItemCell) cell.getItem();
			if(itemCell.getAmountStored(cell) < QMDConfig.antimatter_launcher_particle_usage)
			{
				return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemstack);
			}

			player.inventory.setInventorySlotContents(findCell(player),itemCell.use(cell, QMDConfig.antimatter_launcher_particle_usage));
			player.getCooldownTracker().setCooldown(this, QMDConfig.antimatter_launcher_cool_down);
			
			if(!world.isRemote)
			{
				EntityAntimatterProjectile projectile = new EntityAntimatterProjectile(world, player,damage, color);	
				float velocity = 3.0F;
				projectile.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, velocity, 1.0F);
				world.spawnEntity(projectile);
				
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
        	return stack.getMetadata() == CellType.ANTIHYDROGEN.getID() || stack.getMetadata() == CellType.ANTIDEUTERIUM.getID() || stack.getMetadata() == CellType.ANTITRITIUM.getID() || stack.getMetadata() == CellType.ANTIHELIUM3.getID() || stack.getMetadata() == CellType.ANTIHELIUM.getID();
        }
        return false;
    }
	


}
