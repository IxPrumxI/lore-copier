package me.prum.lorecopier;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

public class Listener {
    @Mod.EventBusSubscriber(modid = LoreCopierMod.MODID, value = Dist.CLIENT)
    public static class ForgeEventBus {
        public static KeyMapping inventory = new KeyMapping("Copy inventory", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_HOME, "Lore Copier");
        public static KeyMapping one = new KeyMapping("Copy one", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_END, "Lore Copier");

        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event){
            if(inventory.consumeClick()) {
                if(Minecraft.getInstance().player == null) return;
                String url = LoreProcesser.processInventory(Minecraft.getInstance().player.getInventory());

                if(url.equals("Error")) {
                    Minecraft.getInstance().player.sendSystemMessage(Component.literal("Something went wrong."));
                } else {
                    Minecraft.getInstance().player.sendSystemMessage(Component.literal("Click here to view!").setStyle(
                            Style.EMPTY.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, url))
                    ));
                }
            } else if (one.consumeClick()) {
                if(Minecraft.getInstance().player == null) return;
                String url = LoreProcesser.processOne(Minecraft.getInstance().player.getMainHandItem());

                if(url.equals("Error")) {
                    Minecraft.getInstance().player.sendSystemMessage(Component.literal("Something went wrong."));
                } else {
                    Minecraft.getInstance().player.sendSystemMessage(Component.literal("Click here to view!").setStyle(
                            Style.EMPTY.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, url))
                    ));
                }
            }
        }
    }

    @Mod.EventBusSubscriber(modid = LoreCopierMod.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientEventBus {
        @SubscribeEvent
        public static void onKeyRegistry(RegisterKeyMappingsEvent event){
            event.register(ForgeEventBus.inventory);
            event.register(ForgeEventBus.one);
        }
    }
}
