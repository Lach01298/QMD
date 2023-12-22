package lach_01298.qmd.item;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public interface IItemMode
{
	public default String getMode(ItemStack stack)
	{
		NBTTagCompound nbt = getStorageNBT(stack);
		if (nbt == null) 
		{
			setStorageNBT(stack);
			nbt = getStorageNBT(stack);
		}
		
		return nbt.getString("mode");
	}
	
	
	public String getDefaultMode();
	
	public List<String> getModes();
	
	public default boolean setMode(ItemStack stack, String mode) 
	{
		if(stack.getItem() instanceof IItemMode)
		{
			NBTTagCompound nbt = getStorageNBT(stack);
			if (nbt != null) 
			{
				if(getModes().contains(mode))
				{
					nbt.setString("mode", mode);
					return true;
				}
			}
		}
		return false;
	}
	
	public static NBTTagCompound getStorageNBT(ItemStack stack) 
	{
		NBTTagCompound nbt = stack.getTagCompound();
		if (nbt == null || !nbt.hasKey("qmd_item_mode")) 
		{
			return null;
		}
		return nbt.getCompoundTag("qmd_item_mode");
	}
	
	public static void setStorageNBT(ItemStack stack) 
	{
		if (!(stack.getItem() instanceof IItemMode)) 
		{
			return;
		}
		
		IItemMode item = (IItemMode) stack.getItem();
		
		NBTTagCompound nbt = stack.getTagCompound();
		if (nbt == null)
		{
			nbt = new NBTTagCompound();
		}
		
		if (!nbt.hasKey("qmd_item_mode")) 
		{
			NBTTagCompound storage =  new NBTTagCompound();
			storage.setString("mode", item.getDefaultMode());
			nbt.setTag("qmd_item_mode",storage);
			stack.setTagCompound(nbt);
		}
		else if(!nbt.getCompoundTag("qmd_item_mode").hasKey("mode"))
		{
			nbt.getCompoundTag("qmd_item_mode").setString("mode", item.getDefaultMode());
			stack.setTagCompound(nbt);
		}
	}
	
	
}
