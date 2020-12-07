package lach_01298.qmd.accelerator.tile;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import lach_01298.qmd.accelerator.Accelerator;
import lach_01298.qmd.accelerator.LinearAcceleratorLogic;
import lach_01298.qmd.accelerator.RFCavity;
import lach_01298.qmd.accelerator.RingAcceleratorLogic;
import lach_01298.qmd.particle.ParticleStack;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.SimpleComponent;
import nc.block.property.BlockProperties;
import nc.multiblock.cuboidal.CuboidalPartPositionType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Optional;

import java.util.HashMap;
import java.util.Map;

@Optional.Interface(
		iface = "li.cil.oc.api.network.SimpleComponent",
		modid = "opencomputers"
)
public class TileAcceleratorComputerPort extends TileAcceleratorPart implements SimpleComponent
{

	@Optional.Method(
			modid = "opencomputers"
	)
	public String getComponentName() {
		return "qmd_accelerator";
	}

	public TileAcceleratorComputerPort()
	{
		super(CuboidalPartPositionType.EXTERIOR);
	}

	@Override
	public void onMachineAssembled(Accelerator controller)
	{
		super.onMachineAssembled(controller);
		super.onMachineAssembled(controller);
		if (!getWorld().isRemote && getPartPosition().isFrame())
		{
			if (getWorld().getBlockState(getPos()).withProperty(BlockProperties.FRAME, false) != null)
			{
				getWorld().setBlockState(getPos(),
						getWorld().getBlockState(getPos()).withProperty(BlockProperties.FRAME, true), 2);
			}
		}
	}

