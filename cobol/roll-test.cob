IDENTIFICATION DIVISION.
PROGRAM-ID. ROLL-TEST.

DATA DIVISION.
WORKING-STORAGE SECTION.
01 R PIC 9.

PROCEDURE DIVISION.

PERFORM DO-ROLL 10 TIMES.
STOP RUN.


DO-ROLL.
    CALL "ROLL" USING BY REFERENCE R.
    DISPLAY R.
