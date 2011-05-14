package me.passivepicasso.craftbox;

import java.util.HashMap;
import java.util.logging.Logger;

import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class CraftBox extends JavaPlugin {
    private final HashMap<Player, Boolean> debugees = new HashMap<Player, Boolean>();

    private static Logger                  log      = null;

    public static Logger getLog() {
        return log;
    }

    private static void setLog( Logger log ) {
        CraftBox.log = log;
    }

    public CraftBox() {
    }

    public boolean isDebugging( final Player player ) {
        if (debugees.containsKey(player)) {
            return debugees.get(player);
        } else {
            return false;
        }
    }

    public void onDisable() {
        System.out.println("Goodbye world!");
    }

    public void onEnable() {
        PluginDescriptionFile pdfFile = this.getDescription();
        Recipes.initialize(this);
        Recipes.addCoalToOreBlock();
        Recipes.addRedstoneToOreBlock();
        Recipes.addLapisToOreBlock();
        Recipes.addOreToCoal();
        Recipes.addOreToRedstone();
        Recipes.addOreToLapis();
        Recipes.addDiamondRecipe();
        System.out.println(pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!");
    }

    @Override
    public void onLoad() {
        setLog(getServer().getLogger());
    }

    public void setDebugging( final Player player, final boolean value ) {
        debugees.put(player, value);
    }

    @SuppressWarnings("unused")
    private void registerEvents() {
        // PluginManager pm = getServer().getPluginManager();
    }

}
