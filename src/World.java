import java.util.Arrays;

/**
 * World will serve as
 * 
 * World will also instantiate the memory and pass it to the Hash class.
 * 
 * @author Jesus
 * @version 1
 */
public class World {
    // ~ Fields
    private Hash artistH;
    private Hash songH;

    private byte[] buffer;
    private byte[] bufferTwo;

    private MemoryPool memPool;

    // ~ Constructors

    /**
     * Initializes the the databases.
     * 
     * @param hashSize
     *            size of table
     * @param blockSize
     *            of mem pool
     */

    public World(int hashSize, int blockSize) {
        artistH = new Hash(hashSize);
        songH = new Hash(hashSize);
        memPool = new MemoryPool(blockSize);

    }

    // ~Public Methods ........................................................

    /**
     * @param str1
     *            artist
     * @param str2
     *            song
     */
    public void insert(String str1, String str2) {

        if (insertArtist(str1)) {
            System.out
                    .println("|" + str1 + "| is added to the artist database.");
        }
        else {
            System.out.println("|" + str1
                    + "| duplicates a record already in the artist database.");
        }

        if (insertSong(str2)) {
            System.out.println("|" + str2 + "| is added to the song database.");
        }
        else {
            System.out.println("|" + str2
                    + "| duplicates a record already in the song database.");
        }

    }

    /**
     * inserts artist, then song
     * 
     * @param str1
     *            String to insert
     * @return True if insert succeeded, false else
     */
    public boolean insertArtist(String str1) {
        Handle artistHandle;
        buffer = str1.getBytes();
        if (artistExistsInPool(str1)) {
            return false;
        }
        if (artistH.numOfElts() == artistH.arrSize() / 2) {
            artistH.replaceTableTwo(memPool);
            System.out.println("Artist hash table size doubled.");

        }

        int slot = artistH.h(str1, artistH.arrSize());
        int probe = slot;
        int i = 1;
        while (artistH.getHandle(probe) != null) {
            // We want to use this position since its "empty"
            if (artistH.getHandle(probe).isTombstone) {
                break;
            }
            probe = (slot + (i * i)) % artistH.arrSize();
            i++;
        }
        artistHandle = memPool.insert(buffer, buffer.length);
        artistH.addHandle(artistHandle, probe);

        return true;
    }

    /**
     * Use to insert a song
     * 
     * @param str2
     *            String of song name to insert
     * @return True if inserted, false else
     */
    public boolean insertSong(String str2) {
        Handle songHandle;
        buffer = str2.getBytes();
        if (songExistsInPool(str2)) {
            return false;
        }
        if (songH.numOfElts() == songH.arrSize() / 2) {

            songH.replaceTableTwo(memPool);
            System.out.println("Song hash table size doubled.");

        }

        int slot = songH.h(str2, songH.arrSize());

        int probe = slot;
        int i = 1;
        while (songH.getHandle(probe) != null) {
            // We want to use this position since its "empty"
            if (songH.getHandle(probe).isTombstone) {
                break;
            }
            probe = (slot + (i * i)) % songH.arrSize();
            i++;
        }

        songHandle = memPool.insert(buffer, buffer.length);
        songH.addHandle(songHandle, probe);

        return true;
    }

    /**
     * See return
     * 
     * @param str
     *            Artist name
     * @return True if already in pool, false else
     */
    public boolean artistExistsInPool(String str) {

        buffer = str.getBytes();

        int homeSlot = artistH.h(str, artistH.arrSize());
        int probe = homeSlot;
        int i = 1;
        while (artistH.getHandle(probe) != null) {

            // bufferTwo = memPool.getBytes(artistH.getHandle(probe));

            if (!artistH.getHandle(probe).isTombstone) {
                bufferTwo = memPool.getBytes(artistH.getHandle(probe));

                if (Arrays.equals(buffer, bufferTwo)) {
                    return true;
                }
            }

            probe = (homeSlot + (i * i)) % artistH.arrSize();
            i++;

        }
        return false;
    }

    /**
     * See return
     * 
     * @param str
     *            Song name
     * @return True if song is in pool, false else
     */
    public boolean songExistsInPool(String str) {
        buffer = str.getBytes();

        int homeSlot = songH.h(str, songH.arrSize());
        int probe = homeSlot;
        int i = 1;
        while (songH.getHandle(probe) != null && probe <= songH.arrSize() - 1) {

            // bufferTwo = new byte[str.length()];

            if (!songH.getHandle(probe).isTombstone) {
                bufferTwo = memPool.getBytes(songH.getHandle(probe));

                if (Arrays.equals(buffer, bufferTwo)) {
                    return true;
                }
            }
            probe = (homeSlot + (i * i)) % songH.arrSize();
            i++;
        }
        return false;
    }

