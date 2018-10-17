package com.loc8r.seattleexplorer.presentation.poiList

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.loc8r.seattleexplorer.R
import com.loc8r.seattleexplorer.presentation.models.PoiPresentation
import com.squareup.picasso.Picasso
import javax.inject.Inject

class PoiListAdapter @Inject constructor(): RecyclerView.Adapter<PoiListAdapter.ViewHolder>() {

    var pois: List<PoiPresentation> = arrayListOf()

    /**
     * Called when RecyclerView needs a new [ViewHolder] of the given type to represent
     * an item.
     *
     *
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     *
     *
     * The new ViewHolder will be used to display items of the adapter using
     * [.onBindViewHolder]. Since it will be re-used to display
     * different items in the data set, it is a good idea to cache references to sub views of
     * the View to avoid unnecessary [View.findViewById] calls.
     *
     * @param parent The ViewGroup into which the new View will be added after it is bound to
     * an adapter position.
     * @param viewType The view type of the new View.
     *
     * @return A new ViewHolder that holds a View of the given view type.
     * @see .getItemViewType
     * @see .onBindViewHolder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.poi_list_item,parent,false)

        return ViewHolder(itemView)
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    override fun getItemCount(): Int {
        return pois.count()
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the [ViewHolder.itemView] to reflect the item at the given
     * position.
     *
     *
     * Note that unlike [android.widget.ListView], RecyclerView will not call this method
     * again if the position of the item changes in the data set unless the item itself is
     * invalidated or the new position cannot be determined. For this reason, you should only
     * use the `position` parameter while acquiring the related data item inside
     * this method and should not keep a copy of it. If you need the position of an item later
     * on (e.g. in a click listener), use [ViewHolder.getAdapterPosition] which will
     * have the updated adapter position.
     *
     * Override [.onBindViewHolder] instead if Adapter can
     * handle efficient partial bind.
     *
     * @param holder The ViewHolder which should be updated to represent the contents of the
     * item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val poi = pois[position]
        holder.poiNameText.text = poi.name
        holder.poiCollectionText.text = poi.collection

        Picasso.get()
                .load(poi.img_url)
                .fit()
                .centerCrop()
                .into(holder.poiImage)
    }

    // Having a ViewHolder inner class is a standard design pattern.
    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        var poiImage: ImageView = view.findViewById(R.id.img_poi_img)
        var poiNameText: TextView = view.findViewById(R.id.txt_poi_name)
        var poiCollectionText: TextView = view.findViewById(R.id.txt_poi_collection_name)
    }
}