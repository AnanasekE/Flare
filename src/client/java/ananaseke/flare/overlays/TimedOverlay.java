package ananaseke.flare.overlays;

import net.minecraft.text.Text;

public class TimedOverlay {
    private long displayUntil;
    private long initialDisplayTimeMs;
    private Text message;

    public TimedOverlay() {
        this.message = Text.of("");
        this.displayUntil = 0;
        this.initialDisplayTimeMs = 0;
    }

    public Text getMessage() {
        return message;
    }

    public void setMessage(Text message, long displayTimeMs) {
        this.message = message;
        this.initialDisplayTimeMs = displayTimeMs;
        this.displayUntil = System.currentTimeMillis() + displayTimeMs;
    }

    public boolean shouldRender() {
        return System.currentTimeMillis() < displayUntil;
    }

    public float getTimeLeftPercentage() {
        long timeElapsed = initialDisplayTimeMs - (displayUntil - System.currentTimeMillis());
        return (float) (initialDisplayTimeMs - timeElapsed) / initialDisplayTimeMs;
    }

    public float getTimeLeftSec() {
        return (float) (displayUntil - System.currentTimeMillis()) / 1000;
    }
}
