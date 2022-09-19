package me.prum.lorecopier;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LoreProcesser {

    public static String processOne(ItemStack item) {
        NBTTagCompound nbt = item.getTagCompound();
        try {
            return upload(nbt.toString());
        } catch (IOException e) {
            return "Error";
        }
    }

    public static String processInventory(IInventory inventory) {
        List<String> loreArray = new ArrayList<>();
        for (int i = 0; i < inventory.getSizeInventory(); i++) {
            ItemStack itemStack = inventory.getStackInSlot(i);
            if(itemStack == null || itemStack.stackSize == 0) {
                continue;
            }
            loreArray.add(itemStack.getTagCompound().toString());
        }

        String lore = "[" + String.join(",", loreArray) + "]";
        try {
            return upload(lore);
        } catch (IOException e) {
            return "Error";
        }
    }

    public static String upload(String jsonString) throws IOException {
        URL url = new URL("https://hastebin.com/documents");
        URLConnection connection = url.openConnection();

        connection.setRequestProperty("authority", "hastebin.com");
        connection.setRequestProperty("accept", "application/json, text/javascript, /; q=0.01");
        connection.setRequestProperty("x-requested-with", "XMLHttpRequest");
        connection.setRequestProperty("user-agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.92 Safari/537.36'");
        connection.setRequestProperty("content-type", "application/json; charset=UTF-8");
        connection.setDoOutput(true);

        OutputStream stream = connection.getOutputStream();
        stream.write(jsonString.getBytes());
        stream.flush();
        stream.close();

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String response = reader.lines().collect(Collectors.joining("\n"));

        return "https://hastebin.com/" + response.split("\"")[3];
    }
}
