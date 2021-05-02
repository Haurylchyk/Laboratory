package com.epam.esm;

import com.epam.esm.assembler.GiftCertificateModelAssembler;
import com.epam.esm.assembler.TagModelAssembler;
import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.dto.GiftCertificateParamDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public GiftCertificateDTO create(@RequestBody GiftCertificateDTO giftCertificateDTO) {
        return giftCertificateService.create(giftCertificateDTO);
    }

    @GetMapping("/{id}")
    public GiftCertificateDTO findById(@PathVariable Integer id) {
        return giftCertificateService.findById(id);
    }

    @PutMapping("/{id}")
    public GiftCertificateDTO update(@RequestBody GiftCertificateDTO giftCertificateDTO, @PathVariable Integer id) {
        return giftCertificateService.update(giftCertificateDTO, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        giftCertificateService.delete(id);
    }

    @GetMapping
    public List<GiftCertificateDTO> findByParameter(GiftCertificateParamDTO parameterDTO) {
        return giftCertificateModelAssembler.toModel(giftCertificateService.findByParam(parameterDTO));
    }
}
