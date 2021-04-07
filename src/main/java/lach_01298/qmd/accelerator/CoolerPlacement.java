package lach_01298.qmd.accelerator;


import static lach_01298.qmd.config.QMDConfig.cooler_rule;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import javax.annotation.Nullable;

import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import lach_01298.qmd.accelerator.tile.IAcceleratorPart;
import lach_01298.qmd.accelerator.tile.TileAcceleratorBeam;
import lach_01298.qmd.accelerator.tile.TileAcceleratorCooler;
import lach_01298.qmd.accelerator.tile.TileAcceleratorMagnet;
import lach_01298.qmd.accelerator.tile.TileAcceleratorRFCavity;
import lach_01298.qmd.accelerator.tile.TileAcceleratorYoke;
import lach_01298.qmd.block.QMDBlocks;
import lach_01298.qmd.enums.BlockTypes.CoolerType1;
import lach_01298.qmd.enums.BlockTypes.CoolerType2;
import nc.multiblock.PlacementRule;
import nc.multiblock.PlacementRule.AdjacencyType;
import nc.multiblock.PlacementRule.CountType;
import nc.multiblock.PlacementRule.PlacementMap;
import nc.util.I18nHelper;
import nc.util.Lang;
import nc.util.StringHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class CoolerPlacement
{

	/** List of all defined rule parsers. Earlier entries are prioritised! */
	public static final List<PlacementRule.RuleParser<IAcceleratorPart>> RULE_PARSER_LIST = new LinkedList<>();
	
	/** Map of all placement rule IDs to unparsed rule strings, used for ordered iterations. */
	public static final Object2ObjectMap<String, String> RULE_MAP_RAW = new Object2ObjectArrayMap<>();
	
	/** Map of all defined placement rules. */
	public static final Object2ObjectMap<String, PlacementRule<IAcceleratorPart>> RULE_MAP = new PlacementMap<>();
	
	/** List of all defined tooltip builders. Earlier entries are prioritised! */
	public static final List<PlacementRule.TooltipBuilder<IAcceleratorPart>> TOOLTIP_BUILDER_LIST = new LinkedList<>();
	
	public static PlacementRule.RecipeHandler recipe_handler;
	
	/** Map of all localised tooltips. */
	public static final Object2ObjectMap<String, String> TOOLTIP_MAP = new Object2ObjectOpenHashMap<>();
	
	public static void preInit() {
		RULE_PARSER_LIST.add(new DefaultRuleParser());
		
		TOOLTIP_BUILDER_LIST.add(new DefaultTooltipBuilder());
	}
	
	public static void init() {
		recipe_handler = new RecipeHandler();
		
		RULE_MAP.put("", new PlacementRule.Or<>(new ArrayList<>()));
		
		addRule("water_cooler", cooler_rule[CoolerType1.WATER.getID()], new ItemStack(QMDBlocks.acceleratorCooler1, 1, CoolerType1.WATER.getID()));
		addRule("iron_cooler", cooler_rule[CoolerType1.IRON.getID()], new ItemStack(QMDBlocks.acceleratorCooler1, 1, CoolerType1.IRON.getID()));
		addRule("redstone_cooler", cooler_rule[CoolerType1.REDSTONE.getID()], new ItemStack(QMDBlocks.acceleratorCooler1, 1, CoolerType1.REDSTONE.getID()));
		addRule("quartz_cooler", cooler_rule[CoolerType1.QUARTZ.getID()], new ItemStack(QMDBlocks.acceleratorCooler1, 1, CoolerType1.QUARTZ.getID()));
		addRule("obsidian_cooler", cooler_rule[CoolerType1.OBSIDIAN.getID()], new ItemStack(QMDBlocks.acceleratorCooler1, 1, CoolerType1.OBSIDIAN.getID()));
		addRule("nether_brick_cooler", cooler_rule[CoolerType1.NETHER_BRICK.getID()], new ItemStack(QMDBlocks.acceleratorCooler1, 1, CoolerType1.NETHER_BRICK.getID()));
		addRule("glowstone_cooler", cooler_rule[CoolerType1.GLOWSTONE.getID()], new ItemStack(QMDBlocks.acceleratorCooler1, 1, CoolerType1.GLOWSTONE.getID()));
		addRule("lapis_cooler", cooler_rule[CoolerType1.LAPIS.getID()], new ItemStack(QMDBlocks.acceleratorCooler1, 1, CoolerType1.LAPIS.getID()));
		addRule("gold_cooler", cooler_rule[CoolerType1.GOLD.getID()], new ItemStack(QMDBlocks.acceleratorCooler1, 1, CoolerType1.GOLD.getID()));
		addRule("prismarine_cooler", cooler_rule[CoolerType1.PRISMARINE.getID()], new ItemStack(QMDBlocks.acceleratorCooler1, 1, CoolerType1.PRISMARINE.getID()));
		addRule("slime_cooler", cooler_rule[CoolerType1.SLIME.getID()], new ItemStack(QMDBlocks.acceleratorCooler1, 1, CoolerType1.SLIME.getID()));
		addRule("end_stone_cooler", cooler_rule[CoolerType1.END_STONE.getID()], new ItemStack(QMDBlocks.acceleratorCooler1, 1, CoolerType1.END_STONE.getID()));
		addRule("purpur_cooler", cooler_rule[CoolerType1.PURPUR.getID()], new ItemStack(QMDBlocks.acceleratorCooler1, 1, CoolerType1.PURPUR.getID()));
		addRule("diamond_cooler", cooler_rule[CoolerType1.DIAMOND.getID()], new ItemStack(QMDBlocks.acceleratorCooler1, 1, CoolerType1.DIAMOND.getID()));
		addRule("emerald_cooler", cooler_rule[CoolerType1.EMERALD.getID()], new ItemStack(QMDBlocks.acceleratorCooler1, 1, CoolerType1.EMERALD.getID()));
		addRule("copper_cooler", cooler_rule[CoolerType1.COPPER.getID()], new ItemStack(QMDBlocks.acceleratorCooler1, 1, CoolerType1.COPPER.getID()));
		addRule("tin_cooler", cooler_rule[CoolerType2.TIN.getID()+16], new ItemStack(QMDBlocks.acceleratorCooler2, 1, CoolerType2.TIN.getID()));
		addRule("lead_cooler", cooler_rule[CoolerType2.LEAD.getID()+16], new ItemStack(QMDBlocks.acceleratorCooler2, 1, CoolerType2.LEAD.getID()));
		addRule("boron_cooler", cooler_rule[CoolerType2.BORON.getID()+16], new ItemStack(QMDBlocks.acceleratorCooler2, 1, CoolerType2.BORON.getID()));
		addRule("lithium_cooler", cooler_rule[CoolerType2.LITHIUM.getID()+16], new ItemStack(QMDBlocks.acceleratorCooler2, 1, CoolerType2.LITHIUM.getID()));
		addRule("magnesium_cooler", cooler_rule[CoolerType2.MAGNESIUM.getID()+16], new ItemStack(QMDBlocks.acceleratorCooler2, 1, CoolerType2.MAGNESIUM.getID()));
		addRule("manganese_cooler", cooler_rule[CoolerType2.MANGANESE.getID()+16], new ItemStack(QMDBlocks.acceleratorCooler2, 1, CoolerType2.MANGANESE.getID()));
		addRule("aluminum_cooler", cooler_rule[CoolerType2.ALUMINUM.getID()+16], new ItemStack(QMDBlocks.acceleratorCooler2, 1, CoolerType2.ALUMINUM.getID()));
		addRule("silver_cooler", cooler_rule[CoolerType2.SILVER.getID()+16], new ItemStack(QMDBlocks.acceleratorCooler2, 1, CoolerType2.SILVER.getID()));
		addRule("fluorite_cooler", cooler_rule[CoolerType2.FLUORITE.getID()+16], new ItemStack(QMDBlocks.acceleratorCooler2, 1, CoolerType2.FLUORITE.getID()));
		addRule("villiaumite_cooler", cooler_rule[CoolerType2.VILLIAUMITE.getID()+16], new ItemStack(QMDBlocks.acceleratorCooler2, 1, CoolerType2.VILLIAUMITE.getID()));
		addRule("carobbiite_cooler", cooler_rule[CoolerType2.CAROBBIITE.getID()+16], new ItemStack(QMDBlocks.acceleratorCooler2, 1, CoolerType2.CAROBBIITE.getID()));
		addRule("arsenic_cooler", cooler_rule[CoolerType2.ARSENIC.getID()+16], new ItemStack(QMDBlocks.acceleratorCooler2, 1, CoolerType2.ARSENIC.getID()));
		addRule("liquid_nitrogen_cooler", cooler_rule[CoolerType2.LIQUID_NITROGEN.getID()+16], new ItemStack(QMDBlocks.acceleratorCooler2, 1, CoolerType2.LIQUID_NITROGEN.getID()));
		addRule("liquid_helium_cooler", cooler_rule[CoolerType2.LIQUID_HELIUM.getID()+16], new ItemStack(QMDBlocks.acceleratorCooler2, 1, CoolerType2.LIQUID_HELIUM.getID()));
		addRule("enderium_cooler", cooler_rule[CoolerType2.ENDERIUM.getID()+16], new ItemStack(QMDBlocks.acceleratorCooler2, 1, CoolerType2.ENDERIUM.getID()));
		addRule("cryotheum_cooler", cooler_rule[CoolerType2.CRYOTHEUM.getID()+16], new ItemStack(QMDBlocks.acceleratorCooler2, 1, CoolerType2.CRYOTHEUM.getID()));
		
		
	}
	
	public static void addRule(String id, String rule, Object... blocks) 
	{
		RULE_MAP_RAW.put(id, rule);
		RULE_MAP.put(id, parse(rule));
		for (Object block : blocks) 
		{
			recipe_handler.addRecipe(block, id);
		}
	}
	
	public static void postInit() 
	{
		for (Object2ObjectMap.Entry<String, PlacementRule<IAcceleratorPart>> entry : RULE_MAP.object2ObjectEntrySet()) {
			for (PlacementRule.TooltipBuilder<IAcceleratorPart> builder : TOOLTIP_BUILDER_LIST) 
			{
				String tooltip = builder.buildTooltip(entry.getValue());
				if (tooltip != null)
					TOOLTIP_MAP.put(entry.getKey(), tooltip);
			}
		}
	}
	
	// Default Rule Parser
	
	public static PlacementRule<IAcceleratorPart> parse(String string) 
	{
		return PlacementRule.parse(string, RULE_PARSER_LIST);
	}
	
	/** Rule parser for all rule types available in base NC. */
	public static class DefaultRuleParser extends PlacementRule.DefaultRuleParser<IAcceleratorPart> 
	{
		
		@Override
		protected @Nullable PlacementRule<IAcceleratorPart> partialParse(String s) 
		{
			s = s.toLowerCase(Locale.ROOT);
			
			s = s.replaceAll("at exactly one vertex", "vertex");
			
			boolean exact = s.contains("exact"), atMost = s.contains("at most");
			boolean axial = s.contains("axial"), vertex = s.contains("vertex");
			boolean different = s.contains("different");
			
			if ((exact && atMost) || (axial && vertex))
				return null;
			
			s = s.replaceAll("at least", "");
			s = s.replaceAll("exactly", "");
			s = s.replaceAll("exact", "");
			s = s.replaceAll("at most", "");
			s = s.replaceAll("axially", "");
			s = s.replaceAll("axial", "");
			s = s.replaceAll("at one vertex", "");
			s = s.replaceAll("at a vertex", "");
			s = s.replaceAll("at vertex", "");
			s = s.replaceAll("vertex", "");
			
			int amount = -1;
			String rule = null, type = null;
			
			String[] split = s.split(Pattern.quote(" "));
			for (int i = 0; i < split.length; i++) 
			{
				if (StringHelper.NUMBER_S2I_MAP.containsKey(split[i])) 
				{
					amount = StringHelper.NUMBER_S2I_MAP.getInt(split[i]);
				}
				else if (rule == null) {
					if (split[i].contains("beam")) 
					{
						rule = "beam";
					}
					else if (split[i].contains("magnet")) 
					{
						rule = "magnet";
						
					}
					else if (split[i].contains("yoke")) 
					{
						rule = "yoke";
					}
					else if (split[i].contains("cavity")) 
					{
						rule = "cavity";
						
					}
					else if (split[i].contains("cooler")) 
					{
						rule = "cooler";
						if (i > 0)
							type = split[i - 1];
						else
							return null;
					}
				}
			}
			
			if (amount < 0 || rule == null)
				return null;
			
			CountType countType = exact ? CountType.EXACTLY : (atMost ? CountType.AT_MOST : CountType.AT_LEAST);
			AdjacencyType adjType = axial ? AdjacencyType.AXIAL : (vertex ? AdjacencyType.VERTEX : AdjacencyType.STANDARD);
			
			if (rule.equals("beam")) 
			{
				return new AdjacentBeam(amount, countType, adjType);
			}
			else if (rule.equals("magnet")) 
			{
				if(different)
				{
					return new AdjacentDifferentMagnet(amount, countType, adjType);
				}
				return new AdjacentMagnet(amount, countType, adjType, type);
			}
			else if (rule.equals("yoke")) 
			{
				return new AdjacentYoke(amount, countType, adjType);
			}
			else if (rule.equals("cavity")) 
			{
				if(different)
				{
					return new AdjacentDifferentCavity(amount, countType, adjType);
				}
				return new AdjacentRFCavity(amount, countType, adjType, type);
			}
			else if (rule.equals("cooler")) 
			{
				return new AdjacentCooler(amount, countType, adjType, type);
			}
		
			
			return null;
		}
	}
	
	

	
	// Adjacent
	
	public static abstract class Adjacent extends PlacementRule.Adjacent<IAcceleratorPart> 
	{
		
		public Adjacent(String dependency, int amount, CountType countType, AdjacencyType adjType) 
		{
			super(dependency, amount, countType, adjType);
		}
	}
	
	public static class AdjacentBeam extends Adjacent 
	{
		
		public AdjacentBeam(int amount, CountType countType, AdjacencyType adjType) 
		{
			super("beam", amount, countType, adjType);
		}
		
		@Override
		public boolean satisfied(IAcceleratorPart part, EnumFacing dir) 
		{
			return isBeam(part.getMultiblock(), part.getTilePos().offset(dir));
		}
	}
	
	public static class AdjacentMagnet extends Adjacent 
	{
		
		protected final String magnetType;
		
		public AdjacentMagnet(int amount, CountType countType, AdjacencyType adjType, String magnetType) 
		{
			super("magnet", amount, countType, adjType);
			this.magnetType = magnetType;
		}
		
		@Override
		public boolean satisfied(IAcceleratorPart part, EnumFacing dir) 
		{
			return isActiveMagnet(part.getMultiblock(), part.getTilePos().offset(dir),magnetType);
		}
	}
	
	public static class AdjacentYoke extends Adjacent 
	{
		
		public AdjacentYoke(int amount, CountType countType, AdjacencyType adjType) 
		{
			super("yoke", amount, countType, adjType);
		}
		
		@Override
		public boolean satisfied(IAcceleratorPart part, EnumFacing dir) 
		{
			return isActiveYoke(part.getMultiblock(), part.getTilePos().offset(dir));
		}
	}
	
	public static class AdjacentRFCavity extends Adjacent 
	{
		
		protected final String cavityType;
		
		public AdjacentRFCavity(int amount, CountType countType, AdjacencyType adjType, String cavityType) 
		{
			super("cavity", amount, countType, adjType);
			this.cavityType = cavityType;
		}
		
		@Override
		public boolean satisfied(IAcceleratorPart part, EnumFacing dir) 
		{
			return isActiveRFCavity(part.getMultiblock(), part.getTilePos().offset(dir),cavityType);
		}
	}
	
	public static class AdjacentCooler extends Adjacent 
	{
		
		protected final String coolerType;
		
		public AdjacentCooler(int amount, CountType countType, AdjacencyType adjType, String coolerType) 
		{
			super(coolerType + "_cooler", amount, countType, adjType);
			this.coolerType = coolerType;
		}
		
		@Override
		public void checkIsRuleAllowed(String ruleID) 
		{
			super.checkIsRuleAllowed(ruleID);
			if (countType != CountType.AT_LEAST && coolerType.equals("any")) 
			{
				
				throw new IllegalArgumentException((countType == CountType.EXACTLY ? "Exact 'any cooler'" : "'At most n of any cooler'") + " placement rule with ID \"" + ruleID + "\" is disallowed due to potential ambiguity during rule checks!");
			}
		}
		
		@Override
		public boolean satisfied(IAcceleratorPart part, EnumFacing dir) 
		{
			return isActiveCooler(part.getMultiblock(), part.getTilePos().offset(dir), coolerType);
		}
	}
	
	
	public static class AdjacentDifferentMagnet extends Adjacent 
	{
		
		
		public AdjacentDifferentMagnet(int amount, CountType countType, AdjacencyType adjType) 
		{
			super("magnet", amount, countType, adjType);
		}
		
		@Override
		public void checkIsRuleAllowed(String ruleID) 
		{
			if (adjType != AdjacencyType.STANDARD)
			{
				throw new IllegalArgumentException("Diffrent Adjacency placement rule with ID \"" + ruleID + "\" can only be AdjacencyType standard  ");
			}
			if (amount > 6) 
			{
				throw new IllegalArgumentException("Diffrent Adjacency placement rule with ID \"" + ruleID + "\" can not require more than six adjacencies!");
			}
			if (amount < 1) 
			{
				throw new IllegalArgumentException("Diffrent Adjacency placement rule with ID \"" + ruleID + "\" can only require more than one difrent adjacencies");
			}
		}
		
			
		@Override
		public boolean satisfied(IAcceleratorPart tile)
		{
			List<String> storedTypes = new ArrayList<String>();

			for (EnumFacing dir : EnumFacing.VALUES)
			{
				if (isActiveMagnet(tile.getMultiblock(), tile.getTilePos().offset(dir), null))
				{
					String type = tile.getMultiblock().getPartMap(TileAcceleratorMagnet.class)
							.get(tile.getTilePos().offset(dir).toLong()).name;

					if (!storedTypes.contains(type))
					{
						storedTypes.add(type);
					}
				}

				if (countType == CountType.AT_LEAST)
				{
					if (storedTypes.size() >= amount)
						return true;
				}
				else if (storedTypes.size() > amount)
					return false;
			}
			return countType == CountType.AT_MOST || (countType == CountType.EXACTLY && storedTypes.size() == amount);
		}
		
		
		@Override
		public String buildSubTooltip() 
		{	
			return Lang.localise("nc.sf.placement_rule.adjacent." + countType.tooltipSubstring(amount) + adjType.tooltipSubstring(amount),I18nHelper.getPluralForm("nc.sf." + dependencies.get(0), amount, Lang.localise("nc.sf.different." + StringHelper.NUMBER_I2S_MAP.get(amount))));
		}

		@Override
		public boolean satisfied(IAcceleratorPart tile, EnumFacing dir)
		{
			return false;
		}
	}
	
	public static class AdjacentDifferentCavity extends Adjacent 
	{
		List<String> storedTypes;
		
		public AdjacentDifferentCavity(int amount, CountType countType, AdjacencyType adjType) 
		{
			super("cavity", amount, countType, adjType);
			storedTypes = new ArrayList<String>();
		}
		
		@Override
		public void checkIsRuleAllowed(String ruleID) 
		{
			if (adjType != AdjacencyType.STANDARD)
			{
				throw new IllegalArgumentException("Diffrent Adjacency placement rule with ID \"" + ruleID + "\" can only be AdjacencyType standard  ");
			}
			if (amount > 6) 
			{
				throw new IllegalArgumentException("Diffrent Adjacency placement rule with ID \"" + ruleID + "\" can not require more than six adjacencies!");
			}
			if (amount < 1) 
			{
				throw new IllegalArgumentException("Diffrent Adjacency placement rule with ID \"" + ruleID + "\" can only require more than one difrent adjacencies");
			}
		}
		
			
		@Override
		public boolean satisfied(IAcceleratorPart tile)
		{
			List<String> storedTypes = new ArrayList<String>();

			for (EnumFacing dir : EnumFacing.VALUES)
			{
				if (isActiveRFCavity(tile.getMultiblock(), tile.getTilePos().offset(dir), null))
				{
					String type = tile.getMultiblock().getPartMap(TileAcceleratorRFCavity.class)
							.get(tile.getTilePos().offset(dir).toLong()).name;

					if (!storedTypes.contains(type))
					{
						storedTypes.add(type);
					}
				}

				if (countType == CountType.AT_LEAST)
				{
					if (storedTypes.size() >= amount)
						return true;
				}
				else if (storedTypes.size() > amount)
					return false;
			}
			return countType == CountType.AT_MOST || (countType == CountType.EXACTLY && storedTypes.size() == amount);
		}
		
		@Override
		public boolean satisfied(IAcceleratorPart part, EnumFacing dir) 
		{
			return false;
		}
		
		@Override
		public String buildSubTooltip() 
		{
			return Lang.localise("nc.sf.placement_rule.adjacent." + countType.tooltipSubstring(amount) + adjType.tooltipSubstring(amount),I18nHelper.getPluralForm("nc.sf." + dependencies.get(0), amount, Lang.localise("nc.sf.different." + StringHelper.NUMBER_I2S_MAP.get(amount))));
		}
		
		
	}
	
	
	
	
	
	// Helper Methods
	
	public static boolean isBeam(Accelerator accelerator, BlockPos pos) 
	{
		TileAcceleratorBeam beam = accelerator.getPartMap(TileAcceleratorBeam.class).get(pos.toLong());
		return beam == null ? false : beam.isFunctional();
	}
	
	public static boolean isActiveMagnet(Accelerator accelerator, BlockPos pos, String magnetName) 
	{
		TileAcceleratorMagnet magnet = accelerator.getPartMap(TileAcceleratorMagnet.class).get(pos.toLong());
		return magnet == null ? false : (magnet.isFunctional() && magnet.name.equals(magnetName))||(magnet.isFunctional() && magnetName == null);
	}
	
	public static boolean isActiveYoke(Accelerator accelerator, BlockPos pos) 
	{
		TileAcceleratorYoke yoke  = accelerator.getPartMap(TileAcceleratorYoke.class).get(pos.toLong());
		return yoke == null ? false : yoke.isFunctional();
	}
	
	public static boolean isActiveRFCavity(Accelerator accelerator, BlockPos pos, String cavityName) 
	{
		TileAcceleratorRFCavity cavity =  accelerator.getPartMap(TileAcceleratorRFCavity.class).get(pos.toLong());
		return cavity == null ? false : (cavity.isFunctional() && (cavity.name.equals(cavityName))||(cavity.isFunctional() && cavityName == null));
	}
	
	public static boolean isActiveCooler(Accelerator accelerator, BlockPos pos, String coolerName) 
	{
		TileAcceleratorCooler cooler = accelerator.getPartMap(TileAcceleratorCooler.class).get(pos.toLong());
		return cooler == null ? false : cooler.isFunctional() && (coolerName.equals("any") || cooler.name.equals(coolerName));
	}
	
	
	
	// Default Tooltip Builder
	
	public static class DefaultTooltipBuilder extends PlacementRule.DefaultTooltipBuilder<IAcceleratorPart> {}
	
	// Tooltip Recipes
	
	public static class RecipeHandler extends PlacementRule.RecipeHandler 
	{
		
		public RecipeHandler() 
		{
			super("accelerator");
		}
	}
	
}
