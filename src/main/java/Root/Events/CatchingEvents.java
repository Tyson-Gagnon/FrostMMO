package Root.Events;

import com.pixelmonmod.pixelmon.api.events.CaptureEvent;
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
import com.pixelmonmod.pixelmon.spawning.PixelmonSpawning;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.spongepowered.api.entity.living.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CatchingEvents {

    @SubscribeEvent
    public void onCatch(CaptureEvent.SuccessfulCapture e){

        Player player = (Player)e.player;
        Pokemon pokemon = e.getPokemon().getPokemonData();

        SpawnLocationType spawnLocation = e.getPokemon().getSpawnLocation();

        AbstractSpawner abstractSpawner = PixelmonSpawning.coordinator.getSpawner(pokemon.getSpecies().name);
        List<String> list = new ArrayList<>();
        list.add(pokemon.getSpecies().name);

        SpawnInfo spawnInfo;
        SpawnSet spawnInfos = new SpawnSet();

        ISelectionAlgorithm iSelectionAlgorithm = abstractSpawner.selectionAlgorithm;
        //Map i = iSelectionAlgorithm.getPercentages(abstractSpawner,);


    }
}
