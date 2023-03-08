package com.example.machinetestvolly

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.machinetestvolly.databinding.ProductViewBinding

class ProductAdapter(var products:ArrayList<Product>):RecyclerView.Adapter<ProductAdapter.ProductViewHolder>(){
    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var productViewBinding : ProductViewBinding = ProductViewBinding.bind(itemView)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        var layoutInflater = LayoutInflater.from(parent.context)
        var productView = layoutInflater.inflate(R.layout.product_view,null)
        return ProductViewHolder(productView)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.productViewBinding.txtProductTitle.text = "${products[position].title}"
        holder.productViewBinding.txtProductDescription.text ="${products[position].description}"
        holder.productViewBinding.txtProductPrice.text = "${products[position].price}"
        holder.productViewBinding.txtProductBrand.text = "${products[position].brand}"
        Glide.with(holder.productViewBinding.imgProduct).load(products[position].thumbnail)
            .into(holder.productViewBinding.imgProduct)
    }

    override fun getItemCount(): Int {
        return products.size
    }
}