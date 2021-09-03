 package com.leethesologamer.leescreatures.init;

import com.leethesologamer.leescreatures.LeesCreatures;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModSoundEventTypes
{
    public static DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, LeesCreatures.MOD_ID);

    public static final RegistryObject<SoundEvent> CRESTED_CRIKESTREAKER_AMBIENT = SOUND_EVENTS.register("crested_crikestreaker_ambient", () ->
            new SoundEvent(new ResourceLocation(LeesCreatures.MOD_ID, "crested_crikestreaker_ambient"))
    );
}
