package nl.rug.oop.flaps.simulation.model.loaders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import nl.rug.oop.flaps.simulation.model.airport.Airport;
import nl.rug.oop.flaps.simulation.model.loaders.utils.FileUtils;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

/**
 * This class is responsible for loading the set of airports from various YAML
 * files and other resources.
 *
 * @author T.O.W.E.R.
 */
@Log
public class AirportLoader {
    private static final Path AIRPORTS_PATH = Path.of("data", "airports");
    private static final String YAML_FILE_PATTERN = "glob:**airport.yaml";
    public static final String BANNER_FILE_PATTERN = "glob:**banner.*";
    private static final String P_YAML = "glob:**passengers.yaml";

    private final ObjectMapper mapper;

    public AirportLoader() {
        this(new ObjectMapper(new YAMLFactory()));
    }

    public AirportLoader(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public Set<Airport> loadAirports() throws IOException {
        Set<Airport> airports = new HashSet<>();

        for (var airportFile : FileUtils.findMatches(AIRPORTS_PATH, YAML_FILE_PATTERN)) {

            AirportHolder airportHolder = new AirportHolder();
            ObjectReader reader = mapper.readerForUpdating(airportHolder);
            reader.readValue(airportFile.toFile());
            FileUtils.findMatch(airportFile.getParent(), P_YAML).ifPresent(passengerFile -> {
                threadedLoading(new Runnable() {
                    @SneakyThrows
                    @Override
                    public void run() {
                        reader.readValue(passengerFile.toFile());
                    }
                }, "Could not retrieve passenger type set");
            });

            Airport airport = airportHolder.getAirport();
            var optionalBannerFile = FileUtils.findMatch(airportFile.getParent(), BANNER_FILE_PATTERN);
            optionalBannerFile.ifPresent(imagePath ->
                    threadedLoading(new Runnable() {
                        @SneakyThrows
						@Override
                        public void run() {
                            airport.setBannerImage(ImageIO.read(imagePath.toFile()));
                        }
                    }, "Could not load banner image for airport "));

            airports.add(airport);
        }
        return airports;
    }

    private void threadedLoading(Runnable r, String warning) {
        int attempts = 10;
        boolean success = false;
        while (attempts > 0) {
            try {
                r.run();
                success = true;
                break;
            } catch (Exception e) {
                attempts--;
            }
        }
        if (!success) {
            log.warning(warning);
        }
    }
}
