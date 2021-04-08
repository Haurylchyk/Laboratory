package com.epam.esm;

import com.epam.esm.dao.query.GiftCertificateCompositeParameter;
import com.epam.esm.dto.GiftCertificateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/certificate")
public class GiftCertificateController {

    private final GiftCertificateService giftCertificateService;

    @Autowired
    public GiftCertificateController(GiftCertificateService giftCertificateService, TagService tagService) {
        this.giftCertificateService = giftCertificateService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GiftCertificateDTO createGiftCertificate(@RequestBody GiftCertificateDTO giftCertificateDTO) {
        return giftCertificateService.createGiftCertificate(giftCertificateDTO);
    }

    @GetMapping("/{id}")
    public GiftCertificateDTO getGiftCertificateById(@PathVariable Integer id) {
        return giftCertificateService.getGiftCertificateById(id);
    }

    @PutMapping("/{id}")
    public GiftCertificateDTO updateGiftCertificate(@RequestBody GiftCertificateDTO giftCertificateDTO, @PathVariable Integer id) {
        return giftCertificateService.updateGiftCertificate(giftCertificateDTO, id);
    }

    @DeleteMapping("/{id}")
    public void deleteGiftCertificate(@PathVariable Integer id) {
        giftCertificateService.deleteGiftCertificate(id);
    }

    @GetMapping
    public List<GiftCertificateDTO> getGiftCertificates(GiftCertificateCompositeParameter parameter) {
        return giftCertificateService.getGiftCertificates(parameter);
    }
}
