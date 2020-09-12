package com.levent8421.wechat.tools.commons.utils;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Create By leven ont 2020/8/17 0:50
 * Class Name :[CollectionUtils]
 * <p>
 * Collection Utils
 *
 * @author leven
 */
public class CollectionUtils {
    /**
     * Check A Collection is empty
     *
     * @param collection collection object
     * @return is empty
     */
    public static boolean isEmpry(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * Check a map is empty
     *
     * @param map map object
     * @return is empty
     */
    public static boolean isEmpry(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    /**
     * Join collection as string
     *
     * @param stream    stream
     * @param delimiter delimiter
     * @return str
     */
    public static String joinAsString(Stream<?> stream, String delimiter) {
        return stream
                .filter(Objects::nonNull)
                .map(Object::toString)
                .reduce((a, b) -> a + delimiter + b)
                .orElse("");
    }
}
