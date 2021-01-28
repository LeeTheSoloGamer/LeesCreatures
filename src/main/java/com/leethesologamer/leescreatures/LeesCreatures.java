package com.leethesologamer.leescreatures;

import com.leethesologamer.leescreatures.entities.BoarlinEntity;
import com.leethesologamer.leescreatures.entities.CrystalWyvernEntity;
import com.leethesologamer.leescreatures.entities.SouleuronEntity;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.leethesologamer.leescreatures.init.ModBlocks;
import com.leethesologamer.leescreatures.init.ModEntityTypes;
import com.leethesologamer.leescreatures.init.ModItems;
import com.leethesologamer.leescreatures.objects.items.ModSpawnEggItem;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import software.bernie.geckolib3.GeckoLib;


@Mod(LeesCreatures.MOD_ID)
public class LeesCreatures {

    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "leescreatures";
    public static final ItemGroup LEES_CREATURES_GROUP = new LeesCreaturesGroup("leescreaturestab");

    public LeesCreatures() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::setup);
        GeckoLib.initialize();

        ModBlocks.BLOCKS.register(bus);
        ModItems.ITEMS.register(bus);
        ModEntityTypes.ENTITY_TYPES.register(bus);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            GlobalEntityTypeAttributes.put(ModEntityTypes.BOARLIN_ENTITY.get(), BoarlinEntity.func_233666_p_().create());
            GlobalEntityTypeAttributes.put(ModEntityTypes.SOULEURON_ENTITY.get(), SouleuronEntity.func_233666_p_().create());
            GlobalEntityTypeAttributes.put(ModEntityTypes.CRYSTAL_WYVERN_ENTITY.get(), CrystalWyvernEntity.func_233666_p_().create());
        });
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
