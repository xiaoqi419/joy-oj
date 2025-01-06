package com.ojason.joyoj.judge.codesandbox;

import com.ojason.joyoj.judge.codesandbox.model.ExecuteCodeRequest;
import com.ojason.joyoj.judge.codesandbox.model.ExecuteCodeResponse;
import com.ojason.joyoj.model.enums.QuestionSubmitLanguageEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @ClassName : CodeSandboxTest
 * @Description :
 * @Author : Jason
 * @Date: 2024-07-12 15:57
 */
@SpringBootTest
class CodeSandboxTest {


    @Value("${codesandbox.type}")
    private String codesandboxType;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String type = scanner.next();
            CodeSandbox codeSandbox = CodeSandboxFactory.newInstance(type);
            String code = "int main(){}";
            String language = QuestionSubmitLanguageEnum.JAVA.getValue();
            List<String> inputList = new ArrayList<>();
            ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                    .code(code)
                    .language(language)
                    .inputList(inputList)
                    .build();
            ExecuteCodeResponse executeCodeResponse = codeSandbox.executeCode(executeCodeRequest);
        }
    }

    @Test
    void executeCode() {
        String type = codesandboxType;
        CodeSandbox codeSandbox = CodeSandboxFactory.newInstance(type);
        String code = "public class Main {\n" +
                "    public static void main(String[] args) {\n" +
                "        int a = Integer.parseInt(args[0]);\n" +
                "        int b = Integer.parseInt(args[1]);\n" +
                "        System.out.println(\"结果：\" + a + b);\n" +
                "    }\n" +
                "}";
        String language = QuestionSubmitLanguageEnum.JAVA.getValue();
        List<String> inputList = new ArrayList<>();
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .language(language)
                .inputList(inputList)
                .build();
        ExecuteCodeResponse executeCodeResponse = codeSandbox.executeCode(executeCodeRequest);
        System.out.println(executeCodeResponse);
    }
}

