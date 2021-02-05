package lach_01298.qmd.particleChamber;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.apache.commons.lang3.tuple.Pair;

import lach_01298.qmd.accelerator.Accelerator;
import lach_01298.qmd.capabilities.CapabilityParticleStackHandler;
import lach_01298.qmd.enums.EnumTypes.IOType;
import lach_01298.qmd.multiblock.network.ParticleChamberUpdatePacket;
import lach_01298.qmd.particle.IParticleStackHandler;
import lach_01298.qmd.particleChamber.tile.IParticleChamberController;
import lach_01298.qmd.particleChamber.tile.IParticleChamberPart;
import lach_01298.qmd.particleChamber.tile.TileParticleChamberBeamPort;
import nc.multiblock.Multiblock;
import nc.multiblock.MultiblockLogic;
import nc.multiblock.container.ContainerMultiblockController;
import nc.multiblock.tile.TileBeefAbstract.SyncReason;
import nc.tile.internal.fluid.Tank;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class ParticleChamberLogic extends MultiblockLogic<ParticleChamber, ParticleChamberLogic,IParticleChamberPart,ParticleChamberUpdatePacket>
{ 

	public static final int maxSize = 7;
	public static final int minSize = 1;
	
	
	public ParticleChamberLogic(ParticleChamber multiblock)
	{
		super(multiblock);
	
	}
	
	public ParticleChamberLogic(ParticleChamberLogic oldLogic) 
	{
		super(oldLogic);
	}

	@Override
	public String getID() 
	{
		return "";
	}

	protected ParticleChamber getMultiblock() 
	{
		return multiblock;
	}
	
	@Override
	public int getMinimumInteriorLength()
	{
		return minSize;
	}

	@Override
	public int getMaximumInteriorLength()
	{
		return maxSize;
	}

	@Override
	public void onMachineAssembled()
	{
		onChamberFormed();
	}

	public void onChamberFormed()
	{
		for (IParticleChamberController contr : getPartMap(IParticleChamberController.class).values()) 
		{
			 getMultiblock().controller = contr;
		}
		
		getMultiblock().energyStorage.setStorageCapacity(getMultiblock().BASE_MAX_ENERGY * getCapacityMultiplier());
		getMultiblock().energyStorage.setMaxTransfer(getMultiblock().BASE_MAX_ENERGY * getCapacityMultiplier());
		
		getMultiblock().tanks.get(0).setCapacity(getMultiblock().BASE_MAX_INPUT * getCapacityMultiplier());
		getMultiblock().tanks.get(1).setCapacity(getMultiblock().BASE_MAX_OUTPUT * getCapacityMultiplier());
		
		if (!getWorld().isRemote) 
		{
			refreshChamber();
			getMultiblock().updateActivity();	 
		}
	
	}

	@Override
	public void onMachineRestored()
	{
		onChamberFormed();
		
	}

	
	public int getCapacityMultiplier() 
	{
		return getMultiblock().getExteriorVolume();
	}
	
	
	@Override
	public void onMachinePaused()
	{
		onChamberBroken();
	}

	@Override
	public void onMachineDisassembled()
	{	
		onChamberBroken();
	}
	
	public void onChamberBroken()
	{
		if (!getWorld().isRemote)
		{
			getMultiblock().updateActivity();
		}
	}
	
	@Override
	public boolean isMachineWhole(Multiblock multiblock)
	{
		multiblock.setLastError("zerocore.api.nc.multiblock.validation.invalid_logic", null);
		return false;
	}

	@Override
	public void writeToLogicTag(NBTTagCompound data, SyncReason syncReason)
	{
		
	}

	@Override
	public void readFromLogicTag(NBTTagCompound data, SyncReason syncReason)
	{
		
	}

	@Override
	public ParticleChamberUpdatePacket getUpdatePacket()
	{
		return null;
	}

	@Override
	public void onPacket(ParticleChamberUpdatePacket message)
	{
		
	}

	public void onAssimilate(Multiblock assimilated)
	{	
		if (assimilated instanceof ParticleChamber)
		{
			ParticleChamber assimilatedAccelerator = (ParticleChamber) assimilated;
			getMultiblock().energyStorage.mergeEnergyStorage(assimilatedAccelerator.energyStorage);
		}
		
		if (getMultiblock().isAssembled()) 
		{
			onChamberFormed();
		}
		else 
		{
			onChamberBroken();
		}
	}

	public void onAssimilated(Multiblock assimilator)
	{	
	}

	public void refreshChamber()
	{
	
	}

	public boolean onUpdateServer()
	{
		return true;
	}

	public void onUpdateClient()
	{
		// TODO Auto-generated method stub
		
	}

	public void refreshChamberStats()
	{
		getMultiblock().resetStats();
	}

	public boolean isChamberOn()
	{
		
		return getMultiblock().beams.get(0).getParticleStack() != null;
	}

	public ContainerMultiblockController<ParticleChamber, IParticleChamberController> getContainer(EntityPlayer player)
	{
		return null;
	}

	
	protected void pull()
	{
		for(TileParticleChamberBeamPort port : getPartMap(TileParticleChamberBeamPort.class).values())
		{
		
			if(port.getIOType() == IOType.INPUT)
			{
				for(EnumFacing face : EnumFacing.HORIZONTALS)
				{
					TileEntity tile = port.getWorld().getTileEntity(port.getPos().offset(face));
					if(tile != null)
					{
						if (tile.hasCapability(CapabilityParticleStackHandler.PARTICLE_HANDLER_CAPABILITY, face.getOpposite()))
						{
							IParticleStackHandler otherStorage = tile.getCapability(CapabilityParticleStackHandler.PARTICLE_HANDLER_CAPABILITY,face.getOpposite());
							getMultiblock().beams.get(port.getIONumber()).setParticleStack(otherStorage.extractParticle(face.getOpposite()));
						}
					}
				}
			}
		}
		
	}
	
	protected void push()
	{
		for(TileParticleChamberBeamPort port : getPartMap(TileParticleChamberBeamPort.class).values())
		{
		
			if(port.getIOType() == IOType.OUTPUT)
			{
				for(EnumFacing face : EnumFacing.HORIZONTALS)
				{
					TileEntity tile = port.getWorld().getTileEntity(port.getPos().offset(face));
					if(tile != null)
					{
						if (tile.hasCapability(CapabilityParticleStackHandler.PARTICLE_HANDLER_CAPABILITY, face.getOpposite()))
						{
							IParticleStackHandler otherStorage = tile.getCapability(CapabilityParticleStackHandler.PARTICLE_HANDLER_CAPABILITY,face.getOpposite());
							otherStorage.reciveParticle(face.getOpposite(), getMultiblock().beams.get(port.getIONumber()).getParticleStack());
						}
					}
				}
			}
		}
	}

	public boolean toggleSetting(BlockPos pos,int ioNumber)
	{
		return false;	
	}

	@Override
	public List<Pair<Class<? extends IParticleChamberPart>, String>> getPartBlacklist()
	{
		return new ArrayList<>();
	}
	
	public void clearAllMaterial()
	{
		for (Tank tank : getMultiblock().tanks)
		{
			tank.setFluidStored(null);
		}
	}
	public @Nonnull List<Tank> getTanks(List<Tank> backupTanks) {
		return getMultiblock().isAssembled() ? getMultiblock().tanks : backupTanks;
	}
	
	
	

}
