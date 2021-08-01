package lach_01298.qmd;

import lach_01298.qmd.config.QMDConfig;
import lach_01298.qmd.enums.BlockTypes.CoolerType1;
import lach_01298.qmd.enums.BlockTypes.CoolerType2;
import lach_01298.qmd.enums.BlockTypes.DetectorType;
import lach_01298.qmd.enums.BlockTypes.HeaterType;
import lach_01298.qmd.enums.BlockTypes.MagnetType;
import lach_01298.qmd.enums.BlockTypes.NeutronShieldType;
import lach_01298.qmd.enums.BlockTypes.RFCavityType;
import lach_01298.qmd.enums.ICoolerEnum;
import nc.Global;
import nc.util.InfoHelper;
import nc.util.Lang;
import nc.util.UnitHelper;
import net.minecraft.util.IStringSerializable;

public class QMDInfo
{

	// RF Cavity info
	public static String[][] RFCavityFixedInfo()
	{
		RFCavityType[] values = RFCavityType.values();
		String[][] info = new String[values.length][];
		for (int i = 0; i < values.length; i++) {
			info[i] = new String[] {
					Lang.localise("info." + QMD.MOD_ID + ".rf_cavity.voltage", values[i].getVoltage()),
					Lang.localise("info." + QMD.MOD_ID + ".item.efficiency", Math.round(100D*values[i].getEfficiency()) + "%"),
					Lang.localise("info." + QMD.MOD_ID + ".item.heat", values[i].getHeatGenerated()),
					Lang.localise("info." + QMD.MOD_ID + ".item.power", values[i].getBasePower()),
					Lang.localise("info." + QMD.MOD_ID + ".item.max_temp", values[i].getMaxOperatingTemp())
					};
		}
		return info;
	}
	
	
	
	public static String[][] RFCavityInfo()
	{
		RFCavityType[] values = RFCavityType.values();
		String[][] info = new String[values.length][];
		for (int i = 0; i < values.length; i++) {
			info[i] = InfoHelper.formattedInfo(Lang.localise("tile." + QMD.MOD_ID + ".rf_cavity.desc"));
		}
		return info;
	}




	// Magnet info
	public static String[][] magnetFixedInfo()
	{
		MagnetType[] values = MagnetType.values();
		String[][] info = new String[values.length][];
		for (int i = 0; i < values.length; i++) {
			info[i] = new String[] {
					Lang.localise("info." + QMD.MOD_ID + ".accelerator_magnet.strength", values[i].getStrength()),
					Lang.localise("info." + QMD.MOD_ID + ".item.efficiency", Math.round(100D*values[i].getEfficiency()) + "%"),
					Lang.localise("info." + QMD.MOD_ID + ".item.heat", values[i].getHeatGenerated()),
					Lang.localise("info." + QMD.MOD_ID + ".item.power", values[i].getBasePower()),
					Lang.localise("info." + QMD.MOD_ID + ".item.max_temp", values[i].getMaxOperatingTemp())
					};
		}
		return info;
	}

	public static String[][] magnetInfo()
	{
		MagnetType[] values = MagnetType.values();
		String[][] info = new String[values.length][];
		for (int i = 0; i < values.length; i++) {
			info[i] = InfoHelper.formattedInfo(Lang.localise("tile." + QMD.MOD_ID + ".accelerator_magnet.desc"));
		}
		return info;
	}
	
	
	// Cooler info
	public static String[][] cooler1FixedInfo()
	{
		return coolerFixedInfo(CoolerType1.values());
	}

	public static String[][] cooler2FixedInfo() 
	{
		return coolerFixedInfo(CoolerType2.values());
	}

	private static <T extends Enum<T> & IStringSerializable & ICoolerEnum> String[][] coolerFixedInfo(T[] values) {
		String[][] info = new String[values.length][];
		for (int i = 0; i < values.length; i++) {
			info[i] = new String[] {coolerCoolingRateString(values[i])};
		}
		return info;
	}

	
	private static <T extends Enum<T> & ICoolerEnum> String coolerCoolingRateString(T type) 
	{
		return Lang.localise("tile." + QMD.MOD_ID + ".accelerator.cooler.cooling_rate") + " " + type.getHeatRemoved() + " H/t";
	}
	
	

