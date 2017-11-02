package sjph.life.friendship.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import sjph.life.friendship.config.ServiceConfig;
import sjph.life.friendship.service.RelationshipService;

/**
 * @author Shaohui Guo
 *
 */
@RestController
@RequestMapping(value = "v1/friendship/{userId}/")
public class FriendshipServiceController {

    @Autowired
    private RelationshipService relationshipService;

    @Autowired
    ServiceConfig               config;

    @RequestMapping(value = "/follow/{followingId}", method = RequestMethod.GET)
    public void follow(@PathVariable("userId") String userId,
            @PathVariable("followingId") String followingId) {
        relationshipService.follow(userId, followingId);

    }

    @RequestMapping(value = "/unfollow/{followingId}", method = RequestMethod.GET)
    public void unfollow(@PathVariable("userId") String userId,
            @PathVariable("followingId") String followingId) {
        relationshipService.unFollow(userId, followingId);

    }

    @RequestMapping(value = "/getfollowers", method = RequestMethod.GET)
    public Collection<String> getFollowers(@PathVariable("userId") String userId) {
        // return relationshipService.getFollwers(userId);
        Collection<String> list = relationshipService.getFollwers(userId);
        list.add(config.getExampleProperty());
        return list;
    }

    @RequestMapping(value = "/getfollowings", method = RequestMethod.GET)
    public Collection<String> getFollowings(@PathVariable("userId") String userId) {
        return relationshipService.getFollwees(userId);
    }

    @RequestMapping(value = "/getNetwork", method = RequestMethod.GET)
    public String getNetwork(@PathVariable("userId") String userId) {
        return relationshipService.getNetwork(userId).toString();
    }
}
