package com.ecom.project.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {

    private List<ProductDto> content;

    private Integer pageNumber;
    private  Integer pageSize;
    private  Long totalElements;
    private  Integer totalPage;
    private  Boolean lastPage;
}
