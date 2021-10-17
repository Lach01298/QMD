package lach_01298.qmd.tile;

import lach_01298.qmd.config.QMDConfig;
import lach_01298.qmd.recipes.AtmosphereCollectorRecipes;
import nc.tile.generator.TileItemFluidGenerator;
import nc.tile.internal.energy.EnergyConnection;
import nc.util.MaterialHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class TileAtmosphereCollector extends  TileItemFluidGenerator
{

	private double efficiency = 0D;
	
	public TileAtmosphereCollector()
	{
		super("atmosphere_collector", 0, 0, 0, 1, 0, defaultItemSorptions(0, 0),  defaultTankCapacities(32000, 0, 1), defaultTankSorptions(0, 1),
				null, QMDConfig.processor_power[1]*20, null);
		setEnergyConnectionAll(EnergyConnection.IN);
	}

	@Override
	public void update()
	{
		if (!world.isRemote) 
		{
			time++;
			
			if(time >= 100)
			{
				checkEfficency();
				time = 0;
			}
			
			
			boolean wasProcessing = isProcessing, shouldUpdate = false;
			isProcessing = isProcessing();
			if (isProcessing) 
			{
				process();
			}
			
			if (wasProcessing != isProcessing) 
			{
				shouldUpdate = true;
				
			}
			if (shouldUpdate) 
			{
				markDirty();
			}
		}
	}

	
	@Override
	public boolean setRecipeStats()
	{
		return true;
	}
	
	@Override
	public void refreshRecipe() 
	{
		
	}
	
	@Override
	public void refreshActivity() 
	{
	
	}
	
	@Override
	public boolean isProcessing() 
	{
		return readyToProcess();
	}
	
	@Override
	public boolean readyToProcess() 
	{
		return hasSufficientEnergy();
	}
	
	public boolean hasSufficientEnergy() 
	{
		return getEnergyStored() >= QMDConfig.processor_power[1];
	}
	
	
	@Override
	public void process() 
	{
		FluidStack fluidStack = AtmosphereCollectorRecipes.getRecipe(world.provider.getDimension());
		if(fluidStack != null && !getTanks().get(0).isFull())
		{
			getEnergyStorage().changeEnergyStored((int) -QMDConfig.processor_power[1]);
			
			if (getTanks().get(0).isEmpty()) 
			{
				getTanks().get(0).changeFluidStored(fluidStack.getFluid(),(int) (fluidStack.amount*efficiency));
			}
			else if (getTanks().get(0).getFluid().isFluidEqual(fluidStack)) 
			{
				getTanks().get(0).changeFluidAmount((int) (fluidStack.amount*efficiency));
			}
		}	
	}
	
	public void checkEfficency()
	{
		Iterable<MutableBlockPos> checkArea = BlockPos.getAllInBoxMutable(this.pos.add(-2, -2, -2),this.pos.add(2, 2, 2));
		int occlusiveBlocks =0;
		int checkedBlocks =0;
		for (BlockPos otherPos : checkArea)
		{
			checkedBlocks++;
			IBlockState state = world.getBlockState(otherPos);
			if(!MaterialHelper.isEmpty(state.getMaterial()) && (state.isOpaqueCube() || !state.getMaterial().isOpaque()))
			{
				occlusiveBlocks ++;
			}
		}
		efficiency = 1-(occlusiveBlocks-1)/ (double)(checkedBlocks-1);
		
		
	}
	
	
	public int getCollectionRate()
	{
		if(isProcessing())
		{
			FluidStack fluidStack = AtmosphereCollectorRecipes.getRecipe(world.provider.getDimension());
			if(fluidStack != null)
			{
				return (int) (fluidStack.amount*efficiency);
			}
		}
		return 0;
	}
	
	public Fluid getCollectionFluid()
	{
		if(isProcessing())
		{
			FluidStack fluidStack = AtmosphereCollectorRecipes.getRecipe(world.provider.getDimension());
			if(fluidStack != null)
			{
				return fluidStack.getFluid();
			}
		}
		return null;
	}
	
	

	@Override
	public int getSinkTier() 
	{
		return 10;
	}
	
	@Override
	public int getSourceTier() 
	{
		return 1;
	}

	@Override
	public NBTTagCompound writeAll(NBTTagCompound nbt) {
		super.writeAll(nbt);
		nbt.setDouble("efficiency", efficiency);
	
		return nbt;
	}

	
	@Override
	public void readAll(NBTTagCompound nbt) 
	{
		super.readAll(nbt);
		efficiency = nbt.getDouble("efficiency");
	
	}
}
