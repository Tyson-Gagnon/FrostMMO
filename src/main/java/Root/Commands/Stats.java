package Root.Commands;

import Root.FrostMMO;
import Root.Objects.GUIS;
import Root.Objects.PlayerLevels;
import Root.Objects.Storage;
import akka.io.Tcp;
import com.mcsimonflash.sponge.teslalibs.inventory.Action;
import com.mcsimonflash.sponge.teslalibs.inventory.Element;
import com.mcsimonflash.sponge.teslalibs.inventory.Layout;
import com.mcsimonflash.sponge.teslalibs.inventory.View;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.type.DyeColors;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.InventoryArchetypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.property.InventoryDimension;
import org.spongepowered.api.item.inventory.property.InventoryTitle;
import org.spongepowered.api.scoreboard.Scoreboard;
import org.spongepowered.api.scoreboard.critieria.Criteria;
import org.spongepowered.api.scoreboard.displayslot.DisplaySlots;
import org.spongepowered.api.scoreboard.objective.Objective;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;
import org.spongepowered.api.world.Location;
import scala.xml.Elem;

import javax.jws.soap.SOAPBinding;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;


public class Stats implements CommandExecutor {
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if (!(src instanceof Player)) {
            return CommandResult.success();
        }
        Player player = (Player) src;



