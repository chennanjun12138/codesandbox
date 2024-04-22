import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        String[] inputArgsArray = {"arg1", "arg2"};

        String[] cmdArray = ArrayUtils.addAll(new String[]{"/bin/bash", "-c", "'cd /app && echo '1 2' | ./a.out'"}, inputArgsArray);

        for (String s : cmdArray) {
            System.out.println(s);
        }
    }
}
