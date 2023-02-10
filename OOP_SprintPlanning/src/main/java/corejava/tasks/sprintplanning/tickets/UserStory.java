package corejava.tasks.sprintplanning.tickets;

public class UserStory extends Ticket {
    private final UserStory[] dependsOn;
    public UserStory(int id, String name, int estimate, UserStory... dependsOn) {
        super(id, name, estimate);
        this.dependsOn = dependsOn;
    }

    @Override
    public void complete() {
        if (depsCompleted()) {
            super.complete();
        }
    }

    public UserStory[] getDependencies() {
        UserStory[] us = new UserStory[dependsOn.length];
        System.arraycopy(dependsOn, 0, us, 0, dependsOn.length);
        return us;
    }

    private boolean depsCompleted() {
        if (dependsOn == null || dependsOn.length == 0) { return true; }
        boolean completed = true;
        for (int i = 0; i < dependsOn.length; ++i) {
            if (!dependsOn[i].isCompleted()) {
                completed = false;
                break;
            }
        }
        return completed;
    }

    @Override
    public String toString() {
        return "[US " + getId() + "] " + getName();
    }
}
