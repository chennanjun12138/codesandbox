package com.example.codesandbox;

import org.apache.commons.lang3.ArrayUtils;

import java.io.*;
import java.util.*;

public class Main
{
    public static void main(String args[]) throws Exception
    {
        String inputArgs = "arg1 arg2"; // 假设这是你的输入参数字符串
        String[] inputArgsArray = inputArgs.split(" ");

        String[] cmdArray = new String[]{"/bin/bash", "-c", "cd /app && echo"};
        cmdArray = ArrayUtils.addAll(cmdArray, inputArgsArray);
        cmdArray = ArrayUtils.addAll(cmdArray, new String[]{"| ./main"});

        for (String s : cmdArray) {
            System.out.print(s + " ");
        }
    }
}
