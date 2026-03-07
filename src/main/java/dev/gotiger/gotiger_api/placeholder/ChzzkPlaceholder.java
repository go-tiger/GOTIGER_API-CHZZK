package dev.gotiger.gotiger_api.placeholder;

import dev.gotiger.gotiger_api.GOTIGER_API;
import dev.gotiger.gotiger_api.listener.PlayerListener;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ChzzkPlaceholder extends PlaceholderExpansion {

    private final GOTIGER_API plugin;
    private final PlayerListener playerListener;

    public ChzzkPlaceholder(GOTIGER_API plugin, PlayerListener playerListener) {
        this.plugin = plugin;
        this.playerListener = playerListener;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "gotiger";
    }

    @Override
    public @NotNull String getAuthor() {
        return "GOTIGER";
    }

    @Override
    public @NotNull String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onPlaceholderRequest(Player player, @NotNull String params) {
        if (player == null) return "";

        // %gotiger_linked% - true / false
        if (params.equals("linked")) {
            return playerListener.isLinked(player.getUniqueId()) ? "true" : "false";
        }

        // %gotiger_linked_display% - 색상 포함 표시용 (연동됨 / 미연동)
        if (params.equals("linked_display")) {
            return playerListener.isLinked(player.getUniqueId()) ? "§a연동됨" : "§c미연동";
        }

        return null;
    }
}
