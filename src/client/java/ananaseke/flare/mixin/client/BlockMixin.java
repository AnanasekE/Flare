package ananaseke.flare.mixin.client;

import ananaseke.flare.Config;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Block.class)
public class BlockMixin {

    @Inject(method = "spawnBreakParticles", at = @At("HEAD"), cancellable = true)
    private void spawnBreakParticles(World world, PlayerEntity player, BlockPos pos, BlockState state, CallbackInfo ci) {
        Config config = AutoConfig.getConfigHolder(Config.class).getConfig();

        if (config.disableBlockBreakParticles) {
            ci.cancel();
        }
    }
}
