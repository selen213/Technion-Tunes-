package OOP.Solution;
import OOP.Provided.Song;
import OOP.Provided.User;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class UserImpl implements User {
    // Fields
        private final int UserId;
        private final String UserName;
        private final int UserAge;
        private final Map<Song, Integer> ratedSongs; // Songs and their ratings
        private final Map<User,Integer> friends; // Friends and their rated songs count
        // private  int sumOfRaitings; 
        // private  int numOfSongs;   
        // private  int playListTime;  

        // Constructor
        public UserImpl(int id, String name, int age) {
            if (id < 0 || name == null || age < 0) {
                throw new IllegalArgumentException("Invalid parameters for UserImpl constructor");
            }
            this.UserId = id;
            this.UserName = name;
            this.UserAge = age;
            // this.sumOfRaitings = 0;
            // this.numOfSongs = 0;
            // this.playListTime = 0 ;
            this.ratedSongs = new HashMap<>();
            this.friends = new HashMap<>();
    }

    // Implement Methods from User Interface
    @Override
    public int getID() {
        return this.UserId;
    }

    @Override
    public String getName() {
        return this.UserName;
    }

    @Override
    public int getAge() {
        return this.UserAge;
    }

    @Override
    public User rateSong(Song song, int rate) throws IllegalRateValue, SongAlreadyRated {
        if (rate < 0 || rate > 10) {
            throw new IllegalRateValue();
        }
        if (ratedSongs.containsKey(song)) {
            throw new SongAlreadyRated();
        }
        // this.sumOfRaitings += rate;
        // this.numOfSongs += 1;
        // this.playListTime += song.songTime;
        
        ratedSongs.put(song, rate);
        return this;
    }

    @Override
    public double getAverageRating() {
        if (ratedSongs.isEmpty()) {
            return 0;
        }
        return ratedSongs.values().stream().mapToInt(Integer::intValue).average().orElse(0);
    }

    @Override
    public int getPlaylistLength() {
        if (ratedSongs.isEmpty()) {
            return 0;
        }
        return ratedSongs.keySet().stream().mapToInt(Song::getLength).sum();
    }

    @Override
    public Collection<Song> getRatedSongs() {
        return ratedSongs.entrySet().stream()
                .sorted((e1, e2) -> {
                    int rateCompare = Integer.compare(e2.getValue(), e1.getValue());
                    if (rateCompare != 0) return rateCompare;
                    int lengthCompare = Integer.compare(e1.getKey().getLength(), e2.getKey().getLength());
                    if (lengthCompare != 0) return lengthCompare;
                    return Integer.compare(e2.getKey().getID(), e1.getKey().getID());
                })
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Song> getFavoriteSongs() {
        return ratedSongs.entrySet().stream()
                .filter(e -> e.getValue() >= 8)
                .sorted(Comparator.comparingInt(e -> e.getKey().getID()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    @Override
    public User AddFriend(User friend) throws AlreadyFriends, SamePerson {
        if (this.equals(friend)) {
            throw new SamePerson();
        }
        if (friends.containsKey(friend)) {
            throw new AlreadyFriends();
        }
        friends.put(friend, friend.getRatedSongs().size());
        return this;
    }

    @Override
    public boolean favoriteSongInCommon(User user) {
        return this.friends.containsKey(user) && user.getFriends().containsKey(this)
        &&this.getFavoriteSongs().stream().anyMatch(song -> user.getFavoriteSongs().contains(song));
    }

    @Override
    public Map<User, Integer> getFriends() {
        return new HashMap<>(friends); // Return a shallow copy to maintain encapsulation
    }

    @Override
    public int compareTo(User user) {
        return Integer.compare(this.UserId, user.getID());
    }

    @Override
    public boolean equals(Object o) {
        if(o==null)
        {
            return false;
        }
        if (this == o) return true;
        if (!(o instanceof UserImpl)) return false;
        UserImpl user = (UserImpl) o;
        return UserId == user.UserId;
    }

    //in tutorial we did this:
    // @Override
    // public boolean equals(Object o) {
    //     if (!(o instanceof UserImpl)) return false;
    //     UserImpl user = (UserImpl) o;
    //     return UserId==(user.getID());
    // }

    @Override
    public int hashCode() {
        return Objects.hash(UserId);
    }
}