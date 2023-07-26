package dev.lpa;

import java.util.*;

public class Main {

    public static void main(String[] args) {

        Set<Task> tasks = TaskData.getTasks("all");
        sortAndPrint("All Tasks", tasks);
//        __________________________________________________________________________________________
//        All Tasks
//        __________________________________________________________________________________________
//        Data Access          Set Up Access Policy      LOW        null
//        Data Access          Set Up Users              LOW        null
//        Data Access          Write Views               LOW        null
//        Data Design          Cross Reference Tables    HIGH       null
//        Data Design          Employee Table            MEDIUM     null
//        Data Design          Encryption Policy         HIGH       null
//        Data Design          Task Table                MEDIUM     null
//        Infrastructure       DB Access                 MEDIUM     null
//        Infrastructure       Logging                   HIGH       null
//        Infrastructure       Password Policy           MEDIUM     null
//        Infrastructure       Security                  HIGH       null

        Comparator<Task> sortByPriority = Comparator.comparing(Task::getPriority);
        Set<Task> annsTasks = TaskData.getTasks("ann");
        sortAndPrint("Ann's Tasks", annsTasks, sortByPriority);
//        __________________________________________________________________________________________
//        Ann's Tasks
//        __________________________________________________________________________________________
//        Data Design          Encryption Policy         HIGH       ann
//        Infrastructure       Security                  HIGH       ann
//        Infrastructure       Password Policy           MEDIUM     ann
//        Research             Cloud solutions           MEDIUM     ann
//        Data Design          Project Table             MEDIUM     ann
//        Data Access          Write Views               LOW        ann
    }

    private static void sortAndPrint(String header, Collection<Task> collection) {
        sortAndPrint(header, collection, null);
    }

    private static void sortAndPrint(String header, Collection<Task> collection, Comparator<Task> sorter) {

        String lineSeparator = "_".repeat(90);
        System.out.println(lineSeparator);
        System.out.println(header);
        System.out.println(lineSeparator);

        List<Task> list = new ArrayList<>(collection);
        list.sort(sorter);
        list.forEach(System.out::println);
    }

    public static Set<Task> getUnion(List<Set<Task>> taskSetList) {
        Set<Task> union = new HashSet<>();
        taskSetList.forEach(s -> union.addAll(s));
        return union;
    }


}