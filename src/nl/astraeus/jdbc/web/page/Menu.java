package nl.astraeus.jdbc.web.page;

/**
 * User: rnentjes
 * Date: 3/28/12
 * Time: 9:07 PM
 */
public class Menu extends StatsPage {

    @Override
    public void get() {
        set();
    }

    public void set() {
        set("user", getSession("user", String.class));
    }
}
