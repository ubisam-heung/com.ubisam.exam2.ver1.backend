package com.ubisam.exam.api.buses;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ubisam.exam.domain.Bus;

import io.u2ware.common.docs.MockMvcRestDocs;

@Component
public class BusDocs extends MockMvcRestDocs{

  // 버스 엔티티 생성용 (Integer 타입)
  public Bus newEntity(Object... entity){
    Bus body = new Bus();
    if(entity.length > 0 && entity[0] != null) {
      if(entity[0] instanceof Integer) {
        body.setBusNumber((Integer) entity[0]);
      } else if(entity[0] instanceof String) {
        body.setBusNumber(Integer.valueOf((String) entity[0]));
      }
      // else ignore
    } else {
      body.setBusNumber(super.randomInt());
    }
    body.setBusCapacity(super.randomInt());
    return body;
  }

  // 버스 번호 수정용 (Integer 타입)
  public Map<String, Object> updateEntity(Map<String, Object> body, Object entity){
    if(entity instanceof Integer) {
      body.put("busNumber", entity);
    } else if(entity instanceof String) {
      body.put("busNumber", Integer.valueOf((String) entity));
    }
    return body;
  }

  // 버스 번호 검색용 (Integer 타입)
  public Map<String, Object> setSearchNumber(Object search){
    Map<String, Object> body = new HashMap<>();
    if(search instanceof Integer) {
      body.put("searchNumber", search);
    } else if(search instanceof String) {
      body.put("searchNumber", Integer.valueOf((String) search));
    }
    return body;
  }
  
}
