package me.prum.lorecopier;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;

@Mod(LoreCopierMod.MODID)
public class LoreCopierMod
{
    public static final String MODID = "lorecopier";

    public LoreCopierMod(){
        MinecraftForge.EVENT_BUS.register(new Listener());
    }
}
