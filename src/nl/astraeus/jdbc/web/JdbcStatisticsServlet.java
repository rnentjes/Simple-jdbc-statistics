package nl.astraeus.jdbc.web;

import nl.astraeus.jdbc.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * User: rnentjes
 * Date: 3/28/12
 * Time: 3:05 PM
 */
public class JdbcStatisticsServlet extends HttpServlet {
    private final static Logger logger = LoggerFactory.getLogger(JdbcStatisticsServlet.class);

    private String head;
    private String bottom;
//    private Map<String, Page> mapping = new HashMap<String, Page>();

    @Override
    public void init() throws ServletException {
        super.init();

        try {
            head = IOUtils.toString(getClass().getResourceAsStream("head.html"));
            bottom = IOUtils.toString(getClass().getResourceAsStream("bottom.html"));
        } catch (IOException e) {
            throw new ServletException(e);
        }

/*
        mapping.put("queries", new QueryOverview());
        mapping.put("login", new Login());
        mapping.put("jvm", new JvmStats());
*/

        // queries/select/1234
        // queries/page/2
        // queries/cancel
        // queries/select/1234/select/5432

        // queries/action=select/actionValue=1234
        // transactions/action=page&actionValue=3
        // settings
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();

/*
        String [] parts = uri.split("\\/");
        int index = 0;

        if (parts.length > 0) {
            Page page = mapping.get(parts[index++]);

            while(index < (parts.length - 1)) {
                page = page.processGetRequest(parts[index++], parts[index++]);
            }
        }
*/

        doPost(req, resp);
    }

    @Override
    protected void doPost(final HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long nano = System.nanoTime();
        
/*
        HttpSession session =  req.getSession();
        boolean ajax = "true".equals(req.getParameter("ajax"));

        resp.setContentType("text/html");

        Page page = (Page)session.getAttribute("page");
        Page menu = (Page)session.getAttribute("menu");

        session.setMaxInactiveInterval(1800);

        if (menu == null) {
            menu = new Menu();

            session.setAttribute("menu", menu);
        }

        if (Settings.get().isSecure() && session.getAttribute("loggedin") == null && !(page instanceof Login)) {
            page = new Login();
        } else if (!(page instanceof Login)) {
            if (page == null || "menumain".equals(req.getParameter("action"))) {
                page = new QueryOverview();
            } else if ("menulive".equals(req.getParameter("action"))) {
                page = new LiveOverview();
            } else if ("menutransactions".equals(req.getParameter("action"))) {
                page = new TransactionOverview();
            } else if ("menusettings".equals(req.getParameter("action"))) {
                page = new SettingsOverview();
            } else if ("menuinfo".equals(req.getParameter("action"))) {
                page = new ServerInfo();
            } else if ("jvmstats".equals(req.getParameter("action"))) {
                page = new JvmStats();
            } else {
                page = page .processRequest(req);
            }
        } else {
            page = page .processRequest(req);
        }

        menu.processRequest(req);

        session.setAttribute("page", page);

        if (!ajax) {
            resp.getWriter().print(head);
        }

        resp.getWriter().print(menu.render(req));

        resp.getWriter().println("<div class=\"container\">");

        resp.getWriter().print(Warnings.get(req).render(req));

        long time = System.nanoTime();
        resp.getWriter().print(page.render(req));
        resp.getWriter().println("</div>");

        if (!ajax) {
            resp.getWriter().print(bottom);
        }
*/

        //logger.debug("Request ends, time=" + Util.formatNano(System.nanoTime() - nano) + ", page=" + page.getClass().getSimpleName());
    }

}