	public static String[][] cooler1Info() {
		CoolerType1[] values = CoolerType1.values();
		String[][] info = new String[values.length][];
		for (int i = 0; i < values.length; i++) {
			info[i] = InfoHelper.formattedInfo(cooler1InfoString(values[i]));
		}
		return info;
	}
	
	private static String cooler1InfoString(CoolerType1 type) 
	{
		return Lang.localise("tile." + QMD.MOD_ID + ".accelerator.cooler." + type.getName() + ".desc");
	}
	
	public static String[][] cooler2Info() {
		CoolerType2[] values = CoolerType2.values();
		String[][] info = new String[values.length][];
		for (int i = 0; i < values.length; i++) {
			info[i] = InfoHelper.formattedInfo(cooler2InfoString(values[i]));
		}
		return info;
	}
	
	private static String cooler2InfoString(CoolerType2 type) 
	{
		return Lang.localise("tile." + QMD.MOD_ID + ".accelerator.cooler." + type.getName() + ".desc");
	}



	// Magnet info
	public static String[][] detectorFixedInfo()
	{
		DetectorType[] values = DetectorType.values();
		String[][] info = new String[values.length][];
		for (int i = 0; i < values.length; i++) {
			info[i] = new String[] {
					Lang.localise("info." + QMD.MOD_ID + ".particle_chamber.detector.efficiency", Math.round(100D*values[i].getEfficiency()) + "%"),
					Lang.localise("info." + QMD.MOD_ID + ".particle_chamber.detector.power", values[i].getBasePower())
					};
		}
		return info;
	}


	public static String[][] detectorInfo()
	{
		DetectorType[] values = DetectorType.values();
		String[][] info = new String[values.length][];
		for (int i = 0; i < values.length; i++) {
			info[i] = InfoHelper.formattedInfo(detectorInfoString(values[i]));
		}
		return info;
	}


	private static String detectorInfoString(DetectorType type) 
	{
		return Lang.localise("tile." + QMD.MOD_ID + ".particle_chamber.detector." + type.getName() + ".desc");
	}



	public static String BeamlineInfo()
	{	
		return Lang.localise("tile." + QMD.MOD_ID + ".beamline.desc");
	}
	public static String BeamlineFixedlineInfo()
	{	
		return Lang.localise("info." + QMD.MOD_ID + ".beamline.attenuation",QMDConfig.beamAttenuationRate);
	}

	// Fission Neutron Shields

	public static String[][] neutronShieldFixedInfo()
	{
		NeutronShieldType[] values = NeutronShieldType.values();
		String[][] info = new String[values.length][];
		for (int i = 0; i < values.length; i++)
		{
			info[i] = new String[] {
					Lang.localise("info." + Global.MOD_ID + ".fission_shield.heat_per_flux.fixd",
							UnitHelper.prefix(values[i].getHeatPerFlux(), 5, "H/t/N")),
					Lang.localise("info." + Global.MOD_ID + ".fission_shield.efficiency.fixd",
							Math.round(100D * values[i].getEfficiency()) + "%"), };
		}
		return info;
	}

	public static String[][] neutronShieldInfo()
	{
		NeutronShieldType[] values = NeutronShieldType.values();
		String[][] info = new String[values.length][];
		for (int i = 0; i < values.length; i++)
		{
			info[i] = InfoHelper.formattedInfo(Lang.localise("tile." + Global.MOD_ID + ".fission_shield.desc"));
		}
		return info;
	}
	
	
	
	// Heater info
	public static String[][] heaterFixedInfo()
	{
		return heaterFixedInfo(HeaterType.values());
	}
	
	private static <T extends Enum<T> & IStringSerializable & ICoolerEnum> String[][] heaterFixedInfo(T[] values) {
		String[][] info = new String[values.length][];
		for (int i = 0; i < values.length; i++) {
			info[i] = new String[] {coolerCoolingRateString(values[i])};
		}
		return info;
	}
	
	
	
}
