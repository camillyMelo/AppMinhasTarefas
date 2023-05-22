package br.edu.ifsp.arq.minhastarefas.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.edu.ifsp.arq.minhastarefas.R;
import br.edu.ifsp.arq.minhastarefas.model.entities.Tarefa;
import br.edu.ifsp.arq.minhastarefas.mvp.MainMVP;
import br.edu.ifsp.arq.minhastarefas.view.RecyclerItemClickListener;


public class ItemPocketRecyclerAdapter extends RecyclerView.Adapter<ItemPocketRecyclerAdapter.ViewHolder> {
    private Context context;
    private MainMVP.Presenter presenter;
    private List<Tarefa> data;
    private static RecyclerItemClickListener clickListener;

    public ItemPocketRecyclerAdapter(Context context, List<Tarefa> data, MainMVP.Presenter presenter){
        this.context = context;
        this.presenter = presenter;
        this.data = data;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_tarefa, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Tarefa tarefa = data.get(position);
        holder.titleTextView.setText(tarefa.getNomeTarefa());
     switch (tarefa.getPrioridade()) {
            case 1:
                holder.priorityImageView.setColorFilter(context.getColor(R.color.pink_high));
                break;
            case 2:
                holder.priorityImageView.setColorFilter(context.getColor(R.color.pink_medium));
                break;
            case 3:
                holder.priorityImageView.setColorFilter(context.getColor(R.color.pink_low));
                break;
            default:
                holder.priorityImageView.setColorFilter(context.getColor(R.color.white));
                break;
        }
        holder.priorityImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                starClick(tarefa);
            }
        });

        holder.editImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pencilClick(tarefa);
            }
        });

        holder.deleteImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteClick(tarefa);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public void setClickListener(RecyclerItemClickListener listener){
        clickListener = listener;
    }

    private void starClick(Tarefa tarefa){
        presenter.favoriteTarefa(tarefa);
        notifyDataSetChanged();
    }

    private void pencilClick(Tarefa task){
        presenter.openDetails(task);
        notifyDataSetChanged();
    }

    private void deleteClick(Tarefa task){
        presenter.deleteTask(task);
        notifyDataSetChanged();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView titleTextView;
        public ImageView priorityImageView;
        public  ImageView editImageView;
        public ImageView deleteImageView;



        public ViewHolder(View itemView){
            super(itemView);
            titleTextView = itemView.findViewById(R.id.text_nome_tarefa);
            priorityImageView = itemView.findViewById(R.id.image_flag_prioridade);
            editImageView = itemView.findViewById(R.id.image_editar);
            deleteImageView = itemView.findViewById(R.id.image_delete);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(clickListener != null){
                clickListener.onItemClick(getAdapterPosition());
            }
        }
    }
}
