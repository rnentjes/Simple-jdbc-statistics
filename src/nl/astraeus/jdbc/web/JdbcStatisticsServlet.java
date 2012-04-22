package nl.astraeus.jdbc.web;

import nl.astraeus.jdbc.util.IOUtils;
import nl.astraeus.jdbc.util.Util;
import nl.astraeus.jdbc.web.model.Settings;
import nl.astraeus.jdbc.web.page.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.LoginContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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

    @Override
    public void init() throws ServletException {
        super.init();

        try {
            head = IOUtils.toString(getClass().getResourceAsStream("head.html"));
            bottom = IOUtils.toString(getClass().getResourceAsStream("bottom.html"));
        } catch (IOException e) {
            throw new ServletException(e);
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(final HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long nano = System.nanoTime();
        
        HttpSession session =  req.getSession();
        boolean ajax = "true".equals(req.getParameter("ajax"));

        resp.setContentType("text/html");
        Settings.get().readSettings(req);

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
            } else if ("menutransactions".equals(req.getParameter("action"))) {
                page = new TransactionOverview();
            } else if ("menusettings".equals(req.getParameter("action"))) {
                page = new SettingsOverview();
            } else if ("diagnostics".equals(req.getParameter("action"))) {
                //page = new Diagnostics();
            } else {
                long time = System.nanoTime();
                page = page .processRequest(req);
            }
        } else {
            long time = System.nanoTime();
            page = page .processRequest(req);
        }

        menu.processRequest(req);

        session.setAttribute("page", page);

        if (!ajax) {
            resp.getWriter().print(head);
        }

        resp.getWriter().print(menu.render(req));
        long time = System.nanoTime();
        resp.getWriter().print(page.render(req));

        Settings.get().saveSettings(resp);

        if (!ajax) {
            resp.getWriter().print(bottom);
        }

        logger.debug("Request ends, time=" + Util.formatNano(System.nanoTime() - nano) + ", page=" + page.getClass().getSimpleName());
    }

}
