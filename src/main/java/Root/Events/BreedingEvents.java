package Root.Events;

import Root.Commands.Stats;
import Root.FrostMMO;
import Root.Manager.ConfigurationManager;
import Root.Objects.PlayerLevels;
import Root.Objects.Storage;
import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.events.BreedEvent;
import com.pixelmonmod.pixelmon.api.events.EggHatchEvent;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.config.PixelmonConfig;
import com.pixelmonmod.pixelmon.entities.pixelmon.stats.BaseStats;
import info.pixelmon.repack.ninja.leaping.configurate.commented.CommentedConfigurationNode;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.service.user.UserStorageService;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;


public class BreedingEvents {

    CommentedConfigurationNode node = ConfigurationManager.getConfNode();

    @SubscribeEvent
    public void onSucessfullBreed(BreedEvent.MakeEgg e) {

        UserStorageService userStorageService = Sponge.getServiceManager().provide(UserStorageService.class).get();
        User user = userStorageService.get(e.owner).get();


        PlayerLevels playerLevels = new PlayerLevels(
                user.getPlayer().get(),
                Storage.getBreedExp(user.getUniqueId()),
                Storage.getCatchEXP(user.getUniqueId()),
                Storage.getBattleExp(user.getUniqueId()));

        int breedLevel = playerLevels.getBreedlevel();

        if (!(breedLevel >= 100)) {

            int xp = node.getNode("XPValues", "Breeding", "xp-per-egg-make").getInt();



            Storage.setBreedXp(user.getUniqueId(), Storage.getBreedExp(user.getUniqueId()) + xp);

            if (user.getPlayer().isPresent()) {
                Player player = user.getPlayer().get();
                player.sendMessage(Text.of(TextColors.AQUA, "[FrostMMO] - ", TextColors.GRAY,
                        "You gained ", TextColors.YELLOW, xp, TextColors.GRAY, " xp in the", TextColors.YELLOW, " breeding ", TextColors.GRAY, "stat!"
                ));
                if (!FrostMMO.updateExemptions.contains(player.getUniqueId().toString())) {
                    Stats.updateScoreBoard(player);
                }
            }
        }
    }

    @SubscribeEvent
    public void onEggHatch(EggHatchEvent e) {

        Player player = (Player) e.pokemon.getOwnerPlayer();


        PlayerLevels playerLevels = new PlayerLevels(
                player,
                Storage.getBreedExp(player.getUniqueId()),
                Storage.getCatchEXP(player.getUniqueId()),
                Storage.getBattleExp(player.getUniqueId()));

        int breedLevel = playerLevels.getBreedlevel();

        if (!(breedLevel >= 100)) {


            Pokemon pokemon = e.pokemon;
            Pokemon copyOfPokemon = Pixelmon.pokemonFactory.create(pokemon.getSpecies());

            int stepsToHatch = copyOfPokemon.getBaseStats().eggCycles * 255;

            double xpGain = ConfigurationManager.getConfNode("XPValues", "Breeding", "xp-per-egg-hatch").getDouble();

            int xp = (int) (stepsToHatch * xpGain);

            Storage.setBreedXp(player.getUniqueId(), Storage.getBreedExp(player.getUniqueId()) + xp);

            player.sendMessage(Text.of(TextColors.AQUA, "[FrostMMO] - ", TextColors.GRAY,
                    "You gained ", TextColors.YELLOW, xp, TextColors.GRAY, " xp in the", TextColors.YELLOW, " breeding ", TextColors.GRAY, "stat!"
            ));
            if (!FrostMMO.updateExemptions.contains(player.getUniqueId().toString())) {
                Stats.updateScoreBoard(player);
            }
        }
    }


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
                int stepsToRemove = stepsToRemovePerLevel * breedLevel;

                pokemon.setEggSteps(stepsToRemove);

                player.sendMessage(Text.of(TextColors.AQUA, "[FrostMMO] - ", TextColors.GRAY,
                        "Based on your breeding level, ", TextColors.YELLOW, stepsToRemove, " steps", TextColors.GRAY,
                        " have been taken off of your egg!"
                ));

            }
        }
    }

}
