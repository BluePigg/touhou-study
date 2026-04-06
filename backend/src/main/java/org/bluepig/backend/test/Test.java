package org.bluepig.backend.test;

import org.bluepig.backend.Soup;

import java.io.IOException;
import java.util.Map;

public class Test {
    public static void main(String[] args) throws IOException {
        Map<String,String> map = Soup.Crawl();
        System.out.println(map);
    }
}
