package com.ojason.joyoj.model.enums;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: joyoj-backend
 * @ClassName: QuestionSubmitLanguageEnum
 * @description: 编程语言枚举
 * @author: Jason
 * @create: 2024-06-23 23:10
 * @from: <a href="https://www.ojason.top">我的博客</a>
 */
public enum QuestionSubmitLanguageEnum {
    JAVA("java", "java"),
    CPLUSPLUS("cpp", "cpp"),
    PYTHON("python", "python"),
    C("c", "c"),
    GOLANG("go", "go");


    private final String text;

    private final String value;

    QuestionSubmitLanguageEnum(String text, String value) {
        this.text = text;
        this.value = value;
    }

    /**
     * 获取值列表
     *
     * @return
     */
    public static List<String> getValues() {
        return Arrays.stream(values()).map(item -> item.value).collect(Collectors.toList());
    }

    /**
     * 根据 value 获取枚举
     *
     * @param value
     * @return
     */
    public static QuestionSubmitLanguageEnum getEnumByValue(String value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }
        for (QuestionSubmitLanguageEnum anEnum : QuestionSubmitLanguageEnum.values()) {
            if (anEnum.value.equals(value)) {
                return anEnum;
            }
        }
        return null;
    }

    public String getValue() {
        return value;
    }

    public String getText() {
        return text;
    }
}
