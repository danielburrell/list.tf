package uk.co.solong.listtf.core.pojo.mongo;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "wantedDocument")
public class WantedDocument {

    @Id
    private WantedDocumentId wantedDocumentId;
    private List<WantedDocumentDetail> wantedDocumentDetails;
    private int state;

    public WantedDocument(WantedDocumentId wantedDocumentId, int state, List<WantedDocumentDetail> wantedDocumentDetails) {
        this.wantedDocumentId = wantedDocumentId;
        this.wantedDocumentDetails = wantedDocumentDetails;
        this.state = state;
    }

    public List<WantedDocumentDetail> getWantedDocumentDetails() {
        return wantedDocumentDetails;
    }

    public void setWantedDocumentDetails(List<WantedDocumentDetail> wantedDocumentDetails) {
        this.wantedDocumentDetails = wantedDocumentDetails;
    }

    public WantedDocumentId getWantedDocumentId() {
        return wantedDocumentId;
    }

    public void setWantedDocumentId(WantedDocumentId wantedDocumentId) {
        this.wantedDocumentId = wantedDocumentId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

}
