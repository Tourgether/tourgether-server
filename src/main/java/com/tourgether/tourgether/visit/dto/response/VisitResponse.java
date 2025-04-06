package com.tourgether.tourgether.visit.dto.response;

import java.time.LocalDateTime;

public record VisitResponse(
    Long visitId,
    Long attractionId,
    LocalDateTime visitedAt
) {

}
