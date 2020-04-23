package lach_01298.qmd.block;

import lach_01298.qmd.QMD;
import lach_01298.qmd.QMDInfo;
import lach_01298.qmd.config.QMDConfig;
import lach_01298.qmd.enums.EnumTypes;
import lach_01298.qmd.enums.EnumTypes.ProcessorType;
import lach_01298.qmd.enums.QMDRTGType;
import lach_01298.qmd.machine.block.BlockQMDProcessor;
import lach_01298.qmd.multiblock.accelerator.block.BlockAcceleratorBeam;
import lach_01298.qmd.multiblock.accelerator.block.BlockAcceleratorBeamPort;
import lach_01298.qmd.multiblock.accelerator.block.BlockAcceleratorCasing;
import lach_01298.qmd.multiblock.accelerator.block.BlockAcceleratorCooler1;
import lach_01298.qmd.multiblock.accelerator.block.BlockAcceleratorCooler2;
import lach_01298.qmd.multiblock.accelerator.block.BlockAcceleratorEnergyPort;
import lach_01298.qmd.multiblock.accelerator.block.BlockAcceleratorGlass;
import lach_01298.qmd.multiblock.accelerator.block.BlockAcceleratorInlet;
import lach_01298.qmd.multiblock.accelerator.block.BlockAcceleratorMagnet;
import lach_01298.qmd.multiblock.accelerator.block.BlockAcceleratorOutlet;
import lach_01298.qmd.multiblock.accelerator.block.BlockAcceleratorSource;
import lach_01298.qmd.multiblock.accelerator.block.BlockAcceleratorYoke;
import lach_01298.qmd.multiblock.accelerator.block.BlockBeamDiverterController;
import lach_01298.qmd.multiblock.accelerator.block.BlockRFCavity;
import lach_01298.qmd.multiblock.accelerator.block.BlockLinearAcceleratorController;
import lach_01298.qmd.multiblock.accelerator.block.BlockRingAcceleratorController;
import lach_01298.qmd.multiblock.particleChamber.block.BlockParticleChamberBeam;
import lach_01298.qmd.multiblock.particleChamber.block.BlockParticleChamberBeamPort;
import lach_01298.qmd.multiblock.particleChamber.block.BlockParticleChamberCasing;
import lach_01298.qmd.multiblock.particleChamber.block.BlockParticleChamberDetector;
import lach_01298.qmd.multiblock.particleChamber.block.BlockParticleChamberEnergyPort;
import lach_01298.qmd.multiblock.particleChamber.block.BlockParticleChamberGlass;
import lach_01298.qmd.multiblock.particleChamber.block.BlockParticleChamberPort;
import lach_01298.qmd.multiblock.particleChamber.block.BlockDecayChamberController;
import lach_01298.qmd.multiblock.particleChamber.block.BlockParticleChamber;
import lach_01298.qmd.multiblock.particleChamber.block.BlockTargetChamberController;
import lach_01298.qmd.pipe.BlockBeamline;
import nc.block.BlockMeta;
import nc.block.item.ItemBlockMeta;
import nc.block.item.NCItemBlock;
import nc.block.tile.BlockSimpleTile;
import nc.block.tile.ITileType;
import nc.config.NCConfig;
import nc.enumm.MetaEnums;
import nc.util.InfoHelper;
import nc.util.UnitHelper;
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
	
	public static Block linearAcceleratorController;
	public static Block ringAcceleratorController;
	public static Block acceleratorBeam;
	public static Block acceleratorCasing;
	public static Block acceleratorGlass;
	public static Block acceleratorInlet;
	public static Block acceleratorOutlet;
	public static Block acceleratorBeamPort;
	public static Block RFCavity;
	public static Block acceleratorMagnet;
	public static Block acceleratorYoke;
	public static Block acceleratorCooler1;
	public static Block acceleratorCooler2;
	public static Block acceleratorSource;
	public static Block acceleratorEnergyPort;
	public static Block beamDiverterController;
	
	public static Block targetChamberController;
	public static Block decayChamberController;
	public static Block particleChamberBeam;
	public static Block particleChamberCasing;
	public static Block particleChamberGlass;
	public static Block particleChamberBeamPort;
	public static Block particleChamberDetector;
	public static Block particleChamberEnergyPort;
	public static Block particleChamber;
	public static Block particleChamberPort;
	
	public static Block oreLeacher;
	public static Block irradiator;

	public static Block fissionReflector;
	public static Block rtgStrontium;
	
	public static void init() 
	{
		beamline = withName(new BlockBeamline(), "beamline");
		
		
		linearAcceleratorController = withName(new BlockLinearAcceleratorController(), "linear_accelerator_controller");
		ringAcceleratorController = withName(new BlockRingAcceleratorController(), "ring_accelerator_controller");
		acceleratorBeam = withName(new BlockAcceleratorBeam(), "accelerator_beam");
		acceleratorCasing = withName(new BlockAcceleratorCasing(), "accelerator_casing");
		acceleratorGlass = withName(new BlockAcceleratorGlass(), "accelerator_glass");
		acceleratorInlet = withName(new BlockAcceleratorInlet(), "accelerator_inlet");
		acceleratorOutlet = withName(new BlockAcceleratorOutlet(), "accelerator_outlet");
		acceleratorBeamPort = withName(new BlockAcceleratorBeamPort(), "accelerator_beam_port");
		RFCavity = withName(new BlockRFCavity(), "accelerator_cavity");
		acceleratorMagnet = withName(new BlockAcceleratorMagnet(), "accelerator_magnet");
		acceleratorYoke = withName(new BlockAcceleratorYoke(), "accelerator_yoke");
		acceleratorCooler1 = withName(new BlockAcceleratorCooler1(), "accelerator_cooler1");
		acceleratorCooler2 = withName(new BlockAcceleratorCooler2(), "accelerator_cooler2");
		acceleratorSource =  withName(new BlockAcceleratorSource(), "accelerator_source");
		acceleratorEnergyPort = withName(new BlockAcceleratorEnergyPort(), "accelerator_energy_port");
		beamDiverterController = withName(new BlockBeamDiverterController(), "beam_diverter_controller");
		
		targetChamberController = withName(new BlockTargetChamberController(), "target_chamber_controller");
		decayChamberController = withName(new BlockDecayChamberController(), "decay_chamber_controller");
		particleChamberBeam = withName(new BlockParticleChamberBeam(), "particle_chamber_beam");
		particleChamberCasing = withName(new BlockParticleChamberCasing(), "particle_chamber_casing");
		particleChamberGlass = withName(new BlockParticleChamberGlass(), "particle_chamber_glass");
		particleChamberBeamPort = withName(new BlockParticleChamberBeamPort(), "particle_chamber_beam_port");
		particleChamberDetector = withName(new BlockParticleChamberDetector(), "particle_chamber_detector");
		particleChamberEnergyPort = withName(new BlockParticleChamberEnergyPort(), "particle_chamber_energy_port");
		particleChamber = withName(new BlockParticleChamber(), "particle_chamber");
		particleChamberPort = withName(new BlockParticleChamberPort(), "particle_chamber_port");
		
		
		 oreLeacher = withName(new BlockQMDProcessor(ProcessorType.ORE_LEACHER));
		 irradiator = withName(new BlockQMDProcessor(ProcessorType.IRRADIATOR));
		
		 fissionReflector = withName(new QMDBlockMeta.BlockFissionReflector(), "fission_reflector");
		 rtgStrontium = withName(new QMDBlockRTG(QMDRTGType.STRONTIUM), "rtg_strontium");
	}
	
	public static void register() 
	{
		registerBlock(beamline,TextFormatting.AQUA+ QMDInfo.BeamlineInfo(),TextFormatting.GREEN + QMDInfo.BeamlineFixedlineInfo());
		
		
		registerBlock(linearAcceleratorController);
		registerBlock(ringAcceleratorController);
		registerBlock(acceleratorBeam,TextFormatting.GREEN + QMDInfo.BeamlineFixedlineInfo());
		registerBlock(acceleratorCasing);
		registerBlock(acceleratorGlass);
		registerBlock(acceleratorInlet);
		registerBlock(acceleratorOutlet);
		registerBlock(acceleratorBeamPort);
		registerBlock(RFCavity, new ItemBlockMeta(RFCavity, EnumTypes.RFCavityType.class,TextFormatting.GREEN ,QMDInfo.RFCavityFixedInfo(),TextFormatting.AQUA,QMDInfo.RFCavityInfo()));
		registerBlock(acceleratorMagnet, new ItemBlockMeta(acceleratorMagnet, EnumTypes.MagnetType.class,TextFormatting.GREEN ,QMDInfo.magnetFixedInfo(),TextFormatting.AQUA,QMDInfo.magnetInfo()));
		registerBlock(acceleratorYoke);
		registerBlock(acceleratorCooler1, new ItemBlockMeta(acceleratorCooler1, EnumTypes.CoolerType1.class,TextFormatting.BLUE, QMDInfo.cooler1FixedInfo(),TextFormatting.AQUA,QMDInfo.cooler1Info()));
		registerBlock(acceleratorCooler2, new ItemBlockMeta(acceleratorCooler2, EnumTypes.CoolerType2.class,TextFormatting.BLUE, QMDInfo.cooler2FixedInfo(),TextFormatting.AQUA,QMDInfo.cooler2Info()));
		registerBlock(acceleratorSource);
		registerBlock(acceleratorEnergyPort);
		registerBlock(beamDiverterController);
		
		registerBlock(targetChamberController);
		registerBlock(decayChamberController);
		registerBlock(particleChamberBeam);
		registerBlock(particleChamberCasing);
		registerBlock(particleChamberGlass);
		registerBlock(particleChamberBeamPort);
		registerBlock(particleChamberDetector, new ItemBlockMeta(particleChamberDetector, EnumTypes.DetectorType.class,TextFormatting.GREEN, QMDInfo.detectorFixedInfo(),TextFormatting.AQUA,QMDInfo.detectorInfo()));
		registerBlock(particleChamberEnergyPort);
		registerBlock(particleChamber);
		registerBlock(particleChamberPort);
		
		registerBlock(oreLeacher);
		registerBlock(irradiator);
		registerBlock(fissionReflector, new ItemBlockMeta(fissionReflector, EnumTypes.NeutronReflectorType.class, TextFormatting.AQUA));	
		registerBlock(rtgStrontium,InfoHelper.formattedInfo(infoLine("rtg"), UnitHelper.prefix(QMDConfig.rtg_power[0], 5, "RF/t")));
				
	}

	public static void registerRenders() 
	{
		registerRender(beamline);
		
		
		registerRender(linearAcceleratorController);
		registerRender(ringAcceleratorController);
		registerRender(acceleratorBeam);
		registerRender(acceleratorCasing);
		registerRender(acceleratorGlass);
		registerRender(acceleratorInlet);
		registerRender(acceleratorOutlet);
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
		for (int i=0; i < EnumTypes.CoolerType2.values().length; i++)
		{
			registerRender(acceleratorCooler2, i, EnumTypes.CoolerType2.values()[i].getName());
		}
		registerRender(acceleratorSource);
		registerRender(acceleratorEnergyPort);
		registerRender(beamDiverterController);
	
		registerRender(targetChamberController);
		registerRender(decayChamberController);
		registerRender(particleChamberBeam);
		registerRender(particleChamberCasing);
		registerRender(particleChamberGlass);
		registerRender(particleChamberBeamPort);
		for (int i=0; i < EnumTypes.DetectorType.values().length; i++)
		{
			registerRender(particleChamberDetector, i, EnumTypes.DetectorType.values()[i].getName());
		}
		registerRender(particleChamberEnergyPort);
		registerRender(particleChamber);
		registerRender(particleChamberPort);
	
		registerRender(oreLeacher);
		registerRender(irradiator);
		for (int i = 0; i < EnumTypes.NeutronReflectorType.values().length; i++) {
			registerRender(fissionReflector, i, EnumTypes.NeutronReflectorType.values()[i].getName());
		}
		
		registerRender(rtgStrontium);
	}


	
	
	
	
	
	
	
	
	
	public static Block withName(Block block, String name) {
		return block.setTranslationKey(QMD.MOD_ID + "." + name).setRegistryName(new ResourceLocation(QMD.MOD_ID, name));
	}
	
	public static <T extends Block & ITileType> Block withName(T block) {
		return block.setTranslationKey(QMD.MOD_ID + "." + block.getTileName()).setRegistryName(new ResourceLocation(QMD.MOD_ID, block.getTileName()));
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
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), meta, new ModelResourceLocation(new ResourceLocation(QMD.MOD_ID, block.getRegistryName().getPath()), "type=" + type));
	}



}