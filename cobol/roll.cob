IDENTIFICATION DIVISION.
PROGRAM-ID. ROLL.

DATA DIVISION.
WORKING-STORAGE SECTION.
01 UNIX-TIME     PIC 9(10).
01 RANDOM-NUMBER PIC 9(9).
01 RANDOM-INITIALIZED PIC X VALUE "N".

LINKAGE SECTION.
01 DIE-ROLL      PIC 9.

PROCEDURE DIVISION USING DIE-ROLL.

           IF RANDOM-INITIALIZED = "N" 
               MOVE "Y" TO RANDOM-INITIALIZED
               CALL "time" USING BY REFERENCE UNIX-TIME
               CALL "srand" USING UNIX-TIME.
CALL "rand" RETURNING RANDOM-NUMBER.

DIVIDE RANDOM-NUMBER BY 6 GIVING RANDOM-NUMBER REMAINDER DIE-ROLL.
ADD 1 TO DIE-ROLL.

