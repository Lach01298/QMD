package lach_01298.qmd.item;

import lach_01298.qmd.MaterialEnums;
import lach_01298.qmd.QMD;
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
	
	
	
	
	
	
	
	
	
	public static void init()
	{
		dust = withName(new NCItemMeta(MaterialEnums.DustType.class), "dust");
		ingot = withName(new NCItemMeta(MaterialEnums.IngotType.class), "ingot");
		ingotAlloy = withName(new NCItemMeta(MaterialEnums.IngotAlloyType.class), "ingot_alloy");
		
		
		
		
	}

	public static void register()
	{
		registerItem(dust, NCTabs.MATERIAL);
		registerItem(ingot, NCTabs.MATERIAL);
		registerItem(ingotAlloy, NCTabs.MATERIAL);
		
		
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
	
	
	
	
	
	
	
	

