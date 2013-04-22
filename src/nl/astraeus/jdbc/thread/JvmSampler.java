package nl.astraeus.jdbc.thread;

import nl.astraeus.jdbc.web.model.Settings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * User: rnentjes
 * Date: 4/22/13
 * Time: 8:52 PM
 */
public class JvmSampler extends Thread {
    private final static Logger logger = LoggerFactory.getLogger(JvmSampler.class);

    private boolean stopped;
    private boolean running;
    private int samplesPerSecond = 100;
    private long samples = 0;
    private Map<String, Integer> count = new HashMap<>();

    public JvmSampler() {
        super("JvmSampler");
    }

    public void exit() {
        stopped = true;
    }

    public boolean isRunning() {
        return running;
    }

    public void stopRunning() {
        running = false;
    }

    public void startRunning() {
        running = true;
    }

    public void setSamplesPerSecond(int samplesPerSecond) {
        this.samplesPerSecond = samplesPerSecond;
    }

    public long getSamples() {
        return samples;
    }

    public synchronized void clear() {
        count.clear();
        samples = 0;
    }

    @Override
    public void run() {
        while(!stopped) {
            try {
            if (running) {
                Map<Thread, StackTraceElement[]> map = Thread.getAllStackTraces();

                processAllThreads(map);
                samples++;

                long nanos = 1000000000/samplesPerSecond;

                Thread.sleep(nanos / 1000000, (int)(nanos % 1000000));
            } else {
                Thread.sleep(10);
            }
            } catch (Exception e) {
                logger.warn(e.getMessage(), e);
            }

        }
    }

    private synchronized void processAllThreads(Map<Thread, StackTraceElement[]> map) {
        for(Map.Entry<Thread, StackTraceElement[]> entry : map.entrySet()) {
            for(StackTraceElement element : entry.getValue()) {
                String name = element.getClassName()+"."+element.getMethodName()+"()";
                Integer samples = this.count.get(name);

                if (samples == null) {
                    samples = 0;
                }

                samples++;
                this.count.put(name, samples);
            }
        }
    }

    public synchronized Set<SampleInfo> getSampleCount() {
        Set<SampleInfo> result = new TreeSet<>();

        for(Map.Entry<String, Integer> entry : count.entrySet()) {
            result.add(new SampleInfo(entry.getKey(), entry.getValue()));
        }

        return result;
    }

    public static class SampleInfo implements Comparable<SampleInfo> {
        private String location;
        private int count;

        public SampleInfo(String location, int count) {
            this.location = location;
            this.count = count;
        }

        public String getLocation() {
            return location;
        }

        public int getCount() {
            return count;
        }

        public boolean getHighlight() {
            return location.startsWith(Settings.get().getPackageStart());
        }

        @Override
        public int compareTo(SampleInfo o) {
            int result = o.count - this.count;

            if (result == 0) {
                result = this.location.compareTo(o.location);
            }

            return result;
        }
    }

}
