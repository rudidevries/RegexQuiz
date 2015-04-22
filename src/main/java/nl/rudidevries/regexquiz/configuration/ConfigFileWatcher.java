package nl.rudidevries.regexquiz.configuration;

import java.io.IOException;
import java.nio.file.*;

import static java.nio.file.StandardWatchEventKinds.*;

/**
 * Config file watcher. Initiates loading of config files.
 * This class can be used to just load .rgx files once
 * (when constructed), or to create a thread that keeps
 * a look out for new .rgx file in the watched folder.
 * That we newly added files are directly added to the
 * question bank.
 */
public class ConfigFileWatcher implements Runnable {

    private FileLoader loader;
    private WatchService watcher;
    private Path dir;
    private PathMatcher fileMatcher;

    /**
     * Constructor
     *
     * @param loader for loading the files that are found.
     * @param dir the directory to be searched / watched.
     */
    public ConfigFileWatcher(FileLoader loader, Path dir) {
        this.loader = loader;
        this.dir = dir;

        loadExistingFiles();
        fileMatcher = FileSystems.getDefault().getPathMatcher("glob:**/*.rgx");
    }

    /**
     * Load files that are in the directory at this moment.
     */
    private void loadExistingFiles() {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir, "*.rgx")) {
            for (Path path : stream) {
                loader.readFile(path);
            }
        }
        catch (IOException e) {
            // Do not handle at this time.
        }
    }

    /**
     * Initialize the watch service.
     * @throws IOException
     */
    private void initWatcher() throws IOException {
        watcher = FileSystems.getDefault().newWatchService();
        dir.register(watcher, ENTRY_CREATE);
    }

    /**
     * Runnable
     *
     * Create a file watcher, and directly load new .rgx files
     * into the question bank.
     */
    public void run() {
        try {
            initWatcher();

            while(true) {
                WatchKey key;
                key = watcher.take();

                for (WatchEvent<?> event : key.pollEvents()) {
                    Path configFile = dir.resolve(event.context().toString());
                    if (fileMatcher.matches(configFile)) {
                        loader.readFile(configFile);
                    }
                }
                key.reset();
            }

        }
        catch (IOException | InterruptedException e) {
            // Do not handle at this time.
        }
        finally {
            try {
                watcher.close();
            }
            catch (IOException e) {
                // Also not handling this one.
            }
        }
    }
}
