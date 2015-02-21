package nl.astraeus.jdbc.web.page;

import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        List<TraceElement> trace = new LinkedList<TraceElement>();

//        for (int index = 4; index < thread.getStackTrace().length; index++) {
//            boolean hl = thread.getStackTrace()[index].getClassName().startsWith("nl.astraeus");
//
//            trace.add(new TraceElement(thread.getStackTrace()[index], hl));
//        }
//
//        set("thread", thread);
        set("trace", trace);
        set("threads", new LinkedList()); //server.getThreads());
    }
}
