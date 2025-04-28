package ananaseke.flare.misc;

import net.minecraft.util.math.BlockPos;

import java.awt.*;

public class Waypoint {
    public BlockPos pos;
    public Color color;
    public boolean isBeacon;

    public Waypoint(BlockPos pos, Color color, boolean isBeacon) {
        this.pos = pos;
        this.color = color;
        this.isBeacon = isBeacon;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Waypoint o)) return false;
        return this.pos.equals(o.pos)
                && this.color.equals(o.color)
                && this.isBeacon == o.isBeacon;
    }

    @Override
    public int hashCode() {
        int result = pos.hashCode();
        result = 31 * result + color.hashCode();
        result = 31 * result + (isBeacon ? 1 : 0);
        return result;
    }
}