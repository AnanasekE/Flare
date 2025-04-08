package ananaseke.flare.combat;

import ananaseke.flare.Config;
import ananaseke.flare.Utils.RenderUtils;
import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Slayers {
    public Slayers() { //TODO: rework this to look better, like ChatHiders
        Config config = AutoConfig.getConfigHolder(Config.class).getConfig();
        MinecraftClient client = MinecraftClient.getInstance();

        new Voidgloom();

        HudRenderCallback.EVENT.register((drawContext, tickCounter) -> {
            if (!config.showSlayerInfo) return;

            PlayerEntity player = client.player;
            if (player == null) return;
            Box targetArea = new Box(player.getPos().add(-10, -10, -10), player.getPos().add(10, 10, 10));

            List<LivingEntity> entities = new ArrayList<>();

            if (client.world == null) return;
            client.world.getEntities().forEach(entity -> {
                if (entity instanceof LivingEntity) {
                    if (targetArea.contains(entity.getPos())) {
                        entities.add((LivingEntity) entity);
                    }
                }
            });

            Optional<LivingEntity> OpSlayer = entities.stream().filter(livingEntity ->
                    livingEntity.getName().getString().contains("Revenant Horror") ||
                            livingEntity.getName().getString().contains("Tarantula Broodfather") ||
                            livingEntity.getName().getString().contains("Sven Packmaster") ||
                            livingEntity.getName().getString().contains("Voidgloom Seraph") ||
                            livingEntity.getName().getString().contains("Riftstalker Bloodfiend") ||
                            livingEntity.getName().getString().contains("Inferno Demonlord")
            ).findFirst();


            Optional<LivingEntity> OpMiniboss = entities.stream().filter(livingEntity ->
                    livingEntity.getName().getString().contains("Revenant Champion") ||
                            livingEntity.getName().getString().contains("Deformed Revenant") ||
                            livingEntity.getName().getString().contains("Atoned Champion") ||
                            livingEntity.getName().getString().contains("Atoned Revenant") ||
                            livingEntity.getName().getString().contains("Sven Follower") ||
                            livingEntity.getName().getString().contains("Sven Alpha") ||
                            livingEntity.getName().getString().contains("Tarantula Beast") ||
                            livingEntity.getName().getString().contains("Tarantula Vermin") ||
                            livingEntity.getName().getString().contains("Mutant Tarantula") ||
                            livingEntity.getName().getString().contains("Voidling Devotee") ||
                            livingEntity.getName().getString().contains("Voidling Radical") ||
                            livingEntity.getName().getString().contains("Voidcrazed Maniac")

            ).findFirst();

            String slayerName;

            if (OpSlayer.isPresent()) {
                slayerName = OpSlayer.get().getName().getString();
                RenderUtils.drawCenteredText(slayerName, config.slayerInfoVerticalOffset, drawContext);
            }

            if (OpMiniboss.isPresent()) {
                RenderUtils.drawCenteredText("Miniboss", -75, drawContext);
            }

        });
    }
}
