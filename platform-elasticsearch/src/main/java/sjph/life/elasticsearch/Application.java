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
package sjph.life.elasticsearch;

import java.util.Iterator;
import java.util.Map;

import org.elasticsearch.client.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import sjph.life.elasticsearch.book.service.BookService;

/**
 * Spring Boot application for elasticsearch.
 *
 * @author Shaohui Guo
 */
@SpringBootApplication
public class Application implements CommandLineRunner {

    private static final Logger      logger = LoggerFactory.getLogger(Application.class);

    @Bean
    public RestTemplate getElasticsearchRestTemplate() {
        RestTemplate template = new RestTemplate();
        return template;
    }
    
    @Autowired
    private ElasticsearchOperations es;

    @Autowired
    private BookService bookService;

    public static void main(String args[]) {
        SpringApplication.run(Application.class, args);
    }

    
    @Override
    public void run(String... args) throws Exception {

        printElasticSearchInfo();

        bookService.save(new Book("1001", "Elasticsearch Basics", "Rambabu Posa", "23-FEB-2017"));
        bookService.save(new Book("1002", "Apache Lucene Basics", "Rambabu Posa", "13-MAR-2017"));
        bookService.save(new Book("1003", "Apache Solr Basics", "Rambabu Posa", "21-MAR-2017"));

        //fuzzey search
        Page<Book> books = bookService.findByAuthor("Rambabu", new PageRequest(0, 10));

        //List<Book> books = bookService.findByTitle("Elasticsearch Basics");

        books.forEach(x -> System.out.println(x));


    }

    //useful for debug, print elastic search details
    private void printElasticSearchInfo() {

        System.out.println("--ElasticSearch--");
        Client client = es.getClient();
        Map<String, String> asMap = client.settings().getAsMap();

        asMap.forEach((k, v) -> {
            System.out.println(k + " = " + v);
        });
        System.out.println("--ElasticSearch--");
    }
    
    
    //@Override
    public void runN(String... args) throws Exception {

      RestTemplate template = new RestTemplate();
        
      
      Book book  = new Book();
      book.setMessage("one test 吃 from client");
      book.setName("sf's food");
      HttpEntity<Book> entity = new HttpEntity<>(book);
      ResponseEntity<JsonNode> restExchange = template.exchange("http://127.0.0.1:9200/msg/test?pretty",
              HttpMethod.POST, entity, JsonNode.class);
      
//        Book book = null;
//        Map<String,Object> paras = new HashMap<String,Object>();
//        paras.put("id", 1);
//        String str = template.getForObject("http://127.0.0.1:9200/msg/test/{id}", String.class,paras);
//        ObjectMapper mapper = new ObjectMapper();
//        JsonFactory factory = mapper.getFactory();
//        JsonParser parser = factory.createParser(str);
//        JsonNode root = mapper.readTree(parser);
//        JsonNode sourceNode = root.get("_source");
//        book  = mapper.convertValue(sourceNode, Book.class);
//        paras.put("key", "");
      String str = template.getForObject("http://127.0.0.1:9200/msg/test/_search?q=message:{key}", String.class,"吃");
      ObjectMapper mapper = new ObjectMapper();
      JsonFactory factory = mapper.getFactory();
      JsonParser parser = factory.createParser(str);
      JsonNode root = mapper.readTree(parser);
      JsonNode hitsNode = root.get("hits");
      int result = hitsNode.get("total").asInt();
      JsonNode  array = hitsNode.get("hits");
      Iterator<JsonNode> iterator = array.elements();
      while (iterator.hasNext()) {
          JsonNode node = iterator.next();
          JsonNode sourceNode = node.get("_source");
          Book bookunit  = mapper.convertValue(sourceNode, Book.class);
          System.out.println("message: "+bookunit.getMessage());
      }
      
      
        
        
        
        
        
//        Date createdDate = new Date();
//        postElasticsearchService.save(new PostElasticsearch("1001", "Elasticsearch Basics",
//                "Rambabu Posa", createdDate, createdDate, "guoshaohui"));
//        postElasticsearchService.save(new PostElasticsearch("1002", "Apache Lucene Basics",
//                "Rambabu Posa", createdDate, createdDate, "guoshaohui"));
//        postElasticsearchService.save(new PostElasticsearch("1003", "Apache Solr Basics",
//                "Rambabu Posa", createdDate, createdDate, "guoshaohui"));
//
//        // fuzzey search
//        Page<PostElasticsearch> posts = postElasticsearchService.findByContent("Apache",
//                new PageRequest(0, 10));

        // List<Book> books = bookService.findByTitle("Elasticsearch Basics");


    }
}
