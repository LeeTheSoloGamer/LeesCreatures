package com.leethesologamer.leescreatures;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.leethesologamer.leescreatures.entities.CrystalWyvernEntity;
import com.leethesologamer.leescreatures.init.ModBlocks;
import com.leethesologamer.leescreatures.init.ModEntityTypes;
import com.leethesologamer.leescreatures.init.ModItems;
import com.leethesologamer.leescreatures.init.ModSoundEventTypes;
import com.leethesologamer.leescreatures.objects.items.ModSpawnEggItem;
import com.leethesologamer.leescreatures.world.ModEntitySpawing;
import com.leethesologamer.leescreatures.world.OreGeneration;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import software.bernie.example.GeckoLibMod;
import software.bernie.geckolib3.GeckoLib;


@Mod(LeesCreatures.MOD_ID)
public class  LeesCreatures {

    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "leescreatures";
    public static final ItemGroup LEES_CREATURES_GROUP = new LeesCreaturesGroup("leescreaturestab");

    public LeesCreatures() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::setup);
        GeckoLibMod.DISABLE_IN_DEV = true;
        GeckoLib.initialize();

        ModBlocks.BLOCKS.register(bus);
        ModItems.ITEMS.register(bus);
        ModEntityTypes.ENTITY_TYPES.register(bus);
        ModSoundEventTypes.SOUND_EVENTS.register(bus);
        MinecraftForge.EVENT_BUS.addListener(EventPriority.HIGH, OreGeneration::generateOres);
        MinecraftForge.EVENT_BUS.addListener(this::onBiomeLoad);

        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onBiomeLoad(BiomeLoadingEvent event) {
        ModEntitySpawing.onBiomesLoad(event);
    }

    private void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
        	
        	 GlobalEntityTypeAttributes.put(ModEntityTypes.CRYSTAL_WYVERN_ENTITY.get(), CrystalWyvernEntity.registerAttributes().create());
        	/*
            GlobalEntityTypeAttributes.put(ModEntityTypes.BOARLIN_ENTITY.get(), BoarlinEntity.registerAttributes().create());
            GlobalEntityTypeAttributes.put(ModEntityTypes.SOULEURON_ENTITY.get(), SouleuronEntity.registerAttributes().create());
            GlobalEntityTypeAttributes.put(ModEntityTypes.CRYSTAL_WYVERN_ENTITY.get(), CrystalWyvernEntity.registerAttributes().create());
            GlobalEntityTypeAttributes.put(ModEntityTypes.JUNGLE_SERPENT_ENTITY.get(), JungleSerpentEntity.registerAttributes().create());
            GlobalEntityTypeAttributes.put(ModEntityTypes.BEAST_DOG_ENTITY.get(), BeastDogEntity.registerAttributes().create());
            GlobalEntityTypeAttributes.put(ModEntityTypes.CRESTED_CRIKESTREAKER_ENTITY.get(), CrestedCrikestreakerEntity.registerAttributes().create());
            GlobalEntityTypeAttributes.put(ModEntityTypes.ADDER_BACK_ENTITY.get(), AdderBackEntity.registerAttributes().create());
            GlobalEntityTypeAttributes.put(ModEntityTypes.FLORRET_ENTITY.get(), FlorretEntity.registerAttributes().create());
            GlobalEntityTypeAttributes.put(ModEntityTypes.COSMIC_WHALE_ENTITY.get(), CosmicWhaleEntity.registerAttributes().create());
        	 */
         });
        ModEntitySpawing.entitySpawnPlacementRegistry();
    }
    private void doClientStuff(final FMLClientSetupEvent event) {

    }
        @SubscribeEvent
        public static void onRegisterEntities(final RegistryEvent.Register<EntityType<?>> event) {
        ModSpawnEggItem.initSpawnEggs();
    }
    public static class LeesCreaturesGroup extends ItemGroup {
        public LeesCreaturesGroup(String label) {
            super(label);
        }

        @Override
        public ItemStack createIcon() {
            return ModItems.DURANTIUM_ORE_ITEM.get().getDefaultInstance();
        }
    }
}
