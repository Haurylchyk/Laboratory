package com.epam.esm;

import com.epam.esm.assembler.GiftCertificateModelAssembler;
import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.dto.GiftCertificateParamDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@Validated
@RestController
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
    public GiftCertificateDTO update(@Valid @RequestBody GiftCertificateDTO giftCertificateDTO, @PathVariable Integer id) {
        return giftCertificateService.update(giftCertificateDTO, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        giftCertificateService.delete(id);
    }

    @GetMapping
    public List<GiftCertificateDTO> findByParameter(@RequestParam(required = false, defaultValue = "1") @Min(1) Integer page,
                                                    @RequestParam(required = false, defaultValue = "1") @Min(1) Integer size,
                                                    @Valid GiftCertificateParamDTO parameterDTO) {
        return giftCertificateModelAssembler.toModel(giftCertificateService.findByParam(page, size, parameterDTO));
    }
}
