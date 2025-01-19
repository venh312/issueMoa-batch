package com.issuemoa.batch.domain.prodcut.price;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@Table(name = "products_price")
@Entity
public class ProductPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String inspectDay;
    private String goodsId;
    private String entpId;
    private String price;
    private String plusOneYn;
    private String dcYn;
    private String dcStartDay;
    private String dcEndDay;
    private Long registerId;
    private Long modifyId;
    private LocalDateTime registerTime;
    private LocalDateTime modifyTime;
}
