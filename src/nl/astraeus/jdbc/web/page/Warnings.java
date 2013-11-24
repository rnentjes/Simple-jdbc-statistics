package nl.astraeus.jdbc.web.page;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * User: rnentjes
 * Date: 3/28/12
 * Time: 9:07 PM
 */
public class Warnings extends TemplatePage implements Serializable {
    private static final long serialVersionUID = 1;

    public static Warnings getWarnings(HttpServletRequest request) {
        Warnings result = (Warnings)request.getAttribute("warnings");

        if (result == null) {
            result = new Warnings();

            request.setAttribute("warnings", result);
        }

        return result;
    }

    public static class Message implements Serializable {
        private static final long serialVersionUID = 1;

        private static int ids = 1;

        public static enum Type {
            SUCCESS,
            INFO,
            WARNING,
            ERROR
        }

        public boolean success = false;
        public boolean info = false;
        public boolean warning = false;
        public boolean error = false;

        public String header;
        public String body;
        private int id;

        public Message(Type type, String header, String body) {
            switch(type) {
                case SUCCESS:
                    success = true;
                    break;
                case INFO:
                    info = true;
                    break;
                case WARNING:
                    warning = true;
                    break;
                case ERROR:
                    error = true;
                    break;
            }

            this.header = header;
            this.body = body;
            this.id = ids++;
        }

        public boolean getSuccess() {
            return success;
        }

        public boolean getInfo() {
            return info;
        }

        public boolean getWarning() {
            return warning;
        }

        public boolean getError() {
            return error;
        }

        public String getHeader() {
            return header;
        }

        public String getBody() {
            return body;
        }

        public int getId() {
            return id;
        }
    }

    private List<Message> messages = new LinkedList<Message>();

    public void addMessage(Message.Type type, String header, String body) {
        messages.add(new Message(type, header, body));
    }

    public boolean hasMessages() {
        return !messages.isEmpty();
    }

    public boolean hasWarnings() {
        boolean result = false;

        for (Message message : messages) {
            if (message.getError() || message.getWarning()) {
                result = true;
                break;
            }
        }

        return result;
    }

    public synchronized void add(Warnings other) {
        if (other.messages != null) {
            messages.addAll(other.messages);
        }
    }

    @Override
    public void render(OutputStream out) throws IOException {
        set("messages", messages);

        super.render(out);
    }

}
