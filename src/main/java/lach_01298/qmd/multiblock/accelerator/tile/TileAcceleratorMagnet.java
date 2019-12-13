package lach_01298.qmd.multiblock.accelerator.tile;

import javax.annotation.Nullable;

import lach_01298.qmd.config.QMDConfig;
import lach_01298.qmd.multiblock.accelerator.Accelerator;
import nc.config.NCConfig;
import nc.multiblock.cuboidal.CuboidalPartPositionType;
import nc.multiblock.fission.FissionCluster;
import nc.multiblock.fission.FissionReactor;
import nc.multiblock.fission.tile.IFissionCoolingComponent;
import nc.multiblock.fission.tile.TileFissionPartBase;
import nc.util.BlockPosHelper;
import net.minecraft.util.EnumFacing;

public abstract class TileAcceleratorMagnet extends TileAcceleratorPartBase implements IAcceleratorComponent
{

	public final double strength;
	public final double efficiency;
	public final int heat;
	public final int basePower;
	public final String name;

	public boolean isInValidPosition = false;

	public TileAcceleratorMagnet(double strength,double efficiency, int heat, int basePower, String name)
	{
		super(CuboidalPartPositionType.INTERIOR);
		this.strength = strength;
		this.efficiency =efficiency;
		this.heat = heat;
		this.basePower = basePower;
		this.name = name;
	}

	

	public static class Copper extends TileAcceleratorMagnet
	{

		public Copper()
		{
			super(QMDConfig.magnet_strength[0], QMDConfig.magnet_efficiency[0], QMDConfig.magnet_heat_generated[0], QMDConfig.magnet_base_power[0], "copper");
		}

		@Override
		public boolean isMagnetValid()
		{
			//TODO
			return true;
		}
	}

	public static class MagnesiumDiboride extends TileAcceleratorMagnet
	{

		public MagnesiumDiboride()
		{
			super(QMDConfig.magnet_strength[1], QMDConfig.magnet_efficiency[1], QMDConfig.magnet_heat_generated[1], QMDConfig.magnet_base_power[1], "magnesium_diboride");
		}

		@Override
		public boolean isMagnetValid()
		{
			//TODO
			return true;
		}
	}

	public static class NiobiumTin extends TileAcceleratorMagnet
	{

		public NiobiumTin()
		{
			super(QMDConfig.magnet_strength[2], QMDConfig.magnet_efficiency[2], QMDConfig.magnet_heat_generated[2], QMDConfig.magnet_base_power[2], "niobium_tin");
		}

		@Override
		public boolean isMagnetValid()
		{
			//TODO
			return true;
		}
	}

	
	

	@Override
	public void onMachineAssembled(Accelerator controller)
	{
		doStandardNullControllerResponse(controller);
		super.onMachineAssembled(controller);
		// if (getWorld().isRemote) return;
	}

	@Override
	public void onMachineBroken()
	{
		super.onMachineBroken();
		// if (getWorld().isRemote) return;
		// getWorld().setBlockState(getPos(), getWorld().getBlockState(getPos()), 2);
	}


	@Override
	public void update()
	{
		if (!isAdded)
		{
			onAdded();
			isAdded = true;
		}
		if (isMarkedDirty)
		{
			markDirty();
			isMarkedDirty = false;
		}
	}


	@Override
	public boolean isFunctional()
	{
		return isInValidPosition;
	}

	public abstract boolean isMagnetValid();

	@Override
	public void resetStats()
	{
		isInValidPosition = false;
	}


}
