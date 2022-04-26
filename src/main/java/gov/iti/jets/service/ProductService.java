package gov.iti.jets.service;

import gov.iti.jets.repo.CategoryRepo;
import gov.iti.jets.repo.ProductRepo;
import gov.iti.jets.repo.entity.CategoryEntity;
import gov.iti.jets.repo.entity.ProductEntity;
import gov.iti.jets.service.dtos.ProductDto;
import gov.iti.jets.service.mappers.ProductMapper;
import java.util.*;
import java.util.stream.Collectors;

public enum ProductService {
    INSTANCE;
    private final ProductRepo productRepo = ProductRepo.getInstance();
    private final ProductMapper productMapper = ProductMapper.INSTANCE;
    private final CategoryRepo categoryRepo = CategoryRepo.getInstance();

    public boolean updateProductCategories(Long id, List<String> categories) {
        Set<CategoryEntity> categoryEntities = new HashSet<>();
        categories.forEach(category -> categoryEntities.add(categoryRepo.insertCategory(new CategoryEntity(category))));
        return productRepo.updateProductCategories(id, categoryEntities);
    }


    public Optional<ProductDto> addProduct(ProductDto productDto) {
        ProductEntity productEntity = productRepo.insertProduct(mapDtoToEntity(productDto));
        System.out.println(productEntity);
        if(productEntity == null)
            return Optional.empty();
        productDto.setId(productEntity.getId());
        return Optional.of(productDto);
    }

    public boolean updateProduct(ProductDto productDto){
        ProductEntity productEntity = productMapper.productDtoToEntity(productDto);
        System.out.println("in service: " + productEntity);
        return productRepo.updateProduct(productEntity);
    }

    public List<ProductDto> getAllProducts(int page, int rpp) {
        List<ProductEntity> productEntityList = productRepo.getAllProduct(page, rpp);
        List<ProductDto> productDtos = new ArrayList<>();
        productEntityList.forEach(productEntity -> {
            ProductDto productDto = productMapper.mapEntityToDto(productEntity);
            productDtos.add(productDto);
        });
        return productDtos;
    }

    public Optional<ProductDto> getProduct(Long id) {
        Optional<ProductEntity> productEntityOptional = productRepo.findProductById(id);
        Optional<ProductDto> productDtoOptional = Optional.empty();
        if(productEntityOptional.isPresent()){
            ProductDto productDto = productMapper.mapEntityToDto(productEntityOptional.get());
            productDtoOptional = Optional.of(productDto);
        }
        return productDtoOptional;
    }



    private ProductEntity mapDtoToEntity(ProductDto productDto) {
        ProductEntity productEntity = productMapper.productDtoToEntity(productDto);
        Set<CategoryEntity> categoryEntities = new HashSet<>();
        productDto.getCategoryList().forEach(category -> {
            categoryEntities.add(categoryRepo.insertCategory(new CategoryEntity(category)));
        });
        productEntity.setCategories(categoryEntities);
        return productEntity;
    }

    public boolean removeProduct(Long productId) {
        return productRepo.deleteProduct(productId);
    }

    public List<ProductDto> getCategoryProducts(String category) {
        return productRepo.getProductsByCategory(new CategoryEntity(category))
                .stream().map(
                        productMapper::mapEntityToDto
                ).collect(Collectors.toList());
    }
}
