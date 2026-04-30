package com.malignant.iter.common.entity;

import com.malignant.iter.common.registry.ModSpawnRestrictions;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
// TODO: NetworkHooks removed in NeoForge 1.21 - use vanilla entity spawn packets

import javax.annotation.Nullable;

public class GhoulEntity extends Monster {

    public GhoulEntity(EntityType<GhoulEntity> type, Level world) {
        super(type, world);
    }

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Mob.createMobAttributes();
        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.225f);
        builder = builder.add(Attributes.MAX_HEALTH, 20);
        builder = builder.add(Attributes.ARMOR, 0);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 7);
        builder = builder.add(Attributes.FOLLOW_RANGE, 32);
        return builder;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new MeleeAttackGoal(this, 1.6f, true) {
        });

        this.goalSelector.addGoal(1, new FleeSunGoal(this, 1.0D));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 0.8));
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));



        this.targetSelector.addGoal(1, new HurtByTargetGoal(this).setAlertOthers());
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true, false));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Villager .class, true, false));
    }


    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ZOMBIE_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return SoundEvents.ZOMBIE_HURT;
    }

    @Override
    protected SoundEvent getDeathSound(){
        return SoundEvents.ZOMBIE_DEATH;
    }

    public void aiStep() {
        if (this.isAlive()) {
            boolean flag = this.isSunBurnTick();
            if (flag) {
                ItemStack itemstack = this.getItemBySlot(EquipmentSlot.HEAD);
                if (!itemstack.isEmpty()) {
                    if (itemstack.isDamageableItem()) {
                        itemstack.setDamageValue(itemstack.getDamageValue() + this.random.nextInt(2));
                        if (itemstack.getDamageValue() >= itemstack.getMaxDamage()) {
                            this.onEquippedItemBroken(this.getItemBySlot(EquipmentSlot.HEAD).getItem(), EquipmentSlot.HEAD);
                            this.setItemSlot(EquipmentSlot.HEAD, ItemStack.EMPTY);
                        }
                    }

                    flag = false;
                }

                if (flag) {
                    this.setRemainingFireTicks(8*20);
                }
            }
        }

        super.aiStep();
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty,
                                        MobSpawnType reason, @Nullable SpawnGroupData spawnData) {
        spawnData = super.finalizeSpawn(world, difficulty, reason, spawnData);
        this.populateDefaultEquipmentSlots(this.random, difficulty);
        return spawnData;
    }

    public static boolean GhoulSpawnRules(
            EntityType<GhoulEntity> entityType,
            ServerLevelAccessor level,
            MobSpawnType spawnType,
            BlockPos pos,
            RandomSource random) {
        if (!(level.getLevel().dimension() == Level.OVERWORLD)) {
            return false;
        }

        if (!ModSpawnRestrictions.defaultMonsterCheck(level, pos)) {
            return false;
        }

        return true;
    }
}
