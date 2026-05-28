import java.util.ArrayList;

public class ZoneMapSimulation {

    static final int TOTAL_BLOCKS = 100000;

    static class Block {

        long minTimestamp;
        long maxTimestamp;

        Block(long min, long max) {

            this.minTimestamp = min;
            this.maxTimestamp = max;
        }

        boolean overlaps(
                long queryMin,
                long queryMax) {

            return !(maxTimestamp < queryMin ||
                     minTimestamp > queryMax);
        }
    }

    static ArrayList<Block> zoneMap =
            new ArrayList<>();

    static void buildZoneMap() {

        long timestamp = 1;

        for(int i = 0;
            i < TOTAL_BLOCKS;
            i++) {

            long blockMin =
                    timestamp;

            long blockMax =
                    timestamp + 9999;

            zoneMap.add(
                new Block(
                    blockMin,
                    blockMax
                )
            );

            timestamp += 10000;
        }
    }

    static int queryBlocks(
            long queryStart,
            long queryEnd) {

        int blocksRead = 0;

        for(Block block : zoneMap) {

            if(block.overlaps(
                    queryStart,
                    queryEnd)) {

                blocksRead++;
            }
        }

        return blocksRead;
    }

    public static void main(
            String[] args) {

        buildZoneMap();

        long queryStart =
                100000000;

        long queryEnd =
                180000000;

        int blocksRead =
                queryBlocks(
                    queryStart,
                    queryEnd
                );

        long storage =
                TOTAL_BLOCKS * 16;

        System.out.println(
        "Total Blocks: "
        + TOTAL_BLOCKS);

        System.out.println(
        "Blocks Read: "
        + blocksRead);

        System.out.println(
        "Blocks Skipped: "
        + (TOTAL_BLOCKS
           - blocksRead));

        System.out.println(
        "Zone Map Storage: "
        + storage
        + " bytes");
    }
}