package Root.Events;

import com.pixelmonmod.pixelmon.api.events.BreedEvent;
import com.pixelmonmod.pixelmon.api.events.EggHatchEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.service.user.UserStorageService;

public class BreedingEvents {

    @SubscribeEvent
    public void onSucessfullBreed(BreedEvent.MakeEgg e){

        UserStorageService userStorageService = Sponge.getServiceManager().provide(UserStorageService.class).get();
        User user = userStorageService.get(e.owner).get();


    }

    @SubscribeEvent
    public void onEggHatch(EggHatchEvent e){
        Player player = (Player) e.pokemon.getOwnerPlayer();



    }

}
