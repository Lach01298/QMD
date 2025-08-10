package lach_01298.qmd.liquefier;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.longs.*;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import lach_01298.qmd.QMD;
import lach_01298.qmd.config.QMDConfig;
import lach_01298.qmd.liquefier.tile.TileLiquefierCompressor;
import lach_01298.qmd.liquefier.tile.TileLiquefierEnergyPort;
import lach_01298.qmd.liquefier.tile.TileLiquefierNozzle;
import lach_01298.qmd.liquefier.tile.TileLiquefierFluidPort;
import lach_01298.qmd.multiblock.network.LiquefierRenderPacket;
import lach_01298.qmd.multiblock.network.LiquefierUpdatePacket;
import lach_01298.qmd.recipes.QMDRecipes;
import nc.Global;
import nc.multiblock.hx.*;
import nc.network.multiblock.HeatExchangerRenderPacket;
import nc.network.multiblock.HeatExchangerUpdatePacket;
import nc.recipe.BasicRecipe;
import nc.recipe.RecipeInfo;
import nc.recipe.ingredient.IFluidIngredient;
import nc.tile.hx.*;
import nc.tile.internal.energy.EnergyStorage;
import nc.tile.internal.fluid.Tank;
import nc.tile.multiblock.TilePartAbstract;
import nc.util.*;
import net.minecraft.block.material.Material;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.function.LongSupplier;
import java.util.stream.Stream;

import static nc.block.property.BlockProperties.ACTIVE;

public class LiquefierLogic extends HeatExchangerLogic
{
	public static final int BASE_MAX_INPUT = 32000, BASE_MAX_OUTPUT = 8000;


	public RecipeInfo<BasicRecipe> recipeInfo;

	public double energyEfficiency = 0;
	public double heatEfficiency = 0;
	public double pressure = 0;
	private double excessShellRecipes =0;

	public final EnergyStorage energyStorage = new EnergyStorage(QMDConfig.liquefier_base_energy_capacity);
	public List<Tank> tanks = Lists.newArrayList(new Tank(QMDConfig.liquefier_input_tank_capacity,QMDRecipes.liquefier.validFluids.get(0)), new Tank(QMDConfig.liquefier_output_tank_capacity, null));

	public LiquefierLogic(HeatExchanger exchanger)
	{
		super(exchanger);
	}

	public LiquefierLogic(HeatExchangerLogic oldLogic)
	{
		super(oldLogic);
	}

	@Override
	public String getID()
	{
		return "liquefier";
	}

	@Override
	protected int getTubeInputTankDensity()
	{
		return BASE_MAX_INPUT;
	}

	@Override
	protected int getTubeOutputTankDensity()
	{
		return BASE_MAX_OUTPUT;
	}

	protected Set<String> getShellValidFluids()
	{
		return QMDRecipes.liquefier_coolant.validFluids.get(0);
	}

	protected Set<String> getTubeValidFluids()
	{
		return QMDRecipes.liquefier.validFluids.get(0);
	}


	public @Nonnull List<Tank> getTanks(List<Tank> backupTanks)
	{
		return multiblock.isAssembled() ? tanks : backupTanks;
	}

	// Multiblock Methods

	@Override
	protected void onExchangerFormed()
	{
		super.onExchangerFormed();

		Long2ObjectMap<TileLiquefierCompressor> compressorMap = getPartMap(TileLiquefierCompressor.class);
		Long2ObjectMap<TileLiquefierNozzle> nozzleMap = getPartMap(TileLiquefierNozzle.class);

		int nozzleAmount = nozzleMap.size();
		int compressorAmount = compressorMap.size();
		energyEfficiency = 1;
		heatEfficiency = 1;
		for(TileLiquefierCompressor compressor : compressorMap.values())
		{
			energyEfficiency *= Math.pow(compressor.energyEfficiency,1/((double)nozzleAmount));
			heatEfficiency *= Math.pow(compressor.heatEfficiency,1/((double)nozzleAmount));
		}

		pressure = 10.0 * Math.pow(compressorAmount/(double)nozzleAmount,2.0);
		
	}

