package com.thirdlife.itermod.common.item.magic.defaults;

import com.thirdlife.itermod.common.registry.ModAttributes;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

public abstract class SpellItem extends Item{

    private final int castTime;
    private final int cooldown;
    private final int etherCost;

    public SpellItem(Properties properties, int castTime, int etherCost, int cooldown) {
        super(properties.stacksTo(1));
        this.castTime = castTime;
        this.etherCost = etherCost;
        this.cooldown = cooldown;

    }

    public float getCastTimeBase(){
        return this.castTime;
    }
    public float getCooldownBase(){
        return this.cooldown;
    }
    public float getEtherCostBase(){
        return this.etherCost;
    }

    public float getCastTime(Player player) {
        AttributeInstance CastingSpeedAttribute = player.getAttribute(ModAttributes.CASTING_SPEED.get());
        float castTimeModifier = CastingSpeedAttribute != null ? (float) CastingSpeedAttribute.getValue() : 0.01f;
        castTimeModifier = (castTimeModifier + 1) * 0.5f;
        float castTimeNew = castTime / castTimeModifier;

        return castTimeNew;
    }

    public float getCooldown(Player player) {
        AttributeInstance CastingSpeedAttribute = player.getAttribute(ModAttributes.CASTING_SPEED.get());
        float cooldownModifier = CastingSpeedAttribute != null ? (float) CastingSpeedAttribute.getValue() : 0.01f;
        cooldownModifier = (cooldownModifier + 1) * 0.5f;
        float cooldownNew = cooldown / cooldownModifier;

        return cooldownNew;
    }

    public float getManaCost(Player player) {

        AttributeInstance EtherEfficiencyAttribute = player.getAttribute(ModAttributes.ETHER_EFFICIENCY.get());
        float etherCostModifier = EtherEfficiencyAttribute != null ? (float) EtherEfficiencyAttribute.getValue() : 0f;
        float etherCostNew = etherCost * (1 - etherCostModifier);

        return etherCostNew;
    }

    public float getSpellPower(Player player){
        AttributeInstance SpellPowerAttribute = player.getAttribute(ModAttributes.SPELL_POWER.get());
        float spellpower = SpellPowerAttribute != null ? (float) SpellPowerAttribute.getValue() : 0.01f;

        return spellpower;
    }

    @Override
    public void appendHoverText(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, world, list, flag);

        ResourceLocation registryName = ForgeRegistries.ITEMS.getKey(this);
        if (registryName != null) {
            String baseKey = BuiltInRegistries.ITEM.getKey(this).getNamespace() + "." + BuiltInRegistries.ITEM.getKey(this).getPath();

            list.add(Component.translatable(baseKey + ".spellname"));
            list.add(Component.literal(""));



            LocalPlayer clientPlayer = getClientPlayer();
            if (clientPlayer != null) {

                float dynamicCastTime = getCastTime(clientPlayer)/20f;
                float dynamicCooldown = getCooldown(clientPlayer)/20f;
                float dynamicManaCost = getManaCost(clientPlayer);

                String castTimeString = String.format("%.1f", dynamicCastTime);
                String cooldownString = String.format("%.1f", dynamicCooldown);
                String manaCostString = String.format("%.1f", dynamicManaCost);

                list.add(Component.translatable("iterpg.spell.cast_time", dynamicCastTime));

                list.add(Component.translatable("iterpg.spell.mana_cost", dynamicManaCost));

                list.add(Component.translatable("iterpg.spell.cooldown", dynamicCooldown));

            } else {
                list.add(Component.translatable("iterpg.spell.cast_time", castTime));

                list.add(Component.translatable("iterpg.spell.mana_cost", etherCost));

                list.add(Component.translatable("iterpg.spell.cooldown", cooldown));
            }
            list.add(Component.literal(""));
            list.add(Component.translatable(baseKey + ".desc"));
        }
    }


    @OnlyIn(Dist.CLIENT)
    private LocalPlayer getClientPlayer() {
        return Minecraft.getInstance().player;
    }

    public abstract void castSpell(Level level, Player player, ItemStack wand, ItemStack spellStack, float spellpower);

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return false;
    }
}