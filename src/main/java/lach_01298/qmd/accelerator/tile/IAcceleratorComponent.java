package lach_01298.qmd.accelerator.tile;

public interface IAcceleratorComponent extends IAcceleratorPart
{
	
	public int getMaxOperatingTemp();

	public boolean isFunctional();
	
	public void setFunctional(boolean func);
	
	public default boolean isToHot()
	{
		if (getMultiblock().getTemperature() > this.getMaxOperatingTemp())
		{
			return true;
		}
		return false;
	}
	

}
