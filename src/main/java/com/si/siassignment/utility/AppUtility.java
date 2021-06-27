package com.si.siassignment.utility;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.si.siassignment.models.Clipboard;

import javax.sound.sampled.Clip;
import java.io.IOException;

public class AppUtility {

    public static String getDomain() {
        return "http://localhost:8080/";
    }
    public static String getClipboardBaseURL() {return getDomain() + "b/";}
    public static String convertToJson(Object obj) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        String jsonStr = null;
        try {
            jsonStr = mapper.writeValueAsString(obj);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return jsonStr;
    }

}
