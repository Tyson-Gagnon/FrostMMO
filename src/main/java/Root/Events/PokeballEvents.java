package Root.Events;

import Root.Commands.Stats;
import Root.FrostMMO;
import Root.Manager.ConfigurationManager;
import Root.Objects.PlayerLevels;
import Root.Objects.Storage;
import com.pixelmonmod.pixelmon.api.events.AnvilEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.Item;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.filter.cause.Root;
import org.spongepowered.api.event.item.inventory.CraftItemEvent;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.common.item.inventory.util.ItemStackUtil;

import java.util.Random;

public class PokeballEvents {

    @SubscribeEvent
    public void onFInishAnvil(AnvilEvent.FinishedSmith e) {
        int xpToGainAnvil = ConfigurationManager.getConfNode("XPValues", "Pball", "xp-per-finished-anvil").getInt();
        Player player = (Player) e.player;

        net.minecraft.item.ItemStack itemStack;
        itemStack = new net.minecraft.item.ItemStack(e.item);
        ItemStack itemStack1 = ItemStackUtil.fromNative(itemStack);

        PlayerLevels playerLevels = new PlayerLevels(
                player,
                Storage.getBreedExp(player.getUniqueId()),
                Storage.getCatchEXP(player.getUniqueId()),
                Storage.getBattleExp(player.getUniqueId()),
                Storage.getPokeballExp(player.getUniqueId()));

        double doubleDrop = (((ConfigurationManager.getConfNode("MultiplierValues", "Pball", "double-drop-at-max").getDouble() / 100)) * playerLevels.getPballLevel()) / 100;
        double tripleDrop = (((ConfigurationManager.getConfNode("MultiplierValues", "Pball", "triple-drop-at-max").getDouble() / 100)) * playerLevels.getPballLevel()) / 100;


        if (playerLevels.getPballLevel() < 100) {
            Storage.setPokeballXp(e.player.getUniqueID(), Storage.getPokeballExp(e.player.getUniqueID()) + xpToGainAnvil);
            PlayerLevels playerLevels2 = new PlayerLevels(
                    player.getPlayer().get(),
                    Storage.getBreedExp(player.getUniqueId()),
                    Storage.getCatchEXP(player.getUniqueId()),
                    Storage.getBattleExp(player.getUniqueId()));

            if(playerLevels2.getPballLevel() > playerLevels.getPballLevel()){
                player.sendMessage(Text.of(TextColors.AQUA, "[FrostMMO] - ", TextColors.GRAY,
                        "You gained a level in the smithing stat. Yoo are now level " + playerLevels.getPballLevel()
                ));
            }
        }

        boolean flag = true;
        Random rand = new Random();
        float chance = rand.nextFloat();

        if (chance < tripleDrop) {
            itemStack1.setQuantity(2);
            Entity optItem = player.getLocation().getExtent().createEntity(EntityTypes.ITEM, player.getPosition());
            Item item = (Item) optItem;
            item.offer(Keys.REPRESENTED_ITEM, itemStack1.createSnapshot());
            player.getLocation().getExtent().spawnEntity(item);
            flag = false;
        }
        if (flag) {
            if (chance < doubleDrop) {
                itemStack1.setQuantity(1);
                Entity optItem = player.getLocation().getExtent().createEntity(EntityTypes.ITEM, player.getPosition());
                Item item = (Item) optItem;
                item.offer(Keys.REPRESENTED_ITEM, itemStack1.createSnapshot());
                player.getLocation().getExtent().spawnEntity(item);
            }
        }


    }

    @Listener
    public void onCraftEvent(CraftItemEvent.Craft e, @Root Player player) {

        if (e.getCrafted().createStack().getType().getId().contains("ball") && !e.getCrafted().createStack().getType().getId().contains("disc")) {
            int xpToGainAnvil = ConfigurationManager.getConfNode("XPValues", "Pball", "xp-per-finished-anvil").getInt();
            PlayerLevels playerLevels = new PlayerLevels(
                    player,
                    Storage.getBreedExp(player.getUniqueId()),
                    Storage.getCatchEXP(player.getUniqueId()),
                    Storage.getBattleExp(player.getUniqueId()),
                    Storage.getPokeballExp(player.getUniqueId()));

            if (playerLevels.getPballLevel() < 100) {
                xpToGainAnvil = xpToGainAnvil * e.getCrafted().getQuantity();
                Storage.setPokeballXp(player.getUniqueId(), Storage.getPokeballExp(player.getUniqueId()) + xpToGainAnvil);
                PlayerLevels playerLevels2 = new PlayerLevels(
                        player.getPlayer().get(),
                        Storage.getBreedExp(player.getUniqueId()),
                        Storage.getCatchEXP(player.getUniqueId()),
                        Storage.getBattleExp(player.getUniqueId()));

                if(playerLevels2.getPballLevel() > playerLevels.getPballLevel()){
                    player.sendMessage(Text.of(TextColors.AQUA, "[FrostMMO] - ", TextColors.GRAY,
                            "You gained a level in the smithing stat. Yoo are now level " + playerLevels.getPballLevel()
                    ));
                }
            }

        }

    }

}
