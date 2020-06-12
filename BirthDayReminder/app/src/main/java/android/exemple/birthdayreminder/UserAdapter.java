package android.exemple.birthdayreminder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class UserAdapter extends ArrayAdapter<User> {
    private String[] monthsNames;

    public UserAdapter(@NonNull Context context, int resource, @NonNull List<User> objects) {
        super(context, resource, objects);
        monthsNames = context.getResources().getStringArray(R.array.month_names);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        User user = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.list_layout, null);
        }
        ((TextView) convertView.findViewById(R.id.text1))
                .setText(user.getName());
        ((TextView) convertView.findViewById(R.id.text2))
                .setText(user.getDay()+" " + monthsNames[user.getMonth()-1]);
        ((TextView) convertView.findViewById(R.id.text3))
                .setText(user.getAge()!=0?"" +user.getAge():"?");

        return convertView;
    }
}
