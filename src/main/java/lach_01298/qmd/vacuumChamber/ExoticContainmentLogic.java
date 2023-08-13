package lach_01298.qmd.vacuumChamber;

import static lach_01298.qmd.recipes.QMDRecipes.cell_filling;
import static lach_01298.qmd.recipes.QMDRecipes.neutral_containment;
import static nc.block.property.BlockProperties.ACTIVE;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;

import lach_01298.qmd.QMD;
import lach_01298.qmd.config.QMDConfig;
import lach_01298.qmd.entity.EntityGammaFlash;
import lach_01298.qmd.enums.EnumTypes.IOType;
import lach_01298.qmd.item.IItemParticleAmount;
import lach_01298.qmd.multiblock.network.ContainmentRenderPacket;
import lach_01298.qmd.multiblock.network.NeutralContainmentUpdatePacket;
import lach_01298.qmd.multiblock.network.VacuumChamberUpdatePacket;
import lach_01298.qmd.network.QMDPacketHandler;
import lach_01298.qmd.particle.ParticleStack;
import lach_01298.qmd.recipe.QMDRecipe;
import lach_01298.qmd.recipe.QMDRecipeInfo;
import lach_01298.qmd.vacuumChamber.tile.TileExoticContainmentController;
import lach_01298.qmd.vacuumChamber.tile.TileVacuumChamberBeamPort;
import lach_01298.qmd.vacuumChamber.tile.TileVacuumChamberCoil;
import lach_01298.qmd.vacuumChamber.tile.TileVacuumChamberLaser;
import lach_01298.qmd.vacuumChamber.tile.TileVacuumChamberRedstonePort;
import nc.capability.radiation.entity.IEntityRads;
import nc.multiblock.tile.TileBeefAbstract.SyncReason;
import nc.radiation.RadiationHelper;
import nc.recipe.BasicRecipe;
import nc.recipe.RecipeInfo;
import nc.recipe.RecipeMatchResult;
import nc.recipe.ingredient.EmptyFluidIngredient;
import nc.recipe.ingredient.FluidIngredient;
import nc.recipe.ingredient.IFluidIngredient;
import nc.recipe.ingredient.IItemIngredient;
import nc.recipe.ingredient.ItemIngredient;
import nc.tile.internal.fluid.Tank;
import nc.util.DamageSources;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;

public class ExoticContainmentLogic extends VacuumChamberLogic
{
	
	
	
	
	public long particle1WorkDone, particle2WorkDone, recipeParticle1Work = 600, recipeParticle2Work = 600;

	public boolean shouldSpecialRenderLasers = false;
	
	public QMDRecipeInfo<QMDRecipe> recipeInfo;
	public QMDRecipeInfo<QMDRecipe> rememberedRecipeInfo;

	public RecipeInfo<BasicRecipe> cellRecipeInfo;

	public ExoticContainmentLogic(VacuumChamberLogic oldLogic)
	{
		super(oldLogic);
		
		/*
		beam 0 = input particle 1
		beam 1 = input particle 2
		tank 0 = input coolant
		tank 1 = output coolant
		tank 2 = output fluid 
		*/
		
		//on the rare occasion of changing the multiblock to a different type with the tank full
		if(oldLogic instanceof NucleosynthesisChamberLogic)
		{
			getMultiblock().tanks.get(2).setFluidStored(null);
		}
		
	}

	@Override
	public String getID()
	{
		return "neutral_containment";
	}

	// Multiblock Size Limits

	@Override
	public int getMinimumInteriorLength()
	{
		return 5;
	}

	@Override
	public int getMaximumInteriorLength()
	{
		return 9;
	}

	// Multiblock Methods

