package com.epam.esm;

import com.epam.esm.assembler.GiftCertificateModelAssembler;
import com.epam.esm.model.dto.GiftCertificateDTO;
import com.epam.esm.model.dto.GiftCertificateParamDTO;
import com.epam.esm.model.dto.StatisticGiftCertificateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@Validated
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/certificates")
public class GiftCertificateController {

    private final GiftCertificateService giftCertificateService;
    private final GiftCertificateModelAssembler giftCertificateModelAssembler;

    @Autowired
    public GiftCertificateController(GiftCertificateService giftCertificateService, GiftCertificateModelAssembler giftCertificateModelAssembler) {
        this.giftCertificateService = giftCertificateService;
        this.giftCertificateModelAssembler = giftCertificateModelAssembler;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GiftCertificateDTO create(@Valid @RequestBody GiftCertificateDTO giftCertificateDTO) {
        return giftCertificateService.create(giftCertificateDTO);
    }

    @GetMapping("/{id}")
    public GiftCertificateDTO findById(@PathVariable @Min(1) Integer id) {
        return giftCertificateModelAssembler.toModel(giftCertificateService.findById(id));
    }

    @PutMapping("/{id}")
    public GiftCertificateDTO update(@Valid @RequestBody GiftCertificateDTO giftCertificateDTO,
                                     @PathVariable Integer id) {
        return giftCertificateService.update(giftCertificateDTO, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        giftCertificateService.delete(id);
    }

    @GetMapping
    public PagedModel<GiftCertificateDTO> findByParameter(@Valid GiftCertificateParamDTO parameterDTO,
                                                          Pageable pageable, PagedResourcesAssembler assembler) {
        Page<GiftCertificateDTO> certDTOPage = giftCertificateService.findByParam(parameterDTO, pageable);
        certDTOPage.get().forEach(certDTO -> giftCertificateModelAssembler.toModel(certDTO));
        return assembler.toModel(certDTOPage);
    }

    @GetMapping("/stat")
    public StatisticGiftCertificateDTO makeStatistics() {
        Integer maxPrice = giftCertificateService.findTopPrice();
        Integer maxDuration = giftCertificateService.findTopDuration();
        return new StatisticGiftCertificateDTO(maxPrice, maxDuration);
    }
}
