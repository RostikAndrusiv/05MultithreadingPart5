package org.rostik.andrusiv.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Optional;

@Slf4j
public class JsonUtils {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private JsonUtils() {
    }

    public static <T> void saveJsonToFile(T source, String out) {
        String json = toJson(source);
        var file = new File(out);
        file.getParentFile().mkdirs();
        try {
            FileWriter myWriter = new FileWriter(out);
            myWriter.write(json);
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static <T> Optional<T> readJsonFromFile(String location, Class<T> type) {
        try (FileInputStream fis = new FileInputStream(location)) {
            return fromJson(fis, type);
        } catch (IOException e) {
            log.info("File not found");
        }
        return Optional.empty();
    }

    private static <T> String toJson(T source) {
        if (source == null) {
            return null;
        }
        try {
            return OBJECT_MAPPER.writeValueAsString(source);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private static <T> Optional<T> fromJson(InputStream stream, Class<T> type) {
        try {
            return Optional.of(fromJson(copyToString(stream, Charset.defaultCharset()), type));
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private static <T> T fromJson(String json, Class<T> type) {
        if (type != null && !StringUtils.isEmpty(json)) {
            try {
                return OBJECT_MAPPER.readValue(json, type);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private static String copyToString(InputStream in, Charset charset) throws IOException {
        if (in == null) {
            return "";
        }
        StringBuilder out = new StringBuilder(4096);
        InputStreamReader reader = new InputStreamReader(in, charset);
        char[] buffer = new char[4096];
        int charsRead;
        while ((charsRead = reader.read(buffer)) != -1) {
            out.append(buffer, 0, charsRead);
        }
        return out.toString();
    }
}
