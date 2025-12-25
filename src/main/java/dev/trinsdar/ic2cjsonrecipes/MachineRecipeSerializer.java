package dev.trinsdar.ic2cjsonrecipes;

import com.google.gson.JsonObject;
import ic2.api.recipes.ingridients.inputs.IInput;
import ic2.api.recipes.ingridients.recipes.IRecipeOutput;
import ic2.api.recipes.registries.IMachineRecipeList;
import ic2.core.block.machines.recipes.IRecipeList;
import ic2.core.platform.recipes.misc.IngredientRegistry;
import ic2.core.utils.collection.CollectionUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MachineRecipeSerializer implements RecipeSerializer<IRecipeEntry> {
    private RecipeType<IRecipeEntry> recipeType;

    public MachineRecipeSerializer(RecipeType<IRecipeEntry> recipeType) {
        this.recipeType = recipeType;
    }

    @Override
    public IRecipeEntry fromJson(ResourceLocation resourceLocation, JsonObject jsonObject) {
        List<IInput> inputs = CollectionUtils.createList();
        IRecipeList.iterateObject(jsonObject.get("input"), (T) -> inputs.add(IngredientRegistry.INSTANCE.readInput(T)));
        if (inputs.isEmpty()) {
            throw new RuntimeException("Inputs are empty");
        } else {
            IRecipeOutput out = IngredientRegistry.INSTANCE.readOutput(GsonHelper.getAsJsonObject(jsonObject, "output"));
            if (out == null) {
                throw new RuntimeException("Output is Empty");
            } else {
                return ((IRecipeEntry) new IMachineRecipeList.RecipeEntry(resourceLocation, out, inputs.toArray(IInput[]::new))).setRecipeType(recipeType).setSerializer(this);
            }
        }
    }

    @Override
    public @Nullable IRecipeEntry fromNetwork(ResourceLocation resourceLocation, FriendlyByteBuf buffer) {
        ResourceLocation location = buffer.readResourceLocation();
        IRecipeOutput output = IngredientRegistry.INSTANCE.createOutput(buffer);
        IInput[] inputs = new IInput[buffer.readByte()];

        for(int j = 0; j < inputs.length; ++j) {
            inputs[j] = IngredientRegistry.INSTANCE.readInput(buffer);
        }

        return ((IRecipeEntry) new IMachineRecipeList.RecipeEntry(location, output, inputs)).setRecipeType(recipeType).setSerializer(this);
    }

    @Override
    public void toNetwork(FriendlyByteBuf buffer, IRecipeEntry entry) {
        buffer.writeResourceLocation(entry.getRecipeEntry().getLocation());
        IngredientRegistry.INSTANCE.writeRecipeOutput(entry.getRecipeEntry().getOutput(), buffer);
        buffer.writeByte(entry.getRecipeEntry().getInputs().length);

        for(IInput input : entry.getRecipeEntry().getInputs()) {
            IngredientRegistry.INSTANCE.writeInput(input, buffer);
        }
    }
}
