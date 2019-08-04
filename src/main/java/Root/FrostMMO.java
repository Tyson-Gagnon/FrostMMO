package Root;

import Root.Commands.*;
import Root.Commands.CommandElemets.TypesCommandElemts;
import Root.Events.BreedingEvents;
import Root.Events.CatchingEvents;
import Root.Events.KillingEvents;
import Root.Manager.ConfigurationManager;
import Root.Manager.SQLManager;
import com.google.inject.Inject;
import com.pixelmonmod.pixelmon.Pixelmon;
import org.slf4j.Logger;
import org.spongepowered.api.*;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.config.ConfigDir;

import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.text.Text;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Plugin(
        name = "FrostMMO",
        version = "0.1",
        id = "frostmmoid",
        description = "A MMO plugin for pixelmon made for the PermaFrostMC Server! :)",
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

    private static FrostMMO instance;
    public static FrostMMO getInstance(){return instance;}

    public static PluginContainer pluginContainer;

    public static List<String> updateExemptions = new ArrayList<>();

    @Listener
    public void onPreInit(GamePreInitializationEvent e){
        instance = this;
        ConfigurationManager.setup(path);
        SQLManager.load();

    }

    @Listener
    public void onInit(GameInitializationEvent e){

        registerCommands();
        registerListeners();
        pluginContainer= Sponge.getPluginManager().fromInstance(instance).get();

        //f = 500 *((int)Math.pow(i,2))-(500 * i);
        //(int)(25+ Math.sqrt(5*(125+exp)))/50;
    }

    private void registerCommands() {


        CommandSpec toggleMessages = CommandSpec.builder()
                .permission("frostmmo.toggle")
                .executor(new ToggleUpdates())
                .build();

        CommandSpec addExp = CommandSpec.builder()
                .permission("frostmmo.addExp")
                .arguments(GenericArguments.player(Text.of("target")),new TypesCommandElemts(Text.of("stat")),GenericArguments.integer(Text.of("xp")))
                .executor(new AddExp())
                .build();

        CommandSpec showStats = CommandSpec.builder()
                .permission("frostmmo.stats")
                .executor(new Stats())
                .build();

        CommandSpec hideStats = CommandSpec.builder()
                .permission("frostmmo.stats")
                .executor(new HideStats())
                .build();

        CommandSpec baseCommand = CommandSpec.builder()
                .permission("frostmmo.base")
                .executor(new Base())
                .child(addExp, "addxp")
                .child(showStats, "stats")
                .child(hideStats, "hide", "hidestats")
                .child(toggleMessages, "togglestats")
                .build();

        game.getCommandManager().register(this, baseCommand,"frostmmo");
    }

    private void registerListeners() {
        Pixelmon.EVENT_BUS.register(new BreedingEvents());
        Pixelmon.EVENT_BUS.register(new CatchingEvents());
        Pixelmon.EVENT_BUS.register(new KillingEvents());

    }

    public static Path getDir(){return instance.path;}


    public static org.slf4j.Logger getLogger() {
        return logger;
    }

}
