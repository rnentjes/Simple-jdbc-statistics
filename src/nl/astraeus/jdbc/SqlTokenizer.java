package nl.astraeus.jdbc;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * User: rnentjes
 * Date: 4/24/12
 * Time: 9:00 PM
 */
public class SqlTokenizer {

    public static void main(String [] args) {
        SqlTokenizer st = new SqlTokenizer();

        List<SqlToken> result = st.parse("select \n" +
                "    seller0_.id as id26_2_ , \n" +
                "     seller0_.auctionSiteSellerId as auctionS2_26_2_ , \n" +
                "     seller0_.blackList as blackList26_2_ , \n" +
                "     seller0_.lastUpdate as lastUpdate26_2_ , \n" +
                "     seller0_.monitored as monitored26_2_ , \n" +
                "     seller0_.monitoredSince as monitore6_26_2_ , \n" +
                "     seller0_.nickName as nickName26_2_ , \n" +
                "     seller0_.reported as reported26_2_ , \n" +
                "     seller0_.sellerImage_id as sellerI14_26_2_ , \n" +
                "     seller0_.site_id as site13_26_2_ , \n" +
                "     seller0_.veroBlackListCode as veroBlac9_26_2_ , \n" +
                "     seller0_.version as version26_2_ , \n" +
                "     seller0_.warnings as warnings26_2_ , \n" +
                "     seller0_.whiteList as whiteList26_2_ , \n" +
                "     image1_.id as id8_0_ , \n" +
                "     image1_.data_id as data7_8_0_ , \n" +
                "     image1_.downloadAttempts as download2_8_0_ , \n" +
                "     image1_.fileName as fileName8_0_ , \n" +
                "     image1_.ok as ok8_0_ , \n" +
                "     image1_.originalUrl as original5_8_0_ , \n" +
                "     image1_.version as version8_0_ , \n" +
                "     auctionsit2_.id as id5_1_ , \n" +
                "     auctionsit2_.description as descript2_5_1_ , \n" +
                "     auctionsit2_.name as name5_1_ , \n" +
                "     auctionsit2_.url as url5_1_ , \n" +
                "     auctionsit2_.version as version5_1_ , \n" +
                "     auctionsit2_1_.applicationKey as applicat2_6_1_ , \n" +
                "     auctionsit2_1_.certificateKey as certific3_6_1_ , \n" +
                "     auctionsit2_1_.developerKey as develope4_6_1_ , \n" +
                "     auctionsit2_1_.restToken as restToken6_1_ , \n" +
                "     auctionsit2_1_.token as token6_1_ , \n" +
                "     \n" +
                "    case \n" +
                "        when auctionsit2_1_.id is not null then 1 \n" +
                "        when auctionsit2_.id is not null then 0 \n" +
                "    end \n" +
                "    as clazz_1_ \n" +
                "from \n" +
                "    Seller seller0_ \n" +
                "    left outer join \n" +
                "    Image image1_ on seller0_.sellerImage_id=image1_.id \n" +
                "    left outer join \n" +
                "    AuctionSite auctionsit2_ on seller0_.site_id=auctionsit2_.id \n" +
                "    left outer join \n" +
                "    Ebay auctionsit2_1_ on auctionsit2_.id=auctionsit2_1_.id \n" +
                "where \n" +
                "    seller0_.id=? ");

        for (SqlToken token : result) {
            System.out.println(token.getType()+"\t->\t"+token.getText());
        }
    }

    private Map<String, SqlTokenType> basicMapping = new HashMap<String, SqlTokenType>();

    public SqlTokenizer() {
        basicMapping.put("select", SqlTokenType.SELECT);
        basicMapping.put("from", SqlTokenType.FROM);
        basicMapping.put("where", SqlTokenType.WHERE);
        basicMapping.put("having", SqlTokenType.HAVING);
        basicMapping.put("update", SqlTokenType.UPDATE);
        basicMapping.put("set", SqlTokenType.SET);

        basicMapping.put("as", SqlTokenType.AS);
        basicMapping.put("on", SqlTokenType.ON);

        basicMapping.put("and", SqlTokenType.AND);
        basicMapping.put("or", SqlTokenType.OR);
        basicMapping.put("not", SqlTokenType.NOT);

        basicMapping.put("join", SqlTokenType.NOT);
        basicMapping.put("exists", SqlTokenType.EXISTS);

        basicMapping.put("values", SqlTokenType.VALUES);
        basicMapping.put("null", SqlTokenType.NULL);
        basicMapping.put(",", SqlTokenType.COMMA);

        basicMapping.put("case", SqlTokenType.CASE);
        basicMapping.put("when", SqlTokenType.WHEN);
        basicMapping.put("then", SqlTokenType.THEN);
        basicMapping.put("else", SqlTokenType.ELSE);
        basicMapping.put("end", SqlTokenType.END);

        basicMapping.put("commit", SqlTokenType.COMMIT);
        basicMapping.put("rollback", SqlTokenType.ROLLBACK);
        basicMapping.put("close", SqlTokenType.CLOSE);
    }

