package com.ubisam.exam.api.busStops;

import java.io.Serializable;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.HandleAfterDelete;
import org.springframework.data.rest.core.annotation.HandleAfterSave;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeDelete;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

import com.ubisam.exam.api.busRoutes.BusRouteRepository;
import com.ubisam.exam.domain.BusRoute;
import com.ubisam.exam.domain.BusStop;

import io.u2ware.common.data.jpa.repository.query.JpaSpecificationBuilder;
import io.u2ware.common.data.rest.core.annotation.HandleAfterRead;
import io.u2ware.common.data.rest.core.annotation.HandleBeforeRead;

@Component
@RepositoryEventHandler
public class BusStopHandler {

  @Autowired
  private BusRouteRepository busRouteRepository;

  // busStopsNames, busesNumbers를 실제 엔티티로 변환해 할당하는 메서드
  private void setBusRoutes(BusStop e) {
    if (e.getBusRoutesNames() != null && !e.getBusRoutesNames().isEmpty()) {
      var routes = new ArrayList<BusRoute>();
      for (String name : e.getBusRoutesNames()) {
        for (BusRoute route : busRouteRepository.findAll()) {
          if (name != null && name.equals(route.getBusRouteName())) {
            routes.add(route);
            break;
          }
        }
      }
      e.setBusRoutes(routes);
    }
  }

  @HandleBeforeRead
  public void beforeRead(BusStop e, Specification<BusStop> spec) throws Exception{
    // 쿼리 작성
    JpaSpecificationBuilder<BusStop> query = JpaSpecificationBuilder.of(BusStop.class);
    query
      .where()
      .and().eq("busStopName", e.getSearchName())
      .and().eq("busStopLocation", e.getSearchLocation())
      .build(spec);
    // 로그 코드 작성 (Auditing)
    // 테스트에서는 sysout으로 작성하나 실제는 log 사용
    System.out.println("[HandleBeforeRead] "+e);
  }
  
  @HandleAfterRead
  public void afterRead(BusStop e, Serializable r) throws Exception{
    // 로그 코드 작성 (Auditing)
    // 테스트에서는 sysout으로 작성하나 실제는 log 사용
    System.out.println("[HandleafterRead] "+e);
    System.out.println("[HandleafterRead] "+r);
  }

  @HandleBeforeCreate
  public void beforeCreate(BusStop e) throws Exception{
    setBusRoutes(e);
    // 로그 코드 작성 (Auditing)
    // 테스트에서는 sysout으로 작성하나 실제는 log 사용
    System.out.println("[HandlebeforeCreate] "+e);
  }

  @HandleBeforeSave
  public void beforeSave(BusStop e) throws Exception{
    setBusRoutes(e);
    // 로그 코드 작성 (Auditing)
    // 테스트에서는 sysout으로 작성하나 실제는 log 사용
    System.out.println("[HandlebeforeSave] "+e);
  }
  
  @HandleBeforeDelete
  public void beforeDelete(BusStop e) throws Exception{
    // 로그 코드 작성 (Auditing)
    // 테스트에서는 sysout으로 작성하나 실제는 log 사용
    System.out.println("[HandlebeforeDelete] "+e);
  }

  @HandleAfterCreate
  public void afterCreate(BusStop e) throws Exception{
    // 로그 코드 작성 (Auditing)
    // 테스트에서는 sysout으로 작성하나 실제는 log 사용
    System.out.println("[HandleafterCreate] "+e);
  }

  @HandleAfterSave
  public void afterSave(BusStop e) throws Exception{
    // 로그 코드 작성 (Auditing)
    // 테스트에서는 sysout으로 작성하나 실제는 log 사용
    System.out.println("[HandleafterSave] "+e);
  }
  
  @HandleAfterDelete
  public void afterDelete(BusStop e) throws Exception{
    // 로그 코드 작성 (Auditing)
    // 테스트에서는 sysout으로 작성하나 실제는 log 사용
    System.out.println("[HandleafterDelete] "+e);
  }
}
