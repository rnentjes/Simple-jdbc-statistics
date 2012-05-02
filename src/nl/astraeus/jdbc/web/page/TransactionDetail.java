package nl.astraeus.jdbc.web.page;

import nl.astraeus.jdbc.JdbcLogger;
import nl.astraeus.jdbc.web.model.TransactionEntry;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * User: rnentjes
 * Date: 5/2/12
 * Time: 2:16 PM
 */
public class TransactionDetail extends TemplatePage {
    
    private Page previous;
    private TransactionEntry transaction;
    
    public TransactionDetail(Page previous, TransactionEntry entry) {
        this.previous = previous;
        this.transaction = entry;
    }

    @Override
    public Page processRequest(HttpServletRequest request) {
        Page result = this;

        if ("stacktrace".equals(request.getParameter("action"))) {
            long timestamp = Long.parseLong(request.getParameter("actionValue"));
            
            JdbcLogger.LogEntry found = null;
            for (JdbcLogger.LogEntry entry : transaction.getQueries()) {
                if (entry.getTimestamp() == timestamp) {
                    found = entry;
                    break;
                }
            }
    
            if (found != null) {
                result = new ShowStacktrace(this, found);
            } else {
                Warnings.get(request).addMessage(Warnings.Message.Type.ERROR, "Stacktrace not found!", "Query stacktrace not found!");
            }
        } else if ("back".equals(request.getParameter("action"))) {
            result = previous;
        }
        
        return result;
    }

    @Override
    public Map<String, Object> defineModel(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();

        result.put("transaction", transaction);
        
        return result;
    }

}
