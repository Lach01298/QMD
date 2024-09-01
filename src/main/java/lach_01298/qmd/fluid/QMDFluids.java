package lach_01298.qmd.fluid;

import lach_01298.qmd.QMD;
import nc.block.fluid.NCBlockFluid;
import nc.block.item.NCItemBlock;
import nc.enumm.FluidType;
import nc.util.*;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fluids.*;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

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
			addFluidPair(FluidType.LIQUID, "liquid_hydrogen",false, 0xB37AC4,71,20,170,0);
			addFluidPair(FluidType.LIQUID, "liquid_argon",false, 0xff75dd,1395,87,170,0);
			addFluidPair(FluidType.LIQUID, "liquid_neon",false, 0xff9f7a,1207,27,170,0);
			addFluidPair(FluidType.LIQUID, "liquid_oxygen",false, 0x7E8CC8,1141,90,170,0);
			
			//liquids
			addFluidPair(FluidType.LIQUID, "mercury", true,0xC6C6C6,13540,300,1000,0);
			addFluidPair(FluidType.LIQUID, "hot_mercury", true, 0xAAAAAA, 13540, 630,1000,0);
			addFluidPair(FluidType.LIQUID, "iodine",false, 0x7400B3,3960,387,1000,0);
			
			//gases
			addFluidPair(FluidType.GAS, "argon", 0xff75dd);
			addFluidPair(FluidType.GAS, "neon", 0xff9f7a);
			addFluidPair(FluidType.GAS, "chlorine", 0xffff8f);
			addFluidPair(FluidType.GAS, "nitric_oxide", 0xc9eeff);
			addFluidPair(FluidType.GAS, "nitrogen_dioxide", 0x782a10);
			addFluidPair(FluidType.GAS, "compressed_air", 0xD4D4D4);
			
			//molten
			addFluidPair(FluidType.MOLTEN, "silicon", 0x676767);
			addFluidPair(FluidType.MOLTEN, "yag", 0xfffddb);
			addFluidPair(FluidType.MOLTEN, "nd_yag", 0xe4bcf5);
			addFluidPair(FluidType.MOLTEN, "tungsten", 0x4E564F);
			addFluidPair(FluidType.MOLTEN, "niobium", 0xCCCCC0);
			addFluidPair(FluidType.MOLTEN, "chromium", 0xC9C9C9);
			addFluidPair(FluidType.MOLTEN, "titanium", 0x8E7E8D);
			addFluidPair(FluidType.MOLTEN, "cobalt", 0x364F70);
			addFluidPair(FluidType.MOLTEN, "nickel", 0xA3A998);
			addFluidPair(FluidType.MOLTEN, "hafnium", 0x948484);
			addFluidPair(FluidType.MOLTEN, "zinc", 0xE0E0E0);
			addFluidPair(FluidType.MOLTEN, "osmium", 0x6F89A9);
			addFluidPair(FluidType.MOLTEN, "iridium", 0xDDD5DD);
			addFluidPair(FluidType.MOLTEN, "platinum", 0x71A4A9);
			addFluidPair(FluidType.MOLTEN, "calcium", 0xF4F3EA);
			addFluidPair(FluidType.MOLTEN, "strontium", 0xC4CB97);
			addFluidPair(FluidType.MOLTEN, "yttrium", 0xD1C375);
			addFluidPair(FluidType.MOLTEN, "neodymium", 0x818A97);
			
			
			addFluidPair(FluidType.MOLTEN, "samarium", 0xa4d95f);
			addFluidPair(FluidType.MOLTEN, "terbium", 0x5ba694);
			addFluidPair(FluidType.MOLTEN, "erbium", 0x5a7a45);
			addFluidPair(FluidType.MOLTEN, "ytterbium", 0x7a4552);
			addFluidPair(FluidType.MOLTEN, "bismuth", 0x827e73);
			addFluidPair(FluidType.MOLTEN, "polonium", 0x5c7c78);
			addFluidPair(FluidType.MOLTEN, "radium", 0x6e607b);
			
			addFluidPair(FluidType.HOT_GAS, "carbon", 0x343434);
			addFluidPair(FluidType.MOLTEN, "sodium_chloride", 0xd4cccc);

			//exotic matter
			addFluidPair(QMDFluidType.EXOTIC,"antihydrogen", 0xB37AC4);
			addFluidPair(QMDFluidType.EXOTIC,"antideuterium", 0x9E6FEF);
			addFluidPair(QMDFluidType.EXOTIC,"antitritium", 0x5DBBD6);
			addFluidPair(QMDFluidType.EXOTIC,"antihelium3", 0xCBBB67);
			addFluidPair(QMDFluidType.EXOTIC,"antihelium", 0xC57B81);
			addFluidPair(QMDFluidType.EXOTIC,"positronium", 0xc9c9c9);
			addFluidPair(QMDFluidType.EXOTIC,"muonium", 0x9b93ff);
			addFluidPair(QMDFluidType.EXOTIC,"tauonium", 0xc86300);
			addFluidPair(QMDFluidType.EXOTIC,"glueballs", 0xa6f1f2);
			
			
			
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
			T fluid = ReflectionHelper.newInstance(fluidType.getFluidClass(), fluidArgs);
			V block = ReflectionHelper.newInstance(fluidType.getBlockClass(), fluid);
			fluidPairList.add(Pair.of(fluid, block));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	private static <T extends Fluid, V extends NCBlockFluid> void addFluidPair(QMDFluidType fluidType, Object... fluidArgs)
	{
		try
		{
			T fluid = ReflectionHelper.newInstance(fluidType.getFluidClass(), fluidArgs);
			V block = ReflectionHelper.newInstance(fluidType.getBlockClass(), fluid);
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
			
			if(!(fluidPair.getRight() instanceof BlockFluidExotic))
			{
			FluidRegistry.addBucketForFluid(fluid);
			}
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
