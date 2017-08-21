package sjph.life.friendship.controller;

import sjph.life.friendship.service.RelationshipService;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="v1/friendship/{userId}/")
public class FriendshipServiceController {

    @Autowired
    private RelationshipService relationshipService;

    @RequestMapping(value="/follow/{followingId}",method = RequestMethod.GET)
    public void follow( @PathVariable("userId") String userId,
                                @PathVariable("followingId") String followingId) {
        relationshipService.follow(userId, followingId);
        
    }
    
    @RequestMapping(value="/unfollow/{followingId}",method = RequestMethod.GET)
    public void unfollow( @PathVariable("userId") String userId,
                                @PathVariable("followingId") String followingId) {
        relationshipService.unFollow(userId, followingId);
        
    }
    
    @RequestMapping(value="/getfollowers",method = RequestMethod.GET)
    public Collection<String> getFollowers( @PathVariable("userId") String userId) {
        return relationshipService.getFollwers(userId);
    }
    
    @RequestMapping(value="/getfollowings",method = RequestMethod.GET)
    public Collection<String> getFollowings( @PathVariable("userId") String userId) {
        return relationshipService.getFollwees(userId);
    }
}
