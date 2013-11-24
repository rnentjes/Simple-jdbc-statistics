package nl.astraeus.jdbc.web.page;

import nl.astraeus.jdbc.JdbcLogger;
import nl.astraeus.jdbc.web.model.TransactionEntry;
import nl.astraeus.web.page.Message;
import nl.astraeus.web.page.Page;

/**
 * User: rnentjes
 * Date: 5/2/12
 * Time: 2:16 PM
 */
public class TransactionDetail extends StatsPage {
    
    private TransactionEntry transaction;
    
    public TransactionDetail(String transactionEntry) {
        //this.transaction = entry;
    }

    @Override
    public void get() {
        Page result = this;

        if ("stacktrace".equals(getParameter("action"))) {
            long timestamp = Long.parseLong(getParameter("actionValue"));
            
            JdbcLogger.LogEntry found = null;
            for (JdbcLogger.LogEntry entry : transaction.getQueries()) {
                if (entry.getTimestamp() == timestamp) {
                    found = entry;
                    break;
                }
            }
    
            if (found != null) {
                //redirect();
                //result = new ShowStacktrace(this, found);
            } else {
                addMessage(Message.Type.ERROR, "Stacktrace not found!", "Query stacktrace not found!");
            }
        } else if ("back".equals(getParameter("action"))) {
            // redirect back
//            result = previous;
        }
    }

    public void set() {
        set("transaction", transaction);
    }

}
