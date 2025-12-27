package com.thirdlife.itermod.common.item.magic.defaults;

import com.thirdlife.itermod.common.event.SpellBookUtils;
import com.thirdlife.itermod.common.registry.ModAttributes;
import com.thirdlife.itermod.common.registry.ModItems;
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
import java.util.Objects;

public abstract class SpellItem extends Item{

    private final int castTime;
    private final int cooldown;
    private final int etherCost;
    private final String domain;
    private final String method;
    private final int tier;

    public SpellItem(Properties properties, String domain, String method, int tier, int castTime, int etherCost, int cooldown) {
        super(properties.stacksTo(1));
        this.castTime = castTime;
        this.etherCost = etherCost;
        this.cooldown = cooldown;
        this.domain = domain;
        this.method = method;
        this.tier = tier;
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
    public String getDomain(){
        return this.domain;
    }
    public String getMethod(){
        return this.method;
    }
    public int getTier(){
        return this.tier;
    }


    public int getQuality(ItemStack stack){
        if (stack.hasTag()) {
            assert stack.getTag() != null;
            if (stack.getTag().contains("IterSpellQuality")) {
                return stack.getTag().getInt("IterSpellQuality");
            }
        }
        return 0;
    }

    public ItemStack setQuality(ItemStack stack, int newQuality){
        stack.getOrCreateTag().putInt("IterSpellQuality", newQuality);
        return stack;
    }


    public String getSpellDisplayName(){
        Component fullname = Component.translatable(this.getDescriptionId());
        Component prefix = Component.translatable("iter.spell.prefix");
        String prefix_trim = prefix.getString();
        String nameOnly = fullname.getString();
        if (nameOnly.startsWith(prefix_trim)){
        nameOnly = nameOnly.substring(prefix_trim.length()).trim();
        return nameOnly;
        }
        return nameOnly;
    }

    public float getCastTime(Player player, ItemStack spellStack) {
        AttributeInstance CastingSpeedAttribute = player.getAttribute(ModAttributes.CASTING_SPEED.get());
        float castTimeModifier = CastingSpeedAttribute != null ? (float) CastingSpeedAttribute.getValue() : 1f;
        float quality = getQuality(spellStack);
        float castTimeBase = castTime * (1 - quality * 0.025f);
        float castTimeNew = castTimeBase / (((castTimeModifier-1)/2f)+1);

        if (castTimeNew <= 0){castTimeNew = 1;}

        return castTimeNew;
    }

    public float getCooldown(Player player, ItemStack spellStack) {
        AttributeInstance CastingSpeedAttribute = player.getAttribute(ModAttributes.CASTING_SPEED.get());
        float cooldownModifier = CastingSpeedAttribute != null ? (float) CastingSpeedAttribute.getValue() : 1f;
        float quality = getQuality(spellStack);
        float cooldownBase = cooldown * (1 - quality * 0.025f);
        float cooldownNew = cooldownBase / cooldownModifier;

        if (cooldownNew <= 0){cooldownNew = 1;}

        return cooldownNew;
    }

    public float getManaCost(Player player, ItemStack spellStack) {

        AttributeInstance EtherEfficiencyAttribute = player.getAttribute(ModAttributes.ETHER_EFFICIENCY.get());
        float etherCostModifier = EtherEfficiencyAttribute != null ? (float) EtherEfficiencyAttribute.getValue() : 0f;
        float quality = getQuality(spellStack);
        float costBase = etherCost * (1 - quality * 0.02f);
        float etherCostNew = costBase * (2 - etherCostModifier);

        if (etherCostNew < 0) {etherCostNew=0;}

        return etherCostNew;
    }

    public float getSpellPower(Player player, ItemStack spellStack){
        AttributeInstance SpellPowerAttribute = player.getAttribute(ModAttributes.SPELL_POWER.get());
        float spellpower = SpellPowerAttribute != null ? (float) SpellPowerAttribute.getValue() : 1f;
        float quality = getQuality(spellStack);
        float spellpowerBase = spellpower * (1 + quality * 0.1f);
        spellpower = spellpowerBase * 0.2f;

        if (spellpower <= 0.05) {spellpower = 0.05f;}
        return spellpower;
    }


    @Override
    public void appendHoverText(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, world, list, flag);

        ResourceLocation registryName = ForgeRegistries.ITEMS.getKey(this);
        if (registryName != null) {
            String baseKey = BuiltInRegistries.ITEM.getKey(this).getNamespace() + "." + BuiltInRegistries.ITEM.getKey(this).getPath();
            String domainKey = "iterpg.spell.domain." + this.getDomain();
            String methodKey = "iterpg.spell.method." + this.getMethod();
            Component SpellInfo = Component.translatable("iterpg.spell.domain_and_method",
                    (Component.translatable(domainKey)),
                    (Component.translatable(methodKey)));

            int quality = getQuality(itemstack);
            Component qualityText = Component.translatable("iterpg.spell.quality")
                    .append(Component.literal(": " + quality));

            list.add(Component.translatable("iterpg.spell.tier", Component.translatable("iterpg.spell.tier." + this.getTier())));
            list.add(SpellInfo);
            list.add(qualityText);
            list.add(Component.literal(""));

            LocalPlayer clientPlayer = getClientPlayer();
            if (clientPlayer != null) {

                float dynamicSpellPower = getSpellPower(clientPlayer, itemstack);
                float dynamicCastTime = getCastTime(clientPlayer, itemstack)/20f;
                float dynamicCooldown = getCooldown(clientPlayer, itemstack)/20f;
                float dynamicManaCost = getManaCost(clientPlayer, itemstack);

                String spellPowerString = String.format("%.2f", dynamicSpellPower);
                String castTimeString = String.format("%.1f", dynamicCastTime);
                String cooldownString = String.format("%.1f", dynamicCooldown);
                String manaCostString = String.format("%.1f", dynamicManaCost);


                list.add(Component.translatable("iterpg.spell.spellpower", spellPowerString));
                if (dynamicCastTime > 0.05f) {
                list.add(Component.translatable("iterpg.spell.cast_time", castTimeString));}

                list.add(Component.translatable("iterpg.spell.mana_cost", manaCostString));

                list.add(Component.translatable("iterpg.spell.cooldown", cooldownString));

            } else {
                list.add(Component.translatable("iterpg.spell.spellpower", 1));
                if (this.castTime > 0.05){
                list.add(Component.translatable("iterpg.spell.cast_time", castTime));}

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