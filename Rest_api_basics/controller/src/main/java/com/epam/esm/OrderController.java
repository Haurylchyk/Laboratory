package com.epam.esm;

import com.epam.esm.model.dto.OrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Validated
@RestController
@RequestMapping(value = "/users/{userId}/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDTO create(@PathVariable @Min(1) Integer userId,
                           @RequestBody @NotEmpty @NotNull List<@Min(1) Integer> giftCertificatesIdList) {
        return orderService.create(userId, giftCertificatesIdList);
    }

    @GetMapping
    public PagedModel<OrderDTO> findByUserId(@PathVariable @Min(1) Integer userId, Pageable pageable,
                                             PagedResourcesAssembler assembler) {
        Page<OrderDTO> orderDTOPage = orderService.findByUserId(userId, pageable);
        return assembler.toModel(orderDTOPage);
    }
}
