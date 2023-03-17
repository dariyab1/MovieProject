import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class MovieCollection
{
    private ArrayList<Movie> movies;
    private Scanner scanner;
    private ArrayList<String> actors;
    private ArrayList<String> genres;

    public void makeList(ArrayList<String> array,String type){
        if(type.equals("cast")){
            for (Movie movie : movies) {
                String temp = movie.getCast();
                while (temp.indexOf("|") > 0) {
                    int place = temp.indexOf("|");
                    array.add(temp.substring(0, place));
                    temp = temp.substring(place + 1);
                }
                array.add(temp);
            }
        }
        else if(type.equals("genres")){
            for (Movie movie : movies) {
                String temp = movie.getGenres();
                while (temp.indexOf("|") > 0) {
                    int place = temp.indexOf("|");
                    array.add(temp.substring(0, place));
                    temp = temp.substring(place + 1);
                }
                array.add(temp);
            }
        }
        for(int j=0; j<array.size();j++){
            for(int x=j+1;x<array.size();x++){
                if(array.get(j).equals(array.get(x))){
                    array.remove(x);
                    x--;
                }
            }
        }

        for (int t = 1; t < array.size(); t++)
        {
            String temp = array.get(t);

            int possibleIndex = t;
            while (possibleIndex > 0 && temp.compareTo(array.get(possibleIndex - 1)) < 0)
            {
                array.set(possibleIndex, array.get(possibleIndex - 1));
                possibleIndex--;
            }
            array.set(possibleIndex, temp);
        }


    }

    public MovieCollection(String fileName)
    {
        importMovieList(fileName);
        scanner = new Scanner(System.in);

        actors=new ArrayList<String>();
        makeList(actors, "cast");
        genres=new ArrayList<String>();
        makeList(genres, "genres");


    }

    public ArrayList<Movie> getMovies()
    {
        return movies;
    }

    public void menu()
    {
        String menuOption = "";

        System.out.println("Welcome to the movie collection!");
        System.out.println("Total: " + movies.size() + " movies");

        while (!menuOption.equals("q"))
        {
            System.out.println("------------ Main Menu ----------");
            System.out.println("- search (t)itles");
            System.out.println("- search (k)eywords");
            System.out.println("- search (c)ast");
            System.out.println("- see all movies of a (g)enre");
            System.out.println("- list top 50 (r)ated movies");
            System.out.println("- list top 50 (h)igest revenue movies");
            System.out.println("- (q)uit");
            System.out.print("Enter choice: ");
            menuOption = scanner.nextLine();

            if (!menuOption.equals("q"))
            {
                processOption(menuOption);
            }
        }
    }

    private void processOption(String option)
    {
        if (option.equals("t"))
        {
            searchTitles();
        }
        else if (option.equals("c"))
        {
            searchCast();
        }
        else if (option.equals("k"))
        {
            searchKeywords();
        }
        else if (option.equals("g"))
        {
            listGenres();
        }
        else if (option.equals("r"))
        {
            listHighestRated();
        }
        else if (option.equals("h"))
        {
            listHighestRevenue();
        }
        else
        {
            System.out.println("Invalid choice!");
        }
    }

    private void searchTitles()
    {
        System.out.print("Enter a title search term: ");
        String searchTerm = scanner.nextLine();

        // prevent case sensitivity
        searchTerm = searchTerm.toLowerCase();

        // arraylist to hold search results
        ArrayList<Movie> results = new ArrayList<Movie>();

        // search through ALL movies in collection
        for (int i = 0; i < movies.size(); i++)
        {
            String movieTitle = movies.get(i).getTitle();
            movieTitle = movieTitle.toLowerCase();

            if (movieTitle.indexOf(searchTerm) != -1)
            {
                //add the Movie object to the results list
                results.add(movies.get(i));
            }
        }

        // sort the results by title
        sortResults(results);

        // now, display them all to the user
        for (int i = 0; i < results.size(); i++)
        {
            String title = results.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void sortResults(ArrayList<Movie> listToSort)
    {
        for (int j = 1; j < listToSort.size(); j++)
        {
            Movie temp = listToSort.get(j);
            String tempTitle = temp.getTitle();

            int possibleIndex = j;
            while (possibleIndex > 0 && tempTitle.compareTo(listToSort.get(possibleIndex - 1).getTitle()) < 0)
            {
                listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
                possibleIndex--;
            }
            listToSort.set(possibleIndex, temp);
        }
    }

    private void displayMovieInfo(Movie movie)
    {
        System.out.println();
        System.out.println("Title: " + movie.getTitle());
        System.out.println("Tagline: " + movie.getTagline());
        System.out.println("Runtime: " + movie.getRuntime() + " minutes");
        System.out.println("Year: " + movie.getYear());
        System.out.println("Directed by: " + movie.getDirector());
        System.out.println("Cast: " + movie.getCast());
        System.out.println("Overview: " + movie.getOverview());
        System.out.println("User rating: " + movie.getUserRating());
        System.out.println("Box office revenue: " + movie.getRevenue());
    }

    private void searchCast()
    {
        System.out.println("Enter a search term:");
        String search=scanner.nextLine();

        ArrayList<String> nameMatch=new ArrayList<String>();
        for(String actor:actors){
            if(actor.toLowerCase().contains(search.toLowerCase())){
                nameMatch.add(actor);
            }
        }

        for(int m=0; m< nameMatch.size();m++){
            int num=m+1;
            System.out.println(""+num+". "+nameMatch.get(m));

        }

        System.out.println("Choose an actor:");
        int whichOne=scanner.nextInt();

        String name=nameMatch.get(whichOne-1);



        ArrayList<Movie> tempList=new ArrayList<Movie>();
        for (Movie movie : movies) {
            String cast = movie.getCast().toLowerCase();
            if (cast.contains(name.toLowerCase())) {
                tempList.add(movie);
            }
        }
        sortResults(tempList);

        for (int x = 0; x < tempList.size(); x++)
        {
            String title = tempList.get(x).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = x + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = tempList.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();

    }

    private void searchKeywords()
    {

        System.out.println("Enter a keyword search item:");
        String searchItem=scanner.nextLine();

        searchItem=searchItem.toLowerCase();

        ArrayList<Movie> tempList=new ArrayList<Movie>();
        for (int i = 0; i < movies.size(); i++)
        {
            String keyword = movies.get(i).getKeywords();
            keyword = keyword.toLowerCase();

            if (keyword.indexOf(searchItem) != -1)
            {
                //add the Movie object to the results list
                tempList.add(movies.get(i));
            }
        }
        sortResults(tempList);
        for (int i = 0; i < tempList.size(); i++)
        {
            String title = tempList.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }
        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = tempList.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();

    }

    private void listGenres()
    {
        for(int i=0; i<genres.size();i++){
            System.out.println(i+1+". "+genres.get(i));
        }
        System.out.println("Choose a genre (number):");
        int num=scanner.nextInt();
        String searchTerm=genres.get(num-1);
        ArrayList<Movie> temp=new ArrayList<Movie>();
        for(int j=0; j<movies.size();j++){
            if(movies.get(j).getGenres().contains(searchTerm)){
                temp.add(movies.get(j));
            }
        }
        sortResults(temp);
        int place=1;
        for(Movie movie:temp){
            System.out.println(place+". "+movie.getTitle());
            place++;
        }
        System.out.println("Which movie would you like to learn more about?");

        num=scanner.nextInt();
        displayMovieInfo(temp.get(num-1));
    }

    private void listHighestRated()
    {
        ArrayList<Movie> temp=movies;
        for (int j = 1; j < temp.size(); j++)
        {
            Movie tempMovie = temp.get(j);
            double tempRating = tempMovie.getUserRating();

            int possibleIndex = j;
            while (possibleIndex > 0 && tempRating>temp.get(possibleIndex - 1).getUserRating())
            {
                temp.set(possibleIndex, temp.get(possibleIndex - 1));
                possibleIndex--;
            }
            temp.set(possibleIndex, tempMovie);
        }
        ArrayList<Movie> top50=new ArrayList<Movie>();
        for(int i=0; i<50; i++){
            top50.add(temp.get(i));
        }
        int place=1;
        for(Movie movie:top50){
            System.out.println(place+". "+movie.getTitle()+ ", User Rating: "+movie.getUserRating());
            place++;
        }

        System.out.println("Which movie would you like to learn about?");
        int num=scanner.nextInt();
        displayMovieInfo(temp.get(num-1));
    }

    private void listHighestRevenue()
    {
        ArrayList<Movie> temp=movies;
        for (int j = 1; j < temp.size(); j++)
        {
            Movie tempMovie = temp.get(j);
            int tempRevenue = tempMovie.getRevenue();

            int possibleIndex = j;
            while (possibleIndex > 0 && tempRevenue>temp.get(possibleIndex - 1).getRevenue())
            {
                temp.set(possibleIndex, temp.get(possibleIndex - 1));
                possibleIndex--;
            }
            temp.set(possibleIndex, tempMovie);
        }
        ArrayList<Movie> top50=new ArrayList<Movie>();
        for(int i=0; i<50; i++){
            top50.add(temp.get(i));
        }
        int place=1;
        for(Movie movie:top50){
            System.out.println(place+". "+movie.getTitle()+ ", Revenue: "+movie.getRevenue());
            place++;
        }

        System.out.println("Which movie would you like to learn about?");
        int num=scanner.nextInt();
        displayMovieInfo(temp.get(num-1));
    }

    private void importMovieList(String fileName)
    {
        try
        {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();

            movies = new ArrayList<Movie>();

            while ((line = bufferedReader.readLine()) != null)
            {
                String[] movieFromCSV = line.split(",");

                String title = movieFromCSV[0];
                String cast = movieFromCSV[1];
                String director = movieFromCSV[2];
                String tagline = movieFromCSV[3];
                String keywords = movieFromCSV[4];
                String overview = movieFromCSV[5];
                int runtime = Integer.parseInt(movieFromCSV[6]);
                String genres = movieFromCSV[7];
                double userRating = Double.parseDouble(movieFromCSV[8]);
                int year = Integer.parseInt(movieFromCSV[9]);
                int revenue = Integer.parseInt(movieFromCSV[10]);

                Movie nextMovie = new Movie(title, cast, director, tagline, keywords, overview, runtime, genres, userRating, year, revenue);
                movies.add(nextMovie);
            }
            bufferedReader.close();
        }
        catch(IOException exception)
        {
            // Print out the exception that occurred
            System.out.println("Unable to access " + exception.getMessage());
        }
    }
}
