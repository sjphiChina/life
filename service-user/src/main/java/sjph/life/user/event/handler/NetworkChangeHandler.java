/**
 * 
 */
package sjph.life.user.event.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

import sjph.life.user.cache.RelationshipCacheHandler;
import sjph.life.user.event.CustomChannels;
import sjph.life.user.event.model.NetworkChangeModel;

/**
 * @author Shaohui Guo
 *
 */
@EnableBinding(CustomChannels.class)
public class NetworkChangeHandler {

    private static final Logger logger = LoggerFactory.getLogger(NetworkChangeHandler.class);
    
    @Autowired(required = true)
    private RelationshipCacheHandler    relationshipCacheHandler;
    
    @StreamListener("inboundNetworkChanges")
    public void loggerSink(NetworkChangeModel networkChange) {
        logger.debug("Received a message: " + networkChange.toString());
        
        switch(networkChange.getAction()){
            case "follow":
                logger.debug("Received a FOLLOW event from the organization service for organization id {}", networkChange.toString());
                break;
            case "unFollow":
                logger.debug("Received a UNFOLLOW event from the organization service for organization id {}", networkChange.toString());
                break;
            case "GET_FOLLOWERS":
                logger.debug("Received a GET_FOLLOWERS event from the organization service for organization id {}", relationshipCacheHandler.getFollowers(networkChange.getNetworkId()));
                break;
            case "GET_FOLLOWINGS":
                logger.debug("Received a GET_FOLLOWINGS event from the organization service for organization id {}", relationshipCacheHandler.getFollowing(networkChange.getNetworkId()));
                break;
            case "GET_NETWORK":
                logger.debug("Received a GET_NETWORK event from the organization service for organization id {}", networkChange.toString());
                break;
            case "GET_NUMBER_OF_FOLLOWERS":
                logger.debug("Received a GET_NUMBER_OF_FOLLOWERS event from the organization service for organization id {}", networkChange.toString());
                break;
            case "DELETE_FOLLOWER":
                logger.debug("Received a DELETE_FOLLOWER event from the organization service for organization id {}", networkChange.toString());
                break;
            default:
                logger.error("Received an UNKNOWN event from the organization service of type {}", networkChange.getType());
                break;

        }
    }
}
