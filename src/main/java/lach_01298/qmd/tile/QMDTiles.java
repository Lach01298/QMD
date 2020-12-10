package lach_01298.qmd.tile;

import lach_01298.qmd.QMD;
import lach_01298.qmd.Util;
import lach_01298.qmd.accelerator.tile.TileAcceleratorComputerPort;
import lach_01298.qmd.accelerator.tile.TileAcceleratorBeam;
import lach_01298.qmd.accelerator.tile.TileAcceleratorBeamPort;
import lach_01298.qmd.accelerator.tile.TileAcceleratorCasing;
import lach_01298.qmd.accelerator.tile.TileAcceleratorCooler;
import lach_01298.qmd.accelerator.tile.TileAcceleratorEnergyPort;
import lach_01298.qmd.accelerator.tile.TileAcceleratorGlass;
import lach_01298.qmd.accelerator.tile.TileAcceleratorMagnet;
import lach_01298.qmd.accelerator.tile.TileAcceleratorRFCavity;
import lach_01298.qmd.accelerator.tile.TileAcceleratorSource;
import lach_01298.qmd.accelerator.tile.TileAcceleratorSynchrotronPort;
import lach_01298.qmd.accelerator.tile.TileAcceleratorVent;
import lach_01298.qmd.accelerator.tile.TileAcceleratorYoke;
import lach_01298.qmd.accelerator.tile.TileBeamDiverterController;
import lach_01298.qmd.accelerator.tile.TileDeceleratorController;
import lach_01298.qmd.accelerator.tile.TileLinearAcceleratorController;
import lach_01298.qmd.accelerator.tile.TileRingAcceleratorController;
import lach_01298.qmd.containment.tile.TileContainmentBeamPort;
import lach_01298.qmd.containment.tile.TileContainmentCasing;
import lach_01298.qmd.containment.tile.TileContainmentCoil;
import lach_01298.qmd.containment.tile.TileContainmentEnergyPort;
import lach_01298.qmd.containment.tile.TileContainmentGlass;
import lach_01298.qmd.containment.tile.TileContainmentLaser;
import lach_01298.qmd.containment.tile.TileContainmentPort;
import lach_01298.qmd.containment.tile.TileContainmentVent;
import lach_01298.qmd.containment.tile.TileNeutralContainmentController;
import lach_01298.qmd.enums.BlockTypes.CoolerType1;
import lach_01298.qmd.enums.BlockTypes.CoolerType2;
import lach_01298.qmd.enums.BlockTypes.DetectorType;
import lach_01298.qmd.enums.BlockTypes.MagnetType;
import lach_01298.qmd.enums.BlockTypes.RFCavityType;
import lach_01298.qmd.fission.tile.QMDTileFissionShield;
import lach_01298.qmd.machine.tile.TileQMDProcessor;
import lach_01298.qmd.particleChamber.tile.TileBeamDumpController;
import lach_01298.qmd.particleChamber.tile.TileDecayChamberController;
import lach_01298.qmd.particleChamber.tile.TileParticleChamber;
import lach_01298.qmd.particleChamber.tile.TileParticleChamberBeam;
import lach_01298.qmd.particleChamber.tile.TileParticleChamberBeamPort;
import lach_01298.qmd.particleChamber.tile.TileParticleChamberCasing;
import lach_01298.qmd.particleChamber.tile.TileParticleChamberDetector;
import lach_01298.qmd.particleChamber.tile.TileParticleChamberEnergyPort;
import lach_01298.qmd.particleChamber.tile.TileParticleChamberFluidPort;
import lach_01298.qmd.particleChamber.tile.TileParticleChamberGlass;
import lach_01298.qmd.particleChamber.tile.TileParticleChamberPort;
import lach_01298.qmd.particleChamber.tile.TileTargetChamberController;
import lach_01298.qmd.pipe.TileBeamline;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class QMDTiles
{
	private static ResourceLocation acceleratorPath = new ResourceLocation(QMD.MOD_ID,"accelerator_");
	private static ResourceLocation magnetPath = new ResourceLocation(QMD.MOD_ID,"accelerator_magnet");
	private static ResourceLocation cavityPath = new ResourceLocation(QMD.MOD_ID,"accelerator_cavity");
	private static ResourceLocation coolerPath = new ResourceLocation(QMD.MOD_ID,"accelerator_cooler");
	
	private static ResourceLocation chamberPath = new ResourceLocation(QMD.MOD_ID,"particle_chamber_");
	private static ResourceLocation detectorPath = new ResourceLocation(QMD.MOD_ID,"particle_chamber_detector_");
	private static ResourceLocation containmentPath = new ResourceLocation(QMD.MOD_ID,"containment_");

	
	public static void register() 
	{
		
		//other
		GameRegistry.registerTileEntity(TileBeamline.class,new ResourceLocation(QMD.MOD_ID,"beamline"));
		GameRegistry.registerTileEntity(QMDTileRTG.Strontium.class,new ResourceLocation(QMD.MOD_ID,"rtg_strontium"));
		GameRegistry.registerTileEntity(QMDTileFissionShield.Hafnium.class,new ResourceLocation(QMD.MOD_ID,"fission_shield_hafnium"));
		GameRegistry.registerTileEntity(QMDTilePassive.HeliumCollector.class,new ResourceLocation(QMD.MOD_ID,"helium_collector"));
		GameRegistry.registerTileEntity(QMDTilePassive.NeonCollector.class,new ResourceLocation(QMD.MOD_ID,"neon_collector"));
		GameRegistry.registerTileEntity(QMDTilePassive.ArgonCollector.class,new ResourceLocation(QMD.MOD_ID,"argon_collector"));
		
		
		//Accelerator parts
		GameRegistry.registerTileEntity(TileLinearAcceleratorController.class,Util.appendPath(acceleratorPath, "linear_controller"));
		GameRegistry.registerTileEntity(TileRingAcceleratorController.class,Util.appendPath(acceleratorPath, "ring_controller"));
		GameRegistry.registerTileEntity(TileBeamDiverterController.class,Util.appendPath(acceleratorPath, "beam_diverter_controller"));
		GameRegistry.registerTileEntity(TileDeceleratorController.class,Util.appendPath(acceleratorPath, "decelerator_controller"));
		GameRegistry.registerTileEntity(TileAcceleratorBeam.class,Util.appendPath(acceleratorPath, "beam"));
		GameRegistry.registerTileEntity(TileAcceleratorCasing.class,Util.appendPath(acceleratorPath, "casing"));
		GameRegistry.registerTileEntity(TileAcceleratorGlass.class,Util.appendPath(acceleratorPath, "glass"));
		GameRegistry.registerTileEntity(TileAcceleratorVent.class,Util.appendPath(acceleratorPath, "vent"));
		GameRegistry.registerTileEntity(TileAcceleratorBeamPort.class,Util.appendPath(acceleratorPath, "beam_port"));
		GameRegistry.registerTileEntity(TileAcceleratorSynchrotronPort.class,Util.appendPath(acceleratorPath, "synchrotron_port"));
		GameRegistry.registerTileEntity(TileAcceleratorSource.class,Util.appendPath(acceleratorPath, "source"));
		GameRegistry.registerTileEntity(TileAcceleratorYoke.class,Util.appendPath(acceleratorPath, "yoke"));
		GameRegistry.registerTileEntity(TileAcceleratorEnergyPort.class,Util.appendPath(acceleratorPath, "energy_port"));
		GameRegistry.registerTileEntity(TileAcceleratorComputerPort.class,Util.appendPath(acceleratorPath, "computer_port"));
		
		//magnets
		GameRegistry.registerTileEntity(TileAcceleratorMagnet.Copper.class, Util.appendPath(magnetPath, MagnetType.COPPER.getName()));
		GameRegistry.registerTileEntity(TileAcceleratorMagnet.MagnesiumDiboride.class, Util.appendPath(magnetPath, MagnetType.MAGNESIUM_DIBORIDE.getName()));
		GameRegistry.registerTileEntity(TileAcceleratorMagnet.NiobiumTin.class, Util.appendPath(magnetPath, MagnetType.NIOBIUM_TIN.getName()));
		GameRegistry.registerTileEntity(TileAcceleratorMagnet.NiobiumTitanium.class, Util.appendPath(magnetPath, MagnetType.NIOBIUM_TITANIUM.getName()));
		GameRegistry.registerTileEntity(TileAcceleratorMagnet.BSCCO.class, Util.appendPath(magnetPath, MagnetType.BSCCO.getName()));
		
		//RF Cavities
		GameRegistry.registerTileEntity(TileAcceleratorRFCavity.Copper.class, Util.appendPath(cavityPath, RFCavityType.COPPER.getName()));
		GameRegistry.registerTileEntity(TileAcceleratorRFCavity.MagnesiumDiboride.class, Util.appendPath(cavityPath, RFCavityType.MAGNESIUM_DIBORIDE.getName()));
		GameRegistry.registerTileEntity(TileAcceleratorRFCavity.NiobiumTin.class, Util.appendPath(cavityPath, RFCavityType.NIOBIUM_TIN.getName()));
		GameRegistry.registerTileEntity(TileAcceleratorRFCavity.NiobiumTitanium.class, Util.appendPath(cavityPath, RFCavityType.NIOBIUM_TITANIUM.getName()));
		GameRegistry.registerTileEntity(TileAcceleratorRFCavity.BSCCO.class, Util.appendPath(cavityPath, RFCavityType.BSCCO.getName()));

	
		
		//coolers
		GameRegistry.registerTileEntity(TileAcceleratorCooler.Water.class, Util.appendPath(coolerPath, CoolerType1.WATER.getName()));
		GameRegistry.registerTileEntity(TileAcceleratorCooler.Iron.class, Util.appendPath(coolerPath, CoolerType1.IRON.getName()));
		GameRegistry.registerTileEntity(TileAcceleratorCooler.Redstone.class, Util.appendPath(coolerPath, CoolerType1.REDSTONE.getName()));
		GameRegistry.registerTileEntity(TileAcceleratorCooler.Quartz.class, Util.appendPath(coolerPath, CoolerType1.QUARTZ.getName()));
		GameRegistry.registerTileEntity(TileAcceleratorCooler.Obsidian.class, Util.appendPath(coolerPath, CoolerType1.OBSIDIAN.getName()));
		GameRegistry.registerTileEntity(TileAcceleratorCooler.NetherBrick.class, Util.appendPath(coolerPath, CoolerType1.NETHER_BRICK.getName()));
		GameRegistry.registerTileEntity(TileAcceleratorCooler.Glowstone.class,Util.appendPath(coolerPath, CoolerType1.GLOWSTONE.getName()));
		GameRegistry.registerTileEntity(TileAcceleratorCooler.Lapis.class, Util.appendPath(coolerPath, CoolerType1.LAPIS.getName()));
		GameRegistry.registerTileEntity(TileAcceleratorCooler.Gold.class,Util.appendPath(coolerPath, CoolerType1.GOLD.getName()));
		GameRegistry.registerTileEntity(TileAcceleratorCooler.Prismarine.class, Util.appendPath(coolerPath, CoolerType1.PRISMARINE.getName()));
		GameRegistry.registerTileEntity(TileAcceleratorCooler.Slime.class, Util.appendPath(coolerPath, CoolerType1.SLIME.getName()));
		GameRegistry.registerTileEntity(TileAcceleratorCooler.EndStone.class, Util.appendPath(coolerPath, CoolerType1.END_STONE.getName()));
		GameRegistry.registerTileEntity(TileAcceleratorCooler.Purpur.class, Util.appendPath(coolerPath, CoolerType1.PURPUR.getName()));
		GameRegistry.registerTileEntity(TileAcceleratorCooler.Diamond.class, Util.appendPath(coolerPath, CoolerType1.DIAMOND.getName()));
		GameRegistry.registerTileEntity(TileAcceleratorCooler.Emerald.class, Util.appendPath(coolerPath, CoolerType1.EMERALD.getName()));
		GameRegistry.registerTileEntity(TileAcceleratorCooler.Copper.class, Util.appendPath(coolerPath, CoolerType1.COPPER.getName()));
		GameRegistry.registerTileEntity(TileAcceleratorCooler.Tin.class,  Util.appendPath(coolerPath, CoolerType2.TIN.getName()));
		GameRegistry.registerTileEntity(TileAcceleratorCooler.Lead.class, Util.appendPath(coolerPath, CoolerType2.LEAD.getName()));
		GameRegistry.registerTileEntity(TileAcceleratorCooler.Boron.class, Util.appendPath(coolerPath, CoolerType2.BORON.getName()));
		GameRegistry.registerTileEntity(TileAcceleratorCooler.Lithium.class, Util.appendPath(coolerPath, CoolerType2.LITHIUM.getName()));
		GameRegistry.registerTileEntity(TileAcceleratorCooler.Magnesium.class, Util.appendPath(coolerPath, CoolerType2.MAGNESIUM.getName()));
		GameRegistry.registerTileEntity(TileAcceleratorCooler.Manganese.class, Util.appendPath(coolerPath, CoolerType2.MANGANESE.getName()));
		GameRegistry.registerTileEntity(TileAcceleratorCooler.Aluminum.class, Util.appendPath(coolerPath, CoolerType2.ALUMINUM.getName()));
		GameRegistry.registerTileEntity(TileAcceleratorCooler.Silver.class, Util.appendPath(coolerPath, CoolerType2.SILVER.getName()));
		GameRegistry.registerTileEntity(TileAcceleratorCooler.Fluorite.class, Util.appendPath(coolerPath, CoolerType2.FLUORITE.getName()));
		GameRegistry.registerTileEntity(TileAcceleratorCooler.Villiaumite.class, Util.appendPath(coolerPath, CoolerType2.VILLIAUMITE.getName()));
		GameRegistry.registerTileEntity(TileAcceleratorCooler.Carobbiite.class, Util.appendPath(coolerPath, CoolerType2.CAROBBIITE.getName()));
		GameRegistry.registerTileEntity(TileAcceleratorCooler.Arsenic.class, Util.appendPath(coolerPath, CoolerType2.ARSENIC.getName()));
		GameRegistry.registerTileEntity(TileAcceleratorCooler.LiquidNitrogen.class, Util.appendPath(coolerPath, CoolerType2.LIQUID_NITROGEN.getName()));
		GameRegistry.registerTileEntity(TileAcceleratorCooler.LiquidHelium.class, Util.appendPath(coolerPath, CoolerType2.LIQUID_HELIUM.getName()));
		GameRegistry.registerTileEntity(TileAcceleratorCooler.Enderium.class, Util.appendPath(coolerPath, CoolerType2.ENDERIUM.getName()));
		GameRegistry.registerTileEntity(TileAcceleratorCooler.Cryotheum.class, Util.appendPath(coolerPath, CoolerType2.CRYOTHEUM.getName()));
		
		
		
		
		
		//Particle Chamber Parts
		GameRegistry.registerTileEntity(TileTargetChamberController.class,Util.appendPath(chamberPath, "target_chamber_controller"));
		GameRegistry.registerTileEntity(TileDecayChamberController.class,Util.appendPath(chamberPath, "decay_chamber_controller"));
		GameRegistry.registerTileEntity(TileBeamDumpController.class,Util.appendPath(chamberPath, "beam_dump_controller"));
		GameRegistry.registerTileEntity(TileParticleChamberBeam.class,Util.appendPath(chamberPath, "beam"));
		GameRegistry.registerTileEntity(TileParticleChamberCasing.class,Util.appendPath(chamberPath, "casing"));
		GameRegistry.registerTileEntity(TileParticleChamberGlass.class,Util.appendPath(chamberPath, "glass"));;
		GameRegistry.registerTileEntity(TileParticleChamberBeamPort.class,Util.appendPath(chamberPath, "beam_port"));
		GameRegistry.registerTileEntity(TileParticleChamber.class,Util.appendPath(chamberPath, "particle_chamber"));
		GameRegistry.registerTileEntity(TileParticleChamberEnergyPort.class,Util.appendPath(chamberPath, "energy_port"));
		GameRegistry.registerTileEntity(TileParticleChamberPort.class,Util.appendPath(chamberPath, "port"));
		GameRegistry.registerTileEntity(TileParticleChamberFluidPort.class,Util.appendPath(chamberPath, "fluid_port"));
		
		//detectors
		GameRegistry.registerTileEntity(TileParticleChamberDetector.BubbleChamber.class, Util.appendPath(detectorPath, DetectorType.BUBBLE_CHAMBER.getName()));
		GameRegistry.registerTileEntity(TileParticleChamberDetector.SiliconTracker.class, Util.appendPath(detectorPath, DetectorType.SILLICON_TRACKER.getName()));
		GameRegistry.registerTileEntity(TileParticleChamberDetector.WireChamber.class, Util.appendPath(detectorPath, DetectorType.WIRE_CHAMBER.getName()));
		GameRegistry.registerTileEntity(TileParticleChamberDetector.EMCalorimeter.class, Util.appendPath(detectorPath, DetectorType.EM_CALORIMETER.getName()));
		GameRegistry.registerTileEntity(TileParticleChamberDetector.HadronCalorimeter.class, Util.appendPath(detectorPath, DetectorType.HADRON_CALORIMETER.getName()));
	
	
		//machines
		GameRegistry.registerTileEntity(TileQMDProcessor.TileOreLeacher.class,new ResourceLocation(QMD.MOD_ID,"ore_leacher"));
		GameRegistry.registerTileEntity(TileQMDProcessor.TileIrradiator.class,new ResourceLocation(QMD.MOD_ID,"irradiator"));
		
		
		
		//contaiment parts
		
		GameRegistry.registerTileEntity(TileNeutralContainmentController.class,Util.appendPath(containmentPath, "neutral_containment_controller"));
		GameRegistry.registerTileEntity(TileContainmentCasing.class,Util.appendPath(containmentPath, "casing"));
		GameRegistry.registerTileEntity(TileContainmentGlass.class,Util.appendPath(containmentPath, "glass"));;
		GameRegistry.registerTileEntity(TileContainmentBeamPort.class,Util.appendPath(containmentPath, "beam_port"));
		GameRegistry.registerTileEntity(TileContainmentEnergyPort.class,Util.appendPath(containmentPath, "energy_port"));
		GameRegistry.registerTileEntity(TileContainmentPort.class,Util.appendPath(containmentPath, "port"));
		GameRegistry.registerTileEntity(TileContainmentVent.class,Util.appendPath(containmentPath, "vent"));
		GameRegistry.registerTileEntity(TileContainmentCoil.class,Util.appendPath(containmentPath, "coil"));
		GameRegistry.registerTileEntity(TileContainmentLaser.class,Util.appendPath(containmentPath, "laser"));
	}
}
