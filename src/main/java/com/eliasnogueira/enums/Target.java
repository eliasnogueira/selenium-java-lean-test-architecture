/*
 * MIT License
 *
 * Copyright (c) 2022 Elias Nogueira
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.eliasnogueira.enums;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toMap;

public enum Target {

    LOCAL("local"), LOCAL_SUITE("local-suite"), SELENIUM_GRID("selenium-grid"),
    TESTCONTAINERS("testcontainers");

    private final String value;
    private static final Map<String, Target> ENUM_MAP;

    Target(String value) {
        this.value = value;
    }

    static {
        Map<String, Target> map = stream(Target.values())
                .collect(toMap(instance -> instance.value.toLowerCase(), instance -> instance, (_, b) -> b, ConcurrentHashMap::new));
        ENUM_MAP = Collections.unmodifiableMap(map);
    }

    public static Target get(String value) {
        if (!ENUM_MAP.containsKey(value.toLowerCase()))
            throw new IllegalArgumentException(String.format("Value %s not valid. Use one of the TARGET enum values", value));

        return ENUM_MAP.get(value.toLowerCase());
    }
}
