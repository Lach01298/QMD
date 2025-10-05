package lach_01298.qmd.particleChamber;

import com.google.common.collect.Lists;
import lach_01298.qmd.QMD;
import lach_01298.qmd.config.QMDConfig;
import lach_01298.qmd.enums.EnumTypes.IOType;
import lach_01298.qmd.multiblock.network.*;
import lach_01298.qmd.particle.ParticleStack;
import lach_01298.qmd.particleChamber.tile.*;
import lach_01298.qmd.recipe.*;
import nc.tile.internal.fluid.Tank;
import nc.tile.multiblock.TilePartAbstract.SyncReason;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

import static lach_01298.qmd.recipes.QMDRecipes.beam_dump;
import static nc.block.property.BlockProperties.ACTIVE;


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
		if (multiblock.getExteriorLengthX() != multiblock.getExteriorLengthZ())
		{
			multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.chamber.must_be_square", null);
			return false;
		}
		if (multiblock.getExteriorLengthX() % 2 != 1)
		{
			multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.chamber.must_be_odd", null);
			return false;
		}
		
		
		
		BlockPos middle = multiblock.getExtremeCoord(false, false, false).add(multiblock.getExteriorLengthX()/2,multiblock.getExteriorLengthY()/2,multiblock.getExteriorLengthZ()/2);
		
		//target
		if (!(multiblock.WORLD.getTileEntity(middle) instanceof TileParticleChamber))
		{
			multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.chamber.must_have_target", middle);
			return false;
		}
		
		TileParticleChamber target = (TileParticleChamber) multiblock.WORLD.getTileEntity(middle);
		
		// target beams
		int ports = 0;
		for (EnumFacing face : EnumFacing.HORIZONTALS)
		{
			if (multiblock.WORLD.getTileEntity(middle.offset(face, multiblock.getExteriorLengthX() / 2)) instanceof TileParticleChamberBeamPort)
			{
				ports++;
				for (int i = 1; i <= multiblock.getExteriorLengthX() / 2 - 1; i++)
				{
					if (!(multiblock.WORLD.getTileEntity(middle.offset(face, i)) instanceof TileParticleChamberBeam))
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
		
		if(containsBlacklistedPart())
		{
			return false;
		}
		
		return true;
	}
	
	@Override
	public int getMinimumInteriorLength()
	{
		return 1;
	}
	
	public static final List<Pair<Class<? extends IParticleChamberPart>, String>> PART_BLACKLIST = Lists.newArrayList(
			Pair.of(TileParticleChamberBeam.class, QMD.MOD_ID + ".multiblock_validation.chamber.no_beams"),
			Pair.of(TileParticleChamberDetector.class, QMD.MOD_ID + ".multiblock_validation.chamber.no_detectors"),
			Pair.of(TileParticleChamberPort.class, QMD.MOD_ID + ".multiblock_validation.chamber.no_item_ports"));
	
	@Override
	public List<Pair<Class<? extends IParticleChamberPart>, String>> getPartBlacklist()
	{
		return PART_BLACKLIST;
	}
	
	@Override
	public void onChamberFormed()
	{
		onResetStats();
		
		multiblock.tanks.get(1).setCapacity(QMDConfig.particle_chamber_output_tank_capacity * getCapacityMultiplier());
		
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
		multiblock.beams.get(0).setParticleStack(null);
		pull();
		
		if (isChamberOn())
		{
			if (multiblock.energyStorage.extractEnergy(multiblock.requiredEnergy,true) == multiblock.requiredEnergy)
			{
				
			
				refreshRecipe();
				
				if(recipeInfo != null)
				{
					if(rememberedRecipeInfo != null)
					{
						if(rememberedRecipeInfo.recipe !=recipeInfo.recipe)
						{
							particleWorkDone= 0;
						}
					}
					rememberedRecipeInfo = recipeInfo;
					
					if(canProduceProduct())
					{
						multiblock.energyStorage.changeEnergyStored(-multiblock.requiredEnergy);
						particleWorkDone += multiblock.beams.get(0).getParticleStack().getAmount()*multiblock.efficiency;
						produceProduct();
					}
				}
				
			}
		}
		return super.onUpdateServer();
	}
	

	

	private boolean canProduceProduct()
	{
		FluidStack product = recipeInfo.recipe.getFluidProducts().get(0).getStack();
		if(multiblock.tanks.get(1).fill(product, false) == product.amount && multiblock.tanks.get(1).getCapacity()>= multiblock.tanks.get(1).getFluidAmount()+product.amount)
		{
			return true;
		}
		
		return false;
	}

	private void produceProduct()
	{
		recipeParticleWork = recipeInfo.recipe.getParticleIngredients().get(0).getStack().getAmount();
		particleWorkDone=Math.min(particleWorkDone, recipeParticleWork*1000);
		while(particleWorkDone >= recipeParticleWork && canProduceProduct())
		{
			FluidStack product = recipeInfo.recipe.getFluidProducts().get(0).getStack();
			multiblock.tanks.get(1).fill(product, true);
	
			particleWorkDone = Math.max(0, particleWorkDone - recipeParticleWork);
		}
	}
	

	
	

	public void onResetStats()
	{
		multiblock.efficiency =1;
		multiblock.requiredEnergy =QMDConfig.beam_dump_power;
		
	}

	
	protected void refreshRecipe()
	{
		ArrayList<ItemStack> items = new ArrayList<ItemStack>();
		ArrayList<ParticleStack> particles = new ArrayList<ParticleStack>();
		particles.add(multiblock.beams.get(0).getParticleStack());
		
		recipeInfo = beam_dump.getRecipeInfoFromInputs(items, new ArrayList<Tank>(), particles);
		
	}
	
	@Override
	public ParticleChamberUpdatePacket getMultiblockUpdatePacket()
	{
		return new BeamDumpUpdatePacket(multiblock.controller.getTilePos(), multiblock.isChamberOn,
				multiblock.requiredEnergy, multiblock.efficiency, multiblock.energyStorage,
				particleWorkDone,recipeParticleWork, multiblock.tanks, multiblock.beams);
	}
	
	@Override
	public void onMultiblockUpdatePacket(ParticleChamberUpdatePacket message)
	{
		super.onMultiblockUpdatePacket(message);
		if (message instanceof BeamDumpUpdatePacket)
		{
			BeamDumpUpdatePacket packet = (BeamDumpUpdatePacket) message;
			multiblock.beams = packet.beams;
			for (int i = 0; i < multiblock.tanks.size(); i++) multiblock.tanks.get(i).readInfo(message.tanksInfo.get(i));
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
	
	
	
	
	/*public ContainerMultiblockController<ParticleChamber, IParticleChamberController> getContainer(EntityPlayer player)
	{
		
		return new ContainerBeamDumpController(player, (TileBeamDumpController) multiblock.controller);
	}*/
	
	
}
