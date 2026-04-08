package com.ubisam.exam.api.busStops;

import static io.u2ware.common.docs.MockMvcRestDocs.delete;
import static io.u2ware.common.docs.MockMvcRestDocs.get;
import static io.u2ware.common.docs.MockMvcRestDocs.is2xx;
import static io.u2ware.common.docs.MockMvcRestDocs.post;
import static io.u2ware.common.docs.MockMvcRestDocs.print;
import static io.u2ware.common.docs.MockMvcRestDocs.put;
import static io.u2ware.common.docs.MockMvcRestDocs.result;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.ubisam.exam.domain.BusStop;
import com.ubisam.exam.oauth2.Oauth2Docs;
import org.springframework.security.oauth2.jwt.Jwt;

import io.u2ware.common.data.jpa.repository.query.JpaSpecificationBuilder;

@SpringBootTest
@AutoConfigureMockMvc
public class BusStopTests {

  @Autowired
  private MockMvc mvc;

  @Autowired
  private BusStopDocs docs;

  @Autowired
  private BusStopRepository busStopRepository;

  @Autowired
  private Oauth2Docs od;

  // Crud 테스트용
  @Test
  void contextLoads() throws Exception{
    Jwt u = od.jose("busStop1");
    // Crud - C
    mvc.perform(post("/api/busStops").content(docs::newEntity, "어반워크정류장").auth(u)).andDo(print()).andExpect(is2xx()).andDo(result(docs::context, "entity1"));

    // Crud - R
    String uri = docs.context("entity1", "$._links.self.href");
    mvc.perform(post(uri).auth(u)).andExpect(is2xx());

    // Crud - U
    Map<String, Object> body = docs.context("entity1", "$");
    mvc.perform(put(uri).content(docs::updateEntity, body, "디폴리스정류장").auth(u)).andExpect(is2xx());

    // Crud - D
    mvc.perform(delete(uri).auth(u)).andExpect(is2xx());
  }

  // 핸들러 테스트용
  @Test
  void contextLoads2 () throws Exception{

    List<BusStop> result;
    boolean hasResult;

    // 10개의 정류장 추가
    List<BusStop> busStopLists = new ArrayList<>();
    List<String> stopNames = new ArrayList<>();
    for(int i=1; i<=10; i++){
      String uniqueSuffix = "_" + System.nanoTime() + "_" + i;
      String stopName = i+"노선"+uniqueSuffix;
      stopNames.add(stopName);
      busStopLists.add(docs.newEntity(stopName, "서울시"+i));
    }
    busStopRepository.saveAll(busStopLists);


    // 정류장 이름 쿼리
    String targetStopName = stopNames.get(2); // 3번째(인덱스 2)
    JpaSpecificationBuilder<BusStop> nameQuery = JpaSpecificationBuilder.of(BusStop.class);
    nameQuery.where().and().eq("busStopName", targetStopName);
    result = busStopRepository.findAll(nameQuery.build());
    hasResult = result.stream().anyMatch(u -> targetStopName.equals(u.getBusStopName()));
    assertEquals(true, hasResult);

    // 정류장 위치 쿼리
    JpaSpecificationBuilder<BusStop> locationQuery = JpaSpecificationBuilder.of(BusStop.class);
    locationQuery.where().and().eq("busStopLocation", "서울시2");
    result = busStopRepository.findAll(locationQuery.build());
    hasResult = result.stream().anyMatch(u -> "서울시2".equals(u.getBusStopLocation()));
    assertEquals(true, hasResult);
  }

  // Search 테스트용
  @Test
  void contextLoads3 () throws Exception{
    Jwt u = od.jose("busStop2");
    // 10개의 정류장 추가
    List<BusStop> busStopLists = new ArrayList<>();
    List<String> stopNames = new ArrayList<>();
    for(int i=1; i<=10; i++){
      String uniqueSuffix = "_" + System.nanoTime() + "_" + i;
      String stopName = i+"노선"+uniqueSuffix;
      stopNames.add(stopName);
      busStopLists.add(docs.newEntity(stopName, "서울시"+i));
    }
    busStopRepository.saveAll(busStopLists);

    String uri = "/api/busStops/search";
    String targetStopName = stopNames.get(0); // 1번째(인덱스 0)
    // Search - 단일 검색 (이름, 위치)
    mvc.perform(post(uri).content(docs::setSearchName, targetStopName).auth(u)).andExpect(is2xx());
    mvc.perform(post(uri).content(docs::setSearchLocation, "서울시1").auth(u)).andExpect(is2xx());

    // Search - 페이지네이션 5개씩 2페이지
    mvc.perform(post(uri).param("size", "5").auth(u)).andExpect(is2xx());

    // Search - 정렬 busStopName,desc
    mvc.perform(post(uri).param("sort", "busStopName,desc").auth(u)).andExpect(is2xx());
  }
  
}
