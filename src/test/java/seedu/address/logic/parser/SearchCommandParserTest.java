package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.MODULE_DESC_CS2100;
import static seedu.address.logic.commands.CommandTestUtil.MODULE_DESC_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TUTORIAL_GROUP_DESC_TG01;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_CS2100;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TUTORIAL_GROUP_TG01;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.SearchCommand;
import seedu.address.model.module.Module;
import seedu.address.model.person.Name;
import seedu.address.model.tag.Tag;
import seedu.address.model.tutorial.Tutorial;

public class SearchCommandParserTest {
    private final SearchCommandParser parser = new SearchCommandParser();

    @Test
    public void parse_namesPresentSuccess() {
        Name expectedName = new Name(VALID_NAME_AMY);
        List<Name> expectedNameList = new ArrayList<>(List.of(expectedName));
        List<Module> expectedModuleList = new ArrayList<>();
        List<Tutorial> expectedTutorialList = new ArrayList<>();
        List<Tag> expectedTagList = new ArrayList<>();
        assertParseSuccess(parser, NAME_DESC_AMY,
                new SearchCommand(expectedNameList, expectedModuleList, expectedTutorialList, expectedTagList));
    }

    @Test
    public void parse_modulesPresentSuccess() {
        List<Name> expectedNameList = new ArrayList<>();
        Module testModuleTag = new Module(VALID_MODULE_CS2100);
        List<Module> expectedModuleList = new ArrayList<>(List.of(testModuleTag));
        List<Tutorial> expectedTutorialList = new ArrayList<>();
        List<Tag> expectedTagList = new ArrayList<>();
        assertParseSuccess(parser, MODULE_DESC_CS2100,
                new SearchCommand(expectedNameList, expectedModuleList, expectedTutorialList, expectedTagList));
    }

    @Test
    public void parse_tutorialPresentSuccess() {
        List<Name> expectedNameList = new ArrayList<>();
        Module testModule = new Module(VALID_MODULE_CS2100);
        List<Module> expectedModuleList = new ArrayList<>(List.of(testModule));
        Tutorial testTutorial = new Tutorial(testModule, VALID_TUTORIAL_GROUP_TG01);
        List<Tutorial> expectedTutorialList = new ArrayList<>(List.of(testTutorial));
        List<Tag> expectedTagList = new ArrayList<>();
        assertParseSuccess(parser, MODULE_DESC_CS2100 + TUTORIAL_GROUP_DESC_TG01,
                new SearchCommand(expectedNameList, expectedModuleList, expectedTutorialList, expectedTagList));
    }

    @Test
    public void parse_tagPresentSuccess() {
        List<Name> expectedNameList = new ArrayList<>();
        List<Module> expectedModuleList = new ArrayList<>();
        List<Tutorial> expectedTutorialList = new ArrayList<>();

        String testInput = " " + PREFIX_TAG + "S2";

        Tag testTag = new Tag("S2");
        List<Tag> expectedTagList = new ArrayList<>(List.of(testTag));
        assertParseSuccess(parser, testInput,
                new SearchCommand(expectedNameList, expectedModuleList, expectedTutorialList, expectedTagList));
    }

    @Test
    public void parse_preambleNotEmptyError() {
        assertParseFailure(parser, " dummy text", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SearchCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_tooManyNamesError() {
        assertParseFailure(parser, NAME_DESC_AMY + NAME_DESC_BOB,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_ERROR_TOO_MANY_NAMES));
    }

    @Test
    public void parse_tooManyModulesGivenError() {
        assertParseFailure(parser,
                TUTORIAL_GROUP_DESC_TG01 + MODULE_DESC_CS2100 + MODULE_DESC_CS2101,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_INVALID_NUM_OF_MODULES));
    }

    @Test
    public void parse_notEnoughModulesGivenError() {
        assertParseFailure(parser, TUTORIAL_GROUP_DESC_TG01,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_INVALID_NUM_OF_MODULES));
    }
}

