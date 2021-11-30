# VideosDB (POO project)

## Commands

I made functions that verifies if a command can be done or not by a user. For example, a video can be added in the favorite list

of a user just once, so with the function favorite defined in User.java we control which videos can be added or not to favorites.

The same principle is applied with the other commands, each of it has a function that tells if an operation we try to realise can be done

or not... View command uses view function (see User.java) and Rating command uses addRating which is a function defined in Movie.java and Series.java

and works differently for each type of video.

## Querries

Each querry uses the filters depending on the action we wnt to proceed. If the querry has the "actors" object type than we can apply filters for 

words and awards. Same, if the querry has the "movies" object type we can apply filters for year and genre.

Used regex expressions to apply the filter for words, to verify if the text contained in the description of the actor's career contains the 
words specified in the querry.

The second criteria of sorting has the same order as the first one and it's based on the lexicographic order of the names, usernames, titles.

## Recommendations

For the popular, favorite, search recommendation I verify from the beggining if the user we want to apply this on has premium subscription.

In this case, the second citeria of sorting is based on the order of apparition in movies and series lists from database
