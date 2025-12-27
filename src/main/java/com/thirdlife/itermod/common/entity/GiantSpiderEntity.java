package com.thirdlife.itermod.common.entity;


import com.thirdlife.itermod.common.IterModConfig;
import com.thirdlife.itermod.common.event.SpiderEggHatchEvent;
import com.thirdlife.itermod.common.registry.ModEntities;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.Set;

public class GiantSpiderEntity extends Spider {

    public GiantSpiderEntity(EntityType<? extends Spider> type, Level world) {
        super(type, world);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 40.0)
                .add(Attributes.ATTACK_DAMAGE, 7.0)
                .add(Attributes.MOVEMENT_SPEED, 0.225)
                .add(Attributes.FOLLOW_RANGE, 32)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.25)
                .add(Attributes.ATTACK_KNOCKBACK, 0.5)
                .add(Attributes.FOLLOW_RANGE, 32);

    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntityDimensions dimensions) {
        return dimensions.height * 0.85f;
    }

    @Override
    public int getExperienceReward() {
        return 78;
    }

    @Override
    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        return pSpawnData;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.15, true){
            @Override
            protected double getAttackReachSqr(LivingEntity entity) {
                return 2f;
            }
        });
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 0.8));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 8.0F));

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Animal.class, true));
    }

    @Override
    public MobType getMobType() {
        return MobType.ARTHROPOD;
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public void die(DamageSource source) {
        super.die(source);
        SpiderEggHatchEvent.spawnSpiderlings(this.level(), this.getX(), this.getY(), this.getZ());
    }


    public static void init(){
        SpawnPlacements.register(ModEntities.GIANT_SPIDER.get(), SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (entityType, level, reason, pos, random) -> {

            if (!IterModConfig.COMMON.giantSpiders.get()) {
                        return false;
                    }

            if (!level.getLevel().dimension().location().getPath().equals("overworld")) {
                        return false;
                    }

            if (level.getMaxLocalRawBrightness(pos) > 7) {
                        return false;
                    }

            boolean cave = false;
            boolean rightbiome = false;

            if (pos.getY() <= 32) {cave = true;}

            AABB searchArea = new AABB(pos.getX() - 50, pos.getY() - 20, pos.getZ() - 50,
                            pos.getX() + 50, pos.getY() + 20, pos.getZ() + 50);
                    int nearbySpiders = level.getEntitiesOfClass(GiantSpiderEntity.class, searchArea, e -> true).size();
                    if (nearbySpiders >= 2) {
                        return false;
                    }

                    Biome biome = level.getBiome(pos).value();
                    ResourceLocation biomeId = level.getLevel().registryAccess().registryOrThrow(Registries.BIOME)
                            .getKey(biome);

                    Set<ResourceLocation> allowedBiomes = Set.of(
                            new ResourceLocation("minecraft:dark_forest"),
                            new ResourceLocation("minecraft:old_growth_pine_taiga"),
                            new ResourceLocation("minecraft:old_growth_spruce_taiga")
                    );

                    if (allowedBiomes.contains(biomeId)) {
                        rightbiome = true;
                    }

                    if (!level.getBlockState(pos.below()).isSolidRender(level, pos.below())) {
                        return false;
                    }

                    if (!(cave || rightbiome)){
                        return false;
                    }

                    return true;
                });
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
    }
}
