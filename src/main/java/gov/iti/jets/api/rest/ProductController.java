package gov.iti.jets.api.rest;

import gov.iti.jets.api.dtos.ErrorMessage;
import gov.iti.jets.api.dtos.PostProductRequest;
import gov.iti.jets.service.ProductService;
import gov.iti.jets.service.dtos.ProductDto;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObjectBuilder;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

import java.util.List;
import java.util.Optional;

@Path("products")
public class ProductController {
    private final ProductService productService = ProductService.INSTANCE;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addProduct(PostProductRequest postProductRequest) {
        ProductDto productDto = new ProductDto(postProductRequest.getName(),postProductRequest.getPrice(), postProductRequest.getDesc(), postProductRequest.getImg(), postProductRequest.getStock(),postProductRequest.getCategoryList());
        Optional<ProductDto> productDtoOptional = productService.addProduct(productDto);
        if(productDtoOptional.isEmpty()){
            ErrorMessage errorMessage = new ErrorMessage("Resource could not be created",406, "Probably duplicate id");
            Response response = Response.status(Status.NOT_ACCEPTABLE).entity(errorMessage).build();
            throw new NotAcceptableException(response);
        }
        return Response.status(Status.CREATED).entity(productDtoOptional.get()).build();
    }

    @GET
    public Response getAllProducts(@DefaultValue("id,name,price,desc,categories,stock,img") @QueryParam("fields") String q,
                                   @DefaultValue("1") @QueryParam("page") int start,@DefaultValue("10") @QueryParam("rpp") int rpp){
        List<ProductDto> products = productService.getAllProducts(start,rpp);
        String []fields = q.split(",");
        return Response.ok().entity(generateJsonRespForProducts(fields, products)).build();
    }

    @GET
    @Path("{prodId}")
    public Response getProduct(@PathParam("prodId") Long id){
        Optional<ProductDto> productDtoOptional = productService.getProduct(id);
        if(productDtoOptional.isEmpty()) {
            ErrorMessage errorMessage = new ErrorMessage("Resource not found",404, "Probably wrong id");
            Response response = Response.status(Status.NOT_FOUND).entity(errorMessage).build();
            throw new NotFoundException(response);
        }
        return Response.ok().entity(productDtoOptional.get()).build();
    }

    @GET
    @Path("{prodId}/categories")
    public Response getProductCategories(@PathParam("prodId") Long id){
        Optional<ProductDto> productDtoOptional = productService.getProduct(id);
        if(productDtoOptional.isEmpty()) {
            ErrorMessage errorMessage = new ErrorMessage("Resource not found",404, "Probably wrong id");
            Response response = Response.status(Status.NOT_FOUND).entity(errorMessage).build();
            throw new NotFoundException(response);
        }
        return Response.ok().entity(productDtoOptional.get().getCategoryList()).build();
    }

    @PUT
    @Path("{id}/categories")
    public Response updateProductCategories(@PathParam("id") Long id, List<String> categories) {
        boolean updated = productService.updateProductCategories(id, categories);
        if(!updated){
            ErrorMessage errorMessage = new ErrorMessage("Resource not found",404, "Probably wrong id");
            Response response = Response.status(Status.NOT_FOUND).entity(errorMessage).build();
            throw new NotFoundException(response);
        }
        return Response.status(Status.ACCEPTED).entity(categories).build();
    }


    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateProduct(ProductDto productDto) {
        boolean updated = productService.updateProduct(productDto);
        if(!updated){
            ErrorMessage errorMessage = new ErrorMessage("Resource not found",404, "Probably wrong id");
            Response response = Response.status(Status.NOT_FOUND).entity(errorMessage).build();
            throw new NotFoundException(response);
        }
        return Response.status(Status.ACCEPTED).entity(productDto).build();
    }

    @DELETE
    @Path("{id}")
    public Response removeProduct(@PathParam("id") Long id) {
        boolean deleted = productService.removeProduct(id);
        if(!deleted) {
            ErrorMessage errorMessage = new ErrorMessage("Resource not found", 404, "Probably wrong id or product cannot be deleted");
            Response response = Response.status(Status.NOT_FOUND).entity(errorMessage).build();
            throw new NotFoundException(response);
        }
        return Response.status(Status.OK).build();
    }


    private JsonArray generateJsonRespForProducts(String [] fields, List<ProductDto> products) {
        JsonArrayBuilder jsonArray = Json.createArrayBuilder();
        products.forEach(product -> {
            JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
            for(String field: fields) {
                if(field.equals("id") && product.getId() != null)
                    jsonObjectBuilder.add("id", product.getId());
                if(field.equals("name") && product.getName() != null)
                    jsonObjectBuilder.add("name", product.getName());
                if(field.equals("desc") && product.getDesc() != null)
                    jsonObjectBuilder.add("desc", product.getDesc());
                if(field.equals("img") && product.getImg() != null)
                    jsonObjectBuilder.add("img", product.getImg());
                if(field.equals("stock") && product.getStock() != null)
                    jsonObjectBuilder.add("stock", product.getStock());
                if(field.equals("price"))
                    jsonObjectBuilder.add("price", product.getPrice());
                if(field.equals("categories"))
                {
                    JsonArrayBuilder categoriesBuilder = Json.createArrayBuilder();
                    product.getCategoryList().forEach(categoriesBuilder::add);
                    jsonObjectBuilder.add("categories", categoriesBuilder.build());
                }
            }
            jsonArray.add(jsonObjectBuilder.build());
        });
        return jsonArray.build();
    }

    


}
