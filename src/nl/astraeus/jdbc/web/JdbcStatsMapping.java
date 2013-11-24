package nl.astraeus.jdbc.web;

import nl.astraeus.jdbc.web.page.JvmStats;
import nl.astraeus.jdbc.web.page.Login;
import nl.astraeus.jdbc.web.page.QueryOverview;
import nl.astraeus.web.page.Page;
import nl.astraeus.web.page.PageMapping;

/**
 * Date: 11/24/13
 * Time: 10:27 AM
 */
public enum JdbcStatsMapping implements PageMapping {
    QERIES("queries", QueryOverview.class),
    LOGIN("login", Login.class),
    JVM("jvm", JvmStats.class),
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
