package net.azor.azorsdesertplus.block.custom;

import net.azor.azorsdesertplus.block.ModBlocks;
import net.azor.azorsdesertplus.item.ModItems;
import net.azor.azorsdesertplus.util.ModTags;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageSources;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldEvents;

import java.util.Optional;


public class QuicksandBlock extends Block implements FluidDrainable {
    private static final VoxelShape FALLING_SHAPE = VoxelShapes.cuboid(0.0, 0.0, 0.0, 1.0, 0.9, 1.0);

    public QuicksandBlock(Settings settings) {
        super(settings);
    }

    public boolean isSideInvisible(BlockState state, BlockState stateFrom, Direction direction) {
        return stateFrom.isOf(this) || super.isSideInvisible(state, stateFrom, direction);
    }

    public VoxelShape getCullingShape(BlockState state, BlockView world, BlockPos pos) {

        return VoxelShapes.empty();
    }

    public boolean shouldSuffocate(World world, LivingEntity entity) {
        return world.getBlockState(new BlockPos(entity.getBlockX(), (int)(entity.getEyeY() - 0.1), entity.getBlockZ())).isOf(ModBlocks.QUICKSAND);
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (!(entity instanceof LivingEntity) || entity.getBlockStateAtPos().isOf(this)) {
            entity.slowMovement(state, new Vec3d(0.4, 1, 0.4));

            if (world.isClient) {
                Random random = world.getRandom();
                boolean moved = entity.lastRenderX != entity.getX() || entity.lastRenderZ != entity.getZ();

                if (moved && random.nextFloat() < 0.2) {
                    if (entity instanceof LivingEntity living && shouldSuffocate(world, living)) {
                        living.damage(world.getDamageSources().drown(), 1.0F);
                    }

                    if (random.nextBoolean()) {
                        spawnParticles(world, state, new Vec3d(entity.getX(), entity.getY(), entity.getZ()));
                    }
                }

            }
        }
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (context instanceof EntityShapeContext entityShapeContext) {
            Entity entity = entityShapeContext.getEntity();
            if (entity != null) {
                if (entity.fallDistance > 2.5F) {
                    return FALLING_SHAPE;
                }

                if (entity instanceof FallingBlockEntity || canWalkOnQuickSand(entity) && context.isAbove(VoxelShapes.fullCube(), pos, false) && !context.isDescending()) {
                    return super.getCollisionShape(state, world, pos, context);
                }
            }
        }
        return VoxelShapes.empty();
    }

    public boolean canWalkOnQuickSand(Entity entity) {
        if (entity.getType().isIn(ModTags.QUICKSAND_WALKABLE_MOBS)) {
            return true;
        }
        else return entity instanceof LivingEntity && ((LivingEntity) entity).getEquippedStack(EquipmentSlot.FEET).isOf(Items.LEATHER_BOOTS);
    }

    public void spawnParticles(World world, BlockState state, Vec3d pos) {
        if (world.isClient) {
            Random random = world.getRandom();

            world.addParticle(new BlockStateParticleEffect(ParticleTypes.FALLING_DUST, state), pos.x, (double)(pos.y + 1), pos.z, (double)(MathHelper.nextBetween(random, -1.0F, 1.0F) * 0.1F), 0.05F, (double)(MathHelper.nextBetween(random, -1.0F, 1.0F) * 0.1F));
        }
    }

    @Override
    public ItemStack tryDrainFluid(WorldAccess world, BlockPos pos, BlockState state) {
        world.setBlockState(pos, Blocks.AIR.getDefaultState(), Block.REDRAW_ON_MAIN_THREAD, Block.NOTIFY_ALL);

        if (!world.isClient()) {
            world.syncWorldEvent(WorldEvents.BLOCK_BROKEN, pos, Block.getRawIdFromState(state));
        }

        return new ItemStack(ModItems.QUICKSAND_BUCKET);
    }

    @Override
    public Optional<SoundEvent> getBucketFillSound() {
        return Optional.of(SoundEvents.BLOCK_POWDER_SNOW_BREAK);
    }

    @Override
    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return true;
    }
}
