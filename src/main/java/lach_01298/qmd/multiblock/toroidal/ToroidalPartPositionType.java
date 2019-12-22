package lach_01298.qmd.multiblock.toroidal;

public enum ToroidalPartPositionType {
	WALL,
	FRAME,
	INTERIOR,
	EXTERIOR,
	ALL;
	
	public boolean isGoodForWall() {
		return this == WALL || this == EXTERIOR || this == ALL;
	}
	
	public boolean isGoodForFrame() {
		return this == FRAME || this == EXTERIOR || this == ALL;
	}
	
	public boolean isGoodForInterior() {
		return this == INTERIOR || this == ALL;
	}
}
