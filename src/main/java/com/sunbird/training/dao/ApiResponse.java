package com.sunbird.training.dao;

//!User Api response
public record ApiResponse<T>(
    String id,
    String ver,
    String ts,
    ResponseParams params,
    String responseCode,
    T result
) {}
