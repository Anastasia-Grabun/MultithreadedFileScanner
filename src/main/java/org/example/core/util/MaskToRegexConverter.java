package org.example.core.util;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class MaskToRegexConverter {

    public static String convert(String mask) {
        StringBuilder regex = new StringBuilder();
        for (char c : mask.toCharArray()) {
            switch (c) {
                case '*':
                    regex.append(".*");
                    break;
                case '?':
                    regex.append(".");
                    break;
                case '.':
                    regex.append("\\.");
                    break;
                case '\\':
                    regex.append("\\\\");
                    break;
                default:
                    if ("+()^$|{}[]".indexOf(c) != -1) {
                        regex.append("\\");
                    }
                    regex.append(c);
                    break;
            }
        }

        return regex.toString();
    }

}

