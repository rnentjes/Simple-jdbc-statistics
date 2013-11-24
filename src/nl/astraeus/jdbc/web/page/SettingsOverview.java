package nl.astraeus.jdbc.web.page;

import nl.astraeus.jdbc.web.model.Settings;
import nl.astraeus.web.page.Message;

/**
 * User: rnentjes
 * Date: 4/17/12
 * Time: 9:19 PM
 */
public class SettingsOverview extends StatsPage {

    @Override
    public void get() {
        set();
    }

    @Override
    public void post() {
        Settings settings = Settings.get();

        if (getParameter("save") != null) {
            String nrq = getParameter("queries");
            String pq = getParameter("formattedQueries");
            String rs = getParameter("recordingStacktraces");
            String wsc = getParameter("webServerConnections");
            String wsp = getParameter("webServerPort");
            String pstart = getParameter("packageStart");

            if (wsp != null) {
                try {
                    int port = Integer.parseInt(wsp);

                    if (port < 1025 || port > 65535) {
                        addMessage(Message.Type.ERROR, "Error!", "Web server port should be a number between 1025 and 65535.");
                    } else {
                        settings.setWebServerPort(port);
                    }
                } catch (NumberFormatException e) {
                    addMessage(Message.Type.ERROR, "Error!", "Web server port should be a number between 1025 and 65535.");
                }
            }

            if (wsc != null) {
                try {
                    int nrcon = Integer.parseInt(wsc);

                    if (nrcon < 1 || nrcon > 25) {
                        addMessage(Message.Type.ERROR, "Error!", "Number of web server threads should be a number between 1 and 25.");
                    } else {
                        settings.setWebServerConnections(nrcon);
                    }
                } catch (NumberFormatException e) {
                    addMessage(Message.Type.ERROR, "Error!", "Number of web server threads should be a number between 1 and 25.");
                }
            }

            if (nrq != null) {
                try {
                    int nrquery = Integer.parseInt(nrq);

                    if (nrquery < 1 || nrquery > 25000) {
                       addMessage(Message.Type.ERROR, "Error!", "Number of queries logged should be a number between 1 and 25000.");
                    } else {
                        settings.setNumberOfQueries(nrquery);
                    }
                } catch (NumberFormatException e) {
                    addMessage(Message.Type.ERROR, "Error!", "Number of queries logged should be a number between 1 and 25000.");
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

            if (!hasWarnings()) {
                addMessage(Message.Type.SUCCESS, "Success!", "Settings are successfully saved.");
            }
        }

        set();
    }

    public void set() {
        Settings settings = Settings.get();

        int nrq = settings.getNumberOfQueries();
        set("q1000", nrq == 1000);
        set("q2500", nrq == 2500);
        set("q5000", nrq == 5000);
        set("q10000", nrq == 10000);
        set("q15000", nrq == 15000);
        set("q25000", nrq == 25000);

        set("formattedQueries", settings.isFormattedQueries());
        set("recordingStacktraces", settings.isRecordingStacktraces());
        set("webServerConnections", String.valueOf(settings.getWebServerConnections()));
        set("webServerPort", String.valueOf(settings.getWebServerPort()));
        set("packageStart", settings.getPackageStart());

        set("jdbcUrl", Settings.get().isSecure() ?
            "jdbc:secstat:"+Settings.get().getSettings()+":<original jdbc url>" :
            "jdbc:stat:"+Settings.get().getSettings()+":<original jdbc url>");
    }
}
