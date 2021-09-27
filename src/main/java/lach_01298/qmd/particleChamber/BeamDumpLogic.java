package lach_01298.qmd.particleChamber;

import static lach_01298.qmd.recipes.QMDRecipes.beam_dump;
import static nc.block.property.BlockProperties.ACTIVE;

import java.util.ArrayList;

import lach_01298.qmd.QMD;
import lach_01298.qmd.config.QMDConfig;
import lach_01298.qmd.enums.EnumTypes.IOType;
import lach_01298.qmd.multiblock.container.ContainerBeamDumpController;
import lach_01298.qmd.multiblock.network.BeamDumpUpdatePacket;
import lach_01298.qmd.multiblock.network.ParticleChamberUpdatePacket;
import lach_01298.qmd.particle.ParticleStack;
import lach_01298.qmd.particle.ParticleStorageAccelerator;
import lach_01298.qmd.particleChamber.tile.IParticleChamberController;
import lach_01298.qmd.particleChamber.tile.TileBeamDumpController;
import lach_01298.qmd.particleChamber.tile.TileParticleChamber;
import lach_01298.qmd.particleChamber.tile.TileParticleChamberBeam;
import lach_01298.qmd.particleChamber.tile.TileParticleChamberBeamPort;
import lach_01298.qmd.particleChamber.tile.TileParticleChamberEnergyPort;
import lach_01298.qmd.particleChamber.tile.TileParticleChamberFluidPort;
import lach_01298.qmd.recipe.QMDRecipe;
import lach_01298.qmd.recipe.QMDRecipeInfo;
import nc.multiblock.Multiblock;
import nc.multiblock.container.ContainerMultiblockController;
import nc.multiblock.tile.TileBeefAbstract.SyncReason;
import nc.tile.internal.fluid.Tank;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;


public class BeamDumpLogic extends ParticleChamberLogic
{
	public QMDRecipeInfo<QMDRecipe> recipeInfo;
	
	public QMDRecipeInfo<QMDRecipe> rememberedRecipeInfo;

	protected TileParticleChamber mainChamber;
	
	
	public long particleWorkDone = 0;
	public long recipeParticleWork = 100;
	
	public BeamDumpLogic(ParticleChamberLogic oldLogic)
	{
		super(oldLogic);
		
		/*
		beam 0 = input particle
		tank 0 = other
		tank 1 = output fluid
		*/
	}
	
	@Override
	public String getID() 
	{
		return "beam_dump";
	}
	
	@Override
	public int getMaximumInteriorLength()
	{
		return 1;
	}
	
	@Override
	public boolean isMachineWhole()
	{
		
		//sizing
		if (getMultiblock().getExteriorLengthX() != getMultiblock().getExteriorLengthZ())
		{
			multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.chamber.must_be_square", null);
			return false;
		}
		if (getMultiblock().getExteriorLengthX() % 2 != 1)
		{
			multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.chamber.must_be_odd", null);
			return false;
		}
		
		
		
		BlockPos middle =getMultiblock().getExtremeCoord(false, false, false).add(getMultiblock().getExteriorLengthX()/2,getMultiblock().getExteriorLengthY()/2,getMultiblock().getExteriorLengthZ()/2);
		
		//target
		if (!(getMultiblock().WORLD.getTileEntity(middle) instanceof TileParticleChamber))
		{
			multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.chamber.must_have_target", middle);
			return false;
		}
		
		TileParticleChamber target = (TileParticleChamber) getMultiblock().WORLD.getTileEntity(middle);
		
		// target beams
		int ports = 0;
		for (EnumFacing face : EnumFacing.HORIZONTALS)
		{
			if (getMultiblock().WORLD.getTileEntity(middle.offset(face, getMultiblock().getExteriorLengthX() / 2)) instanceof TileParticleChamberBeamPort)
			{
				ports++;
				for (int i = 1; i <= getMultiblock().getExteriorLengthX() / 2 - 1; i++)
				{
					if (!(getMultiblock().WORLD.getTileEntity(middle.offset(face, i)) instanceof TileParticleChamberBeam))
					{
						multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.chamber.must_be_beam", middle.offset(face, i));
						return false;
					}
				}
			}
		}
		
		if(ports != getPartMap(TileParticleChamberBeamPort.class).size())
		{
			multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.chamber.beam_port_wrong_spot", null);
			return false;
		}
		
		// has input
		int inputs =0;
		for(TileParticleChamberBeamPort tile :getPartMap(TileParticleChamberBeamPort.class).values())
		{
			if(tile.getIOType() == IOType.INPUT)
			{
				inputs++;
			}
		}
		if(inputs != 1)
		{
			multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.chamber.must_have_input_beam",null);
			return false;
		}
	
		if(inputs != ports)
		{
			multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.chamber.beam_dump.only_input_beam",null);
			return false;
		}
		
		boolean outlet = false;
		for (TileParticleChamberFluidPort port : getPartMap(TileParticleChamberFluidPort.class).values())
		{
			if (!port.getBlockState(port.getPos()).getValue(ACTIVE).booleanValue())
			{
				
			}
			else
			{
				outlet = true;
			}
		}

		if (!outlet)
		{
			multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.chamber.beam_dump.must_have_fluid_output", null);
			return false;
		}
		
		
		// Energy Ports
		if (getPartMap(TileParticleChamberEnergyPort.class).size() < 1)
		{
			multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.need_energy_ports", null);
			return false;
		}
		
		
		return true;
	}
	
