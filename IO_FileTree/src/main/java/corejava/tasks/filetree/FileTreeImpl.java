package corejava.tasks.filetree;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class FileTreeImpl implements FileTree {
    private static class Prop {
        int level;
        Path path;
        String name;
        long size;
        boolean opened;

        Prop(int level, Path path, long size) {
            this.level = level;
            this.path = path;
            this.name = path.getFileName().toString();
            this.size = size;
            if (Files.isDirectory(path)) { opened = false; }
            else { opened = true; }
        }
    }
    @Override
    public Optional<String> tree(Path path) {
        try {
            if (path == null || !Files.exists(path)) { return Optional.empty(); }
            if (!Files.isDirectory(path)) {
                return Optional.of(path.getFileName().toString() + " " + Files.size(path) + " bytes");
            }

            int nestLevel = 1;
            List<Prop> tree = new ArrayList<>(100);
            tree.add(new Prop(nestLevel, path, 0));
            int added;

            do {
                ++nestLevel;
                added = 0;
                int size = tree.size() - 1;
                for (int i = size; i >= 0; --i) {
                    Prop el = tree.get(i);
                    if (!el.opened) {
                        File folder = new File(String.valueOf(el.path));
                        File[] dirs = folder.listFiles((f) -> Files.isDirectory(f.toPath()));
                        List<Prop> toAdd = new ArrayList<>();

                        if (dirs != null && dirs.length != 0) {
                            Arrays.sort(dirs, (o1, o2) -> o1.getName().compareToIgnoreCase(o2.getName()));
                            for (var d : dirs) {
                                toAdd.add(new Prop(nestLevel, d.toPath(), 0));
                            }
                        }
                        File[] files = folder.listFiles((f) -> !Files.isDirectory(f.toPath()));
                        if (files != null && files.length != 0) {
                            Arrays.sort(files, (o1, o2) -> o1.getName().compareToIgnoreCase(o2.getName()));
                            for (var f : files) {
                                toAdd.add(new Prop(nestLevel, f.toPath(), Files.size(f.toPath())));
                            }
                        }
                        added += toAdd.size();
                        tree.addAll(i + 1, toAdd);

                    }
                    el.opened = true;
                }
            } while (added != 0);

            for (int level = nestLevel; level > 1; --level) {
                long currentSum = 0;
                boolean beingChained = false, levelFilesStarted = false;
                for (int i = tree.size() - 1; i >= 0; --i) {
                    Prop file = tree.get(i);
                    if (file.level == level) { currentSum += file.size; }
                    else if (file.level == level - 1 && currentSum > 0) {
                        file.size = currentSum;
                        currentSum = 0;
                    }

                    if (file.level == level) {
                        levelFilesStarted = true;
                        if (!beingChained) {
                            file.name = "└─ " + file.name;
                            beingChained = true;
                        } else {
                            file.name = "├─ " + file.name;
                        }
                    } else if (file.level > level) {
                        if (levelFilesStarted) { file.name = "│  " + file.name; }
                        else { file.name = "   " + file.name; }
                    } else {
                        levelFilesStarted = false;
                        beingChained = false;
                    }
                }
            }

            StringBuilder s = new StringBuilder();
            for (var f : tree) {
                s.append(f.name).append(" ").append(f.size).append(" bytes\n");
            }

            return Optional.of(s.toString());
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}