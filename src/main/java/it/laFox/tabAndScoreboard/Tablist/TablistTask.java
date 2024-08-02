package it.laFox.tabAndScoreboard.Tablist;

import it.laFox.tabAndScoreboard.Settings.TabScoreboardSettings;
import net.kyori.adventure.audience.ForwardingAudience;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class TablistTask implements Runnable {

    private static final TablistTask instance = new TablistTask();

    private final Map<UUID, Integer> headerPositions = new HashMap<>();
    private final Map<UUID, Integer> footerPositions = new HashMap<>();

    public TablistTask() {
    }

    @Override
    public void run() {
        ForwardingAudience audience = Bukkit.getServer();


        List<String> headerLines = TabScoreboardSettings.getInstance().getHeaderLines();
        List<String> footerLines = TabScoreboardSettings.getInstance().getFooterLines();
        MiniMessage miniMessage = MiniMessage.miniMessage();

        for (Player player : Bukkit.getOnlinePlayers()) {
            int headerPosition = headerPositions.getOrDefault(player.getUniqueId(), 0);
            int footerPosition = footerPositions.getOrDefault(player.getUniqueId(), 0);

            if (headerPosition >= headerLines.size())
                headerPosition = 0;

            if (footerPosition >= footerLines.size())
                footerPosition = 0;

            player.sendPlayerListHeader(
                    miniMessage.deserialize(replaceVariables(player, headerLines.get(headerPosition))));

            player.sendPlayerListFooter(
                    miniMessage.deserialize(replaceVariables(player, footerLines.get(footerPosition))));

            headerPositions.put(player.getUniqueId(), headerPosition + 1);
            footerPositions.put(player.getUniqueId(), footerPosition + 1);
        }
    }

    private String replaceVariables(Player player, String message) {
        return message.replace("{player}", player.getName());
    }

    public static TablistTask getInstance() {
        return instance;
    }
}