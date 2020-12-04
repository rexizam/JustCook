package com.example.justcook.ui.fragments.addrecipe;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.RenderMode;
import com.example.justcook.R;
import com.example.justcook.adapters.IngredientListAdapter;
import com.example.justcook.adapters.InstructionListAdapter;
import com.example.justcook.adapters.Observer;
import com.example.justcook.models.AnalyzedInstruction;
import com.example.justcook.models.ExtendedIngredient;
import com.example.justcook.models.Recipe;
import com.example.justcook.models.Step;
import com.example.justcook.util.Converter;
import com.example.justcook.viewmodels.AddRecipeViewModel;

import org.angmarch.views.NiceSpinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddRecipeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

@AndroidEntryPoint
public class AddRecipeFragment extends Fragment implements Observer, TextView.OnEditorActionListener {

    LottieAnimationView buttonAddIngredient;
    LottieAnimationView buttonAddInstruction;
    LottieAnimationView save;
    ImageView photo;
    Bitmap imageToSave;
    NiceSpinner spinner;
    LottieAnimationView animationView;
    ListView ingredientListView;
    ListView instructionListView;
    EditText ingredientName;
    EditText ingredientAmount;
    EditText unitMeasure;
    EditText instruction;
    EditText title;
    ScrollView scrollView;
    IngredientListAdapter ingredientsAdapter;
    InstructionListAdapter instructionListAdapter;
    AddRecipeViewModel viewModel;
    ArrayList<ExtendedIngredient> extendedIngredientList = new ArrayList<>();
    ArrayList<Step> instructionList = new ArrayList<>();

