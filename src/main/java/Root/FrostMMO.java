package Root;

import Root.Commands.Base;
import Root.Manager.SQLManager;
import akka.io.UdpConnected;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.config.ConfigDir;

import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.plugin.Plugin;

import me.itsy.pixelqueue.Managers.ConfigurationManager;
import org.spongepowered.api.plugin.PluginContainer;

import java.nio.file.Path;

@Plugin(
        name = "FrostMMO",
        version = "0.1",
        id = "frostmmoid",
        authors = {"itsTyxD","Future"}
)
public class FrostMMO {

    @Inject
    Game game;

    @Inject
    private static Logger logger;

    @Inject
    @ConfigDir(sharedRoot = false)
    private Path path;

    private static FrostMMO instace;
    public static FrostMMO getInstance(){return instace;}

    public static PluginContainer pluginContainer;

    @Listener
    public void onPreInit(GamePreInitializationEvent e){

        ConfigurationManager.setup(path);
        SQLManager.load();

    }

    @Listener
    public void onInit(GameInitializationEvent e){
        instace = this;
        registerCommands();
        registerListeners();
        pluginContainer= Sponge.getPluginManager().fromInstance(instace).get();
    }

    private void registerCommands() {

        CommandSpec baseCommand = CommandSpec.builder()
                .permission("frostmmo.base")
                .executor(new Base())
                .build();

        game.getCommandManager().register(this, baseCommand,"frostmmo");
    }

    private void registerListeners() {

    }

    public static Path getDir(){return instace.path;}


    public static org.slf4j.Logger getLogger() {
        return logger;
    }

}
