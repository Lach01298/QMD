package lach_01298.qmd.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import lach_01298.qmd.QMD;
import nc.util.Lang;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.IModGuiFactory;
import net.minecraftforge.fml.client.config.DummyConfigElement.DummyCategoryElement;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.GuiConfigEntries;
import net.minecraftforge.fml.client.config.GuiConfigEntries.CategoryEntry;
import net.minecraftforge.fml.client.config.GuiConfigEntries.IConfigEntry;
import net.minecraftforge.fml.client.config.IConfigElement;

public class QMDConfigGUIFactory implements IModGuiFactory
{

	@Override
	public void initialize(Minecraft minecraftInstance)
	{
	}

	public Class<? extends GuiScreen> mainConfigGuiClass()
	{
		return QMDConfigGui.class;
	}

	@Override
	public Set<RuntimeOptionCategoryElement> runtimeGuiCategories()
	{
		return null;
	}

	public static class QMDConfigGui extends GuiConfig
	{

		public QMDConfigGui(GuiScreen parentScreen)
		{
			super(parentScreen, getConfigElements(), QMD.MOD_ID, false, false,
					Lang.localise("gui.qmd.config.main_title"));
		}

		private static List<IConfigElement> getConfigElements()
		{
			List<IConfigElement> list = new ArrayList<>();
			list.add(categoryElement(QMDConfig.CATEGORY_PROCESSORS, CategoryEntryProcessors.class));
			list.add(categoryElement(QMDConfig.CATEGORY_ACCELERATOR, CategoryEntryAccelerator.class));
			list.add(categoryElement(QMDConfig.CATEGORY_PARTICLE_CHAMBER, CategoryEntryParticleChamber.class));
			list.add(categoryElement(QMDConfig.CATEGORY_VACUUM_CHAMBER, CategoryEntryContainment.class));
			list.add(categoryElement(QMDConfig.CATEGORY_FISSION, CategoryEntryFission.class));
			list.add(categoryElement(QMDConfig.CATEGORY_FUSION, CategoryEntryFusion.class));
			list.add(categoryElement(QMDConfig.CATEGORY_TOOLS, CategoryEntryTools.class));
			list.add(categoryElement(QMDConfig.CATEGORY_OTHER, CategoryEntryOther.class));

			return list;
		}

		private static DummyCategoryElement categoryElement(String categoryName,
				Class<? extends IConfigEntry> categoryClass)
		{
			return new DummyCategoryElement(Lang.localise("gui.qmd.config.category." + categoryName),
					"gui.qmd.config.category." + categoryName, categoryClass);
		}

		public static class CategoryEntryProcessors extends CategoryEntry implements IQMDConfigCategory
		{

			public CategoryEntryProcessors(GuiConfig owningScreen, GuiConfigEntries owningEntryList,
					IConfigElement configElement)
			{
				super(owningScreen, owningEntryList, configElement);
			}

			@Override
			protected GuiScreen buildChildScreen()
			{
				return buildChildScreen(QMDConfig.CATEGORY_PROCESSORS, owningScreen, configElement);
			}
		}
		
		public static class CategoryEntryAccelerator extends CategoryEntry implements IQMDConfigCategory
		{

			public CategoryEntryAccelerator(GuiConfig owningScreen, GuiConfigEntries owningEntryList,
					IConfigElement configElement)
			{
				super(owningScreen, owningEntryList, configElement);
			}

			@Override
			protected GuiScreen buildChildScreen()
			{
				return buildChildScreen(QMDConfig.CATEGORY_ACCELERATOR, owningScreen, configElement);
			}
		}
		
		public static class CategoryEntryParticleChamber extends CategoryEntry implements IQMDConfigCategory
		{

			public CategoryEntryParticleChamber(GuiConfig owningScreen, GuiConfigEntries owningEntryList,
					IConfigElement configElement)
			{
				super(owningScreen, owningEntryList, configElement);
			}

			@Override
			protected GuiScreen buildChildScreen()
			{
				return buildChildScreen(QMDConfig.CATEGORY_PARTICLE_CHAMBER, owningScreen, configElement);
			}
		}
		
		public static class CategoryEntryContainment extends CategoryEntry implements IQMDConfigCategory
		{

			public CategoryEntryContainment(GuiConfig owningScreen, GuiConfigEntries owningEntryList,
					IConfigElement configElement)
			{
				super(owningScreen, owningEntryList, configElement);
			}

			@Override
			protected GuiScreen buildChildScreen()
			{
				return buildChildScreen(QMDConfig.CATEGORY_VACUUM_CHAMBER, owningScreen, configElement);
			}
		}

		public static class CategoryEntryFission extends CategoryEntry implements IQMDConfigCategory
		{

			public CategoryEntryFission(GuiConfig owningScreen, GuiConfigEntries owningEntryList,
					IConfigElement configElement)
			{
				super(owningScreen, owningEntryList, configElement);
			}

			@Override
			protected GuiScreen buildChildScreen()
			{
				return buildChildScreen(QMDConfig.CATEGORY_FISSION, owningScreen, configElement);
			}
		}

		public static class CategoryEntryFusion extends CategoryEntry implements IQMDConfigCategory
		{

			public CategoryEntryFusion(GuiConfig owningScreen, GuiConfigEntries owningEntryList,
					IConfigElement configElement)
			{
				super(owningScreen, owningEntryList, configElement);
			}

			@Override
			protected GuiScreen buildChildScreen()
			{
				return buildChildScreen(QMDConfig.CATEGORY_FUSION, owningScreen, configElement);
			}
		}

		public static class CategoryEntryTools extends CategoryEntry implements IQMDConfigCategory
		{

			public CategoryEntryTools(GuiConfig owningScreen, GuiConfigEntries owningEntryList,
					IConfigElement configElement)
			{
				super(owningScreen, owningEntryList, configElement);
			}

			@Override
			protected GuiScreen buildChildScreen()
			{
				return buildChildScreen(QMDConfig.CATEGORY_TOOLS, owningScreen, configElement);
			}
		}


		public static class CategoryEntryOther extends CategoryEntry implements IQMDConfigCategory
		{

			public CategoryEntryOther(GuiConfig owningScreen, GuiConfigEntries owningEntryList,
					IConfigElement configElement)
			{
				super(owningScreen, owningEntryList, configElement);
			}

			@Override
			protected GuiScreen buildChildScreen()
			{
				return buildChildScreen(QMDConfig.CATEGORY_OTHER, owningScreen, configElement);
			}
		}
	}

	@Override
	public boolean hasConfigGui()
	{
		return true;
	}

	@Override
	public GuiScreen createConfigGui(GuiScreen parentScreen)
	{
		return new QMDConfigGui(parentScreen);
	}
}
