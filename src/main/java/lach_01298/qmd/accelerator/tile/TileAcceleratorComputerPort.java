package lach_01298.qmd.accelerator.tile;

import lach_01298.qmd.accelerator.*;
import lach_01298.qmd.particle.ParticleStack;
import li.cil.oc.api.machine.*;
import li.cil.oc.api.network.SimpleComponent;
import nc.multiblock.cuboidal.CuboidalPartPositionType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Optional;

import java.util.*;

@Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "opencomputers")
public class TileAcceleratorComputerPort extends TileAcceleratorPart implements SimpleComponent
{

	@Optional.Method(modid = "opencomputers")
	public String getComponentName()
	{
		return "qmd_accelerator";
	}

	public TileAcceleratorComputerPort()
	{
		super(CuboidalPartPositionType.EXTERIOR);
	}

	@Override
	public void onMachineAssembled(Accelerator controller)
	{
		doStandardNullControllerResponse(controller);
		super.onMachineAssembled(controller);
	}

	@Override
	public void onMachineBroken()
	{
		super.onMachineBroken();
	}

	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState)
	{
		return oldState.getBlock() != newState.getBlock();
	}

	
	//general stats
	@Callback(doc = "--function():bool Returns true if structure is complete.")
	@Optional.Method(modid = "opencomputers")
	public Object[] isComplete(Context context, Arguments args)
	{
		return new Object[] { isMultiblockAssembled() };
	}

	@Callback(doc = "--function():bool Returns true if accelerator is on.")
	@Optional.Method(modid = "opencomputers")
	public Object[] isAcceleratorOn(Context context, Arguments args)
	{
		return new Object[] { getLogic().isAcceleratorOn() };
	}

	@Callback(doc = "--function():string Returns accelerators logic ID if accelerator is assembled.")
	@Optional.Method(modid = "opencomputers")
	public Object[] getAcceleratorType(Context context, Arguments args)
	{
		return new Object[] { isMultiblockAssembled() ? getLogic().getID() : "" };
	}
	
	@Callback(doc = "--function():int Returns number of RF Cavities.")
	@Optional.Method(modid = "opencomputers")
	public Object[] getNumberOfRfCavity(Context context, Arguments args)
	{
		return new Object[] { isMultiblockAssembled() ? getMultiblock().RFCavityNumber : 0 };
	}

	@Callback(doc = "--function():int Returns number of dipole magnets.")
	@Optional.Method(modid = "opencomputers")
	public Object[] getNumberOfDipole(Context context, Arguments args)
	{
		return new Object[] { isMultiblockAssembled() ? getMultiblock().dipoleNumber : 0 };
	}

	@Callback(doc = "--function():int Returns number of quadrupole magnets.")
	@Optional.Method(modid = "opencomputers")
	public Object[] getNumberOfQuadrupole(Context context, Arguments args)
	{
		return new Object[] { isMultiblockAssembled() ? getMultiblock().quadrupoleNumber : 0 };
	}
	
	@Callback(doc = "--function():int Returns accelerators current temperature.")
	@Optional.Method(modid = "opencomputers")
	public Object[] getTemperature(Context context, Arguments args)
	{
		return new Object[] { isMultiblockAssembled() ? getMultiblock().getTemperature() : 0};
	}
	
	@Callback(doc = "--function():int Returns accelerators maximum operating temperature.")
	@Optional.Method(modid = "opencomputers")
	public Object[] getMaxTemperature(Context context, Arguments args)
	{
		return new Object[] { isMultiblockAssembled() ? getMultiblock().maxOperatingTemp : 0};
	}
	
	
	@Callback(doc = "--function():table Returns infomation about the heat buffer of the accelerator. (stored_heat, heat_capacity)")
	@Optional.Method(modid = "opencomputers")
	public Object[] getHeatBufferInfo(Context context, Arguments args)
	{
		Map<String, Object> statsData = new HashMap<String, Object>();
		statsData.put("heat_stored", isMultiblockAssembled() ? getMultiblock().heatBuffer.getHeatStored() : 0);
		statsData.put("heat_capacity", isMultiblockAssembled() ? getMultiblock().heatBuffer.getHeatCapacity() : 0);
		
		return new Object[] { statsData };
	}
	@Callback(doc = "--function():table Returns infomation about the cooling of the accelerator. (cooling_fluid, cooling, max_coolant_in, max_coolant_out)")
	@Optional.Method(modid = "opencomputers")
	public Object[] getCoolingInfo(Context context, Arguments args)
	{
		Map<String, Object> statsData = new HashMap<String, Object>();
		statsData.put("cooling_fluid",isMultiblockAssembled() && getMultiblock().coolingRecipeInfo != null ? getMultiblock().coolingRecipeInfo.getRecipe().getFluidIngredients().get(0).getStack().getLocalizedName(): "");
		statsData.put("cooling", isMultiblockAssembled() ? getMultiblock().cooling : 0);
		statsData.put("maxCoolantIn", isMultiblockAssembled() ? getMultiblock().maxCoolantIn : 0);
		statsData.put("maxCoolantOut", isMultiblockAssembled() ? getMultiblock().maxCoolantOut : 0);
		
		return new Object[] { statsData };
	}
	
	@Callback(doc = "--function():table Returns infomation about the heating of the accelerator. (internal_heating, external_heating, max_external_heating, ambient_temperature)")
	@Optional.Method(modid = "opencomputers")
	public Object[] getHeatingInfo(Context context, Arguments args)
	{
		Map<String, Object> statsData = new HashMap<String, Object>();
		statsData.put("internal_heating",isMultiblockAssembled() ? getMultiblock().rawHeating: 0);
		statsData.put("external_heating", isMultiblockAssembled() ? getMultiblock().getExternalHeating() : 0);
		statsData.put("max_external_heating", isMultiblockAssembled() ? getMultiblock().getMaxExternalHeating() : 0);
		statsData.put("ambient_temperature", isMultiblockAssembled() ? getMultiblock().ambientTemp : 0);
		return new Object[] { statsData };
	}
	
	@Callback(doc = "--function():table Returns infomation about the size of the accelerator. (x_length, y_length, z_length, volume, surface_area)")
	@Optional.Method(modid = "opencomputers")
	public Object[] getSizeInfo(Context context, Arguments args)
	{
		Map<String, Object> statsData = new HashMap<String, Object>();
		statsData.put("x_length",isMultiblockAssembled() ? getMultiblock().getExteriorLengthX(): 0);
		statsData.put("y_length",isMultiblockAssembled() ? getMultiblock().getExteriorLengthY(): 0);
		statsData.put("z_length",isMultiblockAssembled() ? getMultiblock().getExteriorLengthZ(): 0);
		statsData.put("volume",isMultiblockAssembled() ? getMultiblock().getExteriorVolume(): 0);
		statsData.put("surface_area",isMultiblockAssembled() ? getMultiblock().getExteriorSurfaceArea(): 0);

		return new Object[] { statsData };
	}
	
	@Callback(doc = "--function():table Returns infomation about the beam of the accelerator. (beam_length, beam_radius)")
	@Optional.Method(modid = "opencomputers")
	public Object[] getBeamInfo(Context context, Arguments args)
	{
		Map<String, Object> statsData = new HashMap<String, Object>();
		statsData.put("beam_length",isMultiblockAssembled() ? getLogic().getBeamLength(): 0);
		statsData.put("beam_radius",isMultiblockAssembled() ? getLogic().getBeamRadius(): 0);

		return new Object[] { statsData };
	}
	

	@Callback(doc = "--function():table Returns infomation about the energy and energy usage of the accelerator. (energy_required, energy_stored, energy_capacity, energy_efficiency)")
	@Optional.Method(modid = "opencomputers")
	public Object[] getEnergyInfo(Context context, Arguments args)
	{
		Map<String, Object> statsData = new HashMap<String, Object>();
		statsData.put("energy_required", isMultiblockAssembled() ? getMultiblock().requiredEnergy : 0);
		statsData.put("energy_stored", isMultiblockAssembled() ? getMultiblock().energyStorage.getEnergyStored() : 0);
		statsData.put("energy_capacity", isMultiblockAssembled() ? getMultiblock().energyStorage.getMaxEnergyStored() : 0);
		statsData.put("energy_efficiency", isMultiblockAssembled() ? getMultiblock().efficiency : 0);
		
		return new Object[] { statsData };
	}
	
	
	@Callback(doc = "--function():table Returns general accelerator statistics. (accelerating_voltage, dipole_strength, quadrupole_strength, input_particle_min_energy)")
	@Optional.Method(modid = "opencomputers")
	public Object[] getStats(Context context, Arguments args)
	{
		Map<String, Object> statsData = new HashMap<String, Object>();
		statsData.put("accelerating_voltage", isMultiblockAssembled() ? getMultiblock().acceleratingVoltage : 0);
		statsData.put("dipole_strength", isMultiblockAssembled() ? getMultiblock().dipoleStrength : 0);
		statsData.put("quadrupole_strength", isMultiblockAssembled() ? getMultiblock().quadrupoleStrength : 0);
		statsData.put("input_particle_min_energy", isMultiblockAssembled() ? getMultiblock().beams.get(0).getMinEnergy() : 0);
		
		return new Object[] { statsData };
	}

	

	// Particle stuff
	@Callback(doc = "--function():bool Returns if the accelerator has a particle stack")
	@Optional.Method(modid = "opencomputers")
	public Object[] hasParticle(Context context, Arguments args)
	{
		return new Object[] { getMultiblock().beams.get(1).getParticleStack() != null };
	}
	
	@Callback(doc = "--function():table Returns input particle stack parameters.(type, amount, energy, focus)")
	@Optional.Method(modid = "opencomputers")
	public Object[] getInputParticleInfo(Context context, Arguments args)
	{
		Map<String, Object> infoData = new HashMap<String, Object>();
		if(getMultiblock().beams.get(0).getParticleStack() != null)
		{
			infoData.put("type", getMultiblock().beams.get(0).getParticleStack().getParticle().getName());
			infoData.put("amount", getMultiblock().beams.get(0).getParticleStack().getAmount());
			infoData.put("energy", getMultiblock().beams.get(0).getParticleStack().getMeanEnergy());
			infoData.put("focus", getMultiblock().beams.get(0).getParticleStack().getFocus());
		}
		
		
		
		return new Object[] { infoData };
	}
	
	@Callback(doc = "--function():table Returns output particle stack parameters.(type, amount, energy, focus)")
	@Optional.Method(modid = "opencomputers")
	public Object[] getOutputParticleInfo(Context context, Arguments args)
	{
		Map<String, Object> infoData = new HashMap<String, Object>();
		
		if(getMultiblock().beams.get(1).getParticleStack() != null)
		{
			infoData.put("type", getMultiblock().beams.get(1).getParticleStack().getParticle().getName());
			infoData.put("amount", getMultiblock().beams.get(1).getParticleStack().getAmount());
			infoData.put("energy", getMultiblock().beams.get(1).getParticleStack().getMeanEnergy());
			infoData.put("focus", getMultiblock().beams.get(1).getParticleStack().getFocus());
		}
		
		
		return new Object[] { infoData };
	}
	
	@Callback(doc = "--function():table Returns synchrotron port particle stack parameters.(type, amount, energy, focus)")
	@Optional.Method(modid = "opencomputers")
	public Object[] getSynchrotronParticleInfo(Context context, Arguments args)
	{
		Map<String, Object> infoData = new HashMap<String, Object>();
		
		if(isMultiblockAssembled() && getMultiblock().controller.getLogicID() == "ring_accelerator")
		{
			if(getMultiblock().beams.get(2).getParticleStack() != null)
			{
				infoData.put("type", getMultiblock().beams.get(2).getParticleStack().getParticle().getName());
				infoData.put("amount", getMultiblock().beams.get(2).getParticleStack().getAmount());
				infoData.put("energy", getMultiblock().beams.get(2).getParticleStack().getMeanEnergy());
				infoData.put("focus", getMultiblock().beams.get(2).getParticleStack().getFocus());
			}
		}
		
	
		return new Object[] { infoData };
	}
	
	
	@Callback(doc = "--function():table Returns infomation about the particle type.(type, mass, energy, charge, spin, interacts_with_em, interacts_with_weak, interacts_with_strong)")
	@Optional.Method(modid = "opencomputers")
	public Object[] getParticleInfo(Context context, Arguments args)
	{
		Map<String, Object> infoData = new HashMap<String, Object>();
		if(getMultiblock().beams.get(1).getParticleStack() != null)
		{
			infoData.put("type", getMultiblock().beams.get(1).getParticleStack().getParticle().getName());
			infoData.put("mass", getMultiblock().beams.get(1).getParticleStack().getParticle().getMass());
			infoData.put("charge", getMultiblock().beams.get(1).getParticleStack().getParticle().getCharge());
			infoData.put("spin", getMultiblock().beams.get(1).getParticleStack().getParticle().getSpin());
			infoData.put("interacts_with_em", getMultiblock().beams.get(1).getParticleStack().getParticle().interactsWithEM());
			infoData.put("interacts_with_weak", getMultiblock().beams.get(1).getParticleStack().getParticle().interactsWithWeak());
			infoData.put("interacts_with_strong", getMultiblock().beams.get(1).getParticleStack().getParticle().interactsWithStrong());
		}
		
		
		
		
		return new Object[] { infoData };
	}
	
	
	@Callback(doc = "--function():bool Returns if the accelerator has an ion source")
	@Optional.Method(modid = "opencomputers")
	public Object[] hasIonSource(Context context, Arguments args)
	{
		boolean hasSource = false;
		if(isMultiblockAssembled() && getMultiblock().controller.getLogicID() == "linear_accelerator")
		{
			LinearAcceleratorLogic logic = (LinearAcceleratorLogic) getMultiblock().controller.getLogic();
			if(logic.getSource() != null)
			{
				hasSource = true;
			}
		}
		
		return new Object[] { hasSource };
	}
	
	@Callback(doc = "--function():table Returns infomation about the ion source. (source_item, source_fluid, particle_type, amount, energy, focus)")
	@Optional.Method(modid = "opencomputers")
	public Object[] getIonSourceInfo(Context context, Arguments args)
	{
		Map<String, Object> infoData = new HashMap<String, Object>();
		if(isMultiblockAssembled() && getMultiblock().controller.getLogicID() == "linear_accelerator")
		{
			LinearAcceleratorLogic logic = (LinearAcceleratorLogic) getMultiblock().controller.getLogic();
			if(logic.getSource() != null)
			{
				infoData.put("source_item",  logic.getSource().getInventoryStacks().get(0).getDisplayName()); // TODO test!
				infoData.put("source_fluid", logic.getSource().getTanks().get(0).getFluidLocalizedName());
				
				if(logic.recipeInfo.getRecipe() != null)
				{
					ParticleStack particle = logic.recipeInfo.getRecipe().getParticleProducts().get(0).getStack();
					infoData.put("particle_type",logic.recipeInfo != null ? particle.getParticle().getName() : "");
					infoData.put("amount", particle.getAmount());
					infoData.put("energy", particle.getMeanEnergy());
					infoData.put("focus", particle.getFocus());
				}
				else
				{
					infoData.put("particle_type", "");
					infoData.put("amount", 0);
					infoData.put("energy", 0l);
					infoData.put("focus", 0d);
				}
			}
			
		}
		
		return new Object[] { infoData };
	}
	
	//accelerator control
	@Callback(doc = "--function(int energy_percentage):int changes output particle energy to this percentage of the max energy (For decelerators it outputs the opposite percentage e.g. 15% -> 85% output energy). Can only be between 5 and 100 inclusive or 0 to turn of accelerator entirely. For beam diverters this only turns it on/off. Returns what it was set to")
	@Optional.Method(modid = "opencomputers")
	public Object[] setEnergyPercentage(Context context, Arguments args)
	{
		if(!isMultiblockAssembled()) return new Object[] {0};
		getMultiblock().computerControlled = true;
		int percentage = args.checkInteger(0);
		if(percentage < 5)
		{
			percentage = 0;
		}
		if(percentage > 100 )
		{
			percentage = 100;
		}
		
		getMultiblock().energyPercentage = percentage;

		return new Object[] {percentage};
	}
	
	@Callback(doc = "--function(bool computer_controlled):bool turns computer controlled mode on/off. With this on accelerator controller ignores redstone. Returns what it was set to")
	@Optional.Method(modid = "opencomputers")
	public Object[] setComputerControlled(Context context, Arguments args)
	{
		if(!isMultiblockAssembled()) return new Object[] {false};
		boolean computerCotrolled = args.checkBoolean(0);
		getMultiblock().computerControlled = computerCotrolled;

		return new Object[] {computerCotrolled};
	}
	
	
	@Callback(doc = "--function():int Returns the energyPercentage set.")
	@Optional.Method(modid = "opencomputers")
	public Object[] getEnergyPercentage(Context context, Arguments args)
	{
		if(!isMultiblockAssembled()) return new Object[] {0};
		return new Object[] {getMultiblock().energyPercentage};
	}
	
	@Callback(doc = "--function():bool Returns if accelerator is in computer controlled mode")
	@Optional.Method(modid = "opencomputers")
	public Object[] isComputerControlled(Context context, Arguments args)
	{
		if(!isMultiblockAssembled()) return new Object[] {false};
		
		return new Object[] {getMultiblock().computerControlled};
	}
	
	@Callback(doc = "--function(x,y,z):bool Returns if position (x,y,z) is a beam port")
	@Optional.Method(modid = "opencomputers")
	public Object[] isBeamPort(Context context, Arguments args)
	{
		if(!isMultiblockAssembled()) return new Object[] {false};
		BlockPos pos = new BlockPos(args.checkInteger(0),args.checkInteger(1),args.checkInteger(2));
		for (TileAcceleratorBeamPort port :getMultiblock().getPartMap(TileAcceleratorBeamPort.class).values())
		{
			if(port.getTilePos().equals(pos))
			{
				return new Object[] {true};
			}
		}
		
		return new Object[] {false};
	}
	
	@Callback(doc = "--function(x,y,z):bool Returns if beam port mode was switched")
	@Optional.Method(modid = "opencomputers")
	public Object[] switchBeamPort(Context context, Arguments args)
	{
		if(!isMultiblockAssembled()) return new Object[] {false};
		BlockPos pos = new BlockPos(args.checkInteger(0),args.checkInteger(1),args.checkInteger(2));
		
		for (TileAcceleratorBeamPort port :getMultiblock().getPartMap(TileAcceleratorBeamPort.class).values())
		{
			if(port.getTilePos().equals(pos))
			{
				port.setTrigger();
				port.getMultiblock().switchIO();
				return new Object[] {true};
			}
		}
		
		return new Object[] {false};
	}
	
	@Callback(doc = "--function(x,y,z):string Returns beam port's mode. Returns invalid if invalid beam port otherwise either input, output or disabled")
	@Optional.Method(modid = "opencomputers")
	public Object[] getBeamPortMode(Context context, Arguments args)
	{
		if(!isMultiblockAssembled()) return new Object[] {"invalid"};
		BlockPos pos = new BlockPos(args.checkInteger(0),args.checkInteger(1),args.checkInteger(2));
		for (TileAcceleratorBeamPort port :getMultiblock().getPartMap(TileAcceleratorBeamPort.class).values())
		{
			if(port.getTilePos().equals(pos))
			{
				return new Object[] {port.getIOType().getName()};
			}
		}
		
		return new Object[] {"invalid"};
	}
	
	@Callback(doc = "--function(x,y,z):string Returns beam port's switch mode. Returns invalid if invalid beam port otherwise either input or output")
	@Optional.Method(modid = "opencomputers")
	public Object[] getBeamPortSwitchMode(Context context, Arguments args)
	{
		if(!isMultiblockAssembled()) return new Object[] {"invalid"};
		BlockPos pos = new BlockPos(args.checkInteger(0),args.checkInteger(1),args.checkInteger(2));
		
		for (TileAcceleratorBeamPort port :getMultiblock().getPartMap(TileAcceleratorBeamPort.class).values())
		{
			if(port.getTilePos().equals(pos))
			{
				return new Object[] {port.getSetting().getName()};
			}
		}
		
		return new Object[] {"invalid"};
	}
	
}
