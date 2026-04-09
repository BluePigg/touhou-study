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
import java.util.List;
import java.util.Map;

public class Soup {
    //위에서부터 id="개요"를 초점으로 두고 중요한 상위개체까지 내려옴. body 넣으면 됌.
    public static Element toGaeyo(Element element) throws Exception {
        Element target = element;
        List<Element> res;
        for (int i=0; i<8; i++) {
            res = target.children().stream().filter(j->j.children().toString().contains("id=\"개요\"")).toList();
            if (res.size()==1) {
                target = res.get(0);
            } else {
                throw new Exception("개요를 두 개 이상의 개체가 가지고 있음.");
            }
        }

        return target;
    }

    //나무위키에서 크롤링해서 동방 프로젝트 캐릭터들을 <이름, 문서 링크> 형식으로 정리한 것 (이름은 문서 링크의 이름을 따름. 자세한건 String name 참조.)
    public static Map<String,String> Crawl() throws IOException {
        Document doc = Jsoup.connect("https://namu.wiki/w/%EB%8F%99%EB%B0%A9%20%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8/%EB%93%B1%EC%9E%A5%EC%9D%B8%EB%AC%BC").get();

        //가나다순이라고 써있는거 기준 7단계 위 개체의 아래에 있는 모든 a 개체
        Elements CharacterLists = doc.selectXpath("//span[@id='가나다순']/../../../../../../..//a");

        Boolean startList = false;
        Map<String,String> CharacterWLinks = new HashMap<>();

        for (Element chr : CharacterLists) {
            String innerHTML = chr.html();

            //2.1.에서 시작 2.5.에서 끝
            //동방, [, <, 2, 비봉, 프리즘리버로 시작하는거 제외.
            if (innerHTML.equals("2.1.")) {
                startList = true;
            } else if (!startList || innerHTML.startsWith("동방") || innerHTML.startsWith("[") || innerHTML.startsWith("<") || innerHTML.startsWith("비봉") || innerHTML.startsWith("프리즘리버")) {
                continue;
            }
            if (innerHTML.equals("2.5.")) {break;}
            if (innerHTML.startsWith("2")) {
                continue;
            }

            //예: https://namu.wiki/w/%ED%95%98%EC%BF%A0%EB%A0%88%EC%9D%B4%20%EB%A0%88%EC%9D%B4%EB%AC%B4%20%28%EB%8F%99%EB%B0%A9%20%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8%29 -> (https://namu.wiki/w/ 삭제) %ED%95%98%EC%BF%A0%EB%A0%88%EC%9D%B4%20%EB%A0%88%EC%9D%B4%EB%AC%B4%20%28%EB%8F%99%EB%B0%A9%20%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8%29
            // -> (UTF-8로 인코딩) 하쿠레이 레이무 (동방 프로젝트) -> (공백 제거) 하쿠레이레이무(동방프로젝트) -> (동방프로젝트 제거) 하쿠레이레이무
            String name = URLDecoder.decode(chr.attr("abs:href").replace("https://namu.wiki/w/", ""), StandardCharsets.UTF_8).replaceAll(" ","").replaceAll("\\(동방프로젝트\\)", "");

            CharacterWLinks.put(name,chr.attr("abs:href"));
        }

        return CharacterWLinks;
    }

    //전달받은 캐릭터 이름이 맵의 키 중 존재할 시, 맵의 값 반환
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

    //캐릭터 표 얻기
    public static Element getCharacterTable(String link) throws Exception {
        Document doc = Jsoup.connect(link).get();
        Element highElement = toGaeyo(doc.body());
        Element table = null;
        for (Object _e : highElement.children()) {
            Element e = (Element) _e;
            String s = e.children().toString();
            int b = BpUtil.keywordScoring(s,"이름","종족","직업","능력","거주지","활동 장소","활동","첫 등장");
            if (b>=4) {
                table = e;
            }
        }
        if (!table.equals(null)) {
            return table;
        } else {
            throw new Exception("표를 찾을 수 없음.");
        }
    }

    //캐릭터 이미지 주소 얻기
    public static String getCharacterImg(Element table) throws Exception {
        Elements imgs = table.selectXpath("//div[@class='"+table.attr("class")+"']//img[@data-src]");
        Element img = null;
        if (imgs.size()==1) {
            img = imgs.get(0);
        } else {
            System.out.println(imgs);
        }

        if (!img.equals(null)) {
            return img.attr("data-src");
        } else {
            throw new Exception("이미지를 못 찾음");
        }
    }
}
