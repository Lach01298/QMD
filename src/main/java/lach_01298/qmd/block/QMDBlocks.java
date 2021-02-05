package lach_01298.qmd.block;

import static nc.config.NCConfig.turbine_mb_per_blade;

import lach_01298.qmd.QMD;
import lach_01298.qmd.QMDInfo;
import lach_01298.qmd.accelerator.block.BlockAcceleratorBeam;
import lach_01298.qmd.accelerator.block.BlockAcceleratorBeamPort;
import lach_01298.qmd.accelerator.block.BlockAcceleratorCasing;
import lach_01298.qmd.accelerator.block.BlockAcceleratorComputerPort;
import lach_01298.qmd.accelerator.block.BlockAcceleratorCooler1;
import lach_01298.qmd.accelerator.block.BlockAcceleratorCooler2;
import lach_01298.qmd.accelerator.block.BlockAcceleratorEnergyPort;
import lach_01298.qmd.accelerator.block.BlockAcceleratorGlass;
import lach_01298.qmd.accelerator.block.BlockAcceleratorMagnet;
import lach_01298.qmd.accelerator.block.BlockAcceleratorPort;
import lach_01298.qmd.accelerator.block.BlockAcceleratorSource;
import lach_01298.qmd.accelerator.block.BlockAcceleratorSynchrotronPort;
import lach_01298.qmd.accelerator.block.BlockAcceleratorVent;
import lach_01298.qmd.accelerator.block.BlockAcceleratorYoke;
import lach_01298.qmd.accelerator.block.BlockBeamDiverterController;
import lach_01298.qmd.accelerator.block.BlockDeceleratorController;
import lach_01298.qmd.accelerator.block.BlockLinearAcceleratorController;
import lach_01298.qmd.accelerator.block.BlockRFCavity;
import lach_01298.qmd.accelerator.block.BlockRingAcceleratorController;
import lach_01298.qmd.config.QMDConfig;
import lach_01298.qmd.containment.block.BlockContainmentBeamPort;
import lach_01298.qmd.containment.block.BlockContainmentCasing;
import lach_01298.qmd.containment.block.BlockContainmentCoil;
import lach_01298.qmd.containment.block.BlockContainmentEnergyPort;
import lach_01298.qmd.containment.block.BlockContainmentGlass;
import lach_01298.qmd.containment.block.BlockContainmentLaser;
import lach_01298.qmd.containment.block.BlockContainmentPort;
import lach_01298.qmd.containment.block.BlockContainmentVent;
import lach_01298.qmd.containment.block.BlockNeutralContainmentController;
import lach_01298.qmd.enums.BlockTypes.CoolerType1;
import lach_01298.qmd.enums.BlockTypes.CoolerType2;
import lach_01298.qmd.enums.BlockTypes.DetectorType;
import lach_01298.qmd.enums.BlockTypes.LampType;
import lach_01298.qmd.enums.BlockTypes.MagnetType;
import lach_01298.qmd.enums.BlockTypes.NeutronReflectorType;
import lach_01298.qmd.enums.BlockTypes.NeutronShieldType;
import lach_01298.qmd.enums.BlockTypes.ProcessorType;
import lach_01298.qmd.enums.BlockTypes.RFCavityType;
import lach_01298.qmd.enums.BlockTypes.RTGType;
import lach_01298.qmd.enums.BlockTypes.SimpleTileType;
import lach_01298.qmd.enums.BlockTypes.TurbineBladeType;
import lach_01298.qmd.fission.block.BlockFissionReflector;
import lach_01298.qmd.fission.block.QMDBlockFissionShield;
import lach_01298.qmd.machine.block.BlockQMDProcessor;
import lach_01298.qmd.particleChamber.block.BlockBeamDumpController;
import lach_01298.qmd.particleChamber.block.BlockCollisionChamberController;
import lach_01298.qmd.particleChamber.block.BlockDecayChamberController;
import lach_01298.qmd.particleChamber.block.BlockParticleChamber;
import lach_01298.qmd.particleChamber.block.BlockParticleChamberBeam;
import lach_01298.qmd.particleChamber.block.BlockParticleChamberBeamPort;
import lach_01298.qmd.particleChamber.block.BlockParticleChamberCasing;
import lach_01298.qmd.particleChamber.block.BlockParticleChamberDetector;
import lach_01298.qmd.particleChamber.block.BlockParticleChamberEnergyPort;
import lach_01298.qmd.particleChamber.block.BlockParticleChamberFluidPort;
import lach_01298.qmd.particleChamber.block.BlockParticleChamberGlass;
import lach_01298.qmd.particleChamber.block.BlockParticleChamberPort;
import lach_01298.qmd.particleChamber.block.BlockTargetChamberController;
import lach_01298.qmd.pipe.BlockBeamline;
import nc.block.item.ItemBlockMeta;
import nc.block.item.NCItemBlock;
import nc.block.tile.ITileType;
import nc.init.NCBlocks;
import nc.util.InfoHelper;
import nc.util.Lang;
import nc.util.UnitHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
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
	public static Block acceleratorVent;
	public static Block acceleratorBeamPort;
	public static Block acceleratorSynchrotronPort;
	public static Block RFCavity;
	public static Block acceleratorMagnet;
	public static Block acceleratorYoke;
	public static Block acceleratorCooler1;
	public static Block acceleratorCooler2;
	public static Block acceleratorSource;
	public static Block acceleratorEnergyPort;
	public static Block beamDiverterController;
	public static Block deceleratorController;
	public static Block acceleratorComputerPort;
	public static Block acceleratorPort;

	public static Block targetChamberController;
	public static Block decayChamberController;
	public static Block beamDumpController;
	public static Block collisionChamberController;
	public static Block particleChamberBeam;
	public static Block particleChamberCasing;
	public static Block particleChamberGlass;
	public static Block particleChamberBeamPort;
	public static Block particleChamberDetector;
	public static Block particleChamberEnergyPort;
	public static Block particleChamber;
	public static Block particleChamberPort;
	public static Block particleChamberFluidPort;
	
	public static Block oreLeacher;
	public static Block irradiator;

	public static Block fissionReflector;
	public static Block fissionShield;
	public static Block rtgStrontium;
	
	public static Block heliumCollector;
	public static Block neonCollector;
	public static Block argonCollector;
	
	public static Block dischargeLamp;
	
	public static Block neutralContainmentController;
	public static Block containmentCasing;
	public static Block containmentGlass;
	public static Block containmentPort;
	public static Block containmentVent;
	public static Block containmentBeamPort;
	public static Block containmentEnergyPort;
	public static Block containmentCoil;
	public static Block containmentLaser;
	
	public static Block creativeParticleSource;
	
	public static Block turbineBladeSuperAlloy;
	
	public static Block strontium90;
	
	public static void init() 
	{
		beamline = withName(new BlockBeamline(), "beamline");
		
		
		linearAcceleratorController = withName(new BlockLinearAcceleratorController(), "linear_accelerator_controller");
		ringAcceleratorController = withName(new BlockRingAcceleratorController(), "ring_accelerator_controller");
		acceleratorBeam = withName(new BlockAcceleratorBeam(), "accelerator_beam");
		acceleratorCasing = withName(new BlockAcceleratorCasing(), "accelerator_casing");
		acceleratorGlass = withName(new BlockAcceleratorGlass(), "accelerator_glass");
		acceleratorVent = withName(new BlockAcceleratorVent(), "accelerator_vent");
		acceleratorComputerPort = withName(new BlockAcceleratorComputerPort(), "accelerator_computer_port");
		acceleratorPort = withName(new BlockAcceleratorPort(), "accelerator_port");

		acceleratorBeamPort = withName(new BlockAcceleratorBeamPort(), "accelerator_beam_port");
		acceleratorSynchrotronPort = withName(new BlockAcceleratorSynchrotronPort(), "accelerator_synchrotron_port");
		RFCavity = withName(new BlockRFCavity(), "accelerator_cavity");
		acceleratorMagnet = withName(new BlockAcceleratorMagnet(), "accelerator_magnet");
		acceleratorYoke = withName(new BlockAcceleratorYoke(), "accelerator_yoke");
		acceleratorCooler1 = withName(new BlockAcceleratorCooler1(), "accelerator_cooler1");
		acceleratorCooler2 = withName(new BlockAcceleratorCooler2(), "accelerator_cooler2");
		acceleratorSource =  withName(new BlockAcceleratorSource(), "accelerator_source");
		acceleratorEnergyPort = withName(new BlockAcceleratorEnergyPort(), "accelerator_energy_port");
		beamDiverterController = withName(new BlockBeamDiverterController(), "beam_diverter_controller");
		deceleratorController = withName(new BlockDeceleratorController(), "decelerator_controller");
		
		targetChamberController = withName(new BlockTargetChamberController(), "target_chamber_controller");
		decayChamberController = withName(new BlockDecayChamberController(), "decay_chamber_controller");
		beamDumpController = withName(new BlockBeamDumpController(), "beam_dump_controller");
		collisionChamberController = withName(new BlockCollisionChamberController(), "collision_chamber_controller");
		particleChamberBeam = withName(new BlockParticleChamberBeam(), "particle_chamber_beam");
		particleChamberCasing = withName(new BlockParticleChamberCasing(), "particle_chamber_casing");
		particleChamberGlass = withName(new BlockParticleChamberGlass(), "particle_chamber_glass");
		particleChamberBeamPort = withName(new BlockParticleChamberBeamPort(), "particle_chamber_beam_port");
		particleChamberDetector = withName(new BlockParticleChamberDetector(), "particle_chamber_detector");
		particleChamberEnergyPort = withName(new BlockParticleChamberEnergyPort(), "particle_chamber_energy_port");
		particleChamber = withName(new BlockParticleChamber(), "particle_chamber");
		particleChamberPort = withName(new BlockParticleChamberPort(), "particle_chamber_port");
		particleChamberFluidPort = withName(new BlockParticleChamberFluidPort(), "particle_chamber_fluid_port");

		oreLeacher = withName(new BlockQMDProcessor(ProcessorType.ORE_LEACHER));
		irradiator = withName(new BlockQMDProcessor(ProcessorType.IRRADIATOR));

		fissionReflector = withName(new BlockFissionReflector(), "fission_reflector");
		fissionShield = withName(new QMDBlockFissionShield(), "fission_shield");

		rtgStrontium = withName(new QMDBlockRTG(RTGType.STRONTIUM), "rtg_strontium");

		dischargeLamp = withName(new BlockLamp(), "discharge_lamp");

		heliumCollector = withName(new QMDBlockSimpleTile(SimpleTileType.HELIUM_COLLECTOR), "helium_collector");
		neonCollector = withName(new QMDBlockSimpleTile(SimpleTileType.NEON_COLLECTOR), "neon_collector");
		argonCollector = withName(new QMDBlockSimpleTile(SimpleTileType.ARGON_COLLECTOR), "argon_collector");

		neutralContainmentController = withName(new BlockNeutralContainmentController(), "neutral_containment_controller");
		containmentCasing = withName(new BlockContainmentCasing(), "containment_casing");
		containmentGlass = withName(new BlockContainmentGlass(), "containment_glass");
		containmentPort = withName(new BlockContainmentPort(), "containment_port");
		containmentBeamPort = withName(new BlockContainmentBeamPort(), "containment_beam_port");
		containmentVent = withName(new BlockContainmentVent(), "containment_vent");
		containmentEnergyPort = withName(new BlockContainmentEnergyPort(), "containment_energy_port");
		containmentCoil = withName(new BlockContainmentCoil(), "containment_coil");
		containmentLaser = withName(new BlockContainmentLaser(), "containment_laser");
		strontium90 = withName(new BlockQMD(Material.IRON), "strontium_90_block");
		
		creativeParticleSource = withName(new BlockCreativeParticleSource(), "creative_particle_source");
		
		turbineBladeSuperAlloy = withName(new QMDBlockTurbineBlade(TurbineBladeType.SUPER_ALLOY), "turbine_blade_super_alloy");
		
	}
	
	public static void register() 
	{
		registerBlock(beamline,TextFormatting.AQUA+ QMDInfo.BeamlineInfo(),TextFormatting.GREEN + QMDInfo.BeamlineFixedlineInfo());
		
		
		registerBlock(linearAcceleratorController);
		registerBlock(ringAcceleratorController);
		registerBlock(acceleratorBeam,TextFormatting.GREEN + QMDInfo.BeamlineFixedlineInfo());
		registerBlock(acceleratorCasing);
		registerBlock(acceleratorComputerPort);
		registerBlock(acceleratorPort);
		registerBlock(acceleratorGlass);
		registerBlock(acceleratorVent);
		registerBlock(acceleratorBeamPort);
		registerBlock(acceleratorSynchrotronPort);
		registerBlock(RFCavity, new ItemBlockMeta(RFCavity, RFCavityType.class,TextFormatting.GREEN ,QMDInfo.RFCavityFixedInfo(),TextFormatting.AQUA,QMDInfo.RFCavityInfo()));
		registerBlock(acceleratorMagnet, new ItemBlockMeta(acceleratorMagnet, MagnetType.class,TextFormatting.GREEN ,QMDInfo.magnetFixedInfo(),TextFormatting.AQUA,QMDInfo.magnetInfo()));
		registerBlock(acceleratorYoke);
		registerBlock(acceleratorCooler1, new ItemBlockMeta(acceleratorCooler1, CoolerType1.class,TextFormatting.BLUE, QMDInfo.cooler1FixedInfo(),TextFormatting.AQUA,QMDInfo.cooler1Info()));
		registerBlock(acceleratorCooler2, new ItemBlockMeta(acceleratorCooler2, CoolerType2.class,TextFormatting.BLUE, QMDInfo.cooler2FixedInfo(),TextFormatting.AQUA,QMDInfo.cooler2Info()));
		registerBlock(acceleratorSource);
		registerBlock(acceleratorEnergyPort);
		registerBlock(beamDiverterController);
		registerBlock(deceleratorController);
		
		registerBlock(targetChamberController);
		registerBlock(decayChamberController);
		registerBlock(beamDumpController);
		registerBlock(collisionChamberController);
		registerBlock(particleChamberBeam, TextFormatting.GREEN + QMDInfo.BeamlineFixedlineInfo());
		registerBlock(particleChamberCasing);
		registerBlock(particleChamberGlass);
		registerBlock(particleChamberBeamPort);
		registerBlock(particleChamberDetector, new ItemBlockMeta(particleChamberDetector, DetectorType.class,TextFormatting.GREEN, QMDInfo.detectorFixedInfo(),TextFormatting.AQUA,QMDInfo.detectorInfo()));
		registerBlock(particleChamberEnergyPort);
		registerBlock(particleChamber,TextFormatting.GREEN + QMDInfo.BeamlineFixedlineInfo());
		registerBlock(particleChamberPort);
		registerBlock(particleChamberFluidPort);
		
		registerBlock(oreLeacher);
		registerBlock(irradiator);
		
		registerBlock(fissionReflector, new ItemBlockMeta(fissionReflector, NeutronReflectorType.class, TextFormatting.AQUA));	
		registerBlock(fissionShield,new ItemBlockMeta(fissionShield, NeutronShieldType.class, new TextFormatting[] {TextFormatting.YELLOW, TextFormatting.LIGHT_PURPLE}, QMDInfo.neutronShieldFixedInfo(), TextFormatting.AQUA, QMDInfo.neutronShieldInfo()));
		
		
		registerBlock(rtgStrontium,InfoHelper.formattedInfo(NCBlocks.infoLine("rtg"), UnitHelper.prefix(QMDConfig.rtg_power[0], 5, "RF/t")));
		
		registerBlock(dischargeLamp, new ItemBlockMeta(dischargeLamp, LampType.class));	
		
		registerBlock(heliumCollector);
		registerBlock(neonCollector);
		registerBlock(argonCollector);
		
		registerBlock(neutralContainmentController);
		registerBlock(containmentCasing);
		registerBlock(containmentGlass);
		registerBlock(containmentPort);
		registerBlock(containmentBeamPort);
		registerBlock(containmentVent);
		registerBlock(containmentEnergyPort);
		registerBlock(containmentCoil);
		registerBlock(containmentLaser);
		
		registerBlock(strontium90);
		
		registerBlock(creativeParticleSource);
		registerBlock(turbineBladeSuperAlloy, new TextFormatting[] {TextFormatting.LIGHT_PURPLE, TextFormatting.GRAY}, new String[] {Lang.localise(NCBlocks.fixedLine("turbine_rotor_blade_efficiency"), Math.round(100D * QMDConfig.turbine_blade_efficiency[0]) + "%"), Lang.localise(NCBlocks.fixedLine("turbine_rotor_blade_expansion"), Math.round(100D * QMDConfig.turbine_blade_expansion[0]) + "%")}, TextFormatting.AQUA, InfoHelper.formattedInfo(NCBlocks.infoLine("turbine_rotor_blade"), UnitHelper.prefix(turbine_mb_per_blade, 5, "B/t", -1)));
		
	}

	public static void registerRenders() 
	{
		registerRender(beamline);
		
		
		registerRender(linearAcceleratorController);
		registerRender(ringAcceleratorController);
		registerRender(acceleratorBeam);
		registerRender(acceleratorCasing);
		registerRender(acceleratorComputerPort);
		registerRender(acceleratorPort);
		registerRender(acceleratorGlass);
		registerRender(acceleratorVent);
		registerRender(acceleratorBeamPort);
		registerRender(acceleratorSynchrotronPort);
		
		for (int i=0; i < RFCavityType.values().length; i++)
		{
			registerRender(RFCavity, i, RFCavityType.values()[i].getName());
		}
		for (int i=0; i < MagnetType.values().length; i++)
		{
			registerRender(acceleratorMagnet, i, MagnetType.values()[i].getName());
		}
		registerRender(acceleratorYoke);
		
		for (int i=0; i < CoolerType1.values().length; i++)
		{
			registerRender(acceleratorCooler1, i, CoolerType1.values()[i].getName());
		}
		for (int i=0; i < CoolerType2.values().length; i++)
		{
			registerRender(acceleratorCooler2, i, CoolerType2.values()[i].getName());
		}
		registerRender(acceleratorSource);
		registerRender(acceleratorEnergyPort);
		registerRender(beamDiverterController);
		registerRender(deceleratorController);
	
		registerRender(targetChamberController);
		registerRender(decayChamberController);
		registerRender(beamDumpController);
		registerRender(collisionChamberController);
		registerRender(particleChamberBeam);
		registerRender(particleChamberCasing);
		registerRender(particleChamberGlass);
		registerRender(particleChamberBeamPort);
		for (int i=0; i < DetectorType.values().length; i++)
		{
			registerRender(particleChamberDetector, i, DetectorType.values()[i].getName());
		}
		registerRender(particleChamberEnergyPort);
		registerRender(particleChamber);
		registerRender(particleChamberPort);
		registerRender(particleChamberFluidPort);
	
		registerRender(oreLeacher);
		registerRender(irradiator);
		
		for (int i = 0; i < NeutronReflectorType.values().length; i++) 
		{
			registerRender(fissionReflector, i, NeutronReflectorType.values()[i].getName());
		}
		for (int i = 0; i < NeutronShieldType.values().length; i++) 
		{
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(fissionShield), i, new ModelResourceLocation(new ResourceLocation(QMD.MOD_ID, fissionShield.getRegistryName().getPath()), "active=true,type=" + NeutronShieldType.values()[i].getName()));
		}
		
		registerRender(rtgStrontium);
		
		for (int i = 0; i < LampType.values().length; i++) 
		{
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(dischargeLamp), i, new ModelResourceLocation(new ResourceLocation(QMD.MOD_ID, dischargeLamp.getRegistryName().getPath()), "active=true,type=" + LampType.values()[i].getName()));
		}
		
		registerRender(heliumCollector);
		registerRender(neonCollector);
		registerRender(argonCollector);
		
		
		
		
		registerRender(neutralContainmentController);
		registerRender(containmentCasing);
		registerRender(containmentGlass);
		registerRender(containmentPort);
		registerRender(containmentBeamPort);
		registerRender(containmentVent);
		registerRender(containmentEnergyPort);
		registerRender(containmentCoil);
		registerRender(containmentLaser);
		
		registerRender(strontium90);
		registerRender(creativeParticleSource);
		
		registerRender(turbineBladeSuperAlloy);
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