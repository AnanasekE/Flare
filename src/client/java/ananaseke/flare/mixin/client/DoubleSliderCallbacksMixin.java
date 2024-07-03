package ananaseke.flare.mixin.client;


import net.minecraft.client.option.SimpleOption;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;


@Mixin(SimpleOption.DoubleSliderCallbacks.class)
public class DoubleSliderCallbacksMixin {
    // removes validation because of fullbright error
    @Inject(method = "validate(Ljava/lang/Double;)Ljava/util/Optional;", at = @At("RETURN"), cancellable = true)
    public void removeValidation(Double double_, CallbackInfoReturnable<Optional<Double>> cir) {
        cir.setReturnValue(double_ == null ? Optional.empty() : Optional.of(double_));
    }

}