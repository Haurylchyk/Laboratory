package com.epam.esm.assembler;

import com.epam.esm.OrderController;
import com.epam.esm.OrderService;
import com.epam.esm.constant.PaginationConstant;
import com.epam.esm.dto.OrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class OrderModelAssembler implements RepresentationModelAssembler<OrderDTO, OrderDTO> {

    private static final String LINK_NAME_ALL_ORDERS_FIRST = "All Orders - FirstPage";
    private static final String LINK_NAME_ALL_ORDERS_LAST = "All Orders - LastPage";
    private Integer size = PaginationConstant.DEFAULT_NUMBER_ON_PAGE;
    private Integer page = PaginationConstant.DEFAULT_PAGE;

    private final OrderService orderService;

    @Autowired
    public OrderModelAssembler(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public OrderDTO toModel(OrderDTO orderDTO) {
        orderDTO.add(
                linkTo(methodOn(OrderController.class).findById(orderDTO.getId())).withSelfRel(),
                linkTo(methodOn(OrderController.class).findAll(page, size))
                        .withRel(LINK_NAME_ALL_ORDERS_FIRST),
                linkTo(methodOn(OrderController.class).findAll(orderService.findNumberPagesForAllOrders(size), size))
                        .withRel(LINK_NAME_ALL_ORDERS_LAST));
        return orderDTO;
    }

    public List<OrderDTO> toModel(List<OrderDTO> orderDTOList) {
        size = orderDTOList.size();
        return orderDTOList.stream().map(orderDTO -> toModel(orderDTO)).collect(Collectors.toList());
    }
}
