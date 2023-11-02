package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIAL_NAME;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.tutorial.Tutorial;

/**
 * Edits the details of an existing person in the address book.
 */
public class RemoveFromTutorialCommand extends Command {

    public static final String COMMAND_WORD = "removeFromTutorial";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Removes a user from a given tutorial "
            + "Parameters:"
            + "INDEX (must be a positive integer)"
            + "[" + PREFIX_MODULE + "MODULE_NAME]"
            + "[" + PREFIX_TUTORIAL_NAME + "TUTORIAL_NAME]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_MODULE + "CS2103T "
            + PREFIX_TUTORIAL_NAME + "T11 ";

    public static final String MESSAGE_SUCCESS = "Edited Person: %1$s";
    public static final String MESSAGE_PERSON_LACKS_TUTORIAL = "User does not have the given tutorial.";

    private final Index index;

    private final Tutorial tutorialToRemoveFrom;

    /**
     * @param index of the person in the filtered person list to add tag to
     */
    public RemoveFromTutorialCommand(Index index, Tutorial tutorialToRemoveFrom) {
        requireNonNull(index);
        requireNonNull(tutorialToRemoveFrom);
        this.index = index;
        this.tutorialToRemoveFrom = tutorialToRemoveFrom;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Tutorial realTutorial;
        try {
            realTutorial = (Tutorial) model.getTutorialList().stream().filter(
                    tut -> tut.equals(tutorialToRemoveFrom)

            ).toArray()[0];
        } catch (RuntimeException e) {
            throw new CommandException(Messages.MESSAGE_INVALID_TUTORIAL);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        if (!personHasTutorial(personToEdit, realTutorial)) {
            throw new CommandException(MESSAGE_PERSON_LACKS_TUTORIAL);
        }

        Person editedPerson = createEditedPerson(personToEdit, realTutorial);
        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(editedPerson)));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private Person createEditedPerson(Person personToEdit, Tutorial realTutorial) {
        assert personToEdit != null;
        assert realTutorial != null;
        return personToEdit.removeTutorial(realTutorial);
    }

    /**
     * Returns if a given person is part of a specified tutorial.
     * @param personToCheck The given person.
     * @param tutorial The specified tutorial.
     * @return Whether the person is part of the tutorial.
     */
    private boolean personHasTutorial(Person personToCheck, Tutorial tutorial) {
        return personToCheck.getTutorials().contains(tutorial);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RemoveFromTutorialCommand)) {
            return false;
        }

        return this.index.equals(((RemoveFromTutorialCommand) other).index)
                && this.tutorialToRemoveFrom.equals(((RemoveFromTutorialCommand) other).tutorialToRemoveFrom);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("tutorialName", tutorialToRemoveFrom)
                .toString();
    }
}

