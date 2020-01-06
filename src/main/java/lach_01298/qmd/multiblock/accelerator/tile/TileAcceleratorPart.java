package lach_01298.qmd.multiblock.accelerator.tile;


import lach_01298.qmd.multiblock.TileCuboidalOrToroidalMultiblockPart;
import lach_01298.qmd.multiblock.accelerator.Accelerator;
import nc.multiblock.TileMultiblockPart;
import nc.multiblock.cuboidal.CuboidalPartPositionType;
import net.minecraft.nbt.NBTTagCompound;

public abstract class TileAcceleratorPart extends TileCuboidalOrToroidalMultiblockPart<Accelerator> implements IAcceleratorPart
{


	public TileAcceleratorPart(CuboidalPartPositionType positionType)
	{
		super(Accelerator.class, positionType, 5);
	}

	@Override
	public Accelerator createNewMultiblock()
	{
		return new Accelerator(getWorld());
	}

	@Override
	public void onMachineAssembled(Accelerator controller)
	{
		doStandardNullControllerResponse(controller);
		super.onMachineAssembled(controller);
		
	}

	@Override
	public void onMachineBroken()
	{
		super.onMachineBroken();
	}


}
