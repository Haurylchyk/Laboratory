package com.epam.esm;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.dto.GiftCertificateCompositeParameterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/certificates")
public class GiftCertificateController {

    private final GiftCertificateService giftCertificateService;

    @Autowired
    public GiftCertificateController(GiftCertificateService giftCertificateService) {
        this.giftCertificateService = giftCertificateService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GiftCertificateDTO create(@RequestBody GiftCertificateDTO giftCertificateDTO) {
        return giftCertificateService.create(giftCertificateDTO);
    }

    @GetMapping("/{id}")
    public GiftCertificateDTO read(@PathVariable Integer id) {
        return giftCertificateService.read(id);
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
    public List<GiftCertificateDTO> readByParameter(GiftCertificateCompositeParameterDTO parameterDTO) {
        return giftCertificateService.readByParameter(parameterDTO);
    }
}
