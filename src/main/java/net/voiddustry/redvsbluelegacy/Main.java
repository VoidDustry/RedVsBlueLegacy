package net.voiddustry.redvsbluelegacy;

import arc.util.CommandHandler;
import mindustry.mod.Plugin;
import net.voiddustry.redvsbluelegacy.commands.Menu;

@SuppressWarnings("unused")

public class Main extends Plugin {
    public void init() {

    }

    @Override
    public void registerClientCommands(CommandHandler handler) {
        handler.register("m", "Opens a menu", Menu::execute);
    }
}
