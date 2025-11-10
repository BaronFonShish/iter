package com.thirdlife.itermod.common.event;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class RingEffectManager {

    public static void TieredRingStuff(String uuidname, String attributeName, Attribute attribute, Item irontier, Item goldentier, Item netheritetier,
                                       float t1, float t2, float t3, Entity entity, int OperationType){
        final UUID AttributeUUID = UUID.nameUUIDFromBytes((uuidname.getBytes()));
        float tiermod = 0f;

        if (entity instanceof LivingEntity lv){
            if (CuriosApi.getCuriosHelper().findEquippedCurio(netheritetier, lv).isPresent()){
                tiermod = t3;
            }
            else if (CuriosApi.getCuriosHelper().findEquippedCurio(goldentier, lv).isPresent()){
                tiermod = t2;
            }
            else if (CuriosApi.getCuriosHelper().findEquippedCurio(irontier, lv).isPresent()){
                tiermod = t1;
            }

            lv.getAttribute(attribute).removeModifier(AttributeUUID);

            if (tiermod > 0){
                switch (OperationType){
                    case 1 -> Objects.requireNonNull(lv.getAttribute(attribute)).addTransientModifier(new AttributeModifier(AttributeUUID, attributeName, tiermod, AttributeModifier.Operation.ADDITION));
                    case 2 -> Objects.requireNonNull(lv.getAttribute(attribute)).addTransientModifier(new AttributeModifier(AttributeUUID, attributeName, tiermod, AttributeModifier.Operation.MULTIPLY_TOTAL));
                }
            }
        }
    }
}