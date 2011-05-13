package me.passivepicasso.craftbox;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CraftBox extends JavaPlugin {
    private final HashMap<Player, Boolean> debugees = new HashMap<Player, Boolean>();

    private static Logger                  log      = null;

    private static void setLog( Logger log ) {
        CraftBox.log = log;
    }

    @SuppressWarnings({"UnusedDeclaration"})
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
        registerEvents();
        PluginDescriptionFile pdfFile = this.getDescription();
        System.out.println(pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!");
    }

    @Override
    public void onLoad() {
        setLog(getServer().getLogger());
        addCoalToOreBlock();
        addDiamondRecipe();
        addRedstoneToOreBlock();
        addOreToCoal();
        addOreToRedstone();
    }

    public void setDebugging( final Player player, final boolean value ) {
        debugees.put(player, value);
    }

    private void addCoalToOreBlock() {
        ItemStack coalOre = new ItemStack(Material.COAL_ORE);
        coalOre.setAmount(1);
        ShapedRecipe coalOreRecipe = new ShapedRecipe(coalOre);
        coalOreRecipe.shape("aaa", "asa", "aaa");
        coalOreRecipe.setIngredient('a', Material.COAL);
        coalOreRecipe.setIngredient('s', Material.COBBLESTONE);
        getServer().addRecipe(coalOreRecipe);
        log.log(Level.INFO, "Coal to Ore recipe submitted");
    }

    private void addDiamondRecipe() {
        ItemStack diamond = new ItemStack(Material.DIAMOND);
        diamond.setAmount(1);
        ShapelessRecipe diamondRecipe = new ShapelessRecipe(diamond);
        diamondRecipe.addIngredient(9, Material.COAL_ORE);
        getServer().addRecipe(diamondRecipe);
        log.log(Level.INFO, "Coal Ore to Diamond recipe submitted");
    }

    private void addOreToRedstone() {
        ItemStack coalOre = new ItemStack(Material.REDSTONE);
        coalOre.setAmount(4);
        ShapelessRecipe oreCoalRecipe = new ShapelessRecipe(coalOre);
        oreCoalRecipe.addIngredient(Material.REDSTONE_ORE);
        getServer().addRecipe(oreCoalRecipe);
        log.log(Level.INFO, "Ore to Redstone recipe submitted");
    }

    private void addOreToCoal() {
        ItemStack coalOre = new ItemStack(Material.COAL);
        coalOre.setAmount(8);
        ShapelessRecipe oreCoalRecipe = new ShapelessRecipe(coalOre);
        oreCoalRecipe.addIngredient(Material.COAL_ORE);
        getServer().addRecipe(oreCoalRecipe);
        log.log(Level.INFO, "Ore to Coal recipe submitted");
    }

    private void addRedstoneToOreBlock() {
        ItemStack coalOre = new ItemStack(Material.REDSTONE_ORE);
        coalOre.setAmount(1);
        ShapedRecipe coalOreRecipe = new ShapedRecipe(coalOre);
        coalOreRecipe.shape("a a", " s ", "a a");
        coalOreRecipe.setIngredient('a', Material.REDSTONE);
        coalOreRecipe.setIngredient('s', Material.COBBLESTONE);
        getServer().addRecipe(coalOreRecipe);
        log.log(Level.INFO, "Redstone Ore recipe submitted");
    }

    @SuppressWarnings({"EmptyMethod"})
    private void registerEvents() {
        // PluginManager pm = getServer().getPluginManager();
    }

}
