package Root.Events;

import Root.Commands.Stats;
import Root.FrostMMO;
import Root.Manager.ConfigurationManager;
import Root.Objects.PlayerLevels;
import Root.Objects.Storage;
import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.enums.ExperienceGainType;
import com.pixelmonmod.pixelmon.api.events.BeatWildPixelmonEvent;
import com.pixelmonmod.pixelmon.api.events.ExperienceGainEvent;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.entities.pixelmon.EntityPixelmon;
import com.pixelmonmod.pixelmon.entities.pixelmon.stats.links.PokemonLink;
import com.pixelmonmod.pixelmon.enums.EnumSpecies;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.service.user.UserStorageService;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import java.util.List;

public class KillingEvents {

    private EnumSpecies[] legendaryEnums = EnumSpecies.LEGENDARY_ENUMS;

    @SubscribeEvent
    public void onWildPokemonDefeated(BeatWildPixelmonEvent e) {
        Player player = (Player) e.player;
        EntityPixelmon entityPixelmon = (EntityPixelmon) e.wpp.getEntity();
        Pokemon Pokemon = entityPixelmon.getPokemonData();

        PlayerLevels playerLevels = new PlayerLevels(
                player,
                Storage.getBreedExp(player.getUniqueId()),
                Storage.getCatchEXP(player.getUniqueId()),
                Storage.getBattleExp(player.getUniqueId()));

        if (!(playerLevels.getKilllevel() >= 100)) {

            int xpPerLevel = ConfigurationManager.getConfNode("XPValues", "Battling", "xp-per-level").getInt();

            int xpGain = xpPerLevel * Pokemon.getLevel();

            for (EnumSpecies legendaryEnum : legendaryEnums) {
                if (legendaryEnum.equals(Pokemon.getSpecies())) {
                    if(!entityPixelmon.isBossPokemon()){
                        xpGain = xpGain + ConfigurationManager.getConfNode("XPValues", "Battling", "legend-xp-bonus").getInt();
                    }
                }
            }

            Storage.setBattleXp(player.getUniqueId(), Storage.getBattleExp(player.getUniqueId()) + xpGain);

            PlayerLevels playerLevels2 = new PlayerLevels(
                    player.getPlayer().get(),
                    Storage.getBreedExp(player.getUniqueId()),
                    Storage.getCatchEXP(player.getUniqueId()),
                    Storage.getBattleExp(player.getUniqueId()));

            if(playerLevels2.getKilllevel() > playerLevels.getKilllevel()){
                player.sendMessage(Text.of(TextColors.AQUA, "[FrostMMO] - ", TextColors.GRAY,
                        "You gained a level in the battling stat. You are now level " + playerLevels.getKilllevel()
                ));
            }
        }
    }


    @SubscribeEvent
    public void onXPGainEvent(ExperienceGainEvent e) {
        if (e.getType().equals(ExperienceGainType.BATTLE)) {
            Pokemon pokemon = e.pokemon.getPokemon();
            Player player = (Player) pokemon.getOwnerPlayer();

            PlayerLevels playerLevels = new PlayerLevels(
                    player,
                    Storage.getBreedExp(player.getUniqueId()),
                    Storage.getCatchEXP(player.getUniqueId()),
                    Storage.getBattleExp(player.getUniqueId()));

            int battleLevel = playerLevels.getKilllevel();
            double xpMultiplier = ConfigurationManager.getConfNode("MultiplierValues", "Battling", "xp-multiplier-per-level").getDouble() * battleLevel;
            int originalXp = e.getExperience();
            int newXp;

            if (xpMultiplier < 1) {
                newXp = (int) (originalXp + (xpMultiplier * originalXp));
            } else {
                newXp = (int) (originalXp * xpMultiplier);
            }

            e.setExperience(newXp);

            player.sendMessage(Text.of(TextColors.AQUA, "[FrostMMO] - ", TextColors.GRAY,
                    "Based on your battling level, you gained ", TextColors.YELLOW, (newXp - originalXp), " extra experience ", TextColors.GRAY, "points!"
            ));

        }
    }
}
