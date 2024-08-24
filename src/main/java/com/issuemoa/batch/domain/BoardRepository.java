package com.issuemoa.batch.domain;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Set;

public interface BoardRepository extends MongoRepository<Board, String> {
    void deleteByType(String type);
    List<Board> findByUrlIn(Set<String> urls);
}
