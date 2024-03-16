/*
 * Copyright WeiLianYang
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.william.toolkit.activity

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.william.toolkit.R
import com.william.toolkit.adapter.RecordListAdapter
import com.william.toolkit.base.BaseActivity
import com.william.toolkit.base.BaseAdapter
import com.william.toolkit.base.BaseViewHolder
import com.william.toolkit.bean.ApiRecordBean
import com.william.toolkit.databinding.ActivityToolkitRecordListBinding
import com.william.toolkit.util.openActivity
import com.william.toolkit.vm.RecordListViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.take

/**
 * @author William
 * @date 2020-02-17 20:26
 * Class Comment：接口数据列表
 */
class RecordListActivity : BaseActivity() {

    private val viewModel: RecordListViewModel by viewModels()

    override val mViewBinding: ActivityToolkitRecordListBinding by bindingView()

    override var loadingTextResId = R.string.loading

    private var mAdapter: RecordListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()

        initData()
    }

    private fun initView() {
        mViewBinding.includeTitle.apply {
            ivPrevious.setOnClickListener { onBackPressed() }
            ivToolSearch.isVisible = true;
            ivToolSearch.setImageResource(R.drawable.toolkit_search)
            ivToolSearch.setOnClickListener{showInputDialog()}
            tvToolTitle.setText(R.string.tool_title_record_list)
            ivToolRight.setImageResource(R.drawable.toolkit_clear)
            ivToolRight.setOnClickListener {
                viewModel.clearRecord()
            }
        }
    }

    fun showInputDialog() {
        val inputEditText = EditText(this)

        val dialog = AlertDialog.Builder(this)
            .setTitle("搜索")
            .setView(inputEditText)
            .setPositiveButton("搜索") { _, _ ->
                val userInput = inputEditText.text.toString()
                search(userInput)
            }
            .setNegativeButton("取消") { dialog, _ ->
                dialog.cancel()
            }
            .create()

        dialog.show()
    }

    fun search(key:String){
        viewModel.searchListData(key).observe(this@RecordListActivity,{
            dismissLoading()
            mAdapter?.setList(it)
            mViewBinding.tvEmptyData.isVisible = it.isEmpty()
        });
    }

    private fun initData() {
        mAdapter = RecordListAdapter(this)
        mViewBinding.recyclerView.apply {
            adapter = mAdapter
        }
        mAdapter?.apply {
            setOnItemClickListener(object :
                BaseAdapter.OnItemClickListener<ApiRecordBean> {
                override fun onItemClick(
                    holder: BaseViewHolder,
                    position: Int,
                    bean: ApiRecordBean?
                ) {
                    openActivity<RecordDetailActivity>(this@RecordListActivity) {
                        putExtra("id", bean?.id)
                        putExtra("title",bean?.title)
                    }
                }
            })
        }

        viewModel.apply {
            clearFlag.observe(this@RecordListActivity, {
                mAdapter?.apply {
                    clear()
                    notifyDataSetChanged()
                }
            })

            recordListData.observe(this@RecordListActivity, {
                dismissLoading()
                mAdapter?.setList(it)
                mViewBinding.tvEmptyData.isVisible = it.isEmpty()
            })
            showLoading()
        }
    }

}