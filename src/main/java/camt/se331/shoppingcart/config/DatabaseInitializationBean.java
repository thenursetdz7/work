package camt.se331.shoppingcart.config;

import camt.se331.shoppingcart.dao.ShoppingCartDao;
import camt.se331.shoppingcart.entity.*;
import camt.se331.shoppingcart.repository.ProductRepository;
import camt.se331.shoppingcart.repository.ShoppingCartRepository;
import camt.se331.shoppingcart.repository.UserRepository;
import camt.se331.shoppingcart.service.ImageUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by Dto on 2/11/2015.
 */
@Component
@Profile("db.init")
public class DatabaseInitializationBean implements InitializingBean {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ShoppingCartRepository shoppingCartRepository;

    @Autowired
    UserRepository userRepository;
    @Override
    public void afterPropertiesSet() throws Exception {
        Product[] initProduct = {
                new Product(1l, "กล้วยทอดเหลือง (กล้วยไข่)", "กล้วยเป็นสินค้าขึ้นชื่อของอำเภอสะเมิง อำเภอที่ปลูกพันธุ์พื้นเมือง เช่นกล้วยน้ำว้า กล้วยไข่ได้หวานหอมลูกใหญ่ จึงนิยมนำมาแปรรูปจนเป็นของขึ้นชื่อของอำเภอสะเมิง", 50.00, ImageUtil.resizeImage(ImageUtil.getImage("pic/yellowbanana.jpg"), 350)),
                new Product(2l, "กล้วยทอดแดง (กล้วยน้ำว้า)", "กล้วยเป็นสินค้าขึ้นชื่อของอำเภอสะเมิง อำเภอที่ปลูกพันธุ์พื้นเมือง เช่นกล้วยน้ำว้า กล้วยไข่ได้หวานหอมลูกใหญ่ จึงนิยมนำมาแปรรูปจนเป็นของขึ้นชื่อของอำเภอสะเมิง", 50.00, ImageUtil.resizeImage(ImageUtil.getImage("pic/redbanana.jpg"), 350)),
                new Product(3l, "ข้าวกล้องงอกดอย GABA Rice", " ข้าวกล้องดอยเป็นข้าวพันธุ์เดียวกับข้าวขัดขาวและข้าวกล้อง แต่มีกรรมวิธีต่างกัน (ปริมาณบรรจุ 1 kg)", 95.00,  ImageUtil.resizeImage(ImageUtil.getImage("pic/jasmindoi.jpg"), 350)),
                new Product(4l, "ข้าวขัดขาว", "ข้าวขัดขาวดอยเป็นข้าวพื้นถิ่นพันธุ์โปะโละ มีวิตามิน เหนียว หอม นุ่ม เคี้ยวแล้วจะมีรสหวานธรรมชาติ", 45.00, ImageUtil.resizeImage(ImageUtil.getImage("pic/rice.jpg"), 350)),
                new Product(5l, "ข้าวกล้องดอย", "ข้าวกล้องดอยเป็นข้าวพื้นถิ่นพันธุ์โปะโละเช่นเดียวกับข้าวขัดขาวดอย แต่ผ่านการสีน้อยครั้งจึงยังเหลือแร่ธาตุและวิตามินอยู่มาก", 55.00, ImageUtil.resizeImage(ImageUtil.getImage("pic/jasmin.jpg"), 350))
        };
        productRepository.save(Arrays.asList(initProduct));






                ShoppingCart shoppingCart = new ShoppingCart();
        List<SelectedProduct> selectedProducts = new ArrayList<>();
        SelectedProduct[] initSelectedProduct = {
                new SelectedProduct(initProduct[2], 5),
                new SelectedProduct(initProduct[4], 2),
                new SelectedProduct(initProduct[1], 1),
        };
        selectedProducts.addAll(Arrays.asList(initSelectedProduct));
        Calendar calendar = new GregorianCalendar(2015, 4, 7);
        shoppingCart.setSelectedProducts(selectedProducts);
        shoppingCart.setPurchaseDate(calendar.getTime());
        shoppingCart.setId(1L);
        shoppingCartRepository.save(shoppingCart);

        // add user
        Role adminRole = new Role("admin");
        Role userRole = new Role("user");
        Role userRole2 = new Role("user");


        User admin = new User();
        admin.setName("admin");
        admin.setUsername("admin");
        admin.setEmail("admin@yahoo.com");
        admin.setPassword("123456");
        Set<Role> roles = new HashSet<>();
        roles.add(adminRole);
        admin.setRoles(roles);

        User user = new User();
        user.setName("Retailer");
        user.setUsername("retailer");
        user.setEmail("user@yahoo.com");
        user.setPassword("123456");
        Set<Role> roles2 = new HashSet<>();
        roles2.add(userRole);
        user.setRoles(roles2);

        User user2 = new User();
        user2.setName("Wholesaler");
        user2.setUsername("wholesaler");
        user2.setEmail("user@yahoo.com");
        user2.setPassword("123456");
        Set<Role> roles3 = new HashSet<>();
        roles3.add(userRole2);
        user2.setRoles(roles3);
        userRepository.save(admin);
        userRepository.save(user);
        userRepository.save(user2);
        admin.setRoles(roles);
        user.setRoles(roles2);
        user2.setRoles(roles3);
    }
}
