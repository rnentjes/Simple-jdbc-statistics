package nl.astraeus.jdbc;

/**
 * User: riennentjes
 * Date: Jul 10, 2008
 * Time: 8:32:49 PM
 */
public enum QueryType {
    PLAIN("plain"),
    PREPARED("prepared"),
    CALLABLE("callable"),
    STATEMENT("statement"),
    UNKNOWN("unknown"),
    BATCH("batch");

    private String description;

    QueryType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
