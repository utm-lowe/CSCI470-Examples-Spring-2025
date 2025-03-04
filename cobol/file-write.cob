IDENTIFICATION DIVISION.
PROGRAM-ID. EMPLOYEE-RECORDS.

ENVIRONMENT DIVISION.
INPUT-OUTPUT SECTION.
FILE-CONTROL.
    *> Define the file and its storage location
    SELECT EMPLOYEE-FILE ASSIGN TO "customer.dat"
        ORGANIZATION IS LINE SEQUENTIAL
        ACCESS MODE IS SEQUENTIAL
        FILE STATUS IS FILE-STATUS.

DATA DIVISION.
FILE SECTION.
FD EMPLOYEE-FILE.
01 EMPLOYEE-RECORD-OUT.
   02 EMP-NAME       PIC X(20).
   02 EMP-DEPARTMENT PIC X(15).
   02 EMP-POSITION   PIC X(20).
   02 EMP-SALARY     PIC 9(7)V99.

WORKING-STORAGE SECTION.
01 FILE-STATUS      PIC 9(2).
01 EMPLOYEE-TABLE.
   02 EMPLOYEE OCCURS 5 TIMES INDEXED BY EMP-INDEX.
      03 EMP-TBL-NAME       PIC X(20).
      03 EMP-TBL-DEPARTMENT PIC X(15).
      03 EMP-TBL-POSITION   PIC X(20).
      03 EMP-TBL-SALARY     PIC 9(7)V99.

PROCEDURE DIVISION.
MAIN-PROGRAM.
    *> Initialize employee records with sample data
    MOVE "Alice Johnson"   TO EMP-TBL-NAME(1).
    MOVE "HR"              TO EMP-TBL-DEPARTMENT(1).
    MOVE "Manager"         TO EMP-TBL-POSITION(1).
    MOVE 75000.00          TO EMP-TBL-SALARY(1).

    MOVE "Bob Smith"       TO EMP-TBL-NAME(2).
    MOVE "IT"              TO EMP-TBL-DEPARTMENT(2).
    MOVE "Software Eng."   TO EMP-TBL-POSITION(2).
    MOVE 90000.50          TO EMP-TBL-SALARY(2).

    MOVE "Charlie Brown"   TO EMP-TBL-NAME(3).
    MOVE "Finance"         TO EMP-TBL-DEPARTMENT(3).
    MOVE "Analyst"         TO EMP-TBL-POSITION(3).
    MOVE 68000.25          TO EMP-TBL-SALARY(3).

    MOVE "David White"     TO EMP-TBL-NAME(4).
    MOVE "Marketing"       TO EMP-TBL-DEPARTMENT(4).
    MOVE "Lead"            TO EMP-TBL-POSITION(4).
    MOVE 72000.75          TO EMP-TBL-SALARY(4).

    MOVE "Emma Green"      TO EMP-TBL-NAME(5).
    MOVE "Sales"           TO EMP-TBL-DEPARTMENT(5).
    MOVE "Representative"  TO EMP-TBL-POSITION(5).
    MOVE 65000.00          TO EMP-TBL-SALARY(5).

    *> Open the file for writing
    OPEN OUTPUT EMPLOYEE-FILE.
    IF FILE-STATUS NOT = "00"
        DISPLAY "Error opening file, status: " FILE-STATUS
        STOP RUN.

    *> Write records to the file
    PERFORM WRITE-EMPLOYEES.

    *> Close the file
    CLOSE EMPLOYEE-FILE.

    DISPLAY "Employee records successfully written to customer.dat".
    STOP RUN.

WRITE-EMPLOYEES.
    *> Loop through each employee and write it to the file
    SET EMP-INDEX TO 1.
    PERFORM UNTIL EMP-INDEX > 5
        MOVE EMP-TBL-NAME(EMP-INDEX)       TO EMP-NAME
        MOVE EMP-TBL-DEPARTMENT(EMP-INDEX) TO EMP-DEPARTMENT
        MOVE EMP-TBL-POSITION(EMP-INDEX)   TO EMP-POSITION
        MOVE EMP-TBL-SALARY(EMP-INDEX)     TO EMP-SALARY
        WRITE EMPLOYEE-RECORD-OUT
        SET EMP-INDEX UP BY 1
    END-PERFORM.
