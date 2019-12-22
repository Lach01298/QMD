package lach_01298.qmd.fluid;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import lach_01298.qmd.QMD;
import nc.Global;
import nc.ModCheck;
import nc.NuclearCraft;
import nc.block.fluid.NCBlockFluid;
import nc.block.item.NCItemBlock;
import nc.config.NCConfig;
import nc.enumm.FluidType;
import nc.util.ColorHelper;
import nc.util.NCUtil;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class QMDFluids
{

	public static List<Pair<Fluid, NCBlockFluid>> fluidPairList = new ArrayList<Pair<Fluid, NCBlockFluid>>();

	public static void init()
	{
		try
		{

			// acids
			fluidPairList.add(fluidPair(FluidType.ACID, "hydrochloric_acid", 0x99ffee));
			fluidPairList.add(fluidPair(FluidType.ACID, "nitric_acid", 0x4f9eff));

			// solutions
			fluidPairList.add(fluidPair(FluidType.SALT_SOLUTION, "sodium_chloride_solution", waterBlend(0x0057fa)));

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static <T extends NCBlockFluid> Block withName(T block)
	{
		return block.setTranslationKey(QMD.MOD_ID + "." + block.getBlockName())
				.setRegistryName(new ResourceLocation(QMD.MOD_ID, block.getBlockName()));
	}

	public static void register()
	{
		for (Pair<Fluid, NCBlockFluid> fluidPair : fluidPairList)
		{
			Fluid fluid = fluidPair.getLeft();

			boolean defaultFluid = FluidRegistry.registerFluid(fluid);
			if (!defaultFluid)
				fluid = FluidRegistry.getFluid(fluid.getName());
			FluidRegistry.addBucketForFluid(fluid);

			registerBlock(fluidPair.getRight());
		}
	}

	public static void registerBlock(NCBlockFluid block)
	{
		ForgeRegistries.BLOCKS.register(withName(block));
		ForgeRegistries.ITEMS.register(new NCItemBlock(block, TextFormatting.AQUA).setRegistryName(block.getRegistryName()));
		NuclearCraft.proxy.registerFluidBlockRendering(block, block.getBlockName());
	}

	public static <T extends Fluid, V extends NCBlockFluid> Pair<Fluid, NCBlockFluid> fluidPair(FluidType fluidType, Object... fluidArgs) throws Exception
	{
		T fluid = NCUtil.newInstance(fluidType.getFluidClass(), fluidArgs);
		V block = NCUtil.newInstance(fluidType.getBlockClass(), fluid);
		return Pair.of(fluid, block);
	}

	private static int waterBlend(int soluteColor, float blendRatio)
	{
		return ColorHelper.blend(0x2F43F4, soluteColor, blendRatio);
	}

	private static int waterBlend(int soluteColor)
	{
		return waterBlend(soluteColor, 0.5F);
	}
}