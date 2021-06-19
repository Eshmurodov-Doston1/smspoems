package com.example.smssherlar.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.smssherlar.R
import com.example.smssherlar.database.AppDatabase
import com.example.smssherlar.databinding.ItemRvBinding
import com.example.smssherlar.entitiy.Save
import com.example.smssherlar.models.Sms_Sher
import com.google.firebase.firestore.FirebaseFirestore

class RvSave(var context: Context,var onClickItem: OnClickItem):ListAdapter<Save,RvSave.Vh>(RvItemDiffUtil()) {
    lateinit var appDatabase: AppDatabase
    inner class Vh(var itemRvBinding: ItemRvBinding):RecyclerView.ViewHolder(itemRvBinding.root){
        lateinit var firebaseFirestore: FirebaseFirestore
     fun onBind(save: Save,position: Int){
         firebaseFirestore = FirebaseFirestore.getInstance()
         appDatabase = AppDatabase.getInstance(context)
         firebaseFirestore.collection(save.category_name!!).document(save.name!!).get().
         addOnCompleteListener {value->
             if (value.isSuccessful){
                 val sms = value.result!!.toObject(Sms_Sher::class.java)
                 itemRvBinding.leek.setImageResource(R.drawable.ic_heart_2)
                 itemRvBinding.info.text = sms!!.info
                 itemRvBinding.textName.text = sms.name
             }
         }


         itemRvBinding.leek.setOnClickListener {
             firebaseFirestore.collection(save.category_name!!).document(save.name!!).get().
             addOnCompleteListener {value->
                 if (value.isSuccessful){
                     val sms = value.result!!.toObject(Sms_Sher::class.java)
                   onClickItem.onLeekClick(save, sms!!,position)
                 }
             }
//             save.click = false
//             appDatabase.saveDao().updateSaveInformation(save)
//             notifyItemChanged(position)
         }
     }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemRvBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(getItem(position),position)
    }


    class RvItemDiffUtil : DiffUtil.ItemCallback<Save>() {

        override fun areItemsTheSame(oldItem: Save, newItem: Save): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Save, newItem: Save): Boolean {
            return oldItem.equals(newItem)
        }

    }

    interface OnClickItem{
        fun itemClick(save: Save,smsSher: Sms_Sher,position: Int)
        fun onLeekClick(save: Save,smsSher: Sms_Sher,position: Int)
    }
}