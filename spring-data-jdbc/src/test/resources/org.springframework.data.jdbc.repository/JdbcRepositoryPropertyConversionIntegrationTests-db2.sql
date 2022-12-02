DROP TABLE ENTITY_WITH_COLUMNS_REQUIRING_CONVERSIONS_RELATION;
DROP TABLE ENTITY_WITH_COLUMNS_REQUIRING_CONVERSIONS;

CREATE TABLE ENTITY_WITH_COLUMNS_REQUIRING_CONVERSIONS (
    ID_TIMESTAMP DATETIME NOT NULL PRIMARY KEY,
    BOOL BOOLEAN,
    SOME_ENUM VARCHAR(100),
    BIG_DECIMAL VARCHAR(100),
    BIG_INTEGER BIGINT,
    DATE DATETIME,
    LOCAL_DATE_TIME DATETIME,
    ZONED_DATE_TIME VARCHAR(30)
);

CREATE TABLE ENTITY_WITH_COLUMNS_REQUIRING_CONVERSIONS_RELATION (
    ID_TIMESTAMP DATETIME NOT NULL PRIMARY KEY,
    DATA VARCHAR(100),
    ENTITY_WITH_COLUMNS_REQUIRING_CONVERSIONS TIMESTAMP
);
