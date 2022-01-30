import java.nio.file.FileSystem;
import java.util.HashMap;

import io.github.coolcrabs.brachyura.dependency.FileDependency;
import io.github.coolcrabs.brachyura.fabric.FabricLoader;
import io.github.coolcrabs.brachyura.fabric.FabricMaven;
import io.github.coolcrabs.brachyura.fabric.FabricProject;
import io.github.coolcrabs.brachyura.fabric.Intermediary;
import io.github.coolcrabs.brachyura.mappings.Namespaces;
import io.github.coolcrabs.brachyura.maven.Maven;
import io.github.coolcrabs.brachyura.maven.MavenId;
import io.github.coolcrabs.brachyura.util.FileSystemUtil;
import io.github.coolcrabs.brachyura.util.Util;
import net.fabricmc.mappingio.MappingReader;
import net.fabricmc.mappingio.adapter.MappingNsRenamer;
import net.fabricmc.mappingio.format.MappingFormat;
import net.fabricmc.mappingio.tree.MappingTree;
import net.fabricmc.mappingio.tree.MemoryMappingTree;

public class Buildscript extends FabricProject {

    @Override
    public String getMcVersion() {
        return "1.18.1";
    }

    @Override
    public MappingTree createIntermediary() {
        try {
            HashMap<String, String> nameMap = new HashMap<>();
            nameMap.put("official", Namespaces.OBF);
            nameMap.put("hashed", Namespaces.INTERMEDIARY);
            FileDependency d = Maven.getMavenFileDep("https://maven.quiltmc.org/repository/release/", new MavenId("org.quiltmc:hashed:1.18.1"), ".jar");
            MemoryMappingTree r = new MemoryMappingTree(false);
            try (FileSystem f = FileSystemUtil.newJarFileSystem(d.file)) {
                MappingReader.read(f.getPath("hashed", "mappings.tiny"), MappingFormat.TINY_2, new MappingNsRenamer(r, nameMap));
            }
            return r;
        } catch (Exception e) {
            throw Util.sneak(e);
        }
    }

    @Override
    public MappingTree createMappings() {
        return createMojmap();
    }

    @Override
    public FabricLoader getLoader() {
        return new FabricLoader(FabricMaven.URL, FabricMaven.loader("0.12.12"));
    }

    @Override
    public void getModDependencies(ModDependencyCollector d) {
        // noop
    }

}