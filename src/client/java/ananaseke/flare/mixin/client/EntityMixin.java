package ananaseke.flare.mixin.client;

import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;


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
