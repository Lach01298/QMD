package lach_01298.qmd.groovyscript;

import com.cleanroommc.groovyscript.api.documentation.annotations.*;
import com.cleanroommc.groovyscript.api.documentation.annotations.MethodDescription.Type;
import lach_01298.qmd.groovyscript.GSQMDRecipeBuilders.*;


public class GSQMDRecipeRegistries
{

	@RegistryDescription
	public static class GSQMDProcessorRecipeRegistry extends GSQMDRecipeRegistry
	{

		public GSQMDProcessorRecipeRegistry(String name)
		{
			super(name);
		}

		public GSQMDProcessorRecipeBuilder builder()
		{
			return new GSQMDProcessorRecipeBuilder(this);
		}
	}


	@RegistryDescription
	public static class GSTargetChamberRecipeRegistry extends GSQMDRecipeRegistry
	{

		public GSTargetChamberRecipeRegistry(String name)
		{
			super(name);
		}

		public GSTargetChamberRecipeBuilder builder()
		{
			return new GSTargetChamberRecipeBuilder(this);
		}
	}


}