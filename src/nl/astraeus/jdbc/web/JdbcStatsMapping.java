package nl.astraeus.jdbc.web;

import nl.astraeus.jdbc.web.page.*;
import nl.astraeus.web.page.Page;
import nl.astraeus.web.page.PageMapping;

/**
 * Date: 11/24/13
 * Time: 10:27 AM
 */
public enum JdbcStatsMapping implements PageMapping {
    QUERIES("queries", QueryOverview.class),
    QUERY("query", QueryDetail.class),

    TRANSACTIONS("transactions", TransactionOverview.class),
    TRANSACTION("transaction", TransactionDetail.class),

    JVM("jvm", JvmStats.class),

    SETTINGS("settings", SettingsOverview.class),

    LOGIN("login", Login.class),
    STACKTRACE("stacktrace", ShowStacktrace.class),

    RESOURCES("resources", Resources.class),

    NOTFOUND("login", Login.class),
    MAIN("", QueryOverview.class),
    ;

    private String uri;
    private Class<?> cls;

    private JdbcStatsMapping(String uri, Class<?> cls) {
        this.uri = uri;
        this.cls = cls;
    }

    public String getUri() {
        return uri;
    }

    public Class<? extends Page> getPage() {
        return (Class<? extends Page>) cls;
    }

}
