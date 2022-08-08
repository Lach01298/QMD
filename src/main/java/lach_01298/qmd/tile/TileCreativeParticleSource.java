package lach_01298.qmd.tile;

import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.collect.Lists;

import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import lach_01298.qmd.capabilities.CapabilityParticleStackHandler;
import lach_01298.qmd.gui.GUI_ID;
import lach_01298.qmd.machine.network.CreativeParticleSourceUpdatePacket;
import lach_01298.qmd.particle.IParticleStackHandler;
import lach_01298.qmd.particle.ITileParticleStorage;
import lach_01298.qmd.particle.ParticleStorage;
import lach_01298.qmd.particle.ParticleStorageSource;
import nc.network.tile.TileUpdatePacket;
import nc.tile.ITileGui;
import nc.tile.NCTile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;

public class TileCreativeParticleSource extends NCTile implements ITileParticleStorage, ITickable, ITileGui<CreativeParticleSourceUpdatePacket>
{
	private final @Nonnull List<ParticleStorageSource> particleBeams;

	protected Set<EntityPlayer> playersToUpdate;

	public TileCreativeParticleSource()
	{
		super();

		playersToUpdate = new ObjectOpenHashSet<EntityPlayer>();
		particleBeams = Lists.newArrayList(new ParticleStorageSource());
	}

	@Override
	public NBTTagCompound writeAll(NBTTagCompound nbt)
	{
		super.writeAll(nbt);
		writeBeams(particleBeams,nbt);

		return nbt;
	}

	@Override
	public void readAll(NBTTagCompound nbt)
	{
		super.readAll(nbt);
		readBeams(particleBeams,nbt);
	}

	// Capability

	@Override
	public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing side)
	{
		if (capability == CapabilityParticleStackHandler.PARTICLE_HANDLER_CAPABILITY)
		{
			return true;
		}
		return super.hasCapability(capability, side);
	}

	@Override
	public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing side)
	{
		if (capability == CapabilityParticleStackHandler.PARTICLE_HANDLER_CAPABILITY)
		{
			if (!getParticleBeams().isEmpty())
			{
				return (T) getParticleBeams().get(0);
			}
			return null;
		}

		return super.getCapability(capability, side);
	}

	@Override
	public void update()
	{
		
		if(!world.isRemote)
		{
			if(!getIsRedstonePowered())
			{
				for (EnumFacing face : EnumFacing.values())
				{
					TileEntity tile = world.getTileEntity(this.pos.offset(face));
					if (tile != null)
					{
						if (tile.hasCapability(CapabilityParticleStackHandler.PARTICLE_HANDLER_CAPABILITY, face.getOpposite()))
						{
							IParticleStackHandler otherStorage = tile.getCapability(CapabilityParticleStackHandler.PARTICLE_HANDLER_CAPABILITY, face.getOpposite());
							otherStorage.reciveParticle(face.getOpposite(), this.particleBeams.get(0).getParticleStack());
						}
					}

				}
			}
		}	
	}

	@Override
	public List<? extends ParticleStorage> getParticleBeams()
	{
		return particleBeams;
	}
	
	
	public  String getParticleName()
	{
		if(particleBeams.get(0).getParticleStack() != null)
		{
			if(particleBeams.get(0).getParticleStack().getParticle() != null)
			{
				return particleBeams.get(0).getParticleStack().getParticle().getName();
			}
			
		}
		
		return "";
	}
	
	public int getParticleAmount()
	{
		if(particleBeams.get(0).getParticleStack() != null)
		{
			return particleBeams.get(0).getParticleStack().getAmount();
		}
		
		return 0;
	}
	
	public long getParticleEnergy()
	{
		if(particleBeams.get(0).getParticleStack() != null)
		{
			return particleBeams.get(0).getParticleStack().getMeanEnergy();
		}
		
		return 0;
	}
	
	public double getParticleFocus()
	{
		if(particleBeams.get(0).getParticleStack() != null)
		{
			return particleBeams.get(0).getParticleStack().getFocus();
		}
		
		return 0;
	}
	


	@Override
	public int getGuiID()
	{
		return GUI_ID.CREATIVE_SOURCE;
	}

	@Override
	public Set<EntityPlayer> getTileUpdatePacketListeners()
	{
		return playersToUpdate;
	}

	@Override
	public CreativeParticleSourceUpdatePacket getTileUpdatePacket()
	{
		return new CreativeParticleSourceUpdatePacket(pos, particleBeams);
	}

	@Override
	public void onTileUpdatePacket(CreativeParticleSourceUpdatePacket message)
	{
		if(message instanceof CreativeParticleSourceUpdatePacket)
		{
			CreativeParticleSourceUpdatePacket mess = (CreativeParticleSourceUpdatePacket) message;
			for(int i = 0; i< mess.beams.size(); i++)
			{
				particleBeams.set(i, mess.beams.get(i));
			}	
		}		
	}
	
	
	
	public NBTTagCompound writeBeams(List<ParticleStorageSource> beams, NBTTagCompound data)
	{
		for (int i = 0; i < beams.size(); i++)
		{
			beams.get(i).writeToNBT(data, i);
		}
		
		return data;
	}

	public void readBeams(List<ParticleStorageSource> beams, NBTTagCompound data)
	{
		for (int i = 0; i < beams.size(); i++)
		{
			beams.get(i).readFromNBT(data, i);
		}
	}

	public void setParticleBeams(List<ParticleStorageSource> beams)
	{
		for(int i = 0; i<beams.size(); i++)
		{
			particleBeams.set(i, beams.get(i));
		}	
		
	}

}
