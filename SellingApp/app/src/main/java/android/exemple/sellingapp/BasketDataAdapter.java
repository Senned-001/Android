package android.exemple.sellingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

class BasketDataAdapter extends RecyclerView.Adapter<BasketDataAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private List<Order> orders;

    BasketDataAdapter(Context context, List<Order> orders) {
        this.orders = orders;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public BasketDataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.basket, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Order order = orders.get(position);
        holder.imageView.setImageResource(order.getImageID());
        holder.infoView.setText(order.getInfo());
        holder.priceView.setText(BasketActivity.doubleToString(order.getTotalCost()));
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               orders.remove(position);
               BasketActivity ba = (BasketActivity) inflater.getContext();
               ba.displayTotalSum();
               notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView imageView;
        final TextView infoView, priceView;
        final Button deleteButton;

        ViewHolder(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.basketOrderImage);
            infoView = (TextView) view.findViewById(R.id.basketOrderInfo);
            priceView = (TextView) view.findViewById(R.id.basketOrderPrice);
            deleteButton =  (Button) view.findViewById(R.id.basketOrderDeleteButton);
        }
    }
}