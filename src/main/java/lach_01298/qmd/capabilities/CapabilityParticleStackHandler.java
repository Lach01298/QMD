package lach_01298.qmd.capabilities;

import lach_01298.qmd.particle.IParticleStackHandler;
import lach_01298.qmd.particle.IParticleStorage;
import lach_01298.qmd.particle.ParticleStack;
import lach_01298.qmd.particle.ParticleStorage;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;

public class CapabilityParticleStackHandler
{
	@CapabilityInject(IParticleStackHandler.class)
    public static Capability<IParticleStackHandler> PARTICLE_HANDLER_CAPABILITY = null;
	
    public static void register()
    {
        CapabilityManager.INSTANCE.register(IParticleStackHandler.class, new DefaultParticleHandlerStorage(), () -> new ParticleStorage(null, Integer.MAX_VALUE));
       
    }
    
    private static class DefaultParticleHandlerStorage<T extends IParticleStackHandler> implements Capability.IStorage<T> {
        @Override
		public NBTBase writeNBT(Capability<T> capability, T instance, EnumFacing side)
		{
			if (!(instance instanceof IParticleStorage))
				throw new RuntimeException("IParticleStackHandler instance does not implement IParticleStorage");
			NBTTagCompound nbt = new NBTTagCompound();
			IParticleStorage tank = (IParticleStorage) instance;
			ParticleStack particle = tank.getParticleStack();
			if (particle != null)
			{
				particle.writeToNBT(nbt);
			}
			else
			{
				nbt.setString("Empty", "");
			}
			nbt.setInteger("MaxEnergy", tank.getMaxEnergy());
			nbt.setInteger("Capacity", tank.getCapacity());
			nbt.setInteger("MinEnergy", tank.getMinEnergy());
			return nbt;
		}

        @Override
		public void readNBT(Capability<T> capability, T instance, EnumFacing side, NBTBase nbt)
		{
			if (!(instance instanceof ParticleStorage))
				throw new RuntimeException("IParticleStackHandler instance is not instance of ParticleStorage");
			NBTTagCompound tags = (NBTTagCompound) nbt;
			ParticleStorage tank = (ParticleStorage) instance;
			tank.setMaxEnergy(tags.getInteger("MaxEnergy"));
			tank.setCapacity(tags.getInteger("Capacity"));
			tank.setMinEnergy(tags.getInteger("MinEnergy"));
			tank.readFromNBT(tags);
		}
    }
}
