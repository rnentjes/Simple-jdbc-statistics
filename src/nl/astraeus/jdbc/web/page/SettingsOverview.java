package nl.astraeus.jdbc.web.page;

import nl.astraeus.jdbc.web.model.Settings;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * User: rnentjes
 * Date: 4/17/12
 * Time: 9:19 PM
 */
public class SettingsOverview extends TemplatePage {

    @Override
    public Page processRequest(HttpServletRequest request) {
        Page result = this;

        if ("save".equals(request.getParameter("action"))) {
            String nrq = request.getParameter("queries");
            String pq = request.getParameter("formattedQueries");

            if (nrq != null) {
                Settings.get().setNumberOfQueries(Integer.parseInt(nrq));
            }

            if (pq != null) {
                Settings.get().setFormattedQueries(true);
            } else {
                Settings.get().setFormattedQueries(false);
            }
        }

        return result;
    }

    @Override
    public Map<String, Object> defineModel(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();

        int nrq = Settings.get().getNumberOfQueries();
        result.put("q1000", nrq == 1000);
        result.put("q2500", nrq == 2500);
        result.put("q5000", nrq == 5000);
        result.put("q10000", nrq == 10000);
        result.put("q15000", nrq == 15000);
        result.put("q25000", nrq == 25000);

        result.put("formattedQueries", Settings.get().isFormattedQueries());

        return result;
    }
}
