package dev.trinsdar.ic2cjsonrecipes.mixin;

import dev.trinsdar.ic2cjsonrecipes.IRecipeEntry;
import dev.trinsdar.ic2cjsonrecipes.MachineRecipeSerializer;
import ic2.api.recipes.registries.IMachineRecipeList;
import ic2.core.block.machines.recipes.MachineRecipeList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.fml.ModLoadingContext;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(MachineRecipeList.class)
public class MachineRecipeListMixin {
    @Mutable
    @Final
    @Unique
    private RecipeType<IRecipeEntry> recipeType;
    @Mutable
    @Final
    @Unique
    private RecipeSerializer<IRecipeEntry> recipeSerializer;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void ic2cjsonr$injectInit(String folder, Consumer<IMachineRecipeList> reloader, CallbackInfo ci){
        ResourceLocation id = new ResourceLocation(ModLoadingContext.get().getActiveNamespace(), folder);
        recipeType = RecipeType.simple(id);
        recipeSerializer = new MachineRecipeSerializer(recipeType);
        //ForgeRegistries.RECIPE_TYPES.register(id, recipeType);
    }
}
