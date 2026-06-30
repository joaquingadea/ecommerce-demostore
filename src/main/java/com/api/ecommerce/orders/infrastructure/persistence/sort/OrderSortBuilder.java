package com.api.ecommerce.orders.infrastructure.persistence.sort;

import org.springframework.data.domain.Sort;

public class OrderSortBuilder {
    public static Sort build(String sortByDate){
        Sort sort = Sort.unsorted();
        if (!(sortByDate == null) && !(sortByDate.isBlank())){
            switch (sortByDate){
                case "date_asc" -> sort = sort.and(Sort.by("createdAt").ascending());
                case "date_desc" -> sort = sort.and(Sort.by("createdAt").descending());
            }
        }
        return sort;
    }
}
