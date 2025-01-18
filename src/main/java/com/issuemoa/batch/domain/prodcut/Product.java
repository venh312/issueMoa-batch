package com.issuemoa.batch.domain.prodcut;


import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "products")
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String unitDivCode;
    private int baseCnt;
    private int smlclsCode;
    private String detailMean;
    private int totalCnt;
    private String totalDivCode;
    private Long registerId;
    private LocalDateTime registerTime;
    private Long modifyId;
    private LocalDateTime modifyTime;
}
