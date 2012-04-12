package nl.astraeus.jdbc.util;

import java.io.*;

/**
 * User: rnentjes
 * Date: 4/9/12
 * Time: 2:33 PM
 */
public class IOUtils {

    public static String toString(InputStream in) throws IOException {
        assert in != null;

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder buffer = new StringBuilder();

            while(reader.ready()) {
                buffer.append(reader.readLine());
                buffer.append("\n");
            }

            return buffer.toString();
        } finally {
            in.close();
        }
    }

    public static void copy(InputStream in, OutputStream out) throws IOException {
        byte [] buffer = new byte[1<<16];
        int bytes;

        while((bytes = in.read(buffer)) > 0) {
            out.write(buffer, 0, bytes);
        }
    }
}
