package com.issuemoa.batch.domain.board;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;
import java.time.LocalDateTime;

@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Document(collection = "board")
public class Board {
    @MongoId
    private String id;
    private String type;
    private String title;
    private String contents;
    private String url;
    private String thumbnail;
    private String register;
    private LocalDateTime registerDateTime;

    @Builder
    public Board(String id, String type, String title, String contents, String url, String thumbnail) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.contents = contents;
        this.url = url;
        this.thumbnail = thumbnail;
        this.register = "BATCH";
        this.registerDateTime = LocalDateTime.now();
    }
}