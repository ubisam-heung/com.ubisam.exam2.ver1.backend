package com.ubisam.exam.api.busDrivers;

import java.util.UUID;

import com.ubisam.exam.domain.BusDriver;

import io.u2ware.common.data.jpa.repository.RestfulJpaRepository;

public interface BusDriverRepository extends RestfulJpaRepository<BusDriver, UUID>{
  
}