	@Override
	public void onVacuumChamberFormed()
	{
		getMultiblock().tanks.get(2).setCapacity((int) (Math.pow((getMultiblock().getInteriorLengthX() - 4), 3) * 8000));
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
				if(getMultiblock().controller instanceof TileExoticContainmentController)
				{
					TileExoticContainmentController cont = (TileExoticContainmentController) getMultiblock().controller;
					cont.setIsRenderer(true);
				}
				
				
				getMultiblock().sendMultiblockUpdatePacketToAll();
				getMultiblock().markReferenceCoordForUpdate();
			}
			

		}
			
		
		if (getMultiblock().controller != null)
		{
			if (getMultiblock().controller instanceof TileExoticContainmentController)
			{
				TileExoticContainmentController cont = (TileExoticContainmentController) getMultiblock().controller;
				cont.setIsRenderer(true);
			}
		}
		
		for (TileVacuumChamberLaser laser : getParts(TileVacuumChamberLaser.class))
		{
			laser.setIsRenderer(true);
		}

		super.onVacuumChamberFormed();
	}

	public void onMachineDisassembled()
	{
		for (TileVacuumChamberBeamPort tile : getPartMap(TileVacuumChamberBeamPort.class).values())
		{
			tile.setIONumber(0);
		}
		
		if (getMultiblock().controller != null)
		{
			if (getMultiblock().controller instanceof TileExoticContainmentController)
			{
				TileExoticContainmentController cont = (TileExoticContainmentController) getMultiblock().controller;
				cont.setIsRenderer(true);
			}
		}
		
		for (TileVacuumChamberLaser laser : getParts(TileVacuumChamberLaser.class))
		{
			laser.setIsRenderer(false);
		}
		
		if(QMDConfig.exotic_containment_explosion ||QMDConfig.exotic_containment_gamma_flash)
		{
			containmentFailure();
		}
		super.onMachineDisassembled();
	}

	public boolean isMachineWhole()
	{
		VacuumChamber con = getMultiblock();

		if (con.getExteriorLengthX() != getMultiblock().getExteriorLengthZ())
		{
			multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.exotic_containment.must_be_square", null);
			return false;
		}

		if (con.getExteriorLengthX() % 2 != 1)
		{
			multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.exotic_containment.must_be_odd", null);
			return false;
		}

		// coils
		for (BlockPos pos : getCoilPositions())
		{
			if (!(con.WORLD.getTileEntity(pos) instanceof TileVacuumChamberCoil))
			{
				multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.exotic_containment.must_be_coil", pos);
				return false;
			}
		}
		
		//empty space
		Set<BlockPos> interior = new HashSet<BlockPos>();	
			for (BlockPos pos : BlockPos.getAllInBoxMutable(con.getExtremeInteriorCoord(false, false, false),
					con.getExtremeInteriorCoord(true, true, true)))
			{
				interior.add(pos.toImmutable());
			}
			interior.removeAll(getCoilPositions());

		for (BlockPos pos : interior)
		{
			if (con.WORLD.getBlockState(pos).getMaterial() != Material.AIR)
			{
				multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.exotic_containment.must_be_empty", pos);
				return false;
			}
		}
		
		
		
		

		Axis axis;
		BlockPos westMiddle = new BlockPos(con.getMinX(), con.getMiddleY(), con.getMiddleZ());
		BlockPos northMiddle = new BlockPos(con.getMiddleX(), con.getMiddleY(), con.getMinZ());

		if (multiblock.WORLD.getTileEntity(westMiddle) instanceof TileVacuumChamberLaser)
		{
			axis = Axis.X;
		}
		else if (con.WORLD.getTileEntity(westMiddle) instanceof TileVacuumChamberBeamPort)
		{
			axis = Axis.Z;
		}
		else
		{
			multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.exotic_containment.beam_port_or_laser",
					westMiddle);
			return false;
		}

		if (axis == Axis.X)
		{
			if (!(con.WORLD
					.getTileEntity(westMiddle.add(con.getInteriorLengthX() + 1, 0, 0)) instanceof TileVacuumChamberLaser))
			{
				multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.exotic_containment.must_be_laser",
						westMiddle.add(con.getInteriorLengthX() + 1, 0, 0));
				return false;
			}

			if (!(con.WORLD.getTileEntity(northMiddle) instanceof TileVacuumChamberBeamPort))
			{
				multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.exotic_containment.must_be_input_beam",
						northMiddle);
				return false;
			}
			else
			{
				TileVacuumChamberBeamPort beam = (TileVacuumChamberBeamPort) con.WORLD.getTileEntity(northMiddle);
				if (beam.getIOType() != IOType.INPUT)
				{
					multiblock.setLastError(
							QMD.MOD_ID + ".multiblock_validation.exotic_containment.must_be_input_beam", northMiddle);
					return false;
				}
			}

			if (!(con.WORLD.getTileEntity(
					northMiddle.add(0, 0, con.getInteriorLengthZ() + 1)) instanceof TileVacuumChamberBeamPort))
			{
				multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.exotic_containment.must_be_input_beam",
						northMiddle.add(0, 0, con.getInteriorLengthZ() + 1));
				return false;
			}
			else
			{
				TileVacuumChamberBeamPort beam = (TileVacuumChamberBeamPort) con.WORLD
						.getTileEntity(northMiddle.add(0, 0, con.getInteriorLengthZ() + 1));
				if (beam.getIOType() != IOType.INPUT)
				{
					multiblock.setLastError(
							QMD.MOD_ID + ".multiblock_validation.exotic_containment.must_be_input_beam",
							northMiddle.add(0, 0, con.getInteriorLengthZ() + 1));
					return false;
				}
			}

		}
		else
		{
			if (!(con.WORLD.getTileEntity(westMiddle) instanceof TileVacuumChamberBeamPort))
			{
				multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.exotic_containment.must_be_input_beam",
						westMiddle);
				return false;
			}
			else
			{
				TileVacuumChamberBeamPort beam = (TileVacuumChamberBeamPort) con.WORLD.getTileEntity(westMiddle);
				if (beam.getIOType() != IOType.INPUT)
				{
					multiblock.setLastError(
							QMD.MOD_ID + ".multiblock_validation.exotic_containment.must_be_input_beam", westMiddle);
					return false;
				}
			}

			if (!(con.WORLD.getTileEntity(
					westMiddle.add(con.getInteriorLengthX() + 1, 0, 0)) instanceof TileVacuumChamberBeamPort))
			{
				multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.exotic_containment.must_be_input_beam",
						westMiddle.add(con.getInteriorLengthX() + 1, 0, 0));
				return false;
			}
			else
			{
				TileVacuumChamberBeamPort beam = (TileVacuumChamberBeamPort) con.WORLD
						.getTileEntity(westMiddle.add(con.getInteriorLengthX() + 1, 0, 0));
				if (beam.getIOType() != IOType.INPUT)
				{
					multiblock.setLastError(
							QMD.MOD_ID + ".multiblock_validation.exotic_containment.must_be_input_beam",
							westMiddle.add(con.getInteriorLengthX() + 1, 0, 0));
					return false;
				}
			}

			if (!(con.WORLD.getTileEntity(northMiddle) instanceof TileVacuumChamberLaser))
			{
				multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.exotic_containment.must_be_laser",
						northMiddle);
				return false;
			}

			if (!(con.WORLD.getTileEntity(
					northMiddle.add(0, 0, con.getInteriorLengthZ() + 1)) instanceof TileVacuumChamberLaser))
			{
				multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.exotic_containment.must_be_laser",
						northMiddle.add(0, 0, con.getInteriorLengthZ() + 1));
				return false;
			}

		}
		int lasers = 0;
		for (TileVacuumChamberLaser laser : getPartMap(TileVacuumChamberLaser.class).values())
		{
			lasers++;
		}
		if(lasers != 2)
		{
			multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.exotic_containment.must_have_2_lasers",null);
			return false;
		}
		
		int beamPorts = 0;
		for (TileVacuumChamberBeamPort port : getPartMap(TileVacuumChamberBeamPort.class).values())
		{
			beamPorts++;
		}
		if(beamPorts != 2)
		{
			multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.exotic_containment.must_have_2_beam_ports",null);
			return false;
		}
		

		return super.isMachineWhole();
	}

	public Set<BlockPos> getCoilPositions()
	{
		Set<BlockPos> postions = new HashSet<BlockPos>();
		VacuumChamber con = getMultiblock();

		boolean top = false;
		for (int i = 0; i < 2; i++)
		{
			for (BlockPos pos : BlockPos.getAllInBoxMutable(con.getExtremeInteriorCoord(false, top, false).add(1, 0, 1),
					con.getExtremeInteriorCoord(true, top, false).add(-1, 0, 1)))
			{
				postions.add(pos.toImmutable());
			}
			for (BlockPos pos : BlockPos.getAllInBoxMutable(con.getExtremeInteriorCoord(false, top, true).add(1, 0, -1),
					con.getExtremeInteriorCoord(true, top, true).add(-1, 0, -1)))
			{
				postions.add(pos.toImmutable());
			}
			for (BlockPos pos : BlockPos.getAllInBoxMutable(con.getExtremeInteriorCoord(false, top, false).add(1, 0, 2),
					con.getExtremeInteriorCoord(false, top, true).add(1, 0, -2)))
			{
				postions.add(pos.toImmutable());
			}
			for (BlockPos pos : BlockPos.getAllInBoxMutable(con.getExtremeInteriorCoord(true, top, false).add(-1, 0, 2),
					con.getExtremeInteriorCoord(true, top, true).add(-1, 0, -2)))
			{
				postions.add(pos.toImmutable());
			}
			top = true;
		}
		return postions;
	}

	// Server
	
	@Override
	public boolean onUpdateServer()
	{
		getMultiblock().beams.get(0).setParticleStack(null);
		getMultiblock().beams.get(1).setParticleStack(null);
		pull();

		
		
		if (getMultiblock().energyStorage.extractEnergy(getMultiblock().requiredEnergy,
				true) == getMultiblock().requiredEnergy)
		{

			getMultiblock().energyStorage.changeEnergyStored(-getMultiblock().requiredEnergy);
			internalHeating();
			
			if (getMultiblock().getTemperature() <= getMultiblock().maxOperatingTemp)
			{
				operational = true;

				refreshCellRecipe();

				if (cellRecipeInfo != null)
				{

					produceCellProduct();
				}

				refreshRecipe();
				if (recipeInfo != null)
				{

					if (rememberedRecipeInfo != null)
					{
						if (rememberedRecipeInfo.getRecipe() != recipeInfo.getRecipe())
						{
							particle1WorkDone = 0;
							particle2WorkDone = 0;
						}
					}
					rememberedRecipeInfo = recipeInfo;

					if (canProduceProduct())
					{
						boolean switchInputs = false;

						if (getMultiblock().beams.get(0).getParticleStack() != null)
						{

							if (recipeInfo.getRecipe().getParticleIngredients().get(0).getStack() != null)
							{
								if (recipeInfo.getRecipe().getParticleIngredients().get(0).getStack()
										.getParticle() != getMultiblock().beams.get(0).getParticleStack().getParticle())
								{
									switchInputs = true;
								}
							}
							else
							{
								switchInputs = true;
							}
						}

						if (!switchInputs && getMultiblock().beams.get(1).getParticleStack() != null)
						{
							if (recipeInfo.getRecipe().getParticleIngredients().get(1).getStack() != null)
							{
								if (recipeInfo.getRecipe().getParticleIngredients().get(1).getStack()
										.getParticle() != getMultiblock().beams.get(1).getParticleStack().getParticle())
								{
									switchInputs = true;
								}
							}
							else
							{
								switchInputs = true;
							}
						}

						if (getMultiblock().beams.get(0).getParticleStack() != null)
						{
							particle1WorkDone += getMultiblock().beams.get(0).getParticleStack().getAmount();
						}

						if (getMultiblock().beams.get(1).getParticleStack() != null)
						{
							particle2WorkDone += getMultiblock().beams.get(1).getParticleStack().getAmount();
						}

						produceProduct(switchInputs);
					}
				}
			}
			else
			{
				if (operational)
				{
					containmentFailure();
				}
				operational = false;

			}

		}
		else
		{
			containmentFailure();
			operational = false;
		}

		updateRedstone();
		
		if (getMultiblock().controller != null)
		{
			QMDPacketHandler.instance.sendToAll(getMultiblock().getRenderPacket());
			getMultiblock().sendMultiblockUpdatePacketToListeners();
			getMultiblock().sendRenderToAllPlayers();
		}

		
		return super.onUpdateServer();
	}

	private void containmentFailure()
	{
		
		if (!getMultiblock().tanks.get(2).isEmpty() && getMultiblock().tanks.get(2).getFluid() != null)
		{
			FluidStack fluid = getMultiblock().tanks.get(2).getFluid();
			double size = 1;
			switch (fluid.getFluid().getName())
			{
			case "antihydrogen":
				size = 1;
				break;
			case "antideuterium":
				size = 2;
				break;
			case "antitritium":
			case "antihelium3":
				size = 3;
				break;
			case "antiHelium":
				size = 4;
				break;
			case "positronium":
				size = 0.00054;
			case "muonium":
				size = 0.11;
				break;
			case "tauonium":
				size = 1.9;
				break;
			case "glueballs":
				size = 1.8;
			}
			size *= fluid.amount / 1000d;
			
			BlockPos middle = new BlockPos(getMultiblock().getMiddleX(), getMultiblock().getMiddleY(),
					getMultiblock().getMiddleZ());

			if(QMDConfig.exotic_containment_explosion)
			{
				getMultiblock().WORLD.createExplosion(null, middle.getX(), middle.getY(), middle.getZ(), (float) (size * QMDConfig.exotic_containment_explosion_size),
						true);
			}
			else
			{
				multiblock.WORLD.destroyBlock(multiblock.controller.getTilePos(), false);
			}
			
			if(QMDConfig.exotic_containment_gamma_flash)
			{
				getMultiblock().WORLD.spawnEntity(
						new EntityGammaFlash(getMultiblock().WORLD, middle.getX(), middle.getY(), middle.getZ(), size));
				
	
				Set<EntityLivingBase> entitylist = new HashSet();
				double radius = 128 * Math.sqrt(size);
	
				entitylist.addAll(getMultiblock().WORLD.getEntitiesWithinAABB(EntityLivingBase.class,
						new AxisAlignedBB(middle.getX() - radius, middle.getY() - radius, middle.getZ() - radius,
								middle.getX() + radius, middle.getY() + radius, middle.getZ() + radius)));
	
				for (EntityLivingBase entity : entitylist)
				{
					IEntityRads entityRads = RadiationHelper.getEntityRadiation(entity);
					if(entityRads != null)
					{
						double rads = Math.min(QMDConfig.exotic_containment_radiation * size,(QMDConfig.exotic_containment_radiation * size) / middle.distanceSq(entity.posX, entity.posY, entity.posZ));
						entityRads.setRadiationLevel(RadiationHelper.addRadsToEntity(entityRads, entity, rads, false, false, 1));
						if (rads >= entityRads.getMaxRads())
						{
							entity.attackEntityFrom(DamageSources.FATAL_RADS, Float.MAX_VALUE);
						}
					}
				}
			}
			getMultiblock().tanks.get(2).setFluidStored(null);
		}

	}
	
	public @Nonnull List<Tank> getHeaterVentTanks(List<Tank> backupTanks)
	{
		return getMultiblock().isAssembled() ? getMultiblock().tanks.subList(0, 2) : backupTanks;
	}
	

	// Recipes
	
	protected void refreshRecipe()
	{
		ArrayList<ParticleStack> particles = new ArrayList<ParticleStack>();
		particles.add(getMultiblock().beams.get(0).getParticleStack());
		particles.add(getMultiblock().beams.get(1).getParticleStack());

		recipeInfo = neutral_containment.getRecipeInfoFromInputs(new ArrayList<ItemStack>(), new ArrayList<Tank>(),
				particles);

	}

	protected void refreshCellRecipe()
	{
		ArrayList<IItemIngredient> itemIngredients = new ArrayList<IItemIngredient>();
		ArrayList<IFluidIngredient> fluidIngredients = new ArrayList<IFluidIngredient>();
		TileExoticContainmentController cont = (TileExoticContainmentController) getMultiblock().controller;

		if (cont.getInventoryStacks().get(0).getItem() instanceof IItemParticleAmount)
		{

			ItemStack itemStack = cont.getInventoryStacks().get(0);
			IItemParticleAmount item = (IItemParticleAmount) itemStack.getItem();
			
			if (item.getAmountStored(itemStack) == IItemParticleAmount.getCapacity(itemStack) && IItemParticleAmount.getCapacity(itemStack) > 0)
			{

				// cell emptying
				ItemIngredient itemIngredient = new ItemIngredient(cont.getInventoryStacks().get(0));
				itemIngredients.add(itemIngredient);

				EmptyFluidIngredient fluidIngredient = new EmptyFluidIngredient();
				fluidIngredients = new ArrayList<IFluidIngredient>();
				fluidIngredients.add(fluidIngredient);

				BasicRecipe recipe = cell_filling.getRecipeFromIngredients(itemIngredients, fluidIngredients);
				if (recipe != null)
				{
					RecipeMatchResult matchResult = recipe.matchIngredients(itemIngredients, fluidIngredients);
					cellRecipeInfo = new RecipeInfo(recipe, matchResult);
				}
			}
			else
			{
				
				// cell filling
				if (item.getAmountStored(itemStack) == 0)
				{

					// fill empty cell
					ItemIngredient itemIngredient = new ItemIngredient(
							IItemParticleAmount.cleanNBT(cont.getInventoryStacks().get(0)));
					itemIngredients.add(itemIngredient);
				}
				else
				{
					// fill partially full cell
					ItemIngredient itemIngredient = new ItemIngredient(item.getEmptyItem());
					itemIngredients.add(itemIngredient);
				}

				ArrayList<Tank> fluids = new ArrayList<Tank>();
				Tank tank = getMultiblock().tanks.get(2);
				if (tank.getFluid() != null)
				{
					FluidStack copy = tank.getFluid().copy();
			

					FluidIngredient fluidIngredient = new FluidIngredient(copy);
					fluidIngredients.add(fluidIngredient);

					BasicRecipe recipe = cell_filling.getRecipeFromIngredients(itemIngredients, fluidIngredients);
					if (recipe != null)
					{
						RecipeMatchResult matchResult = recipe.matchIngredients(itemIngredients, fluidIngredients);
						cellRecipeInfo = new RecipeInfo(recipe, matchResult);
					}
				}
			}
		}

	}

	private boolean canProduceProduct()
	{

		FluidStack product = recipeInfo.getRecipe().getFluidProducts().get(0).getStack();
		if (product != null)
		{
			if (getMultiblock().tanks.get(2).fill(product, false) == product.amount)
			{
				return true;
			}
		}

		return false;
	}

	private void produceProduct(boolean switchInputs)
	{

		if (switchInputs)
		{
			if (recipeInfo.getRecipe().getParticleIngredients().get(1).getStack() == null)
			{
				recipeParticle1Work = 0;
			}
			else
			{
				recipeParticle1Work = recipeInfo.getRecipe().getParticleIngredients().get(1).getStack().getAmount();
			}

			if (recipeInfo.getRecipe().getParticleIngredients().get(0).getStack() == null)
			{
				recipeParticle2Work = 0;
			}
			else
			{
				recipeParticle2Work = recipeInfo.getRecipe().getParticleIngredients().get(0).getStack().getAmount();
			}
		}
		else
		{
			if (recipeInfo.getRecipe().getParticleIngredients().get(0).getStack() == null)
			{
				recipeParticle1Work = 0;
			}
			else
			{
				recipeParticle1Work = recipeInfo.getRecipe().getParticleIngredients().get(0).getStack().getAmount();
			}

			if (recipeInfo.getRecipe().getParticleIngredients().get(1).getStack() == null)
			{
				recipeParticle2Work = 0;
			}
			else
			{
				recipeParticle2Work = recipeInfo.getRecipe().getParticleIngredients().get(1).getStack().getAmount();
			}

		}

		

		while (particle1WorkDone >= recipeParticle1Work && particle2WorkDone >= recipeParticle2Work
				&& canProduceProduct())
		{
			FluidStack product = recipeInfo.getRecipe().getFluidProducts().get(0).getStack();
			getMultiblock().tanks.get(2).fill(product, true);

			particle1WorkDone = Math.max(0, particle1WorkDone - recipeParticle1Work);
			particle2WorkDone = Math.max(0, particle2WorkDone - recipeParticle2Work);
		}

	}

	private void produceCellProduct()
	{
		TileExoticContainmentController cont = (TileExoticContainmentController) getMultiblock().controller;
		ItemStack itemInput = cont.getInventoryStacks().get(0);
		ItemStack itemOutput = cont.getInventoryStacks().get(1);

		if (itemOutput.getCount() <= 0)
		{
			cont.getInventoryStacks().set(1, ItemStack.EMPTY); // fix weird 0xtile.air bug
		}

		if (itemOutput != ItemStack.EMPTY)
		{
			return;
		}

		IItemParticleAmount item;
		if (itemInput.getItem() instanceof IItemParticleAmount)
		{
			item = (IItemParticleAmount) itemInput.getItem();
		}
		else
		{
			return;
		}

		ItemStack itemProduct = cellRecipeInfo.getRecipe().getItemProducts().get(0).getStack();
		int amount = item.getAmountStored(itemInput);

		if (amount == 0)
		{
			// fill empty cells
			
			if (cellRecipeInfo.getRecipe().getFluidProducts().get(0).getStack() == null)
			{
				ItemStack outputItem = cellRecipeInfo.getRecipe().getItemProducts().get(0).getStack();
				
				IItemParticleAmount output;
				if (outputItem.getItem() instanceof IItemParticleAmount)
				{
					output = (IItemParticleAmount) outputItem.getItem();
				}
				else
				{
					return;
				}
				
				
				int amountPerMillibuckets = IItemParticleAmount.getCapacity(outputItem) / cellRecipeInfo.getRecipe().getFluidIngredients().get(0).getStack().amount;

				if (!getMultiblock().tanks.get(2).isEmpty())
				{
					int cellAmount = amountPerMillibuckets * getMultiblock().tanks.get(2).drain(cellRecipeInfo.getRecipe().getFluidIngredients().get(0).getStack(), true).amount;

					output.setAmountStored(outputItem, cellAmount);
					cont.getInventoryStacks().set(0, outputItem);

					if (output.getAmountStored(outputItem) == IItemParticleAmount.getCapacity(outputItem))
					{
						cont.getInventoryStacks().set(1, outputItem);
						cont.getInventoryStacks().set(0, ItemStack.EMPTY);
					}
				}
			}
		}
		else if (amount < IItemParticleAmount.getCapacity(itemInput))
		{
			// fill partially full cells
			
			
			if (cellRecipeInfo.getRecipe().getFluidProducts().get(0).getStack() == null)
			{
				
				ItemStack output = cellRecipeInfo.getRecipe().getItemProducts().get(0).getStack();
				
				if (output.getItem() == cont.getInventoryStacks().get(0).getItem()
						&& output.getMetadata() == cont.getInventoryStacks().get(0).getMetadata()) // make sure it the
																									// right cell type
				{
					
					int amountPerMillibuckets = IItemParticleAmount.getCapacity(output) / cellRecipeInfo.getRecipe().getFluidIngredients().get(0).getStack().amount;
					
					if (!getMultiblock().tanks.get(2).isEmpty())
					{

						int recipemb = cellRecipeInfo.getRecipe().getFluidIngredients().get(0).getStack().amount;
						
						int cellAmount = 0;
						if(recipemb - amount / amountPerMillibuckets <= 0)
						{
							cellAmount = amount + amountPerMillibuckets; // the extra bit for non integer (amount / amountPerMillibuckets)
						}
						else
						{
							cellAmount = amount + amountPerMillibuckets * getMultiblock().tanks.get(2).drain(recipemb - amount / amountPerMillibuckets, true).amount;
						}
						
						
						item.setAmountStored(output, cellAmount);
						cont.getInventoryStacks().set(0, output);

						if (item.getAmountStored(output) == IItemParticleAmount.getCapacity(output))
						{
							cont.getInventoryStacks().set(1, output);
							cont.getInventoryStacks().set(0, ItemStack.EMPTY);
						}
					}
				}
			}
		}
		else
		{
			// empty full cells
			
			if (cellRecipeInfo.getRecipe().getFluidProducts().get(0).getStack() != null)
			{
				
				FluidStack fluidProduct = cellRecipeInfo.getRecipe().getFluidProducts().get(0).getStack();
				if (getMultiblock().tanks.get(2).fill(fluidProduct, false) == fluidProduct.amount)
				{
					
					getMultiblock().tanks.get(2).fill(fluidProduct, true);
					cont.getInventoryStacks().set(0, ItemStack.EMPTY);

					ItemStack output = cellRecipeInfo.getRecipe().getItemProducts().get(0).getStack();
					item.setAmountStored(output, IItemParticleAmount.getCapacity(output));
					cont.getInventoryStacks().set(1, output);
				}
			}

		}
	}

	
	protected void updateRedstone() 
	{
		
		for (TileVacuumChamberRedstonePort port : getPartMap(TileVacuumChamberRedstonePort.class).values())
		{
			if(getMultiblock().WORLD.getBlockState(port.getPos()).getValue(ACTIVE).booleanValue())
			{		
				if(getMultiblock().tanks.get(2).getFluidAmount() > 0 && (int)(15 *(getMultiblock().tanks.get(2).getFluidAmount()/(double)getMultiblock().tanks.get(2).getCapacity())) == 0)
				{
					port.setRedstoneLevel(1);
				}
				else
				{
					port.setRedstoneLevel((int) (15 *(getMultiblock().tanks.get(2).getFluidAmount()/(double)getMultiblock().tanks.get(2).getCapacity())));			
				}
					
			}
			else
			{
				port.setRedstoneLevel((int) (15 *(getMultiblock().getTemperature()/(double)getMultiblock().maxOperatingTemp)));	
			}
		}
	}
	
	
	
	
	// Client

	@Override
	public void onUpdateClient()
	{

	}

	

	private void refreshStats()
	{

	}

	// NBT

	@Override
	public void writeToLogicTag(NBTTagCompound logicTag, SyncReason syncReason)
	{
		super.writeToLogicTag(logicTag, syncReason);

		logicTag.setLong("particle1WorkDone", particle1WorkDone);
		logicTag.setLong("particle2WorkDone", particle2WorkDone);
		logicTag.setLong("recipeParticle1Work", recipeParticle1Work);
		logicTag.setLong("recipeParticle2Work", recipeParticle2Work);
	}

	@Override
	public void readFromLogicTag(NBTTagCompound logicTag, SyncReason syncReason)
	{
		super.readFromLogicTag(logicTag, syncReason);

		particle1WorkDone = logicTag.getLong("particle1WorkDone");
		particle2WorkDone = logicTag.getLong("particle2WorkDone");
		recipeParticle1Work = logicTag.getLong("recipeParticle1Work");
		recipeParticle2Work = logicTag.getLong("recipeParticle2Work");

	}
	
	// Packets

	@Override
	public VacuumChamberUpdatePacket getMultiblockUpdatePacket()
	{
		return new NeutralContainmentUpdatePacket(getMultiblock().controller.getTilePos(),
				getMultiblock().isChamberOn, getMultiblock().heating,getMultiblock().currentHeating, getMultiblock().maxCoolantIn,
				getMultiblock().maxCoolantOut, getMultiblock().maxOperatingTemp, getMultiblock().requiredEnergy,
				getMultiblock().heatBuffer, getMultiblock().energyStorage, getMultiblock().tanks, getMultiblock().beams,
				particle1WorkDone, particle2WorkDone, recipeParticle1Work, recipeParticle2Work);
	}

	@Override
	public void onMultiblockUpdatePacket(VacuumChamberUpdatePacket message)
	{
		super.onMultiblockUpdatePacket(message);
		if (message instanceof NeutralContainmentUpdatePacket)
		{
			NeutralContainmentUpdatePacket packet = (NeutralContainmentUpdatePacket) message;
			getMultiblock().beams = packet.beams;
			for (int i = 0; i < getMultiblock().tanks.size(); i++)
				getMultiblock().tanks.get(i).readInfo(message.tanksInfo.get(i));
			particle1WorkDone = packet.particle1WorkDone;
			particle2WorkDone = packet.particle2WorkDone;
			recipeParticle1Work = packet.recipeParticle1Work;
			recipeParticle2Work = packet.recipeParticle2Work;
		}
	}

	public ContainmentRenderPacket getRenderPacket()
	{
		return new ContainmentRenderPacket(getMultiblock().controller.getTilePos(), getMultiblock().isChamberOn,
				getMultiblock().tanks);
	}

	public void onRenderPacket(ContainmentRenderPacket message)
	{
		getMultiblock().tanks.get(2).setFluidAmount(message.tanksInfo.get(2).amount());
		
	}

	

	/*@Override
	public ContainerMultiblockController<VacuumChamber, IVacuumChamberController> getContainer(EntityPlayer player)
	{
		return new ContainerExoticContainmentController(player,
				(TileExoticContainmentController) getMultiblock().controller);
	}*/

}
