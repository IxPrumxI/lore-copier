package me.prum.lorecopier;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import nl.kyllian.enums.Expire;
import nl.kyllian.models.Paste;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LoreProcesser {

    public static String processOne(ItemStack item) {
        CompoundTag nbt = item.serializeNBT();
        return upload(nbt.toString());
    }

    public static String processInventory(Inventory inventory) {
        List<String> loreArray = new ArrayList<>();
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack itemStack = inventory.getItem(i);
            if(itemStack.getCount() == 0 || itemStack.isEmpty() || itemStack.getItem().equals(Items.AIR)) {
                continue;
            }
            loreArray.add(itemStack.serializeNBT().toString());
        }

        String lore = "[" + String.join(",", loreArray) + "]";
        return upload(lore);
    }

    public static String upload(String jsonString) {
        Paste paste = new Paste("https://privatebin.net")
                .setMessage(jsonString)
                .setExpire(Expire.ONE_DAY)
                .encrypt();

        try {
            return paste.send();
        } catch (IOException e) {
            System.out.println("Something went wrong.");
            e.printStackTrace();
            return "Error";
        }
    }
}
