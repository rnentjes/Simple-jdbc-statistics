package nl.astraeus.jdbc.web;

import nl.astraeus.web.page.MappingProvider;
import nl.astraeus.web.page.PageMapping;

/**
 * Date: 11/24/13
 * Time: 10:24 AM
 */
public class JdbcStatsMappingProvider implements MappingProvider {

    public PageMapping getMapping(String uri) {
        for (JdbcStatsMapping mapping : JdbcStatsMapping.values()) {
            if (uri.startsWith(mapping.getUri())) {
                return mapping;
            }
        }

        return null;    }

    public PageMapping getLoginPage() {
        return JdbcStatsMapping.LOGIN;
    }

    public PageMapping get404Page() {
        return JdbcStatsMapping.NOTFOUND;
    }
}
