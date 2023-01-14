import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Practice {

    final List<Integer> STANDARD_US_COINS = List.of(25, 10, 5, 1);

    // binary
    private ArrayList<String> getBinaryNumbers(int digits, ArrayList<String> binaryNumbers) {
        if (digits == 0) return binaryNumbers;

        if (binaryNumbers.isEmpty()) {
            binaryNumbers.add("0");
            binaryNumbers.add("1");
            return getBinaryNumbers(--digits, binaryNumbers);
        } 
        
        ArrayList<String> newBinaryNumbers = new ArrayList<>();
        for (String binaryNumber : binaryNumbers) {
            newBinaryNumbers.add(binaryNumber + "0");
            newBinaryNumbers.add(binaryNumber + "1");
        }
        return getBinaryNumbers(--digits, newBinaryNumbers);
    }
    
    public void printBinary(int digits) {
        for (String binaryNumber : getBinaryNumbers(digits, new ArrayList<String>())) System.out.print(binaryNumber + " ");
        System.out.println();
    }

    // stairs
    private ArrayList<ArrayList<Integer>> getCombinations(int steps, ArrayList<Integer> previous, ArrayList<ArrayList<Integer>> combinations) {
        final int total = previous.stream().mapToInt(Integer::intValue).sum();

        if (total == steps) {
            combinations.add(0, previous);
            return combinations;
        }


        if (total + 2 <= steps) {
            ArrayList<Integer> newPrevious = new ArrayList<>(previous);
            newPrevious.add(2);
            combinations = getCombinations(steps, newPrevious, combinations);
        }

        if (total + 1 <= steps) {
            ArrayList<Integer> newPrevious = new ArrayList<>(previous);
            newPrevious.add(1);
            combinations = getCombinations(steps, newPrevious, combinations);
        }

        return combinations;
    }

    public void climbStairs(int steps) {
        for (ArrayList<Integer> combination : getCombinations(steps, new ArrayList<Integer>(), new ArrayList<ArrayList<Integer>>())) System.out.println(combination);
    }


    // camping
    /**
     * @param previous - a list of directions taken
     * @return a list with x and y coordinates
     */
    private int[] getPosition(ArrayList<String> previous) {
        int x = 0;
        int y = 0;
        for (String direction : previous) switch (direction) {
            case "N":
                y++;
                break;

            case "E":
                x++;
                break;

            default:
                x++;
                y++;
        }
        return new int[] {x, y};
    }

    private ArrayList<ArrayList<String>> getRoutes(int xDestination, int yDestination, ArrayList<String> previous, ArrayList<ArrayList<String>> routes) {
        final int[] position = getPosition(previous);
        final int x, y;
        x = position[0];
        y = position[1];

        if (x == xDestination && y == yDestination) {
            routes.add(previous);
            return routes;
        }

        if (x < xDestination) {
            ArrayList<String> newPrevious = new ArrayList<>(previous);
            newPrevious.add("E");
            routes = getRoutes(xDestination, yDestination, newPrevious, routes);
        }

        if (y < yDestination) {
            ArrayList<String> newPrevious = new ArrayList<>(previous);
            newPrevious.add("N");
            routes = getRoutes(xDestination, yDestination, newPrevious, routes);
        }

        if (x < xDestination && y < yDestination) {
            ArrayList<String> newPrevious = new ArrayList<>(previous);
            newPrevious.add("NE");
            routes = getRoutes(xDestination, yDestination, newPrevious, routes);
        }

        return routes;
    }

    public void campsite(int x, int y) {
        for (ArrayList<String> route : getRoutes(x, y, new ArrayList<String>(), new ArrayList<ArrayList<String>>())) System.out.println(route);
    }

    // max
    private ArrayList<ArrayList<Integer>> getSums(List<Integer> nums, int limit, List<Integer> previous, ArrayList<ArrayList<Integer>> sums) {
        final int total = previous.stream().mapToInt(Integer::intValue).sum();
        final int size = nums.size();

        for (int i = 0; i < size; i++) {
            int num = nums.get(i);

            if (total + num <= limit) {
                ArrayList<Integer> newPrevious = new ArrayList<>(previous);
                newPrevious.add(num);
                sums.add(newPrevious);
                List<Integer> newNums = new ArrayList<Integer>(nums);
                newNums.remove(i);
                sums = getSums(newNums, limit, newPrevious, sums);
        }}

        return sums;
    }

    public int getMax(List<Integer> nums, int limit) {
        ArrayList<ArrayList<Integer>> sums = getSums(nums, limit, new ArrayList<Integer>(), new ArrayList<ArrayList<Integer>>());

        int max = 0;
        for (ArrayList<Integer> sum : sums) {
            int total = sum.stream().mapToInt(Integer::intValue).sum();
            if (total > max) max = total;
        }
        return max;
    }

    // change
    private int getNumberOfChanges(int amount, List<Integer> availableCoins, int previous) {
        if (previous == amount) return 1;

        int combinationNumber = 0;
        final int size = availableCoins.size();
        for (int i = 0; i < size; i++) {
            int coin = availableCoins.get(i);
            if (previous + coin <= amount) combinationNumber += getNumberOfChanges(amount, availableCoins.subList(i, size), previous + coin);
        }
        
        return combinationNumber;
    }

    public int makeChange(int amount) { return getNumberOfChanges(amount, STANDARD_US_COINS, 0); }

    private int getChangeAmount(int[] change) {
        int amount = 0;
        for (int i = 0; i < change.length; i++) amount += change[i] * STANDARD_US_COINS.get(3 - i); // reversed to fit output format
        return amount;
    }

    private List<int[]> getChanges(int amount, List<Integer> availableCoins, List<int[]> changes, int[] previous) {
        int total = getChangeAmount(previous);
        List<int[]> newChanges = new ArrayList<>(changes);

        if (total == amount) {
            newChanges.add(previous);
            return newChanges;
        }

        final int size = availableCoins.size();
        for (int i = 0; i < size; i++) {
            int coin = availableCoins.get(i);
            if (total + coin <= amount) {
                int[] newPrevious = Arrays.copyOf(previous, previous.length);
                newPrevious[3 - STANDARD_US_COINS.indexOf(coin)]++; // reversed for reversed print format
                newChanges = getChanges(amount, availableCoins.subList(i, size), newChanges, newPrevious);
        }}
        
        return newChanges;
    }

    public void printChanges(int amount) {
        System.out.println(" P  N  D  Q");
        System.out.println("------------");
        for (int[] change : getChanges(amount, STANDARD_US_COINS, new ArrayList<int[]>(), new int[] { 0, 0, 0, 0 })) System.out.println(Arrays.toString(change));
    }

    // common substring
    private List<String> getCommonSubsequence(String a, String b, String previous) {
        if (a.length() == 0 || b.length() == 0) return new ArrayList<String>();

        List<String> substrings = new ArrayList<String>();

        for (int i = 0; i < a.length(); i++) {
            char character = a.charAt(i);
            
            if (b.contains(character + "")) {
                String newPrevious = previous + character; 
                substrings.add(newPrevious);

                substrings.addAll(getCommonSubsequence(a.substring(i + 1), b.substring(b.indexOf(character + "")), newPrevious));
        }}

        return substrings;
    }

    public String longestCommonSub(String a, String b) {
        String longest = "";
        for (String substring : getCommonSubsequence(a, b, "")) if (substring.length() > longest.length()) longest = substring;

        return longest;
    }

    public static void main(String[] args) {
        Practice practice = new Practice();

        practice.printBinary(3); // 000 001 010 011 100 101 110 111
        practice.climbStairs(4);
        practice.campsite(2, 1);
        System.out.println(practice.getMax(Arrays.asList(7, 30, 8, 22, 6, 1, 14), 19)); // 16
        practice.makeChange(25); // 13
        practice.makeChange(100); // 242
        practice.printChanges(11);
        System.out.println(practice.longestCommonSub("ABCDEFG", "BGCEHAF")); // BCEF
        System.out.println(practice.longestCommonSub("12345", "54321 21 54321")); // 123
    }
}
