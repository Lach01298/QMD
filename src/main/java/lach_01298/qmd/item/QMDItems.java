package lach_01298.qmd.item;

import lach_01298.qmd.QMD;
import lach_01298.qmd.enums.MaterialEnums;
import lach_01298.qmd.particle.Particles;
import lach_01298.qmd.tab.QMDTabs;
import nc.Global;
import nc.NCInfo;
import nc.enumm.MetaEnums;
import nc.item.IInfoItem;
import nc.item.NCItemMeta;
import nc.tab.NCTabs;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class QMDItems
{

	public static Item dust;
	public static Item ingot;
	public static Item ingotAlloy;
	public static Item tungsten_filament;
	public static Item canister;
	public static Item canister_hydrogen;
	public static Item canister_deuterium;
	public static Item canister_helium;
	public static Item canister_diborane;
	public static Item source_sodium_22;
	
	public static Item isotope;
	public static Item part;
	public static Item semiconductor;
	public static Item chemicalDust;
	
	
	
	
	public static void init()
	{
		dust = withName(new NCItemMeta(MaterialEnums.DustType.class), "dust");
		ingot = withName(new NCItemMeta(MaterialEnums.IngotType.class), "ingot");
		ingotAlloy = withName(new NCItemMeta(MaterialEnums.IngotAlloyType.class), "ingot_alloy");
		tungsten_filament = withName(new ItemBrakeable(300),"tungsten_filament");
		canister = withName(new ItemBrakeable(300),"canister");
		canister_hydrogen = withName(new ItemBrakeable(300),"canister_hydrogen");
		canister_deuterium = withName(new ItemBrakeable(300),"canister_deuterium");
		canister_helium = withName(new ItemBrakeable(300),"canister_helium");
		canister_diborane = withName(new ItemBrakeable(300),"canister_diborane");
		source_sodium_22 = withName(new ItemBrakeable(300),"source_sodium_22");
		isotope = withName(new NCItemMeta(MaterialEnums.IsotopeType.class), "isotope");
		part =  withName(new NCItemMeta(MaterialEnums.PartType.class), "part");
		semiconductor =  withName(new NCItemMeta(MaterialEnums.SemiconductorType.class), "semiconductor");
		chemicalDust =  withName(new NCItemMeta(MaterialEnums.ChemicalDustType.class), "chemical_dust");
	}

	public static void register()
	{
		registerItem(dust, QMDTabs.ITEMS);
		registerItem(ingot, QMDTabs.ITEMS);
		registerItem(ingotAlloy, QMDTabs.ITEMS);
		
		registerItem(tungsten_filament, QMDTabs.ITEMS);
		registerItem(canister, QMDTabs.ITEMS);
		registerItem(canister_hydrogen, QMDTabs.ITEMS);
		registerItem(canister_deuterium, QMDTabs.ITEMS);
		registerItem(canister_helium, QMDTabs.ITEMS);
		registerItem(canister_diborane, QMDTabs.ITEMS);
		registerItem(source_sodium_22, QMDTabs.ITEMS);
		
		registerItem(isotope, QMDTabs.ITEMS);
		
		registerItem(part, QMDTabs.ITEMS);
		registerItem(semiconductor, QMDTabs.ITEMS);
		registerItem(chemicalDust, QMDTabs.ITEMS);
		
		
	}

	public static void registerRenders() 
	{
		for(int i = 0; i < MaterialEnums.DustType.values().length; i++) 
		{
			registerRender(dust, i, MaterialEnums.DustType.values()[i].getName());
		}
		
		for(int i = 0; i < MaterialEnums.IngotType.values().length; i++) 
		{
			registerRender(ingot, i, MaterialEnums.IngotType.values()[i].getName());
		}
		
		for(int i = 0; i < MaterialEnums.IngotAlloyType.values().length; i++) 
		{
			registerRender(ingotAlloy, i, MaterialEnums.IngotAlloyType.values()[i].getName());
		}
		
		registerRender(tungsten_filament);
		registerRender(canister);
		registerRender(canister_hydrogen);
		registerRender(canister_deuterium);
		registerRender(canister_helium);
		registerRender(canister_diborane);
		registerRender(source_sodium_22);
		
		for(int i = 0; i < MaterialEnums.IsotopeType.values().length; i++) 
		{
			registerRender(isotope, i, MaterialEnums.IsotopeType.values()[i].getName());
		}
		
		for (int i = 0; i < MaterialEnums.PartType.values().length; i++)
		{
			registerRender(part, i, MaterialEnums.PartType.values()[i].getName());
		}
		
		for (int i = 0; i < MaterialEnums.SemiconductorType.values().length; i++)
		{
			registerRender(semiconductor, i, MaterialEnums.SemiconductorType.values()[i].getName());
		}
		for (int i = 0; i < MaterialEnums.ChemicalDustType.values().length; i++)
		{
			registerRender(chemicalDust, i, MaterialEnums.ChemicalDustType.values()[i].getName());
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
}
	
	
	
	
	
	
	
	

