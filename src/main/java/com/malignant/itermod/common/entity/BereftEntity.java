package com.malignant.itermod.common.entity;

import com.malignant.itermod.common.registry.ModEntities;
import com.malignant.itermod.common.registry.ModItems;
import com.malignant.itermod.common.registry.ModSounds;
import com.malignant.itermod.common.registry.ModSpawnRestrictions;
import net.minecraft.core.BlockPos;
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
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;

import javax.annotation.Nullable;

public class BereftEntity extends Monster implements RangedAttackMob {

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

        this.goalSelector.addGoal(0, new FleeSunGoal(this, 1.0D));


            this.goalSelector.addGoal(1, new RangedBowAttackGoal<BereftEntity>(this, 1.0, 20, 15.0F) {
                @Override
                public boolean canUse() {
                    return super.canUse() && BereftEntity.this.getMainHandItem().getItem() instanceof BowItem;
                }
            });

            this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.25f, true) {
                @Override
                protected double getAttackReachSqr(LivingEntity attackTarget) {
                    return super.getAttackReachSqr(attackTarget) + 1f;
                }

                @Override
                public boolean canUse() {
                    return super.canUse() && !(BereftEntity.this.getMainHandItem().getItem() instanceof BowItem);
                }
            });

        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 0.8));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this){
            @Override
            public boolean canUse() {
                if (this.mob.getLastHurtByMob() instanceof BereftEntity) {
                    return false;
                }
                return super.canUse();
            }
        });
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true, false));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Villager .class, true, false));
    }

    @Override
    public void performRangedAttack(LivingEntity target, float distanceFactor) {
        ItemStack bowStack = this.getMainHandItem();
        if (bowStack.getItem() instanceof BowItem) {
            AbstractArrow arrow = ProjectileUtil.getMobArrow(this, bowStack, distanceFactor);

            double d0 = target.getX() - this.getX();
            double d1 = target.getY(0.3333333333333333D) - arrow.getY();
            double d2 = target.getZ() - this.getZ();
            double d3 = Math.sqrt(d0 * d0 + d2 * d2);

            arrow.shoot(d0, d1 + d3 * 0.2D, d2, 1.6F, (float)(14 - this.level().getDifficulty().getId() * 4));

            this.playSound(SoundEvents.SKELETON_SHOOT, 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));

            this.level().addFreshEntity(arrow);
        }
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.BEREFT_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return ModSounds.BEREFT_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound(){
        return ModSounds.BEREFT_HURT.get();
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
        determineBereftType(random);
    }

    private void determineBereftType(RandomSource random) {
        int type = random.nextInt(14);
        switch (type) {
            case 0,1,2 -> equipKnight(random);
            case 3,4,5,6 -> equipArcher(random);
            default -> equipMilitia(random);
        };
    }

    private void equipMilitia(RandomSource random) {
        ItemStack[] weapons = {
                new ItemStack(Items.IRON_SWORD),
                new ItemStack(Items.IRON_AXE),
                new ItemStack(Items.IRON_SHOVEL),
                new ItemStack(Items.IRON_HOE),
                new ItemStack(ModItems.IRON_SPEAR.get()),
                new ItemStack(ModItems.IRON_DAGGER.get()),
                new ItemStack(ModItems.IRON_SCYTHE.get()),
                new ItemStack(Items.STONE_SWORD),
                new ItemStack(Items.STONE_AXE),
                new ItemStack(Items.STONE_SHOVEL),
                new ItemStack(Items.STONE_HOE),
                new ItemStack(ModItems.STONE_SPEAR.get()),
                new ItemStack(ModItems.STONE_DAGGER.get()),
                new ItemStack(ModItems.STONE_SCYTHE.get()),
                new ItemStack(Items.WOODEN_SWORD),
                new ItemStack(ModItems.WOODEN_SPEAR.get()),
                new ItemStack(ModItems.WOODEN_SPEAR.get()),
                new ItemStack(Items.BOW),
                new ItemStack(Items.STICK),
                new ItemStack(Items.STICK),
                new ItemStack(Items.STICK),
                new ItemStack(Items.STICK),
                new ItemStack(Items.BONE),
                new ItemStack(Items.BONE),
                new ItemStack(Items.GLASS_BOTTLE),
                new ItemStack(Items.TORCH),
                new ItemStack(Items.FISHING_ROD),
                new ItemStack(Items.SHEARS),
                new ItemStack(Items.FLINT_AND_STEEL),
                new ItemStack(Items.AIR),
                new ItemStack(Items.AIR),
                new ItemStack(Items.AIR),
                new ItemStack(Items.AIR),
                new ItemStack(Items.AIR),
                new ItemStack(Items.AIR),
                new ItemStack(Items.AIR),
        };

        ItemStack[] helmets = {
                new ItemStack(Items.CHAINMAIL_HELMET),
                new ItemStack(Items.LEATHER_HELMET),
                new ItemStack(Items.LEATHER_HELMET),
                new ItemStack(Items.LEATHER_HELMET),
                new ItemStack(Items.AIR),
                new ItemStack(Items.AIR),
                new ItemStack(Items.AIR),
                new ItemStack(Items.AIR)
        };

        ItemStack[] chestplates = {
                new ItemStack(Items.CHAINMAIL_CHESTPLATE),
                new ItemStack(Items.LEATHER_CHESTPLATE),
                new ItemStack(Items.LEATHER_CHESTPLATE),
                new ItemStack(Items.LEATHER_CHESTPLATE),
                new ItemStack(Items.AIR),
                new ItemStack(Items.AIR),
                new ItemStack(Items.AIR),
                new ItemStack(Items.AIR)
        };

        ItemStack[] leggings = {
                new ItemStack(Items.CHAINMAIL_LEGGINGS),
                new ItemStack(Items.LEATHER_LEGGINGS),
                new ItemStack(Items.LEATHER_LEGGINGS),
                new ItemStack(Items.LEATHER_LEGGINGS),
                new ItemStack(Items.AIR),
                new ItemStack(Items.AIR),
                new ItemStack(Items.AIR),
                new ItemStack(Items.AIR)
        };

        ItemStack[] boots = {
                new ItemStack(Items.CHAINMAIL_BOOTS),
                new ItemStack(Items.LEATHER_BOOTS),
                new ItemStack(Items.LEATHER_BOOTS),
                new ItemStack(Items.LEATHER_BOOTS),
                new ItemStack(Items.AIR),
                new ItemStack(Items.AIR),
                new ItemStack(Items.AIR),
                new ItemStack(Items.AIR)
        };

        setRandomEquipment(random, weapons, helmets, chestplates, leggings, boots);
    }

    private void equipKnight(RandomSource random) {
        ItemStack[] weapons = {
                new ItemStack(Items.DIAMOND_SWORD),
                new ItemStack(Items.DIAMOND_AXE),
                new ItemStack(ModItems.DIAMOND_SPEAR.get()),
                new ItemStack(ModItems.DIAMOND_FLAIL.get()),
                new ItemStack(ModItems.RECURVE_BOW.get()),
                new ItemStack(Items.BOW),
                new ItemStack(Items.BOW),
                new ItemStack(Items.IRON_SWORD),
                new ItemStack(Items.IRON_SWORD),
                new ItemStack(Items.IRON_AXE),
                new ItemStack(Items.IRON_AXE),
                new ItemStack(ModItems.IRON_SPEAR.get()),
                new ItemStack(ModItems.IRON_SPEAR.get()),
                new ItemStack(ModItems.IRON_FLAIL.get()),
                new ItemStack(ModItems.IRON_FLAIL.get()),
                new ItemStack(Items.GOLDEN_SWORD),
                new ItemStack(Items.GOLDEN_AXE),
                new ItemStack(ModItems.GOLDEN_SPEAR.get()),
                new ItemStack(ModItems.GOLDEN_FLAIL.get()),
        };

        ItemStack[] helmets = {
                new ItemStack(Items.DIAMOND_HELMET),
                new ItemStack(Items.GOLDEN_HELMET),
                new ItemStack(Items.IRON_HELMET),
                new ItemStack(Items.IRON_HELMET),
                new ItemStack(Items.IRON_HELMET),
                new ItemStack(Items.IRON_HELMET),
                new ItemStack(Items.IRON_HELMET),
                new ItemStack(Items.CHAINMAIL_HELMET),
                new ItemStack(Items.CHAINMAIL_HELMET),
                new ItemStack(Items.CHAINMAIL_HELMET)
        };

        ItemStack[] chestplates = {
                new ItemStack(Items.DIAMOND_CHESTPLATE),
                new ItemStack(Items.GOLDEN_CHESTPLATE),
                new ItemStack(Items.IRON_CHESTPLATE),
                new ItemStack(Items.IRON_CHESTPLATE),
                new ItemStack(Items.IRON_CHESTPLATE),
                new ItemStack(Items.IRON_CHESTPLATE),
                new ItemStack(Items.IRON_CHESTPLATE),
                new ItemStack(Items.CHAINMAIL_CHESTPLATE),
                new ItemStack(Items.CHAINMAIL_CHESTPLATE),
                new ItemStack(Items.CHAINMAIL_CHESTPLATE)
        };

        ItemStack[] leggings = {
                new ItemStack(Items.DIAMOND_LEGGINGS),
                new ItemStack(Items.GOLDEN_LEGGINGS),
                new ItemStack(Items.IRON_LEGGINGS),
                new ItemStack(Items.IRON_LEGGINGS),
                new ItemStack(Items.IRON_LEGGINGS),
                new ItemStack(Items.IRON_LEGGINGS),
                new ItemStack(Items.IRON_LEGGINGS),
                new ItemStack(Items.CHAINMAIL_LEGGINGS),
                new ItemStack(Items.CHAINMAIL_LEGGINGS),
                new ItemStack(Items.CHAINMAIL_LEGGINGS)
        };

        ItemStack[] boots = {
                new ItemStack(Items.DIAMOND_BOOTS),
                new ItemStack(Items.GOLDEN_BOOTS),
                new ItemStack(Items.IRON_BOOTS),
                new ItemStack(Items.IRON_BOOTS),
                new ItemStack(Items.IRON_BOOTS),
                new ItemStack(Items.IRON_BOOTS),
                new ItemStack(Items.IRON_BOOTS),
                new ItemStack(Items.CHAINMAIL_BOOTS),
                new ItemStack(Items.CHAINMAIL_BOOTS),
                new ItemStack(Items.CHAINMAIL_BOOTS)
        };

        setRandomEquipment(random, weapons, helmets, chestplates, leggings, boots);
    }

    private void equipArcher(RandomSource random) {
        ItemStack[] weapons = {
                new ItemStack(ModItems.RECURVE_BOW.get()),
                new ItemStack(Items.BOW),
                new ItemStack(Items.BOW),
        };

        ItemStack[] helmets = {
                new ItemStack(Items.IRON_HELMET),
                new ItemStack(Items.CHAINMAIL_HELMET),
                new ItemStack(Items.CHAINMAIL_HELMET),
                new ItemStack(Items.LEATHER_HELMET),
                new ItemStack(Items.LEATHER_HELMET),
                new ItemStack(Items.LEATHER_HELMET),
                new ItemStack(Items.LEATHER_HELMET)
        };

        ItemStack[] chestplates = {
                new ItemStack(Items.IRON_CHESTPLATE),
                new ItemStack(Items.CHAINMAIL_CHESTPLATE),
                new ItemStack(Items.CHAINMAIL_CHESTPLATE),
                new ItemStack(Items.LEATHER_CHESTPLATE),
                new ItemStack(Items.LEATHER_CHESTPLATE),
                new ItemStack(Items.LEATHER_CHESTPLATE),
                new ItemStack(Items.LEATHER_CHESTPLATE)
        };

        ItemStack[] leggings = {
                new ItemStack(Items.IRON_LEGGINGS),
                new ItemStack(Items.CHAINMAIL_LEGGINGS),
                new ItemStack(Items.CHAINMAIL_LEGGINGS),
                new ItemStack(Items.LEATHER_LEGGINGS),
                new ItemStack(Items.LEATHER_LEGGINGS),
                new ItemStack(Items.LEATHER_LEGGINGS),
                new ItemStack(Items.LEATHER_LEGGINGS)
        };

        ItemStack[] boots = {
                new ItemStack(Items.IRON_BOOTS),
                new ItemStack(Items.CHAINMAIL_BOOTS),
                new ItemStack(Items.CHAINMAIL_BOOTS),
                new ItemStack(Items.LEATHER_BOOTS),
                new ItemStack(Items.LEATHER_BOOTS),
                new ItemStack(Items.LEATHER_BOOTS),
                new ItemStack(Items.LEATHER_BOOTS)
        };

        setRandomEquipment(random, weapons, helmets, chestplates, leggings, boots);
    }

    private void setRandomEquipment(RandomSource random, ItemStack[] weapons, ItemStack[] helmets,
                                    ItemStack[] chestplates, ItemStack[] leggings, ItemStack[] boots) {

        ItemStack weapon = weapons[random.nextInt(weapons.length)];
        if (!weapon.isEmpty() && weapon.isDamageableItem()) {
            int maxDamage = weapon.getMaxDamage();
            int minDamage = (int) (maxDamage * 0.4f);
            int maxDamageAmount = (int) (maxDamage * 0.9f);
            int damageAmount = random.nextIntBetweenInclusive(minDamage, maxDamageAmount - 1);
            weapon.setDamageValue(damageAmount);
        }
        this.setItemSlot(EquipmentSlot.MAINHAND, weapon);
        this.armorDropChances[EquipmentSlot.MAINHAND.getIndex()] = 0.15F;

        ItemStack helmetItem = helmets[random.nextInt(helmets.length)];
        ItemStack chestplateItem = chestplates[random.nextInt(chestplates.length)];
        ItemStack leggingsItem = leggings[random.nextInt(leggings.length)];
        ItemStack bootsItem = boots[random.nextInt(boots.length)];

        if (!helmetItem.isEmpty()) this.setItemSlot(EquipmentSlot.HEAD, helmetItem);
        if (!chestplateItem.isEmpty()) this.setItemSlot(EquipmentSlot.CHEST, chestplateItem);
        if (!leggingsItem.isEmpty()) this.setItemSlot(EquipmentSlot.LEGS, leggingsItem);
        if (!bootsItem.isEmpty()) this.setItemSlot(EquipmentSlot.FEET, bootsItem);

        this.armorDropChances[EquipmentSlot.HEAD.getIndex()] = 0.08F;
        this.armorDropChances[EquipmentSlot.CHEST.getIndex()] = 0.08F;
        this.armorDropChances[EquipmentSlot.LEGS.getIndex()] = 0.08F;
        this.armorDropChances[EquipmentSlot.FEET.getIndex()] = 0.08F;
    }

    public static boolean BereftSpawnRules(
            EntityType<BereftEntity> entityType,
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
