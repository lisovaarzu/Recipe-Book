package com.example.recipebook

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.recipebook.navigation.NavGraph
import com.example.recipebook.ui.theme.RecipeBookTheme
import com.example.recipebook.ui.viewmodel.RecipeViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.statusBarColor = Color.rgb(11, 11, 13)
        window.navigationBarColor = Color.rgb(11, 11, 13)

        setContent {
            RecipeBookTheme {
                val viewModel: RecipeViewModel = viewModel()
                NavGraph(viewModel)
            }
        }
    }
}