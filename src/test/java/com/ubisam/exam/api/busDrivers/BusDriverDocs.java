package com.ubisam.exam.api.busDrivers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ubisam.exam.domain.BusDriver;

import io.u2ware.common.docs.MockMvcRestDocs;

@Component
public class BusDriverDocs extends MockMvcRestDocs{

  // 운전수 엔티티 생성용
  public BusDriver newEntity(String... entity){
    BusDriver body = new BusDriver();
    body.setBusDriverName(entity.length > 0 ? entity[0] : super.randomText("busDriverName"));
    body.setBusDriverLicense(entity.length > 1 ? entity[1] : super.randomText("busDriverLicense"));
    body.setBusDriverPhone(super.randomText("010-xxxx-"));
    return body;
  }

  // 운전수 이름 변경용
  public Map<String, Object> updateEntity(Map<String, Object> body, String entity){
    body.put("busDriverName", entity);
    return body;
  }

  // 운전수 이름 검색용
  public Map<String, Object> setSearchName(String search){
    Map<String, Object> body = new HashMap<>();
    body.put("searchName", search);
    return body;
  }

  // 운전수 라이센스 검색용
  public Map<String, Object> setSearchLicense(String search){
    Map<String, Object> body = new HashMap<>();
    body.put("searchLicense", search);
    return body;
  }

}