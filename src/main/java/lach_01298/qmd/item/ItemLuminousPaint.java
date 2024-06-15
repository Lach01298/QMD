package lach_01298.qmd.item;

import lach_01298.qmd.block.QMDBlocks;
import lach_01298.qmd.enums.MaterialTypes.LuminousPaintType;
import nc.item.NCItemMeta;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.entity.player.*;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static nc.block.property.BlockProperties.FACING_ALL;

public class ItemLuminousPaint extends NCItemMeta
{

	 public ItemLuminousPaint()
	 {
        super(LuminousPaintType.class);
	 }
	
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		boolean flag = worldIn.getBlockState(pos).getBlock().isReplaceable(worldIn, pos);
		BlockPos blockpos = flag ? pos : pos.offset(facing);
		ItemStack itemstack = player.getHeldItem(hand);

		Block block;
		
		switch(itemstack.getMetadata())
		{
			case 0:
				block =  QMDBlocks.greenLuminousPaint;
				break;
			case 1:
				block =  QMDBlocks.blueLuminousPaint;
				break;
			case 2:
				block =  QMDBlocks.orangeLuminousPaint;
				break;
			default:
				block =  QMDBlocks.greenLuminousPaint;
		}
		
		if (player.canPlayerEdit(blockpos, facing, itemstack)
				&& worldIn.mayPlace(worldIn.getBlockState(blockpos).getBlock(), blockpos, false, facing, player)
				&& block.canPlaceBlockOnSide(worldIn, blockpos, facing))
		{
			worldIn.setBlockState(blockpos, block.getDefaultState().withProperty(FACING_ALL, facing));

			if (player instanceof EntityPlayerMP)
			{
				CriteriaTriggers.PLACED_BLOCK.trigger((EntityPlayerMP) player, blockpos, itemstack);
			}

			itemstack.shrink(1);
			return EnumActionResult.SUCCESS;
		}
		else
		{
			return EnumActionResult.FAIL;
		}
	}

	
}
