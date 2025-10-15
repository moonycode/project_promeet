package util;

import com.google.gson.Gson;

public class CreateJsonResponse {
    
    public static String toJson(Object data) {
        Gson gson = new Gson();
        return gson.toJson(data);
    }
}
