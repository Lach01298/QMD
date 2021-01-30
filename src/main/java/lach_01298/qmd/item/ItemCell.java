package lach_01298.qmd.item;

import java.util.List;

import javax.annotation.Nullable;

import lach_01298.qmd.enums.MaterialTypes.CellType;
import nc.item.NCItem;
import nc.util.InfoHelper;
import nc.util.Lang;
import nc.util.StackHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;


public class ItemCell extends NCItem implements IItemAmount
{

	public ItemCell()
	{
		setHasSubtypes(true);
		setMaxStackSize(1);
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
	{
		if (isInCreativeTab(tab))
		{
			items.add(new ItemStack(this, 1, 0));
			for (int i = 1; i < CellType.values().length; i++)
			{
				ItemStack stack = new ItemStack(this, 1, i);
				ItemCell cell = (ItemCell) stack.getItem();
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
				return CellType.EMPTY.getCapacity();
			case 1:
				return CellType.ANTIHYDROGEN.getCapacity();
			case 2:
				return CellType.ANTIDEUTERIUM.getCapacity();
			case 3:
				return CellType.ANTITRITIUM.getCapacity();
			case 4:
				return CellType.ANTIHELIUM3.getCapacity();
			case 5:
				return CellType.ANTIHELIUM.getCapacity();
			case 6:
				return CellType.POSITRONIUM.getCapacity();
			case 7:
				return CellType.MUONIUM.getCapacity();
			case 8:
				return CellType.TAUONIUM.getCapacity();
			case 9:
				return CellType.GLUEBALLS.getCapacity();
				
			default: 
				return 0;
			
		}

	}
	
	
	
	@Override
	public String getTranslationKey(ItemStack stack)
	{
		for (int i = 0; i < CellType.values().length; i++)
		{
			if (StackHelper.getMetadata(stack) == i)
			{
				return getTranslationKey() + "." + CellType.values()[i].getName();
			}
			else
			{
				continue;
			}
		}
		return getTranslationKey() + "." + CellType.values()[0].getName();
	}
	

	@Override
	public ItemStack fill(ItemStack stack, int amount, String type)
	{
		
		
		if(stack.getMetadata() == CellType.EMPTY.getID())
		{
			int meta =0;
			for(CellType cellType : CellType.values())
			{
				if(cellType.getName().equals(type))
				{
					meta = cellType.getID();
				}
			}
			ItemStack newStack = new ItemStack(QMDItems.cell,1,meta);
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
			return new ItemStack(QMDItems.cell,1,CellType.EMPTY.getID());
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
