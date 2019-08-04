package Root.Commands;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.scoreboard.Score;
import org.spongepowered.api.scoreboard.Scoreboard;
import org.spongepowered.api.text.Text;
import org.yaml.snakeyaml.constructor.SafeConstructor;

import java.util.Optional;

public class HideStats implements CommandExecutor {
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

        if (!(src instanceof Player)) {
            src.sendMessage(Text.of("YOu need to be player retard"));
            return CommandResult.success();
        }

        Player player = (Player) src;


        Scoreboard scoreboard = Scoreboard.builder().build();

        player.setScoreboard(scoreboard);


        return CommandResult.success();
    }
}
