package com.ubisam.exam.api.busRoutes;

import java.util.List;
import java.util.UUID;

import com.ubisam.exam.domain.BusRoute;

import io.u2ware.common.data.jpa.repository.RestfulJpaRepository;

public interface BusRouteRepository extends RestfulJpaRepository<BusRoute, UUID>{

}
