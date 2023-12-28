package com.example.androidapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import Controller.Adapter;
import Controller.DataBaseHandler;
import Model.MachineLearning;
import Utils.Utils;

public class Recycler extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Adapter adapter;
    private DataBaseHandler dataBaseHandler;
    private EditText searchEditText;
    private Button searchButton, edit, deleteButton;


    // Méthode appelée à la création de l'activité
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Définition du layout associé à l'activité
        setContentView(R.layout.recycler_view_data_base);

        // Initialisation des éléments d'interface utilisateur
        recyclerView = findViewById(R.id.recyclerView);
        searchEditText = findViewById(R.id.searchEditText);
        searchButton = findViewById(R.id.searchButton);
        deleteButton = findViewById(R.id.delete);

        // Initialisation du gestionnaire de base de données
        dataBaseHandler = new DataBaseHandler(this, Utils.DATABASE_NAME, null, Utils.DATABASE_VERSION);

        // Définition d'un écouteur de clic pour le bouton de recherche
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Récupération du texte saisi pour la recherche
                String searchText = searchEditText.getText().toString();
                // Recherche dans la base de données en fonction du texte saisi
                List<MachineLearning> searchResults = dataBaseHandler.searchByAnyAtt(searchText);

                // Mise à jour du RecyclerView avec les résultats de la recherche
                adapter.setData(searchResults);
                adapter.notifyDataSetChanged();
            }
        });

        // Vérification si le bouton de suppression n'est pas null
        if (deleteButton != null) {
            // Le bouton n'est pas null, on peut définir un écouteur de clic
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Récupération de la position de l'élément actuellement sélectionné dans l'adaptateur
                    int selectedPosition = adapter.getSelectedPosition();

                    // Vérification si une position est sélectionnée
                    if (selectedPosition != RecyclerView.NO_POSITION) {
                        // Suppression de l'élément de la base de données
                        long itemId = adapter.getItemId(selectedPosition);
                        dataBaseHandler.deleteData(itemId);

                        // Mise à jour de la liste dans l'activité
                        List<MachineLearning> machineLearningList = dataBaseHandler.getAllData();
                        adapter.setData(machineLearningList);
                        adapter.notifyDataSetChanged();
                    }
                }
            });
        }








        // Initialisation du RecyclerView, du gestionnaire de base de données et chargement des données depuis la base
        recyclerView = findViewById(R.id.recyclerView);
        dataBaseHandler = new DataBaseHandler(this, Utils.DATABASE_NAME, null, Utils.DATABASE_VERSION);
        List<MachineLearning> machineLearningList = dataBaseHandler.getAllData();

        // Création et configuration de l'adaptateur
        adapter = new Adapter(this, machineLearningList, dataBaseHandler);
        recyclerView.setAdapter(adapter);

        // Configuration du gestionnaire de disposition (par exemple, LinearLayoutManager)
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }
}
