package uk.co.solong.listtf.core.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.collections4.ListUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;

import uk.co.solong.listtf.core.exceptions.DetailDoesNotExistException;
import uk.co.solong.listtf.core.exceptions.ItemDoesNotExistException;
import uk.co.solong.listtf.core.exceptions.SearchFailedException;
import uk.co.solong.listtf.core.pojo.mongo.WantedDocument;
import uk.co.solong.listtf.core.pojo.mongo.WantedDocumentDetail;
import uk.co.solong.listtf.core.pojo.mongo.WantedDocumentId;
import uk.co.solong.listtf.core.pojo.mongo.WantedState;
import uk.co.solong.listtf.core.pojo.requests.DetailResult;
import uk.co.solong.listtf.core.pojo.requests.ItemState;
import uk.co.solong.listtf.core.pojo.requests.ListRequest;
import uk.co.solong.listtf.core.pojo.requests.ListResult;
import uk.co.solong.listtf.core.pojo.requests.SyncResult;
import uk.co.solong.listtf.core.pojo.requests.WantedDetail;

public class ListDao {

    private static final Logger logger = LoggerFactory.getLogger(ListDao.class);
    private final MongoTemplate mongoTemplate;

    public SyncResult sync(String userId) {
        RestTemplate t = new RestTemplate();
        JsonNode l = t.getForObject("https://schema.tf/api/getAllItemsCdn", JsonNode.class);
        List<Integer> allDefIndexes = new ArrayList<>();
        for (JsonNode item : l) {
            allDefIndexes.add(item.get("defindex").asInt());
        }
        
        
        Query findUserQuery = new Query();
        findUserQuery.addCriteria(Criteria.where("_id.userId").is(userId));
        List<WantedDocument> find = mongoTemplate.find(findUserQuery, WantedDocument.class);
        List<Integer> usersDefIndexes = find.stream().map(x -> x.getWantedDocumentId().getDefIndex()).collect(Collectors.toList());
        List<Integer> outstandingDefIndexes = ListUtils.subtract(allDefIndexes, usersDefIndexes);

        List<WantedDocument> docsToInsert = outstandingDefIndexes.stream().map(x -> new WantedDocument(new WantedDocumentId(userId, x), WantedState.unknown.getState(), new ArrayList<>(0)))
                .collect(Collectors.toList());
        mongoTemplate.insert(docsToInsert, WantedDocument.class);
        
        SyncResult syncResult = new SyncResult();
        syncResult.setCount(docsToInsert.size());
        return syncResult;
        // request the latest item list (cached in memory) sourced from
        // schema.tf
        // request all items from the user
        // create a list of entries based on the diff marked as unknown
        
    }

    public ListResult searchItems(ListRequest listRequest) throws SearchFailedException {
        // return a list of all entries for the given user
        Query findUserQuery = new Query();
        findUserQuery.addCriteria(Criteria.where("_id.userId").is(listRequest.getUserId()));
        List<WantedDocument> wantedDocuments = mongoTemplate.find(findUserQuery, WantedDocument.class);
        ListResult result = new ListResult();
        result.setResultList(wantedDocuments);
        return result;
    }

    public DetailResult add(WantedDetail wantedDetail, String userId) {
        Query findUserQuery = new Query();
        findUserQuery.addCriteria(Criteria.where("_id.userId").is(userId).andOperator(Criteria.where("_id.defIndex").is(wantedDetail.getDefIndex())));
        WantedDocument wantedDocument = mongoTemplate.findOne(findUserQuery, WantedDocument.class);
        if (wantedDocument != null) {
            String wantedDetailId = UUID.randomUUID().toString();
            WantedDocumentDetail wantedDocumentDetail = new WantedDocumentDetail();
            wantedDocumentDetail.setWantedId(wantedDetailId);
            wantedDocumentDetail.setCraftable(wantedDetail.getIsCraftable());
            wantedDocumentDetail.setCraftNumber(wantedDetail.getCraftNumber());
            wantedDocumentDetail.setGifted(wantedDetail.getIsGifted());
            wantedDocumentDetail.setLevel(wantedDetail.getLevel());
            wantedDocumentDetail.setNumbered(wantedDetail.getIsNumbered());
            wantedDocumentDetail.setQuality(wantedDetail.getQuality());
            wantedDocumentDetail.setTradable(wantedDetail.getIsTradable());
            wantedDocument.getWantedDocumentDetails().add(wantedDocumentDetail);
            mongoTemplate.save(wantedDocument);
            // TODO manage write concern (no write concern available now due to
            // save abstraction. still though...??)
        } else {
            throw new ItemDoesNotExistException();
        }
        DetailResult detailResult = new DetailResult();
        detailResult.setWantedDocument(wantedDocument);
        return detailResult;
    }

