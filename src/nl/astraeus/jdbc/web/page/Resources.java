package nl.astraeus.jdbc.web.page;

import nl.astraeus.jdbc.util.IOUtils;
import nl.astraeus.web.page.Page;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * User: rnentjes
 * Date: 3/28/12
 * Time: 3:05 PM
 */
public class Resources extends Page {

    private long startup = System.currentTimeMillis();

    private String [] parts;

    public Resources(String [] parts) {
        this.parts = parts;
    }

    @Override
    public void get() {
    }

    @Override
    public void render(OutputStream out) throws IOException {
        StringBuilder uriBuilder = new StringBuilder("nl/astraeus/jdbc/web/resources");

        for (String part : parts) {
            uriBuilder.append("/");
            uriBuilder.append(part);
        }

        String uri = uriBuilder.toString();

        InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(uri);

        if (in == null) {
            sendResponse(404);
        } else {
            try {
                if (uri.endsWith("js")) {
                    setContentType("text/javascript");
                } else if (uri.endsWith("css")) {
                    setContentType("text/css");
                } else if (uri.endsWith("png")) {
                    setContentType("image/png");
                } else if (uri.endsWith("jpg")) {
                    setContentType("image/jpeg");
                } else if (uri.endsWith("gif")) {
                    setContentType("image/gif");
                }

                setHeader("Cache-Control", "max-age=3600");
                setHeader("ETag", Long.toHexString(startup));

                IOUtils.copy(in, out);
            } finally {
                in.close();
            }
        }
    }


}