	@Override
	public void onMachineBroken()
	{
		if (!getWorld().isRemote && getPartPosition().isFrame())
		{
			if (getWorld().getBlockState(getPos()).withProperty(BlockProperties.FRAME, false) != null)
			{
				getWorld().setBlockState(getPos(),
						getWorld().getBlockState(getPos()).withProperty(BlockProperties.FRAME, false), 2);
			}
		}
		super.onMachineBroken();
	}
	
	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState)
	{
		return oldState.getBlock() != newState.getBlock();
	}

	@Callback(doc = "--function():bool Is Acclelerator complete")
	@Optional.Method(
			modid = "opencomputers"
	)
	public Object[] isComplete(Context context, Arguments args) {
		return new Object[]{this.isMultiblockAssembled()};
	}

	@Callback(doc = "--function():bool")
	@Optional.Method(
			modid = "opencomputers"
	)
	public Object[] isAcceleratorOn(Context context, Arguments args) {
		return new Object[]{getLogic().isAcceleratorOn()};
	}

	@Callback(doc = "--function():int")
	@Optional.Method(
			modid = "opencomputers"
	)
	public Object[] getNumberOfRfCavity(Context context, Arguments args) {
		return new Object[]{this.isMultiblockAssembled() ? getMultiblock().RFCavityNumber : 0};
	}

	@Callback(doc = "--function():int")
	@Optional.Method(
			modid = "opencomputers"
	)
	public Object[] getNumberOfDipole(Context context, Arguments args) {
		return new Object[]{this.isMultiblockAssembled() ? getMultiblock().dipoleNumber : 0};
	}

	@Callback(doc = "--function():int")
	@Optional.Method(
			modid = "opencomputers"
	)
	public Object[] getNumberOfQuadrupole(Context context, Arguments args) {
		return new Object[]{this.isMultiblockAssembled() ? getMultiblock().quadrupoleNumber : 0};
	}

	@Callback(doc = "--function():table Returns accelerator statistics")
	@Optional.Method(
			modid = "opencomputers"
	)
	public Object[] getStats(Context context, Arguments args) {
		Map<String, Object> statsData = new HashMap<String, Object>();
		statsData.put("temperature", isMultiblockAssembled() ? getMultiblock().getTemperature() : 0);
		statsData.put("heat_stored", isMultiblockAssembled() ? getMultiblock().heatBuffer.getHeatStored() : 0);
		statsData.put("heat_capacity", isMultiblockAssembled() ? getMultiblock().heatBuffer.getHeatCapacity() : 0);
		statsData.put("cooling", isMultiblockAssembled() ? getMultiblock().cooling : 0);
		statsData.put("cooling_fluid", isMultiblockAssembled() ? getMultiblock().coolingRecipeInfo.getRecipe().getFluidIngredients().get(0).getStack().getLocalizedName() : "");
		statsData.put("dipole_strength", isMultiblockAssembled() ? getMultiblock().dipoleStrength : 0);
		statsData.put("quadrupole_strength", isMultiblockAssembled() ? getMultiblock().quadrupoleStrength : 0);
		statsData.put("accelerating_voltage", isMultiblockAssembled() ? getMultiblock().acceleratingVoltage : 0);
		statsData.put("energy_stored", isMultiblockAssembled() ? getMultiblock().energyStorage.getEnergyStored() : 0);
		statsData.put("energy_capacity", isMultiblockAssembled() ? getMultiblock().energyStorage.getMaxEnergyStored() : 0);
		statsData.put("energy_required", isMultiblockAssembled() ? getMultiblock().requiredEnergy : 0);
		return new Object[]{statsData};
	}

	@Callback(doc = "--function():table Returns source item, particle parameters")
	@Optional.Method(
			modid = "opencomputers"
	)
	public Object[] getParticleInfo(Context context, Arguments args) {
		Map<String, Object> infoData = new HashMap<String, Object>();
		if(getMultiblock().controller.getLogicID() == "linear_accelerator") {
			LinearAcceleratorLogic logic = (LinearAcceleratorLogic) getMultiblock().controller.getLogic();
			infoData.put("source",
					logic.getSource() != null ?
							logic.getSource().getInventoryStacks().get(0).getDisplayName() : "");
			ParticleStack particle = logic.recipeInfo.getRecipe().getParticleProducts().get(0).getStack();
			infoData.put("particle_recipe",
					logic.recipeInfo != null ?
							particle.getParticle().getName() : "");
			infoData.put("focus", getMultiblock().dipoleStrength + getMultiblock().quadrupoleStrength + particle.getFocus());
			infoData.put("accelerating_voltage", getMultiblock().acceleratingVoltage + particle.getMeanEnergy());
			infoData.put("amount", particle.getAmount());

		}
		if(getMultiblock().controller.getLogicID() == "ring_accelerator") {
			RingAcceleratorLogic logic = (RingAcceleratorLogic) getMultiblock().controller.getLogic();

			ParticleStack particle = getMultiblock().beams.get(0).getParticleStack();
			infoData.put("particle_recipe",
					particle != null ?
							particle.getParticle().getName() : "");
			infoData.put("focus", getMultiblock().dipoleStrength + getMultiblock().quadrupoleStrength + particle.getFocus());
			infoData.put("accelerating_voltage", getMultiblock().acceleratingVoltage + particle.getMeanEnergy());
			infoData.put("amount", particle.getAmount());

		}


		return new Object[]{infoData};
	}




	@Callback(doc = "--function(int id, bool state):bool Enable/disable RF cavity.")
	@Optional.Method(
			modid = "opencomputers"
	)
	public Object[] manageRFCavity(Context context, Arguments args) {

		int id = args.checkInteger(0);
		boolean state = args.checkBoolean(1);

		Long2ObjectMap<RFCavity> rfCavities = new Long2ObjectOpenHashMap<>();
		int i = 1;
		for ( Long key : getMultiblock().getRFCavityMap().keySet() ) {
			if (id == i) {
				RFCavity cavity = getMultiblock().getRFCavityMap().get(key);
				getMultiblock().getRFCavityMap().remove(key);
				cavity.is_active = state;
				getMultiblock().getRFCavityMap().put(key, cavity);
				getMultiblock().resetStats();
				getLogic().afterManageRfCavity();
				break;
			}
			i++;
		}
		return new Object[] {state};
	}

	@Callback(doc = "--function():bool Activate accelerator. Ignores redstone signal")
	@Optional.Method(
			modid = "opencomputers"
	)
	public Object[] activateAccelerator(Context context, Arguments args) {
		if(!isMultiblockAssembled()) return new Object[] {false};
		getMultiblock().isAcceleratorOn = true;
		getMultiblock().refreshFlag = true;
		getMultiblock().manualDisableFlag = false;
		getMultiblock().manualEnableFlag = true;

		return new Object[] {true};
	}

	@Callback(doc = "--function():bool Deactivate accelerator. Ignores redstone signal")
	@Optional.Method(
			modid = "opencomputers"
	)
	public Object[] deactivateAccelerator(Context context, Arguments args) {
		if(!isMultiblockAssembled()) return new Object[] {false};
		getMultiblock().isAcceleratorOn = false;
		getMultiblock().refreshFlag = true;
		getMultiblock().manualDisableFlag = true;
		getMultiblock().manualEnableFlag = false;
		return new Object[] {true};
	}

	@Callback(doc = "--function():bool Reset flags. So controller will count redstone signal")
	@Optional.Method(
			modid = "opencomputers"
	)
	public Object[] resetManualOverrides(Context context, Arguments args) {
		if(!isMultiblockAssembled()) return new Object[] {false};
		getMultiblock().manualDisableFlag = false;
		getMultiblock().manualEnableFlag = false;
		return new Object[] {true};
	}
}
