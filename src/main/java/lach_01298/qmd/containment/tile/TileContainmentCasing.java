package lach_01298.qmd.containment.tile;

import lach_01298.qmd.containment.Containment;
import nc.block.property.BlockProperties;
import nc.multiblock.cuboidal.CuboidalPartPositionType;

public class TileContainmentCasing extends TileContainmentPart
{

	public TileContainmentCasing()
	{
		super(CuboidalPartPositionType.EXTERIOR);
	}

	@Override
	public void onMachineAssembled(Containment controller)
	{
		super.onMachineAssembled(controller);
		if (!getWorld().isRemote && getPartPosition().isFrame())
		{
			if (getWorld().getBlockState(getPos()).withProperty(BlockProperties.FRAME, false) != null)
			{
				getWorld().setBlockState(getPos(),
						getWorld().getBlockState(getPos()).withProperty(BlockProperties.FRAME, true), 2);
			}
		}
	}

	@Override
	public void onMachineBroken()
	{
		if (!getWorld().isRemote && getPartPosition().isFrame())
		{
			if (getWorld().getBlockState(getPos()).withProperty(BlockProperties.FRAME, false) != null)
			{
				getWorld().setBlockState(getPos(),
						getWorld().getBlockState(getPos()).withProperty(BlockProperties.FRAME, false), 2);
			}
		}
		super.onMachineBroken();
	}

}
