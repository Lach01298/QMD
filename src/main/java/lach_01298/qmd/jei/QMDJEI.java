package lach_01298.qmd.jei;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

import lach_01298.qmd.jei.catergory.AcceleratorDetectorCategory;
import lach_01298.qmd.jei.catergory.AcceleratorSourceCategory;
import lach_01298.qmd.jei.catergory.AcceleratorTargetCategory;
import lach_01298.qmd.particle.ParticleBeam;
import mezz.jei.Internal;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IJeiRuntime;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.ISubtypeRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.ingredients.IIngredientRegistry;
import mezz.jei.api.ingredients.IModIngredientRegistration;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.api.recipe.IVanillaRecipeFactory;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import mezz.jei.api.recipe.transfer.IRecipeTransferRegistry;
import mezz.jei.gui.GuiHelper;
import mezz.jei.plugins.vanilla.anvil.AnvilRecipeCategory;
import mezz.jei.plugins.vanilla.brewing.BrewingRecipeCategory;
import mezz.jei.plugins.vanilla.crafting.CraftingRecipeCategory;
import mezz.jei.plugins.vanilla.crafting.CraftingRecipeChecker;
import mezz.jei.plugins.vanilla.furnace.FurnaceFuelCategory;
import mezz.jei.plugins.vanilla.furnace.FurnaceSmeltingCategory;
import mezz.jei.runtime.JeiHelpers;
import mezz.jei.startup.StackHelper;
import nc.ModCheck;
import nc.config.NCConfig;
import nc.container.processor.ContainerAlloyFurnace;
import nc.container.processor.ContainerCentrifuge;
import nc.container.processor.ContainerChemicalReactor;
import nc.container.processor.ContainerCrystallizer;
import nc.container.processor.ContainerDecayHastener;
import nc.container.processor.ContainerElectrolyzer;
import nc.container.processor.ContainerEnricher;
import nc.container.processor.ContainerExtractor;
import nc.container.processor.ContainerFuelReprocessor;
import nc.container.processor.ContainerInfuser;
import nc.container.processor.ContainerIngotFormer;
import nc.container.processor.ContainerIrradiator;
import nc.container.processor.ContainerIsotopeSeparator;
import nc.container.processor.ContainerManufactory;
import nc.container.processor.ContainerMelter;
import nc.container.processor.ContainerPressurizer;
import nc.container.processor.ContainerRockCrusher;
import nc.container.processor.ContainerSaltMixer;
import nc.container.processor.ContainerSupercooler;
import nc.enumm.MetaEnums;
import nc.gui.processor.GuiAlloyFurnace;
import nc.gui.processor.GuiCentrifuge;
import nc.gui.processor.GuiChemicalReactor;
import nc.gui.processor.GuiCrystallizer;
import nc.gui.processor.GuiDecayHastener;
import nc.gui.processor.GuiElectrolyzer;
import nc.gui.processor.GuiEnricher;
import nc.gui.processor.GuiExtractor;
import nc.gui.processor.GuiFuelReprocessor;
import nc.gui.processor.GuiInfuser;
import nc.gui.processor.GuiIngotFormer;
import nc.gui.processor.GuiIrradiator;
import nc.gui.processor.GuiIsotopeSeparator;
import nc.gui.processor.GuiManufactory;
import nc.gui.processor.GuiMelter;
import nc.gui.processor.GuiPressurizer;
import nc.gui.processor.GuiRockCrusher;
import nc.gui.processor.GuiSaltMixer;
import nc.gui.processor.GuiSupercooler;
import nc.init.NCArmor;
import nc.init.NCBlocks;
import nc.init.NCItems;
import nc.integration.jei.generator.DecayGeneratorCategory;
import nc.integration.jei.multiblock.CondenserCategory;
import nc.integration.jei.multiblock.CoolantHeaterCategory;
import nc.integration.jei.multiblock.FissionHeatingCategory;
import nc.integration.jei.multiblock.FissionModeratorCategory;
import nc.integration.jei.multiblock.FissionReflectorCategory;
import nc.integration.jei.multiblock.HeatExchangerCategory;
import nc.integration.jei.multiblock.SaltFissionCategory;
import nc.integration.jei.multiblock.SolidFissionCategory;
import nc.integration.jei.multiblock.TurbineCategory;
import nc.integration.jei.other.CollectorCategory;
import nc.integration.jei.processor.AlloyFurnaceCategory;
import nc.integration.jei.processor.CentrifugeCategory;
import nc.integration.jei.processor.ChemicalReactorCategory;
import nc.integration.jei.processor.CrystallizerCategory;
import nc.integration.jei.processor.DecayHastenerCategory;
import nc.integration.jei.processor.ElectrolyzerCategory;
import nc.integration.jei.processor.EnricherCategory;
import nc.integration.jei.processor.ExtractorCategory;
import nc.integration.jei.processor.FuelReprocessorCategory;
import nc.integration.jei.processor.InfuserCategory;
import nc.integration.jei.processor.IngotFormerCategory;
import nc.integration.jei.processor.IrradiatorCategory;
import nc.integration.jei.processor.IsotopeSeparatorCategory;
import nc.integration.jei.processor.ManufactoryCategory;
import nc.integration.jei.processor.MelterCategory;
import nc.integration.jei.processor.PressurizerCategory;
import nc.integration.jei.processor.RockCrusherCategory;
import nc.integration.jei.processor.SaltMixerCategory;
import nc.integration.jei.processor.SupercoolerCategory;
import nc.recipe.NCRecipes;
import nc.recipe.ProcessorRecipeHandler;
import nc.util.ItemStackHelper;
import nc.util.NCUtil;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

//@JEIPlugin
public class QMDJEI implements IModPlugin
{



	public void registerIngredients(IModIngredientRegistration registry)
	{
		
		
		List<ParticleBeam> particleBeams = ParticleBeamListFactory.create();
		ParticleBeamHelper particleBeamHelper = new ParticleBeamHelper();
		ParticleBeamRenderer particleBeamRenderer = new ParticleBeamRenderer();
		
		
		
		registry.register(ParticleType.Particle, particleBeams, particleBeamHelper, particleBeamRenderer);
	}

	public void registerCategories(IRecipeCategoryRegistration registry)
	{
		JeiHelpers jeiHelpers = Internal.getHelpers();
		GuiHelper guiHelper = jeiHelpers.getGuiHelper();
		registry.addRecipeCategories(
			new AcceleratorSourceCategory(guiHelper),
			new AcceleratorTargetCategory(guiHelper),
			new AcceleratorDetectorCategory(guiHelper)
	
		);
	}

	public void register(IModRegistry registry)
	{
		IIngredientRegistry ingredientRegistry = registry.getIngredientRegistry();
		IJeiHelpers jeiHelpers = registry.getJeiHelpers();


		registry.addRecipes(CraftingRecipeChecker.getValidRecipes(jeiHelpers), VanillaRecipeCategoryUid.CRAFTING);
	}


}
