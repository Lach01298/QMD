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
			addFluidPair(FluidType.ACID, "hydrochloric_acid", 0x99ffee);
			addFluidPair(FluidType.ACID, "nitric_acid", 0x4f9eff);

			// solutions
			addFluidPair(FluidType.SALT_SOLUTION, "sodium_chloride_solution", waterBlend(0x0057fa));
			addFluidPair(FluidType.SALT_SOLUTION, "sodium_nitrate_solution", waterBlend(0xffffff));
			addFluidPair(FluidType.SALT_SOLUTION, "lead_nitrate_solution", waterBlend(0xd5d5d5));
			addFluidPair(FluidType.SALT_SOLUTION, "sodium_tungstate_solution", waterBlend(0xfffea3));
			addFluidPair(FluidType.SALT_SOLUTION, "lead_tungstate_solution", waterBlend(0xd58715));
			
			
			//cryo liquids
			addFluidPair(FluidType.LIQUID, "liquid_hydrogen",false, 0xB37AC4,71,20,100,0);
			addFluidPair(FluidType.LIQUID, "liquid_argon",false, 0xfa6400,1395,87,100,0);
			
			//gases
			addFluidPair(FluidType.GAS, "argon", 0xfa6400);
			addFluidPair(FluidType.GAS, "chlorine", 0xffff8f);
			addFluidPair(FluidType.GAS, "nitric_oxide", 0xc9eeff);
			addFluidPair(FluidType.GAS, "nitrogen_dioxide", 0x782a10);
			
			//molten
			addFluidPair(FluidType.MOLTEN, "silicon", 0x676767);
			addFluidPair(FluidType.MOLTEN, "yag", 0xfffddb);
			addFluidPair(FluidType.MOLTEN, "nd_yag", 0xe4bcf5);

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private static <T extends Fluid, V extends NCBlockFluid> void addFluidPair(FluidType fluidType, Object... fluidArgs)
	{
		try
		{
			T fluid = NCUtil.newInstance(fluidType.getFluidClass(), fluidArgs);
			V block = NCUtil.newInstance(fluidType.getBlockClass(), fluid);
			fluidPairList.add(Pair.of(fluid, block));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
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
		QMD.proxy.registerFluidBlockRendering(block, block.name);
	}
	
	public static <T extends NCBlockFluid> Block withName(T block)
	{
		return block.setTranslationKey(QMD.MOD_ID + "." + block.name).setRegistryName(new ResourceLocation(QMD.MOD_ID, block.name));
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