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

        Set<Task> bobsTasks = TaskData.getTasks("bob");
        Set<Task> carolsTasks = TaskData.getTasks("carol");
        List<Set<Task>> sets = List.of(annsTasks, bobsTasks, carolsTasks);

        Set<Task> assignedTasks = getUnion(sets);
        sortAndPrint("AssignedTasks", assignedTasks);
//        __________________________________________________________________________________________
//        AssignedTasks
//        __________________________________________________________________________________________
//        Data Access          Write Views               LOW        ann
//        Data Design          Encryption Policy         HIGH       ann
//        Data Design          Project Table             MEDIUM     ann
//        Data Design          Task Table                HIGH       carol
//        Infrastructure       DB Access                 MEDIUM     carol
//        Infrastructure       Logging                   HIGH       carol
//        Infrastructure       Password Policy           MEDIUM     ann
//        Infrastructure       Security                  HIGH       ann
//        Research             Cloud solutions           MEDIUM     ann

        Set<Task> everyTask = getUnion(List.of(tasks, assignedTasks));
        sortAndPrint("The true all tasks", everyTask);
//        __________________________________________________________________________________________
//        The true all tasks
//        __________________________________________________________________________________________
//        Data Access          Set Up Access Policy      LOW        null
//        Data Access          Set Up Users              LOW        null
//        Data Access          Write Views               LOW        null
//        Data Design          Cross Reference Tables    HIGH       null
//        Data Design          Employee Table            MEDIUM     null
//        Data Design          Encryption Policy         HIGH       null
//        Data Design          Project Table             MEDIUM     ann
//        Data Design          Task Table                MEDIUM     null
//        Infrastructure       DB Access                 MEDIUM     null
//        Infrastructure       Logging                   HIGH       null
//        Infrastructure       Password java             MEDIUM     null
//        Infrastructure       Security                  HIGH       null
//        Research             Cloud solutions           MEDIUM     ann

        Set<Task> missingTasks = getDifference(everyTask, tasks);
        sortAndPrint("Missing tasks", missingTasks);
//        __________________________________________________________________________________________
//        Missing tasks
//        __________________________________________________________________________________________
//        Data Design          Project Table             MEDIUM     ann
//        Research             Cloud solutions           MEDIUM     ann

        Set<Task> unassignedTasks = getDifference(tasks, assignedTasks);
        sortAndPrint("Unassigned tasks", unassignedTasks, sortByPriority);
//        __________________________________________________________________________________________
//        Unassigned tasks
//        __________________________________________________________________________________________
//        Data Design          Cross Reference Tables    HIGH       null
//        Data Design          Employee Table            MEDIUM     null
//        Data Access          Set Up Access Policy      LOW        null
//        Data Access          Set Up Users              LOW        null

        Set<Task> overlap = getUnion(List.of(
                getIntersect(annsTasks, bobsTasks),
                getIntersect(carolsTasks, bobsTasks),
                getIntersect(annsTasks, carolsTasks)
        ));
        sortAndPrint("Assigned to multiples", overlap, sortByPriority);
//        __________________________________________________________________________________________
//        Assigned to multiples
//                __________________________________________________________________________________________
//        Data Design          Encryption Policy         HIGH       ann
//        Infrastructure       Security                  HIGH       ann
//        Infrastructure       Password Policy           MEDIUM     ann
//        Data Access          Write Views               LOW        ann

        List<Task> overlapping = new ArrayList<>();
        for (Set<Task> set : sets) {
            Set<Task> dupes = getIntersect(set, overlap);
            overlapping.addAll(dupes);
        }
        Comparator<Task> priorityNatural = sortByPriority.thenComparing(
                Comparator.naturalOrder());
        sortAndPrint("Overlapping", overlapping, priorityNatural);
//        __________________________________________________________________________________________
//        Overlapping
//        __________________________________________________________________________________________
//        Data Design          Encryption Policy         HIGH       ann
//        Data Design          Encryption Policy         HIGH       bob
//        Infrastructure       Security                  HIGH       ann
//        Infrastructure       Security                  HIGH       bob
//        Infrastructure       Password Policy           MEDIUM     ann
//        Infrastructure       Password Policy           MEDIUM     bob
//        Infrastructure       Password Policy           MEDIUM     carol
//        Data Access          Write Views               LOW        ann
//        Data Access          Write Views               LOW        bob
//        Data Access          Write Views               LOW        carol
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

    private static Set<Task> getUnion(List<Set<Task>> sets) {
        Set<Task> union = new HashSet<>();
        for (var taskSet : sets) {
            union.addAll(taskSet);
        }
        return union;
    }

    private static Set<Task> getIntersect(Set<Task> a, Set<Task> b) {
        Set<Task> intersect = new HashSet<>(a);
        intersect.retainAll(b);
        return intersect;
    }

    private static Set<Task> getDifference(Set<Task> a, Set<Task> b) {
        Set<Task> result = new HashSet<>(a);
        result.removeAll(b);
        return result;
    }
}