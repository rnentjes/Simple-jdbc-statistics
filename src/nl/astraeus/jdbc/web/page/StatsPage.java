package nl.astraeus.jdbc.web.page;

import nl.astraeus.web.page.Page;
import nl.astraeus.web.page.TemplatePage;

/**
 * User: rnentjes
 * Date: 11/24/13
 * Time: 2:12 PM
 */
public class StatsPage extends TemplatePage {

    @Override
    public Page getHeader() {
        return new HeaderPage();
    }

    @Override
    public Page getFooter() {
        return new FooterPage();
    }
}
