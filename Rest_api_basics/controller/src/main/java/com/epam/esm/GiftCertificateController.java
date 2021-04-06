package com.epam.esm;

import com.epam.esm.GiftCertificateService;
import com.epam.esm.TagService;
import com.epam.esm.dto.GiftCertificateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "gift_system/certificate")
public class GiftCertificateController {

    private final GiftCertificateService giftCertificateService;
    private final TagService tagService;


    @Autowired
    public GiftCertificateController(GiftCertificateService giftCertificateService, TagService tagService) {
        this.giftCertificateService = giftCertificateService;
        this.tagService = tagService;
    }

    @PostMapping
    public GiftCertificateDTO createGiftCertificate(@RequestBody GiftCertificateDTO giftCertificateDTO) {
        return giftCertificateService.createGiftCertificate(giftCertificateDTO);
    }

    @GetMapping("/{id}")
    public GiftCertificateDTO getGiftCertificateById(@PathVariable Integer id) {
        return giftCertificateService.getGiftCertificateById(id);
    }

    @PutMapping("/{id}")
    public void updateGiftCertificate(@RequestBody GiftCertificateDTO giftCertificateDTO, @PathVariable Integer id) {
        giftCertificateService.updateGiftCertificate(giftCertificateDTO, id);
    }

    @DeleteMapping("/{id}")
    public void deleteGiftCertificate(@PathVariable Integer id) {
        giftCertificateService.deleteGiftCertificate(id);
    }

    @GetMapping
    public List<GiftCertificateDTO> getCertificates() {
        return giftCertificateService.getAllGiftCertificates();
    }
}
