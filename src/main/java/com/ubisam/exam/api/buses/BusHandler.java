
package com.ubisam.exam.api.buses;

import java.io.Serializable;

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
import com.ubisam.exam.api.busDrivers.BusDriverRepository;
import org.springframework.beans.factory.annotation.Autowired;

import com.ubisam.exam.domain.Bus;

import io.u2ware.common.data.jpa.repository.query.JpaSpecificationBuilder;
import io.u2ware.common.data.rest.core.annotation.HandleAfterRead;
import io.u2ware.common.data.rest.core.annotation.HandleBeforeRead;

@Component
@RepositoryEventHandler
public class BusHandler {

  @Autowired
  private BusRouteRepository busRouteRepository;
  @Autowired
  private BusDriverRepository busDriverRepository;

  // busRouteName, busDriverName을 실제 엔티티로 변환해 할당하는 메서드
  private void setBusRouteAndDriver(Bus e) {
    String routeName = e.getBusRouteName();
    if (routeName != null && !routeName.isEmpty()) {
      for (var route : busRouteRepository.findAll()) {
        if (routeName.equals(route.getBusRouteName())) {
          e.setBusRoute(route);
          break;
        }
      }
    }

    String driverName = e.getBusDriverName();
    if (driverName != null && !driverName.isEmpty()) {
      for (var driver : busDriverRepository.findAll()) {
        if (driverName.equals(driver.getBusDriverName())) {
          e.setBusDriver(driver);
          break;
        }
      }
    }
  }

  @HandleBeforeRead
  public void beforeRead(Bus e, Specification<Bus> spec) throws Exception{
    // 쿼리 작성
    JpaSpecificationBuilder<Bus> query = JpaSpecificationBuilder.of(Bus.class);
    query
      .where()
      .and().eq("busNumber", e.getSearchNumber())
      .build(spec);
    // 로그 코드 작성 (Auditing)
    // 테스트에서는 sysout으로 작성하나 실제는 log 사용
    System.out.println("[HandleBeforeRead] "+e);
  }
  
  @HandleAfterRead
  public void afterRead(Bus e, Serializable r) throws Exception{
    // 로그 코드 작성 (Auditing)
    // 테스트에서는 sysout으로 작성하나 실제는 log 사용
    System.out.println("[HandleafterRead] "+e);
    System.out.println("[HandleafterRead] "+r);
  }


  @HandleBeforeCreate
  public void beforeCreate(Bus e) throws Exception{
    setBusRouteAndDriver(e);
    // 로그 코드 작성 (Auditing)
    // 테스트에서는 sysout으로 작성하나 실제는 log 사용
    System.out.println("[HandleBeforeCreate] "+e);
  }
  @HandleBeforeSave
  public void beforeSave(Bus e) throws Exception{
    setBusRouteAndDriver(e);
    //로그 코드 작성 (Auditing)
    //테스트에서는 sysout으로 작성하나 실제는 log 사용
    System.out.println("[HandlebeforeSave] "+e);
  }
  
  @HandleBeforeDelete
  public void beforeDelete(Bus e) throws Exception{
    //로그 코드 작성 (Auditing)
    //테스트에서는 sysout으로 작성하나 실제는 log 사용
    System.out.println("[HandlebeforeDelete] "+e);
  }

  @HandleAfterCreate
  public void afterCreate(Bus e) throws Exception{
    //로그 코드 작성 (Auditing)
    //테스트에서는 sysout으로 작성하나 실제는 log 사용
    System.out.println("[HandleafterCreate] "+e);
  }

  @HandleAfterSave
  public void afterSave(Bus e) throws Exception{
    //로그 코드 작성 (Auditing)
    //테스트에서는 sysout으로 작성하나 실제는 log 사용
    System.out.println("[HandleafterSave] "+e);
  }
  
  @HandleAfterDelete
  public void afterDelete(Bus e) throws Exception{
    //로그 코드 작성 (Auditing)
    //테스트에서는 sysout으로 작성하나 실제는 log 사용
    System.out.println("[HandleafterDelete] "+e);
  }
}
