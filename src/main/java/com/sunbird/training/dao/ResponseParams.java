package com.sunbird.training.dao;

public record ResponseParams(
    String msgid,
    String status,
    String err,
    String errmsg
) {} 
