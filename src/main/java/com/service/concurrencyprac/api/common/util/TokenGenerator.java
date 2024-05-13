package com.service.concurrencyprac.api.common.util;

import org.apache.commons.lang3.RandomStringUtils;

public class TokenGenerator {

    private static final int TOKEN_LENGTH = 20;

    public static String randomCharacter(int length) {
        return RandomStringUtils.randomAlphabetic(length);
    }

    public static String randomCharacterWithPrefix(String prefix) {
        return prefix + randomCharacter(TOKEN_LENGTH - prefix.length());
    }

}
