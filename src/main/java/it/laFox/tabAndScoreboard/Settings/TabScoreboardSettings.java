package it.laFox.tabAndScoreboard.Settings;

import it.laFox.tabAndScoreboard.TabAndScoreboard;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.yaml.snakeyaml.constructor.SafeConstructor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class TabScoreboardSettings {

	private final static TabScoreboardSettings instance = new TabScoreboardSettings();

	private File file;
	private FileConfiguration config;

	private EntityType explodingType;
	private List<String> headerLines;
	private List<String> footerLines;

	private TabScoreboardSettings() {
	}


	public void load() {
		file = new File(TabAndScoreboard.getInstance().getDataFolder(), "settings.yml");

		if (!file.exists()) {

			file.getParentFile().mkdirs();
			TabAndScoreboard.getInstance().saveResource("settings.yml", false);
		}

		config = new YamlConfiguration();

		try {

			config.load(file);

		} catch (IOException | InvalidConfigurationException e) {

			e.printStackTrace();

		}

		headerLines = config.getStringList("tablist.header");
		footerLines = config.getStringList("tablist.footer");
	}

	public void save() {
		try {
			config.save(file);

		} catch (Exception ex) {
			System.out.println("Error saving settings.yml");
		}
	}

	public void set(String path, Object value) {
		config.set(path, value);

		save();
	}

	public EntityType getExplodingType() {
		return explodingType;
	}

	public List<String> getHeaderLines() {
		return headerLines;
	}

	public List<String> getFooterLines() {
		return footerLines;
	}

	public static TabScoreboardSettings getInstance() {
		return instance;
	}
}
