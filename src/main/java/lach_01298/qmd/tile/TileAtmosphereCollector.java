package lach_01298.qmd.tile;

import lach_01298.qmd.config.QMDConfig;
import lach_01298.qmd.recipes.AtmosphereCollectorRecipes;
import nc.util.MaterialHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;

public class TileAtmosphereCollector extends TileFluidCollector
{
	public TileAtmosphereCollector()
	{
		super("atmosphere_collector", QMDConfig.processor_power[1] * 20);
	}

	public boolean hasSufficientEnergy()
	{
		return getEnergyStored() >= QMDConfig.processor_power[1];
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
		Iterable<MutableBlockPos> checkArea = BlockPos.getAllInBoxMutable(this.pos.add(-2, 0, -2),this.pos.add(2, 4, 2));
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

	@Override
	public void checkInputs()
	{
		String biome = world.getBiome(this.pos).getRegistryName().toString();
		int dimensionId = world.provider.getDimension();

		outputFluid = AtmosphereCollectorRecipes.getRecipe(biome,dimensionId);
	}


}
