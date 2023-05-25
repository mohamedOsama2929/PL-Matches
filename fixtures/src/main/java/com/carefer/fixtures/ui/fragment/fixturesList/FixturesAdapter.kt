package com.carefer.fixtures.ui.fragment.fixturesList

import android.content.Context
import android.util.Log
import android.widget.TextView
import androidx.core.view.isVisible
import com.carefer.core.base.adapter.diffutilsAdapter.BaseRecyclerAdapter
import com.carefer.fixtures.databinding.ItemFixtureBinding
import com.carefer.fixtures.domain.entity.local.MatchItem
import com.carefer.fixtures.domain.entity.local.MatchStatus


class FixturesAdapter :
    BaseRecyclerAdapter<ItemFixtureBinding, MatchItem>(
        ItemFixtureBinding::inflate, { oldItem, newItem -> oldItem.id == newItem.id }) {


    override fun bind(
        context: Context,
        binding: ItemFixtureBinding,
        item: MatchItem,
        position: Int
    ) {
        binding.run {
            tvHomeTeamName.text = item.homeTeam
            tvAwayTeamName.text = item.awayTeam
            Log.e("TAG", "bind: " + item.score)
            tvScore.text = item.score
            tvTime.text = item.matchTime
            tvScore.isVisible = item.matchStatus == MatchStatus.FINISHED
            tvTime.isVisible = item.matchStatus == MatchStatus.SCHEDULED

            if (position == 0) {
                showStickyHeaderTitle(item.headerTitle, tvStickyHeader)
            } else if ((item.headerTitle != currentList[position - 1].headerTitle)) {
                showStickyHeaderTitle(item.headerTitle, tvStickyHeader)
            } else {
                tvStickyHeader.isVisible = false
            }
        }
    }

    private fun showStickyHeaderTitle(
        headerTitle: String,
        tvHeader: TextView
    ) {
        tvHeader.text = headerTitle
        tvHeader.isVisible = true
    }
}