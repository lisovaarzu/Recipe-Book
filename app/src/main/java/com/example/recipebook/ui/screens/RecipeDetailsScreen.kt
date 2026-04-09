package com.example.recipebook.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import com.example.recipebook.data.RecipeState
import com.example.recipebook.ui.viewmodel.RecipeUiState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RecipeDetailsScreen(
    recipeId: Int,
    uiState: RecipeUiState,
    onChangeState: (Int, RecipeState) -> Unit,
    onBack: () -> Unit
) {

    val recipe = uiState.recipes.find { it.id == recipeId }

    recipe?.let {

        Column(modifier = Modifier.padding(16.dp)) {

            Text(it.title, style = MaterialTheme.typography.headlineMedium)

            Spacer(modifier = Modifier.height(8.dp))

            Text(it.description)

            Spacer(modifier = Modifier.height(8.dp))

            Text("Ингредиенты:")
            it.ingredients.forEach {
                Text("- $it")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text("Время: ${it.time}")
            Text("Сложность: ${it.difficulty}")

            Spacer(modifier = Modifier.height(16.dp))

            Row {

                Button(onClick = {
                    onChangeState(it.id, RecipeState.WANT_TO_COOK)
                }) {
                    Text("Хочу")
                }

                Spacer(modifier = Modifier.width(8.dp))

                Button(onClick = {
                    onChangeState(it.id, RecipeState.COOKING)
                }) {
                    Text("Готовлю")
                }

                Spacer(modifier = Modifier.width(8.dp))

                Button(onClick = {
                    onChangeState(it.id, RecipeState.COOKED)
                }) {
                    Text("Готово")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = onBack) {
                Text("Назад")
            }
        }
    }
}