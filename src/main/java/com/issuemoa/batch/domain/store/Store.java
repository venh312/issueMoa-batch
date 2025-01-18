package com.issuemoa.batch.domain.store;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import javax.persistence.*;
import java.time.LocalDateTime;

@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@Table(name = "stores")
@Entity
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String entpId;
    private String name;
    private String typeCode;
    private String areaCode;
    private String detailCode;
    private String tel;
    private String postNo;
    private String addr;
    private String addrDetail;
    private String roadAddr;
    private String roadAddrDetail;
    private String xCoord;
    private String yCoord;
    private Long registerId;
    private Long modifyId;
    private LocalDateTime registerTime;
    private LocalDateTime modifyTime;
}
