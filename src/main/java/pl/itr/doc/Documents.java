package pl.itr.doc;

import java.util.List;

public class Documents {
    private final List<Document> documents;

    public Documents(List<Document> documents) {
        this.documents = documents;
    }

    @Override
    public String toString() {
        return "Documents{" +
                "documents=" + documents +
                '}';
    }
}
