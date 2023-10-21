package net.voiddustry.redvsbluelegacy.game;

import arc.Events;
import arc.util.Timer;
import mindustry.Vars;
import mindustry.content.Blocks;
import mindustry.game.EventType;
import net.voiddustry.redvsbluelegacy.commands.Units;

public class Loader {
    public static void load() {
        Rules.initStats();
        EventLoader.init();
        Units.initUnitsTable();

        Events.on(EventType.WorldLoadBeginEvent.class, event -> {
            Rules.initRules();
            Timer.schedule(Game::start, 10);
        });

        Events.on(EventType.WorldLoadEndEvent.class, event -> {
            for (int x = 0; x < Vars.world.width(); x++) {
                for (int y = 0; y < Vars.world.height(); y++) {
                    if(Vars.world.tile(x, y).block() == Blocks.worldCell) {
                        Vars.world.tile(x, y).setNet(Blocks.air);
                        Game.blueSpawn = Vars.world.tile(x, y);
                    }
                }
            }
        });
    }
}
