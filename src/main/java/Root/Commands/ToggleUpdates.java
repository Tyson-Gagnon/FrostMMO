package Root.Commands;

import Root.FrostMMO;
import com.sun.prism.shader.FillRoundRect_Color_AlphaTest_Loader;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.scoreboard.Scoreboard;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class ToggleUpdates implements CommandExecutor {
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

        if (!(src instanceof Player)) {
            return CommandResult.success();
        }

        Player player = (Player) src;

        if (FrostMMO.updateExemptions.contains(player.getUniqueId().toString())) {
            FrostMMO.updateExemptions.remove(player.getUniqueId().toString());
            player.sendMessage(Text.of(TextColors.AQUA, "[FrostMMO] - ", TextColors.GRAY,
                    "You gained have ", TextColors.GREEN, "ENABLED", TextColors.GRAY, " the FrostMMO sidebar."
            ));
        } else {
            FrostMMO.updateExemptions.add(player.getUniqueId().toString());
            player.setScoreboard(Scoreboard.builder().build());
            player.sendMessage(Text.of(TextColors.AQUA, "[FrostMMO] - ", TextColors.GRAY,
                    "You gained have ", TextColors.RED, "DISABLED", TextColors.GRAY, " the FrostMMO sidebar. You can reactivate the sidebar by doing this command again " +
                            "or using /frostmmo stats to display your stats once"
            ));
        }
        return CommandResult.success();
    }
}
