package com.ubisam.exam.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.data.rest.core.annotation.RestResource;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
@ToString(exclude = "buses")
@Table(name = "example_bus_driver")
public class BusDriver {

  @Id
  @GeneratedValue
  private UUID id;

  // 운전수 이름
  private String busDriverName;
  // 운전수 라이센스
  @Column(unique = true)
  private String busDriverLicense;
  // 운전수 폰번호
  private String busDriverPhone;

  // 한 명의 운전기사는 여러 대의 버스를 운전할 수 있음.
  @OneToMany(mappedBy = "busDriver", fetch = FetchType.EAGER)
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
  private String searchLicense;
  
  
}
