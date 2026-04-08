package com.ubisam.exam.api.busStops;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ubisam.exam.domain.BusStop;

import io.u2ware.common.docs.MockMvcRestDocs;

@Component
public class BusStopDocs extends MockMvcRestDocs{

  // 정류장 엔티티 생성용
  public BusStop newEntity(String... entity){
    BusStop body = new BusStop();
    body.setBusStopName(entity.length > 0 ? entity[0] : super.randomText("busStopName"));
    body.setBusStopLocation(entity.length > 1 ? entity[1] : super.randomText("busStopLocation"));
    return body;
  }

  // 정류장 이름 변경용
  public Map<String, Object> updateEntity(Map<String, Object> body, String entity){
    body.put("busStopName", entity);
    return body;
  }

  // 정류장 이름 검색용
  public Map<String, Object> setSearchName(String search){
    Map<String, Object> body = new HashMap<>();
    body.put("searchName", search);
    return body;
  }

  // 정류장 위치 검색용
  public Map<String, Object> setSearchLocation(String search){
    Map<String, Object> body = new HashMap<>();
    body.put("searchLocation", search);
    return body;
  }

}