package lach_01298.qmd.accelerator.block;

import lach_01298.qmd.accelerator.tile.TileAcceleratorIonSource;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockAcceleratorLaserIonSource extends BlockAcceleratorSource
{

	public BlockAcceleratorLaserIonSource()
	{
		super();
	}

	@Override
	public TileEntity createNewTileEntity(World world, int metadata)
	{
		return new TileAcceleratorIonSource.Laser();
	}


}
