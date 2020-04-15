package de.demmer.dennis.odrauserservice.service.util;

import com.google.gson.GsonBuilder;
import org.springframework.stereotype.Service;

@Service
public class JsonConverter {

    public String convert(Object o){
        return new GsonBuilder().setPrettyPrinting().create().toJson(o).toString();
    }
}
