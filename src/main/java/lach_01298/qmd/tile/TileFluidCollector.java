package lach_01298.qmd.tile;

import nc.tile.internal.energy.EnergyConnection;
import nclegacy.tile.TileItemFluidGeneratorLegacy;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;


public abstract class TileFluidCollector extends TileItemFluidGeneratorLegacy
{
	protected double efficiency = 0D;

	protected FluidStack outputFluid;

	public TileFluidCollector(String name, int capacity)
	{
		super(name, 0, 0, 0, 0, 0, defaultItemSorptions(0, 0), defaultTankCapacities(32000, 0, 1), defaultTankSorptions(0, 1), null, capacity, null);
		setEnergyConnectionAll(EnergyConnection.IN);
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
	public NBTTagCompound writeAll(NBTTagCompound nbt)
	{
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

	@Override
	public void update()
	{
		if (!world.isRemote)
		{
			time--;
			if(time <= 0)
			{
				checkInputs();
				if (outputFluid != null)
				{
					checkEfficiency();
				}
				time = 100;
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

			pushFluid();

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
		return false;
	}


	public void checkEfficiency()
	{

	}


	public void checkInputs()
	{

	}



	public int getCollectionRate()
	{
		if(isProcessing())
		{

			if(outputFluid != null)
			{
				return (int) (outputFluid.amount*efficiency);
			}
		}
		return 0;
	}

	public Fluid getCollectionFluid()
	{
		if(isProcessing())
		{
			if(outputFluid != null)
			{
				return outputFluid.getFluid();
			}
		}
		return null;
	}

}





