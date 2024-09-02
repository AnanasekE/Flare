package ananaseke.flare;

import ananaseke.flare.misc.FlareMessage;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.text.Text;

public class Commands {
    public static void initialize() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) ->
                dispatcher.register(ClientCommandManager.literal("dn").executes(context -> {
                    if (context.getSource().getClient().getNetworkHandler() == null) return 0;
                    if (context.getSource().getClient().player != null) {
                        context.getSource().getClient().player.sendMessage(new FlareMessage("Warping to Dungeon Hub").getMessage(), false);
                    }
                    context.getSource().getClient().getNetworkHandler().sendCommand("warp dhub");
                    return 1;
                })));
    }
}
