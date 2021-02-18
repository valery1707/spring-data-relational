DROP TABLE ADDRESS;
DROP TABLE PERSON;

CREATE TABLE PERSON (
    ID NUMBER GENERATED by default on null as IDENTITY PRIMARY KEY,
    NAME VARCHAR2(100)
);

CREATE TABLE ADDRESS (
    ID NUMBER GENERATED by default on null as IDENTITY PRIMARY KEY,
    STREET VARCHAR2(100),
    PERSON_ID NUMBER);

ALTER TABLE ADDRESS ADD FOREIGN KEY (PERSON_ID) REFERENCES PERSON(ID);