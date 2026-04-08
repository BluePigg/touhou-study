package org.bluepig.backend.test;

import org.bluepig.backend.Soup;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.bluepig.backend.BpUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Test {
    public static Element toGaeyo(Element element) throws Exception {
        Element target = element;
        List<Element> res = new ArrayList<>();
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

    public static void main(String[] args) throws Exception {
        Document soup = Jsoup.connect("https://namu.wiki/w/%EC%9D%B4%EC%9E%90%EC%9A%94%EC%9D%B4%20%EC%82%AC%EC%BF%A0%EC%95%BC").get();
        Element par = toGaeyo(soup.body());

        Elements children = par.children();

        for (Object _e : children) {
            Element e = (Element) _e;
//            String s = e.children().toString();
//            int b = BpUtil.keywordScoring(s,"이름","종족","직업","능력","거주지","활동 장소","활동","첫 등장");
//            System.out.println(b);
//            if (b>=7) {
//                System.out.println(e.attr("class").toString()+": 당첨!");
//            }
            System.out.println(e.getElementsByTag("span").eachText());
        }
    }
}
