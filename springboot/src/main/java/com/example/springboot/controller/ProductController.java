package com.example.springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.springboot.models.Product;
import com.example.springboot.repository.ProductRepository;

@Controller
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductRepository productRepo;

    @GetMapping(path = "/findByPrice")
    public @ResponseBody List<Product> findProduct(@RequestParam Double price, @RequestParam Integer page,
            @RequestParam Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepo.findAllByPrice(price, pageable);
    }

    @GetMapping(path = "/findByPriceSort")
    public @ResponseBody List<Product> findProductSort(@RequestParam Double price, @RequestParam Integer page,
            @RequestParam Integer size, String sortColumn) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortColumn).descending());
        return productRepo.findAllByPrice(price, pageable);
    }


    @GetMapping("/getall")
    public @ResponseBody Iterable<Product> getAllProduct(){
        return productRepo.findAll();
    }

    @GetMapping(path = "/paging")
    public @ResponseBody Page<Product> findProductAll(@RequestParam Integer page, @RequestParam Integer size){
        Pageable pageable = PageRequest.of(page, size);
        return productRepo.findAllProducts(pageable);
    }


}
