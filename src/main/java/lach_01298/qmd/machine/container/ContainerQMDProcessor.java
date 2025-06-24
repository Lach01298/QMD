
package lach_01298.qmd.machine.container;

import lach_01298.qmd.recipe.QMDRecipeHandler;
import nc.init.NCItems;
import nc.recipe.BasicRecipeHandler;
import nclegacy.container.ContainerTileLegacy;
import nclegacy.tile.IItemFluidProcessorLegacy;
import nclegacy.tile.ITileGuiLegacy;
import nclegacy.tile.IUpgradableLegacy;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerQMDProcessor<PROCESSOR extends IItemFluidProcessorLegacy & ITileGuiLegacy<?>> extends ContainerTileLegacy<PROCESSOR>
{
	protected final PROCESSOR tile;
	protected final QMDRecipeHandler recipeHandler;
	protected static final ItemStack SPEED_UPGRADE;
	protected static final ItemStack ENERGY_UPGRADE;

	public ContainerQMDProcessor(EntityPlayer player, PROCESSOR tileEntity, QMDRecipeHandler recipeHandler) {
		super(tileEntity);
		this.tile = tileEntity;
		this.recipeHandler = recipeHandler;
		((ITileGuiLegacy)tileEntity).addTileUpdatePacketListener(player);
	}

	public boolean canInteractWith(EntityPlayer player) {
		return this.tile.isUsableByPlayer(player);
	}

	public void onContainerClosed(EntityPlayer player) {
		super.onContainerClosed(player);
		((ITileGuiLegacy)this.tile).removeTileUpdatePacketListener(player);
	}

	public ItemStack transferStackInSlot(EntityPlayer player, int index) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = (Slot)this.inventorySlots.get(index);
		boolean hasUpgrades = this.tile instanceof IUpgradableLegacy && ((IUpgradableLegacy)this.tile).hasUpgrades();
		int upgrades = hasUpgrades ? ((IUpgradableLegacy)this.tile).getNumberOfUpgrades() : 0;
		int invStart = this.tile.getItemInputSize() + this.tile.getItemOutputSize() + upgrades;
		int speedUpgradeSlot = this.tile.getItemInputSize() + this.tile.getItemOutputSize();
		int otherUpgradeSlot = this.tile.getItemInputSize() + this.tile.getItemOutputSize() + 1;
		int invEnd = this.tile.getItemInputSize() + this.tile.getItemOutputSize() + 36 + upgrades;
		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			if (index >= this.tile.getItemInputSize() && index < invStart) {
				if (!this.mergeItemStack(itemstack1, invStart, invEnd, false)) {
					return ItemStack.EMPTY;
				}

				slot.onSlotChange(itemstack1, itemstack);
			} else if (index >= invStart) {
				if (hasUpgrades && itemstack1.getItem() == NCItems.upgrade) {
					if (this.tile.isItemValidForSlot(speedUpgradeSlot, itemstack1)) {
						if (!this.mergeItemStack(itemstack1, speedUpgradeSlot, speedUpgradeSlot + 1, false)) {
							return ItemStack.EMPTY;
						}
					} else if (this.tile.isItemValidForSlot(otherUpgradeSlot, itemstack1) && !this.mergeItemStack(itemstack1, otherUpgradeSlot, otherUpgradeSlot + 1, false)) {
						return ItemStack.EMPTY;
					}
				}

				if (this.recipeHandler.isValidItemInput(itemstack1)) {
					if (!this.mergeItemStack(itemstack1, 0, this.tile.getItemInputSize(), false)) {
						return ItemStack.EMPTY;
					}
				} else if (index >= invStart && index < invEnd - 9) {
					if (!this.mergeItemStack(itemstack1, invEnd - 9, invEnd, false)) {
						return ItemStack.EMPTY;
					}
				} else if (index >= invEnd - 9 && index < invEnd && !this.mergeItemStack(itemstack1, invStart, invEnd - 9, false)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.mergeItemStack(itemstack1, invStart, invEnd, false)) {
				return ItemStack.EMPTY;
			}

			if (itemstack1.isEmpty()) {
				slot.putStack(ItemStack.EMPTY);
			} else {
				slot.onSlotChanged();
			}

			if (itemstack1.getCount() == itemstack.getCount()) {
				return ItemStack.EMPTY;
			}

			slot.onTake(player, itemstack1);
		}

		return itemstack;
	}

	static {
		SPEED_UPGRADE = new ItemStack(NCItems.upgrade, 1, 0);
		ENERGY_UPGRADE = new ItemStack(NCItems.upgrade, 1, 1);
	}
}
