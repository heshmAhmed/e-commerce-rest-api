package gov.iti.jets.repo.util;

import gov.iti.jets.repo.OrderRepo;
import gov.iti.jets.repo.CategoryRepo;
import gov.iti.jets.repo.ProductRepo;
import gov.iti.jets.repo.UserRepo;
import gov.iti.jets.repo.entity.*;

import java.util.Date;

public class DBFiller {
    public static void main(String[] args) {
        fillCustomerTable();
        fillCategoriesTable();
        fillProductTable();
    }

    private static void fillOrdersTable() {
           OrderRepo orderRepo = OrderRepo.getInstance();
           OrderEntity order = new OrderEntity();
    }

    private static void fillCategoriesTable() {
        CategoryRepo productCategoryRepo = CategoryRepo.getInstance();
        CategoryEntity category = new CategoryEntity();
        category.setCategory("CAT1");

        CategoryEntity category2 = new CategoryEntity();
        category2.setCategory("CAT2");

        CategoryEntity category3 = new CategoryEntity();
        category3.setCategory("CAT3");

        CategoryEntity category4 = new CategoryEntity();
        category4.setCategory("CAT4");

        productCategoryRepo.insertCategory(category);
        productCategoryRepo.insertCategory(category2);
        productCategoryRepo.insertCategory(category3);
    }

    private static void fillProductTable() {
        ProductRepo productRepo = ProductRepo.getInstance();
        ProductEntity productEntity = new ProductEntity();
        productEntity.setDesc("product desc1");
        productEntity.setName("product name1");
        productEntity.setStock(50L);
        productEntity.setImg("product img1");
        productEntity.setPrice(5000.0);
        productEntity.getCategories().add(new CategoryEntity(1L));

        ProductEntity productEntity2 = new ProductEntity();
        productEntity2.setDesc("product desc2");
        productEntity2.setName("product name2");
        productEntity2.setStock(50L);
        productEntity2.setPrice(5000.0);
        productEntity2.setImg("product img2");

        productEntity2.getCategories().add(new CategoryEntity(2L));


        ProductEntity productEntity3 = new ProductEntity();
        productEntity3.setDesc("product desc3");
        productEntity3.setName("product name3");
        productEntity3.setStock(50L);
        productEntity3.setImg("product img3");
        productEntity3.setPrice(5000.0);
        productEntity3.getCategories().add(new CategoryEntity(1L));


        ProductEntity productEntity4 = new ProductEntity();
        productEntity4.setDesc("product desc4");
        productEntity4.setName("product name4");
        productEntity4.setStock(50L);
        productEntity4.setImg("product img4");
        productEntity4.setPrice(5000.0);
        productEntity4.getCategories().add(new CategoryEntity(2L));


        ProductEntity productEntity5 = new ProductEntity();
        productEntity5.setDesc("product desc5");
        productEntity5.setName("product name5");
        productEntity5.setStock(50L);
        productEntity5.setPrice(5000.0);
        productEntity5.setImg("product img5");
        productEntity5.getCategories().add(new CategoryEntity(3L));


        productRepo.insertProduct(productEntity);
        productRepo.insertProduct(productEntity2);
        productRepo.insertProduct(productEntity3);
        productRepo.insertProduct(productEntity4);
        productRepo.insertProduct(productEntity5);
    }
    private static void fillCustomerTable(){
        UserRepo userRepo = UserRepo.getInstance();
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setEmail("hesham@gmail.com");
        customerEntity.setName("hesham");
        customerEntity.setPassword("1234");
        customerEntity.setAddress("8 st fahmy mabrouk");
        customerEntity.setBirthDate(new Date());
        customerEntity.setJoinedDate(new Date());

        CustomerEntity customerEntity1 = new CustomerEntity();
        customerEntity1.setEmail("hesham1@gmail.com");
        customerEntity1.setName("hesham1");
        customerEntity1.setPassword("1234");
        customerEntity1.setAddress("8 st fahmy mabrouk");
        customerEntity1.setBirthDate(new Date());
        customerEntity1.setJoinedDate(new Date());

        CustomerEntity customerEntity2 = new CustomerEntity();
        customerEntity2.setEmail("hesham2@gmail.com");
        customerEntity2.setName("hesham2");
        customerEntity2.setPassword("1234");
        customerEntity2.setAddress("8 st fahmy mabrouk");
        customerEntity2.setBirthDate(new Date());
        customerEntity2.setJoinedDate(new Date());

        AdminEntity adminEntity = new AdminEntity();
        adminEntity.setEmail("admin@gmail.com");
        adminEntity.setName("admin");
        adminEntity.setPassword("1234");

        ClerkEntity clerkEntity = new ClerkEntity();
        clerkEntity.setEmail("clerk@gmail.com");
        clerkEntity.setName("clerk");
        clerkEntity.setPassword("1234");
        clerkEntity.setHireDate(new Date());

        userRepo.insertUser(customerEntity);
        userRepo.insertUser(customerEntity1);
        userRepo.insertUser(customerEntity2);
        userRepo.insertUser(adminEntity);
        userRepo.insertUser(clerkEntity);
    }
}
