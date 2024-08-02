package it.laFox.tabAndScoreboard;

import it.laFox.tabAndScoreboard.Settings.TabScoreboardSettings;
import it.laFox.tabAndScoreboard.Tablist.TablistTask;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class TabAndScoreboard extends JavaPlugin {

    @Override
    public void onEnable() {

        TabScoreboardSettings.getInstance().load();

        Bukkit.getScheduler().runTaskTimer(this, new TablistTask(), 20L, 20L);

    }

    public static TabAndScoreboard getInstance() {
        return getPlugin(TabAndScoreboard.class);
    }
}
