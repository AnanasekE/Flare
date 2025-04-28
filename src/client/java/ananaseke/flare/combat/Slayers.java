package ananaseke.flare.combat;

import ananaseke.flare.Config;
import ananaseke.flare.Utils.RenderUtils;
import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Slayers {

    private static final Set<String> SLAYER_NAMES = Set.of(
            "Revenant Horror",
            "Tarantula Broodfather",
            "Sven Packmaster",
            "Voidgloom Seraph",
            "Riftstalker Bloodfiend",
            "Inferno Demonlord"
    );

    private static final Set<String> MINIBOSS_NAMES = Set.of(
            "Revenant Champion",
            "Deformed Revenant",
            "Atoned Champion",
            "Atoned Revenant",
            "Sven Follower",
            "Sven Alpha",
            "Tarantula Beast",
            "Tarantula Vermin",
            "Mutant Tarantula",
            "Voidling Devotee",
            "Voidling Radical",
            "Voidcrazed Maniac"
    );

    private final Config config = AutoConfig.getConfigHolder(Config.class).getConfig();
    private final List<LivingEntity> nearbyEntities = new ArrayList<>();

    public Slayers() {
        new Voidgloom();

        HudRenderCallback.EVENT.register(this::onHudRender);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.world == null) return;

            nearbyEntities.clear();
            client.world.getEntities().forEach(entity -> {
                if (entity.distanceTo(client.player) < 15 && entity instanceof LivingEntity) {
                    nearbyEntities.add((LivingEntity) entity);
                }
            });
        });
    }

    private void onHudRender(net.minecraft.client.gui.DrawContext drawContext, RenderTickCounter tickDelta) {
        if (!config.showSlayerInfo) return;

        findEntityByName(nearbyEntities, SLAYER_NAMES).ifPresent(slayer ->
                RenderUtils.drawCenteredText(slayer.getName().getString(), config.slayerInfoVerticalOffset, drawContext)
        );

        findEntityByName(nearbyEntities, MINIBOSS_NAMES).ifPresent(miniboss ->
                RenderUtils.drawCenteredText(miniboss.getName().getString(), -75, drawContext)
        );
    }


    private java.util.Optional<LivingEntity> findEntityByName(List<LivingEntity> entities, Set<String> targetNames) {
        return entities.stream()
                .filter(entity -> targetNames.stream().anyMatch(name -> entity.getName().getString().contains(name)))
                .findFirst();
    }
}
