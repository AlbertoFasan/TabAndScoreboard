package it.laFox.tabAndScoreboard;

import com.google.common.util.concurrent.AbstractScheduledService;
import it.laFox.tabAndScoreboard.Tablist.TablistTask;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Async;

public final class TabAndScoreboard extends JavaPlugin {

    @Override
    public void onEnable() {

        Bukkit.getScheduler().runTaskTimer(this, new TablistTask(), 20L, 20L);

    }

    public static TabAndScoreboard getInstance() {
        return getPlugin(TabAndScoreboard.class);
    }
}
