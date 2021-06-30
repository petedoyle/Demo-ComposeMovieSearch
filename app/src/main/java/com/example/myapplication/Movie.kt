package com.example.myapplication

data class Movie(
    val title: String,
    val year: String
) {
    companion object {
        val MOVIE_TEST_DATA = listOf(
            Movie("Star Wars: Episode IV - A New Hope", "1977"),
            Movie("Star Wars: Episode V - The Empire Strikes Back", "1980"),
            Movie("Rush Hour", "1998"),
            Movie("Rush Hour 2", "2001"),
            Movie("The Lord of the Rings: The Fellowship of the Ring", "2001"),
            Movie("The Nightmare Before Christmas", "1993"),
            Movie("Home Alone", "1990"),
            Movie("Home Alone 2", "2012"),
            Movie("The Wolf of Wall Street", "2013"),
            Movie("Meet The Parents", "2000"),
            Movie("Billy Madison", "1995"),
            Movie("Happy Gilmore", "1996"),
            Movie("Mr. Deeds", "2002"),
            Movie("Spider-Man: Into the Spider-Verse", "2018"),
            Movie("The Edge", "1997")
        )
    }
}