package dev.gotiger.gotiger_api.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class OnEventDonation extends Event {
    private static final HandlerList handlers = new HandlerList();
    private final String donatorNickname;
    private final String donationText;
    private final int donationAmount;
    private final Player targetPlayer;

    public OnEventDonation(String donatorNickname, String donationText, int donationAmount, Player targetPlayer) {
        super(true);
        this.donatorNickname = donatorNickname;
        this.donationText = donationText;
        this.donationAmount = donationAmount;
        this.targetPlayer = targetPlayer;
    }

    public Player getTargetPlayer() { return targetPlayer; }
    public String getDonatorNickname() { return donatorNickname; }
    public String getDonationText() { return donationText; }
    public int getDonationAmount() { return donationAmount; }

    @Override
    public HandlerList getHandlers() { return handlers; }
    public static HandlerList getHandlerList() { return handlers; }
}
