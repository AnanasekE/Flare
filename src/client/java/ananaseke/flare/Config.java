package ananaseke.flare;


import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@me.shedaniel.autoconfig.annotation.Config(name = "flare")
public class Config implements ConfigData {
    public boolean shouldRenderDungeonMap = true;
    public boolean dungeonHigherLowerSolver = true;
    public boolean fullbright = true;

    @ConfigEntry.Category(value = "Chat Hiders")
    public boolean bossMessageHider = true;
    @ConfigEntry.Category(value = "Chat Hiders")
    public boolean mortMessageHider = true;
    @ConfigEntry.Category(value = "Chat Hiders")
    public boolean killComboHider = true;
    @ConfigEntry.Category(value = "Chat Hiders")
    public boolean dungeonBuffHider = true;
    @ConfigEntry.Category(value = "Chat Hiders")
    public boolean superboomHider = true;
    @ConfigEntry.Category(value = "Chat Hiders")
    public boolean reviveStoneHider = true;
    @ConfigEntry.Category(value = "Chat Hiders")
    public boolean abilityHider = true;
    @ConfigEntry.Category(value = "Chat Hiders")
    public boolean witherEssenceHider = true;
    @ConfigEntry.Category(value = "Chat Hiders")
    public boolean abilityDamageHider = true;



}
