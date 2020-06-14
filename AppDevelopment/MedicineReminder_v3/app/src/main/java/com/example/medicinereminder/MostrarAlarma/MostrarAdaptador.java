package com.example.medicinereminder.MostrarAlarma;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicinereminder.R;

import java.util.List;

public class MostrarAdaptador extends RecyclerView.Adapter<MostrarAdaptador.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nombre, notas;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre = (TextView)itemView.findViewById(R.id.nombreMedicina);
            notas = (TextView)itemView.findViewById(R.id.notasMedicina);
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
    }

    //Funcion que establece cuantos elementos tendrá la lista
    @Override
    public int getItemCount() {
        return alarmaLista.size();
    }
}
