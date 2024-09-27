package by.NB6361.quizer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class PoolTaskGenerator implements Task.Generator {
    public PoolTaskGenerator(boolean allowDuplicate, Task... tasks) {
        allowedDuplicate = allowDuplicate;
        tasksPool = new ArrayList<Task>();
        tasksPool.addAll(Arrays.asList(tasks));
    }

    public PoolTaskGenerator(boolean allowDuplicate, List<Task> tasks) {
        allowedDuplicate = allowDuplicate;
        tasksPool = (ArrayList<Task>) tasks;
    }

    @Override
    public Task generate() {
        assert tasksPool != null;
        if (allowedDuplicate) {
            return tasksPool.get(ThreadLocalRandom.current().nextInt(tasksPool.size()));
        }
        int currIndex = ThreadLocalRandom.current().nextInt(tasksPool.size());
        Task currentTask = tasksPool.get(currIndex);
        tasksPool.remove(currentTask);
        return currentTask;
    }

    private ArrayList<Task> tasksPool;
    private boolean allowedDuplicate;
}
