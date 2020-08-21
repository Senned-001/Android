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

        View view = inflater.inflate(R.layout.product_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Product product = appartaments.get(position);
        holder.imageView.setImageResource(product.getImageID());
        holder.nameView.setText(product.getName());
        holder.firstPrice.setText("from " + product.getParamPrices()[0] + "$");
        final Product prod = product;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentOrder = new Intent(inflater.getContext(), OrderActivity.class);
                intentOrder.putExtra("Product", prod);
                inflater.getContext().startActivity(intentOrder);
            }
        });
    }

    @Override
    public int getItemCount() {
        return appartaments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView imageView;
        final TextView nameView, firstPrice;

        ViewHolder(View view){
            super(view);
            imageView = (ImageView)view.findViewById(R.id.product_image);
            nameView = (TextView) view.findViewById(R.id.product_name);
            firstPrice = (TextView) view.findViewById(R.id.product_price);
        }
    }
}