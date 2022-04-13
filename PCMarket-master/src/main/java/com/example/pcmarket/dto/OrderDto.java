package com.example.pcmarket.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderDto {
  private Integer userId;
  private List<DetailDto> detailDtoList;
}
