package lach_01298.qmd.item;

import nc.item.IInfoItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemBrakeable extends Item implements IInfoItem
{

	public ItemBrakeable(int durability)
	{
		this.setMaxStackSize(16);
	    this.setMaxDamage(durability);
	    this.setNoRepair();
	}

	@Override
	public void setInfo()
	{
		// TODO Auto-generated method stub
		
	}

	
	@Override
	public boolean isEnchantable(ItemStack stack) 
    {
		return false;
	}
	
	@Override
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair)
    {
        return false;
    }
	
	public boolean isBookEnchantable(ItemStack stack, ItemStack book)
    {
        return false;
    }
	
	public boolean isRepairable()
    {
        return false;
    }
	
}
