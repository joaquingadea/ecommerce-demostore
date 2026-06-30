package com.api.ecommerce.orders.api;

import com.api.ecommerce.orders.application.IOrderService;
import com.api.ecommerce.orders.dto.response.OrderDTO;
import com.api.ecommerce.orders.dto.response.OrderDetailDTO;
import com.api.ecommerce.orders.infrastructure.persistence.sort.OrderSortBuilder;
import com.api.ecommerce.shared.web.PaginationConstants;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/orders")
public class AdminOrderController {

    private final IOrderService orderService;

    public AdminOrderController(IOrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/get")
    public ResponseEntity<Page<OrderDTO>> getOrders(
            @RequestParam(required = false) String sortByDate,
            @PageableDefault(page = 0,size = 8)Pageable pageable) throws BadRequestException {
        Sort ordersSort = OrderSortBuilder.build(sortByDate);
        Pageable ordersPageable = PaginationConstants.validatePageable(pageable.getPageNumber(),pageable.getPageSize(),ordersSort);
        return ResponseEntity.status(HttpStatus.OK)
                .body(orderService.getOrders(ordersPageable));
    }
}
