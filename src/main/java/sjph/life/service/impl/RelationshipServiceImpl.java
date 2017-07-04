package sjph.life.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sjph.life.model.dao.RelationshipDao;
import sjph.life.service.RelationshipService;

/**
 * @author shaohuiguo
 *
 */
@Service
public class RelationshipServiceImpl implements RelationshipService {

    @Autowired(required = true)
    private RelationshipDao relationshipDao;

    @Override
    public void createRelationship(Long userId, Long followerId) {
        relationshipDao.createRelationship(userId, followerId);
    }

    @Override
    public List<Long> getFollwers(Long userId) {
        return relationshipDao.getFollwers(userId);
    }

    @Override
    public List<Long> getFollwees(Long followerId) {
        return relationshipDao.getFollwees(followerId);
    }

    @Override
    public Long getNumberOfFollower(Long userId) {
        return relationshipDao.getNumberOfFollower(userId);
    }

    @Override
    public int deleteFollwer(Long userId, Long followerId) {
        return relationshipDao.deleteFollwer(userId, followerId);
    }

    @Override
    public int deleteFollwee(Long userId, Long followerId) {
        return relationshipDao.deleteFollwee(userId, followerId);
    }

    @Override
    public boolean isFollowUser(Long userId, Long followerId) {
        return relationshipDao.isFollowUser(userId, followerId);
    }
}
