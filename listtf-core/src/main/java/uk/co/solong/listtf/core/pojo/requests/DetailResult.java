package uk.co.solong.listtf.core.pojo.requests;

import uk.co.solong.listtf.core.pojo.mongo.WantedDocument;

public class DetailResult {
    private WantedDocument wantedDocument;

    public WantedDocument getWantedDocument() {
        return wantedDocument;
    }

    public void setWantedDocument(WantedDocument wantedDocument) {
        this.wantedDocument = wantedDocument;
    }
}
