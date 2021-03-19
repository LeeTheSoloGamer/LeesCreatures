package com.leethesologamer.leescreatures.util;

import com.leethesologamer.leescreatures.LeesCreatures;
import com.leethesologamer.leescreatures.client.entity.render.*;
import com.leethesologamer.leescreatures.init.ModEntityTypes;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = LeesCreatures.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventBusSubscriber {

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.BOARLIN_ENTITY.get(), BoarlinEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.SOULEURON_ENTITY.get(), SouleuronEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.CRYSTAL_WYVERN_ENTITY.get(), CrystalWyvernEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.JUNGLE_SERPENT_ENTITY.get(), JungleSerpentEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.BEAST_DOG_ENTITY.get(), BeastDogRender::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.CRESTED_CRIKESTREAKER_ENTITY.get(), CrestedCrikestreakerRender::new);
    }
}