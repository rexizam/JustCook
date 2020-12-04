package com.example.justcook.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.justcook.R;
import com.example.justcook.models.ExtendedIngredient;

import java.util.ArrayList;
import java.util.List;

public class IngredientListAdapter extends ArrayAdapter<ExtendedIngredient> implements ListAdapterObserver {

    private static final String TAG = "IngredientListAdapter";
    private Context mContext;
    private int mResource;
    private int lastPosition = -1;
    private List<ExtendedIngredient> extendedIngredients;
    private List<Observer> observers;

    public IngredientListAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        mContext = context;
        mResource = resource;
        observers = new ArrayList<>();
        extendedIngredients = new ArrayList<>();
    }

    public void setIngredientList(ArrayList<ExtendedIngredient> extendedIngredients)
    {
        this.extendedIngredients = extendedIngredients;
    }

    @Override
    public void registerObserver(Observer listAdapterObserver) {
        if(!observers.contains(listAdapterObserver)) {
            observers.add(listAdapterObserver);
        }
    }

    @Override
    public void removeObserver(Observer listAdapterObserver) {
        if(observers.contains(listAdapterObserver)) {
            observers.remove(listAdapterObserver);
        }
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers)
        {
            observer.onIngredientDataChanged();
        }
    }

    private static class ViewHolder {
        TextView ingredient;
        TextView amount;
        TextView unit;
    }

    @Override
    public void add(@Nullable ExtendedIngredient object) {
        extendedIngredients.add(object);
        super.add(object);
    }

    @Override
    public void remove(@Nullable ExtendedIngredient object) {
        extendedIngredients.remove(object);
        super.remove(object);
    }

    @Override
    public void clear() {
        extendedIngredients.clear();
        super.clear();
    }

    @Override
    public int getCount() {
        return extendedIngredients.size();
    }

    public List<ExtendedIngredient> getExtendedIngredients() {
        return extendedIngredients;
    }

    @Nullable
    @Override
    public ExtendedIngredient getItem(int position) {
        return extendedIngredients.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String ingredientName = getItem(position).getName();
        double amount = getItem(position).getAmount();
        String unit = getItem(position).getUnit();

        ExtendedIngredient extendedIngredientTemp = new ExtendedIngredient(ingredientName, amount, unit);

        //create the view result for showing the animation
        final View result;

        //ViewHolder object
        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder = new ViewHolder();
            holder.ingredient = convertView.findViewById(R.id.textView1);
            holder.amount = convertView.findViewById(R.id.textView2);
            holder.unit = convertView.findViewById(R.id.textView3);
            ImageView imageView = convertView.findViewById(R.id.removeBtn);

            imageView.setOnClickListener(view -> {
                ExtendedIngredient toRemove = getItem(position);
                extendedIngredients.remove(toRemove);
                notifyDataSetChanged();
                notifyObservers();
            });

            result = convertView;

            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
            result = convertView;
        }


        Animation animation = AnimationUtils.loadAnimation(mContext,
                (position > lastPosition) ? R.anim.load_down_anim : R.anim.load_up_anim);
        result.startAnimation(animation);
        lastPosition = position;

        holder.ingredient.setText(extendedIngredientTemp.getName());
        holder.amount.setText(String.valueOf(extendedIngredientTemp.getAmount()));
        holder.unit.setText(extendedIngredientTemp.getUnit());

        return convertView;
    }
}
