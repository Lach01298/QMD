package lach_01298.qmd.vacuumChamber;


import static lach_01298.qmd.recipes.QMDRecipes.nucleosynthesis_chamber;
import static lach_01298.qmd.recipes.QMDRecipes.vacuum_chamber_heating;
import static nc.block.property.BlockProperties.ACTIVE;
import static nc.block.property.BlockProperties.AXIS_ALL;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import lach_01298.qmd.QMD;
import lach_01298.qmd.accelerator.tile.TileAcceleratorMagnet;
import lach_01298.qmd.accelerator.tile.TileAcceleratorRFCavity;
import lach_01298.qmd.config.QMDConfig;
import lach_01298.qmd.enums.EnumTypes.IOType;
import lach_01298.qmd.multiblock.container.ContainerNucleosynthesisChamberController;
import lach_01298.qmd.multiblock.network.ContainmentRenderPacket;
import lach_01298.qmd.multiblock.network.NucleosynthesisChamberUpdatePacket;
import lach_01298.qmd.multiblock.network.VacuumChamberUpdatePacket;
import lach_01298.qmd.particle.ParticleStack;
import lach_01298.qmd.particle.ParticleStorageAccelerator;
import lach_01298.qmd.recipe.QMDRecipe;
import lach_01298.qmd.recipe.QMDRecipeInfo;
import lach_01298.qmd.recipes.QMDRecipes;
import lach_01298.qmd.vacuumChamber.tile.IVacuumChamberComponent;
import lach_01298.qmd.vacuumChamber.tile.IVacuumChamberController;
import lach_01298.qmd.vacuumChamber.tile.TileVacuumChamberBeam;
import lach_01298.qmd.vacuumChamber.tile.TileVacuumChamberBeamPort;
import lach_01298.qmd.vacuumChamber.tile.TileVacuumChamberFluidPort;
import lach_01298.qmd.vacuumChamber.tile.TileVacuumChamberHeater;
import lach_01298.qmd.vacuumChamber.tile.TileVacuumChamberHeaterVent;
import lach_01298.qmd.vacuumChamber.tile.TileVacuumChamberPlasmaGlass;
import lach_01298.qmd.vacuumChamber.tile.TileVacuumChamberPlasmaNozzle;
import lach_01298.qmd.vacuumChamber.tile.TileVacuumChamberRedstonePort;
import nc.multiblock.container.ContainerMultiblockController;
import nc.multiblock.tile.TileBeefAbstract.SyncReason;
import nc.recipe.BasicRecipe;
import nc.recipe.RecipeInfo;
import nc.recipe.ingredient.EmptyFluidIngredient;
import nc.recipe.ingredient.IFluidIngredient;
import nc.tile.internal.fluid.Tank;
import nc.tile.internal.heat.HeatBuffer;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class NucleosynthesisChamberLogic extends VacuumChamberLogic
{
	
	public boolean searchFlag = false;
	public final ObjectSet<TileVacuumChamberHeater> heaterCache = new ObjectOpenHashSet<>();
	public final Long2ObjectMap<TileVacuumChamberHeater> componentFailCache = new Long2ObjectOpenHashMap<>(), assumedValidCache = new Long2ObjectOpenHashMap<>();
	
	public final static int chamberLength = 11;
	public final static int chamberWidth = 5;
	public final static int chamberHeight = 7;
	public long particleWorkDone, recipeParticleWork = 600;

	public boolean plasmaOn = false;
	
	
	public QMDRecipeInfo<QMDRecipe> recipeInfo;
	public QMDRecipeInfo<QMDRecipe> rememberedRecipeInfo;
	
	public static final int CASING_MAX_TEMP = 2400;
	public static final  int cooling_efficiency_leniency =20;
	public double casingHeating = 0L;
	public long casingCooling = 0L;
	public int maxCasingCoolantIn = 0, maxCasingCoolantOut = 0; // in mirco buckets/t
	private int excessCasingCoolant =0; // in mirco buckets
	private double excessCasingHeat =0;
	
	public RecipeInfo<BasicRecipe> casingCoolingRecipeInfo;
	
	public final HeatBuffer casingHeatBuffer = new HeatBuffer(QMDConfig.accelerator_base_heat_capacity);

	public NucleosynthesisChamberLogic(VacuumChamberLogic oldLogic)
	{
		super(oldLogic);
		/*
		beam 0 = input particle 1
		tank 0 = input coolant
		tank 1 = output coolant
		tank 2 = input heater fluid 
		tank 3 = output heater fluid
		tank 4 = input fluid 1
		tank 5 = input fluid 2
		tank 6 = output fluid 1
		tank 7 = output fluid 2
		*/
		
		//on the rare occasion of changing the multiblock to a different type with the tank full
		if(oldLogic instanceof ExoticContainmentLogic)
		{
			getMultiblock().tanks.get(2).setFluidStored(null);
		}
	}

	@Override
	public String getID()
	{
		return "nucleosynthesis_chamber";
	}

	// Multiblock Size Limits

	@Override
	public int getMinimumInteriorLength()
	{
		return 3;
	}

	@Override
	public int getMaximumInteriorLength()
	{
		return chamberLength-2;
	}

	// Multiblock Methods

	@Override
	public void onVacuumChamberFormed()
	{
		getMultiblock().tanks.get(2).setCapacity(QMDConfig.vacuum_chamber_input_tank_capacity * 100);
		getMultiblock().tanks.get(2).setAllowedFluids(QMDRecipes.vacuum_chamber_heater_valid_fluids.get(0));
		getMultiblock().tanks.get(3).setCapacity(QMDConfig.vacuum_chamber_output_tank_capacity * 100);

		getMultiblock().tanks.get(4).setCapacity(QMDConfig.vacuum_chamber_input_tank_capacity * 10);
		getMultiblock().tanks.get(4).setAllowedFluids(QMDRecipes.nucleosynthesis_valid_fluids.get(0));
		getMultiblock().tanks.get(5).setCapacity(QMDConfig.vacuum_chamber_input_tank_capacity * 10);
		getMultiblock().tanks.get(5).setAllowedFluids(QMDRecipes.nucleosynthesis_valid_fluids.get(0));
		getMultiblock().tanks.get(6).setCapacity(QMDConfig.vacuum_chamber_output_tank_capacity * 10);
		getMultiblock().tanks.get(7).setCapacity(QMDConfig.vacuum_chamber_output_tank_capacity * 10);
		
		casingHeatBuffer.setHeatCapacity(QMDConfig.accelerator_base_heat_capacity * getCapacityMultiplier());
		
		if (!getWorld().isRemote)
		{

			int io = 0;
			for (TileVacuumChamberBeamPort port : getMultiblock().getPartMap(TileVacuumChamberBeamPort.class).values())
			{
				port.setIONumber(io);
				io++;
			}

			if (getMultiblock().controller != null)
			{
				getMultiblock().sendUpdateToAllPlayers();
				getMultiblock().markReferenceCoordForUpdate();
			}

			

			for (TileVacuumChamberBeam beam : getMultiblock().getPartMap(TileVacuumChamberBeam.class).values())
			{
				beam.setFunctional(true);
			}

			// heaters

			casingCooling = 0;
			casingHeating = 0;

			componentFailCache.clear();
			do
			{
				assumedValidCache.clear();
				refreshHeaters();
			}
			while (searchFlag);

			for (IVacuumChamberComponent part : getMultiblock().getPartMap(IVacuumChamberComponent.class).values())
			{
				if (part instanceof TileVacuumChamberHeater)
				{
					TileVacuumChamberHeater heater = (TileVacuumChamberHeater) part;

					if (part.isFunctional())
					{
						casingCooling += heater.coolingRate;

					}
				}
			}

		}

		BlockPos beamPortPos = null;

		for (TileVacuumChamberBeamPort port : getParts(TileVacuumChamberBeamPort.class))
		{
			beamPortPos = port.getPos();
		}

		// fluid input ports
		
		BlockPos fluidPortInPos1 = beamPortPos.add(0, 3, 0);
		BlockPos fluidPortInPos2 = beamPortPos.add(0, 1, 0);

		if (multiblock.WORLD.getTileEntity(fluidPortInPos1) instanceof TileVacuumChamberFluidPort)
		{
			TileVacuumChamberFluidPort fluidPort = (TileVacuumChamberFluidPort) multiblock.WORLD
					.getTileEntity(fluidPortInPos1);
			fluidPort.setIONumber(4);
			
		}
		if (multiblock.WORLD.getTileEntity(fluidPortInPos2) instanceof TileVacuumChamberFluidPort)
		{
			TileVacuumChamberFluidPort fluidPort = (TileVacuumChamberFluidPort) multiblock.WORLD
					.getTileEntity(fluidPortInPos2);
			fluidPort.setIONumber(5);
			
		}

		// fluid output ports

		BlockPos fluidPortOutPos1;
		BlockPos fluidPortOutPos2;
		if (multiblock.getExteriorLengthX() > multiblock.getExteriorLengthZ())
		{
			if (beamPortPos.getX() <= multiblock.getMinX())
			{
				fluidPortOutPos1 = beamPortPos.add(chamberLength - 1, 3, 0);
				fluidPortOutPos2 = beamPortPos.add(chamberLength - 1, 1, 0);
			}
			else
			{
				fluidPortOutPos1 = beamPortPos.add(-chamberLength + 1, 3, 0);
				fluidPortOutPos2 = beamPortPos.add(-chamberLength + 1, 1, 0);
			}
		}
		else
		{
			if (beamPortPos.getZ() <= multiblock.getMinZ())
			{
				fluidPortOutPos1 = beamPortPos.add(0, 3, chamberLength - 1);
				fluidPortOutPos2 = beamPortPos.add(0, 1, chamberLength - 1);
			}
			else
			{
				fluidPortOutPos1 = beamPortPos.add(0, 3, -chamberLength + 1);
				fluidPortOutPos2 = beamPortPos.add(0, 1, -chamberLength + 1);
			}
		}

		if (multiblock.WORLD.getTileEntity(fluidPortOutPos1) instanceof TileVacuumChamberFluidPort)
		{
			TileVacuumChamberFluidPort fluidPort = (TileVacuumChamberFluidPort) multiblock.WORLD
					.getTileEntity(fluidPortOutPos1);
			fluidPort.setIONumber(6);
		}

		if (multiblock.WORLD.getTileEntity(fluidPortOutPos2) instanceof TileVacuumChamberFluidPort)
		{
			TileVacuumChamberFluidPort fluidPort = (TileVacuumChamberFluidPort) multiblock.WORLD
					.getTileEntity(fluidPortOutPos2);
			fluidPort.setIONumber(7);
		}

		super.onVacuumChamberFormed();
	}
	
	
	private void refreshHeaters()
	{
		
		searchFlag = false;
		
		if (getPartMap(TileVacuumChamberHeater.class).isEmpty()) 
		{
			return;
		}
		
		for (TileVacuumChamberHeater heater : getParts(TileVacuumChamberHeater.class)) 
		{
			heater.isSearched = heater.isInValidPosition = false;
			
		}
		
		heaterCache.clear();
		
		for (TileVacuumChamberHeater heater : getParts(TileVacuumChamberHeater.class)) 
		{
			 
			if (heater.isSearchRoot()) 
			{
				 
				iterateHeaterSearch(heater, heaterCache);
			}
		}
		
		for (TileVacuumChamberHeater heater : assumedValidCache.values()) 
		{
			if (!heater.isInValidPosition) 
			{
				componentFailCache.put(heater.getPos().toLong(), heater);
				searchFlag = true;
			}
		}
		
	}
	
	private void iterateHeaterSearch(TileVacuumChamberHeater rootHeater, ObjectSet<TileVacuumChamberHeater> heaterCache)
	{
		final ObjectSet<TileVacuumChamberHeater> searchCache = new ObjectOpenHashSet<>();
		rootHeater.coolerSearch(heaterCache, searchCache, componentFailCache, assumedValidCache);
		
		do 
		{
			final Iterator<TileVacuumChamberHeater> searchIterator = searchCache.iterator();
			final ObjectSet<TileVacuumChamberHeater> searchSubCache = new ObjectOpenHashSet<>();
			while (searchIterator.hasNext()) 
			{
				TileVacuumChamberHeater component = searchIterator.next();
				searchIterator.remove();
				component.coolerSearch(heaterCache, searchSubCache, componentFailCache, assumedValidCache);
			}
			searchCache.addAll(searchSubCache);
		}
		while (!searchCache.isEmpty());
	}
	

	public void onMachineDisassembled()
	{
		for (TileVacuumChamberBeamPort tile : getPartMap(TileVacuumChamberBeamPort.class).values())
		{
			tile.setIONumber(0);
		}
		
		for (TileVacuumChamberFluidPort tile : getPartMap(TileVacuumChamberFluidPort.class).values())
		{
			tile.setIONumber(0);
		}
		
		for (TileVacuumChamberBeam beam :getMultiblock().getPartMap(TileVacuumChamberBeam.class).values())
		{
			beam.setFunctional(false);
		}
		if(plasmaOn)
		{
			containmentFaliure();
		}
		
		super.onMachineDisassembled();
	}

	public boolean isMachineWhole()
	{
		VacuumChamber chamber = getMultiblock();
		Axis axis;
		
		if (chamber.getExteriorLengthX() > chamber.getExteriorLengthZ())
		{
			axis = Axis.X;
			if (chamber.getExteriorLengthX() != chamberLength)
			{
				multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.nucleosynthesis_chamber.size",null);
				return false;
			}
			if (chamber.getExteriorLengthZ() != chamberWidth)
			{
				multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.nucleosynthesis_chamber.size",null);
				return false;
			}
		}
		else
		{
			axis = Axis.Z;
			if (chamber.getExteriorLengthZ() != chamberLength)
			{
				multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.nucleosynthesis_chamber.size",null);
				return false;
			}
			if (chamber.getExteriorLengthX() != chamberWidth)
			{
				multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.nucleosynthesis_chamber.size",null);
				return false;
			}

		}
		
		if (chamber.getExteriorLengthY() != chamberHeight)
		{
			multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.nucleosynthesis_chamber.size",null);
			return false;
		}
		
		// beams
		for (BlockPos pos : getBeamPositions(axis))
		{
			if (!(chamber.WORLD.getTileEntity(pos) instanceof TileVacuumChamberBeam))
			{
				multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.nucleosynthesis_chamber.must_be_beam", pos);
				return false;
			}
		}
		
		// plasma glass
				for (BlockPos pos : getGlassPositions(axis))
				{
					if (!(chamber.WORLD.getTileEntity(pos) instanceof TileVacuumChamberPlasmaGlass))
					{
						multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.nucleosynthesis_chamber.must_be_plasma_glass", pos);
						return false;
					}
				}
		// Nozzle

		BlockPos nozzle1;
		BlockPos nozzle2;
		if(axis == Axis.X)
		{
			nozzle1 = chamber.getExtremeInteriorCoord(false, false, false).add(1,1,1);
			nozzle2 = chamber.getExtremeInteriorCoord(true, false, false).add(-1,1,1);
			if (!(chamber.WORLD.getTileEntity(nozzle1) instanceof TileVacuumChamberPlasmaNozzle) || chamber.WORLD.getBlockState(nozzle1).getValue(AXIS_ALL) !=  axis)
			{
				multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.nucleosynthesis_chamber.must_be_plasma_nozzle", nozzle1);
				return false;
			}
			
			if (!(chamber.WORLD.getTileEntity(nozzle2) instanceof TileVacuumChamberPlasmaNozzle) || chamber.WORLD.getBlockState(nozzle2).getValue(AXIS_ALL) !=  axis)
			{
				multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.nucleosynthesis_chamber.must_be_plasma_nozzle", nozzle2);
				return false;
			}
		}
		else
		{
			nozzle1 = chamber.getExtremeInteriorCoord(false, false, false).add(1,1,1);
			nozzle2 = chamber.getExtremeInteriorCoord(false, false, true).add(1,1,-1);
			if (!(chamber.WORLD.getTileEntity(nozzle1) instanceof TileVacuumChamberPlasmaNozzle) || chamber.WORLD.getBlockState(nozzle1).getValue(AXIS_ALL) !=  axis)
			{
				multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.nucleosynthesis_chamber.must_be_plasma_nozzle", nozzle1);
				return false;
			}
			
			if (!(chamber.WORLD.getTileEntity(nozzle2) instanceof TileVacuumChamberPlasmaNozzle) || chamber.WORLD.getBlockState(nozzle2).getValue(AXIS_ALL) !=  axis)
			{
				multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.nucleosynthesis_chamber.must_be_plasma_nozzle", nozzle2);
				return false;
			}
		}
				
		
				
				
				
				
			//empty space or heater
			Set<BlockPos> interior = new HashSet<BlockPos>();	
				for (BlockPos pos : BlockPos.getAllInBoxMutable(chamber.getExtremeInteriorCoord(false, false, false),
						chamber.getExtremeInteriorCoord(true, true, true)))
				{
					interior.add(pos.toImmutable());
				}
				interior.removeAll(getBeamPositions(axis));
				interior.removeAll(getGlassPositions(axis));
				interior.removeAll(getPlasmaPositions(axis));
				interior.remove(nozzle1);
				interior.remove(nozzle2);
				
				Fluid plasma = FluidRegistry.getFluid("plasma");
				Block block = plasma == null ? null : plasma.getBlock();
				IBlockState plasmaState = block == null ? Blocks.AIR.getDefaultState() : block.getDefaultState();
				
				for (BlockPos pos : getPlasmaPositions(axis))
				{
					if (chamber.WORLD.getBlockState(pos) != Blocks.AIR.getDefaultState() && chamber.WORLD.getBlockState(pos) != plasmaState)
					{
						multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.nucleosynthesis_chamber.must_be_empty", pos);
						return false;
					}
				}	
				
				
			for (BlockPos pos : interior)
			{
				if (!(chamber.WORLD.getBlockState(pos).getMaterial() == Material.AIR || chamber.WORLD.getTileEntity(pos) instanceof TileVacuumChamberHeater))
				{
					multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.nucleosynthesis_chamber.must_be_heater_or_empty", pos);
					return false;
				}
			}
				
			
			
			//beam port
			BlockPos beamPortPos;
			int beamPorts = 0;
			if(axis == Axis.X)
			{
				beamPortPos = new BlockPos(chamber.getMinX(), chamber.getMiddleY()-1, chamber.getMiddleZ());
				
				if(multiblock.WORLD.getTileEntity(beamPortPos) instanceof TileVacuumChamberBeamPort)
				{
					beamPorts++;
				}
				else if(multiblock.WORLD.getTileEntity(new BlockPos(chamber.getMaxX(), chamber.getMiddleY()-1, chamber.getMiddleZ())) instanceof TileVacuumChamberBeamPort)
				{
					beamPortPos = new BlockPos(chamber.getMaxX(), chamber.getMiddleY()-1, chamber.getMiddleZ());
					beamPorts++;	
				}
			}
			else
			{
				beamPortPos = new BlockPos(chamber.getMiddleX(), chamber.getMiddleY()-1, chamber.getMinZ());
				if(multiblock.WORLD.getTileEntity(beamPortPos) instanceof TileVacuumChamberBeamPort)
				{	
					beamPorts++;
				}
				else if(multiblock.WORLD.getTileEntity(new BlockPos(chamber.getMiddleX(), chamber.getMiddleY()-1, chamber.getMaxZ())) instanceof TileVacuumChamberBeamPort)
				{
					beamPortPos = new BlockPos(chamber.getMiddleX(), chamber.getMiddleY()-1, chamber.getMaxZ());
					beamPorts++;
					
				}
			}
			if(beamPorts != 1)
			{
				multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.nucleosynthesis_chamber.must_be_input_beam", beamPortPos);
				return false;
			}
			TileVacuumChamberBeamPort beamPort = (TileVacuumChamberBeamPort) chamber.WORLD.getTileEntity(beamPortPos);
			if (beamPort.getIOType() != IOType.INPUT)
			{
				multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.nucleosynthesis_chamber.must_be_input_beam", beamPortPos);
				return false;
			}
				
			//fluid  input ports
			BlockPos fluidPortInPos1 = beamPortPos.add(0, 3, 0);
			BlockPos fluidPortInPos2 = beamPortPos.add(0, 1, 0);
			
			
			
			if(multiblock.WORLD.getTileEntity(fluidPortInPos1) instanceof TileVacuumChamberFluidPort)
			{
				TileVacuumChamberFluidPort fluidPort = (TileVacuumChamberFluidPort) multiblock.WORLD.getTileEntity(fluidPortInPos1);
				if (fluidPort.getBlockState(fluidPort.getPos()).getValue(ACTIVE).booleanValue())
				{
					multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.nucleosynthesis_chamber.must_be_input_fluid_port", fluidPortInPos1);
					return false;
				}
			}
			else
			{
				multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.nucleosynthesis_chamber.must_be_input_fluid_port", fluidPortInPos1);
				return false;
			}
			
			if(multiblock.WORLD.getTileEntity(fluidPortInPos2) instanceof TileVacuumChamberFluidPort)
			{
				TileVacuumChamberFluidPort fluidPort = (TileVacuumChamberFluidPort) multiblock.WORLD.getTileEntity(fluidPortInPos2);
				if (fluidPort.getBlockState(fluidPort.getPos()).getValue(ACTIVE).booleanValue())
				{
					multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.nucleosynthesis_chamber.must_be_input_fluid_port", fluidPortInPos2);
					return false;
				}
			}
			else
			{
				multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.nucleosynthesis_chamber.must_be_input_fluid_port", fluidPortInPos2);
				return false;
			}
			
			//fluid output ports
			
			BlockPos fluidPortOutPos1; 
			BlockPos fluidPortOutPos2;
			if(axis== Axis.X)
			{
				if(beamPortPos.getX() <= chamber.getMinX())
				{
					fluidPortOutPos1	= beamPortPos.add(chamberLength-1, 3, 0);
					fluidPortOutPos2 = beamPortPos.add(chamberLength-1, 1, 0);
				}
				else
				{
					fluidPortOutPos1	= beamPortPos.add(-chamberLength+1, 3, 0);
					fluidPortOutPos2 = beamPortPos.add(-chamberLength+1, 1, 0);
				}
			}
			else
			{
				if(beamPortPos.getZ() <= chamber.getMinZ())
				{
					fluidPortOutPos1	= beamPortPos.add(0, 3, chamberLength-1);
					fluidPortOutPos2 = beamPortPos.add(0, 1, chamberLength-1);
				}
				else
				{
					fluidPortOutPos1	= beamPortPos.add(0, 3, -chamberLength+1);
					fluidPortOutPos2 = beamPortPos.add(0, 1, -chamberLength+1);
				}
			}
			
			if(multiblock.WORLD.getTileEntity(fluidPortOutPos1) instanceof TileVacuumChamberFluidPort)
			{
				TileVacuumChamberFluidPort fluidPort = (TileVacuumChamberFluidPort) multiblock.WORLD.getTileEntity(fluidPortOutPos1);
				if (!fluidPort.getBlockState(fluidPort.getPos()).getValue(ACTIVE).booleanValue())
				{
					multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.nucleosynthesis_chamber.must_be_output_fluid_port", fluidPortOutPos1);
					return false;
				}
			}
			else
			{
				multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.nucleosynthesis_chamber.must_be_output_fluid_port", fluidPortOutPos1);
				return false;
			}
			
			if(multiblock.WORLD.getTileEntity(fluidPortOutPos2) instanceof TileVacuumChamberFluidPort)
			{
				TileVacuumChamberFluidPort fluidPort = (TileVacuumChamberFluidPort) multiblock.WORLD.getTileEntity(fluidPortOutPos2);
				if (!fluidPort.getBlockState(fluidPort.getPos()).getValue(ACTIVE).booleanValue())
				{
					multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.nucleosynthesis_chamber.must_be_output_fluid_port", fluidPortOutPos2);
					return false;
				}
			}
			else
			{
				multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.nucleosynthesis_chamber.must_be_output_fluid_port", fluidPortOutPos2);
				return false;
			}
			
			
			int fluidPorts = 0;
			for (TileVacuumChamberFluidPort fluidPort : getPartMap(TileVacuumChamberFluidPort.class).values())
			{
				fluidPorts++;
			}
			if(fluidPorts != 4)
			{
				multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.nucleosynthesis_chamber.must_have_4_fluid_ports",null);
				return false;
			}
				
				
			
			// heater vents
			boolean inlet = false;
			boolean outlet = false;
			for (TileVacuumChamberHeaterVent vent : getPartMap(TileVacuumChamberHeaterVent.class).values())
			{
				if (!vent.getBlockState(vent.getPos()).getValue(ACTIVE).booleanValue())
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
				multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.nucleosynthesis_chamber.no_heater_inlet", null);
				return false;
			}

			if (!outlet)
			{
				multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.nucleosynthesis_chamber.no_heater_outlet", null);
				return false;
			}
			
			
			
		
		return super.isMachineWhole();
	}

	public Set<BlockPos> getBeamPositions(Axis axis)
	{
		Set<BlockPos> postions = new HashSet<BlockPos>();
		VacuumChamber chamber = getMultiblock();

		if (axis == Axis.X)
		{
			for (BlockPos pos : BlockPos.getAllInBoxMutable(chamber.getExtremeInteriorCoord(false, true, false).add(0, 0, 1),
					chamber.getExtremeInteriorCoord(true, true, false).add(0, 0, 1)))
			{
				postions.add(pos.toImmutable());
			}
			for (BlockPos pos : BlockPos.getAllInBoxMutable(chamber.getExtremeInteriorCoord(false, true, false).add(0, -1, 1),
					chamber.getExtremeInteriorCoord(false, false, false).add(0, 1, 1)))
			{
				postions.add(pos.toImmutable());
			}
			for (BlockPos pos : BlockPos.getAllInBoxMutable(chamber.getExtremeInteriorCoord(true, true, false).add(0, -1, 1),
					chamber.getExtremeInteriorCoord(true, false, false).add(0, 1, 1)))
			{
				postions.add(pos.toImmutable());
			}
			
		}
		else
		{
			
			for (BlockPos pos : BlockPos.getAllInBoxMutable(chamber.getExtremeInteriorCoord(false, true, false).add(1, 0, 0),
					chamber.getExtremeInteriorCoord(false, true, true).add(1, 0, 0)))
			{
				postions.add(pos.toImmutable());
			}
			for (BlockPos pos : BlockPos.getAllInBoxMutable(chamber.getExtremeInteriorCoord(false, true, false).add(1, -1, 0),
					chamber.getExtremeInteriorCoord(false, false, false).add(1, 1, 0)))
			{
				postions.add(pos.toImmutable());
			}
			for (BlockPos pos : BlockPos.getAllInBoxMutable(chamber.getExtremeInteriorCoord(false, true, true).add(1, -1, 0),
					chamber.getExtremeInteriorCoord(false, false, true).add(1, 1, 0)))
			{
				postions.add(pos.toImmutable());
			}

		}
		
		return postions;
	}
		
	public Set<BlockPos> getGlassPositions(Axis axis)
	{
		Set<BlockPos> postions = new HashSet<BlockPos>();
		VacuumChamber chamber = getMultiblock();

		if (axis == Axis.X)
		{
			for (BlockPos pos : BlockPos.getAllInBoxMutable(chamber.getExtremeInteriorCoord(false, false, false).add(2, 0, 1),
					chamber.getExtremeInteriorCoord(true, false, false).add(-2, 0, 1)))
			{
				postions.add(pos.toImmutable());
			}	
			
			for (BlockPos pos : BlockPos.getAllInBoxMutable(chamber.getExtremeInteriorCoord(false, false, false).add(2, 1, 0),
					chamber.getExtremeInteriorCoord(true, false, false).add(-2, 1, 0)))
			{
				postions.add(pos.toImmutable());
			}	
			
			for (BlockPos pos : BlockPos.getAllInBoxMutable(chamber.getExtremeInteriorCoord(false, false, false).add(2, 2, 1),
					chamber.getExtremeInteriorCoord(true, false, false).add(-2, 2, 1)))
			{
				postions.add(pos.toImmutable());
			}	
			
			for (BlockPos pos : BlockPos.getAllInBoxMutable(chamber.getExtremeInteriorCoord(false, false, true).add(2, 1, 0),
					chamber.getExtremeInteriorCoord(true, false, true).add(-2, 1, 0)))
			{
				postions.add(pos.toImmutable());
			}	
			
		}
		else
		{
			for (BlockPos pos : BlockPos.getAllInBoxMutable(chamber.getExtremeInteriorCoord(false, false, false).add(1, 0, 2),
					chamber.getExtremeInteriorCoord(false, false, true).add(1, 0, -2)))
			{
				postions.add(pos.toImmutable());
			}	
			
			for (BlockPos pos : BlockPos.getAllInBoxMutable(chamber.getExtremeInteriorCoord(false, false, false).add(0, 1, 2),
					chamber.getExtremeInteriorCoord(false, false, true).add(0, 1, -2)))
			{
				postions.add(pos.toImmutable());
			}	
			
			for (BlockPos pos : BlockPos.getAllInBoxMutable(chamber.getExtremeInteriorCoord(false, false, false).add(1, 2, 2),
					chamber.getExtremeInteriorCoord(false, false, true).add(1, 2, -2)))
			{
				postions.add(pos.toImmutable());
			}	
			
			for (BlockPos pos : BlockPos.getAllInBoxMutable(chamber.getExtremeInteriorCoord(true, false, false).add(0, 1, 2),
					chamber.getExtremeInteriorCoord(true, false, true).add(0, 1, -2)))
			{
				postions.add(pos.toImmutable());
			}	

		}
		return postions;
	}
	
	public Set<BlockPos> getPlasmaPositions(Axis axis)
	{
		Set<BlockPos> postions = new HashSet<BlockPos>();
		VacuumChamber chamber = getMultiblock();
		
		if (axis == Axis.X)
		{
			for (BlockPos pos : BlockPos.getAllInBoxMutable(chamber.getExtremeInteriorCoord(false, false, false).add(2, 1, 1),
					chamber.getExtremeInteriorCoord(true, false, false).add(-2, 1, 1)))
			{
				postions.add(pos.toImmutable());
			}		
		}
		else
		{
			for (BlockPos pos : BlockPos.getAllInBoxMutable(chamber.getExtremeInteriorCoord(false, false, false).add(1, 1, 2),
					chamber.getExtremeInteriorCoord(false, false, true).add(1, 1, -2)))
			{
				postions.add(pos.toImmutable());
			}		

		}
		return postions;
	}

	// Server
	
	@Override
	public boolean onUpdateServer()
	{
		getMultiblock().beams.get(0).setParticleStack(null);
		pull();
		getMultiblock().currentHeating = 0;

		if ((!getMultiblock().tanks.get(4).isEmpty() || !getMultiblock().tanks.get(5).isEmpty()))
		{

			if (getMultiblock().energyStorage.extractEnergy(getMultiblock().requiredEnergy,
					true) == getMultiblock().requiredEnergy)
			{

				getMultiblock().energyStorage.changeEnergyStored(-getMultiblock().requiredEnergy);
				internalHeating();
				if (getMultiblock().getTemperature() <= getMultiblock().maxOperatingTemp)
				{
					operational = true;

					refreshRecipe();

					if (!plasmaOn)
					{
						setPlasma(true);
					}

					if (recipeInfo != null)
					{
						if (rememberedRecipeInfo != null)
						{
							if (rememberedRecipeInfo.getRecipe() != recipeInfo.getRecipe())
							{
								particleWorkDone = 0;
								startRecipe(); // to void the in use contents to stop infinite power exploit
							}
						}
						rememberedRecipeInfo = recipeInfo;

						if (canProduceProduct())
						{
							processRecipe();
							while (particleWorkDone >= recipeParticleWork && canProduceProduct())
							{
								startRecipe();
								finishRecipe();

							}
						}
					}
					else
					{
						casingExternalCooling();
					}

				}
				else
				{
					if (operational && plasmaOn)
					{
						containmentFaliure();
					}

					operational = false;
				}
			}
			else if(plasmaOn)
			{
				containmentFaliure();
			}
		}
		else if (plasmaOn)
		{
			setPlasma(false);
			operational = false;
		}
		
		
		refreshCasingFluidRecipe();
		if (canProcessCasingFluidInputs())
		{	
			produceCasingFluidProducts();
		}

		updateRedstone();
		getMultiblock().sendUpdateToListeningPlayers();
		return super.onUpdateServer();
	}

	
	
	private void setPlasma(boolean on)
	{
		plasmaOn = on;
		Axis axis = Axis.Z;
		if (multiblock.getExteriorLengthX() > multiblock.getExteriorLengthZ())
		{
			axis =  Axis.X;
		}
		

		Fluid plasma = FluidRegistry.getFluid("plasma");
		Block block = plasma == null ? null : plasma.getBlock();
		IBlockState plasmaState = block == null ? Blocks.AIR.getDefaultState() : block.getDefaultState();
		
		if (on)
		{
			for(BlockPos pos : getPlasmaPositions(axis))
			{
				if(multiblock.WORLD.getBlockState(pos) != plasmaState)
				{
					multiblock.WORLD.setBlockState(pos, plasmaState);
				}	
			}
		}
		else
		{
			for(BlockPos pos : getPlasmaPositions(axis))
			{
				if(multiblock.WORLD.getBlockState(pos) != Blocks.AIR.getDefaultState())
				{
					multiblock.WORLD.setBlockState(pos, Blocks.AIR.getDefaultState());
				}	
			}
		}
	}
	
	
	
	
	// Recipes
	
	private void startRecipe()
	{
		if(getMultiblock().tanks.get(4).getFluid() != null)
		{
			if(rememberedRecipeInfo.getRecipe().getFluidIngredients().get(0).getStack() != null && getMultiblock().tanks.get(4).getFluid().getFluid() == rememberedRecipeInfo.getRecipe().getFluidIngredients().get(0).getStack().getFluid())
			{
				getMultiblock().tanks.get(4).drain(rememberedRecipeInfo.getRecipe().getFluidIngredients().get(0).getStack(), true);
			}
			else if(rememberedRecipeInfo.getRecipe().getFluidIngredients().get(1).getStack() != null && getMultiblock().tanks.get(4).getFluid().getFluid() == rememberedRecipeInfo.getRecipe().getFluidIngredients().get(1).getStack().getFluid())
			{
				getMultiblock().tanks.get(4).drain(rememberedRecipeInfo.getRecipe().getFluidIngredients().get(1).getStack(), true);
			}
		}
		
		if(getMultiblock().tanks.get(5).getFluid() != null)
		{
			if(rememberedRecipeInfo.getRecipe().getFluidIngredients().get(0).getStack() != null && getMultiblock().tanks.get(5).getFluid().getFluid() == rememberedRecipeInfo.getRecipe().getFluidIngredients().get(0).getStack().getFluid())
			{
				getMultiblock().tanks.get(5).drain(rememberedRecipeInfo.getRecipe().getFluidIngredients().get(0).getStack(), true);
			}
			else if(rememberedRecipeInfo.getRecipe().getFluidIngredients().get(1).getStack() != null && getMultiblock().tanks.get(5).getFluid().getFluid() == rememberedRecipeInfo.getRecipe().getFluidIngredients().get(1).getStack().getFluid())
			{
				getMultiblock().tanks.get(5).drain(rememberedRecipeInfo.getRecipe().getFluidIngredients().get(1).getStack(), true);
			}
		}
	}

	
	private void processRecipe()
	{
		recipeParticleWork = recipeInfo.getRecipe().getParticleIngredients().get(0).getStack().getAmount();
		particleWorkDone += getMultiblock().beams.get(0).getParticleStack().getAmount();
		Long totalHeat = (Long) recipeInfo.getRecipe().getExtras().get(0);
		casingHeating = totalHeat/(double)(recipeParticleWork) * getMultiblock().beams.get(0).getParticleStack().getAmount();
		
		casingHeatBuffer.addHeat((long)casingHeating, false);
		
		excessCasingHeat += casingHeating - (long)casingHeating;
		
		if(excessCasingHeat > 1)
		{
			casingHeatBuffer.addHeat((long)excessCasingHeat, false);
			excessCasingHeat = excessCasingHeat - (long)excessCasingHeat;

		}
		if(casingHeatBuffer.isFull())
		{
			overheat();
		}
	}
	
	private void finishRecipe()
	{	
			IFluidIngredient fluidOutput1 = recipeInfo.getRecipe().getFluidProducts().get(0);
			IFluidIngredient fluidOutput2 = recipeInfo.getRecipe().getFluidProducts().get(1);
			if(fluidOutput1.getStack() != null)
			{
				getMultiblock().tanks.get(6).fill(fluidOutput1.getStack(), true);
			}
			if(fluidOutput2.getStack() != null)
			{
				getMultiblock().tanks.get(7).fill(fluidOutput2.getStack(), true);
			}

			particleWorkDone = Math.max(0, particleWorkDone - recipeParticleWork);
	}
	
	protected void refreshRecipe()
	{
		ArrayList<ParticleStack> particles = new ArrayList<ParticleStack>();
		ArrayList<Tank> tanks = new ArrayList<Tank>();
		particles.add(getMultiblock().beams.get(0).getParticleStack());
		tanks.add(getMultiblock().tanks.get(4));
		tanks.add(getMultiblock().tanks.get(5));
		recipeInfo = nucleosynthesis_chamber.getRecipeInfoFromInputs(new ArrayList<ItemStack>(), tanks, particles);
	}


	private boolean canProduceProduct()
	{
		
		IFluidIngredient fluidOutput1 = recipeInfo.getRecipe().getFluidProducts().get(0);
		IFluidIngredient fluidOutput2 = recipeInfo.getRecipe().getFluidProducts().get(1);
		if ((fluidOutput1.getStack() != null && getMultiblock().tanks.get(6).fill(fluidOutput1.getStack(), false) == fluidOutput1.getStack().amount)||fluidOutput1 instanceof EmptyFluidIngredient)
		{
			
			if ((fluidOutput2.getStack() != null && getMultiblock().tanks.get(7).fill(fluidOutput2.getStack(), false) == fluidOutput2.getStack().amount)||fluidOutput2 instanceof EmptyFluidIngredient)
			{
				return true;
			}
		}
		return false;
	}
	
	protected void refreshCasingFluidRecipe() 
	{
		casingCoolingRecipeInfo = vacuum_chamber_heating.getRecipeInfoFromInputs(new ArrayList<ItemStack>(),getMultiblock().tanks.subList(2, 3));
		if(casingCoolingRecipeInfo != null)
		{
			maxCasingCoolantIn = 1000 / casingCoolingRecipeInfo.getRecipe().getFissionHeatingHeatPerInputMB() * (int) (casingCooling * casingCoolingRecipeInfo.getRecipe().getFluidIngredients().get(0).getMaxStackSize(0));
			maxCasingCoolantOut = 1000 / casingCoolingRecipeInfo.getRecipe().getFissionHeatingHeatPerInputMB() * (int) (casingCooling * casingCoolingRecipeInfo.getRecipe().getFluidProducts().get(0).getMaxStackSize(0));
		}	
	}
	
	
	protected boolean canProcessCasingFluidInputs() 
	{
		
		if(casingCoolingRecipeInfo== null)
		{
			return false;
		}
				
		IFluidIngredient fluidProduct = casingCoolingRecipeInfo.getRecipe().getFluidProducts().get(0);
		if (fluidProduct.getMaxStackSize(0) <= 0 || fluidProduct.getStack() == null)
			return false;

		if (!getMultiblock().tanks.get(3).isEmpty())
		{
			if (!getMultiblock().tanks.get(3).getFluid().isFluidEqual(fluidProduct.getStack()))
			{
				return false;
			}
			else if (getMultiblock().tanks.get(3).getFluidAmount() + (maxCasingCoolantIn/1000 +1)*fluidProduct.getNextStack(0).amount > getMultiblock().tanks.get(3).getCapacity())			
			{
				return false;
			}
			
			else if (casingHeatBuffer.getHeatStored() < 1)
			{
				return false;
			}
		}
		return true;
	}
	
	private void produceCasingFluidProducts()
	{
		int uBConsumed = maxCasingCoolantIn;
		
	
		if(uBConsumed > getMultiblock().tanks.get(2).getFluidAmount() *1000)
		{
			uBConsumed = getMultiblock().tanks.get(2).getFluidAmount() *1000;
		}
		if(uBConsumed/1000D*casingCoolingRecipeInfo.getRecipe().getFissionHeatingHeatPerInputMB() > casingHeatBuffer.getHeatStored())
		{
			uBConsumed = (int) (1000D*1/(double)(casingCoolingRecipeInfo.getRecipe().getFissionHeatingHeatPerInputMB())*casingHeatBuffer.getHeatStored());
		}		
		
		int mBConsumed =0;	
		
		if(uBConsumed%1000 != 0)
		{
			mBConsumed = (uBConsumed + (1000-(uBConsumed%1000)))/1000;

			
			excessCasingCoolant += (1000-(uBConsumed%1000));
		}
		else
		{
			mBConsumed = uBConsumed/1000;
		}
		
		if(excessCasingCoolant > 1000)
		{
			mBConsumed -= excessCasingCoolant/1000;
			excessCasingCoolant = excessCasingCoolant%1000;
		}
		
		
		
		
		getMultiblock().tanks.get(2).changeFluidAmount((int) (-mBConsumed*getCoolingEfficiency()));
		if (getMultiblock().tanks.get(2).getFluidAmount() <= 0) getMultiblock().tanks.get(2).setFluidStored(null);
		
		casingHeatBuffer.changeHeatStored(-mBConsumed*casingCoolingRecipeInfo.getRecipe().getFissionHeatingHeatPerInputMB());
		
		
		IFluidIngredient fluidProduct = casingCoolingRecipeInfo.getRecipe().getFluidProducts().get(0);
		int producedCoolant = (int)(mBConsumed*getCoolingEfficiency())* fluidProduct.getNextStack(0).amount;
		if (getMultiblock().tanks.get(3).isEmpty())
		{
			getMultiblock().tanks.get(3).changeFluidStored(fluidProduct.getNextStack(0).getFluid(),producedCoolant);
		}
		else
		{
			getMultiblock().tanks.get(3).changeFluidAmount(producedCoolant);	
		}
			
	}
	

	public double getCoolingEfficiency()
	{
		if(casingCooling>casingHeating)
		{
			return Math.min(1D,(cooling_efficiency_leniency+casingHeating)/(double)casingCooling);
		}
		else
		{
			return Math.min(1D,(cooling_efficiency_leniency+casingHeating)/(-casingCooling+2*casingHeating));
		}	
	}
	
	
	
	private void containmentFaliure()
	{
		plasmaOn = false;
		List<BlockPos> components = new ArrayList<BlockPos>();
		
		for (TileVacuumChamberBeam beam : getPartMap(TileVacuumChamberBeam.class).values())
		{
				components.add(beam.getPos());
		}
		
		for (TileVacuumChamberPlasmaGlass glass : getPartMap(TileVacuumChamberPlasmaGlass.class).values())
		{
				components.add(glass.getPos());
		}
		for (TileVacuumChamberPlasmaNozzle nozzle : getPartMap(TileVacuumChamberPlasmaNozzle.class).values())
		{
				components.add(nozzle.getPos());
		}
		
		Fluid plasma = FluidRegistry.getFluid("plasma");
		Block block = plasma == null ? null : plasma.getBlock();
		IBlockState plasmaState = block == null ? Blocks.AIR.getDefaultState() : block.getDefaultState();
		
		
		if(!components.isEmpty())
		{	
			int breaches = 1+ rand.nextInt(1+components.size()/8);
			for(int i = 0; i < breaches; i++)
			{
				int j = rand.nextInt(components.size());
				BlockPos component = components.get(j);
				multiblock.WORLD.createExplosion(null, component.getX(), component.getY(), component.getZ(), 4.0f, true);
				multiblock.WORLD.setBlockState(component, plasmaState);
				components.remove(j);
			}
		}
		

	}
	
	private void overheat()
	{
		casingHeatBuffer.setHeatStored(0L);
		containmentFaliure();
	}
	
	public double getCasingTemperature()
	{
		return multiblock.ambientTemp+Math.round(CASING_MAX_TEMP * (float) casingHeatBuffer.getHeatStored() / casingHeatBuffer.getHeatCapacity());
	}
	
	
	protected void casingExternalCooling()
	{
		casingHeatBuffer.addHeat((long) ((multiblock.ambientTemp - getCasingTemperature()) * QMDConfig.accelerator_thermal_conductivity * multiblock.getExteriorSurfaceArea()), false);
	}
	
	// Client

	@Override
	public void onUpdateClient()
	{

	}

	

	private void refreshStats()
	{

	}
	
	protected void updateRedstone() 
	{
		
		for (TileVacuumChamberRedstonePort port : getPartMap(TileVacuumChamberRedstonePort.class).values())
		{
			if(getMultiblock().WORLD.getBlockState(port.getPos()).getValue(ACTIVE).booleanValue())
			{		
				port.setRedstoneLevel((int) (15 *(casingHeatBuffer.getHeatStored()/(double)casingHeatBuffer.getHeatCapacity())));	
			}
			else
			{
				port.setRedstoneLevel((int) (15 *(getMultiblock().getTemperature()/(double)getMultiblock().maxOperatingTemp)));	
			}
		}
	}

	// NBT

		@Override
		public void writeToLogicTag(NBTTagCompound logicTag, SyncReason syncReason)
		{
			super.writeToLogicTag(logicTag, syncReason);

			logicTag.setLong("particleWorkDone", particleWorkDone);
			logicTag.setLong("recipeParticleWork", recipeParticleWork);
			logicTag.setDouble("casingHeating", casingHeating);
			logicTag.setLong("casingCooling", casingCooling);
			logicTag.setDouble("excessCasingHeat", excessCasingHeat);
			logicTag.setInteger("maxCasingCoolantIn", maxCasingCoolantIn);
			logicTag.setInteger("maxCasingCoolantOut", maxCasingCoolantOut);
			logicTag.setInteger("excessCasingCoolant", excessCasingCoolant);
			casingHeatBuffer.writeToNBT(logicTag, "casingHeatBuffer");
			logicTag.setBoolean("plasmaOn", plasmaOn);

		}

		@Override
		public void readFromLogicTag(NBTTagCompound logicTag, SyncReason syncReason)
		{
			super.readFromLogicTag(logicTag, syncReason);

			particleWorkDone = logicTag.getLong("particleWorkDone");
			recipeParticleWork = logicTag.getLong("recipeParticleWork");
			casingHeating = logicTag.getDouble("casingHeating");
			casingCooling = logicTag.getLong("casingCooling");
			excessCasingHeat = logicTag.getDouble("excessCasingHeat");
			maxCasingCoolantIn = logicTag.getInteger("maxCasingCoolantIn");
			maxCasingCoolantOut = logicTag.getInteger("maxCasingCoolantOut");
			excessCasingCoolant = logicTag.getInteger("excessCasingCoolant");
			casingHeatBuffer.readFromNBT(logicTag, "casingHeatBuffer");
			plasmaOn = logicTag.getBoolean("plasmaOn");
		}
	
	// Packets

	@Override
	public VacuumChamberUpdatePacket getUpdatePacket()
	{
		return new NucleosynthesisChamberUpdatePacket(getMultiblock().controller.getTilePos(),
				getMultiblock().isChamberOn, getMultiblock().heating,getMultiblock().currentHeating, getMultiblock().maxCoolantIn,
				getMultiblock().maxCoolantOut, getMultiblock().maxOperatingTemp, getMultiblock().requiredEnergy,
				getMultiblock().heatBuffer, getMultiblock().energyStorage, getMultiblock().tanks, getMultiblock().beams,
				particleWorkDone, recipeParticleWork,casingHeating,casingCooling,maxCasingCoolantIn,maxCasingCoolantOut,casingHeatBuffer);
	}

	@Override
	public void onPacket(VacuumChamberUpdatePacket message)
	{
		super.onPacket(message);
		
		if (message instanceof NucleosynthesisChamberUpdatePacket)
		{
			NucleosynthesisChamberUpdatePacket packet = (NucleosynthesisChamberUpdatePacket) message;
			getMultiblock().beams = packet.beams;
			for (int i = 0; i < getMultiblock().tanks.size(); i++)
				getMultiblock().tanks.get(i).readInfo(message.tanksInfo.get(i));
		
			particleWorkDone = packet.particleWorkDone;
			recipeParticleWork = packet.recipeParticleWork;
			casingHeating = packet.casingHeating;
			casingCooling = packet.casingCooling;
			maxCasingCoolantIn = packet.maxCasingCoolantIn;
			maxCasingCoolantOut = packet.maxCasingCoolantOut;
			casingHeatBuffer.setHeatCapacity(packet.casingHeatBuffer.getHeatCapacity());
			casingHeatBuffer.setHeatStored(packet.casingHeatBuffer.getHeatStored());
		}
		
	}

	public ContainmentRenderPacket getRenderPacket()
	{
		return null;
	}

	public void onRenderPacket(ContainmentRenderPacket message)
	{
		
	}

	

	@Override
	public ContainerMultiblockController<VacuumChamber, IVacuumChamberController> getContainer(EntityPlayer player)
	{
		return new ContainerNucleosynthesisChamberController(player, getMultiblock().controller);
	}



}
