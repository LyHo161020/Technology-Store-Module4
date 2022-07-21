package com.cg.model;

import com.cg.model.dto.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "products")
@Accessors(chain = true)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    private String image;

    private int amount;

    private Long price;

    @OneToOne(targetEntity = Category.class,fetch = FetchType.EAGER)
    private Category category;

    private boolean deleted;

//    @ManyToMany
//    @JoinColumn(name = "customer_id", referencedColumnName = "id")
//    private List<Customer> customers;

    public ProductDTO toProductDTO() {
        return new ProductDTO()
                .setId(this.id)
                .setName(this.name)
                .setImage(this.image)
                .setAmount(String.valueOf(this.amount))
                .setPrice(String.valueOf(this.price))
                .setCategory(this.category);
    }
}
