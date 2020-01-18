package lach_01298.qmd.multiblock.particleChamber.tile;

import lach_01298.qmd.multiblock.particleChamber.ParticleChamber;
import nc.block.property.BlockProperties;
import nc.multiblock.cuboidal.CuboidalPartPositionType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileParticleChamberGlass extends TileParticleChamberPart
{

	public TileParticleChamberGlass()
	{
		super(CuboidalPartPositionType.EXTERIOR);
	}


	@Override
	public void update() {
		// TODO Auto-generated method stub
		
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