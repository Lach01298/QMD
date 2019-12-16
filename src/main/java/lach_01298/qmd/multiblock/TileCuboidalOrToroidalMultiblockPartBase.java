package lach_01298.qmd.multiblock;

import nc.multiblock.MultiblockBase;
import nc.multiblock.cuboidal.CuboidalMultiblockTileBase;
import nc.multiblock.cuboidal.CuboidalPartPositionType;
import nc.multiblock.validation.IMultiblockValidator;

public abstract class TileCuboidalOrToroidalMultiblockPartBase<T extends MultiblockBase> extends CuboidalOrToroidalMultiblockTileBase<T>
{

	protected final CuboidalPartPositionType positionType;

	public TileCuboidalOrToroidalMultiblockPartBase(Class<T> tClass, CuboidalPartPositionType positionType, int thickness)
	{
		super(tClass, thickness);
		this.positionType = positionType;
	}

	@Override
	public void onMachineActivated()
	{

	}

	@Override
	public void onMachineDeactivated()
	{

	}

	public CuboidalPartPositionType getPartPositionType()
	{
		return positionType;
	}

	@Override
	public boolean isGoodForFrame(IMultiblockValidator validator)
	{
		if (positionType.isGoodForFrame())
			return true;
		setStandardLastError(validator);
		return false;
	}

	@Override
	public boolean isGoodForSides(IMultiblockValidator validator)
	{
		if (positionType.isGoodForWall())
			return true;
		setStandardLastError(validator);
		return false;
	}

	@Override
	public boolean isGoodForTop(IMultiblockValidator validator)
	{
		if (positionType.isGoodForWall())
			return true;
		setStandardLastError(validator);
		return false;
	}

	@Override
	public boolean isGoodForBottom(IMultiblockValidator validator)
	{
		if (positionType.isGoodForWall())
			return true;
		setStandardLastError(validator);
		return false;
	}

	@Override
	public boolean isGoodForInterior(IMultiblockValidator validator)
	{
		if (positionType.isGoodForInterior())
			return true;
		setStandardLastError(validator);
		return false;
	}
}