package org.bluepig.backend.controller;

import org.bluepig.backend.Soup;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RestController
public class CrawlController {
    @PostMapping("/specific")
    public Map<String, Object> specificRequest(@RequestBody Map<String,Object> map) throws IOException {
        Map<String, Object> result = map;
        String input = result.get("in").toString().replaceAll(" ","");
        String link = Soup.getCharacterLink(input);
        if (!link.equals("")) {
//            Map<String,String> map1 = Soup.Crawl();
//            String res = "";
//            Iterator<String> keys = map1.keySet().iterator();
//            while (keys.hasNext()) {
//                String key = keys.next();
//                res += key + ", ";
//            }
//            result.put("out", res);
            result.put("out",link);
        } else {
            result.put("out","no");
        }
        return result;
    }

    @PostMapping("/random")
    public Map<String, Object> randomRequest() throws IOException {
        Map<String,String> soup = Soup.Crawl();
        Map<String, Object> result = new HashMap<>();
        Random generator = new Random();
        Object[] values = soup.values().toArray();
        Object randomValue = values[generator.nextInt(values.length)];
        result.put("out", randomValue);
        return result;
    }
}
