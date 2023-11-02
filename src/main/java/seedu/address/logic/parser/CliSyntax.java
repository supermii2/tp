package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_ATTENDANCE = new Prefix("ln/");
    public static final Prefix PREFIX_STUDENT_NUMBER = new Prefix("s/");
    public static final Prefix PREFIX_TELEGRAM = new Prefix("tele/");

    public static final Prefix PREFIX_MODULE = new Prefix("m/");
    // Prefixes here can be changed if needed.
    public static final Prefix PREFIX_TUTORIAL_NAME = new Prefix("tn/");
    public static final Prefix PREFIX_TUTORIAL_TIME = new Prefix("tt/");

    // "Flag Prof"
    public static final Prefix PREFIX_IS_PROF = new Prefix("fp/");
    public static final Prefix PREFIX_ASSIGNMENT = new Prefix("assgn/");


}
