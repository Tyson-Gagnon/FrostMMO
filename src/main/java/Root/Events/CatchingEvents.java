package Root.Events;

import Root.Commands.Stats;
import Root.FrostMMO;
import Root.Manager.ConfigurationManager;
import Root.Objects.PlayerLevels;
import Root.Objects.Storage;
import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.events.CaptureEvent;
import com.pixelmonmod.pixelmon.api.events.storage.StorageManagerLoadEvent;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.pokemon.PokemonSpec;
import com.pixelmonmod.pixelmon.api.spawning.AbstractSpawner;
import com.pixelmonmod.pixelmon.api.spawning.SpawnInfo;
import com.pixelmonmod.pixelmon.api.spawning.SpawnLocation;
import com.pixelmonmod.pixelmon.api.spawning.SpawnSet;
import com.pixelmonmod.pixelmon.api.spawning.archetypes.entities.pokemon.SpawnInfoPokemon;
import com.pixelmonmod.pixelmon.api.spawning.calculators.ICheckSpawns;
import com.pixelmonmod.pixelmon.api.spawning.calculators.ISelectionAlgorithm;
import com.pixelmonmod.pixelmon.entities.SpawnLocationType;
import com.pixelmonmod.pixelmon.entities.pixelmon.abilities.AbilityBase;
import com.pixelmonmod.pixelmon.enums.EnumSpecies;
import com.pixelmonmod.pixelmon.spawning.PixelmonSpawning;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import java.util.Random;


public class CatchingEvents {

    private EnumSpecies[] legendaryEnums = EnumSpecies.LEGENDARY_ENUMS;

    @SubscribeEvent
    public void onCatch(CaptureEvent.SuccessfulCapture e) {

        Player player = (Player) e.player;
        Pokemon pokemon = e.getPokemon().getPokemonData();

        int xpToGive = ConfigurationManager.getConfNode("XPValues", "Catching", "xp-per-catch").getInt();

        PlayerLevels playerLevels = new PlayerLevels(
                player.getPlayer().get(),
                Storage.getBreedExp(player.getUniqueId()),
                Storage.getCatchEXP(player.getUniqueId()),
                Storage.getBattleExp(player.getUniqueId()));

        if (!(playerLevels.getCathclevel() >= 100)) {

            for (EnumSpecies legendaryEnum : legendaryEnums) {
                if (legendaryEnum.equals(pokemon.getSpecies())) {
                    xpToGive = xpToGive + ConfigurationManager.getConfNode("XPValues", "Catching", "Legend-Bonus").getInt();
                }
            }

            Storage.setCatchingXp(player.getUniqueId(), Storage.getCatchEXP(player.getUniqueId()) + xpToGive);
            PlayerLevels playerLevels2 = new PlayerLevels(
                    player.getPlayer().get(),
                    Storage.getBreedExp(player.getUniqueId()),
                    Storage.getCatchEXP(player.getUniqueId()),
                    Storage.getBattleExp(player.getUniqueId()));

            if(playerLevels2.getCathclevel() > playerLevels.getCathclevel()){
                player.sendMessage(Text.of(TextColors.AQUA, "[FrostMMO] - ", TextColors.GRAY,
                        "You gained a level in the catching stat. You are now level " + playerLevels.getCathclevel()
                ));
            }
        }
    }

    @SubscribeEvent
    public void onCatch2(CaptureEvent.SuccessfulCapture e) {

        Player player = (Player) e.player;
        Pokemon pokemon = e.getPokemon().getPokemonData();

        PlayerLevels playerLevels = new PlayerLevels(
                player,
                Storage.getBreedExp(player.getUniqueId()),
                Storage.getCatchEXP(player.getUniqueId()),
                Storage.getBattleExp(player.getUniqueId()));

        int playerLevel = playerLevels.getCathclevel();

        double percentChanceShiny = (((ConfigurationManager.getConfNode("MultiplierValues", "Catching", "percent-to-shiny-at-max").getDouble() / 100)) * playerLevel) / 100;
        double percentChanceHA = (((ConfigurationManager.getConfNode("MultiplierValues", "Catching", "percent-to-HA-at-max").getDouble() / 100)) * playerLevel) / 100;

        Random rand = new Random();
        float chance = rand.nextFloat();

        if (chance < percentChanceShiny) {
            pokemon.setShiny(true);
            player.sendMessage(Text.of(TextColors.AQUA, "[FrostMMO] - ", TextColors.GRAY,
                    "Wow! Your pokemon turned into a shiny!"
            ));
        }

        rand = new Random();
        chance = rand.nextFloat();

        if (chance < percentChanceHA) {
            pokemon.setAbilitySlot(2);
            player.sendMessage(Text.of(TextColors.AQUA, "[FrostMMO] - ", TextColors.GRAY,
                    "Wow! Your pokemon learned its hidden ability!"
            ));
        }

    }


}
