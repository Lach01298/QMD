package lach_01298.qmd.item;

import lach_01298.qmd.QMD;
import lach_01298.qmd.config.QMDConfig;
import lach_01298.qmd.tab.QMDTabs;
import nc.item.IInfoItem;
import nc.radiation.RadArmor;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.RecipeItemHelper;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.*;
import java.util.Map.Entry;

import static lach_01298.qmd.config.QMDConfig.*;

public class QMDArmour
{
	
	public static final ArmorMaterial HEV = EnumHelper.addArmorMaterial("hev", QMD.MOD_ID + ":" + "hev", 0, new int[] {hev_armour[4], hev_armour[5], hev_armour[6], hev_armour[7]}, 0, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, (float) hev_toughness[1]);
	
	public static Item helm_hev;
	public static Item chest_hev;
	public static Item legs_hev;
	public static Item boots_hev;
	
	
	public static void init()
	{
		helm_hev = withName(new ItemHEVSuit(HEV, 1, EntityEquipmentSlot.HEAD, 0.2D, TextFormatting.AQUA), "helm_hev");
		chest_hev = withName(new ItemHEVSuit(HEV, 1, EntityEquipmentSlot.CHEST, 0.4D, TextFormatting.AQUA), "chest_hev");
		legs_hev = withName(new ItemHEVSuit(HEV, 2, EntityEquipmentSlot.LEGS, 0.2D, TextFormatting.AQUA), "legs_hev");
		boots_hev = withName(new ItemHEVSuit(HEV, 1, EntityEquipmentSlot.FEET, 0.2D, TextFormatting.AQUA), "boots_hev");
	}
	
	public static void register()
	{
		registerItem(helm_hev, QMDTabs.ITEMS);
		registerItem(chest_hev, QMDTabs.ITEMS);
		registerItem(legs_hev, QMDTabs.ITEMS);
		registerItem(boots_hev, QMDTabs.ITEMS);
	}
	
	public static void registerRenders()
	{
		registerRender(helm_hev);
		registerRender(chest_hev);
		registerRender(legs_hev);
		registerRender(boots_hev);
	}
	
	public static void addRadResistance()
	{
		
		
		Map<ItemStack,Double> radAmours =  new HashMap<ItemStack,Double>();
		radAmours.put(new ItemStack(helm_hev), QMDConfig.hev_rad_res[0]);
		radAmours.put(new ItemStack(chest_hev), QMDConfig.hev_rad_res[1]);
		radAmours.put(new ItemStack(legs_hev), QMDConfig.hev_rad_res[2]);
		radAmours.put(new ItemStack(boots_hev), QMDConfig.hev_rad_res[3]);
		
		
		for(Entry<ItemStack, Double> entry : radAmours.entrySet())
		{
			int packed = RecipeItemHelper.pack(entry.getKey());
			double resistance = entry.getValue();
			
			RadArmor.ARMOR_RAD_RESISTANCE_MAP.put(packed,resistance);
		}
		
		
		
		
	}
	
	public static void blacklistShielding()
	{
		List<ItemStack> radAmours =  new ArrayList<ItemStack>();
		radAmours.add(new ItemStack(helm_hev));
		radAmours.add(new ItemStack(chest_hev));
		radAmours.add(new ItemStack(legs_hev));
		radAmours.add(new ItemStack(boots_hev));
		
		for(ItemStack stack : radAmours)
		{
			int packed = RecipeItemHelper.pack(stack);
			RadArmor.ARMOR_STACK_SHIELDING_BLACKLIST.add(packed);
		}
	}
	
	
	
	
	
	public static <T extends Item & IInfoItem> Item withName(T item, String name)
	{
		item.setTranslationKey(QMD.MOD_ID + "." + name).setRegistryName(new ResourceLocation(QMD.MOD_ID, name));
		item.setInfo();
		return item;
	}
	
	public static void registerItem(Item item, CreativeTabs tab)
	{
		item.setCreativeTab(tab);
		ForgeRegistries.ITEMS.register(item);
	}
	
	public static void registerRender(Item item)
	{
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}

}
