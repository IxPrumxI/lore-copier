package me.prum.lorecopier;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.event.ClickEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

public class Listener {
    private boolean isLooping = false;
    public static KeyBinding key = new KeyBinding("Copy inventory lore", 221, "Lore Copier");
    public Listener(){
        ClientRegistry.registerKeyBinding(key);
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event){
        handleInventoryEvent(false);
    }

    @SubscribeEvent
    public void onGuiKeyInput(GuiScreenEvent.KeyboardInputEvent.Post event){
        handleInventoryEvent(Keyboard.isKeyDown(key.getKeyCode()));
    }

    private void handleInventoryEvent(boolean bypass) {
        if(isLooping) return;
        if(!key.isPressed() && !bypass) return;
        isLooping = true;
        String url = LoreProcesser.processInventory(Minecraft.getMinecraft().thePlayer.inventory);

        IChatComponent comp = new ChatComponentText("Click here to view!");
        ChatStyle style = new ChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, url));
        comp.setChatStyle(style);

        Minecraft.getMinecraft().thePlayer.addChatMessage(comp);
        isLooping = false;
    }
}
