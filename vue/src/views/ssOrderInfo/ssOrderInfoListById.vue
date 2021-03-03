<template>
  <page-header-wrapper>
    <a-card :bordered="false">
      <div class="table-page-search-wrapper">
        <a-form layout="inline">
          <a-row :gutter="48">
            <a-col :md="8" :sm="24">
              <a-form-item label="水站名称">
                <a-input v-model="queryParam.stationName" placeholder=""/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="订单状态">
                <a-select v-model="queryParam.orderStatus" placeholder="请选择" default-value="0">
                  <a-select-option :value="0">全部</a-select-option>
                  <a-select-option :value="1">待分配</a-select-option>
                  <a-select-option :value="2">配送中</a-select-option>
                  <a-select-option :value="3">已完成</a-select-option>
                  <a-select-option :value="4">已取消</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <template v-if="advanced">
            </template>
            <a-col :md="!advanced && 8 || 24" :sm="24">
              <span class="table-page-search-submitButtons" :style="advanced && { float: 'right', overflow: 'hidden' } || {} ">
                <a-button type="primary" @click="$refs.table.refresh(true)">查询</a-button>
                <a-button style="margin-left: 8px" @click="resetSearchForm">重置</a-button>
              </span>
            </a-col>
          </a-row>
        </a-form>
      </div>

      <div class="table-operator">
        <a-button type="primary" v-action:add icon="plus" @click="handleAdd">新建</a-button>
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
        :scroll="{ x: 1500}"
        showPagination="auto"
      >
        <span slot="serial" slot-scope="text, record, index">
          {{ index + 1 }}
        </span>
        <span slot="status" slot-scope="text">
          <a-badge :status="text | statusTypeFilter" :text="text | statusFilter" />
        </span>
        <span slot="action" slot-scope="text, record">
          <template>
            <a v-action:edit @click="handleEdit(record)">修改</a>
            <a-divider type="vertical" />
            <a-popconfirm title="是否要删除当前数据？" @confirm="handleDel(record)">
              <a v-action:delete style="color: red">删除</a>
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
    import { statusMap } from '@/api/RC'
    import { getSsOrderInfoListById, saveSsOrderInfo, delSsOrderInfo, batchDelSsOrderInfo } from '@/api/ssOrderInfo'
    import EditForm from './ssOrderInfoForm'

    const columns = [
        {
            title: '序号',
            sorter: true,
            width: '80px',
            dataIndex: 'id',
            fixed: 'left',
            width: 150
        },  
        {
            title: '订单编号',
            sorter: true,
            dataIndex: 'orderNumber',
            fixed: 'left',
            width: 150
        },  
        {
            title: '订单小计',
            sorter: true,
            dataIndex: 'amount',
            fixed: 'left',
            width: 150
        },  
        {
            title: '订单状态',
            sorter: true,
            dataIndex: 'orderStatus',
            width: 150,
            fixed: 'left',
            customRender:function (text) {
                if(text==1){
                    return '待分配'
                }else if(text==2){
                    return '配送中'
                }else if(text==3){
                    return '已完成'
                }else if(text==4){
                    return '已取消'
                }else{
                    return text
                }
            }
        },          
        {
            title: '商品名称',
            sorter: true,
            dataIndex: 'goodsName',
            width: 150
        },  
        {
            title: '商品类型',
            sorter: true,
            dataIndex: 'goodsTypeId',
            width: 150,
            customRender:function (text) {
                if(text==1){
                    return '水票'
                }else if(text==2){
                    return '水'
                }else{
                    return 'text'
                }
            }
        }, 
        {
            title: '水站名称',
            sorter: true,
            dataIndex: 'stationName',
            width: 150
        },  
        {
            title: '水站负责人',
            sorter: true,
            dataIndex: 'stationLeaderName',
            width: 150
        }, 
        {
            title: '水站电话',
            sorter: true,
            dataIndex: 'stationPhone',
            width: 150
        },  
        {
            title: '水站地址',
            sorter: true,
            dataIndex: 'stationAddress',
            width: 150
        },                   
        {
            title: '收货人',
            sorter: true,
            dataIndex: 'ssCustomerAddressContacts',
            width: 150
        },   
        {
            title: '收货人电话',
            sorter: true,
            dataIndex: 'ssCustomerAddressPhone',
            width: 150
        },     
        {
            title: '收货人地址',
            sorter: true,
            dataIndex: 'ssCustomerContactsAddress',
            width: 150
        },        
        {
            title: '下单时间',
            sorter: true,
            width: '150px',
            customRender: (text) => text ? moment(text).format('YYYY-MM-DD HH:mm') : '',
            dataIndex: 'orderDate',
            width: 150
        },        
        {
            title: '接单时间',
            sorter: true,
            width: '150px',
            customRender: (text) => text ? moment(text).format('YYYY-MM-DD HH:mm') : '',
            dataIndex: 'receiveTime',
            width: 150
        },
        {
            title: '配送时间',
            sorter: true,
            width: '150px',
            customRender: (text) => text ? moment(text).format('YYYY-MM-DD HH:mm') : '',
            dataIndex: 'deleveryTime',
            width: 150
        },
        {
            title: '配送完成时间',
            sorter: true,
            width: '150px',
            customRender: (text) => text ? moment(text).format('YYYY-MM-DD HH:mm') : '',
            dataIndex: 'finishTime',
            width: 150
        }
        // ,
        // {
        //     title: '操作',
        //     dataIndex: 'action',
        //     width: '150px',
        //     scopedSlots: { customRender: 'action' }
        // }
    ]



    export default {
        name: 'TableList',
        components: {
            STable,
            Ellipsis,
            EditForm
        },
        data () {
            this.columns = columns,
             this.uid = this.$route.params.customerId
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
                    const requestParameters = Object.assign({}, parameter, this.queryParam,this.$route.params)
                   // 删除为空字符串属性
                    Object.keys(requestParameters).forEach(item => {
                        if (!requestParameters[item]) delete requestParameters[item]
                    })
                    // 设置获取全部状态
                    if (requestParameters['orderStatus'] && requestParameters['orderStatus'] === 0) delete requestParameters['orderStatus']
                    console.log('loadData request parameters:', requestParameters)
                    return getSsOrderInfoListById(requestParameters)
                        .then(res => {
                            return res.result
                        })
                },
                selectedRowKeys: [],
                selectedRows: []
            }
        },
        filters: {
            statusFilter (type) {
                return statusMap[type].text
            },
            statusTypeFilter (type) {
                return statusMap[type].status
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
                            values.orderDate = moment(values.orderDate).format('YYYY-MM-DD HH:mm:ss')
                            values.appointTime = moment(values.appointTime).format('YYYY-MM-DD HH:mm:ss')


                        if (values.id > 0) {
                            // 修改 e.g.

                            saveSsOrderInfo(values).then(res => {
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
                            saveSsOrderInfo(values).then(res => {
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
                batchDelSsOrderInfo(this.selectedRowKeys).then(res => {
                    this.confirmLoading = false
                    // 刷新表格
                    this.$refs.table.refresh()

                    this.$message.info('删除成功')
                })
            },
            handleDel (record) {
                if (record.id > 0) {
                    // 修改 e.g.
                    delSsOrderInfo(record).then(res => {
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
            }
        }
    }
</script>
