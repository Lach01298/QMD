package lach_01298.qmd.item;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.collect.Sets;

import lach_01298.qmd.QMD;
import lach_01298.qmd.fluid.CellFluids;
import lach_01298.qmd.fluid.FluidContainerItemWrapper;
import lach_01298.qmd.fluid.IFluidContainerItem;
import nc.item.IInfoItem;
import nc.util.Lang;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * A universal bucket that can hold any liquid
 */
public class ItemCell extends Item implements IFluidContainerItem, IInfoItem
{
  
	public static ArrayList<ItemStack> fluidList = new ArrayList<>();
	static Set<Fluid> cellFluids = Sets.newHashSet();

    public ItemCell()
    {
    	setMaxStackSize(1);
		setNoRepair();
    }

    public static void addFluid(Fluid fluid) 
    {
    	 if(fluid == null) 
         {
             return;
         }
         if (!FluidRegistry.isFluidRegistered(fluid))
         {
         	 return;
         }
        cellFluids.add(fluid);
    	
    	ItemStack itemStack = new ItemStack(QMDItems.cell);
		IFluidContainerItem cell = (IFluidContainerItem) itemStack.getItem();
		cell.fill(itemStack, new FluidStack(fluid,  Fluid.BUCKET_VOLUME), true);
		fluidList.add(itemStack);
	}
  
    
    @Override
    public void getSubItems(@Nullable CreativeTabs tab, @Nonnull NonNullList<ItemStack> subItems)
    {
    	if (isInCreativeTab(tab)) 
    	{
    		subItems.add(new ItemStack(this, 1, 0));
        	subItems.addAll(fluidList);
    	}
    	
    }

    @Override
    @Nonnull
    public String getItemStackDisplayName(@Nonnull ItemStack stack)
    {
    	FluidStack fluidStack = getFluid(stack);
        if (fluidStack == null)
        {
        	return super.getItemStackDisplayName(stack);
        }
        else
        {
        	return Lang.localise("fluid."+ fluidStack.getFluid().getName()) + " " + Lang.localise(stack.getTranslationKey()+".name");
        }
    	
    }

   
    @Override
	public boolean isEnchantable(ItemStack stack) 
    {

		return false;
	}

	@Override
	public boolean isDamageable() {

		return true;
	}
   

	@Override
	public FluidStack getFluid(ItemStack container)
	{

		if (container.getTagCompound() == null)
		{
			container.setTagCompound(new NBTTagCompound());
		}
		if (!container.getTagCompound().hasKey("fluid"))
		{
			return null;
		}
		return FluidStack.loadFluidStackFromNBT(container.getTagCompound().getCompoundTag("fluid"));
	}
    
	@Override
	public int getCapacity(ItemStack container)
	{
		return Fluid.BUCKET_VOLUME;
	}
   
	@Override
	public int fill(ItemStack container, FluidStack resource, boolean doFill)
	{
		if (resource != null)
		{

			if (cellFluids.contains(resource.getFluid()))
			{

				if (container.getTagCompound() == null)
				{
					container.setTagCompound(new NBTTagCompound());
				}
				if (resource == null || resource.amount <= 0)
				{
					return 0;
				}
				int capacity = getCapacity(container);

				if (!doFill)
				{
					if (!container.getTagCompound().hasKey("fluid"))
					{
						return Math.min(capacity, resource.amount);
					}
					FluidStack stack = FluidStack
							.loadFluidStackFromNBT(container.getTagCompound().getCompoundTag("fluid"));

					if (stack == null)
					{
						return Math.min(capacity, resource.amount);
					}
					if (!stack.isFluidEqual(resource))
					{
						return 0;
					}
					return Math.min(capacity - stack.amount, resource.amount);
				}
				if (!container.getTagCompound().hasKey("fluid"))
				{
					NBTTagCompound fluidTag = resource.writeToNBT(new NBTTagCompound());

					if (capacity < resource.amount)
					{
						fluidTag.setInteger("amount", capacity);
						container.getTagCompound().setTag("fluid", fluidTag);
						return capacity;
					}
					fluidTag.setInteger("amount", resource.amount);
					container.getTagCompound().setTag("fluid", fluidTag);
					return resource.amount;
				}
				NBTTagCompound fluidTag = container.getTagCompound().getCompoundTag("fluid");
				FluidStack stack = FluidStack.loadFluidStackFromNBT(fluidTag);

				if (!stack.isFluidEqual(resource))
				{
					return 0;
				}
				int filled = capacity - stack.amount;

				if (resource.amount < filled)
				{
					stack.amount += resource.amount;
					filled = resource.amount;
				}
				else
				{
					stack.amount = capacity;
				}
				container.getTagCompound().setTag("fluid", stack.writeToNBT(fluidTag));
				return filled;
			}
		}
		return 0;
	}
	

	@Override
	public FluidStack drain(ItemStack container, int maxDrain, boolean doDrain)
	{

		if (container.getTagCompound() == null)
		{
			container.setTagCompound(new NBTTagCompound());
		}
		if (!container.getTagCompound().hasKey("fluid") || maxDrain == 0)
		{
			return null;
		}
		FluidStack stack = FluidStack.loadFluidStackFromNBT(container.getTagCompound().getCompoundTag("fluid"));

		if (stack == null)
		{
			return null;
		}

		int drained = Math.min(stack.amount, maxDrain);

		if (doDrain)
		{
			if (maxDrain >= stack.amount)
			{
				container.getTagCompound().removeTag("fluid");
				return stack;
			}
			NBTTagCompound fluidTag = container.getTagCompound().getCompoundTag("fluid");
			fluidTag.setInteger("fluid", fluidTag.getInteger("fluid") - drained);
			container.getTagCompound().setTag("fluid", fluidTag);
		}
		stack.amount = drained;
		return stack;
	}
	
   


	@Override
	public void setInfo()
	{
		// TODO Auto-generated method stub
		
	}
	
	
	
	

	
	
}
