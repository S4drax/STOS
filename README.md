# STOS

Sadrax Terminal Operating System application, mouse is optional (as in: most stuff is made via command prompts, only use mouse if somewhere somehow focus is lost on command line).

## 1) Introduction

Welcome to STOS, or Sadrax Terminal Operating System.

## 2) General

The app was developed for LARPs so that anyone could setup their terminal. 

This is a java application so before running remember to have JAVA installed (for now 1.8).

Also the app was designed with 1920x1080 resolution in mind, tho i am going to add different resolution options along the way.

Roadmap on Trello:

https://trello.com/b/PiKPOvlv/stos

## 3) Commands and Shortcuts

### a) Commands

-help/HELP - Shows in-app help containing commands

-stos/STOS - Shows an ASCII logo

-clear/CLEAR - Clears the main screen.

-back/BACK - Brings you back to main menu.

-add/Add (-m/-r/-s) - Opens a saveable document (-m medical, -r report, -s schematic)

-save/Save FOLDER_NAME FILE_NAME (ACCESS_LEVEL) - Saves the currently viewed saveable document.

-exit/EXIT - Closes the app.


### b) Shortcuts

-ctrl+alt+h while in Login screen -> opens hacking

-ctrl+alt+l while in Main menu screen (or any subfolder) -> logs you out


## 3) JSON explanation

The 'documents' this app shows is based on reading and saving to JSON files.

#### a) directory.json:

```
[ //table of folders
  {
    "folder": {      //folder
      "fname": "",   //folder name
      "fragments": [  //table of fragments (aka documents) in the folder
        {
          "fragment": { //document
            "textBox": {   //document properties
              "positionY": 0, //position Y, should always be 0
              "addHeader": "", //the text that the document has inside
              "level": 5,    //access level for the users
              "style": "",   //style (available styles and header options below)
              "positionX": 0  //position X, should always be 0
            },
            "name": ""  //document name
          }
        }
      ]
    }
  }
] 
```

#### Styles


##### -"doc_compact"

Standard document, compacted so that you can maximise the ammount of text on screen.

addHeader example: "just plain text"


##### -"doc_spread"

Standard document, every second line is empty for design/readability purposes.

addHeader example: "just plain text"


##### -"med_chart"

Medical chart

addHeader example: "NAME%DATE%GENDER%STATUS%CARETAKER%CAUSE_OF_VISIT%THREATMENT[with commas spliting the steps]"


##### -"status_report_c"

Status Report, main text is compacted.

addHeader example: "NAME%DATE%MAIN_TEXT"


##### -"status_report_s"

Status Report, main text has every second line empty.

addHeader example: "NAME%DATE%MAIN_TEXT"


##### -"schematic"

Schematic, for either some in-game mechanic schematics or fluff text.

addHeader example: "NAME%TIME_IT_TAKES%MATERIALS_LIST[commas split]%RESULTS%LORE_TEXT"


##### -"audio"

Audio files, here addHeader has to be the name of a file you want to play with the file extension (i only checked it with .wav files).



#### b) users.json

```
[
    {
        "user": {
            "login": "", //login
            "password": "", //password
	    "access" : ""  //user access level
        }
    }
]
```

## 4) Used Libraries

Zircon: A great library with a purpose to easily make ASCII Roguelike games, here i used it for most of 'visuals'.

https://github.com/Hexworks/zircon


## 5) Contact info:

Either drop comments on here or contact me via discord at Sadrax#1839
