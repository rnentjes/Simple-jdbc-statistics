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
        mapping.put(SqlTokenType.NOT, new LayoutMapping(0, 0, Newline.NONE));

        mapping.put(SqlTokenType.JOIN, new LayoutMapping(0, 0, Newline.PREPOST));
        mapping.put(SqlTokenType.ON, new LayoutMapping(0, 0, Newline.NONE));

        mapping.put(SqlTokenType.CREATE_TABLE, new LayoutMapping(-1, 1, Newline.POST));
        mapping.put(SqlTokenType.ALTER_TABLE, new LayoutMapping(-1, 1, Newline.PRE));
        mapping.put(SqlTokenType.ADD, new LayoutMapping(0, 0, Newline.PRE));

        mapping.put(SqlTokenType.DELETE_FROM, new LayoutMapping(-1, 1, Newline.PREPOST));

        mapping.put(SqlTokenType.CASE, new LayoutMapping(0, 1, Newline.NONE));
        mapping.put(SqlTokenType.WHEN, new LayoutMapping(0, 0, Newline.PRE));
        mapping.put(SqlTokenType.THEN, new LayoutMapping(0, 0, Newline.NONE));
        mapping.put(SqlTokenType.ELSE, new LayoutMapping(0, 0, Newline.NONE));
        mapping.put(SqlTokenType.END, new LayoutMapping(-1, 0, Newline.PREPOST));

        mapping.put(SqlTokenType.EXISTS, new LayoutMapping(0, 0, Newline.NONE));

        mapping.put(SqlTokenType.NULL, new LayoutMapping(0, 0, Newline.NONE));
        mapping.put(SqlTokenType.IS_NULL, new LayoutMapping(0, 0, Newline.NONE));
        mapping.put(SqlTokenType.NOT_NULL, new LayoutMapping(0, 0, Newline.NONE));

        mapping.put(SqlTokenType.COMMIT, new LayoutMapping(0, 0, Newline.NONE));
        mapping.put(SqlTokenType.ROLLBACK, new LayoutMapping(0, 0, Newline.NONE));
        mapping.put(SqlTokenType.CLOSE, new LayoutMapping(0, 0, Newline.NONE));
    }

    private Map<Integer, String> cache = new HashMap<Integer, String>();

    public static String getHTMLFormattedSQL(String sql) {
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

    public static void main(String [] args) {
        SqlFormatter f = new SqlFormatter();

        System.out.println(f.getHTMLFormattedSQL("select screenshot0_.Auction_id as Auction1_1_, screenshot0_.screenshots_id as screensh2_1_, image1_.id as id8_0_, image1_.data_id as data7_8_0_, image1_.downloadAttempts as download2_8_0_, image1_.fileName as fileName8_0_, image1_.ok as ok8_0_, image1_.originalUrl as original5_8_0_, image1_.version as version8_0_ from auction_screenshots screenshot0_ left outer join Image image1_ on screenshot0_.screenshots_id=image1_.id where screenshot0_.Auction_id=?"));
    }

}
