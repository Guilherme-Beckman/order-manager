package com.ms.orders.model.product;

import java.util.List;

public record ProductModelDTO(
    String id,
    String ownerid,
    String menuId,
    String name,
    Integer price,
    String description,
    boolean availability,
    Float rating,
    Integer reviewsCount,
    List<String> reviews
) {
}
