package lach_01298.qmd.multiblock.accelerator.tile;

import javax.annotation.Nonnull;

import lach_01298.qmd.EnumTypes;
import lach_01298.qmd.multiblock.accelerator.Accelerator;
import nc.multiblock.cuboidal.CuboidalPartPositionType;
import nc.multiblock.heatExchanger.HeatExchangerTubeSetting;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileAcceleratorBeam extends TileAcceleratorPartBase
{

	
	private @Nonnull EnumTypes.IOType[] beamSettings = new  EnumTypes.IOType[] { EnumTypes.IOType.DISABLED,  EnumTypes.IOType.DISABLED,  EnumTypes.IOType.DISABLED,  EnumTypes.IOType.DISABLED,  EnumTypes.IOType.DISABLED,  EnumTypes.IOType.DISABLED};
	
	public TileAcceleratorBeam()
	{
		super(CuboidalPartPositionType.INTERIOR);
		
	}

	boolean isFunctional()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onMachineAssembled(Accelerator controller) {
		doStandardNullControllerResponse(controller);
		super.onMachineAssembled(controller);
	
	}
	
	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
		return oldState.getBlock() != newSate.getBlock();
	}
	
	@Override
	public void update()
	{
		// TODO Auto-generated method stub
		
	}

	public @Nonnull EnumTypes.IOType[] getBeamSettings()
	{
		return beamSettings;
	}

	public void setBeamSettings(@Nonnull EnumTypes.IOType[] settings)
	{
		beamSettings = settings;
	}

	public EnumTypes.IOType getBeamSetting(@Nonnull EnumFacing side)
	{
		return beamSettings[side.getIndex()];
	}

	public void setBeamSetting(@Nonnull EnumFacing side, @Nonnull EnumTypes.IOType setting)
	{
		beamSettings[side.getIndex()] = setting;
	}

	public void toggleBeamSetting(@Nonnull EnumFacing side)
	{
		setBeamSetting(side, getBeamSetting(side).getNextIO());
		markDirtyAndNotify();
	}

	@Override
	public NBTTagCompound writeAll(NBTTagCompound nbt) {
		super.writeAll(nbt);
		
		NBTTagCompound settingsTag = new NBTTagCompound();
		for (EnumFacing side : EnumFacing.VALUES) {
			settingsTag.setInteger("setting" + side.getIndex(), getBeamSetting(side).ordinal());
		}
		nbt.setTag("beamSettings", settingsTag);
		
	
		return nbt;
	}
	
	@Override
	public void readAll(NBTTagCompound nbt) {
		super.readAll(nbt);
		
		NBTTagCompound settingsTag = nbt.getCompoundTag("beamSettings");
		for (EnumFacing side : EnumFacing.VALUES) 
		{
			setBeamSetting(side, EnumTypes.IOType.values()[settingsTag.getInteger("setting" + side.getIndex())]);
		}	

	}
	
	
	
}
