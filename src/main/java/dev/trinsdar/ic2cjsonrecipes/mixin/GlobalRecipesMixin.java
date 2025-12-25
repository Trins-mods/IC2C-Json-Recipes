package dev.trinsdar.ic2cjsonrecipes.mixin;

import dev.trinsdar.ic2cjsonrecipes.DynamicDataPack;
import ic2.core.platform.recipes.misc.GlobalRecipes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GlobalRecipes.class)
public class GlobalRecipesMixin {
    @Inject(method = "reloadRecipes", at = @At("HEAD"), remap = false)
    private void ic2cjsonr$injectReload(CallbackInfo ci){
        DynamicDataPack.clearServer();
    }
}