    public static final int CAMERA_PERMISSION_CODE = 1;
    public static final int CAMERA_REQUEST_CODE = 2;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddRecipeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddRecipe.
     */
    // TODO: Rename and change types and number of parameters
    public static AddRecipeFragment newInstance(String param1, String param2) {
        AddRecipeFragment fragment = new AddRecipeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        if (savedInstanceState != null)
        {
            extendedIngredientList = savedInstanceState.getParcelableArrayList("ingredients");
            instructionList = savedInstanceState.getParcelableArrayList("instructions");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_recipe, container, false);

        scrollView = view.findViewById(R.id.scrollView);
        animationView = view.findViewById(R.id.lottieCook);
        animationView.setRenderMode(RenderMode.SOFTWARE);
        ingredientName = view.findViewById(R.id.ingredientName);
        ingredientName.setOnEditorActionListener(this);
        ingredientAmount = view.findViewById(R.id.ingredientAmount);
        unitMeasure = view.findViewById(R.id.unitMeasure);
        instruction = view.findViewById(R.id.addInstructionMultiline);
        title = view.findViewById(R.id.recipeTitle);
        photo = view.findViewById(R.id.photoView);

        ingredientListView = view.findViewById(R.id.listViewIngredients);
        ingredientsAdapter = new IngredientListAdapter(view.getContext(),
                R.layout.ingredient_adapter_view_layout);
        ingredientsAdapter.setIngredientList(extendedIngredientList);
        ingredientListView.setAdapter(ingredientsAdapter);
        ingredientsAdapter.registerObserver(this);
        setListViewHeightBasedOnChildren(ingredientListView);

        instructionListView = view.findViewById(R.id.listViewInstructions);
        instructionListAdapter = new InstructionListAdapter(view.getContext(),
                R.layout.instruction_adapter_view_layout);
        instructionListAdapter.setInstructionList(instructionList);
        instructionListView.setAdapter(instructionListAdapter);
        instructionListAdapter.registerObserver(this);
        setListViewHeightBasedOnChildren(instructionListView);

        spinner = view.findViewById(R.id.categorySpinner);
        List<String> categoryList = Arrays.asList(getResources().getStringArray(R.array.Categories));
        spinner.attachDataSource(categoryList);

        spinner.setOnSpinnerItemSelectedListener((parent, view12, position, id) -> {
            String text = String.valueOf(parent.getItemAtPosition(position));
            Toast.makeText(view12.getContext(), text, Toast.LENGTH_SHORT).show();
        });

        buttonAddIngredient = view.findViewById(R.id.add_ingredient_row);
        buttonAddIngredient.setOnClickListener(view1 -> {

            if (!ingredientAmount.getText().toString().isEmpty())
            {
                buttonAddIngredient.setSpeed(1);
                buttonAddIngredient.playAnimation();

                double amount = Double.parseDouble(ingredientAmount.getText().toString());
                ExtendedIngredient extendedIngredient = new ExtendedIngredient();
                extendedIngredient.setName(ingredientName.getText().toString());
                extendedIngredient.setAmount(amount);
                extendedIngredient.setUnit(unitMeasure.getText().toString());
                ingredientsAdapter.add(extendedIngredient);
                setListViewHeightBasedOnChildren(ingredientListView);
                ingredientName.setText("");
                ingredientAmount.setText("");
                unitMeasure.setText("");
            }
        });

        buttonAddInstruction = view.findViewById(R.id.add_cooking_instruction);
        buttonAddInstruction.setOnClickListener(view13 -> {

            if (!instruction.getText().toString().isEmpty())
            {
                buttonAddInstruction.setSpeed(1);
                buttonAddInstruction.playAnimation();

                int index = instructionListAdapter.getCount() + 1;
                Step instr = new Step(index, instruction.getText().toString());
                instructionListAdapter.add(instr);
                setListViewHeightBasedOnChildren(instructionListView);
                instruction.setText("");
            }
        });

        photo.setOnClickListener(view14 -> {
            if (ContextCompat.checkSelfPermission(view14.getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
            }
            else
            {
                openCamera();
            }
        });

        save = view.findViewById(R.id.saveRecipe);
        save.setOnClickListener(view15 -> {
            if (title.getText().toString().isEmpty()
                    || instructionListAdapter.getCount() == 0
                    || ingredientsAdapter.getCount() == 0
                    || imageToSave == null)
            {
                Toast.makeText(getContext(), "Please fill in all the required fields", Toast.LENGTH_SHORT).show();
            }
            else
            {
                save.setSpeed(1);
                save.playAnimation();

                Recipe recipe = new Recipe();
                List<ExtendedIngredient> extendedIngredientList = ingredientsAdapter.getExtendedIngredients();
                List<Step> stepList = instructionListAdapter.getInstructions();
                AnalyzedInstruction analyzedInstruction = new AnalyzedInstruction();
                analyzedInstruction.setSteps(stepList);
                List<AnalyzedInstruction> analyzedInstructionList = new ArrayList<>();
                analyzedInstructionList.add(analyzedInstruction);

                recipe.setCategory(spinner.getSelectedItem().toString());
                recipe.setName(title.getText().toString());
                recipe.setPicture(Converter.bitMapToByteArray(imageToSave));
                recipe.setExtendedIngredients(extendedIngredientList);
                recipe.setAnalyzedInstructions(analyzedInstructionList);
                viewModel.insert(recipe);

                instructionListAdapter.clear();
                ingredientsAdapter.clear();
                title.setText("");
                photo.setImageResource(R.drawable.add_photo_placeholder);
                spinner.setSelectedIndex(0);
                Toast.makeText(getContext(), "Recipe saved.", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) return;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(),
                View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0) view.setLayoutParams(new
                    ViewGroup.LayoutParams(desiredWidth,
                    ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();

        params.height = totalHeight + (listView.getDividerHeight() *
                (listAdapter.getCount() - 1));

        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    private void openCamera() {
        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(camera, CAMERA_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {
            openCamera();
        }
        else
        {
            Toast.makeText(getContext(), "Camera permission is required to use camera.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == CAMERA_REQUEST_CODE)
        {
            if (data != null && data.getExtras() != null)
            {
                Bitmap image = (Bitmap) data.getExtras().get("data");

                if (image != null)
                {
                    photo.setImageBitmap(image);
                    imageToSave = image;
                    scrollView.post(() -> scrollView.fullScroll(View.FOCUS_DOWN));
                }
            }
        }
    }

    @Override
    public void onInstructionDataChanged() {
        setListViewHeightBasedOnChildren(instructionListView);
    }

    @Override
    public void onIngredientDataChanged() {
        setListViewHeightBasedOnChildren(ingredientListView);
    }

    @Override
    public void onDestroy() {
        instructionListAdapter.removeObserver(this);
        ingredientsAdapter.removeObserver(this);
        super.onDestroy();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(AddRecipeViewModel.class);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        extendedIngredientList = (ArrayList<ExtendedIngredient>)ingredientsAdapter.getExtendedIngredients();
        instructionList = (ArrayList<Step>)instructionListAdapter.getInstructions();
        outState.putParcelableArrayList("ingredients", extendedIngredientList);
        outState.putParcelableArrayList("instructions", instructionList);
    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        if (i == EditorInfo.IME_ACTION_NEXT) {
            ingredientAmount.requestFocus();
        }
        return true;
    }
}