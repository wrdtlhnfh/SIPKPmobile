package app.sipkp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import app.sipkp.R;
import app.sipkp.pojo.Task;

/**
 *
 */
public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {
    private Context context;
    private List<Task> tasks;

    public TaskAdapter(Context context, List<Task> tasks){
        this.context = context;
        this.tasks = tasks;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context)
        .inflate(R.layout.content_recycler_task, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Task task = tasks.get(i);
        viewHolder.dateTxt.setText(task.getTanggal());
        viewHolder.taskTxt.setText(task.getKegiatan());
        if (task.getCatatan()!= null){
            viewHolder.reportTxt.setText(task.getCatatan());
        } else {
            viewHolder.reportTxt.setText(" - ");
        }
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView dateTxt, taskTxt, reportTxt;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            dateTxt = itemView.findViewById(R.id.content_date_txt);
            taskTxt = itemView.findViewById(R.id.content_task_txt);
            reportTxt = itemView.findViewById(R.id.content_report_txt);
        }
    }
}
