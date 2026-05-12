package OOP.Solution;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

import OOP.Provided.Song;
import OOP.Provided.TechnionTunes;
import OOP.Provided.User;
import OOP.Provided.User.AlreadyFriends;
import OOP.Provided.User.IllegalRateValue;
import OOP.Provided.User.SamePerson;
import OOP.Provided.User.SongAlreadyRated;


public class TechnionTunesImpl implements TechnionTunes {

    private final Map<Integer, User> tunesUsers;
    private final Map<Integer, Song> tunesSongs;
    private final Map<Integer, Set<Integer>> friendships;

    public TechnionTunesImpl() {
        this.tunesUsers = new HashMap<>();
        this.tunesSongs = new HashMap<>();
        this.friendships = new HashMap<>();
    }

    @Override
    public void addUser(int userID, String userName, int userAge) throws UserAlreadyExists {
        if (tunesUsers.containsKey(userID)) {
            throw new UserAlreadyExists();
        }
        User newUser = new UserImpl(userID, userName, userAge);
        tunesUsers.put(userID, newUser);
        friendships.put(userID, new HashSet<>());
    }

    @Override
    public User getUser(int id) throws UserDoesntExist {
        if (!tunesUsers.containsKey(id)) {
            throw new UserDoesntExist();
        }
        return tunesUsers.get(id);
    }

    @Override
    public void makeFriends(int id1, int id2) throws UserDoesntExist, AlreadyFriends, SamePerson {
        if (!tunesUsers.containsKey(id1) || !tunesUsers.containsKey(id2)) {
            throw new UserDoesntExist();
        }
        if (id1 == id2) {
            throw new SamePerson();
        }
        if (friendships.get(id1).contains(id2) ||friendships.get(id2).contains(id1) ) {
            throw new AlreadyFriends();
        }
        friendships.get(id1).add(id2);
        friendships.get(id2).add(id1);
        
        //addFriend to the user map and the user's friend map
        //tunesUsers.get(id1).AddFriend(getUser(id2));
        //tunesUsers.get(id2).AddFriend(getUser(id1));
    }

    @Override
    public void addSong(int songID, String songName, int length, String singerName) throws SongAlreadyExists {
        if (tunesSongs.containsKey(songID)) {
            throw new SongAlreadyExists();
        }
        Song newSong = new SongImpl(songID, songName, length, singerName);
        tunesSongs.put(songID, newSong);
    }

    @Override
    public Song getSong(int id) throws SongDoesntExist {
        if (!tunesSongs.containsKey(id)) {
            throw new SongDoesntExist();
        }
        return tunesSongs.get(id);
    }

    @Override
    public void rateSong(int userId, int songId, int rate) throws UserDoesntExist, SongDoesntExist, IllegalRateValue, SongAlreadyRated {
        if (!tunesUsers.containsKey(userId)) {
            throw new UserDoesntExist();
        }
        if (!tunesSongs.containsKey(songId)) {
            throw new SongDoesntExist();
        }
        //what they mean lo hoki
        if(rate < 0){
            throw new IllegalRateValue();
        }
        Song song = tunesSongs.get(songId);
        User user = tunesUsers.get(userId);
        //dunno if needed
        user.rateSong(song, rate);
        song.rateSong(user, rate);
    }

    @Override
    public Set<Song> getIntersection(int[] IDs) throws UserDoesntExist {
        Set<Song> intersection = null;

        for (int id : IDs) {
            if (!tunesUsers.containsKey(id)) {
                throw new UserDoesntExist();
            }
            User user = tunesUsers.get(id);
            Set<Song> ratedSongs = new HashSet<>(user.getRatedSongs());

            if (intersection == null) {
                intersection = ratedSongs;
            } else {
                intersection.retainAll(ratedSongs);
            }
        }
        return intersection == null ? new HashSet<>() : intersection;
    }

    @Override
    public Collection<Song> sortSongs(Comparator<Song> comp) {
        return tunesSongs.values().stream()
                .sorted(comp)
                .collect(Collectors.toList());
    }

