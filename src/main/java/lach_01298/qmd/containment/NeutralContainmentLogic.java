package lach_01298.qmd.containment;

import static lach_01298.qmd.recipes.QMDRecipes.cell_filling;
import static lach_01298.qmd.recipes.QMDRecipes.neutral_containment;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import lach_01298.qmd.QMD;
import lach_01298.qmd.config.QMDConfig;
import lach_01298.qmd.containment.tile.IContainmentController;
import lach_01298.qmd.containment.tile.TileContainmentBeamPort;
import lach_01298.qmd.containment.tile.TileContainmentCoil;
import lach_01298.qmd.containment.tile.TileContainmentLaser;
import lach_01298.qmd.containment.tile.TileNeutralContainmentController;
import lach_01298.qmd.entity.EntityGammaFlash;
import lach_01298.qmd.enums.EnumTypes.IOType;
import lach_01298.qmd.multiblock.container.ContainerNeutralContainmentController;
import lach_01298.qmd.multiblock.network.ContainmentRenderPacket;
import lach_01298.qmd.multiblock.network.ContainmentUpdatePacket;
import lach_01298.qmd.multiblock.network.NeutralContainmentUpdatePacket;
import lach_01298.qmd.network.QMDPacketHandler;
import lach_01298.qmd.particle.ParticleStack;
import lach_01298.qmd.particle.ParticleStorageAccelerator;
import lach_01298.qmd.recipe.QMDRecipe;
import lach_01298.qmd.recipe.QMDRecipeInfo;
import nc.capability.radiation.entity.IEntityRads;
import nc.multiblock.Multiblock;
import nc.multiblock.container.ContainerMultiblockController;
import nc.multiblock.tile.TileBeefAbstract.SyncReason;
import nc.radiation.RadiationHelper;
import nc.recipe.ProcessorRecipe;
import nc.recipe.RecipeInfo;
import nc.recipe.RecipeMatchResult;
import nc.recipe.ingredient.EmptyFluidIngredient;
import nc.recipe.ingredient.FluidIngredient;
import nc.recipe.ingredient.IFluidIngredient;
import nc.recipe.ingredient.IItemIngredient;
import nc.recipe.ingredient.ItemIngredient;
import nc.tile.internal.fluid.Tank;
import nc.util.DamageSources;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class NeutralContainmentLogic extends ContainmentLogic
{

	public long particle1WorkDone, particle2WorkDone, recipeParticle1Work =600, recipeParticle2Work = 600;
	
	public QMDRecipeInfo<QMDRecipe> recipeInfo;
	public QMDRecipeInfo<QMDRecipe> rememberedRecipeInfo;
	
	public RecipeInfo<ProcessorRecipe> cellRecipeInfo;
	
	public NeutralContainmentLogic(ContainmentLogic oldLogic)
	{
		super(oldLogic);
		getMultiblock().beams.add(new ParticleStorageAccelerator());
	}

	@Override
	public String getID()
	{
		return "neutral_containment";
	}

	// Multiblock Validation
	
	
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
	
	
	
	
	public boolean isMachineWhole(Multiblock multiblock) 
	{
		Containment con = getMultiblock();
		
		if (con.getExteriorLengthX() != getMultiblock().getExteriorLengthZ())
		{
			multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.containment.must_be_square", null);
			return false;
		}
		
		if (con.getExteriorLengthX() % 2 != 1)
		{
			multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.containment.must_be_odd", null);
			return false;
		}
		
		
		
		//coils
		for (BlockPos pos : getCoilPositions())
		{
			if (!(con.WORLD.getTileEntity(pos) instanceof TileContainmentCoil))
			{
				multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.containment.neutral.must_be_coil", pos);
				return false;
			}
		}
		
		Axis axis;
		BlockPos westMiddle = new BlockPos(con.getMinX(), con.getMiddleY(), con.getMiddleZ());
		BlockPos northMiddle = new BlockPos(con.getMiddleX(), con.getMiddleY(), con.getMinZ());
		
		if(multiblock.WORLD.getTileEntity(westMiddle) instanceof TileContainmentLaser)
		{
			axis = Axis.X;
		}
		else if(con.WORLD.getTileEntity(westMiddle) instanceof TileContainmentBeamPort)
		{
			axis = Axis.Z;
		}
		else
		{
			multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.containment.neutral.beam_port_or_laser", westMiddle);
			return false;
		}
		
		if(axis == Axis.X)
		{
			if(!(con.WORLD.getTileEntity(westMiddle.add(con.getInteriorLengthX()+1,0,0)) instanceof TileContainmentLaser))
			{
				multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.containment.neutral.must_be_laser", westMiddle.add(con.getInteriorLengthX()+1,0,0));
				return false;
			}
			
			if(!(con.WORLD.getTileEntity(northMiddle) instanceof TileContainmentBeamPort))
			{
				multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.containment.neutral.must_be_input_beam", northMiddle);
				return false;
			}
			else
			{
				TileContainmentBeamPort beam =(TileContainmentBeamPort) con.WORLD.getTileEntity(northMiddle);
				if(beam.getIOType() != IOType.INPUT)
				{
					multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.containment.neutral.must_be_input_beam", northMiddle);
					return false;
				}
			}
			
			if(!(con.WORLD.getTileEntity(northMiddle.add(0, 0, con.getInteriorLengthZ()+1)) instanceof TileContainmentBeamPort))
			{
				multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.containment.neutral.must_be_input_beam", northMiddle.add(0, 0, con.getInteriorLengthZ()+1));
				return false;
			}
			else
			{
				TileContainmentBeamPort beam =(TileContainmentBeamPort) con.WORLD.getTileEntity(northMiddle.add(0, 0, con.getInteriorLengthZ()+1));
				if(beam.getIOType() != IOType.INPUT)
				{
					multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.containment.neutral.must_be_input_beam", northMiddle.add(0, 0, con.getInteriorLengthZ()+1));
					return false;
				}
			}

		}
		else
		{
			if(!(con.WORLD.getTileEntity(westMiddle) instanceof TileContainmentBeamPort))
			{
				multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.containment.neutral.must_be_input_beam", westMiddle);
				return false;
			}
			else
			{
				TileContainmentBeamPort beam =(TileContainmentBeamPort) con.WORLD.getTileEntity(westMiddle);
				if(beam.getIOType() != IOType.INPUT)
				{
					multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.containment.neutral.must_be_input_beam", westMiddle);
					return false;
				}
			}
			
			if(!(con.WORLD.getTileEntity(westMiddle.add(con.getInteriorLengthX()+1,0,0)) instanceof TileContainmentBeamPort))
			{
				multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.containment.neutral.must_be_input_beam", westMiddle.add(con.getInteriorLengthX()+1,0,0));
				return false;
			}
			else
			{
				TileContainmentBeamPort beam =(TileContainmentBeamPort) con.WORLD.getTileEntity(westMiddle.add(con.getInteriorLengthX()+1,0,0));
				if(beam.getIOType() != IOType.INPUT)
				{
					multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.containment.neutral.must_be_input_beam", westMiddle.add(con.getInteriorLengthX()+1,0,0));
					return false;
				}
			}
		
			if(!(con.WORLD.getTileEntity(northMiddle) instanceof TileContainmentLaser))
			{
				multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.containment.neutral.must_be_laser", northMiddle);
				return false;
			}
			
			if(!(con.WORLD.getTileEntity(northMiddle.add(0, 0, con.getInteriorLengthZ()+1)) instanceof TileContainmentLaser))
			{
				multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.containment.neutral.must_be_laser", northMiddle.add(0, 0, con.getInteriorLengthZ()+1));
				return false;
			}
				
		}

		for (IContainmentController controller : getParts(IContainmentController.class))
		{
			controller.setIsRenderer(false);
		}
	
		
		for (IContainmentController controller : getParts(IContainmentController.class))
		{
			controller.setIsRenderer(true);
			break;
		}
		
		return super.isMachineWhole(multiblock);
	}
	
	
	
	
	
	public Set<BlockPos> getCoilPositions()
	{
		Set<BlockPos> postions = new HashSet<BlockPos>();
		Containment con = getMultiblock();
		
		boolean top = false;
		for(int i =0; i < 2; i++)
		{
			for (BlockPos pos : BlockPos.getAllInBoxMutable(
					con.getExtremeInteriorCoord(false, top, false).add(1, 0, 1),
					con.getExtremeInteriorCoord(true, top, false).add(-1, 0, 1)))
			{
				postions.add(pos.toImmutable());
			}
			for (BlockPos pos : BlockPos.getAllInBoxMutable(
					con.getExtremeInteriorCoord(false, top, true).add(1, 0, -1),
					con.getExtremeInteriorCoord(true, top, true).add(-1, 0, -1)))
			{
				postions.add(pos.toImmutable());
			}
			for (BlockPos pos : BlockPos.getAllInBoxMutable(
					con.getExtremeInteriorCoord(false, top, false).add(1, 0, 2),
					con.getExtremeInteriorCoord(false, top, true).add(1, 0, -2)))
			{
				postions.add(pos.toImmutable());
			}
			for (BlockPos pos : BlockPos.getAllInBoxMutable(
					con.getExtremeInteriorCoord(true, top, false).add(-1, 0, 2),
					con.getExtremeInteriorCoord(true, top, true).add(-1,0,-2)))
			{
				postions.add(pos.toImmutable());
			}
			top = true;
		}
		return postions;
	}
	
	
	
	
	
	
	
	
	// Multiblock Methods
	
	@Override
	public void onContainmentFormed()
	{
		getMultiblock().tanks.get(2).setCapacity((int) (Math.pow((getMultiblock().getInteriorLengthX()-4),3)*8000));
		if (!getWorld().isRemote)
		{
			int energy = 0;
			long heat = 0;
			for(TileContainmentCoil magnet :getMultiblock().getPartMap(TileContainmentCoil.class).values())
			{
				heat += QMDConfig.containment_part_heat[0];
				energy += QMDConfig.containment_part_power[0];
			}
			for(TileContainmentLaser laser :getMultiblock().getPartMap(TileContainmentLaser.class).values())
			{
				heat += QMDConfig.containment_part_heat[1];
				energy += QMDConfig.containment_part_power[1];
			}
			int io = 0;
			for(TileContainmentBeamPort port :getMultiblock().getPartMap(TileContainmentBeamPort.class).values())
			{
				port.setIONumber(io);
				io++;
			}
			
			getMultiblock().requiredEnergy = energy;
			getMultiblock().heating = heat; 

			if (getMultiblock().controller != null)
			{
				QMDPacketHandler.instance.sendToAll(getMultiblock().getFormPacket());
				getMultiblock().sendUpdateToListeningPlayers();
			}
			
		}
		
		for (TileContainmentLaser laser : getParts(TileContainmentLaser.class))
		{
			laser.setIsRenderer(true);
		}
		
		
		 super.onContainmentFormed();
	}
	
	public void onMachineDisassembled()
	{
		for(TileContainmentBeamPort tile :getPartMap(TileContainmentBeamPort.class).values())
		{
			tile.setIONumber(0);
		}
		for (TileContainmentLaser laser : getParts(TileContainmentLaser.class))
		{
			laser.setIsRenderer(false);
		}
		containmentFaliure();
		super.onMachineDisassembled();
	}
	
	@Override
	public boolean onUpdateServer()
	{
		getMultiblock().beams.get(0).setParticleStack(null);
		getMultiblock().beams.get(1).setParticleStack(null);
		pull();
		
		if (getMultiblock().energyStorage.extractEnergy(getMultiblock().requiredEnergy, true) == getMultiblock().requiredEnergy)
		{
			
			getMultiblock().energyStorage.changeEnergyStored(-getMultiblock().requiredEnergy);
			internalHeating();
			if (getMultiblock().getTemperature() <= getMultiblock().maxOperatingTemp)
			{
				operational = true;
				
				refreshCellRecipe();
				
				if(cellRecipeInfo != null)
				{
					
					if(canProduceCellProduct())
					{		
						
						produceCellProduct();
					}	
				}
				
				refreshRecipe();
				if(recipeInfo != null)
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
					
					
					if(canProduceProduct())
					{		
						
						particle1WorkDone += getMultiblock().beams.get(0).getParticleStack().getAmount();
						particle2WorkDone += getMultiblock().beams.get(1).getParticleStack().getAmount();
						produceProduct();
					}	
				}
			}
			else
			{
				if(operational)
				{
					containmentFaliure();
				}
				operational = false;
			
			}

		}
		else
		{
			containmentFaliure();
			operational = false;
		}
		
		
		if (getMultiblock().controller != null) 
		{
			QMDPacketHandler.instance.sendToAll(getMultiblock().getRenderPacket());
			getMultiblock().sendUpdateToListeningPlayers();
		}
		

		return super.onUpdateServer();
	}

	
	
	private void containmentFaliure()
	{
		if(!getMultiblock().tanks.get(2).isEmpty() && getMultiblock().tanks.get(2).getFluid() != null)
		{
			FluidStack fluid = getMultiblock().tanks.get(2).getFluid();
			double size = 1;
			switch(fluid.getFluid().getName())
			{
			case "antiHydrogen":
				size = 1;
				break;
			case "antiDeuterium":
				size = 2;
				break;
			case "antiTritium":
			case "antiHelium3":
				size = 3;
				break;
			case "antiHelium":
				size = 4;
				
			}
			size *= fluid.amount/1000d;
			BlockPos middle = new BlockPos(getMultiblock().getMiddleX(),getMultiblock().getMiddleY(),getMultiblock().getMiddleZ());
			
			getMultiblock().WORLD.createExplosion(null, middle.getX(), middle.getY(), middle.getZ(), (float)size*10f, true);
			getMultiblock().WORLD.spawnEntity(new EntityGammaFlash(getMultiblock().WORLD, middle.getX(), middle.getY(),  middle.getZ(), size));
			
			Set<EntityLivingBase> entitylist = new HashSet();
			double radius = 128 * Math.sqrt(size);

			entitylist.addAll(getMultiblock().WORLD.getEntitiesWithinAABB(EntityLivingBase.class,
					new AxisAlignedBB(middle.getX() - radius, middle.getY() - radius, middle.getZ() - radius, middle.getX() + radius,
							middle.getY() + radius, middle.getZ() + radius)));

			for (EntityLivingBase entity : entitylist)
			{
				
				double rads = (1000 * 32 * 32 * size) / middle.distanceSq(entity.posX, entity.posY, entity.posZ);
				IEntityRads entityRads = RadiationHelper.getEntityRadiation(entity);
				entityRads.setRadiationLevel(RadiationHelper.addRadsToEntity(entityRads, entity, rads, false, false, 1));
				if (rads >= entityRads.getMaxRads())
				{
					entity.attackEntityFrom(DamageSources.FATAL_RADS, Float.MAX_VALUE);
				}
			}
			getMultiblock().tanks.get(2).setFluidStored(null);
		}
		
	}

	protected void refreshRecipe() 
	{
		ArrayList<ParticleStack> particles = new ArrayList<ParticleStack>();
		particles.add(getMultiblock().beams.get(0).getParticleStack());
		particles.add(getMultiblock().beams.get(1).getParticleStack());
		
		recipeInfo = neutral_containment.getRecipeInfoFromInputs(new ArrayList<ItemStack>(), new ArrayList<Tank>(), particles);
		
	}
	
	protected void refreshCellRecipe()
	{
		ArrayList<IItemIngredient> itemIngredients = new ArrayList<IItemIngredient>();
		ArrayList<IFluidIngredient> fluidIngredients = new ArrayList<IFluidIngredient>();
		TileNeutralContainmentController cont = (TileNeutralContainmentController) getMultiblock().controller;

		ItemStack item = cont.getInventoryStacks().get(0).copy();
		item.setTagCompound(new NBTTagCompound());

		ArrayList<Tank> fluids = new ArrayList<Tank>();
		Tank tank = getMultiblock().tanks.get(2);
		if (tank.getFluid() != null)
		{
			FluidIngredient fluidIngredient = new FluidIngredient(tank.getFluid());
			fluidIngredients.add(fluidIngredient);
		}
		else
		{
			EmptyFluidIngredient fluidIngredient = new EmptyFluidIngredient();
			fluidIngredients.add(fluidIngredient);
		}

		ItemIngredient itemIngredient = new ItemIngredient(item);
		itemIngredients.add(itemIngredient);

		ProcessorRecipe recipe = cell_filling.getRecipeFromIngredients(itemIngredients, fluidIngredients);
		if (recipe != null)
		{
			RecipeMatchResult matchResult = recipe.matchIngredients(itemIngredients, fluidIngredients);
			cellRecipeInfo = new RecipeInfo(recipe, matchResult);
		}
		else
		{
			cellRecipeInfo = null;
		}

		if (cellRecipeInfo == null)
		{
			EmptyFluidIngredient fluidIngredient = new EmptyFluidIngredient();
			fluidIngredients = new ArrayList<IFluidIngredient>();
			fluidIngredients.add(fluidIngredient);

			recipe = cell_filling.getRecipeFromIngredients(itemIngredients, fluidIngredients);

			if (recipe != null)
			{
				RecipeMatchResult matchResult = recipe.matchIngredients(itemIngredients, fluidIngredients);
				cellRecipeInfo = new RecipeInfo(recipe, matchResult);
			}
		}

	}
	
	private boolean canProduceProduct()
	{
		
		FluidStack product = recipeInfo.getRecipe().getFluidProducts().get(0).getStack();
		if(product != null)
		{
			if(getMultiblock().tanks.get(2).fill(product, false) == product.amount)
			{
				return true;
			}	
		}
		
		
		return false;
	}
	
	private boolean canProduceCellProduct()
	{	
		boolean itemAllowed = false;
		TileNeutralContainmentController cont = (TileNeutralContainmentController) getMultiblock().controller;
		ItemStack itemProduct = cellRecipeInfo.getRecipe().getItemProducts().get(0).getStack();
		
		if(cont.getInventoryStacks().get(1).getCount() <= 0)
		{
			cont.getInventoryStacks().set(1, ItemStack.EMPTY);
		}
		if (cont.getInventoryStacks().get(1) == ItemStack.EMPTY)
		{	
			itemAllowed= true;
		}
		else if (cont.getInventoryStacks().get(1).isItemEqual(itemProduct) && cont.getInventoryStacks().get(0).getTagCompound().getInteger("energy") == cont.getInventoryStacks().get(1).getTagCompound().getInteger("energy"))
		{
			int count = cont.getInventoryStacks().get(1).getCount();
			if (count + itemProduct.getCount() <= itemProduct.getMaxStackSize())
			{
				itemAllowed= true;
			}
		}
		FluidStack fluidProduct = cellRecipeInfo.getRecipe().getFluidProducts().get(0).getStack();

		if(fluidProduct == null)
		{
			return true && itemAllowed;
		}
		else if (fluidProduct.amount == getMultiblock().tanks.get(2).fill(fluidProduct, false))
		{
			return true && itemAllowed;
		}
		
		return false;
	}
	
	private void produceProduct()
	{
		
		recipeParticle1Work = recipeInfo.getRecipe().getParticleIngredients().get(0).getStack().getAmount();
		recipeParticle2Work = recipeInfo.getRecipe().getParticleIngredients().get(1).getStack().getAmount();
		particle1WorkDone=Math.min(particle1WorkDone, recipeParticle1Work*64);
		particle2WorkDone=Math.min(particle2WorkDone, recipeParticle2Work*64);
		boolean switchInputs = false;
		if(getMultiblock().beams.get(0).getParticleStack() != null && recipeInfo.getRecipe().getParticleIngredients().get(0).getStack() != null)
		{
			if(recipeInfo.getRecipe().getParticleIngredients().get(0).getStack().getParticle() != getMultiblock().beams.get(0).getParticleStack().getParticle())
			{
				switchInputs = true;
			}
		}
		
		if(!switchInputs && getMultiblock().beams.get(1).getParticleStack() != null && recipeInfo.getRecipe().getParticleIngredients().get(1).getStack() != null)
		{
			if(recipeInfo.getRecipe().getParticleIngredients().get(1).getStack().getParticle() != getMultiblock().beams.get(1).getParticleStack().getParticle())
			{
				switchInputs = true;
			}
		}
		
		
		if(switchInputs)
		{
			while(particle1WorkDone >= recipeParticle2Work && particle2WorkDone >= recipeParticle1Work && canProduceProduct())
			{
				FluidStack product = recipeInfo.getRecipe().getFluidProducts().get(0).getStack();
				getMultiblock().tanks.get(2).fill(product, true);

				particle1WorkDone = Math.max(0, particle1WorkDone - recipeParticle2Work);
				particle2WorkDone = Math.max(0, particle2WorkDone - recipeParticle1Work);
			}	
		}
		else
		{
			while(particle1WorkDone >= recipeParticle1Work && particle2WorkDone >= recipeParticle2Work && canProduceProduct())
			{
				FluidStack product = recipeInfo.getRecipe().getFluidProducts().get(0).getStack();
				getMultiblock().tanks.get(2).fill(product, true);

				particle1WorkDone = Math.max(0, particle1WorkDone - recipeParticle1Work);
				particle2WorkDone = Math.max(0, particle2WorkDone - recipeParticle2Work);
			}	
		}
		

		
		

	}
	
	private void produceCellProduct()
	{
		TileNeutralContainmentController cont = (TileNeutralContainmentController) getMultiblock().controller;
		ItemStack itemProduct = cellRecipeInfo.getRecipe().getItemProducts().get(0).getStack();
		
		NBTTagCompound tag = cont.getInventoryStacks().get(0).getTagCompound();
		
		if (cont.getInventoryStacks().get(1) == ItemStack.EMPTY)
		{
			cont.getInventoryStacks().set(1, itemProduct);
			cont.getInventoryStacks().get(1).setTagCompound(tag);
			
			if(cont.getInventoryStacks().get(0).getCount() -cellRecipeInfo.getRecipe().getItemIngredients().get(0).getStack().getCount() <= 0)
			{
				cont.getInventoryStacks().set(0, ItemStack.EMPTY);
				
			}
			else
			{
				int inputCount = cont.getInventoryStacks().get(0).getCount();
				cont.getInventoryStacks().get(0).setCount(inputCount -cellRecipeInfo.getRecipe().getItemIngredients().get(0).getStack().getCount());
				
			}
			cont.markDirtyAndNotify();
			
		}
		else if (cont.getInventoryStacks().get(1).isItemEqual(itemProduct))
		{
			int count = cont.getInventoryStacks().get(1).getCount();
			if (count + itemProduct.getCount() <= itemProduct.getMaxStackSize())
			{
				cont.getInventoryStacks().get(1).setCount(count + itemProduct.getCount());
				if(cont.getInventoryStacks().get(0).getCount() -cellRecipeInfo.getRecipe().getItemIngredients().get(0).getStack().getCount() <= 0)
				{
					cont.getInventoryStacks().set(0, ItemStack.EMPTY);
					
					
				}
				else
				{
					int inputCount = cont.getInventoryStacks().get(0).getCount();
					cont.getInventoryStacks().get(0).setCount(inputCount -cellRecipeInfo.getRecipe().getItemIngredients().get(0).getStack().getCount());
					
				}
				cont.markDirtyAndNotify();
				
			}

		}
		
		FluidStack fluidProduct = cellRecipeInfo.getRecipe().getFluidProducts().get(0).getStack();
		getMultiblock().tanks.get(2).fill(fluidProduct, true);	
		FluidStack fluidIngredient = cellRecipeInfo.getRecipe().getFluidIngredients().get(0).getStack();
		getMultiblock().tanks.get(2).drain(fluidIngredient, true);	
	}
	
	@Override
	public void onUpdateClient() {
		
	}
	
	@SideOnly(Side.CLIENT)
	protected void updateRender() 
	{
		
	}
	
	
	
	public boolean isMultiblockOn()
	{
		return operational;
	}
	
	
	
	private void refreshStats()
	{
		
		
	}

	
	// Recipe Stuff
	

	@Override
	public ContainmentUpdatePacket getUpdatePacket()
	{
		return new NeutralContainmentUpdatePacket(getMultiblock().controller.getTilePos(),
				getMultiblock().isContainmentOn, getMultiblock().heating, getMultiblock().maxCoolantIn,
				getMultiblock().maxCoolantOut, getMultiblock().maxOperatingTemp, getMultiblock().requiredEnergy,
				getMultiblock().heatBuffer, getMultiblock().energyStorage, getMultiblock().tanks, getMultiblock().beams,
				particle1WorkDone, particle2WorkDone, recipeParticle1Work, recipeParticle2Work);
	}
	
	@Override
	public void onPacket(ContainmentUpdatePacket message)
	{
		super.onPacket(message);
		if (message instanceof NeutralContainmentUpdatePacket)
		{
			NeutralContainmentUpdatePacket packet = (NeutralContainmentUpdatePacket) message;
			getMultiblock().beams = packet.beams;
			for (int i = 0; i < getMultiblock().tanks.size(); i++) getMultiblock().tanks.get(i).readInfo(message.tanksInfo.get(i));
			particle1WorkDone = packet.particle1WorkDone;
			particle2WorkDone = packet.particle2WorkDone;
			recipeParticle1Work = packet.recipeParticle1Work;
			recipeParticle2Work = packet.recipeParticle2Work;
		}
	}
	
	public void onRenderPacket(ContainmentRenderPacket message) 
	{
		getMultiblock().tanks.get(2).setFluidAmount(message.tanksInfo.get(2).amount());
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
	
	
	
	
	
	
	@Override
	public ContainerMultiblockController<Containment, IContainmentController> getContainer(EntityPlayer player) 
	{
		return new ContainerNeutralContainmentController(player, (TileNeutralContainmentController) getMultiblock().controller);
	}
	
	
}
