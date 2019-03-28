package sjph.life.elasticsearch.service;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class OperationServicesImpl<E> implements OperationServices<E> {
    
    private static final Logger  LOGGER = LoggerFactory.getLogger(OperationServices.class);

    @Autowired(required = true)
    private RestTemplate elasticsearchRestTemplate;

    @Override
    public List<E> findObjects(String keyword) {
        String str = elasticsearchRestTemplate.getForObject("http://locahost:9200/life/post/_search?q=content:{key}", String.class,keyword);
        ObjectMapper mapper = new ObjectMapper();
        JsonFactory factory = mapper.getFactory();
        JsonParser parser;
        List<E> list = new LinkedList<>();
        try {
            parser = factory.createParser(str);
            JsonNode root = mapper.readTree(parser);
            JsonNode hitsNode = root.get("hits");
            int result = hitsNode.get("total").asInt();
            JsonNode  array = hitsNode.get("hits");
            Iterator<JsonNode> iterator = array.elements();
            while (iterator.hasNext()) {
                JsonNode node = iterator.next();
                JsonNode sourceNode = node.get("_source");
                list.add(convertJsonNode(sourceNode));
            }
        }
        catch (JsonParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean createObject(E element) {
        HttpEntity<E> entity = new HttpEntity<>(element);
        ResponseEntity<JsonNode> restEntity = elasticsearchRestTemplate.exchange("http://locahost:9200/life/post?pretty",
                HttpMethod.POST, entity, JsonNode.class);
        JsonNode node = restEntity.getBody();
        LOGGER.info("Returned info for createObject: " + node.toString());
        String result = node.get("result").asText();
        if (result.equals("created")) {
            return true;
        }
        return false;
    }

}
