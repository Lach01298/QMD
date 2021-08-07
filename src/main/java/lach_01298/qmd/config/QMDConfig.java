package lach_01298.qmd.config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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

public class QMDConfig {

	private static Configuration config = null;
	
	public static final String CATEGORY_PROCESSORS = "processors";
	public static final String CATEGORY_ACCELERATOR = "accelerator";
	public static final String CATEGORY_PARTICLE_CHAMBER = "particle_chamber";
	public static final String CATEGORY_VACUUM_CHAMBER = "vacuum_chamber";
	public static final String CATEGORY_FISSION = "fission";
	public static final String CATEGORY_FUSION = "fusion";
	public static final String CATEGORY_TOOLS = "tools";
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
	public static String[]cooler_rule;

	public static double beamAttenuationRate;
	public static int beamDiverterRadius;

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
	
	public static int[] heater_heat_removed;
	public static String[]heater_rule;
	
	public static int[] processor_power;
	public static int[] processor_time;
	public static String[] atmosphere_collector_recipes;
	
	public static int[] tool_mining_level;
	public static int[] tool_durability;
	public static double[] tool_speed;
	public static double[] tool_attack_damage;
	public static int[] tool_enchantability;
	
	public static double[] lepton_damage;
	public static double[] lepton_radiation;
	public static double[] lepton_range;
	public static int lepton_cool_down;
	
	public static double gluon_damage;
	public static double gluon_radiation;
	public static double gluon_range;
	
	public static int[] hev_armour;
	public static double[] hev_rad_res;
	public static double [] hev_toughness;
	public static int[] hev_energy;
	public static int[] hev_power;
	
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

	
	
	public static int source_particle_amount;
	public static int[] source_capacity;
	public static int[] canister_capacity;
	public static int[] cell_capacity;
	
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

		Property propertyProcessorPower = config.get(CATEGORY_PROCESSORS, "power", new int[] {50,100}, Lang.localise("gui.qmd.config.processors.power.comment"), 0, 32767);
		propertyProcessorPower.setLanguageKey("gui.qmd.config.processors.power");
		
		Property propertyProcessorTime = config.get(CATEGORY_PROCESSORS, "time", new int[] {400,200}, Lang.localise("gui.qmd.config.processors.time.comment"), 0, 32767);
		propertyProcessorTime.setLanguageKey("gui.qmd.config.processors.time");
		
		Property propertyAtmosphereCollectorRecipes = config.get(CATEGORY_PROCESSORS, "atmosphere_collector_recipes", new String[] {"0:compressed_air:1000","-1:compressed_air:1000","1:compressed_air:1000"}, Lang.localise("gui.qmd.config.processors.atmosphere_collector_recipes.comment"));
		propertyAtmosphereCollectorRecipes.setLanguageKey("gui.qmd.config.processors.atmosphere_collector_recipes");
		
		
		
		Property propertyAcceleratorLinearMinSize = config.get(CATEGORY_ACCELERATOR, "accelerator_linear_min_size", 6, Lang.localise("gui.qmd.config.accelerator.accelerator_linear_min_size.comment"), 6, 255);
		propertyAcceleratorLinearMinSize.setLanguageKey("gui.qmd.config.accelerator.accelerator_linear_min_size");
		Property propertyAcceleratorLinearMaxSize = config.get(CATEGORY_ACCELERATOR, "accelerator_linear_max_size", 100, Lang.localise("gui.qmd.config.accelerator.accelerator_linear_max_size.comment"), 6, 255);
		propertyAcceleratorLinearMaxSize.setLanguageKey("gui.qmd.config.accelerator.accelerator_linear_max_size");
		
		Property propertyAcceleratorRingMinSize = config.get(CATEGORY_ACCELERATOR, "accelerator_ring_min_size", 11, Lang.localise("gui.qmd.config.accelerator.accelerator_ring_min_size.comment"), 11, 255);
		propertyAcceleratorRingMinSize.setLanguageKey("gui.qmd.config.accelerator.accelerator_ring_min_size");
		Property propertyAcceleratorRingMaxSize = config.get(CATEGORY_ACCELERATOR, "accelerator_ring_max_size", 100, Lang.localise("gui.qmd.config.accelerator.accelerator_ring_max_size.comment"), 11, 255);
		propertyAcceleratorRingMaxSize.setLanguageKey("gui.qmd.config.accelerator.accelerator_ring_max_size");
		
		
		
