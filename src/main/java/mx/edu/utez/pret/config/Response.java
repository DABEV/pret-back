package mx.edu.utez.pret.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class Response {
    // Response fields names
    public static final String TITLE = "title";
    public static final String DESCRIPTION = "description";
    public static final String DATA = "data";

    private Map<String, Object> mainJson;

    public Response () {
        mainJson = new HashMap<>();
    }
    
    public Map<String, Object> buildStandarResponse (String title, Object data) {
        mainJson.clear();
        mainJson.put(TITLE, title);
        mainJson.put(DATA, data);
        return mainJson;       
    }

    public Map<String, Object> buildStandarResponse (String title, Object data, String description) {
        mainJson = buildStandarResponse(title, data);
        mainJson.put(DESCRIPTION, description);
        return mainJson;       
    }
}