	@Override
	public int getMinimumInteriorLength()
	{
		return 1;
	}
	
	
	@Override
	public void onChamberFormed()
	{
		onResetStats();
		
		getMultiblock().tanks.get(1).setCapacity(QMDConfig.particle_chamber_output_tank_capacity * getCapacityMultiplier());
		
		if (!getWorld().isRemote)
		{
			for (TileParticleChamber target : getPartMap(TileParticleChamber.class).values())
			{
				this.mainChamber = target;
			}


			BlockPos input = null;

			for (TileParticleChamberBeamPort tile : getPartMap(TileParticleChamberBeamPort.class).values())
			{	
				tile.setIONumber(0);
			}

		}

		super.onChamberFormed();
	}
	
	
	public void onMachineDisassembled()
	{	
		mainChamber = null;
		for(TileParticleChamberBeamPort tile :getPartMap(TileParticleChamberBeamPort.class).values())
		{
			tile.setIONumber(0);
		}
		super.onMachineDisassembled();
	}
	
	

	@Override
	public boolean onUpdateServer() 
	{
		getMultiblock().beams.get(0).setParticleStack(null);
		pull();
		
		
		
		isChamberOn();
		
		if (getMultiblock().isChamberOn)
		{
			if (getMultiblock().energyStorage.extractEnergy(getMultiblock().requiredEnergy,true) == getMultiblock().requiredEnergy)
			{
				
			
				refreshRecipe();
				
				if(recipeInfo != null)
				{
					if(rememberedRecipeInfo != null)
					{
						if(rememberedRecipeInfo.getRecipe() !=recipeInfo.getRecipe())
						{
							particleWorkDone= 0;
						}	
					}
					rememberedRecipeInfo = recipeInfo;
					
					if(canProduceProduct())
					{
						getMultiblock().energyStorage.changeEnergyStored(-getMultiblock().requiredEnergy);
						particleWorkDone += getMultiblock().beams.get(0).getParticleStack().getAmount()*getMultiblock().efficiency;
						produceProduct();
					}	
				}
					
			}
		}
		return super.onUpdateServer();
	}
	

	

	private boolean canProduceProduct()
	{
		FluidStack product = recipeInfo.getRecipe().getFluidProducts().get(0).getStack();
		if(getMultiblock().tanks.get(1).fill(product, false) == product.amount)
		{
			return true;
		}
		
		return false;
	}

	private void produceProduct()
	{
		recipeParticleWork = recipeInfo.getRecipe().getParticleIngredients().get(0).getStack().getAmount();
		particleWorkDone=Math.min(particleWorkDone, recipeParticleWork*64);
		while(particleWorkDone >= recipeParticleWork && canProduceProduct())
		{
			FluidStack product = recipeInfo.getRecipe().getFluidProducts().get(0).getStack();
			getMultiblock().tanks.get(1).fill(product, true);	
	
			particleWorkDone = Math.max(0, particleWorkDone - recipeParticleWork);
		}
	}
	

	
	

	public void onResetStats()
	{
		getMultiblock().efficiency =1;
		getMultiblock().requiredEnergy =QMDConfig.beam_dump_power;
		
	}

	
	protected void refreshRecipe() 
	{
		ArrayList<ItemStack> items = new ArrayList<ItemStack>();
		ArrayList<ParticleStack> particles = new ArrayList<ParticleStack>();
		particles.add(getMultiblock().beams.get(0).getParticleStack());
		
		recipeInfo = beam_dump.getRecipeInfoFromInputs(items, new ArrayList<Tank>(), particles);
		
	}
	
	@Override
	public ParticleChamberUpdatePacket getUpdatePacket()
	{
		return new BeamDumpUpdatePacket(getMultiblock().controller.getTilePos(), getMultiblock().isChamberOn,
				getMultiblock().requiredEnergy, getMultiblock().efficiency, getMultiblock().energyStorage,
				particleWorkDone,recipeParticleWork, getMultiblock().tanks, getMultiblock().beams);
	}
	
	@Override
	public void onPacket(ParticleChamberUpdatePacket message)
	{
		super.onPacket(message);
		if (message instanceof BeamDumpUpdatePacket)
		{
			BeamDumpUpdatePacket packet = (BeamDumpUpdatePacket) message;
			getMultiblock().beams = packet.beams;
			for (int i = 0; i < getMultiblock().tanks.size(); i++) getMultiblock().tanks.get(i).readInfo(message.tanksInfo.get(i));
			this.particleWorkDone = packet.particleWorkDone;
			this.recipeParticleWork = packet.recipeParticleWork;
		}
	}
	
	
	
	
	@Override
	public void writeToLogicTag(NBTTagCompound logicTag, SyncReason syncReason)
	{
		super.writeToLogicTag(logicTag, syncReason);
		
		logicTag.setLong("particleCount", particleWorkDone);
		logicTag.setLong("recipeParticleCount", recipeParticleWork);
	}

	@Override
	public void readFromLogicTag(NBTTagCompound logicTag, SyncReason syncReason)
	{
		super.readFromLogicTag(logicTag, syncReason);
		
		particleWorkDone=logicTag.getLong("particleCount");
		recipeParticleWork=logicTag.getLong("recipeParticleCount");
	}
	
	
	
	
	public ContainerMultiblockController<ParticleChamber, IParticleChamberController> getContainer(EntityPlayer player)
	{
		
		return new ContainerBeamDumpController(player, (TileBeamDumpController) getMultiblock().controller);
	}
	
	
}
