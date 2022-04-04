# FilmQueryProject

  -- Developed for Skill Distillery Bootcamp Cohort 32 --

### Description

  This is a console-based application which uses object-relational mapping (ORM) to retrieve data from a SQL database to construct Java objects.  The end result is a command-line application that retrieves and displays film data via a menu.  Users can look up a film by its ID (film.id), or they can enter a search keyword that will search for any matches containing that keyword within its title (film.title) and (film.description) fields.  They may view data not included in the 'film' table - such as a list of actors (cast) for that film (provided by joining the film, film_actor, and actor tables) as well as language (provided by joining the film and language tables), category (provided by joining the film, film_category, and category tables), and details specific to that copy in the rental inventory (provided by joining the film and inventory_item tables).
### Cool Features

  If a search returns many results (SETTINGS.MAX_RESULTS by default set to 20) rather than printing all those matches, a user is asked how many they would like to view and starting from what result number.  This functionality could be applied to a future version i.e. outputting to a HTML file and showing multiple pages of results.

### Technologies Used

- Java
- MySql
- MAMP
- sdvid database
- Maven
- Eclipse
- Atom
- Github
- Terminal
- Google Chrome
- MacBook Pro Retina 2015

### Thoughts For The Future

  I look forward to extending this to more options and to a browser-based application.  I also look forward to experimenting with different queries and possibly creating them dynamically through the code, rather than always using a hard-coded query.