    public List<SqlToken> parse(String sql) {
        List<SqlToken> result = new LinkedList<SqlToken>();

        String [] pts = sql.split("\\s");

        List<String> parts = new LinkedList<String>();

        for (int index = 0; index < pts.length; index++) {
            parts.add(pts[index]);
        }

        while(!parts.isEmpty()) {
            SqlToken token = getToken(parts);

            if (token.getType() != SqlTokenType.EMPTY) {
                result.add(token);
            }
        }

        return result;
    }

    private SqlToken getToken(List<String> parts) {
        SqlTokenType type = SqlTokenType.UNKNOWN;
        String part = null;

        if (parts.get(0).trim().length() == 0) {
            parts.remove(0);

            part = null;
            type = SqlTokenType.EMPTY;
        } else if (parts.get(0).startsWith("'")) {
            // find end of tekst
            String result = parts.remove(0);
            part = result.substring(1);

            while (part.indexOf("'") == -1 || part.startsWith("''")) {
                if (part.startsWith("''")) {
                    part = part.substring(2);
                } else {
                    part = parts.remove(0);
                    result += " "+part;
                }
            }

            part = result;
            type = SqlTokenType.UNKNOWN;
        } else if (parts.get(0).startsWith(",")) {
            part = parts.remove(0);
            part = part.substring(1);

            parts.add(0, part);

            part = ",";
            type = SqlTokenType.COMMA;
        } else if (parts.get(0).endsWith(",")) {
            part = parts.remove(0);
            part = part.substring(0, part.length() -1);

            parts.add(0, ",");
            parts.add(0, part);

            part = null;
            type = SqlTokenType.EMPTY;
        } else if (check(parts, "order", "by")) {
            part = parts.remove(0);
            part += " " + parts.remove(0);
            type = SqlTokenType.ORDER_BY;
        } else if (check(parts, "insert", "into")) {
            part = parts.remove(0);
            part += " " + parts.remove(0);
            type = SqlTokenType.INSERT_INTO;
        } else if (check(parts, "delete", "from")) {
            part = parts.remove(0);
            part += " " + parts.remove(0);
            type = SqlTokenType.DELETE_FROM;
        } else if (check(parts, "create", "table")) {
            part = parts.remove(0);
            part += " " + parts.remove(0);
            type = SqlTokenType.CREATE_TABLE;
        } else if (check(parts, "alter", "table")) {
            part = parts.remove(0);
            part += " " + parts.remove(0);
            type = SqlTokenType.ALTER_TABLE;
        } else if (check(parts, "is", "null")) {
            part = parts.remove(0);
            part += " " + parts.remove(0);
            type = SqlTokenType.IS_NULL;
        } else if (check(parts, "not", "null")) {
            part = parts.remove(0);
            part += " " + parts.remove(0);
            type = SqlTokenType.NOT_NULL;
        } else if (check(parts, "left", "outer", "join")) {
            part = parts.remove(0);
            part += " " + parts.remove(0);
            part += " " + parts.remove(0);
            type = SqlTokenType.JOIN;
        } else if (check(parts, "right", "outer", "join")) {
            part = parts.remove(0);
            part += " " + parts.remove(0);
            part += " " + parts.remove(0);
            type = SqlTokenType.JOIN;
        } else if (check(parts, "full", "outer", "join")) {
            part = parts.remove(0);
            part += " " + parts.remove(0);
            part += " " + parts.remove(0);
            type = SqlTokenType.JOIN;
        } else if (check(parts, "outer", "join")) {
            part = parts.remove(0);
            part += " " + parts.remove(0);
            type = SqlTokenType.JOIN;
        } else if (check(parts, "inner", "join")) {
            part = parts.remove(0);
            part += " " + parts.remove(0);
            type = SqlTokenType.JOIN;
        } else if (check(parts, "natural", "join")) {
            part = parts.remove(0);
            part += " " + parts.remove(0);
            type = SqlTokenType.JOIN;
        } else if (check(parts, "cross", "join")) {
            part = parts.remove(0);
            part += " " + parts.remove(0);
            type = SqlTokenType.JOIN;
        } else if (check(parts, "(")) {
            part = parts.remove(0);
            type = SqlTokenType.EXPRESSION_OPEN;
        } else if (check(parts, ")")) {
            part = parts.remove(0);
            type = SqlTokenType.EXPRESSION_CLOSE;
        } else if (parts.get(0).startsWith("(")) {
            part = parts.remove(0);
            part = part.substring(1);

            parts.add(0, part);

            part = "(";
            type = SqlTokenType.EXPRESSION_OPEN;
        } else if (parts.get(0).endsWith(")")) {
            part = parts.remove(0);
            part = part.substring(0, part.length()-1);

            parts.add(0, ")");
            parts.add(0, part);

            part = null;
            type = SqlTokenType.EMPTY;
        } else if (basicMapping.get(parts.get(0).toLowerCase()) != null) {
            part = parts.remove(0);
            type = basicMapping.get(part.toLowerCase());
        } else {
            part = parts.remove(0);
        }

        return new SqlToken(type, part);
    }

    private boolean check(List<String> parts, String ... elements) {
        boolean result = true;

        for (int index = 0; index < elements.length; index++) {
            result = result && ((parts.size() > index) && parts.get(index).trim().equalsIgnoreCase(elements[index]));
        }

        return result;
    }
}
