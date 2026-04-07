package com.malignant.itermod.common.entity;

import com.malignant.itermod.common.registry.ModEntities;
import com.malignant.itermod.common.registry.ModItems;
import com.malignant.itermod.common.registry.ModTags;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FleeSunGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;

import javax.annotation.Nullable;

public class BereftEntity extends Monster {

    public BereftEntity(PlayMessages.SpawnEntity packet, Level world) {
        this(ModEntities.BEREFT.get(), world);
    }

    public BereftEntity(EntityType<BereftEntity> type, Level world) {
        super(type, world);
        setMaxUpStep(0.6f);
        xpReward = 3;
        setNoAi(false);
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public MobType getMobType() {
        return MobType.UNDEAD;
    }

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Mob.createMobAttributes();
        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.2f);
        builder = builder.add(Attributes.MAX_HEALTH, 30);
        builder = builder.add(Attributes.ARMOR, 0);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 4);
        builder = builder.add(Attributes.FOLLOW_RANGE, 32);
        return builder;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new MeleeAttackGoal(this, 1.25f, true) {
            @Override
            protected double getAttackReachSqr(LivingEntity attackTarget) {
                return super.getAttackReachSqr(attackTarget) + 1f;
            }
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


    @Override
    protected float getStandingEyeHeight(Pose pose, EntityDimensions dimensions) {
        return dimensions.height * 0.9f;
    }


    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty,
                                        MobSpawnType reason, @Nullable SpawnGroupData spawnData,
                                        @Nullable CompoundTag dataTag) {
        spawnData = super.finalizeSpawn(world, difficulty, reason, spawnData, dataTag);

        this.populateDefaultEquipmentSlots(this.random, difficulty);

        return spawnData;
    }

    @Override
    protected void populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance difficulty) {
        super.populateDefaultEquipmentSlots(random, difficulty);

        ItemStack weapon = getRandomWeapon(random);
        if (weapon.isDamageableItem()) {


            int maxDamage = weapon.getMaxDamage();
            int minDamage = (int) (maxDamage * 0.4f);
            int maxDamageAmount = (int) (maxDamage * 0.9f);
            int damageAmount = this.random.nextIntBetweenInclusive(minDamage, maxDamageAmount-1);

            weapon.setDamageValue(damageAmount);
        }

        this.setItemSlot(EquipmentSlot.MAINHAND, weapon);
        this.armorDropChances[EquipmentSlot.MAINHAND.getIndex()] = 0.125F;
    }

    private ItemStack getRandomWeapon(RandomSource random) {
        int weaponChoice = random.nextInt(10);
        return switch (weaponChoice) {
            case 0,1,2 -> new ItemStack(Items.IRON_SWORD);
            case 3 -> new ItemStack(Items.IRON_AXE);
            case 4 -> new ItemStack(ModItems.IRON_SPEAR.get());
            case 5 -> new ItemStack(ModItems.IRON_DAGGER.get());
            default -> new ItemStack(Items.AIR);
        };
    }

}
