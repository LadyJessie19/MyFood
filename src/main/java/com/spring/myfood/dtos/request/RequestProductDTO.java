package com.spring.myfood.dtos.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestProductDTO {
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Description is required")
    private String description;

    @NotBlank(message = "Price is required")
    private int price;

    private String image;

    @NotBlank(message = "Category is required")
    private String category;

    @Override
    public String toString() {
        return "RequestProductDTO [ name=" + name + ", price=" + price + ", category=" + category + ", description="
                + description + ", image=" + image;
    }
}