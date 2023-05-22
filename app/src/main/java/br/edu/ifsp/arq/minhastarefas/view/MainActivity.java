package br.edu.ifsp.arq.minhastarefas.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


import br.edu.ifsp.arq.minhastarefas.R;
import br.edu.ifsp.arq.minhastarefas.mvp.MainMVP;
import br.edu.ifsp.arq.minhastarefas.presenter.MainPresenter;

public class MainActivity extends AppCompatActivity implements MainMVP.View{


    private RecyclerView mTarefasRecyclerView;
    private MainMVP.Presenter presenter;
    private FloatingActionButton mActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        setListener();
        presenter = new MainPresenter(this);

    }
    @Override
    protected void onStart() {
        super.onStart();
        presenter.populateList(mTarefasRecyclerView);
    }

    @Override
    protected void onDestroy() {
        presenter.deatach();
        super.onDestroy();
    }

    @Override
    public Context getContext() {
        return this;
    }

    private void findViews(){
        mActionButton = findViewById(R.id.fab_add_tarefa);
        mTarefasRecyclerView = findViewById(R.id.recycler_tarefas);
    }

    private void setListener(){
        mActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.openDetails();
            }
        });


    }


}

