package lach_01298.qmd.util;

import lach_01298.qmd.QMD;
import lach_01298.qmd.entity.EntityGammaFlash;
import nc.capability.radiation.entity.IEntityRads;
import nc.radiation.RadiationHelper;
import nc.util.DamageSources;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.*;
import net.minecraft.init.Enchantments;
import net.minecraft.item.*;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.server.SPacketBlockChange;
import net.minecraft.stats.StatList;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import org.apache.logging.log4j.*;

import java.util.*;

public class Util
{

	private static Logger logger;
	
	public static Logger getLogger()
	{
		if (logger == null)
		{
			logger = LogManager.getFormatterLogger(QMD.MOD_ID);
		}
		return logger;
	}
	
	public static ResourceLocation appendPath(ResourceLocation location, String  stringToAppend)
	{
		String domain =location.getNamespace();
		String path =location.getPath();
		ResourceLocation newLocation = new ResourceLocation(domain,path+stringToAppend);
		
		return newLocation;
	}
	
	
	
	
	
	
	
	public static EnumFacing getAxisFacing(EnumFacing.Axis axis, boolean positive)
	{
		if(axis == EnumFacing.Axis.X)
		{
			if(positive)
			{
				return EnumFacing.EAST;
			}
			return EnumFacing.WEST;
		}
		if(axis == EnumFacing.Axis.Y)
		{
			if(positive)
			{
				return EnumFacing.UP;
			}
			return EnumFacing.DOWN;
		}
		if(axis == EnumFacing.Axis.Z)
		{
			if(positive)
			{
				return EnumFacing.SOUTH;
			}
			return EnumFacing.NORTH;
		}
	
		return null;
	}
	
	
	
	public static int getTaxiDistance(BlockPos a, BlockPos b)
	{
		int x = Math.abs(a.getX()- b.getX());
		int y = Math.abs(a.getY()- b.getY());
		int z = Math.abs(a.getZ()- b.getZ());
		return x+y+z;
	}
	
	
	public static boolean mineBlock(World world, BlockPos pos, EntityPlayer player)
	{
		int fortune = EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, player.getHeldItemMainhand());
		boolean silkTouch = EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, player.getHeldItemMainhand()) > 0;
		return mineBlock(world, pos, player, fortune, silkTouch, false);
	}
	
	public static boolean mineBlock(World world, BlockPos pos, EntityPlayer player, int fortune, boolean silkTouch, boolean ignoreHarvestLevels)
	{
		IBlockState state = world.getBlockState(pos);
		Block block = state.getBlock();

		if (world.isAirBlock(pos))
		{
			return false;
		}
		
		if (!ForgeHooks.canHarvestBlock(block, player, world, pos) && !ignoreHarvestLevels)
		{
			return false;
		}

		if(state.getBlockHardness(world, pos) < 0) // Don't break unbreakable blocks
		{
			return false;
		}
		
		EntityPlayerMP playerMP = null;
		if (player instanceof EntityPlayerMP)
		{
			playerMP = (EntityPlayerMP) player;
		}
		
		if (playerMP != null)
		{
			if (ForgeHooks.onBlockBreakEvent(world, playerMP.interactionManager.getGameType(), playerMP, pos) == -1) // Should make the breaking cancelable
			{
				return false;
			}
		}
		
		if (!world.isRemote)
		{
			if (block.removedByPlayer(state, world, pos, player, !player.capabilities.isCreativeMode))
			{
				block.onPlayerDestroy(world, pos, state);

				player.addStat(StatList.getBlockStats(block));

				if (silkTouch && block.canSilkHarvest(world, pos, state, player) && !player.isCreative())
				{
					java.util.List<ItemStack> items = new java.util.ArrayList<ItemStack>();
					ItemStack itemstack = getSilkTouchDrop(state);

					if (!itemstack.isEmpty())
					{
						items.add(itemstack);
					}

					net.minecraftforge.event.ForgeEventFactory.fireBlockHarvesting(items, world, pos, state, 0, 1.0f, true,
							player);
					for (ItemStack item : items)
					{
						block.spawnAsEntity(world, pos, item);
					}
				}
				else if(!player.isCreative())
				{
					
					int xp = state.getBlock().getExpDrop(state, world, pos, fortune);
					block.dropXpOnBlockBreak(world, pos, xp);
					block.dropBlockAsItem(world, pos, state, fortune);
				}

				
			}
			playerMP.connection.sendPacket(new SPacketBlockChange(world, pos));
		}
		else
		{
			if (block.removedByPlayer(state, world, pos, player, !player.capabilities.isCreativeMode))
			{
				block.onPlayerDestroy(world, pos, state);
			}
			Minecraft.getMinecraft().getConnection().sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, pos, Minecraft.getMinecraft().objectMouseOver.sideHit));
		}
		
		return true;
	}
    
	
	
    public static ItemStack getSilkTouchDrop(IBlockState state)
    {
        Item item = Item.getItemFromBlock(state.getBlock());
        int i = 0;

        if (item.getHasSubtypes())
        {
            i = state.getBlock().getMetaFromState(state);
        }

        return new ItemStack(item, 1, i);
    }
	
    
    
    
    
    public static void createGammaFlash(World world, Vec3d pos, double size, float explosionSize, double radiation)
    {
		if(explosionSize > 0f)
		{
			world.createExplosion(null, pos.x, pos.y, pos.z, explosionSize, true);
		}
  
		world.spawnEntity(new EntityGammaFlash(world, pos.x, pos.y, pos.z, size));

		Set<EntityLivingBase> entitylist = new HashSet();
		double radius = 128 * Math.sqrt(size);

		entitylist.addAll(world.getEntitiesWithinAABB(EntityLivingBase.class,
				new AxisAlignedBB(pos.x - radius, pos.y - radius, pos.z - radius, pos.x + radius, pos.y + radius, pos.z + radius)));

		for (EntityLivingBase entity : entitylist)
		{
			IEntityRads entityRads = RadiationHelper.getEntityRadiation(entity);
			if(entityRads != null)
			{
				double rads = Math.min(radiation, (radiation) / pos.squareDistanceTo(entity.posX, entity.posY, entity.posZ));
				
				entityRads.setRadiationLevel(RadiationHelper.addRadsToEntity(entityRads, entity, rads, false, false, 1));
				if (entityRads.isFatal() && !entityRads.isImmune())
				{
					entity.attackEntityFrom(DamageSources.FATAL_RADS, Float.MAX_VALUE);
				}
			}
		}
    
    }
	
	
}
