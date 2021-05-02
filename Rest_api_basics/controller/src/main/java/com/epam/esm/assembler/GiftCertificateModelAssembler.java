package com.epam.esm.assembler;

import com.epam.esm.GiftCertificateController;
import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.dto.GiftCertificateParamDTO;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class GiftCertificateModelAssembler implements RepresentationModelAssembler<GiftCertificateDTO, GiftCertificateDTO> {
    private static final String LINK_NAME_ALL_GC = "All GiftCertificates";

    @Override
    public GiftCertificateDTO toModel(GiftCertificateDTO giftCertificateDTO) {
        giftCertificateDTO.add(
                linkTo(methodOn(GiftCertificateController.class).findById(giftCertificateDTO.getId())).withSelfRel(),
                linkTo(methodOn(GiftCertificateController.class).findByParameter(new GiftCertificateParamDTO()))
                        .withRel(LINK_NAME_ALL_GC));
        return giftCertificateDTO;
    }

    public List<GiftCertificateDTO> toModel(List<GiftCertificateDTO> giftCertificateDTOList) {
        return giftCertificateDTOList.stream().map(GiftCertificateDTO -> toModel(GiftCertificateDTO)).
                collect(Collectors.toList());
    }
}
