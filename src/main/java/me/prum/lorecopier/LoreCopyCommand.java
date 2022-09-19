package me.prum.lorecopier;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.event.ClickEvent;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.IChatComponent;

public class LoreCopyCommand extends CommandBase {

    @Override
    public String getCommandName() {
        return "lorecopy";
    }

    @Override
    public String getCommandUsage(ICommandSender iCommandSender) {
        return "";
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender p_canCommandSenderUseCommand_1_) {
        return true;
    }

    @Override
    public void processCommand(ICommandSender iCommandSender, String[] strings) throws CommandException {
        EntityPlayer player = (EntityPlayer) iCommandSender.getCommandSenderEntity();
        ItemStack itemStack = player.getHeldItem();
        if(itemStack == null || itemStack.stackSize == 0) {
            player.addChatMessage(new ChatComponentText("You have nothing selected!"));
            return;
        }

        String url = LoreProcesser.processOne(itemStack);

        IChatComponent comp = new ChatComponentText("Click here to view!");
        ChatStyle style = new ChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, url) {
            @Override
            public Action getAction() {
                //custom behavior
                return Action.OPEN_URL;
            }
        });
        comp.setChatStyle(style);

        player.addChatMessage(comp);
    }
}
