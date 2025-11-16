
package framework;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Config {
    public static Map<String, Object> loadCaps(String path) {
        try (InputStream is = Files.newInputStream(Paths.get(path))) {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(is, new TypeReference<Map<String, Object>>() {});
        } catch (IOException e) {
            throw new RuntimeException("Failed to load capabilities from: " + path, e);
        }
    }
}
