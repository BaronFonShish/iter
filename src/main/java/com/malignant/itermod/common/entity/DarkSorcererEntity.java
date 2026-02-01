package com.malignant.itermod.common.entity;

import com.malignant.itermod.common.entity.misc.EtherboltEntity;
import com.malignant.itermod.common.entity.misc.FlameboltEntity;
import com.malignant.itermod.common.entity.misc.FrostSpikeEntity;
import com.malignant.itermod.common.item.magic.defaults.SpellItem;
import com.malignant.itermod.common.misc.StrafeMovementGoal;
import com.malignant.itermod.common.registry.ModEntities;
import com.malignant.itermod.common.registry.ModItems;
import com.malignant.itermod.common.registry.ModSounds;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;

import javax.annotation.Nullable;

public class DarkSorcererEntity extends Monster {

    public DarkSorcererEntity(PlayMessages.SpawnEntity packet, Level world) {
        this(ModEntities.DARK_SORCERER.get(), world);
    }

    private static final EntityDataAccessor<Boolean> DATA_CASTING = SynchedEntityData.defineId(DarkSorcererEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> DATA_CAST_COOLDOWN = SynchedEntityData.defineId(DarkSorcererEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_CAST_TIME = SynchedEntityData.defineId(DarkSorcererEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_SPELL_TYPE = SynchedEntityData.defineId(DarkSorcererEntity.class, EntityDataSerializers.INT);

    public DarkSorcererEntity(EntityType<DarkSorcererEntity> type, Level world) {
        super(type, world);
        setMaxUpStep(0.6f);
        xpReward = 5;
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
        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.225f);
        builder = builder.add(Attributes.MAX_HEALTH, 20);
        builder = builder.add(Attributes.ARMOR, 0);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 4);
        builder = builder.add(Attributes.FOLLOW_RANGE, 32);
        return builder;
    }

    @Override
    public void tick() {
        super.tick();

        if (this.isAlive()) {
            handleCasting();

            if (this.getCastCooldown() > 0) {
                this.setCastCooldown(this.getCastCooldown() - 1);
            }
        } else {
            if (this.isCasting()) {
                this.stopCasting();
            }
        }
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new StrafeMovementGoal<>(this, 1.15f, 12));
        this.goalSelector.addGoal(2, new RestrictSunGoal(this));
        this.goalSelector.addGoal(3, new FleeSunGoal(this, 1.0f));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));


        this.targetSelector.addGoal(1, new HurtByTargetGoal(this).setAlertOthers());
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true, false));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Villager.class, true, false));
    }


    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.SKELETON_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return SoundEvents.SKELETON_HURT;
    }

    @Override
    protected SoundEvent getDeathSound(){
        return SoundEvents.SKELETON_DEATH;
    }

    private void handleCasting() {
        LivingEntity target = this.getTarget();

        if (target != null && target.isAlive() && this.getCastCooldown() <= 0) {

            boolean canSee = this.getSensing().hasLineOfSight(target);

            if (canSee) {
                if (!this.isCasting()) {
                    startCasting();
                }
            }
        }

        if (this.isCasting()){
            this.setCastTime(this.getCastTime() + 1);

            Item Spell = switch (this.getSpelltype()){
                case 1 -> ModItems.SPELL_ETHERBOLT.get();
                case 2 -> ModItems.SPELL_FLAMEBOLT.get();
                case 3 -> ModItems.SPELL_FROST_SPIKE.get();
                default -> ModItems.SPELL_ETHERBOLT.get();
            };

            if (Spell instanceof SpellItem spellItem){
                if (this.getCastTime() >= 15 + spellItem.getCastTimeBase() * 1.5f){
                    this.setCastCooldown((int) spellItem.getCooldownBase() + 5);
                    this.finishCast(this.getSpelltype());
                    this.stopCasting();
                }
            }
        }
    }

    private void finishCast(int spelltype){
        LivingEntity target = this.getTarget();
        Vec3 shootvec = this.getLookAngle();
        if (target != null){
            shootvec = new Vec3(target.getX(), target.getY() + target.getBbHeight()*0.75f, target.getZ())
                    .subtract(this.getX(), this.getEyeY(), this.getZ());
            shootvec.normalize();
        }

        switch (spelltype){
            case 1 -> {
                EtherboltEntity etherbolt = new EtherboltEntity(ModEntities.ETHERBOLT.get(), this.level(), this, 4);
                etherbolt.shootWithDamage(this, shootvec, 0.75f, 1f, 4);
                this.level().addFreshEntity(etherbolt);
            }
            case 2 ->{
                FlameboltEntity flamebolt = new FlameboltEntity(ModEntities.FLAMEBOLT.get(), this.level(), this, 6);
                flamebolt.shootWithDamage(this, shootvec, 1.15f, 1f, 6);
                this.level().addFreshEntity(flamebolt);
            }
            case 3 ->{
                FrostSpikeEntity frostSpike = new FrostSpikeEntity(ModEntities.FROST_SPIKE.get(), this.level(), this, 5);
                frostSpike.shootWithDamage(this, shootvec, 1.15f, 1f, 5);
                this.level().addFreshEntity(frostSpike);
            }
        }

        this.swing(InteractionHand.MAIN_HAND);
        this.level().playSound(null, this.getX(), this.getY(), this.getZ(),
                ModSounds.CAST_ARCANE.get(), SoundSource.HOSTILE, 1F, 1.0F);
    }

    private void startCasting() {
        this.setCasting(true);
        this.setCastTime(0);
        this.level().playSound(null, this.getX(), this.getY(), this.getZ(),
                ModSounds.START_CASTING.get(), SoundSource.HOSTILE, 0.8F, 1.0F);
        RandomSource random = this.level().random;
        int spellSelect = (random.nextIntBetweenInclusive(1, 5));

        LivingEntity target = this.getTarget();

        if (target != null) {
            if (target.isOnFire()) {
                switch (spellSelect){
                    case 1,2 -> this.setSpellType(1);
                    case 3,4,5 -> this.setSpellType(3);
                }
            }
            else if (target.isFullyFrozen()){
                switch (spellSelect){
                    case 1,2 -> this.setSpellType(1);
                    case 3,4,5 -> this.setSpellType(2);
                }
            }

            else switch (spellSelect) {
                    case 1, 2 -> this.setSpellType(2);
                    case 3 -> this.setSpellType(3);
                    case 4, 5 -> this.setSpellType(1);
            }
        }
        else {
            this.setSpellType(1);
        }
    }

    private void stopCasting() {
        this.setCasting(false);
        this.setCastTime(0);
    }

    public boolean isCasting(){
        return this.entityData.get(DATA_CASTING);
    }
    public void setCasting(boolean cast){
        this.entityData.set(DATA_CASTING, cast);
    }

    public int getCastCooldown(){
        return this.entityData.get(DATA_CAST_COOLDOWN);
    }
    public void setCastCooldown(int ticks){
        this.entityData.set(DATA_CAST_COOLDOWN, ticks);
    }

    public int getCastTime(){
        return this.entityData.get(DATA_CAST_TIME);
    }
    public void setCastTime(int ticks){
        this.entityData.set(DATA_CAST_TIME, ticks);
    }

    public int getSpelltype(){
        return this.entityData.get(DATA_SPELL_TYPE);
    }
    public void setSpellType(int spell){
        this.entityData.set(DATA_SPELL_TYPE, spell);
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntityDimensions dimensions) {
        return dimensions.height * 0.8f;
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

        ItemStack weapon = new ItemStack(ModItems.ANCIENT_STAFF.get());
        this.setItemSlot(EquipmentSlot.MAINHAND, weapon);
        this.armorDropChances[EquipmentSlot.MAINHAND.getIndex()] = 0.125F;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_CASTING, false);
        this.entityData.define(DATA_CAST_COOLDOWN, 0);
        this.entityData.define(DATA_CAST_TIME, 0);
        this.entityData.define(DATA_SPELL_TYPE, 1);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setCastCooldown(compound.getInt("CastCooldown"));
        this.setSpellType(compound.getInt("SpellType"));
        if (compound.getBoolean("Casting")) {
            this.setCasting(true);
            this.setCastTime(compound.getInt("CastTime"));
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("CastCooldown", this.getCastCooldown());
        compound.putInt("SpellType", this.getSpelltype());
        compound.putBoolean("Casting", this.isCasting());
        compound.putInt("CastTime", this.getCastTime());
    }
}
