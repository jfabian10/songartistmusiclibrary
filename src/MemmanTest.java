import java.io.IOException;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.containsString;

import student.TestCase;

/**
 * @author {Your Name Here}
 * @version {Put Something Here}
 */
public class MemmanTest extends TestCase {
    /**
     * Get code coverage of the class declaration.
     * 
     * @throws IOException
     */
    public void testMInit() throws IOException {

        Memman mem = new Memman();
        mem.main(new String[] { "10", "32", "DummyTextTwo.txt" });

        // Memman.main(new String[] {"10", "32", "DummyTextTwo.txt"});

        String firstArtist = "|Drake| is added to the artist database.\n";
        String firstSong = "|I'm Going In| is added to the song database.\n";
        String sameArtist = "|Drake| duplicates a record "
                + "already in the artist database.\n";

        String song = "|Too Good| is added to the song database.\n";
        String printArtist = "total artists: 2\n";
        String artistLoc = "|Drake| 7\n";
        String printSongs = "total songs: 3\n";
        String songLoc = "|I'm Going In| 3\n";

        String output = systemOut().getHistory();
        assertThat(output, containsString(firstArtist));
        assertThat(output, containsString(firstSong));
        assertThat(output, containsString(sameArtist));
        assertThat(output, containsString(song));
        assertThat(output, containsString("Invalid line\n"));

        assertThat(output, containsString("|I'm Going In| duplicates a record "
                + "already in the song database.\n"));

        assertThat(output, containsString(printArtist));
        assertThat(output, containsString(artistLoc));
        assertThat(output, containsString(printSongs));
        assertThat(output, containsString(songLoc));

        assertThat(output, containsString("Can't print object\n"));

        //// now we attempt to remove

        String artNotFound = "|Meek Mill| does not exist "
                + "in the artist database.\n";
        String songNotFound = "|Forever| does not exist "
                + "in the song database.\n";
        String succArtRem = "|Drake| is removed from the artist database.\n";
        String succSongRem = "|Under Pressure| is removed "
                + "from the song database.\n";

        assertThat(output, containsString(artNotFound));
        assertThat(output, containsString(songNotFound));
        assertThat(output, containsString(succArtRem));
        assertThat(output, containsString(succSongRem));

        assertThat(output, containsString("Can't remove object\n"));
        assertThat(output, containsString("Invalid line\n"));
        assertThat(output, containsString("Can't print object\n"));

    }

    /**
     * Tests main method with bad input
     * 
     * @throws IOException
     *             This is expected
     */
    public void testMainBadArguments() throws IOException {
        Memman.main(new String[] { "10", "45" });
        String output = systemOut().getHistory();
        assertEquals("Error\n", output);
    }

    /**
     * @throws IOException
     */

    public void testPrintBlocks() throws IOException {
        Memman.main(new String[] { "10", "32", "P1sampleInput.txt" });
        String output = systemOut().getHistory();
        assertThat(output, containsString("(44,11) -> (121,4) -> (319,1)\n"));

    }

}
