package ananaseke.flare.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

public final class DungeonEvents {
    public static final Event<Entered> ENTER = EventFactory.createArrayBacked(Entered.class, listeners -> (int floor) -> {
        for (DungeonEvents.Entered listener : listeners) {
            listener.onDungeonEntered(floor);
        }
    });

    public static final Event<Started> START = EventFactory.createArrayBacked(Started.class, listeners -> () -> {
        for (DungeonEvents.Started listener : listeners) {
            listener.onDungeonStarted();
        }
    });

    public static final Event<Ended> END = EventFactory.createArrayBacked(Ended.class, listeners -> () -> {
        for (DungeonEvents.Ended listener : listeners) {
            listener.onDungeonEnded();
        }
    });

    public static final Event<Exited> EXIT = EventFactory.createArrayBacked(Exited.class, listeners -> () -> {
        for (DungeonEvents.Exited listener : listeners) {
            listener.onDungeonExited();
        }
    });

    public static final Event<BossEnter> BOSS_ENTER = EventFactory.createArrayBacked(BossEnter.class, listeners -> () -> {
        for (DungeonEvents.BossEnter listener : listeners) {
            listener.onBossEnter();
        }
    });

    public interface Entered {
        void onDungeonEntered(int floor);
    }

    public interface Started {
        void onDungeonStarted();
    }

    public interface Ended {
        void onDungeonEnded();
    }

    public interface Exited {
        void onDungeonExited();
    }

    public interface BossEnter {
        void onBossEnter();
    }
}