    public DetailResult edit(WantedDetail wantedDetail, String userId) {
        Query findUserQuery = new Query();
        findUserQuery.addCriteria(Criteria.where("_id.userId").is(userId).andOperator(Criteria.where("_id.defIndex").is(wantedDetail.getDefIndex())));
        WantedDocument wantedDocument = mongoTemplate.findOne(findUserQuery, WantedDocument.class);
        if (wantedDocument != null) {
            String wantedDetailId = wantedDetail.getId();
            Optional<WantedDocumentDetail> maybeWantedDocumentDetail = wantedDocument.getWantedDocumentDetails().stream()
                    .filter(x -> x.getWantedId().equals(wantedDetailId)).findFirst();
            if (maybeWantedDocumentDetail.isPresent()) {
                WantedDocumentDetail wantedDocumentDetail = maybeWantedDocumentDetail.get();
                wantedDocumentDetail.setCraftable(wantedDetail.getIsCraftable());
                wantedDocumentDetail.setCraftNumber(wantedDetail.getCraftNumber());
                wantedDocumentDetail.setGifted(wantedDetail.getIsGifted());
                wantedDocumentDetail.setLevel(wantedDetail.getLevel());
                wantedDocumentDetail.setNumbered(wantedDetail.getIsNumbered());
                wantedDocumentDetail.setQuality(wantedDetail.getQuality());
                wantedDocumentDetail.setTradable(wantedDetail.getIsTradable());
                mongoTemplate.save(wantedDocument);
                // TODO manage write concern (no write concern available now due
                // to save abstraction. still though...??)
            } else {
                throw new DetailDoesNotExistException();
            }

        } else {
            throw new ItemDoesNotExistException();
        }
        DetailResult detailResult = new DetailResult();
        detailResult.setWantedDocument(wantedDocument);
        return detailResult;
    }

    public DetailResult delete(WantedDetail wantedDetail, String userId) {
        Query findUserQuery = new Query();
        findUserQuery.addCriteria(Criteria.where("_id.userId").is(userId).andOperator(Criteria.where("_id.defIndex").is(wantedDetail.getDefIndex())));
        WantedDocument wantedDocument = mongoTemplate.findOne(findUserQuery, WantedDocument.class);
        if (wantedDocument != null) {
            String wantedDetailId = wantedDetail.getId();
            List<WantedDocumentDetail> collect = wantedDocument.getWantedDocumentDetails().stream().filter(x -> !x.getWantedId().equals(wantedDetailId))
                    .collect(Collectors.toList());
            wantedDocument.setWantedDocumentDetails(collect);
            mongoTemplate.save(wantedDocument);
            // TODO manage write concern (no write concern available now due to
            // save abstraction. still though...??)
        } else {
            throw new ItemDoesNotExistException();
        }
        DetailResult detailResult = new DetailResult();
        detailResult.setWantedDocument(wantedDocument);
        return detailResult;
    }

    public DetailResult edit(ItemState itemState, String userId) {
        // get the entry (defindex, userid)
        // ammend the state
        Query findUserQuery = new Query();
        findUserQuery.addCriteria(Criteria.where("_id.userId").is(userId).andOperator(Criteria.where("_id.defIndex").is(itemState.getDefIndex())));
        WantedDocument wantedDocument = mongoTemplate.findOne(findUserQuery, WantedDocument.class);
        if (wantedDocument != null) {
            wantedDocument.setState(itemState.getState());
            mongoTemplate.save(wantedDocument);
        } else {
            throw new ItemDoesNotExistException();
        }
        DetailResult detailResult = new DetailResult();
        detailResult.setWantedDocument(wantedDocument);
        return detailResult;
    }

    public ListDao(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

}
