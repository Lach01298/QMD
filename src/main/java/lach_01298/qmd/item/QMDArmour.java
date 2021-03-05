package lach_01298.qmd.item;

import static lach_01298.qmd.config.QMDConfig.armor_durability;
import static lach_01298.qmd.config.QMDConfig.armor_enchantability;
import static lach_01298.qmd.config.QMDConfig.armor_hev;
import static lach_01298.qmd.config.QMDConfig.armor_toughness;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import lach_01298.qmd.QMD;
import lach_01298.qmd.config.QMDConfig;
import lach_01298.qmd.enums.MaterialTypes.IngotAlloyType;
import lach_01298.qmd.tab.QMDTabs;
import nc.item.IInfoItem;
import nc.radiation.RadArmor;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.RecipeItemHelper;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class QMDArmour
{

	public static final ArmorMaterial HEV = armorMaterial("hev", 0, armor_hev, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, new ItemStack(QMDItems.ingotAlloy, 1, IngotAlloyType.SUPER_ALLOY.getID()));
	
	
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
		radAmours.put(new ItemStack(helm_hev), QMDConfig.rad_res_hev[0]);
		radAmours.put(new ItemStack(chest_hev), QMDConfig.rad_res_hev[1]);
		radAmours.put(new ItemStack(legs_hev), QMDConfig.rad_res_hev[2]);
		radAmours.put(new ItemStack(boots_hev), QMDConfig.rad_res_hev[3]);
		
		
		for(Entry<ItemStack, Double> entry : radAmours.entrySet())
		{
			int packed = RecipeItemHelper.pack(entry.getKey());
			double resistance = entry.getValue();
			RadArmor.ARMOR_RAD_RESISTANCE_MAP.put(packed,resistance);
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
	
	
	public static ArmorMaterial armorMaterial(String name, int id, int[] durability, SoundEvent equipSound, ItemStack repairStack) {
		return EnumHelper.addArmorMaterial(name, QMD.MOD_ID + ":" + name, armor_durability[id], new int[] {durability[0], durability[1], durability[2], durability[3]}, armor_enchantability[id], equipSound, (float) armor_toughness[id]).setRepairItem(repairStack);
	}
}
