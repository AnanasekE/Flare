package ananaseke.flare.misc;

import net.minecraft.text.Text;

public class FlareMessage {
    Text message;

    public FlareMessage(String message) {
        this.message = Text.of("§b[§6Flare§b] " + message);
    }

    public Text getMessage() {
        return message;
    }
}
