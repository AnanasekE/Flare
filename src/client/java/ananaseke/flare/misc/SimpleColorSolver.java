package ananaseke.flare.misc;

import ananaseke.flare.Utils.RenderUtils;
import ananaseke.flare.callbacks.DrawSlotCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.collection.DefaultedList;
import org.intellij.lang.annotations.Language;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class SimpleColorSolver {
    protected Pattern pattern;
    @Nullable
    protected Matcher matcher;
    protected List<Slot> highlightSlots = new ArrayList<>();
    private final MinecraftClient client = MinecraftClient.getInstance();
    private int translationOffset = 0;
    private boolean shouldDrawTextures = false;

    public SimpleColorSolver(@NotNull @Language("RegExp") String regex) {
        this.pattern = Pattern.compile(regex);

        DrawSlotCallback.EVENT.register((drawContext, slot) -> {
            if (highlightSlots.isEmpty()) return;
            if (!(client.currentScreen instanceof GenericContainerScreen)) return;
            if (highlightSlots.stream().anyMatch(slot1 -> slot1.id == slot.id)) {
                if (shouldDrawTextures) {
                    RenderUtils.renderItemTexture(drawContext, slot, slot.getStack(), translationOffset);
                } else {
                    RenderUtils.highlightSlot(drawContext, slot, translationOffset);
                }
            }
        });

        ClientTickEvents.END_CLIENT_TICK.register(client1 -> {
            if (client1.currentScreen != null) {
                if (test(client1.currentScreen)) {
                    highlightSlots = getHighlightedSlots(((GenericContainerScreen) (client1.currentScreen)).getScreenHandler().slots);
                } else {
                    if (!highlightSlots.isEmpty()) {
                        highlightSlots.clear();
                    }
                }
            } else if (!highlightSlots.isEmpty()) {
                highlightSlots.clear();
            }
        });
    }

    public SimpleColorSolver(@NotNull @Language("RegExp") String regex, int translationOffset) {
        this(regex);
        this.translationOffset = translationOffset;
    }

    public SimpleColorSolver(@NotNull @Language("RegExp") String regex, int translationOffset, boolean drawTextures) {
        this(regex, translationOffset);
        this.shouldDrawTextures = drawTextures;
    }

    protected boolean test(@NotNull Screen screen) {
        return test(screen.getTitle().getString());
    }

    protected boolean test(@NotNull String title) {
        Matcher matcher1 = pattern.matcher(title);
        if (matcher1.matches()) {
            matcher = matcher1;
            return true;
        }
        return false;
    }

    protected abstract List<Slot> getHighlightedSlots(DefaultedList<Slot> slots);

    public @Nullable ArrayList<String> getGroups(int group) {
        if (matcher == null) return null;
        ArrayList<String> groups = new ArrayList<>();

        for (int i = 1; i < matcher.groupCount() - 1; i++) {
            groups.add(matcher.group(i));
        }
        return groups;
    }
}
