package com.tourgether.tourgether.attraction.dto;

import com.tourgether.tourgether.attraction.entity.AttractionTranslation;

public record AttractionSummaryResponse(
    Long id,
    String name,
    String address,
    String summary,
    String thumbnailImgUrl) {

  public static AttractionSummaryResponse from(AttractionTranslation entity) {

    return new AttractionSummaryResponse(
        entity.getTranslationId(),
        entity.getName(),
        entity.getAddress(),
        entity.getSummary(),
        entity.getAttraction().getThumbnailImgUrl()
    );
  }
}
