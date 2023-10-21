package net.voiddustry.redvsbluelegacy.game;

import mindustry.Vars;
import mindustry.content.UnitTypes;
import mindustry.game.Team;

public class Rules {
    public static void initStats() {
        UnitTypes.quasar.health = 1550;
        UnitTypes.spiroct.health = 2000;
        UnitTypes.mace.health = 200;

        UnitTypes.quell.weapons.forEach(weapon -> weapon.bullet.lifetime = 99999);
        UnitTypes.anthicus.weapons.forEach(weapon -> weapon.bullet.lifetime = 99999);
        UnitTypes.mace.weapons.forEach(weapon -> weapon.bullet.damage = 23);
    }

    public static void initRules() {

        mindustry.game.Rules rules = Vars.state.rules;

        rules.bannedUnits.add(UnitTypes.alpha);

        rules.bannedBlocks.addAll(Vars.content.blocks());

        rules.unitCap = 50;
        rules.defaultTeam = Team.blue;

        rules.schematicsAllowed = false;
    }
}
