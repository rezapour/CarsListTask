package me.rezapour.sixttask.view.carlist

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import me.rezapour.sixttask.R
import me.rezapour.sixttask.databinding.RowCarDetailBinding
import me.rezapour.sixttask.model.Car

class CarListAdapter(private val carList: ArrayList<Car>) :
    RecyclerView.Adapter<CarListAdapter.CarViewHolder>() {


    inner class CarViewHolder(private val binding: RowCarDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val carImage: ImageView = binding.imageViewCar
        private val carName: TextView = binding.txtViewCarName
        private val platNumber: TextView = binding.textViewPlatNumber
        private val fullLevel: TextView = binding.txtFuelLevel
        private val transmissionType: TextView = binding.txtTransmissionType
        private val fullType: TextView = binding.txtFuelType
        private val color: TextView = binding.txtColor


        fun onBind(car: Car) {
            Glide.with(itemView.context)
                .load(car.carImageUrl)
                .error(R.drawable.ic_baseline_broken_image_24)
                .into(carImage);

            carName.text = "${car.make} ${car.name}"
            platNumber.text = car.licensePlate
            val level = car.fuelLevel * 100
            fullLevel.text = "$level %"
            transmissionType.text = if (car.transmission == "M") "Manual" else "Automatic"
            fullType.text = if (car.fuelType == "D") "Diesel" else "Electric"
            color.text = car.color.replace("_", " ").capitalize()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        val binding =
            RowCarDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CarViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        val car = carList[position]
        holder.onBind(car)
    }

    override fun getItemCount() = carList.size

    fun addItem(list: List<Car>) {
        carList.clear()
        carList.addAll(list)
    }


}