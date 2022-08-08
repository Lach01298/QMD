package lach_01298.qmd.vacuumChamber.tile;

public interface IVacuumChamberComponent extends IVacuumChamberPart
{
	
	public int getMaxOperatingTemp();

	public boolean isFunctional();
	
	public void setFunctional(boolean func);
	
	public int getHeating();
	public int getPower();
	
	public default boolean isToHot()
	{
		if (getMultiblock().getTemperature() > this.getMaxOperatingTemp())
		{
			return true;
		}
		return false;
	}
}
