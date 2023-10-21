package net.voiddustry.redvsbluelegacy.commands;

import arc.Core;
import arc.util.Log;
import mindustry.content.UnitTypes;
import mindustry.game.Team;
import mindustry.gen.Call;
import mindustry.gen.Player;
import mindustry.gen.Unit;
import mindustry.type.UnitType;
import mindustry.ui.Menus;
import net.voiddustry.redvsbluelegacy.Players;
import net.voiddustry.redvsbluelegacy.domain.PlayerData;
import net.voiddustry.redvsbluelegacy.domain.UnitsTable;
import net.voiddustry.redvsbluelegacy.other.Bundle;

import java.util.HashMap;
import java.util.Map;

public class Units {
    static final String[] prefixes = {"[lime]", "[scarlet]", "[lightgray]"};

    private static final int menu = Menus.registerMenu((player, option) -> {
        UnitType unitType = UnitsTable.units[option / UnitsTable.units[0].length][option % UnitsTable.units[0].length];
        openUnitMenuGui(unitType, player);
    });

    private static final Map<UnitType, Integer> unitPrices = new HashMap<>();
    private static final String[][] buttons = new String[6][3];

    public static void initUnitsTable() {
        int row = 0;
        int column = 0;
        String prefix;

        for (UnitType[] unitsLine : UnitsTable.units) {
            for (UnitType unit : unitsLine) {
                prefix = prefixes[column];

                buttons[row][column] = prefix + unit.name.substring(0, 1).toUpperCase().concat(unit.name.substring(1));
                unitPrices.put(unit, UnitsTable.prices[row][column]);

                column++;
            }
            row++;
            column = 0;
        }
    }

    private static void buyUnit(UnitType unitType, Player player) {
        PlayerData playerData = Players.getPlayer(player);
        int price = unitPrices.get(unitType);
        if (playerData.getPoints() >= price) {

            if(player.team() == Team.crux) {
                return;
            }

            playerData.subtractPoints(unitPrices.get(unitType));

            Unit oldUnit = player.unit();
            Unit spawned = unitType.spawn(Team.blue, player.x, player.y);

            if (!spawned.dead) {
                if (spawned.health <= oldUnit.health) {
                    spawned.health = spawned.type.health;
                } else {
                    spawned.health = oldUnit.health;
                }

                playerData.setUnit(spawned);
                Call.unitControl(player, spawned);
                oldUnit.kill();

                player.sendMessage(Bundle.get("unit.brought", player.locale));
            } else {
                Log.err("Spawned unit is dead/null");
                Core.app.exit();
            }


        } else {
            player.sendMessage(Bundle.get("menu.units.not-enough", player.locale()));
        }
    }

    public static void execute(Player player) {
        openGui(player);
    }

    private static void openGui(Player player) {
        Call.menu(player.con, menu, Bundle.get("menu.units.title", player.locale), "", buttons);
    }

    private static void openUnitMenuGui(UnitType unitType, Player player) {
        String[][] buttons = {
                {"[lime]Buy"},
                {"[lightgray]Back", "[gray]Close"}
        };

        String message = unitType.emoji() + "\n" + "\n" +
                Bundle.get("menu.units.info.health", player.locale) + " " + (int) unitType.health + "\n" +
                Bundle.get("menu.units.info.armor", player.locale) + " " + (int) unitType.armor + "\n" +
                Bundle.get("menu.units.info.price", player.locale) + " " + unitPrices.get(unitType);

        int menu = Menus.registerMenu(((player1, option) -> {
            switch (option) {
                case 0 -> buyUnit(unitType, player);
                case 1 -> openGui(player);
            }
        }));

        Call.menu(player.con, menu, Bundle.get("menu.units.title"), message, buttons);
    }
}
//я пока код почитаю