package lach_01298.qmd.config;

import java.util.List;

import nc.util.Lang;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.*;
import net.minecraftforge.fml.client.config.*;

public interface IQMDConfigCategory {
	
	public default GuiScreen buildChildScreen(String categoryName, GuiConfig owningScreen, IConfigElement configElement) {
		Configuration config = QMDConfig.getConfig();
		ConfigElement newElement = new ConfigElement(config.getCategory(categoryName));
		List<IConfigElement> propertiesOnScreen = newElement.getChildElements();
		String windowTitle = Lang.localise("gui.qmd.config.category." + categoryName);
		return new GuiConfig(owningScreen, propertiesOnScreen, owningScreen.modID, configElement.requiresWorldRestart() || owningScreen.allRequireWorldRestart, configElement.requiresMcRestart() || owningScreen.allRequireMcRestart, windowTitle);
	}
}
