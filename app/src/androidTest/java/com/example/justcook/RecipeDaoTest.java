package com.example.justcook;

import com.example.justcook.models.Recipe;
import com.example.justcook.room.RecipeDao;
import com.example.justcook.room.RecipeDatabase;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.hilt.android.testing.HiltAndroidRule;
import dagger.hilt.android.testing.HiltAndroidTest;

import static com.google.common.truth.Truth.assertThat;


@HiltAndroidTest
public class RecipeDaoTest {

    @Rule
    public HiltAndroidRule rule = new HiltAndroidRule(this);

    @Inject
    @Named("test_db")
    RecipeDatabase recipeDatabase;
    private RecipeDao recipeDao;

    @Before
    public void setUp()
    {
        rule.inject();
        recipeDao = recipeDatabase.recipeDao();
    }

    @After
    public void tearDown()
    {
        recipeDatabase.close();
    }

    @Test
    public void insertRecipe()
    {
        int initial = recipeDao.getCount();
        Recipe recipe = new Recipe();
        recipe.setName("Dummy recipe");
        recipe.setCategory("Dummy category");
        recipeDao.insert(recipe);
        int after = recipeDao.getCount();
        assertThat(initial).isNotEqualTo(after);
    }
}
