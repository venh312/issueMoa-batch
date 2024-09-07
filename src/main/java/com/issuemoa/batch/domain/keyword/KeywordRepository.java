package com.issuemoa.batch.domain.keyword;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface KeywordRepository extends MongoRepository<Keyword, String> {
}
