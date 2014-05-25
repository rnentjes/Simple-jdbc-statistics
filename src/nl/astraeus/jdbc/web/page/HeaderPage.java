package nl.astraeus.jdbc.web.page;

import nl.astraeus.web.page.TemplatePage;

/**
 * Date: 11/17/13
 * Time: 2:31 PM
 */
public class HeaderPage extends TemplatePage {

    @Override
    public void get() {
        if (hasMessages()) {
            set("showMessages", true);
        }
    }

    @Override
    public void post() {
        if (hasMessages()) {
            set("showMessages", true);
        }
    }

}
