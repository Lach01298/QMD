package lach_01298.qmd.block;

import lach_01298.qmd.EnumTypes;
import lach_01298.qmd.QMD;
import lach_01298.qmd.QMDInfo;
import lach_01298.qmd.multiblock.accelerator.block.BlockAcceleratorBeam;
import lach_01298.qmd.multiblock.accelerator.block.BlockAcceleratorBeamPort;
import lach_01298.qmd.multiblock.accelerator.block.BlockAcceleratorCasing;
import lach_01298.qmd.multiblock.accelerator.block.BlockAcceleratorController;
import lach_01298.qmd.multiblock.accelerator.block.BlockAcceleratorCooler1;
import lach_01298.qmd.multiblock.accelerator.block.BlockAcceleratorCooler2;
import lach_01298.qmd.multiblock.accelerator.block.BlockAcceleratorGlass;
import lach_01298.qmd.multiblock.accelerator.block.BlockAcceleratorMagnet;
import lach_01298.qmd.multiblock.accelerator.block.BlockAcceleratorPort;
import lach_01298.qmd.multiblock.accelerator.block.BlockAcceleratorSource;
import lach_01298.qmd.multiblock.accelerator.block.BlockAcceleratorYoke;
import lach_01298.qmd.multiblock.accelerator.block.BlockRFCavity;
import nc.Global;
import nc.NCInfo;
import nc.block.item.ItemBlockMeta;
import nc.block.item.NCItemBlock;
import nc.block.tile.ITileType;
import nc.util.InfoHelper;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;


public class QMDBlocks
{

	public static Block beamline;
	
	public static Block acceleratorCotroller;
	public static Block acceleratorBeam;
	public static Block acceleratorCasing;
	public static Block acceleratorGlass;
	public static Block acceleratorPort;
	public static Block acceleratorBeamPort;
	public static Block RFCavity;
	public static Block acceleratorMagnet;
	public static Block acceleratorYoke;
	public static Block acceleratorCooler1;
	public static Block acceleratorCooler2;
	public static Block acceleratorSource;

	
	public static void init() 
	{
//		beamline = withName(new BlockBeamLine(), "beamline");
		
		
		acceleratorCotroller = withName(new BlockAcceleratorController(), "accelerator_controller");
		acceleratorBeam = withName(new BlockAcceleratorBeam(), "accelerator_beam");
		acceleratorCasing = withName(new BlockAcceleratorCasing(), "accelerator_casing");
		acceleratorGlass = withName(new BlockAcceleratorGlass(), "accelerator_glass");
		acceleratorPort = withName(new BlockAcceleratorPort(), "accelerator_port");
		acceleratorBeamPort = withName(new BlockAcceleratorBeamPort(), "accelerator_beam_port");
		RFCavity = withName(new BlockRFCavity(), "accelerator_cavity");
		acceleratorMagnet = withName(new BlockAcceleratorMagnet(), "accelerator_magnet");
		acceleratorYoke = withName(new BlockAcceleratorYoke(), "accelerator_yoke");
		acceleratorCooler1 = withName(new BlockAcceleratorCooler1(), "accelerator_cooler1");
		acceleratorCooler2 = withName(new BlockAcceleratorCooler2(), "accelerator_cooler2");
		acceleratorSource =  withName(new BlockAcceleratorSource(), "accelerator_source");
	}
	
	public static void register() 
	{
		//registerBlock(beamline);
		
		
		registerBlock(acceleratorCotroller);
		registerBlock(acceleratorBeam);
		registerBlock(acceleratorCasing);
		registerBlock(acceleratorGlass);
		registerBlock(acceleratorPort);
		registerBlock(acceleratorBeamPort);
		registerBlock(RFCavity, new ItemBlockMeta(RFCavity, EnumTypes.RFCavityType.class, QMDInfo.RFCavityInfo()));
		registerBlock(acceleratorMagnet, new ItemBlockMeta(acceleratorMagnet, EnumTypes.MagnetType.class , QMDInfo.MagnetInfo()));
		registerBlock(acceleratorYoke);
		registerBlock(acceleratorCooler1, new ItemBlockMeta(acceleratorCooler1, EnumTypes.CoolerType1.class, QMDInfo.CoolerInfo()));
		registerBlock(acceleratorCooler2, new ItemBlockMeta(acceleratorCooler2, EnumTypes.CoolerType2.class, QMDInfo.CoolerInfo()));
		registerBlock(acceleratorSource);
				
				
				
	}

