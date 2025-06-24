package lach_01298.qmd.groovyscript;

import com.cleanroommc.groovyscript.api.documentation.annotations.MethodDescription;
import com.cleanroommc.groovyscript.api.documentation.annotations.RegistryDescription;
import com.cleanroommc.groovyscript.api.documentation.annotations.MethodDescription.Type;
import com.cleanroommc.groovyscript.helper.Alias;
import com.cleanroommc.groovyscript.registry.NamedRegistry;
import com.google.common.base.CaseFormat;


@RegistryDescription
public class GSStaticQMDRecipeHandler extends NamedRegistry
{
	public GSStaticQMDRecipeHandler()
	{
		super(Alias.generateOf("QMDRecipeHandler", CaseFormat.UPPER_CAMEL));
	}

	@MethodDescription(
			type = Type.QUERY
	)
	public GSQMDRecipeRegistry get(String name)
	{
		return QMDGSContainer.instance.getRecipeRegistry(name);
	}
}