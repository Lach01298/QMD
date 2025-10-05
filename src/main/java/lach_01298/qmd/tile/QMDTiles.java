package lach_01298.qmd.tile;

import lach_01298.qmd.QMD;
import lach_01298.qmd.accelerator.tile.*;
import lach_01298.qmd.enums.BlockTypes.*;
import lach_01298.qmd.fission.tile.QMDTileFissionShield;
import lach_01298.qmd.liquefier.tile.*;
import lach_01298.qmd.machine.tile.TileQMDProcessors;
import lach_01298.qmd.particleChamber.tile.*;
import lach_01298.qmd.pipe.TileBeamline;
import lach_01298.qmd.util.Util;
import lach_01298.qmd.vacuumChamber.tile.*;
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
	private static ResourceLocation heaterPath = new ResourceLocation(QMD.MOD_ID,"containment_heater");

	private static ResourceLocation compressorPath = new ResourceLocation(QMD.MOD_ID,"liquefier_compressor");
	
	public static void register()
	{
		//other
		GameRegistry.registerTileEntity(TileBeamline.class,new ResourceLocation(QMD.MOD_ID,"beamline"));
		GameRegistry.registerTileEntity(QMDTileRTG.Strontium.class,new ResourceLocation(QMD.MOD_ID,"rtg_strontium"));
		GameRegistry.registerTileEntity(QMDTileFissionShield.Hafnium.class,new ResourceLocation(QMD.MOD_ID,"fission_shield_hafnium"));
		GameRegistry.registerTileEntity(TileCreativeParticleSource.class,new ResourceLocation(QMD.MOD_ID,"creative_particle_source"));
		GameRegistry.registerTileEntity(QMDTileTurbineBlade.SuperAlloy.class,new ResourceLocation(QMD.MOD_ID,"turbine_blade_super_alloy"));
		
		//Accelerator parts
		GameRegistry.registerTileEntity(TileLinearAcceleratorController.class,Util.appendPath(acceleratorPath, "linear_controller"));
		GameRegistry.registerTileEntity(TileRingAcceleratorController.class,Util.appendPath(acceleratorPath, "ring_controller"));
		GameRegistry.registerTileEntity(TileBeamDiverterController.class,Util.appendPath(acceleratorPath, "beam_diverter_controller"));
		GameRegistry.registerTileEntity(TileBeamSplitterController.class,Util.appendPath(acceleratorPath, "beam_splitter_controller"));
		GameRegistry.registerTileEntity(TileDeceleratorController.class,Util.appendPath(acceleratorPath, "decelerator_controller"));
		GameRegistry.registerTileEntity(TileMassSpectrometerController.class,Util.appendPath(acceleratorPath, "mass_spectrometer_controller"));
		GameRegistry.registerTileEntity(TileAcceleratorBeam.class,Util.appendPath(acceleratorPath, "beam"));
		GameRegistry.registerTileEntity(TileAcceleratorCasing.class,Util.appendPath(acceleratorPath, "casing"));
		GameRegistry.registerTileEntity(TileAcceleratorGlass.class,Util.appendPath(acceleratorPath, "glass"));
		GameRegistry.registerTileEntity(TileAcceleratorVent.class,Util.appendPath(acceleratorPath, "vent"));
		GameRegistry.registerTileEntity(TileAcceleratorBeamPort.class,Util.appendPath(acceleratorPath, "beam_port"));
		GameRegistry.registerTileEntity(TileAcceleratorSynchrotronPort.class,Util.appendPath(acceleratorPath, "synchrotron_port"));
		GameRegistry.registerTileEntity(TileAcceleratorIonSource.class,Util.appendPath(acceleratorPath, "source"));
		GameRegistry.registerTileEntity(TileAcceleratorIonSource.Basic.class,Util.appendPath(acceleratorPath, "basic_ion_source"));
		GameRegistry.registerTileEntity(TileAcceleratorIonSource.Laser.class,Util.appendPath(acceleratorPath, "laser_ion_source"));
		
		GameRegistry.registerTileEntity(TileAcceleratorIonCollector.class,Util.appendPath(acceleratorPath, "ion_collector"));
		GameRegistry.registerTileEntity(TileAcceleratorYoke.class,Util.appendPath(acceleratorPath, "yoke"));
		GameRegistry.registerTileEntity(TileAcceleratorEnergyPort.class,Util.appendPath(acceleratorPath, "energy_port"));
		GameRegistry.registerTileEntity(TileAcceleratorComputerPort.class,Util.appendPath(acceleratorPath, "computer_port"));
		GameRegistry.registerTileEntity(TileAcceleratorPort.class,Util.appendPath(acceleratorPath, "port"));
		GameRegistry.registerTileEntity(TileAcceleratorRedstonePort.class,Util.appendPath(acceleratorPath, "redstone_port"));
		
		//magnets
		GameRegistry.registerTileEntity(TileAcceleratorMagnet.class, Util.appendPath(magnetPath, "magnet"));
		GameRegistry.registerTileEntity(TileAcceleratorMagnet.Copper.class, Util.appendPath(magnetPath, MagnetType.COPPER.getName()));
		GameRegistry.registerTileEntity(TileAcceleratorMagnet.MagnesiumDiboride.class, Util.appendPath(magnetPath, MagnetType.MAGNESIUM_DIBORIDE.getName()));
		GameRegistry.registerTileEntity(TileAcceleratorMagnet.NiobiumTin.class, Util.appendPath(magnetPath, MagnetType.NIOBIUM_TIN.getName()));
		GameRegistry.registerTileEntity(TileAcceleratorMagnet.NiobiumTitanium.class, Util.appendPath(magnetPath, MagnetType.NIOBIUM_TITANIUM.getName()));
		GameRegistry.registerTileEntity(TileAcceleratorMagnet.BSCCO.class, Util.appendPath(magnetPath, MagnetType.BSCCO.getName()));
		GameRegistry.registerTileEntity(TileAcceleratorMagnet.Aluminium.class, Util.appendPath(magnetPath, MagnetType.Aluminium.getName()));
		GameRegistry.registerTileEntity(TileAcceleratorMagnet.SSFAF.class, Util.appendPath(magnetPath, MagnetType.SSFAF.getName()));
		GameRegistry.registerTileEntity(TileAcceleratorMagnet.YBCO.class, Util.appendPath(magnetPath, MagnetType.YBCO.getName()));
		
		//RF Cavities
		GameRegistry.registerTileEntity(TileAcceleratorRFCavity.class, Util.appendPath(cavityPath, "rf_cavity"));
		GameRegistry.registerTileEntity(TileAcceleratorRFCavity.Copper.class, Util.appendPath(cavityPath, RFCavityType.COPPER.getName()));
		GameRegistry.registerTileEntity(TileAcceleratorRFCavity.MagnesiumDiboride.class, Util.appendPath(cavityPath, RFCavityType.MAGNESIUM_DIBORIDE.getName()));
		GameRegistry.registerTileEntity(TileAcceleratorRFCavity.NiobiumTin.class, Util.appendPath(cavityPath, RFCavityType.NIOBIUM_TIN.getName()));
		GameRegistry.registerTileEntity(TileAcceleratorRFCavity.NiobiumTitanium.class, Util.appendPath(cavityPath, RFCavityType.NIOBIUM_TITANIUM.getName()));
		GameRegistry.registerTileEntity(TileAcceleratorRFCavity.BSCCO.class, Util.appendPath(cavityPath, RFCavityType.BSCCO.getName()));
		GameRegistry.registerTileEntity(TileAcceleratorRFCavity.Aluminium.class, Util.appendPath(cavityPath, RFCavityType.Aluminium.getName()));
		GameRegistry.registerTileEntity(TileAcceleratorRFCavity.SSFAF.class, Util.appendPath(cavityPath, RFCavityType.SSFAF.getName()));
		GameRegistry.registerTileEntity(TileAcceleratorRFCavity.YBCO.class, Util.appendPath(cavityPath, RFCavityType.YBCO.getName()));

		//coolers
		GameRegistry.registerTileEntity(TileAcceleratorCooler.class, Util.appendPath(coolerPath, "cooler"));
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
		GameRegistry.registerTileEntity(TileCollisionChamberController.class,Util.appendPath(chamberPath, "collision_chamber_controller"));
		GameRegistry.registerTileEntity(TileParticleChamberBeam.class,Util.appendPath(chamberPath, "beam"));
		GameRegistry.registerTileEntity(TileParticleChamberCasing.class,Util.appendPath(chamberPath, "casing"));
		GameRegistry.registerTileEntity(TileParticleChamberGlass.class,Util.appendPath(chamberPath, "glass"));;
		GameRegistry.registerTileEntity(TileParticleChamberBeamPort.class,Util.appendPath(chamberPath, "beam_port"));
		GameRegistry.registerTileEntity(TileParticleChamber.class,Util.appendPath(chamberPath, "particle_chamber"));
		GameRegistry.registerTileEntity(TileParticleChamberEnergyPort.class,Util.appendPath(chamberPath, "energy_port"));
		GameRegistry.registerTileEntity(TileParticleChamberPort.class,Util.appendPath(chamberPath, "port"));
		GameRegistry.registerTileEntity(TileParticleChamberFluidPort.class,Util.appendPath(chamberPath, "fluid_port"));
		
		//detectors
		GameRegistry.registerTileEntity(TileParticleChamberDetector.class, Util.appendPath(detectorPath, "detector"));
		GameRegistry.registerTileEntity(TileParticleChamberDetector.BubbleChamber.class, Util.appendPath(detectorPath, DetectorType.BUBBLE_CHAMBER.getName()));
		GameRegistry.registerTileEntity(TileParticleChamberDetector.SiliconTracker.class, Util.appendPath(detectorPath, DetectorType.SILLICON_TRACKER.getName()));
		GameRegistry.registerTileEntity(TileParticleChamberDetector.WireChamber.class, Util.appendPath(detectorPath, DetectorType.WIRE_CHAMBER.getName()));
		GameRegistry.registerTileEntity(TileParticleChamberDetector.EMCalorimeter.class, Util.appendPath(detectorPath, DetectorType.EM_CALORIMETER.getName()));
		GameRegistry.registerTileEntity(TileParticleChamberDetector.HadronCalorimeter.class, Util.appendPath(detectorPath, DetectorType.HADRON_CALORIMETER.getName()));

		//machines
		GameRegistry.registerTileEntity(TileQMDProcessors.TileOreLeacher.class,new ResourceLocation(QMD.MOD_ID,"ore_leacher"));
		GameRegistry.registerTileEntity(TileQMDProcessors.TileIrradiator.class,new ResourceLocation(QMD.MOD_ID,"irradiator"));
		GameRegistry.registerTileEntity(TileAtmosphereCollector.class,new ResourceLocation(QMD.MOD_ID,"atmosphere_collector"));
		GameRegistry.registerTileEntity(TileLiquidCollector.class,new ResourceLocation(QMD.MOD_ID,"liquid_collector"));

		//vacuum chamber parts
		GameRegistry.registerTileEntity(TileExoticContainmentController.class,Util.appendPath(containmentPath, "neutral_containment_controller"));
		GameRegistry.registerTileEntity(TileNucleosynthesisChamberController.class,Util.appendPath(containmentPath, "neucleosynthesis_chamber_controller"));
		GameRegistry.registerTileEntity(TileVacuumChamberCasing.class,Util.appendPath(containmentPath, "casing"));
		GameRegistry.registerTileEntity(TileVacuumChamberGlass.class,Util.appendPath(containmentPath, "glass"));;
		GameRegistry.registerTileEntity(TileVacuumChamberBeamPort.class,Util.appendPath(containmentPath, "beam_port"));
		GameRegistry.registerTileEntity(TileVacuumChamberEnergyPort.class,Util.appendPath(containmentPath, "energy_port"));
		GameRegistry.registerTileEntity(TileVacuumChamberPort.class,Util.appendPath(containmentPath, "port"));
		GameRegistry.registerTileEntity(TileVacuumChamberVent.class,Util.appendPath(containmentPath, "vent"));
		GameRegistry.registerTileEntity(TileVacuumChamberCoil.class,Util.appendPath(containmentPath, "coil"));
		GameRegistry.registerTileEntity(TileVacuumChamberLaser.class,Util.appendPath(containmentPath, "laser"));
		GameRegistry.registerTileEntity(TileVacuumChamberBeam.class,Util.appendPath(containmentPath, "beam"));
		GameRegistry.registerTileEntity(TileVacuumChamberFluidPort.class,Util.appendPath(containmentPath, "fluid_port"));
		GameRegistry.registerTileEntity(TileVacuumChamberPlasmaNozzle.class,Util.appendPath(containmentPath, "cd_nozzle"));
		GameRegistry.registerTileEntity(TileVacuumChamberPlasmaGlass.class,Util.appendPath(containmentPath, "plasma_glass"));
		GameRegistry.registerTileEntity(TileVacuumChamberHeaterVent.class,Util.appendPath(containmentPath, "heater_vent"));
		GameRegistry.registerTileEntity(TileVacuumChamberRedstonePort.class,Util.appendPath(containmentPath, "redstone_port"));

		//heaters
		GameRegistry.registerTileEntity(TileVacuumChamberHeater.class, Util.appendPath(heaterPath, "heater"));
		GameRegistry.registerTileEntity(TileVacuumChamberHeater.Iron.class, Util.appendPath(heaterPath, HeaterType.IRON.getName()));
		GameRegistry.registerTileEntity(TileVacuumChamberHeater.Redstone.class, Util.appendPath(heaterPath, HeaterType.REDSTONE.getName()));
		GameRegistry.registerTileEntity(TileVacuumChamberHeater.Quartz.class, Util.appendPath(heaterPath, HeaterType.QUARTZ.getName()));
		GameRegistry.registerTileEntity(TileVacuumChamberHeater.Obsidian.class, Util.appendPath(heaterPath, HeaterType.OBSIDIAN.getName()));
		GameRegistry.registerTileEntity(TileVacuumChamberHeater.Glowstone.class,Util.appendPath(heaterPath, HeaterType.GLOWSTONE.getName()));
		GameRegistry.registerTileEntity(TileVacuumChamberHeater.Lapis.class, Util.appendPath(heaterPath, HeaterType.LAPIS.getName()));
		GameRegistry.registerTileEntity(TileVacuumChamberHeater.Gold.class,Util.appendPath(heaterPath, HeaterType.GOLD.getName()));
		GameRegistry.registerTileEntity(TileVacuumChamberHeater.Diamond.class, Util.appendPath(heaterPath, HeaterType.DIAMOND.getName()));

		// liquefier parts
		GameRegistry.registerTileEntity(TileLiquefierController.class,new ResourceLocation(QMD.MOD_ID,"liquefier_controller"));
		GameRegistry.registerTileEntity(TileLiquefierNozzle.class,new ResourceLocation(QMD.MOD_ID,"liquefier_nozzle"));
		GameRegistry.registerTileEntity(TileLiquefierFluidPort.class,new ResourceLocation(QMD.MOD_ID,"liquefier_fluid_port"));
		GameRegistry.registerTileEntity(TileLiquefierEnergyPort.class,new ResourceLocation(QMD.MOD_ID,"liquefier_energy_port"));
		GameRegistry.registerTileEntity(TileLiquefierCompressor.Copper.class, Util.appendPath(compressorPath, CompressorType.COPPER.getName()));
		GameRegistry.registerTileEntity(TileLiquefierCompressor.Neodymium.class, Util.appendPath(compressorPath, CompressorType.NEODYMIUM.getName()));
		GameRegistry.registerTileEntity(TileLiquefierCompressor.SamariumCobalt.class, Util.appendPath(compressorPath, CompressorType.SAMARIUM_COBALT.getName()));


	}
}
