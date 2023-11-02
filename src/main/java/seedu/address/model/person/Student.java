package seedu.address.model.person;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.EditCommand;
import seedu.address.model.module.Module;
import seedu.address.model.tag.Tag;
import seedu.address.model.tutorial.Tutorial;

/**
 * Represents a Student in the address book.
 */
public class Student extends Person {
    private static StudentNumber studentNumber;
    /**
     * Every field must be present and not null.
     */
    public Student(Name name, Phone phone, Email email, Set<Tag> tags,
                   Set<Module> modules, Set<Tutorial> tutorials, StudentNumber studentNumber, Telegram telegram) {
        super(name, phone, email, tags, modules, tutorials, telegram);
        this.studentNumber = studentNumber;
    }

    public StudentNumber getStudentNumber() {
        return studentNumber;
    }

    @Override
    public Person changeTags(Set<Tag> updatedTags) {
        return new Student(name, phone, email, updatedTags, modules, tutorials, studentNumber, telegram);
    }

    @Override
    public Person editPerson(EditCommand.EditPersonDescriptor editPersonDescriptor) {
        Name updatedName = editPersonDescriptor.getName().orElse(this.getName());
        Phone updatedPhone = editPersonDescriptor.getPhone().orElse(this.getPhone());
        Email updatedEmail = editPersonDescriptor.getEmail().orElse(this.getEmail());
        Set<Tag> updatedTags = editPersonDescriptor.getTags().orElse(this.getTags());
        Set<Module> updatedModules = this.getModules();
        Set<Tutorial> updatedTutorials = this.getTutorials();
        StudentNumber updatedStudentNumber = editPersonDescriptor.getStudentNumber().orElse(this.getStudentNumber());
        Telegram updatedTelegram = editPersonDescriptor.getTelegram().orElse(this.getTelegram());

        return new Student(updatedName, updatedPhone, updatedEmail, updatedTags, updatedModules,
                updatedTutorials, updatedStudentNumber, updatedTelegram);
    }

    @Override
    public Person addModule(Module moduleToAddTo) {
        Set<Module> updatedModules = new HashSet<>(this.getModules());
        updatedModules.add(moduleToAddTo);
        return new Student(name, phone, email, tags, updatedModules, tutorials, studentNumber, telegram);
    }

    @Override
    public Person addTutorial(Tutorial tutorialToAddTo) {
        Set<Module> updatedModules = new HashSet<>(this.getModules());
        updatedModules.add(new Module(tutorialToAddTo.getModuleCode()));
        Set<Tutorial> updatedTutorials = new HashSet<>(this.getTutorials());
        updatedTutorials.add(tutorialToAddTo);
        return new Student(name, phone, email, tags, updatedModules, updatedTutorials, studentNumber, telegram);
    }

    @Override
    public Person removeModule(Module moduleToRemoveFrom) {
        Set<Module> updatedModules = new HashSet<>(this.getModules());
        updatedModules.remove(moduleToRemoveFrom);
        Set<Tutorial> updatedTutorials = new HashSet<>(this.getTutorials());
        updatedTutorials.removeIf(tutorial -> Objects.equals(tutorial.getModuleCode(),
                moduleToRemoveFrom.getModuleCode()));

        return new Student(name, phone, email, tags, updatedModules, updatedTutorials, studentNumber, telegram);
    }

    @Override
    public Person removeTutorial(Tutorial tutorialToRemoveFrom) {
        Set<Tutorial> updatedTutorials = new HashSet<>(this.getTutorials());
        updatedTutorials.remove(tutorialToRemoveFrom);
        return new Student(name, phone, email, tags, modules, updatedTutorials, studentNumber, telegram);
    }

    @Override
    public String format() {
        final StringBuilder builder = new StringBuilder();
        builder.append(this.getName())
                .append("; Phone: ")
                .append(this.getPhone())
                .append("; Email: ")
                .append(this.getEmail())
                .append("; Tags: ");
        this.getTags().forEach(builder::append);
        builder.append("; Modules: ");
        this.getModules().forEach(builder::append);
        builder.append("; Tutorials: ");
        this.getTutorials().forEach(builder::append);
        builder.append("; Student Number: ")
                .append(this.getStudentNumber());
        return builder.toString();
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Student)) {
            return false;
        }

        Student otherPerson = (Student) other;
        return super.equals(otherPerson)
                && studentNumber.equals(otherPerson.studentNumber);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, tags, modules, tutorials, studentNumber, telegram);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("tags", tags)
                .add("modules", modules)
                .add("tutorials", tutorials)
                .add("student number", studentNumber)
                .add("telegram", telegram)
                .toString();
    }
}
