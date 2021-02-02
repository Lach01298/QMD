package lach_01298.qmd.research;

import java.util.Set;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;


public interface IResearch
{

	public boolean giveResearch(String id); 
	public boolean removeResearch(String id); 
	public void addResearchPoints(int points,String id); 
	public void removeResearchPoints(int points,String id); 
	public void setPlayerResearches(Set<String> researches);
	public Set<String> getResearch(); 
	public int getResearchPoints(String id); 
	public boolean hasResearch(String id); 
	
	
	
	
	public class ResearchStorage implements IStorage<IResearch>
	{
		@Override
		public NBTBase writeNBT(Capability<IResearch> capability, IResearch instance, EnumFacing side)
		{
			
			
			
			NBTTagCompound nbt = new NBTTagCompound();
			NBTTagCompound researches = new NBTTagCompound();
			NBTTagCompound points = new NBTTagCompound();
			
			for(String r : Researches.getResearches())
			{
				
				NBTTagCompound tag = new NBTTagCompound();
				researches.setBoolean(r , instance.hasResearch(r));
				
			}
			
			for(String r : Researches.getResearches())
			{
				NBTTagCompound tag = new NBTTagCompound();
				points.setInteger(r, instance.getResearchPoints(r));
				
			}
			
			nbt.setTag("playerResearches", researches);
			nbt.setTag("playerResearchPoints", points);
			return nbt;
		}

		@Override
		public void readNBT(Capability<IResearch> capability, IResearch instance, EnumFacing side, NBTBase nbt)
		{
			
			if (nbt instanceof NBTTagCompound)
			{
				NBTTagCompound comp = (NBTTagCompound) nbt;
				NBTTagCompound researches = comp.getCompoundTag("playerResearches");
				NBTTagCompound points = comp.getCompoundTag("playerResearchPoints");

				for (String r : Researches.getResearches())
				{
					if (researches.getBoolean(r))
					{
						instance.giveResearch(r);
					}
					instance.addResearchPoints(points.getInteger(r), r);
				}
			}
		}
	}

	
	
	
	
}

