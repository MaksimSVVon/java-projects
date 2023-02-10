package corejava.tasks.observer;

import java.util.*;

public class GitRepoObservers {
    public static Repository newRepository() {
        return new Repository() {
            final Map<Branch, ArrayList<Commit>> branches = new HashMap<>();
            final List<WebHook> hooks = new ArrayList<>();

            {
                branches.put(new Branch("main"), new ArrayList<>());
            }

            @Override
            public Branch getBranch(String name) {
                Branch branch = new Branch(name);
                for (var b : branches.keySet()) {
                    if (b.equals(branch)) { return b; }
                }
                return null;
            }

            @Override
            public Branch newBranch(Branch sourceBranch, String name) {
                if (getBranch(name) == null && getBranch(sourceBranch.toString()) != null) {
                    Branch branch = new Branch(name);
                    branches.put(branch, new ArrayList<>(branches.get(sourceBranch)));
                    return branch;
                }
                throw new IllegalArgumentException();
            }

            @Override
            public Commit commit(Branch branch, String author, String[] changes) {
                Commit commit = new Commit(author, changes);
                branches.get(branch).add(commit);
                for (var hook : hooks) {
                    Branch hooksBranch = getBranch(hook.branch());
                    if (hook.type() == Event.Type.COMMIT && branch.equals(hooksBranch)) {
                        hook.onEvent(new Event(
                                Event.Type.COMMIT,
                                branch,
                                List.of(commit)
                        ));
                    }
                }
                return commit;
            }

            @Override
            public void merge(Branch sourceBranch, Branch targetBranch) {
                List<Commit> source = branches.get(sourceBranch),
                        target = branches.get(targetBranch),
                        added = new ArrayList<>();

                source.forEach(commit -> {
                    if (!target.contains(commit)) {
                        target.add(commit);
                        added.add(commit);
                    }
                });

                if (added.size() != 0) {
                    for (var hook : hooks) {
                        Branch hooksBranch = getBranch(hook.branch());
                        if (hook.type() == Event.Type.MERGE && targetBranch.equals(hooksBranch)) {
                            hook.onEvent(new Event(
                                    Event.Type.MERGE,
                                    targetBranch,
                                    added
                            ));
                        }
                    }
                }
            }

            @Override
            public void addWebHook(WebHook webHook) { hooks.add(webHook); }
        };
    }

    public static WebHook mergeToBranchWebHook(String branchName) {
        return new WebHook() {
            final Branch observeBranch = new Branch(branchName);
            final Event.Type type = Event.Type.MERGE;
            final List<Event> caught = new ArrayList<>();

            @Override
            public String branch() {
                return observeBranch.toString();
            }

            @Override
            public Event.Type type() {
                return type;
            }

            @Override
            public List<Event> caughtEvents() {
                return caught;
            }

            @Override
            public void onEvent(Event event) {
                if (event.type() == Event.Type.MERGE) {
                    caught.add(event);
                } else throw new IllegalArgumentException();
            }
        };
    }

    public static WebHook commitToBranchWebHook(String branchName) {
        return new WebHook() {
            final Branch observeBranch = new Branch(branchName);
            final Event.Type type = Event.Type.COMMIT;
            final List<Event> caught = new ArrayList<>();

            @Override
            public String branch() {
                return observeBranch.toString();
            }

            @Override
            public Event.Type type() {
                return type;
            }

            @Override
            public List<Event> caughtEvents() {
                return caught;
            }

            @Override
            public void onEvent(Event event) {
                if (event.type() == Event.Type.COMMIT) {
                    caught.add(event);
                } else throw new IllegalArgumentException();
            }
        };
    }
}
