package sjph.life.post.service.impl;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import sjph.life.elasticsearch.service.OperationServicesImpl;
import sjph.life.post.model.Post;

@Service
public class ElasticsearchServices extends OperationServicesImpl<Post>{

    @Override
    public Post convertJsonNode(JsonNode node) {
        ObjectMapper mapper = new ObjectMapper();
        Post post  = mapper.convertValue(node, Post.class);
        return post;
    }

}
