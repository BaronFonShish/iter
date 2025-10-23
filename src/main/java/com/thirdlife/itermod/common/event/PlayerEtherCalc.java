package com.thirdlife.itermod.common.event;


import com.thirdlife.itermod.common.registry.ModAttributes;
import com.thirdlife.itermod.common.registry.ModCapabilities;
import com.thirdlife.itermod.common.variables.EtherBurnoutPacket;
import com.thirdlife.itermod.common.variables.MageData;
import com.thirdlife.itermod.common.variables.MageDataProvider;
import com.thirdlife.itermod.common.variables.MageUtils;
import com.thirdlife.itermod.iterMod;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;

@Mod.EventBusSubscriber(modid = "iter", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PlayerEtherCalc {

    @SubscribeEvent
    public static void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        MageDataProvider.attach(event);
    }

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (!event.getEntity().level().isClientSide()) {
            MageUtils.syncBurnout((ServerPlayer) event.getEntity());
        }
    }

    @SubscribeEvent
    public static void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        if (!event.getEntity().level().isClientSide()) {
            MageUtils.syncBurnout((ServerPlayer) event.getEntity());
        }
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        if (!event.getEntity().level().isClientSide()) {
            MageUtils.syncBurnout((ServerPlayer) event.getEntity());
        }
    }


    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END && !event.player.level().isClientSide()) {
            event.player.getCapability(ModCapabilities.MAGE_DATA).ifPresent(mageData -> {

                AttributeInstance dissipationBase = event.player.getAttribute(ModAttributes.ETHER_BURNOUT_DISSIPATION.get());
                float dissipation = dissipationBase != null ? (float) dissipationBase.getValue() : 0.01f;

                AttributeInstance thresholdBase = event.player.getAttribute(ModAttributes.ETHER_BURNOUT_THRESHOLD.get());
                float threshold = thresholdBase != null ? (float) thresholdBase.getValue() : 0.01f;

                dissipation = (dissipation + (threshold * 0.0005f));
                dissipation = dissipation * 0.05f;
                float Burnout = mageData.getEtherBurnout();

                if (Burnout > 0) {
                    if (dissipation >= Burnout) {
                        mageData.setEtherBurnout(0);
                        MageUtils.syncBurnout((ServerPlayer) event.player);}
                    else {
                    mageData.subtractEtherBurnout(dissipation);
                        MageUtils.syncBurnout((ServerPlayer) event.player);
                    }
                }
                if (Burnout < 0) {mageData.setEtherBurnout(0); MageUtils.syncBurnout((ServerPlayer) event.player);}
            });
        }
    }
}
