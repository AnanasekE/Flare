package ananaseke.flare.mixin.client;

import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.Entity;
import net.minecraft.network.packet.s2c.play.EntityTrackerUpdateS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin {
    @Inject(method = "onEntityTrackerUpdate", at = @At("HEAD"), cancellable = true)
    private void onEntityTrackerUpdate(EntityTrackerUpdateS2CPacket packet, CallbackInfo ci) {
        if (((ClientPlayNetworkHandler) (Object) this).getWorld() == null) return;
        Entity entity = ((ClientPlayNetworkHandler) (Object) this).getWorld().getEntityById(packet.id());
        if (entity == null) return;
        if (entity.getName().getString().contains("Decoy")) ci.cancel();
    }
}
