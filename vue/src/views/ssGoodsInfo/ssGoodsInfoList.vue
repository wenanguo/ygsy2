<template>
  <page-header-wrapper>
    <a-card :bordered="false">
      <div class="table-page-search-wrapper">
        <a-form layout="inline">
          <a-row :gutter="48">
            <a-col :md="6" :sm="24">
              <a-form-item label="名称">
                <a-input v-model="queryParam.goodsName" placeholder=""/>
              </a-form-item>
            </a-col>
            <a-col :md="6" :sm="24">
              <a-form-item label="商品类型">
                <a-select v-model="queryParam.goodsTypeId" placeholder="请选择" default-value="0">>
                  <a-select-option v-for="type in typeList" :key="type.id" :value="type.id">
                    {{ type.type_name }}
                  </a-select-option>
                </a-select>
               </a-form-item>
            </a-col>
            <a-col :md="6" :sm="24">
              <a-form-item label="是否上架">
                <a-select v-model="queryParam.status" placeholder="请选择" default-value="0">
                  <a-select-option :value="0">全部</a-select-option>
                  <a-select-option :value="100">已上架</a-select-option>
                  <a-select-option :value="101">已下架</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <template v-if="advanced">
            </template>
            <a-col :md="!advanced && 6 || 24" :sm="24">
              <span class="table-page-search-submitButtons" :style="advanced && { float: 'right', overflow: 'hidden' } || {} ">
                <a-button type="primary" @click="$refs.table.refresh(true)">查询</a-button>
                <a-button style="margin-left: 8px" @click="resetSearchForm">重置</a-button>
              </span>
            </a-col>
          </a-row>
        </a-form>
      </div>

      <div class="table-operator">
        <a-button type="primary" icon="plus" @click="handleAdd">新建</a-button>
        <a-dropdown v-action:delete v-if="selectedRowKeys.length > 0">
          <a-menu slot="overlay">
            <a-menu-item key="1" @click="handleconfirmDel"><a-icon type="delete" />删除</a-menu-item>
            <!-- lock | unlock -->
            <a-menu-item key="2"><a-icon type="lock" />锁定</a-menu-item>
          </a-menu>
          <a-button style="margin-left: 8px">
            批量操作 <a-icon type="down" />
          </a-button>
        </a-dropdown>
      </div>

      <s-table
        ref="table"
        size="default"
        rowKey="id"
        :columns="columns"
        :data="loadData"
        :alert="false"
        :rowSelection="rowSelection"
        showPagination="auto"
      >
        <span slot="serial" slot-scope="text, record, index">
          {{ index + 1 }}
        </span>
        <span slot="showNum" slot-scope="text, record">
          <a-badge :count="record.ticketNum"  :number-style="{ backgroundColor: '#2db7f5' }">
              {{ record.goodsTypeId==1 ? text+"--" : text }}
          </a-badge>
        </span>
        <span slot="statusSlot" slot-scope="text">
          <a-badge :status="text | statusTypeFilter" :text="text | statusFilter" />
        </span>
        <span slot="typename" slot-scope="text">
          {{ handleTypeName(text) }}
        </span>
        <span slot="recommend" slot-scope="text">
          {{ text==1 ? '是' : '否' }}
        </span>
        <span slot="pic" slot-scope="text, record">
          <img style="width:50px;heigth:50px" :src="record.goodsImg" />
        </span>
        <span slot="action" slot-scope="text, record">
          <template>
            <a @click="handleEdit(record)">修改</a>
            <a-divider type="vertical" />
            <a-popconfirm title="是否要删除当前数据？" @confirm="handleDel(record)">
              <a style="color: red">删除</a>
            </a-popconfirm>
          </template>
        </span>
      </s-table>

      <edit-form
        ref="editForm"
        :title="title"
        :visible="visible"
        :loading="confirmLoading"
        :model="mdl"
        @cancel="handleCancel"
        @ok="handleOk"
      />
    </a-card>
  </page-header-wrapper>
</template>

