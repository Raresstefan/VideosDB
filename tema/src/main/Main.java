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
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The entry point to this homework. It runs the checker that tests your implentation.
 */


public final class Main {
    /**
     * searches a user by it's name in the list of users
     */
    public static User searchUser(final List<User> users, final String username) {
        for (User currUser : users) {
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
        for (Series currSeries : series) {
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
        for (Movie currMovie : movies) {
            if (currMovie.getTitle().equals(title)) {
                return currMovie;
            }
        }
        return null;
    }
    /**
     * Populate the actors list with the values from input
     */
    public static ArrayList<Actor> populateActors(final List<ActorInputData> actorsFromInput) {
        ArrayList<Actor> actors = new ArrayList<>();
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
        for (UserInputData user : usersFromInput) {
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

        for (File file : Objects.requireNonNull(directory.listFiles())) {

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
        ArrayList<Actor> myActors = populateActors(actors);
        List<User> myUsers = populateUsers(users);
        List<Series> mySeries = populateSeries(serials);
        List<Movie> myMovies = populateMovies(movies);
        for (ActionInputData action : actions) {
            if (action.getType() != null) {
                JSONObject output = new JSONObject();
                if (action.getActionType().equals("command")) {
                    // This is for commands
                    output.put("id", 1);
                    User user = searchUser(myUsers, action.getUsername());
                    String message = new String();
                    if (action.getType().equals("favorite")) {
                        message = user.favorite(action.getTitle());
                    }
                    if (action.getType().equals("view")) {
                        int increment = 1;
                        Series serial = searchSeries(mySeries, action.getTitle());
                        if (serial != null) {
                            increment = serial.getNumberSeason();
                            serial.incrementViews(increment);
                        } else {
                            Movie movie = searchMovie(myMovies,action.getTitle());
                            movie.incrementViews(increment);
                        }
                        message = user.view(action.getTitle(), increment);
                    }
                    if (action.getType().equals("rating")) {
//                        // verificam intai ce tip de video avem (film sau serial)
                        if(action.getSeasonNumber() == 0) {
                            // este film
                            Movie movie = searchMovie(myMovies, action.getTitle());
                            message = movie.addRating(user, action.getGrade());
                        }
                        else {
                            Series serial = searchSeries(mySeries, action.getTitle());
                            message = serial.addRating(user, action.getGrade(), action.getSeasonNumber());
                        }
                    }
                    output.put("message", message);
                    arrayResult.add(output);
                }
                // next TODO: implement queries
            }

        }
        fileWriter.closeJSON(arrayResult);
    }
}
