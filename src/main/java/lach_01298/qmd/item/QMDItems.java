package lach_01298.qmd.item;

import lach_01298.qmd.MaterialEnums;
import lach_01298.qmd.QMD;
import lach_01298.qmd.particle.Particles;
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
	public static Item canister_Hydrogen;
	public static Item canister_Deuterium;
	public static Item canister_Helium;
	public static Item sodium_22_source;
	
	
	
	
	
	
	
	public static void init()
	{
		dust = withName(new NCItemMeta(MaterialEnums.DustType.class), "dust");
		ingot = withName(new NCItemMeta(MaterialEnums.IngotType.class), "ingot");
		ingotAlloy = withName(new NCItemMeta(MaterialEnums.IngotAlloyType.class), "ingot_alloy");
		tungsten_filament = withName(new ItemBrakeable(100),"tungsten_filament");
		canister_Hydrogen = withName(new ItemBrakeable(100),"canister_Hydrogen");
		canister_Deuterium = withName(new ItemBrakeable(100),"canister_Deuterium");
		canister_Helium = withName(new ItemBrakeable(100),"canister_Helium");
		sodium_22_source = withName(new ItemBrakeable(100),"sodium_22_source");
		
		
		
	}

	public static void register()
	{
		registerItem(dust, NCTabs.MATERIAL);
		registerItem(ingot, NCTabs.MATERIAL);
		registerItem(ingotAlloy, NCTabs.MATERIAL);
		
		registerItem(tungsten_filament, NCTabs.MISC);
		registerItem(canister_Hydrogen, NCTabs.MISC);
		registerItem(canister_Deuterium, NCTabs.MISC);
		registerItem(canister_Helium, NCTabs.MISC);
		registerItem(sodium_22_source, NCTabs.MISC);
	
		
		
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
		registerRender(canister_Hydrogen);
		registerRender(canister_Deuterium);
		registerRender(canister_Helium);
		registerRender(sodium_22_source);
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
	
	
	
	
	
	
	
	