	@Override
	public void onExchangerBroken()
	{
		super.onExchangerBroken();
		energyEfficiency = 0;
		heatEfficiency = 0;
		pressure = 0;
	}

	@Override
	public boolean isMachineWhole()
	{
		if (containsBlacklistedPart())
		{
			return false;
		}

		multiblock.masterShellInlet = null;
		multiblock.networks.clear();

		multiblock.shellRecipe = null;

		multiblock.totalNetworkCount = 0;

		Long2ObjectMap<TileHeatExchangerTube> tubeMap = getPartMap(TileHeatExchangerTube.class);
		Long2ObjectMap<TileHeatExchangerBaffle> baffleMap = getPartMap(TileHeatExchangerBaffle.class);

		for (TileHeatExchangerTube tube : tubeMap.values())
		{
			tube.tubeFlow = null;
			tube.shellFlow = null;
		}

		Long2ObjectMap<TileHeatExchangerInlet> inletMap = getPartMap(TileHeatExchangerInlet.class);
		Long2ObjectMap<TileHeatExchangerOutlet> outletMap = getPartMap(TileHeatExchangerOutlet.class);
		Long2ObjectMap<TileLiquefierCompressor> compressorMap = getPartMap(TileLiquefierCompressor.class);
		Long2ObjectMap<TileLiquefierNozzle> nozzleMap = getPartMap(TileLiquefierNozzle.class);

		for (TileHeatExchangerInlet inlet : inletMap.values())
		{
			inlet.isMasterInlet = false;
			inlet.network = null;
		}

		for (TileHeatExchangerOutlet outlet : outletMap.values())
		{
			outlet.network = null;
		}

		LongSet tubeInletPosLongSet = new LongOpenHashSet();
		LongSet tubeOutletPosLongSet = new LongOpenHashSet();

		LongSet visitedTubePosLongSet = new LongOpenHashSet();




		for (BlockPos pos : BlockPos.getAllInBoxMutable(
				multiblock.getExtremeInteriorCoord(false, false, false).add(0,1,0),
				multiblock.getExtremeInteriorCoord(true, false, true).add(0,1,0)))
		{
			if(multiblock.getWorld().getBlockState(pos).getMaterial() != Material.AIR)
			{
				multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.liquefier.must_be_empty_layer", pos);
				return false;
			}
		}

		for (BlockPos pos : BlockPos.getAllInBoxMutable(
			multiblock.getExtremeInteriorCoord(false, false, false).add(0,1,0),
				multiblock.getExtremeInteriorCoord(true, false, true).add(0,1,0)))
		{
			if(!(multiblock.getWorld().getTileEntity(pos) instanceof TileHeatExchangerBaffle) || !(multiblock.getWorld().getTileEntity(pos) instanceof TileLiquefierNozzle))
			{
				multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.liquefier.must_be_baffle_or_nozzle", pos);
				return false;
			}
		}

		for (TileLiquefierCompressor compresor : compressorMap.values())
		{
			if(compresor.getPos().getY() != multiblock.getMaxY())
			{
				multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.liquefier.compressor_must_be_in_roof", compresor.getPos());
				return false;
			}

		}

		if(compressorMap.size()< nozzleMap.size())
		{
			multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.liquefier.invalid_compressor_amount", null);
			return false;
		}


		boolean inlet = false;
		boolean outlet = false;
		for (TileLiquefierFluidPort port : getPartMap(TileLiquefierFluidPort.class).values())
		{
			if (!port.getBlockState(port.getPos()).getValue(ACTIVE).booleanValue())
			{
				inlet = true;
			}
			else
			{
				outlet = true;
			}
		}
		if (!inlet)
		{
			multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.liquefier.must_have_fluid_input", null);
			return false;
		}
		if (!outlet)
		{
			multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.liquefier.must_have_fluid_output", null);
			return false;
		}

		if(getPartMap(TileLiquefierEnergyPort.class).size() < 1)
		{
			multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.liquefier.must_have_energy_port", null);
			return false;
		}



		for (long tubePosLong : tubeMap.keySet())
		{
			if (!visitedTubePosLongSet.contains(tubePosLong))
			{
				HeatExchangerTubeNetwork network = new HeatExchangerTubeNetwork(this);

				LongList tubePosLongStack = new LongArrayList();
				LongSupplier popTubePosLong = () -> tubePosLongStack.removeLong(tubePosLongStack.size() - 1);

				visitedTubePosLongSet.add(tubePosLong);
				tubePosLongStack.add(tubePosLong);

				while (!tubePosLongStack.isEmpty())
				{
					long nextPosLong = popTubePosLong.getAsLong();
					BlockPos nextPos = BlockPos.fromLong(nextPosLong);

					TileHeatExchangerTube tube = tubeMap.get(nextPosLong);
					HeatExchangerTubeSetting[] tubeSettings = tube.settings;

					for (int i = 0; i < 6; ++i)
					{
						if (tubeSettings[i].isOpen())
						{
							EnumFacing dir = EnumFacing.VALUES[i];
							long offsetPosLong = nextPos.offset(dir).toLong();

							TileLiquefierCompressor compressor = compressorMap.get(offsetPosLong);
							if (compressor != null)
							{
								tubeInletPosLongSet.add(offsetPosLong);
								network.inletPosLongSet.add(offsetPosLong);
								//compressor.network = network;
								continue;
							}

							TileLiquefierNozzle nozzle = nozzleMap.get(offsetPosLong);
							if (nozzle != null)
							{
								tubeOutletPosLongSet.add(offsetPosLong);
								network.outletPosLongSet.add(offsetPosLong);
								nozzle.network = network;
								continue;
							}

							TileHeatExchangerTube other = tubeMap.get(offsetPosLong);
							if (other != null && other.getTubeSetting(dir.getOpposite()).isOpen())
							{
								if (!visitedTubePosLongSet.contains(offsetPosLong))
								{
									visitedTubePosLongSet.add(offsetPosLong);
									tubePosLongStack.add(offsetPosLong);
								}
								continue;
							}

							multiblock.setLastError(Global.MOD_ID + ".multiblock_validation.heat_exchanger.dangling_tube", nextPos);
							return false;
						}
					}

					network.tubePosLongSet.add(nextPosLong);
				}

				multiblock.networks.add(network);
			}
		}

		if (multiblock.networks.size() != nozzleMap.size())
		{
			multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.liquefier.invalid_nozzle_network", null);
			return false;
		}

		for (HeatExchangerTubeNetwork network : multiblock.networks)
		{
			if (network.inletPosLongSet.isEmpty() || network.outletPosLongSet.isEmpty())
			{
				for (long posLong : network.tubePosLongSet)
				{
					multiblock.setLastError(Global.MOD_ID + ".multiblock_validation.heat_exchanger.invalid_network", BlockPos.fromLong(posLong));
					return false;
				}
			}
		}

		LongSet shellInletPosLongSet = new LongRBTreeSet(), shellOutletPosLongSet = new LongOpenHashSet();

		for (long inletPosLong : inletMap.keySet())
		{
			if (!tubeInletPosLongSet.contains(inletPosLong))
			{
				shellInletPosLongSet.add(inletPosLong);
			}
		}

		for (long outletPosLong : outletMap.keySet())
		{
			if (!tubeOutletPosLongSet.contains(outletPosLong))
			{
				shellOutletPosLongSet.add(outletPosLong);
			}
		}

		for (HeatExchangerTubeNetwork network : multiblock.networks)
		{
			network.setTubeFlows(tubeMap);
		}

		if (shellInletPosLongSet.isEmpty() || shellOutletPosLongSet.isEmpty())
		{
			multiblock.setLastError(Global.MOD_ID + ".multiblock_validation.heat_exchanger.invalid_shell", null);
			return false;
		}

		LongSet shellPosLongSet = new LongOpenHashSet();
		LongList shellPosLongStack = new LongArrayList();
		LongSupplier popShellPosLong = () -> shellPosLongStack.removeLong(shellPosLongStack.size() - 1);

		for (long inletPosLong : shellInletPosLongSet)
		{
			BlockPos inletPos = BlockPos.fromLong(inletPosLong);
			long clampedPosLong = multiblock.getClampedInteriorCoord(inletPos).toLong();
			if (baffleMap.containsKey(clampedPosLong))
			{
				multiblock.setLastError(Global.MOD_ID + ".multiblock_validation.heat_exchanger.blocked_inlet", inletPos);
				return false;
			}

			shellPosLongSet.add(clampedPosLong);
			shellPosLongStack.add(clampedPosLong);

			if (multiblock.masterShellInlet == null)
			{
				multiblock.masterShellInlet = inletMap.get(inletPosLong);
				multiblock.masterShellInlet.isMasterInlet = true;
			}
		}

		for (long outletPosLong : shellOutletPosLongSet)
		{
			BlockPos outletPos = BlockPos.fromLong(outletPosLong);
			if (baffleMap.containsKey(multiblock.getClampedInteriorCoord(outletPos).toLong()))
			{
				multiblock.setLastError(Global.MOD_ID + ".multiblock_validation.heat_exchanger.blocked_outlet", outletPos);
				return false;
			}
		}

		while (!shellPosLongStack.isEmpty())
		{
			long nextPosLong = popShellPosLong.getAsLong();
			BlockPos nextPos = BlockPos.fromLong(nextPosLong);

			if (!tubeMap.containsKey(nextPosLong) && !MaterialHelper.isEmpty(getWorld().getBlockState(nextPos).getMaterial()))
			{
				multiblock.setLastError("zerocore.api.nc.multiblock.validation.invalid_part_for_interior", nextPos, nextPos.getX(), nextPos.getY(), nextPos.getZ());
				return false;
			}

			HeatExchangerTubeSetting[] tubeSettings = tubeMap.containsKey(nextPosLong) ? tubeMap.get(nextPosLong).settings : null;

			for (int i = 0; i < 6; ++i)
			{
				if (tubeSettings == null || !tubeSettings[i].isBaffle())
				{
					EnumFacing dir = EnumFacing.VALUES[i];
					BlockPos offsetPos = nextPos.offset(dir);
					long offsetPosLong = offsetPos.toLong();

					if (multiblock.isInInterior(offsetPos) && !shellPosLongSet.contains(offsetPosLong) && (!baffleMap.containsKey(offsetPosLong) || (tubeMap.containsKey(offsetPosLong) && !tubeMap.get(offsetPosLong).getTubeSetting(dir.getOpposite()).isBaffle())))
					{
						shellPosLongSet.add(offsetPosLong);
						shellPosLongStack.add(offsetPosLong);
					}
				}
			}
		}

		if (shellPosLongSet.size() + baffleMap.size() != multiblock.getInteriorVolume())
		{
			multiblock.setLastError(Global.MOD_ID + ".multiblock_validation.heat_exchanger.blocked_shell", null);
			return false;
		}

		Long2ObjectMap<ObjectSet<Vec3d>> flowMap = HeatExchangerFlowHelper.getFlowMap(
				shellInletPosLongSet,
				shellOutletPosLongSet,
				x -> LambdaHelper.let(x.toLong(), y -> tubeMap.containsKey(y) ? tubeMap.get(y).settings : null),
				x -> !x.isBaffle(),
				(x, y) ->
				{
					long posLong = x.toLong();
					return shellPosLongSet.contains(posLong) && (!tubeMap.containsKey(posLong) || !tubeMap.get(posLong).getTubeSetting(y.getOpposite()).isBaffle());
				},
				x -> shellOutletPosLongSet.contains(x.toLong())
		);

		for (Long2ObjectMap.Entry<ObjectSet<Vec3d>> entry : flowMap.long2ObjectEntrySet())
		{
			long posLong = entry.getLongKey();
			if (tubeMap.containsKey(posLong))
			{
				TileHeatExchangerTube tube = tubeMap.get(posLong);
				if (tube.tubeFlow != null)
				{
					tube.shellFlow = entry.getValue().stream().reduce(Vec3d.ZERO, Vec3d::add).normalize();
				}
			}
		}

		for (IHeatExchangerController<?> controller : getParts(IHeatExchangerController.class))
		{
			controller.setIsRenderer(false);
		}
		for (IHeatExchangerController<?> controller : getParts(IHeatExchangerController.class))
		{
			controller.setIsRenderer(true);
			break;
		}

		return true;
	}