    // @Override
    // public Collection<Song> getHighestRatedSongs(int num) {
    //     return tunesSongs.values().stream()
    //             .sorted(Comparator.comparingDouble(Song::getAverageRating).reversed()
    //                     .thenComparingInt(Song::getLength).reversed()
    //                     .thenComparingInt(Song::getID))
    //             .limit(num)
    //             .collect(Collectors.toList());
    // }
    @Override
    public Collection<Song> getHighestRatedSongs(int num) {
        return tunesSongs.values().stream()
            .sorted((s1, s2) -> {
                // Compare by average rating (higher to lower)
                int avgRatingComparison = Double.compare(s2.getAverageRating(), s1.getAverageRating());
                if (avgRatingComparison != 0) {
                    return avgRatingComparison;
                }

                // Compare by length (higher to lower)
                int lengthComparison = Integer.compare(s2.getLength(), s1.getLength());
                if (lengthComparison != 0) {
                    return lengthComparison;
                }

                // Compare by ID (lower to higher)
                return Integer.compare(s1.getID(), s2.getID());
            })
            .limit(num)
            .collect(Collectors.toList());
}


    @Override
    public Collection<Song> getMostRatedSongs(int num) {
        return tunesSongs.values().stream()
            .sorted((s1, s2) -> {
                // Compare by average rating (higher to lower)
                int ratingNumComparison = Double.compare(s2.getRaters().size(), s1.getRaters().size());
                if (ratingNumComparison != 0) {
                    return ratingNumComparison;
                }

                // Compare by length (higher to lower)
                int lengthComparison = Integer.compare(s1.getLength(), s2.getLength());
                if (lengthComparison != 0) {
                    return lengthComparison;
                }

                // Compare by ID (lower to higher)
                return Integer.compare(s2.getID(), s1.getID());
            })
            .limit(num)
            .collect(Collectors.toList());
    }

//didnt check it assumed its right
    @Override
    public Collection<User> getTopLikers(int num) {
        return tunesUsers.values().stream()
                .sorted(Comparator.comparingDouble(User::getAverageRating)
                        .thenComparingInt(User::getAge).reversed()
                        .thenComparingInt(User::getID))
                .limit(num)
                .collect(Collectors.toList());
    }



    @Override
    public boolean canGetAlong(int userId1, int userId2) throws UserDoesntExist {
        if (!tunesUsers.containsKey(userId1) || !tunesUsers.containsKey(userId2)) {
            throw new UserDoesntExist();
        }
        if (userId1 == userId2) {
            return true; // A user always gets along with themselves.
        }

        // Check if they are direct friends with a shared favorite song.
        User user1 = tunesUsers.get(userId1);
        User user2 = tunesUsers.get(userId2);

        if (friendships.getOrDefault(userId1, new HashSet<>()).contains(userId2)) {
            // Check for shared favorite song.
            boolean directSharedFavorite = user1.getFavoriteSongs().stream()
                    .anyMatch(song -> user2.getFavoriteSongs().contains(song));
            if (directSharedFavorite) {
                return true;
            }
        }

        // Perform BFS to find a valid path of friends.
        Set<Integer> visited = new HashSet<>();
        Queue<Integer> queue = new LinkedList<>();
        queue.add(userId1);

        while (!queue.isEmpty()) {
            int current = queue.poll();
            visited.add(current);

            for (int friendId : friendships.getOrDefault(current, new HashSet<>())) {
                if (!visited.contains(friendId)) {
                    User currentUser = tunesUsers.get(current);
                    User friendUser = tunesUsers.get(friendId);

                    // Check if they can "get along" based on a shared favorite song.
                    boolean sharedFavorite = currentUser.getFavoriteSongs()
                            .stream()
                            .anyMatch(friendUser.getFavoriteSongs()::contains);

                    if (sharedFavorite) {
                        if (friendId == userId2) {
                            return true; // Found a path to userId2.
                        }
                        queue.add(friendId); // Add to queue for further exploration.
                    }
                }
            }
        }
        return false; // No valid path found
    }

    @Override
    public Iterator<Song> iterator() {
        return tunesSongs.values().iterator();
    }
}
