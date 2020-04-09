package lach_01298.qmd.multiblock.particleChamber.tile;


import lach_01298.qmd.multiblock.TileCuboidalOrToroidalMultiblockPart;
import lach_01298.qmd.multiblock.accelerator.Accelerator;
import lach_01298.qmd.multiblock.particleChamber.ParticleChamber;
import nc.multiblock.cuboidal.CuboidalPartPositionType;
import nc.multiblock.cuboidal.TileCuboidalMultiblockPart;
import net.minecraft.nbt.NBTTagCompound;

public abstract class TileParticleChamberPart extends TileCuboidalMultiblockPart<ParticleChamber> implements IParticleChamberPart
{


	public TileParticleChamberPart(CuboidalPartPositionType positionType)
	{
		super(ParticleChamber.class, positionType);
	}

	@Override
	public ParticleChamber createNewMultiblock()
	{
		return new ParticleChamber(getWorld());
	}

	@Override
	public void onMachineAssembled(ParticleChamber controller)
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
