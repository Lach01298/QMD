package lach_01298.qmd.fluid;

import lach_01298.qmd.QMD;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class FluidExotic extends Fluid
{

	
	public FluidExotic(String fluidName, String textureName, Integer color)
	{
		super(fluidName, stillTextureLocation(textureName), flowTextureLocation(textureName));

		
		int fixedColor = color.intValue();
		if ((fixedColor >> 24 & 0xFF) == 0)
		{
			fixedColor |= 0xFF << 24;
		}
		setColor(fixedColor);
		FluidRegistry.registerFluid(this);
	
	}

	public FluidExotic(String fluidName, Integer colour)
	{
		this(fluidName, "antimatter", colour);
	}

	private static ResourceLocation stillTextureLocation(String textureName)
	{
		return new ResourceLocation(QMD.MOD_ID + ":blocks/fluids/" + textureName+ "_still");
	}
	
	private static ResourceLocation flowTextureLocation(String textureName)
	{
		return new ResourceLocation(QMD.MOD_ID + ":blocks/fluids/" + textureName+"_flow");
	}

}
