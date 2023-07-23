package com.example.jetpack_compose_all_in_one.demos.instagram

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import com.example.jetpack_compose_all_in_one.demos.DemoDataProvider
import com.example.jetpack_compose_all_in_one.ui.theme.JetpackComposeAllInOneTheme

class InstagramActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = ContextCompat.getColor(this, android.R.color.transparent)
        setContent {
            val posts = remember { DemoDataProvider.tweetList.filter { it.tweetImageId != 0 } }
            val profiles = remember { DemoDataProvider.tweetList }
            JetpackComposeAllInOneTheme {
                InstagramHome(
                    posts = posts,
                    profiles = profiles,
                    onLikeClicked = {},
                    onCommentsClicked = {},
                    onSendClicked = {},
                    onProfileClicked = {},
                    onMessagingClicked = {}
                )
            }
        }
    }

    companion object {
        fun newIntent(context: Context) = Intent(context, InstagramActivity::class.java)
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview4() {
    JetpackComposeAllInOneTheme {
        InstagramHome(
            posts = DemoDataProvider.tweetList.filter { it.tweetImageId != 0 },
            profiles = DemoDataProvider.tweetList,
            onLikeClicked = {},
            onCommentsClicked = {},
            onSendClicked = {},
            onProfileClicked = {},
            onMessagingClicked = {}
        )
    }
}
