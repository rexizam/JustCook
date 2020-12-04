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
import com.example.justcook.models.Step;

import java.util.ArrayList;
import java.util.List;

public class InstructionListAdapter extends ArrayAdapter<Step> implements ListAdapterObserver {

    private static final String TAG = "InstructionListAdapter";
    private Context mContext;
    private int mResource;
    private int lastPosition = -1;
    private List<Step> instructions;
    private List<Observer> observers;

    public InstructionListAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        mContext = context;
        mResource = resource;
        observers = new ArrayList<>();
        instructions = new ArrayList<>();
    }

    public void setInstructionList(ArrayList<Step> instructions)
    {
        this.instructions = instructions;
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
            observer.onInstructionDataChanged();
        }
    }

    private static class ViewHolder {
        TextView instructionNumber;
        TextView instruction;
    }

    @Override
    public void add(@Nullable Step object) {
        instructions.add(object);
        super.add(object);
    }

    @Override
    public void remove(@Nullable Step object) {
        instructions.remove(object);
        super.remove(object);
    }

    @Override
    public void clear() {
        instructions.clear();
        super.clear();
    }

    @Override
    public int getCount() {
        return instructions.size();
    }

    @Nullable
    @Override
    public Step getItem(int position) {
        return instructions.get(position);
    }

    public List<Step> getInstructions() {
        return instructions;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String instruction = getItem(position).getStep();
        int number = getItem(position).getNumber();

        Step instructionTemp = new Step(number, instruction);

        //create the view result for showing the animation
        final View result;

        //ViewHolder object
        InstructionListAdapter.ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder = new InstructionListAdapter.ViewHolder();
            holder.instructionNumber = convertView.findViewById(R.id.instructionNumber);
            holder.instruction = convertView.findViewById(R.id.analyzedInstruction);
            ImageView imageView = convertView.findViewById(R.id.removeInstruction);

            imageView.setOnClickListener(view -> {
                Step toRemove = getItem(position);
                instructions.remove(toRemove);
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

        holder.instructionNumber.setText("" + instructionTemp.getNumber());
        holder.instruction.setText(instructionTemp.getStep());

        return convertView;
    }
}
