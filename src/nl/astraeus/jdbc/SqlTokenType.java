package nl.astraeus.jdbc;

/**
 * User: rnentjes
 * Date: 4/24/12
 * Time: 9:00 PM
 */
public enum SqlTokenType {
    UNKNOWN, EMPTY, COMMA,
    SELECT, FROM, WHERE, ORDER_BY, GROUP_BY, HAVING,
    AND, OR,
    JOIN,
    NOT, NULL, IS_NULL,
    AS, ON,
    EXISTS,
    EXPRESSION_OPEN, EXPRESSION_CLOSE,
    INSERT_INTO, VALUES,
    UPDATE, SET,
    DELETE_FROM,
    CREATE_TABLE,
    ALTER_TABLE, ADD,
    CASE, WHEN, THEN, ELSE, END
}
