package com.issuemoa.batch.domain.keyword;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;

@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Document(collection = "keyword")
public class Keyword {
    @MongoId
    private ObjectId id;
    private String keyword;
    private int count;
    private LocalDateTime registerDateTime;

    @Builder
    public Keyword(String keyword, int count) {
        this.keyword = keyword;
        this.count = count;
        this.registerDateTime = LocalDateTime.now();
    }
}
