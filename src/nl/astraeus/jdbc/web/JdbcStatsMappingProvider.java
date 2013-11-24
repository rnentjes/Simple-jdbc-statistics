package nl.astraeus.jdbc.web;

import nl.astraeus.jdbc.web.page.JvmStats;
import nl.astraeus.jdbc.web.page.Login;
import nl.astraeus.jdbc.web.page.QueryOverview;
import nl.astraeus.web.page.MappingProvider;
import nl.astraeus.web.page.PageMapping;

import java.util.HashMap;
import java.util.Map;

/**
 * Date: 11/24/13
 * Time: 10:24 AM
 */
public class JdbcStatsMappingProvider implements MappingProvider {

    private Map<String, Class<?>> mapping = new HashMap<String, Class<?>>();

    public JdbcStatsMappingProvider() {
        mapping.put("queries", QueryOverview.class);
        mapping.put("login", Login.class);
        mapping.put("jvm", JvmStats.class);
    }

    public PageMapping getMapping(String uri) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public PageMapping getLoginPage() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public PageMapping get404Page() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
