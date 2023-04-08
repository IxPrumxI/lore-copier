package me.prum.lorecopier;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import nl.kyllian.enums.Expire;
import nl.kyllian.models.Paste;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LoreProcesser {

    public static String processOne(ItemStack item) {
        JSONObject itemObject = new JSONObject();

        itemObject.put("nbt", item.serializeNBT().toString());
        itemObject.put("hovername", item.getHoverName().getString());
        itemObject.put("displayname", item.getDisplayName().getString());

        return upload(itemObject.toString());
    }

    public static String processInventory(Inventory inventory) {
        List<String> loreArray = new ArrayList<>();
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack itemStack = inventory.getItem(i);
            if(itemStack.getCount() == 0 || itemStack.isEmpty() || itemStack.getItem().equals(Items.AIR)) {
                continue;
            }
            JSONObject itemObject = new JSONObject();

            itemObject.put("nbt", itemStack.serializeNBT().toString());
            itemObject.put("hovername", itemStack.getHoverName().getString());
            itemObject.put("displayname", itemStack.getDisplayName().getString());
            itemObject.put("slot", i);

            loreArray.add(itemObject.toString());
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
