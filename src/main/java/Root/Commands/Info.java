package Root.Commands;

import Root.Manager.ConfigurationManager;
import Root.Objects.PlayerLevels;
import Root.Objects.Storage;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class Info implements CommandExecutor {
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if (!(src instanceof Player)) {
            return CommandResult.success();
        }
        Player player = (Player) src;

        PlayerLevels playerLevels = new PlayerLevels(
                player,
                Storage.getBreedExp(player.getUniqueId()),
                Storage.getCatchEXP(player.getUniqueId()),
                Storage.getBattleExp(player.getUniqueId()));

        int stepsRemoved = ConfigurationManager.getConfNode("MultiplierValues", "Breeding", "steps-removed-per-level").getInt() * playerLevels.getBreedlevel();
        double xpMultiplier = ConfigurationManager.getConfNode("MultiplierValues", "Battling", "xp-multiplier-per-level").getDouble() * playerLevels.getKilllevel();
        double percentChanceShiny = (((ConfigurationManager.getConfNode("MultiplierValues", "Catching", "percent-to-shiny-at-max").getDouble() / 100)) * playerLevels.getCathclevel());
        double percentChanceHA = (((ConfigurationManager.getConfNode("MultiplierValues", "Catching", "percent-to-HA-at-max").getDouble() / 100)) * playerLevels.getCathclevel());

        player.sendMessage(Text.of(TextColors.AQUA, "[FrostMMO] - ", TextColors.GRAY,
                "Here are your current stats and what those stats offer based on your level in that stat!"
        ));

        player.sendMessage(Text.of(TextColors.AQUA, "Breeding - Level ", playerLevels.getBreedlevel()));
        player.sendMessage(Text.of(TextColors.GRAY, "> Reduces amount of steps it takes to hatch an egg by ", stepsRemoved, " steps!"));

        player.sendMessage(Text.of(TextColors.AQUA, "Battling - Level ", playerLevels.getKilllevel()));
        player.sendMessage(Text.of(TextColors.GRAY, "> Multiplies the amount of xp you get when defeating pokemon by ", (int) (xpMultiplier * 100), "%"));
        player.sendMessage(Text.of(TextColors.GRAY, "> 2% chance of getting double loot!"));

        player.sendMessage(Text.of(TextColors.AQUA, "Catching - Level ", playerLevels.getCathclevel()));
        player.sendMessage(Text.of(TextColors.GRAY, "> ", percentChanceShiny, " chance pokemon turning shiny after its caught!"));
        player.sendMessage(Text.of(TextColors.GRAY, "> ", percentChanceHA, " chance pokemon learning its HA after its caught!"));


        return CommandResult.success();
    }
}
