package com.api.ecommerce.products.infrastructure.persistence.sort;
import org.springframework.data.domain.Sort;

public class ProductSortBuilder {

    public static Sort build(
            String sortByPrice,
            Boolean bestSellers,
            String sortByDate) {

        Sort sort = Sort.unsorted();

        if (Boolean.TRUE.equals(bestSellers)) {
            sort = sort.and(
                    Sort.by("unitsSold").descending()
            );
        }

        if (sortByPrice != null && !sortByPrice.isBlank()) {

            Sort priceSort = switch (sortByPrice.toLowerCase()) {
                case "price_asc" -> Sort.by("unitPrice").ascending();
                case "price_desc" -> Sort.by("unitPrice").descending();
                default -> Sort.unsorted();
            };

            sort = sort.and(priceSort);
        }

        if (sortByDate != null && !sortByDate.isBlank()) {

            Sort dateSort = switch (sortByDate.toLowerCase()) {
                case "date_asc" -> Sort.by("uploadDate").ascending();
                case "date_desc" -> Sort.by("uploadDate").descending();
                default -> Sort.unsorted();
            };

            sort = sort.and(dateSort);
        }

        return sort;
    }
}
