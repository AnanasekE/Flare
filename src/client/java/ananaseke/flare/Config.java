package ananaseke.flare;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class Config {
    public static Screen parent;

    public static boolean currentValue;

    public static boolean renderDungeonMap = true;


    static ConfigBuilder builder = ConfigBuilder.create()
            .setParentScreen(parent)
            .setTitle(Text.of("CONFIG"));

    static Screen configScreen = builder.build();


    public static void setScreen(Screen configScreen) {
        Screen currentScreen = MinecraftClient.getInstance().currentScreen;
        MinecraftClient.getInstance().setScreen(

                builder.setSavingRunnable(() -> {
                    MinecraftClient.getInstance().setScreen(currentScreen);
                    // save
                }).build()
        );
    }

    public static void createConfig() {
        ConfigCategory general = builder.getOrCreateCategory(Text.of("General"));

        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        general.addEntry(entryBuilder.startBooleanToggle(Text.of("Render dungeon map"), renderDungeonMap)
            .setDefaultValue(true) // Recommended: Used when user click "Reset"
            .setTooltip(Text.of("Toggles dungeon map")) // Optional: Shown when the user hover over this option
            .setSaveConsumer(newValue -> currentValue = newValue) // Recommended: Called when user save the config
            .build()); // Builds the option entry for cloth config

    }

}
