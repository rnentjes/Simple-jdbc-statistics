package nl.astraeus.jdbc.thread;

/**
 * User: rnentjes
 * Date: 4/22/13
 * Time: 8:52 PM
 */
public class SamplerManager {

    private final static SamplerManager instance = new SamplerManager();

    public static SamplerManager get() {
        return instance;
    }

    private JvmSampler sampler;

    private SamplerManager() {
        sampler = new JvmSampler();
        sampler.start();
    }

    public JvmSampler getSampler() {
        return sampler;
    }

}
