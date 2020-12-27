package io.github.hhservers.bungeebroadcast;

import lombok.SneakyThrows;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class ReloadCommand extends Command {
    public ReloadCommand() {
        super("bbreload");
    }

    @SneakyThrows
    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender.hasPermission("bungeebroadcast.reload")) {
            BungeeBroadcast.plugin.reloadConfig();
            sender.sendMessage(new TextComponent("Config reloaded!"));
        }
    }
}
