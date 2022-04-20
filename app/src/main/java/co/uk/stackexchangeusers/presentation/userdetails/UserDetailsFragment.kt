package co.uk.stackexchangeusers.presentation.userdetails


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import co.uk.stackexchangeusers.R
import co.uk.stackexchangeusers.databinding.FragmentUserDetailsBinding
import co.uk.stackexchangeusers.domain.model.BadgeCounts
import com.bumptech.glide.Glide
import org.koin.android.ext.android.inject
import java.util.*

class UserDetailsFragment : Fragment() {
    companion object {

        val ACCOUNT_ID = "ACCOUNT_ID"

        fun newInstance(accountId: Int): UserDetailsFragment {
            val fragment = UserDetailsFragment()
            val bundle = Bundle()
            bundle.putInt(ACCOUNT_ID, accountId)
            fragment.arguments = bundle
            return fragment
        }

        @JvmStatic
        @BindingAdapter("image")
        fun ImageView.bindImage(url: String?) {
            Glide.with(this).load(url).centerCrop()
                .into(this)
        }

        @JvmStatic
        @BindingAdapter("date")
        fun TextView.bindDate(date: Long) {
            this.text = Date(date).toString()
        }

        @JvmStatic
        @BindingAdapter("badges")
        fun TextView.bindBadges(badges: BadgeCounts?) {
            this.text = if (badges!=null) {
                this.context.getString(R.string.badges_have_badges, badges.bronze, badges.silver, badges.gold)
            } else {
                this.context.getString(R.string.badges_no_badges)
            }
        }
    }

    val viewModel: UserDetailsViewModel by inject()
    private lateinit var binding: FragmentUserDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        requireActivity().actionBar?.setDisplayHomeAsUpEnabled(true)
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_user_details, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val accountId = arguments?.getInt(ACCOUNT_ID)?.let { Integer.valueOf(it) }
        viewModel.start(accountId?:0)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.viewModel = viewModel
    }
}