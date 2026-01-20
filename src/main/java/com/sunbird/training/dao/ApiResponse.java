package com.sunbird.training.dao;

public record ApiResponse<T>(
    String id,
    String ver,
    String ts,
    ResponseParams params,
    String responseCode,
    T result
) {}
