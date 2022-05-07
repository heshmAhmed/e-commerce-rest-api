package gov.iti.jets.api.soap;

import gov.iti.jets.api.dtos.PostProductRequest;
import gov.iti.jets.service.CategoryService;
import gov.iti.jets.service.ProductService;
import gov.iti.jets.service.dtos.ProductDto;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import jakarta.ws.rs.*;
import jakarta.xml.bind.annotation.XmlList;
import jakarta.xml.ws.BindingType;
import jakarta.xml.ws.soap.SOAPBinding;

import java.util.List;
import java.util.Optional;

@WebService
@BindingType(SOAPBinding.SOAP12HTTP_BINDING)
public class InventoryService {
    private final ProductService productService = gov.iti.jets.service.ProductService.INSTANCE;
    private final CategoryService categoryService = CategoryService.getInstance();

    @WebMethod
    public ProductDto addProduct(@WebParam(name = "product") PostProductRequest postProductRequest){
        ProductDto productDto = new ProductDto(postProductRequest.getName(),postProductRequest.getPrice(), postProductRequest.getDesc(), postProductRequest.getImg(), postProductRequest.getStock(),postProductRequest.getCategoryList());
        Optional<ProductDto> productDtoOptional = productService.addProduct(productDto);
        if(productDtoOptional.isEmpty())
            throw new RuntimeException("Resource could not be created, probably duplicate id");
        return productDtoOptional.get();
    }

    @WebMethod
    public List<ProductDto> getAllProducts(@WebParam(name = "pageNum") int start, @WebParam(name = "number") int rpp){
        return productService.getAllProducts(start, rpp);
    }

//    @WebMethod
//    public List<ProductDto> getAllProducts(){
//        return productService.getAllProducts(1, 10);
//    }

    @WebMethod
    public ProductDto getProduct(@WebParam(name = "productId") Long productId) {
        Optional<ProductDto> productDtoOptional = productService.getProduct(productId);
        if(productDtoOptional.isEmpty())
            throw new RuntimeException("Resource not found, probably wrong id");
        return productDtoOptional.get();
    }

    @WebMethod
    public List<String> getProductCategories(@WebParam(name = "productId") Long productId) {
        Optional<ProductDto> productDtoOptional = productService.getProduct(productId);
        if(productDtoOptional.isEmpty())
            throw new RuntimeException("Resource not found, probably wrong id");
        return productDtoOptional.get().getCategoryList();
    }

    @WebMethod
    public List<String> updateProductCategories(@WebParam(name = "productId") Long productId, @WebParam(name = "categoriesList") @XmlList List<String> categories) {
        boolean updated = productService.updateProductCategories(productId, categories);
        if(!updated)
            throw new NotFoundException("Resource not found, probably wrong id");
        return categories;
    }

    @WebMethod
    public ProductDto updateProduct(@WebParam(name = "product") ProductDto product) {
        boolean updated = productService.updateProduct(product);
        if(!updated){
            throw new RuntimeException("Resource not found, probably wrong id");
        }
        return product;
    }

    @WebMethod
    public void removeProduct(@WebParam(name = "productId") Long productId) {
        boolean deleted = productService.removeProduct(productId);
        if(!deleted)
            throw new RuntimeException("Resource not found, probably wrong id or product cannot be deleted");
    }

    @WebMethod
    public List<ProductDto> getCategoryProducts(@WebParam(name = "category") String category) {
        return productService.getCategoryProducts(category);
    }
    @WebMethod
    public List<String> getAllCategories(){
        return categoryService.getCategories();
    }
}
