package ananaseke.flare;


import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@me.shedaniel.autoconfig.annotation.Config(name = "flare")
public class Config implements ConfigData {
    public boolean fullbright = true;

    @ConfigEntry.Category(value = "Dungeons")
    public boolean highlightStarredMobs = true;
    @ConfigEntry.Category(value = "Dungeons")
    public boolean shouldRenderDungeonMap = true;
    @ConfigEntry.Category(value = "Dungeons")
    public boolean dungeonHigherLowerSolver = true;
    @ConfigEntry.Category(value = "Dungeons")
    public boolean fragRunMode = false;

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
    @ConfigEntry.Category(value = "Chat Hiders")
    public boolean crowdHider = true;
    @ConfigEntry.Category(value = "Chat Hiders")
    public boolean youArePlayingOnProfileHider = true;


    @ConfigEntry.Category(value = "Slayers")
    public boolean showSlayerInfo = true;
    @ConfigEntry.Category(value = "Slayers")
    public int slayerInfoVerticalOffset = 50;

    @ConfigEntry.Category(value = "Other")
    public double itemRenderXoffset = 0.0;
    @ConfigEntry.Category(value = "Other")
    public double itemRenderYoffset = 0.0;
    @ConfigEntry.Category(value = "Other")
    public double itemRenderZoffset = 0.0;
    @ConfigEntry.Category(value = "Other")
    public double itemRenderScale = 1;
    @ConfigEntry.Category(value = "Other")
    public boolean disableBlockBreakParticles = false;
    @ConfigEntry.Category(value = "Other")
    public boolean disableFireOverlay = false;
    @ConfigEntry.Category(value = "Other")
    public float catchFishNotificationSoundVolume = 1F;
}
