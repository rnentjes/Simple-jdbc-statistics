package nl.astraeus.jdbc.web.page;

import nl.astraeus.jdbc.JdbcLogger;
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

        Settings settings = Settings.get();

        if ("save".equals(request.getParameter("action"))) {
            String nrq = request.getParameter("queries");
            String pq = request.getParameter("formattedQueries");
            String rs = request.getParameter("recordingStacktraces");

            if (nrq != null) {
                settings.setNumberOfQueries(Integer.parseInt(nrq));
            }

            if (pq != null) {
                settings.setFormattedQueries(true);
            } else {
                settings.setFormattedQueries(false);
            }

            if (rs != null) {
                settings.setRecordingStacktraces(true);
            } else {
                settings.setRecordingStacktraces(false);
            }

            Warnings.get(request).addMessage(Warnings.Message.Type.SUCCESS, "Success!", "Settings are successfully saved.");
        }

        return result;
    }

    @Override
    public Map<String, Object> defineModel(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();
        Settings settings = Settings.get();

        int nrq = settings.getNumberOfQueries();
        result.put("q1000", nrq == 1000);
        result.put("q2500", nrq == 2500);
        result.put("q5000", nrq == 5000);
        result.put("q10000", nrq == 10000);
        result.put("q15000", nrq == 15000);
        result.put("q25000", nrq == 25000);

        result.put("formattedQueries", settings.isFormattedQueries());
        result.put("recordingStacktraces", settings.isRecordingStacktraces());

        return result;
    }
}
