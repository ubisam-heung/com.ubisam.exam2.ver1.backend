package com.ubisam.exam.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.data.rest.core.annotation.RestResource;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.persistence.ForeignKey;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
@ToString(exclude = "buses")
@Table(name = "example_bus_route")
public class BusRoute {

  @Id
  @GeneratedValue
  private UUID id;

  // 노선 이름
  @Column(unique = true)
  private String busRouteName;
  // 노선 시작점
  private String busRouteStart;
  // 노선 종점
  private String busRouteEnd;

  // 하나의 정류장은 여러 노선이 배정될 수 있고,
  // 하나의 노선은 여러 정류장이 배정될 수 있음.
  @ManyToMany(fetch=FetchType.EAGER) 
  @JoinTable(foreignKey=@ForeignKey(ConstraintMode.NO_CONSTRAINT), inverseForeignKey=@ForeignKey(ConstraintMode.NO_CONSTRAINT))
	@RestResource(exported = false)
  private List<BusStop> busStops;

  @Transient
	@JsonProperty(access = Access.WRITE_ONLY)
  private List<String> busStopsNames = new ArrayList<>();

  // 하나의 노선에는 여러 대의 버스가 배정될 수 있음.
  @OneToMany(mappedBy = "busRoute", fetch = FetchType.EAGER)
  @RestResource(exported = false)
  @JsonProperty(access = Access.READ_ONLY)
  @JsonIgnoreProperties({"busRoute", "busDriver"})
  private List<Bus> buses;

  @Transient
	@JsonProperty(access = Access.WRITE_ONLY)
	private List<Integer> busesNumbers = new ArrayList<>();

  // 검색용 필드 추가
  @Transient
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private String searchName;

  @Transient
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private String searchStart;

  @Transient
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private String searchEnd;
  
}
