package yate;

import net.fabricmc.api.ModInitializer;
import net.minecraft.resources.ResourceLocation;

public class ExampleMod implements ModInitializer {

    @Override
    public void onInitialize() {
        System.out.println("ez");
        System.out.println(ResourceLocation.class);
    }

}