package com.ecom.project.service;

import com.ecom.project.exceptions.APIException;
import com.ecom.project.exceptions.ResourceNotFoundException;
import com.ecom.project.model.Category;
import com.ecom.project.payload.CategoryDto;
import com.ecom.project.payload.CategoryResponse;
import com.ecom.project.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class CategoryServiceImp implements CategoryService {


    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    ModelMapper modelMapper;


    @Override
    public CategoryResponse getAllCategories(Integer pageName,Integer pageSize,String sortBy,String sortByOrder) {

//      Category Sorting

        Sort  sortByAndOrder = sortByOrder.equalsIgnoreCase("asc") ?
                Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();


        Pageable pageDetails = PageRequest.of(pageName,pageSize,sortByAndOrder);
        Page<Category> categoryPage = categoryRepository.findAll(pageDetails);

        List<Category> category = categoryPage.getContent();

        if (category.isEmpty())
            throw  new APIException("No Category Create Till Now ");

//        in a list category all object   retriv  in the categoryDto  using Stream
        List<CategoryDto> categoryDTOS = category.stream()
                .map(categories ->  modelMapper.map(categories, CategoryDto.class))
                .toList();




//       Create a  new object of categoryResponse and  there set the categoryDto and return the categoryResponse

        CategoryResponse categoryResponse = new CategoryResponse();

        categoryResponse.setContent(categoryDTOS);
        categoryResponse.setPageNumber(categoryPage.getNumber());
        categoryResponse.setPageSize(categoryPage.getSize());
        categoryResponse.setTotalPage(categoryPage.getTotalPages());
        categoryResponse.setTotalElements(categoryPage.getTotalElements());
        categoryResponse.setLastPage(categoryPage.isLast());


        return categoryResponse;
    }

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {

        Category category = modelMapper.map(categoryDto,Category.class);

        Category categoryFromDB= categoryRepository.findByCategoryName(categoryDto.getCategoryName());
        if (categoryFromDB != null)
            throw  new APIException("Category with The Name " + categoryFromDB.getCategoryName() + "Already Exist!!!!");

        Category savedCategory =  categoryRepository.save(category);

        return modelMapper.map(savedCategory , CategoryDto.class);
    }

    @Override
    public CategoryDto deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category","categoryId",categoryId));
        categoryRepository.delete(category);
        return modelMapper.map(category,CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Long categoryId) {

//        step 1 = Fetch the  existing category or throw exception
          Category savedCategory = categoryRepository.findById(categoryId)
                  .orElseThrow(()->new ResourceNotFoundException("Category","categoryId",categoryId));

//        step 2 = update the filed your want to change
            Category category = modelMapper.map(categoryDto,Category.class);
            category.setCategoryId(categoryId);

//        save the updated  category in  the DB
          savedCategory = categoryRepository.save(category);

//         Step 4: Convert entity to DTO and return
          return modelMapper.map(savedCategory,CategoryDto.class);
    }

    @Override
    public CategoryDto getByCategoriesId(Long categoryId) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category","categoryId",categoryId));

        return modelMapper.map(category,CategoryDto.class);

    }


}
