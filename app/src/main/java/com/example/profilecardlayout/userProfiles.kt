package com.example.profilecardlayout

data class userProfiles( val id: Int,
                         val name:String,
                        val status: Boolean,
                        // for internet  image loadding
                        // val pictureUrl :String
                        val drawableId:Int)

val listOfProfile= arrayListOf(
    userProfiles(0,"Barney Stinson",true, R.drawable.barney ),
    userProfiles(1,"Ted Mosby",false, R.drawable.ted ),
    userProfiles(2,"Marshall Erikson",true, R.drawable.marshall ),
    userProfiles(3,"Michael Scott",false, R.drawable.michael_scott ),
    userProfiles(4,"Barney Stinson",true, R.drawable.barney ),
    userProfiles(5,"Ted Mosby",false, R.drawable.ted ),
    userProfiles(6,"Marshall Erikson",true, R.drawable.marshall ),
    userProfiles(7,"Michael Scott",false, R.drawable.michael_scott ),
    userProfiles(8,"Barney Stinson",true, R.drawable.barney ),
    userProfiles(9,"Ted Mosby",false, R.drawable.ted ),
    userProfiles(10,"Marshall Erikson",true, R.drawable.marshall ),
    userProfiles(11,"Michael Scott",false, R.drawable.michael_scott ),


)