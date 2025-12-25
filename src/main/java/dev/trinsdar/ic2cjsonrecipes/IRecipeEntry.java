package dev.trinsdar.ic2cjsonrecipes;

import ic2.api.recipes.registries.IMachineRecipeList.RecipeEntry;
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
}
