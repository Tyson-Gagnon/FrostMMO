package Root.Events;

import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.events.BreedEvent;
import com.pixelmonmod.pixelmon.api.events.EggHatchEvent;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.pokemon.PokemonFactory;
import com.pixelmonmod.pixelmon.api.pokemon.PokemonSpec;
import com.pixelmonmod.pixelmon.api.spawning.SpawnInfo;
import com.pixelmonmod.pixelmon.api.spawning.SpawnSet;
import com.pixelmonmod.pixelmon.api.spawning.archetypes.entities.pokemon.SpawnInfoPokemon;
import com.pixelmonmod.pixelmon.api.spawning.conditions.SpawnCondition;
import com.pixelmonmod.pixelmon.api.spawning.util.SetLoader;
import com.pixelmonmod.pixelmon.api.spawning.util.SpawnInfoTypeAdapter;
import com.pixelmonmod.pixelmon.entities.pixelmon.EntityPixelmon;
import com.pixelmonmod.pixelmon.enums.EnumSpecies;
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

        Pokemon pokemon = Pixelmon.pokemonFactory.create(EnumSpecies.getFromNameAnyCase("ree"));


        SpawnSet mf = SetLoader.getSet("Abra");
        float rarity = mf.spawnInfos.get(0).rarity;



    }

    @SubscribeEvent
    public void onEggHatch(EggHatchEvent e){
        Player player = (Player) e.pokemon.getOwnerPlayer();



    }

}
