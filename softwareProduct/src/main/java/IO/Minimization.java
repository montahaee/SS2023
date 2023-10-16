package IO;

import framework.Handleable;

import java.util.Set;

/**
 * This class represents die expected output data namely minimized location data regarding
 * service station, which will be written as location in the {@link FileConsumer#write(Handleable.Data)}.
 */
public record Minimization(Set<String> stations, String path) {

    public enum Extension {
        OUT, ERR
    }

    /**
     * An explicit construction for minimization.
     *
     * @param stations represent the location of train service.
     */
    public Minimization {
    }

    /**
     * @return A list of popular routes for service stations.
     */
    @Override
    public Set<String> stations() {
        return this.stations;
    }
}
