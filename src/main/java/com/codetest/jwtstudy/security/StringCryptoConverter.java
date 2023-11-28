package com.codetest.jwtstudy.security;

import com.codetest.jwtstudy.utils.Aes256Util;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

@Converter
@RequiredArgsConstructor
public class StringCryptoConverter implements AttributeConverter<String, String> {

    private final Aes256Util aes256Util;

    @Override
    public String convertToDatabaseColumn(String attribute) {
        if (!StringUtils.hasText(attribute)) return null;

        try {
            return aes256Util.encrypt(attribute);
        } catch (Exception e) {
            throw new RuntimeException("failed to encrypt data", e);
        }
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        if (!StringUtils.hasText(dbData)) return null;

        try {
            return aes256Util.decrypt(dbData);
        } catch (Exception e) {
            return dbData;
        }
    }

}
