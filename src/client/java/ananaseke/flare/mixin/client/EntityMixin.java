package ananaseke.flare.mixin.client;

import ananaseke.flare.KeyBinds;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(Entity.class)
public class EntityMixin {

//    @Inject(method = "isGlowing", at = @At("RETURN"), cancellable = true)
//    public boolean isGlowing(CallbackInfoReturnable<Boolean> cir) {
//        if (KeyBinds.highlightEntitesGlowToggle) {
////             && ((Entity)((Object)this)).getType() != EntityType.ARMOR_STAND
//            cir.setReturnValue(true);
//        }
//        return cir.getReturnValue();
//    }
}
