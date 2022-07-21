package com.cg.controller.rest;

import com.cg.model.Customer;
import com.cg.model.Product;
import com.cg.model.dto.ProductDTO;
import com.cg.service.category.ICategoryService;
import com.cg.service.product.IProductService;
import com.cg.utils.AppUtils;
import com.cg.utils.ValidDateUtils;
import com.cg.validates.ValidationStepOne;
import com.cg.validates.ValidationStepTwo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductRestController {
    @Autowired
    private IProductService productService;

    @Autowired
    private ICategoryService categoryService;


    @GetMapping
    public ResponseEntity<?> showListProduct() {
        List<ProductDTO> products = productService.findAllProductDTO();

        if (products.isEmpty()) {
            return new ResponseEntity<>("Danh sách trống!", HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        Optional<ProductDTO> productDTO = productService.findProductDTOById(id);

        if (!productDTO.isPresent()) {
            return new ResponseEntity<>("Không tìm thấy sản phẩm có id là:" + id + "!", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(productDTO.get(), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> doCreate(@Validated @RequestBody ProductDTO productDTO, BindingResult bindingResult) {

          new ProductDTO().validate(productDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            return AppUtils.errors(bindingResult);
        }

        try {
            Product product = productDTO.toProduct();
            product.setId(0L);
            product.setDeleted(false);
            product = productService.save(product);

            return new ResponseEntity<>(product.toProductDTO(), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Server ko xử lý được", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/edit/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> doEdit(@PathVariable Long id,@Validated @RequestBody ProductDTO productDTO,
                                    BindingResult bindingResult) {
        Optional<Product> p = productService.findById(id);

        if (!p.isPresent()) {
            return new ResponseEntity<>("Không tồn tại sản phẩm", HttpStatus.NOT_FOUND);
        }

        new ProductDTO().validate(productDTO,bindingResult);

        if (bindingResult.hasErrors()) {
            return AppUtils.errors(bindingResult);
        }

        try {
            Product product = productDTO.toProduct();

            product.setId(p.get().getId());
            product.setDeleted(p.get().isDeleted());

            productDTO = product.toProductDTO();

            productService.save(product);

            return new ResponseEntity<>(productDTO, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>("Server ko xử lý được", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/block/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> doBlock(@PathVariable Long id, @Validated @RequestBody ProductDTO productDTO, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return AppUtils.errors(bindingResult);
        }

        try{
            productService.deleteProductById(id);

            return new ResponseEntity<>(id,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Server không xử lý được", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
