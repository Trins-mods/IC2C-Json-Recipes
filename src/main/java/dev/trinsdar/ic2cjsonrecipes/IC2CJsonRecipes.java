package dev.trinsdar.ic2cjsonrecipes;

import ic2.core.IC2;
import net.devtech.arrp.ARRP;
import net.devtech.arrp.api.RRPEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod(IC2CJsonRecipes.MODID)
public class IC2CJsonRecipes {
    public static final String MODID = "ic2cjsonrecipes";
    public IC2CJsonRecipes(){
        ARRP.EVENT_BUS.addListener(this::onResourcePackBeforeUser);
    }

    public void onResourcePackBeforeUser(RRPEvent.BetweenModsAndUser event){
        event.addPack(new DynamicDataPack(MODID + ":recipes", List.of()));
    }

}
