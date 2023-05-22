package br.edu.ifsp.arq.minhastarefas.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import br.edu.ifsp.arq.minhastarefas.R;
import br.edu.ifsp.arq.minhastarefas.mvp.TarefaDetailsMVP;
import br.edu.ifsp.arq.minhastarefas.presenter.TarefaDetailsPresenter;
import br.edu.ifsp.arq.minhastarefas.utils.Constantes;

public class TarefaActivity extends AppCompatActivity implements TarefaDetailsMVP.View, View.OnClickListener{

    private TarefaDetailsMVP.Presenter presenter;

    private EditText mNomeTarefaEditText;
    private Spinner mPrioridadeSpinner;
    String prioridadeTarefaValue;
    private TextView mDataTarefaTextView;
    private Button mSalvarButton;

    private String mOldNome;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarefa);

        presenter = new TarefaDetailsPresenter(this);
        findViews();
        setListener();
        setToolbar();

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            mOldNome = bundle.getString(Constantes.KEY_NOME);
            String d = bundle.getString(Constantes.KEY_DATA);
            int p = bundle.getInt(Constantes.KEY_PRIORIDADE, -1);

            mNomeTarefaEditText.setText(mOldNome);
            mDataTarefaTextView.setText(d);
            mPrioridadeSpinner.setSelection(setarIndex(p));
        }else{
            mOldNome = "";
        }

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.priority_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mPrioridadeSpinner.setAdapter(adapter);
        mPrioridadeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                prioridadeTarefaValue = mPrioridadeSpinner.getSelectedItem().toString();

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
    @Override
    protected void onStart() {
        super.onStart();
        presenter.verifyUpdate();
    }

    @Override
    protected void onDestroy() {
        presenter.deatach();
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        int pri = getPriorityFromViews();
        if(v == mSalvarButton){
            presenter.saveTarefa(
                    mNomeTarefaEditText.getText().toString(),
                    mDataTarefaTextView.getText().toString(),
                    pri);

        }
       
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            setResult(RESULT_CANCELED);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void updateUI(String nomeTarefa, String data, int prioridade) {
        mNomeTarefaEditText.setText(nomeTarefa);
        mDataTarefaTextView.setText(data);
prioridadeTarefaValue = getPriorityInt(prioridade);
    }

    @Override
    public Bundle getBundle() {
        return getIntent().getExtras();
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void close() {
        presenter.deatach();
        finish();
    }

    private void setToolbar() {
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setListener() {
        mSalvarButton.setOnClickListener(this);
        mDataTarefaTextView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(TarefaActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                mDataTarefaTextView.setBackground(null);
                                String date = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year ;
                                mDataTarefaTextView.setText(date);
                            }
                        }, year, month, day);
                datePickerDialog.show();
            }



        });
    }

    private void findViews() {
        mNomeTarefaEditText = findViewById(R.id.edit_nome_tarefa);
        mPrioridadeSpinner = findViewById(R.id.spinner_prioridade);
        mDataTarefaTextView = findViewById(R.id.text_edit_data);
        mSalvarButton = findViewById(R.id.button_salvar);

    }

    public int getPriorityFromViews() {
        int priority = 1;
        switch (prioridadeTarefaValue) {
            case "High":
                priority = Constantes.PRIORITY_HIGH;
                break;
            case "Medium":
                priority = Constantes.PRIORITY_MEDIUM;
                break;
            case "Low":
                priority = Constantes.PRIORITY_LOW;
        }
        return priority;
    }
    public String getPriorityInt(int pri) {
         String priority = null ;
        switch (pri) {
            case 1:
                priority = Constantes.PRIORITY_HIGH_STRING;
                break;
            case 2:
                priority = Constantes.PRIORITY_MEDIUM_STRING;
                break;
            case 3:
                priority = Constantes.PRIORITY_LOW_STRING;
        }
        return priority;
    }
    private int setarIndex(int prioridade) {
        int indexPosition = -1;
        switch (prioridade) {
            case 1:
                indexPosition = 0;
                break;
            case 2:
                indexPosition = 1;
                break;
            case 3:
                indexPosition = 2;
                break;
            default:
                indexPosition = 0;
        }
        return indexPosition;
    }

}