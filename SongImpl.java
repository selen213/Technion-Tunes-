package OOP.Solution;

import OOP.Provided.Song;
import OOP.Provided.User;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SongImpl implements Song {
    private final int songId;
    private final String songName;
    private final int songLength;
    private final String singerName;
    private final Map<User, Integer> ratings;

    public SongImpl(int id, String name, int length, String singerName) {
        this.songId = id;
        this.songName = name;
        this.songLength = length;
        this.singerName = singerName;
        this.ratings = new HashMap<>();
    }

    @Override
    public int getID() {
        return this.songId;
    }

    @Override
    public String getName() {
        return this.songName;
    }

    @Override
    public int getLength() {
        return this.songLength;
    }

    @Override
    public String getSingerName() {
        return singerName;
    }

    @Override
    public void rateSong(User user, int rate) throws User.IllegalRateValue, User.SongAlreadyRated {
        if (rate < 0 || rate > 10) {
            throw new User.IllegalRateValue();
        }
        if (ratings.containsKey(user)) {
            throw new User.SongAlreadyRated();
        }
        ratings.put(user, rate);
    }

    @Override
    public Collection<User> getRaters() {
        return ratings.entrySet().stream()
                .sorted((e1, e2) -> {
                    int ratingCompare = Integer.compare(e2.getValue(), e1.getValue());
                    if (ratingCompare != 0) return ratingCompare;
                    int ageCompare = Integer.compare(e1.getKey().getAge(), e2.getKey().getAge());
                    if (ageCompare != 0) return ageCompare;
                    return Integer.compare(e2.getKey().getID(), e1.getKey().getID());
                })
                .map(Map.Entry::getKey)
                .toList();
    }

     @Override
    //with lambda
    // public Map<Integer, Set<User>> getRatings() {
    //     Map<Integer, Set<User>> ratingMap = new HashMap<>();
    //     for (Map.Entry<User, Integer> entry : ratings.entrySet()) {
    //         ratingMap.computeIfAbsent(entry.getValue(), k -> new HashSet<>()).add(entry.getKey());
    //     }
    //     return ratingMap;
    // }

    //without lambda 
    public Map<Integer, Set<User>> getRatings() {
        Map<Integer, Set<User>> ratingMap = new HashMap<>();
        for (Map.Entry<User, Integer> entry : ratings.entrySet()) {
            int rating = entry.getValue();
            User user = entry.getKey();
    
            // Check if the rating already exists in the map
            if (!ratingMap.containsKey(rating)) {
                ratingMap.put(rating, new HashSet<>());
            }
    
            // Add the user to the set for the corresponding rating
            ratingMap.get(rating).add(user);
        }
        return ratingMap;
    }
    

    @Override
    public double getAverageRating() {
        return ratings.values().stream()
                .mapToInt(Integer::intValue)
                .average()
                .orElse(0);

    }
//like the tutorial
    @Override
    public int compareTo(Song other) {
        return Integer.compare(this.songId, other.getID());
    }
    @Override
    public boolean equals(Object o) {
        if(o==null)
        {
            return false;
        }
        if (this == o) return true;
        if (!(o instanceof SongImpl)) return false;
        SongImpl song = (SongImpl) o;
        return songId == song.songId;
    }
}