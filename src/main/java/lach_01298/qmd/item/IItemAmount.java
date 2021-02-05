package lach_01298.qmd.item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import lach_01298.qmd.enums.IItemCapacity;
import lach_01298.qmd.enums.MaterialTypes.CellType;
import nc.item.IInfoItem;
import nc.item.NCItem;
import nc.util.InfoHelper;
import nc.util.Lang;
import nc.util.StackHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public interface IItemAmount 
{	

	
	public  int getCapacity(ItemStack stack);

	
	public default ItemStack fill(ItemStack stack, int amount, String type)
	{	
		if(getAmountStored(stack) + amount <= getCapacity(stack))
		{
			setAmountStored(stack,getAmountStored(stack)+amount);
		}
		
		return stack;
	}
	
	public default ItemStack empty(ItemStack stack, int amount)
	{
		if(getAmountStored(stack) > amount)
		{
			setAmountStored(stack,getAmountStored(stack)-amount);
		}
		else if (getAmountStored(stack) == amount)
		{
			return  ItemStack.EMPTY;
		}
		
		return stack;
	}
	
	
	public static ItemStack cleanNBT(ItemStack stack)
	{
		ItemStack newStack = stack.copy();
		newStack.setTagCompound(null);

		return newStack;
	}
	
	
	public static ItemStack fullItem(ItemStack stack)
	{
		if(stack.getItem() instanceof IItemAmount)
		{
			IItemAmount item = (IItemAmount) stack.getItem();
			item.setAmountStored(stack, item.getCapacity(stack));
		}
		return stack;
	}
	
	
	
	public default int getAmountStored(ItemStack stack)
	{
		NBTTagCompound nbt = getStorageNBT(stack);
		if (nbt == null) {
			return 0;
		}
		return nbt.getInteger("amount");
	}

	public default void setAmountStored(ItemStack stack, int amount) 
	{
		
		if(stack.getItem() instanceof IItemAmount)
		{
			NBTTagCompound nbt = getStorageNBT(stack);
			if (nbt != null && nbt.hasKey("amount")) 
			{
				if(amount< getCapacity(stack))
				{
					nbt.setInteger("amount", amount);
				}
				else
				{
					nbt.setInteger("amount", getCapacity(stack));
				}
			}
			else
			{
				nbt = new NBTTagCompound();
				NBTTagCompound storage =  new NBTTagCompound();
				if(amount< getCapacity(stack))
				{
					storage.setInteger("amount", amount);
				}
				else
				{
					storage.setInteger("amount", getCapacity(stack));
				}
				nbt.setTag("storage",storage);
				stack.setTagCompound(nbt);
			}
		}	
	}

	public static NBTTagCompound getStorageNBT(ItemStack stack) 
	{
		NBTTagCompound nbt = stack.getTagCompound();
		if (nbt == null || !nbt.hasKey("storage")) 
		{
			return null;
		}
		return nbt.getCompoundTag("storage");
	}
	
}
