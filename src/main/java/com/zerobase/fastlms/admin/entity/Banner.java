package com.zerobase.fastlms.admin.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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

    OpenType openType;

    int sortValue;

    boolean openYn;

    LocalDateTime regDt;
}
