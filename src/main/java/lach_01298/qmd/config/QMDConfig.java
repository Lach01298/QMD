package lach_01298.qmd.config;

import lach_01298.qmd.QMD;
import lach_01298.qmd.QMDRadSources;
import nc.util.Lang;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class QMDConfig {

	private static Configuration config = null;
	
	public static final String CATEGORY_PROCESSORS = "processors";
	public static final String CATEGORY_ACCELERATOR = "accelerator";
	public static final String CATEGORY_PARTICLE_CHAMBER = "particle_chamber";
	public static final String CATEGORY_VACUUM_CHAMBER = "vacuum_chamber";
	public static final String CATEGORY_HEAT_EXCHANGER = "heat_exchanger";
	public static final String CATEGORY_FISSION = "fission";
	public static final String CATEGORY_FUSION = "fusion";
	public static final String CATEGORY_TOOLS = "tools";
	public static final String CATEGORY_RECIPES = "recipes";
	public static final String CATEGORY_OTHER = "other";

	
	
	public static int accelerator_linear_min_size;
	public static int accelerator_linear_max_size;
	public static int accelerator_ring_min_size;
	public static int accelerator_ring_max_size;
	
	
	public static int accelerator_base_heat_capacity;
	public static int accelerator_base_energy_capacity;
	public static int accelerator_base_input_tank_capacity;
	public static int accelerator_base_output_tank_capacity;
	
	public static double accelerator_thermal_conductivity;
	public static int minimium_accelerator_ring_input_particle_energy;
	public static int[] ion_source_power;
	public static int[] ion_source_output_multiplier;
	public static double[] ion_source_focus;
	
	
	public static int[] RF_cavity_voltage; //in keV
	public static double[] RF_cavity_efficiency;
	public static int[] RF_cavity_heat_generated;
	public static int[]  RF_cavity_base_power;
	public static int[] RF_cavity_max_temp;
	
	public static double[] magnet_strength;
	public static double[] magnet_efficiency;
	public static int[] magnet_heat_generated;
	public static int[] magnet_base_power;
	public static int[] magnet_max_temp;

	public static int[] cooler_heat_removed;
	public static String[] cooler_rule;

	public static double beamAttenuationRate;
	public static int beamDiverterRadius;

	public static String[] mass_spectrometer_valid_magnets;
	public static String[] mass_spectrometer_valid_sources;

	public static boolean accelerator_explosion;
	
	public static int target_chamber_power;
	public static int decay_chamber_power;
	public static int beam_dump_power;
	public static int collision_chamber_power;
	public static int[] detector_base_power;
	public static double[] detector_efficiency;
	
	
	public static int particle_chamber_base_energy_capacity;
	public static int particle_chamber_input_tank_capacity;
	public static int particle_chamber_output_tank_capacity;
	
	public static int[] vacuum_chamber_part_power;
	public static int[] vacuum_chamber_part_heat;
	public static int[] vacuum_chamber_part_max_temp;
	
	public static int vacuum_chamber_base_energy_capacity;
	public static int vacuum_chamber_input_tank_capacity;
	public static int vacuum_chamber_output_tank_capacity;
	
	public static boolean exotic_containment_explosion;
	public static boolean exotic_containment_gamma_flash;
	public static double exotic_containment_radiation;
	public static double exotic_containment_explosion_size;

	public static boolean nucleosynthesis_chamber_explosion;
	
	public static int[] heater_heat_removed;
	public static String[]heater_rule;

	public static int liquefier_base_energy_capacity;
	public static int liquefier_input_tank_capacity;
	public static int liquefier_output_tank_capacity;
	public static double[] liquefier_compressor_energy_efficiency;
	public static double[] liquefier_compressor_heat_efficiency;

	public static int[] processor_power;
	public static int[] processor_time;
	public static double irradiator_rad_res;
	public static int irradiator_fuel_usage;
	
	public static boolean[] register_tool;
	public static int[] tool_mining_level;
	public static int[] tool_durability;
	public static double[] tool_speed;
	public static double[] tool_attack_damage;
	public static int[] tool_enchantability;
	public static int drill_energy_usage;
	public static int[] drill_energy_capacity;
	public static int[] drill_radius;
	
	public static double[] lepton_damage;
	public static double[] lepton_radiation;
	public static double[] lepton_range;
	public static int lepton_cool_down;
	public static int lepton_particle_usage;
	
	public static double gluon_damage;
	public static double gluon_radiation;
	public static double gluon_range;
	public static int gluon_particle_usage;
	
	public static double antimatter_launcher_damage;
	public static double antimatter_launcher_radiation;
	public static double antimatter_launcher_explosion_size;
	public static int antimatter_launcher_cool_down;
	public static int antimatter_launcher_particle_usage;
	
	public static int cell_lifetime;
	public static double cell_radiation;
	public static double cell_explosion_size;
	
	public static int[] hev_armour;
	public static double[] hev_rad_res;
	public static double [] hev_toughness;
	public static int[] hev_energy;
	public static int[] hev_power;
	
	public static int ki_time;
	
	public static double[] fission_reflector_efficiency;
	public static double[] fission_reflector_reflectivity;

	public static double[] fission_shield_heat_per_flux;
	public static double[] fission_shield_efficiency;
	
	public static int[] copernicium_fuel_time;
	public static int[] copernicium_heat_generation;
	public static double[] copernicium_efficiency;
	public static int[] copernicium_criticality;
	public static double[] copernicium_decay_factor;
	public static boolean[] copernicium_self_priming;
	public static double[] copernicium_radiation;
	
	public static int[] rtg_power;
	

	public static int beam_scaling;

	//recipe scale factors
	public static int rsf_target_chamber;
	public static int rsf_nucleosynthesis;
	
	public static boolean override_nc_recipes;
	
	//public static int item_ticker_chunks_per_tick;
	
	public static double[]  turbine_blade_efficiency;
	public static double[]  turbine_blade_expansion;

	
	
	
	
	public static Configuration getConfig()
	{
		return config;
	}

	public static void preInit()
	{
		config = new Configuration(new File(Loader.instance().getConfigDir(), "qmd.cfg"));
		syncConfig(true, true);
	
	}

	public static void postInit()
	{
		//outputInfo();
	}
	
	public static void clientPreInit()
	{
		MinecraftForge.EVENT_BUS.register(new ClientConfigEventHandler());
	}


	private static void syncConfig(boolean loadFromFile, boolean setFromConfig)
	{
		if (loadFromFile) config.load();

		Property propertyProcessorPower = config.get(CATEGORY_PROCESSORS, "power", new int[] {50,100,50}, Lang.localize("gui.qmd.config.processors.power.comment"), 0, 32767);
		propertyProcessorPower.setLanguageKey("gui.qmd.config.processors.power");
		
		Property propertyProcessorTime = config.get(CATEGORY_PROCESSORS, "time", new int[] {400,200,500}, Lang.localize("gui.qmd.config.processors.time.comment"), 0, 32767);
		propertyProcessorTime.setLanguageKey("gui.qmd.config.processors.time");
		
		Property propertyIrradiatorRadRes = config.get(CATEGORY_PROCESSORS, "irradiator_rad_res", 10000.0D, Lang.localize("gui.qmd.config.processors.irradiator_rad_res.comment"), 0.0D, Double.MAX_VALUE);
		propertyIrradiatorRadRes.setLanguageKey("gui.qmd.config.processors.irradiator_rad_res");
		Property propertyIrradiatorFuelUsage = config.get(CATEGORY_PROCESSORS, "irradiator_fuel_usage", 10, Lang.localize("gui.qmd.config.processors.irradiator_fuel_usage.comment"), 0, Integer.MAX_VALUE);
		propertyIrradiatorFuelUsage.setLanguageKey("gui.qmd.config.processors.irradiator_fuel_usage");
		
		Property propertyAcceleratorLinearMinSize = config.get(CATEGORY_ACCELERATOR, "accelerator_linear_min_size", 6, Lang.localize("gui.qmd.config.accelerator.accelerator_linear_min_size.comment"), 6, 255);
		propertyAcceleratorLinearMinSize.setLanguageKey("gui.qmd.config.accelerator.accelerator_linear_min_size");
		Property propertyAcceleratorLinearMaxSize = config.get(CATEGORY_ACCELERATOR, "accelerator_linear_max_size", 100, Lang.localize("gui.qmd.config.accelerator.accelerator_linear_max_size.comment"), 6, 255);
		propertyAcceleratorLinearMaxSize.setLanguageKey("gui.qmd.config.accelerator.accelerator_linear_max_size");
		
		Property propertyAcceleratorRingMinSize = config.get(CATEGORY_ACCELERATOR, "accelerator_ring_min_size", 11, Lang.localize("gui.qmd.config.accelerator.accelerator_ring_min_size.comment"), 11, 255);
		propertyAcceleratorRingMinSize.setLanguageKey("gui.qmd.config.accelerator.accelerator_ring_min_size");
		Property propertyAcceleratorRingMaxSize = config.get(CATEGORY_ACCELERATOR, "accelerator_ring_max_size", 100, Lang.localize("gui.qmd.config.accelerator.accelerator_ring_max_size.comment"), 11, 255);
		propertyAcceleratorRingMaxSize.setLanguageKey("gui.qmd.config.accelerator.accelerator_ring_max_size");
		
		
		
		Property propertyAcceleratorBaseHeatCapacity = config.get(CATEGORY_ACCELERATOR, "accelerator_base_heat_capacity", 25000, Lang.localize("gui.qmd.config.accelerator.accelerator_base_heat_capacity.comment"), 1, Integer.MAX_VALUE);
		propertyAcceleratorBaseHeatCapacity.setLanguageKey("gui.qmd.config.accelerator.accelerator_base_heat_capacity");
		Property propertyAcceleratorBaseEnergyCapacity = config.get(CATEGORY_ACCELERATOR, "accelerator_base_energy_capacity", 40000, Lang.localize("gui.qmd.config.accelerator.accelerator_base_energy_capacity.comment"), 1, Integer.MAX_VALUE);
		propertyAcceleratorBaseEnergyCapacity.setLanguageKey("gui.qmd.config.accelerator.accelerator_base_energy_capacity");
		Property propertyAcceleratorBaseInputTankCapacity = config.get(CATEGORY_ACCELERATOR, "accelerator_base_input_tank_capacity", 10, Lang.localize("gui.qmd.config.accelerator.accelerator_base_input_tank_capacity.comment"), 1, Integer.MAX_VALUE);
		propertyAcceleratorBaseInputTankCapacity.setLanguageKey("gui.qmd.config.accelerator.accelerator_base_input_tank_capacity");
		Property propertyAcceleratorBaseOutputTankCapacity = config.get(CATEGORY_ACCELERATOR, "accelerator_base_output_tank_capacity", 3200, Lang.localize("gui.qmd.config.accelerator.accelerator_base_output_tank_capacity.comment"), 1, Integer.MAX_VALUE);
		propertyAcceleratorBaseOutputTankCapacity.setLanguageKey("gui.qmd.config.accelerator.accelerator_base_output_tank_capacity");
		
		Property propertyAcceleratorThermalConductivity = config.get(CATEGORY_ACCELERATOR, "accelerator_thermal_conductivity",  0.0025d, Lang.localize("gui.qmd.config.accelerator.accelerator_thermal_conductivity.comment"), 0d, Double.MAX_VALUE);
		propertyAcceleratorThermalConductivity.setLanguageKey("gui.qmd.config.accelerator.accelerator_thermal_conductivity");
		Property propertyAcceleratorRingInputEnergy = config.get(CATEGORY_ACCELERATOR, "minimium_accelerator_ring_input_particle_energy", 5000, Lang.localize("gui.qmd.config.accelerator.minimium_accelerator_ring_input_particle_energy.comment"), 0, Integer.MAX_VALUE);
		propertyAcceleratorRingInputEnergy.setLanguageKey("gui.qmd.config.accelerator.minimium_accelerator_ring_input_particle_energy");
		
		Property propertyIonSourcePower = config.get(CATEGORY_ACCELERATOR, "ion_source_power", new int[] {500, 2000}, Lang.localize("gui.qmd.config.accelerator.ion_source_power.comment"), 0, Integer.MAX_VALUE);
		propertyIonSourcePower.setLanguageKey("gui.qmd.config.accelerator.ion_source_power");
		Property propertyIonSourceOutputMultiplier = config.get(CATEGORY_ACCELERATOR, "ion_source_output_multiplier", new int[] {1, 2}, Lang.localize("gui.qmd.config.accelerator.ion_source_output_multiplier.comment"), 1, Integer.MAX_VALUE);
		propertyIonSourceOutputMultiplier.setLanguageKey("gui.qmd.config.accelerator.ion_source_output_multiplier");
		Property propertyIonSourceFocus = config.get(CATEGORY_ACCELERATOR, "ion_source_focus", new double[] {0.4, 0.2d}, Lang.localize("gui.qmd.config.accelerator.ion_source_focus.comment"), 0d, Double.MAX_VALUE);
		propertyIonSourceFocus.setLanguageKey("gui.qmd.config.accelerator.ion_source_focus");
		
		
		
		Property propertyBeamAttenuationRate = config.get(CATEGORY_ACCELERATOR, "beam_attenuation_rate", 0.02D, Lang.localize("gui.qmd.config.accelerator.beam_attenuation_rate.comment"), 0.0D, 1000D);
		propertyBeamAttenuationRate.setLanguageKey("gui.qmd.config.accelerator.beam_attenuation_rate");
		Property propertyBeamDiverterRadius = config.get(CATEGORY_ACCELERATOR, "beam_diverter_radius", 160, Lang.localize("gui.qmd.config.accelerator.beam_diverter_radius.comment"), 0, 1000);
		propertyBeamDiverterRadius.setLanguageKey("gui.qmd.config.accelerator.beam_diverter_radius");
		
		Property propertyMassSpectrometerValidMagnets = config.get(CATEGORY_ACCELERATOR, "mass_spectrometer_valid_magnets", new String[] {"bscco"}, Lang.localize("gui.qmd.config.accelerator.mass_spectrometer_valid_magnets.comment"));
		propertyMassSpectrometerValidMagnets.setLanguageKey("gui.qmd.config.accelerator.mass_spectrometer_valid_magnets");
		
		Property propertyMassSpectrometerValidSources = config.get(CATEGORY_ACCELERATOR, "mass_spectrometer_valid_sources", new String[] {"laser"}, Lang.localize("gui.qmd.config.accelerator.mass_spectrometer_valid_sources.comment"));
		propertyMassSpectrometerValidSources.setLanguageKey("gui.qmd.config.accelerator.mass_spectrometer_valid_sources");
		
		Property propertyRFCavityVoltage = config.get(CATEGORY_ACCELERATOR, "RF_cavity_voltage", new int[] {200, 500, 1000, 2000, 4000,100,1500,3000}, Lang.localize("gui.qmd.config.accelerator.RF_cavity_voltage.comment"), 0, Integer.MAX_VALUE);
		propertyRFCavityVoltage.setLanguageKey("gui.qmd.config.accelerator.RF_cavity_voltage");
		Property propertyRFCavityEfficiency = config.get(CATEGORY_ACCELERATOR, "RF_cavity_efficiency", new double[] {0.75D, 0.8D, 0.90D, 0.95D, 0.99D,0.5D,0.95D,0.99D}, Lang.localize("gui.qmd.config.accelerator.RF_cavity_efficiency.comment"), 0D, 1D);
		propertyRFCavityEfficiency.setLanguageKey("gui.qmd.config.accelerator.RF_cavity_efficiency");
		Property propertyRFCavityHeatGenerated = config.get(CATEGORY_ACCELERATOR, "RF_cavity_heat_generated", new int[] {300, 540, 1020, 1980, 3900,180,1500,2940}, Lang.localize("gui.qmd.config.accelerator.RF_cavity_heat_generated.comment"), 0, Integer.MAX_VALUE);
		propertyRFCavityHeatGenerated.setLanguageKey("gui.qmd.config.accelerator.RF_cavity_heat_generated");
		Property propertyRFCavityBasePower = config.get(CATEGORY_ACCELERATOR, "RF_cavity_base_power", new int[] {500, 1000, 2000, 4000, 8000,250,3000,6000}, Lang.localize("gui.qmd.config.accelerator.RF_cavity_base_power.comment"), 0, Integer.MAX_VALUE);
		propertyRFCavityBasePower.setLanguageKey("gui.qmd.config.accelerator.RF_cavity_base_power");
		Property propertyRFCavityMaxTemp = config.get(CATEGORY_ACCELERATOR, "RF_cavity_max_temp", new int[] {350, 39, 18, 10, 110,350,56,95}, Lang.localize("gui.qmd.config.accelerator.RF_cavity_max_temp.comment"), 0, 400);
		propertyRFCavityMaxTemp.setLanguageKey("gui.qmd.config.accelerator.RF_cavity_max_temp");
		
		Property propertyMagnetStrength = config.get(CATEGORY_ACCELERATOR, "magnet_strength", new double[] {0.2D, 0.5D, 1D, 2D, 4D, 0.1D, 1.5D,3D}, Lang.localize("gui.qmd.config.accelerator.magnet_strength.comment"), 0D, 100D);
		propertyMagnetStrength.setLanguageKey("gui.qmd.config.accelerator.magnet_strength");
		Property propertyMagnetEfficiency = config.get(CATEGORY_ACCELERATOR, "magnet_efficiency", new double[] {0.75D, 0.8D, 0.90D, 0.95D, 0.99D,0.5D,0.95D,0.99D}, Lang.localize("gui.qmd.config.accelerator.magnet_efficiency.comment"), 0D, 1D);
		propertyMagnetEfficiency.setLanguageKey("gui.qmd.config.accelerator.magnet_efficiency");
		Property propertyMagnetHeatGenerated = config.get(CATEGORY_ACCELERATOR, "magnet_heat_generated", new int[] {300, 540, 1020, 1980, 3900,180,1500,2940}, Lang.localize("gui.qmd.config.accelerator.magnet_heat_generated.comment"),0, Integer.MAX_VALUE);
		propertyMagnetHeatGenerated.setLanguageKey("gui.qmd.config.accelerator.magnet_heat_generated");
		Property propertyMagnetBasePower = config.get(CATEGORY_ACCELERATOR, "magnet_base_power", new int[] {1000, 2000, 4000, 8000, 16000,500,6000,12000}, Lang.localize("gui.qmd.config.accelerator.magnet_base_power.comment"), 0, Integer.MAX_VALUE);
		propertyMagnetBasePower.setLanguageKey("gui.qmd.config.accelerator.magnet_base_power");
		Property propertyMagnetMaxTemp = config.get(CATEGORY_ACCELERATOR, "magnet_max_temp", new int[] {350, 39, 18, 10, 110,350,56,95}, Lang.localize("gui.qmd.config.accelerator.magnet_max_temp.comment"), 0, Integer.MAX_VALUE);
		propertyMagnetMaxTemp.setLanguageKey("gui.qmd.config.accelerator.magnet_max_temp");
		
		Property propertyCoolerHeatRemoved = config.get(CATEGORY_ACCELERATOR, "cooler_heat_removed", new int[] {60, 55, 115, 75, 70, 90, 110, 130, 95, 85, 165, 50, 100, 185, 135, 80, 120, 65, 105, 125, 150, 180, 175, 160, 155, 170, 140, 145, 195, 200, 190, 205}, Lang.localize("gui.qmd.config.accelerator.cooler_heat_removed.comment"), 0, Integer.MAX_VALUE);
		propertyCoolerHeatRemoved.setLanguageKey("gui.qmd.config.accelerator.cooler_heat_removed");
		Property propertyCoolerRule = config.get(CATEGORY_ACCELERATOR, "cooler_rule", new String[] { "one cavity",
				"one magnet", "one cavity && one magnet", "one redstone cooler", "two glowstone coolers",
				"one obsidian cooler", "two different magnets", "one yoke && one magnet", "two iron coolers", "two water coolers",
				"two lead coolers && one water cooler", "one yoke", "two end_stone coolers", "one gold cooler && one prismarine cooler",
				"one cavity && one prismarine cooler", "one water cooler", "two lapis coolers", "one iron cooler",
				"one yoke && one cavity", "one boron cooler", "one end_stone cooler && one prismarine cooler",
				"one gold cooler && one quartz cooler", "one tin cooler && one quartz cooler", "two arsenic coolers",
				"three gold coolers", "one purpur cooler && one prismarine cooler",
				"one end_stone cooler && one gold cooler", "two different cavity", "one lapis cooler && one gold cooler",
				"one boron cooler && one lapis cooler", "three purpur coolers", "three tin coolers" },
				Lang.localize("gui.qmd.config.accelerator.cooler_rule.comment"));
		propertyCoolerRule.setLanguageKey("gui.qmd.config.accelerator.cooler_rule");
		
		Property propertyAcceleratorExplosion = config.get(CATEGORY_ACCELERATOR, "accelerator_explosion", true, Lang.localize("gui.qmd.config.accelerator.accelerator_explosion.comment"));
		propertyAcceleratorExplosion.setLanguageKey("gui.qmd.config.accelerator.accelerator_explosion");
		
		
		Property propertyTargetChamberPower = config.get(CATEGORY_PARTICLE_CHAMBER, "target_chamber_power", 5000, Lang.localize("gui.qmd.config.particle_chamber.target_chamber_power.comment"), 0, Integer.MAX_VALUE);
		propertyTargetChamberPower.setLanguageKey("gui.qmd.config.particle_chamber.target_chamber_power");
		Property propertyDecayChamberPower = config.get(CATEGORY_PARTICLE_CHAMBER, "decay_chamber_power", 5000, Lang.localize("gui.qmd.config.particle_chamber.decay_chamber_power.comment"), 0, Integer.MAX_VALUE);
		propertyDecayChamberPower.setLanguageKey("gui.qmd.config.particle_chamber.decay_chamber_power");
		Property propertyBeamDumpPower = config.get(CATEGORY_PARTICLE_CHAMBER, "beam_dump_power", 5000, Lang.localize("gui.qmd.config.particle_chamber.beam_dump_power.comment"), 0, Integer.MAX_VALUE);
		propertyBeamDumpPower.setLanguageKey("gui.qmd.config.particle_chamber.beam_dump_power");
		Property propertyCollisionChamberPower = config.get(CATEGORY_PARTICLE_CHAMBER, "collision_chamber_power", 5000, Lang.localize("gui.qmd.config.particle_chamber.collision_chamber_power.comment"), 0, Integer.MAX_VALUE);
		propertyCollisionChamberPower.setLanguageKey("gui.qmd.config.particle_chamber.collision_chamber_power");
		
		Property propertyDetectorEfficiency = config.get(CATEGORY_PARTICLE_CHAMBER, "detector_efficiency", new double[] {0.075D, 0.15D, 0.1D, 0.05D,0.025D}, Lang.localize("gui.qmd.config.particle_chamber.detector_efficiency.comment"), 0D, 100D);
		propertyDetectorEfficiency.setLanguageKey("gui.qmd.config.particle_chamber.detector_efficiency");
		Property propertyDetectorBasePower = config.get(CATEGORY_PARTICLE_CHAMBER, "detector_base_power", new int[] {200, 2000, 1000,200,100}, Lang.localize("gui.qmd.config.particle_chamber.detector_base_power.comment"), 0, Integer.MAX_VALUE);
		propertyDetectorBasePower.setLanguageKey("gui.qmd.config.particle_chamber.detector_base_power");

		
		Property propertyParticleChamberBaseEnergyCapacity = config.get(CATEGORY_PARTICLE_CHAMBER, "particle_chamber_base_energy_capacity", 40000, Lang.localize("gui.qmd.config.particle_chamber.particle_chamber_base_energy_capacity.comment"), 1, Integer.MAX_VALUE);
		propertyParticleChamberBaseEnergyCapacity.setLanguageKey("gui.qmd.config.particle_chamber.particle_chamber_base_energy_capacity");
		Property propertyParticleChamberInputTankCapacity = config.get(CATEGORY_PARTICLE_CHAMBER, "particle_chamber_base_input_tank_capacity", 16000, Lang.localize("gui.qmd.config.particle_chamber.particle_chamber_base_input_tank_capacity.comment"), 1, Integer.MAX_VALUE);
		propertyParticleChamberInputTankCapacity.setLanguageKey("gui.qmd.config.particle_chamber.particle_chamber_base_input_tank_capacity");
		Property propertyParticleChamberOutputTankCapacity = config.get(CATEGORY_PARTICLE_CHAMBER, "particle_chamber_base_output_tank_capacity", 1000, Lang.localize("gui.qmd.config.particle_chamber.particle_chamber_base_output_tank_capacity.comment"), 1, Integer.MAX_VALUE);
		propertyParticleChamberOutputTankCapacity.setLanguageKey("gui.qmd.config.particle_chamber.particle_chamber_base_output_tank_capacity");
		
		
		
		Property propertyContainmentPartPower = config.get(CATEGORY_VACUUM_CHAMBER, "part_power", new int[] {400, 500,500,500,1000}, Lang.localize("gui.qmd.config.vacuum_chamber.part_power.comment"), 0, Integer.MAX_VALUE);
		propertyContainmentPartPower.setLanguageKey("gui.qmd.config.vacuum_chamber.part_power");
		Property propertyContainmentPartHeat = config.get(CATEGORY_VACUUM_CHAMBER, "part_heat", new int[] {200, 500,100,100,500}, Lang.localize("gui.qmd.config.vacuum_chamber.part_heat.comment"), 0, Integer.MAX_VALUE);
		propertyContainmentPartHeat.setLanguageKey("gui.qmd.config.vacuum_chamber.part_heat");
		Property propertyContainmentMaxTemp = config.get(CATEGORY_VACUUM_CHAMBER, "part_max_temp", new int[] {110,110,110,110,110}, Lang.localize("gui.qmd.config.vacuum_chamber.part_max_temp.comment"), 0, 400);
		propertyContainmentMaxTemp.setLanguageKey("gui.qmd.config.vacuum_chamber.part_max_temp");
		
		Property propertyVacuumChamberBaseEnergyCapacity = config.get(CATEGORY_VACUUM_CHAMBER, "vacuum_chamber_base_energy_capacity", 40000, Lang.localize("gui.qmd.config.vacuum_chamber.vacuum_chamber_base_energy_capacity.comment"), 1, Integer.MAX_VALUE);
		propertyVacuumChamberBaseEnergyCapacity.setLanguageKey("gui.qmd.config.vacuum_chamber.vacuum_chamber_base_energy_capacity");
		Property propertyVacuumChamberInputTankCapacity = config.get(CATEGORY_VACUUM_CHAMBER, "vacuum_chamber_base_input_tank_capacity", 1000, Lang.localize("gui.qmd.config.vacuum_chamber.vacuum_chamber_base_input_tank_capacity.comment"), 1, Integer.MAX_VALUE);
		propertyVacuumChamberInputTankCapacity.setLanguageKey("gui.qmd.config.vacuum_chamber.vacuum_chamber_base_input_tank_capacity");
		Property propertyVacuumChamberOutputTankCapacity = config.get(CATEGORY_VACUUM_CHAMBER, "vacuum_chamber_base_output_tank_capacity", 1000, Lang.localize("gui.qmd.config.vacuum_chamber.vacuum_chamber_base_output_tank_capacity.comment"), 1, Integer.MAX_VALUE);
		propertyVacuumChamberOutputTankCapacity.setLanguageKey("gui.qmd.config.vacuum_chamber.vacuum_chamber_base_output_tank_capacity");
		
		Property propertyExoticContainmentExplosion = config.get(CATEGORY_VACUUM_CHAMBER, "exotic_containment_explosion", true, Lang.localize("gui.qmd.config.vacuum_chamber.exotic_containment_explosion.comment"));
		propertyExoticContainmentExplosion.setLanguageKey("gui.qmd.config.vacuum_chamber.exotic_containment_explosion");
		Property propertyExoticContainmentGammaFlash = config.get(CATEGORY_VACUUM_CHAMBER, "exotic_containment_gamma_flash", true, Lang.localize("gui.qmd.config.vacuum_chamber.exotic_containment_gamma_flash.comment"));
		propertyExoticContainmentGammaFlash.setLanguageKey("gui.qmd.config.vacuum_chamber.exotic_containment_gamma_flash");
		Property propertyExoticContainmentRadiation = config.get(CATEGORY_VACUUM_CHAMBER, "exotic_containment_radiation", 1024000.0, Lang.localize("gui.qmd.config.vacuum_chamber.exotic_containment_radiation.comment"), 0.0, Double.MAX_VALUE);
		propertyExoticContainmentRadiation.setLanguageKey("gui.qmd.config.vacuum_chamber.exotic_containment_radiation");
		Property propertyExoticContainmentExplosionSize = config.get(CATEGORY_VACUUM_CHAMBER, "exotic_containment_explosion_size", 50.0, Lang.localize("gui.qmd.config.vacuum_chamber.exotic_containment_explosion_size.comment"), 0.0, 1000.0);
		propertyExoticContainmentExplosionSize.setLanguageKey("gui.qmd.config.vacuum_chamber.exotic_containment_explosion_size");

		Property propertyNucleosynthesisChamberExplosion = config.get(CATEGORY_VACUUM_CHAMBER, "nucleosynthesis_chamber_explosion", true, Lang.localize("gui.qmd.config.vacuum_chamber.nucleosynthesis_chamber_explosion.comment"));
		propertyNucleosynthesisChamberExplosion.setLanguageKey("gui.qmd.config.vacuum_chamber.nucleosynthesis_chamber_explosion");

		Property propertyHeaterHeatRemoved = config.get(CATEGORY_VACUUM_CHAMBER, "heater_heat_removed", new int[] {5000,10000,20000,40000,80000,160000,320000,640000}, Lang.localize("gui.qmd.config.vacuum_chamber.heater_heat_removed.comment"), 0, Integer.MAX_VALUE);
		propertyHeaterHeatRemoved.setLanguageKey("gui.qmd.config.vacuum_chamber.heater_heat_removed");
		Property propertyHeaterRule = config.get(CATEGORY_VACUUM_CHAMBER, "heater_rule", new String[] {"one casing","one beam", "two glass", "exactly one quartz heater && exactly one redstone heater", "two axial obsidian heaters", "exactly one redstone heater && two iron heaters", "one obsidian heater && one quartz heater", "one nozzle"},
				Lang.localize("gui.qmd.config.vacuum_chamber.heater_rule.comment"));
		propertyHeaterRule.setLanguageKey("gui.qmd.config.vacuum_chamber.heater_rule");

		Property propertyLiquefierBaseEnergyCapacity = config.get(CATEGORY_HEAT_EXCHANGER, "liquefier_base_energy_capacity", 1000, Lang.localize("gui.qmd.config.heat_exchanger.liquefier_base_energy_capacity.comment"), 1, Integer.MAX_VALUE);
		propertyLiquefierBaseEnergyCapacity.setLanguageKey("gui.qmd.config.heat_exchanger.liquefier_base_energy_capacity");
		Property propertyLiquefierInputTankCapacity = config.get(CATEGORY_HEAT_EXCHANGER, "liquefier_input_tank_capacity", 6400, Lang.localize("gui.qmd.config.heat_exchanger.liquefier_input_tank_capacity.comment"), 1, Integer.MAX_VALUE);
		propertyLiquefierInputTankCapacity.setLanguageKey("gui.qmd.config.heat_exchanger.liquefier_input_tank_capacity");
		Property propertyLiquefierOutputTankCapacity = config.get(CATEGORY_HEAT_EXCHANGER, "liquefier_output_tank_capacity", 100, Lang.localize("gui.qmd.config.heat_exchanger.liquefier_output_tank_capacity.comment"), 1, Integer.MAX_VALUE);
		propertyLiquefierOutputTankCapacity.setLanguageKey("gui.qmd.config.heat_exchanger.liquefier_output_tank_capacity");
		Property propertyLiquefierCompressorEnergyEfficiency= config.get(CATEGORY_HEAT_EXCHANGER, "liquefier_energy_efficiency", new double[] {0.9,0.8,0.95}, Lang.localize("gui.qmd.config.heat_exchanger.liquefier_energy_efficiency.comment"), 0.0, Double.MAX_VALUE);
		propertyLiquefierCompressorEnergyEfficiency.setLanguageKey("gui.qmd.config.heat_exchanger.liquefier_energy_efficiency");
		Property propertyLiquefierCompressorHeatEfficiency= config.get(CATEGORY_HEAT_EXCHANGER, "liquefier_heat_efficiency", new double[] {0.9,0.95,0.8}, Lang.localize("gui.qmd.config.heat_exchanger.liquefier_heat_efficiency.comment"), 0.0, Double.MAX_VALUE);
		propertyLiquefierCompressorHeatEfficiency.setLanguageKey("gui.qmd.config.heat_exchanger.liquefier_heat_efficiency");


		Property propertyRegisterTool = config.get(CATEGORY_TOOLS, "register_tool", new boolean[] {true, true}, Lang.localize("gui.qmd.config.tools.register_tool.comment"));
		propertyRegisterTool.setLanguageKey("gui.qmd.config.tools.register_tool");
		Property propertyToolMiningLevel = config.get(CATEGORY_TOOLS, "tool_mining_level", new int[] {3, 3, 4}, Lang.localize("gui.qmd.config.tools.tool_mining_level.comment"), 0, 15);
		propertyToolMiningLevel.setLanguageKey("gui.qmd.config.tools.tool_mining_level");
		Property propertyToolDurability = config.get(CATEGORY_TOOLS, "tool_durability", new int[] {1561*2}, Lang.localize("gui.qmd.config.tools.tool_durability.comment"), 1, Integer.MAX_VALUE);
		propertyToolDurability.setLanguageKey("gui.qmd.config.tools.tool_durability");
		Property propertyToolSpeed = config.get(CATEGORY_TOOLS, "tool_speed", new double[] {8D, 8D, 12D}, Lang.localize("gui.qmd.config.tools.tool_speed.comment"), 1D, 255D);
		propertyToolSpeed.setLanguageKey("gui.qmd.config.tools.tool_speed");
		Property propertyToolAttackDamage = config.get(CATEGORY_TOOLS, "tool_attack_damage", new double[] {3D}, Lang.localize("gui.qmd.config.tools.tool_attack_damage.comment"), 0D, 255D);
		propertyToolAttackDamage.setLanguageKey("gui.qmd.config.tools.tool_attack_damage");
		Property propertyToolEnchantability = config.get(CATEGORY_TOOLS, "tool_enchantability", new int[] {12}, Lang.localize("gui.qmd.config.tools.tool_enchantability.comment"), 1, 255);
		propertyToolEnchantability.setLanguageKey("gui.qmd.config.tools.tool_enchantability");
		
		Property propertyDrillEnergyUsage = config.get(CATEGORY_TOOLS, "drill_energy_usage", 100, Lang.localize("gui.qmd.config.tools.drill_energy_usage.comment"), 0, Integer.MAX_VALUE);
		propertyDrillEnergyUsage.setLanguageKey("gui.qmd.config.tools.drill_energy_usage");
		Property propertyDrillEnergyCapacity = config.get(CATEGORY_TOOLS, "drill_energy_capacity", new int[] {250000,2500000}, Lang.localize("gui.qmd.config.tools.drill_energy_capacity.comment"), 0, Integer.MAX_VALUE);
		propertyDrillEnergyCapacity.setLanguageKey("gui.qmd.config.tools.drill_energy_capacity");
		Property propertyDrillRadius = config.get(CATEGORY_TOOLS, "drill_radius", new int[] {1,2}, Lang.localize("gui.qmd.config.tools.drill_radius.comment"), 0, 20);
		propertyDrillRadius.setLanguageKey("gui.qmd.config.tools.drill_radius");
		
		Property propertyLeptonDamage = config.get(CATEGORY_TOOLS, "lepton_damage", new double[] {7.0, 14.0, 28.0}, Lang.localize("gui.qmd.config.tools.lepton_damage.comment"), 0, Float.MAX_VALUE);
		propertyLeptonDamage.setLanguageKey("gui.qmd.config.tools.lepton_damage");
		Property propertyLeptonRadiation = config.get(CATEGORY_TOOLS, "lepton_radiation", new double[] {10.0, 20.0, 40.0}, Lang.localize("gui.qmd.config.tools.lepton_radiation.comment"), 0, Double.MAX_VALUE);
		propertyLeptonRadiation.setLanguageKey("gui.qmd.config.tools.lepton_radiation");
		Property propertyLeptonRange = config.get(CATEGORY_TOOLS, "lepton_range", new double[] {30.0, 60.0, 90.0}, Lang.localize("gui.qmd.config.tools.lepton_range.comment"), 0, 128.0);
		propertyLeptonRange.setLanguageKey("gui.qmd.config.tools.lepton_range");
		Property propertyLeptonCoolDown = config.get(CATEGORY_TOOLS, "lepton_cool_down", 8, Lang.localize("gui.qmd.config.tools.lepton_cool_down.comment"), 0, 10000);
		propertyLeptonCoolDown.setLanguageKey("gui.qmd.config.tools.lepton_cool_down");
		Property propertyLeptonParticleUsage = config.get(CATEGORY_TOOLS, "lepton_particle_usage", 500, Lang.localize("gui.qmd.config.tools.lepton_particle_usage.comment"), 0, 100000);
		propertyLeptonParticleUsage.setLanguageKey("gui.qmd.config.tools.lepton_particle_usage");
		
		
		Property propertyGluonDamage = config.get(CATEGORY_TOOLS, "gluon_damage", 10.0, Lang.localize("gui.qmd.config.tools.gluon_damage.comment"), 0, Float.MAX_VALUE);
		propertyGluonDamage.setLanguageKey("gui.qmd.config.tools.gluon_damage");
		Property propertyGluonRadiation = config.get(CATEGORY_TOOLS, "gluon_radiation", 10.0, Lang.localize("gui.qmd.config.tools.gluon_radiation.comment"), 0, Double.MAX_VALUE);
		propertyGluonRadiation.setLanguageKey("gui.qmd.config.tools.gluon_radiation");
		Property propertyGluonRange = config.get(CATEGORY_TOOLS, "gluon_range", 40.0, Lang.localize("gui.qmd.config.tools.gluon_range.comment"), 0, 128.0);
		propertyGluonRange.setLanguageKey("gui.qmd.config.tools.gluon_range");
		Property propertyGluonParticleUsage = config.get(CATEGORY_TOOLS, "gluon_particle_usage", 10, Lang.localize("gui.qmd.config.tools.gluon_particle_usage.comment"), 0, 100000);
		propertyGluonParticleUsage.setLanguageKey("gui.qmd.config.tools.gluon_particle_usage");
		
		Property propertyAntimatterLauncherDamage = config.get(CATEGORY_TOOLS, "antimatter_launcher_damage", 20.0, Lang.localize("gui.qmd.config.tools.antimatter_launcher_damage.comment"), 0, Float.MAX_VALUE);
		propertyAntimatterLauncherDamage.setLanguageKey("gui.qmd.config.tools.antimatter_launcher_damage");
		Property propertyAntimatterLauncherRadiation = config.get(CATEGORY_TOOLS, "antimatter_launcher_radiation", 15360.0, Lang.localize("gui.qmd.config.tools.antimatter_launcher_radiation.comment"), 0, Double.MAX_VALUE);
		propertyAntimatterLauncherRadiation.setLanguageKey("gui.qmd.config.tools.antimatter_launcher_radiation");
		Property propertyAntimatterLauncherExplosionSize = config.get(CATEGORY_TOOLS, "antimatter_launcher_explosion_size", 2.5, Lang.localize("gui.qmd.config.tools.antimatter_launcher_explosion_size.comment"), 0.0, 1000.0);
		propertyAntimatterLauncherExplosionSize.setLanguageKey("gui.qmd.config.tools.antimatter_launcher_explosion_size");
		Property propertyAntimatterLauncherCoolDown = config.get(CATEGORY_TOOLS, "antimatter_launcher_cool_down", 30, Lang.localize("gui.qmd.config.tools.antimatter_launcher_cool_down.comment"), 0, 10000);
		propertyAntimatterLauncherCoolDown.setLanguageKey("gui.qmd.config.tools.antimatter_launcher_cool_down");
		Property propertyAntimatterLauncherParticleUsage = config.get(CATEGORY_TOOLS, "antimatter_launcher_usage", 5000, Lang.localize("gui.qmd.config.tools.antimatter_launcher_usage.comment"), 0, 100000);
		propertyAntimatterLauncherParticleUsage.setLanguageKey("gui.qmd.config.tools.antimatter_launcher_usage");


		Property propertyCellLifetime = config.get(CATEGORY_TOOLS, "cell_lifetime", 200, Lang.localize("gui.qmd.config.tools.cell_lifetime.comment"), 0, 6000);
		propertyCellLifetime.setLanguageKey("gui.qmd.config.tools.cell_lifetime");
		
		Property propertyCellRadiation = config.get(CATEGORY_TOOLS, "cell_radiation", 102400.0, Lang.localize("gui.qmd.config.tools.cell_radiation.comment"), 0.0, Double.MAX_VALUE);
		propertyCellRadiation.setLanguageKey("gui.qmd.config.tools.cell_radiation"); // the radiation at 1 block radius or lower
		Property propertyCellExplosionSize = config.get(CATEGORY_TOOLS, "cell_explosion_size", 5.0, Lang.localize("gui.qmd.config.tools.cell_explosion_size.comment"), 0.0, 1000.0);
		propertyCellExplosionSize.setLanguageKey("gui.qmd.config.tools.cell_explosion_size");
		
		Property propertyHEVArmour = config.get(CATEGORY_TOOLS, "hev_armour", new int[] {4, 7, 9, 4, 1, 3, 4, 1}, Lang.localize("gui.qmd.config.tools.hev_armour.comment"), 1, 25);
		propertyHEVArmour.setLanguageKey("gui.qmd.config.tools.hev_armour");
		Property propertyHEVRadRes = config.get(CATEGORY_TOOLS, "hev_rad_res", new double[] {20.0, 30.0, 20.0, 20.0}, Lang.localize("gui.qmd.config.tools.hev_rad_res.comment"), 0.0, 1000.0);
		propertyHEVRadRes.setLanguageKey("gui.qmd.config.tools.hev_rad_res");
		Property propertyHEVToughness = config.get(CATEGORY_TOOLS, "hev_toughness", new double[] {4D, 0D}, Lang.localize("gui.qmd.config.tools.hev_toughness.comment"), 0D, 8D);
		propertyHEVToughness.setLanguageKey("gui.qmd.config.tools.hev_toughness");
		Property propertyHEVEnergy = config.get(CATEGORY_TOOLS, "hev_energy", new int[] {1000000,1000000,1000000,1000000}, Lang.localize("gui.qmd.config.tools.hev_energy.comment"), 0, Integer.MAX_VALUE);
		propertyHEVEnergy.setLanguageKey("gui.qmd.config.tools.hev_energy");
		Property propertyHEVPower = config.get(CATEGORY_TOOLS, "hev_power", new int[] {100,100,250,100, 1000}, Lang.localize("gui.qmd.config.tools.hev_power.comment"), 0, Integer.MAX_VALUE);
		propertyHEVPower.setLanguageKey("gui.qmd.config.tools.hev_power");//damage,jump boost,long jump, fall reduction, posion/wither
		
		Property propertyKITime = config.get(CATEGORY_TOOLS, "ki_time", 400, Lang.localize("gui.qmd.config.tools.ki_time.comment"), 1, Integer.MAX_VALUE);
		propertyKITime.setLanguageKey("gui.qmd.config.tools.ki_time");
		
		
		Property propertyFissionReflectorEfficiency = config.get(CATEGORY_FISSION, "reflector_efficiency", new double[] {0.75D}, Lang.localize("gui.qmd.config.fission.reflector_efficiency.comment"), 0D, 255D);
		propertyFissionReflectorEfficiency.setLanguageKey("gui.qmd.config.fission.reflector_efficiency");
		Property propertyFissionReflectorReflectivity = config.get(CATEGORY_FISSION, "reflector_reflectivity", new double[] {0.75D}, Lang.localize("gui.qmd.config.fission.reflector_reflectivity.comment"), 0D, 1D);
		propertyFissionReflectorReflectivity.setLanguageKey("gui.qmd.config.fission.reflector_reflectivity");
		Property propertyFissionShieldHeatPerFlux = config.get(CATEGORY_FISSION, "shield_heat_per_flux", new double[] {15D}, Lang.localize("gui.qmd.config.fission.shield_heat_per_flux.comment"), 0D, 32767D);
		propertyFissionShieldHeatPerFlux.setLanguageKey("gui.qmd.config.fission.shield_heat_per_flux");
		Property propertyFissionShieldEfficiency = config.get(CATEGORY_FISSION, "shield_efficiency", new double[] {1D}, Lang.localize("gui.qmd.config.fission.shield_efficiency.comment"), 0D, 255D);
		propertyFissionShieldEfficiency.setLanguageKey("gui.qmd.config.fission.shield_efficiency");
		
		Property propertyCoperniciumFuelTime = config.get(CATEGORY_FISSION, "copernicium_fuel_time", new int[] {10000, 10000, 12004, 9001}, Lang.localize("gui.qmd.config.copernicium_fuel_time.comment"), 1, Integer.MAX_VALUE);
		propertyCoperniciumFuelTime.setLanguageKey("gui.qmd.config.copernicium_fuel_time");
		Property propertyCoperniciumHeatGeneration = config.get(CATEGORY_FISSION, "copernicium_heat_generation", new int[] {2000, 2000, 1666, 2222}, Lang.localize("gui.qmd.config.copernicium_heat_generation.comment"), 0, 32767);
		propertyCoperniciumHeatGeneration.setLanguageKey("gui.qmd.config.copernicium_heat_generation");
		Property propertyCoperniciumEfficiency = config.get(CATEGORY_FISSION, "copernicium_efficiency", new double[] {5.0D, 5.0D, 5.0D, 5.0D}, Lang.localize("gui.qmd.config.copernicium_efficiency.comment"), 0D, 32767D);
		propertyCoperniciumEfficiency.setLanguageKey("gui.qmd.config.copernicium_efficiency");
		Property propertyCoperniciumCriticality = config.get(CATEGORY_FISSION, "copernicium_criticality", new int[] {20, 25, 35, 20}, Lang.localize("gui.qmd.config.copernicium_criticality.comment"), 0, 32767);
		propertyCoperniciumCriticality.setLanguageKey("gui.qmd.config.copernicium_criticality");
		Property propertyCoperniciumDecayFactor = config.get(CATEGORY_FISSION, "copernicium_decay_factor", new double[] {0.11D, 0.11D, 0.11D, 0.11D}, Lang.localize("gui.qmd.config.copernicium_criticality.comment"), 0, 32767);
		propertyCoperniciumDecayFactor.setLanguageKey("gui.qmd.config.copernicium_decay_factor");
		Property propertyCoperniciumSelfPriming = config.get(CATEGORY_FISSION, "copernicium_self_priming", new boolean[] {true, true, true, true}, Lang.localize("gui.qmd.config.copernicium_decay_factor.comment"));
		propertyCoperniciumSelfPriming.setLanguageKey("gui.qmd.config.copernicium_self_priming");
		Property propertyCoperniciumRadiation = config.get(CATEGORY_FISSION, "copernicium_radiation", new double[] {QMDRadSources.MIX_291, QMDRadSources.MIX_291, QMDRadSources.MIX_291, QMDRadSources.MIX_291}, Lang.localize("gui.qmd.config.copernicium_radiation.comment"), 0D, 1000D);
		propertyCoperniciumRadiation.setLanguageKey("gui.qmd.config.copernicium_radiation");
		
		
		Property propertyOverrideNCRecipes = config.get(CATEGORY_RECIPES, "override_nc_recipes", true, Lang.localize("gui.qmd.config.recipes.override_nc_recipes.comment"));
		propertyOverrideNCRecipes.setLanguageKey("gui.qmd.config.recipes.override_nc_recipes");
		
		Property propertyRSFTargetChamber = config.get(CATEGORY_RECIPES, "rsf_target_chamber", 100, Lang.localize("gui.qmd.config.recipes.rsf_target_chamber.comment"), 1, Integer.MAX_VALUE);
		propertyRSFTargetChamber.setLanguageKey("gui.qmd.config.recipes.rsf_target_chamber");
		
		Property propertyRSFNucleosynthesis = config.get(CATEGORY_RECIPES, "rsf_nucleosynthesis", 100, Lang.localize("gui.qmd.config.recipes.rsf_nucleosynthesis.comment"), 1, Integer.MAX_VALUE);
		propertyRSFNucleosynthesis.setLanguageKey("gui.qmd.config.recipes.rsf_nucleosynthesis");
		
		
		Property propertyRTGPower = config.get(CATEGORY_OTHER, "rtg_power", new int[] {50}, Lang.localize("gui.qmd.config.other.rtg_power.comment"), 0, Integer.MAX_VALUE);
		propertyRTGPower.setLanguageKey("gui.qmd.config.other.rtg_power");
		
		Property propertyBeamScaling = config.get(CATEGORY_OTHER, "beam_scaling", 10000, Lang.localize("gui.qmd.config.other.beam_scaling.comment"), 1, Integer.MAX_VALUE);
		propertyBeamScaling.setLanguageKey("gui.qmd.config.other.beam_scaling");

		//Property propertyItemTickerChunksPerTick = config.get(CATEGORY_OTHER, "item_ticker_chunks_per_tick", 5, Lang.localize("gui.qmd.config.other.item_ticker_chunks_per_tick.comment"),0,400);
		//propertyItemTickerChunksPerTick.setLanguageKey("gui.qmd.config.other.item_ticker_chunks_per_tick");
		
		Property propertyTurbineBladeEfficiency = config.get(CATEGORY_OTHER, "turbine_blade_efficiency", new double[] {1.1D}, Lang.localize("gui.qmd.config.other.turbine_blade_efficiency.comment"),  0.01D, 15D);
		propertyTurbineBladeEfficiency.setLanguageKey("gui.qmd.config.other.turbine_blade_efficiency");

		Property propertyTurbineBladeExpansion = config.get(CATEGORY_OTHER, "turbine_blade_expansion", new double[] {1.3D}, Lang.localize("gui.qmd.config.other.turbine_blade_expansion.comment"), 1D, 15D);
		propertyTurbineBladeExpansion.setLanguageKey("gui.qmd.config.other.turbine_blade_expansion");
		
		List<String> propertyOrderProcessors = new ArrayList<String>();
		propertyOrderProcessors.add(propertyProcessorPower.getName());
		propertyOrderProcessors.add(propertyProcessorTime.getName());
		propertyOrderProcessors.add(propertyIrradiatorRadRes.getName());
		propertyOrderProcessors.add(propertyIrradiatorFuelUsage.getName());
		
		
		config.setCategoryPropertyOrder(CATEGORY_PROCESSORS, propertyOrderProcessors);
		
		
		List<String> propertyOrderAccelerator = new ArrayList<String>();
		propertyOrderAccelerator.add(propertyAcceleratorLinearMinSize.getName());
		propertyOrderAccelerator.add(propertyAcceleratorLinearMaxSize.getName());
		propertyOrderAccelerator.add(propertyAcceleratorRingMinSize.getName());
		propertyOrderAccelerator.add(propertyAcceleratorRingMaxSize.getName());
		
		propertyOrderAccelerator.add(propertyAcceleratorBaseHeatCapacity.getName());
		propertyOrderAccelerator.add(propertyAcceleratorBaseEnergyCapacity.getName());
		propertyOrderAccelerator.add(propertyAcceleratorBaseInputTankCapacity.getName());
		propertyOrderAccelerator.add(propertyAcceleratorBaseOutputTankCapacity.getName());
		
		propertyOrderAccelerator.add(propertyAcceleratorThermalConductivity.getName());
		propertyOrderAccelerator.add(propertyAcceleratorRingInputEnergy.getName());
		
		propertyOrderAccelerator.add(propertyIonSourcePower.getName());
		propertyOrderAccelerator.add(propertyIonSourceOutputMultiplier.getName());
		propertyOrderAccelerator.add(propertyIonSourceFocus.getName());
		
		propertyOrderAccelerator.add(propertyBeamAttenuationRate.getName());
		propertyOrderAccelerator.add(propertyBeamDiverterRadius.getName());
		
		propertyOrderAccelerator.add(propertyMassSpectrometerValidMagnets.getName());
		propertyOrderAccelerator.add(propertyMassSpectrometerValidSources.getName());
		
		propertyOrderAccelerator.add(propertyRFCavityVoltage.getName());
		propertyOrderAccelerator.add(propertyRFCavityEfficiency.getName());
		propertyOrderAccelerator.add(propertyRFCavityHeatGenerated.getName());
		propertyOrderAccelerator.add(propertyRFCavityBasePower.getName());
		propertyOrderAccelerator.add(propertyRFCavityMaxTemp.getName());
		
		propertyOrderAccelerator.add(propertyMagnetStrength.getName());
		propertyOrderAccelerator.add(propertyMagnetEfficiency.getName());
		propertyOrderAccelerator.add(propertyMagnetHeatGenerated.getName());
		propertyOrderAccelerator.add(propertyMagnetBasePower.getName());
		propertyOrderAccelerator.add(propertyMagnetMaxTemp.getName());
		
		propertyOrderAccelerator.add(propertyCoolerHeatRemoved.getName());
		propertyOrderAccelerator.add(propertyCoolerRule.getName());
		
		propertyOrderAccelerator.add(propertyAcceleratorExplosion.getName());
		
		
		config.setCategoryPropertyOrder(CATEGORY_ACCELERATOR, propertyOrderAccelerator);
		
		
		List<String> propertyOrderParticleChamber = new ArrayList<String>();
		propertyOrderParticleChamber.add(propertyTargetChamberPower.getName());
		propertyOrderParticleChamber.add(propertyDecayChamberPower.getName());
		propertyOrderParticleChamber.add(propertyBeamDumpPower.getName());
		propertyOrderParticleChamber.add(propertyCollisionChamberPower.getName());
		propertyOrderParticleChamber.add(propertyDetectorEfficiency.getName());
		propertyOrderParticleChamber.add(propertyDetectorBasePower.getName());
		propertyOrderParticleChamber.add(propertyParticleChamberBaseEnergyCapacity.getName());
		propertyOrderParticleChamber.add(propertyParticleChamberInputTankCapacity.getName());
		propertyOrderParticleChamber.add(propertyParticleChamberOutputTankCapacity.getName());
		
		config.setCategoryPropertyOrder(CATEGORY_PARTICLE_CHAMBER, propertyOrderParticleChamber);
		
		List<String> propertyOrderContainment = new ArrayList<String>();
		propertyOrderContainment.add(propertyContainmentPartPower.getName());
		propertyOrderContainment.add(propertyContainmentPartHeat.getName());
		propertyOrderContainment.add(propertyContainmentMaxTemp.getName());
		propertyOrderContainment.add(propertyVacuumChamberBaseEnergyCapacity.getName());
		propertyOrderContainment.add(propertyVacuumChamberInputTankCapacity.getName());
		propertyOrderContainment.add(propertyVacuumChamberOutputTankCapacity.getName());
		propertyOrderContainment.add(propertyExoticContainmentExplosion.getName());
		propertyOrderContainment.add(propertyExoticContainmentGammaFlash.getName());
		propertyOrderContainment.add(propertyExoticContainmentRadiation.getName());
		propertyOrderContainment.add(propertyExoticContainmentExplosionSize.getName());
		propertyOrderContainment.add(propertyNucleosynthesisChamberExplosion.getName());
		
		propertyOrderContainment.add(propertyHeaterHeatRemoved.getName());
		propertyOrderContainment.add(propertyHeaterRule.getName());
		
		config.setCategoryPropertyOrder(CATEGORY_VACUUM_CHAMBER, propertyOrderContainment);
		
		
		
		List<String> propertyOrderTools = new ArrayList<String>();
		propertyOrderTools.add(propertyRegisterTool.getName());
		propertyOrderTools.add(propertyToolMiningLevel.getName());
		propertyOrderTools.add(propertyToolDurability.getName());
		propertyOrderTools.add(propertyToolSpeed.getName());
		propertyOrderTools.add(propertyToolAttackDamage.getName());
		propertyOrderTools.add(propertyToolEnchantability.getName());
		propertyOrderTools.add(propertyDrillEnergyUsage.getName());
		propertyOrderTools.add(propertyDrillEnergyCapacity.getName());
		propertyOrderTools.add(propertyDrillRadius.getName());
		
		propertyOrderTools.add(propertyLeptonDamage.getName());
		propertyOrderTools.add(propertyLeptonRadiation.getName());
		propertyOrderTools.add(propertyLeptonRange.getName());
		propertyOrderTools.add(propertyLeptonCoolDown.getName());
		propertyOrderTools.add(propertyLeptonParticleUsage.getName());
		
		propertyOrderTools.add(propertyGluonDamage.getName());
		propertyOrderTools.add(propertyGluonRadiation.getName());
		propertyOrderTools.add(propertyGluonRange.getName());
		propertyOrderTools.add(propertyGluonParticleUsage.getName());
		
		propertyOrderTools.add(propertyAntimatterLauncherDamage.getName());
		propertyOrderTools.add(propertyAntimatterLauncherRadiation.getName());
		propertyOrderTools.add(propertyAntimatterLauncherExplosionSize.getName());
		propertyOrderTools.add(propertyAntimatterLauncherCoolDown.getName());
		propertyOrderTools.add(propertyAntimatterLauncherParticleUsage.getName());
		
		propertyOrderTools.add(propertyCellLifetime.getName());
		propertyOrderTools.add(propertyCellRadiation.getName());
		propertyOrderTools.add(propertyCellExplosionSize.getName());
		
		propertyOrderTools.add(propertyHEVArmour.getName());
		
		propertyOrderTools.add(propertyHEVToughness.getName());
		propertyOrderTools.add(propertyHEVEnergy.getName());
		propertyOrderTools.add(propertyHEVPower.getName());
		
		propertyOrderTools.add(propertyKITime.getName());
		
		
		config.setCategoryPropertyOrder(CATEGORY_TOOLS, propertyOrderTools);
		
		
		List<String> propertyOrderFission = new ArrayList<String>();
		propertyOrderFission.add(propertyFissionReflectorEfficiency.getName());
		propertyOrderFission.add(propertyFissionReflectorReflectivity.getName());
		propertyOrderFission.add(propertyFissionShieldHeatPerFlux.getName());
		propertyOrderFission.add(propertyFissionShieldEfficiency.getName());
		
		propertyOrderFission.add(propertyCoperniciumFuelTime.getName());
		propertyOrderFission.add(propertyCoperniciumHeatGeneration.getName());
		propertyOrderFission.add(propertyCoperniciumEfficiency.getName());
		propertyOrderFission.add(propertyCoperniciumCriticality.getName());
		propertyOrderFission.add(propertyCoperniciumDecayFactor.getName());
		propertyOrderFission.add(propertyCoperniciumSelfPriming.getName());
		propertyOrderFission.add(propertyCoperniciumRadiation.getName());
		
		config.setCategoryPropertyOrder(CATEGORY_FISSION, propertyOrderFission);
		
		
		List<String> propertyOrderFusion = new ArrayList<String>();
		
		config.setCategoryPropertyOrder(CATEGORY_FUSION, propertyOrderFusion);
		
		
		List<String> propertyOrderRecipes = new ArrayList<String>();
		
		propertyOrderRecipes.add(propertyOverrideNCRecipes.getName());
		propertyOrderRecipes.add(propertyRSFTargetChamber.getName());
		propertyOrderRecipes.add(propertyRSFNucleosynthesis.getName());

		config.setCategoryPropertyOrder(CATEGORY_RECIPES, propertyOrderRecipes);
		
		
		List<String> propertyOrderOther = new ArrayList<String>();
		
		propertyOrderOther.add(propertyRTGPower.getName());
		propertyOrderOther.add(propertyBeamScaling.getName());

		//propertyOrderOther.add(propertyItemTickerChunksPerTick.getName());
		propertyOrderOther.add(propertyTurbineBladeEfficiency.getName());
		propertyOrderOther.add(propertyTurbineBladeExpansion.getName());
		
		config.setCategoryPropertyOrder(CATEGORY_OTHER, propertyOrderOther);
		
		
		if (setFromConfig)
		{
			processor_power = readIntegerArrayFromConfig(propertyProcessorPower);
			processor_time = readIntegerArrayFromConfig(propertyProcessorTime);
			irradiator_rad_res = propertyIrradiatorRadRes.getDouble();
			irradiator_fuel_usage = propertyIrradiatorFuelUsage.getInt();
			
			accelerator_linear_min_size = propertyAcceleratorLinearMinSize.getInt();
			accelerator_linear_max_size = propertyAcceleratorLinearMaxSize.getInt();
			accelerator_ring_min_size = propertyAcceleratorRingMinSize.getInt();
			accelerator_ring_max_size = propertyAcceleratorRingMaxSize.getInt();
			
			accelerator_base_heat_capacity = propertyAcceleratorBaseHeatCapacity.getInt();
			accelerator_base_energy_capacity = propertyAcceleratorBaseEnergyCapacity.getInt();
			accelerator_base_input_tank_capacity = propertyAcceleratorBaseInputTankCapacity.getInt();
			accelerator_base_output_tank_capacity = propertyAcceleratorBaseOutputTankCapacity.getInt();
			
			accelerator_thermal_conductivity= propertyAcceleratorThermalConductivity.getDouble();
			minimium_accelerator_ring_input_particle_energy= propertyAcceleratorRingInputEnergy.getInt();
			
			ion_source_power = readIntegerArrayFromConfig(propertyIonSourcePower);
			ion_source_output_multiplier= readIntegerArrayFromConfig(propertyIonSourceOutputMultiplier);
			ion_source_focus= readDoubleArrayFromConfig(propertyIonSourceFocus);
			
			beamAttenuationRate = propertyBeamAttenuationRate.getDouble();
			beamDiverterRadius = propertyBeamDiverterRadius.getInt();
			
			mass_spectrometer_valid_magnets = propertyMassSpectrometerValidMagnets.getStringList();
			mass_spectrometer_valid_sources = propertyMassSpectrometerValidSources.getStringList();
			
			RF_cavity_voltage = readIntegerArrayFromConfig(propertyRFCavityVoltage);
			RF_cavity_efficiency = readDoubleArrayFromConfig(propertyRFCavityEfficiency);
			RF_cavity_heat_generated = readIntegerArrayFromConfig(propertyRFCavityHeatGenerated);
			RF_cavity_base_power = readIntegerArrayFromConfig(propertyRFCavityBasePower);
			RF_cavity_max_temp = readIntegerArrayFromConfig(propertyRFCavityMaxTemp);
			
			magnet_strength = readDoubleArrayFromConfig(propertyMagnetStrength);
			magnet_efficiency = readDoubleArrayFromConfig(propertyMagnetEfficiency);
			magnet_heat_generated = readIntegerArrayFromConfig(propertyMagnetHeatGenerated);
			magnet_base_power = readIntegerArrayFromConfig(propertyMagnetBasePower);
			magnet_max_temp = readIntegerArrayFromConfig(propertyMagnetMaxTemp);
			
			cooler_heat_removed = readIntegerArrayFromConfig(propertyCoolerHeatRemoved);
			cooler_rule = propertyCoolerRule.getStringList();
			
			accelerator_explosion = propertyAcceleratorExplosion.getBoolean();
			
			target_chamber_power = propertyTargetChamberPower.getInt();
			decay_chamber_power = propertyDecayChamberPower.getInt();
			beam_dump_power = propertyBeamDumpPower.getInt();
			collision_chamber_power = propertyCollisionChamberPower.getInt();
			
			detector_efficiency = readDoubleArrayFromConfig(propertyDetectorEfficiency);
			detector_base_power = readIntegerArrayFromConfig(propertyDetectorBasePower);
			
			particle_chamber_base_energy_capacity = propertyParticleChamberBaseEnergyCapacity.getInt();
			particle_chamber_input_tank_capacity = propertyParticleChamberInputTankCapacity.getInt();
			particle_chamber_output_tank_capacity = propertyParticleChamberOutputTankCapacity.getInt();
			
			
			vacuum_chamber_part_power = readIntegerArrayFromConfig(propertyContainmentPartPower);
			vacuum_chamber_part_heat = readIntegerArrayFromConfig(propertyContainmentPartHeat);
			vacuum_chamber_part_max_temp = readIntegerArrayFromConfig(propertyContainmentMaxTemp);
			
			vacuum_chamber_base_energy_capacity = propertyVacuumChamberBaseEnergyCapacity.getInt();
			vacuum_chamber_input_tank_capacity = propertyVacuumChamberInputTankCapacity.getInt();
			vacuum_chamber_output_tank_capacity = propertyVacuumChamberOutputTankCapacity.getInt();
			
			exotic_containment_explosion = propertyExoticContainmentExplosion.getBoolean();
			exotic_containment_gamma_flash = propertyExoticContainmentGammaFlash.getBoolean();
			exotic_containment_radiation = propertyExoticContainmentRadiation.getDouble();
			exotic_containment_explosion_size = propertyExoticContainmentExplosionSize.getDouble();
			nucleosynthesis_chamber_explosion = propertyNucleosynthesisChamberExplosion.getBoolean();

			heater_heat_removed = readIntegerArrayFromConfig(propertyHeaterHeatRemoved);
			heater_rule = propertyHeaterRule.getStringList();

			liquefier_base_energy_capacity = propertyLiquefierBaseEnergyCapacity.getInt();
			liquefier_input_tank_capacity = propertyLiquefierInputTankCapacity.getInt();
			liquefier_output_tank_capacity = propertyLiquefierOutputTankCapacity.getInt();
			liquefier_compressor_energy_efficiency = readDoubleArrayFromConfig(propertyLiquefierCompressorEnergyEfficiency);
			liquefier_compressor_heat_efficiency = readDoubleArrayFromConfig(propertyLiquefierCompressorHeatEfficiency);


			register_tool = readBooleanArrayFromConfig(propertyRegisterTool);
			
			tool_mining_level = readIntegerArrayFromConfig(propertyToolMiningLevel);
			tool_durability = readIntegerArrayFromConfig(propertyToolDurability);
			tool_speed = readDoubleArrayFromConfig(propertyToolSpeed);
			tool_attack_damage = readDoubleArrayFromConfig(propertyToolAttackDamage);
			tool_enchantability = readIntegerArrayFromConfig(propertyToolEnchantability);
			
			drill_energy_usage = propertyDrillEnergyUsage.getInt();
			drill_energy_capacity =  readIntegerArrayFromConfig(propertyDrillEnergyCapacity);
			drill_radius =  readIntegerArrayFromConfig(propertyDrillRadius);
			
			lepton_damage = readDoubleArrayFromConfig(propertyLeptonDamage);
			lepton_radiation = readDoubleArrayFromConfig(propertyLeptonRadiation);
			lepton_range = readDoubleArrayFromConfig(propertyLeptonRange);
			lepton_cool_down = propertyLeptonCoolDown.getInt();
			lepton_particle_usage = propertyLeptonParticleUsage.getInt();
			
			gluon_damage = propertyGluonDamage.getDouble();
			gluon_radiation = propertyGluonRadiation.getDouble();
			gluon_range = propertyGluonRange.getDouble();
			gluon_particle_usage = propertyGluonParticleUsage.getInt();
			
			antimatter_launcher_damage = propertyAntimatterLauncherDamage.getDouble();
			antimatter_launcher_radiation = propertyAntimatterLauncherRadiation.getDouble();
			antimatter_launcher_explosion_size = propertyAntimatterLauncherExplosionSize.getDouble();
			antimatter_launcher_cool_down = propertyAntimatterLauncherCoolDown.getInt();
			antimatter_launcher_particle_usage = propertyAntimatterLauncherParticleUsage.getInt();
			
			cell_lifetime = propertyCellLifetime.getInt();
			cell_radiation = propertyCellRadiation.getDouble();
			cell_explosion_size = propertyCellExplosionSize.getDouble();
			
			hev_armour = readIntegerArrayFromConfig(propertyHEVArmour);
			hev_rad_res = readDoubleArrayFromConfig(propertyHEVRadRes);
			hev_toughness = readDoubleArrayFromConfig(propertyHEVToughness);
			hev_energy = readIntegerArrayFromConfig(propertyHEVEnergy);
			hev_power =  readIntegerArrayFromConfig(propertyHEVPower);
			
			ki_time =  propertyKITime.getInt();
			
			fission_reflector_efficiency = readDoubleArrayFromConfig(propertyFissionReflectorEfficiency);
			fission_reflector_reflectivity = readDoubleArrayFromConfig(propertyFissionReflectorReflectivity);
			fission_shield_heat_per_flux = readDoubleArrayFromConfig(propertyFissionShieldHeatPerFlux);
			fission_shield_efficiency = readDoubleArrayFromConfig(propertyFissionShieldEfficiency);
			
			copernicium_fuel_time = readIntegerArrayFromConfig(propertyCoperniciumFuelTime);
			copernicium_heat_generation = readIntegerArrayFromConfig(propertyCoperniciumHeatGeneration);
			copernicium_efficiency = readDoubleArrayFromConfig(propertyCoperniciumEfficiency);
			copernicium_criticality = readIntegerArrayFromConfig(propertyCoperniciumCriticality);
			copernicium_decay_factor = readDoubleArrayFromConfig(propertyCoperniciumDecayFactor);
			copernicium_self_priming = readBooleanArrayFromConfig(propertyCoperniciumSelfPriming);
			copernicium_radiation = readDoubleArrayFromConfig(propertyCoperniciumRadiation);
			
			
			override_nc_recipes = propertyOverrideNCRecipes.getBoolean();
			rsf_target_chamber = propertyRSFTargetChamber.getInt();
			rsf_nucleosynthesis = propertyRSFNucleosynthesis.getInt();
			
			
			rtg_power = readIntegerArrayFromConfig(propertyRTGPower);
			beam_scaling = propertyBeamScaling.getInt();
			//item_ticker_chunks_per_tick = propertyItemTickerChunksPerTick.getInt();
			turbine_blade_efficiency = readDoubleArrayFromConfig(propertyTurbineBladeEfficiency);
			turbine_blade_expansion = readDoubleArrayFromConfig(propertyTurbineBladeExpansion);
		}
		propertyProcessorPower.set(processor_power);
		propertyProcessorTime.set(processor_time);
		propertyIrradiatorRadRes.set(irradiator_rad_res);
		propertyIrradiatorFuelUsage.set(irradiator_fuel_usage);
		
		
		propertyAcceleratorLinearMinSize.set(accelerator_linear_min_size);
		propertyAcceleratorLinearMaxSize.set(accelerator_linear_max_size);
		propertyAcceleratorRingMinSize.set(accelerator_ring_min_size);
		propertyAcceleratorRingMaxSize.set(accelerator_ring_max_size);
		
		propertyAcceleratorBaseHeatCapacity.set(accelerator_base_heat_capacity);
		propertyAcceleratorBaseEnergyCapacity.set(accelerator_base_energy_capacity);
		propertyAcceleratorBaseInputTankCapacity.set(accelerator_base_input_tank_capacity);
		propertyAcceleratorBaseOutputTankCapacity.set(accelerator_base_output_tank_capacity);
		
		propertyAcceleratorBaseHeatCapacity.set(accelerator_base_heat_capacity);
		propertyAcceleratorRingInputEnergy.set(minimium_accelerator_ring_input_particle_energy);

		
		propertyBeamAttenuationRate.set(beamAttenuationRate);
		propertyBeamDiverterRadius.set(beamDiverterRadius);
		
		propertyRFCavityVoltage.set(RF_cavity_voltage);
		propertyRFCavityEfficiency.set(RF_cavity_efficiency);
		propertyRFCavityHeatGenerated.set(RF_cavity_heat_generated);
		propertyRFCavityBasePower.set(RF_cavity_base_power);
		propertyRFCavityMaxTemp.set(RF_cavity_max_temp);
		
		propertyMagnetStrength.set(magnet_strength);
		propertyMagnetEfficiency.set(magnet_efficiency);
		propertyMagnetHeatGenerated.set(magnet_heat_generated);
		propertyMagnetBasePower.set(magnet_base_power);
		propertyMagnetMaxTemp.set(magnet_max_temp);
		
		propertyCoolerHeatRemoved.set(cooler_heat_removed);
		propertyCoolerRule.set(cooler_rule);
		
		propertyAcceleratorExplosion.set(accelerator_explosion);
		
		propertyTargetChamberPower.set(target_chamber_power);
		propertyDecayChamberPower.set(decay_chamber_power);
		propertyBeamDumpPower.set(beam_dump_power);
		propertyCollisionChamberPower.set(collision_chamber_power);
		propertyDetectorEfficiency.set(detector_efficiency);
		propertyDetectorBasePower.set(detector_base_power);
		propertyParticleChamberBaseEnergyCapacity.set(particle_chamber_base_energy_capacity);
		propertyParticleChamberInputTankCapacity.set(particle_chamber_input_tank_capacity);
		propertyParticleChamberOutputTankCapacity.set(particle_chamber_output_tank_capacity);
		
		propertyContainmentPartPower.set(vacuum_chamber_part_power);
		propertyContainmentPartHeat.set(vacuum_chamber_part_heat);
		propertyContainmentMaxTemp.set(vacuum_chamber_part_max_temp);
		propertyVacuumChamberBaseEnergyCapacity.set(vacuum_chamber_base_energy_capacity);
		propertyVacuumChamberInputTankCapacity.set(vacuum_chamber_input_tank_capacity);
		propertyVacuumChamberOutputTankCapacity.set(vacuum_chamber_output_tank_capacity);
		propertyExoticContainmentExplosion.set(exotic_containment_explosion);
		propertyExoticContainmentGammaFlash.set(exotic_containment_gamma_flash);
		propertyNucleosynthesisChamberExplosion.set(nucleosynthesis_chamber_explosion);
		
		propertyHeaterHeatRemoved.set(heater_heat_removed);
		propertyHeaterRule.set(heater_rule);

		propertyLiquefierBaseEnergyCapacity.set(liquefier_base_energy_capacity);
		propertyLiquefierInputTankCapacity.set(liquefier_input_tank_capacity);
		propertyLiquefierOutputTankCapacity.set(liquefier_output_tank_capacity);
		propertyLiquefierCompressorEnergyEfficiency.set(liquefier_compressor_energy_efficiency);
		propertyLiquefierCompressorHeatEfficiency.set(liquefier_compressor_heat_efficiency);

		propertyRegisterTool.set(register_tool);
		propertyToolMiningLevel.set(tool_mining_level);
		propertyToolDurability.set(tool_durability);
		propertyToolSpeed.set(tool_speed);
		propertyToolAttackDamage.set(tool_attack_damage);
		propertyToolEnchantability.set(tool_enchantability);
		
		propertyDrillEnergyUsage.set(drill_energy_usage);
		propertyDrillEnergyCapacity.set(drill_energy_capacity);
		
		propertyLeptonDamage.set(lepton_damage);
		propertyLeptonRadiation.set(lepton_radiation);
		propertyLeptonRange.set(lepton_range);
		propertyLeptonCoolDown.set(lepton_cool_down);
		propertyLeptonParticleUsage.set(lepton_particle_usage);
		
		propertyGluonDamage.set(gluon_damage);
		propertyGluonRadiation.set(gluon_radiation);
		propertyGluonRange.set(gluon_range);
		propertyGluonParticleUsage.set(gluon_particle_usage);
		
		
		propertyAntimatterLauncherDamage.set(antimatter_launcher_damage);
		propertyAntimatterLauncherRadiation.set(antimatter_launcher_radiation);
		propertyAntimatterLauncherExplosionSize.set(antimatter_launcher_explosion_size);
		propertyAntimatterLauncherCoolDown.set(antimatter_launcher_cool_down);
		propertyAntimatterLauncherParticleUsage.set(antimatter_launcher_particle_usage);
		
		propertyCellLifetime.set(cell_lifetime);
		propertyCellRadiation.set(cell_radiation);
		propertyCellExplosionSize.set(cell_explosion_size);
		
		propertyHEVArmour.set(hev_armour);
		propertyHEVRadRes.set(hev_rad_res);
		propertyHEVToughness.set(hev_toughness);
		propertyHEVEnergy.set(hev_energy);
		propertyHEVPower.set(hev_power);
		
		propertyKITime.set(ki_time);
		
		propertyFissionReflectorEfficiency.set(fission_reflector_efficiency);
		propertyFissionReflectorReflectivity.set(fission_reflector_reflectivity);
		propertyFissionShieldHeatPerFlux.set(fission_shield_heat_per_flux);
		propertyFissionShieldEfficiency.set(fission_shield_efficiency);
		
		
		propertyCoperniciumFuelTime.set(copernicium_fuel_time);
		propertyCoperniciumHeatGeneration.set(copernicium_heat_generation);
		propertyCoperniciumEfficiency.set(copernicium_efficiency);
		propertyCoperniciumCriticality.set(copernicium_criticality);
		propertyCoperniciumDecayFactor.set(copernicium_decay_factor);
		propertyCoperniciumSelfPriming.set(copernicium_self_priming);
		propertyCoperniciumRadiation.set(copernicium_radiation);
	
		
		propertyOverrideNCRecipes.set(override_nc_recipes);
		propertyRSFTargetChamber.set(rsf_target_chamber);
		propertyRSFNucleosynthesis.set(rsf_nucleosynthesis);
		
		
		propertyRTGPower.set(rtg_power);
		propertyBeamScaling.set(beam_scaling);
		//propertyItemTickerChunksPerTick.set(item_ticker_chunks_per_tick);
		propertyTurbineBladeEfficiency.set(turbine_blade_efficiency);
		propertyTurbineBladeExpansion.set(turbine_blade_expansion);
		
		if (config.hasChanged()) config.save();
	}
	
	
	
	
	private static boolean[] readBooleanArrayFromConfig(Property property) {
		int currentLength = property.getBooleanList().length;
		int defaultLength = property.getDefaults().length;
		if (currentLength == defaultLength) {
			return property.getBooleanList();
		}
		boolean[] newArray = new boolean[defaultLength];
		if (currentLength > defaultLength) {
			for (int i = 0; i < defaultLength; i++) {
				newArray[i] = property.getBooleanList()[i];
			}
		} else {
			for (int i = 0; i < currentLength; i++) {
				newArray[i] = property.getBooleanList()[i];
			}
			for (int i = currentLength; i < defaultLength; i++) {
				newArray[i] = property.setToDefault().getBooleanList()[i];
			}
		}
		return newArray;
	}
	
	private static int[] readIntegerArrayFromConfig(Property property) {
		int currentLength = property.getIntList().length;
		int defaultLength = property.getDefaults().length;
		if (currentLength == defaultLength) {
			return property.getIntList();
		}
		int[] newArray = new int[defaultLength];
		if (currentLength > defaultLength) {
			for (int i = 0; i < defaultLength; i++) {
				newArray[i] = property.getIntList()[i];
			}
		} else {
			for (int i = 0; i < currentLength; i++) {
				newArray[i] = property.getIntList()[i];
			}
			for (int i = currentLength; i < defaultLength; i++) {
				newArray[i] = property.setToDefault().getIntList()[i];
			}
		}
		return newArray;
	}
	
	private static double[] readDoubleArrayFromConfig(Property property) {
		int currentLength = property.getDoubleList().length;
		int defaultLength = property.getDefaults().length;
		if (currentLength == defaultLength) {
			return property.getDoubleList();
		}
		double[] newArray = new double[defaultLength];
		if (currentLength > defaultLength) {
			for (int i = 0; i < defaultLength; i++) {
				newArray[i] = property.getDoubleList()[i];
			}
		} else {
			for (int i = 0; i < currentLength; i++) {
				newArray[i] = property.getDoubleList()[i];
			}
			for (int i = currentLength; i < defaultLength; i++) {
				newArray[i] = property.setToDefault().getDoubleList()[i];
			}
		}
		return newArray;
	}
	
	

	private static class ClientConfigEventHandler
	{

		@SubscribeEvent(priority = EventPriority.LOWEST)
		public void onEvent(OnConfigChangedEvent event)
		{
			if (event.getModID().equals(QMD.MOD_ID))
			{
				syncConfig(false, true);
			}
		}
	}
	

}
