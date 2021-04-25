package com.epam.esm;

import com.epam.esm.dto.OrderDTO;
import com.epam.esm.dto.OrderParamDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDTO create(@RequestBody OrderParamDTO orderParamDTO) {
        return orderService.create(orderParamDTO);
    }

    @GetMapping("/{id}")
    public OrderDTO findById(@PathVariable Integer id) {
        return orderService.findById(id);
    }

    @GetMapping("/user/{id}")
    public List<OrderDTO> findByUserId(@PathVariable Integer id) {
        return orderService.findByUserId(id);
    }

    @GetMapping
    public List<OrderDTO> findAll() {
        return orderService.findAll();
    }

}
