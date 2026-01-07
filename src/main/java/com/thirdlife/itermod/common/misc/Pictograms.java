package com.thirdlife.itermod.common.misc;

import com.thirdlife.itermod.iterMod;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

import static com.thirdlife.itermod.iterMod.MOD_ID;

public class Pictograms {
    public static final char ID_ARCANE = '\uE000';
    public static final char ID_PRIMAL = '\uE001';
    public static final char ID_OCCULT = '\uE002';
    public static final char IM_FORCE = '\uE003';
    public static final char IM_FORM = '\uE004';
    public static final char IM_BODY = '\uE005';
    public static final char IM_CONVEYANCE = '\uE006';
    public static final char IA_FIRE = '\uE007';
    public static final char IA_ICE = '\uE008';
    public static final char IA_LIGHTNING = '\uE009';
    public static final char IA_WATER = '\uE00a';
    public static final char IA_EARTH = '\uE00b';
    public static final char IA_AIR = '\uE00c';
    public static final char IA_ETHER = '\uE00d';
    public static final char IA_LIFE = '\uE00e';
    public static final char IA_DECAY = '\uE00f';

    public static final ResourceLocation PICTOGRAM_FONT = iterMod.PICTOGRAM_FONT;


    public static MutableComponent getIcon(char icon) {
        return Component.literal(String.valueOf(icon))
                .withStyle(Style.EMPTY.withFont(PICTOGRAM_FONT));
    }
}