	@Override
	public List<Pair<Class<? extends IHeatExchangerPart>, String>> getPartBlacklist()
	{
		return Collections.emptyList();
	}

	@Override
	public void onAssimilate(HeatExchanger assimilated)
	{
	}

	@Override
	public void onAssimilated(HeatExchanger assimilator)
	{
	}


	public Stream<TileLiquefierNozzle> getNozzles()
	{
		return getPartMap(TileLiquefierNozzle.class).values().stream();
	}


	@Override
	public boolean onUpdateServer()
	{
		multiblock.shellRecipe = QMDRecipes.liquefier_coolant.getRecipeInfoFromInputs(Collections.emptyList(), multiblock.shellTanks.subList(0, 1));

		int[] nozzleUpdates = getNozzles().mapToInt(x -> x.processor.onTick() ? 1 : 0).toArray();
		boolean shouldUpdate = multiblock.refreshFlag || Arrays.stream(nozzleUpdates).anyMatch(x -> x != 0);


		produceShellProducts();

		if (multiblock.packetFlag > 1)
		{
			multiblock.sendMultiblockUpdatePacketToAll();
		}
		else if (multiblock.packetFlag > 0)
		{
			multiblock.sendMultiblockUpdatePacketToListeners();
		}

//		if (multiblock.controller != null)
//		{
//			multiblock.sendRenderPacketToAll();
//		}
		// TODO fix

		return shouldUpdate;
	}