		Property propertyAcceleratorBaseHeatCapacity = config.get(CATEGORY_ACCELERATOR, "accelerator_base_heat_capacity", 25000, Lang.localise("gui.qmd.config.accelerator.accelerator_base_heat_capacity.comment"), 1, Integer.MAX_VALUE);
		propertyAcceleratorBaseHeatCapacity.setLanguageKey("gui.qmd.config.accelerator.accelerator_base_heat_capacity");
		Property propertyAcceleratorBaseEnergyCapacity = config.get(CATEGORY_ACCELERATOR, "accelerator_base_energy_capacity", 40000, Lang.localise("gui.qmd.config.accelerator.accelerator_base_energy_capacity.comment"), 1, Integer.MAX_VALUE);
		propertyAcceleratorBaseEnergyCapacity.setLanguageKey("gui.qmd.config.accelerator.accelerator_base_energy_capacity");
		Property propertyAcceleratorBaseInputTankCapacity = config.get(CATEGORY_ACCELERATOR, "accelerator_base_input_tank_capacity", 10, Lang.localise("gui.qmd.config.accelerator.accelerator_base_input_tank_capacity.comment"), 1, Integer.MAX_VALUE);
		propertyAcceleratorBaseInputTankCapacity.setLanguageKey("gui.qmd.config.accelerator.accelerator_base_input_tank_capacity");
		Property propertyAcceleratorBaseOutputTankCapacity = config.get(CATEGORY_ACCELERATOR, "accelerator_base_output_tank_capacity", 3200, Lang.localise("gui.qmd.config.accelerator.accelerator_base_output_tank_capacity.comment"), 1, Integer.MAX_VALUE);
		propertyAcceleratorBaseOutputTankCapacity.setLanguageKey("gui.qmd.config.accelerator.accelerator_base_output_tank_capacity");
		
		Property propertyAcceleratorThermalConductivity = config.get(CATEGORY_ACCELERATOR, "accelerator_thermal_conductivity",  0.0025d, Lang.localise("gui.qmd.config.accelerator.accelerator_thermal_conductivity.comment"), 0d, Double.MAX_VALUE);
		propertyAcceleratorThermalConductivity.setLanguageKey("gui.qmd.config.accelerator.accelerator_thermal_conductivity");
		Property propertyAcceleratorRingInputEnergy = config.get(CATEGORY_ACCELERATOR, "minimium_accelerator_ring_input_particle_energy", 5000, Lang.localise("gui.qmd.config.accelerator.minimium_accelerator_ring_input_particle_energy.comment"), 0, Integer.MAX_VALUE);
		propertyAcceleratorRingInputEnergy.setLanguageKey("gui.qmd.config.accelerator.minimium_accelerator_ring_input_particle_energy");
		
		Property propertyBeamAttenuationRate = config.get(CATEGORY_ACCELERATOR, "beam_attenuation_rate", 0.04D, Lang.localise("gui.qmd.config.accelerator.beam_attenuation_rate.comment"), 0.0D, 1000D);
		propertyBeamAttenuationRate.setLanguageKey("gui.qmd.config.accelerator.beam_attenuation_rate");
		Property propertyBeamDiverterRadius = config.get(CATEGORY_ACCELERATOR, "beam_diverter_radius", 160, Lang.localise("gui.qmd.config.accelerator.beam_diverter_radius.comment"), 0, 1000);
		propertyBeamDiverterRadius.setLanguageKey("gui.qmd.config.accelerator.beam_diverter_radius");
		
		Property propertyRFCavityVoltage = config.get(CATEGORY_ACCELERATOR, "RF_cavity_voltage", new int[] {200, 500, 1000, 2000, 4000}, Lang.localise("gui.qmd.config.accelerator.RF_cavity_voltage.comment"), 0, Integer.MAX_VALUE);
		propertyRFCavityVoltage.setLanguageKey("gui.qmd.config.accelerator.RF_cavity_voltage");
		Property propertyRFCavityEfficiency = config.get(CATEGORY_ACCELERATOR, "RF_cavity_efficiency", new double[] {0.5D, 0.8D, 0.90D, 0.95D, 0.99D}, Lang.localise("gui.qmd.config.accelerator.RF_cavity_efficiency.comment"), 0D, 1D);
		propertyRFCavityEfficiency.setLanguageKey("gui.qmd.config.accelerator.RF_cavity_efficiency");
		Property propertyRFCavityHeatGenerated = config.get(CATEGORY_ACCELERATOR, "RF_cavity_heat_generated", new int[] {300, 580, 1140, 2260, 4500}, Lang.localise("gui.qmd.config.accelerator.RF_cavity_heat_generated.comment"), 0, Integer.MAX_VALUE);
		propertyRFCavityHeatGenerated.setLanguageKey("gui.qmd.config.accelerator.RF_cavity_heat_generated");
		Property propertyRFCavityBasePower = config.get(CATEGORY_ACCELERATOR, "RF_cavity_base_power", new int[] {500, 1000, 2000, 4000, 8000}, Lang.localise("gui.qmd.config.accelerator.RF_cavity_base_power.comment"), 0, Integer.MAX_VALUE);
		propertyRFCavityBasePower.setLanguageKey("gui.qmd.config.accelerator.RF_cavity_base_power");
		Property propertyRFCavityMaxTemp = config.get(CATEGORY_ACCELERATOR, "RF_cavity_max_temp", new int[] {350, 39, 18, 10, 104}, Lang.localise("gui.qmd.config.accelerator.RF_cavity_max_temp.comment"), 0, 400);
		propertyRFCavityMaxTemp.setLanguageKey("gui.qmd.config.accelerator.RF_cavity_max_temp");
		
