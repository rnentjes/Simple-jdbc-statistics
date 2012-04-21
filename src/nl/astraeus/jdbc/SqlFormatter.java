package nl.astraeus.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.io.StringReader;

/**
 * User: rnentjes
 * Date: 4/18/12
 * Time: 10:10 PM
 */
public class SqlFormatter {
    private final static Logger logger = LoggerFactory.getLogger(SqlFormatter.class);

    public static String getHTMLFormattedSQL(String sql) {
        String tmp = " "+sql.toLowerCase();
        String tab = "    ";
        String ntab = "\n"+tab;

        tmp = tmp.replaceAll(" left outer join ", ntab+blue("LEFT OUTER JOIN "));

        tmp = tmp.replaceAll(" create table ", blue("CREATE TABLE")+ntab);
        tmp = tmp.replaceAll(" insert into ", blue("INSERT INTO")+ntab);
        tmp = tmp.replaceAll(" delete from ", blue("DELETE FROM")+ntab);
        tmp = tmp.replaceAll(" values\\(", "\n"+blue("VALUES")+"(");

        tmp = tmp.replaceAll(" select ", blue("SELECT")+ntab);
        tmp = tmp.replaceAll(" from ", "\n"+blue("FROM")+ntab);
        tmp = tmp.replaceAll(" where ", "\n"+blue("WHERE")+ntab);
        tmp = tmp.replaceAll(" order by ", "\n"+blue("ORDER BY")+ntab);
        tmp = tmp.replaceAll(" group by ", "\n"+blue("GROUP BY")+ntab);
        tmp = tmp.replaceAll(" having ", "\n"+blue("HAVING")+tab);

        tmp = tmp.replaceAll(" update ", blue("UPDATE")+ntab);
        tmp = tmp.replaceAll(" set ", "\n"+blue("SET")+ntab);

        tmp = tmp.replaceAll(" commit", "\n"+blue("COMMIT"));
        tmp = tmp.replaceAll(" rollback", "\n"+blue("ROLLBACK"));
        tmp = tmp.replaceAll(" close", "\n"+blue("CLOSE"));

        tmp = tmp.replaceAll(" case ", ntab+blue("CASE "));
        tmp = tmp.replaceAll(" when ", ntab+blue("WHEN "));
        tmp = tmp.replaceAll(" is ", blue(" IS "));
        tmp = tmp.replaceAll(" then ", blue(" THEN "));
        tmp = tmp.replaceAll(" not ", blue(" NOT "));
        tmp = tmp.replaceAll(" end ", blue(" END "));
        tmp = tmp.replaceAll(" null ", blue(" NULL "));
        tmp = tmp.replaceAll(" else ", blue(" ELSE "));

        tmp = tmp.replaceAll(" on ", blue(" ON "));
        tmp = tmp.replaceAll(" and ", ntab+blue("AND "));
        tmp = tmp.replaceAll(" or ", ntab+blue("OR "));

        tmp = tmp.replaceAll(" as ", blue(" AS "));

        tmp = tmp.replaceAll(", ", ","+ntab);

        return tmp.trim();
    }

    public static String blue(String text) {
        return "<span style=\"color: blue;\">"+text+"</span>";
    }

}
