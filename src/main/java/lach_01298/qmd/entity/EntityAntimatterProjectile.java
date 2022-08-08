package lach_01298.qmd.entity;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import lach_01298.qmd.QMDDamageSources;
import lach_01298.qmd.network.AntimatterProjectileUpdatePacket;
import lach_01298.qmd.network.LeptonBeamUpdatePacket;
import lach_01298.qmd.network.QMDPacketHandler;
import nc.capability.radiation.entity.IEntityRads;
import nc.radiation.RadiationHelper;
import nc.util.DamageSources;

import java.awt.Color;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Enchantments;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.server.SPacketChangeGameState;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityAntimatterProjectile extends Entity implements IProjectile
{
	private static final Predicate<Entity> ARROW_TARGETS = Predicates.and(EntitySelectors.NOT_SPECTATING,
			EntitySelectors.IS_ALIVE, new Predicate<Entity>()
			{
				public boolean apply(@Nullable Entity p_apply_1_)
				{
					return p_apply_1_.canBeCollidedWith();
				}
			});
	
	private int xTile;
	private int yTile;
	private int zTile;
	private Block inTile;
	private int inData;
	private Color color;
	

	/** The owner of this arrow. */
	public Entity shootingEntity;
	private int ticksInAir;
	private double damage;

	/** The amount of knockback an arrow applies when it hits a mob. */

	public EntityAntimatterProjectile(World worldIn)
	{
		super(worldIn);
		this.xTile = -1;
		this.yTile = -1;
		this.zTile = -1;

		this.damage = 1.0f;
		this.setSize(0.5F, 0.5F);
	}

	public EntityAntimatterProjectile(World worldIn, double x, double y, double z)
	{
		this(worldIn);
		this.setPosition(x, y, z);
	}

	public EntityAntimatterProjectile(World worldIn, EntityLivingBase shooter, double damage, int color)
	{
		this(worldIn, shooter.posX, shooter.posY + (double) shooter.getEyeHeight() - 0.10000000149011612D,
				shooter.posZ);
		this.shootingEntity = shooter;
		this.damage = damage;
		this.color = new Color(color);
	}

	
	public Color getColor()
	{
		return this.color;
	}
	
	public void setColor(int color)
	{
		this.color = new Color(color);
	}
	
	/**
	 * Checks if the entity is in range to render.
	 */
	@SideOnly(Side.CLIENT)
	public boolean isInRangeToRenderDist(double distance)
	{
		double d0 = this.getEntityBoundingBox().getAverageEdgeLength() * 10.0D;

		if (Double.isNaN(d0))
		{
			d0 = 1.0D;
		}

		d0 = d0 * 64.0D * getRenderDistanceWeight();
		return distance < d0 * d0;
	}

	protected void entityInit()
	{
		
	}

	public void shoot(Entity shooter, float pitch, float yaw, float p_184547_4_, float velocity, float inaccuracy)
	{
		float f = -MathHelper.sin(yaw * 0.017453292F) * MathHelper.cos(pitch * 0.017453292F);
		float f1 = -MathHelper.sin(pitch * 0.017453292F);
		float f2 = MathHelper.cos(yaw * 0.017453292F) * MathHelper.cos(pitch * 0.017453292F);
		this.shoot((double) f, (double) f1, (double) f2, velocity, inaccuracy);
		this.motionX += shooter.motionX;
		this.motionZ += shooter.motionZ;

		if (!shooter.onGround)
		{
			this.motionY += shooter.motionY;
		}
	}

	/**
	 * Similar to setArrowHeading, it's point the throwable entity to a x, y, z
	 * direction.
	 */
	public void shoot(double x, double y, double z, float velocity, float inaccuracy)
	{
		float f = MathHelper.sqrt(x * x + y * y + z * z);
		x = x / (double) f;
		y = y / (double) f;
		z = z / (double) f;
		x = x + this.rand.nextGaussian() * 0.007499999832361937D * (double) inaccuracy;
		y = y + this.rand.nextGaussian() * 0.007499999832361937D * (double) inaccuracy;
		z = z + this.rand.nextGaussian() * 0.007499999832361937D * (double) inaccuracy;
		x = x * (double) velocity;
		y = y * (double) velocity;
		z = z * (double) velocity;
		this.motionX = x;
		this.motionY = y;
		this.motionZ = z;
		float f1 = MathHelper.sqrt(x * x + z * z);
		this.rotationYaw = (float) (MathHelper.atan2(x, z) * (180D / Math.PI));
		this.rotationPitch = (float) (MathHelper.atan2(y, (double) f1) * (180D / Math.PI));
		this.prevRotationYaw = this.rotationYaw;
		this.prevRotationPitch = this.rotationPitch;

	}

	/**
	 * Sets a target for the client to interpolate towards over the next few ticks
	 */
	@SideOnly(Side.CLIENT)
	public void setPositionAndRotationDirect(double x, double y, double z, float yaw, float pitch,
			int posRotationIncrements, boolean teleport)
	{
		this.setPosition(x, y, z);
		this.setRotation(yaw, pitch);
	}

	/**
	 * Updates the entity motion clientside, called by packets from the server
	 */
	@SideOnly(Side.CLIENT)
	public void setVelocity(double x, double y, double z)
	{
		this.motionX = x;
		this.motionY = y;
		this.motionZ = z;

		if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F)
		{
			float f = MathHelper.sqrt(x * x + z * z);
			this.rotationPitch = (float) (MathHelper.atan2(y, (double) f) * (180D / Math.PI));
			this.rotationYaw = (float) (MathHelper.atan2(x, z) * (180D / Math.PI));
			this.prevRotationPitch = this.rotationPitch;
			this.prevRotationYaw = this.rotationYaw;
			this.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);

		}
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	public void onUpdate()
	{
		super.onUpdate();

		if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F)
		{
			float f = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
			this.rotationYaw = (float) (MathHelper.atan2(this.motionX, this.motionZ) * (180D / Math.PI));
			this.rotationPitch = (float) (MathHelper.atan2(this.motionY, (double) f) * (180D / Math.PI));
			this.prevRotationYaw = this.rotationYaw;
			this.prevRotationPitch = this.rotationPitch;
		}

		BlockPos blockpos = new BlockPos(this.xTile, this.yTile, this.zTile);
		IBlockState iblockstate = this.world.getBlockState(blockpos);
		Block block = iblockstate.getBlock();

		if (iblockstate.getMaterial() != Material.AIR)
		{
			explode(world, this.getPosition());
			this.setDead();
		}


		++this.ticksInAir;
		
		
		
		Vec3d vec3d1 = new Vec3d(this.posX, this.posY, this.posZ);
		Vec3d vec3d = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
		RayTraceResult raytraceresult = this.world.rayTraceBlocks(vec3d1, vec3d, false, true, false);
		vec3d1 = new Vec3d(this.posX, this.posY, this.posZ);
		vec3d = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);

		if (raytraceresult != null)
		{
			vec3d = new Vec3d(raytraceresult.hitVec.x, raytraceresult.hitVec.y, raytraceresult.hitVec.z);
		}

		Entity entity = this.findEntityOnPath(vec3d1, vec3d);

		if (entity != null)
		{
			raytraceresult = new RayTraceResult(entity);
		}

		if (raytraceresult != null && raytraceresult.entityHit instanceof EntityPlayer)
		{
			EntityPlayer entityplayer = (EntityPlayer) raytraceresult.entityHit;

			if (this.shootingEntity instanceof EntityPlayer
					&& !((EntityPlayer) this.shootingEntity).canAttackPlayer(entityplayer))
			{
				raytraceresult = null;
			}
		}

		if (raytraceresult != null
				&& !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, raytraceresult))
		{
			this.onHit(raytraceresult);
		}

		this.posX += this.motionX;
		this.posY += this.motionY;
		this.posZ += this.motionZ;
		float f4 = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
		this.rotationYaw = (float) (MathHelper.atan2(this.motionX, this.motionZ) * (180D / Math.PI));

		for (this.rotationPitch = (float) (MathHelper.atan2(this.motionY, (double) f4)
				* (180D / Math.PI)); this.rotationPitch
						- this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F)
	

		while (this.rotationPitch - this.prevRotationPitch >= 180.0F)
		{
			this.prevRotationPitch += 360.0F;
		}

		while (this.rotationYaw - this.prevRotationYaw < -180.0F)
		{
			this.prevRotationYaw -= 360.0F;
		}

		while (this.rotationYaw - this.prevRotationYaw >= 180.0F)
		{
			this.prevRotationYaw += 360.0F;
		}

		this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
		this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
		float f1 = 0.99F;
		float f2 = 0.05F;

		if (this.isInWater())
		{
			explode(world, this.getPosition());
			this.setDead();
		}

		this.motionX *= (double) f1;
		this.motionY *= (double) f1;
		this.motionZ *= (double) f1;

		if (!this.hasNoGravity())
		{
			this.motionY -= 0.05000000074505806D;
		}

		this.setPosition(this.posX, this.posY, this.posZ);
		this.doBlockCollisions();
		
		if (!world.isRemote)
		{
			if (getColor() != null)
			{
				sendUpdatePacket();
			}
			else
			{
				this.setDead();
			}

			if (this.ticksInAir > 2000)
			{
				this.setDead();
			}
		}
		
		

	}

	/**
	 * Called when the arrow hits a block or an entity
	 */
	protected void onHit(RayTraceResult raytraceResultIn)
	{
		
		if(this.ticksExisted>1)
		{
			Entity entity = raytraceResultIn.entityHit;
			
			if (entity != null)
			{

				DamageSource damagesource;

				if (this.shootingEntity == null)
				{
					damagesource = QMDDamageSources.causeAntimatterLauncherDamage(this, this);
				}
				else
				{
					damagesource = QMDDamageSources.causeAntimatterLauncherDamage(this, this.shootingEntity);
				}


				if (entity.attackEntityFrom(damagesource, 10.0f))
				{
					if (entity instanceof EntityLivingBase)
					{
						EntityLivingBase entitylivingbase = (EntityLivingBase) entity;


						this.arrowHit(entitylivingbase);

						if (this.shootingEntity != null && entitylivingbase != this.shootingEntity
								&& entitylivingbase instanceof EntityPlayer
								&& this.shootingEntity instanceof EntityPlayerMP)
						{
							((EntityPlayerMP) this.shootingEntity).connection
									.sendPacket(new SPacketChangeGameState(6, 0.0F));
						}
					}

					this.playSound(SoundEvents.ENTITY_ARROW_HIT, 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
				}
				
			}
			else
			{
				BlockPos blockpos = raytraceResultIn.getBlockPos();
				this.xTile = blockpos.getX();
				this.yTile = blockpos.getY();
				this.zTile = blockpos.getZ();
				IBlockState iblockstate = this.world.getBlockState(blockpos);
				this.inTile = iblockstate.getBlock();
				this.inData = this.inTile.getMetaFromState(iblockstate);
				this.motionX = (double) ((float) (raytraceResultIn.hitVec.x - this.posX));
				this.motionY = (double) ((float) (raytraceResultIn.hitVec.y - this.posY));
				this.motionZ = (double) ((float) (raytraceResultIn.hitVec.z - this.posZ));
				float f2 = MathHelper
						.sqrt(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
				this.posX -= this.motionX / (double) f2 * 0.05000000074505806D;
				this.posY -= this.motionY / (double) f2 * 0.05000000074505806D;
				this.posZ -= this.motionZ / (double) f2 * 0.05000000074505806D;

				if (iblockstate.getMaterial() != Material.AIR)
				{
					this.inTile.onEntityCollision(this.world, blockpos, iblockstate, this);
				}
			}

			explode(world, this.getPosition());
			this.setDead();

		}
		
		
		
		
	}

	/**
	 * Tries to move the entity towards the specified location.
	 */
	public void move(MoverType type, double x, double y, double z)
	{
		super.move(type, x, y, z);

	}

	protected void arrowHit(EntityLivingBase living)
	{
	}

	@Nullable
	protected Entity findEntityOnPath(Vec3d start, Vec3d end)
	{
		Entity entity = null;
		List<Entity> list = this.world.getEntitiesInAABBexcluding(this,
				this.getEntityBoundingBox().expand(this.motionX, this.motionY, this.motionZ).grow(1.0D), ARROW_TARGETS);
		double d0 = 0.0D;

		for (int i = 0; i < list.size(); ++i)
		{
			Entity entity1 = list.get(i);

			if (entity1 != this.shootingEntity || this.ticksInAir >= 5)
			{
				AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().grow(0.30000001192092896D);
				RayTraceResult raytraceresult = axisalignedbb.calculateIntercept(start, end);

				if (raytraceresult != null)
				{
					double d1 = start.squareDistanceTo(raytraceresult.hitVec);

					if (d1 < d0 || d0 == 0.0D)
					{
						entity = entity1;
						d0 = d1;
					}
				}
			}
		}

		return entity;
	}

	public static void registerFixesArrow(DataFixer fixer, String name)
	{
	}

	public static void registerFixesArrow(DataFixer fixer)
	{
		registerFixesArrow(fixer, "Arrow");
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	public void writeEntityToNBT(NBTTagCompound compound)
	{
		compound.setInteger("xTile", this.xTile);
		compound.setInteger("yTile", this.yTile);
		compound.setInteger("zTile", this.zTile);
		ResourceLocation resourcelocation = Block.REGISTRY.getNameForObject(this.inTile);
		compound.setString("inTile", resourcelocation == null ? "" : resourcelocation.toString());
		compound.setByte("inData", (byte) this.inData);
		compound.setDouble("damage", this.damage);
		

	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	public void readEntityFromNBT(NBTTagCompound compound)
	{
		this.xTile = compound.getInteger("xTile");
		this.yTile = compound.getInteger("yTile");
		this.zTile = compound.getInteger("zTile");

		if (compound.hasKey("inTile", 8))
		{
			this.inTile = Block.getBlockFromName(compound.getString("inTile"));
		}
		else
		{
			this.inTile = Block.getBlockById(compound.getByte("inTile") & 255);
		}

		this.inData = compound.getByte("inData") & 255;

		if (compound.hasKey("damage", 99))
		{
			this.damage = compound.getDouble("damage");
		}
		

	}

	/**
	 * Called by a player entity when they collide with an entity
	 */
	public void onCollideWithPlayer(EntityPlayer entityIn)
	{

	}

	/**
	 * returns if this entity triggers Block.onEntityWalking on the blocks they walk
	 * on. used for spiders and wolves to prevent them from trampling crops
	 */
	protected boolean canTriggerWalking()
	{
		return false;
	}

	public void setDamage(double damageIn)
	{
		this.damage = damageIn;
	}

	public double getDamage()
	{
		return this.damage;
	}

	/**
	 * Returns true if it's possible to attack this entity with an item.
	 */
	public boolean canBeAttackedWithItem()
	{
		return false;
	}

	public float getEyeHeight()
	{
		return 0.0F;
	}

	public void explode(World world, BlockPos pos)
	{

		
		if (!world.isRemote)
		{

			double size = this.damage;
			world.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), (float) (size*2.5f), true);
			world.spawnEntity(new EntityGammaFlash(world, pos.getX(), pos.getY(), pos.getZ(), size));

			Set<EntityLivingBase> entitylist = new HashSet();
			double radius = 128 * Math.sqrt(size);

			entitylist.addAll(world.getEntitiesWithinAABB(EntityLivingBase.class,
					new AxisAlignedBB(pos.getX() - radius, pos.getY() - radius, pos.getZ() - radius,
							pos.getX() + radius, pos.getY() + radius, pos.getZ() + radius)));

			for (EntityLivingBase entity : entitylist)
			{
				double rads = (15 * 32 * 32 * size) / pos.distanceSq(entity.posX, entity.posY, entity.posZ);
				IEntityRads entityRads = RadiationHelper.getEntityRadiation(entity);
				entityRads.setRadiationLevel(RadiationHelper.addRadsToEntity(entityRads, entity, rads, false, false, 1));

				if (rads >= entityRads.getMaxRads())
				{
					entity.attackEntityFrom(DamageSources.FATAL_RADS, Float.MAX_VALUE);
				}
			}
		}
	}
	
	
	protected void sendUpdatePacket()
	{
		if (!this.world.isRemote)
		{
			QMDPacketHandler.instance.sendToAllAround(new AntimatterProjectileUpdatePacket(this), new TargetPoint(this.world.provider.getDimension(), this.posX,this.posY,this.posZ, 128));
		}
	}
	

}