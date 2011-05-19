package me.passivepicasso.craftbox;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import me.passivepicasso.craftbox.Recipes.Result;
import me.passivepicasso.util.DatabaseManager;
import me.passivepicasso.util.DatabaseVersion;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class CraftBox extends JavaPlugin {

    private final HashMap<Player, Boolean> debugees               = new HashMap<Player, Boolean>();
    private static Logger                  log                    = null;
    private static DatabaseVersion         currentDatabaseVersion = new DatabaseVersion();

    static {
        currentDatabaseVersion.setMajor(0);
        currentDatabaseVersion.setMinor(1);
        currentDatabaseVersion.setSub(3);
    }

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
        setLog(getServer().getLogger());
        PluginDescriptionFile pdfFile = this.getDescription();
        Recipes.initialize(this);

        DatabaseManager.initialize("CraftBox", currentDatabaseVersion);

        Set<RecipeState> recipeStates = DatabaseManager.getSet(RecipeState.class);

        if (recipeStates.size() == 0) {
            getLog().log(Level.INFO, "[CraftBox] No Recipe settings found, defaulting all recipes to on.");
            for (Recipes.Result result : EnumSet.allOf(Recipes.Result.class)) {
                RecipeState state = Recipes.enableRecipe(result);
                DatabaseManager.store(state);
                Recipes.activateRecipe(state);
                getLog().log(Level.INFO, "[CraftBox] Set Recipes.Result." + result.toString() + (state.getRecipeState() ? " enabled." : " disabled."));
            }
        } else {
            Recipes.loadRecipes(recipeStates);
            for (RecipeState recipeState : Recipes.getRecipes()) {
                if (recipeState.getRecipeState()) {
                    Recipes.activateRecipe(recipeState);
                }
                getLog().log(Level.INFO, "[CraftBox] Recipe " + recipeState.getResult().toString() + (recipeState.getRecipeState() ? " activated." : " inactive."));
            }
        }
        setupCommands();
        DatabaseManager.close();
        registerEvents();
        System.out.println(pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!");
    }

    @Override
    public void onLoad() {
        setLog(getServer().getLogger());
        CraftBoxBlockListener.initialize(this);
    }

    public void setDebugging( final Player player, final boolean value ) {
        debugees.put(player, value);
    }

    @SuppressWarnings("unused")
    private void registerEvents() {
        PluginManager pm = getServer().getPluginManager();
        // pm.registerEvent(Type.REDSTONE_CHANGE, blockListener, Priority.Normal, this);
        // pm.registerEvent(Type.BLOCK_PLACE, blockListener, Priority.Normal, this);
    }

    private void setupCommands() {
        PluginCommand pc = getCommand("togglerecipe");
        PluginCommand pcShort = getCommand("tr");
        CommandExecutor commandExecutor = new CommandExecutor() {
            public boolean onCommand( CommandSender sender, Command command, String label, String[] args ) {
                if (!(sender instanceof Player)) {
                    try {
                        Result result = Recipes.Result.valueOf(args[0]);
                        boolean state = Recipes.toggleRecipe(result);
                        sender.sendMessage("Recipe for " + result.toString() + " has been toggled " + (state ? "on." : "off."));
                        if (!state) {
                            sender.sendMessage("However this change will not take effect until the server is restarted.");
                        } else {
                            sender.sendMessage("However this change will not take effect until the server is reloaded.");
                        }
                        return true;
                    } catch (Exception e) {
                        StringBuilder recipeSet = new StringBuilder();
                        for (Result result : EnumSet.allOf(Recipes.Result.class)) {
                            recipeSet.append(" " + result.toString());
                        }
                        sender.sendMessage("Invalid recipe title, options are" + recipeSet.toString() + ".");
                    }
                }
                return false;
            }
        };
        if (pc != null) {
            pc.setExecutor(commandExecutor);
        }
        if (pcShort != null) {
            pcShort.setExecutor(commandExecutor);
        }
    }
}
