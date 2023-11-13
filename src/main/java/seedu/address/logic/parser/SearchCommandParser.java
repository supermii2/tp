package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIAL_NAME;

import java.util.List;
import java.util.stream.Collectors;

import seedu.address.logic.commands.SearchCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.module.Module;
import seedu.address.model.person.Name;
import seedu.address.model.tag.Tag;
import seedu.address.model.tutorial.Tutorial;

/**
 * Parses input arguments and creates a new SearchCommand object
 */
public class SearchCommandParser implements Parser<SearchCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the SearchCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SearchCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_MODULE, PREFIX_TUTORIAL_NAME, PREFIX_TAG);
        if (!argMultimap.getPreamble().isEmpty() || argMultimap.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));
        }

        if (argMultimap.getAllValues(PREFIX_NAME).size() > 1) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_ERROR_TOO_MANY_NAMES));
        }

        if (argMultimap.getAllValues(PREFIX_MODULE).size() != 1
                && argMultimap.getAllValues(PREFIX_TUTORIAL_NAME).size() > 0) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_INVALID_NUM_OF_MODULES));
        }
        final List<Name> personNameList = argMultimap.getAllValues(PREFIX_NAME)
                .stream()
                .map(nameString -> new Name(nameString))
                .collect(Collectors.toList());
        final List<Module> moduleList = argMultimap.getAllValues(PREFIX_MODULE)
                .stream()
                .map(moduleCode -> new Module(moduleCode))
                .collect(Collectors.toList());
        final List<Tutorial> tutorialList = argMultimap.getAllValues(PREFIX_TUTORIAL_NAME)
                .stream()
                .map(nameString -> new Tutorial(moduleList.get(0), nameString))
                .collect(Collectors.toList());
        final List<Tag> tagList = argMultimap.getAllValues(PREFIX_TAG)
                .stream()
                .map(tagName -> new Tag(tagName))
                .collect(Collectors.toList());

        return new SearchCommand(personNameList, moduleList, tutorialList, tagList);
    }
}