<script>
    import moment from 'moment'
    import { STable, Ellipsis } from '@/components'
    import { getSsGoodsInfoList, saveSsGoodsInfo, delSsGoodsInfo, batchDelSsGoodsInfo } from '@/api/ssGoodsInfo'
    import EditForm from './ssGoodsInfoForm'
    import { goodsStatusMap } from '@/api/goodsRelatedRC'
    import { getGoodsTypeDict } from '@/api/ssGoodsType'

    const columns = [
        {
            title: '序号',
            sorter: true,
            width: '80px',
            dataIndex: 'id'
        }, {
            title: '商品名称',
            sorter: true,
            dataIndex: 'goodsName',
            scopedSlots: { customRender: 'showNum' }
        }, {
            title: '识别码',
            sorter: true,
            dataIndex: 'goodsCode'
        }, {
            title: '商品类型',
            sorter: true,
            dataIndex: 'goodsTypeId',
            scopedSlots: { customRender: 'typename' }
        }, {
            title: '商品图片',
            dataIndex: 'goodsImg',
            scopedSlots: { customRender: 'pic' }
        }, {
            title: '单位',
            dataIndex: 'unit'
        }, {
            title: '单价',
            sorter: true,
            dataIndex: 'unitPrice'
        }, {
            title: '成本',
            sorter: true,
            dataIndex: 'costPrice'
        // }, {
        //    title: '描述',
        //    dataIndex: 'description',
        //    ellipsis: true
        }, {
            title: '商品状态',
            sorter: true,
            scopedSlots: { customRender: 'statusSlot' },
            dataIndex: 'status'
        }, {
        //    title: '是否推荐',
        //    sorter: true,
        //    width: '110px',
            // scopedSlots: { customRender: 'recommend' },
        //    customRender: (text) => text==1 ? '是' : '否' ,
        //    dataIndex: 'isrecommend'
        // }, {
            title: '排序',
            sorter: true,
            dataIndex: 'sort'
        }, {
            title: '发布时间',
            sorter: true,
            width: '150px',
            customRender: (text) => text ? moment(text).format('YYYY-MM-DD HH:mm') : '',
            dataIndex: 'publishTime'
        }, {
            title: '过期时间',
            sorter: true,
            width: '150px',
            customRender: (text) => text ? moment(text).format('YYYY-MM-DD HH:mm') : '',
            dataIndex: 'expiredTime'
        }, {
            title: '操作',
            dataIndex: 'action',
            width: '150px',
            scopedSlots: { customRender: 'action' }
        }
    ]

    export default {
        name: 'TableList',
        components: {
            STable,
            Ellipsis,
            EditForm
        },
        data () {
            this.columns = columns
            return {
                // create model
                visible: false,
                title: '新增',
                confirmLoading: false,
                mdl: null,
                // 高级搜索 展开/关闭
                advanced: false,
                // 查询参数
                queryParam: {},
                // 加载数据方法 必须为 Promise 对象
                loadData: parameter => {
                    const requestParameters = Object.assign({}, parameter, this.queryParam)
                    // 删除为空字符串属性
                    Object.keys(requestParameters).forEach(item => {
                        if (!requestParameters[item]) delete requestParameters[item]
                    })

                    // 获取商品类型
                    getGoodsTypeDict().then(res => {
                        if (res.code === 200) {
                            this.typeList = res.result
                        }
                    })

                    // 设置获取全部状态
                    if (requestParameters['status'] && requestParameters['status'] === 0) delete requestParameters['status']
                    console.log('loadData request parameters:', requestParameters)
                    return getSsGoodsInfoList(requestParameters)
                        .then(res => {
                            return res.result
                        })
                },
                selectedRowKeys: [],
                selectedRows: [],
                typeList: []
            }
        },
        filters: {
            statusFilter(type) {
                return goodsStatusMap[type].text
            },
            statusTypeFilter(type) {
                return goodsStatusMap[type].status
            }
        },
        computed: {
            rowSelection () {
                return {
                    selectedRowKeys: this.selectedRowKeys,
                    onChange: this.onSelectChange
                }
            }
        },
        methods: {
            handleAdd () {
                this.mdl = null
                this.title = '新增'
                this.visible = true
            },
            handleEdit (record) {
                this.title = '修改'
                this.visible = true
                this.mdl = { ...record }
            },
            handleOk () {
                const form = this.$refs.editForm.form
                this.confirmLoading = true
                form.validateFields((errors, values) => {
                    if (!errors) {
                         // 日期格式化
                            values.createTime = moment(values.createTime).format('YYYY-MM-DD HH:mm:ss')
                            values.lastUpdateTime = moment(values.lastUpdateTime).format('YYYY-MM-DD HH:mm:ss')
                            values.publishTime = moment(values.publishTime).format('YYYY-MM-DD HH:mm:ss')
                            values.expiredTime = moment(values.expiredTime).format('YYYY-MM-DD HH:mm:ss')

                        if (values.id > 0) {
                            // 修改 e.g.

                            saveSsGoodsInfo(values).then(res => {
                                this.visible = false
                                this.confirmLoading = false
                                // 重置表单数据
                                form.resetFields()
                                // 刷新表格
                                this.$refs.table.refresh()

                                this.$message.info('修改成功')
                            })
                        } else {
                            // 新增
                            saveSsGoodsInfo(values).then(res => {
                                this.visible = false
                                this.confirmLoading = false
                                // 重置表单数据
                                form.resetFields()
                                // 刷新表格
                                this.$refs.table.refresh()

                                this.$message.info('新增成功')
                            })
                        }
                    } else {
                        this.confirmLoading = false
                    }
                })
            },
            handleconfirmDel () {
                this.$confirm({
                    title: '是否确认要删除选中数据?',
                    onOk: this.handleBatchDel,
                    class: '提示'
                })
            },
            handleBatchDel () {
                batchDelSsGoodsInfo(this.selectedRowKeys).then(res => {
                    this.confirmLoading = false
                    // 刷新表格
                    this.$refs.table.refresh()

                    this.$message.info('删除成功')
                })
            },
            handleDel (record) {
                if (record.id > 0) {
                    // 修改 e.g.
                    delSsGoodsInfo(record).then(res => {
                        this.confirmLoading = false
                        // 刷新表格
                        this.$refs.table.refresh()

                        this.$message.info('删除成功')
                    })
                }
            },

            handleCancel () {
                this.visible = false

                const form = this.$refs.editForm.form
                form.resetFields() // 清理表单数据（可不做）
            },
            onSelectChange (selectedRowKeys, selectedRows) {
                this.selectedRowKeys = selectedRowKeys
                this.selectedRows = selectedRows
            },
            toggleAdvanced () {
                this.advanced = !this.advanced
            },
            resetSearchForm () {
                this.queryParam = {
                    date: moment(new Date())
                }
                // 刷新表格
                this.$refs.table.refresh()
            },
            handleTypeName(id) {
                for (var i = 0; i < this.typeList.length; i++) {
                    if(id === this.typeList[i].id){
                      var s = this.typeList[i].type_name
                      break
                    }
                }
                return s
             }
        }
    }
</script>
