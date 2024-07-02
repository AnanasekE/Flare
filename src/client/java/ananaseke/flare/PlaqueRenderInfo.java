package ananaseke.flare;

import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class PlaqueRenderInfo {
    private Text message;
    private Identifier backgroundIdentifier;
    private ItemStack plaqueItemStack;
    private long displayUntil;

    public PlaqueRenderInfo() {
        this.message = Text.of("");
        this.displayUntil = 0;
    }

    public Text getMessage() {
        return message;
    }

    public Identifier getBackgroundIdentifier() {
        return backgroundIdentifier;
    }

    public ItemStack getPlaqueItemStack() {
        return plaqueItemStack;
    }

    public void setData(Text message, Identifier backgroundIdentifier, ItemStack plaqueItemStack, long displayTimeMs) {
        this.message = message;
        this.backgroundIdentifier = backgroundIdentifier;
        this.plaqueItemStack = plaqueItemStack;
        this.displayUntil = System.currentTimeMillis() + displayTimeMs;
    }

    public boolean shouldRender() {
        return System.currentTimeMillis() < displayUntil;
    }
}
