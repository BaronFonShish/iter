package com.malignant.itermod.common.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.malignant.itermod.common.registry.ModBlocks;
import com.malignant.itermod.common.registry.ModEnchantments;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ScytheItem extends TieredItem {

    public ScytheItem(Tiers tier, Properties properties) {
        super(tier, properties.durability((int)(tier.getUses())));
    }


    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        if ((enchantment == Enchantments.SWEEPING_EDGE)
        || (enchantment == Enchantments.BLOCK_FORTUNE)){
            return true;
        }
        return enchantment.category == EnchantmentCategory.WEAPON || super.canApplyAtEnchantingTable(stack, enchantment);
    }

    public static final String ReachUUID = "c38ac4f4-d813-4ba7-a986-07c164f89266";

    @Override
    public @NotNull Multimap<Attribute, AttributeModifier> getAttributeModifiers (@NotNull final EquipmentSlot slot, final ItemStack itemStack)
    {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = new ImmutableMultimap.Builder<>();
        if (slot == EquipmentSlot.MAINHAND)
        {
            builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(Item.BASE_ATTACK_DAMAGE_UUID, "Weapon modifier",
                    ((this.getTier().getAttackDamageBonus() + 3) * 0.75f),
                    AttributeModifier.Operation.ADDITION));
            builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(Item.BASE_ATTACK_SPEED_UUID, "Weapon modifier", -2.7d, AttributeModifier.Operation.ADDITION));
            builder.put(ForgeMod.ENTITY_REACH.get(), new AttributeModifier(UUID.fromString(ReachUUID), "Weapon modifier", 0.5d, AttributeModifier.Operation.ADDITION));

            Map<Enchantment, Integer> itemEnchants = itemStack.getAllEnchantments();
        }

        return builder.build();
    }

    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        pStack.hurtAndBreak(1, pAttacker, (p_43296_) -> {
            p_43296_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
        });

        float baseDamage = (float) pAttacker.getAttributeValue(Attributes.ATTACK_DAMAGE);

        float areaDamage = (float) (baseDamage * 0.35);
        areaDamage *= (1 + ((pStack.getEnchantmentLevel(Enchantments.SWEEPING_EDGE) * 0.15f)));

        float radius = (3 + (areaDamage * 0.5f) + (pStack.getEnchantmentLevel(Enchantments.SWEEPING_EDGE) * 0.5f))/2;
        ScytheAreaDamage(pAttacker,pTarget, pTarget.level(), pTarget.getX(), pTarget.getY(), pTarget.getZ(), areaDamage, radius);

        return true;
    }

    private void ScytheAreaDamage(LivingEntity attacker, LivingEntity target, Level level, double centerX, double centerY, double centerZ, float areaDamage, float radius) {
        final Vec3 center = new Vec3(centerX, centerY, centerZ);

        List<LivingEntity> nearbyEntities = level.getEntitiesOfClass
                        (LivingEntity.class, new AABB(center, center)
                                .inflate((radius)));

        for (LivingEntity entity : nearbyEntities) {
            if (entity == attacker) {
                continue;
            }

            if (attacker instanceof Player player) {
                entity.hurt(level.damageSources().playerAttack(player), areaDamage);
            } else {
                entity.hurt(level.damageSources().mobAttack(attacker), areaDamage);
            }

                ServerLevel serverLevel = (ServerLevel) level;
                serverLevel.sendParticles(ParticleTypes.SWEEP_ATTACK, entity.getX(), (entity.getY()+(entity.getBbHeight()/2f)), entity.getZ(), 1, 0, 0, 0, 0);
        }
    }

    @Override
    public boolean canAttackBlock (@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, Player pPlayer)
    {
        return !pPlayer.isCreative();
    }

    @Override
    public InteractionResult useOn(UseOnContext context){

        if (context.getLevel().isClientSide()) {
            return InteractionResult.SUCCESS;
        }

        boolean harvested = false;
        int harvestedAmount = 0;
        boolean hasSowing = (context.getItemInHand().getEnchantmentLevel(ModEnchantments.SOWING.get()) > 0);
        int seedRemove = 1;
        int dx;
        int dz;
        for (dx = -1; dx <= 1; dx++){
            for (dz = -1; dz <= 1; dz++){
                BlockPos targetpos = context.getClickedPos().offset(dx, 0, dz);
                BlockState blockstate = context.getLevel().getBlockState(targetpos);
                Block block = blockstate.getBlock();

                if ((block instanceof CropBlock crop) && !(block == ModBlocks.ETHERBLOOM_PLANT.get())){
                    if (crop.isMaxAge(blockstate)){
                        harvested = true;
                        harvestedAmount++;

                        if (hasSowing){
                            List<ItemStack> drops = Block.getDrops(blockstate, (ServerLevel) context.getLevel(), targetpos, null, context.getPlayer(), context.getPlayer().getMainHandItem());
                            for (ItemStack drop : drops) {
                                Block.popResource(context.getLevel(), targetpos, drop);
                            }
                            context.getLevel().levelEvent(2001, targetpos, Block.getId(blockstate));
                            context.getLevel().setBlock(targetpos, crop.getStateForAge(0), 3);
                        } else {
                            context.getLevel().destroyBlock(targetpos, true, context.getPlayer());
                        }
                    }
                } else if (block == ModBlocks.ETHERBLOOM.get()){
                    harvested = true;
                    harvestedAmount++;
                    context.getLevel().destroyBlock(targetpos, true, context.getPlayer());
                }
            }
        }
        if (harvested){
            if (context.getPlayer() instanceof ServerPlayer serverPlayer) {
                if ((serverPlayer.gameMode.getGameModeForPlayer() == GameType.SURVIVAL)
                        ||(serverPlayer.gameMode.getGameModeForPlayer() == GameType.ADVENTURE)) {

                    RandomSource random = context.getLevel().getRandom();
                    int breakchance = Mth.nextInt(random, 0, 9);
                    if (harvestedAmount >= breakchance){
                        context.getItemInHand().hurtAndBreak(1, serverPlayer,
                                (player) -> player.broadcastBreakEvent(context.getHand()));
                    }
                };
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.FAIL;
    }
}