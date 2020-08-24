package android.exemple.appartamentoviewer;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class AppartamentAdapter extends RecyclerView.Adapter<AppartamentAdapter.ViewHolder> {


    private LayoutInflater inflater;
    private List<Appartament> appartaments;

    AppartamentAdapter(Context context, List<Appartament> appartaments) {
        this.appartaments = appartaments;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public AppartamentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.appartament_adapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Appartament appartament = appartaments.get(position);
        //holder.imageView.setImageResource(appartament.getImageID());
        holder.nameView.setText(appartament.getTitle());
        holder.priceView.setText(appartament.getCoast()+"$");
        holder.mainInfoView.setText(appartament.getInfo());
        final Appartament app = appartament;
/*
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentOrder = new Intent(inflater.getContext(), OrderActivity.class);
                intentOrder.putExtra("Product", app);
                inflater.getContext().startActivity(intentOrder);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return appartaments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView imageView;
        final TextView nameView, priceView, mainInfoView;

        ViewHolder(View view){
            super(view);
            imageView = (ImageView)view.findViewById(R.id.adapter_mainphoto);
            nameView = (TextView) view.findViewById(R.id.adapter_title);
            priceView = (TextView) view.findViewById(R.id.adapter_cost);
            mainInfoView = (TextView) view.findViewById(R.id.adapter_info);
        }
    }
}