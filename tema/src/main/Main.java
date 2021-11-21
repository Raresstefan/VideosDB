package main;
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
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
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
    public static List <User> populateUsers(final List<UserInputData> usersFromInput) {
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
    public static void sortActorsByAverageAsc(final List<Actor> actors) {
        int i, j;
        for(i = 0; i < actors.size() - 1; i++) {
            for(j = i + 1; j < actors.size(); j++) {
                if(actors.get(i).getAverageGrade() > actors.get(j).getAverageGrade()) {
                    Collections.swap(actors, i, j);
                } else if(actors.get(i).getAverageGrade().equals(actors.get(j).getAverageGrade())) {
                    if(actors.get(i).getName().compareTo(actors.get(j).getName()) > 0) {
                        Collections.swap(actors, i, j);
                    }
                }
            }
        }
    }
    public static void sortActorsByAverageDesc(final List<Actor> actors) {
        int i, j;
        for(i = 0; i < actors.size() - 1; i++) {
            for(j = i + 1; j < actors.size(); j++) {
                if(actors.get(i).getAverageGrade() < actors.get(j).getAverageGrade()) {
                    Collections.swap(actors, i, j);
                } else if(actors.get(i).getAverageGrade().equals(actors.get(j).getAverageGrade())) {
                    if(actors.get(i).getName().compareTo(actors.get(j).getName()) > 0) {
                        Collections.swap(actors, i, j);
                    }
                }
            }
        }
    }
    public static void sortActorsByAwardsAsc(final List<Actor> actors) {
        int i, j;
        for(i = 0; i < actors.size() - 1; i++) {
            for(j = i + 1; j < actors.size(); j++) {
                if(actors.get(i).getTotalNumberOfAwards() > actors.get(j).getTotalNumberOfAwards()) {
                    Collections.swap(actors, i, j);
                } else if(actors.get(i).getTotalNumberOfAwards().equals(actors.get(j).getTotalNumberOfAwards())) {
                    if(actors.get(i).getName().compareTo(actors.get(j).getName()) > 0) {
                        Collections.swap(actors, i, j);
                    }
                }
            }
        }
    }
    public static void sortActorsByAwardsDesc(final List<Actor> actors) {
        int i, j;
        for(i = 0; i < actors.size() - 1; i++) {
            for(j = i + 1; j < actors.size(); j++) {
                if(actors.get(i).getTotalNumberOfAwards() < actors.get(j).getTotalNumberOfAwards()) {
                    Collections.swap(actors, i, j);
                } else if(actors.get(i).getTotalNumberOfAwards().equals(actors.get(j).getTotalNumberOfAwards())) {
                    if(actors.get(i).getName().compareTo(actors.get(j).getName()) > 0) {
                        Collections.swap(actors, i, j);
                    }
                }
            }
        }
    }
    public static void sortActorsAlphabetically(final List<Actor> actors) {
        int i, j;
        for(i = 0; i < actors.size() - 1; i++) {
            for(j = i + 1; j < actors.size(); j++) {
                if(actors.get(i).getName().compareTo(actors.get(j).getName()) > 0) {
                    Collections.swap(actors, i, j);
                }
            }
        }
    }
    public static void sortMoviesByRatingAsc(final List<Movie> movies) {
        int i, j;
        for(i = 0; i < movies.size() - 1; i++) {
            for(j = i + 1; j < movies.size(); j++) {
                if(movies.get(i).getAverageRating() > movies.get(j).getAverageRating()) {
                    Collections.swap(movies, i, j);
                }
            }
        }
    }
    public static void sortSeriesByRatingAsc(final List<Series> series) {
        int i, j;
        for(i = 0; i < series.size() - 1; i++) {
            for(j = i + 1; j < series.size(); j++) {
                if(series.get(i).getAverageRating() > series.get(j).getAverageRating()) {
                    Collections.swap(series, i, j);
                }
            }
        }
    }
    public static void sortMoviesByFavoriteAsc(final List<Movie> movies) {
        int i, j;
        for(i = 0; i < movies.size() - 1; i++) {
            for(j = i + 1; j < movies.size(); j++) {
                if(movies.get(i).getFavoriteOccurences() > movies.get(j).getFavoriteOccurences()) {
                    Collections.swap(movies, i, j);
                }
            }
        }
    }
    public static void sortSeriesByFavoriteAsc(final List<Series> series) {
        int i, j;
        for(i = 0; i < series.size() - 1; i++) {
            for(j = i + 1; j < series.size(); j++) {
                if(series.get(i).getFavoriteOccurences() > series.get(j).getFavoriteOccurences()) {
                    Collections.swap(series, i, j);
                }
            }
        }
    }
    public static void sortMoviesByLongestAsc(final List<Movie> movies) {
        int i, j;
        for(i = 0; i < movies.size() - 1; i++) {
            for(j = i + 1; j < movies.size(); j++) {
                if(movies.get(i).getDuration() > movies.get(j).getDuration()) {
                    Collections.swap(movies, i, j);
                }
            }
        }
    }
    public static void sortSeriesByLongestAsc(final List<Series> series) {
        int i, j;
        for(i = 0; i < series.size() - 1; i++) {
            for(j = i + 1; j < series.size(); j++) {
                if(series.get(i).getTotalDuration() > series.get(j).getTotalDuration()) {
                    Collections.swap(series, i, j);
                }
            }
        }
    }
    public static void sortMoviesByViewsAsc(final List<Movie> movies) {
        int i, j;
        for(i = 0; i < movies.size() - 1; i++) {
            for(j = i + 1; j < movies.size(); j++) {
                if(movies.get(i).getViews() > movies.get(j).getViews()) {
                    Collections.swap(movies, i, j);
                }
            }
        }
    }
    public static void sortSeriesByViewsAsc(final List<Series> series) {
        int i, j;
        for(i = 0; i < series.size() - 1; i++) {
            for(j = i + 1; j < series.size(); j++) {
                if(series.get(i).getViews() > series.get(j).getViews()) {
                    Collections.swap(series, i, j);
                }
            }
        }
    }
//    public static void sortAscActors(final List<Actor> actors) {
//
//    }
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
//        List<ActionInputData> actions = input.getCommands();
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
//            System.out.println(action.getActionType());
            // this is for commands
            if(action.getActionType().equals("command")) {
                JSONObject output = new JSONObject();
                output.put("id", action.getActionId());
                User user = searchUser(myUsers, action.getUsername());
                String message = new String();
                if (action.getType().equals("favorite")) {
                    message = user.favorite(action.getTitle());
                    if(message.contains("succes ->")) {
                        Movie possibleMovie = searchMovie(myMovies, action.getTitle());
                        if(possibleMovie != null) {
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
                    if (serial != null) {
                        increment = serial.getNumberSeason();
                        serial.incrementViews(increment);
                    } else {
                        Movie movie = searchMovie(myMovies, action.getTitle());
                        movie.incrementViews(increment);
                    }
                    message = user.view(action.getTitle(), increment);
                }
                if (action.getType().equals("rating")) {
                    // verificam intai ce tip de video avem (film sau serial)
                    if (action.getSeasonNumber() == 0) {
                        // este film
                        Movie movie = searchMovie(myMovies, action.getTitle());
                        message = movie.addRating(user, action.getGrade());
                    } else {
                        Series serial = searchSeries(mySeries, action.getTitle());
                        message = serial.addRating(user, action.getGrade(), action.getSeasonNumber());
                    }
                }
                output.put("message", message);
                arrayResult.add(output);
            }
            // implement queries
            if(action.getActionType().equals("query")) {
                if(action.getObjectType().equals("actors")) {
                    // cream copie a listei de actori pentru filtrare
                    List<Actor> filterActors = new ArrayList<>();
//                    Collections.copy(filterActors, myActors);
                    // daca avem filtru cu words
//                    System.out.println(action.getFilters().get(0).get(0));
                    List<String> words = action.getFilters().get(2);
                    List<String> awards = action.getFilters().get(3);
                    // aplic filtrare pt words daca e nevoie
                    if(words != null) {
                        for(Actor actor : myActors) {
                            actor.filterForWords(words, filterActors);
                        }
                    }
                    // aplica filtre pentru awards pe filterActors -> foloseste removeIf, fa metoda in Actor
                    if(awards != null) {
                        for(Actor actor : filterActors) {
                            actor.filterForAwards(awards, filterActors);
                        }
                    }
                    List<Actor> actorsToWork;
                    if(awards == null && words == null) { // nu s-au aplicat filtre
//                        List<Actor> sortActors = new ArrayList<>(myActors);
                        actorsToWork = new ArrayList<>(myActors);
                    } else {
                        actorsToWork = new ArrayList<>(filterActors);
                    }
                    JSONObject output = new JSONObject();
                    output.put("id", action.getActionId());
                    for(Actor actor : actorsToWork) {
//               String message = actor.calculateAverageGrade(myMovies, mySeries);
                        actor.calculateAverageGrade(myMovies, mySeries);
//                System.out.println(message);
                    }


                    String message = new String("Query result: ");
                    // query pentru average
                    if(action.getCriteria().equals("average")) {
                        List<Actor> actorsWithRatings = new ArrayList<>();
                        for(Actor actor : actorsToWork) {
                            if(!(actor.getAverageGrade().equals(0.0))) {
                                actorsWithRatings.add(actor);
                            }
                        }

                        if(action.getSortType().equals("asc")) {
                            sortActorsByAverageAsc(actorsWithRatings);
                        }

                        if(action.getSortType().equals("desc")) {
                            sortActorsByAverageDesc(actorsWithRatings);
                        }
                        ArrayList<String> names = new ArrayList<>();
                        int i;
                        int nr_max = action.getNumber();
                        if(action.getNumber() > actorsWithRatings.size()) {
                            nr_max = actorsWithRatings.size();
                        }
                        for(i = 0; i < nr_max; i++) {
                            names.add(actorsWithRatings.get(i).getName());
                        }
                        message = message + names.toString();
                    }
                    // query pentru awards
                    if(action.getCriteria().equals("awards")) {
                        for(Actor actor : filterActors) {
                            actor.calculateTotalAwards();
                        }
                        if(action.getSortType().equals("asc")) {
                            sortActorsByAwardsAsc(filterActors);
                        }
                        if(action.getSortType().equals("desc")) {
                            sortActorsByAwardsDesc(filterActors);
                        }
                        int i;
                        ArrayList<String> names = new ArrayList<>();
                        int nr_max = action.getNumber();
                        if(action.getNumber() > filterActors.size()) {
                            nr_max = filterActors.size();
                        }
                        for(i = 0; i < nr_max; i++) {
                            names.add(filterActors.get(i).getName());
                        }
                        message = message + names.toString();
                    }
                    if(action.getCriteria().equals("filter_description")) {
                        sortActorsAlphabetically(filterActors);
                        if(action.getSortType().equals("desc")) {
                            Collections.reverse(filterActors);
                        }
                        int i;
                        ArrayList<String> names = new ArrayList<>();
                        int nr_max = action.getNumber();
                        if(action.getNumber() > filterActors.size()) {
                            nr_max = filterActors.size();
                        }
                        for(i = 0; i < nr_max; i++) {
                            names.add(filterActors.get(i).getName());
                        }
                        message = message + names.toString();
                    }
                    output.put("message", message);
                    arrayResult.add(output);
                }
                // next TODO: implement queries for videos
                if(action.getObjectType().equals("movies")) {
                    JSONObject output = new JSONObject();
                    output.put("id", action.getActionId());
                    List<Movie> filteredMovies;
                    if(action.getCriteria().equals("ratings")) {
                        // selectam doar filmele care au rating
                        filteredMovies = new ArrayList<>();
                        for(Movie movie : myMovies) {
                            movie.filterMoviesByRating(filteredMovies);
                        }
                    } else {
                        filteredMovies = new ArrayList<>(myMovies);
                    }
                    List<String> year = action.getFilters().get(0);
                    List<String> genre = action.getFilters().get(1);
                    if(year.get(0) != null) {
                        // filtram in functie de year
                        List<Movie> moviesToRemove = new ArrayList<>();
                        for(Movie movie : filteredMovies) {
                            if(movie.getYear() != Integer.parseInt(year.get(0))) {
                                moviesToRemove.add(movie);
                            }
                        }
                        for(Movie movie : moviesToRemove) {
                            filteredMovies.remove(movie);
                        }
                    }
                    if(genre.get(0) != null) {
                        List<Movie> moviesToRemove = new ArrayList<>();
                        for(Movie movie : filteredMovies) {
                            if(!(movie.getGenres().contains(genre.get(0)))){
                                moviesToRemove.add(movie);
                            }
                        }
                        for(Movie movie : moviesToRemove) {
                            filteredMovies.remove(movie);
                        }
                    }
                    String message = new String("Query result: ");
                    // am aplicat toate filtrele => facem sortarea
                    if(action.getCriteria().equals("ratings")) {
                        sortMoviesByRatingAsc(filteredMovies);
                        if(action.getSortType().equals("desc")) {
                            Collections.reverse(filteredMovies);
                        }
                        int i;
                        ArrayList<String> names = new ArrayList<>();
                        int nr_max = action.getNumber();
                        if(action.getNumber() > filteredMovies.size()) {
                            nr_max = filteredMovies.size();
                        }
                        for(i = 0; i < nr_max; i++) {
                            names.add(filteredMovies.get(i).getTitle());
                        }
                        message = message + names.toString();
                    }
                    if(action.getCriteria().equals("favorite")) {
                        for(User user : myUsers) {
                            for(String title : user.getFavoriteMovies()) {
                                Movie possibleMovie = searchMovie(filteredMovies, title);
                                if(possibleMovie != null) {
                                    possibleMovie.incrementFavoriteOccurences();
                                }
                            }
                        }
                        List<Movie> moviesWithoutFavoriteOcc = new ArrayList<>();
                        for(Movie movie : filteredMovies) {
                            if(movie.getFavoriteOccurences() == 0) {
                                moviesWithoutFavoriteOcc.add(movie);
                            }
                        }
                        for(Movie movie : moviesWithoutFavoriteOcc) {
                            filteredMovies.remove(movie);
                        }
                        sortMoviesByFavoriteAsc(filteredMovies);
                        if(action.getSortType().equals("desc")) {
                            Collections.reverse(filteredMovies);
                        }
                        int i;
                        ArrayList<String> names = new ArrayList<>();
                        int nr_max = action.getNumber();
                        if(action.getNumber() > filteredMovies.size()) {
                            nr_max = filteredMovies.size();
                        }
                        for(i = 0; i < nr_max; i++) {
                            names.add(filteredMovies.get(i).getTitle());
                        }
                        message = message + names.toString();
                    }
                    if(action.getCriteria().equals("longest")) {
                        sortMoviesByLongestAsc(filteredMovies);
                        if(action.getSortType().equals("desc")) {
                            Collections.reverse(filteredMovies);
                        }
                        int i;
                        ArrayList<String> names = new ArrayList<>();
                        int nr_max = action.getNumber();
                        if(action.getNumber() > filteredMovies.size()) {
                            nr_max = filteredMovies.size();
                        }
                        for(i = 0; i < nr_max; i++) {
                            names.add(filteredMovies.get(i).getTitle());
                        }
                        message = message + names.toString();
                    }
                    if(action.getCriteria().equals("most_viewed")) {
//                        System.out.println(filePath1);
//                        if(filePath1.toString().equals("/Users/raresrosu/Desktop/VideosDB/tema/test_db/test_files/single_query_most_viewed_movie.json")) {
//                            for(User user : myUsers) {
//                                for(Map.Entry<String, Integer> entry : user.getHistory().entrySet()) {
//                                    Movie possibleMovie = searchMovie(filteredMovies, entry.getKey());
//                                    if(possibleMovie != null) {
//                                        possibleMovie.incrementViews(entry.getValue());
//                                        System.out.println("Movie : " + possibleMovie.getTitle() + " Views " + possibleMovie.getViews());
//                                    }
//                                }
//                            }
//                        }
                        for(User user : myUsers) {
                            for(Map.Entry<String, Integer> entry : user.getHistory().entrySet()) {
                                Movie possibleMovie = searchMovie(filteredMovies, entry.getKey());
                                if(possibleMovie != null) {
                                    possibleMovie.incrementViews(entry.getValue());
                                }
                            }
                        }
                        List<Movie> moviesWithoutViews = new ArrayList<>();
                        for(Movie movie : filteredMovies) {
                            if(movie.getViews() == 0) {
                                moviesWithoutViews.add(movie);
                            }
                        }
                        for(Movie movie : moviesWithoutViews) {
                            filteredMovies.remove(movie);
                        }
                        sortMoviesByViewsAsc(filteredMovies);
                        if(action.getSortType().equals("desc")) {
                            Collections.reverse(filteredMovies);
                        }
                        int i;
                        ArrayList<String> names = new ArrayList<>();
                        int nr_max = action.getNumber();
                        if(action.getNumber() > filteredMovies.size()) {
                            nr_max = filteredMovies.size();
                        }
                        for(i = 0; i < nr_max; i++) {
                            names.add(filteredMovies.get(i).getTitle());
                        }
                        message = message + names.toString();
                    }
                    output.put("message", message);
                    arrayResult.add(output);
                }
                if(action.getObjectType().equals("shows")) {
                    JSONObject output = new JSONObject();
                    output.put("id", action.getActionId());
                    List<Series> filteredSeries;
                    if(action.getCriteria().equals("ratings")) {
                        // selectam doar filmele care au rating
                        filteredSeries = new ArrayList<>();
                        for(Series serial : mySeries) {
                            serial.filterSeriesByRating(filteredSeries);
                        }
                    } else {
                        filteredSeries = new ArrayList<>(mySeries);
                    }
                    List<String> year = action.getFilters().get(0);
                    List<String> genre = action.getFilters().get(1);
                    if(year.get(0) != null) {
                        // filtram in functie de year
                        List<Series> seriesToRemove = new ArrayList<>();
                        for(Series serial : filteredSeries) {
                            if(serial.getYear() != Integer.parseInt(year.get(0))) {
                                seriesToRemove.add(serial);
                            }
                        }
                        for(Series serial : seriesToRemove) {
                            filteredSeries.remove(serial);
                        }
                    }
                    if(genre.get(0) != null) {
                        List<Series> seriesToRemove = new ArrayList<>();
                        for(Series serial : filteredSeries) {
                            if(!(serial.getGenres().contains(genre.get(0)))){
                                seriesToRemove.add(serial);
                            }
                        }
                        for(Series serial : seriesToRemove) {
                            filteredSeries.remove(serial);
                        }
                    }
                    // calculam durata totala pentru fiecare serial
                    for(Series serial : filteredSeries) {
                        serial.calculateTotalDuration();
                    }
                    String message = new String("Query result: ");
                    // am aplicat toate filtrele => facem sortarea
                    if(action.getCriteria().equals("ratings")) {
                        sortSeriesByRatingAsc(filteredSeries);
                        if(action.getSortType().equals("desc")) {
                            Collections.reverse(filteredSeries);
                        }
                        int i;
                        ArrayList<String> names = new ArrayList<>();
                        int nr_max = action.getNumber();
                        if(action.getNumber() > filteredSeries.size()) {
                            nr_max = filteredSeries.size();
                        }
                        for(i = 0; i < nr_max; i++) {
                            names.add(filteredSeries.get(i).getTitle());
                        }
                        message = message + names.toString();
                    }
                    if(action.getCriteria().equals("favorite")) {
                        for(User user : myUsers) {
                            for(String title : user.getFavoriteMovies()) {
                                Series serial = searchSeries(filteredSeries, title);
                                if(serial != null) {
                                    serial.incrementFavoriteOccurences();
                                }
                            }
                        }
                        List<Series> serialsWithoutFavOcc = new ArrayList<>();
                        for(Series serial : filteredSeries) {
                            if(serial.getFavoriteOccurences() == 0) {
                                serialsWithoutFavOcc.add(serial);
                            }
                        }
                        for(Series serial : serialsWithoutFavOcc) {
                            filteredSeries.remove(serial);
                        }
                        sortSeriesByFavoriteAsc(filteredSeries);
                        if(action.getSortType().equals("desc")) {
                            Collections.reverse(filteredSeries);
                        }
                        int i;
                        ArrayList<String> names = new ArrayList<>();
                        int nr_max = action.getNumber();
                        if(action.getNumber() > filteredSeries.size()) {
                            nr_max = filteredSeries.size();
                        }
                        for(i = 0; i < nr_max; i++) {
                            names.add(filteredSeries.get(i).getTitle());
                        }
                        message = message + names.toString();
                    }
                    if(action.getCriteria().equals("longest")) {
                        sortSeriesByLongestAsc(filteredSeries);
                        if(action.getSortType().equals("desc")) {
                            Collections.reverse(filteredSeries);
                        }
                        int i;
                        ArrayList<String> names = new ArrayList<>();
                        int nr_max = action.getNumber();
                        if(action.getNumber() > filteredSeries.size()) {
                            nr_max = filteredSeries.size();
                        }
                        for(i = 0; i < nr_max; i++) {
                            names.add(filteredSeries.get(i).getTitle());
                        }
                        message = message + names.toString();
                    }
                    if(action.getCriteria().equals("most_viewed")) {
                        for(User user : myUsers) {
                            for(Map.Entry<String, Integer> entry : user.getHistory().entrySet()) {
                                Series possibleSerial = searchSeries(filteredSeries, entry.getKey());
                                if(possibleSerial != null) {
                                    possibleSerial.incrementViews(entry.getValue());
                                }
                            }
                        }
                        List<Series> seriesWithoutViews = new ArrayList<>();
                        for(Series serial : filteredSeries) {
                            if(serial.getViews() == 0) {
                                seriesWithoutViews.add(serial);
                            }
                        }
                        for(Series serial : seriesWithoutViews) {
                            filteredSeries.remove(serial);
                        }
                        sortSeriesByViewsAsc(filteredSeries);
                        if(action.getSortType().equals("desc")) {
                            Collections.reverse(filteredSeries);
                        }
                        int i;
                        ArrayList<String> names = new ArrayList<>();
                        int nr_max = action.getNumber();
                        if(action.getNumber() > filteredSeries.size()) {
                            nr_max = filteredSeries.size();
                        }
                        for(i = 0; i < nr_max; i++) {
                            names.add(filteredSeries.get(i).getTitle());
                        }
                        message = message + names.toString();
                    }
                    output.put("message", message);
                    arrayResult.add(output);
                }
            }

        }
        fileWriter.closeJSON(arrayResult);
    }
}
