package com.issuemoa.batch.infrastructure.writer;

import com.issuemoa.batch.domain.prodcut.Product;
import com.issuemoa.batch.domain.prodcut.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Component
public class ProductWriter implements ItemWriter<List<Product>> {

    private final ProductRepository productRepository;

    @Override
    public void write(List<? extends List<Product>> items) {
        log.info("==> [StoreWriter] items.size :: {}", items.size());
        List<Product> products = new ArrayList<>();

        items.forEach(list -> list.forEach(products::add));

        productRepository.saveAll(products);
    }
}
