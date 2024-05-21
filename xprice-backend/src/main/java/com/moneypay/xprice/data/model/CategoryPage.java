package com.moneypay.xprice.data.model;

import com.moneypay.xprice.enums.ThirdPartyService;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"product_id", "thirdPartyService"})})
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryPage {

    @Id
    @GeneratedValue
    private UUID id;

    @Enumerated(EnumType.STRING)
    private ThirdPartyService thirdPartyService;

    @Column(length = 2000)
    private String url;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

}
