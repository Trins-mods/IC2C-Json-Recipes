package dev.trinsdar.ic2cjsonrecipes.mixin;

import dev.trinsdar.ic2cjsonrecipes.IRecipeEntry;
import ic2.api.recipes.registries.IMachineRecipeList.RecipeEntry;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(RecipeEntry.class)
public class RecipeEntryMixin implements IRecipeEntry {
    @Unique
    RecipeType<IRecipeEntry> recipeType;
    @Unique
    RecipeSerializer<IRecipeEntry> recipeSerializer;

    @Override
    public RecipeEntry getRecipeEntry() {
        return (RecipeEntry) (Object) this;
    }

    @Override
    public IRecipeEntry setRecipeType(RecipeType<IRecipeEntry> recipeType) {
        this.recipeType = recipeType;
        return this;
    }

    @Override
    public IRecipeEntry setSerializer(RecipeSerializer<IRecipeEntry> recipeSerializer) {
        this.recipeSerializer = recipeSerializer;
        return this;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return recipeSerializer;
    }

    @Override
    public RecipeType<?> getType() {
        return recipeType;
    }
}
