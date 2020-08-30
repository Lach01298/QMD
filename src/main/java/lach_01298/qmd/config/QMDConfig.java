package lach_01298.qmd.config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import lach_01298.qmd.QMD;
import nc.util.Lang;
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
	public static final String CATEGORY_CONTAINMENT = "containment";
	public static final String CATEGORY_FISSION = "fission";
	public static final String CATEGORY_FUSION = "fusion";
	public static final String CATEGORY_TOOLS = "tools";
	public static final String CATEGORY_OTHER = "other";

	
	
	public static int accelerator_linear_min_size;
	public static int accelerator_linear_max_size;
	public static int accelerator_ring_min_size;
	public static int accelerator_ring_max_size;
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


	public static double beamAttenuationRate;
	public static int beamDiverterRadius;

	public static int target_chamber_power;
	public static int decay_chamber_power;
	public static int beam_dump_power;
	public static int[] detector_base_power;
	public static double[] detector_efficiency;
	
	public static int[] containment_part_power;
	public static int[] containment_part_heat;
	public static int containment_max_temp;
	
	public static int[] processor_power;
	public static int[] processor_time;
	public static int irradiator_fuel_life_time;
	
	public static int[] tool_mining_level;
	public static int[] tool_durability;
	public static double[] tool_speed;
	public static double[] tool_attack_damage;
	public static int[] tool_enchantability;
	
	public static double[] fission_reflector_efficiency;
	public static double[] fission_reflector_reflectivity;

	public static double[] fission_shield_heat_per_flux;
	public static double[] fission_shield_efficiency;
	
	public static int[] rtg_power;
	public static double[] processor_passive_rate;
	
	public static int source_life_time;
	public static int source_particle_amount;
	
	public static int cell_life_time;
	public static int cell_power;
	
	public static boolean override_nc_recipes;
	
	public static Configuration getConfig()
	{
		return config;
	}

	public static void preInit()
	{
		File configFile = new File(Loader.instance().getConfigDir(), "qmd.cfg");
		config = new Configuration(configFile);
		syncFromFiles();

		
	}

	public static void clientPreInit()
	{
		
	}

	public static void syncFromFiles()
	{
		syncConfig(true, true);
	}

	public static void syncFromGui()
	{
		syncConfig(false, true);
	}

	public static void syncFromFields()
	{
		syncConfig(false, false);
	}

	private static void syncConfig(boolean loadFromFile, boolean setFromConfig)
	{
		if (loadFromFile) config.load();

		Property propertyProcessorPower = config.get(CATEGORY_PROCESSORS, "power", new int[] {100}, Lang.localise("gui.qmd.config.processors.power.comment"), 0, 32767);
		propertyProcessorPower.setLanguageKey("gui.qmd.config.processors.power");
		
		Property propertyProcessorTime = config.get(CATEGORY_PROCESSORS, "time", new int[] {400,200}, Lang.localise("gui.qmd.config.processors.time.comment"), 0, 32767);
		propertyProcessorTime.setLanguageKey("gui.qmd.config.processors.time");
		
		Property propertyIrradiatorFuelLifeTime = config.get(CATEGORY_PROCESSORS, "irradiator_fuel_life_time", 300, Lang.localise("gui.qmd.config.processors.irradiator_fuel_life_time.comment"), 1, Integer.MAX_VALUE);
		propertyIrradiatorFuelLifeTime.setLanguageKey("gui.qmd.config.processors.irradiator_fuel_life_time");
		
		Property propertyAcceleratorLinearMinSize = config.get(CATEGORY_ACCELERATOR, "accelerator_linear_min_size", 6, Lang.localise("gui.qmd.config.accelerator.accelerator_linear_min_size.comment"), 6, 255);
		propertyAcceleratorLinearMinSize.setLanguageKey("gui.qmd.config.accelerator.accelerator_linear_min_size");
		Property propertyAcceleratorLinearMaxSize = config.get(CATEGORY_ACCELERATOR, "accelerator_linear_max_size", 100, Lang.localise("gui.qmd.config.accelerator.accelerator_linear_max_size.comment"), 6, 255);
		propertyAcceleratorLinearMaxSize.setLanguageKey("gui.qmd.config.accelerator.accelerator_linear_max_size");
		
		Property propertyAcceleratorRingMinSize = config.get(CATEGORY_ACCELERATOR, "accelerator_ring_min_size", 11, Lang.localise("gui.qmd.config.accelerator.accelerator_ring_min_size.comment"), 11, 255);
		propertyAcceleratorRingMinSize.setLanguageKey("gui.qmd.config.accelerator.accelerator_ring_min_size");
		Property propertyAcceleratorRingMaxSize = config.get(CATEGORY_ACCELERATOR, "accelerator_ring_max_size", 100, Lang.localise("gui.qmd.config.accelerator.accelerator_ring_max_size.comment"), 11, 255);
		propertyAcceleratorRingMaxSize.setLanguageKey("gui.qmd.config.accelerator.accelerator_ring_max_size");
		
		Property propertyBeamAttenuationRate = config.get(CATEGORY_ACCELERATOR, "beam_attenuation_rate", 0.04D, Lang.localise("gui.qmd.config.accelerator.beam_attenuation_rate.comment"), 0.0D, 1000D);
		propertyBeamAttenuationRate.setLanguageKey("gui.qmd.config.accelerator.beam_attenuation_rate");
		Property propertyBeamDiverterRadius = config.get(CATEGORY_ACCELERATOR, "beam_diverter_radius", 100, Lang.localise("gui.qmd.config.accelerator.beam_diverter_radius.comment"), 0, 1000);
		propertyBeamDiverterRadius.setLanguageKey("gui.qmd.config.accelerator.beam_diverter_radius");
		
		Property propertyRFCavityVoltage = config.get(CATEGORY_ACCELERATOR, "RF_cavity_voltage", new int[] {200, 500, 1000, 2000, 4000}, Lang.localise("gui.qmd.config.accelerator.RF_cavity_voltage.comment"), 0, Integer.MAX_VALUE);
		propertyRFCavityVoltage.setLanguageKey("gui.qmd.config.accelerator.RF_cavity_voltage");
		Property propertyRFCavityEfficiency = config.get(CATEGORY_ACCELERATOR, "RF_cavity_efficiency", new double[] {0.5D, 0.8D, 0.90D, 0.95D, 0.99D}, Lang.localise("gui.qmd.config.accelerator.RF_cavity_efficiency.comment"), 0D, 1D);
		propertyRFCavityEfficiency.setLanguageKey("gui.qmd.config.accelerator.RF_cavity_efficiency");
		Property propertyRFCavityHeatGenerated = config.get(CATEGORY_ACCELERATOR, "RF_cavity_heat_generated", new int[] {60, 110, 210, 410, 810}, Lang.localise("gui.qmd.config.accelerator.RF_cavity_heat_generated.comment"), 0, Integer.MAX_VALUE);
		propertyRFCavityHeatGenerated.setLanguageKey("gui.qmd.config.accelerator.RF_cavity_heat_generated");
		Property propertyRFCavityBasePower = config.get(CATEGORY_ACCELERATOR, "RF_cavity_base_power", new int[] {250, 1000, 2000, 4000, 8000}, Lang.localise("gui.qmd.config.accelerator.RF_cavity_base_power.comment"), 0, Integer.MAX_VALUE);
		propertyRFCavityBasePower.setLanguageKey("gui.qmd.config.accelerator.RF_cavity_base_power");
		Property propertyRFCavityMaxTemp = config.get(CATEGORY_ACCELERATOR, "RF_cavity_max_temp", new int[] {350, 39, 18, 10, 104}, Lang.localise("gui.qmd.config.accelerator.RF_cavity_max_temp.comment"), 0, 400);
		propertyRFCavityMaxTemp.setLanguageKey("gui.qmd.config.accelerator.RF_cavity_max_temp");
		
		Property propertyMagnetStrength = config.get(CATEGORY_ACCELERATOR, "magnet_strength", new double[] {0.2D, 0.5D, 1D, 4D, 8D}, Lang.localise("gui.qmd.config.accelerator.magnet_strength.comment"), 0D, 100D);
		propertyMagnetStrength.setLanguageKey("gui.qmd.qmd.config.accelerator.magnet_strength");
		Property propertyMagnetEfficiency = config.get(CATEGORY_ACCELERATOR, "magnet_efficiency", new double[] {0.5D, 0.8D, 0.90D, 0.95D, 0.99D}, Lang.localise("gui.qmd.config.accelerator.magnet_efficiency.comment"), 0D, 1D);
		propertyMagnetEfficiency.setLanguageKey("gui.qmd.qmd.config.accelerator.magnet_efficiency");
		Property propertyMagnetHeatGenerated = config.get(CATEGORY_ACCELERATOR, "magnet_heat_generated", new int[] {110, 210, 410, 810, 1610}, Lang.localise("gui.qmd.config.accelerator.magnet_heat_generated.comment"),0, Integer.MAX_VALUE);
		propertyMagnetHeatGenerated.setLanguageKey("gui.qmd.config.accelerator.magnet_heat_generated");
		Property propertyMagnetBasePower = config.get(CATEGORY_ACCELERATOR, "magnet_base_power", new int[] {250, 1000, 2000, 4000, 8000}, Lang.localise("gui.qmd.config.accelerator.magnet_base_power.comment"), 0, Integer.MAX_VALUE);
		propertyMagnetBasePower.setLanguageKey("gui.qmd.config.accelerator.magnet_base_power");
		Property propertyMagnetMaxTemp = config.get(CATEGORY_ACCELERATOR, "magnet_max_temp", new int[] {350, 39, 18, 10, 104}, Lang.localise("gui.qmd.config.accelerator.magnet_max_temp.comment"), 0, Integer.MAX_VALUE);
		propertyMagnetMaxTemp.setLanguageKey("gui.qmd.config.accelerator.magnet_max_temp");
		
		Property propertyCoolerHeatRemoved = config.get(CATEGORY_ACCELERATOR, "cooler_heat_removed", new int[] {60, 55, 115, 75, 70, 90, 110, 130, 95, 85, 165, 50, 100, 195, 135, 80, 120, 65, 125, 180, 105, 140, 175, 160, 155, 170, 150, 145, 185, 200, 190, 205}, Lang.localise("gui.qmd.config.accelerator.cooler_heat_removed.comment"), 0, Integer.MAX_VALUE);
		propertyCoolerHeatRemoved.setLanguageKey("gui.qmd.config.accelerator.cooler_heat_removed");
		
		Property propertyAcceleratorRingInputEnergy = config.get(CATEGORY_ACCELERATOR, "minimium_accelerator_ring_input_particle_energy", 5000, Lang.localise("gui.qmd.config.accelerator.minimium_accelerator_ring_input_particle_energy.comment"), 0, Integer.MAX_VALUE);
		propertyAcceleratorRingInputEnergy.setLanguageKey("gui.qmd.config.accelerator.minimium_accelerator_ring_input_particle_energy");
		
		
		
		Property propertyTargetChamberPower = config.get(CATEGORY_PARTICLE_CHAMBER, "target_chamber_power", 5000, Lang.localise("gui.qmd.config.particle_chamber.target_chamber_power.comment"), 0, Integer.MAX_VALUE);
		propertyTargetChamberPower.setLanguageKey("gui.qmd.config.particle_chamber.target_chamber_power");
		Property propertyDecayChamberPower = config.get(CATEGORY_PARTICLE_CHAMBER, "decay_chamber_power", 5000, Lang.localise("gui.qmd.config.particle_chamber.decay_chamber_power.comment"), 0, Integer.MAX_VALUE);
		propertyDecayChamberPower.setLanguageKey("gui.qmd.config.particle_chamber.decay_chamber_power");
		Property propertyBeamDumpPower = config.get(CATEGORY_PARTICLE_CHAMBER, "beam_dump_power", 5000, Lang.localise("gui.qmd.config.particle_chamber.beam_dump_power.comment"), 0, Integer.MAX_VALUE);
		propertyDecayChamberPower.setLanguageKey("gui.qmd.config.particle_chamber.beam_dump_power");
		Property propertyDetectorEfficiency = config.get(CATEGORY_PARTICLE_CHAMBER, "detector_efficiency", new double[] {0.15D, 0.3D, 0.20D, 0.1D,0.05D}, Lang.localise("gui.qmd.config.particle_chamber.detector_efficiency.comment"), 0D, 100D);
		propertyDetectorEfficiency.setLanguageKey("gui.qmd.config.particle_chamber.detector_efficiency");
		Property propertyDetectorBasePower = config.get(CATEGORY_PARTICLE_CHAMBER, "detector_base_power", new int[] {200, 5000, 1000,200,100}, Lang.localise("gui.qmd.config.particle_chamber.detector_base_power.comment"), 0, Integer.MAX_VALUE);
		propertyDetectorBasePower.setLanguageKey("gui.qmd.config.particle_chamber.detector_base_power");

		Property propertyContainmentPartPower = config.get(CATEGORY_CONTAINMENT, "part_power", new int[] {8000, 10000}, Lang.localise("gui.qmd.config.containment.part_power.comment"), 0, Integer.MAX_VALUE);
		propertyContainmentPartPower.setLanguageKey("gui.qmd.config.containment.part_power");
		Property propertyContainmentPartHeat = config.get(CATEGORY_CONTAINMENT, "part_heat", new int[] {200, 500}, Lang.localise("gui.qmd.config.containment.part_power.comment"), 0, Integer.MAX_VALUE);
		propertyContainmentPartHeat.setLanguageKey("gui.qmd.config.containment.part_heat");
		Property propertyContainmentMaxTemp = config.get(CATEGORY_CONTAINMENT, "max_temp", 104, Lang.localise("gui.qmd.config.containment.max_temp.comment"), 0, 400);
		propertyContainmentMaxTemp.setLanguageKey("gui.qmd.config.containment.max_temp");
		
		
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
		
		
		Property propertyFissionReflectorEfficiency = config.get(CATEGORY_FISSION, "reflector_efficiency", new double[] {0.75D}, Lang.localise("gui.qmd.config.fission.reflector_efficiency.comment"), 0D, 255D);
		propertyFissionReflectorEfficiency.setLanguageKey("gui.nc.config.fission.reflector_efficiency");
		Property propertyFissionReflectorReflectivity = config.get(CATEGORY_FISSION, "reflector_reflectivity", new double[] {0.75D}, Lang.localise("gui.qmd.config.fission.reflector_reflectivity.comment"), 0D, 1D);
		propertyFissionReflectorReflectivity.setLanguageKey("gui.nc.config.fission.reflector_reflectivity");
		Property propertyFissionShieldHeatPerFlux = config.get(CATEGORY_FISSION, "shield_heat_per_flux", new double[] {15D}, Lang.localise("gui.qmd.config.fission.shield_heat_per_flux.comment"), 0D, 32767D);
		propertyFissionShieldHeatPerFlux.setLanguageKey("gui.qmd.config.fission.shield_heat_per_flux");
		Property propertyFissionShieldEfficiency = config.get(CATEGORY_FISSION, "shield_efficiency", new double[] {1D}, Lang.localise("gui.qmd.config.fission.shield_efficiency.comment"), 0D, 255D);
		propertyFissionShieldEfficiency.setLanguageKey("gui.qmd.config.fission.shield_efficiency");
		
		Property propertyRTGPower = config.get(CATEGORY_OTHER, "rtg_power", new int[] {50}, Lang.localise("gui.qmd.config.other.rtg_power.comment"), 0, Integer.MAX_VALUE);
		propertyFissionReflectorEfficiency.setLanguageKey("gui.qmd.config.other.rtg_power");
		
		Property propertyProcessorPassiveRate = config.get(CATEGORY_OTHER, "processor_passive_rate", new double[] {5D,5D,5D}, Lang.localise("gui.qmd.config.other.processor_passive_rate.comment"), 0D, 4000D);
		propertyProcessorPassiveRate.setLanguageKey("gui.qmd.config.other.processor_passive_rate");
		
		Property propertySourceLifeTime = config.get(CATEGORY_OTHER, "source_life_time", 300, Lang.localise("gui.qmd.config.other.source_life_time.comment"), 1, Integer.MAX_VALUE);
		propertySourceLifeTime.setLanguageKey("gui.qmd.config.other.source_life_time");
		Property propertySourceParticleAmount = config.get(CATEGORY_OTHER, "source_particle_amount", 100, Lang.localise("gui.qmd.config.other.source_particle_amount.comment"), 1, Integer.MAX_VALUE);
		propertySourceParticleAmount.setLanguageKey("gui.qmd.config.other.source_particle_amount");
		
		Property propertyCellLifeTime = config.get(CATEGORY_OTHER, "cell_life_time", 300, Lang.localise("gui.qmd.config.other.cell_life_time.comment"), 1, Integer.MAX_VALUE);
		propertyCellLifeTime.setLanguageKey("gui.qmd.config.other.cell_life_time");
		Property propertyCellPower = config.get(CATEGORY_OTHER, "cell_power", 500, Lang.localise("gui.qmd.config.other.cell_power.comment"), 1, Integer.MAX_VALUE);
		propertyCellPower.setLanguageKey("gui.qmd.config.other.cell_power");
		
		
		
		Property propertyOverrideNCRecipes = config.get(CATEGORY_OTHER, "override_nc_recipes", true, Lang.localise("gui.qmd.config.other.override_nc_recipes.comment"));
		propertyOverrideNCRecipes.setLanguageKey("gui.qmd.config.other.override_nc_recipes");
		

		List<String> propertyOrderProcessors = new ArrayList<String>();
		propertyOrderProcessors.add(propertyProcessorPower.getName());
		propertyOrderProcessors.add(propertyProcessorTime.getName());
		propertyOrderProcessors.add(propertyIrradiatorFuelLifeTime.getName());
		
		config.setCategoryPropertyOrder(CATEGORY_PROCESSORS, propertyOrderProcessors);
		
		
		List<String> propertyOrderAccelerator = new ArrayList<String>();
		propertyOrderAccelerator.add(propertyAcceleratorLinearMinSize.getName());
		propertyOrderAccelerator.add(propertyAcceleratorLinearMaxSize.getName());
		propertyOrderAccelerator.add(propertyAcceleratorRingMinSize.getName());
		propertyOrderAccelerator.add(propertyAcceleratorRingMaxSize.getName());
		
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
		
		propertyOrderAccelerator.add(propertyAcceleratorRingInputEnergy.getName());
		
		config.setCategoryPropertyOrder(CATEGORY_ACCELERATOR, propertyOrderAccelerator);
		
		
		List<String> propertyOrderParticleChamber = new ArrayList<String>();
		propertyOrderParticleChamber.add(propertyTargetChamberPower.getName());
		propertyOrderParticleChamber.add(propertyDecayChamberPower.getName());
		propertyOrderParticleChamber.add(propertyBeamDumpPower.getName());
		propertyOrderParticleChamber.add(propertyDetectorEfficiency.getName());
		propertyOrderParticleChamber.add(propertyDetectorBasePower.getName());
		
		config.setCategoryPropertyOrder(CATEGORY_PARTICLE_CHAMBER, propertyOrderParticleChamber);
		
		List<String> propertyOrderContainment = new ArrayList<String>();
		propertyOrderContainment.add(propertyContainmentPartPower.getName());
		propertyOrderContainment.add(propertyContainmentPartHeat.getName());
		propertyOrderContainment.add(propertyContainmentMaxTemp.getName());
		config.setCategoryPropertyOrder(CATEGORY_CONTAINMENT, propertyOrderContainment);
		
		
		
		List<String> propertyOrderTools = new ArrayList<String>();
		propertyOrderTools.add(propertyToolMiningLevel.getName());
		propertyOrderTools.add(propertyToolDurability.getName());
		propertyOrderTools.add(propertyToolSpeed.getName());
		propertyOrderTools.add(propertyToolAttackDamage.getName());
		propertyOrderTools.add(propertyToolEnchantability.getName());
		config.setCategoryPropertyOrder(CATEGORY_TOOLS, propertyOrderTools);
		
		
		List<String> propertyOrderFission = new ArrayList<String>();
		propertyOrderFission.add(propertyFissionReflectorEfficiency.getName());
		propertyOrderFission.add(propertyFissionReflectorReflectivity.getName());
		propertyOrderFission.add(propertyFissionShieldHeatPerFlux.getName());
		propertyOrderFission.add(propertyFissionShieldEfficiency.getName());
		
		config.setCategoryPropertyOrder(CATEGORY_FISSION, propertyOrderFission);	
		
		
		List<String> propertyOrderFusion = new ArrayList<String>();
		
		config.setCategoryPropertyOrder(CATEGORY_FUSION, propertyOrderFusion);
		
		
		List<String> propertyOrderOther = new ArrayList<String>();
		
		propertyOrderOther.add(propertyRTGPower.getName());
		propertyOrderOther.add(propertyProcessorPassiveRate.getName());
		propertyOrderOther.add(propertySourceLifeTime.getName());
		propertyOrderOther.add(propertySourceParticleAmount.getName());
		propertyOrderOther.add(propertyCellLifeTime.getName());
		propertyOrderOther.add(propertyCellPower.getName());

		propertyOrderOther.add(propertyOverrideNCRecipes.getName());
		config.setCategoryPropertyOrder(CATEGORY_OTHER, propertyOrderOther);
		
		
		if (setFromConfig) 
		{
			processor_power = readIntegerArrayFromConfig(propertyProcessorPower);
			processor_time = readIntegerArrayFromConfig(propertyProcessorTime);
			irradiator_fuel_life_time = propertyIrradiatorFuelLifeTime.getInt();
			
			accelerator_linear_min_size = propertyAcceleratorLinearMinSize.getInt();
			accelerator_linear_max_size = propertyAcceleratorLinearMaxSize.getInt();
			accelerator_ring_min_size = propertyAcceleratorRingMinSize.getInt();
			accelerator_ring_max_size = propertyAcceleratorRingMaxSize.getInt();
			
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
			
			minimium_accelerator_ring_input_particle_energy= propertyAcceleratorRingInputEnergy.getInt();
			
			target_chamber_power = propertyTargetChamberPower.getInt();
			decay_chamber_power = propertyDecayChamberPower.getInt();
			beam_dump_power = propertyDecayChamberPower.getInt();
			detector_efficiency = readDoubleArrayFromConfig(propertyDetectorEfficiency);
			detector_base_power = readIntegerArrayFromConfig(propertyDetectorBasePower);
			
			containment_part_power = readIntegerArrayFromConfig(propertyContainmentPartPower);
			containment_part_heat = readIntegerArrayFromConfig(propertyContainmentPartHeat);
			containment_max_temp = propertyContainmentMaxTemp.getInt();
			
			
			tool_mining_level = readIntegerArrayFromConfig(propertyToolMiningLevel);
			tool_durability = readIntegerArrayFromConfig(propertyToolDurability);
			tool_speed = readDoubleArrayFromConfig(propertyToolSpeed);
			tool_attack_damage = readDoubleArrayFromConfig(propertyToolAttackDamage);
			tool_enchantability = readIntegerArrayFromConfig(propertyToolEnchantability);
			
			
			fission_reflector_efficiency = readDoubleArrayFromConfig(propertyFissionReflectorEfficiency);
			fission_reflector_reflectivity = readDoubleArrayFromConfig(propertyFissionReflectorReflectivity);
			fission_shield_heat_per_flux = readDoubleArrayFromConfig(propertyFissionShieldHeatPerFlux);
			fission_shield_efficiency = readDoubleArrayFromConfig(propertyFissionShieldEfficiency);
			
			
			rtg_power = readIntegerArrayFromConfig(propertyRTGPower);
			processor_passive_rate = readDoubleArrayFromConfig(propertyProcessorPassiveRate);
			
			source_life_time = propertySourceLifeTime.getInt();
			source_particle_amount = propertySourceParticleAmount.getInt();

			cell_life_time = propertyCellLifeTime.getInt();
			cell_power = propertyCellPower.getInt();
			
			override_nc_recipes = propertyOverrideNCRecipes.getBoolean();
			
		}
		propertyProcessorPower.set(processor_power);
		propertyProcessorTime.set(processor_time);
		propertyIrradiatorFuelLifeTime.set(irradiator_fuel_life_time);
		
		propertyAcceleratorLinearMinSize.set(accelerator_linear_min_size);
		propertyAcceleratorLinearMaxSize.set(accelerator_linear_max_size);
		propertyAcceleratorRingMinSize.set(accelerator_ring_min_size);
		propertyAcceleratorRingMaxSize.set(accelerator_ring_max_size);
		
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

		propertyAcceleratorRingInputEnergy.set(minimium_accelerator_ring_input_particle_energy);
		
		propertyTargetChamberPower.set(target_chamber_power);
		propertyDecayChamberPower.set(decay_chamber_power);
		propertyBeamDumpPower.set(beam_dump_power);
		propertyDetectorEfficiency.set(detector_efficiency);
		propertyDetectorBasePower.set(detector_base_power);
		
		propertyContainmentPartPower.set(containment_part_power);
		propertyContainmentPartHeat.set(containment_part_heat);
		propertyContainmentMaxTemp.set(containment_max_temp);
		
		propertyToolMiningLevel.set(tool_mining_level);
		propertyToolDurability.set(tool_durability);
		propertyToolSpeed.set(tool_speed);
		propertyToolAttackDamage.set(tool_attack_damage);
		propertyToolEnchantability.set(tool_enchantability);
		
		propertyFissionReflectorEfficiency.set(fission_reflector_efficiency);
		propertyFissionReflectorReflectivity.set(fission_reflector_reflectivity);
		propertyFissionShieldHeatPerFlux.set(fission_shield_heat_per_flux);
		propertyFissionShieldEfficiency.set(fission_shield_efficiency);
		
		
		propertyRTGPower.set(rtg_power);
		propertyProcessorPassiveRate.set(processor_passive_rate);
		propertySourceLifeTime.set(source_life_time);
		propertySourceParticleAmount.set(source_particle_amount);
		propertyCellLifeTime.set(cell_life_time);
		propertyCellPower.set(cell_power);
		
		propertyOverrideNCRecipes.set(override_nc_recipes);
		
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
				syncFromGui();
			}
		}
	}
	

}
