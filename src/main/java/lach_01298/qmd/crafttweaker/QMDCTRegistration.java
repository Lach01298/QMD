package lach_01298.qmd.crafttweaker;

import java.util.ArrayList;
import java.util.List;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.mc1120.util.CraftTweakerPlatformUtils;
import lach_01298.qmd.QMD;
import lach_01298.qmd.accelerator.CoolerPlacement;
import lach_01298.qmd.accelerator.block.BlockAcceleratorPart;
import lach_01298.qmd.accelerator.tile.TileAcceleratorCooler;
import lach_01298.qmd.block.QMDBlocks;
import nc.init.NCBlocks;
import nc.util.InfoHelper;
import nc.util.Lang;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.qmd.Registration")
@ZenRegister
public class QMDCTRegistration
{

	public static final List<QMDRegistrationInfo> INFO_LIST = new ArrayList<>();

	@ZenMethod
	public static void registerAcceleratorCooler(String coolerID, int cooling, String rule)
	{

		Block cooler = QMDBlocks.withName(new BlockAcceleratorPart()
		{

			@Override
			public TileEntity createNewTileEntity(World world, int metadata)
			{
				return new TileAcceleratorCooler(coolerID, cooling, coolerID + "_cooler");
			}

			@Override
			public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player,
					EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
			{
				if (player == null || hand != EnumHand.MAIN_HAND || player.isSneaking())
				{
					return false;
				}
				return rightClickOnPart(world, pos, player, hand, facing);
			}
		}, "accelerator_cooler" + coolerID);

		INFO_LIST.add(new AcceleratorCoolerRegistrationInfo(cooler, coolerID, cooling, rule));
		CraftTweakerAPI.logInfo("Registered accelerator cooler with ID \"" + coolerID + "\", cooling rate " + cooling
				+ " H/t and placement rule \"" + rule + "\"");
	}

	// Registration Wrapper

	public abstract static class QMDRegistrationInfo
	{

		public abstract void preInit();

		public void recipeInit()
		{
		}

		public abstract void init();

		public abstract void postInit();
	}

	public static class BlockRegistrationInfo extends QMDRegistrationInfo
	{

		protected final Block block;

		public BlockRegistrationInfo(Block block)
		{
			this.block = block;
		}

		@Override
		public void preInit()
		{
			registerBlock();

			if (CraftTweakerPlatformUtils.isClient())
			{
				registerRender();
			}
		}

		public void registerBlock()
		{
			QMDBlocks.registerBlock(block);
		}

		public void registerRender()
		{
			QMDBlocks.registerRender(block);
		}

		@Override
		public void init()
		{
		}

		@Override
		public void postInit()
		{
		}
	}

	public static class TileBlockRegistrationInfo extends BlockRegistrationInfo
	{

		public TileBlockRegistrationInfo(Block block)
		{
			super(block);
		}
	}

	public static class AcceleratorCoolerRegistrationInfo extends TileBlockRegistrationInfo
	{

		protected final String coolerID, rule;
		protected final int cooling;

		AcceleratorCoolerRegistrationInfo(Block block, String coolerID, int cooling, String rule)
		{
			super(block);
			this.coolerID = coolerID;
			this.cooling = cooling;
			this.rule = rule;
		}

		@Override
		public void registerBlock()
		{
			QMDBlocks.registerBlock(block, TextFormatting.BLUE, new String[] {
					Lang.localise("tile." + QMD.MOD_ID + ".accelerator_cooler.cooling_rate") + " " + cooling + " H/t" },
					TextFormatting.AQUA, InfoHelper.NULL_ARRAY);
		}

		@Override
		public void init()
		{
			super.init();
			CoolerPlacement.addRule(coolerID + "_cooler", rule, block);
		}
	}

}
