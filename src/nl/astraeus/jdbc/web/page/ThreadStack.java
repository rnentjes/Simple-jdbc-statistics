package nl.astraeus.jdbc.web.page;

import nl.astraeus.http.ConnectionHandlerThread;
import nl.astraeus.http.SimpleWebServer;
import nl.astraeus.jdbc.Driver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * User: rnentjes
 * Date: 4/12/12
 * Time: 9:16 PM
 */
public class ThreadStack extends TemplatePage {
    private static Logger logger = LoggerFactory.getLogger(ThreadStack.class);

    private Page previous;
    private String threadName;

    public ThreadStack(Page previous, String threadName) {
        this.previous = previous;
        this.threadName = threadName;
    }

    @Override
    public Page processRequest(HttpServletRequest request) {
        Page result = this;

        if ("back".equals(request.getParameter("action"))) {
            result = previous;
        }

        return result;
    }

    public static class TraceElement {
        private StackTraceElement element;
        private boolean highlight;

        public TraceElement(StackTraceElement element, boolean highlight) {
            this.element = element;
            this.highlight = highlight;
        }

        public StackTraceElement getElement() {
            return element;
        }

        public boolean getHighlight() {
            return highlight;
        }
    }

    @Override
    public Map<String, Object> defineModel(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();
        SimpleWebServer server = Driver.getServer();

        ConnectionHandlerThread thread = null;
        for (ConnectionHandlerThread t : server.getThreads()) {
            if (t.getName().equals(threadName)) {
                thread = t;
                break;
            }
        }

        List<TraceElement> trace = new LinkedList<TraceElement>();

        for (int index = 4; index < thread.getStackTrace().length; index++) {
            boolean hl = thread.getStackTrace()[index].getClassName().startsWith("nl.astraeus");

            trace.add(new TraceElement(thread.getStackTrace()[index], hl));
        }

        result.put("thread", thread);
        result.put("trace", trace);
        result.put("threads", server.getThreads());

        return result;
    }
}