	@Override
	public void refreshRecipe()
	{


	}

//	public boolean canProcessInputs()
//	{
//		int coolantTemp = multiblock.shellRecipe.recipe.getHeatExchangerInputTemperature();
//		int inversionTemp = (int) recipeInfo.recipe.getExtras().get(2);
//		if(coolantTemp > inversionTemp)
//		{
//			return false;
//		}
//
//		IFluidIngredient coolantOutput = multiblock.shellRecipe.recipe.getFluidProducts().get(0);
//		IFluidIngredient fluidOutput = recipeInfo.recipe.getFluidProducts().get(0);
//
//		Tank coolantOutputTank =  multiblock.shellTanks.get(1);
//		Tank fluidOutputTank =  tanks.get(1);
//
//		if (!fluidOutputTank.isEmpty())
//		{
//			if (!fluidOutputTank.getFluid().isFluidEqual(fluidOutput.getStack()))
//			{
//				return false;
//			}
//			if (fluidOutputTank.getFluidAmount() + fluidOutput.getMaxStackSize(0)> fluidOutputTank.getCapacity())
//			{
//				return false;
//			}
//		}
//		if (!coolantOutputTank.isEmpty())
//		{
//			if (!coolantOutputTank.getFluid().isFluidEqual(coolantOutput.getStack()))
//			{
//				return false;
//			}
//			if (coolantOutputTank.getFluidAmount() + coolantOutput.getMaxStackSize(0)> coolantOutputTank.getCapacity())
//			{
//				return false;
//			}
//		}
//
//
//
//		return true;
//	}

