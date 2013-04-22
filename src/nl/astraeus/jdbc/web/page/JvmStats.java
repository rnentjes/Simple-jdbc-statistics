package nl.astraeus.jdbc.web.page;

import nl.astraeus.jdbc.thread.SamplerManager;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * User: rnentjes
 * Date: 4/22/13
 * Time: 8:45 PM
 */
public class JvmStats extends TemplatePage {

    @Override
    public Page processRequest(HttpServletRequest request) {
        if ("startRunning".equals(request.getParameter("action"))) {
            SamplerManager.get().getSampler().startRunning();
        }
        if ("stopRunning".equals(request.getParameter("action"))) {
            SamplerManager.get().getSampler().stopRunning();
        }
        if ("clear".equals(request.getParameter("action"))) {
            SamplerManager.get().getSampler().clear();
        }

        return this;
    }

    @Override
    public Map<String, Object> defineModel(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();

        result.put("running", SamplerManager.get().getSampler().isRunning());
        result.put("samples", SamplerManager.get().getSampler().getSampleCount());
        result.put("totalSamples", SamplerManager.get().getSampler().getSamples());

        return result;
    }
}
