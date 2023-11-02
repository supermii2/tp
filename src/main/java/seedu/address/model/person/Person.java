package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.EditCommand;
import seedu.address.model.module.Module;
import seedu.address.model.tag.Tag;
import seedu.address.model.tutorial.Tutorial;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {
    // Identity fields
    protected final Name name;
    protected final Phone phone;
    protected final Email email;

    // Data fields
    protected final Set<Tag> tags = new HashSet<>();
    protected final Set<Module> modules = new HashSet<>();
    protected final Set<Tutorial> tutorials = new HashSet<>();

    protected final Telegram telegram;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Set<Tag> tags,
                  Set<Module> modules, Set<Tutorial> tutorials,
                  Telegram telegram) {
        requireAllNonNull(name, phone, email, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.tags.addAll(tags);
        this.modules.addAll(modules);
        this.tutorials.addAll(tutorials);
        this.telegram = telegram;
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Module> getModules() {
        return Collections.unmodifiableSet(modules);
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tutorial> getTutorials() {
        return Collections.unmodifiableSet(tutorials);
    }

    public Set<String> getUiOfModulesAndTutorials() {
        /* LinkedHashSet used over HashSet because it maintains insertion order, so user will see all tutorials first,
        then all modules without tutorials. */
        Set<String> uiList = new LinkedHashSet<>();
        Set<String> moduleCodesWithTutorials = new HashSet<>();
        for (Tutorial tutorial : this.getTutorials()) {
            moduleCodesWithTutorials.add(tutorial.getModuleCode());
            uiList.add(tutorial.toString());
        }
        for (Module module : this.getModules()) {
            if (!moduleCodesWithTutorials.contains(module.getModuleCode())) {
                uiList.add(module.toString());
            }
        }
        return uiList;
    }

    public Telegram getTelegram() {
        return telegram;
    }

    public Person changeTags(Set<Tag> updatedTags) {
        return new Person(name, phone, email, updatedTags, modules, tutorials, telegram);
    }

    /**
     * Creates a new Person with the given  {@code EditPersonDescriptor}
     * and returns it
     */
    public Person editPerson(EditCommand.EditPersonDescriptor editPersonDescriptor) {
        Name updatedName = editPersonDescriptor.getName().orElse(this.getName());
        Phone updatedPhone = editPersonDescriptor.getPhone().orElse(this.getPhone());
        Email updatedEmail = editPersonDescriptor.getEmail().orElse(this.getEmail());
        Set<Tag> updatedTags = editPersonDescriptor.getTags().orElse(this.getTags());
        Set<Module> updatedModules = this.getModules();
        Set<Tutorial> updatedTutorials = this.getTutorials();
        Telegram updatedTelegram = editPersonDescriptor.getTelegram().orElse(this.getTelegram());

        return new Person(updatedName, updatedPhone, updatedEmail, updatedTags, updatedModules,
        updatedTutorials, updatedTelegram);
    }

    /**
     * Creates a new Person, adds the given {@code Module}
     * and returns it
     */
    public Person addModule(Module moduleToAddTo) {
        Set<Module> updatedModules = new HashSet<>(this.getModules());
        updatedModules.add(moduleToAddTo);
        return new Person(name, phone, email, tags, updatedModules, tutorials, telegram);
    }

    /**
     * Creates a new Person, adds the given {@code Tutorial}
     * and returns it
     */
    public Person addTutorial(Tutorial tutorialToAddTo) {
        Set<Module> updatedModules = new HashSet<>(this.getModules());
        updatedModules.add(new Module(tutorialToAddTo.getModuleCode()));
        Set<Tutorial> updatedTutorials = new HashSet<>(this.getTutorials());
        updatedTutorials.add(tutorialToAddTo);
        return new Person(name, phone, email, tags, updatedModules, updatedTutorials, telegram);
    }

    /**
     * Creates a new Person, removes the given {@code Module}
     * and returns it
     */
    public Person removeModule(Module moduleToRemoveFrom) {
        Set<Module> updatedModules = new HashSet<>(this.getModules());
        updatedModules.remove(moduleToRemoveFrom);
        Set<Tutorial> updatedTutorials = new HashSet<>(this.getTutorials());
        updatedTutorials.removeIf(tutorial -> Objects.equals(tutorial.getModuleCode(),
                moduleToRemoveFrom.getModuleCode()));
        return new Person(name, phone, email, tags, updatedModules, updatedTutorials, telegram);
    }

    /**
     * Creates a new Person, removes the given {@code Tutorial}
     * and returns it
     */
    public Person removeTutorial(Tutorial tutorialToRemoveFrom) {
        Set<Tutorial> updatedTutorials = new HashSet<>(this.getTutorials());
        updatedTutorials.remove(tutorialToRemoveFrom);
        return new Person(name, phone, email, tags, modules, updatedTutorials, telegram);
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName());
    }

    /**
     * Formats the Person
     */
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
        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && tags.equals(otherPerson.tags)
                && modules.equals(otherPerson.modules)
                && tutorials.equals(otherPerson.tutorials)
                && telegram.equals(otherPerson.telegram);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, tags, modules, tutorials, telegram);
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
                .add("telegram", telegram)
                .toString();
    }
}
