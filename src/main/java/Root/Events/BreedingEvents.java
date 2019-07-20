package Root.Events;

import Root.Objects.Storage;
import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.events.BreedEvent;
import com.pixelmonmod.pixelmon.api.events.EggHatchEvent;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import info.pixelmon.repack.ninja.leaping.configurate.commented.CommentedConfigurationNode;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.service.user.UserStorageService;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;


public class BreedingEvents {

    CommentedConfigurationNode node = me.itsy.pixelqueue.Managers.ConfigurationManager.getConfNode();

    @SubscribeEvent
    public void onSucessfullBreed(BreedEvent.MakeEgg e){

        Sponge.getServer().getBroadcastChannel().send(Text.of("Make Egg event found!"));

        int xp = node.getNode("XPValues","Breeding","xp-per-egg-make").getInt();

        UserStorageService userStorageService = Sponge.getServiceManager().provide(UserStorageService.class).get();
        User user = userStorageService.get(e.owner).get();

        Storage.setBreedXp(user.getUniqueId(),Storage.getBreedExp(user.getUniqueId()) + xp);

        if(user.getPlayer().isPresent()){
            Player player = user.getPlayer().get();
            player.sendMessage(Text.of(TextColors.AQUA,"[FrostMMO] - ",TextColors.GRAY,
                    "You gained ",TextColors.YELLOW,xp,TextColors.GRAY," xp in the",TextColors.YELLOW, " breeding ",TextColors.GRAY, "stat!"
                    ));
        }

    }

    @SubscribeEvent
    public void onEggHatch(EggHatchEvent e){
        Player player = (Player) e.pokemon.getOwnerPlayer();


        Pokemon pokemon = e.pokemon;
        Pokemon copyOfPokemon = Pixelmon.pokemonFactory.create(pokemon.getSpecies());

        int stepsToHatch = copyOfPokemon.makeEgg().getEggSteps();

        int xp = (int) (stepsToHatch*0.005);


    }

}
