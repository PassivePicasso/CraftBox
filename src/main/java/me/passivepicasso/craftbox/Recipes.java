package me.passivepicasso.craftbox;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.material.MaterialData;

public class Recipes {

    private static CraftBox     plugin;
    private static MaterialData lapisDye = new MaterialData(Material.INK_SACK, (byte) 4);

    public static void addCoalToOreBlock() {
        ItemStack coalOre = new ItemStack(Material.COAL_ORE, 1);
        ShapedRecipe coalOreRecipe = new ShapedRecipe(coalOre);
        coalOreRecipe.shape("aaa", "asa", "aaa");
        coalOreRecipe.setIngredient('a', Material.COAL);
        coalOreRecipe.setIngredient('s', Material.COBBLESTONE);
        getServer().addRecipe(coalOreRecipe);
        getLog().log(Level.INFO, "Coal to Ore recipe submitted");
    }

    public static void addDiamondRecipe() {
        ItemStack diamond = new ItemStack(Material.DIAMOND, 1);
        ShapelessRecipe diamondRecipe = new ShapelessRecipe(diamond);
        diamondRecipe.addIngredient(9, Material.COAL_ORE);
        getServer().addRecipe(diamondRecipe);
        getLog().log(Level.INFO, "Coal Ore to Diamond recipe submitted");
    }

    public static void addLapisToOreBlock() {
        ItemStack lapisOre = new ItemStack(Material.LAPIS_ORE, 1);
        ShapedRecipe lapisOreRecipe = new ShapedRecipe(lapisOre);
        lapisOreRecipe.shape("lll", "lcl", "lll");
        lapisOreRecipe.setIngredient('l', lapisDye);
        lapisOreRecipe.setIngredient('c', Material.COBBLESTONE);
        getServer().addRecipe(lapisOreRecipe);
        getLog().log(Level.INFO, "Lapis Ore recipe submitted");
    }

    public static void addOreToCoal() {
        ItemStack coalOre = new ItemStack(Material.COAL, 8);
        ShapelessRecipe oreCoalRecipe = new ShapelessRecipe(coalOre);
        oreCoalRecipe.addIngredient(Material.COAL_ORE);
        getServer().addRecipe(oreCoalRecipe);
        getLog().log(Level.INFO, "Ore to Coal recipe submitted");
    }

    public static void addOreToLapis() {
        ItemStack lapisDye = new ItemStack(Material.INK_SACK, 8, ((short) 0), ((byte) 4));

        ShapelessRecipe oreLapisRecipe = new ShapelessRecipe(lapisDye);
        oreLapisRecipe.addIngredient(Material.LAPIS_ORE);
        getServer().addRecipe(oreLapisRecipe);
        getLog().log(Level.INFO, "Ore to Lapis recipe submitted");
    }

    public static void addOreToRedstone() {
        ItemStack coalOre = new ItemStack(Material.REDSTONE, 4);
        ShapelessRecipe oreCoalRecipe = new ShapelessRecipe(coalOre);
        oreCoalRecipe.addIngredient(Material.REDSTONE_ORE);
        getServer().addRecipe(oreCoalRecipe);
        getLog().log(Level.INFO, "Ore to Redstone recipe submitted");
    }

    public static void addRedstoneToOreBlock() {
        ItemStack coalOre = new ItemStack(Material.REDSTONE_ORE, 1);
        ShapedRecipe coalOreRecipe = new ShapedRecipe(coalOre);
        coalOreRecipe.shape("a a", " s ", "a a");
        coalOreRecipe.setIngredient('a', Material.REDSTONE);
        coalOreRecipe.setIngredient('s', Material.COBBLESTONE);
        getServer().addRecipe(coalOreRecipe);
        getLog().log(Level.INFO, "Redstone Ore recipe submitted");
    }

    public static final Logger getLog() {
        return CraftBox.getLog();
    }

    public static final Server getServer() {
        return plugin.getServer();
    }

    public static void initialize( CraftBox plugin ) {
        Recipes.plugin = plugin;
    }
}