package sjph.life.friendship.event.source;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import sjph.life.friendship.event.model.NetworkChangeModel;

@Component
public class NetworkSourceBean {

    private Source source;

    private static final Logger logger = LoggerFactory.getLogger(NetworkSourceBean.class);

    @Autowired
    public NetworkSourceBean(Source source){
        this.source = source;
    }

    public void publishNetworkChange(String action,String userId){
       logger.debug("Sending Kafka message {} for User Id: {}", action, userId);
       NetworkChangeModel change =  new NetworkChangeModel(
               NetworkChangeModel.class.getTypeName(),
                action,
                userId,
                "test");

       MessageChannel messageChannel = source.output();
       messageChannel.send(MessageBuilder.withPayload(change).build());
       logger.debug("Sent Kafka message {} for User Id: {}, Action: {}", change.toString(), userId, action);
    }
}
