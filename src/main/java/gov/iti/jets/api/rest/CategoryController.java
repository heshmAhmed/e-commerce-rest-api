package gov.iti.jets.api.rest;

import gov.iti.jets.service.CategoryService;
import gov.iti.jets.service.ProductService;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

@Path("categories")
public class CategoryController {
    private final CategoryService categoryService = CategoryService.getInstance();
    private final ProductService productService = ProductService.INSTANCE;
    @GET
    @Path("{category}/products")
    public Response getCategoryProducts(@PathParam("category") String category) {
            return Response.ok().entity(productService.getCategoryProducts(category)).build();
    }

    @GET
    public Response getCategories(){
        return Response.ok().entity(categoryService.getCategories()).build();
    }

}
