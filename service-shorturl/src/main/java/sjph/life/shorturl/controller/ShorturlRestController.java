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
package sjph.life.shorturl.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import sjph.life.shorturl.model.Shorturl;
import sjph.life.shorturl.service.ShorturlService;

/**
 * Rest controller for {@link ShorturlService} operations.
 * 
 * @author Shaohui Guo
 *
 */
@RestController
@RequestMapping(value = "v1/shorturl")
public class ShorturlRestController {

    private static final Logger LOGGER   = LoggerFactory.getLogger(ShorturlRestController.class);

    @Autowired(required = true)
    private ShorturlService     shorturlService;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "Work hard, Good luck!";
    }

    @RequestMapping(value = "/getshort", method = RequestMethod.POST)
    public String getShorturl(@RequestBody Shorturl shorturl) {

        return shorturlService.getShorturl(shorturl);
    }

    @RequestMapping(value = "/getlong", method = RequestMethod.POST)
    public String getLongturl(@RequestBody Shorturl shorturl) {

        return shorturlService.getLongurl(shorturl.getShorturl());
    }
    
//    @RequestMapping(value = "/{longurl}/getshort", method = RequestMethod.GET)
//    public String getShorturl(@PathVariable("longurl") String longurl) {
//
//        return shorturlService.getShorturl(longurl);
//    }
//
//    @RequestMapping(value = "/{shorturl}/getlong", method = RequestMethod.GET)
//    public String getLongturl(@PathVariable("shorturl") String shorturl) {
//
//        return shorturlService.getLongurl(shorturl);
//    }

}
