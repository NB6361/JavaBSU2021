package by.NB6361.quizer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class GroupTaskGenerator implements Task.Generator {
    public GroupTaskGenerator(Task.Generator... generators) {
        this.generators = new ArrayList<Task.Generator>();
        this.generators.addAll(Arrays.asList(generators));
    }

    public GroupTaskGenerator(List<Task.Generator> generators) {
        this.generators = (ArrayList<Task.Generator>) generators;
    }

    @Override
    public Task generate() {
        assert generators != null;
        return generators.get((ThreadLocalRandom.current().nextInt(generators.size()))).generate();
    }

    private ArrayList<Task.Generator> generators;
}
