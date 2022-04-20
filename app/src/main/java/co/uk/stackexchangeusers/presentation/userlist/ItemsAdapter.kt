package co.uk.stackexchangeusers.presentation.userlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import co.uk.stackexchangeusers.R
import co.uk.stackexchangeusers.databinding.ItemUserBinding
import co.uk.stackexchangeusers.domain.model.User
import co.uk.stackexchangeusers.domain.model.UserResponse
import com.bumptech.glide.Glide

class ItemsAdapter(val onUserClickListener: OnUserClickListener) : RecyclerView.Adapter<ItemsAdapter.ItemViewHolder>() {

    private var items: MutableList<User> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(parent)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        if (items.size > position) {
            holder.bind(items[position], onUserClickListener)
        }
    }

    fun update(items: List<User>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    companion object {
        @JvmStatic
        @BindingAdapter("items")
        fun RecyclerView.bindItems(items: MutableLiveData<UserResponse>) {
            val adapter = adapter as ItemsAdapter
            adapter.update(items.value?.items?: emptyList())
            scheduleLayoutAnimation()
        }
    }

    class ItemViewHolder(
        private val parent: ViewGroup,
        private val binding: ItemUserBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_user,
            parent,
            false
        )
    ) :  RecyclerView.ViewHolder(binding.root) {

        fun bind(item: User, onUserClickListener: OnUserClickListener) {

            binding.textUserDescription.text = item.displayName
            binding.textUserName.text = item.userId.toString()
            Glide.with(binding.imageUserThumb)
                .load(item.profileImage)
                .into(binding.imageUserThumb)
            itemView.setOnClickListener { onUserClickListener.onUserClick(item) }
        }
    }

    interface OnUserClickListener {
        fun onUserClick(user:User)
    }
}