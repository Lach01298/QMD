package lach_01298.qmd.item;

import java.util.List;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import nc.item.IInfoItem;
import nc.item.NCItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;

public abstract class ItemGun  extends NCItem
{
	
	public ItemGun(String... tooltip)
	{
		super(tooltip);
		this.maxStackSize = 1;
	}





	public RayTraceResult rayTrace(EntityLivingBase entity, double reachDistance, float partialTicks, boolean hitBlocks, boolean hitEntities)
	{
		Vec3d eyesPos = entity.getPositionEyes(partialTicks);
		Vec3d lookVec = entity.getLook(1.0F);
		Vec3d rayVec = eyesPos.add(lookVec.scale(reachDistance));
		Entity pointedEntity = null;
		Vec3d hitVec = null;
		RayTraceResult entityHit = null;
		RayTraceResult blockHit = null;
		RayTraceResult missHit = new RayTraceResult(RayTraceResult.Type.MISS, eyesPos.subtract(rayVec), EnumFacing.UP, new BlockPos(MathHelper.floor(eyesPos.subtract(rayVec).x),MathHelper.floor(eyesPos.subtract(rayVec).y),MathHelper.floor(eyesPos.subtract(rayVec).z)));
		
		if(hitEntities)
		{
		
		List<Entity> list = entity.world.getEntitiesInAABBexcluding(entity, entity.getEntityBoundingBox().expand(lookVec.x * reachDistance, lookVec.y * reachDistance,lookVec.z * reachDistance).grow(1.0D, 1.0D, 1.0D),
				Predicates.and(EntitySelectors.NOT_SPECTATING, new Predicate<Entity>()
				{
					public boolean apply(@Nullable Entity entity)
					{
						return entity != null && entity.canBeCollidedWith();
					}
				}));
		
		
		
			double d2 = reachDistance;

			for (int j = 0; j < list.size(); ++j)
			{
				Entity entity1 = list.get(j);
				AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().grow((double) entity1.getCollisionBorderSize());
				RayTraceResult raytraceresult = axisalignedbb.calculateIntercept(eyesPos, rayVec);
				
				if (axisalignedbb.contains(eyesPos))
				{
					pointedEntity = entity1;
					hitVec = raytraceresult == null ? eyesPos : raytraceresult.hitVec;
				}
				 else if (raytraceresult != null)
	             {
	                 double d3 = eyesPos.distanceTo(raytraceresult.hitVec);

	                 if (d3 < d2 || d2 == 0.0D)
	                 {
	                     if (entity1.getLowestRidingEntity() == entity.getLowestRidingEntity() && !entity1.canRiderInteract())
	                     {
	                         if (d2 == 0.0D)
	                         {
	                             pointedEntity = entity1;
	                             hitVec = raytraceresult.hitVec;
	                         }
	                     }
	                     else
	                     {
	                         pointedEntity = entity1;
	                         hitVec = raytraceresult.hitVec;
	                         d2 = d3;
	                     }
	                 }
	             }
	         }
			if (pointedEntity != null)
	         {  
		    	entityHit = new RayTraceResult(pointedEntity, hitVec);
	         }
		}
		
		
	     if(hitBlocks)
	     {
	    	 blockHit = entity.world.rayTraceBlocks(eyesPos, rayVec, false, false, true); 
	     }
	     
	    
	    
	   
	     double blockDistance = 0;
	     double entityDistance = 0;
	     
	     if(entityHit != null)
	     {
	    	 entityDistance = entityHit.hitVec.distanceTo(entity.getPositionVector());
	     }
	     
	     if(blockHit != null)
	     {
	    	 blockDistance = blockHit.hitVec.distanceTo(entity.getPositionVector());
	     }

		if (hitEntities && hitBlocks)
		{
			if (entityDistance < blockDistance && entityDistance > 0)
			{
				return entityHit;
			}
			else
			{
				return blockHit;
			}
		}
		else if (hitEntities)
		{
			if(entityHit == null)
			{
				return missHit;
			}
			return entityHit;
		}
		else
		{
			if(blockHit == null)
			{
				return missHit;
			}
			return blockHit;
		}
	     
	    
	}





}
