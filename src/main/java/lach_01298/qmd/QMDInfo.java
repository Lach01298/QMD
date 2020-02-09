package lach_01298.qmd;

import lach_01298.qmd.config.QMDConfig;
import lach_01298.qmd.enums.EnumTypes;
import lach_01298.qmd.enums.ICoolerEnum;
import lach_01298.qmd.enums.EnumTypes.CoolerType1;
import nc.Global;
import nc.enumm.IHeatSinkEnum;
import nc.enumm.MetaEnums;
import nc.util.InfoHelper;
import nc.util.Lang;
import net.minecraft.util.IStringSerializable;

public class QMDInfo
{

	// RF Cavity info
	public static String[][] RFCavityFixedInfo()
	{
		EnumTypes.RFCavityType[] values = EnumTypes.RFCavityType.values();
		String[][] info = new String[values.length][];
		for (int i = 0; i < values.length; i++) {
			info[i] = new String[] {
					Lang.localise("info." + QMD.MOD_ID + ".rf_cavity.voltage", values[i].getVoltage()),
					Lang.localise("info." + QMD.MOD_ID + ".rf_cavity.efficiency", Math.round(100D*values[i].getEfficiency()) + "%"),
					Lang.localise("info." + QMD.MOD_ID + ".rf_cavity.heat", values[i].getHeatGenerated()),
					Lang.localise("info." + QMD.MOD_ID + ".rf_cavity.power", values[i].getBasePower())
					};
		}
		return info;
	}
	
	
	
	public static String[][] RFCavityInfo()
	{
		EnumTypes.RFCavityType[] values = EnumTypes.RFCavityType.values();
		String[][] info = new String[values.length][];
		for (int i = 0; i < values.length; i++) {
			info[i] = InfoHelper.formattedInfo(Lang.localise("tile." + QMD.MOD_ID + ".rf_cavity.desc"));
		}
		return info;
	}




	// Magnet info
	public static String[][] magnetFixedInfo()
	{
		EnumTypes.MagnetType[] values = EnumTypes.MagnetType.values();
		String[][] info = new String[values.length][];
		for (int i = 0; i < values.length; i++) {
			info[i] = new String[] {
					Lang.localise("info." + QMD.MOD_ID + ".accelerator_magnet.strength", values[i].getStrength()),
					Lang.localise("info." + QMD.MOD_ID + ".accelerator_magnet.efficiency", Math.round(100D*values[i].getEfficiency()) + "%"),
					Lang.localise("info." + QMD.MOD_ID + ".accelerator_magnet.heat", values[i].getHeatGenerated()),
					Lang.localise("info." + QMD.MOD_ID + ".accelerator_magnet.power", values[i].getBasePower())
					};
		}
		return info;
	}

	public static String[][] magnetInfo()
	{
		EnumTypes.MagnetType[] values = EnumTypes.MagnetType.values();
		String[][] info = new String[values.length][];
		for (int i = 0; i < values.length; i++) {
			info[i] = InfoHelper.formattedInfo(Lang.localise("tile." + QMD.MOD_ID + ".accelerator_magnet.desc"));
		}
		return info;
	}
	
	
	// Cooler info
	public static String[][] cooler1FixedInfo()
	{
		return coolerFixedInfo(EnumTypes.CoolerType1.values());
	}

	public static String[][] cooler2FixedInfo() 
	{
		return coolerFixedInfo(EnumTypes.CoolerType2.values());
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
		EnumTypes.CoolerType1[] values = EnumTypes.CoolerType1.values();
		String[][] info = new String[values.length][];
		for (int i = 0; i < values.length; i++) {
			info[i] = InfoHelper.formattedInfo(cooler1InfoString(values[i]));
		}
		return info;
	}
	
	private static String cooler1InfoString(EnumTypes.CoolerType1 type) 
	{
		return Lang.localise("tile." + QMD.MOD_ID + ".accelerator.cooler." + type.getName() + ".desc");
	}
	
	public static String[][] cooler2Info() {
		EnumTypes.CoolerType2[] values = EnumTypes.CoolerType2.values();
		String[][] info = new String[values.length][];
		for (int i = 0; i < values.length; i++) {
			info[i] = InfoHelper.formattedInfo(cooler2InfoString(values[i]));
		}
		return info;
	}
	
	private static String cooler2InfoString(EnumTypes.CoolerType2 type) 
	{
		return Lang.localise("tile." + QMD.MOD_ID + ".accelerator.cooler." + type.getName() + ".desc");
	}



	// Magnet info
	public static String[][] detectorFixedInfo()
	{
		EnumTypes.DetectorType[] values = EnumTypes.DetectorType.values();
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
		EnumTypes.DetectorType[] values = EnumTypes.DetectorType.values();
		String[][] info = new String[values.length][];
		for (int i = 0; i < values.length; i++) {
			info[i] = InfoHelper.formattedInfo(detectorInfoString(values[i]));
		}
		return info;
	}


	private static String detectorInfoString(EnumTypes.DetectorType type) 
	{
		return Lang.localise("tile." + QMD.MOD_ID + ".particle_chamber.detector." + type.getName() + ".desc");
	}



	public static String BeamlineInfo()
	{	
		return Lang.localise("tile." + QMD.MOD_ID + ".beamline.desc");
	}
	public static String BeamlineFixedlineInfo()
	{	
		return Lang.localise("info." + QMD.MOD_ID + ".beamline.attenuation",Units.getSIFormat(QMDConfig.beamAttenuationRate,"lu/Block"));
	}


	
}
