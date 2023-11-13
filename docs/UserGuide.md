---
layout: default.md
title: "User Guide"
pageNav: 3
---

# **TAssistant User Guide**

## **Table of Contents**

- **[Introduction](#introduction)**
- **[Quick Start](#quick-start)**
- **[Layout](#layout)**
- **[Features](#features)**
- **[Command summary](#command-summary)**
- **[Known issues](#known-issues)**
- **[Glossary](#glossary)**
- **[FAQ](#faq)**

--------------------------------------------------------------------------------------------------------------------

## **Introduction**

TAssistant is a desktop app for Teaching Assistants (TAs) in NUS School of Computing who are taking
multiple modules and tutorials with a large class size. It aims to help TAs track information more
easily about the students and professors involved in tutoring.

If you are experienced in using a Command Line Interface (CLI) and can type quickly, you will be able to
handle contact organisation faster than GUI apps.

<!-- @@author izzahaj-reused
Table Reused from (https://github.com/AY2223S1-CS2103T-T12-4/tp/blob/master/docs/UserGuide.md)
!-->

Here are some symbols used throughout this user guide:

| Symbol               | Meaning                                  |
|----------------------|------------------------------------------|
| :information_source: | Important things you should take note of |
| :bulb:               | Useful information                       |
| :exclamation:        | Warning                                  |

--------------------------------------------------------------------------------------------------------------------

<div style="page-break-after: always;"></div>

## **Quick start**

1. Ensure you have Java `11` or above installed in your Computer.

2. Download the latest `TAssistant.jar` from [here](https://github.com/AY2324S1-CS2103T-F12-3/tp/releases).

3. Copy the file to the folder you want to use as the _home folder_ for your TAssistant.

4. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar TAssistant.jar` command to run the application.<br>

5. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

    * `list` : Lists all contacts.

    * `delete 3` : Deletes the 3rd contact shown in the current list.

    * `clear` : Deletes all contacts.

    * `exit` : Exits the app.


6. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

<div style="page-break-after: always;"></div>

## **Layout**

![layout image](images/GUI.png)

1. Module List: This is where all modules that you have added to the program will appear.
2. Tutorial List: This is where all tutorials that you have added to the program will appear.
3. Command Box: This is where you type in your commands. After typing the command, press `enter` to execute them.
4. Output Box: This is where the program will provide feedback to your commands. When launching the program, the
output box will be empty, but after executing a command, successful or otherwise, the feedback of the program, such as
telling you that it doesn't recognise your command via an "Invalid command" message, will appear.
5. Person List: The list of people TAssistant is showing you. When launching the program, his will be everyone
TAssistant has data on, but you may filter the list of people with commands like
[find](#locating-persons-by-name-find) and [search](#searching-for-persons--search).
6. Assignment List: The list of assignments you have.

--------------------------------------------------------------------------------------------------------------------

<div style="page-break-after: always;"></div>

## **Features**

**Notes about the command format:**

* Words in `UPPER_CASE` are the parameters to be supplied.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.<br>
  e.g. `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order, except if the command requires an index. Indexes must always be put at the front. <br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.
  e.g. if the command specifies `1 n/NAME`, `n/NAME 1` is NOT acceptable and may lead the program to read n/NAME as the index.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
  </box>

### Viewing help : `help`

Shows a message explaining how to access the help page.

![help message](images/newHelpMessage.png)

Format: `help`

### Adding a person: `add`

Adds a person to TAssistant.

Format: `add n/NAME p/PHONE_NUMBER e/EMAIL [s/STUDENT_NUMBER] [tele/TELEGRAM_HANDLE]`
* Currently, the telegram handle is unrestricted, however, restrictions such as having the handle only start withh `@` will be implemented in the future.

**Tip:** A person can have any number of tags (including 0)
</box>

Examples:
* `add n/Judas p/98768432 e/judas@example.com s/A1234567J tele/@judas`
* `add n/John Doe p/98765432 e/johnd@example.com s/A0240253J tele/@john t/friends`

### Listing all persons : `list`

Shows a list of all persons in TAssistant.

Format: `list`

### Editing a person : `edit`

Edits an existing person in the address book.


Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [s/STUDENT_NUMBER] [tele/TELEGRAM_HANDLE] [t/TAG]…​`

* Edits the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the person will be removed i.e. adding of tags is not cumulative.
* You can remove all the person’s tags by typing `t/` without
    specifying any tags after it.

Examples:
*  `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st person to be `91234567` and `johndoe@example.com` respectively.
*  `edit 2 n/Betsy Crower t/` Edits the name of the 2nd person to be `Betsy Crower` and clears all existing tags.
*  `edit 3 s/A0514624K tele/@JP` Edits the student number of the 2nd person to be `A0514624K` and telegram handle to be `@JP`.

### Locating persons by name: `find`

Finds persons whose names contain any of the given keywords.

Format: `find KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g. `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Only the name is searched.
* Only full words will be matched e.g. `Han` will not match `Hans`
* Persons matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`

Examples:
* `find John` returns `john` and `John Doe`
* `find alex david` returns `Alex Yeoh`, `David Li`<br>
  ![result for 'find alex david'](images/findAlexDavidResult.png)

### Deleting a person : `delete`

Deletes the specified person from TAssistant.

Format: `delete INDEX`

* Deletes the person at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `delete 2` deletes the 2nd person in TAssistant.

### Searching for persons : `search`

Lists out all persons with the given condition.

Format: `search CONDITION1, CONDITION2, …`

* Condition is given in the format keyword: value.
* Accepted condition keywords are module, tutorial name, person name and tags.
* Keywords are case-sensitive, only NAME is case-insensitive.
* Unrecognised keywords cause this method to fail.
* Searching with no conditions will return no users.
* Searching by name is limited to 1 name per search.
* Searching by tutorial must include exactly 1 module.
* Preamble must be empty.

Examples:
* `search m/CS2103T` returns all persons in the module CS2103T
* `search m/CS2103T tn/T03` returns all persons in the tutorial T03 in the module CS2103T
* `search n/John Doe` returns all persons with the name John Doe
* `search t/student` returns all persons with the tag student

### Clearing all entries : `clear`

Clears all entries from TAssistant.

Format: `clear`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

TAssistant data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

TAssistant data are saved automatically as a JSON file `[JAR file location]/data/addressbook.json`.
Advanced users are welcome to update data directly by editing that data file.

:exclamation: If your changes to the data file makes its format invalid, TAssistant will discard all data and start with an empty data file at the next run.
Hence, it is recommended to take a backup of the file before editing it.

### Adding a module to TAssistant: `addModule`

Adds the module to TAssistant, displaying it on the GUI.
The module name must consist of at least 5 characters, which must be alphanumeric.
Modules must be added one at a time.

The output for module name inputs "cs2100" and "CS2100" will be the same as the module
shown on the GUI will be capitalised.

Will give an error message if the module already exists in TAssistant,
further prompts are given for incorrect inputs.

Format: `addModule m/MODULE_NAME`

Example: `addModule m/CS2100`

### Deleting a module from TAssistant: `deleteModule`

Deletes the module from TAssistant and the GUI will change to reflect this.
The index refers to the position of the module displayed on the GUI.

:exclamation: Any related tutorials on the GUI as well as modules/tutorials on persons
will also be deleted. As such, please use this command with discretion.

Will give an error message if the module does not exist in TAssistant,
further prompts are given for incorrect inputs.

Format: `deleteModule INDEX`

Example: `deleteModule 1`

### Adding a person to a given module: `addToModule`

Adds the person at the specified INDEX displayed on the GUI to a given module.
The index refers to the index number shown in the displayed user list. The index must be a positive integer.
Will give an error message if the module given has not been made.

Format: `addToModule INDEX m/MODULE_NAME`

Example: `addToModule 1 m/CS1000` adds the person who is at the top in the current list to the module
CS1000, if the module CS1000 exists.

### Removing a person from a given module: `removeFromModule`

Removes the user at the specified INDEX displayed on the GUI from a given module.
The index refers to the index number shown in the displayed user list. The index must be a positive integer.

:exclamation: Doing this also removes any tutorials that the user has which are part of the given module.

Format: `removeFromModule INDEX m/MODULE_NAME`

Example: `removeFromModule 1 m/CS1000` removes the person who is at the top in the current list from the module
CS1000, if the module CS1000 exists and the person at the top in the current list is part of the module CS1000.

### Adding a tutorial to a given module: `addTutorial`

Adds a tutorial with a specific tutorial name and time to a given module.
Note that:
- Different modules can have different tutorials with the same name.
- Tutorial time
  - should follow `DAY_OF_THE_WEEK 12_HOUR_CLOCK_TIME` format.
  - space between DAY_OF_THE_WEEK and 12_HOUR_CLOCK_TIME is **compulsory**.
  - is **not** case-sensitive.
  - e.g. `Mon 6PM`, `Mon 6pm`, `mon 6PM`, `Monday 6PM` are all valid, but `Mon 0PM` is not valid.
- Will give an error message if:
  1. The module does not exist;
  2. Tutorial with the same name has already been made for the module;
  3. TUTORIAL_TIME is invalid.

Format: `addTutorial m/MODULE_NAME tn/TUTORIAL_NAME tt/TUTORIAL_TIME`

Example: `addTutorial m/CS1000 tn/T12 tt/Mon 6PM` adds a tutorial with name T12 and time "Mon 6PM" belonging to module
CS1000, if module CS1000 exists.

### Deleting a tutorial from a given module: `deleteTutorial`

Deletes a tutorial.
The index refers to the position of the tutorial in the displayed tutorial list, with the leftmost tutorial being index 1.
The index **must be a positive integer** 1, 2, 3...
Will give an error message if the amount of tutorials is less than the index.

:exclamation: Doing this removes the tutorial from everyone in the address book.

Format: `deleteTutorial INDEX`

Example: `deleteTutorial 2` removes the second-leftmost tutorial from the tutorial list, if it exists.

### Adding a person to a given tutorial: `addToTutorial`

Adds a given user to the stated tutorial.
The index refers to the position of the given person as displayed in the GUI and **must be a positive integer** 1, 2, 3...
Fails if the index is not valid.

Format: `addToTutorial INDEX m/MODULE_NAME tn/TUTORIAL_NAME`

Example: `addToTutorial 1 m/CS2103T tn/T11`

### Removing a person from a given tutorial: `removeFromTutorial`

Removes a given person from the stated tutorial.
The index refers to the position of the given person as displayed in the GUI and **must be a positive integer** 1, 2, 3...
Fails if the index is not valid.

Format: `removeFromTutorial INDEX m/MODULE_NAME tn/TUTORIAL_NAME`

Example: `removeFromTutorial 1 m/CS2103T tn/T11`

### Recording attendance of a given person: `attn`

Records a person's attendance and displays the change by adding a tag on the GUI.
The index refers to the position of the person as displayed in the GUI.
The lesson number must be alphanumeric.

An error is thrown if the lesson number already exists.
Further prompts are given for incorrect inputs.

Format: `attn INDEX ln/LESSON_NUMBER`

Example: `attn 1 ln/S1`

### Deleting attendance of a given person: `deleteAttn`

Deletes a person's attendance and displays the change by removing the tag on the GUI.
The index refers to the position of the person as displayed in the GUI.
The lesson number must be alphanumeric.

Only tags added using `attn` can be deleted with this command.

Likewise, tags not added using `attn` cannot be deleted with this command.

An error is thrown if :
* The given LESSON_NUMBER does not exist.
* The given LESSON_NUMBER exists but was not added using `attn`.

Further prompts are given for incorrect inputs.

Format: `deleteAttn INDEX ln/LESSON_NUMBER`

Example: `deleteAttn 1 ln/S1`

### Adding an assignment `addAssignment`

Adds a new assignment to the system. Fails if the name given is blank. Currently, TAssistant only supports
addition of assignments to the system, however, editing and removing functionality will be
implemented in the future.

Format: `addAssignment assgn/ASSIGNMENT_TITLE`

Example: `addAssignment assgn/Assignment 1`

--------------------------------------------------------------------------------------------------------------------

<div style="page-break-after: always;"></div>

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen,
   and later switch to using only the primary screen, the GUI will open off-screen.
   To resolve this, delete the `preferences.json` file created by the application before running the application again.

--------------------------------------------------------------------------------------------------------------------

<div style="page-break-after: always;"></div>

## Command summary

| Action                 | Format, Examples                                                                                                                                                      |
|------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Add**                | `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​` <br> e.g., `add n/Judas p/98768432 e/judas@example.com s/A1234567J tele/@judas`                               |
| **Clear**              | `clear`                                                                                                                                                               |
| **Delete**             | `delete INDEX`<br> e.g., `delete 3`                                                                                                                                   |
| **Edit**               | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [s/STUDENT_NUMBER] [tele/TELEGRAM_HANDLE] [t/TAG]…​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com` |
| **Find**               | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`                                                                                                            |
| **List**               | `list`                                                                                                                                                                |
| **Search**             | `search CONDITION1, CONDITION2`<br> e.g., `search module: CS2103T`                                                                                                    |
| **Help**               | `help`                                                                                                                                                                |
| **addModule**          | `addModule m/MODULE_NAME` <br> e.g., `addModule m/CS2100`                                                                                                             |
| **deleteModule**       | `deleteModule INDEX` <br> e.g., `deleteModule 1`                                                                                                                      |
| **addToModule**        | `addToModule INDEX m/MODULE_NAME` <br> e.g., `addToModule 2 m/CS2103T`                                                                                                |
| **removeFromModule**   | `removeFromModule INDEX m/MODULE_NAME` <br> e.g., `removeFromModule 2 m/CS2103T`                                                                                      |
| **addTutorial**        | `addTutorial TUTORIALNAME m/MODULE_NAME` <br> e.g., `addTutorial tn/c12 m/CS2103T`                                                                                    |
| **deleteTutorial**     | `deleteTutorial INDEX` <br> e.g., `deleteTutorial 2`                                                                                                                  |
| **addToTutorial**      | `addToTutorial INDEX m/MODULE_NAME tn/TUTORIAL_NAME` <br> e.g., `addToTutorial 1 m/CS2103T tn/T11`                                                                    |
| **removeFromTutorial** | `removeFromTutorial INDEX m/MODULE_NAME tn/TUTORIAL_NAME` <br> e.g., `removeFromTutorial 1 m/CS2103T tn/T11`                                                          |
| **attn**               | `attn INDEX ln/LESSON_NUMBER` <br> e.g., `attn 1 ln/S1`                                                                                                               |
| **deleteAttn**         | `deleteAttn INDEX ln/LESSON_NUMBER` <br> e.g., `deleteAttn 1 ln/S1`                                                                                                   |
| **addAssignment**      | `addAssignment assgn/ASSIGNMENT_TITLE` <br> e.g., `addAssignment assgn/Project`                                                                                       |


--------------------------------------------------------------------------------------------------------------------

<div style="page-break-after: always;"></div>

## Glossary

* **Mainstream OS**: Windows, Linux, Unix, OS-X
* **Teaching Assistant (TA)**: A non-professor tutor which helps the professors of a module by teaching one or more tutorials.
* **Module**: A unit of study in a specific field set by NUS, such as CS2103T focusing on Software Engineering.
* **Tutorial**: Lessons that are part of a module. Includes not only tutorials,
but also laboratories, recitations and any form of lesson that includes a TA.

--------------------------------------------------------------------------------------------------------------------

## **FAQ**

**Q**: How do I transfer my data to another computer?<br>
**A**: Install the app on the other computer and overwrite the empty data file it creates with the file that contains
the data of your previous TAssistant home folder.

--------------------------------------------------------------------------------------------------------------------
