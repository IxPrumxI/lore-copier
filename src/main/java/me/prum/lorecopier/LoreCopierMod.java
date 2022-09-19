package me.prum.lorecopier;

import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(modid = LoreCopierMod.MODID, version = LoreCopierMod.VERSION)
public class LoreCopierMod
{
    public static final String MODID = "lorecopier";
    public static final String VERSION = "1.0";
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        ClientCommandHandler.instance.registerCommand(new LoreCopyCommand());
        MinecraftForge.EVENT_BUS.register(new Listener());
    }
}
