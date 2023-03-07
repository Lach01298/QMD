package lach_01298.qmd.item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import lach_01298.qmd.enums.IItemCapacity;
import lach_01298.qmd.enums.MaterialTypes.CellType;
import nc.item.IInfoItem;
import nc.item.NCItem;
import nc.item.energy.IChargableItem;
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

public interface IItemParticleAmount 
{	

	/** gets the items hardcoded capacity */
	public int getItemCapacity(ItemStack stack);

	/** gets the items capacity by reading its nbt data*/
	public static int getCapacity(ItemStack stack)
	{
		NBTTagCompound nbt = getStorageNBT(stack);
		if (nbt == null) {
			return 0;
		}
		
		return nbt.getInteger("particle_capacity");
	}
	
	
	public default int getAmountStored(ItemStack stack)
	{
		NBTTagCompound nbt = getStorageNBT(stack);
		if (nbt == null) {
			return 0;
		}
		return nbt.getInteger("particle_amount");
	}

	public default void setAmountStored(ItemStack stack, int amount) 
	{
		if(stack.getItem() instanceof IItemParticleAmount)
		{
			NBTTagCompound nbt = getStorageNBT(stack);
			if (nbt != null) 
			{
				if(amount< getCapacity(stack))
				{
					nbt.setInteger("particle_amount", amount);
				}
				else
				{
					nbt.setInteger("particle_amount", getCapacity(stack));
				}
			}
		}	
	}
	
	
	public default ItemStack fill(ItemStack stack, int amount, String type)
	{	
		if(getAmountStored(stack) + amount <= getCapacity(stack))
		{
			setAmountStored(stack,getAmountStored(stack)+amount);
		}
		
		return stack;
	}
	
	public default ItemStack use(ItemStack stack, int amount)
	{
		if(getAmountStored(stack) > amount)
		{
			setAmountStored(stack,getAmountStored(stack)-amount);
		}
		else if (getAmountStored(stack) == amount)
		{
			return   getEmptyItem();
		}
		
		return stack;
	}
	
	public default ItemStack getEmptyItem()
	{
		return  ItemStack.EMPTY;
	}
	
	public default boolean isEmptyItem(ItemStack stack)
	{	
		return  stack == getEmptyItem() || getAmountStored(stack) <= 0;
	}
	
	
	
	public static ItemStack cleanNBT(ItemStack stack)
	{
		ItemStack newStack = stack.copy();
		newStack.setTagCompound(null);

		return newStack;
	}
	
	
	public static ItemStack fullItem(ItemStack stack)
	{
		if(stack.getItem() instanceof IItemParticleAmount)
		{
			setStorageNBT(stack);
			IItemParticleAmount item = (IItemParticleAmount) stack.getItem();
			item.setAmountStored(stack, item.getItemCapacity(stack));
		}
		return stack;
	}
	

	
	public static NBTTagCompound getStorageNBT(ItemStack stack) 
	{
		NBTTagCompound nbt = stack.getTagCompound();
		if (nbt == null || !nbt.hasKey("particle_storage")) 
		{
			return null;
		}
		return nbt.getCompoundTag("particle_storage");
	}

	public static void setStorageNBT(ItemStack stack) 
	{
		if (!(stack.getItem() instanceof IItemParticleAmount)) 
		{
			return;
		}
		
		IItemParticleAmount item = (IItemParticleAmount) stack.getItem();
		
		NBTTagCompound nbt = stack.getTagCompound();
		if (nbt == null)
		{
			nbt = new NBTTagCompound();
		}
		
		if (!nbt.hasKey("particle_storage")) 
		{
			NBTTagCompound storage =  new NBTTagCompound();
			storage.setInteger("particle_amount", 0);
			storage.setInteger("particle_capacity", item.getItemCapacity(stack));
			nbt.setTag("particle_storage",storage);
			stack.setTagCompound(nbt);
		}
		else if(!nbt.getCompoundTag("particle_storage").hasKey("particle_capacity"))
		{
			nbt.getCompoundTag("particle_storage").setInteger("particle_capacity", item.getItemCapacity(stack));
			stack.setTagCompound(nbt);
		}
	}
	
}
