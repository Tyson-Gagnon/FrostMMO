package Root.Manager;

import Root.FrostMMO;
import info.pixelmon.repack.ninja.leaping.configurate.ConfigurationNode;
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
            configurationNode.getNode("XPValues","Breeding","xp-per-egg-make").setValue(50);

            configurationNode.getNode("XPValues","Breeding","xp-per-egg-hatch").setComment("change this value to change the xp/1000steps 0.005 == 5xp, 0.01 == 10 xp");
            configurationNode.getNode("XPValues","Breeding","xp-per-egg-hatch").setValue(0.005);

            configurationNode.getNode("XPValues", "Battling", "xp-per-level").setValue(5);
            configurationNode.getNode("XPValues", "Battling", "legend-xp-bonus").setValue(100);

            configurationNode.getNode("XPValues","Catching","xp-per-catch").setValue(500);
            ////////////////////////////////////////////////////////////

            configurationNode.getNode("MultiplierValues", "Breeding", "steps-removed-per-level").setValue(5);

            configurationNode.getNode("MultiplierValues", "Battling", "xp-multiplier-per-level").setComment("Multiplier per level. 0.03 per level = 3x at level 100. 0.04 would be 4x ect..");
            configurationNode.getNode("MultiplierValues", "Battling", "xp-multiplier-per-level").setValue(0.03);



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