package Root.Events;

import Root.Manager.ConfigurationManager;
import Root.Objects.Storage;
import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.events.BreedEvent;
import com.pixelmonmod.pixelmon.api.events.PixelmonReceivedEvent;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.config.PixelmonConfig;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class BreedSetting {

    @SubscribeEvent
    public void onEggGain(BreedEvent.CollectEgg event) {


        Player player = Sponge.getServer().getPlayer(event.owner).get();
        Pokemon pokemon = event.getEgg();

        if (pokemon.isEgg()) {
            final int steps = (pokemon.getEggCycles()) * PixelmonConfig.stepsPerEggCycle - pokemon.getEggSteps();



            int defaultSteps = (Pixelmon.pokemonFactory.create(pokemon.getSpecies()).makeEgg().getBaseStats().eggCycles * PixelmonConfig.stepsPerEggCycle);



            if (steps == defaultSteps) {
                int breedLevel = (int) (25 + Math.sqrt(5 * (125 + Storage.getBreedExp(player.getUniqueId())))) / 50;
                int stepsToRemovePerLevel = ConfigurationManager.getConfNode("MultiplierValues", "breeding", "steps-removed-per-level").getInt();
                int stepsToRemove = stepsToRemovePerLevel*breedLevel;

                pokemon.setEggSteps(stepsToRemove);

                player.sendMessage(Text.of(TextColors.AQUA, "[FrostMMO] - ", TextColors.GRAY,
                        "Based on your breeding level, ",TextColors.YELLOW,stepsToRemove," steps",TextColors.GRAY,
                        " have been taken off of your egg!"
                ));

            }
        }
    }
}
