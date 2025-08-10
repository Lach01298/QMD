package lach_01298.qmd.tile;

import lach_01298.qmd.config.QMDConfig;
import lach_01298.qmd.recipes.LiquidCollectorRecipes;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraftforge.fluids.FluidStack;

public class TileLiquidCollector extends TileFluidCollector
{
	public TileLiquidCollector()
	{
		super("liquid_collector", QMDConfig.processor_power[2] * 20);
	}

	@Override
	public boolean hasSufficientEnergy()
	{
		return getEnergyStored() >= QMDConfig.processor_power[2];
	}


	@Override
	public void process()
	{
		if(outputFluid != null && !getTanks().get(0).isFull())
		{
			getEnergyStorage().changeEnergyStored(-QMDConfig.processor_power[1]);

			if (getTanks().get(0).isEmpty())
			{
				getTanks().get(0).changeFluidStored(outputFluid.getFluid(),(int) (outputFluid.amount*efficiency));
			}
			else if (getTanks().get(0).getFluid().isFluidEqual(outputFluid))
			{
				getTanks().get(0).changeFluidAmount((int) (outputFluid.amount*efficiency));
			}
		}
	}

	@Override
	public void checkEfficiency()
	{
		Iterable<MutableBlockPos> checkArea = BlockPos.getAllInBoxMutable(this.pos.add(-2, -4, -2),this.pos.add(2, 0, 2));
		int fluidBlocks =0;
		int checkedBlocks =0;
		Block fluidBlock = world.getBlockState(this.pos.down()).getBlock();
		for (BlockPos otherPos : checkArea)
		{
			checkedBlocks++;
			Block block = world.getBlockState(otherPos).getBlock();
			if(fluidBlock == block)
			{
				fluidBlocks++;
			}
		}
		efficiency = (fluidBlocks)/ (double)(checkedBlocks-1);
	}

	@Override
	public void checkInputs()
	{
		IBlockState blockState = world.getBlockState(this.pos.down());
		String biome = world.getBiome(this.pos).getRegistryName().toString();
		int dimensionId = world.provider.getDimension();

		outputFluid = LiquidCollectorRecipes.getRecipe(blockState,biome,dimensionId);
	}




}
