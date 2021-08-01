package lach_01298.qmd.vacuumChamber;


import static lach_01298.qmd.config.QMDConfig.heater_rule;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import javax.annotation.Nullable;

import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import lach_01298.qmd.accelerator.Accelerator;
import lach_01298.qmd.block.QMDBlocks;
import lach_01298.qmd.enums.BlockTypes.HeaterType;
import lach_01298.qmd.vacuumChamber.tile.IVacuumChamberPart;
import lach_01298.qmd.vacuumChamber.tile.TileVacuumChamberBeam;
import lach_01298.qmd.vacuumChamber.tile.TileVacuumChamberHeater;
import lach_01298.qmd.vacuumChamber.tile.TileVacuumChamberPart;
import lach_01298.qmd.vacuumChamber.tile.TileVacuumChamberPlasmaGlass;
import lach_01298.qmd.vacuumChamber.tile.TileVacuumChamberPlasmaNozzle;
import nc.multiblock.PlacementRule;
import nc.multiblock.PlacementRule.AdjacencyType;
import nc.multiblock.PlacementRule.CountType;
import nc.multiblock.PlacementRule.PlacementMap;
import nc.multiblock.fission.FissionReactor;
import nc.multiblock.fission.FissionPlacement.Adjacent;
import nc.multiblock.fission.FissionPlacement.AdjacentCasing;
import nc.multiblock.fission.tile.IFissionPart;
import nc.multiblock.fission.tile.TileFissionPart;
import nc.util.StringHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class HeaterPlacement
{

	/** List of all defined rule parsers. Earlier entries are prioritised! */
	public static final List<PlacementRule.RuleParser<IVacuumChamberPart>> RULE_PARSER_LIST = new LinkedList<>();
	
	/** Map of all placement rule IDs to unparsed rule strings, used for ordered iterations. */
	public static final Object2ObjectMap<String, String> RULE_MAP_RAW = new Object2ObjectArrayMap<>();
	
	/** Map of all defined placement rules. */
	public static final Object2ObjectMap<String, PlacementRule<IVacuumChamberPart>> RULE_MAP = new PlacementMap<>();
	
	/** List of all defined tooltip builders. Earlier entries are prioritised! */
	public static final List<PlacementRule.TooltipBuilder<IVacuumChamberPart>> TOOLTIP_BUILDER_LIST = new LinkedList<>();
	
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
		
		
		addRule("iron_heater", heater_rule[HeaterType.IRON.getID()], new ItemStack(QMDBlocks.vacuumChamberHeater, 1, HeaterType.IRON.getID()));
		addRule("redstone_heater", heater_rule[HeaterType.REDSTONE.getID()], new ItemStack(QMDBlocks.vacuumChamberHeater, 1, HeaterType.REDSTONE.getID()));
		addRule("quartz_heater", heater_rule[HeaterType.QUARTZ.getID()], new ItemStack(QMDBlocks.vacuumChamberHeater, 1, HeaterType.QUARTZ.getID()));
		addRule("obsidian_heater", heater_rule[HeaterType.OBSIDIAN.getID()], new ItemStack(QMDBlocks.vacuumChamberHeater, 1, HeaterType.OBSIDIAN.getID()));
		addRule("glowstone_heater", heater_rule[HeaterType.GLOWSTONE.getID()], new ItemStack(QMDBlocks.vacuumChamberHeater, 1, HeaterType.GLOWSTONE.getID()));
		addRule("lapis_heater", heater_rule[HeaterType.LAPIS.getID()], new ItemStack(QMDBlocks.vacuumChamberHeater, 1, HeaterType.LAPIS.getID()));
		addRule("gold_heater", heater_rule[HeaterType.GOLD.getID()], new ItemStack(QMDBlocks.vacuumChamberHeater, 1, HeaterType.GOLD.getID()));
		addRule("diamond_heater", heater_rule[HeaterType.DIAMOND.getID()], new ItemStack(QMDBlocks.vacuumChamberHeater, 1, HeaterType.DIAMOND.getID()));
		
		
		
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
		for (Object2ObjectMap.Entry<String, PlacementRule<IVacuumChamberPart>> entry : RULE_MAP.object2ObjectEntrySet()) {
			for (PlacementRule.TooltipBuilder<IVacuumChamberPart> builder : TOOLTIP_BUILDER_LIST) 
			{
				String tooltip = builder.buildTooltip(entry.getValue());
				if (tooltip != null)
					TOOLTIP_MAP.put(entry.getKey(), tooltip);
			}
		}
	}
	
	// Default Rule Parser
	
	public static PlacementRule<IVacuumChamberPart> parse(String string) 
	{
		return PlacementRule.parse(string, RULE_PARSER_LIST);
	}
	
	/** Rule parser for all rule types available in base NC. */
	public static class DefaultRuleParser extends PlacementRule.DefaultRuleParser<IVacuumChamberPart> 
	{
		
		@Override
		protected @Nullable PlacementRule<IVacuumChamberPart> partialParse(String s) 
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
				if (split[i].contains("wall") || split[i].contains("casing"))
				{
					rule = "casing";
				}
				else if (rule == null)
				{
					if (split[i].contains("beam"))
					{
						rule = "beam";
					}
					if (split[i].contains("glass"))
					{
						rule = "glass";
					}
					if (split[i].contains("nozzle"))
					{
						rule = "nozzle";
					}
					else if (split[i].contains("heater"))
					{
						rule = "heater";
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
			if (rule.equals("casing")) {
				return new AdjacentCasing(amount, countType, adjType);
			}
			else if (rule.equals("beam")) 
			{
				return new AdjacentBeam(amount, countType, adjType);
			}
			else if (rule.equals("glass")) 
			{
				return new AdjacentGlass(amount, countType, adjType);
			}
			else if (rule.equals("nozzle")) 
			{
				return new AdjacentNozzle(amount, countType, adjType);
			}
			else if (rule.equals("heater")) 
			{
				return new AdjacentHeater(amount, countType, adjType, type);
			}
		
			
			return null;
		}
	}
	
	

	
	// Adjacent
	
	public static abstract class Adjacent extends PlacementRule.Adjacent<IVacuumChamberPart> 
	{
		
		public Adjacent(String dependency, int amount, CountType countType, AdjacencyType adjType) 
		{
			super(dependency, amount, countType, adjType);
		}
	}
	
	public static class AdjacentCasing extends Adjacent {
		
		public AdjacentCasing(int amount, CountType countType, AdjacencyType adjType) {
			super("vacuum_chamber_casing", amount, countType, adjType);
		}
		
		@Override
		public boolean satisfied(IVacuumChamberPart part, EnumFacing dir) {
			return isCasing(part.getMultiblock(), part.getTilePos().offset(dir));
		}
	}
	
	public static class AdjacentBeam extends Adjacent 
	{
		
		public AdjacentBeam(int amount, CountType countType, AdjacencyType adjType) 
		{
			super("beam", amount, countType, adjType);
		}
		
		@Override
		public boolean satisfied(IVacuumChamberPart part, EnumFacing dir) 
		{
			return isBeam(part.getMultiblock(), part.getTilePos().offset(dir));
		}
	}
	
	public static class AdjacentGlass extends Adjacent 
	{
		
		public AdjacentGlass(int amount, CountType countType, AdjacencyType adjType) 
		{
			super("plasma_glass", amount, countType, adjType);
		}
		
		@Override
		public boolean satisfied(IVacuumChamberPart part, EnumFacing dir) 
		{
			return isPlasmaGlass(part.getMultiblock(), part.getTilePos().offset(dir));
		}
	}
	
	public static class AdjacentNozzle extends Adjacent 
	{
		
		public AdjacentNozzle(int amount, CountType countType, AdjacencyType adjType) 
		{
			super("nozzle", amount, countType, adjType);
		}
		
		@Override
		public boolean satisfied(IVacuumChamberPart part, EnumFacing dir) 
		{
			return isNozzle(part.getMultiblock(), part.getTilePos().offset(dir));
		}
	}
	
	
	
	public static class AdjacentHeater extends Adjacent 
	{
		
		protected final String heaterType;
		
		public AdjacentHeater(int amount, CountType countType, AdjacencyType adjType, String heaterType) 
		{
			super(heaterType + "_heater", amount, countType, adjType);
			this.heaterType = heaterType;
		}
		
		@Override
		public void checkIsRuleAllowed(String ruleID) 
		{
			super.checkIsRuleAllowed(ruleID);
			if (countType != CountType.AT_LEAST && heaterType.equals("any")) 
			{
				
				throw new IllegalArgumentException((countType == CountType.EXACTLY ? "Exact 'any heater'" : "'At most n of any heater'") + " placement rule with ID \"" + ruleID + "\" is disallowed due to potential ambiguity during rule checks!");
			}
		}
		
		@Override
		public boolean satisfied(IVacuumChamberPart part, EnumFacing dir) 
		{
			return isActiveHeater(part.getMultiblock(), part.getTilePos().offset(dir), heaterType);
		}
	}
	

	
	
	
	
	
	// Helper Methods
	
	public static boolean isCasing(VacuumChamber vacuumChamber, BlockPos pos) 
	{
		TileEntity tile = vacuumChamber.WORLD.getTileEntity(pos);
		return tile instanceof TileVacuumChamberPart && ((TileVacuumChamberPart) tile).getPartPositionType().isGoodForWall();
	}
	
	public static boolean isBeam(VacuumChamber vacuumChamber, BlockPos pos) 
	{
		TileVacuumChamberBeam beam = vacuumChamber.getPartMap(TileVacuumChamberBeam.class).get(pos.toLong());
		return beam == null ? false : beam.isFunctional();
	}
	
	public static boolean isPlasmaGlass(VacuumChamber vacuumChamber, BlockPos pos) 
	{
		TileVacuumChamberPlasmaGlass glass = vacuumChamber.getPartMap(TileVacuumChamberPlasmaGlass.class).get(pos.toLong());
		return glass == null ? false : true;
	}
	
	public static boolean isNozzle(VacuumChamber vacuumChamber, BlockPos pos) 
	{
		TileVacuumChamberPlasmaNozzle nozzle = vacuumChamber.getPartMap(TileVacuumChamberPlasmaNozzle.class).get(pos.toLong());
		return nozzle == null ? false : true;
	}
	
	
	public static boolean isActiveHeater(VacuumChamber vacuumChamber, BlockPos pos, String heaterName) 
	{
		TileVacuumChamberHeater heater = vacuumChamber.getPartMap(TileVacuumChamberHeater.class).get(pos.toLong());
		return heater == null ? false : heater.isFunctional() && (heaterName.equals("any") || heater.name.equals(heaterName));
	}
	
	
	
	// Default Tooltip Builder
	
	public static class DefaultTooltipBuilder extends PlacementRule.DefaultTooltipBuilder<IVacuumChamberPart> {}
	
	// Tooltip Recipes
	
	public static class RecipeHandler extends PlacementRule.RecipeHandler 
	{
		
		public RecipeHandler() 
		{
			super("vacuum_chamber");
		}
	}
	
}
