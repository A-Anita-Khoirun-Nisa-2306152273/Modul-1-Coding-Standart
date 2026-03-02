package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.CarService;
import id.ac.ui.cs.advprog.eshop.service.CarServiceImpl;
import id.ac.ui.cs.advprog.eshop.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping("/create")
    public String createProductPage(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        return "createProduct";
    }

    @PostMapping("/create")
    public String createProductPost(@ModelAttribute Product product, Model model) {
        service.create(product);
        return "redirect:list";
    }

    @GetMapping("/list")
    public String productListPage(Model model) {
        List<Product> allProducts = service.findAll();
        model.addAttribute("products", allProducts);
        return "productList";
    }

    @GetMapping("/edit")
    public String editProductPage(@RequestParam("id") String productId, Model model) {
        Product product = service.findById(productId);
        model.addAttribute("product", product);
        return "editProduct";
    }

    @PostMapping("/edit")
    public String editProductPost(@ModelAttribute Product product, Model model) {
        service.editProduct(product.getProductId(), product);
        return "redirect:list";
    }

    @PostMapping("/delete")
    public String deleteProduct(@RequestParam("productId") String productId) {
        service.deleteProduct(productId);
        return "redirect:list";
    }

    @Controller
    @RequestMapping("/car")
    class CarController extends ProductController {
        @Autowired
        private CarService carService;

        @GetMapping("/createCar")
        public String createCarPage(Model model){
            Car car = new Car();
            model.addAttribute("car", car);
            return "createCar";
        }

        @PostMapping("/createCar")
        public String createCarPost(@ModelAttribute("product") Car car, Model model){
            carService.create(car);
            return "redirect:CarList";
        }

        @GetMapping("/listCar")
        public String listCarPage(Model model){
            List<Car> allCars = carService.findAll();
            model.addAttribute("cars", allCars);
            return "carList";
        }

        @GetMapping(value="/editCar/{carId}")
        public String editProductPage(Model model, @PathVariable("carId") String productId){
            Car car = carService.findById(productId);
            model.addAttribute("car", car);
            return "editCar";
        }

        @PostMapping("/editCar")
        public String editProductPost(@ModelAttribute("car") Car car, Model model){
            System.out.println(car.getCarId());
            carService.update(car.getCarId(), car);
            return "redirect:CarList";
        }
        @PostMapping("/deleteCar")
        public String deleteCar(Model model, @RequestParam("carId") String carId){
            carService.deleteCarById(carId);
            return "redirect:CarList";
        }
    }
}

