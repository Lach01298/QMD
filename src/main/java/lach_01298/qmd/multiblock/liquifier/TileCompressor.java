package lach_01298.qmd.multiblock.liquifier;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import com.google.common.collect.Lists;

import nc.multiblock.cuboidal.CuboidalPartPositionType;
import nc.multiblock.heatExchanger.HeatExchanger;
import nc.multiblock.heatExchanger.HeatExchangerTubeSetting;
import nc.multiblock.heatExchanger.tile.TileHeatExchangerPart;
import nc.recipe.NCRecipes;
import nc.recipe.ProcessorRecipe;
import nc.recipe.ProcessorRecipeHandler;
import nc.recipe.RecipeInfo;
import nc.recipe.ingredient.IFluidIngredient;
import nc.tile.fluid.ITileFluid;
import nc.tile.internal.fluid.FluidConnection;
import nc.tile.internal.fluid.FluidTileWrapper;
import nc.tile.internal.fluid.GasTileWrapper;
import nc.tile.internal.fluid.Tank;
import nc.tile.internal.fluid.TankOutputSetting;
import nc.tile.internal.fluid.TankSorption;
import nc.tile.processor.IFluidProcessor;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

public class TileCompressor extends TileHeatExchangerPart implements IFluidProcessor, ITileFluid 
{

	
private final @Nonnull List<Tank> tanks = Lists.newArrayList(new Tank(32000, NCRecipes.heat_exchanger_valid_fluids.get(0)), new Tank(64000, new ArrayList<String>()));
	
	private @Nonnull FluidConnection[] fluidConnections = ITileFluid.fluidConnectionAll(Lists.newArrayList(TankSorption.NON, TankSorption.NON));
	
	private @Nonnull FluidTileWrapper[] fluidSides;
	
	private @Nonnull GasTileWrapper gasWrapper;
	
	private @Nonnull HeatExchangerTubeSetting[] tubeSettings = new HeatExchangerTubeSetting[] {HeatExchangerTubeSetting.DISABLED, HeatExchangerTubeSetting.DISABLED, HeatExchangerTubeSetting.DISABLED, HeatExchangerTubeSetting.DISABLED, HeatExchangerTubeSetting.DISABLED, HeatExchangerTubeSetting.DISABLED};
	
	public final int fluidInputSize = 1, fluidOutputSize = 1;
	
	public final int defaultProcessTime = 16000;
	public double baseProcessTime = defaultProcessTime;
	
	public double time;
	public boolean isProcessing, canProcessInputs;
	public double speedMultiplier = 0;
	
	public int inputTemperature = 0, outputTemperature = 0;
	public EnumFacing flowDir = null;
	
	public static final ProcessorRecipeHandler RECIPE_HANDLER = NCRecipes.heat_exchanger;
	protected RecipeInfo<ProcessorRecipe> recipeInfo;
	
	
	protected int tubeCount;
	
	
	
	
	public TileCompressor()
	{
		super(CuboidalPartPositionType.INTERIOR);
		fluidSides = ITileFluid.getDefaultFluidSides(this);
		gasWrapper = new GasTileWrapper(this);
	}


	
	@Override
	public void onMachineAssembled(HeatExchanger controller)
	{
		doStandardNullControllerResponse(controller);
		super.onMachineAssembled(controller);
		// if (getWorld().isRemote) return;
	}

	@Override
	public void onMachineBroken()
	{
		super.onMachineBroken();
		// if (getWorld().isRemote) return;
		// getWorld().setBlockState(getPos(), getWorld().getBlockState(getPos()), 2);
	}



	@Override
	public void refreshRecipe()
	{
		// TODO Auto-generated method stub
		
	}



	@Override
	public void refreshActivity()
	{
		// TODO Auto-generated method stub
		
	}



	@Override
	public void refreshActivityOnProduction()
	{
		// TODO Auto-generated method stub
		
	}



	@Override
	public List<Tank> getTanks()
	{
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public FluidConnection[] getFluidConnections()
	{
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public void setFluidConnections(FluidConnection[] connections)
	{
		// TODO Auto-generated method stub
		
	}



	@Override
	public FluidTileWrapper[] getFluidSides()
	{
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public GasTileWrapper getGasWrapper()
	{
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public boolean getInputTanksSeparated()
	{
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public void setInputTanksSeparated(boolean separated)
	{
		// TODO Auto-generated method stub
		
	}



	@Override
	public boolean getVoidUnusableFluidInput(int tankNumber)
	{
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public void setVoidUnusableFluidInput(int tankNumber, boolean voidUnusableFluidInput)
	{
		// TODO Auto-generated method stub
		
	}



	@Override
	public TankOutputSetting getTankOutputSetting(int tankNumber)
	{
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public void setTankOutputSetting(int tankNumber, TankOutputSetting setting)
	{
		// TODO Auto-generated method stub
		
	}



	@Override
	public List<Tank> getFluidInputs()
	{
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public List<IFluidIngredient> getFluidIngredients()
	{
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public List<IFluidIngredient> getFluidProducts()
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	
	
	

}
