package com.issuemoa.batch.infrastructure.reader;

import com.issuemoa.batch.infrastructure.utils.HttpUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.w3c.dom.NodeList;

@Slf4j
@RequiredArgsConstructor
@Component
public class StoreReader implements ItemReader<NodeList> {
    private final HttpUtil httpUtil;

    @Value("${endpoint.open.product.store}")
    private String endpointProductStore;
    @Value("${api.key.open.store}")
    private String storeKey;

    private boolean isReadCompleted = false;  // 한 번만 데이터를 읽도록 제어

    @Override
    public NodeList read() {
        if (isReadCompleted) {
            return null;  // 이미 읽었으므로 더 이상 데이터를 읽지 않음
        }

        log.info("==> [StoreReader] API 호출 시작");
        String url = endpointProductStore + "?serviceKey=" + storeKey;

        // 외부 API 호출
        NodeList nodeList = httpUtil.sendAndReceiveXml(url, "", false, "application/json", "", "iros.openapi.service.vo.entpInfoVO");

        if (nodeList == null || nodeList.getLength() == 0) {
            return null;
        }

        isReadCompleted = true;  // 데이터 한 번만 읽기
        return nodeList;
    }
}