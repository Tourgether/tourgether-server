package com.tourgether.tourgether.visit.service;

import com.tourgether.tourgether.visit.dto.response.VisitResponse;

public interface VisitService {

  VisitResponse createVisit(Long memberId, Long attractionId);
}
