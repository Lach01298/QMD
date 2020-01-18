package lach_01298.qmd.item;

import lach_01298.qmd.particle.Particle;
import nc.item.IInfoItem;
import nc.tab.NCTabs;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

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
	
}
