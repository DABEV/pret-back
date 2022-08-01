package mx.edu.utez.pret.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class Response {
    // Response fields names
    public static final String TITLE = "title";
    public static final String MESSAGE = "message";
    public static final String DATA = "data";

    private Map<String, Object> mainJson;

    public Response () {
        mainJson = new HashMap<>();
    }
    
    /**
     * Build a standard JSON to response in API method
     * @param title
     * @param data
     * @return A map representing JSON response
     */
    public Map<String, Object> buildStandardResponse (String title, Object data) {
        mainJson.clear();
        mainJson.put(TITLE, title);
        mainJson.put(DATA, data);
        return mainJson;       
    }
    
    /**
     * Build a standard JSON to response in API method with a descriptive message
     * @param title
     * @param data
     * @param message
     * @return A map representing JSON response
     */
    public Map<String, Object> buildStandardResponse (String title, Object data, String message) {
        mainJson = buildStandardResponse(title, data);
        mainJson.put(MESSAGE, message);
        return mainJson;       
    }

    /**
     * Build a standard JSON to response in API method with a descriptive message
     * @param title
     * @param data
     * @param message
     * @return A map representing JSON response
     */
    public <C> Map<String, Object> buildStandardResponse (String title, List<C> data, String message) {
        mainJson = buildStandardResponse(title, data);
        mainJson.put(MESSAGE, message);
        return mainJson;       
    }
}
