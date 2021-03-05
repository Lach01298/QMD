package lach_01298.qmd.item;

import java.awt.Color;
import java.util.List;
import java.util.UUID;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.collect.Multimap;

import ic2.api.item.IElectricItemManager;
import ic2.api.item.IHazmatLike;
import ic2.api.item.ISpecialElectricItem;
import nc.ModCheck;
import nc.item.armor.NCItemArmor;
import nc.item.energy.ElectricItemManager;
import nc.item.energy.IChargableItem;
import nc.item.energy.ItemEnergyCapabilityProvider;
import nc.tile.internal.energy.EnergyConnection;
import nc.util.InfoHelper;
import nc.util.UnitHelper;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.ISpecialArmor;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.common.Optional;

@Optional.InterfaceList({ @Optional.Interface(iface = "ic2.api.item.IHazmatLike", modid = "ic2"),@Optional.Interface(iface = "ic2.api.item.ISpecialElectricItem", modid = "ic2") })
public class ItemHEVSuit extends NCItemArmor implements ISpecialArmor, IHazmatLike, IChargableItem, ISpecialElectricItem
{
    private static final UUID[] ARMOR_MODIFIERS = new UUID[] {UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B6B"), UUID.fromString("D8499B04-0E66-4726-AB29-64469D734E0D"), UUID.fromString("9F3D476D-C118-4544-8365-64846904B48E"), UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150")};

	
	public final double radiationProtection;
	private final long capacity;
	private final int maxTransfer;
	private final EnergyConnection energyConnection;
	private final int energyTier;
	
	
	public ItemHEVSuit(ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn,
			double radiationProtection, TextFormatting infoColor, String... tooltip)
	{
		super(materialIn, renderIndexIn, equipmentSlotIn, infoColor, tooltip);
		this.radiationProtection = radiationProtection;
		
		
		this.capacity = 100000;
		this.maxTransfer = 100000;
		this.energyConnection = EnergyConnection.BOTH;
		this.energyTier = 3;
		
		
	}

	
    public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot)
    {
        Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);

        if (equipmentSlot == this.armorType && this.armorType == EntityEquipmentSlot.LEGS)
        {
        	multimap.put(SharedMonsterAttributes.MOVEMENT_SPEED.getName(), new AttributeModifier(ARMOR_MODIFIERS[equipmentSlot.getIndex()], "Movement speed", 0.25D, 1));
        }

        return multimap;
    }
	
	
	
	@Override
	public ArmorProperties getProperties(EntityLivingBase player, @Nonnull ItemStack armor, DamageSource source,
			double damage, int slot)
	{
		if (source.damageType.equals("radiation") || source.damageType.equals("sulphuric_acid")
				|| source.damageType.equals("acid_burn") || source.damageType.equals("corium_burn")
				|| source.damageType.equals("hot_coolant_burn"))
		{
			return new ArmorProperties(0, radiationProtection, Integer.MAX_VALUE);
		}
		return new ArmorProperties(0, 0, Integer.MAX_VALUE);
	}

	@Override
	public int getArmorDisplay(EntityPlayer player, @Nonnull ItemStack armor, int slot)
	{
		return 0;
	}

	@Override
	public void damageArmor(EntityLivingBase entity, @Nonnull ItemStack stack, DamageSource source, int damage,
			int slot)
	{
		if (ModCheck.ic2Loaded())
		{
			Potion radiation = Potion.getPotionFromResourceLocation("ic2:radiation");
			if (radiation != null && entity.isPotionActive(radiation))
			{
				entity.removePotionEffect(radiation);
			}
		}
	}

	@Override
	public boolean handleUnblockableDamage(EntityLivingBase entity, @Nonnull ItemStack armor, DamageSource source,
			double damage, int slot)
	{
		return source.damageType.equals("radiation") || source.damageType.equals("sulphuric_acid")
				|| source.damageType.equals("acid_burn") || source.damageType.equals("corium_burn")
				|| source.damageType.equals("hot_coolant_burn");
	}

	@Override
	@Optional.Method(modid = "ic2")
	public boolean addsProtection(EntityLivingBase entity, EntityEquipmentSlot slot, ItemStack stack)
	{
		return true;
	}


	
	@Override
	@Optional.Method(modid = "ic2")
	public IElectricItemManager getManager(ItemStack itemStack) 
	{
		return ElectricItemManager.getElectricItemManager(this);
	}
	
	@Override
	public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {
		InfoHelper.infoLine(tooltip, TextFormatting.LIGHT_PURPLE, "Energy Stored: " + UnitHelper.prefix(getEnergyStored(stack), getMaxEnergyStored(stack), 5, "RF"));
		InfoHelper.infoLine(tooltip, TextFormatting.WHITE, "EU Power Tier: " + getEnergyTier(stack));
		super.addInformation(stack, world, tooltip, flag);
	}
	
	@Override
	public boolean showDurabilityBar(ItemStack stack) 
	{
		NBTTagCompound nbt = IChargableItem.getEnergyStorageNBT(stack);
		if (nbt == null || !nbt.hasKey("energy")) 
		{
			return false;
		}
		return nbt.getLong("energy") > 0;
	}
	
	
	@Override
	public double getDurabilityForDisplay(ItemStack stack) 
	{
		return 1D - MathHelper.clamp((double) getEnergyStored(stack) / capacity, 0D, 1D);
	}
	
	
	@Override
	public long getMaxEnergyStored(ItemStack stack)
	{
		return capacity;
	}


	@Override
	public int getMaxTransfer(ItemStack stack)
	{
		return  maxTransfer;
	}


	@Override
	public boolean canReceive(ItemStack stack)
	{
		return energyConnection.canReceive();
	}


	@Override
	public boolean canExtract(ItemStack stack)
	{
		return energyConnection.canExtract();
	}


	@Override
	public EnergyConnection getEnergyConnection(ItemStack stack)
	{
		return energyConnection;
	}


	@Override
	public int getEnergyTier(ItemStack stack)
	{
		return energyTier;
	}
	
	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) 
	{
		return new ItemEnergyCapabilityProvider(stack, capacity, maxTransfer, getEnergyStored(stack), energyConnection, energyTier);
	}
	
	public boolean hasColor(ItemStack stack)
	{
		return true;
	}
	
	public int getColor(ItemStack stack)
	{
		NBTTagCompound nbttagcompound = stack.getTagCompound();

		if (nbttagcompound != null)
		{
			NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");

			if (nbttagcompound1 != null && nbttagcompound1.hasKey("color", 3))
			{
				return nbttagcompound1.getInteger("color");
			}
		}

		return Color.decode("0xFF8B2E").getRGB();

	}
	
	   public void removeColor(ItemStack stack)
	    {
	            NBTTagCompound nbttagcompound = stack.getTagCompound();

	            if (nbttagcompound != null)
	            {
	                NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");

	                if (nbttagcompound1.hasKey("color"))
	                {
	                    nbttagcompound1.removeTag("color");
	                }
	            }
	    }

	public void setColor(ItemStack stack, int color)
	{

		NBTTagCompound nbttagcompound = stack.getTagCompound();

		if (nbttagcompound == null)
		{
			nbttagcompound = new NBTTagCompound();
			stack.setTagCompound(nbttagcompound);
		}

		NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");

		if (!nbttagcompound.hasKey("display", 10))
		{
			nbttagcompound.setTag("display", nbttagcompound1);
		}

		nbttagcompound1.setInteger("color", color);
	}
	
	public boolean hasOverlay(ItemStack stack)
	{
		return true;
	}




}
