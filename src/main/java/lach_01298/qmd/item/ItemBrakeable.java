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
	    setNoRepair();
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
	
	
	
}