	public void produceShellProducts()
	{
		Tank inputTank = multiblock.shellTanks.get(0);
		Tank outputTank = multiblock.shellTanks.get(1);

		IFluidIngredient fluidInput = multiblock.shellRecipe.recipe.getFluidIngredients().get(0);
		IFluidIngredient fluidOutput = multiblock.shellRecipe.recipe.getFluidProducts().get(0);

		double baseRecipeHeat = (double) multiblock.shellRecipe.recipe.getExtras().get(0);
		double recipesPerTick = multiblock.heatTransferRate/baseRecipeHeat;

		int recipesThisTick = (int) Math.floor(recipesPerTick);
		excessShellRecipes += recipesPerTick - recipesThisTick;

		if(excessShellRecipes >= 1)
		{
			recipesThisTick += (int) Math.floor(excessShellRecipes);
			excessShellRecipes -= Math.floor(excessShellRecipes);
		}

		inputTank.changeFluidAmount(-recipesThisTick*fluidInput.getMaxStackSize(0));
		if (inputTank.getFluidAmount() <= 0) inputTank.setFluidStored(null);

		if(outputTank.isEmpty())
		{
			outputTank.changeFluidStored(fluidOutput.getNextStack(0).getFluid(),recipesThisTick*fluidOutput.getMaxStackSize(0));
		}
		else
		{
			outputTank.changeFluidAmount(recipesThisTick*fluidOutput.getMaxStackSize(0));
		}
	}