        if (args.hasAny("target")) {


            User user = args.<User>getOne("target").get();

            Consumer<Action.Click> openCatching = a ->{
                GUIS.openCatchGUI(player, user);
            };
            Consumer<Action.Click> openBreeding = a ->{
                GUIS.openBreedGUI(player, user);
            };
            Consumer<Action.Click> openBattling = a ->{
                GUIS.openBattleGUI(player, user);
            };
            Consumer<Action.Click> openSmith = a ->{
                GUIS.openSmithingGUI(player, user);
            };


            PlayerLevels playerLevels = new PlayerLevels(user,
                    Storage.getBreedExp(user.getUniqueId()),
                    Storage.getCatchEXP(user.getUniqueId()),
                    Storage.getBattleExp(user.getUniqueId()),
                    Storage.getPokeballExp(user.getUniqueId()));

            Layout.Builder layout = Layout.builder().dimension(InventoryDimension.of(9, 3));
            View view = View.builder().archetype(InventoryArchetypes.CHEST).property(InventoryTitle.of(Text.of(TextColors.AQUA, "FrostMMO Stats"))).build(FrostMMO.pluginContainer);

            List<Text> lore = new ArrayList<>();
            lore.add(Text.of(TextColors.AQUA, TextStyles.BOLD, "LEVEL " + playerLevels.getCathclevel()));
            lore.add(Text.of(TextColors.GREEN, "Info:"));
            lore.add(Text.of(TextColors.WHITE, " This stat allows pokemon that you are catching\n have a chance to turn into a shiny,\n or gain is hidden ability upon capture.\n The probability of this" +
                    "happening scales with levels."));
            Element catchingElement = Element.of(getItemStackWithLore(Sponge.getGame().getRegistry().getType(ItemType.class, "pixelmon:master_ball").get()
                    , Text.of(TextColors.YELLOW, TextStyles.BOLD, "Catching"), lore),openCatching);

            lore.clear();

            lore.add(Text.of(TextColors.AQUA, TextStyles.BOLD, "LEVEL " + playerLevels.getBreedlevel()));
            lore.add(Text.of(TextColors.GREEN, "Info:"));
            lore.add(Text.of(TextColors.WHITE, " This stat will decrease the amount of steps\n required to hatch an egg. This stat will also\n decrease the ammount of time it takes to breed pokemon."));
            Element breedingElement = Element.of(getItemStackWithLore(Sponge.getGame().getRegistry().getType(ItemType.class, "pixelmon:destiny_knot").get()
                    , Text.of(TextColors.YELLOW, TextStyles.BOLD, "Breeding"), lore),openBreeding);

            lore.clear();

            lore.add(Text.of(TextColors.AQUA, TextStyles.BOLD, "LEVEL " + playerLevels.getKilllevel()));
            lore.add(Text.of(TextColors.GREEN, "Info:"));
            lore.add(Text.of(TextColors.WHITE, " This stat will increase the amount of experience\n gained when defeating wild pokemon. You will also\n gain a chance to earn more valuable loot."));
            Element battlingElement = Element.of(getItemStackWithLore(Sponge.getGame().getRegistry().getType(ItemType.class, "minecraft:diamond_sword").get()
                    , Text.of(TextColors.YELLOW, TextStyles.BOLD, "Battiling"), lore),openBattling);

            lore.clear();

            lore.add(Text.of(TextColors.AQUA, TextStyles.BOLD, "LEVEL " + playerLevels.getPballLevel()));
            lore.add(Text.of(TextColors.GREEN, "Info:"));
            lore.add(Text.of(TextColors.WHITE, " This stat will allow you to gain double,\n and even triple the amount of items that\n you hammer on an pokemon anvil."));
            Element smithingElement = Element.of(getItemStackWithLore(Sponge.getGame().getRegistry().getType(ItemType.class, "minecraft:anvil").get()
                    , Text.of(TextColors.YELLOW, TextStyles.BOLD, "Smithing"), lore),openSmith);

            Element fillerBlue = Element.of(ItemStack.builder().itemType(ItemTypes.STAINED_GLASS_PANE).add(Keys.DYE_COLOR, DyeColors.BLUE).build());
            Element fillerBlack = Element.of(ItemStack.builder().itemType(ItemTypes.STAINED_GLASS_PANE).add(Keys.DYE_COLOR, DyeColors.BLACK).build());

            layout.checker(fillerBlack, fillerBlue);
            layout.set(catchingElement, 10);
            layout.set(breedingElement, 12);
            layout.set(battlingElement, 14);
            layout.set(smithingElement, 16);

            view.define(layout.build());
            view.open(player);


            return CommandResult.success();
        } else {

            PlayerLevels playerLevels = new PlayerLevels(player,
                    Storage.getBreedExp(player.getUniqueId()),
                    Storage.getCatchEXP(player.getUniqueId()),
                    Storage.getBattleExp(player.getUniqueId()),
                    Storage.getPokeballExp(player.getUniqueId()));

            Consumer<Action.Click> openCatching = a ->{
                GUIS.openCatchGUI(player, player);
            };
            Consumer<Action.Click> openBreeding = a ->{
                GUIS.openBreedGUI(player, player);
            };
            Consumer<Action.Click> openBattling = a ->{
                GUIS.openBattleGUI(player, player);
            };
            Consumer<Action.Click> openSmith = a ->{
                GUIS.openSmithingGUI(player, player);
            };

            Layout.Builder layout = Layout.builder().dimension(InventoryDimension.of(9, 3));
            View view = View.builder().archetype(InventoryArchetypes.CHEST).property(InventoryTitle.of(Text.of(TextColors.AQUA, "FrostMMO Stats"))).build(FrostMMO.pluginContainer);

            List<Text> lore = new ArrayList<>();
            lore.add(Text.of(TextColors.AQUA, TextStyles.BOLD, "LEVEL " + playerLevels.getCathclevel()));
            lore.add(Text.of(TextColors.GREEN, "Info:"));
            lore.add(Text.of(TextColors.WHITE, " This stat allows pokemon that you are catching\n have a chance to turn into a shiny,\n or gain is hidden ability upon capture.\n The probability of this" +
                    "happening scales with levels."));
            Element catchingElement = Element.of(getItemStackWithLore(Sponge.getGame().getRegistry().getType(ItemType.class, "pixelmon:master_ball").get()
                    , Text.of(TextColors.YELLOW, TextStyles.BOLD, "Catching"), lore),openCatching);

            lore.clear();

            lore.add(Text.of(TextColors.AQUA, TextStyles.BOLD, "LEVEL " + playerLevels.getBreedlevel()));
            lore.add(Text.of(TextColors.GREEN, "Info:"));
            lore.add(Text.of(TextColors.WHITE, " This stat will decrease the amount of steps\n required to hatch an egg. This stat will also\n decrease the ammount of time it takes to breed pokemon."));
            Element breedingElement = Element.of(getItemStackWithLore(Sponge.getGame().getRegistry().getType(ItemType.class, "pixelmon:destiny_knot").get()
                    , Text.of(TextColors.YELLOW, TextStyles.BOLD, "Breeding"), lore),openBreeding);

            lore.clear();

            lore.add(Text.of(TextColors.AQUA, TextStyles.BOLD, "LEVEL " + playerLevels.getKilllevel()));
            lore.add(Text.of(TextColors.GREEN, "Info:"));
            lore.add(Text.of(TextColors.WHITE, " This stat will increase the amount of experience\n gained when defeating wild pokemon. You will also\n gain a chance to earn more valuable loot."));
            Element battlingElement = Element.of(getItemStackWithLore(Sponge.getGame().getRegistry().getType(ItemType.class, "minecraft:diamond_sword").get()
                    , Text.of(TextColors.YELLOW, TextStyles.BOLD, "Battiling"), lore),openBattling);

            lore.clear();

            lore.add(Text.of(TextColors.AQUA, TextStyles.BOLD, "LEVEL " + playerLevels.getPballLevel()));
            lore.add(Text.of(TextColors.GREEN, "Info:"));
            lore.add(Text.of(TextColors.WHITE, " This stat will allow you to gain double,\n and even triple the amount of items that\n you hammer on an pokemon anvil."));
            Element smithingElement = Element.of(getItemStackWithLore(Sponge.getGame().getRegistry().getType(ItemType.class, "minecraft:anvil").get()
                    , Text.of(TextColors.YELLOW, TextStyles.BOLD, "Smithing"), lore),openSmith);

            Element fillerBlue = Element.of(ItemStack.builder().itemType(ItemTypes.STAINED_GLASS_PANE).add(Keys.DYE_COLOR, DyeColors.BLUE).build());
            Element fillerBlack = Element.of(ItemStack.builder().itemType(ItemTypes.STAINED_GLASS_PANE).add(Keys.DYE_COLOR, DyeColors.BLACK).build());

            layout.checker(fillerBlack, fillerBlue);
            layout.set(catchingElement, 10);
            layout.set(breedingElement, 12);
            layout.set(battlingElement, 14);
            layout.set(smithingElement, 16);

            view.define(layout.build());
            view.open(player);
        }
        return CommandResult.success();
    }



    public ItemStack getItemStackWithLore(ItemType itemType, Text name, List<Text> lore) {
        ItemStack itemStack = ItemStack.builder().itemType(itemType).add(Keys.DISPLAY_NAME, name).add(Keys.ITEM_LORE, lore).build();
        return itemStack;
    }

}
