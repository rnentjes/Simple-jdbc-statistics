package nl.astraeus.jdbc.web.page;

import nl.astraeus.web.page.TemplatePage;
import nl.astraeus.web.page.Warnings;
import nl.astraeus.web.state.Request;

/**
 * Date: 11/17/13
 * Time: 2:31 PM
 */
public class HeaderPage extends TemplatePage {

    @Override
    public void get() {
        if (hasMessages()) {
            set("showMessages", true);
            set("messages", Warnings.getWarnings(Request.get()).getMessages());
        }
    }

}