	@Override
	public void writeToLogicTag(NBTTagCompound logicTag, TilePartAbstract.SyncReason syncReason)
	{
		super.writeToLogicTag(logicTag, syncReason);
		energyStorage.writeToNBT(logicTag,"energyStorage");
		writeTanks(tanks,logicTag,"tanks");
		logicTag.setDouble("energyEfficiency", energyEfficiency);
		logicTag.setDouble("heatEfficiency", heatEfficiency);
		logicTag.setDouble("pressure", pressure);
	}

	@Override
	public void readFromLogicTag(NBTTagCompound logicTag, TilePartAbstract.SyncReason syncReason)
	{
		super.readFromLogicTag(logicTag, syncReason);
		energyStorage.readFromNBT(logicTag,"energyStorage");
		readTanks(tanks,logicTag, "tanks");
		energyEfficiency=logicTag.getDouble("energyEfficiency");
		heatEfficiency=logicTag.getDouble("heatEfficiency");
		pressure=logicTag.getDouble("pressure");

	}

	// Packets

	@Override
	public LiquefierUpdatePacket getMultiblockUpdatePacket()
	{
		// TODO fix
		return null;//new LiquefierUpdatePacket(multiblock.controller.getTilePos(), multiblock.isExchangerOn, multiblock.totalNetworkCount, multiblock.activeNetworkCount, multiblock.activeTubeCount, multiblock.activeContactCount, multiblock.tubeInputRateFP, multiblock.shellInputRateFP, multiblock.heatTransferRateFP, multiblock.totalTempDiff, energyStorage, tanks, energyEfficiency, heatEfficiency, pressure);
	}

	@Override
	public void onMultiblockUpdatePacket(HeatExchangerUpdatePacket message)
	{
		super.onMultiblockUpdatePacket(message);
		if (message instanceof LiquefierUpdatePacket)
		{
			LiquefierUpdatePacket packet = (LiquefierUpdatePacket) message;
			energyStorage.setStorageCapacity(packet.energyStorage.getMaxEnergyStored());
			energyStorage.setEnergyStored(packet.energyStorage.getEnergyStored());
			for (int i = 0; i < tanks.size(); i++) tanks.get(i).readInfo(packet.tanksInfo.get(i));

			this.energyEfficiency = packet.energyEfficiency;
			this.heatEfficiency = packet.heatEfficiency;
			this.pressure = packet.pressure;
		}
	}

	public LiquefierRenderPacket getRenderPacket()
	{
		// TODO fix
		return null;//new LiquefierRenderPacket(multiblock.controller.getTilePos(), multiblock.shellTanks);
	}

	public void onRenderPacket(HeatExchangerRenderPacket message)
	{
		super.onRenderPacket(message);
	}

	@Override
	public boolean isBlockGoodForInterior(World world, BlockPos pos)
	{
		return super.isBlockGoodForInterior(world, pos);
	}

	// Clear Material

	@Override
	public void clearAllMaterial()
	{
		super.clearAllMaterial();
	}


}
