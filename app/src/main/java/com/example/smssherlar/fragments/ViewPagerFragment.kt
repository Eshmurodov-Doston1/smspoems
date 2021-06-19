package com.example.smssherlar.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.Toast
import com.example.mydictionary.utils.MyBounceInterpolator
import com.example.smssherlar.R
import com.example.smssherlar.adapters.RvAdapter
import com.example.smssherlar.database.AppDatabase
import com.example.smssherlar.databinding.FragmentViewPagerBinding
import com.example.smssherlar.entitiy.Save
import com.example.smssherlar.models.Category
import com.example.smssherlar.models.Sms_Sher
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ViewPagerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ViewPagerFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: Int? = null
    private var param2: Category? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getInt(ARG_PARAM1)
            param2 = it.getSerializable(ARG_PARAM2) as Category
        }
    }
    lateinit var fragmentViewPagerBinding:FragmentViewPagerBinding
    lateinit var root:View
    lateinit var rvAdapter: RvAdapter
    lateinit var firebaseFirestore: FirebaseFirestore
    lateinit var listSms_Sher: ArrayList<Sms_Sher>
    lateinit var appDatabase: AppDatabase
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentViewPagerBinding = FragmentViewPagerBinding.inflate(inflater,container,false)
        root = fragmentViewPagerBinding.root
        firebaseFirestore = FirebaseFirestore.getInstance()
        listSms_Sher = ArrayList()
        appDatabase= AppDatabase.getInstance(root.context)
        if(!appDatabase.isOpen){ appDatabase.openHelper.writableDatabase }
        firebaseFirestore.collection("${param2!!.name_category}").addSnapshotListener { value, error ->
            value!!.documentChanges.forEach {queryDocumentSnapshot->
                when(queryDocumentSnapshot.type){
                    DocumentChange.Type.ADDED->{
                        val smsSher = queryDocumentSnapshot.document.toObject(Sms_Sher::class.java)
                        listSms_Sher.add(smsSher)
                    }
                }
            }
            rvAdapter = RvAdapter(root.context,listSms_Sher,object:RvAdapter.OnClickItem{
                override fun onItemClickListener(sms: Sms_Sher, position: Int) {

                }
            })
            fragmentViewPagerBinding.rv.adapter = rvAdapter
        }






        return root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ViewPagerFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: Int,param2: Category) =
            ViewPagerFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, param1)
                    putSerializable(ARG_PARAM2, param2)
                }
            }
    }
}