package ananaseke.flare;

import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class PlaqueRenderInfo {
    private Text message;
    private Identifier backgroundIdentifier;
    private Identifier plaqueItemIdentifier;
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

    public Identifier getPlaqueItemIdentifier() {
        return plaqueItemIdentifier;
    }

    public void setData(Text message, Identifier backgroundIdentifier, Identifier plaqueItemIdentifier, long displayTimeMs) {
        this.message = message;
        this.backgroundIdentifier = backgroundIdentifier;
        this.plaqueItemIdentifier = plaqueItemIdentifier;
        this.displayUntil = System.currentTimeMillis() + displayTimeMs;
    }

    public boolean shouldRender() {
        return System.currentTimeMillis() < displayUntil;
    }
}
