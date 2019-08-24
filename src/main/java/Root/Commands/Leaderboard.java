package Root.Commands;

import Root.Commands.CommandElemets.TypesCommandElemts;
import Root.Objects.PlayerLevels;
import Root.Objects.Storage;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.scoreboard.Scoreboard;
import org.spongepowered.api.scoreboard.critieria.Criteria;
import org.spongepowered.api.scoreboard.displayslot.DisplaySlots;
import org.spongepowered.api.scoreboard.objective.Objective;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Leaderboard implements CommandExecutor {
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if (!(src instanceof Player)) {
            return CommandResult.success();
        }
        Player player = (Player) src;
        String stat = args.<String>getOne("stat").get();

        showLeaderBoard(player,stat);

        return CommandResult.success();
    }

    private void showLeaderBoard(Player player, String stat) {
        List<PlayerLevels> playerRankingList = Storage.getPlayerRankings();
        switch (stat.toLowerCase()) {
            case "breeding":
                playerRankingList.sort(Comparator.comparingInt(PlayerLevels::getBreedlevel).reversed());
                Objective obj = Sponge.getGame().getRegistry().createBuilder(Objective.Builder.class).criterion(Criteria.DUMMY).name("Rankings").displayName(Text.of(TextStyles.BOLD, TextColors.BLUE, "   Breeding Rankings   ")).build();
                Scoreboard scoreboard = Scoreboard.builder().build();
                obj.getOrCreateScore(Text.of(TextColors.DARK_GRAY, TextStyles.BOLD, "  ")).setScore(30);
                obj.getOrCreateScore(Text.of(TextColors.DARK_GRAY, TextStyles.BOLD, " ")).setScore(1);
                int f = 29;
                for (int i = 0; i < 10; i++) {

                    if (i < playerRankingList.size()) {
                        obj.getOrCreateScore(Text.of(
                                TextColors.YELLOW, "#" + (i + 1) + " ",
                                TextColors.GOLD, playerRankingList.get(i).getUser().getName(),
                                TextColors.GRAY, ">>",
                                TextColors.GOLD, playerRankingList.get(i).getBreedlevel()
                        )).setScore(f);
                        f--;
                    }
                }scoreboard.addObjective(obj);
                scoreboard.updateDisplaySlot(obj, DisplaySlots.SIDEBAR);
                player.setScoreboard(scoreboard);
                break;
            case "battling":
                playerRankingList.sort(Comparator.comparingInt(PlayerLevels::getKilllevel).reversed());
                Objective objw = Sponge.getGame().getRegistry().createBuilder(Objective.Builder.class).criterion(Criteria.DUMMY).name("Rankings").displayName(Text.of(TextStyles.BOLD, TextColors.BLUE, "   Battling Rankings   ")).build();
                Scoreboard scoreboardw = Scoreboard.builder().build();
                objw.getOrCreateScore(Text.of(TextColors.DARK_GRAY, TextStyles.BOLD, "  ")).setScore(30);
                objw.getOrCreateScore(Text.of(TextColors.DARK_GRAY, TextStyles.BOLD, " ")).setScore(1);
                int fw = 29;
                for (int i = 0; i < 10; i++) {

                    if (i < playerRankingList.size()) {
                        objw.getOrCreateScore(Text.of(
                                TextColors.YELLOW, "#" + (i + 1) + " ",
                                TextColors.GOLD, playerRankingList.get(i).getUser().getName(),
                                TextColors.GRAY, ">>",
                                TextColors.GOLD, playerRankingList.get(i).getKilllevel()
                        )).setScore(fw);
                        fw--;
                    }
                }
                scoreboardw.addObjective(objw);
                scoreboardw.updateDisplaySlot(objw,DisplaySlots.SIDEBAR);
                player.setScoreboard(scoreboardw);
                break;
            case "catching":
                playerRankingList.sort(Comparator.comparingInt(PlayerLevels::getCathclevel).reversed());
                Objective obje = Sponge.getGame().getRegistry().createBuilder(Objective.Builder.class).criterion(Criteria.DUMMY).name("Rankings").displayName(Text.of(TextStyles.BOLD, TextColors.BLUE, "   Catching Rankings   ")).build();
                Scoreboard scoreboarde = Scoreboard.builder().build();
                obje.getOrCreateScore(Text.of(TextColors.DARK_GRAY, TextStyles.BOLD, "  ")).setScore(30);
                obje.getOrCreateScore(Text.of(TextColors.DARK_GRAY, TextStyles.BOLD, " ")).setScore(1);
                int fe = 29;
                for (int i = 0; i < 10; i++) {

                    if (i < playerRankingList.size()) {
                        obje.getOrCreateScore(Text.of(
                                TextColors.YELLOW, "#" + (i + 1) + " ",
                                TextColors.GOLD, playerRankingList.get(i).getUser().getName(),
                                TextColors.GRAY, ">>",
                                TextColors.GOLD, playerRankingList.get(i).getCathclevel()
                        )).setScore(fe);
                        fe--;
                    }
                }scoreboarde.addObjective(obje);
                scoreboarde.updateDisplaySlot(obje, DisplaySlots.SIDEBAR);
                player.setScoreboard(scoreboarde);
                break;
        }


    }

}
