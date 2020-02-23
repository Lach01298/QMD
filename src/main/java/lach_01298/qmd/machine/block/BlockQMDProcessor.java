package lach_01298.qmd.machine.block;

import static nc.block.property.BlockProperties.ACTIVE;
import static nc.block.property.BlockProperties.FACING_HORIZONTAL;

import java.util.Random;

import lach_01298.qmd.QMD;
import lach_01298.qmd.enums.EnumTypes.ProcessorType;
import nc.NuclearCraft;
import nc.block.tile.BlockSidedTile;
import nc.block.tile.IActivatable;
import nc.block.tile.ITileType;
import nc.init.NCItems;
import nc.tile.ITileGui;
import nc.tile.fluid.ITileFluid;
import nc.tile.processor.IProcessor;
import nc.tile.processor.IUpgradable;
import nc.util.BlockHelper;
import nc.util.FluidHelper;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockQMDProcessor extends BlockSidedTile implements IActivatable, ITileType {
	
	protected final ProcessorType type;
	
	public BlockQMDProcessor(ProcessorType type) {
		super(Material.IRON);
		if (type.getCreativeTab() != null) setCreativeTab(type.getCreativeTab());
		this.type = type;
	}
	
	@Override
	public String getTileName() {
		return type.getName();
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return type.getTile();
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		EnumFacing enumfacing = EnumFacing.byIndex(meta & 7);
		if (enumfacing.getAxis() == EnumFacing.Axis.Y) {
			enumfacing = EnumFacing.NORTH;
		}
		return getDefaultState().withProperty(FACING_HORIZONTAL, enumfacing).withProperty(ACTIVE, Boolean.valueOf((meta & 8) > 0));
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		int i = state.getValue(FACING_HORIZONTAL).getIndex();
		if (state.getValue(ACTIVE).booleanValue()) i |= 8;
		return i;
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] {FACING_HORIZONTAL, ACTIVE});
	}
	
	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		return getDefaultState().withProperty(FACING_HORIZONTAL, placer.getHorizontalFacing().getOpposite()).withProperty(ACTIVE, Boolean.valueOf(false));
	}
	
	@Override
	public void setState(boolean isActive, TileEntity tile) {
		World world = tile.getWorld();
		BlockPos pos = tile.getPos();
		IBlockState state = world.getBlockState(pos);
		if (!world.isRemote && state.getBlock() == type.getBlock()) {
			if (isActive != state.getValue(ACTIVE)) {
				world.setBlockState(pos, state.withProperty(ACTIVE, isActive), 2);
			}
		}
	}
	
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (player == null || hand != EnumHand.MAIN_HAND) return false;
		
		TileEntity tile = world.getTileEntity(pos);
		if (tile instanceof IUpgradable) {
			if (installUpgrade(tile, ((IUpgradable) tile).getSpeedUpgradeSlot(), player, facing, new ItemStack(NCItems.upgrade, 1, 0))) return true;
			if (installUpgrade(tile, ((IUpgradable) tile).getEnergyUpgradeSlot(), player, facing, new ItemStack(NCItems.upgrade, 1, 1))) return true;
		}
		
		if (player.isSneaking()) return false;
		
		if (!(tile instanceof ITileFluid) && !(tile instanceof ITileGui)) return false;
		if (tile instanceof ITileFluid && !(tile instanceof ITileGui) && FluidUtil.getFluidHandler(player.getHeldItem(hand)) == null) return false;
		
		if (tile instanceof ITileFluid) {
			if (world.isRemote) return true;
			ITileFluid tileFluid = (ITileFluid) tile;
			boolean accessedTanks = FluidHelper.accessTanks(player, hand, facing, tileFluid);
			if (accessedTanks) {
				if (tile instanceof IProcessor) {
					((IProcessor) tile).refreshRecipe();
					((IProcessor) tile).refreshActivity();
				}
				return true;
			}
		}
		if (tile instanceof ITileGui) {
			if (world.isRemote) {
				onGuiOpened(world, pos);
				return true;
			} else {
				onGuiOpened(world, pos);
				if (tile instanceof IProcessor) {
					((IProcessor) tile).refreshRecipe();
					((IProcessor) tile).refreshActivity();
				}
				FMLNetworkHandler.openGui(player, QMD.instance, ((ITileGui) tile).getGuiID(), world, pos.getX(), pos.getY(), pos.getZ());
			}
		}
		else return false;
		
		return true;
	}
	
	
	
	
	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {
		if (!state.getValue(ACTIVE)) return;
		BlockHelper.spawnParticleOnProcessor(state, world, pos, rand, state.getValue(FACING_HORIZONTAL), type.getParticle1());
		BlockHelper.spawnParticleOnProcessor(state, world, pos, rand, state.getValue(FACING_HORIZONTAL), type.getParticle2());
		BlockHelper.playSoundOnProcessor(world, pos, rand, type.getSound());
	}
}