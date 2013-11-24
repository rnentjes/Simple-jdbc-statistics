package nl.astraeus.jdbc.web.page;

import javax.servlet.http.HttpServletRequest;

/**
 * User: rnentjes
 * Date: 10/16/13
 * Time: 4:53 PM
 */
public class Request {

    private static ThreadLocal<HttpServletRequest> request = new ThreadLocal<HttpServletRequest>();

    public static void set(HttpServletRequest req) {
        request.set(req);
    }

    public static HttpServletRequest get() {
        return request.get();
    }

}
