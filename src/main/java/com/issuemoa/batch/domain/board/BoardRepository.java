package com.issuemoa.batch.domain.board;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Set;

public interface BoardRepository extends MongoRepository<Board, String> {
    void deleteByType(String type);
    List<Board> findByUrlIn(Set<String> urls);
    Page<Board> findAll(Pageable pageable);
}
