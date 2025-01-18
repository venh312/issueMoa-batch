package com.issuemoa.batch.infrastructure.writer;

import com.issuemoa.batch.domain.store.Store;
import com.issuemoa.batch.domain.store.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Component
public class StoreWriter implements ItemWriter<List<Store>> {

    private final StoreRepository storeRepository;

    @Override
    public void write(List<? extends List<Store>> items) {
        log.info("==> [StoreWriter] items.size :: {}", items.size());
        List<Store> stores = new ArrayList<>();

        items.forEach(list -> list.forEach(stores::add));

        storeRepository.saveAll(stores);
    }
}
