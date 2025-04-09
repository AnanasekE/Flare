package ananaseke.flare;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.networking.v1.EntityTrackingEvents;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.component.*;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;


public class Info {

    public static Logger LOGGER = FlareClient.LOGGER;
    public static KeyBinding keyBinding;
    public static KeyBinding keyBinding2;

    public static void initialize() {
        EntityTrackingEvents.START_TRACKING.register((trackedEntity, player) -> {
//            LOGGER.info(String.valueOf(trackedEntity.getCustomName()));
        });

        // register keybind
        keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.flare.print_entities",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_PAGE_UP,
                "category.flare.main"
        ));
        // register keybind
        keyBinding2 = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.flare.held_item_info",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_PAGE_DOWN,
                "category.flare.main"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            KeyBinds.processKey(keyBinding, () -> {
                if (client.player == null) return;
                Vec3d playerPos = client.player.getPos();

                Box boundingBox = new Box(
                        playerPos.add(-5, -5, -5),
                        playerPos.add(5, 5, 5)
                );

                assert client.world != null;
                client.world.getEntitiesByClass(LivingEntity.class, boundingBox, entity -> true)
                        .forEach(entity -> {
                            LOGGER.info(entity.getName().getString());
                            LOGGER.info(String.valueOf(entity instanceof ArmorStandEntity));
                            LOGGER.info(entity.getClass().getSimpleName());
                        });
            });
        });

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            KeyBinds.processKey(keyBinding2, () -> {
                if (client.player == null) return;
                client.player.getMainHandStack().getComponents().forEach(component -> LOGGER.info(component.toString()));
                if (client.player.getMainHandStack().getComponents().get(DataComponentTypes.CUSTOM_DATA) != null) {
                    @Nullable NbtComponent customData = client.player.getMainHandStack().getComponents().get(DataComponentTypes.CUSTOM_DATA);
                    if (customData == null) return;
                    customData.apply((nbtComponent) -> LOGGER.info(nbtComponent.getString("id")));
                }
            });
        });
    }
}
