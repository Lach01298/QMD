package lach_01298.qmd.item;

import lach_01298.qmd.particle.Particle;
import nc.item.IInfoItem;
import nc.tab.NCTabs;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemBrakeable extends Item implements IInfoItem
{

	public ItemBrakeable(int durability)
	{
		this.setMaxStackSize(16);
	    this.setMaxDamage(durability);
	  
	}

	@Override
	public void setInfo()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isRepairable()
	{
		return false;
	}
	
	public boolean canApplyAtEnchantingTable(ItemStack stack, net.minecraft.enchantment.Enchantment enchantment)
    {
        return false;
    }

	public boolean isBookEnchantable(ItemStack stack, ItemStack book)
	{
		return false;
	}
	
	
	
}
