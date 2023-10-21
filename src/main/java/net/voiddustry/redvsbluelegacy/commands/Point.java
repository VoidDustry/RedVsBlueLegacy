package net.voiddustry.redvsbluelegacy.commands;


import mindustry.gen.Call;
import mindustry.gen.Player;
import net.voiddustry.redvsbluelegacy.other.Bundle;

public class Point {
    public static void point(Player player) {
        Call.label("[scarlet]\uE805", 3, player.mouseX, player.mouseY);
        Call.label(Bundle.format("commands.point", Bundle.findLocale(player.locale), player.name), 3, player.mouseX, player.mouseY-5);
    }
}
