package lach_01298.qmd.item;

import com.google.common.collect.Multimap;
import ic2.api.item.*;
import lach_01298.qmd.config.QMDConfig;
import lach_01298.qmd.util.Util;
import nc.item.NCItem;
import nc.item.energy.*;
import nc.tile.internal.energy.EnergyConnection;
import nc.util.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.*;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.*;
import net.minecraftforge.fml.common.Optional;

import javax.annotation.Nullable;
import java.util.*;


@Optional.InterfaceList({@Optional.Interface(iface = "ic2.api.item.ISpecialElectricItem", modid = "ic2") })
public class ItemDrill extends NCItem implements IChargableItem, ISpecialElectricItem
{

	private float attackSpeed;
	private float attackDamage;
	
	private final long capacity;
	private final int maxTransfer;
	private final EnergyConnection energyConnection;
	private final int energyTier;
	private int energyUsage;
	private int radius;
	private float miningSpeed;
	private int miningLevel;
	
	public ItemDrill(int radius, int capacity, int miningLevel, float miningSpeed, String... tooltip)
	{
		super(tooltip);
		maxStackSize = 1;
		attackSpeed = -2.8f;
		attackDamage = 1;
		this.capacity = capacity;
		this.radius = radius;
		this.miningSpeed = miningSpeed;
		this.miningLevel = miningLevel;
		
		energyUsage = QMDConfig.drill_energy_usage;
		this.maxTransfer = NCMath.toInt(this.capacity);
		this.energyConnection = EnergyConnection.BOTH;
		this.energyTier = 3;
		setHarvestLevel("pickaxe", miningLevel);
		setHarvestLevel("shovel", miningLevel);
		
	}

	@Override
	public float getDestroySpeed(ItemStack stack, IBlockState state)
	{
		IEnergyStorage energy =  stack.getCapability(CapabilityEnergy.ENERGY, null);
		Material material = state.getMaterial();
		if(energy.extractEnergy(energyUsage, true) == energyUsage)
		{
			if (material == Material.IRON || material == Material.ANVIL || material == Material.ROCK)
			{
				 return miningSpeed;
			}
			for (String type : getToolClasses(stack))
	        {
	            if (state.getBlock().isToolEffective(type, state))
	                return miningSpeed;
	        }
		}

		return 1f;
	}
	

	@Override
	public boolean canHarvestBlock(IBlockState state)
    {
		ItemStack stack = new ItemStack(this);
		for (String type : this.getToolClasses(stack))
        {
            if (state.getBlock().isToolEffective(type, state) && state.getBlock().getHarvestLevel(state) <= this.getHarvestLevel(stack, type, null, state))
                return true;
        }
		
		return false;
    }
	
	@Override
	public boolean onBlockDestroyed(ItemStack stack, World world, IBlockState state, BlockPos pos,
			EntityLivingBase entityLiving)
	{
		if (!world.isRemote && (double) state.getBlockHardness(world, pos) != 0.0D)
		{
			if(entityLiving instanceof EntityPlayer)
			{
				EntityPlayer player = (EntityPlayer) entityLiving;
				if(player.isCreative())
				{
					return true;
				}
			}
			
			IEnergyStorage energy =  stack.getCapability(CapabilityEnergy.ENERGY, null);
		
			energy.extractEnergy(energyUsage, false);
		}

		return true;
	}
	
	
	
	@Override
	public int getItemEnchantability()
	{
		return 20;
	}

	@Override
	public boolean isBookEnchantable(ItemStack stack, ItemStack book)
	{
		return true;
	}
	
	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment)
	{
			return enchantment.equals(Enchantments.FORTUNE) || enchantment.equals(Enchantments.SILK_TOUCH);
	}
	
	
	
	@Override
	 public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot)
    {
        Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);

        if (equipmentSlot == EntityEquipmentSlot.MAINHAND)
        {
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Tool modifier", (double)this.attackDamage, 0));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Tool modifier", (double)this.attackSpeed, 0));
        }

        return multimap;
    }

    @Override
    public int getHarvestLevel(ItemStack stack, String toolClass, @javax.annotation.Nullable net.minecraft.entity.player.EntityPlayer player, @javax.annotation.Nullable IBlockState blockState)
    {
    	if(toolClass.equals("pickaxe")||toolClass.equals("shovel"))
    	{
    		IEnergyStorage energy =  stack.getCapability(CapabilityEnergy.ENERGY, null);
    		
    		if(energy.extractEnergy(energyUsage, true) == energyUsage && radius >= 1)
    		{
    			return miningLevel;
    		}
    		else
    		{
    			return 1; //stone mining level
    		}
    	}
    	return -1;
    }

	@Override
	public boolean onBlockStartBreak(ItemStack stack, BlockPos blockPos, EntityPlayer player)
	{
		World world = player.world;
		float hardness = world.getBlockState(blockPos).getBlockHardness(world, blockPos);
		
		IEnergyStorage energy =  stack.getCapability(CapabilityEnergy.ENERGY, null);
		
		if(energy.extractEnergy(energyUsage, true) == energyUsage && radius >= 1)
		{
			for(BlockPos pos :getDiggedBlocks(blockPos,player,radius))
			{
				if(world.getBlockState(pos).getBlockHardness(world, pos) <= hardness*2)
				{
					if(world.getBlockState(pos).getBlockHardness(world, pos) != 0 && Util.mineBlock(world, pos, player) && !player.isCreative())
					{
						energy.extractEnergy(energyUsage, false);
					}
				}
			}

		}
		
		return false;
		
	}
	
	
	public int getRadius()
	{
		return radius;
	}
	

	public Set<BlockPos> getDiggedBlocks(BlockPos pos, EntityPlayer player, int radius)
	{
		Set<BlockPos> postions = new HashSet<BlockPos>();
		RayTraceResult ray = this.rayTrace(player.world, player, false);
		
		if(ray != null)
		{
			EnumFacing facing = ray.sideHit;
			
			if(facing != null)
			{
				switch(facing)
				{
				case UP:
				case DOWN:
					for (BlockPos p : BlockPos.getAllInBoxMutable(pos.add(-radius,0,-radius),pos.add(radius,0,radius)))
					{
						postions.add(p.toImmutable());
					}
					break;
				case NORTH:
				case SOUTH:
					for (BlockPos p : BlockPos.getAllInBoxMutable(pos.add(-radius,-radius,0),pos.add(radius,radius,0)))
					{
						postions.add(p.toImmutable());
					}
					break;
				case EAST:
				case WEST:
					for (BlockPos p : BlockPos.getAllInBoxMutable(pos.add(0,-radius,-radius),pos.add(0,radius,radius)))
					{
						postions.add(p.toImmutable());
					}
					break;
				default:
					break;
				}
				postions.remove(pos.toImmutable());
			}
		}

		return postions;

	}

	@Override
	@Optional.Method(modid = "ic2")
	public IElectricItemManager getManager(ItemStack stack)
	{
		return ElectricItemManager.getElectricItemManager(this);
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
	
	
	@Override
	public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag)
	{
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
	
	
}
