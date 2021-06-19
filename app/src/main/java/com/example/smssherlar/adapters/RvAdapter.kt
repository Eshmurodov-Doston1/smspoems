package com.example.smssherlar.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.mydictionary.utils.MyBounceInterpolator
import com.example.smssherlar.R
import com.example.smssherlar.database.AppDatabase
import com.example.smssherlar.databinding.ItemRvBinding
import com.example.smssherlar.entitiy.Save
import com.example.smssherlar.models.Sms_Sher
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

class RvAdapter(var context: Context,var list:List<Sms_Sher>,var onClickItem: OnClickItem):RecyclerView.Adapter<RvAdapter.Vh>() {
    inner class Vh(var itemRvBinding: ItemRvBinding):RecyclerView.ViewHolder(itemRvBinding.root){
        lateinit var appDatabase:AppDatabase
        fun onBind(sms: Sms_Sher,position: Int){
            appDatabase = AppDatabase.getInstance(context)
            if(!appDatabase.isOpen){ appDatabase.openHelper.writableDatabase }
            itemRvBinding.textName.text = sms.name
            itemRvBinding.info.text = sms.info
            itemRvBinding.card.setOnClickListener {
                onClickItem.onItemClickListener(sms,position)
            }

            if(sms.name!=null){
                appDatabase.saveDao().getSaveInformations(sms.name!!, sms.category_position!!)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object:Consumer<Save>{
                        override fun accept(t: Save?) {
                            if(t!!.click==true){
                                itemRvBinding.leek.setImageResource(R.drawable.ic_heart_2)
                            }else if (t.click==false){
                                itemRvBinding.leek.setImageResource(R.drawable.ic_heart)
                            }
                        }
                    })
            }



            itemRvBinding.leek.setOnClickListener {
                val saveInformations = appDatabase.saveDao().getSaveInformations1(sms.name!!,
                    sms.category_position!!
                )
                if (saveInformations!=null){
                    if (saveInformations.click==true){
                        saveInformations.click = false
                        saveInformations.icon = R.drawable.ic_heart
                        val myAnim = AnimationUtils.loadAnimation(context, R.anim.save_animation)
                        val interpolator = MyBounceInterpolator(0.1, 20.0)
                        myAnim.interpolator = interpolator
                        itemRvBinding.leek.startAnimation(myAnim)
                        itemRvBinding.leek.setImageResource(R.drawable.ic_heart)
                        appDatabase.saveDao().updateSaveInformation(saveInformations)
                    }else{
                        saveInformations.click = true
                        saveInformations.icon = R.drawable.ic_heart_2
                        val myAnim = AnimationUtils.loadAnimation(context, R.anim.save_animation)
                        val interpolator = MyBounceInterpolator(0.1, 20.0)
                        myAnim.interpolator = interpolator
                        itemRvBinding.leek.startAnimation(myAnim)
                        itemRvBinding.leek.setImageResource(R.drawable.ic_heart_2)
                        appDatabase.saveDao().updateSaveInformation(saveInformations)
                    }
                }else{
                    var save = Save(true,sms.category,sms.name,sms.category_position,R.drawable.ic_heart_2)
                    val myAnim = AnimationUtils.loadAnimation(context, R.anim.save_animation)
                    val interpolator = MyBounceInterpolator(0.1, 20.0)
                    myAnim.interpolator = interpolator
                    itemRvBinding.leek.startAnimation(myAnim)
                    itemRvBinding.leek.setImageResource(R.drawable.ic_heart_2)
                    appDatabase.saveDao().addSaveInformation(save)

                }
            }


//            val saveInformations = appDatabase.saveDao().getSaveInformations1(sms.category!!)
//            if (saveInformations!=null){
//                if (saveInformations.click==true){
//                    saveInformations.click = false
//                    val myAnim = AnimationUtils.loadAnimation(context, R.anim.save_animation)
//                    val interpolator = MyBounceInterpolator(0.1, 20.0)
//                    myAnim.interpolator = interpolator
//                    itemRvBinding.leek.startAnimation(myAnim)
//                    itemRvBinding.leek.setImageResource(R.drawable.ic_heart)
//                    appDatabase.saveDao().updateSaveInformation(saveInformations)
//                }else{
//                    saveInformations.click = true
//                    val myAnim = AnimationUtils.loadAnimation(context, R.anim.save_animation)
//                    val interpolator = MyBounceInterpolator(0.1, 20.0)
//                    myAnim.interpolator = interpolator
//                    itemRvBinding.leek.startAnimation(myAnim)
//                    itemRvBinding.leek.setImageResource(R.drawable.ic_heart_2)
//                    appDatabase.saveDao().updateSaveInformation(saveInformations)
//                }
//            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemRvBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position],position)
    }

    override fun getItemCount(): Int {
       return list.size
    }

    interface OnClickItem{
        fun onItemClickListener(sms: Sms_Sher,position: Int)
    }
}