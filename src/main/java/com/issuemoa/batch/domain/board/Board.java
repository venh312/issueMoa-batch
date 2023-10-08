package com.issuemoa.batch.domain.board;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Document(collection = "board")
public class Board {
    @Id
    private String id;
    private String type;
    private String startDate;
    private String endDate;
    private String allTimeYn;
    private String title;
    private String contents;
    private String url;
    private Long readCount;
    private Long registerId;
    private Long modifyId;

    @Builder
    public Board(String id, String type, String startDate, String endDate, String allTimeYn, String title, String contents, String url, Long readCount, Long registerId, Long modifyId) {
        this.id = id;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
        this.allTimeYn = allTimeYn;
        this.title = title;
        this.contents = contents;
        this.url = url;
        this.readCount = readCount;
        this.registerId = registerId;
        this.modifyId = modifyId;
    }

    public Board(String type, String allTimeYn, String title, String contents, String url) {
        this.type = type;
        this.allTimeYn = allTimeYn;
        this.title = title;
        this.contents = contents;
        this.url = url;
    }
}