	public static void registerRenders() 
	{
		//registerRender(beamline);
		
		
		registerRender(acceleratorCotroller);
		registerRender(acceleratorBeam);
		registerRender(acceleratorCasing);
		registerRender(acceleratorGlass);
		registerRender(acceleratorPort);
		registerRender(acceleratorBeamPort);
		
		for (int i=0; i < EnumTypes.RFCavityType.values().length; i++)
		{
			registerRender(RFCavity, i, EnumTypes.RFCavityType.values()[i].getName());
		}
		for (int i=0; i < EnumTypes.MagnetType.values().length; i++)
		{
			registerRender(acceleratorMagnet, i, EnumTypes.MagnetType.values()[i].getName());
		}
		registerRender(acceleratorYoke);
		
		for (int i=0; i < EnumTypes.CoolerType1.values().length; i++)
		{
			registerRender(acceleratorCooler1, i, EnumTypes.CoolerType1.values()[i].getName());
		}
		for (int i=0; i < EnumTypes.CoolerType1.values().length; i++)
		{
			registerRender(acceleratorCooler2, i, EnumTypes.CoolerType2.values()[i].getName());
		}
		registerRender(acceleratorSource);
	}


	
	
	
	
	
	
	
	
	
	public static Block withName(Block block, String name) {
		return block.setUnlocalizedName(QMD.MOD_ID + "." + name).setRegistryName(new ResourceLocation(QMD.MOD_ID, name));
	}
	
	public static <T extends Block & ITileType> Block withName(T block) {
		return block.setUnlocalizedName(QMD.MOD_ID + "." + block.getTileName()).setRegistryName(new ResourceLocation(QMD.MOD_ID, block.getTileName()));
	}
	
	public static String fixedLine(String name) {
		return "tile." + QMD.MOD_ID + "." + name + ".fixd";
	}
	
	public static String infoLine(String name) {
		return "tile." + QMD.MOD_ID + "." + name + ".desc";
	}
	
	public static void registerBlock(Block block, TextFormatting[] fixedColors, String[] fixedTooltip, TextFormatting infoColor, String... tooltip) {
		ForgeRegistries.BLOCKS.register(block);
		ForgeRegistries.ITEMS.register(new NCItemBlock(block, fixedColors, fixedTooltip, infoColor, tooltip).setRegistryName(block.getRegistryName()));
	}
	
	public static void registerBlock(Block block, TextFormatting fixedColor, String[] fixedTooltip, TextFormatting infoColor, String... tooltip) {
		ForgeRegistries.BLOCKS.register(block);
		ForgeRegistries.ITEMS.register(new NCItemBlock(block, fixedColor, fixedTooltip, infoColor, tooltip).setRegistryName(block.getRegistryName()));
	}
	
	public static void registerBlock(Block block, String... tooltip) {
		registerBlock(block, TextFormatting.RED, InfoHelper.EMPTY_ARRAY, TextFormatting.AQUA, tooltip);
	}
	
	public static void registerBlock(Block block, ItemBlock itemBlock) {
		ForgeRegistries.BLOCKS.register(block);
		ForgeRegistries.ITEMS.register(itemBlock.setRegistryName(block.getRegistryName()));
	}
	
	public static void registerRender(Block block) {
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));
	}
	
	public static void registerRender(Block block, int meta, String type) {
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), meta, new ModelResourceLocation(new ResourceLocation(QMD.MOD_ID, block.getRegistryName().getResourcePath()), "type=" + type));
	}



}