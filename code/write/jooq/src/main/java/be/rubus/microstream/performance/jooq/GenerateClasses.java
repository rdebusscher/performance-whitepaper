package be.rubus.microstream.performance.jooq;

import org.jooq.codegen.GenerationTool;

import java.nio.file.Files;
import java.nio.file.Path;

public class GenerateClasses {

    public static void main(String[] args) throws Exception {
        GenerationTool.generate(
                Files.readString(
                        Path.of("jooq/src/main/resources/jooq-config.xml")
                )
        );
    }
}
