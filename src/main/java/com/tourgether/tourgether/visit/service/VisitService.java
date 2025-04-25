package com.tourgether.tourgether.visit.service;

import com.tourgether.tourgether.visit.dto.request.VisitCreateRequest;
import com.tourgether.tourgether.visit.dto.response.VisitCreateResponse;

public interface VisitService {

  VisitCreateResponse createVisit(Long memberId, VisitCreateRequest request);
}
