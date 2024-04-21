package com.issuemoa.batch.domain;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface BoardRepository extends MongoRepository<Board, String> {
    void deleteByType(String type);
}
