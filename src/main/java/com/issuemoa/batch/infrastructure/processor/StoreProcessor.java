package com.issuemoa.batch.infrastructure.processor;

import com.issuemoa.batch.domain.store.Store;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Component
public class StoreProcessor implements ItemProcessor<NodeList, List<Store>> {
    @Override
    public List<Store> process(NodeList nodes) {
        log.info("==> [StoreProcessor] nodes.size :: {}", nodes.getLength());

        List<Store> stores = new ArrayList<>();
        for (int i = 0; i < nodes.getLength(); i++) {
            Element element = (Element) nodes.item(i);
            Store store = Store.builder()
                            .entpId(getTagValue("entpId", element))
                            .name(getTagValue("entpName", element))
                            .typeCode(getTagValue("entpTypeCode", element))
                            .areaCode(getTagValue("entpAreaCode", element))
                            .detailCode(getTagValue("areaDetailCode", element))
                            .tel(getTagValue("entpTelno", element))
                            .postNo(getTagValue("postNo", element))
                            .addr(getTagValue("plmkAddrBasic", element))
                            .addrDetail(getTagValue("plmkAddrDetail", element))
                            .roadAddr(getTagValue("roadAddrBasic", element))
                            .roadAddrDetail(getTagValue("roadAddrDetail", element))
                            .xCoord(getTagValue("xMapCoord", element))
                            .yCoord(getTagValue("yMapCoord", element))
                            .registerId(0L)
                            .registerTime(LocalDateTime.now())
                            .build();
            stores.add(store);
        }

        return stores;
    }

    private String getTagValue(String tagName, Element element) {
        NodeList nodeList = element.getElementsByTagName(tagName);
        if (nodeList.getLength() > 0)
            return nodeList.item(0).getTextContent();
        return null;
    }
}
