package org.bluepig.backend.controller;

import org.bluepig.backend.Soup;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RestController
public class CrawlController {

    //캐릭터 이름을 클라이언트로부터 받아서, 해당하는 캐릭터의 나무위키 문서 링크를 보내줌.
    @PostMapping("/specific")
    public Map<String, Object> specificRequest(@RequestBody Map<String,Object> map) throws IOException {
        Map<String, Object> result = map;
        String input = result.get("in").toString().replaceAll(" ","");
        String link = Soup.getCharacterLink(input);
        if (!link.equals("")) {
            result.put("out",link);
        } else {
            result.put("out","error");
        }
        return result;
    }

    //아무 캐릭터의 나무위키 문서 링크를 보내줌.
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

    @PostMapping("/img")
    public Map<String, Object> imgRequest(@RequestBody Map<String,Object> map) throws Exception {
        Map<String, Object> result = map;
        String input = result.get("in").toString();
        String link = Soup.getCharacterImg(Soup.getCharacterTable(input));
        if (!link.equals("")) {
            result.put("out",link);
        } else {
            result.put("out","error");
        }
        return result;
    }
}
