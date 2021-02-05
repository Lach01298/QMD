package lach_01298.qmd.item;

import java.util.List;

import javax.annotation.Nullable;

import lach_01298.qmd.enums.MaterialTypes.CanisterType;
import nc.item.NCItem;
import nc.util.InfoHelper;
import nc.util.Lang;
import nc.util.StackHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;


public class ItemCanister extends NCItem implements IItemAmount
{

	public ItemCanister()
	{
		setHasSubtypes(true);
		setMaxStackSize(16);
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
	{
		if (isInCreativeTab(tab))
		{
			items.add(new ItemStack(this, 1, 0));
			for (int i = 1; i < CanisterType.values().length; i++)
			{
				ItemStack stack = new ItemStack(this, 1, i);
				ItemCanister Canister = (ItemCanister) stack.getItem();
				setAmountStored(stack, getCapacity(stack));
				items.add(stack);
			}
		}
	}
		
	
	@Override
	public int getCapacity(ItemStack stack)
	{
		switch(stack.getMetadata())
		{
			case 0:
				return CanisterType.EMPTY.getCapacity();
			case 1:
				return CanisterType.HYDROGEN.getCapacity();
			case 2:
				return CanisterType.DEUTERIUM.getCapacity();
			case 3:
				return CanisterType.TRITIUM.getCapacity();
			case 4:
				return CanisterType.HELIUM3.getCapacity();
			case 5:
				return CanisterType.HELIUM.getCapacity();
			case 6:
				return CanisterType.DIBORANE.getCapacity();
				
			default: 
				return 0;
		}

	}
	
	
	
	@Override
	public String getTranslationKey(ItemStack stack)
	{
		for (int i = 0; i < CanisterType.values().length; i++)
		{
			if (StackHelper.getMetadata(stack) == i)
			{
				return getTranslationKey() + "." + CanisterType.values()[i].getName();
			}
			else
			{
				continue;
			}
		}
		return getTranslationKey() + "." + CanisterType.values()[0].getName();
	}
	

	@Override
	public ItemStack fill(ItemStack stack, int amount, String type)
	{
		
		
		if(stack.getMetadata() == CanisterType.EMPTY.getID())
		{
			int meta =0;
			for(CanisterType canisterType : CanisterType.values())
			{
				if(canisterType.getName().equals(type))
				{
					meta = canisterType.getID();
				}
			}
			ItemStack newStack = new ItemStack(QMDItems.canister,1,meta);
			setAmountStored(newStack,amount);
			return newStack;
		}
		
		if(getAmountStored(stack) + amount <= getCapacity(stack))
		{
			setAmountStored(stack,getAmountStored(stack)+amount);
		}
		
		
		return stack;
	}
	
	@Override
	public ItemStack empty(ItemStack stack, int amount)
	{
		
		if(getAmountStored(stack) > amount)
		{
			setAmountStored(stack,getAmountStored(stack)-amount);
		}
		else if (getAmountStored(stack) == amount)
		{
			return new ItemStack(QMDItems.canister,1,CanisterType.EMPTY.getID());
		}
		
		return stack;
	}
	

	@Override
	public double getDurabilityForDisplay(ItemStack stack) 
	{
		return 1D - MathHelper.clamp((double) getAmountStored(stack) / getCapacity(stack), 0D, 1D);
	}
	
	@Override
	public boolean showDurabilityBar(ItemStack stack) 
	{
		return getAmountStored(stack) > 0;
	}
	
	@Override
	public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag)
	{
		InfoHelper.infoLine(tooltip, TextFormatting.DARK_GREEN,Lang.localise("info.qmd.item.amount", getAmountStored(stack), getCapacity(stack)));
	
		super.addInformation(stack, world, tooltip, flag);
	}
	
}
