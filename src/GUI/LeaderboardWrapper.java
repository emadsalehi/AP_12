package GUI;

import java.util.*;

public class LeaderboardWrapper {
    private HashMap<String, Integer> leaderboardHashMap;

    public void sortByValue() {
        leaderboardHashMap = sortByValue(leaderboardHashMap);
    }

    public HashMap<String, Integer> getLeaderboardHashMap() {
        return leaderboardHashMap;
    }

    public LeaderboardWrapper(HashMap<String, Integer> leaderboardHashMap) {
        this.leaderboardHashMap = leaderboardHashMap;
    }

    public void setLeaderboardHashMap(HashMap<String, Integer> leaderboardHashMap) {
        this.leaderboardHashMap = leaderboardHashMap;
    }

    public void sortByKey() {
        TreeMap<Integer,String> treeMap = new TreeMap<Integer,String>(
                new Comparator<Integer>() {
                    @Override
                    public int compare(Integer o1, Integer o2) {
                        return o2.compareTo(o1);//sort in descending order
                    }
                });
        leaderboardHashMap.putAll(new TreeMap<String,Integer>(leaderboardHashMap));

    }

    public static HashMap<String, Integer> sortByValue(HashMap<String, Integer> hm)
    {
        // Create a list from elements of HashMap
        List<Map.Entry<String, Integer> > list =
                new LinkedList<Map.Entry<String, Integer> >(hm.entrySet());

        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<String, Integer> >() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2)
            {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        // put data from sorted list to hashmap
        HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }
}
