/*
 * Copyright 2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package sjph.life.shorturl.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sjph.life.shorturl.config.ServiceConfig;
import sjph.life.shorturl.dao.ShorturlDao;
import sjph.life.shorturl.model.Shorturl;

/**
 * @author shaohuiguo
 *
 */
@Service
public class ShorturlService {
    private static final Logger LOGGER   = LoggerFactory.getLogger(ShorturlService.class);
    private static String       charsSet = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    @Autowired(required = true)
    private ServiceConfig       config;
    @Autowired(required = true)
    private ShorturlDao         shorturlDao;

    public String getShorturl(Shorturl shorturl) {
        long id = shorturlDao.createShorturlRecord(shorturl);
        String shorturlString = convertLongToShort(id);
        shorturl.setId(id);
        shorturl.setShorturl(shorturlString);
        shorturlDao.updateShorturlRecord(shorturl);
        return config.getShorturlDomain() + "/" + shorturl.getShorturl();
    }

    public String getLongurl(String shorturl) {
        long id = converShortUrlToId(shorturl);
        Shorturl shorturlObject = shorturlDao.findById(id);
        return shorturlObject.getLongurl();
    }

    public String convertLongToShort(long id) {
        StringBuilder sb = new StringBuilder();
        long num = id;
        while (num > 0) {
            int index = (int) num % 62;
            sb.append(charsSet.charAt(index));
            System.out.println(sb);
            num = num / 62;
        }
        //String reversedString = sb.reverse().toString();
        String result = sb.toString();
        return result;
    }

    public long converShortUrlToId(String shorturl) {
        long id = 0l;
        for (int i = 0; i < shorturl.length(); i++) {
            char c = shorturl.charAt(i);
            int index = charsSet.lastIndexOf(c);
            long base = (long)Math.pow(62, i);
            long num = index * base;
            id = id + num;
        }
        return id;
    }
}
