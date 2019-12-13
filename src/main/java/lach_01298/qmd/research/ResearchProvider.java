package lach_01298.qmd.research;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class ResearchProvider implements ICapabilitySerializable<NBTBase>
{
	@CapabilityInject(IResearch.class)
	public static final Capability<Research> Research_CAP = null;

	private Research instance = Research_CAP.getDefaultInstance();

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing)
	{
		return capability == Research_CAP;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing)
	{
		return capability == Research_CAP ? Research_CAP.<T>cast(this.instance) : null;
	}

	@Override
	public NBTBase serializeNBT()
	{
		return Research_CAP.getStorage().writeNBT(Research_CAP, this.instance, null);
	}

	@Override
	public void deserializeNBT(NBTBase nbt)
	{
		Research_CAP.getStorage().readNBT(Research_CAP, this.instance, null, nbt);
	}
}