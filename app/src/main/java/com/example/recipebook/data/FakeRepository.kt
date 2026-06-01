package com.example.recipebook.data

object FakeRepository {
    val recipes = listOf(
        Recipe(1, "Паста Карбонара", "Итальянская классика",
            listOf("Паста", "Яйца", "Бекон"), "20 мин", "Легко", RecipeState.WANT_TO_COOK),

        Recipe(2, "Салат Цезарь", "Свежий салат",
            listOf("Курица", "Салат", "Соус"), "15 мин", "Средне", RecipeState.COOKED),

        Recipe(3, "Борщ", "Традиционный суп",
            listOf("Свекла", "Картофель", "Мясо"), "60 мин", "Сложно", RecipeState.COOKING),

        Recipe(4, "Омлет", "Быстрый завтрак",
            listOf("Яйца", "Молоко"), "10 мин", "Легко", RecipeState.WANT_TO_COOK),

        Recipe(5, "Пицца", "Домашняя пицца",
            listOf("Тесто", "Сыр", "Томат"), "40 мин", "Средне", RecipeState.COOKING),

        Recipe(6, "Суп-пюре", "Кремовый суп",
            listOf("Овощи", "Сливки"), "30 мин", "Легко", RecipeState.COOKED)
    )
}