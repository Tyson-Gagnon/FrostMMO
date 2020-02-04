package Root.Objects;

import Root.FrostMMO;
import Root.Manager.ConfigurationManager;
import com.mcsimonflash.sponge.teslalibs.inventory.Element;
import com.mcsimonflash.sponge.teslalibs.inventory.Layout;
import com.mcsimonflash.sponge.teslalibs.inventory.View;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.type.DyeColors;
import org.spongepowered.api.data.type.GoldenApples;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.InventoryArchetypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.property.InventoryDimension;
import org.spongepowered.api.item.inventory.property.InventoryTitle;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GUIS {

    public static void openCatchGUI(Player player, User target){

        PlayerLevels playerLevels = new PlayerLevels(target,
                Storage.getBreedExp(target.getUniqueId()),
                Storage.getCatchEXP(target.getUniqueId()),
                Storage.getBattleExp(target.getUniqueId()),
                Storage.getPokeballExp(target.getUniqueId())
        );

        List<PlayerLevels> playerRankingList = Storage.getPlayerRankings();
        playerRankingList.sort(Comparator.comparingInt(PlayerLevels::getCathclevel).reversed());

        int currentLevelXp = 500*((int)Math.pow(playerLevels.getCathclevel(),2))-(500*playerLevels.getCathclevel());
        int nextLevelsXp = 500*((int)Math.pow((playerLevels.getCathclevel()+1),2))-(500*(playerLevels.getCathclevel()+1));
        int currentXp = Storage.getCatchEXP(target.getUniqueId());

        Layout.Builder layout= Layout.builder().dimension(InventoryDimension.of(9,3));

        double percentChanceShiny = (((ConfigurationManager.getConfNode("MultiplierValues", "Catching", "percent-to-shiny-at-max").getDouble() / 100)) * playerLevels.getCathclevel());
        double percentChanceHA = (((ConfigurationManager.getConfNode("MultiplierValues", "Catching", "percent-to-HA-at-max").getDouble() / 100)) * playerLevels.getCathclevel());


        List<Text> lore = new ArrayList<>();
        lore.add(Text.of(TextColors.GRAY," Based on your current level, there is a\n" +
                " " + percentChanceHA + "% chance that your pokemon will learn its\n" +
                " hidden ability and a " + percentChanceShiny + "% chance that it\n" +
                " will turn into a shiny version of that\n pokemon!"));


        Element stat = Element.of(ItemStack.builder().itemType(ItemTypes.BOOK).add(Keys.ITEM_LORE,lore).add(Keys.DISPLAY_NAME,Text.of(TextColors.AQUA,"Catching info:")).build());
        lore.clear();

        lore.add(Text.of(TextColors.YELLOW,currentXp-currentLevelXp,"/",nextLevelsXp-currentLevelXp));

        Element xp = Element.of(ItemStack.builder().itemType(ItemTypes.GOLDEN_APPLE).add(Keys.GOLDEN_APPLE_TYPE, GoldenApples.ENCHANTED_GOLDEN_APPLE).add(Keys.ITEM_LORE,lore).add(Keys.DISPLAY_NAME,Text.of(TextColors.AQUA,"XP to Next Level:")).build());

        lore.clear();

        ///for(int i = 0; i < 10; i++){
        //    lore.add(Text.of(TextColors.YELLOW,i+1,": ",playerRankingList.get(i).getUser().getName()));
       // }
        Element lead =Element.of(ItemStack.builder().itemType(ItemTypes.TOTEM_OF_UNDYING).add(Keys.ITEM_LORE,lore).add(Keys.DISPLAY_NAME,Text.of(TextColors.YELLOW,"Leaderboard")).build());


        Element fillerBlue = Element.of(ItemStack.builder().itemType(ItemTypes.STAINED_GLASS_PANE).add(Keys.DYE_COLOR, DyeColors.BLUE).build());
        Element fillerBlack = Element.of(ItemStack.builder().itemType(ItemTypes.STAINED_GLASS_PANE).add(Keys.DYE_COLOR, DyeColors.BLACK).build());

        layout.checker(fillerBlack,fillerBlue);
        layout.set(stat,11);
        layout.set(xp,13);
        layout.set(lead,15);

        View view = View.builder().archetype(InventoryArchetypes.CHEST).property(InventoryTitle.of(Text.of(TextColors.AQUA, "FrostMMO Stats"))).build(FrostMMO.pluginContainer);
        view.define(layout.build());
        view.open(player);

    }

    public static void openBreedGUI(Player player, User target){

        PlayerLevels playerLevels = new PlayerLevels(target,
                Storage.getBreedExp(target.getUniqueId()),
                Storage.getCatchEXP(target.getUniqueId()),
                Storage.getBattleExp(target.getUniqueId()),
                Storage.getPokeballExp(target.getUniqueId())
        );

        List<PlayerLevels> playerRankingList = Storage.getPlayerRankings();
        playerRankingList.sort(Comparator.comparingInt(PlayerLevels::getBreedlevel).reversed());

        int currentLevelXp = 500*((int)Math.pow(playerLevels.getBreedlevel(),2))-(500*playerLevels.getBreedlevel());
        int nextLevelsXp = 500*((int)Math.pow((playerLevels.getBreedlevel()+1),2))-(500*(playerLevels.getBreedlevel()+1));
        int currentXp = Storage.getBreedExp(target.getUniqueId());

        Layout.Builder layout= Layout.builder().dimension(InventoryDimension.of(9,3));

        int stepsRemoved = ConfigurationManager.getConfNode("MultiplierValues", "Breeding", "steps-removed-per-level").getInt() * playerLevels.getBreedlevel();


        List<Text> lore = new ArrayList<>();
        lore.add(Text.of(TextColors.GRAY," This stat will decrease the amount if steps\n" +
                " required when hatching an egg! Based on your\n" +
                " level," +
                " "+stepsRemoved+" steps will be taken off of\n" +
                " your egg when you claim it! *This will\n" +
                " also reduce the amount of time it takes\n" +
                " to breed pokemon*(Unimplemented)"));


        Element stat = Element.of(ItemStack.builder().itemType(ItemTypes.BOOK).add(Keys.ITEM_LORE,lore).add(Keys.DISPLAY_NAME,Text.of(TextColors.AQUA,"Breeding info:")).build());
        lore.clear();

        lore.add(Text.of(TextColors.YELLOW,currentXp-currentLevelXp,"/",nextLevelsXp-currentLevelXp));

        Element xp = Element.of(ItemStack.builder().itemType(ItemTypes.GOLDEN_APPLE).add(Keys.GOLDEN_APPLE_TYPE, GoldenApples.ENCHANTED_GOLDEN_APPLE).add(Keys.ITEM_LORE,lore).add(Keys.DISPLAY_NAME,Text.of(TextColors.AQUA,"XP to Next Level:")).build());

        lore.clear();

       // for(int i = 0; i < 10; i++){
        //    lore.add(Text.of(TextColors.YELLOW,i+1,": ",playerRankingList.get(i).getUser().getName()));
       // }
        Element lead =Element.of(ItemStack.builder().itemType(ItemTypes.TOTEM_OF_UNDYING).add(Keys.ITEM_LORE,lore).add(Keys.DISPLAY_NAME,Text.of(TextColors.YELLOW,"Leaderboard")).build());


        Element fillerBlue = Element.of(ItemStack.builder().itemType(ItemTypes.STAINED_GLASS_PANE).add(Keys.DYE_COLOR, DyeColors.BLUE).build());
        Element fillerBlack = Element.of(ItemStack.builder().itemType(ItemTypes.STAINED_GLASS_PANE).add(Keys.DYE_COLOR, DyeColors.BLACK).build());

        layout.checker(fillerBlack,fillerBlue);
        layout.set(stat,11);
        layout.set(xp,13);
        layout.set(lead,15);

        View view = View.builder().archetype(InventoryArchetypes.CHEST).property(InventoryTitle.of(Text.of(TextColors.AQUA, "FrostMMO Stats"))).build(FrostMMO.pluginContainer);
        view.define(layout.build());
        view.open(player);

    }

    public static void openBattleGUI(Player player, User target){

        PlayerLevels playerLevels = new PlayerLevels(target,
                Storage.getBreedExp(target.getUniqueId()),
                Storage.getCatchEXP(target.getUniqueId()),
                Storage.getBattleExp(target.getUniqueId()),
                Storage.getPokeballExp(target.getUniqueId())
        );

        List<PlayerLevels> playerRankingList = Storage.getPlayerRankings();
        playerRankingList.sort(Comparator.comparingInt(PlayerLevels::getKilllevel).reversed());

        int currentLevelXp = 500*((int)Math.pow(playerLevels.getKilllevel(),2))-(500*playerLevels.getKilllevel());
        int nextLevelsXp = 500*((int)Math.pow((playerLevels.getKilllevel()+1),2))-(500*(playerLevels.getKilllevel()+1));
        int currentXp = Storage.getBattleExp(target.getUniqueId());

        Layout.Builder layout= Layout.builder().dimension(InventoryDimension.of(9,3));

        double xpMultiplier = ConfigurationManager.getConfNode("MultiplierValues", "Battling", "xp-multiplier-per-level").getDouble() * playerLevels.getKilllevel();


        List<Text> lore = new ArrayList<>();
        lore.add(Text.of(TextColors.GRAY," This stat will multiply your xp gain when\n" +
                " killing wild pokemon. Currently your multiplier is " +
                + xpMultiplier+ ".\n There is also a chance for you to\n" +
                " get double loot when killing pokemon."));


        Element stat = Element.of(ItemStack.builder().itemType(ItemTypes.BOOK).add(Keys.ITEM_LORE,lore).add(Keys.DISPLAY_NAME,Text.of(TextColors.AQUA,"Battling info:")).build());
        lore.clear();

        lore.add(Text.of(TextColors.YELLOW,currentXp-currentLevelXp,"/",nextLevelsXp-currentLevelXp));

        Element xp = Element.of(ItemStack.builder().itemType(ItemTypes.GOLDEN_APPLE).add(Keys.GOLDEN_APPLE_TYPE, GoldenApples.ENCHANTED_GOLDEN_APPLE).add(Keys.ITEM_LORE,lore).add(Keys.DISPLAY_NAME,Text.of(TextColors.AQUA,"XP to Next Level:")).build());

        lore.clear();

       // for(int i = 0; i < 10; i++){
        //    lore.add(Text.of(TextColors.YELLOW,i+1,": ",playerRankingList.get(i).getUser().getName()));
       // }
        Element lead =Element.of(ItemStack.builder().itemType(ItemTypes.TOTEM_OF_UNDYING).add(Keys.ITEM_LORE,lore).add(Keys.DISPLAY_NAME,Text.of(TextColors.YELLOW,"Leaderboard")).build());


        Element fillerBlue = Element.of(ItemStack.builder().itemType(ItemTypes.STAINED_GLASS_PANE).add(Keys.DYE_COLOR, DyeColors.BLUE).build());
        Element fillerBlack = Element.of(ItemStack.builder().itemType(ItemTypes.STAINED_GLASS_PANE).add(Keys.DYE_COLOR, DyeColors.BLACK).build());

        layout.checker(fillerBlack,fillerBlue);
        layout.set(stat,11);
        layout.set(xp,13);
        layout.set(lead,15);

        View view = View.builder().archetype(InventoryArchetypes.CHEST).property(InventoryTitle.of(Text.of(TextColors.AQUA, "FrostMMO Stats"))).build(FrostMMO.pluginContainer);
        view.define(layout.build());
        view.open(player);

    }

    public static void openSmithingGUI(Player player, User target){

        PlayerLevels playerLevels = new PlayerLevels(target,
                Storage.getBreedExp(target.getUniqueId()),
                Storage.getCatchEXP(target.getUniqueId()),
                Storage.getBattleExp(target.getUniqueId()),
                Storage.getPokeballExp(target.getUniqueId())
        );

        List<PlayerLevels> playerRankingList = Storage.getPlayerRankings();
        playerRankingList.sort(Comparator.comparingInt(PlayerLevels::getPballLevel).reversed());

        int currentLevelXp = 500*((int)Math.pow(playerLevels.getPballLevel(),2))-(500*playerLevels.getPballLevel());
        int nextLevelsXp = 500*((int)Math.pow((playerLevels.getPballLevel()+1),2))-(500*(playerLevels.getPballLevel()+1));
        int currentXp = Storage.getPokeballExp(target.getUniqueId());

        Layout.Builder layout= Layout.builder().dimension(InventoryDimension.of(9,3));

        List<Text> lore = new ArrayList<>();
        lore.add(Text.of(TextColors.GRAY," This stat allows for a chance of double,\n or even triple loot being dropped when hammering\n on an anvil."));


        Element stat = Element.of(ItemStack.builder().itemType(ItemTypes.BOOK).add(Keys.ITEM_LORE,lore).add(Keys.DISPLAY_NAME,Text.of(TextColors.AQUA,"Smithing info:")).build());
        lore.clear();

        lore.add(Text.of(TextColors.YELLOW,currentXp-currentLevelXp,"/",nextLevelsXp-currentLevelXp));

        Element xp = Element.of(ItemStack.builder().itemType(ItemTypes.GOLDEN_APPLE).add(Keys.GOLDEN_APPLE_TYPE, GoldenApples.ENCHANTED_GOLDEN_APPLE).add(Keys.ITEM_LORE,lore).add(Keys.DISPLAY_NAME,Text.of(TextColors.AQUA,"XP to Next Level:")).build());

        lore.clear();

      //  for(int i = 0; i < 10; i++){
       //     lore.add(Text.of(TextColors.YELLOW,i+1,": ",playerRankingList.get(i).getUser().getName()));
       // }
        Element lead =Element.of(ItemStack.builder().itemType(ItemTypes.TOTEM_OF_UNDYING).add(Keys.ITEM_LORE,lore).add(Keys.DISPLAY_NAME,Text.of(TextColors.YELLOW,"Leaderboard")).build());


        Element fillerBlue = Element.of(ItemStack.builder().itemType(ItemTypes.STAINED_GLASS_PANE).add(Keys.DYE_COLOR, DyeColors.BLUE).build());
        Element fillerBlack = Element.of(ItemStack.builder().itemType(ItemTypes.STAINED_GLASS_PANE).add(Keys.DYE_COLOR, DyeColors.BLACK).build());

        layout.checker(fillerBlack,fillerBlue);
        layout.set(stat,11);
        layout.set(xp,13);
        layout.set(lead,15);

        View view = View.builder().archetype(InventoryArchetypes.CHEST).property(InventoryTitle.of(Text.of(TextColors.AQUA, "FrostMMO Stats"))).build(FrostMMO.pluginContainer);
        view.define(layout.build());
        view.open(player);

    }




}
