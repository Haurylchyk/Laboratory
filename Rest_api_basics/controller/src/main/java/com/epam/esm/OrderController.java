package com.epam.esm;

import com.epam.esm.assembler.OrderModelAssembler;
import com.epam.esm.dto.OrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/orders")
public class OrderController {

    private final OrderService orderService;
    private final OrderModelAssembler orderModelAssembler;

    @Autowired
    public OrderController(OrderService orderService, OrderModelAssembler orderModelAssembler) {
        this.orderService = orderService;
        this.orderModelAssembler = orderModelAssembler;
    }

    @PostMapping("/users/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDTO create(@PathVariable Integer userId, @RequestBody List<Integer> giftCertificatesIdList) {
        return orderModelAssembler.toModel(orderService.create(userId, giftCertificatesIdList));
    }

    @GetMapping("/{id}")
    public OrderDTO findById(@PathVariable Integer id) {
        return orderModelAssembler.toModel(orderService.findById(id));
    }

    @GetMapping("/users/{userId}")
    public List<OrderDTO> findByUserId(@PathVariable Integer userId, @RequestParam(required = false, defaultValue = "1") Integer page,
                                       @RequestParam(required = false, defaultValue = "1") Integer size) {
        return orderModelAssembler.toModel(orderService.findByUserId(userId, page, size));
    }
}
