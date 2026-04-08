package com.ubisam.exam.api.buses;

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
import org.springframework.security.oauth2.jwt.Jwt;

import com.ubisam.exam.domain.Bus;
import com.ubisam.exam.oauth2.Oauth2Docs;

import io.u2ware.common.data.jpa.repository.query.JpaSpecificationBuilder;

@SpringBootTest
@AutoConfigureMockMvc
public class BusTests {

  @Autowired
  private MockMvc mvc;

  @Autowired
  private BusDocs docs;

  @Autowired
  private BusRepository busRepository;

  @Autowired
  private Oauth2Docs od;	

  // Crud 테스트용
  @Test
  void contextLoads() throws Exception{
    Jwt u = od.jose("busUser1");
    // Crud - C
    mvc.perform(post("/api/buses").content(docs::newEntity, "1140").auth(u)).andDo(print()).andExpect(is2xx()).andDo(result(docs::context, "entity1"));

    // Crud - R
    String uri = docs.context("entity1", "$._links.self.href");
    mvc.perform(post(uri).auth(u)).andExpect(is2xx());

    // Crud - U
    Map<String, Object> body = docs.context("entity1", "$");
    mvc.perform(put(uri).content(docs::updateEntity, body, "1139").auth(u)).andExpect(is2xx());

    // Crud - D
    mvc.perform(delete(uri).auth(u)).andExpect(is2xx());
  }

  // 핸들러 테스트용
  @Test
  void contextLoads2 () throws Exception{
    List<Bus> result;
    boolean hasResult;

    // 40개의 버스 추가 (busNumber 중복 방지)
    List<Bus> busLists = new ArrayList<>();
    for(int i=1; i<=40; i++){
      busLists.add(docs.newEntity((i+10000)+"140"));
    }
    busRepository.saveAll(busLists);

    // 버스 번호 쿼리 
    int targetBusNumber = Integer.parseInt((1+10000) + "140");
    JpaSpecificationBuilder<Bus> numberQuery = JpaSpecificationBuilder.of(Bus.class);
    numberQuery.where().and().eq("busNumber", targetBusNumber);
    result = busRepository.findAll(numberQuery.build());
    hasResult = result.stream().anyMatch(u -> targetBusNumber == u.getBusNumber());
    assertEquals(true, hasResult);

  }

  // Search 테스트용
  @Test
  void contextLoads3 () throws Exception{
    Jwt u = od.jose("busUser3");
    // 40개의 버스 추가 (busNumber 중복 방지, Integer 타입)
    List<Bus> busLists = new ArrayList<>();
    for(int i=1; i<=40; i++){
      // busNumber: 20001, 20002, ..., 20040 (Integer)
      busLists.add(docs.newEntity(20000 + i));
    }
    busRepository.saveAll(busLists);

    String uri = "/api/buses/search";
    // Search - 단일 검색 (버스 번호, Integer)
    mvc.perform(post(uri).content(docs::setSearchNumber, 20001).auth(u)).andExpect(is2xx());

    // Search - 페이지네이션 8개씩 5페이지
    mvc.perform(post(uri).param("size", "6").auth(u)).andExpect(is2xx());

    // Search - 정렬 busNumber,desc
    mvc.perform(post(uri).param("sort", "busNumber,desc").auth(u)).andExpect(is2xx());
  }
  
}
