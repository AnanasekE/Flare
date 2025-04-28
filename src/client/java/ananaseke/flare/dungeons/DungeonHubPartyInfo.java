package ananaseke.flare.dungeons;

import ananaseke.flare.FlareClient;
import ananaseke.flare.KeyBinds;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DungeonHubPartyInfo {
    public DungeonHubPartyInfo() {
//        ClientTickEvents.END_CLIENT_TICK.register(DungeonHubPartyInfo::onEndTick);
//        ServerTickEvents.START_SERVER_TICK.register(server -> {
//            server.getPlayerManager().getPlayerList().forEach(PlayerEntity::getName);
//        });
    }

    private static void onEndTick(MinecraftClient client) { //TODO
        List<Text> tabEntries = new ArrayList<>();
        if (client.getNetworkHandler() == null) return;
        Collection<PlayerListEntry> listedPlayerListEntries = client.getNetworkHandler().getPlayerList();
        if (KeyBinds.devKeybind.isPressed()) {
            for (PlayerListEntry entry : listedPlayerListEntries) {
                Text playerName = client.inGameHud.getPlayerListHud().getPlayerName(entry);
                if (!tabEntries.contains(playerName)) {
                    tabEntries.add(playerName);
                }
                FlareClient.LOGGER.info(playerName.getString());
            }
        }
    }
}
