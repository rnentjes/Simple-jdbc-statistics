package nl.astraeus.jdbc.web.page;

import nl.astraeus.jdbc.util.Util;
import nl.astraeus.template.EscapeMode;
import nl.astraeus.template.SimpleTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * User: rnentjes
 * Date: 3/28/12
 * Time: 3:20 PM
 */
public abstract class TemplatePage extends Page {
    private final static Logger logger = LoggerFactory.getLogger(TemplatePage.class);

    private SimpleTemplate template;

    protected TemplatePage() {
        template = getSimpleTemplate(this.getClass());
    }

    public String render(HttpServletRequest request) {
        long time1 = System.nanoTime();

        Map<String, Object> model = defineModel(request);

        long time2 = System.nanoTime();

        String result = template.render(model);

        long time3 = System.nanoTime();

        logger.info("Page " + this.getClass().getSimpleName() + ", define: " + Util.formatNano(time2-time1) + ", render: " + Util.formatNano(time3-time2) + ", total: " + Util.formatNano(time3-time1));

        return result;
    }

    private static Map<Class, SimpleTemplate> templateCache = new HashMap<Class, SimpleTemplate>();

    public synchronized static SimpleTemplate getSimpleTemplate(Class cls) {
        SimpleTemplate result = templateCache.get(cls);

        if (result == null) {
            InputStream in = null;

            try {
                in = cls.getResourceAsStream(cls.getSimpleName() + ".html");

                result = SimpleTemplate.readTemplate('{','}', EscapeMode.HTML, in);

                templateCache.put(cls, result);
            } catch (IOException e) {
                throw new IllegalStateException(e);
            } finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return result;
    }
}
