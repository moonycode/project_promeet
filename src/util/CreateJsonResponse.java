package util;

import com.google.gson.Gson;

public class CreateJsonResponse {
    public static String toJson(Object data) {
        return new Gson().toJson(data);
    }
}
