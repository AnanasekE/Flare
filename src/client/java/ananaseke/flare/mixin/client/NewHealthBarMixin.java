package ananaseke.flare.mixin.client;

import ananaseke.flare.KeyBinds;
import ananaseke.flare.overlays.HealthBar;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(InGameHud.class)
public class NewHealthBarMixin {

    private static final HealthBar healthBar = new HealthBar();

    private int lastTicks;

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;renderHealthBar(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/entity/player/PlayerEntity;IIIIFIIIZ)V"), method = "renderStatusBars")
    public void replaceVanillaHealthBar(InGameHud instance, DrawContext context, PlayerEntity player, int x, int y, int lines, int regeneratingHeartIndex, float maxHealth, int lastHealth, int health, int absorption, boolean blinking) {
        healthBar.render(context, player, x, y, instance.getTicks() - lastTicks);
        lastTicks = instance.getTicks();
    }

    @Redirect(at = @At(value = "INVOKE", target = "Ljava/lang/Math;max(II)I"), method = "renderStatusBars")
    public int rowHeight(int a, int b) {
        // The height of a health bar is 10 at Math.max(10 - (q - 2), 3)
        // at Math.max(j, i) we want the renderer to think there is only one line of hearts, so we return 10
        return 10;
//        return KeyBinds.renderCustomHealthBarToggle ? 10 : Math.max(a, b);
    }

    @Redirect(at = @At(value = "INVOKE", target = "Ljava/lang/Math;max(FF)F"), method = "renderStatusBars")
    public float fakeHealth(float a, float b) {
        // The renderer should there is only one health row so the armor is displayed at the right place
        return 10;
//        return KeyBinds.renderCustomHealthBarToggle ? 10 : Math.max(a, b);
    }

    @Redirect(method = "renderStatusBars", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;getAbsorptionAmount()F"))
    public float fakeAbsorption(PlayerEntity player) {
        // The renderer should think there is only one absorption row if the player has absorption
        // so the armor is displayed at the right place
        return 0;
//        return KeyBinds.renderCustomHealthBarToggle ? 0 : ((PlayerEntity)MinecraftClient.getInstance().getCameraEntity()).getAbsorptionAmount();
    }
}