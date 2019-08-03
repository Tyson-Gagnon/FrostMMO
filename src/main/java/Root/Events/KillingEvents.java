package Root.Events;

import Root.Manager.ConfigurationManager;
import Root.Objects.Storage;
import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.events.BeatWildPixelmonEvent;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.entities.pixelmon.EntityPixelmon;
import com.pixelmonmod.pixelmon.enums.EnumSpecies;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import java.util.List;

public class KillingEvents {

    EnumSpecies[] legendaryEnums = EnumSpecies.LEGENDARY_ENUMS;

    @SubscribeEvent
    public void onWildPokemonDefeated(BeatWildPixelmonEvent e){
        Player player =(Player) e.player;
        EntityPixelmon entityPixelmon = (EntityPixelmon) e.wpp.getEntity();
        Pokemon Pokemon = entityPixelmon.getPokemonData();

        int xpPerLevel = ConfigurationManager.getConfNode("XPValues", "Battling", "xp-per-level").getInt();

        int xpGain = xpPerLevel * Pokemon.getLevel();

        for (EnumSpecies legendaryEnum : legendaryEnums) {
            if (legendaryEnum.equals(Pokemon.getSpecies())) {
                xpGain = xpGain + ConfigurationManager.getConfNode("XPValues", "Battling", "legend-xp-bonus").getInt();
            }
        }

        Storage.setBattleXp(player.getUniqueId(), Storage.getBattleExp(player.getUniqueId()) + xpGain);

        player.sendMessage(Text.of(TextColors.AQUA, "[FrostMMO] - ", TextColors.GRAY,
                "You gained ", TextColors.YELLOW, xpGain, TextColors.GRAY, " xp in the", TextColors.YELLOW, " battling ", TextColors.GRAY, "stat!"
        ));

    }


}
