package me.bannock.assaultcube.utils;

import java.io.IOException;
import java.util.*;

public class ProcessUtils {

    public static Map<String, List<Integer>> getRunningProcesses() throws IOException {
        Map<String, List<Integer>> processes = new HashMap<>();

        // Run tasklist.exe to get a list of all processes
        Process process = Runtime.getRuntime().exec("tasklist.exe");

        // Capture the output and extract wanted data
        Scanner scanner = new Scanner(process.getInputStream());
        int lines = 0;
        while(scanner.hasNext()){
            String line = scanner.nextLine();

            // Skip the first 3 lines because they're just the table labels as well as whitespace
            if (++lines <= 3)
                continue;

            // Remove unnecessary spaces
            line = line.replaceAll("(\\s| )+", " ");
            // Get data from line and save to map
            try{
                String[] data = line.split(" ");
                String name = data[0];
                int pid = Integer.parseInt(data[1]);
                // Add new arraylist of pids if one is not already created
                if (!processes.containsKey(name))
                    processes.put(name, new ArrayList<>());
                // Finally add to map
                processes.get(name).add(pid);

            }catch (Exception ignored){} // Why do people use spaces in their prorgam names?
        }

        return processes;
    }

}
