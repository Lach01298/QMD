package lach_01298.qmd.item;

import lach_01298.qmd.QMD;
import lach_01298.qmd.QMDInfo;
import lach_01298.qmd.config.QMDConfig;
import lach_01298.qmd.enums.MaterialTypes.CanisterType;
import lach_01298.qmd.enums.MaterialTypes.CellType;
import lach_01298.qmd.enums.MaterialTypes.ChemicalDustType;
import lach_01298.qmd.enums.MaterialTypes.CoperniciumDepletedFuelType;
import lach_01298.qmd.enums.MaterialTypes.CoperniciumFuelType;
import lach_01298.qmd.enums.MaterialTypes.CoperniciumPelletType;
import lach_01298.qmd.enums.MaterialTypes.CoperniciumType;
import lach_01298.qmd.enums.MaterialTypes.DustType;
import lach_01298.qmd.enums.MaterialTypes.DustType2;
import lach_01298.qmd.enums.MaterialTypes.FissionWasteType;
import lach_01298.qmd.enums.MaterialTypes.IngotAlloyType;
import lach_01298.qmd.enums.MaterialTypes.IngotType;
import lach_01298.qmd.enums.MaterialTypes.IngotType2;
import lach_01298.qmd.enums.MaterialTypes.IsotopeType;
import lach_01298.qmd.enums.MaterialTypes.LuminousPaintType;
import lach_01298.qmd.enums.MaterialTypes.PartType;
import lach_01298.qmd.enums.MaterialTypes.SemiconductorType;
import lach_01298.qmd.enums.MaterialTypes.SourceType;
import lach_01298.qmd.enums.MaterialTypes.SpallationWasteType;
import lach_01298.qmd.enums.MaterialTypes.SpallationWasteType2;
import lach_01298.qmd.tab.QMDTabs;
import nc.item.IInfoItem;
import nc.item.ItemFissionFuel;
import nc.item.NCItemFood;
import nc.item.NCItemMeta;
import nc.item.tool.NCAxe;
import nc.item.tool.NCHoe;
import nc.item.tool.NCPickaxe;
import nc.item.tool.NCShovel;
import nc.item.tool.NCSword;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class QMDItems
{

	public static Item dust;
	public static Item dust2;
	public static Item ingot;
	public static Item ingot2;
	public static Item ingotAlloy;
	public static Item isotope;
	public static Item part;
	public static Item semiconductor;
	public static Item chemicalDust;
	public static Item fissionWaste;
	public static Item spallationWaste;
	public static Item spallationWaste2;
	
	public static Item canister;
	public static Item source;
	
	
	
	public static ToolMaterial TUNGSTEN_CARBIDE;
	public static Item sword_tungsten_carbide;
	public static Item pickaxe_tungsten_carbide;
	public static Item shovel_tungsten_carbide;
	public static Item axe_tungsten_carbide;
	public static Item hoe_tungsten_carbide;
	
	public static Item cell;
	
	public static Item flesh;
	public static Item potassiumIodineTablet;
	public static Item luminousPaint;
	
	public static Item leptonCannon;
	public static Item gluonGun;
	public static Item antimatterLauncher;
	
	public static Item beamMeter;
	public static Item basic_drill;
	public static Item advanced_drill;
	
	public static Item copernicium;
	public static Item pellet_copernicium;
	public static Item fuel_copernicium;
	public static Item depleted_fuel_copernicium;
	
	public static void init()
	{
		dust = withName(new NCItemMeta(DustType.class), "dust");
		dust2 = withName(new NCItemMeta(DustType2.class), "dust2");
		ingot = withName(new NCItemMeta(IngotType.class), "ingot");
		ingot2 = withName(new NCItemMeta(IngotType2.class), "ingot2");
		ingotAlloy = withName(new NCItemMeta(IngotAlloyType.class), "ingot_alloy");
		
		canister = withName(new ItemCanister(),"canister");
		source = withName(new ItemSource(),"source");
		
		
		isotope = withName(new NCItemMeta(IsotopeType.class), "isotope");
		part =  withName(new NCItemMeta(PartType.class), "part");
		semiconductor =  withName(new NCItemMeta(SemiconductorType.class), "semiconductor");
		chemicalDust =  withName(new NCItemMeta(ChemicalDustType.class), "chemical_dust");
		fissionWaste =  withName(new NCItemMeta(FissionWasteType.class), "waste_fission");
		spallationWaste =  withName(new NCItemMeta(SpallationWasteType.class), "waste_spallation");
		spallationWaste2 =  withName(new NCItemMeta(SpallationWasteType2.class), "waste_spallation2");
		
		TUNGSTEN_CARBIDE = toolMaterial("tungsten_carbide", 0, new ItemStack(ingotAlloy, 1, IngotAlloyType.TUNGSTEN_CARBIDE.getID()));
		
		sword_tungsten_carbide = withName(new NCSword(TUNGSTEN_CARBIDE, TextFormatting.GRAY), "sword_tungsten_carbide");
		pickaxe_tungsten_carbide = withName(new NCPickaxe(TUNGSTEN_CARBIDE, TextFormatting.GRAY), "pickaxe_tungsten_carbide");
		shovel_tungsten_carbide = withName(new NCShovel(TUNGSTEN_CARBIDE, TextFormatting.GRAY), "shovel_tungsten_carbide");
		axe_tungsten_carbide = withName(new NCAxe(TUNGSTEN_CARBIDE, TextFormatting.GRAY), "axe_tungsten_carbide");
		hoe_tungsten_carbide = withName(new NCHoe(TUNGSTEN_CARBIDE, TextFormatting.GRAY), "hoe_tungsten_carbide");
		
		flesh = withName(new NCItemFood(4, 0.1F, false, new PotionEffect[] {}), "flesh");

		potassiumIodineTablet = withName(new ItemTablet(new PotionEffect[] {}), "potassium_iodine_tablet");
		luminousPaint = withName(new ItemLuminousPaint(), "luminous_paint");
		
		leptonCannon =  withName(new ItemLeptonCannon(),"lepton_cannon");
		gluonGun =  withName(new ItemGluonGun(),"gluon_gun");
		antimatterLauncher =  withName(new ItemAntimatterLauncher(),"antimatter_launcher");
		
		beamMeter = withName(new ItemBeamMeter(),"beam_meter");
		basic_drill = withName(new ItemDrill(QMDConfig.drill_radius[0], QMDConfig.drill_energy_capacity[0], QMDConfig.tool_mining_level[1], (float)QMDConfig.tool_speed[1],QMDInfo.drillInfo(0)),"drill_basic");
		advanced_drill = withName(new ItemDrill(QMDConfig.drill_radius[1], QMDConfig.drill_energy_capacity[1], QMDConfig.tool_mining_level[2], (float)QMDConfig.tool_speed[2], QMDInfo.drillInfo(1)),"drill_advanced");

		
		cell = withName(new ItemCell(),"cell");
		
		copernicium = withName(new NCItemMeta(CoperniciumType.class), "copernicium");
		pellet_copernicium = withName(new NCItemMeta(CoperniciumPelletType.class), "pellet_copernicium");
		fuel_copernicium = withName(new ItemFissionFuel(CoperniciumFuelType.class), "fuel_copernicium");
		depleted_fuel_copernicium = withName(new NCItemMeta(CoperniciumDepletedFuelType.class), "depleted_fuel_copernicium");
		
		
	}

	public static void register()
	{
		registerItem(dust, QMDTabs.ITEMS);
		registerItem(dust2, QMDTabs.ITEMS);
		registerItem(ingot, QMDTabs.ITEMS);
		registerItem(ingot2, QMDTabs.ITEMS);
		registerItem(ingotAlloy, QMDTabs.ITEMS);
		
		registerItem(canister, QMDTabs.ITEMS);
		registerItem(source, QMDTabs.ITEMS);

		
		registerItem(isotope, QMDTabs.ITEMS);
		
		registerItem(part, QMDTabs.ITEMS);
		registerItem(semiconductor, QMDTabs.ITEMS);
		registerItem(chemicalDust, QMDTabs.ITEMS);
		registerItem(fissionWaste, QMDTabs.ITEMS);
		registerItem(spallationWaste, QMDTabs.ITEMS);
		registerItem(spallationWaste2, QMDTabs.ITEMS);
		
		
		registerItem(sword_tungsten_carbide,QMDTabs.ITEMS);
		registerItem(pickaxe_tungsten_carbide,QMDTabs.ITEMS);
		registerItem(shovel_tungsten_carbide,QMDTabs.ITEMS);
		registerItem(axe_tungsten_carbide,QMDTabs.ITEMS);
		registerItem(hoe_tungsten_carbide,QMDTabs.ITEMS);
		
		registerItem(flesh,QMDTabs.ITEMS);
		registerItem(potassiumIodineTablet,QMDTabs.ITEMS);
		registerItem(luminousPaint,QMDTabs.ITEMS);
		registerItem(cell,QMDTabs.ITEMS);
		registerItem(leptonCannon,QMDTabs.ITEMS);
		registerItem(gluonGun,QMDTabs.ITEMS);
		registerItem(antimatterLauncher,QMDTabs.ITEMS);
		registerItem(beamMeter,QMDTabs.ITEMS);
		registerItem(basic_drill,QMDTabs.ITEMS);
		registerItem(advanced_drill,QMDTabs.ITEMS);
		
		registerItem(copernicium,QMDTabs.ITEMS);
		registerItem(pellet_copernicium,QMDTabs.ITEMS);
		registerItem(fuel_copernicium,QMDTabs.ITEMS);
		registerItem(depleted_fuel_copernicium,QMDTabs.ITEMS);
		
		
	}

	public static void registerRenders() 
	{
		for(int i = 0; i < DustType.values().length; i++) 
		{
			registerRender(dust, i, DustType.values()[i].getName());
		}
		
		for(int i = 0; i < DustType2.values().length; i++) 
		{
			registerRender(dust2, i, DustType2.values()[i].getName());
		}
		
		for(int i = 0; i < IngotType.values().length; i++) 
		{
			registerRender(ingot, i, IngotType.values()[i].getName());
		}
		
		for(int i = 0; i < IngotType2.values().length; i++) 
		{
			registerRender(ingot2, i, IngotType2.values()[i].getName());
		}
		
		
		for(int i = 0; i < IngotAlloyType.values().length; i++) 
		{
			registerRender(ingotAlloy, i, IngotAlloyType.values()[i].getName());
		}
		
		for(int i = 0; i < IsotopeType.values().length; i++) 
		{
			registerRender(isotope, i, IsotopeType.values()[i].getName());
		}
		
		for (int i = 0; i < PartType.values().length; i++)
		{
			registerRender(part, i, PartType.values()[i].getName());
		}
		
		for (int i = 0; i < SemiconductorType.values().length; i++)
		{
			registerRender(semiconductor, i, SemiconductorType.values()[i].getName());
		}
		for (int i = 0; i < ChemicalDustType.values().length; i++)
		{
			registerRender(chemicalDust, i, ChemicalDustType.values()[i].getName());
		}
		
		for (int i = 0; i < FissionWasteType.values().length; i++)
		{
			registerRender(fissionWaste, i, FissionWasteType.values()[i].getName());
		}
		
		for (int i = 0; i < SpallationWasteType.values().length; i++)
		{
			registerRender(spallationWaste, i, SpallationWasteType.values()[i].getName());
		}
		
		for (int i = 0; i < SpallationWasteType2.values().length; i++)
		{
			registerRender(spallationWaste2, i, SpallationWasteType2.values()[i].getName());
		}
		
		
		
		for (int i = 0; i < CanisterType.values().length; i++)
		{
			registerRender(canister, i, CanisterType.values()[i].getName());
		}
		
		for (int i = 0; i < SourceType.values().length; i++)
		{
			registerRender(source, i, SourceType.values()[i].getName());
		}
		
		
		
		registerRender(sword_tungsten_carbide);
		registerRender(pickaxe_tungsten_carbide);
		registerRender(shovel_tungsten_carbide);
		registerRender(axe_tungsten_carbide);
		registerRender(hoe_tungsten_carbide);
		
		registerRender(flesh);
		registerRender(potassiumIodineTablet);
		
		for (int i = 0; i < LuminousPaintType.values().length; i++)
		{
			registerRender(luminousPaint, i, LuminousPaintType.values()[i].getName());
		}
		
		registerRender(leptonCannon);
		registerRender(gluonGun);
		registerRender(antimatterLauncher);
		registerRender(beamMeter);
		registerRender(basic_drill);
		registerRender(advanced_drill);
		
		
		for (int i = 0; i < CellType.values().length; i++)
		{
			registerRender(cell, i, CellType.values()[i].getName());
		}
		
		for (int i = 0; i < CoperniciumType.values().length; i++) {
			registerRender(copernicium, i, CoperniciumType.values()[i].getName());
		}
		for (int i = 0; i < CoperniciumPelletType.values().length; i++) {
			registerRender(pellet_copernicium, i, CoperniciumPelletType.values()[i].getName());
		}
		for (int i = 0; i < CoperniciumFuelType.values().length; i++) {
			registerRender(fuel_copernicium, i, CoperniciumFuelType.values()[i].getName());
		}
		for (int i = 0; i < CoperniciumDepletedFuelType.values().length; i++) {
			registerRender(depleted_fuel_copernicium, i, CoperniciumDepletedFuelType.values()[i].getName());
		}
	}
	

	
	public static <T extends Item & IInfoItem> Item withName(T item, String name)
	{
		item.setTranslationKey(QMD.MOD_ID + "." + name).setRegistryName(new ResourceLocation(QMD.MOD_ID, name));
		item.setInfo();
		return item;
	}

	public static String infoLine(String name)
	{
		return "item." + QMD.MOD_ID + "." + name + ".desc";
	}

	public static void registerItem(Item item, CreativeTabs tab)
	{
		item.setCreativeTab(tab);
		ForgeRegistries.ITEMS.register(item);
	}

	public static void registerRender(Item item)
	{
		ModelLoader.setCustomModelResourceLocation(item, 0,new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}

	public static void registerRender(Item item, int meta, String type)
	{
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(new ResourceLocation(QMD.MOD_ID, "items/" + item.getRegistryName().getPath()), "type=" + type));
	}

	public static ToolMaterial toolMaterial(String name, int id, ItemStack repairStack) 
	{
		return EnumHelper.addToolMaterial(QMD.MOD_ID + ":" + name, QMDConfig.tool_mining_level[id], QMDConfig.tool_durability[id], (float) QMDConfig.tool_speed[id], (float) QMDConfig.tool_attack_damage[id], QMDConfig.tool_enchantability[id]).setRepairItem(repairStack);
	}



}
	
	
	
	
	
	
	
	

