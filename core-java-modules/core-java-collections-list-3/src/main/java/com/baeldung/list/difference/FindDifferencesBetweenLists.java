package com.baeldung.list.difference;

import com.google.common.collect.Sets;
import org.apache.commons.collections4.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

public class FindDifferencesBetweenLists {

    public static <T> List<T> differencesUsingPlainJavaCollections(List<T> listOne, List<T> listTwo) {
        List<T> differences = new ArrayList<>(listOne);
        differences.removeAll(listTwo);
        return differences;
    }

    public static <T> List<T> differencesUsingPlainJavaStream(List<T> listOne, List<T> listTwo) {
        return listOne.stream()
                .filter(element -> !listTwo.contains(element))
                .distinct()
                .collect(Collectors.toList());
    }

    public static <T> List<T> differencesUsingApacheCommonsCollections(List<T> listOne, List<T> listTwo) {
        return new ArrayList<>((CollectionUtils.removeAll(listOne, listTwo)));
    }

    public static <T> List<T> differencesUsingGoogleGuava(List<T> listOne, List<T> listTwo) {
        return new ArrayList<>(Sets.difference(Sets.newHashSet(listOne), Sets.newHashSet(listTwo)));
    }

    public static <T> List<T> differencesUsingPlainJavaCollectionsWithDuplicates(List<T> listOne, List<T> listTwo) {
        List<T> differences = new ArrayList<>(listOne);
        listTwo.forEach(differences::remove);
        return differences;
    }

    public static <T> List<T> differencesUsingApacheCommonsCollectionsWithDuplicates(List<T> listOne, List<T> listTwo) {
        return new ArrayList<>(CollectionUtils.subtract(listOne, listTwo));
    }

}
