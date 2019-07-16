package me.itsy.pixelqueue.Managers;

import Root.FrostMMO;
import info.pixelmon.repack.ninja.leaping.configurate.commented.CommentedConfigurationNode;
import info.pixelmon.repack.ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import info.pixelmon.repack.ninja.leaping.configurate.loader.ConfigurationLoader;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ConfigurationManager {

    private static Path dir, config, storage, censor, lang, alliances;
    private static ConfigurationLoader<CommentedConfigurationNode> configurationLoader;
    private static CommentedConfigurationNode configurationNode;
    private static final String[] FILES = {"config.conf"};

    public static void setup(Path folder) {
        dir = folder;
        config = dir.resolve(FILES[0]);
        load();
    }

    public static void load() {
        try {
            if(!Files.exists(dir))
                Files.createDirectory(dir);

            configurationLoader = HoconConfigurationLoader.builder().setPath(config).build();
            configurationNode = configurationLoader.load();

            configurationNode.getNode("version").setValue(1);
            save();

        } catch(IOException e) {
            FrostMMO.getInstance().getLogger().error("Error loading up PokeTeams Configuration"); e.printStackTrace();
        }
    }

    public static void save() {
        try {
            configurationLoader.save(configurationNode);

        } catch (IOException e) {
            FrostMMO.getInstance().getLogger().error("Error saving PokeTeams Configuration"); e.printStackTrace();
        }
    }


    public static CommentedConfigurationNode getConfNode(Object... node) {
        return configurationNode.getNode(node);
    }

}