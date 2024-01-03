package Controller;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidapp.EditData;
import com.example.androidapp.R;

import java.util.List;

import Model.MachineLearning;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    private Context context;
    private List<MachineLearning> machineLearningList;
    private DataBaseHandler dataBaseHandler;
    private int selectedPosition = RecyclerView.NO_POSITION;



    // Constructor for the Adapter
    public Adapter(Context context, List<MachineLearning> machineLearningList, DataBaseHandler dataBaseHandler) {
        this.context = context;
        this.machineLearningList = machineLearningList;
        this.dataBaseHandler = dataBaseHandler;
    }

    // Getter for the selected position
    public int getSelectedPosition() {
        return selectedPosition;
    }




    // ViewHolder class representing the UI elements in each row of the RecyclerView
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView mpg, displacement, horsePower, weight, acceleration, origin;
        public ImageView delete, edit;

        // Constructor for the ViewHolder
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            // Initialize UI elements
            mpg = itemView.findViewById(R.id.mpg);
            displacement = itemView.findViewById(R.id.displacement);
            horsePower = itemView.findViewById(R.id.horsePower);
            weight = itemView.findViewById(R.id.weight);
            acceleration = itemView.findViewById(R.id.acceleration);
            origin = itemView.findViewById(R.id.origine);
            edit = itemView.findViewById(R.id.edit);
            delete = itemView.findViewById(R.id.delete);
        }
    }

    // Method to create a new ViewHolder instance
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for each row of the RecyclerView
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_content, parent, false);
        return new MyViewHolder(view);
    }

    // Method to bind data to each ViewHolder
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // Get the MachineLearning object for the current position
        MachineLearning machineLearning = machineLearningList.get(position);

        // Set text for each UI element in the ViewHolder
        holder.mpg.setText("MPG: " + String.valueOf(machineLearning.getMpg()));
        holder.displacement.setText("Displacement: " + String.valueOf(machineLearning.getDisplacement()));
        holder.horsePower.setText("Horsepower: " + String.valueOf(machineLearning.getHorsePower()));
        holder.weight.setText("Weight: " + String.valueOf(machineLearning.getWeight()));
        holder.acceleration.setText("Acceleration: " + String.valueOf(machineLearning.getAcceleration()));
        holder.origin.setText("Origin: " + machineLearning.getOrigin());

        // Add click listeners for the edit and delete operations
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Get the selected MachineLearning object
                MachineLearning selectedMachineLearning = machineLearningList.get(position);

                // Create an intent to start the EditData activity
                Intent intent = new Intent(context, EditData.class);
                intent.putExtra("machineLearning",selectedMachineLearning);
                context.startActivity(intent);
            }
        });


        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Show a confirmation dialog before deleting
                showDeleteConfirmationDialog(position);
            }
        });
    }

    // Method to display a delete confirmation dialog
    private void showDeleteConfirmationDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Voulez-vous supprimer cet élément ?");
        builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Call the method to delete the item from the database
                long itemId = machineLearningList.get(position).getId();
                dataBaseHandler.deleteData(itemId);

                // Update the list in the activity
                machineLearningList = dataBaseHandler.getAllData();

                // Inform the adapter of the changes
                setData(machineLearningList);
                notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("Non", null);
        builder.show();
    }

    // Method to get the item count in the RecyclerView
    @Override
    public int getItemCount() {
        return machineLearningList.size();
    }


    // Method to update the data set in the adapter
    public void setData(List<MachineLearning> newData) {
        this.machineLearningList = newData;
    }
}
