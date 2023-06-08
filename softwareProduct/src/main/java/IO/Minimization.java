package IO;

import framework.Handleable;

import java.util.List;

/**
 * This class represents die expected output data namely minimized location data regarding
 * service station, which will be written as location in the {@link FileConsumer#write(Handleable.Data)}.
 */
public record Minimization(List<String> stations) {
    /**
     * An explicit construction for minimization.
     *
     * @param stations represent the location of train service.
     */
    public Minimization {
    }

    /**
     * @return A list of popular routes for srvice stations.
     */
    @Override
    public List<String> stations() {
        return this.stations;
    }
}
