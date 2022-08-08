package lach_01298.qmd.item;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Nullable;

import lach_01298.qmd.entity.EntityGammaFlash;
import lach_01298.qmd.enums.MaterialTypes.CellType;
import lach_01298.qmd.util.Units;
import nc.capability.radiation.entity.IEntityRads;
import nc.item.NCItem;
import nc.radiation.RadiationHelper;
import nc.util.DamageSources;
import nc.util.InfoHelper;
import nc.util.Lang;
import nc.util.StackHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;



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
	public ItemStack use(ItemStack stack, int amount)
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
		InfoHelper.infoLine(tooltip, TextFormatting.DARK_GREEN,Lang.localise("info.qmd.item.amount", Units.getSIFormat(getAmountStored(stack), "pu"), Units.getSIFormat(getCapacity(stack),"pu")));
	
		super.addInformation(stack, world, tooltip, flag);
	}
	
	
	@Override
	public boolean onEntityItemUpdate(EntityItem entityItem)
	{
		ItemStack stack = entityItem.getItem();

		
		
		if(stack.getMetadata() != 0)
		{

			if(entityItem.ticksExisted > 120 || entityItem.isInLava() ||entityItem.isBurning()||entityItem.isDead)
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

			world.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), (float) (size*5f), true);
			world.spawnEntity(new EntityGammaFlash(world, pos.getX(), pos.getY(), pos.getZ(), size));

			Set<EntityLivingBase> entitylist = new HashSet();
			double radius = 128 * Math.sqrt(size);

			entitylist.addAll(world.getEntitiesWithinAABB(EntityLivingBase.class,
					new AxisAlignedBB(pos.getX() - radius, pos.getY() - radius, pos.getZ() - radius,
							pos.getX() + radius, pos.getY() + radius, pos.getZ() + radius)));

			for (EntityLivingBase entity : entitylist)
			{
				double rads = (100 * 32 * 32 * size) / pos.distanceSq(entity.posX, entity.posY, entity.posZ);
				IEntityRads entityRads = RadiationHelper.getEntityRadiation(entity);
				entityRads
						.setRadiationLevel(RadiationHelper.addRadsToEntity(entityRads, entity, rads, false, false, 1));

				if (rads >= entityRads.getMaxRads())
				{
					entity.attackEntityFrom(DamageSources.FATAL_RADS, Float.MAX_VALUE);
				}
			}
		}
	}
	
	
	
	
	
	
}
