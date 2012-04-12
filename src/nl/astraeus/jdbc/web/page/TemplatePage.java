package nl.astraeus.jdbc.web.page;

import nl.astraeus.template.EscapeMode;
import nl.astraeus.template.SimpleTemplate;

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

    private SimpleTemplate template;

    protected TemplatePage() {
        template = getSimpleTemplate(this.getClass());
    }

    public String render(HttpServletRequest request) {
        return template.render(defineModel(request));
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
