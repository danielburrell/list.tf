package uk.co.solong.listtf.core.pojo.requests;

import java.util.List;

import uk.co.solong.listtf.core.pojo.mongo.WantedDocument;

public class ListResult {
    private List<WantedDocument> resultList;

    public List<WantedDocument> getResultList() {
        return resultList;
    }

    public void setResultList(List<WantedDocument> resultList) {
        this.resultList = resultList;
    }

}
