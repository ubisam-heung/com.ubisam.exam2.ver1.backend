package com.ubisam.exam.api.busRoutes;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ubisam.exam.domain.BusRoute;

import io.u2ware.common.docs.MockMvcRestDocs;

@Component
public class BusRouteDocs extends MockMvcRestDocs{

  // 노선 엔티티 생성용
  public BusRoute newEntity(String... entity){
    BusRoute body = new BusRoute();
    body.setBusRouteName(entity.length > 0 ? entity[0] : super.randomText("busRouteName"));
    body.setBusRouteStart(entity.length > 1 ? entity[1] : super.randomText("busRouteStart"));
    body.setBusRouteEnd(entity.length > 2 ? entity[2] : super.randomText("busRouteEnd"));
    return body;
  }

  // 노선 이름 변경용
  public Map<String, Object> updateEntity(Map<String, Object> body, String entity){
    body.put("busRouteName", entity);
    return body;
  }

  // 노선 이름 검색용
  public Map<String, Object> setSearchName(String search){
    Map<String, Object> body = new HashMap<>();
    body.put("searchName", search);
    return body;
  }

  // 노선 시작점 검색용
  public Map<String, Object> setSearchStart(String search){
    Map<String, Object> body = new HashMap<>();
    body.put("searchStart", search);
    return body;
  }

  // 노선 종점 검색용
  public Map<String, Object> setSearchEnd(String search){
    Map<String, Object> body = new HashMap<>();
    body.put("searchEnd", search);
    return body;
  }

}