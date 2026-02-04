package com.sunbird.training.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sunbird.training.entity.Certificate;

public interface CertificateRepository extends JpaRepository<Certificate,Integer>{

    
}

