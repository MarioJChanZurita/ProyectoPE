package com.example.medicinereminder.MostrarAlarma;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicinereminder.Alarma.ModificarAlarma;
import com.example.medicinereminder.MainActivity;
import com.example.medicinereminder.R;

import java.util.List;

public class MostrarAdaptador extends RecyclerView.Adapter<MostrarAdaptador.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView nombre, notas;
        CardView alarma;
        Context context;

        //Funcion que sirve de modelo para los card view
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();

            nombre = (TextView)itemView.findViewById(R.id.nombreMedicina);
            notas = (TextView)itemView.findViewById(R.id.notasMedicina);
            alarma = (CardView) itemView.findViewById(R.id.cardViewAlarma);
        }

        //Funcion para hacer seleccionable el card view
        void setOnClickListeners(){
            alarma.setOnClickListener(this);
        }

        //Funcion para cuando presionas un card view te envia al activity modificar de la alarma seleccionada
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.cardViewAlarma:

                    Intent modificar = new Intent(context, ModificarAlarma.class);
                    modificar.putExtra("nombre", nombre.getText());
                    context.startActivity(modificar);

                    break;
            }
        }
    }

    //Definimos la variable tipo lista con el modelo
    public List<MostrarModelo> alarmaLista;

    //Creamos la lista donde se alamacenaran los datos que se mostrarán para cada alarma
    public MostrarAdaptador(List<MostrarModelo> alarmaLista) {this.alarmaLista = alarmaLista;}

    //Funcion que permite inflar el layout con el diseño de cada elemento de la lista(item_alarma)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alarma,parent , false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    //Funcion para obtener los datos que se mostraran de cada alarma en el recycler view de alarmas
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.nombre.setText(alarmaLista.get(position).getNombre());
        holder.notas.setText(alarmaLista.get(position).getNotas());

        //colocar eventos
        holder.setOnClickListeners();
    }

    //Funcion que establece cuantos elementos tendrá la lista
    @Override
    public int getItemCount() {
        return alarmaLista.size();
    }
}
