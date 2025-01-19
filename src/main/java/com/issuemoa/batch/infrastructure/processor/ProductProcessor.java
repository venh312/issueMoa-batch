package com.issuemoa.batch.infrastructure.processor;

import com.issuemoa.batch.domain.prodcut.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class ProductProcessor implements ItemProcessor<NodeList, List<Product>> {
    @Override
    public List<Product> process(NodeList nodes) {
        log.info("==> [ProductProcessor] nodes.size :: {}", nodes.getLength());

        List<Product> products = new ArrayList<>();
        for (int i = 0; i < nodes.getLength(); i++) {
            Element element = (Element) nodes.item(i);
            Product store = Product.builder()
                            .goodsId(getTagValue("goodId", element))
                            .name(getTagValue("goodName", element))
                            .unitDivCode(getTagValue("goodUnitDivCode", element))
                            .baseCnt(getTagValue("goodBaseCnt", element))
                            .smlclsCode(getTagValue("goodSmlclsCode", element))
                            .detailMean(getTagValue("detailMean", element))
                            .totalCnt(getTagValue("goodTotalCnt", element))
                            .totalDivCode(getTagValue("goodTotalDivCode", element))
                            .registerId(0L)
                            .registerTime(LocalDateTime.now())
                            .build();
            products.add(store);
        }

        return products;
    }

    private String getTagValue(String tagName, Element element) {
        NodeList nodeList = element.getElementsByTagName(tagName);
        if (nodeList.getLength() > 0)
            return nodeList.item(0).getTextContent();
        return null;
    }
}
