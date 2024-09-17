package tests.builders;

import builders.BuilderFromFiles;
import models.Arena;
import org.junit.Assert;
import org.junit.*;

import java.io.FileNotFoundException;

public class BuilderFromFilesTests extends Assert {
    @Test
    public void buildArena_buildingCorrectArena_dimensionsIsCorrect() {
        BuilderFromFiles builder = new BuilderFromFiles();
        try {
            builder.build();
        } catch(FileNotFoundException e) {
            fail();
        }

        Arena arena = builder.getArena();
        assertEquals(5, arena.getWidth());
        assertEquals(6, arena.getHeight());
    }

    @Test
    public void buildArena_buildingArena_structureIsCorrect() {
        BuilderFromFiles builder = new BuilderFromFiles();
        boolean[][] readability = {
            {false, false, false, false, false},
            {false, false, true, true, false},
            {false, true, true, true, false},
            {false, true, false, true, false},
            {false, true, true, true, false},
            {false, false, false, false, false},
        };
        try {
            builder.build();
        } catch(FileNotFoundException e) {
            fail();
        }
        Arena arena = builder.getArena();
        for (int i = 0; i < arena.getHeight(); i++) {
            for (int j = 0; j < arena.getWidth(); j++) {
                assertEquals(readability[i][j], arena.getTile(j, i).isAccessible());
            }
        }
        //add tests
    }

}
