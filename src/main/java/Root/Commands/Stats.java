package Root.Commands;

import Root.Objects.PlayerLevels;
import Root.Objects.Storage;
import akka.io.Tcp;
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


public class Stats implements CommandExecutor {
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if (!(src instanceof Player)) {
            return CommandResult.success();
        }
        Player player = (Player) src;

        PlayerLevels playerLevels = new PlayerLevels(player, Storage.getBreedExp(player.getUniqueId()), Storage.getCatchEXP(player.getUniqueId()), Storage.getBattleExp(player.getUniqueId()));

        Scoreboard scoreboard = Scoreboard.builder().build();
        Objective objective = Sponge.getGame().getRegistry().createBuilder(Objective.Builder.class)
                .criterion(Criteria.DUMMY)
                .name("PlayerStats")
                .displayName(Text.of(TextStyles.BOLD, TextColors.BLUE, "FrostMMO Stats"))
                .build();

        objective.getOrCreateScore(Text.of("   ")).setScore(30);
        objective.getOrCreateScore(Text.of(TextColors.YELLOW, "Breeding: ")).setScore(29);
        objective.getOrCreateScore(Text.of(TextColors.GREEN, playerLevels.getBreedlevel())).setScore(28);
        objective.getOrCreateScore(Text.of(TextColors.WHITE, TextStyles.UNDERLINE,
                Storage.getBreedExp(player.getUniqueId()), "/", (500 * ((int) Math.pow(playerLevels.getBreedlevel() + 1, 2)) - (500 * (playerLevels.getBreedlevel() + 1)))
        )).setScore(27);

        objective.getOrCreateScore(Text.of(TextColors.YELLOW, "Catching: ")).setScore(26);
        objective.getOrCreateScore(Text.of(TextColors.GREEN, playerLevels.getCathclevel())).setScore(25);
        objective.getOrCreateScore(Text.of(TextColors.WHITE, TextStyles.UNDERLINE,
                Storage.getCatchEXP(player.getUniqueId()), "/", (500 * ((int) Math.pow(playerLevels.getCathclevel() + 1, 2)) - (500 * (playerLevels.getCathclevel() + 1)))
        )).setScore(24);

        objective.getOrCreateScore(Text.of(TextColors.YELLOW, "Battling: ")).setScore(23);
        objective.getOrCreateScore(Text.of(TextColors.GREEN, playerLevels.getKilllevel())).setScore(22);
        objective.getOrCreateScore(Text.of(TextColors.WHITE, TextStyles.UNDERLINE,
                Storage.getBattleExp(player.getUniqueId()), "/", (500 * ((int) Math.pow(playerLevels.getKilllevel() + 1, 2)) - (500 * (playerLevels.getKilllevel() + 1)))
        )).setScore(21);

        scoreboard.addObjective(objective);
        scoreboard.updateDisplaySlot(objective, DisplaySlots.SIDEBAR);
        player.setScoreboard(scoreboard);

        return CommandResult.success();
    }
}
