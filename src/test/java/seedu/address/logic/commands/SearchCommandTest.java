package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_CS2100;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TUTORIAL_GROUP_TG01;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TUTORIAL_GROUP_TG02;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalModules.FIRST_MODULE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalTutorials.TUTORIAL_TUT1_MON9PM;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.module.Module;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.model.tutorial.Tutorial;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for SearchCommand.
 */
public class SearchCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        model.addModule(FIRST_MODULE);
        model.addTutorial(TUTORIAL_TUT1_MON9PM);
        Person personWithModule = new PersonBuilder(model.getAddressBook().getPersonList().get(0))
                .withModules(FIRST_MODULE).withTutorials(TUTORIAL_TUT1_MON9PM).build();
        model.setPerson(model.getFilteredPersonList().get(0), personWithModule);
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_searchByName() {
        Name nameToSearch = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()).getName();
        List<Name> nameList = new ArrayList<>(Collections.singletonList(nameToSearch));
        List<Module> moduleList = new ArrayList<>();
        List<Tutorial> tutorialList = new ArrayList<>();
        List<Tag> tagList = new ArrayList<>();
        expectedModel.updateFilteredPersonList(person -> person.getName().contains(nameToSearch));
        assertCommandSuccess(new SearchCommand(nameList, moduleList, tutorialList, tagList), model,
                SearchCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_searchByNameContained() {
        Name nameToSearch = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()).getName();
        Name nameToSearchContained = new Name(nameToSearch.fullName.substring(0, 4));
        List<Name> nameList = new ArrayList<>(Collections.singletonList(nameToSearchContained));
        List<Module> moduleList = new ArrayList<>();
        List<Tutorial> tutorialList = new ArrayList<>();
        List<Tag> tagList = new ArrayList<>();
        expectedModel.updateFilteredPersonList(person -> person.getName().contains(nameToSearchContained));
        assertCommandSuccess(new SearchCommand(nameList, moduleList, tutorialList, tagList), model,
                SearchCommand.MESSAGE_SUCCESS, expectedModel);
    }
    @Test
    public void execute_searchByModule() {
        List<Name> nameList = new ArrayList<>();
        List<Module> moduleList = new ArrayList<>(List.of(FIRST_MODULE));
        List<Tutorial> tutorialList = new ArrayList<>();
        List<Tag> tagList = new ArrayList<>();
        expectedModel.updateFilteredPersonList(person -> person.getModules().contains(FIRST_MODULE));
        assertCommandSuccess(new SearchCommand(nameList, moduleList, tutorialList, tagList), model,
                SearchCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_searchByTutorial() {
        List<Name> nameList = new ArrayList<>();
        List<Module> moduleList = new ArrayList<>();
        List<Tutorial> tutorialList = new ArrayList<>(List.of(TUTORIAL_TUT1_MON9PM));
        List<Tag> tagList = new ArrayList<>();
        expectedModel.updateFilteredPersonList(person -> person.getTutorials().contains(TUTORIAL_TUT1_MON9PM));
        assertCommandSuccess(new SearchCommand(nameList, moduleList, tutorialList, tagList), model,
                SearchCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_searchWithNoConditions() {
        List<Name> nameList = new ArrayList<>();
        List<Module> moduleList = new ArrayList<>();
        List<Tutorial> tutorialList = new ArrayList<>();
        List<Tag> tagList = new ArrayList<>();
        expectedModel.updateFilteredPersonList(person -> false);
        assertCommandSuccess(new SearchCommand(nameList, moduleList, tutorialList, tagList), model,
                SearchCommand.MESSAGE_SUCCESS, expectedModel);

    }


    @Test
    public void equals() {
        List<Name> amyNameList = new ArrayList<>(List.of(new Name(VALID_NAME_AMY)));
        List<Name> bobNameList = new ArrayList<>(List.of(new Name(VALID_NAME_BOB)));

        Module modCS2100 = new Module(VALID_MODULE_CS2100);
        Module modCS2101 = new Module(VALID_MODULE_CS2101);

        List<Module> cs2100ModuleList = new ArrayList<>(List.of(modCS2100));
        List<Module> cs2101ModuleList = new ArrayList<>(List.of(modCS2101));

        Tutorial tutTG01 = new Tutorial(modCS2100, VALID_TUTORIAL_GROUP_TG01);
        Tutorial tutTG02 = new Tutorial(modCS2100, VALID_TUTORIAL_GROUP_TG02);

        List<Tutorial> tutorialListTG01 = new ArrayList<>(List.of(tutTG01));
        List<Tutorial> tutorialListTG02 = new ArrayList<>(List.of(tutTG02));

        List<Tag> tags = new ArrayList<>();

        SearchCommand baseSearchCommand = new SearchCommand(amyNameList, cs2100ModuleList, tutorialListTG01, tags);
        SearchCommand baseSearchCommandAlt = new SearchCommand(amyNameList, cs2100ModuleList, tutorialListTG01, tags);
        SearchCommand diffNameSearchCommand = new SearchCommand(bobNameList, cs2100ModuleList, tutorialListTG01, tags);
        SearchCommand diffModSearchCommand = new SearchCommand(amyNameList, cs2101ModuleList, tutorialListTG01, tags);
        SearchCommand diffTutSearchCommand = new SearchCommand(amyNameList, cs2100ModuleList, tutorialListTG02, tags);

        // same object -> returns true
        assertTrue(baseSearchCommand.equals(baseSearchCommand));

        // same values -> returns true
        assertTrue(baseSearchCommand.equals(baseSearchCommandAlt));

        // different types -> returns false
        assertFalse(baseSearchCommand.equals(1));

        // null -> returns false
        assertFalse(baseSearchCommand.equals(null));

        // different name or mod
        assertFalse(baseSearchCommand.equals(diffNameSearchCommand));
        assertFalse(baseSearchCommand.equals(diffModSearchCommand));
        assertFalse(baseSearchCommand.equals(diffTutSearchCommand));
    }
}
