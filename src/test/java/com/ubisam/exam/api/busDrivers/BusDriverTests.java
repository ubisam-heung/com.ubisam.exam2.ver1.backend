package com.ubisam.exam.api.busDrivers;

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
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.ubisam.exam.domain.BusDriver;
import com.ubisam.exam.oauth2.Oauth2Docs;

import io.u2ware.common.data.jpa.repository.query.JpaSpecificationBuilder;

@SpringBootTest
@AutoConfigureMockMvc
public class BusDriverTests {

  @Autowired
  private MockMvc mvc;

  @Autowired
  private BusDriverDocs docs;

  @Autowired
  private Oauth2Docs od;	

  @Autowired
  private BusDriverRepository busDriverRepository;
  
  // contextLoads1: Crud 테스트용 (테이블 초기화 추가)
  @Test
  void contextLoads1() throws Exception{
    Jwt u = od.jose("busDriver1");

    // Crud - C
    mvc.perform(post("/api/busDrivers").content(docs::newEntity, "김길동").auth(u)).andDo(print()).andExpect(is2xx()).andDo(result(docs::context, "entity1"));

    // Crud - R
    String uri = docs.context("entity1", "$._links.self.href");
    mvc.perform(post(uri).auth(u)).andExpect(is2xx());

    // Crud - U
    Map<String, Object> body = docs.context("entity1", "$");
    mvc.perform(put(uri).content(docs::updateEntity, body, "박길동").auth(u)).andExpect(is2xx());

    // Crud - D
    mvc.perform(delete(uri).auth(u)).andExpect(is2xx());
  }

  // contextLoads2: 핸들러 테스트용 (테이블 초기화 및 CRUD 패턴)
  @Test
  void contextLoads2 () throws Exception{

    // Create (bus_driver_license 중복 방지)
    List<BusDriver> busDriverLists = new ArrayList<>();
    for(int i=1; i<=30; i++){
      busDriverLists.add(docs.newEntity(i+"길동", i+"라이센스_"+System.nanoTime()));
    }
    busDriverRepository.saveAll(busDriverLists);

    // Read - 이름 쿼리
    JpaSpecificationBuilder<BusDriver> nameQuery = JpaSpecificationBuilder.of(BusDriver.class);
    nameQuery.where().and().eq("busDriverName", "3길동");
    List<BusDriver> result = busDriverRepository.findAll(nameQuery.build());
    boolean hasResult = result.stream().anyMatch(u2 -> "3길동".equals(u2.getBusDriverName()));
    assertEquals(true, hasResult);
  }

  // contextLoads3: Search 테스트 (테이블 초기화 및 CRUD 패턴)
  @Test
  void contextLoads3() throws Exception{
    Jwt u = od.jose("busDriver3");

    // Create (bus_driver_license 중복 방지)
    List<BusDriver> busDriverLists = new ArrayList<>();
    for(int i=1; i<=30; i++){
      busDriverLists.add(docs.newEntity(i+"길동", i+"라이센스_"+System.nanoTime()));
    }
    busDriverRepository.saveAll(busDriverLists);

    String uri = "/api/busDrivers/search";
    // Read - 단일 검색 (이름, 라이센스)
    mvc.perform(post(uri).content(docs::setSearchName, "4길동").auth(u)).andExpect(is2xx());
    mvc.perform(post(uri).content(docs::setSearchLicense, "3라이센스").auth(u)).andExpect(is2xx());

    // Search - 페이지네이션 6개씩 5페이지
    mvc.perform(post(uri).param("size", "6").auth(u)).andExpect(is2xx());

    // Search - 정렬 busDriverName,asc
    mvc.perform(post(uri).param("sort", "busDriverName").auth(u)).andExpect(is2xx());
  }
  
}
