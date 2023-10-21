package net.voiddustry.redvsbluelegacy.game;

import arc.Events;
import mindustry.content.StatusEffects;
import mindustry.game.EventType;
import mindustry.game.Team;
import mindustry.gen.Call;
import mindustry.gen.Groups;
import mindustry.gen.Player;
import mindustry.gen.Unit;
import mindustry.ui.Menus;
import net.voiddustry.redvsbluelegacy.Players;
import net.voiddustry.redvsbluelegacy.domain.PlayerData;
import net.voiddustry.redvsbluelegacy.other.Bundle;

public class EventLoader {
    public static void init() {
        Events.on(EventType.UnitDestroyEvent.class, event -> {
            Unit unit = event.unit;


            if (event.unit.team == Team.crux) {
                Groups.player.each(p -> {
                    if (p.team() == Team.blue) Players.getPlayer(p).addPoints(1);
                });
            } else if (event.unit.team == Team.blue) {
                if (event.unit.isPlayer() && unit.type != null) {
                    Player player = event.unit.getPlayer();
                    PlayerData data = Players.getPlayer(player);
                    data.setTeam(Team.crux);
                    player.team(Team.crux);

                    if (data.getUnit() != null) {
                        Unit dead = data.getUnit().type.spawn(Team.crux, player.x, player.y);
                        dead.apply(StatusEffects.overdrive);
                        dead.health = dead.type.health/3;

                        if (!dead.dead) {
                            Call.unitControl(player, dead);
                        }
                    }

                    data.setUnit(null);
                }
                Groups.player.each(p -> {
                    if (p.team() == Team.crux) Players.getPlayer(p).addKills(1);
                });
            }
        });

        Events.on(EventType.PlayerJoin.class, event -> {
            Player player = event.player;
            Players.getPlayer(player);

            int menu = Menus.registerMenu(((player1, option) -> {}));

            Call.menu(player.con, menu, Bundle.get("welcome", player.locale), Bundle.get("welcome.message", player.locale), new String[][] {{Bundle.get("close", player.locale)}});
        });

        Events.on(EventType.GameOverEvent.class, event -> {
            Players.clearMap();
        });

        Events.run(EventType.Trigger.update, Players::forEach);
    }
}
