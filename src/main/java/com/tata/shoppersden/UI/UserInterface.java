package com.tata.shoppersden.UI;


import com.tata.shoppersden.dao.*;
import com.tata.shoppersden.models.Admin;
import com.tata.shoppersden.models.Customer;
import com.tata.shoppersden.models.Product;
import com.tata.shoppersden.models.ShoppingCart;

import java.util.ResourceBundle;
import java.util.Scanner;

public class UserInterface {
    public static <E> E loginPrompt() {
        System.out.print("Enter \nEmail: ");
        Scanner sc = new Scanner(System.in);
        String email = sc.next();
        System.out.print("Password: ");
        String password = sc.next();
        LoginDao loginDaoImp = new LoginDaoImpl();
        return loginDaoImp.login(email, password);
    }

    public static <E> E createNewUser() {
        Scanner sc = new Scanner(System.in);
        System.out.println("1 - Admin \n0 - Customer");
        int isadmin = Integer.parseInt(sc.nextLine());
        if (isadmin == 1) {
            Admin user = new Admin();
            System.out.print("Name: ");
            String name = sc.nextLine();
            user.setAdminName(name);
            System.out.print("Password: ");
            String password = sc.nextLine();
            user.setAdminPassword(password);
            return (E) user;
        } else {
            Customer user = new Customer();
            System.out.println("Name: ");
            String name = sc.nextLine();
            user.setName(name);
            System.out.println("Email: ");
            String email = sc.nextLine();
            user.setEmail(email);
            System.out.println("Password: ");
            String password = sc.nextLine();
            user.setPassword(password);
            System.out.println("Phone Number: ");
            String phone_no = sc.nextLine();
            user.setPhoneNumber(phone_no);
            System.out.println("Address: ");
            String address = sc.nextLine();
            user.setAddress(address);
            System.out.println("Question: ");
            String question = sc.nextLine();
            user.setSecretQuestion(question);
            System.out.println("Answer: ");
            String answer = sc.nextLine();
            user.setSecretAnswer(answer);
            return (E) user;
        }
    }

    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(System.in);
            int opt;
            System.out.println("1. Login\n2. Exit");
            opt = Integer.parseInt(sc.nextLine());
            while (true) {
                switch (opt) {
                    case 1: {
                        Object user = loginPrompt();
                        ProductDao productDaoImpl = new ProductDaoImpl();
                        if (user instanceof Customer) {
                            Customer customer = (Customer) user;
                            System.out.println(customer.getCartId());
                            ShoppingCartDao shoppingCartDaoImpl = new ShoppingCartDaoImpl(customer.getCartId());
                            System.out.println("Welcome " + customer.getName());
                            while (true) {
                                System.out.println("Dashboard\n1. Show All Products\n2. Buy A Product\n3. Remove A Product\n4. Show Cart\n5. New Cart\n6. Checkout\n7. Logout");
                                opt = Integer.parseInt(sc.nextLine());
                                switch (opt) {
                                    case 1: {
                                        for(Product product : productDaoImpl.getAllProducts()) {
                                            System.out.println(product);
                                        }
                                        break;
                                    }
                                    case 2: {
                                        buyProduct(customer, shoppingCartDaoImpl);
                                        break;
                                    }
                                    case 3: {
                                        removeProductByCustomer(customer, shoppingCartDaoImpl);
                                        break;
                                    }
                                    case 4: {
                                        shoppingCartDaoImpl.viewAllProducts();
                                        break;
                                    }
                                    case 5: {
                                        //TODO additional feature.
                                        break;
                                    }
                                    case 6: {
                                        shoppingCartDaoImpl = shoppingCartDaoImpl.pay();
                                        break;
                                    }
                                    case 7: {
                                        return;
                                    }
                                }
                            }

                        } else {
                            Admin admin = (Admin) user;
                            System.out.println("Admin Login, Welcome " + admin.getAdminName());
                            AdminDao adminDaoImpl = new AdminDaoImpl();
                            while (true) {
                                System.out.println("Dashboard\n1. Show All Products\n2. Add User\n3. Add Product\n4. Remove Product\n5. Modify Product\n6. Logout");
                                opt = Integer.parseInt(sc.nextLine());
                                switch (opt) {
                                    case 1: {
                                        for(Product product : productDaoImpl.getAllProducts()) {
                                            System.out.println(product);
                                        }
                                        break;
                                    }
                                    case 2: {
                                        user = createNewUser();
                                        adminDaoImpl.addUser(user);
                                        break;
                                    }
                                    case 3: {
                                        Product product = createNewProduct();
                                        productDaoImpl.addProductToDB(product);
                                        break;
                                    }
                                    case 4: {
                                        removeProduct();
                                        break;
                                    }
                                    case 5: {
                                        //TODO
                                        break;
                                    }
                                    case 6: {
                                        return;
                                    }
                                }
                            }
                        }
                    }
                    case 2: {
                        return;
                    }
                    default: {
                        System.out.println("Enter Again\n1. Login\n2. Exit");
                        opt = Integer.parseInt(sc.nextLine());
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private static void removeProductByCustomer(Customer customer, ShoppingCartDao shoppingCartDaoImpl) {
        System.out.println("Enter Product Id");
        Scanner sc = new Scanner(System.in);
        int id = Integer.parseInt(sc.nextLine());
        shoppingCartDaoImpl.removeProductFromCart(id);
    }

    private static void buyProduct(Customer customer, ShoppingCartDao shoppingCartDaoImpl) {
        System.out.println("Enter Product Id");
        Scanner sc = new Scanner(System.in);
        int id = Integer.parseInt(sc.nextLine());
        ProductDao productDaoImpl = new ProductDaoImpl();
        Product product = productDaoImpl.getProductById(id);
        System.out.println("Enter Quantity");
        int qty = Integer.parseInt(sc.nextLine());
        product.setQuantity(qty);
        shoppingCartDaoImpl.addProductToCart(product);
    }

    private static void removeProduct() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Product Id");
        int id = Integer.parseInt(sc.nextLine());
        ProductDao productDaoImpl = new ProductDaoImpl();
        productDaoImpl.removeProductById(id);
    }

    private static Product createNewProduct() {
        Scanner sc = new Scanner(System.in);
        Product product = new Product();
        System.out.print("Product Name: ");
        String name = sc.nextLine();
        product.setProductName(name);
        System.out.print("Product Description: ");
        String des = sc.nextLine();
        product.setProductDescription(des);
        System.out.print("Product Image URL: ");
        String imageUrl = sc.nextLine();
        product.setImgUrl(imageUrl);
        System.out.print("Product Quantity: ");
        int qty = Integer.parseInt(sc.nextLine());
        product.setQuantity(qty);
        System.out.print("Product Price: ");
        float price = Float.parseFloat(sc.nextLine());
        product.setPrice(price);
        return product;
    }
}
