package com.issuemoa.batch.domain.board;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface BoardRepository extends MongoRepository<Board, String> {
    void deleteByType(String type);
    List<Board> findByUrlIn(Set<String> urls);
    List<Board> findByRegisterDateTimeBetween(LocalDateTime startOfDay, LocalDateTime endOfDay, Pageable pageable);
}
