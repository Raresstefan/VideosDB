package main;

import actor.ActorsAwards;
import fileio.ActorInputData;

import java.util.ArrayList;
import java.util.Map;

/**
 * Information about an actor, retrieved from parsing the input test files
 * <p>
 * DO NOT MODIFY
 */
public class Actor {
    /**
     * actor name
     */
    private String name;
    /**
     * description of the actor's career
     */
    private String careerDescription;
    /**
     * videos starring actor
     */
    private ArrayList<String> filmography;
    /**
     * awards won by the actor
     */
    private Map<ActorsAwards, Integer> awards;
    /**
     * average grade based on ratings from movies and series
     */
    private Double averageGrade;

    public Actor(final ActorInputData actor) {
        this.name = actor.getName();
        this.careerDescription = actor.getCareerDescription();
        this.filmography = actor.getFilmography();
        this.awards = actor.getAwards();
        this.averageGrade = 0.0;
    }
    /**
     * getter for name
     */
    public String getName() {
        return name;
    }
    /**
     * setter for name
     */
    public void setName(final String name) {
        this.name = name;
    }
    /**
     * getter for filmography (list)
     */
    public ArrayList<String> getFilmography() {
        return filmography;
    }
    /**
     * setter for filmography (list)
     */
    public void setFilmography(final ArrayList<String> filmography) {
        this.filmography = filmography;
    }
    /**
     * getter for awards (map)
     */
    public Map<ActorsAwards, Integer> getAwards() {
        return awards;
    }
    /**
     * getter for careerDescription
     */
    public String getCareerDescription() {
        return careerDescription;
    }
    /**
     * getter for averageGrade
     */
    public Double getAverageGrade() {
        return averageGrade;
    }
    /**
     * setter for careerDescription
     */
    public void setCareerDescription(final String careerDescription) {
        this.careerDescription = careerDescription;
    }
    /**
     * toString method for this class
     */
    @Override
    public String toString() {
        return "ActorInputData{"
                + "name='" + name + '\''
                + ", careerDescription='"
                + careerDescription + '\''
                + ", filmography=" + filmography + '}';
    }
}
