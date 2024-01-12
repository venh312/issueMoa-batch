package com.issuemoa.batch.domain.board;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Document(collection = "board")
public class Board {
    @MongoId
    private ObjectId id;
    private String type;
    private String title;
    private String contents;
    private String url;
    private String thumbnail;
    private List<String> favoriteUserIds;
    private String register;
    private LocalDateTime registerDateTime;

    @Builder
    public Board(String type, String title, String contents, String url, String thumbnail) {
        this.type = type;
        this.title = title;
        this.contents = contents;
        this.url = url;
        this.thumbnail = thumbnail;
        this.favoriteUserIds = new ArrayList<>();
        this.register = "BATCH";
        this.registerDateTime = LocalDateTime.now();
    }
}