		Property propertyMagnetStrength = config.get(CATEGORY_ACCELERATOR, "magnet_strength", new double[] {0.2D, 0.5D, 1D, 2D, 4D}, Lang.localise("gui.qmd.config.accelerator.magnet_strength.comment"), 0D, 100D);
		propertyMagnetStrength.setLanguageKey("gui.qmd.config.accelerator.magnet_strength");
		Property propertyMagnetEfficiency = config.get(CATEGORY_ACCELERATOR, "magnet_efficiency", new double[] {0.5D, 0.8D, 0.90D, 0.95D, 0.99D}, Lang.localise("gui.qmd.config.accelerator.magnet_efficiency.comment"), 0D, 1D);
		propertyMagnetEfficiency.setLanguageKey("gui.qmd.config.accelerator.magnet_efficiency");
		Property propertyMagnetHeatGenerated = config.get(CATEGORY_ACCELERATOR, "magnet_heat_generated", new int[] {300, 580, 1140, 2260, 4500}, Lang.localise("gui.qmd.config.accelerator.magnet_heat_generated.comment"),0, Integer.MAX_VALUE);
		propertyMagnetHeatGenerated.setLanguageKey("gui.qmd.config.accelerator.magnet_heat_generated");
		Property propertyMagnetBasePower = config.get(CATEGORY_ACCELERATOR, "magnet_base_power", new int[] {1000, 2000, 4000, 8000, 16000}, Lang.localise("gui.qmd.config.accelerator.magnet_base_power.comment"), 0, Integer.MAX_VALUE);
		propertyMagnetBasePower.setLanguageKey("gui.qmd.config.accelerator.magnet_base_power");
		Property propertyMagnetMaxTemp = config.get(CATEGORY_ACCELERATOR, "magnet_max_temp", new int[] {350, 39, 18, 10, 104}, Lang.localise("gui.qmd.config.accelerator.magnet_max_temp.comment"), 0, Integer.MAX_VALUE);
		propertyMagnetMaxTemp.setLanguageKey("gui.qmd.config.accelerator.magnet_max_temp");
		
