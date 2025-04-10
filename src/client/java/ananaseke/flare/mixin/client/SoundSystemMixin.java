package ananaseke.flare.mixin.client;

import ananaseke.flare.KeyBinds;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.sound.SoundSystem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SoundSystem.class)
public class SoundSystemMixin {
    @Inject(method = "play", at = @At("HEAD"))
    public void play(SoundInstance sound, CallbackInfo ci) {
        if (KeyBinds.isToggleActive(KeyBinds.devKeybind)) {
//            FlareClient.LOGGER.info("Sound: " + sound.getId().toString() + " at " + sound.getX() + "," + sound.getY() + "," + sound.getZ());
        }
    }
}
