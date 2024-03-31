package com.example.myapplication.EX6Package;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.ArrayList;

public class EX6 extends AppCompatActivity {
    private ArrayList<Hero> mHeros ;
    private RecyclerView mRecyclerHero;
    private HeroAdapter mHeroAdapter ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ex6);
        mRecyclerHero = findViewById(R.id.recyclerHero);
        mHeros = new ArrayList<>();
        createHeroList();
        mHeroAdapter = new HeroAdapter(this,mHeros);
        mRecyclerHero.setAdapter(mHeroAdapter);
        mRecyclerHero.setLayoutManager(new LinearLayoutManager(this)); // Set linear type
    }
    private void createHeroList() {
        mHeros.add(new Hero("Thor",R.drawable.thor));
        mHeros.add(new Hero("IronMan",R.drawable.ironman));
        mHeros.add(new Hero("Hulk",R.drawable.hulk));
        mHeros.add(new Hero("SpiderMan",R.drawable.spiderman));
    }
}
