package lach_01298.qmd.multiblock.accelerator.tile;

import lach_01298.qmd.multiblock.accelerator.Accelerator;
import nc.block.property.BlockProperties;
import nc.multiblock.cuboidal.CuboidalPartPositionType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileAcceleratorCasing extends TileAcceleratorPart
{

	public TileAcceleratorCasing()
	{
		super(CuboidalPartPositionType.EXTERIOR);
	}



	@Override
	public void onMachineAssembled(Accelerator controller)
	{
		super.onMachineAssembled(controller);
		
	}

	@Override
	public void onMachineBroken()
	{
		super.onMachineBroken();
		
	}



	@Override
	public void update()
	{
		// TODO Auto-generated method stub
		
	}

}
