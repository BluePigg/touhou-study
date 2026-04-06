package org.bluepig.backend.controller;

import org.bluepig.backend.Soup;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class CrawlController {
    @PostMapping("/crawling")
    public Map<String, Object> crawlRequest(@RequestBody Map<String,Object> map, Model model) throws IOException {
        Map<String, Object> result = map;
        String input = result.get("in").toString();
        String link = Soup.getCharacterLink(input);
        if (!link.equals("")) {
            result.put("out",link);
        } else {
            result.put("out","no");
        }
        return result;
    }
}
