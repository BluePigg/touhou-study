package org.bluepig.backend;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//[나 숫자로 시작하는거 제외.
//동방-,비봉-,프리즘리버- 제외.
//"2.1."부터
//"2.5." 까지
//리스트 = "//span[@id='가나다순']/../../../../../../..//a"
public class Soup {
    public static Map<String,String> Crawl() throws IOException {
        Document doc = Jsoup.connect("https://namu.wiki/w/%EB%8F%99%EB%B0%A9%20%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8/%EB%93%B1%EC%9E%A5%EC%9D%B8%EB%AC%BC").get();

        Elements CharacterLists = doc.selectXpath("//span[@id='가나다순']/../../../../../../..//a");

        Boolean startList = false;
        Map<String,String> CharacterWLinks = new HashMap<>();

        int i = 0;
        for (Element chr : CharacterLists) {
            String innerHTML = chr.html();
            //System.out.println(innerHTML);
            if (innerHTML.equals("2.1.")) {
                //System.out.println("Start now!");
                startList = true;
            } else if (!startList || innerHTML.startsWith("동방") || innerHTML.startsWith("[") || innerHTML.startsWith("<") || innerHTML.startsWith("비봉") || innerHTML.startsWith("프리즘리버")) {
                continue;
            }
            if (innerHTML.equals("2.5.")) {/**System.out.println("Ends here!");**/ break;}
            if (innerHTML.startsWith("2")) {
                continue;
            }
            String name = URLDecoder.decode(chr.attr("abs:href").replace("https://namu.wiki/w/", ""), StandardCharsets.UTF_8).replaceAll(" ","").replaceAll("\\(동방프로젝트\\)", "");
            CharacterWLinks.put(name,chr.attr("abs:href"));
            i++;
        }

        //System.out.println(i);
        return CharacterWLinks;
    }

    public static String getCharacterLink(String key) {
        try {
            Map<String,String> map = Crawl();
            if (!map.containsKey(key)) {System.out.println("No key :("); return "";}
            return map.get(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
