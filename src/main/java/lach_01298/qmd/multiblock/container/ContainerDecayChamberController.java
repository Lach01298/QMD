package lach_01298.qmd.multiblock.container;

import javax.annotation.Nullable;

import lach_01298.qmd.container.slot.SlotQMDProcessorInput;
import lach_01298.qmd.multiblock.accelerator.Accelerator;
import lach_01298.qmd.multiblock.accelerator.tile.IAcceleratorController;
import lach_01298.qmd.multiblock.accelerator.tile.TileAcceleratorSource;
import lach_01298.qmd.multiblock.particleChamber.ParticleChamber;
import lach_01298.qmd.multiblock.particleChamber.tile.IParticleChamberController;
import lach_01298.qmd.multiblock.particleChamber.tile.TileTargetChamberController;
import lach_01298.qmd.recipe.QMDRecipeHandler;
import lach_01298.qmd.recipes.QMDRecipes;
import nc.container.slot.SlotFurnace;
import nc.multiblock.container.ContainerMultiblockController;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerDecayChamberController extends ContainerMultiblockController<ParticleChamber, IParticleChamberController>
{
	public ContainerDecayChamberController(EntityPlayer player, IParticleChamberController controller)
	{
		super(player, controller);
		
	}
}
