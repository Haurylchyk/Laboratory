package com.epam.esm.assembler;

import com.epam.esm.GiftCertificateController;
import com.epam.esm.GiftCertificateService;
import com.epam.esm.constant.PaginationConstant;
import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.dto.GiftCertificateParamDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class GiftCertificateModelAssembler implements RepresentationModelAssembler<GiftCertificateDTO, GiftCertificateDTO> {
    private static final String LINK_NAME_ALL_GC_FIRST = "All GiftCertificates - FirstPage";
    private static final String LINK_NAME_ALL_GC_LAST = "All GiftCertificates - LastPage";
    private static final Integer size = PaginationConstant.DEFAULT_NUMBER_ON_PAGE;
    private static final Integer page = PaginationConstant.DEFAULT_PAGE;

    private final GiftCertificateService giftCertificateService;

    @Autowired
    public GiftCertificateModelAssembler(GiftCertificateService giftCertificateService) {
        this.giftCertificateService = giftCertificateService;
    }

    @Override
    public GiftCertificateDTO toModel(GiftCertificateDTO giftCertificateDTO) {
        giftCertificateDTO.add(
                linkTo(methodOn(GiftCertificateController.class).findById(giftCertificateDTO.getId())).withSelfRel(),
                linkTo(methodOn(GiftCertificateController.class).findByParameter(page, size, new GiftCertificateParamDTO()))
                        .withRel(LINK_NAME_ALL_GC_FIRST),
                linkTo(methodOn(GiftCertificateController.class).findByParameter(giftCertificateService
                        .findNumberPagesForAllGiftCertificates(size), size, new GiftCertificateParamDTO()))
                        .withRel(LINK_NAME_ALL_GC_LAST));
        return giftCertificateDTO;
    }

    public List<GiftCertificateDTO> toModel(List<GiftCertificateDTO> giftCertificateDTOList) {
        return giftCertificateDTOList.stream().map(GiftCertificateDTO -> toModel(GiftCertificateDTO)).
                collect(Collectors.toList());
    }
}
