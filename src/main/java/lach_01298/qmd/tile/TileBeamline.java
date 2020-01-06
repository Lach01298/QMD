package lach_01298.qmd.tile;

import lach_01298.qmd.multiblock.accelerator.Accelerator;
import lach_01298.qmd.multiblock.accelerator.tile.IAcceleratorController;
import lach_01298.qmd.pipe.PipeBeamline;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing.Axis;

import static lach_01298.qmd.block.BlockProperties.AXIS_HORIZONTAL;

public class TileBeamline extends TilePipePart<PipeBeamline> 
{

	public TileBeamline()
	{
		super(PipeBeamline.class);
	}

	@Override
	public void update()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public PipeBeamline createNewMultiblock()
	{
		Axis axis = getWorld().getBlockState(pos).getValue(AXIS_HORIZONTAL);
		return new PipeBeamline(getWorld(),axis);
	}





}
