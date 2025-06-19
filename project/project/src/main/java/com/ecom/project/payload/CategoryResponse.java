package com.ecom.project.payload;

import jdk.dynalink.linker.LinkerServices;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponse {

    private List<CategoryDto> content;

    private Integer pageNumber;
    private  Integer pageSize;
    private  Long totalElements;
    private  Integer totalPage;
    private  Boolean lastPage;

}
