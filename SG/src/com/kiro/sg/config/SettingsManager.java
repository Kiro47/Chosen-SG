package com.kiro.sg.config;

import java.io.File;
import java.util.Set;

import com.kiro.sg.SGMain;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class SettingsManager {
	
	private static final SettingsManager
			configuration = new SettingsManager("config"),
			arenas = new SettingsManager("arenas"),
			signs = new SettingsManager("signs"),
			bcConfig = new SettingsManager("broadcastConfig")
	;
	
	public static SettingsManager getConfig() {
		return configuration;
	}
	
	public static SettingsManager getArenas() {
		return arenas;
	}
	
	public static SettingsManager getSigns() {
		return signs;
	}
	
	public static SettingsManager getBroadcastConfig() {
		return bcConfig;
	}
	

	private File file;
	private FileConfiguration config;
	
	
	private SettingsManager(String fileName) {
		if (!SGMain.getPlugin().getDataFolder().exists()) {
			SGMain.getPlugin().getDataFolder().mkdir();
		}
		
		file = new File(SGMain.getPlugin().getDataFolder(), fileName + ".yml");
		
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public <T> T get(String path) {
		return (T) config.get(path);
	}
	
	public Set<String> getKeys() {
		return config.getKeys(false);
	}
	
	public void set(String path, Object value) {
		config.set(path, value);
		save();
	}
	
	public boolean contains(String path) {
		return config.contains(path);
	}
	
	public ConfigurationSection createSection(String path) {
		ConfigurationSection section = config.createSection(path);
		save();
		return section;
	}
	
	public void save() {
		try {
			config.save(file);
		}
		
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void reloadFile() {
		config = YamlConfiguration.loadConfiguration(file);
	}
}