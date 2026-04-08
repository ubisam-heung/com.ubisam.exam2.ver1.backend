package com.ubisam.exam.api.busRoutes;

import java.io.Serializable;
import java.util.ArrayList;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.HandleAfterDelete;
import org.springframework.data.rest.core.annotation.HandleAfterSave;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeDelete;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

import com.ubisam.exam.domain.BusRoute;
import com.ubisam.exam.domain.BusStop;
import com.ubisam.exam.api.busStops.BusStopRepository;
import org.springframework.beans.factory.annotation.Autowired;

import io.u2ware.common.data.jpa.repository.query.JpaSpecificationBuilder;
import io.u2ware.common.data.rest.core.annotation.HandleAfterRead;
import io.u2ware.common.data.rest.core.annotation.HandleBeforeRead;

@Component
@RepositoryEventHandler
public class BusRouteHandler {

  @Autowired
  private BusStopRepository busStopRepository;

  // busStopsNames, busesNumbers를 실제 엔티티로 변환해 할당하는 메서드
  private void setBusStopsAndBuses(BusRoute e) {
    if (e.getBusStopsNames() != null && !e.getBusStopsNames().isEmpty()) {
      var stops = new ArrayList<BusStop>();
      for (String name : e.getBusStopsNames()) {
        for (BusStop stop : busStopRepository.findAll()) {
          if (name != null && name.equals(stop.getBusStopName())) {
            stops.add(stop);
            break;
          }
        }
      }
      e.setBusStops(stops);
    }
  }

  @HandleBeforeRead
  public void beforeRead(BusRoute e, Specification<BusRoute> spec) throws Exception{
    // 쿼리 작성
    JpaSpecificationBuilder<BusRoute> query = JpaSpecificationBuilder.of(BusRoute.class);
    query
      .where()
      .and().eq("busRouteName", e.getSearchName())
      .and().eq("busRouteStart", e.getSearchStart())
      .and().eq("busRouteEnd", e.getSearchEnd())
      .build(spec);
    // 로그 코드 작성 (Auditing)
    // 테스트에서는 sysout으로 작성하나 실제는 log 사용
    System.out.println("[HandleBeforeRead] "+e);
  }
  
  @HandleAfterRead
  public void afterRead(BusRoute e, Serializable r) throws Exception{
    // 로그 코드 작성 (Auditing)
    // 테스트에서는 sysout으로 작성하나 실제는 log 사용
    System.out.println("[HandleafterRead] "+e);
    System.out.println("[HandleafterRead] "+r);
  }

  @HandleBeforeCreate
  public void beforeCreate(BusRoute e) throws Exception{
    setBusStopsAndBuses(e);
    // 로그 코드 작성 (Auditing)
    // 테스트에서는 sysout으로 작성하나 실제는 log 사용
    System.out.println("[HandlebeforeCreate] "+e);
  }

  @HandleBeforeSave
  public void beforeSave(BusRoute e) throws Exception{
    setBusStopsAndBuses(e);
    // 로그 코드 작성 (Auditing)
    // 테스트에서는 sysout으로 작성하나 실제는 log 사용
    System.out.println("[HandlebeforeSave] "+e);
  }

  @HandleBeforeDelete
  public void beforeDelete(BusRoute e) throws Exception{
    // 로그 코드 작성 (Auditing)
    // 테스트에서는 sysout으로 작성하나 실제는 log 사용
    System.out.println("[HandlebeforeDelete] "+e);
  }

  @HandleAfterCreate
  public void afterCreate(BusRoute e) throws Exception{
    // 로그 코드 작성 (Auditing)
    // 테스트에서는 sysout으로 작성하나 실제는 log 사용
    System.out.println("[HandleafterCreate] "+e);
  }

  @HandleAfterSave
  public void afterSave(BusRoute e) throws Exception{
    // 로그 코드 작성 (Auditing)
    // 테스트에서는 sysout으로 작성하나 실제는 log 사용
    System.out.println("[HandleafterSave] "+e);
  }
  
  @HandleAfterDelete
  public void afterDelete(BusRoute e) throws Exception{
    // 로그 코드 작성 (Auditing)
    // 테스트에서는 sysout으로 작성하나 실제는 log 사용
    System.out.println("[HandleafterDelete] "+e);
  }
}
