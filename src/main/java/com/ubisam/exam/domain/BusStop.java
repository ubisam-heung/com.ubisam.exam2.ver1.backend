package com.ubisam.exam.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.data.rest.core.annotation.RestResource;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
@ToString(exclude = "busRoutes")
@Table(name = "example_bus_stop")
public class BusStop {

  @Id
  @GeneratedValue
  private UUID id;

  // 정류장 이름
  @Column(unique = true)
  private String busStopName;
  // 정류장 위치
  private String busStopLocation;

  // 하나의 정류장은 여러 노선이 배정될 수 있고,
  // 하나의 노선은 여러 정류장이 배정될 수 있음.
  @ManyToMany(fetch=FetchType.EAGER, mappedBy = "busStops") 
	@RestResource(exported = false)
  @JsonIgnore
  private List<BusRoute> busRoutes;

  @Transient
	@JsonProperty(access = Access.WRITE_ONLY)
  private List<String> busRoutesNames = new ArrayList<>();

  // 검색용 필드 추가
  @Transient
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private String searchName;

  @Transient
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private String searchLocation;

  @JsonProperty(access = Access.READ_ONLY)
  public List<String> getBusRouteNames() {
    if (busRoutes == null) {
      return List.of();
    }

    return busRoutes.stream()
      .map(BusRoute::getBusRouteName)
      .toList();
  }

  
}
