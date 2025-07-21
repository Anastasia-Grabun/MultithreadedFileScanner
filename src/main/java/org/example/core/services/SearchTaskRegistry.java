package org.example.core.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Future;

@Slf4j
@Component
public class SearchTaskRegistry {

    private final Map<String, List<Future<?>>> tasks = new ConcurrentHashMap<>();

    public void register(String username, Future<?> future) {
        tasks.computeIfAbsent(username, key -> new CopyOnWriteArrayList<>()).add(future);
        log.info("Register task for user " + username);
    }

    public void cancelAll(String username) {
        List<Future<?>> userTasks = tasks.remove(username);
        if (userTasks != null) {
            for (Future<?> task : userTasks) {
                task.cancel(true);
            }
        }
    }

}

