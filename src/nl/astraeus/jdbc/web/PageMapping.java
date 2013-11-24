package nl.astraeus.jdbc.web;

import nl.astraeus.jdbc.web.page.Page;
import nl.astraeus.jdbc.web.page.QueryOverview;

/**
 * User: rnentjes
 * Date: 10/16/13
 * Time: 4:42 PM
 */
public enum PageMapping {
    MAIN("", QueryOverview.class)
    ;

    private final String uri;
    private final Class<? extends Page> cls;

    private PageMapping(String uri, Class<? extends Page> cls) {
        this.uri = uri;
        this.cls = cls;
    }

    public String getUri() {
        return uri;
    }

    public Class<? extends Page> getCls() {
        return cls;
    }

    public static Class<? extends Page> getPage(String uri) {
        for (PageMapping mapping : PageMapping.values()) {
            if (mapping.getUri().equals(uri)) {
                return mapping.getCls();
            }
        }

        return null;
    }

}
