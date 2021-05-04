package com.epam.esm.assembler;

import com.epam.esm.OrderController;
import com.epam.esm.dto.OrderDTO;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class OrderModelAssembler implements RepresentationModelAssembler<OrderDTO, OrderDTO> {

    @Override
    public OrderDTO toModel(OrderDTO orderDTO) {
        orderDTO.add(linkTo(methodOn(OrderController.class).findById(orderDTO.getId())).withSelfRel());
        return orderDTO;
    }

    public List<OrderDTO> toModel(List<OrderDTO> orderDTOList) {
        return orderDTOList.stream().map(orderDTO -> toModel(orderDTO)).collect(Collectors.toList());
    }
}
