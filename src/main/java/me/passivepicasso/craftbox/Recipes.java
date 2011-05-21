package me.passivepicasso.craftbox;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import me.passivepicasso.util.DatabaseManager;

import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.material.MaterialData;

public class Recipes {
    public enum Result {
        CoalOre, Diamond, Ice, Coal, Lapis, LapisOre, Redstone, RedstoneOre, Web, SoulSand
    }

    private static HashSet<RecipeState> recipes  = new HashSet<RecipeState>();
    private static CraftBox             plugin;
    private static MaterialData         lapisDye = new MaterialData(Material.INK_SACK, (byte) 4);

    public static void activateRecipe( RecipeState recipe ) {
        try {
            if (recipe.getRecipeState()) {
                Recipes.class.getMethod(recipe.getRecipeMethod()).invoke(null);
            }
        } catch (NoSuchMethodException e) {
            getLog().log(Level.SEVERE, "No such Method found: Recipes." + recipe.getRecipeMethod());
            e.printStackTrace();
        } catch (Exception e) {
            getLog().log(Level.SEVERE, "Unexpected Error encountered");
            e.printStackTrace();
        }
    }

    public static void activateRecipes() {
        RecipeState currentRecipe = null;
        try {
            for (RecipeState recipe : recipes) {
                if (recipe.getRecipeState()) {
                    currentRecipe = recipe;
                    Recipes.class.getMethod(recipe.getRecipeMethod()).invoke(null);
                }
            }
        } catch (NoSuchMethodException e) {
            if (currentRecipe != null) {
                getLog().log(Level.SEVERE, "No such Method found: Recipes." + currentRecipe.getRecipeMethod());
            } else {
                getLog().log(Level.SEVERE, "No Recipe Found");
            }
            e.printStackTrace();
        } catch (Exception e) {
            getLog().log(Level.SEVERE, "Unexpected Error encountered");
            e.printStackTrace();
        }
    }

    public static void addCoalOreRecipe() {
        ShapedRecipe coalOreRecipe = new ShapedRecipe(new ItemStack(Material.COAL_ORE, 1));
        coalOreRecipe.shape("aaa", "asa", "aaa");
        coalOreRecipe.setIngredient('a', Material.COAL);
        coalOreRecipe.setIngredient('s', Material.COBBLESTONE);
        getServer().addRecipe(coalOreRecipe);
    }

    public static void addCoalRecipe() {
        ShapelessRecipe oreCoalRecipe = new ShapelessRecipe(new ItemStack(Material.COAL, 8));
        oreCoalRecipe.addIngredient(Material.COAL_ORE);
        getServer().addRecipe(oreCoalRecipe);
    }

    public static void addDiamondRecipe() {
        ShapelessRecipe diamondRecipe = new ShapelessRecipe(new ItemStack(Material.DIAMOND, 1));
        diamondRecipe.addIngredient(9, Material.COAL_ORE);
        getServer().addRecipe(diamondRecipe);
    }

    public static void addIceRecipe() {
        ShapedRecipe iceRecipe = new ShapedRecipe(new ItemStack(Material.ICE, 1));
        iceRecipe.shape("SsS", "sws", "SsS");
        iceRecipe.setIngredient('s', Material.SNOW_BLOCK);
        iceRecipe.setIngredient('S', Material.SNOW_BALL);
        iceRecipe.setIngredient('w', Material.WATER_BUCKET);
        getServer().addRecipe(iceRecipe);
    }

    public static void addLapisOreRecipe() {
        ShapedRecipe lapisOreRecipe = new ShapedRecipe(new ItemStack(Material.LAPIS_ORE, 1));
        lapisOreRecipe.shape("lll", "lcl", "lll");
        lapisOreRecipe.setIngredient('l', lapisDye);
        lapisOreRecipe.setIngredient('c', Material.COBBLESTONE);
        getServer().addRecipe(lapisOreRecipe);
    }

    public static void addLapisRecipe() {
        ShapelessRecipe oreLapisRecipe = new ShapelessRecipe(new ItemStack(Material.INK_SACK, 8, ((short) 0), ((byte) 4)));
        oreLapisRecipe.addIngredient(Material.LAPIS_ORE);
        getServer().addRecipe(oreLapisRecipe);
    }

    public static void addRedstoneOreRecipe() {
        ShapedRecipe redstoneOreRecipe = new ShapedRecipe(new ItemStack(Material.REDSTONE_ORE, 1));
        redstoneOreRecipe.shape("a a", " s ", "a a");
        redstoneOreRecipe.setIngredient('a', Material.REDSTONE);
        redstoneOreRecipe.setIngredient('s', Material.COBBLESTONE);
        getServer().addRecipe(redstoneOreRecipe);
    }

    public static void addRedstoneRecipe() {
        ShapelessRecipe oreRedstoneRecipe = new ShapelessRecipe(new ItemStack(Material.REDSTONE, 4));
        oreRedstoneRecipe.addIngredient(Material.REDSTONE_ORE);
        getServer().addRecipe(oreRedstoneRecipe);
    }

    public static void addSoulSandRecipe() {
        ShapelessRecipe soulSandRecipe = new ShapelessRecipe(new ItemStack(Material.SOUL_SAND, 1));
        soulSandRecipe.addIngredient(1, new MaterialData(Material.BONE));
        soulSandRecipe.addIngredient(1, new MaterialData(Material.WATER_BUCKET));
        soulSandRecipe.addIngredient(1, new MaterialData(Material.LAVA_BUCKET));
        soulSandRecipe.addIngredient(1, new MaterialData(Material.SAND));
        soulSandRecipe.addIngredient(1, new MaterialData(Material.PORK));
        soulSandRecipe.addIngredient(1, new MaterialData(Material.JACK_O_LANTERN));
        getServer().addRecipe(soulSandRecipe);
    }

    public static void addSpongeRecipe() {

    }

    public static void addWebRecipe() {
        ShapedRecipe webRecipe = new ShapedRecipe(new ItemStack(Material.WEB, 1));
        webRecipe.shape("s s", " S ", "s s");
        webRecipe.setIngredient('s', Material.STRING);
        webRecipe.setIngredient('S', Material.SOUL_SAND);
        getServer().addRecipe(webRecipe);
    }

    public static RecipeState enableRecipe( Result result ) {
        RecipeState recipeState = new RecipeState();
        recipeState.setResult(result);
        recipeState.setRecipeMethod("add" + result.toString() + "Recipe");
        recipeState.setRecipeState(true);
        recipes.add(recipeState);

        return recipeState;
    }

    public static final Logger getLog() {
        return CraftBox.getLog();
    }

    public static HashSet<RecipeState> getRecipes() {
        return recipes;
    }

    public static void initialize( CraftBox plugin ) {
        Recipes.plugin = plugin;
    }

    public static void loadRecipes( Set<RecipeState> recipes ) {
        Recipes.recipes = new HashSet<RecipeState>(recipes);
    }

    public static boolean toggleRecipe( Result result ) {
        for (RecipeState recipeState : getRecipes()) {
            if (result.equals(recipeState.getResult())) {
                boolean newSetting = !recipeState.getRecipeState();
                recipeState.setRecipeState(newSetting);
                DatabaseManager.open();
                DatabaseManager.store(recipeState);
                DatabaseManager.close();
                return newSetting;
            }
        }
        return false;
    }

    private static Server getServer() {
        return plugin.getServer();
    }
}
