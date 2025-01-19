package com.issuemoa.batch.domain.prodcut;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@Table(name = "products")
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String goodsId;
    private String name;
    private String unitDivCode;
    private String baseCnt;
    private String smlclsCode;
    private String detailMean;
    private String totalCnt;
    private String totalDivCode;
    private Long registerId;
    private Long modifyId;
    private LocalDateTime registerTime;
    private LocalDateTime modifyTime;
}
