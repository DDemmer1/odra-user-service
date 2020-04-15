package de.demmer.dennis.odrauserservice.service;


import de.demmer.dennis.odrauserservice.model.meta.Comment;
import de.demmer.dennis.odrauserservice.model.meta.Metadata;
import de.demmer.dennis.odrauserservice.model.meta.tag.Flag;
import de.demmer.dennis.odrauserservice.model.meta.tag.Star;
import de.demmer.dennis.odrauserservice.model.meta.Topic;
import de.demmer.dennis.odrauserservice.repository.MetadataRepository;
import de.demmer.dennis.odrauserservice.repository.StarRepository;
import de.demmer.dennis.odrauserservice.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class MetadataService {

    @Autowired
    MetadataRepository metadataRepository;

    @Autowired
    TopicRepository topicRepository;

    @Autowired
    StarRepository starRepository;


    public Metadata getMetaOfMedia(long mediaId){
        return metadataRepository.findByMediaId(mediaId).orElse(new Metadata(mediaId));
    }


    public void addComment(Comment comment, long mediaId){

    }

    public boolean addTopic(Topic topic, long mediaId){
        Metadata meta = metadataRepository.findByMediaId(mediaId).orElse(new Metadata(mediaId));
        Set<Topic> topics = meta.getTopics();
        topics.add(topic);
        metadataRepository.save(meta);
        return true;
    }

    public boolean addStar(Star star, long mediaId){
        Metadata meta = metadataRepository.findByMediaId(mediaId).orElse(new Metadata(mediaId));
        List<Star> stars = meta.getStars();
        if(userAlreadyStared(stars,star)){
            stars.remove(star);
            metadataRepository.save(meta);
            starRepository.deleteByUseridAndMediaId(star.getUserid(),mediaId);
            return false;
        } else {
            starRepository.save(star);
            meta.getStars().add(star);
            metadataRepository.save(meta);
            return true;
        }
    }

    public boolean addFlag(Flag flag, Long mediaId) {
        Metadata meta = metadataRepository.findByMediaId(mediaId).orElse(new Metadata(mediaId));
        List<Flag> flags = meta.getFlags();
        if(userAlreadyFlagged(flags,flag)){
            flags.remove(flag);
            metadataRepository.save(meta);
            return false;
        } else {
            meta.getFlags().add(flag);
            metadataRepository.save(meta);
            return true;
        }
    }

    private boolean userAlreadyStared(List<Star> stars, Star star) {
        for (Star ele : stars) {
            if(ele.getUserid() == star.getUserid() && ele.getMediaId() == star.getMediaId()){
                return true;
            }
        }
        return false;
    }

    private boolean userAlreadyFlagged(List<Flag> flags, Flag flag) {
        for (Flag ele : flags) {
            if(ele.getUserid() == flag.getUserid() && ele.getMediaId() == flag.getMediaId()){
                return true;
            }
        }
        return false;
    }


    public Set<Topic> getAllTopics() {
        Set<Topic> result = new HashSet<>();
        for (Topic topic :topicRepository.findAll()) {
            result.add(topic);
        }
        return result;
    }

    public boolean deleteTopic(long topicId, long metaId) {
        Metadata meta = metadataRepository.findById(metaId).get();

        Topic toDelete = null;
        for (Topic topic : meta.getTopics()) {
            if(topic.getId() == topicId){
                toDelete = topic;
            }
        }
        meta.getTopics().remove(toDelete);
        metadataRepository.save(meta);
        return true;
    }

    public Set<Star> getAllStars() {
        Set<Star> result = new HashSet<>();
        for (Star star :starRepository.findAll()) {
            result.add(star);
        }
        return result;
    }


    public Set<Star> getStarsByUserId(long userId) {
        Set<Star> result = new HashSet<>();
        for (Star star :starRepository.findAllByUserid(userId)) {
            result.add(star);
        }
        return result;
    }


    public List<Topic> getAllTopicsByName(String topicName) {
        List<Topic> result = new ArrayList<>();
        for (Topic topic :topicRepository.findAllByTopicName(topicName)) {
            result.add(topic);
        }
        return result;
    }
}
