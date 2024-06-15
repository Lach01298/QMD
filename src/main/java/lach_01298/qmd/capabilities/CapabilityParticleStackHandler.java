package lach_01298.qmd.capabilities;

import lach_01298.qmd.particle.*;
import net.minecraft.nbt.*;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.*;


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
			nbt.setLong("MaxEnergy", tank.getMaxEnergy());
			nbt.setInteger("Capacity", tank.getCapacity());
			nbt.setLong("MinEnergy", tank.getMinEnergy());
			return nbt;
		}

        @Override
		public void readNBT(Capability<T> capability, T instance, EnumFacing side, NBTBase nbt)
		{
			if (!(instance instanceof ParticleStorage))
				throw new RuntimeException("IParticleStackHandler instance is not instance of ParticleStorage");
			NBTTagCompound tags = (NBTTagCompound) nbt;
			ParticleStorage tank = (ParticleStorage) instance;
			tank.setMaxEnergy(tags.getLong("MaxEnergy"));
			tank.setCapacity(tags.getInteger("Capacity"));
			tank.setMinEnergy(tags.getLong("MinEnergy"));
			tank.readFromNBT(tags);
		}
    }
}
