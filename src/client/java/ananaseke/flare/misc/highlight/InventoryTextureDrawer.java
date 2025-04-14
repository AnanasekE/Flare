package ananaseke.flare.misc.highlight;


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

public abstract class InventoryTextureDrawer {
    protected Pattern pattern;
    @Nullable
    protected Matcher matcher;

    protected final List<TextureHighlightEntry> highlights = new ArrayList<>();
    private int translationOffset = 0;

    public InventoryTextureDrawer(@NotNull @Language("RegExp") String regex) {
        this.pattern = Pattern.compile(regex);

        ClientTickEvents.END_CLIENT_TICK.register(client1 -> {
            if (client1.currentScreen instanceof GenericContainerScreen screen) {
                if (test(screen)) {
                    highlights.clear();
                    highlights.addAll(getHighlightedEntries(screen.getScreenHandler().slots));
                } else if (!highlights.isEmpty()) {
                    highlights.clear();
                }
            } else if (!highlights.isEmpty()) {
                highlights.clear();
            }
        });

        DrawSlotCallback.EVENT.register((drawContext, slot) -> {
            for (TextureHighlightEntry entry : highlights) {
                if (entry.slot().id == slot.id) {
                    RenderUtils.renderItemTexture(drawContext, entry.slot(), entry.stack(), translationOffset);
                }
            }
        });
    }

    public InventoryTextureDrawer(@NotNull @Language("RegExp") String regex, int translationOffset) {
        this(regex);
        this.translationOffset = translationOffset;
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

    protected abstract List<TextureHighlightEntry> getHighlightedEntries(DefaultedList<Slot> slots);

    public @Nullable ArrayList<String> getGroups(int group) {
        if (matcher == null) return null;
        ArrayList<String> groups = new ArrayList<>();

        for (int i = 1; i <= matcher.groupCount(); i++) {
            groups.add(matcher.group(i));
        }
        return groups;
    }
}
