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
		}
			
		//TODO
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
