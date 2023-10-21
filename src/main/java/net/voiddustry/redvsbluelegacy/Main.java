package net.voiddustry.redvsbluelegacy;

import arc.util.CommandHandler;
import mindustry.game.Team;
import mindustry.gen.Player;
import mindustry.mod.Plugin;
import net.voiddustry.redvsbluelegacy.commands.Menu;
import net.voiddustry.redvsbluelegacy.commands.Point;
import net.voiddustry.redvsbluelegacy.game.Loader;

@SuppressWarnings("unused")

public class Main extends Plugin {

    public void init() {
        Loader.load();
    }

    @Override
    public void registerClientCommands(CommandHandler handler) {
        handler.register("m", "Opens a menu", (String[] args, Player player) -> Menu.execute(player));
        handler.register("p", "Point somewhere.", (String[] args, Player player) -> Point.point(player));
        handler.register("crux", "Infects you.", (String[] args, Player player) -> {
            if (player.team() == Team.blue) {
                player.unit().kill();
                Players.getPlayer(player).setTeam(Team.crux);
            }
        });
    }
}
