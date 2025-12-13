package dev.gotiger.gotiger_api.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class OnEventChat extends Event {
    private static final HandlerList handlers = new HandlerList();
    private final String nickname;
    private final String content;

    public OnEventChat(String nickname, String content) {
        super(true);
        this.nickname = nickname;
        this.content = content;
    }

    public String getNickname() { return nickname; }
    public String getContent() { return content; }

    @Override
    public HandlerList getHandlers() { return handlers; }
    public static HandlerList getHandlerList() { return handlers; }
}
