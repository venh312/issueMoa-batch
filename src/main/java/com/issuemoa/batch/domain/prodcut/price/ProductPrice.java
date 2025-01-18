package com.issuemoa.batch.domain.prodcut.price;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "productprice")
@Entity
public class ProductPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long storeId;
    private Long productId;
    private int price;
    private String plusOneYn;
    private String dcYn;
    private int dcStartDay;
    private int dcEndDay;
    private LocalDateTime registerTime;
    private LocalDateTime modifyTime;

}
