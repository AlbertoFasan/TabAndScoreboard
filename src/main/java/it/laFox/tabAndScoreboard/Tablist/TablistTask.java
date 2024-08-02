package it.laFox.tabAndScoreboard.Tablist;

import io.papermc.paper.text.PaperComponents;
import it.laFox.tabAndScoreboard.Settings.TabScoreboardSettings;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.audience.ForwardingAudience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

public class TablistTask implements Runnable {

    private static final TablistTask instance = new TablistTask();

    private final Map<UUID, Integer> headerPositions = new HashMap<>();
    private final Map<UUID, Integer> footerPositions = new HashMap<>();

    public TablistTask() {
    }

    @Override
    public void run() {

        List<List<String>> headerLines = TabScoreboardSettings.getInstance().getHeaderLines();
        List<List<String>> footerLines = TabScoreboardSettings.getInstance().getFooterLines();
        MiniMessage miniMessage = MiniMessage.miniMessage();

        for (Player player : Bukkit.getOnlinePlayers()) {
            int headerPosition = headerPositions.getOrDefault(player.getUniqueId(), 0);
            int footerPosition = footerPositions.getOrDefault(player.getUniqueId(), 0);

            for (int i = 0; i < headerLines.size(); i++) {
                if (headerPosition >= headerLines.get(i).size())
                    headerPosition = 0;

                player.sendPlayerListHeader(
                        Component.join(miniMessage.deserialize(replaceVariables(player, headerLines.get(i).get(headerPosition))),
                        Component.newline(),
                        miniMessage.deserialize(replaceVariables(player, headerLines.get(i).get(headerPosition))))
                );
            }


            for (int i = 0; i < headerLines.size(); i++) {
                if (footerPosition >= footerLines.get(i).size())
                    footerPosition = 0;

                player.sendPlayerListFooter(
                        miniMessage.deserialize(replaceVariables(player, footerLines.get(i).get(footerPosition)))
                );

            }


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