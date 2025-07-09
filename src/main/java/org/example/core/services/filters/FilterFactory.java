package org.example.core.services.filters;

import org.example.dto.SearchParams;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class FilterFactory {

    public static FileSearchFilter createFilter(SearchParams params) {
        List<FileSearchFilter> filters = new ArrayList<>();

        addNameFilterIfNeeded(params, filters);
        addSizeFilterIfNeeded(params, filters);
        addDateFilterIfNeeded(params, filters);
        addContentFilterIfNeeded(params, filters);

        return combineFilters(filters);
    }

    private static void addNameFilterIfNeeded(SearchParams params, List<FileSearchFilter> filters) {
        if (params.namePattern() != null) {
            filters.add(new NameSearchFilter(params.namePattern()));
        }
    }

    private static void addSizeFilterIfNeeded(SearchParams params, List<FileSearchFilter> filters) {
        if (hasSizeCondition(params)) {
            filters.add(new SizeSearchFilter(params.minSize(), params.maxSize()));
        }
    }

    private static boolean hasSizeCondition(SearchParams params) {
        return params.minSize() != null || params.maxSize() != null;
    }

    private static void addDateFilterIfNeeded(SearchParams params, List<FileSearchFilter> filters) {
        if (params.modifiedAfter() != null) {
            filters.add(new ModifiedAfterSearchFilter(params.modifiedAfter()));
        }
    }

    private static void addContentFilterIfNeeded(SearchParams params, List<FileSearchFilter> filters) {
        if (params.contentContains() != null) {
            filters.add(new ContentSearchFilter(params.contentContains()));
        }
    }

    private static FileSearchFilter combineFilters(List<FileSearchFilter> filters) {
        return filters.stream()
                .reduce(file -> true, FileSearchFilter::and);
    }

}