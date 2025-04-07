package com.tourgether.tourgether.attraction.controller;

import com.tourgether.tourgether.attraction.dto.AttractionDetailResponse;
import com.tourgether.tourgether.attraction.dto.AttractionSummaryResponse;
import com.tourgether.tourgether.attraction.dto.LevelDescriptionResponse;
import com.tourgether.tourgether.attraction.service.AttractionService;
import com.tourgether.tourgether.common.dto.ApiResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/attractions")
@RequiredArgsConstructor
public class AttractionController {

  private final AttractionService attractionService;

  @GetMapping
  public ResponseEntity<ApiResponse<List<AttractionSummaryResponse>>> getAttractions(
      @RequestParam("lang") Long languageId,
      @RequestParam(value = "keyword", required = false) String keyword
  ) {
    List<AttractionSummaryResponse> attractions = attractionService.searchAttractions(languageId,
        keyword);

    return ResponseEntity.ok(ApiResponse.success(attractions));
  }

  @GetMapping("/nearby")
  public ResponseEntity<ApiResponse<List<AttractionSummaryResponse>>> getNearbyAttractions(
      @RequestParam double latitude,
      @RequestParam double longitude,
      @RequestParam double radius,
      @RequestParam Long languageId
  ) {
    List<AttractionSummaryResponse> nearbyAttractions = attractionService.searchNearbyAttractions(
        latitude, longitude, radius, languageId);

    return ResponseEntity.ok(ApiResponse.success(nearbyAttractions));
  }

  @GetMapping("/{attractionId}")
  public ResponseEntity<ApiResponse<AttractionDetailResponse>> getAttractionDetail(
      @PathVariable Long attractionId,
      @RequestParam("lang") Long languageId
  ) {
    AttractionDetailResponse detail = attractionService.getAttractionDetail(
        attractionId, languageId);

    return ResponseEntity.ok(ApiResponse.success(detail));
  }

  @GetMapping("/{translationId}/levels")
  public ResponseEntity<ApiResponse<List<LevelDescriptionResponse>>> getLevelDescriptions(
      @PathVariable Long translationId
  ) {
    List<LevelDescriptionResponse> descriptions = attractionService.getAttractionLevelDescriptions(
        translationId);

    return ResponseEntity.ok(ApiResponse.success(descriptions));
  }

}
