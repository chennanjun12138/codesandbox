package com.example.codesandbox.utils;

import cn.hutool.core.date.StopWatch;
import cn.hutool.core.util.StrUtil;
import com.example.codesandbox.model.ExecuteMessage;
import org.apache.commons.lang3.StringUtils;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 进程工具类
 */

public class ProcessUtils {


    /**
     * 执行进程并获取信息
     *
     * @param runProcess
     * @param opName
     * @return
     */


    public static ExecuteMessage runProcessAndGetMessage(Process runProcess, String opName)
    {
        ExecuteMessage executeMessage=new ExecuteMessage();

         try {
             StopWatch stopWatch = new StopWatch();
             stopWatch.start();

             int exitValue=runProcess.waitFor();
             executeMessage.setExitValue(exitValue);
            if(exitValue==0)
            {
                System.out.println(opName+"成功");
                //分批获得进程的正常输出
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(runProcess.getInputStream()));
                List<String> outputStrList = new ArrayList<>();
                //逐行读取
                String compileOutputLine;
                while ((compileOutputLine=bufferedReader.readLine())!=null)
                {
                    outputStrList.add(compileOutputLine);
                }
                executeMessage.setMessage(StringUtils.join(outputStrList, "\n"));
            }
            else
            {
                System.out.println(opName+"失败。错误码："+exitValue);
                //分批获得进程的正常输出
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(runProcess.getInputStream()));
                List<String> outputStrList = new ArrayList<>();
                //逐行读取
                String compileOutputLine;
                while ((compileOutputLine=bufferedReader.readLine())!=null)
                {
                    outputStrList.add(compileOutputLine);
                }
                executeMessage.setMessage(StringUtils.join(outputStrList, "\n"));

                BufferedReader errorBufferedReader=new BufferedReader(new InputStreamReader(runProcess.getErrorStream()));
                 List<String> errorOutputStrList = new ArrayList<>();
                //逐行读取
                String errorCompileOutputLine;
                while ((errorCompileOutputLine=errorBufferedReader.readLine())!=null)
                {
                    errorOutputStrList.add(errorCompileOutputLine);
                }
                executeMessage.setErrorMessage(StringUtils.join(errorOutputStrList, "\n"));
            }
             stopWatch.stop();
             executeMessage.setTime(stopWatch.getLastTaskTimeMillis());

         }catch (Exception e)
        {
            e.printStackTrace();
        }
            return executeMessage;
    }
    /**
     * 执行交互式进程并获取信息
     *
     * @param runProcess
     * @param args
     * @return
     */
    public static ExecuteMessage runInteractProcessAndGetMessage(Process runProcess, String args) {
        ExecuteMessage executeMessage=new ExecuteMessage();

        try {
            //向控制台输入程序
            InputStream inputStream = runProcess.getInputStream();
            OutputStream outputStream = runProcess.getOutputStream();

            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            String[] s=args.split(" ");
            String join=StrUtil.join("\n",s)+"\n";
            outputStreamWriter.write(join);
            //相当于按了回车，执行输入的发送
            outputStreamWriter.flush();
            //分批获得进程的正常输出
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder compileOutputStringBuilder = new StringBuilder();
            //逐行读取
            String compileOutputLine;
            while ((compileOutputLine = bufferedReader.readLine()) != null) {
                compileOutputStringBuilder.append(compileOutputLine);
            }
            executeMessage.setMessage(compileOutputStringBuilder.toString());
            //资源释放
            outputStreamWriter.close();
            outputStream.close();
            inputStream.close();
            runProcess.destroy();

        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return executeMessage;
    }

}
