package Root.Events;

import com.pixelmonmod.pixelmon.api.events.CaptureEvent;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.spongepowered.api.entity.living.player.Player;

public class CatchingEvents {

    @SubscribeEvent
    public void onCatch(CaptureEvent.SuccessfulCapture e){

        Player player = (Player)e.player;
        Pokemon pokemon = e.getPokemon().getPokemonData();



    }
}
