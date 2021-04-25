package com.epam.esm.dto.mapper;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.dto.OrderInUserDTO;
import com.epam.esm.dto.UserDTO;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The class converts User to UserDTO and vice versa.
 */
public class UserDTOMapper {

    private UserDTOMapper() {
    }

    /**
     * Converts UserDTO to User.
     *
     * @param tagDTO object of TagDTO type.
     * @return object of Tag type.
     */
    public static User convertToEntity(UserDTO tagDTO) {
        User user = new User();
        user.setId(tagDTO.getId());
        user.setName(tagDTO.getName());
        user.setLogin(tagDTO.getLogin());

        return user;
    }

    /**
     * Converts User to UserDTO.
     *
     * @param user object of User type.
     * @return object of UserDTO type.
     */
    public static UserDTO convertToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        List<OrderInUserDTO> orderDTOList = toOrderInUserDTO(user.getOrderList());

        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setLogin(user.getLogin());
        userDTO.setOrderList(orderDTOList);
        return userDTO;
    }

    /**
     * Converts list of Users to list of UserDTOs.
     *
     * @param userList is list of Users for converting.
     * @return list of converted objects (UserDTOs).
     */
    public static List<UserDTO> convertToDTO(List<User> userList) {
        List<UserDTO> userDTOList = new ArrayList<>();

        userList.forEach(user -> userDTOList.add(convertToDTO(user)));

        return userDTOList;
    }

    private static OrderInUserDTO toOrderInUserDTO(Order order) {
        OrderInUserDTO orderInUserDTO = new OrderInUserDTO();

        orderInUserDTO.setId(order.getId());

        List<GiftCertificateDTO> giftCertificateDTOList = GiftCertificateDTOMapper
                .convertToDTO(order.getGiftCertificateList());
        orderInUserDTO.setGiftCertificateList(giftCertificateDTOList);

        orderInUserDTO.setCost(order.getCost());
        orderInUserDTO.setDate(order.getDate());

        return orderInUserDTO;
    }

    private static List<OrderInUserDTO> toOrderInUserDTO(List<Order> orders) {
        if (orders != null) {
            return orders.stream().map(order -> toOrderInUserDTO(order)).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }
}
