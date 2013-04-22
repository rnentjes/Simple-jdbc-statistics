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

        Settings settings = Settings.get();

        if ("save".equals(request.getParameter("action"))) {
            String nrq = request.getParameter("queries");
            String pq = request.getParameter("formattedQueries");
            String rs = request.getParameter("recordingStacktraces");
            String wsc = request.getParameter("webServerConnections");
            String wsp = request.getParameter("webServerPort");
            String pstart = request.getParameter("packageStart");

            if (wsp != null) {
                try {
                    int port = Integer.parseInt(wsp);

                    if (port < 1025 || port > 65535) {
                        Warnings.get(request).addMessage(Warnings.Message.Type.ERROR, "Error!", "Web server port should be a number between 1025 and 65535.");
                    } else {
                        settings.setWebServerPort(port);
                    }
                } catch (NumberFormatException e) {
                    Warnings.get(request).addMessage(Warnings.Message.Type.ERROR, "Error!", "Web server port should be a number between 1025 and 65535.");
                }
            }

            if (wsc != null) {
                try {
                    int nrcon = Integer.parseInt(wsc);

                    if (nrcon < 1 || nrcon > 25) {
                        Warnings.get(request).addMessage(Warnings.Message.Type.ERROR, "Error!", "Number of web server threads should be a number between 1 and 25.");
                    } else {
                        settings.setWebServerConnections(nrcon);
                    }
                } catch (NumberFormatException e) {
                    Warnings.get(request).addMessage(Warnings.Message.Type.ERROR, "Error!", "Number of web server threads should be a number between 1 and 25.");
                }
            }

            if (nrq != null) {
                try {
                    int nrquery = Integer.parseInt(nrq);

                    if (nrquery < 1 || nrquery > 25000) {
                        Warnings.get(request).addMessage(Warnings.Message.Type.ERROR, "Error!", "Number of queries logged should be a number between 1 and 25000.");
                    } else {
                        settings.setNumberOfQueries(nrquery);
                    }
                } catch (NumberFormatException e) {
                    Warnings.get(request).addMessage(Warnings.Message.Type.ERROR, "Error!", "Number of queries logged should be a number between 1 and 25000.");
                }
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

            if (pstart != null) {
                settings.setPackageStart(pstart);
            }

            if (!Warnings.get(request).hasWarnings()) {
                Warnings.get(request).addMessage(Warnings.Message.Type.SUCCESS, "Success!", "Settings are successfully saved.");
            }
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
        result.put("webServerConnections", String.valueOf(settings.getWebServerConnections()));
        result.put("webServerPort", String.valueOf(settings.getWebServerPort()));
        result.put("packageStart", settings.getPackageStart());

        result.put("jdbcUrl", Settings.get().isSecure() ?
                "jdbc:secstat:"+Settings.get().getSettings()+":<original jdbc url>" :
                "jdbc:stat:"+Settings.get().getSettings()+":<original jdbc url>");

        return result;
    }
}
