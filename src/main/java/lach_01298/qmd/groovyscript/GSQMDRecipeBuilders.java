package lach_01298.qmd.groovyscript;

public class GSQMDRecipeBuilders
{

	public static class GSQMDProcessorRecipeBuilder extends GSQMDRecipeBuilder<GSQMDProcessorRecipeBuilder>
	{

		public GSQMDProcessorRecipeBuilder(GSQMDRecipeRegistry registry)
		{
			super(registry);
		}

		public GSQMDProcessorRecipeBuilder timeMultiplier(double timeMultiplier)
		{
			return setExtra(0, timeMultiplier);
		}

		public GSQMDProcessorRecipeBuilder powerMultiplier(double powerMultiplier)
		{
			return setExtra(1, powerMultiplier);
		}

		public GSQMDProcessorRecipeBuilder processRadiation(double processRadiation)
		{
			return setExtra(2, processRadiation);
		}
	}

	public static class GSTargetChamberRecipeBuilder extends GSQMDRecipeBuilder<GSTargetChamberRecipeBuilder>
	{

		public GSTargetChamberRecipeBuilder(GSQMDRecipeRegistry registry)
		{
			super(registry);
		}

		public GSTargetChamberRecipeBuilder maxEnergy(long maxEnergy)
		{
			return setExtra(0, maxEnergy);
		}

		public GSTargetChamberRecipeBuilder crossSection(double crossSection)
		{
			return setExtra(1, crossSection);
		}

		public GSTargetChamberRecipeBuilder energyReleased(long energyReleased)
		{
			return setExtra(2, energyReleased);
		}

	}


}