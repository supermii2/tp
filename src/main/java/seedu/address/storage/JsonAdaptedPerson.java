package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.module.Module;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Professor;
import seedu.address.model.person.Student;
import seedu.address.model.person.StudentNumber;
import seedu.address.model.person.Telegram;
import seedu.address.model.tag.Tag;
import seedu.address.model.tutorial.Tutorial;

/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";
    private final String name;
    private final String phone;
    private final String email;
    private final List<JsonAdaptedTag> tags = new ArrayList<>();
    private final List<JsonAdaptedModule> modules = new ArrayList<>();
    private final List<JsonAdaptedTutorial> tutorials = new ArrayList<>();
    private final String studentNumber;
    private final String telegram;

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
                             @JsonProperty("email") String email,
                             @JsonProperty("tags") List<JsonAdaptedTag> tags,
                             @JsonProperty("modules") List<JsonAdaptedModule> modules,
                             @JsonProperty("tutorials") List<JsonAdaptedTutorial> tutorials,
                             @JsonProperty("studentNumber") String studentNumber,
                             @JsonProperty("telegram") String telegram) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        if (tags != null) {
            this.tags.addAll(tags);
        }
        if (modules != null) {
            this.modules.addAll(modules);
        }
        if (tutorials != null) {
            this.tutorials.addAll(tutorials);
        }
        this.studentNumber = studentNumber;
        this.telegram = telegram;
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Person source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        tags.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
        modules.addAll(source.getModules().stream()
                .map(JsonAdaptedModule::new)
                .collect(Collectors.toList()));
        tutorials.addAll(source.getTutorials().stream()
                .map(JsonAdaptedTutorial::new)
                .collect(Collectors.toList()));
        if (source instanceof Student) {
            studentNumber = ((Student) source).getStudentNumber().value;
        } else {
            studentNumber = null;
        }
        telegram = source.getTelegram().value;
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Person toModelType() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        final List<Tag> personTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tags) {
            personTags.add(tag.toModelType());
        }

        final List<Module> personModules = new ArrayList<>();
        for (JsonAdaptedModule module : modules) {
            personModules.add(module.toModelType());
        }

        final List<Tutorial> personTutorials = new ArrayList<>();
        for (JsonAdaptedTutorial tutorial : tutorials) {
            personTutorials.add(tutorial.toModelType());
        }

        final StudentNumber modelStudentNumber;
        if (studentNumber == null) {
            modelStudentNumber = null;
        } else {
            modelStudentNumber = new StudentNumber(studentNumber);
        }

        if (telegram == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Telegram.class.getSimpleName()));
        }
        final Telegram modelTelegram = new Telegram(telegram);

        final Set<Tag> modelTags = new HashSet<>(personTags);
        final Set<Module> modelModules = new HashSet<>(personModules);
        final Set<Tutorial> modelTutorials = new HashSet<>(personTutorials);

        if (modelStudentNumber == null) {
            return new Professor(modelName, modelPhone, modelEmail, modelTags,
                    modelModules, modelTutorials, modelTelegram);
        } else {
            return new Student(modelName, modelPhone, modelEmail, modelTags,
                    modelModules, modelTutorials, modelStudentNumber, modelTelegram);
        }
    }
}
