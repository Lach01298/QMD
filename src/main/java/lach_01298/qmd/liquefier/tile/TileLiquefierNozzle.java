package lach_01298.qmd.liquefier.tile;

import com.google.common.collect.Lists;
import lach_01298.qmd.liquefier.LiquefierLogic;
import lach_01298.qmd.recipes.QMDRecipes;
import nc.multiblock.cuboidal.CuboidalPartPositionType;
import nc.multiblock.hx.HeatExchanger;
import nc.multiblock.hx.HeatExchangerLogic;
import nc.multiblock.hx.HeatExchangerTubeNetwork;
import nc.recipe.BasicRecipe;
import nc.recipe.BasicRecipeHandler;
import nc.recipe.NCRecipes;
import nc.recipe.multiblock.CondenserRecipes;
import nc.tile.hx.TileHeatExchangerPart;
import nc.tile.internal.fluid.Tank;
import nc.tile.internal.processor.AbstractProcessorElement;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class TileLiquefierNozzle extends TileHeatExchangerPart
{

	public @Nonnull NonNullList<ItemStack> inventoryStacks = NonNullList.withSize(0, ItemStack.EMPTY);
	public final @Nonnull List<Tank> backupTanks = Lists.newArrayList(new Tank(HeatExchanger.BASE_MAX_INPUT, QMDRecipes.liquefier.validFluids.get(0)), new Tank(HeatExchanger.BASE_MAX_OUTPUT, null));


	public @Nullable HeatExchangerTubeNetwork network;

	double baseEnergy = 50D;
	int inversionTemperature = 600;
	int gasTemperature = 400;
	double pressureCoefficient = 0.25;

	public TileLiquefierNozzle()
	{
		super(CuboidalPartPositionType.INTERIOR);
	}

	@Override
	public void onMachineAssembled(HeatExchanger multiblock)
	{
		doStandardNullControllerResponse(multiblock);
		super.onMachineAssembled(multiblock);
	}


	public final AbstractProcessorElement processor = new AbstractProcessorElement()
	{
		double heatTransferRate;

		@Override
		public World getWorld()
		{
			return world;
		}
		@Override
		public BasicRecipeHandler getRecipeHandler()
		{
			return QMDRecipes.liquefier;
		}

		@Override
		public void setRecipeStats(@Nullable BasicRecipe recipe)
		{
			if (recipe == null)
			{
				baseProcessTime = 1000D;
				baseEnergy = 50D;
				gasTemperature = 400;
				inversionTemperature = 600;
				pressureCoefficient = 0.25;
			}
			else
			{
				baseProcessTime = (double) recipeInfo.recipe.getExtras().get(1);
				baseEnergy = (double) recipeInfo.recipe.getExtras().get(0);
				inversionTemperature = (int) recipeInfo.recipe.getExtras().get(2);
				gasTemperature = (int) recipeInfo.recipe.getExtras().get(3);
				pressureCoefficient = (double) recipeInfo.recipe.getExtras().get(4);
			}
		}

		@Override
		public @Nonnull NonNullList<ItemStack> getInventoryStacks() {
			return inventoryStacks;
		}

		@Override
		public @Nonnull List<Tank> getTanks()
		{
			if(getLogic() instanceof LiquefierLogic)
			{
				LiquefierLogic liquefier = (LiquefierLogic) getLogic();
				return liquefier.tanks;
			}

			return backupTanks;
		}

		@Override
		public boolean getConsumesInputs() {
			return true;
		}

		@Override
		public boolean getLosesProgress() {
			return false;
		}

		@Override
		public int getItemInputSize() {
			return 0;
		}

		@Override
		public int getFluidInputSize() {
			return 1;
		}

		@Override
		public int getItemOutputSize() {
			return 0;
		}

		@Override
		public int getFluidOutputSize() {
			return 1;
		}

		@Override
		public int getItemInputSlot(int index) {
			return index;
		}

		@Override
		public int getFluidInputTank(int index) {
			return index;
		}

		@Override
		public int getItemOutputSlot(int index) {
			return index;
		}

		@Override
		public int getFluidOutputTank(int index) {
			return index + 1;
		}

		@Override
		public double getSpeedMultiplier()
		{
			HeatExchanger hx = getMultiblock();
			if (hx == null)
			{
				return 0D;
			}

			if(!(getLogic() instanceof LiquefierLogic))
			{
				return 0D;
			}
			LiquefierLogic liquefier = (LiquefierLogic) getLogic();

			int coolantInputTemperature = (int) hx.shellRecipe.recipe.getExtras().get(1);
			int coolantOutputTemperature = (int) hx.shellRecipe.recipe.getExtras().get(2);
			if(coolantInputTemperature > inversionTemperature || coolantOutputTemperature > gasTemperature)
			{
				return 0D;
			}

			double pressureEfficiency= 1-1/(pressureCoefficient*liquefier.pressure+1);

			double maxRate = baseProcessTime*liquefier.pressure/(10D * liquefier.heatEfficiency * pressureEfficiency);
			heatTransferRate = (gasTemperature-coolantOutputTemperature)/2.0 * network.baseCoolingMultiplier;

			return Math.min(maxRate,heatTransferRate);

		}

		@Override
		public boolean isHalted()
		{
			HeatExchanger hx = getMultiblock();
			return hx == null || !hx.isExchangerOn;
		}

		@Override
		public void produceProducts()
		{
			int consumedAmount = consumedTanks.get(0).getFluidAmount();
			getMultiblock().tubeInputRate += consumedAmount;

			super.produceProducts();
		}

		@Override
		public void onResumeProcessingState() {
			getMultiblock().packetFlag |= 1;
		}

		@Override
		public void onChangeProcessingState() {
			getMultiblock().packetFlag |=  1;
		}

		@Override
		public void process()
		{
			heatTransferRate  = 0D;

			double speedMultiplier = getSpeedMultiplier();
			double maxProcessCount = speedMultiplier / baseProcessTime;

			time += speedMultiplier;

			if((getLogic() instanceof LiquefierLogic))
			{
				LiquefierLogic liquefier = (LiquefierLogic) getLogic();

				int energyUsed = (int) (getSpeedMultiplier()*baseEnergy/liquefier.energyEfficiency);
				liquefier.energyStorage.changeEnergyStored(-energyUsed);
			}



			int processCount = 0;
			while (time >= baseProcessTime)
			{


				finishProcess();
				processCount++;
			}

			HeatExchanger hx = getMultiblock();
			if (hx != null)
			{
				hx.heatTransferRate += heatTransferRate * (processCount == 0 ? 1D : processCount / maxProcessCount);
			}
		}
		@Override
		public void refreshActivityOnProduction()
		{
			super.refreshActivityOnProduction();
			if (!canProcessInputs) {
				getMultiblock().refreshFlag = true;
			}
		}

	};

}