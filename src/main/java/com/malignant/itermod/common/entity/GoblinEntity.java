package com.malignant.itermod.common.entity;

import com.malignant.itermod.common.registry.ModEntities;
import com.malignant.itermod.common.registry.ModItems;
import com.malignant.itermod.common.registry.ModSounds;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;

import javax.annotation.Nullable;

public class GoblinEntity extends Monster {

    public GoblinEntity(PlayMessages.SpawnEntity packet, Level world) {
        this(ModEntities.GOBLIN.get(), world);
    }

    public GoblinEntity(EntityType<GoblinEntity> type, Level world) {
        super(type, world);
        setMaxUpStep(0.6f);
        xpReward = 2;
        setNoAi(false);
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public MobType getMobType() {
        return MobType.UNDEFINED;
    }

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Mob.createMobAttributes();
        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.225);
        builder = builder.add(Attributes.MAX_HEALTH, 10);
        builder = builder.add(Attributes.ARMOR, 2);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 2);
        builder = builder.add(Attributes.FOLLOW_RANGE, 24);
        return builder;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.2, false) {
            @Override
            protected double getAttackReachSqr(LivingEntity entity) {
                return 1;
            }
        });
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 0.8));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 8.0F));


        this.targetSelector.addGoal(1, new HurtByTargetGoal(this).setAlertOthers());
    }


    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.GOBLIN_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return ModSounds.GOBLIN_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.GOBLIN_DEATH.get();
    }


    @Override
    protected float getStandingEyeHeight(Pose pose, EntityDimensions dimensions) {
        return dimensions.height * 0.8f;
    }

    @Override
    protected void populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance difficulty) {
        super.populateDefaultEquipmentSlots(random, difficulty);

        ItemStack weapon = getRandomWeapon(random);
        this.setItemSlot(EquipmentSlot.MAINHAND, weapon);
    }

    private ItemStack getRandomWeapon(RandomSource random) {
        int weaponChoice = random.nextInt(7);
        return switch (weaponChoice) {
            case 0,1,2,3 -> new ItemStack(ModItems.GOBSTEEL_PICKAXE.get());
            case 4 -> new ItemStack(ModItems.GOBSTEEL_SHOVEL.get());
            case 5 -> new ItemStack(ModItems.GOBSTEEL_HOE.get());
            case 6 -> new ItemStack(ModItems.GOBSTEEL_AXE.get());
            default -> new ItemStack(Items.IRON_PICKAXE);
        };
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
    protected void dropCustomDeathLoot(DamageSource source, int lootingModifier, boolean recentlyHit) {
        super.dropCustomDeathLoot(source, lootingModifier, recentlyHit);
        float dropChance = 0.10f + (lootingModifier * 0.02f);
        if (this.random.nextFloat() < dropChance) {
            ItemStack heldItem = this.getMainHandItem();
            if (!heldItem.isEmpty() && heldItem.isDamageableItem()) {

                ItemStack toDropItem = heldItem.copy();

                int maxDamage = toDropItem.getMaxDamage();
                int minDamage = (int) (maxDamage * 0.4f);
                int maxDamageAmount = (int) (maxDamage * 0.95f);
                int damageAmount = this.random.nextIntBetweenInclusive(minDamage, maxDamageAmount);

                toDropItem.setDamageValue(damageAmount);

                this.spawnAtLocation(toDropItem);
            }
        }
    }

}
