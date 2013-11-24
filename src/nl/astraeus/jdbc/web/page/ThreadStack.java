package nl.astraeus.jdbc.web.page;

import nl.astraeus.http.ConnectionHandlerThread;
import nl.astraeus.http.SimpleWebServer;
import nl.astraeus.jdbc.Driver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;

/**
 * User: rnentjes
 * Date: 4/12/12
 * Time: 9:16 PM
 */
public class ThreadStack extends StatsPage {
    private static Logger logger = LoggerFactory.getLogger(ThreadStack.class);

    private String threadName;

    public ThreadStack(String threadName) {
        this.threadName = threadName;
    }

    @Override
    public void get() {
        if ("back".equals(getParameter("action"))) {
            //result = previous;
        }
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

    public void set() {
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

        set("thread", thread);
        set("trace", trace);
        set("threads", server.getThreads());
    }
}
