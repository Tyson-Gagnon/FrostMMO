package Root.Commands.CommandElemets;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.ArgumentParseException;
import org.spongepowered.api.command.args.CommandArgs;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.CommandElement;
import org.spongepowered.api.text.Text;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class TypesCommandElemts extends CommandElement {

    public TypesCommandElemts(Text key){
        super(key);
    }

    @Override
    protected Object parseValue(CommandSource source, CommandArgs args) throws ArgumentParseException {
        String input = args.next();
        return input;
    }

    @Override
    public List<String> complete(CommandSource src, CommandArgs args, CommandContext context) {
        List<String> usage = new ArrayList<>();
        usage.add("breeding");
        usage.add("catching");
        usage.add("battling");
        usage.add("pball");
        return usage;
    }
}
