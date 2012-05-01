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

        List<SqlToken> result = st.parse("select auction0_.id as id3_, auction0_.account_id as account31_3_, " +
                "auction0_.auctionEndTime as auctionE2_3_, auction0_.auctionSiteId as auctionS3_3_, " +
                "auction0_.auctionStartTime as auctionS4_3_, auction0_.bidCount as bidCount3_, " +
                "auction0_.buyItNowPrice as buyItNow6_3_, auction0_.countryCode as countryC7_3_, " +
                "auction0_.currencyCode as currency8_3_, auction0_.ebaySite as ebaySite3_, " +
                "auction0_.illegal as illegal3_, auction0_.item_id as item32_3_, auction0_.itemURL as itemURL3_, " +
                "auction0_.lastStatusChange as lastSta12_3_, auction0_.price as price3_, " +
                "auction0_.priceImage_id as priceImage34_3_, auction0_.receivedTime as receive14_3_, " +
                "auction0_.screenshot_id as screenshot30_3_, auction0_.seller_id as seller29_3_, " +
                "auction0_.shipping as shipping3_, auction0_.site_id as site33_3_, " +
                "auction0_.spamValue as spamValue3_, auction0_.startPrice as startPrice3_, " +
                "auction0_.status as status3_, auction0_.subTitle as subTitle3_, " +
                "auction0_.takeScreenshot as takeScr20_3_, auction0_.takeScreenshotTime as takeScr21_3_, " +
                "auction0_.title as title3_, auction0_.veroFailReason as veroFai23_3_, " +
                "auction0_.veroPackedID as veroPac24_3_, auction0_.veroReason as veroReason3_, " +
                "auction0_.veroStatus as veroStatus3_, auction0_.version as version3_, " +
                "auction0_.whiteList as whiteList3_ from Auction auction0_ " +
                "where auction0_.site_id=? and auction0_.auctionSiteId=? order by auction0_.id");

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

        if (parts.get(0).startsWith("'")) {
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
