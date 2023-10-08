package com.issuemoa.batch.domain.board;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface BoardRepository extends MongoRepository<Board, String> {
}
