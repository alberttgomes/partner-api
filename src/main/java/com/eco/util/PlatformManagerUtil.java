package com.eco.util;

import com.eco.model.PlatformManager;
import com.google.gson.Gson;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author Albert Gomes Cabral
 */
@Component
public class PlatformManagerUtil {
    public static PlatformManager loadPlatformManagerProperties() throws Exception {
        ClassLoader classLoader =
                PlatformManagerUtil.class.getClassLoader();

        InputStream inputStream =
                classLoader.getResourceAsStream("platform-setup-wizard.json");

        if (inputStream != null) {
            InputStreamReader inputStreamReader =
                    new InputStreamReader(inputStream);

            Gson gson = new Gson();

            return gson.fromJson(
                    inputStreamReader, PlatformManager.class);
        }
        else {
            throw new Exception("File not found. ");
        }
    }
}
