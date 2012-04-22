package nl.astraeus.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: rnentjes
 * Date: 4/18/12
 * Time: 10:10 PM
 */
public class SqlFormatter {
    private final static Logger logger = LoggerFactory.getLogger(SqlFormatter.class);

    public static String getHTMLFormattedSQL(String sql) {
        StringBuilder tmp = new StringBuilder(" "+sql.toLowerCase());
        String tab = "    ";
        String ntab = "\n"+tab;

        replaceAll(tmp, " left outer join ", ntab+blue("LEFT OUTER JOIN "));

        replaceAll(tmp, " create table ", blue("CREATE TABLE")+ntab);
        replaceAll(tmp, " insert into ", blue("INSERT INTO")+ntab);
        replaceAll(tmp, " delete from ", blue("DELETE FROM")+ntab);
        replaceAll(tmp, " values\\(", "\n"+blue("VALUES")+"(");

        replaceAll(tmp, " select ", blue("SELECT")+ntab);
        replaceAll(tmp, " from ", "\n"+blue("FROM")+ntab);
        replaceAll(tmp, " where ", "\n"+blue("WHERE")+ntab);
        replaceAll(tmp, " order by ", "\n"+blue("ORDER BY")+ntab);
        replaceAll(tmp, " group by ", "\n"+blue("GROUP BY")+ntab);
        replaceAll(tmp, " having ", "\n"+blue("HAVING")+tab);

        replaceAll(tmp, " update ", blue("UPDATE")+ntab);
        replaceAll(tmp, " set ", "\n"+blue("SET")+ntab);

        replaceAll(tmp, " commit", "\n"+blue("COMMIT"));
        replaceAll(tmp, " rollback", "\n"+blue("ROLLBACK"));
        replaceAll(tmp, " close", "\n"+blue("CLOSE"));

        replaceAll(tmp, " case ", ntab+blue("CASE "));
        replaceAll(tmp, " when ", ntab+blue("WHEN "));
        replaceAll(tmp, " is ", blue(" IS "));
        replaceAll(tmp, " then ", blue(" THEN "));
        replaceAll(tmp, " not ", blue(" NOT "));
        replaceAll(tmp, " end ", blue(" END "));
        replaceAll(tmp, " null ", blue(" NULL "));
        replaceAll(tmp, " else ", blue(" ELSE "));

        replaceAll(tmp, " on ", blue(" ON "));
        replaceAll(tmp, " and ", ntab+blue("AND "));
        replaceAll(tmp, " or ", ntab+blue("OR "));

        replaceAll(tmp, " as ", blue(" AS "));

        replaceAll(tmp, ", ", ","+ntab);

        return tmp.toString();
    }

    private static void replaceAll(StringBuilder str, String txt, String repl) {
        int index = -1;

        while((index = str.indexOf(txt)) > -1) {
            str.replace(index, index + txt.length(), repl);
        }
    }

    public static String blue(String text) {
        return "<span style=\"color: blue;\">"+text+"</span>";
    }

}
