package lach_01298.qmd.item;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Nullable;

import lach_01298.qmd.config.QMDConfig;
import lach_01298.qmd.entity.EntityGammaFlash;
import lach_01298.qmd.enums.MaterialTypes.ExoticCellType;
import nc.capability.radiation.entity.IEntityRads;
import nc.item.energy.ItemEnergy;
import nc.radiation.RadiationHelper;
import nc.tile.internal.energy.EnergyConnection;
import nc.util.DamageSources;
import nc.util.InfoHelper;
import nc.util.Lang;
import nc.util.StackHelper;
import nc.util.UnitHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;


public class ItemCell extends ItemEnergy implements ITickItem
{

	
	private long lastUpdateTime;

	public ItemCell(int capacity)
	{
		super(capacity * 20 * QMDConfig.cell_power, capacity * 20 * QMDConfig.cell_power, 6, EnergyConnection.IN);
		setHasSubtypes(true);
		lastUpdateTime = 0;
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
	{
		if (isInCreativeTab(tab))
		{
			items.add(new ItemStack(this, 1, 0));
			for (int i = 1; i < ExoticCellType.values().length; i++)
			{
				ItemStack stack = new ItemStack(this, 1, i);
				ItemCell cell = (ItemCell) stack.getItem();
				setEnergyStored(stack, getMaxEnergyStored(stack));
				items.add(stack);
			}
		}
	}

	@Override
	public boolean isEnchantable(ItemStack stack) 
    {
		return false;
	}
	
	@Override
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair)
    {
        return false;
    }
	
	public boolean isBookEnchantable(ItemStack stack, ItemStack book)
    {
        return false;
    }
	
	public boolean isRepairable()
    {
        return false;
    }
	
	
	
	@Override
	public String getTranslationKey(ItemStack stack)
	{
		for (int i = 0; i < ExoticCellType.values().length; i++)
		{
			if (StackHelper.getMetadata(stack) == i)
			{
				return getTranslationKey() + "." + ExoticCellType.values()[i].getName();
			}
			else
			{
				continue;
			}
		}
		return getTranslationKey() + "." + ExoticCellType.values()[0].getName();
	}
	
	@Override
	public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {
		if(stack.getMetadata() == ExoticCellType.EMPTY.getID())
		{
			InfoHelper.infoLine(tooltip, TextFormatting.RED, Lang.localise("info.qmd.item.cell_charge_warning"));
		}
		else
		{
			InfoHelper.infoLine(tooltip, TextFormatting.YELLOW, Lang.localise("info.qmd.item.energy_used",UnitHelper.prefix(QMDConfig.cell_power, 5, "RF/t")));
			InfoHelper.infoLine(tooltip, TextFormatting.RED, Lang.localise("info.qmd.item.cell_warning"));
		}
		
	
		super.addInformation(stack, world, tooltip, flag);
	}

	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
	{
		if (stack.getTagCompound() == null)
		{
			return;
		}

		Long tickTime =worldIn.getTotalWorldTime();
		if(stack.getTagCompound().hasKey("lastTickTime"))
		{
			if (tickTime != stack.getTagCompound().getLong("lastTickTime"))
			{
				if (stack.getMetadata() != ExoticCellType.EMPTY.getID())
				{
					int timeSinceTick = (int) (tickTime - stack.getTagCompound().getLong("lastTickTime"));
					IEnergyStorage energy = stack.getCapability(CapabilityEnergy.ENERGY, null);
					if (timeSinceTick*QMDConfig.cell_power * stack.getCount() > energy.extractEnergy(timeSinceTick*QMDConfig.cell_power * stack.getCount(), false))
					{
						explode(worldIn, entityIn.getPosition(), stack);
						stack.shrink(stack.getCount());
					}
					stack.getTagCompound().setLong("lastTickTime", worldIn.getWorldInfo().getWorldTotalTime());
				}
			}
		}
		else
		{
			if (stack.getMetadata() != ExoticCellType.EMPTY.getID())
			{
				stack.getTagCompound().setLong("lastTickTime", tickTime);
			}
		}
	}