		Property propertyCoolerHeatRemoved = config.get(CATEGORY_ACCELERATOR, "cooler_heat_removed", new int[] {60, 55, 115, 75, 70, 90, 110, 130, 95, 85, 165, 50, 100, 185, 135, 80, 120, 65, 105, 125, 150, 180, 175, 160, 155, 170, 140, 145, 195, 200, 190, 205}, Lang.localise("gui.qmd.config.accelerator.cooler_heat_removed.comment"), 0, Integer.MAX_VALUE);
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
				Lang.localise("gui.qmd.config.accelerator.cooler_rule.comment"));
		propertyCoolerRule.setLanguageKey("gui.qmd.config.accelerator.cooler_rule");
		
		
		
		
		Property propertyTargetChamberPower = config.get(CATEGORY_PARTICLE_CHAMBER, "target_chamber_power", 5000, Lang.localise("gui.qmd.config.particle_chamber.target_chamber_power.comment"), 0, Integer.MAX_VALUE);
		propertyTargetChamberPower.setLanguageKey("gui.qmd.config.particle_chamber.target_chamber_power");
		Property propertyDecayChamberPower = config.get(CATEGORY_PARTICLE_CHAMBER, "decay_chamber_power", 5000, Lang.localise("gui.qmd.config.particle_chamber.decay_chamber_power.comment"), 0, Integer.MAX_VALUE);
		propertyDecayChamberPower.setLanguageKey("gui.qmd.config.particle_chamber.decay_chamber_power");
		Property propertyBeamDumpPower = config.get(CATEGORY_PARTICLE_CHAMBER, "beam_dump_power", 5000, Lang.localise("gui.qmd.config.particle_chamber.beam_dump_power.comment"), 0, Integer.MAX_VALUE);
		propertyBeamDumpPower.setLanguageKey("gui.qmd.config.particle_chamber.beam_dump_power");
		Property propertyCollisionChamberPower = config.get(CATEGORY_PARTICLE_CHAMBER, "collision_chamber_power", 5000, Lang.localise("gui.qmd.config.particle_chamber.collision_chamber_power.comment"), 0, Integer.MAX_VALUE);
		propertyCollisionChamberPower.setLanguageKey("gui.qmd.config.particle_chamber.collision_chamber_power");
		
		Property propertyDetectorEfficiency = config.get(CATEGORY_PARTICLE_CHAMBER, "detector_efficiency", new double[] {0.15D, 0.3D, 0.20D, 0.1D,0.05D}, Lang.localise("gui.qmd.config.particle_chamber.detector_efficiency.comment"), 0D, 100D);
		propertyDetectorEfficiency.setLanguageKey("gui.qmd.config.particle_chamber.detector_efficiency");
		Property propertyDetectorBasePower = config.get(CATEGORY_PARTICLE_CHAMBER, "detector_base_power", new int[] {200, 2000, 1000,200,100}, Lang.localise("gui.qmd.config.particle_chamber.detector_base_power.comment"), 0, Integer.MAX_VALUE);
		propertyDetectorBasePower.setLanguageKey("gui.qmd.config.particle_chamber.detector_base_power");

		
		Property propertyParticleChamberBaseEnergyCapacity = config.get(CATEGORY_PARTICLE_CHAMBER, "particle_chamber_base_energy_capacity", 40000, Lang.localise("gui.qmd.config.particle_chamber.particle_chamber_base_energy_capacity.comment"), 1, Integer.MAX_VALUE);
		propertyParticleChamberBaseEnergyCapacity.setLanguageKey("gui.qmd.config.particle_chamber.particle_chamber_base_energy_capacity");
		Property propertyParticleChamberInputTankCapacity = config.get(CATEGORY_PARTICLE_CHAMBER, "particle_chamber_base_input_tank_capacity", 1000, Lang.localise("gui.qmd.config.particle_chamber.particle_chamber_base_input_tank_capacity.comment"), 1, Integer.MAX_VALUE);
		propertyParticleChamberInputTankCapacity.setLanguageKey("gui.qmd.config.particle_chamber.particle_chamber_base_input_tank_capacity");
		Property propertyParticleChamberOutputTankCapacity = config.get(CATEGORY_PARTICLE_CHAMBER, "particle_chamber_base_output_tank_capacity", 1000, Lang.localise("gui.qmd.config.particle_chamber.particle_chamber_base_output_tank_capacity.comment"), 1, Integer.MAX_VALUE);
		propertyParticleChamberOutputTankCapacity.setLanguageKey("gui.qmd.config.particle_chamber.particle_chamber_base_output_tank_capacity");
		
		
		
		Property propertyContainmentPartPower = config.get(CATEGORY_VACUUM_CHAMBER, "part_power", new int[] {400, 500,500,500,1000}, Lang.localise("gui.qmd.config.vacuum_chamber.part_power.comment"), 0, Integer.MAX_VALUE);
		propertyContainmentPartPower.setLanguageKey("gui.qmd.config.vacuum_chamber.part_power");
		Property propertyContainmentPartHeat = config.get(CATEGORY_VACUUM_CHAMBER, "part_heat", new int[] {200, 500,100,100,500}, Lang.localise("gui.qmd.config.vacuum_chamber.part_heat.comment"), 0, Integer.MAX_VALUE);
		propertyContainmentPartHeat.setLanguageKey("gui.qmd.config.vacuum_chamber.part_heat");
		Property propertyContainmentMaxTemp = config.get(CATEGORY_VACUUM_CHAMBER, "part_max_temp", new int[] {104,104,104,104,104}, Lang.localise("gui.qmd.config.vacuum_chamber.part_max_temp.comment"), 0, 400);
		propertyContainmentMaxTemp.setLanguageKey("gui.qmd.config.vacuum_chamber.part_max_temp");
		
		Property propertyVacuumChamberBaseEnergyCapacity = config.get(CATEGORY_VACUUM_CHAMBER, "vacuum_chamber_base_energy_capacity", 40000, Lang.localise("gui.qmd.config.vacuum_chamber.vacuum_chamber_base_energy_capacity.comment"), 1, Integer.MAX_VALUE);
		propertyVacuumChamberBaseEnergyCapacity.setLanguageKey("gui.qmd.config.vacuum_chamber.vacuum_chamber_base_energy_capacity");
		Property propertyVacuumChamberInputTankCapacity = config.get(CATEGORY_VACUUM_CHAMBER, "vacuum_chamber_base_input_tank_capacity", 1000, Lang.localise("gui.qmd.config.vacuum_chamber.vacuum_chamber_base_input_tank_capacity.comment"), 1, Integer.MAX_VALUE);
		propertyVacuumChamberInputTankCapacity.setLanguageKey("gui.qmd.config.vacuum_chamber.vacuum_chamber_base_input_tank_capacity");
		Property propertyVacuumChamberOutputTankCapacity = config.get(CATEGORY_VACUUM_CHAMBER, "vacuum_chamber_base_output_tank_capacity", 1000, Lang.localise("gui.qmd.config.vacuum_chamber.vacuum_chamber_base_output_tank_capacity.comment"), 1, Integer.MAX_VALUE);
		propertyVacuumChamberOutputTankCapacity.setLanguageKey("gui.qmd.config.vacuum_chamber.vacuum_chamber_base_output_tank_capacity");
		
		Property propertyHeaterHeatRemoved = config.get(CATEGORY_VACUUM_CHAMBER, "heater_heat_removed", new int[] {5,10,20,40,80,160,320,640}, Lang.localise("gui.qmd.config.vacuum_chamber.heater_heat_removed.comment"), 0, Integer.MAX_VALUE);
		propertyHeaterHeatRemoved.setLanguageKey("gui.qmd.config.vacuum_chamber.heater_heat_removed");
		Property propertyHeaterRule = config.get(CATEGORY_VACUUM_CHAMBER, "heater_rule", new String[] {"one casing","one beam", "two glass", "exactly one quartz heater && exactly one redstone heater", "two axial obsidian heaters", "exactly one redstone heater && two iron heaters", "one obsidian heater && one quartz heater", "one nozzle"},
				Lang.localise("gui.qmd.config.vacuum_chamber.heater_rule.comment"));
		propertyHeaterRule.setLanguageKey("gui.qmd.config.vacuum_chamber.heater_rule");
		
		
		Property propertyToolMiningLevel = config.get(CATEGORY_TOOLS, "tool_mining_level", new int[] {4}, Lang.localise("gui.qmd.config.tools.tool_mining_level.comment"), 0, 15);
		propertyToolMiningLevel.setLanguageKey("gui.qmd.config.tools.tool_mining_level");
		Property propertyToolDurability = config.get(CATEGORY_TOOLS, "tool_durability", new int[] {1928*3}, Lang.localise("gui.qmd.config.tools.tool_durability.comment"), 1, Integer.MAX_VALUE);
		propertyToolDurability.setLanguageKey("gui.qmd.config.tools.tool_durability");
		Property propertyToolSpeed = config.get(CATEGORY_TOOLS, "tool_speed", new double[] {11D}, Lang.localise("gui.qmd.config.tools.tool_speed.comment"), 1D, 255D);
		propertyToolSpeed.setLanguageKey("gui.qmd.config.tools.tool_speed");
		Property propertyToolAttackDamage = config.get(CATEGORY_TOOLS, "tool_attack_damage", new double[] {3D}, Lang.localise("gui.qmd.config.tools.tool_attack_damage.comment"), 0D, 255D);
		propertyToolAttackDamage.setLanguageKey("gui.qmd.config.tools.tool_attack_damage");
		Property propertyToolEnchantability = config.get(CATEGORY_TOOLS, "tool_enchantability", new int[] {12}, Lang.localise("gui.qmd.config.tools.tool_enchantability.comment"), 1, 255);
		propertyToolEnchantability.setLanguageKey("gui.qmd.config.tools.tool_enchantability");
		
		Property propertyLeptonDamage = config.get(CATEGORY_TOOLS, "lepton_damage", new double[] {7.0, 14.0, 28.0}, Lang.localise("gui.qmd.config.tools.lepton_damage.comment"), 0, Float.MAX_VALUE);
		propertyLeptonDamage.setLanguageKey("gui.qmd.config.tools.lepton_damage");
		Property propertyLeptonRadiation = config.get(CATEGORY_TOOLS, "lepton_radiation", new double[] {10.0, 20.0, 40.0}, Lang.localise("gui.qmd.config.tools.lepton_radiation.comment"), 0, Double.MAX_VALUE);
		propertyLeptonRadiation.setLanguageKey("gui.qmd.config.tools.lepton_radiation");
		Property propertyLeptonRange = config.get(CATEGORY_TOOLS, "lepton_range", new double[] {30.0, 60.0, 90.0}, Lang.localise("gui.qmd.config.tools.lepton_range.comment"), 0, 128.0);
		propertyLeptonRange.setLanguageKey("gui.qmd.config.tools.lepton_range");
		Property propertyLeptonCoolDown = config.get(CATEGORY_TOOLS, "lepton_cool_down", 8, Lang.localise("gui.qmd.config.tools.lepton_cool_down.comment"), 0, 10000);
		propertyLeptonCoolDown.setLanguageKey("gui.qmd.config.tools.lepton_cool_down");
		
		Property propertyGluonDamage = config.get(CATEGORY_TOOLS, "gluon_damage", 10.0, Lang.localise("gui.qmd.config.tools.gluon_damage.comment"), 0, Float.MAX_VALUE);
		propertyGluonDamage.setLanguageKey("gui.qmd.config.tools.gluon_damage");
		Property propertyGluonRadiation = config.get(CATEGORY_TOOLS, "gluon_radiation", 10.0, Lang.localise("gui.qmd.config.tools.gluon_radiation.comment"), 0, Double.MAX_VALUE);
		propertyGluonRadiation.setLanguageKey("gui.qmd.config.tools.gluon_radiation");
		Property propertyGluonRange = config.get(CATEGORY_TOOLS, "gluon_range", 40.0, Lang.localise("gui.qmd.config.tools.gluon_range.comment"), 0, 128.0);
		propertyGluonRange.setLanguageKey("gui.qmd.config.tools.gluon_range");
		
		
		Property propertyHEVArmour = config.get(CATEGORY_TOOLS, "hev_armour", new int[] {4, 7, 9, 4, 1, 3, 4, 1}, Lang.localise("gui.qmd.config.tools.hev_armour.comment"), 1, 25);
		propertyHEVArmour.setLanguageKey("gui.qmd.config.tools.hev_armour");
		Property propertyHEVRadRes = config.get(CATEGORY_TOOLS, "hev_rad_res", new double[] {20.0, 30.0, 20.0, 20.0}, Lang.localise("gui.qmd.config.tools.hev_rad_res.comment"), 0.0, 1000.0);
		propertyHEVRadRes.setLanguageKey("gui.qmd.config.tools.hev_rad_res");
		Property propertyHEVToughness = config.get(CATEGORY_TOOLS, "hev_toughness", new double[] {4D, 0D}, Lang.localise("gui.qmd.config.tools.hev_toughness.comment"), 0D, 8D);
		propertyHEVToughness.setLanguageKey("gui.qmd.config.tools.hev_toughness");
		Property propertyHEVEnergy = config.get(CATEGORY_TOOLS, "hev_energy", new int[] {1000000,1000000,1000000,1000000}, Lang.localise("gui.qmd.config.tools.hev_energy.comment"), 0, Integer.MAX_VALUE);
		propertyHEVEnergy.setLanguageKey("gui.qmd.config.tools.hev_energy");
		Property propertyHEVPower = config.get(CATEGORY_TOOLS, "hev_power", new int[] {100,100,250,100, 1000}, Lang.localise("gui.qmd.config.tools.hev_power.comment"), 0, Integer.MAX_VALUE);
		propertyHEVPower.setLanguageKey("gui.qmd.config.tools.hev_power");//damage,jump boost,long jump, fall reduction, posion/wither
		
		
		Property propertyFissionReflectorEfficiency = config.get(CATEGORY_FISSION, "reflector_efficiency", new double[] {0.75D}, Lang.localise("gui.qmd.config.fission.reflector_efficiency.comment"), 0D, 255D);
		propertyFissionReflectorEfficiency.setLanguageKey("gui.qmd.config.fission.reflector_efficiency");
		Property propertyFissionReflectorReflectivity = config.get(CATEGORY_FISSION, "reflector_reflectivity", new double[] {0.75D}, Lang.localise("gui.qmd.config.fission.reflector_reflectivity.comment"), 0D, 1D);
		propertyFissionReflectorReflectivity.setLanguageKey("gui.qmd.config.fission.reflector_reflectivity");
		Property propertyFissionShieldHeatPerFlux = config.get(CATEGORY_FISSION, "shield_heat_per_flux", new double[] {15D}, Lang.localise("gui.qmd.config.fission.shield_heat_per_flux.comment"), 0D, 32767D);
		propertyFissionShieldHeatPerFlux.setLanguageKey("gui.qmd.config.fission.shield_heat_per_flux");
		Property propertyFissionShieldEfficiency = config.get(CATEGORY_FISSION, "shield_efficiency", new double[] {1D}, Lang.localise("gui.qmd.config.fission.shield_efficiency.comment"), 0D, 255D);
		propertyFissionShieldEfficiency.setLanguageKey("gui.qmd.config.fission.shield_efficiency");
		
		Property propertyCoperniciumFuelTime = config.get(CATEGORY_FISSION, "copernicium_fuel_time", new int[] {10000, 10000, 12004, 9001}, Lang.localise("gui.qmd.config.copernicium_fuel_time.comment"), 1, Integer.MAX_VALUE);
		propertyCoperniciumFuelTime.setLanguageKey("gui.qmd.config.copernicium_fuel_time");
		Property propertyCoperniciumHeatGeneration = config.get(CATEGORY_FISSION, "copernicium_heat_generation", new int[] {2000, 2000, 1666, 2222}, Lang.localise("gui.qmd.config.copernicium_heat_generation.comment"), 0, 32767);
		propertyCoperniciumHeatGeneration.setLanguageKey("gui.qmd.config.copernicium_heat_generation");
		Property propertyCoperniciumEfficiency = config.get(CATEGORY_FISSION, "copernicium_efficiency", new double[] {5.0D, 5.0D, 5.0D, 5.0D}, Lang.localise("gui.qmd.config.copernicium_efficiency.comment"), 0D, 32767D);
		propertyCoperniciumEfficiency.setLanguageKey("gui.qmd.config.copernicium_efficiency");
		Property propertyCoperniciumCriticality = config.get(CATEGORY_FISSION, "copernicium_criticality", new int[] {20, 25, 35, 20}, Lang.localise("gui.qmd.config.copernicium_criticality.comment"), 0, 32767);
		propertyCoperniciumCriticality.setLanguageKey("gui.qmd.config.copernicium_criticality");
		Property propertyCoperniciumDecayFactor = config.get(CATEGORY_FISSION, "copernicium_decay_factor", new double[] {0.11D, 0.11D, 0.11D, 0.11D}, Lang.localise("gui.qmd.config.copernicium_criticality.comment"), 0, 32767);
		propertyCoperniciumDecayFactor.setLanguageKey("gui.qmd.config.copernicium_decay_factor");
		Property propertyCoperniciumSelfPriming = config.get(CATEGORY_FISSION, "copernicium_self_priming", new boolean[] {true, true, true, true}, Lang.localise("gui.qmd.config.copernicium_decay_factor.comment"));
		propertyCoperniciumSelfPriming.setLanguageKey("gui.qmd.config.copernicium_self_priming");
		Property propertyCoperniciumRadiation = config.get(CATEGORY_FISSION, "copernicium_radiation", new double[] {QMDRadSources.MIX_291, QMDRadSources.MIX_291, QMDRadSources.MIX_291, QMDRadSources.MIX_291}, Lang.localise("gui.qmd.config.copernicium_radiation.comment"), 0D, 1000D);
		propertyCoperniciumRadiation.setLanguageKey("gui.qmd.config.copernicium_radiation");
		
		
		Property propertyRTGPower = config.get(CATEGORY_OTHER, "rtg_power", new int[] {50}, Lang.localise("gui.qmd.config.other.rtg_power.comment"), 0, Integer.MAX_VALUE);
		propertyRTGPower.setLanguageKey("gui.qmd.config.other.rtg_power");
		
		
		
		
		
		Property propertySourceParticleAmount = config.get(CATEGORY_OTHER, "source_particle_amount", 100, Lang.localise("gui.qmd.config.other.source_particle_amount.comment"), 1, Integer.MAX_VALUE);
		propertySourceParticleAmount.setLanguageKey("gui.qmd.config.other.source_particle_amount");
		Property propertySourceCapacity = config.get(CATEGORY_OTHER, "source_capacity", new int[] {6000, 6000, 300, 300, 6000}, Lang.localise("gui.qmd.config.other.source_capacity.comment"), 0, Integer.MAX_VALUE);
		propertySourceCapacity.setLanguageKey("gui.qmd.config.other.source_capacity");
		Property propertyCanisterCapacity = config.get(CATEGORY_OTHER, "canister_capacity", new int[] {0, 6000, 6000, 6000, 6000, 6000, 6000}, Lang.localise("gui.qmd.config.other.canister_capacity.comment"), 0, Integer.MAX_VALUE);
		propertyCanisterCapacity.setLanguageKey("gui.qmd.config.other.canister_capacity");
		Property propertyCellCapacity = config.get(CATEGORY_OTHER, "cell_capacity", new int[] {0, 600, 600, 600, 600, 600, 200, 200, 200, 6000}, Lang.localise("gui.qmd.config.other.cell_capacity.comment"), 0, Integer.MAX_VALUE);
		propertyCellCapacity.setLanguageKey("gui.qmd.config.other.cell_capacity");
		
		
		
		Property propertyOverrideNCRecipes = config.get(CATEGORY_OTHER, "override_nc_recipes", true, Lang.localise("gui.qmd.config.other.override_nc_recipes.comment"));
		propertyOverrideNCRecipes.setLanguageKey("gui.qmd.config.other.override_nc_recipes");
		
		//Property propertyItemTickerChunksPerTick = config.get(CATEGORY_OTHER, "item_ticker_chunks_per_tick", 5, Lang.localise("gui.qmd.config.other.item_ticker_chunks_per_tick.comment"),0,400);
		//propertyItemTickerChunksPerTick.setLanguageKey("gui.qmd.config.other.item_ticker_chunks_per_tick");
		
		Property propertyTurbineBladeEfficiency = config.get(CATEGORY_OTHER, "turbine_blade_efficiency", new double[] {1.25D}, Lang.localise("gui.qmd.config.other.turbine_blade_efficiency.comment"),  0.01D, 15D);
		propertyTurbineBladeEfficiency.setLanguageKey("gui.qmd.config.other.turbine_blade_efficiency");

		Property propertyTurbineBladeExpansion = config.get(CATEGORY_OTHER, "turbine_blade_expansion", new double[] {1.9D}, Lang.localise("gui.qmd.config.other.turbine_blade_expansion.comment"), 1D, 15D);
		propertyTurbineBladeExpansion.setLanguageKey("gui.qmd.config.other.turbine_blade_expansion");
		
		List<String> propertyOrderProcessors = new ArrayList<String>();
		propertyOrderProcessors.add(propertyProcessorPower.getName());
		propertyOrderProcessors.add(propertyProcessorTime.getName());
		propertyOrderProcessors.add(propertyAtmosphereCollectorRecipes.getName());
		
		
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
		
		propertyOrderAccelerator.add(propertyBeamAttenuationRate.getName());
		propertyOrderAccelerator.add(propertyBeamDiverterRadius.getName());
		
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
		propertyOrderContainment.add(propertyHeaterHeatRemoved.getName());
		propertyOrderContainment.add(propertyHeaterRule.getName());
		
		config.setCategoryPropertyOrder(CATEGORY_VACUUM_CHAMBER, propertyOrderContainment);
		
		
		
		List<String> propertyOrderTools = new ArrayList<String>();
		propertyOrderTools.add(propertyToolMiningLevel.getName());
		propertyOrderTools.add(propertyToolDurability.getName());
		propertyOrderTools.add(propertyToolSpeed.getName());
		propertyOrderTools.add(propertyToolAttackDamage.getName());
		propertyOrderTools.add(propertyToolEnchantability.getName());
		
		propertyOrderTools.add(propertyLeptonDamage.getName());
		propertyOrderTools.add(propertyLeptonRadiation.getName());
		propertyOrderTools.add(propertyLeptonRange.getName());
		propertyOrderTools.add(propertyLeptonCoolDown.getName());
		
		propertyOrderTools.add(propertyGluonDamage.getName());
		propertyOrderTools.add(propertyGluonRadiation.getName());
		propertyOrderTools.add(propertyGluonRange.getName());
		
		propertyOrderTools.add(propertyHEVArmour.getName());
		
		propertyOrderTools.add(propertyHEVToughness.getName());
		propertyOrderTools.add(propertyHEVEnergy.getName());
		propertyOrderTools.add(propertyHEVPower.getName());
		
		
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
		
		
		List<String> propertyOrderOther = new ArrayList<String>();
		
		propertyOrderOther.add(propertyRTGPower.getName());
		
		
		
		propertyOrderOther.add(propertySourceParticleAmount.getName());
		propertyOrderOther.add(propertySourceCapacity.getName());
		propertyOrderOther.add(propertyCanisterCapacity.getName());
		propertyOrderOther.add(propertyCellCapacity.getName());
		

		propertyOrderOther.add(propertyOverrideNCRecipes.getName());
		//propertyOrderOther.add(propertyItemTickerChunksPerTick.getName());
		propertyOrderOther.add(propertyTurbineBladeEfficiency.getName());
		propertyOrderOther.add(propertyTurbineBladeExpansion.getName());
		
		
		config.setCategoryPropertyOrder(CATEGORY_OTHER, propertyOrderOther);
		
		
		if (setFromConfig) 
		{
			processor_power = readIntegerArrayFromConfig(propertyProcessorPower);
			processor_time = readIntegerArrayFromConfig(propertyProcessorTime);
			atmosphere_collector_recipes = propertyAtmosphereCollectorRecipes.getStringList();
			
			
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
			
			beamAttenuationRate = propertyBeamAttenuationRate.getDouble();
			beamDiverterRadius = propertyBeamDiverterRadius.getInt();
			
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
			
			heater_heat_removed = readIntegerArrayFromConfig(propertyHeaterHeatRemoved);
			heater_rule = propertyHeaterRule.getStringList();
			
			tool_mining_level = readIntegerArrayFromConfig(propertyToolMiningLevel);
			tool_durability = readIntegerArrayFromConfig(propertyToolDurability);
			tool_speed = readDoubleArrayFromConfig(propertyToolSpeed);
			tool_attack_damage = readDoubleArrayFromConfig(propertyToolAttackDamage);
			tool_enchantability = readIntegerArrayFromConfig(propertyToolEnchantability);
			
			lepton_damage = readDoubleArrayFromConfig(propertyLeptonDamage);
			lepton_radiation = readDoubleArrayFromConfig(propertyLeptonRadiation);
			lepton_range = readDoubleArrayFromConfig(propertyLeptonRange);
			lepton_cool_down = propertyLeptonCoolDown.getInt();
			
			gluon_damage = propertyGluonDamage.getDouble();
			gluon_radiation = propertyGluonRadiation.getDouble();
			gluon_range = propertyGluonRange.getDouble();
			
			
			hev_armour = readIntegerArrayFromConfig(propertyHEVArmour);
			hev_rad_res = readDoubleArrayFromConfig(propertyHEVRadRes);
			hev_toughness = readDoubleArrayFromConfig(propertyHEVToughness);
			hev_energy = readIntegerArrayFromConfig(propertyHEVEnergy);
			hev_power =  readIntegerArrayFromConfig(propertyHEVPower);
			
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
			
			rtg_power = readIntegerArrayFromConfig(propertyRTGPower);
			
			
			
			source_particle_amount = propertySourceParticleAmount.getInt();
			source_capacity = readIntegerArrayFromConfig(propertySourceCapacity);
			canister_capacity = readIntegerArrayFromConfig(propertyCanisterCapacity);
			cell_capacity = readIntegerArrayFromConfig(propertyCellCapacity);
			
			
			override_nc_recipes = propertyOverrideNCRecipes.getBoolean();
			//item_ticker_chunks_per_tick = propertyItemTickerChunksPerTick.getInt();
			
			turbine_blade_efficiency = readDoubleArrayFromConfig(propertyTurbineBladeEfficiency);
			turbine_blade_expansion = readDoubleArrayFromConfig(propertyTurbineBladeExpansion);
		}
		propertyProcessorPower.set(processor_power);
		propertyProcessorTime.set(processor_time);
		propertyAtmosphereCollectorRecipes.set(atmosphere_collector_recipes);
		
		
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
		propertyHeaterHeatRemoved.set(heater_heat_removed);
		propertyHeaterRule.set(heater_rule);
		
		propertyToolMiningLevel.set(tool_mining_level);
		propertyToolDurability.set(tool_durability);
		propertyToolSpeed.set(tool_speed);
		propertyToolAttackDamage.set(tool_attack_damage);
		propertyToolEnchantability.set(tool_enchantability);
		
		propertyLeptonDamage.set(lepton_damage);
		propertyLeptonRadiation.set(lepton_radiation);
		propertyLeptonRange.set(lepton_range);
		propertyLeptonCoolDown.set(lepton_cool_down);
		
		propertyGluonDamage.set(gluon_damage);
		propertyGluonRadiation.set(gluon_radiation);
		propertyGluonRange.set(gluon_range);
		
		
		propertyHEVArmour.set(hev_armour);
		propertyHEVRadRes.set(hev_rad_res);
		propertyHEVToughness.set(hev_toughness); 
		propertyHEVEnergy.set(hev_energy);
		propertyHEVPower.set(hev_power);
		
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
	
		
		propertyRTGPower.set(rtg_power);
		
		
		propertySourceParticleAmount.set(source_particle_amount);
		propertySourceCapacity.set(source_capacity);
		propertyCanisterCapacity.set(canister_capacity);
		propertyCellCapacity.set(cell_capacity);
		
		propertyOverrideNCRecipes.set(override_nc_recipes);
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
