package com.ecom.project.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
     private  Long  categoryId;

     @NotBlank(message = "Category name must not be blank")
     @Size(min = 5, message = "Category must contain at least 5 characters")
     private  String categoryName;

}