    /**
     * See return
     * 
     * @param str
     *            Song name
     * @return position of slot
     */
    public int getSongSlot(String str) {
        int slot = -1;
        buffer = str.getBytes();

        int homeSlot = songH.h(str, songH.arrSize());
        int probe = homeSlot;
        int i = 1;
        while (songH.getHandle(probe) != null) {

            // bufferTwo = new byte[str.length()];

            if (!songH.getHandle(probe).isTombstone) {
                bufferTwo = memPool.getBytes(songH.getHandle(probe));

                if (Arrays.equals(buffer, bufferTwo)) {
                    slot = probe;
                }
            }
            probe = (homeSlot + (i * i)) % songH.arrSize();
            i++;
        }
        return slot;
    }

    /**
     * See return
     * 
     * @param str
     *            name of artist
     * @return slot artist is located in
     */
    public int getArtistSlot(String str) {
        int slot = -1;
        byte[] localBuffer = str.getBytes();
        byte[] localBuffer2;

        int homeSlot = artistH.h(str, artistH.arrSize());
        int probe = homeSlot;
        int i = 1;
        while (artistH.getHandle(probe) != null) {

            // bufferTwo = memPool.getBytes(artistH.getHandle(probe));

            if (!artistH.getHandle(probe).isTombstone) {
                localBuffer2 = memPool.getBytes(artistH.getHandle(probe));

                if (Arrays.equals(localBuffer, localBuffer2)) {
                    slot = probe;
                }
            }

            probe = (homeSlot + (i * i)) % artistH.arrSize();
            i++;

        }
        return slot;
    }

    /**
     * Removes the artist
     * 
     * @param str
     *            Name of artist
     */
    public void removeArtist(String str) {

        if (!artistExistsInPool(str)) {
            System.out.println(
                    "|" + str + "| does not exist in the artist database.");
        }
        else {
            int tombstoneSlot = getArtistSlot(str);
            Handle handleRemoved = artistH.getHandle(tombstoneSlot);
            memPool.remove(handleRemoved);
            artistH.remove(tombstoneSlot);
            System.out.println(
                    "|" + str + "| is removed from the artist database.");
        }
    }

    /**
     * Removes the song
     * 
     * @param str
     *            Name of song
     */
    public void removeSong(String str) {
        if (!songExistsInPool(str)) {
            System.out.println(
                    "|" + str + "| does not exist in the song database.");
        }
        else {
            int tombstoneSlot = getSongSlot(str);
            Handle handleRemoved = songH.getHandle(tombstoneSlot);
            memPool.remove(handleRemoved);
            songH.remove(tombstoneSlot);
            System.out.println(
                    "|" + str + "| is removed from the song database.");
        }
    }

    /**
     * Prints artists to std out
     */
    public void printArtists() {

        for (int i = 0; i < artistH.arrSize(); i++) {
            if (artistH.getHandle(i) != null
                    && !artistH.getHandle(i).isTombstone)

            {

                ///// retrieve from pool and convert it string

                buffer = memPool.getBytes(artistH.getHandle(i));
                String s = new String(buffer);
                System.out.println("|" + s + "| " + i);

            }

        }
        System.out.println("total artists: " + artistH.numOfElts());
    }

    /**
     * Returns the artist hash table
     * @return
     *      The artist hash table
     */
    public Hash getArtistHash() {
        return artistH;
    }

    /**
     * Returns the song hash table
     * @return
     *      The song hash table
     */
    public Hash getSongHash() {
        return songH;
    }

    /**
     * Prints songs to std out
     */
    public void printSongs() {
        for (int i = 0; i < songH.arrSize(); i++) {
            if (songH.getHandle(i) != null && !songH.getHandle(i).isTombstone) {
                buffer = memPool.getBytes(songH.getHandle(i));
                String s = new String(buffer);
                System.out.println("|" + s + "| " + i);
            }

        }
        System.out.println("total songs: " + songH.numOfElts());

    }

    /**
     * prints memory block
     */
    public void printBlocks() {
        System.out.println(memPool.dump());
    }

}
