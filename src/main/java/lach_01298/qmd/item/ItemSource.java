package lach_01298.qmd.item;

import lach_01298.qmd.enums.MaterialTypes.SourceType;
import lach_01298.qmd.util.Units;
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

import javax.annotation.Nullable;
import java.util.List;


public class ItemSource extends NCItem implements IItemParticleAmount
{

	public ItemSource()
	{
		setHasSubtypes(true);
		setMaxStackSize(16);
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
	{
		if (isInCreativeTab(tab))
		{
			for (int i = 0; i < SourceType.values().length; i++)
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
					return SourceType.TUNGSTEN_FILAMENT.getCapacity();
				case 1:
					return SourceType.SODIUM_22.getCapacity();
				case 2:
					return SourceType.COBALT_60.getCapacity();
				case 3:
					return SourceType.IRIDIUM_192.getCapacity();
				case 4:
					return SourceType.CALCIUM_48.getCapacity();
			}
		}
		return 0;
	}
	
	
	@Override
	public String getTranslationKey(ItemStack stack)
	{
		for (int i = 0; i < SourceType.values().length; i++)
		{
			if (StackHelper.getMetadata(stack) == i)
			{
				return getTranslationKey() + "." + SourceType.values()[i].getName();
			}
			else
			{
				continue;
			}
		}
		return getTranslationKey() + "." + SourceType.values()[0].getName();
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
	
}
