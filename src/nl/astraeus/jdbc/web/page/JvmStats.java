package nl.astraeus.jdbc.web.page;

import nl.astraeus.jdbc.thread.SamplerManager;

/**
 * User: rnentjes
 * Date: 4/22/13
 * Time: 8:45 PM
 */
public class JvmStats extends StatsPage {

    @Override
    public void get() {
        set();
    }

    @Override
    public void post() {
        if (getParameter("startRunning") != null) {
            SamplerManager.get().getSampler().startRunning();
        }
        if (getParameter("stopRunning") != null) {
            SamplerManager.get().getSampler().stopRunning();
        }
        if (getParameter("clear") != null) {
            SamplerManager.get().getSampler().clear();
        }
        set();
    }

    public void set() {
        set("running", SamplerManager.get().getSampler().isRunning());
        set("samples", SamplerManager.get().getSampler().getSampleCount());
        set("totalSamples", SamplerManager.get().getSampler().getSamples());
    }
}
