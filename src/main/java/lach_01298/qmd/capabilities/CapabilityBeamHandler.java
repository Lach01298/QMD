package lach_01298.qmd.capabilities;

import lach_01298.qmd.QMD;
import lach_01298.qmd.research.ResearchProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler.DefaultFluidHandlerStorage;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;


public class CapabilityBeamHandler
{
	   @CapabilityInject(IBeamHandler.class)
	    public static Capability<IBeamHandler> BEAM_HANDLER_CAPABILITY = null;

	    public static void register()
	    {
	        CapabilityManager.INSTANCE.register(IBeamHandler.class, new DefaultBeamHandlerStorage<>(), () -> new ParticleBeam());

	        CapabilityManager.INSTANCE.register(IFluidHandlerItem.class, new DefaultFluidHandlerStorage<>(), () -> new FluidHandlerItemStack(new ItemStack(Items.BUCKET), Fluid.BUCKET_VOLUME));
	    }

	    private static class DefaultBeamHandlerStorage<T extends IBeamHandler> implements Capability.IStorage<T> {
	        @Override
			public NBTBase writeNBT(Capability<T> capability, T instance, EnumFacing side)
			{
				if (!(instance instanceof IFluidTank))
					throw new RuntimeException("IFluidHandler instance does not implement IFluidTank");
				NBTTagCompound nbt = new NBTTagCompound();
				IFluidTank tank = (IFluidTank) instance;
				FluidStack fluid = tank.getFluid();
				if (fluid != null)
				{
					fluid.writeToNBT(nbt);
				}
				else
				{
					nbt.setString("Empty", "");
				}
				nbt.setInteger("Capacity", tank.getCapacity());
				return nbt;
			}

	        @Override
			public void readNBT(Capability<T> capability, T instance, EnumFacing side, NBTBase nbt)
			{
				if (!(instance instanceof FluidTank))
					throw new RuntimeException("IFluidHandler instance is not instance of FluidTank");
				NBTTagCompound tags = (NBTTagCompound) nbt;
				FluidTank tank = (FluidTank) instance;
				tank.setCapacity(tags.getInteger("Capacity"));
				tank.readFromNBT(tags);
			}
	    }
	}