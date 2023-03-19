package com.zerobase.fastlms.admin.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Banner {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Long id;

    String bannerName;

    String imagePath;

    String url;

    @Enumerated(EnumType.STRING)
    OpenType openType;

    int sortValue;

    boolean openYn;

    LocalDateTime regDt;
}