	@Override
	public boolean onEntityItemUpdate(EntityItem entityItem)
	{
		ItemStack stack = entityItem.getItem();

		if (stack.getTagCompound() == null)
		{
			return false;
		}
		Long tickTime =entityItem.world.getWorldInfo().getWorldTotalTime();
		
		if(stack.getTagCompound().hasKey("lastTickTime"))
		{
			if (tickTime != stack.getTagCompound().getLong("lastTickTime"))
			{
				if (stack.getMetadata() != ExoticCellType.EMPTY.getID())
				{
					int timeSinceTick = (int) (tickTime - stack.getTagCompound().getLong("lastTickTime"));
					IEnergyStorage energy = stack.getCapability(CapabilityEnergy.ENERGY, null);
					if (timeSinceTick*QMDConfig.cell_power * stack.getCount() > energy.extractEnergy(timeSinceTick*QMDConfig.cell_power * stack.getCount(), false))
					{

						explode(entityItem.world, entityItem.getPosition(), stack);
						stack.shrink(stack.getCount());
					}
					stack.getTagCompound().setLong("lastTickTime", entityItem.world.getWorldInfo().getWorldTotalTime());
				}
			}
		}
		else
		{
			if (stack.getMetadata() != ExoticCellType.EMPTY.getID())
			{
				stack.getTagCompound().setLong("lastTickTime", tickTime);
			}
		}
		return false;
	}

	@Override
	public void updateTick(ItemStack stack, TileEntity tile, long tickTime)
	{
		if (stack.getTagCompound() == null)
		{
			return;
		}
		if(stack.getTagCompound().hasKey("lastTickTime"))
		{
			if (tickTime != stack.getTagCompound().getLong("lastTickTime"))
			{

				if (stack.getMetadata() != ExoticCellType.EMPTY.getID())
				{
					int timeSinceTick = (int) (tickTime - stack.getTagCompound().getLong("lastTickTime"));
					IEnergyStorage energy = stack.getCapability(CapabilityEnergy.ENERGY, null);
					if (timeSinceTick*QMDConfig.cell_power * stack.getCount() > energy.extractEnergy(timeSinceTick*QMDConfig.cell_power * stack.getCount(), false))
					{

						explode(tile.getWorld(), tile.getPos(), stack);
						stack.shrink(stack.getCount());
					}
					stack.getTagCompound().setLong("lastTickTime", tickTime);
				}
			}
		
		}
		else
		{
			if (stack.getMetadata() != ExoticCellType.EMPTY.getID())
			{
				stack.getTagCompound().setLong("lastTickTime", tickTime);
			}
		}
		
		
	}

	public void explode(World world, BlockPos pos, ItemStack stack)
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

		}

		 world.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), (float)size*10f, true);
		world.spawnEntity(new EntityGammaFlash(world, pos.getX(), pos.getY(), pos.getZ(), size));

		Set<EntityLivingBase> entitylist = new HashSet();
		double radius = 128 * Math.sqrt(size);

		entitylist.addAll(world.getEntitiesWithinAABB(EntityLivingBase.class,
				new AxisAlignedBB(pos.getX() - radius, pos.getY() - radius, pos.getZ() - radius, pos.getX() + radius,
						pos.getY() + radius, pos.getZ() + radius)));

		for (EntityLivingBase entity : entitylist)
		{
			double rads = (1000 * 32 * 32 * size) / pos.distanceSq(entity.posX, entity.posY, entity.posZ);
			IEntityRads entityRads = RadiationHelper.getEntityRadiation(entity);
			entityRads.setRadiationLevel(RadiationHelper.addRadsToEntity(entityRads, entity, rads, false, false, 1));
			
			if (rads >= entityRads.getMaxRads())
			{
				entity.attackEntityFrom(DamageSources.FATAL_RADS, Float.MAX_VALUE);
			}
		}
	}

}
