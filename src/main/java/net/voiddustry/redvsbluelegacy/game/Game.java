package net.voiddustry.redvsbluelegacy.game;

import arc.util.Log;
import mindustry.content.UnitTypes;
import mindustry.game.Team;
import mindustry.gen.Groups;
import mindustry.gen.Unit;
import mindustry.world.Tile;
import net.voiddustry.redvsbluelegacy.Players;
import net.voiddustry.redvsbluelegacy.domain.PlayerData;


public class Game {
    public static Tile blueSpawn;
    public static void start() {
        Groups.player.each(player -> {
            PlayerData playerData = Players.getPlayer(player);

            Unit spawned = UnitTypes.mace.spawn(Team.blue, blueSpawn.x*8, blueSpawn.y*8);

            if (!spawned.dead) {
                playerData.setUnit(spawned);

            } else {
                Log.err("Spawned unit is dead/null");
            }
            playerData.setTeam(Team.blue);
        });
    }
}
