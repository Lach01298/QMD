package lach_01298.qmd.fluid;

import nc.block.fluid.NCBlockFluid;
import net.minecraftforge.fluids.Fluid;

public enum QMDFluidType {
	EXOTIC(FluidExotic.class, BlockFluidExotic.class);
	
	
	private final Class<? extends Fluid> fluidClass;
	private final Class<? extends NCBlockFluid> blockClass;
	
	private <T extends Fluid, V extends NCBlockFluid> QMDFluidType(Class<T> fluidClass, Class<V> blockClass) 
	{
		this.fluidClass = fluidClass;
		this.blockClass = blockClass;
	}
	
	public <T extends Fluid> Class<T> getFluidClass() {
		return (Class<T>) fluidClass;
	}
	
	public <T extends NCBlockFluid> Class<T> getBlockClass() {
		return (Class<T>) blockClass;
	}
}
