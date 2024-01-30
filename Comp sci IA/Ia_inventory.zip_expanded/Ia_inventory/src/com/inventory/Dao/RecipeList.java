package com.inventory.Dao;

import javax.swing.DefaultListModel;
import javax.swing.JList;

public class RecipeList extends JList<Recipe> {
    private DefaultListModel<Recipe> model;

    public RecipeList() {
        model = new DefaultListModel<>();
        setModel(model);
    }

    public void addRecipe(Recipe recipe) {
        model.addElement(recipe);
    }

    public void removeRecipe(Recipe recipe) {
        model.removeElement(recipe);
    }

    public Recipe getSelectedRecipe() {
        return getSelectedValue();
    }
}
