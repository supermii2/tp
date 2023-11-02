package seedu.address.model.person;

import java.util.Set;

import seedu.address.model.module.Module;
import seedu.address.model.tag.Tag;
import seedu.address.model.tutorial.Tutorial;

/**
 * Represents a Professor in the address book.
 */
public class Professor extends Person {
    /**
     * Every field must be present and not null.
     */
    public Professor(Name name, Phone phone, Email email, Set<Tag> tags,
                     Set<Module> modules, Set<Tutorial> tutorials, Telegram telegram) {
        super(name, phone, email, tags, modules, tutorials, telegram);
    }
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Professor)) {
            return false;
        }

        Student otherPerson = (Student) other;
        return super.equals(otherPerson);
    }
}
