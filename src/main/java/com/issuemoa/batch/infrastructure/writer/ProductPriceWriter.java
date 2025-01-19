package com.issuemoa.batch.infrastructure.writer;

import com.issuemoa.batch.domain.prodcut.price.ProductPrice;
import com.issuemoa.batch.domain.prodcut.price.ProductPriceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Component
public class ProductPriceWriter implements ItemWriter<List<ProductPrice>> {

    private final ProductPriceRepository productPriceRepository;

    @Override
    public void write(List<? extends List<ProductPrice>> items) {
        log.info("==> [ProductPriceWriter] items.size :: {}", items.size());
        List<ProductPrice> products = new ArrayList<>();

        items.forEach(list -> list.forEach(products::add));

        productPriceRepository.saveAll(products);
    }
}
