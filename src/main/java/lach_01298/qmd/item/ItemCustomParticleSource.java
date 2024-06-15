package lach_01298.qmd.item;

import lach_01298.qmd.config.QMDConfig;
import lach_01298.qmd.entity.EntityGammaFlash;
import lach_01298.qmd.util.Units;
import nc.capability.radiation.entity.IEntityRads;
import nc.item.NCItem;
import nc.radiation.RadiationHelper;
import nc.util.*;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.*;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.*;

public class ItemCustomParticleSource extends NCItem implements IItemParticleAmount
{

	private final int capacity;
	private ItemStack emptyItem;
	private boolean explode;
	private double size;
	private double radiation;
	
	public ItemCustomParticleSource(int capacity, int stackSize)
	{
		this.capacity = capacity;
		setMaxStackSize(stackSize);
		emptyItem = ItemStack.EMPTY;
		explode = false;
		size = 0;
		radiation = 0;
	}
	
	public ItemCustomParticleSource(int capacity, int stackSize, double size, double radiation)
	{
		this.capacity = capacity;
		setMaxStackSize(stackSize);
		emptyItem = ItemStack.EMPTY;
		explode = true;
		this.size = size;
		this.radiation = radiation;
	}
	
	@Override
	public int getItemCapacity(ItemStack stack)
	{
		return capacity;
	}
	
	
	
	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
	{
		if (isInCreativeTab(tab))
		{
			
			ItemStack stack = new ItemStack(this, 1);
			IItemParticleAmount.fullItem(stack);
			items.add(stack);
			
		}
	}
	
	
	@Override
	public ItemStack getEmptyItem()
	{
		return new ItemStack(emptyItem.getItem(),1,emptyItem.getMetadata());
	}
	
	public void setEmptyItem(ItemStack stack)
	{
		emptyItem = stack;
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
		if(explode)
		{
			ItemStack stack = entityItem.getItem();
			
			if(entityItem.ticksExisted > QMDConfig.cell_lifetime || entityItem.isInLava() ||entityItem.isBurning()||entityItem.isDead)
			{
				explode(entityItem.world, entityItem.getPosition(), stack);
				stack.shrink(stack.getCount());
			
			}
	
			
		}
		return false;
	}
	
	
	public void explode(World world, BlockPos pos, ItemStack stack)
	{
		if (!world.isRemote)
		{

			double s = size;
			double r = radiation;
			
			if (IItemParticleAmount.getCapacity(stack) > 0)
			{
				s *= stack.getCount() * getAmountStored(stack) / (double)IItemParticleAmount.getCapacity(stack);
				r *= stack.getCount() * getAmountStored(stack) / (double)IItemParticleAmount.getCapacity(stack);
			}
			
			
			world.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), (float) s, true);
			world.spawnEntity(new EntityGammaFlash(world, pos.getX(), pos.getY(), pos.getZ(), s));

			Set<EntityLivingBase> entitylist = new HashSet();
			double radius = 128 * Math.sqrt(s);

			entitylist.addAll(world.getEntitiesWithinAABB(EntityLivingBase.class,
					new AxisAlignedBB(pos.getX() - radius, pos.getY() - radius, pos.getZ() - radius,
							pos.getX() + radius, pos.getY() + radius, pos.getZ() + radius)));

			for (EntityLivingBase entity : entitylist)
			{
				IEntityRads entityRads = RadiationHelper.getEntityRadiation(entity);
				if(entityRads != null)
				{
					double rads = Math.min(r, r / pos.distanceSq(entity.posX, entity.posY, entity.posZ));
					entityRads.setRadiationLevel(RadiationHelper.addRadsToEntity(entityRads, entity, rads, false, false, 1));
	
					if (rads >= entityRads.getMaxRads())
					{
						entity.attackEntityFrom(DamageSources.FATAL_RADS, Float.MAX_VALUE);
					}
				}
			}
		}
	}


	
	
}
