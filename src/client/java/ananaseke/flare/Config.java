package ananaseke.flare;


import me.shedaniel.autoconfig.ConfigData;

@me.shedaniel.autoconfig.annotation.Config(name = "flare")
public class Config implements ConfigData {
    public boolean shouldRenderDungeonMap = true;
    public boolean dungeonHigherLowerSolver = true;
    public boolean fullbright = true;
}
