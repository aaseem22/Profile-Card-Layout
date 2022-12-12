package com.example.profilecardlayout

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.example.profilecardlayout.ui.theme.ProfileCardLayoutTheme
import com.example.profilecardlayout.ui.theme.Shapes
import com.example.profilecardlayout.ui.theme.aestheticPurple
import com.example.profilecardlayout.ui.theme.lightgreen200

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
           ProfileCardLayoutTheme {
               UserApplicationNavigation()
           }
        }
    }
}

@Composable
fun UserApplicationNavigation(userProfiles:List<userProfiles> = listOfProfile){
    val navController = rememberNavController()
   NavHost(navController = navController , startDestination = "users_list" ){
        composable("users_list"){
            UserListScreen(userProfiles, navController )
        }
       composable(route="users_details/{userId}",
           arguments = listOf(navArgument("userId"){
               type= NavType.IntType
           })
       ){ navBackStackEntry->
           UserProfileDetailsScreen(
               navBackStackEntry.arguments!!.getInt("userId"), navController)
       }
   }

}

@Composable
fun UserListScreen(userProfiles:List<userProfiles> = listOfProfile ,
                   navController:NavHostController?) {
    Scaffold(topBar = {AppBar(
        title = "The Bros",
        icon = Icons.Default.Home
    ){
        // nothing to do
    }
    }) {
        Surface(
            modifier =Modifier.fillMaxSize(),
        ) {
            LazyColumn(){
                items(userProfiles){
                        userProfile->
                    ProfileCard(userProfile = userProfile){
                      navController?.navigate("users_details/${userProfile.id}")
                    }
                }
            }
           }

        }
}

@Composable
fun UserProfileDetailsScreen(userId: Int, navController:NavHostController?) {
    val userProfiles= listOfProfile.first{
        userProfile -> userId == userProfile.id
    }
    Scaffold(topBar = { AppBar(
        title = "Bro's details",
        icon = Icons.Default.ArrowBack
    ){
        navController?.navigateUp()
    }
    }) {
        Surface(
            modifier =Modifier.fillMaxSize(),
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top

            ) {
                ProfilePic(userProfiles.drawableId,userProfiles.status,
                    240.dp)
                ProfileContent(userProfiles.name, userProfiles.status,
                    Alignment.CenterHorizontally)
            }

        }
    }

}


@Composable
fun AppBar(title: String, icon: ImageVector, iconClick:()-> Unit){
    TopAppBar(
        navigationIcon = {
            Icon(icon,
            contentDescription = "home",
            modifier = Modifier.padding(10.dp)
                                .clickable { iconClick.invoke() })
                         },
        title = { Text(title) },
        backgroundColor = aestheticPurple,
        contentColor = Color.White
        )
}

@Composable
fun ProfileCard(userProfile: userProfiles, click:() -> Unit){
    Card(modifier = Modifier
        .padding(top = 8.dp, end = 16.dp, start = 16.dp, bottom = 4.dp)
        .fillMaxWidth()
        .clickable { click.invoke() }
        .wrapContentHeight(align = Alignment.Top),
    elevation = 8.dp,
        backgroundColor = Color.White,
        shape = Shapes.medium
        ) {
       Row(
           modifier = Modifier.fillMaxWidth(),
           verticalAlignment = Alignment.CenterVertically,
           horizontalArrangement = Arrangement.Start

           ) {
           ProfilePic(userProfile.drawableId,userProfile.status,75.dp)
           ProfileContent(userProfile.name, userProfile.status, Alignment.Start)
       }
    }
}

@Composable
fun ProfilePic(drawableId: Int, onlineStatus :Boolean, ImageSize: Dp){
    Card(shape = CircleShape,
        border = BorderStroke(
            color = if (onlineStatus)
            {
                lightgreen200
            }
            else {
                Color.Red
                 },
            width = 2.dp),
        modifier = Modifier
            .padding(4.dp)
            .size(ImageSize)
            .padding(5.dp),
        elevation = 5.dp
        ) {
        Image(
            // using coil library
            painter = rememberImagePainter(
                data = drawableId,
                builder = {
                    transformations(CircleCropTransformation())
                }
            ),
            contentDescription ="content description",
            modifier = Modifier.size(75.dp),


        )
    }


}


@Composable
fun ProfileContent(userName : String, onlineStatus : Boolean, alignment: Alignment.Horizontal){
    Column(
        modifier = Modifier
            .padding(8.dp),
        horizontalAlignment = alignment,
        ) {
        Text(text = userName,
            style= MaterialTheme.typography.h5,
        modifier = Modifier.padding(8.dp))

        // for transparent
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Text(text = if(onlineStatus) {
                "Is Active now!!"
                    }
                else "Offline",
                style = MaterialTheme.typography.body2,
                modifier = Modifier.padding(8.dp)
            )

        }

    }

}


@Preview(showBackground = true)
@Composable
fun DefaultUserListPreview() {
    ProfileCardLayoutTheme {
        UserProfileDetailsScreen(userId = 3, null)
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ProfileCardLayoutTheme {
        UserListScreen(userProfiles = listOfProfile, null)
    }
}
