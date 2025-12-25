package dev.trinsdar.ic2cjsonrecipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import ic2.api.recipes.ingridients.inputs.IInput;
import ic2.api.recipes.registries.IMachineRecipeList.RecipeEntry;
import ic2.core.platform.recipes.misc.IngredientRegistry;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public interface IRecipeEntry extends Recipe<Container> {
    RecipeEntry getRecipeEntry();

    @Override
    default boolean matches(Container container, Level level) {
        return false;
    }

    @Override
    default ItemStack assemble(Container container) {
        return ItemStack.EMPTY;
    }

    @Override
    default boolean canCraftInDimensions(int i, int i1) {
        return false;
    }

    @Override
    default ItemStack getResultItem() {
        return ItemStack.EMPTY;
    }

    @Override
    default ResourceLocation getId() {
        return getRecipeEntry().getLocation();
    }

    @Override
    default boolean isSpecial() {
        return true;
    }

    IRecipeEntry setRecipeType(RecipeType<IRecipeEntry> recipeType);

    IRecipeEntry setSerializer(RecipeSerializer<IRecipeEntry> recipeSerializer);

    default JsonObject serializeRecipe() {
        JsonObject jsonobject = new JsonObject();
        jsonobject.addProperty("type", Registry.RECIPE_SERIALIZER.getKey(this.getSerializer()).toString());
        this.serializeRecipeData(jsonobject);
        return jsonobject;
    }

    default void serializeRecipeData(JsonObject json){
        if (getRecipeEntry().getInputs().length <= 1) {
            json.add("input", IngredientRegistry.INSTANCE.serializeInput(getRecipeEntry().getInputs()[0]));
        } else {
            JsonArray inputs = new JsonArray();
            for(IInput input : getRecipeEntry().getInputs()) {
                inputs.add(IngredientRegistry.INSTANCE.serializeInput(input));
            }
            json.add("input", inputs);
        }
        json.add("output", IngredientRegistry.INSTANCE.serializeOutput(getRecipeEntry().getOutput()));
    }
}
