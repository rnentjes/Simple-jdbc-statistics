package nl.astraeus.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: rnentjes
 * Date: 4/18/12
 * Time: 10:10 PM
 */
public class SqlFormatter {
    private final static Logger logger = LoggerFactory.getLogger(SqlFormatter.class);

    private static SqlTokenizer tokenizer = new SqlTokenizer();

    private static enum Newline {
        PRE,
        POST,
        PREPOST,
        NONE
    }

    private static class LayoutMapping {
        int preIndent;
        int postIndent;
        Newline nl;

        private LayoutMapping() {
            preIndent = 0;
            postIndent = 0;
            nl = Newline.NONE;
        }

        private LayoutMapping(int preIndent, int postIndent) {
            this.preIndent = preIndent;
            this.postIndent = postIndent;

            nl = Newline.NONE;
        }

        private LayoutMapping(Newline nl) {
            preIndent = 0;
            postIndent = 0;

            this.nl = nl;
        }

        private LayoutMapping(int preIndent, int postIndent, Newline nl) {
            this.preIndent = preIndent;
            this.postIndent = postIndent;

            this.nl = nl;
        }

        public boolean preNewline() {
            return nl == Newline.PRE || nl == Newline.PREPOST;
        }

        public boolean postNewline() {
            return nl == Newline.POST || nl == Newline.PREPOST;
        }

        public int getPreIndent() {
            return preIndent;
        }

        public int getPostIndent() {
            return postIndent;
        }
    }

    private static Map<SqlTokenType, LayoutMapping> mapping = new HashMap<SqlTokenType, LayoutMapping>();
    private static LayoutMapping defaultMapping = new LayoutMapping();

    static {
        //mapping.put(SqlTokenType.EXPRESSION_OPEN, new LayoutMapping(0, 0, Newline.PRE));
        //mapping.put(SqlTokenType.EXPRESSION_CLOSE, new LayoutMapping(0, 0, Newline.POST));

        mapping.put(SqlTokenType.SELECT, new LayoutMapping(-1, 1, Newline.POST));
        mapping.put(SqlTokenType.FROM, new LayoutMapping(-1, 1, Newline.PREPOST));
        mapping.put(SqlTokenType.WHERE, new LayoutMapping(-1, 1, Newline.PREPOST));
        mapping.put(SqlTokenType.ORDER_BY, new LayoutMapping(-1, 1, Newline.PREPOST));
        mapping.put(SqlTokenType.GROUP_BY, new LayoutMapping(-1, 1, Newline.PREPOST));
        mapping.put(SqlTokenType.HAVING, new LayoutMapping(-1, 1, Newline.PREPOST));

        mapping.put(SqlTokenType.UPDATE, new LayoutMapping(-1, 1, Newline.POST));
        mapping.put(SqlTokenType.SET, new LayoutMapping(-1, 1, Newline.PREPOST));

        mapping.put(SqlTokenType.INSERT_INTO, new LayoutMapping(-1, 1, Newline.POST));
        mapping.put(SqlTokenType.VALUES, new LayoutMapping(-1, 1, Newline.PREPOST));

        mapping.put(SqlTokenType.COMMA, new LayoutMapping(0, 0, Newline.POST));

        mapping.put(SqlTokenType.AS, new LayoutMapping(0, 0, Newline.NONE));

        mapping.put(SqlTokenType.AND, new LayoutMapping(0, 0, Newline.PRE));
        mapping.put(SqlTokenType.OR, new LayoutMapping(0, 0, Newline.PRE));

        mapping.put(SqlTokenType.NULL, new LayoutMapping(0, 0, Newline.PRE));

        mapping.put(SqlTokenType.JOIN, new LayoutMapping(0, 0, Newline.PREPOST));

        mapping.put(SqlTokenType.CREATE_TABLE, new LayoutMapping(0, 0, Newline.POST));
        mapping.put(SqlTokenType.ALTER_TABLE, new LayoutMapping(0, 0, Newline.PRE));
        mapping.put(SqlTokenType.ADD, new LayoutMapping(0, 0, Newline.PRE));

        mapping.put(SqlTokenType.DELETE_FROM, new LayoutMapping(0, 0, Newline.PRE));

        mapping.put(SqlTokenType.CASE, new LayoutMapping(0, 1, Newline.PREPOST));
        mapping.put(SqlTokenType.WHEN, new LayoutMapping(0, 0, Newline.PRE));
        mapping.put(SqlTokenType.THEN, new LayoutMapping(0, 0, Newline.NONE));
        mapping.put(SqlTokenType.ELSE, new LayoutMapping(0, 0, Newline.NONE));
        mapping.put(SqlTokenType.END, new LayoutMapping(-1, 0, Newline.PREPOST));

        mapping.put(SqlTokenType.EXISTS, new LayoutMapping(0, 0, Newline.NONE));

        mapping.put(SqlTokenType.NULL, new LayoutMapping(0, 0, Newline.PRE));
    }

    private Map<Integer, String> cache = new HashMap<Integer, String>();

    public static String getHTMLFormattedSQL2(String sql) {
        String formatted = null; //cache.get(sql.hashCode());

        if (formatted == null) {
            StringBuilder result = new StringBuilder();

            List<SqlToken> tokens = tokenizer.parse(sql);
            int indent = 0;

            for (SqlToken token : tokens) {
                LayoutMapping lm = mapping.get(token.getType());
                if (lm == null) {
                    lm = defaultMapping;
                }

                indent += lm.getPreIndent();

                if (indent < 0) {
                    indent = 0;
                }

                if (lm.preNewline()) {
                    result.append("\n");
                    indentation(indent, result);
                }

                if (lm != defaultMapping) {
                    result.append("<b>");
                }
                result.append(token.getText());
                if (lm != defaultMapping) {
                    result.append("</b>");
                }
                result.append(" ");

                indent += lm.getPostIndent();

                if (lm.postNewline()) {
                    result.append("\n");
                    indentation(indent, result);
                }
            }

            formatted = result.toString();
            //tcache.put(sql.hashCode(), formatted);
        }

        return formatted;
    }

    public static void indentation(int nr, StringBuilder result) {
        while (nr-- > 0) {
            result.append("    ");
        }
    }

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

    public static void main(String [] args) {
        SqlFormatter f = new SqlFormatter();

        System.out.println(f.getHTMLFormattedSQL2("CREATE TABLE TEST291 (ID INT PRIMARY KEY, NAME VARCHAR(255))"));
    }

}
