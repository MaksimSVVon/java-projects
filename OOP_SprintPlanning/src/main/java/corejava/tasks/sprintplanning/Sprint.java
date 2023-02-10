package corejava.tasks.sprintplanning;

import corejava.tasks.sprintplanning.tickets.Bug;
import corejava.tasks.sprintplanning.tickets.Ticket;
import corejava.tasks.sprintplanning.tickets.UserStory;

public class Sprint {
    private final int capacity;
    private final int ticketsLimit;
    private Ticket[] tickets;
    private int current;
    private int pointer;
    public Sprint(int capacity, int ticketsLimit) {
        this.capacity = capacity;
        this.ticketsLimit = ticketsLimit;
        tickets = new Ticket[ticketsLimit];
        current = 0;
        pointer = 0;
    }

    public boolean addUserStory(UserStory userStory) {
        if (userStory == null || userStory.isCompleted() || userStory.getEstimate() + current > capacity || pointer == ticketsLimit) { return false; }
        UserStory[] us = userStory.getDependencies();
        for (int i = 0; i < us.length; ++i) {
            boolean pass = false;
            for (int j = 0; j < pointer; ++j) {
                if (us[i] == tickets[j]) {
                    pass = true;
                    break;
                }
            }
            if (!pass) { return false; }
        }
        tickets[pointer++] = userStory;
        current += userStory.getEstimate();
        return true;
    }

    public boolean addBug(Bug bugReport) {
        if (bugReport == null || bugReport.isCompleted() || bugReport.getEstimate() + current > capacity || pointer == ticketsLimit) { return false; }
        tickets[pointer++] = bugReport;
        current += bugReport.getEstimate();
        return true;
    }

    public Ticket[] getTickets() {
        Ticket[] ticks = new Ticket[pointer];
        System.arraycopy(tickets, 0, ticks, 0, pointer);
        return ticks;
    }

    public int getTotalEstimate() {
        return current;
    }
}
