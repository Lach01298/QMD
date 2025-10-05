package lach_01298.qmd.item;

import lach_01298.qmd.config.QMDConfig;
import lach_01298.qmd.enums.MaterialTypes.CellType;
import lach_01298.qmd.util.Units;
import lach_01298.qmd.util.Util;
import nc.item.NCItem;
import nc.util.InfoHelper;
import nc.util.Lang;
import nc.util.StackHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;



public class ItemCell extends NCItem implements IItemParticleAmount
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
				IItemParticleAmount.fullItem(stack);
				items.add(stack);
			}
		}
	}
	
	
	@Override
	public int getItemCapacity(ItemStack stack)
	{
		if (stack.getItem() == this)
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
			}
		}
		return 0;
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
		
		if(getAmountStored(stack) + amount <= getItemCapacity(stack))
		{
			setAmountStored(stack,getAmountStored(stack)+amount);
		}
		
		
		return stack;
	}
	
	@Override
	public ItemStack getEmptyItem()
	{
		return new ItemStack(QMDItems.cell,1,CellType.EMPTY.getID());
	}
	

	@Override
	public double getDurabilityForDisplay(ItemStack stack)
	{
		return 1D - MathHelper.clamp((double) getAmountStored(stack) / getItemCapacity(stack), 0D, 1D);
	}
	
	@Override
	public boolean showDurabilityBar(ItemStack stack)
	{
		return getAmountStored(stack) > 0;
	}
	
	@Override
	public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag)
	{
		InfoHelper.infoLine(tooltip, TextFormatting.DARK_GREEN,Lang.localize("info.qmd.item.amount", Units.getSIFormat(getAmountStored(stack), "pu"), Units.getSIFormat(getItemCapacity(stack),"pu")));
	
		super.addInformation(stack, world, tooltip, flag);
	}
	
	
	@Override
	public boolean onEntityItemUpdate(EntityItem entityItem)
	{
		if(QMDConfig.cell_lifetime > 0)
		{
			ItemStack stack = entityItem.getItem();
			
			if(stack.getMetadata() != 0)
			{
	
				if(entityItem.ticksExisted > QMDConfig.cell_lifetime || entityItem.isInLava() ||entityItem.isBurning()||entityItem.isDead)
				{
					explode(entityItem.world, entityItem.getPositionVector(), stack);
					stack.shrink(stack.getCount());
				
				}
	
			}
		}
		return false;
	}
	
	
	
	public void explode(World world, Vec3d pos, ItemStack stack)
	{
		if (!world.isRemote)
		{

			double size = 1;
			switch (stack.getMetadata())
			{
			case 1:
				size = 1;
				break;
			case 2:
				size = 2;
				break;
			case 3:
			case 4:
				size = 3;
				break;
			case 5:
				size = 4;
				break;
			case 6:
				size = 0.00054;
				break;
			case 7:
				size = 0.11;
				break;
			case 8:
				size = 1.9;
				break;
			case 9:
				size = 1.8;

			}

			if (IItemParticleAmount.getCapacity(stack) > 0)
			{
				size *= getAmountStored(stack)/(double)IItemParticleAmount.getCapacity(stack);
			}
			
			
			Util.createGammaFlash(world, pos, size, (float) (size*QMDConfig.cell_explosion_size), QMDConfig.cell_radiation * size);
			
			
			
		}
	}
	
	
	
	
	
	
}
