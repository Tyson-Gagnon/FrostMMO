package Root.Events;

import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.events.BeatWildPixelmonEvent;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.entities.pixelmon.EntityPixelmon;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.spongepowered.api.entity.living.player.Player;

public class KillingEvents {

    @SubscribeEvent
    public void onWildPokemonDefeated(BeatWildPixelmonEvent e){
        Player player =(Player) e.player;
        EntityPixelmon entityPixelmon = (EntityPixelmon) e.wpp.getEntity();
        Pokemon Pokemon = entityPixelmon.getPokemonData();
    }

}
