package nl.rug.oop.rpg.game.configsys;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.stream.Stream;

public class FileSearch {

    public void listSaveFiles(String path, String extension, String absolutepath) {
        File save = new File(path);
        FilenameFilter serf = (file, s) -> s.endsWith(extension);
        File[] list = save.listFiles(serf);

        if (list != null) {
            Stream<File> stream = Stream.of(list);
            stream.forEach(str -> {
                try {
                    System.out.println(" ■ " + str.getName() + " ─ Created on : " + getCreateDate(str.getName(), absolutepath));
                } catch (IOException e) {
                    System.out.println("Error fetching file metadata");
                }
            });
        } else {
            System.out.println("Save files path is empty");
        }
    }

    public String getCreateDate(String name, String absolutepath) throws IOException {
        Path files = Paths.get(absolutepath + name);
        BasicFileAttributes att = Files.readAttributes(files, BasicFileAttributes.class);
        return String.valueOf(att.creationTime());
    }
}
