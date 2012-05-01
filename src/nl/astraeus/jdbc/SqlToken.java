package nl.astraeus.jdbc;

/**
 * User: rnentjes
 * Date: 4/24/12
 * Time: 9:00 PM
 */
public class SqlToken {

    private SqlTokenType type;
    private String text;

    public SqlToken(SqlTokenType type, String text) {
        this.type = type;
        this.text = text;
    }

    public SqlTokenType getType() {
        return type;
    }

    public String getText() {
        return text;
    }
}
