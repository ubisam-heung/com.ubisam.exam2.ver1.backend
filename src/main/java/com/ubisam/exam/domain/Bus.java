package com.ubisam.exam.domain;

import java.util.UUID;

import org.springframework.data.rest.core.annotation.RestResource;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import lombok.Data;

@Entity
@Data
@Table(name = "example_bus")
public class Bus {

  @Id
  @GeneratedValue
  private UUID id;

  // 버스 번호
  @Column(unique = true)
  private Integer busNumber;
  // 버스 정원
  private Integer busCapacity;

  // 한 버스는 하나의 노선만을 운행함.
  @ManyToOne 
  @JoinColumn(foreignKey=@ForeignKey(ConstraintMode.NO_CONSTRAINT))
  @RestResource(exported = false)
  @JsonIgnore
  private BusRoute busRoute;

  @Transient
  @JsonProperty(access = Access.READ_WRITE)
  private String busRouteName;

  public String getBusRouteName() {
    if (busRouteName != null) {
      return busRouteName;
    }
    return busRoute != null ? busRoute.getBusRouteName() : null;
  }

  // 한 버스에는 한 명의 운전기사가 배정됌.
  @ManyToOne 
  @JoinColumn(foreignKey=@ForeignKey(ConstraintMode.NO_CONSTRAINT))
  @RestResource(exported = false)
  @JsonIgnore
  private BusDriver busDriver;

  @Transient
  @JsonProperty(access = Access.READ_WRITE)
	private String busDriverName;

  public String getBusDriverName() {
    if (busDriverName != null) {
      return busDriverName;
    }
    return busDriver != null ? busDriver.getBusDriverName() : null;
  }

  // 검색용 필드 추가
  @Transient
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private Integer searchNumber;

}