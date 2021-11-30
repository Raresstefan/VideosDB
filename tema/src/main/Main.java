package main;
import actor.ActorsAwards;
import checker.Checkstyle;
import checker.Checker;
import common.Constants;
import fileio.ActionInputData;
import fileio.ActorInputData;
import fileio.Input;
import fileio.InputLoader;
import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.UserInputData;
import fileio.Writer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * The entry point to this homework. It runs the checker that tests your implentation.
 */
public final class Main {
    /**
     * searches a user by it's name in the list of users
     */
    public static User searchUser(final List<User> users, final String username) {
        for (User currUser: users) {
            if (currUser.getUsername().equals(username)) {
                return currUser;
            }
        }
        return null;
    }
    /**
     * searches a serial by it's name in the list of series
     */
    public static Series searchSeries(final List<Series> series, final String title) {
        for (Series currSeries: series) {
            if (currSeries.getTitle().equals(title)) {
                return currSeries;
            }
        }
        return null;
    }
    /**
     * searches a movie by it's name in the list of movies
     */
    public static Movie searchMovie(final List<Movie> movies, final String title) {
        for (Movie currMovie: movies) {
            if (currMovie.getTitle().equals(title)) {
                return currMovie;
            }
        }
        return null;
    }
    /**
     * Populate the actors list with the values from input
     */
    public static List<Actor> populateActors(final List<ActorInputData> actorsFromInput) {
        List<Actor> actors = new ArrayList<>();
        actorsFromInput.forEach((actor) -> {
            Actor newActor = new Actor(actor);
            actors.add(newActor);
        });
        return actors;
    }
    /**
     * Populate the users list with the values from input
     */
    public static List<User> populateUsers(final List<UserInputData> usersFromInput) {
        List<User> users = new ArrayList<>();
        for (UserInputData user: usersFromInput) {
            User newUser = new User(user.getUsername(), user.getSubscriptionType(),
                    user.getHistory(), user.getFavoriteMovies());
            users.add(newUser);
        }
        return users;
    }
    /**
     * Populate the series list with the values from input
     */
    public static List<Series> populateSeries(final List<SerialInputData> seriesFromInput) {
        List<Series> series = new ArrayList<>();
        seriesFromInput.forEach((serial) -> {
            Series newSeries = new Series(serial.getTitle(), serial.getCast(), serial.getGenres(),
                    serial.getNumberSeason(), serial.getSeasons(), serial.getYear());
            series.add(newSeries);
        });
        return series;
    }
    /**
     * Populate the movies list with the values from input
     */
    public static List<Movie> populateMovies(final List<MovieInputData> moviesFromInput) {
        List<Movie> movies = new ArrayList<>();
        moviesFromInput.forEach((movie) -> {
            Movie newMovie = new Movie(movie.getTitle(), movie.getCast(), movie.getGenres(),
                    movie.getYear(), movie.getDuration());
            movies.add(newMovie);
        });
        return movies;
    }
    /**
     * Sort the list of actors by their average grade in ascending order
     * the second criteria is their name
     */
    public static void sortActorsByAverageAsc(final List<Actor> actors) {
        int i, j;
        for (i = 0; i < actors.size() - 1; i++) {
            for (j = i + 1; j < actors.size(); j++) {
                if (actors.get(i).getAverageGrade() > actors.get(j).getAverageGrade()) {
                    Collections.swap(actors, i, j);
                } else if
                (actors.get(i).getAverageGrade().equals(actors.get(j).getAverageGrade())) {
                    if (actors.get(i).getName().compareTo(actors.get(j).getName()) > 0) {
                        Collections.swap(actors, i, j);
                    }
                }
            }
        }
    }
    /**
     * Sort the list of actors by their average grade in descending order
     * the second criteria is their name
     */
    public static void sortActorsByAverageDesc(final List<Actor> actors) {
        int i, j;
        for (i = 0; i < actors.size() - 1; i++) {
            for (j = i + 1; j < actors.size(); j++) {
                if (actors.get(i).getAverageGrade() < actors.get(j).getAverageGrade()) {
                    Collections.swap(actors, i, j);
                } else if
                (actors.get(i).getAverageGrade().equals(actors.get(j).getAverageGrade())) {
                    if (actors.get(i).getName().compareTo(actors.get(j).getName()) < 0) {
                        Collections.swap(actors, i, j);
                    }
                }
            }
        }
    }
    /**
     * Sort the list of actors by their total number of awards in ascending order
     * the second criteria is their name
     */
    public static void sortActorsByAwardsAsc(final List<Actor> actors) {
        int i, j;
        for (i = 0; i < actors.size() - 1; i++) {
            for (j = i + 1; j < actors.size(); j++) {
                if
                (actors.get(i).getTotalNumberOfAwards() > actors.get(j).getTotalNumberOfAwards()) {
                    Collections.swap(actors, i, j);
                } else if
                (actors.get(i).getTotalNumberOfAwards().
                                equals(actors.get(j).getTotalNumberOfAwards())) {
                    if (actors.get(i).getName().compareTo(actors.get(j).getName()) > 0) {
                        Collections.swap(actors, i, j);
                    }
                }
            }
        }
    }
    /**
     * Sort the list of actors by their total number of awards in descending order
     * the second criteria is their name
     */
    public static void sortActorsByAwardsDesc(final List<Actor> actors) {
        int i, j;
        for (i = 0; i < actors.size() - 1; i++) {
            for (j = i + 1; j < actors.size(); j++) {
                if (actors.get(i).getTotalNumberOfAwards()
                        <
                        actors.get(j).getTotalNumberOfAwards()) {
                    Collections.swap(actors, i, j);
                } else if
                (actors.get(i).getTotalNumberOfAwards().
                                equals(actors.get(j).getTotalNumberOfAwards())) {
                    if (actors.get(i).getName().compareTo(actors.get(j).getName()) < 0) {
                        Collections.swap(actors, i, j);
                    }
                }
            }
        }
    }
    /**
     * Sort the list of actors by their names in Lexicographic order
     * the second criteria doesn't exist
     */
    public static void sortActorsAlphabetically(final List<Actor> actors) {
        int i, j;
        for (i = 0; i < actors.size() - 1; i++) {
            for (j = i + 1; j < actors.size(); j++) {
                if (actors.get(i).getName().compareTo(actors.get(j).getName()) > 0) {
                    Collections.swap(actors, i, j);
                }
            }
        }
    }
    /**
     * Sort the list of movies by their average rating in ascending order
     * the second criteria is their titles
     */
    public static void sortMoviesByRatingAsc(final List<Movie> movies) {
        int i, j;
        for (i = 0; i < movies.size() - 1; i++) {
            for (j = i + 1; j < movies.size(); j++) {
                if (movies.get(i).getAverageRating() > movies.get(j).getAverageRating()) {
                    Collections.swap(movies, i, j);
                } else if
                (movies.get(i).getAverageRating().equals(movies.get(j).getAverageRating())) {
                    if (movies.get(i).getTitle().compareTo(movies.get(j).getTitle()) > 0) {
                        Collections.swap(movies, i, j);
                    }
                }
            }
        }
    }
    /**
     * Sort the list of movies by their average rating in descending order
     * the second criteria is their titles
     */
    public static void sortMoviesByRatingDesc(final List<Movie> movies) {
        int i, j;
        for (i = 0; i < movies.size() - 1; i++) {
            for (j = i + 1; j < movies.size(); j++) {
                if (movies.get(i).getAverageRating() > movies.get(j).getAverageRating()) {
                    Collections.swap(movies, i, j);
                } else if
                (movies.get(i).getAverageRating().equals(movies.get(j).getAverageRating())) {
                    if (movies.get(i).getTitle().compareTo(movies.get(j).getTitle()) < 0) {
                        Collections.swap(movies, i, j);
                    }
                }
            }
        }
    }
    /**
     * Sort the list of series by their average rating in ascending order
     * the second criteria is their titles
     */
    public static void sortSeriesByRatingAsc(final List<Series> series) {
        int i, j;
        for (i = 0; i < series.size() - 1; i++) {
            for (j = i + 1; j < series.size(); j++) {
                if (series.get(i).getAverageRating()
                        >
                        series.get(j).getAverageRating()) {
                    Collections.swap(series, i, j);
                } else if
                (series.get(i).getAverageRating().equals(series.get(j).getAverageRating())) {
                    if (series.get(i).getTitle().compareTo(series.get(j).getTitle()) > 0) {
                        Collections.swap(series, i, j);
                    }
                }
            }
        }
    }
    /**
     * Sort the list of series by their average rating in descending order
     * the second criteria is their titles
     */
    public static void sortSeriesByRatingDesc(final List<Series> series) {
        int i, j;
        for (i = 0; i < series.size() - 1; i++) {
            for (j = i + 1; j < series.size(); j++) {
                if (series.get(i).getAverageRating()
                        <
                        series.get(j).getAverageRating()) {
                    Collections.swap(series, i, j);
                } else if (series.get(i).getAverageRating().
                        equals(series.get(j).getAverageRating())) {
                    if (series.get(i).getTitle().compareTo(series.get(j).getTitle()) < 0) {
                        Collections.swap(series, i, j);
                    }
                }
            }
        }
    }
    /**
     * Sort the list of movies by their occurences in favorite lists of users, in ascending order
     * the second criteria is their titles
     */
    public static void sortMoviesByFavoriteAsc(final List<Movie> movies) {
        int i, j;
        for (i = 0; i < movies.size() - 1; i++) {
            for (j = i + 1; j < movies.size(); j++) {
                if (movies.get(i).getFavoriteOccurences()
                        >
                        movies.get(j).getFavoriteOccurences()) {
                    Collections.swap(movies, i, j);
                } else if (movies.get(i).getFavoriteOccurences()
                        ==
                        movies.get(j).getFavoriteOccurences()) {
                    if (movies.get(i).getTitle().compareTo(movies.get(j).getTitle()) > 0) {
                        Collections.swap(movies, i, j);
                    }
                }
            }
        }
    }
    /**
     * Sort the list of movies by their occurences in favorite lists of users, in descending order
     * the second criteria is their titles
     */
    public static void sortMoviesByFavoriteDesc(final List<Movie> movies) {
        int i, j;
        for (i = 0; i < movies.size() - 1; i++) {
            for (j = i + 1; j < movies.size(); j++) {
                if (movies.get(i).getFavoriteOccurences()
                        <
                        movies.get(j).getFavoriteOccurences()) {
                    Collections.swap(movies, i, j);
                } else if (movies.get(i).getFavoriteOccurences()
                        ==
                        movies.get(j).getFavoriteOccurences()) {
                    if (movies.get(i).getTitle().compareTo(movies.get(j).getTitle()) < 0) {
                        Collections.swap(movies, i, j);
                    }
                }
            }
        }
    }
    /**
     * Sort the list of series by their occurences in favorite lists of users, in ascending order
     * the second criteria is their titles
     */
    public static void sortSeriesByFavoriteAsc(final List<Series> series) {
        int i, j;
        for (i = 0; i < series.size() - 1; i++) {
            for (j = i + 1; j < series.size(); j++) {
                if (series.get(i).getFavoriteOccurences()
                        >
                        series.get(j).getFavoriteOccurences()) {
                    Collections.swap(series, i, j);
                } else if (series.get(i).getFavoriteOccurences()
                        ==
                        series.get(j).getFavoriteOccurences()) {
                    if (series.get(i).getTitle().compareTo(series.get(j).getTitle()) > 0) {
                        Collections.swap(series, i, j);
                    }
                }
            }
        }
    }
    /**
     * Sort the list of series by their occurences in favorite lists of users, in descending order
     * the second criteria is their titles
     */
    public static void sortSeriesByFavoriteDesc(final List<Series> series) {
        int i, j;
        for (i = 0; i < series.size() - 1; i++) {
            for (j = i + 1; j < series.size(); j++) {
                if (series.get(i).getFavoriteOccurences() < series.get(j).getFavoriteOccurences()) {
                    Collections.swap(series, i, j);
                } else if (series.get(i).getFavoriteOccurences()
                        ==
                        series.get(j).getFavoriteOccurences()) {
                    if (series.get(i).getTitle().compareTo(series.get(j).getTitle()) < 0) {
                        Collections.swap(series, i, j);
                    }
                }
            }
        }
    }
    /**
     * Sort the list of movies by their duration in ascending order
     * the second criteria is their titles
     */
    public static void sortMoviesByLongestAsc(final List<Movie> movies) {
        int i, j;
        for (i = 0; i < movies.size() - 1; i++) {
            for (j = i + 1; j < movies.size(); j++) {
                if (movies.get(i).getDuration() > movies.get(j).getDuration()) {
                    Collections.swap(movies, i, j);
                } else if (movies.get(i).getDuration() == movies.get(j).getDuration()) {
                    if (movies.get(i).getTitle().compareTo(movies.get(j).getTitle()) > 0) {
                        Collections.swap(movies, i, j);
                    }
                }
            }
        }
    }
    /**
     * Sort the list of movies by their duration in descending order
     * the second criteria is their titles
     */
    public static void sortMoviesByLongestDesc(final List<Movie> movies) {
        int i, j;
        for (i = 0; i < movies.size() - 1; i++) {
            for (j = i + 1; j < movies.size(); j++) {
                if (movies.get(i).getDuration() < movies.get(j).getDuration()) {
                    Collections.swap(movies, i, j);
                } else if (movies.get(i).getDuration() == movies.get(j).getDuration()) {
                    if (movies.get(i).getTitle().compareTo(movies.get(j).getTitle()) < 0) {
                        Collections.swap(movies, i, j);
                    }
                }
            }
        }
    }
    /**
     * Sort the list of series by their duration in ascending order
     * the second criteria is their titles
     */
    public static void sortSeriesByLongestAsc(final List<Series> series) {
        int i, j;
        for (i = 0; i < series.size() - 1; i++) {
            for (j = i + 1; j < series.size(); j++) {
                if (series.get(i).getTotalDuration() > series.get(j).getTotalDuration()) {
                    Collections.swap(series, i, j);
                } else if (series.get(i).getTotalDuration() == series.get(j).getTotalDuration()) {
                    if (series.get(i).getTitle().compareTo(series.get(j).getTitle()) > 0) {
                        Collections.swap(series, i, j);
                    }
                }
            }
        }
    }
    /**
     * Sort the list of series by their duration in descending order
     * the second criteria is their titles
     */
    public static void sortSeriesByLongestDesc(final List<Series> series) {
        int i, j;
        for (i = 0; i < series.size() - 1; i++) {
            for (j = i + 1; j < series.size(); j++) {
                if (series.get(i).getTotalDuration() < series.get(j).getTotalDuration()) {
                    Collections.swap(series, i, j);
                } else if (series.get(i).getTotalDuration() == series.get(j).getTotalDuration()) {
                    if (series.get(i).getTitle().compareTo(series.get(j).getTitle()) < 0) {
                        Collections.swap(series, i, j);
                    }
                }
            }
        }
    }
    /**
     * Sort the list of movies by their number of views in ascending order
     * the second criteria is their titles
     */
    public static void sortMoviesByViewsAsc(final List<Movie> movies) {
        int i, j;
        for (i = 0; i < movies.size() - 1; i++) {
            for (j = i + 1; j < movies.size(); j++) {
                if (movies.get(i).getTotalViews() > movies.get(j).getTotalViews()) {
                    Collections.swap(movies, i, j);
                } else if (movies.get(i).getTotalViews() == movies.get(j).getTotalViews()) {
                    if (movies.get(i).getTitle().compareTo(movies.get(j).getTitle()) > 0) {
                        Collections.swap(movies, i, j);
                    }
                }
            }
        }
    }
    /**
     * Sort the list of movies by their number of views in descending order
     * the second criteria is their titles
     */
    public static void sortMoviesByViewsDesc(final List<Movie> movies) {
        int i, j;
        for (i = 0; i < movies.size() - 1; i++) {
            for (j = i + 1; j < movies.size(); j++) {
                if (movies.get(i).getTotalViews() < movies.get(j).getTotalViews()) {
                    Collections.swap(movies, i, j);
                } else if (movies.get(i).getTotalViews() == movies.get(j).getTotalViews()) {
                    if (movies.get(i).getTitle().compareTo(movies.get(j).getTitle()) < 0) {
                        Collections.swap(movies, i, j);
                    }
                }
            }
        }
    }
    /**
     * Sort the list of series by their number of views in ascending order
     * the second criteria is their titles
     */
    public static void sortSeriesByViewsAsc(final List<Series> series) {
        int i, j;
        for (i = 0; i < series.size() - 1; i++) {
            for (j = i + 1; j < series.size(); j++) {
                if (series.get(i).getTotalViews() > series.get(j).getTotalViews()) {
                    Collections.swap(series, i, j);
                } else if (series.get(i).getTotalViews() == series.get(j).getTotalViews()) {
                    if (series.get(i).getTitle().compareTo(series.get(j).getTitle()) > 0) {
                        Collections.swap(series, i, j);
                    }
                }
            }
        }
    }
    /**
     * Sort the list of series by their number of views in descending order
     * the second criteria is their titles
     */
    public static void sortSeriesByViewsDesc(final List<Series> series) {
        int i, j;
        for (i = 0; i < series.size() - 1; i++) {
            for (j = i + 1; j < series.size(); j++) {
                if (series.get(i).getTotalViews() < series.get(j).getTotalViews()) {
                    Collections.swap(series, i, j);
                } else if (series.get(i).getTotalViews() == series.get(j).getTotalViews()) {
                    if (series.get(i).getTitle().compareTo(series.get(j).getTitle()) < 0) {
                        Collections.swap(series, i, j);
                    }
                }
            }
        }
    }
    /**
     * Sort the list of users by their actvity, which consists in the number of rated
     * movies they have in ascending order
     * the second criteria is their names
     */
    public static void sortUsersByActivityAsc(final List<User> users) {
        int i, j;
        for (i = 0; i < users.size() - 1; i++) {
            for (j = i + 1; j < users.size(); j++) {
                if (users.get(i).getRatedMovies().size() > users.get(j).getRatedMovies().size()) {
                    Collections.swap(users, i, j);
                } else if (users.get(i).getRatedMovies().size()
                        ==
                        users.get(j).getRatedMovies().size()) {
                    if (users.get(i).getUsername().compareTo(users.get(j).getUsername()) > 0) {
                        Collections.swap(users, i, j);
                    }
                }
            }
        }
    }
    /**
     * Sort the list of users by their actvity, which consists in the number of rated
     * movies they have in descending order
     * the second criteria is their names
     */
    public static void sortUsersByActivityDesc(final List<User> users) {
        int i, j;
        for (i = 0; i < users.size() - 1; i++) {
            for (j = i + 1; j < users.size(); j++) {
                if (users.get(i).getRatedMovies().size() < users.get(j).getRatedMovies().size()) {
                    Collections.swap(users, i, j);
                } else if (users.get(i).getRatedMovies().size()
                        ==
                        users.get(j).getRatedMovies().size()) {
                    if (users.get(i).getUsername().compareTo(users.get(j).getUsername()) < 0) {
                        Collections.swap(users, i, j);
                    }
                }
            }
        }
    }
    /**
     * Sort the list of videos by their average rating in descending order
     * the second criteria is their apparition in myMovies or mySeries lists from database
     */
    public static void sortVideosByRatings(final List<Video> videos, final List<Movie> movies,
                                           final List<Series> series) {
        int i, j;
        for (i = 0; i < videos.size() - 1; i++) {
            for (j = i + 1; j < videos.size(); j++) {
                Double rating1 = 0.0, rating2 = 0.0;
                int index1 = 0, index2 = 0;
                if (videos.get(i) instanceof Movie) {
                    rating1 = ((Movie) videos.get(i)).getAverageRating();
                    index1 = movies.indexOf(videos.get(i));
                } else if (videos.get(i) instanceof Series) {
                    rating1 = ((Series) videos.get(i)).getAverageRating();
                    index1 = series.indexOf(videos.get(i));
                }
                if (videos.get(j) instanceof Movie) {
                    rating2 = ((Movie) videos.get(j)).getAverageRating();
                    index2 = movies.indexOf(videos.get(j));
                } else if (videos.get(j) instanceof Series) {
                    rating2 = ((Series) videos.get(j)).getAverageRating();
                    index2 = series.indexOf(videos.get(j));
                }
                if (rating1 < rating2) {
                    Collections.swap(videos, i, j);
                } else if (rating1.equals(rating2)) {
                    if (index1 > index2) {
                        Collections.swap(videos, i, j);
                    }
                }
            }
        }
    }
    /**
     * Creates a map where the key is the genre and the value represents the total number of views
     * from videos of that genre
     */
    public static void initialisePopularityMap(final HashMap<String, Integer> map,
                                               final List<Video> videos,
                                               final HashMap<String,
                                                       ArrayList<String>> videosByGenres) {
        for (Video video : videos) {
            for (String genre : video.getGenres()) {
                if (map.containsKey(genre)) {
                    videosByGenres.get(genre).add(video.getTitle());
                    Integer views = map.get(genre);
                    map.remove(genre);
                    if (video instanceof Movie) {
                        map.put(genre, views + ((Movie) video).getViews());
                    } else if (video instanceof Series) {
                        map.put(genre, views + ((Series) video).getViews());
                    }
                } else {
                    ArrayList<String> titles = new ArrayList<>();
                    titles.add(video.getTitle());
                    videosByGenres.put(genre, titles);
                    if (video instanceof Movie) {
                        map.put(genre, ((Movie) video).getViews());
                    } else if (video instanceof Series) {
                        map.put(genre, ((Series) video).getViews());
                    }
                }

            }
        }
    }
    /**
     * Creates a list with all genres of the videos from database
     * from videos of that genre
     */
    public static List<String> createListOfGenres(final Map<String, Integer> map) {
        List<String> genres = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            genres.add(entry.getKey());
        }
        return genres;
    }
    /**
     * Sorts the list of genres by their popularity in descending order
     */
    public static void sortGenres(final List<String> genres, final HashMap<String, Integer> map) {
        int i, j;
        for (i = 0; i < genres.size() - 1; i++) {
            for (j = i + 1; j < genres.size(); j++) {
                if (map.get(genres.get(i)) < map.get(genres.get(j))) {
                    Collections.swap(genres, i, j);
                } else if (map.get(genres.get(i)) == map.get(genres.get(j))) {
                    if (i > j) {
                        Collections.swap(genres, i, j);
                    }
                }
            }
        }
    }
    /**
     * Returns the first video unseen by the user that has the biggest popularity
     */
    public static String findFirstUnseenPopularVideo(final List<String> genres, final User user,
                                                     final HashMap<String,
                                                             ArrayList<String>> videosByGenres) {
        for (String gen : genres) {
            for (String video : videosByGenres.get(gen)) {
                if (!(user.getHistory().containsKey(video))) {
                    return video;
                }
            }
        }
        return null;
    }
    /**
     * Creates a list with all videos that occures at least once in favorite list of user
     */
    public static List<String> createFavoriteList(final List<User> users) {
        List<String> videos = new ArrayList<>();
        for (User user : users) {
            for (String favorite : user.getFavoriteMovies()) {
                if (!(videos.contains(favorite))) {
                    videos.add(favorite);
                }
            }
        }
        return videos;
    }
    /**
     * Creates a map where the key is the title of
     * the video and the value represents the total
     * number of occurences
     * in favorite lists
     */
    public static void initialiseFavoriteMap(final HashMap<String, Integer> map,
                                             final List<User> users) {
        for (User user : users) {
            for (String favorite : user.getFavoriteMovies()) {
                if (map.containsKey(favorite)) {
                    Integer favOccurences = map.get(favorite);
                    map.remove(favorite);
                    map.put(favorite, favOccurences + 1);
                } else {
                    map.put(favorite, 1);
                }
            }
        }
    }
    /**
     * Sorts the list with favorite videos by their occurences in descending order
     */
    public static void sortFavoriteVidList(final List<String> videos,
                                           final HashMap<String, Integer> map) {
        int i, j;
        for (i = 0; i < videos.size() - 1; i++) {
            for (j = i + 1; j < videos.size(); j++) {
                if (map.get(videos.get(i)) < map.get(videos.get(j))) {
                    Collections.swap(videos, i, j);
                } else if (map.get(videos.get(i)) == map.get(videos.get(j))) {
                    if (i > j) {
                        Collections.swap(videos, i, j);
                    }
                }
            }
        }
    }
    /**
     * Returns the first video unseen by the user that has the biggest number of occurences
     */
    public static String findFirstFavUnseen(final User user, final List<String> videos) {
        for (String video : videos) {
            if (!(user.getHistory().containsKey(video))) {
                return video;
            }
        }
        return null;
    }
    /**
     * Creates a list of videos that are of a particular genre
     */
    public static List<Video> initialiseVideosOfGenre(final List<Video> videos,
                                                      final String genre) {
        List<Video> videosOfGenre = new ArrayList<>();
        for (Video video : videos) {
            if (video.getGenres().contains(genre)) {
                videosOfGenre.add(video);
            }
        }
        return videosOfGenre;
    }
    /**
     * Sorts a list of videos by their ratings
     */
    public static void sortByRating(final List<Video> videos) {
        int i, j;
        for (i = 0; i < videos.size() - 1; i++) {
            for (j = i + 1; j < videos.size(); j++) {
                Double rating1 = 0.0, rating2 = 0.0;
                if (videos.get(i) instanceof Movie) {
                    rating1 = ((Movie) videos.get(i)).getAverageRating();
                } else if (videos.get(i) instanceof Series) {
                    rating1 = ((Series) videos.get(i)).getAverageRating();
                }
                if (videos.get(j) instanceof Movie) {
                    rating2 = ((Movie) videos.get(j)).getAverageRating();
                } else if (videos.get(j) instanceof Series) {
                    rating2 = ((Series) videos.get(j)).getAverageRating();
                }
                if (rating1 > rating2) {
                    Collections.swap(videos, i, j);
                } else if (rating1.equals(rating2)) {
                    if (videos.get(i).getTitle().compareTo(videos.get(j).getTitle()) > 0) {
                        Collections.swap(videos, i, j);
                    }
                }
            }
        }
    }
    /**
     * Returns a list with videos that aren't seen by a user
     */
    public static List<Video> videosUnseen(final User user, final List<Video> videos) {
        List<Video> unseenVideos = new ArrayList<>();
        for (Video video : videos) {
            if (!(user.getHistory().containsKey(video.getTitle()))) {
                unseenVideos.add(video);
            }
        }
        return unseenVideos;
    }
    /**
     * Call the main checker and the coding style checker
     * @param args from command line
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void main(final String[] args) throws IOException {
        File directory = new File(Constants.TESTS_PATH);
        Path path = Paths.get(Constants.RESULT_PATH);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
        File outputDirectory = new File(Constants.RESULT_PATH);
        Checker checker = new Checker();
        checker.deleteFiles(outputDirectory.listFiles());
        for (File file: Objects.requireNonNull(directory.listFiles())) {
            String filepath = Constants.OUT_PATH + file.getName();
            File out = new File(filepath);
            boolean isCreated = out.createNewFile();
            if (isCreated) {
                action(file.getAbsolutePath(), filepath);
            }
        }
        checker.iterateFiles(Constants.RESULT_PATH, Constants.REF_PATH, Constants.TESTS_PATH);
        Checkstyle test = new Checkstyle();
        test.testCheckstyle();
    }
    /**
     * @param filePath1 for input file
     * @param filePath2 for output file
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void action(final String filePath1,
                              final String filePath2) throws IOException {
        InputLoader inputLoader = new InputLoader(filePath1);
        Input input = inputLoader.readData();
        Writer fileWriter = new Writer(filePath2);
        JSONArray arrayResult = new JSONArray();
        //TODO add here the entry point to your implementation
        List<ActionInputData> actions = input.getCommands();
        List<ActorInputData> actors = input.getActors();
        List<UserInputData> users = input.getUsers();
        List<MovieInputData> movies = input.getMovies();
        List<SerialInputData> serials = input.getSerials();
        List<Actor> myActors = populateActors(actors);
        List<User> myUsers = populateUsers(users);
        List<Series> mySeries = populateSeries(serials);
        List<Movie> myMovies = populateMovies(movies);
        for (ActionInputData action: actions) {
            // this is for commands
            if (action.getActionType().equals("command")) {
                JSONObject output = new JSONObject();
                output.put("id", action.getActionId());
                User user = searchUser(myUsers, action.getUsername());
                String message = new String();
                if (action.getType().equals("favorite")) {
                    message = user.favorite(action.getTitle());
                    if (message.contains("succes ->")) {
                        Movie possibleMovie = searchMovie(myMovies, action.getTitle());
                        if (possibleMovie != null) {
                            possibleMovie.incrementFavoriteOccurences();
                        } else {
                            Series serial = searchSeries(mySeries, action.getTitle());
                            serial.incrementFavoriteOccurences();
                        }
                    }
                }
                if (action.getType().equals("view")) {
                    int increment = 1;
                    Series serial = searchSeries(mySeries, action.getTitle());
                    Movie movie = searchMovie(myMovies, action.getTitle());
                    message = user.view(action.getTitle());
                    if (serial != null) {
                        serial.getUsersThatSeen().add(user.getUsername());
                        serial.calculateViews(user, serial.getTitle());
                    } else {
                        movie.getUsersThatSeen().add(user.getUsername());
                        movie.calculateViews(user, movie.getTitle());
                    }
                }

                if (action.getType().equals("rating")) {
                    // verificam intai ce tip de video avem (film sau serial)
                    if (action.getSeasonNumber() == 0) {
                        // este film
                        Movie movie = searchMovie(myMovies, action.getTitle());
                        message = movie.addRating(user, action.getGrade());
                    } else {
                        Series serial = searchSeries(mySeries, action.getTitle());
                        message = serial.addRating(user,
                                action.getGrade(),
                                action.getSeasonNumber());
                    }
                }
                output.put("message", message);
                arrayResult.add(output);
            }
            // implement queries
            if (action.getActionType().equals("query")) {
                if (action.getObjectType().equals("actors")) {
                    List<String> words = action.getFilters().get(2);
                    List<String> awards = action.getFilters().get(3);
                    JSONObject output = new JSONObject();
                    output.put("id", action.getActionId());


                    String message = new String("Query result: ");
                    // query pentru average
                    if (action.getCriteria().equals("average")) {
                        List<Actor> actorsWithRatings = new ArrayList<>();
                        for (Actor actor : myActors) {
                            actor.calculateAverageGrade(myMovies, mySeries);
                            if (!(actor.getAverageGrade().equals(0.0))) {
                                actorsWithRatings.add(actor);
                            }
                        }

                        if (action.getSortType().equals("asc")) {
                            sortActorsByAverageAsc(actorsWithRatings);
                        }

                        if (action.getSortType().equals("desc")) {
                            sortActorsByAverageDesc(actorsWithRatings);
                        }
                        ArrayList<String> names = new ArrayList<>();
                        int i;
                        int nrMax = action.getNumber();
                        if (action.getNumber() > actorsWithRatings.size()) {
                            nrMax = actorsWithRatings.size();
                        }
                        for (i = 0; i < nrMax; i++) {
                            names.add(actorsWithRatings.get(i).getName());
                        }
                        message = message + names.toString();
                    }
                    // query pentru awards
                    if (action.getCriteria().equals("awards")) {

                        List<Actor> filterActors = new ArrayList<>();
                        if (awards != null) {
                            for (Actor actor : myActors) {
                                actor.filterForAwards(awards, filterActors);
                            }
                        }

                        for (Actor actor : filterActors) {
                            actor.calculateTotalAwards();
                        }
                        if (action.getSortType().equals("asc")) {
                            sortActorsByAwardsAsc(filterActors);
                        }
                        if (action.getSortType().equals("desc")) {
//                            Collections.reverse(filterActors);
                            sortActorsByAwardsDesc(filterActors);
                        }
                        int i;
                        ArrayList<String> names = new ArrayList<>();
                        int nrMax = action.getNumber();
                        if (action.getNumber() > filterActors.size()) {
                            nrMax = filterActors.size();
                        }
                        for (i = 0; i < nrMax; i++) {
                            names.add(filterActors.get(i).getName());
                        }
                        message = message + names.toString();
                    }
                    if (action.getCriteria().equals("filter_description")) {
                        List<Actor> filterActors = new ArrayList<>();
                        for (Actor actor : myActors) {
                            actor.filterForWords(words, filterActors);
                        }
                        sortActorsAlphabetically(filterActors);
                        if (action.getSortType().equals("desc")) {
                            Collections.reverse(filterActors);
                        }
                        ArrayList<String> names = new ArrayList<>();
                        for (Actor actor : filterActors) {
                            names.add(actor.getName());
                        }
                        message = message + names.toString();
                    }
                    output.put("message", message);
                    arrayResult.add(output);
                }
                if (action.getObjectType().equals("movies")) {
                    JSONObject output = new JSONObject();
                    output.put("id", action.getActionId());
                    List<Movie> filteredMovies;
                    if (action.getCriteria().equals("ratings")) {
                        // selectam doar filmele care au rating
                        filteredMovies = new ArrayList<>();
                        for (Movie movie : myMovies) {
                            movie.filterMoviesByRating(filteredMovies);
                        }
                    } else {
                        filteredMovies = new ArrayList<>(myMovies);
                    }
                    List<String> year = action.getFilters().get(0);
                    List<String> genre = action.getFilters().get(1);
                    if (year.get(0) != null) {
                        // filtram in functie de year
                        List<Movie> moviesToRemove = new ArrayList<>();
                        for (Movie movie : filteredMovies) {
                            if (movie.getYear() != Integer.parseInt(year.get(0))) {
                                moviesToRemove.add(movie);
                            }
                        }
                        for (Movie movie : moviesToRemove) {
                            filteredMovies.remove(movie);
                        }
                    }
                    if (genre.get(0) != null) {
                        List<Movie> moviesToRemove = new ArrayList<>();
                        for (Movie movie : filteredMovies) {
                            if (!(movie.getGenres().contains(genre.get(0)))) {
                                moviesToRemove.add(movie);
                            }
                        }
                        for (Movie movie : moviesToRemove) {
                            filteredMovies.remove(movie);
                        }
                    }
                    String message = new String("Query result: ");
                    // am aplicat toate filtrele => facem sortarea
                    if (action.getCriteria().equals("ratings")) {
                        if (action.getSortType().equals("asc")) {
                            sortMoviesByRatingAsc(filteredMovies);
                        }
                        if (action.getSortType().equals("desc")) {
                            sortMoviesByRatingDesc(filteredMovies);
                        }
                        int i;
                        ArrayList<String> names = new ArrayList<>();
                        int nrMax = action.getNumber();
                        if (action.getNumber() > filteredMovies.size()) {
                            nrMax = filteredMovies.size();
                        }
                        for (i = 0; i < nrMax; i++) {
                            names.add(filteredMovies.get(i).getTitle());
                        }
                        message = message + names.toString();
                    }
                    if (action.getCriteria().equals("favorite")) {
                        for (User user : myUsers) {
                            for (String title : user.getFavoriteMovies()) {
                                Movie possibleMovie = searchMovie(filteredMovies, title);
                                if (possibleMovie != null) {
                                    possibleMovie.incrementFavoriteOccurences();
                                }
                            }
                        }
                        List<Movie> moviesWithoutFavoriteOcc = new ArrayList<>();
                        for (Movie movie : filteredMovies) {
                            if (movie.getFavoriteOccurences() == 0) {
                                moviesWithoutFavoriteOcc.add(movie);
                            }
                        }
                        for (Movie movie : moviesWithoutFavoriteOcc) {
                            filteredMovies.remove(movie);
                        }
                        if (action.getSortType().equals("asc")) {
                            sortMoviesByFavoriteAsc(filteredMovies);
                        }
                        if (action.getSortType().equals("desc")) {
                            sortMoviesByFavoriteDesc(filteredMovies);
                        }
                        int i;
                        ArrayList<String> names = new ArrayList<>();
                        int nrMax = action.getNumber();
                        if (action.getNumber() > filteredMovies.size()) {
                            nrMax = filteredMovies.size();
                        }
                        for (i = 0; i < nrMax; i++) {
                            names.add(filteredMovies.get(i).getTitle());
                        }
                        message = message + names.toString();
                    }
                    if (action.getCriteria().equals("longest")) {
                        if (action.getSortType().equals("asc")) {
                            sortMoviesByLongestAsc(filteredMovies);
                        }
                        if (action.getSortType().equals("desc")) {
                            sortMoviesByLongestDesc(filteredMovies);
                        }
                        int i;
                        ArrayList<String> names = new ArrayList<>();
                        int nrMax = action.getNumber();
                        if (action.getNumber() > filteredMovies.size()) {
                            nrMax = filteredMovies.size();
                        }
                        for (i = 0; i < nrMax; i++) {
                            names.add(filteredMovies.get(i).getTitle());
                        }
                        message = message + names.toString();
                    }
                    if (action.getCriteria().equals("most_viewed")) {
                        for (User user : myUsers) {
                            for (Map.Entry<String, Integer> entry : user.getHistory().entrySet()) {
                                Movie possibleMovie = searchMovie(filteredMovies, entry.getKey());
                                if (possibleMovie != null
                                        &&
                                        possibleMovie.getGoodToIncrement()) {
                                    possibleMovie.incrementViews(entry.getValue());
                                }
                            }
                        }
                        for (Movie movie : filteredMovies) {
                            movie.setFalse();
                        }
                        List<Movie> moviesWithoutViews = new ArrayList<>();
                        for (Movie movie : filteredMovies) {
                            if (movie.getTotalViews() == 0) {
                                moviesWithoutViews.add(movie);
                            }
                        }
                        for (Movie movie : moviesWithoutViews) {
                            filteredMovies.remove(movie);
                        }
                        if (action.getSortType().equals("asc")) {
                            sortMoviesByViewsAsc(filteredMovies);
                        }
                        if (action.getSortType().equals("desc")) {
                            sortMoviesByViewsDesc(filteredMovies);
                        }
                        int i;
                        ArrayList<String> names = new ArrayList<>();
                        int nrMax = action.getNumber();
                        if (action.getNumber() > filteredMovies.size()) {
                            nrMax = filteredMovies.size();
                        }
                        for (i = 0; i < nrMax; i++) {
                            names.add(filteredMovies.get(i).getTitle());
                        }
                        message = message + names.toString();
                    }
                    output.put("message", message);
                    arrayResult.add(output);
                }
                if (action.getObjectType().equals("shows")) {
                    JSONObject output = new JSONObject();
                    output.put("id", action.getActionId());
                    List<Series> filteredSeries;
                    if (action.getCriteria().equals("ratings")) {
                        // selectam doar filmele care au rating
                        filteredSeries = new ArrayList<>();
                        for (Series serial : mySeries) {
                            serial.filterSeriesByRating(filteredSeries);
                        }
                    } else {
                        filteredSeries = new ArrayList<>(mySeries);
                    }
                    List<String> year = action.getFilters().get(0);
                    List<String> genre = action.getFilters().get(1);
                    if (year.get(0) != null) {
                        // filtram in functie de year
                        List<Series> seriesToRemove = new ArrayList<>();
                        for (Series serial : filteredSeries) {
                            if (serial.getYear() != Integer.parseInt(year.get(0))) {
                                seriesToRemove.add(serial);
                            }
                        }
                        for (Series serial : seriesToRemove) {
                            filteredSeries.remove(serial);
                        }
                    }
                    if (genre.get(0) != null) {
                        List<Series> seriesToRemove = new ArrayList<>();
                        for (Series serial : filteredSeries) {
                            if (!(serial.getGenres().contains(genre.get(0)))) {
                                seriesToRemove.add(serial);
                            }
                        }
                        for (Series serial : seriesToRemove) {
                            filteredSeries.remove(serial);
                        }
                    }
                    // calculam durata totala pentru fiecare serial
                    for (Series serial : filteredSeries) {
                        serial.calculateTotalDuration();
                    }
                    String message = new String("Query result: ");
                    // am aplicat toate filtrele => facem sortarea
                    if (action.getCriteria().equals("ratings")) {
                        if (action.getSortType().equals("asc")) {
                            sortSeriesByRatingAsc(filteredSeries);
                        }
                        if (action.getSortType().equals("desc")) {
                            sortSeriesByRatingDesc(filteredSeries);
                        }
                        int i;
                        ArrayList<String> names = new ArrayList<>();
                        int nrMax = action.getNumber();
                        if (action.getNumber() > filteredSeries.size()) {
                            nrMax = filteredSeries.size();
                        }
                        for (i = 0; i < nrMax; i++) {
                            names.add(filteredSeries.get(i).getTitle());
                        }
                        message = message + names.toString();
                    }
                    if (action.getCriteria().equals("favorite")) {
                        for (User user : myUsers) {
                            for (String title : user.getFavoriteMovies()) {
                                Series serial = searchSeries(filteredSeries, title);
                                if (serial != null) {
                                    serial.incrementFavoriteOccurences();
                                }
                            }
                        }
                        List<Series> serialsWithoutFavOcc = new ArrayList<>();
                        for (Series serial : filteredSeries) {
                            if (serial.getFavoriteOccurences() == 0) {
                                serialsWithoutFavOcc.add(serial);
                            }
                        }
                        for (Series serial : serialsWithoutFavOcc) {
                            filteredSeries.remove(serial);
                        }
                        if (action.getSortType().equals("asc")) {
                            sortSeriesByFavoriteAsc(filteredSeries);
                        }
                        if (action.getSortType().equals("desc")) {
                            sortSeriesByFavoriteDesc(filteredSeries);
                        }
                        int i;
                        ArrayList<String> names = new ArrayList<>();
                        int nrMax = action.getNumber();
                        if (action.getNumber() > filteredSeries.size()) {
                            nrMax = filteredSeries.size();
                        }
                        for (i = 0; i < nrMax; i++) {
                            names.add(filteredSeries.get(i).getTitle());
                        }
                        message = message + names.toString();
                    }
                    if (action.getCriteria().equals("longest")) {
                        if (action.getSortType().equals("asc")) {
                            sortSeriesByLongestAsc(filteredSeries);
                        }
                        if (action.getSortType().equals("desc")) {
                            sortSeriesByLongestDesc(filteredSeries);
                        }
                        int i;
                        ArrayList<String> names = new ArrayList<>();
                        int nrMax = action.getNumber();
                        if (action.getNumber() > filteredSeries.size()) {
                            nrMax = filteredSeries.size();
                        }
                        for (i = 0; i < nrMax; i++) {
                            names.add(filteredSeries.get(i).getTitle());
                        }
                        message = message + names.toString();
                    }
                    if (action.getCriteria().equals("most_viewed")) {
                        for (User user : myUsers) {
                            for (Map.Entry<String, Integer> entry : user.getHistory().entrySet()) {
                                Series possibleSerial
                                        =
                                        searchSeries(filteredSeries, entry.getKey());
                                if (possibleSerial != null
                                        &&
                                        possibleSerial.getGoodToIncrement()) {
                                    possibleSerial.incrementViews(entry.getValue());
                                }
                            }
                        }
                        for (Series serial : filteredSeries) {
                            serial.setFalse();
                        }
                        List<Series> seriesWithoutViews = new ArrayList<>();
                        for (Series serial : filteredSeries) {
                            if (serial.getTotalViews() == 0) {
                                seriesWithoutViews.add(serial);
                            }
                        }
                        for (Series serial : seriesWithoutViews) {
                            filteredSeries.remove(serial);
                        }
                        if (action.getSortType().equals("asc")) {
                            sortSeriesByViewsAsc(filteredSeries);
                        }

                        if (action.getSortType().equals("desc")) {
                            sortSeriesByViewsDesc(filteredSeries);
                        }
                        int i;
                        ArrayList<String> names = new ArrayList<>();
                        int nrMax = action.getNumber();
                        if (action.getNumber() > filteredSeries.size()) {
                            nrMax = filteredSeries.size();
                        }
                        for (i = 0; i < nrMax; i++) {
                            names.add(filteredSeries.get(i).getTitle());
                        }
                        message = message + names.toString();
                    }
                    output.put("message", message);
                    arrayResult.add(output);
                }
                if (action.getObjectType().equals("users")) {
                    JSONObject output = new JSONObject();
                    output.put("id", action.getActionId());
                    String message = new String("Query result: ");
                    if (action.getCriteria().equals("num_ratings")) {
                        List<User> usersActive = new ArrayList<>(myUsers);
                        for (User user : myUsers) {
                            if (user.getRatedMovies().size() == 0) {
                                usersActive.remove(user);
                            }
                        }
                        if (action.getSortType().equals("asc")) {
                            sortUsersByActivityAsc(usersActive);
                        }
                        if (action.getSortType().equals("desc")) {
                            sortUsersByActivityDesc(usersActive);
                        }
                        int i;
                        ArrayList<String> names = new ArrayList<>();
                        int nrMax = action.getNumber();
                        if (action.getNumber() > usersActive.size()) {
                            nrMax = usersActive.size();
                        }
                        for (i = 0; i < nrMax; i++) {
                            names.add(usersActive.get(i).getUsername());
                        }
                        message = message + names.toString();
                    }
                    output.put("message", message);
                    arrayResult.add(output);
                }
            }
            if (action.getActionType().equals("recommendation")) {
                List<Video> videos = new ArrayList<>();
                for (Movie movie : myMovies) {
                    videos.add(movie);
                }
                for (Series serial : mySeries) {
                    videos.add(serial);
                }
                if (action.getType().equals("standard")) {
                    JSONObject output = new JSONObject();
                    output.put("id", action.getActionId());
                    String message;
                    User user = searchUser(myUsers, action.getUsername());
                    if (user == null) {
                        message = new String("StandardRecommendation cannot be applied!");
                    } else {
                        String title = user.firstUnseenVideo(videos);
                        if (title == null) {
                            message = new String("StandardRecommendation cannot be applied!");
                        } else {
                            message = new String("StandardRecommendation result: ");
                            message = message + title;
                        }
                    }
                    output.put("message", message);
                    arrayResult.add(output);
                }
                if (action.getType().equals("best_unseen")) {
                    JSONObject output = new JSONObject();
                    output.put("id", action.getActionId());
                    String message;
                    User user = searchUser(myUsers, action.getUsername());
                    if (user == null) {
                        message = new String("BestUnseenRecommendation cannot be applied!");
                    } else {
                        sortVideosByRatings(videos, myMovies, mySeries);
                        String title = user.firstUnseenVideo(videos);
                        if (title == null) {
                            message = new
                                    String("BestRatedUnseenRecommendation cannot be applied!");
                        } else {
                            message = new String("BestRatedUnseenRecommendation result: ");
                            message = message + title;
                        }
                    }
                    output.put("message", message);
                    arrayResult.add(output);
                }
                if (action.getType().equals("popular")) {
                    JSONObject output = new JSONObject();
                    output.put("id", action.getActionId());
                    String message;
                    User user = searchUser(myUsers, action.getUsername());
                    if (user == null || !(user.getSubscriptionType().equals("PREMIUM"))) {
                        message = new String("PopularRecommendation cannot be applied!");
                    } else {
                        HashMap<String, Integer> popularityOfGenres = new HashMap<>();
                        HashMap<String, ArrayList<String>> videosOfGenres = new HashMap<>();
                        initialisePopularityMap(popularityOfGenres, videos, videosOfGenres);
                        List<String> genres = createListOfGenres(popularityOfGenres);

                        // sortarea genurilor in functi de popularitate
                        sortGenres(genres, popularityOfGenres);
                        // gaseste primul video nevazut din cel mai popular gen
                        String title = findFirstUnseenPopularVideo(genres, user, videosOfGenres);
                        if (title != null) {
                            message = new String("PopularRecommendation result: ");
                            message = message + title;
                        } else {
                            message = new String("PopularRecommendation cannot be applied!");
                        }
                    }
                    output.put("message", message);
                    arrayResult.add(output);
                }
                if (action.getType().equals("favorite")) {
                    JSONObject output = new JSONObject();
                    output.put("id", action.getActionId());
                    String message;
                    User user = searchUser(myUsers, action.getUsername());
                    if (user == null || !(user.getSubscriptionType().equals("PREMIUM"))) {
                        message = new String("FavoriteRecommendation cannot be applied!");
                    } else {
                        // make a list with videos that appear at least once in favorites
                        List<String> videosFromFavorites = createFavoriteList(myUsers);
                        HashMap<String, Integer> occurencesInFavorites = new HashMap<>();
                        initialiseFavoriteMap(occurencesInFavorites, myUsers);
                        // sort videos by occurences in favorite lists
                        sortFavoriteVidList(videosFromFavorites, occurencesInFavorites);
                        // cauta primul film nevazut dupa acest criteriu
                        String title = findFirstFavUnseen(user, videosFromFavorites);
                        if (title != null) {
                            message = new String("FavoriteRecommendation result: ");
                            message = message + title;
                        } else {
                            message = new String("FavoriteRecommendation cannot be applied!");
                        }
                    }
                    output.put("message", message);
                    arrayResult.add(output);
                }
                if (action.getType().equals("search")) {
                    JSONObject output = new JSONObject();
                    output.put("id", action.getActionId());
                    String message;
                    User user = searchUser(myUsers, action.getUsername());
                    if (user == null || !(user.getSubscriptionType().equals("PREMIUM"))) {
                        message = new String("SearchRecommendation cannot be applied!");
                    } else {
                        List<Video> videosOfGenre
                                =
                                initialiseVideosOfGenre(videos, action.getGenre());

                        sortByRating(videosOfGenre);

                        List<Video> unseenVideos = videosUnseen(user, videosOfGenre);
                        List<String> titles = new ArrayList<>();
                        if (unseenVideos.size() > 0) {
                            for (Video video : unseenVideos) {
                                titles.add(video.getTitle());
                            }
                            message = new String("SearchRecommendation result: ");
                            message = message + titles.toString();
                        } else {
                            message = new String("SearchRecommendation cannot be applied!");
                        }
                    }
                    output.put("message", message);
                    arrayResult.add(output);
                }
            }
        }
        fileWriter.closeJSON(arrayResult);
    }
}
