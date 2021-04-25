package com.epam.esm.filler;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;

import java.time.LocalDateTime;
import java.util.List;

public class GiftCertificateUpdatedFieldFiller {
    public static GiftCertificate update(GiftCertificate withUpdatedFields, GiftCertificate currentInDatabase) {
        String name = withUpdatedFields.getName();
        if (name != null) {
            currentInDatabase.setName(name);
        }

        String desc = withUpdatedFields.getDescription();
        if (desc != null) {
            currentInDatabase.setDescription(desc);
        }

        Integer price = withUpdatedFields.getPrice();
        if (price != null) {
            currentInDatabase.setPrice(price);
        }

        Integer duration = withUpdatedFields.getDuration();
        if (duration != null) {
            currentInDatabase.setDuration(duration);
        }

        LocalDateTime lastUpdateDate = withUpdatedFields.getLastUpdateDate();
        if (lastUpdateDate != null) {
            currentInDatabase.setLastUpdateDate(lastUpdateDate);
        }

        List<Tag> tagList = withUpdatedFields.getTagList();
        if (tagList != null) {
            currentInDatabase.setTagList(tagList);
        }
        return currentInDatabase;
    }
}
