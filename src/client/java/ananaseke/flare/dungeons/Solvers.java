package ananaseke.flare.dungeons;

import ananaseke.flare.dungeons.solvers.Blaze;
import ananaseke.flare.dungeons.solvers.Riddles;
import ananaseke.flare.dungeons.solvers.Trivia;

public class Solvers {
    public static void initialize() {
        Blaze.initialize();
        new Trivia();
        Riddles.initialize();
    }
}
