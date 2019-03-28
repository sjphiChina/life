package sjph.life.elasticsearch.service;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;

public interface OperationServices<E> {

    List<E> findObjects(String keyword);

    boolean createObject(E element);

    E convertJsonNode(JsonNode node);
}
