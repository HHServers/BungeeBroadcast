package io.github.hhservers.bungeebroadcast;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;

public class Util {

    public void sendBroadcast(String messageString) {
        String message = ChatColor.translateAlternateColorCodes('&', messageString);
        for (ServerInfo server : ProxyServer.getInstance().getServersCopy().values()) {
            server.getPlayers().forEach((proxiedPlayer -> proxiedPlayer.sendMessage(TextComponent.fromLegacyText(message))));
        }
    }

    public void sendURLBroadcast(String messageString, String url) {
        String message = ChatColor.translateAlternateColorCodes('&', messageString);
        TextComponent urlComponent = new TextComponent(url);
        urlComponent.setColor(ChatColor.AQUA);
        urlComponent.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, urlFixer(url)));
        TextComponent msg = new TextComponent(message);
        msg.addExtra(" ");
        msg.addExtra(urlComponent);
        for (ServerInfo server : ProxyServer.getInstance().getServersCopy().values()) {
            server.getPlayers().forEach((proxiedPlayer -> proxiedPlayer.sendMessage(msg)));
        }
    }

    private String urlFixer(String url){
        String fixedUrl = url;
        if(url.startsWith("www.")){
            fixedUrl="http://"+url;
        } else if (url.startsWith("http://") || url.startsWith("https://")){
            fixedUrl=url;
        }
        return fixedUrl;
    }
}
