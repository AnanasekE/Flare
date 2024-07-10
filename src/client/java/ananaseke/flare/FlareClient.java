package ananaseke.flare;

import ananaseke.flare.Plaques.Plaques;
import ananaseke.flare.Utils.ItemPriceUtils;
import ananaseke.flare.Utils.RenderUtils;
import ananaseke.flare.fullbright.Fullbright;
import ananaseke.flare.garden.VisitorTracker;
import ananaseke.flare.misc.AntiSpam;
import ananaseke.flare.misc.ChatHider;
import ananaseke.flare.overlays.ItemOverlays;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.block.entity.VaultBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.item.FilledMapItem;
import net.minecraft.item.Item;
import net.minecraft.client.render.*;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;

public class FlareClient implements ClientModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger("flare");
    public static final String MOD_ID = "flare";

    private static MinecraftClient client = MinecraftClient.getInstance();

    private static boolean update = true;
    public static boolean shouldRender = false;
//    public static FilledMapItem map;
//    public static boolean shouldRenderMap = false;

    @Override
    public void onInitializeClient() {
        Plaques.initialize();
        Fullbright.initialize();
        ItemOverlays.initialize();
        Info.initialize();
        AntiSpam.initialize();
        KeyBinds.initialize();
        ChatHider.initialize();
        VisitorTracker.initialize();


        KeyBinding keybind = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.flare.highlight_entities",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_HOME,
                "category.flare.main"
        ));

        WorldRenderEvents.AFTER_ENTITIES.register(context -> {
            if (keybind.wasPressed()) {
                shouldRender = !shouldRender;
                client.player.sendMessage(Text.of("Changed shouldRender to " + shouldRender), false);
            }
            if (shouldRender) {
                client.world.getEntities().forEach(entity -> {
//                    LOGGER.info(entity.getName().getString());
                    if (entity instanceof ClientPlayerEntity) return;
                    RenderUtils.drawEntityBox(context, entity);
                });
            }
        });

    }


}


