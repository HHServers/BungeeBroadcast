package io.github.hhservers.bungeebroadcast;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;

public class Util {

    private TextComponent getPrefix() {
        String prefix = ChatColor.translateAlternateColorCodes('&', BungeeBroadcast.plugin.getConfiguration().getString("settings.prefix"));
        return new TextComponent(prefix);
    }

    public void sendBroadcast(String messageString) {
        String message = ChatColor.translateAlternateColorCodes('&', messageString);
        TextComponent prefix = getPrefix();
        prefix.addExtra(" ");
        prefix.addExtra(message);
        for (ServerInfo server : ProxyServer.getInstance().getServersCopy().values()) {
            server.getPlayers().forEach((proxiedPlayer -> proxiedPlayer.sendMessage(prefix)));
        }
    }

    public void sendURLBroadcast(String messageString, String url) {

        String message = ChatColor.translateAlternateColorCodes('&', messageString);
        TextComponent msg = new TextComponent(message);
        TextComponent urlComponent = new TextComponent(url);
        urlComponent.setColor(ChatColor.AQUA);
        urlComponent.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, urlFixer(url)));
        TextComponent prefix = getPrefix();
        prefix.addExtra(" ");
        prefix.addExtra(msg);
        prefix.addExtra(" ");
        prefix.addExtra(urlComponent);
        for (ServerInfo server : ProxyServer.getInstance().getServersCopy().values()) {
            server.getPlayers().forEach((proxiedPlayer -> proxiedPlayer.sendMessage(prefix)));
        }
    }

    private String urlFixer(String url) {
        String fixedUrl = url;
        if (url.startsWith("www.")) {
            fixedUrl = "http://" + url;
        } else if (url.startsWith("http://") || url.startsWith("https://")) {
            fixedUrl = url;
        }
        return fixedUrl;
    }
}
