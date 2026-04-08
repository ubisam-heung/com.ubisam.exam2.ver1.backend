package com.ubisam.exam.api.buses;

import java.util.UUID;


import com.ubisam.exam.domain.Bus;

import io.u2ware.common.data.jpa.repository.RestfulJpaRepository;

public interface BusRepository extends RestfulJpaRepository<Bus, UUID>{

  
}
