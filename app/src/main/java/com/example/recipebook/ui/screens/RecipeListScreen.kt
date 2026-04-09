package com.example.recipebook.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.navigation.NavController
import com.example.recipebook.data.RecipeState
import com.example.recipebook.ui.viewmodel.RecipeUiState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RecipeListScreen(
    uiState: RecipeUiState,
    onSearch: (String) -> Unit,
    onFilterChange: (RecipeState?) -> Unit,
    onRecipeClick: (Int) -> Unit
) {

    val wantCount = uiState.recipes.count { it.state == RecipeState.WANT_TO_COOK }
    val cookingCount = uiState.recipes.count { it.state == RecipeState.COOKING }
    val cookedCount = uiState.recipes.count { it.state == RecipeState.COOKED }

    Column {

        Text(
            text = "Recipe Book",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(16.dp)
        )

        TextField(
            value = uiState.searchQuery,
            onValueChange = onSearch,
            label = { Text("Поиск") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        Row(
            modifier = Modifier
                .padding(8.dp)
                .horizontalScroll(rememberScrollState())
        ) {

            FilterButton("Все", uiState.selectedFilter == null) {
                onFilterChange(null)
            }

            FilterButton("Хочу", uiState.selectedFilter == RecipeState.WANT_TO_COOK) {
                onFilterChange(RecipeState.WANT_TO_COOK)
            }

            FilterButton("Готовлю", uiState.selectedFilter == RecipeState.COOKING) {
                onFilterChange(RecipeState.COOKING)
            }

            FilterButton("Готово", uiState.selectedFilter == RecipeState.COOKED) {
                onFilterChange(RecipeState.COOKED)
            }
        }

        Column(modifier = Modifier.padding(8.dp)) {
            Text("Статистика:")
            Text("Хочу: $wantCount")
            Text("Готовлю: $cookingCount")
            Text("Готово: $cookedCount")
        }

        LazyColumn {

            items(uiState.recipes) { recipe ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable {
                            onRecipeClick(recipe.id)
                        }
                ) {

                    Column(modifier = Modifier.padding(16.dp)) {

                        Text(recipe.title)
                        Text("Время: ${recipe.time}")
                        Text("Сложность: ${recipe.difficulty}")

                        Text(
                            when (recipe.state) {
                                RecipeState.WANT_TO_COOK -> "Хочу"
                                RecipeState.COOKING -> "Готовлю"
                                RecipeState.COOKED -> "Готово"
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun FilterButton(text: String, isSelected: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected)
                MaterialTheme.colorScheme.primary
            else
                MaterialTheme.colorScheme.secondary
        ),
        modifier = Modifier.padding(end = 8.dp)
    ) {
        Text(text)
    }
}