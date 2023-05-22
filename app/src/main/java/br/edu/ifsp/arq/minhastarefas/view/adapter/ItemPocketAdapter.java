package br.edu.ifsp.arq.minhastarefas.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.util.List;

import br.edu.ifsp.arq.minhastarefas.R;
import br.edu.ifsp.arq.minhastarefas.model.entities.Tarefa;
import br.edu.ifsp.arq.minhastarefas.mvp.MainMVP;

public class ItemPocketAdapter extends ArrayAdapter {
    private LayoutInflater inflater;
    private MainMVP.Presenter presenter;

    public ItemPocketAdapter(Context context, List<Tarefa> data, MainMVP.Presenter presenter){
        super(context, R.layout.item_list_tarefa, data);
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.presenter = presenter;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        final ViewHolder holder;
        if(convertView == null){
            convertView = inflater.inflate(R.layout.item_list_tarefa, null);
            holder = new ViewHolder();
            holder.taskTitleView = convertView.findViewById(R.id.text_nome_tarefa);
            holder.dateTextView = convertView.findViewById(R.id.text_data);
            holder.editImageView = convertView.findViewById(R.id.image_editar);
            holder.deleteImageView = convertView.findViewById(R.id.image_delete);
            holder.priorityImageView = convertView.findViewById(R.id.image_flag_prioridade);

            holder.priorityImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    starClick(position);
                }
            });

            holder.editImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pencilClick(position);
                }
            });

            holder.deleteImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteClick(position);
                }
            });


            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        Tarefa tarefa = (Tarefa) getItem(position);
        holder.taskTitleView.setText(tarefa.getNomeTarefa());

        if(tarefa.isImportant()){
            holder.priorityImageView.setColorFilter(getContext().getColor(R.color.pink));
        }else{
            holder.priorityImageView.setColorFilter(getContext().getColor(R.color.white));
        }
        return convertView;
    }

    private void starClick(int position){
        Tarefa tarefa = (Tarefa) getItem(position);
        presenter.favoriteTarefa(tarefa);
        notifyDataSetChanged();
    }

    private void pencilClick(int position){
        Tarefa tarefa = (Tarefa) getItem(position);
        presenter.openDetails(tarefa);
        notifyDataSetChanged();
    }

    private void deleteClick(int position){
        Tarefa tarefa = (Tarefa) getItem(position);
        presenter.deleteTask(tarefa);
        notifyDataSetChanged();
    }

    private static class ViewHolder{
        private TextView taskTitleView;
        private TextView dateTextView;
        private ImageView priorityImageView;
        public ImageView editImageView;
        private ImageView deleteImageView;

    }
}
