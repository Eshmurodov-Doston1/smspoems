package com.example.smssherlar.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.smssherlar.R
import com.example.smssherlar.adapters.RvSave
import com.example.smssherlar.database.AppDatabase
import com.example.smssherlar.databinding.FragmentGalleryBinding
import com.example.smssherlar.entitiy.Save
import com.example.smssherlar.models.Sms_Sher
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

class GalleryFragment : Fragment() {
    private lateinit var galleryViewModel: GalleryViewModel
    lateinit var binding: FragmentGalleryBinding
    lateinit var root:View
    lateinit var appDatabase: AppDatabase
    lateinit var rvSave:RvSave
    lateinit var firebaseFirestore: FirebaseFirestore
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGalleryBinding.inflate(inflater, container, false)
         root = binding.root
        appDatabase = AppDatabase.getInstance(root.context)
        firebaseFirestore = FirebaseFirestore.getInstance()
        if (!appDatabase.isOpen) { appDatabase.openHelper.writableDatabase }
        rvSave = RvSave(root.context,object:RvSave.OnClickItem{
            override fun itemClick(save: Save, smsSher: Sms_Sher, position: Int) {

            }

            override fun onLeekClick(save: Save, smsSher: Sms_Sher, position: Int) {
                save.click = false
                save.icon = R.drawable.ic_heart
                appDatabase.saveDao().updateSaveInformation(save)
                rvSave.notifyItemChanged(position)
            }

        })

        appDatabase.saveDao().getSaveInformations2(R.drawable.ic_heart_2)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object:Consumer<List<Save>>{
                override fun accept(t: List<Save>?) {
                    rvSave.submitList(t!!)
                }
            })
        binding.rvSave.adapter = rvSave

        return root
    }

}